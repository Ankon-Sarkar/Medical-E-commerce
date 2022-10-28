package MedicalEcommerce.service;

import MedicalEcommerce.model.UserDtls;
import MedicalEcommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    public UserDtls checkUser(UserDtls user){
        String email=user.getEmail();
//        System.out.println(email);
        UserDtls userExists=userRepo.findByEmail(email);
//        System.out.println(userExists);
        if (userExists!=null){
            return userExists;
        }

        return null;

    }

    public void register(UserDtls user){
        userRepo.save(user);
    }

    public UserDtls getuserid(String sellername) {
        return userRepo.getuserid(sellername);
    }
}
