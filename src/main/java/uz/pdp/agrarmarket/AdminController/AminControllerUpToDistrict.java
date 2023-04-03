package uz.pdp.agrarmarket.AdminController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.agrarmarket.model.address.DistrictRegisterDto;
import uz.pdp.agrarmarket.service.Address.DistrictService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/district")
public class AminControllerUpToDistrict {
    private final DistrictService districtService;

    @PostMapping("/addDistrict")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> addDistrict(@Validated @RequestBody DistrictRegisterDto districtRegisterDto) {
        return districtService.add(districtRegisterDto);
    }

    @GetMapping("/getDistrictList")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getList() {
        return districtService.getList();
    }


    @GetMapping("/getDistrict/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getDistrict(@PathVariable Integer id) {
        return districtService.getById(id);
    }

    @PutMapping("/updateDistrict/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> updateDistrict(@PathVariable Integer id, @Validated @RequestBody DistrictRegisterDto cityOrDistrictRegisterDto) {
        return districtService.update(cityOrDistrictRegisterDto, id);
    }

    @DeleteMapping("/deleteDistrict/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteDistrict(@PathVariable Integer id) {
        return districtService.delete(id);
    }

}
