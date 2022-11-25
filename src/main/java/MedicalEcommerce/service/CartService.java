package MedicalEcommerce.service;


import MedicalEcommerce.model.Cart;
import MedicalEcommerce.model.Medicine;
import MedicalEcommerce.model.UserDtls;
import MedicalEcommerce.repository.CartRepository;
import MedicalEcommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {


    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;


    public void saveTocart(Cart cart) {
        cartRepository.save(cart);

    }

    public Cart getCart(UserDtls customer, Medicine id) {
        return cartRepository.getInCart(customer,id);

    }

    public List<Cart> getAllCartItem() {
        return cartRepository.findAll();
    }

    public List<Cart> getmycartItens(UserDtls customer) {
        return  cartRepository.findmyCartItems(customer);
    }

    public void deletecartItem(UserDtls customer, Medicine id) {
        cartRepository.deleteCartItem(customer,id);
    }
}
