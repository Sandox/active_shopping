package com.active_shoppe.service;

import com.active_shoppe.repository.CustomerActiveDaysRepository;
import com.active_shoppe.dto.BuyProductRequestDTO;
import com.active_shoppe.dto.BuyProductResponseDTO;
import com.active_shoppe.model.CustomerActiveDaysModel;
import com.active_shoppe.model.CustomerModel;
import com.active_shoppe.model.ProductModel;
import com.active_shoppe.repository.CustomerRepository;
import com.active_shoppe.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final CustomerRepository customerRepository;

    @Autowired
    private final CustomerActiveDaysRepository customerActiveDaysRepository;

    @Autowired
    private final MongoTemplate mongoTemplate;

    @Autowired
    private final CustomerActiveDaysService customerActiveDaysService;

    @Override
    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public BuyProductResponseDTO buyProducts(String customerId, BuyProductRequestDTO productsId) {
        Optional<CustomerModel> customerModel = customerRepository.findByCustomerId(customerId);
        BuyProductResponseDTO buyProductResponseDTO = new BuyProductResponseDTO();
        HashMap<String, String> responseMap = new HashMap<>();

        if (customerModel.isPresent()) {
            if (productsId != null && !productsId.getProductCode().isEmpty()) {
                List<ProductModel> products = productRepository.findAll();
                List<ProductModel> usersProducts = new ArrayList<>();
                Optional<CustomerActiveDaysModel> customerActiveDaysModel = customerActiveDaysRepository.findByCustomerId(customerId);

                for (String prodCode : productsId.getProductCode()) {
                    Optional<ProductModel> availableProduct;
                    availableProduct = products.stream().filter(productModel -> productModel.getProductCode().equalsIgnoreCase(prodCode)).findFirst();
                    if (!availableProduct.isPresent()) {
                        if (!prodCode.isEmpty()) {
                            responseMap.put("error ", String.format("No product available with code [%s]", prodCode));
                        } else {
                            responseMap.put("error ", "non existent Product code provided");
                        }
                        responseMap.put("statusCode ", String.valueOf(HttpStatus.NOT_FOUND.value()));
                        buyProductResponseDTO.setMessage(responseMap);
                        return buyProductResponseDTO;
                    } else {
                        availableProduct.ifPresent(usersProducts::add);
                    }
                }
                purchaseProduct(usersProducts, customerActiveDaysModel, responseMap, buyProductResponseDTO);
            } else {
                responseMap.put("message", "Product list is empty");
                buyProductResponseDTO.setMessage(responseMap);
                log.info("Product list is empty");
            }
        } else {
            responseMap.put("message", "Customer does not exist");
            buyProductResponseDTO.setMessage(responseMap);
            log.info("Customer with id {} does not exist", customerId);
        }
        return buyProductResponseDTO;
    }

    private void purchaseProduct(List<ProductModel> usersProducts, Optional<CustomerActiveDaysModel> customerActiveDaysModel, HashMap<String, String> responseMap, BuyProductResponseDTO buyProductResponseDTO) {
        usersProducts.forEach(productModel -> {
            int productCost = productModel.getProductCost();
            if (customerActiveDaysModel.isPresent()) {
                if (customerActiveDaysModel.get().getProductCost() > 0) {
                    int pointsDifference = customerActiveDaysModel.get().getProductCost() - productCost;
                    if (pointsDifference >= 0) {
                        customerActiveDaysService.updateCustomerProductCost(customerActiveDaysModel.get().getCustomerId(), pointsDifference);
                        responseMap.put("message", "OK");
                        buyProductResponseDTO.setMessage(responseMap);
                        log.info("Customer  {} points has been updated {}, new points ", customerActiveDaysModel.get().getCustomerId(), customerActiveDaysModel.get().getProductCost());
                    } else {
                        responseMap.put("message", "Customer does have sufficient active days to buy the product");
                        buyProductResponseDTO.setMessage(responseMap);
                        log.info("Customer {} does not have enough points {}, required {}", customerActiveDaysModel.get().getCustomerId(), customerActiveDaysModel.get().getProductCost(), productCost);
                    }
                } else {
                    responseMap.put("message", "Customer does have sufficient active days to buy the product");
                    buyProductResponseDTO.setMessage(responseMap);
                    log.info("Customer with id {} cannot buy product {}", customerActiveDaysModel.get().getCustomerId(), productModel.getProductCode());
                }
            } else {
                responseMap.put("message", "Customer has invalid active days");
                log.info("Customer with id {} does not exist in customer active document", customerActiveDaysModel.get().getCustomerId());
            }
        });
    }
}
