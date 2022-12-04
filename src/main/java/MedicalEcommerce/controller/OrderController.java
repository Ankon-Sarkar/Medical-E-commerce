package MedicalEcommerce.controller;


import MedicalEcommerce.model.Medicine;
import MedicalEcommerce.model.OrderDtls;
import MedicalEcommerce.model.UserDtls;
import MedicalEcommerce.repository.OrderDtlsRepository;
import MedicalEcommerce.service.MedicineService;
import MedicalEcommerce.service.OrderService;
import MedicalEcommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class OrderController {

    @Autowired
    MedicineService medicineService;

    @Autowired
    UserService userService;
    @Autowired
    OrderDtlsRepository orderDtlsRepository;

    @Autowired
    OrderService orderService;

    Medicine medicine;
    UserDtls seller;
    int med_price;
    UserDtls buyer;
    int ordered_unit;
    int total;
    String buyerName;
    String buyerAddress;
    String buyerPhnNo;

    int med_id;
//    String medicine_name;


    @GetMapping("/proccedToBuy/{id}")
    public String buyProcced(@PathVariable int id, Model model, @ModelAttribute OrderDtls orderDtls) {
         med_id = id;

        medicine = medicineService.getMedById(med_id);
//        medicine_name=medicine.getMedicine_name();
        seller= medicine.getSeller();
        med_price=Integer.parseInt(medicine.getPrice());
        med_price=medicineService.findMedPrice(medicine);
        return "buyForm";
    }

    @PostMapping("/buyNow")
    public String updateUser( @ModelAttribute OrderDtls orderDtls, HttpSession session, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        String customer_name=principal.getName();
        buyer=userService.getuserid(customer_name);
        ordered_unit=orderDtls.getQuantity();
        buyerName=orderDtls.getBuyer_name();
        buyerAddress=orderDtls.getBuyer_address();
        buyerPhnNo=orderDtls.getBuyer_phoneNo();
        total=ordered_unit*med_price;

        return  "PayHere";

    }

    @GetMapping("/paymentSuccess")
    public  String PaymentDone(@ModelAttribute OrderDtls orderDtls, Model model, HttpSession session){
        orderDtls.setBuyer(buyer);
        orderDtls.setProduct(medicine);
        orderDtls.setSellerID(seller);
        orderDtls.setIsDelivered("No");
        orderDtls.setPayment("done");
        orderDtls.setQuantity(ordered_unit);
        orderDtls.setBuyer_name(buyerName);
        orderDtls.setBuyer_address(buyerAddress);
        orderDtls.setBuyer_phoneNo(buyerPhnNo);
        orderDtls.setTotal_bill(total);


        boolean check=medicineService.CheckMedStock(medicine,ordered_unit);


        if (check) {
            medicineService.updateStock(medicine,ordered_unit);
            orderDtlsRepository.save(orderDtls);
            session.setAttribute("msg", "Order is Placed");

        }
        else {
            session.setAttribute("msg", "Something Wrong!");
        }

        return "redirect:/ViewAllMedicine";

    }

    public static String sellername(UserDtls seller ){
        return seller.getEmail();
    }

    public static String medname(Medicine p){
        return p.getMedicine_name();
    }
}
