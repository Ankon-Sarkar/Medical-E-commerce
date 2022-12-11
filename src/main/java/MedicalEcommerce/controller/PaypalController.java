package MedicalEcommerce.controller;

import MedicalEcommerce.model.PaymentDtls;
import MedicalEcommerce.repository.PaymentRepository;
import MedicalEcommerce.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaypalController {

    @Autowired
    PaypalService paypalService;

    @Autowired
    PaymentRepository paymentRepository;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    private String currency="USD";
    private String method="paypal";
    String intent="sale";

    private double price;
    private String description;

    @PostMapping("/pay")
    public String payment(@ModelAttribute PaymentDtls paymentDtls) {
        try {
            price=paymentDtls.getPrice();
            description=paymentDtls.getDescription();
            Payment payment = paypalService.createPayment(paymentDtls.getPrice(), currency, method,
                    intent, description, "http://localhost:8082/" + CANCEL_URL,
                    "http://localhost:8082/" + SUCCESS_URL);


            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();

                }
            }

        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return "Payment-failed";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "Payment-failed";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @ModelAttribute PaymentDtls paymentDtls) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                paymentDtls.setPayment_id(payment.getId());
                paymentDtls.setCurrency("USD");
                paymentDtls.setDescription(description);
                paymentDtls.setIntent("sale");
                paymentDtls.setMethod("paypal");
                paymentDtls.setPrice(price);
                paymentRepository.save(paymentDtls);
                return "redirect:/paymentSuccess";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "Payment-failed";
    }

}