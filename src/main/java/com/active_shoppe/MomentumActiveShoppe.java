package com.active_shoppe;

import com.active_shoppe.dto.ProductDTO;
import com.active_shoppe.model.CustomerActiveDaysModel;
import com.active_shoppe.model.CustomerModel;
import com.active_shoppe.model.ProductModel;
import com.active_shoppe.repository.CustomerActiveDaysRepository;
import com.active_shoppe.repository.CustomerRepository;
import com.active_shoppe.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

@Slf4j
@SpringBootApplication
public class MomentumActiveShoppe implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerActiveDaysRepository customerActiveDaysRepository;

    public static void main(String[] args) {
        SpringApplication.run(MomentumActiveShoppe.class, args);
    }

    @Override
    public void run(String... args) {
        productDataInit();
        customerDataInit();
    }

    public void productDataInit() {
        productRepository.deleteAll();
        HashMap<String, ProductDTO> products = new HashMap<>();
        ProductDTO product = new ProductDTO();
        product.setProductCode("P1");
        product.setProductCost(10); // Cost of the product
        product.setProductName("Product Name");
        products.put("p1", product);

       // product.setProductCode("P2");
        ProductModel productModel = new ProductModel();
        productModel.setProductCode(product.getProductCode());
        productModel.setProductCost(product.getProductCost());
        productModel.setProductName(product.getProductName());
        productRepository.save(productModel);
    }

    public void customerDataInit() {
        customerRepository.deleteAll();
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerName("Sandile");
        customerActiveDaysInit(customerModel.getCustomerId());
        customerRepository.save(customerModel);
        log.info("save customer with {} and Customer {}", customerModel.getCustomerId(), customerModel.toString());
    }

    public void customerActiveDaysInit(String customerId) {
        customerActiveDaysRepository.deleteAll();
        CustomerActiveDaysModel customerActiveDaysModel = new CustomerActiveDaysModel();
        customerActiveDaysModel.setCustomerId(customerId);
        customerActiveDaysModel.setProductCost(10); // avaiable balance that customer has
        customerActiveDaysRepository.save(customerActiveDaysModel);
    }

}
