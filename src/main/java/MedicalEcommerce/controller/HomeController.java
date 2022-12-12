package MedicalEcommerce.controller;

import MedicalEcommerce.model.Medicine;
import MedicalEcommerce.model.UserDtls;
import MedicalEcommerce.service.MedicineService;
import MedicalEcommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;


@Controller
public class HomeController {


    UserDtls user_prevInfo;
    @Autowired
    UserService userservice;
    @Autowired
    MedicineService medicineService;
    Principal principal;
    @Autowired
    private BCryptPasswordEncoder passwordEncode;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }


    //register user and assign role to users
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute UserDtls user, HttpSession session) {

        user.setPassword(passwordEncode.encode(user.getPassword()));

        userservice.set_user_role(user);

        //checking user existence, if an existing user has same email address he/she cannot register
        UserDtls u = userservice.checkUserExistence(user);

        if (u == null) {
            userservice.register(user);
            session.setAttribute("msg", "Successfully Register");
        } else {
            session.setAttribute("msg", "Try with another email address");
        }

        return "redirect:/signup";
    }


    //redirect to customer dashboard
    @GetMapping("/Customerwelcome")
    public String WelcomeCustomer(HttpServletRequest request, Model model) {
        principal = request.getUserPrincipal();
        model.addAttribute("username", principal.getName()+" [Customer]");
        model.addAttribute("role", "customer");
        return "UserDashBoard";
    }


    //redirect to seller dashboard
    @GetMapping("/Sellerwelcome")
    public String WelcomeSeller(HttpServletRequest request, Model model) {
        principal = request.getUserPrincipal();
        model.addAttribute("username", principal.getName()+" [Seller]");
        model.addAttribute("role", "seller");
        return "UserDashBoard";
    }


    //redirect to Admin dashboard
    @GetMapping("/Adminwelcome")
    public String WelcomeAdmin(HttpServletRequest request, Model model) {
        principal = request.getUserPrincipal();
        model.addAttribute("username", principal.getName()+" [Admin]");
        model.addAttribute("role", "admin");
        return "UserDashBoard";
    }


    @GetMapping("/DeliveryManWelcome")
    public String WelcomeDeliveryMan(HttpServletRequest request, Model model) {
        principal = request.getUserPrincipal();
        model.addAttribute("username", principal.getName()+" [DeliveryMan]");
        model.addAttribute("role", "deliveryman");
        return "UserDashBoard";
    }


    @GetMapping("/AllMedicine")
    public String CustomerView(Model model, HttpServletRequest request) {
        List<Medicine> details = medicineService.getAllMedicine();
        model.addAttribute("med", details);

        principal = request.getUserPrincipal();

        if (principal == null) {
            model.addAttribute("user", "anonymous");
        } else {
            model.addAttribute("user", "logged in");

        }
        return "all-medicine-list";
    }

    @GetMapping("/resetUserInfo")
    public String resetUserInfo(HttpServletRequest request, Model model) {
        Principal principal = request.getUserPrincipal();
        String user_name = principal.getName();
        user_prevInfo = userservice.getuserid(user_name);
        model.addAttribute("user", user_prevInfo);
        return "resetinfo";
    }

    @PostMapping("/reset")
    public String updateUser(@ModelAttribute UserDtls user_newInfo, HttpSession session) {
        UserDtls updated_info = userservice.edit_userinfo(user_prevInfo, user_newInfo);
        userservice.update_info(updated_info);
        session.setAttribute("msg", "User Data  has been Successfully Updated..");
        return "redirect:/resetUserInfo";
    }


}