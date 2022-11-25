package MedicalEcommerce.repository;

import MedicalEcommerce.model.UserDtls;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    int user_id;


    @Autowired
    UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;


    @Test
    void findByEmail() {
        UserDtls test_user = new UserDtls("testuser", "test@gmail.com", "test", "Role_CUSTOMER");
        entityManager.persist(test_user);
        assertThat(userRepository.findByEmail("test@gmail.com")).isNotNull();

    }

    @Test
    void deleteById() {
        UserDtls test_user = new UserDtls("testuser", "test@gmail.com", "test", "Role_CUSTOMER");
        entityManager.persist(test_user);
        userRepository.deleteById(test_user.getId());
        assertThat(userRepository.findByEmail("test@gmail.com")).isNull();
    }


    @Test
    void findAllCustomer() {
        UserDtls test_user = new UserDtls("testuser", "test@gmail.com", "test", "ROLE_CUSTOMER");
        List<UserDtls> customer_details = userRepository.findAllCustomer();
        assertThat(customer_details.size() > 0);
    }

    @Test
    void findAllSeller() {
        UserDtls test_user = new UserDtls("testuser", "test@gmail.com", "test", "ROLE_SELLER");
        List<UserDtls> seller_details = userRepository.findAllCustomer();
        assertThat(seller_details.size() > 0);

    }

    //Based on email provides userId
    @Test
    void getuserid() {
        UserDtls test_user = new UserDtls("testuser", "test@gmail.com", "test", "Role_CUSTOMER");
        entityManager.persist(test_user);
        int expected_id = test_user.getId();
        int actual_id = userRepository.getuserid(test_user.getEmail()).getId();
        assertEquals(expected_id, actual_id);
    }


}