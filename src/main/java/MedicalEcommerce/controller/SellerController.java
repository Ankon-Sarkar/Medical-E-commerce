package MedicalEcommerce.controller;



import MedicalEcommerce.model.Medicine;
import MedicalEcommerce.model.OrderDtls;
import MedicalEcommerce.model.UserDtls;
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
public class SellerController {

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    MedicineService medicineService;

    @GetMapping("/viewNewOrderReq")
    public String viewOrderReq(Model model, HttpSession session, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String sellername=principal.getName();
        UserDtls sellerid=userService.getuserid(sellername);
        List<OrderDtls> details = orderService.getAlNewOrderInfo(sellerid);
        model.addAttribute("orderdetails", details);
        return "viewNewOrderDetails";

    }

    @GetMapping("/markAsShipped/{id}")
    public String markAsShipped(@PathVariable int id, HttpSession session){
        orderService.shipped(id);
        session.setAttribute("msg", "Product is shipped");
        return "redirect:/viewNewOrderReq";
    }

    @GetMapping("/viewPreviousSalesRecord")
    public String previousSalesRecord(Model model, HttpSession session, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String sellername=principal.getName();
        UserDtls sellerid=userService.getuserid(sellername);
        List<OrderDtls> details = orderService.getPreviousSalesRec(sellerid);
        model.addAttribute("orderdetails", details);
        return "viewPreviousSalesRecord";
    }

    @GetMapping("/ViewMedicineDetails/{id}")
    public String ViewMedDetails(@PathVariable int id, Model model) {

        Medicine medicine = medicineService.getMedById(id);
        model.addAttribute("med", medicine);
        model.addAttribute("role","seller");
        return "ProductDtls";
    }

}
