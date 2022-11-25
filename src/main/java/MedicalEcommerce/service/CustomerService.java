package MedicalEcommerce.service;


import MedicalEcommerce.model.Medicine;
import MedicalEcommerce.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    MedicineRepository medicineRepository;
    public List<Medicine> getAllMedicine() {
        return medicineRepository.findAll();
    }
}
