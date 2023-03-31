package uz.pdp.agrarmarket.service.Address;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.agrarmarket.entity.address.City;
import uz.pdp.agrarmarket.entity.address.Province;
import uz.pdp.agrarmarket.exception.RecordAlreadyExistException;
import uz.pdp.agrarmarket.exception.RecordNotFoundException;
import uz.pdp.agrarmarket.model.address.CityRegisterDto;
import uz.pdp.agrarmarket.repository.Address.CityRepository;
import uz.pdp.agrarmarket.repository.Address.ProvinceRepository;
import uz.pdp.agrarmarket.service.BaseService;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CityService implements BaseService<CityRegisterDto> {
    private final CityRepository cityRepository;
    private final ProvinceRepository provinceRepository;

    @Override
    public ResponseEntity<?> add(CityRegisterDto cityRegisterDto) {
        Optional<City> byName = cityRepository.findByNameAndProvinceId(cityRegisterDto.getName(), cityRegisterDto.getProvinceId());
        if (byName.isPresent()) {
            throw new RecordAlreadyExistException("This province already have into this province");
        }
        Province province = provinceRepository.findById(cityRegisterDto.getProvinceId()).orElseThrow(() -> new RecordNotFoundException("Province not found"))
        ;
        City city = new City();
        city.setName(cityRegisterDto.getName());
        city.setProvince(province);
        return ResponseEntity.ok(cityRepository.save(city));
    }

    @Override
    public ResponseEntity<?> getList() {
        return ResponseEntity.ok(cityRepository.findAll());
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        cityRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("City not found ")));
        cityRepository.deleteById(id);
        return ResponseEntity.ok("Successfully deleted");
    }

    @Override
    public ResponseEntity<?> getById(Integer id) {
        City city = cityRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("CityService not found ")));
        return ResponseEntity.ok(city);
    }

    @Override
    public ResponseEntity<?> update(CityRegisterDto cityRegisterDto, Integer id) {
        City city = cityRepository.findById(id).orElseThrow((() -> new RecordNotFoundException("CityService not found ")));
        Province province = provinceRepository.findById(cityRegisterDto.getProvinceId()).orElseThrow(() -> new RecordNotFoundException("Province not found"));
        city.setName(cityRegisterDto.getName());
        city.setProvince(province);
        return ResponseEntity.ok(cityRepository.save(city));
    }
}
