package uz.pdp.agrarmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.agrarmarket.entity.Currency;

public interface CurrencyRepository extends JpaRepository<Currency , Integer> {
    Currency findByIdAndActive(int id, boolean active);
}
