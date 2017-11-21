package com.criteo.demo.dao;

import com.criteo.demo.model.ProductView;
import org.springframework.data.repository.CrudRepository;

public interface ProductViewRepository extends CrudRepository<ProductView, Key> {
}
