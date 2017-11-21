package com.criteo.demo.common.dao;

import com.criteo.demo.common.model.ProductView;
import org.springframework.data.repository.CrudRepository;

public interface ProductViewRepository extends CrudRepository<ProductView, Key> {
}
