package com.active_shoppe.service;

import com.active_shoppe.model.CustomerActiveDaysModel;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerActiveDaysServiceImpl implements CustomerActiveDaysService {

    @Autowired
    private final MongoTemplate mongoTemplate;

    @Override
    public int updateCustomerProductCost(String customerId, int productCost) {
        Query query = new Query(Criteria.where("customerId").is(customerId));
        Update update = new Update();
        update.set("productCost", productCost);

        UpdateResult result = mongoTemplate.updateFirst(query, update, CustomerActiveDaysModel.class);

        return result != null ? Integer.parseInt(String.valueOf(result.getMatchedCount())) : 0;
    }
}
