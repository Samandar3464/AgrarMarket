package uz.pdp.agrarmarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import ws.schild.jave.EncoderException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.agrarmarket.entity.AttachmentEntity;
import uz.pdp.agrarmarket.exception.attach.CouldNotRead;
import uz.pdp.agrarmarket.exception.attach.FileUploadException;
import uz.pdp.agrarmarket.exception.attach.OriginalFileNameNullException;
import uz.pdp.agrarmarket.exception.attach.SomethingWentWrong;
import uz.pdp.agrarmarket.model.attach.AttachmentDownloadDTO;
import uz.pdp.agrarmarket.model.attach.AttachmentResponseDTO;
import uz.pdp.agrarmarket.repository.AttachmentRepository;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository repository;

    @Value("${attach.upload.folder}")
    public String attachUploadFolder;

//    @Value("${attach.download.url}")
    public String attachDownloadUrl="/static/image/";


    public String getYearMonthDay() {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();
        return year + "/" + month + "/" + day; // 2022/03/23
    }


    public String getExtension(String fileName) {
        // mp3/jpg/npg/mp4.....
        if (fileName == null) {
            throw new OriginalFileNameNullException("File name null");
        }
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public AttachmentEntity saveToSystem(MultipartFile file) {
        try {
            String pathFolder = getYearMonthDay(); // 2022/04/23
            File folder = new File(attachUploadFolder + pathFolder); // attaches/2022/04/23
            if (!folder.exists()) folder.mkdirs();
            String fileName = UUID.randomUUID().toString(); // dasdasd-dasdasda-asdasda-asdasd
            String extension = getExtension(file.getOriginalFilename()); //zari.jpg

            // attaches/2022/04/23/dasdasd-dasdasda-asdasda-asdasd.jpg
            byte[] bytes = file.getBytes();
            Path path = Paths.get(attachUploadFolder + pathFolder + "/" + fileName + "." + extension);
            File f = Files.write(path, bytes).toFile();

            AttachmentEntity entity = new AttachmentEntity();
            entity.setNewName(fileName);
            entity.setOriginName(file.getOriginalFilename());
            entity.setType(extension);
            entity.setPath(pathFolder);
            entity.setSize(file.getSize());
            entity.setContentType(file.getContentType());

            if (extension.equalsIgnoreCase("mp4")
                    || extension.equalsIgnoreCase("mov")
                    || extension.equalsIgnoreCase("wmv")
                    || extension.equalsIgnoreCase("avi")
            ) {
                MultimediaObject instance = new MultimediaObject(f);
                MultimediaInfo result = instance.getInfo();
                entity.setDuration((double) (result.getDuration() / 1000));
            }
            return repository.save(entity);
        } catch (IOException e) {
            throw new FileUploadException("File could not upload");
        } catch (EncoderException e) {
            throw new RuntimeException(e);
        }

    }

    private AttachmentEntity getAttachment(String fileName) throws FileNotFoundException {
        String newName = fileName.split("\\.")[0];
        Optional<AttachmentEntity> optional = repository.findByNewName(newName);
        if (optional.isEmpty()) {
            throw new FileNotFoundException("File Not Found");
        }
        return optional.get();
    }

    public byte[] open(String fileName) {
        try {
            AttachmentEntity entity = getAttachment(fileName);

            Path file = Paths.get(attachDownloadUrl + entity.getPath() + "/" + fileName+"."+entity.getType());
            return Files.readAllBytes(file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public AttachmentDownloadDTO download(String fileName) {
        try {
            AttachmentEntity entity = getAttachment(fileName);

            File file = new File(attachUploadFolder + entity.getPath() + "/" + fileName);

            File dir = file.getParentFile();
            File rFile = new File(dir, entity.getNewName() + "." + entity.getType());

            Resource resource = new UrlResource(rFile.toURI());

            if (resource.exists() || resource.isReadable()) {
                AttachmentDownloadDTO attachmentDownloadDTO = new AttachmentDownloadDTO(resource, "image/jpeg");
                return attachmentDownloadDTO;
            } else {
                throw new CouldNotRead("Could not read");
            }
        } catch (MalformedURLException | FileNotFoundException e) {
            throw new SomethingWentWrong("Something went wrong");
        }
    }


    public Page<AttachmentResponseDTO> getWithPage(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AttachmentEntity> pageObj = repository.findAll(pageable);

        List<AttachmentEntity> entityList = pageObj.getContent();
        List<AttachmentResponseDTO> dtoList = new ArrayList<>();


        for (AttachmentEntity entity : entityList) {

            AttachmentResponseDTO dto = new AttachmentResponseDTO();
            dto.setId(entity.getId());
            dto.setPath(entity.getPath());
            dto.setExtension(entity.getType());
            dto.setUrl(attachUploadFolder + "/" + entity.getId() + "." + entity.getType());
            dto.setOriginalName(entity.getOriginName());
            dto.setSize(entity.getSize());
            dto.setCreatedData(entity.getCreatedDate());
            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, pageObj.getTotalElements());
    }


    public String deleteById(String fileName) {
        try {
            AttachmentEntity entity = getAttachment(fileName);
            Path file = Paths.get(attachUploadFolder + entity.getPath() + "/" + fileName);

            Files.delete(file);
            repository.deleteById(entity.getId());

            return "deleted";
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getUrl(Long imageId) throws FileNotFoundException {
        Optional<AttachmentEntity> optional = repository.findById(imageId);
        if (optional.isEmpty()) {
            throw new FileNotFoundException("File not found");
        }
        return attachUploadFolder + optional.get().getId() + "." + optional.get().getType();

    }


}

