package uz.pdp.agrarmarket.service.Address;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.agrarmarket.entity.address.City;
import uz.pdp.agrarmarket.entity.address.District;
import uz.pdp.agrarmarket.entity.address.Province;
import uz.pdp.agrarmarket.exception.RecordAlreadyExistException;
import uz.pdp.agrarmarket.exception.RecordNotFoundException;
import uz.pdp.agrarmarket.model.address.DistrictRegisterDto;
import uz.pdp.agrarmarket.repository.Address.DistrictRepository;
import uz.pdp.agrarmarket.repository.Address.ProvinceRepository;
import uz.pdp.agrarmarket.service.BaseService;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class DistrictService implements BaseService<DistrictRegisterDto> {
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;

    @Override
    public ResponseEntity<?> add(DistrictRegisterDto districtRegisterDto) {
        Optional<City> byName = districtRepository.findByNameAndProvinceId(districtRegisterDto.getName(), districtRegisterDto.getProvinceId());
        if (byName.isPresent()) {
            throw new RecordAlreadyExistException("This district already have into this province");
        }
        Province province = provinceRepository.findById(districtRegisterDto.getProvinceId()).orElseThrow(() -> new RecordNotFoundException("Province not found"))
                ;
        District district = new District();
        district.setName(districtRegisterDto.getName());
        district.setProvince(province);
        return ResponseEntity.ok(districtRepository.save(district));
    }

    @Override
    public ResponseEntity<?> getList() {
        return ResponseEntity.ok(districtRepository.findAll());
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        districtRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("District not found ")));
        districtRepository.deleteById(id);
        return ResponseEntity.ok("Successfully deleted");
    }

    @Override
    public ResponseEntity<?> getById(Integer id) {
        District district = districtRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("District not found ")));
        return ResponseEntity.ok(district);
    }

    @Override
    public ResponseEntity<?> update( DistrictRegisterDto districtRegisterDto, Integer id) {
        District district =districtRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("CityService not found ")));
        Province province = provinceRepository.findById(districtRegisterDto.getProvinceId()).orElseThrow(() -> new RecordNotFoundException("Province not found"));
        district.setName(districtRegisterDto.getName());
        district.setProvince(province);
        return ResponseEntity.ok(districtRepository.save(district));
    }
}
