package com.active_shoppe.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class BuyProductResponseDTO {
    HashMap<String, String> message;
}
