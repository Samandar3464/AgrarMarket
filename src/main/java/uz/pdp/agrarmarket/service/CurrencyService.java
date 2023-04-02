package uz.pdp.agrarmarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.agrarmarket.entity.Currency;
import uz.pdp.agrarmarket.exception.RecordNotFoundException;
import uz.pdp.agrarmarket.repository.CurrencyRepository;

@Service
@RequiredArgsConstructor
public class CurrencyService implements BaseService<Currency> {

    private final CurrencyRepository currencyRepository;
    @Override
    public ResponseEntity<?> add(Currency currency) {
        Currency save = currencyRepository.save(currency);
        return ResponseEntity.ok(save);
    }
    @Override
    public ResponseEntity<?> getList() {
        return ResponseEntity.ok(currencyRepository.findAll());
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        Currency currency = currencyRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Currency not found "));
        currency.setActive(false);
        currencyRepository.save(currency);
        return ResponseEntity.ok("Currency deleted successfully");
    }

    @Override
    public ResponseEntity<?> getById(Integer id) {
        Currency currency = currencyRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Currency not found "));
        currency.setActive(false);
        currencyRepository.save(currency);
        return ResponseEntity.ok("Currency deleted successfully");
    }

    @Override
    public ResponseEntity<?> update(Currency currency, Integer id) {
        return null;
    }

}
