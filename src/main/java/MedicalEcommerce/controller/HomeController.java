package MedicalEcommerce.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import MedicalEcommerce.model.UserDtls;
import MedicalEcommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;


@Controller
public class HomeController {


    @Autowired
    private BCryptPasswordEncoder passwordEncode;

    @Autowired
    private UserService userservice;


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
    public String saveUser(@ModelAttribute UserDtls user, Model m, HttpSession session) {

        user.setPassword(passwordEncode.encode(user.getPassword()));

        userservice.set_user_role(user);

        //checking user existence, if a existing user has same email address he/she cannot register
        UserDtls u = userservice.checkUser(user);

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
        Principal principal = request.getUserPrincipal();
//        System.out.println(principal.getName());
        model.addAttribute("username", principal.getName());
        return "Customerwelcome";
    }


    //redirect to seller dashboard
    @GetMapping("/Sellerwelcome")
    public String WelcomeSeller(HttpServletRequest request, Model model) {
        Principal principal = request.getUserPrincipal();
        model.addAttribute("username", principal.getName());
        return "Sellerwelcome";
    }


    //redirect to Admin dashboard
    @GetMapping("/Adminwelcome")
    public String WelcomeAdmin(HttpServletRequest request, Model model) {
        Principal principal = request.getUserPrincipal();
        model.addAttribute("username", principal.getName());
        return "Adminwelcome";
    }


}