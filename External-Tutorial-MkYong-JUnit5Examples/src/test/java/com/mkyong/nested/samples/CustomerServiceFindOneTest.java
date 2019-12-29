package com.mkyong.nested.samples;

import com.mkyong.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerServiceFindOneTest {

    CustomerService customerService;

    @BeforeEach
    void createNewObjectForAll() {
        System.out.println("New CustomerService()");
        //customerService = new CustomerServiceJDBC();
    }

    @Test
    void findOne_with_id() {
        //customerService.findOneById(2L);
    }

    @Test
    void findOne_with_name() {
        //customerService.findOneByName(2L);
    }

    @Test
    void findOne_with_name_regex() {
        //customerService.findOneByNameRegex("%s");
    }

}
