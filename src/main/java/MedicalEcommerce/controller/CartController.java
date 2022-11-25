package MedicalEcommerce.controller;


import MedicalEcommerce.model.Cart;
import MedicalEcommerce.model.Medicine;
import MedicalEcommerce.model.UserDtls;
import MedicalEcommerce.service.CartService;
import MedicalEcommerce.service.MedicineService;
import MedicalEcommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {
    int med_id;


    @Autowired
    MedicineService medicineservice;

    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;


    @GetMapping("/addcart/{id}")
    public String addToCart(@ModelAttribute Cart cart, @PathVariable int id, HttpSession session, HttpServletRequest request) {
        med_id=id;
        Medicine medicine = medicineservice.getMedById(id);
        Principal principal = request.getUserPrincipal();
        String customer_name=principal.getName();
        UserDtls customer=userService.getuserid(customer_name);
        int customer_id = customer.getId();


        System.out.println(cartService.getCart(customer,medicine));

        if(medicine.getQuantity()<=0){
            session.setAttribute("msg", "Not Possible to Add in Cart");
        }


       if(cartService.getCart(customer,medicine)==null) {
            cart.setCustomer(customer);
            cart.setMedicine(medicine);
            cartService.saveTocart(cart);
            session.setAttribute("msg", "Added to Cart");

        }
       else {
           session.setAttribute("msg", "Duplicate");
       }

        return "redirect:/ViewAllMedicine";
    }

    @GetMapping("/viewmycart")
    public String viewCart(@ModelAttribute Cart cart, Model model, HttpSession session, HttpServletRequest request){
        Principal principal = request.getUserPrincipal();
        String customer_name=principal.getName();
        UserDtls customer=userService.getuserid(customer_name);
        List<Cart> cartitems=cartService.getmycartItens(customer);
        List<Medicine> med=new ArrayList<>() ;

        for (int i=0;i<cartitems.size();i++){
            Cart item=cartitems.get(i);
            Medicine cart_Med=item.getMedicine();
            med.add(cart_Med);
        }
//        int a=med.get(0).getMedicine_id();
        model.addAttribute("med",med );

        return "ViewCartItems";
    }


    @GetMapping("/deleteCartItem/{id}")
    public String removefromcart(@PathVariable int  id, HttpSession session,HttpServletRequest request) {
        Medicine medicine = medicineservice.getMedById(id);
        Principal principal = request.getUserPrincipal();
        String customer_name=principal.getName();
        UserDtls customer=userService.getuserid(customer_name);
        cartService.deletecartItem(customer,medicine);
        session.setAttribute("msg", "Successfully Deleted from cart..");
        return "redirect:/viewmycart";
    }

}
