package MedicalEcommerce.controller;


import MedicalEcommerce.model.Medicine;
import MedicalEcommerce.model.OrderDtls;
import MedicalEcommerce.model.UserDtls;
import MedicalEcommerce.service.CustomerService;
import MedicalEcommerce.service.MedicineService;
import MedicalEcommerce.service.OrderService;
import MedicalEcommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class CustomerController {



    @Autowired
    CustomerService customerService;
    @Autowired
    MedicineService medicineservice;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;


    @GetMapping("/ViewAllMedicine")
    public String CustomerView(Model model, HttpServletRequest request) {
        List<Medicine> details = medicineservice.getAllMedicine();
        model.addAttribute("med", details);
        return "ViewAllMedicine";
    }

    @GetMapping("/Customerside_ViewMedicineDetails/{id}")
    public String ViewMedDetails(@PathVariable int id, Model model,HttpServletRequest request) {
        Medicine medicine = medicineservice.getMedById(id);
        model.addAttribute("med", medicine);
        model.addAttribute("role","customer");

        Principal principal = request.getUserPrincipal();

        if (principal == null) {
            model.addAttribute("user", "anonymous");
        }

        return "ProductDtls";
    }

    @GetMapping("/viewMyOrders")
    public String MyOrders(Model model, HttpSession session, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        String customername = principal.getName();
        UserDtls customerid = userService.getuserid(customername);
        List<OrderDtls> details = orderService.getMyOrdersInfo(customerid);
        model.addAttribute("orderdetails", details);
        return "viewMyOrders";

    }
}
