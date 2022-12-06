package MedicalEcommerce.controller;


import MedicalEcommerce.model.OrderDtls;
import MedicalEcommerce.model.UserDtls;
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
public class DeliverymanController {


    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @GetMapping("/view-pending-deliveryItems")
    public String viewPendingDelivery(Model model, HttpSession session, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String Deliveryman_name=principal.getName();
        UserDtls deliveryman_id=userService.getuserid(Deliveryman_name);
        List<OrderDtls> details = orderService.getPendingDelivery(deliveryman_id.getId());
        model.addAttribute("orderdetails", details);
        return "View-pending-delivery";
    }


    @GetMapping("/markAsDelivered/{id}")
    public String marksDelivered(@PathVariable int id, Model model, HttpSession session){
        orderService.markDelivered(id);
        session.setAttribute("msg", "Product Marked as Delivered");
        return "redirect:/view-pending-deliveryItems";
    }


    @GetMapping("/view-deliveredItems")
    public String vieDeliveredItems(Model model, HttpSession session, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String Deliveryman_name=principal.getName();
        UserDtls deliveryman_id=userService.getuserid(Deliveryman_name);
        List<OrderDtls> details = orderService.getDeliveredItems(deliveryman_id.getId());
        model.addAttribute("orderdetails", details);
        return "view-delivered-items";
    }
}
