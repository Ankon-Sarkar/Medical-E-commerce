package MedicalEcommerce.repository;

import MedicalEcommerce.model.Cart;
import MedicalEcommerce.model.Medicine;
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
class CartRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CartRepository cartRepository;

    UserDtls customer;
    Medicine med;
    Cart cart;


    @BeforeEach
    void setUp(){
        customer=new UserDtls();
        med=new Medicine();
        cart=new Cart(customer,med);
        entityManager.persist(customer);
        entityManager.persist(med);
        entityManager.persist(cart);
    }


    @Test
    void checkCart() {
        int Actual_cart_id  =cartRepository.checkCart(cart.getCustomer(),cart.getMedicine()).getCart_id();
        int expected_cart_id= cart.getCart_id();
        assertEquals(Actual_cart_id,expected_cart_id);
    }


    @Test
    void findmyCartItems() {
        List<Cart> cart_items=cartRepository.findmyCartItems((cart.getCustomer()));
        assertThat(cart_items.size() > 0);
    }

    @Test
    void deleteCartItem() {
        cartRepository.deleteCartItem(customer,med);
        List<Cart> cart_items=cartRepository.findmyCartItems(cart.getCustomer());
        assertEquals(cart_items.size(),0);
    }
}