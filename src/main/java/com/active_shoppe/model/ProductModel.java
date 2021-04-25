package com.active_shoppe.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;

@Document
@Getter
@Setter
public class ProductModel implements Serializable {
    @Id
    @ApiModelProperty(notes = "The database generated product code")
    String productCode;

    @ApiModelProperty(notes = "The name of a product", required = true)
    String productName;

    @ApiModelProperty(notes = "The cost of a product", required = true)
    int productCost;
}
