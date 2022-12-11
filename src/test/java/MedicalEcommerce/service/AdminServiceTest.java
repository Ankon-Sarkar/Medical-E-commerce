package MedicalEcommerce.service;

import MedicalEcommerce.model.UserDtls;
import MedicalEcommerce.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AdminServiceTest {

    @Autowired
    AdminService adminService;
    @MockBean
    UserRepository userRepository;


    @Test
    void getCustomersInfo() {
        List<UserDtls> customer_list=new ArrayList<>();
        customer_list.add(new UserDtls("customer", "customer@gmail.com", "test", "ROLE_CUSTOMER"));

        when(userRepository.findAllCustomer()).thenReturn(customer_list);
        List<UserDtls> customer=adminService.getCustomersInfo();
        assertThat(customer.size()>0);
    }

    @Test
    void getSellersInfo() {
        List<UserDtls> seller_list=new ArrayList<>();
        seller_list.add(new UserDtls("seller", "seller@gmail.com", "test", "ROLE_SELLER"));

        when(userRepository.findAllSeller()).thenReturn(seller_list);
        List<UserDtls> seller=adminService.getCustomersInfo();
        assertThat(seller.size()>0);
    }

    @Test
    void getUserById() {
        UserDtls user=new UserDtls("customer", "customer@gmail.com", "test", "ROLE_CUSTOMER");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        assertNotNull(adminService.getUserById(user.getId()));
    }

    @Test
    void deleteUser() {
        UserDtls user=new UserDtls("customer", "customer@gmail.com", "test", "ROLE_CUSTOMER");
        adminService.deleteUser(user.getId());
        verify(userRepository).deleteById(user.getId());
    }
}