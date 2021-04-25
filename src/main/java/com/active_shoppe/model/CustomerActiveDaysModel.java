package com.active_shoppe.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document
@Getter
@Setter
public class CustomerActiveDaysModel {
    @Id
    @ApiModelProperty(notes = "The database generated customer ID")
    String customerId;
    @ApiModelProperty(notes = "The product cost", required = true)
    int productCost;
}
