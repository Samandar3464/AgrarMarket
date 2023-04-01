package uz.pdp.agrarmarket.repository.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.agrarmarket.entity.address.City;
import uz.pdp.agrarmarket.entity.address.District;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    Optional<District> findByName(String name);

    Optional<District> findByNameAndProvinceId(String name, int provinceId);
}
