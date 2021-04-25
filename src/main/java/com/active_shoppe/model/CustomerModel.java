package com.active_shoppe.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Document
@Getter
@Setter
public class CustomerModel implements Serializable {

    @Id
    @ApiModelProperty(notes = "The database generated customer ID")
    String customerId;

    @ApiModelProperty(notes = "Name of a customer", required = true)
    String customerName;

    public CustomerModel() {
        this.customerId = UUID.randomUUID().toString();
    }
}
