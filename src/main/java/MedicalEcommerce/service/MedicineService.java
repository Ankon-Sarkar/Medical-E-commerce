package MedicalEcommerce.service;


import MedicalEcommerce.model.Medicine;
import MedicalEcommerce.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    private String UPLOADED_FOLDER="Medicine images";



    @Autowired
    MedicineRepository medicineRepository;

    public void saveProduct(Medicine medicine, MultipartFile imagefile) throws IOException {
        if(imagefile==null){
    MedicineRepository medicineRepository;
            medicine.setImage(null);
        }
        String fileName = StringUtils.cleanPath(imagefile.getOriginalFilename());

        try {
            medicine.setImage(Base64.getEncoder().encodeToString(imagefile.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        medicineRepository.save(medicine);
    }

    public List<Medicine> getSockInfo(String seller) {
        return medicineRepository.findStockBYSeller(seller);
    }

    public Medicine getMedById(int id) {
        Optional<Medicine> m = medicineRepository.findById(id);
        if (m.isPresent()) {
            return m.get();
        }
        return null;
    }

    public void update_Medinfo(Medicine medicine) {
        medicineRepository.save(medicine);
    }

    public void deleteMedicine(int id) {
        medicineRepository.deleteById(id);

    }



    public void updateStock(Medicine med, int ordered_unit) {
        med.setQuantity(med.getQuantity()-ordered_unit);
        medicineRepository.save(med);
    }

    public boolean CheckMedStock(Medicine med, int ordered_unit) {
        int stock=med.getQuantity();
        if (stock>=ordered_unit){
            return  true;
        }
        else
            return false;
    }

    public int findMedPrice(Medicine medicine) {
        return Integer.parseInt(medicine.getPrice());
    }
}

