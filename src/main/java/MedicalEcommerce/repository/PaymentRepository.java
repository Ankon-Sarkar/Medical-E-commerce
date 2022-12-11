package MedicalEcommerce.repository;

import MedicalEcommerce.model.PaymentDtls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentDtls, Integer> {

}
