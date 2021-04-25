package com.active_shoppe.repository;

import com.active_shoppe.model.CustomerActiveDaysModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerActiveDaysRepository extends MongoRepository<CustomerActiveDaysModel, String> {
    Optional<CustomerActiveDaysModel> findByCustomerId(String customerId);
}
