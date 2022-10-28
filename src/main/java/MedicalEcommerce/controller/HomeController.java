package MedicalEcommerce.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import MedicalEcommerce.model.UserDtls;
import MedicalEcommerce.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepo;

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

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute UserDtls user, Model m, HttpSession session) {


        user.setPassword(passwordEncode.encode(user.getPassword()));

        if (user.getRole().equals("customer")) {
//            System.out.println(user.getRole());
            user.setRole("ROLE_CUSTOMER");
        }
        else if (user.getRole().equals("seller")) {
            user.setRole("ROLE_SELLER");

        }

        UserDtls u = userservice.checkUser(user);

        if (u == null) {
            userservice.register(user);
            session.setAttribute("msg", "Successfully Register");
        }
        else {
            session.setAttribute("msg", "Try with another email address");
        }

        return "redirect:/signup";
    }

    @GetMapping("/Customerwelcome")
    public String WelcomeCustomer(HttpServletRequest request, Model model){
        Principal principal = request.getUserPrincipal();
//        System.out.println(principal.getName());
        model.addAttribute("username",principal.getName() );
        return "Customerwelcome";
    }

    @GetMapping("/Sellerwelcome")
    public String WelcomeSeller(HttpServletRequest request, Model model){
        Principal principal = request.getUserPrincipal();
        model.addAttribute("username",principal.getName() );
        return "Sellerwelcome";
    }

    @GetMapping("/Adminwelcome")
    public String WelcomeAdmin(HttpServletRequest request, Model model){
        Principal principal = request.getUserPrincipal();
        model.addAttribute("username",principal.getName() );
        return "Adminwelcome";
    }



}