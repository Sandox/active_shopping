package com.active_shoppe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.ALWAYS)
public class BuyProductRequestDTO {
    List<String> productCode;
}
