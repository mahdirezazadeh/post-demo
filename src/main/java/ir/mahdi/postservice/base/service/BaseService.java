package ir.mahdi.postservice.base.service;


import ir.mahdi.postservice.base.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BaseService<E extends BaseEntity<ID>, ID extends Serializable> {

    E save(E e);

    List<E> saveAll(Collection<E> collection);

    Optional<E> findById(ID id);

    List<E> findAll();

    Page<E> findAll(Pageable pageable);

    void deleteById(ID id);

}
