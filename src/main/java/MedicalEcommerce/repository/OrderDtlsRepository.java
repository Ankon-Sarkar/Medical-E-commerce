package MedicalEcommerce.repository;


import MedicalEcommerce.model.OrderDtls;
import MedicalEcommerce.model.UserDtls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDtlsRepository extends JpaRepository<OrderDtls, Integer> {
    @Query("Select o from OrderDtls o where o.sellerID=:sellerID and o.isDelivered='No'")
    List<OrderDtls> findAllnewOrderinfo(@Param("sellerID") UserDtls s);

    @Query("Select o from OrderDtls o where o.sellerID=:sellerID and o.isDelivered='Yes'")
    List<OrderDtls> findPreviousSalesRec(@Param("sellerID") UserDtls s);

    @Query("Select o from OrderDtls o where o.buyer=:buyer")
    List<OrderDtls> findMyordersinfo(@Param("buyer") UserDtls s);
}
