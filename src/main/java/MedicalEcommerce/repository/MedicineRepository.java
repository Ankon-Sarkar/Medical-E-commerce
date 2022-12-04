package MedicalEcommerce.repository;


import MedicalEcommerce.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {

    @Query("Select m from Medicine m where seller_email =?1")
    List<Medicine> findStockBYSeller(String seller);


    public Optional<Medicine> findById(int id);

    @Query("Select m from Medicine m where manufacturing_company like %:keyword% or medicine_composition like %:keyword% " +
            "or medicine_name like %:keyword%")
    List<Medicine> findByKeyword(String keyword);
}
