package MedicalEcommerce.service;

import MedicalEcommerce.model.UserDtls;
import MedicalEcommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncode;

    //checking user existence
    public UserDtls checkUser(UserDtls user) {
        String email = user.getEmail();
//        System.out.println(email);
        UserDtls userExists = userRepo.findByEmail(email);
//        System.out.println(userExists);
        if (userExists != null) {
            return userExists;
        }

        return null;

    }

    public void register(UserDtls user) {
        userRepo.save(user);
    }

    public UserDtls getuserid(String sellername) {
        return userRepo.getuserid(sellername);
    }

    public void set_user_role(UserDtls user) {
        if (user.getRole().equals("customer")) {
//            System.out.println(user.getRole());
            user.setRole("ROLE_CUSTOMER");
        } else if (user.getRole().equals("seller")) {
            user.setRole("ROLE_SELLER");

        }
    }

    public UserDtls edit_userinfo(UserDtls previousInfo, UserDtls newInfo) {
        previousInfo.setName(newInfo.getName());
        previousInfo.setPassword(passwordEncode.encode(newInfo.getPassword()));
        return previousInfo;
    }

    public void update_info(UserDtls u) {
        userRepo.save(u);
    }
}
