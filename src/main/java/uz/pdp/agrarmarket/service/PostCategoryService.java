package uz.pdp.agrarmarket.service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.agrarmarket.entity.PostCategory;
import uz.pdp.agrarmarket.exception.RecordAlreadyExistException;
import uz.pdp.agrarmarket.exception.RecordNotFoundException;
import uz.pdp.agrarmarket.model.request.PostCategoryRegisterDto;
import uz.pdp.agrarmarket.repository.PostCategoryRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostCategoryService implements BaseService<PostCategoryRegisterDto> {

    private final PostCategoryRepository postCategoryRepository;


    @Override
    public ResponseEntity<?> add(PostCategoryRegisterDto postCategoryRegisterDto) {
        Optional<PostCategory> byName = postCategoryRepository.findByName(postCategoryRegisterDto.getName());
        if (byName.isPresent()) {
            throw new RecordAlreadyExistException("This category already have into this city");
        }
        PostCategory workCategory = new PostCategory();
        workCategory.setName(postCategoryRegisterDto.getName());
        workCategory.setActive(true);
        PostCategory save = postCategoryRepository.save(workCategory);
        return  ResponseEntity.ok(save);
    }

    @Override
    public ResponseEntity<?> getList() {
        return ResponseEntity.ok(postCategoryRepository.findAllByActive(true));
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        PostCategory category = postCategoryRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Category not found ")));
        category.setActive(false);
        postCategoryRepository.save(category);
        return ResponseEntity.ok("Successfully deleted");
    }

    @Override
    public ResponseEntity<?> getById(Integer id) {
        PostCategory workCategory = postCategoryRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Category not found ")));
        return ResponseEntity.ok(workCategory);
    }

    @Override
    public ResponseEntity<?> update(PostCategoryRegisterDto postCategoryRegisterDto, Integer id) {
        PostCategory workCategory = postCategoryRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("Category not found ")));
        workCategory.setName(postCategoryRegisterDto.getName());
        PostCategory save = postCategoryRepository.save(workCategory);
        return  ResponseEntity.ok(save);
    }
}
