package com.active_shoppe.service;

import com.active_shoppe.dto.BuyProductRequestDTO;
import com.active_shoppe.dto.BuyProductResponseDTO;
import com.active_shoppe.model.ProductModel;

import java.util.List;

public interface ProductService {

    List<ProductModel> getAllProducts();

    BuyProductResponseDTO buyProducts(String customerId, BuyProductRequestDTO productId);
}
