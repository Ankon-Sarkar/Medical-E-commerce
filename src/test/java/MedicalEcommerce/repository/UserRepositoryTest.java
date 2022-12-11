package MedicalEcommerce.repository;

import MedicalEcommerce.model.UserDtls;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {


    @Autowired
    UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;
    UserDtls customer;
    UserDtls seller;

    @BeforeEach
    public void setUp() {
        customer = new UserDtls("customer", "customer@gmail.com", "test", "ROLE_CUSTOMER");
        seller = new UserDtls("seller", "seller@gmail.com", "test", "ROLE_SELLER");
        entityManager.persist(customer);
        entityManager.persist(seller);
    }

    @Test
    void findByEmail() {
        assertThat(userRepository.findByEmail("customer@gmail.com")).isNotNull();
    }

    @Test
    void deleteById() {
        userRepository.deleteById(customer.getId());
        assertThat(userRepository.findByEmail("customer@gmail.com")).isNull();
    }

    @Test
    void findAllCustomer() {
        List<UserDtls> customer_details = userRepository.findAllCustomer();
        assertThat(customer_details.size() > 0);
    }

    @Test
    void findAllSeller() {
        List<UserDtls> seller_details = userRepository.findAllCustomer();
        assertThat(seller_details.size() > 0);
    }

    //Based on email provides userId
    @Test
    void getuserid() {
        int expected_id = customer.getId();
        int actual_id = userRepository.getuserid(customer.getEmail()).getId();
        assertEquals(expected_id, actual_id);
    }


}