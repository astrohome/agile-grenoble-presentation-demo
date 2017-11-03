package com.criteo.demo.engine.dao;

import com.criteo.demo.engine.model.ProductView;
import org.springframework.data.repository.CrudRepository;

public interface ProductViewRepository extends CrudRepository<ProductView, Integer> {
}
