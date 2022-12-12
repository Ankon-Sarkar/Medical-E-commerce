package MedicalEcommerce.controller;


import MedicalEcommerce.model.Medicine;
import MedicalEcommerce.model.UserDtls;
import MedicalEcommerce.service.AdminService;
import MedicalEcommerce.service.MedicineService;
import MedicalEcommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    UserService userservice;
    @Autowired
    AdminService adminService;

    @Autowired
    MedicineService medicineService;

    @Autowired
    BCryptPasswordEncoder passwordEncode;
    UserDtls u;


    @GetMapping("/viewAllCustomer")
    public String viewCustomer(Model model) {
        List<UserDtls> details = adminService.getCustomersInfo();
        model.addAttribute("u", details);
        model.addAttribute("m", "Customer info");
        return "viewusersinfo";
    }

    @GetMapping("/viewAllSeller")
    public String viewSeller(Model model) {
        List<UserDtls> details = adminService.getSellersInfo();
        model.addAttribute("u", details);
        model.addAttribute("m", "Seller info");
        return "viewusersinfo";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model m) {
        u = adminService.getUserById(id);
        m.addAttribute("user", u);
        return "EditUserinfo";
    }


    @PostMapping("/update")
    public String updateUser(@ModelAttribute UserDtls user, HttpSession session) {
        UserDtls updated_info = userservice.edit_userinfo(u, user);
        userservice.update_info(updated_info);
        session.setAttribute("msg", "User Data  has been Successfully Updated..");
        return "redirect:/edit/" + u.getId();
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id, HttpSession session) {
        adminService.deleteUser(id);
        session.setAttribute("msg", "User has been Successfully Deleted..");
        return "redirect:/Adminwelcome";
    }


    @GetMapping("/admin_product_view")
    public String admin_product_view(Model model,HttpSession session){
        List<Medicine> details = medicineService.getAllMedicine();
        model.addAttribute("med", details);
        model.addAttribute("role","admin");
        return "all-medicine-list";

    }

    @GetMapping("/admin_side_product_details/{id}")
    public String admin_product_details(@PathVariable int id, Model model) {
        Medicine medicine = medicineService.getMedById(id);
        model.addAttribute("med", medicine);
        model.addAttribute("role","admin");
        return "ProductDtls";
    }


    @GetMapping("/remove_sell_post/{id}")
    public String remove_sell_post(@PathVariable int id, HttpSession session) {
        medicineService.deleteMedicine(id);
        session.setAttribute("msg", "Medicine has been Successfully Deleted..");
        return "redirect:/ViewAllMedicine";
    }


    @GetMapping("/addDeliveryMan")
    public String addDeliveryMan() {
        return "add-delivery-man";

    }

    @PostMapping("/saveDeliveryMan")
    public String saveDeliveryMan(@ModelAttribute UserDtls user, HttpSession session){
        user.setPassword(passwordEncode.encode(user.getPassword()));
        user.setRole("ROLE_DELIVERYMAN");
        //checking user existence, if an existing user has same email address he/she cannot register
        UserDtls u = userservice.checkUserExistence(user);

        if (u == null) {
            userservice.register(user);
            session.setAttribute("msg", "Successfully Register");
        } else {
            session.setAttribute("msg", "This email already exists");
        }
        return "redirect:/addDeliveryMan";

    }

}
