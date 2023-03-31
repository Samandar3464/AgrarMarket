package uz.pdp.agrarmarket.service.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.agrarmarket.entity.address.Province;
import uz.pdp.agrarmarket.exception.RecordAlreadyExistException;
import uz.pdp.agrarmarket.exception.RecordNotFoundException;
import uz.pdp.agrarmarket.model.address.ProvinceRegisterDto;
import uz.pdp.agrarmarket.repository.Address.ProvinceRepository;
import uz.pdp.agrarmarket.service.BaseService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProvinceService implements BaseService<ProvinceRegisterDto> {
    private final ProvinceRepository provinceRepository;

    @Override
    public ResponseEntity<?> add(ProvinceRegisterDto provinceRegisterDto) {
        Optional<Province> byName = provinceRepository.findByName(provinceRegisterDto.getName());
        if (byName.isPresent()){
            throw new RecordAlreadyExistException("Province already exist");
        }
        Province save = provinceRepository.save(
                Province.builder()
                        .name(provinceRegisterDto.getName())
                        .build());
        return ResponseEntity.ok(save);
    }

    @Override
    public ResponseEntity<?> getList() {
        return ResponseEntity.ok(provinceRepository.findAll());
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        provinceRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Province not found ")));
        provinceRepository.deleteById(id);
        return ResponseEntity.ok("Successfully deleted");
    }

    @Override
    public ResponseEntity<?> getById(Integer id) {
        Province province = provinceRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Province not found ")));
        return ResponseEntity.ok(province);
    }

    @Override
    public ResponseEntity<?> update(ProvinceRegisterDto provinceRegisterDto, Integer id) {
        if (provinceRepository.findByName(provinceRegisterDto.getName()).isPresent()){
            throw  new RecordAlreadyExistException("Province already exist");
        }
        Province province = provinceRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Province not found ")));
        province.setName(provinceRegisterDto.getName());
        return ResponseEntity.ok(provinceRepository.save(province));
    }
}
