package uz.pdp.agrarmarket.AdminController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.agrarmarket.model.address.CityRegisterDto;
import uz.pdp.agrarmarket.service.Address.CityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/city")
public class AminControllerUpToCity {
    private final CityService cityService;

    @PostMapping("/addCity")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> addCity(@Validated @RequestBody CityRegisterDto cityOrDistrictRegisterDto) {
        return cityService.add(cityOrDistrictRegisterDto);
    }

    @GetMapping("/getCityList")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getList() {
        return cityService.getList();
    }

    @GetMapping("/getCity/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> getCity(@PathVariable Integer id) {
        return cityService.getById(id);
    }

    @PutMapping("/updateCity/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> updateCity(@PathVariable Integer id, @Validated @RequestBody CityRegisterDto cityOrDistrictRegisterDto) {
        return cityService.update(cityOrDistrictRegisterDto, id);
    }

    @DeleteMapping("/deleteCity/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteCity(@PathVariable Integer id) {
        return cityService.delete(id);
    }
}
