package uz.pdp.agrarmarket.service;

import org.springframework.http.ResponseEntity;

public interface BaseService<R> {
    ResponseEntity<?> add(R r);
    ResponseEntity<?> getList();
    ResponseEntity<?> delete(Integer id);
    ResponseEntity<?> getById(Integer id);
    ResponseEntity<?> update(R r , Integer id);
}
