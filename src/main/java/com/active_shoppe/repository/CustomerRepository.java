package com.active_shoppe.repository;

import com.active_shoppe.model.CustomerModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerModel, String> {
    Optional<CustomerModel> findByCustomerId(String customerId);
}
