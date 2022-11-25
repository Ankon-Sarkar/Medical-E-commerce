package MedicalEcommerce.service;


import MedicalEcommerce.model.UserDtls;
import MedicalEcommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    UserRepository userRepo;

    public List<UserDtls> getCustomersInfo() {
        return userRepo.findAllCustomer();
    }

    public List<UserDtls> getSellersInfo() {
        return userRepo.findAllSeller();
    }

    public UserDtls getUserById(int id) {
        Optional<UserDtls> u = userRepo.findById(id);
        if (u.isPresent()) {
            return u.get();
        }
        return null;
    }


    public void deleteUser(int id) {
        userRepo.deleteById(id);
    }
}
