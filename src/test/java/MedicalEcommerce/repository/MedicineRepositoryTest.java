package MedicalEcommerce.repository;

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
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MedicineRepositoryTest {
    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private TestEntityManager entityManager;

    Medicine med;

    @BeforeEach
    void setup(){
        med=new Medicine("test_med","test_seller@gmail.com");
        entityManager.persist(med);

    }

    @Test
    void findStockBYSeller() {
        List<Medicine> med_details=medicineRepository.findStockBYSeller(med.getSeller_email());
        assertThat(med_details.size() > 0);


    }

    @Test
    void findById() {
       assertThat(medicineRepository.findById(med.getMedicine_id())).isNotNull();
    }

//    @Test
//    void findByKeyword(){
//        assertThat()
//    }
}