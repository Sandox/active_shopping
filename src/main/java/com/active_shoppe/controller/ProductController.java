package com.active_shoppe.controller;

import com.active_shoppe.dto.BuyProductRequestDTO;
import com.active_shoppe.dto.BuyProductResponseDTO;
import com.active_shoppe.model.ProductModel;
import com.active_shoppe.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@CrossOrigin("*")
@Slf4j
@Api(value="momentum_active_shoppe")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getAllProducts")
    @ApiOperation(value = "View a list of available products", response = ResponseEntity.class)
    public List<ProductModel> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/buyProducts/{customerId}")
    @ApiOperation(value = "Buy an existing product/products",response = ResponseEntity.class)
    public ResponseEntity buyProducts(@PathVariable final String customerId, @RequestBody final BuyProductRequestDTO products) {
        BuyProductResponseDTO buyProductResponseDTO = productService.buyProducts(customerId, products);
        return new ResponseEntity<>(buyProductResponseDTO != null
                ? buyProductResponseDTO.getMessage()
                : new HashMap<>().put("message", "Unexpected error occurred"),
                buyProductResponseDTO.getMessage() != null ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
