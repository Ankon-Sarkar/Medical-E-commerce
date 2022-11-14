package MedicalEcommerce.repository;


import MedicalEcommerce.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {

    @Query("Select m from Medicine m where seller =?1")
    List<Medicine> findStockBYSeller(String seller);


    public Optional<Medicine> findById(int id);


}
