package MedicalEcommerce.service;

import MedicalEcommerce.model.Cart;
import MedicalEcommerce.model.Medicine;
import MedicalEcommerce.model.UserDtls;
import MedicalEcommerce.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CartServiceTest {

    @Autowired
    CartService cartService;
    @MockBean
    CartRepository cartRepository;


    Medicine medicine;
    UserDtls customer;
    Cart cart;


    @BeforeEach
    public void setUp() {
        medicine = new Medicine();
        customer = new UserDtls();
        cart = new Cart(customer, medicine);
    }

    @Test
    void checkAlreadyAdded() {
        when(cartRepository.checkCart(customer,medicine)).thenReturn(cart);
        boolean is_available= cartService.checkAlreadyAdded(customer,medicine);
        assertTrue(is_available);
    }


    @Test
    void getmycartItems() {
        List<Cart>cart_items=new ArrayList<>();
        cart_items.add(cart);
        when(cartRepository.findmyCartItems(customer)).thenReturn(cart_items);
        assertThat(cartService.getmycartItems(customer).size()>0);
    }


    @Test
    void deletecartItem() {
        cartService.deletecartItem(customer,medicine);
        verify(cartRepository).deleteCartItem(customer,medicine);
    }
}