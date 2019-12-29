package com.mkyong.nested;

import com.mkyong.customer.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Test Customer Service")
public class CustomerServiceNestedTest {

    CustomerService customerService;

    // Create one customerService object and reuse for all the nested tests
    @Test
    @DisplayName("new CustomerService() for all the nested methods.")
    void createNewObjectForAll() {
        System.out.println("New CustomerService()");
        //customerService = new CustomerServiceJDBC();
    }

    @Nested
    @DisplayName("findOne methods")
    class FindOne {
        @Test
        void findOne_with_id() {
            //customerService.findOneById(2L);
        }

        @Test
        void findWith_with_name() {
            //customerService.findOneByName(2L);
        }

        @Test
        void findWith_with_name_regex() {
            //customerService.findOneByNameRegex("%s");
        }
    }

    @Nested
    @DisplayName("findAll methods")
    class FindAll {
        @Test
        void findAll_with_ids() {
            //customerService.findAllByIds(Arrays.asList(2, 3, 4));
        }

        @Test
        void findAll_with_name_likeY() {
            //customerService.findAllByName("mkyong");
        }
    }

    @Nested
    @DisplayName("update methods")
    class Update {
        @Test
        void update_with_new() {
            //customerService.update(new Customer());
        }

        @Test
        void update_with_existing() {
            //customerService.update(new Customer());
        }
    }

}
