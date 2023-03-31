package uz.pdp.agrarmarket.repository.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.agrarmarket.entity.address.City;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City> findByNameAndProvinceId(String name, Integer province_id);
}
