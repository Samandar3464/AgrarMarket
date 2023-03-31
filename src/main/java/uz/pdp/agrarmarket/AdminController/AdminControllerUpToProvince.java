package uz.pdp.agrarmarket.AdminController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.agrarmarket.model.address.ProvinceRegisterDto;
import uz.pdp.agrarmarket.service.Address.ProvinceService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AdminControllerUpToProvince {
    private final ProvinceService provinceService;

    @PostMapping("/addProvince")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> addProvince(@Validated @RequestBody ProvinceRegisterDto provinceRegisterDto) {
        return provinceService.add(provinceRegisterDto);
    }

    @GetMapping("/getProvinceList")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getList() {
        return provinceService.getList();
    }

    @GetMapping("/getProvince/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getProvince(@PathVariable Integer id) {
        return provinceService.getById(id);
    }

    @PutMapping("/updateProvince/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> updateProvince(@PathVariable Integer id, @Validated @RequestBody ProvinceRegisterDto provinceRegisterDto) {
        return provinceService.update(provinceRegisterDto, id);
    }
    @DeleteMapping("/deleteProvince/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteProvince(@PathVariable Integer id) {
        return provinceService.delete(id);
    }

}
