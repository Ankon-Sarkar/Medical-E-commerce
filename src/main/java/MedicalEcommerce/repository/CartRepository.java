package MedicalEcommerce.repository;

import MedicalEcommerce.model.Cart;
import MedicalEcommerce.model.Medicine;
import MedicalEcommerce.model.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository <Cart, Integer> {

    @Query("Select c from Cart c where c.customer= :customer and c.medicine= :medicine")
    Cart checkCart(@Param("customer") UserDtls c, @Param("medicine") Medicine id);

    @Query("Select c from Cart c where c.customer=:customer")
    List<Cart> findmyCartItems(@Param("customer") UserDtls c);

    @Transactional
    @Modifying
    @Query("Delete from Cart c where c.customer=:customer and c.medicine=:medicine")
    void deleteCartItem(@Param("customer")UserDtls customer, @Param("medicine")Medicine id);
}
