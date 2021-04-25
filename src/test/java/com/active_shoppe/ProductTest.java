package com.active_shoppe;

import com.active_shoppe.dto.BuyProductRequestDTO;
import com.active_shoppe.dto.BuyProductResponseDTO;
import com.active_shoppe.model.CustomerActiveDaysModel;
import com.active_shoppe.model.CustomerModel;
import com.active_shoppe.model.ProductModel;
import com.active_shoppe.repository.CustomerActiveDaysRepository;
import com.active_shoppe.repository.CustomerRepository;
import com.active_shoppe.service.CustomerActiveDaysServiceImpl;
import com.active_shoppe.service.ProductServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductTest {

    @Autowired
    private CustomerActiveDaysServiceImpl customerActiveDaysService;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CustomerActiveDaysRepository customerActiveDaysRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void productsInsert() {
        List<ProductModel> productsList = productService.getAllProducts();
        Assert.assertEquals(1, productsList.size());
    }

    @Test
    public void scenario2() {
        customerRepository.deleteAll();
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerName("Sandile Test");
        customerActiveDaysInit(customerModel.getCustomerId());
        customerRepository.save(customerModel);
        BuyProductResponseDTO responseDTO = new BuyProductResponseDTO();
        BuyProductRequestDTO buyProductRequestDTO = new BuyProductRequestDTO();
        List<String> testProducts = new ArrayList();
        testProducts.add("T1 Prod");
        buyProductRequestDTO.setProductCode(testProducts);
        BuyProductResponseDTO productResponseDTO = productService.buyProducts(customerModel.getCustomerId(), buyProductRequestDTO);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("statusCode", String.valueOf(HttpStatus.NOT_FOUND.value()));
        responseMap.put("error", String.format("No product available with code [%s]", "T1 Prod"));
        responseDTO.setMessage(responseMap);
        Assert.assertEquals(productResponseDTO.getMessage().values().toArray()[1], responseDTO.getMessage().get("error"));
        Assert.assertEquals(productResponseDTO.getMessage().values().toArray()[0], responseDTO.getMessage().get("statusCode"));
    }

    public void customerActiveDaysInit(String customerId) {
        customerActiveDaysRepository.deleteAll();
        CustomerActiveDaysModel customerActiveDaysModel = new CustomerActiveDaysModel();
        customerActiveDaysModel.setCustomerId(customerId);
        customerActiveDaysModel.setProductCost(10); // available balance that customer has
        customerActiveDaysRepository.save(customerActiveDaysModel);
    }

}
