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

    public boolean checkAlreadyAdded(UserDtls customer, Medicine med_id) {
        Cart is_added= cartRepository.checkCart(customer,med_id);
        if(is_added==null){
            return false;
        }
        return true;
    }

    public List<Cart> getmycartItems(UserDtls customer) {
        return  cartRepository.findmyCartItems(customer);
    }

    public void deletecartItem(UserDtls customer, Medicine id) {
        cartRepository.deleteCartItem(customer,id);
    }
}
