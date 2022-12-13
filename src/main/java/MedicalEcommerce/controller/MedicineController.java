package MedicalEcommerce.controller;


import MedicalEcommerce.model.Medicine;
import MedicalEcommerce.model.UserDtls;
import MedicalEcommerce.service.MedicineService;
import MedicalEcommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class MedicineController {

    @Autowired
    MedicineService medicineservice;
    @Autowired
    UserService userService;

    int med_id;

    @GetMapping("/addMedicine")
    public String addMedicinepage() {
        return "addMedicine";
    }

    @PostMapping("/saveMedicine")
    public String saveProduct(@ModelAttribute Medicine medicine, Model model, HttpSession session, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        Principal principal = request.getUserPrincipal();
        String seller_mail = principal.getName();
        medicine.setSeller_email(seller_mail);
        UserDtls sellerid = userService.getuserid(seller_mail);
        medicine.setSeller(sellerid);
        medicineservice.saveProduct(medicine, file);
        session.setAttribute("msg", "Product Added");
        return "addMedicine";
    }

    @GetMapping("/ViewSellerStock")
    public String CustomerView(Model model, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        String sellername = principal.getName();
        List<Medicine> details = medicineservice.getSockInfo(sellername);
        model.addAttribute("med", details);
        return "ViewStock";
    }

    @GetMapping("/editMedicine/{id}")
    public String edit(@PathVariable int id, Model m) {
        med_id = id;
        Medicine medicine = medicineservice.getMedById(id);
        m.addAttribute("med", medicine);
        return "EditMedicineInfo";
    }

    @PostMapping("/updateMedicine")
    public String updateMed(@ModelAttribute Medicine medicine, HttpSession session, MultipartFile imagefile) {
        Medicine med = medicineservice.getMedById(med_id);
        Medicine upadated_info = medicineservice.set_new_info(med, medicine);
        medicineservice.update_Medinfo(upadated_info);
        session.setAttribute("msg", "Medicine Data  has been Successfully Updated..");
        return "redirect:/editMedicine/" + med.getMedicine_id();
    }

    @GetMapping("/deleteMedicine/{id}")
    public String deleteUser(@PathVariable int id, HttpSession session) {
        medicineservice.deleteMedicine(id);
        session.setAttribute("msg", "Medicine has been Successfully Deleted..");
        return "redirect:/ViewSellerStock";
    }

    @GetMapping("/search")
    public String search(Model model, @Param("keyword") String keyword) {
        if (keyword != null) {
            List<Medicine> med = medicineservice.getByKeyword(keyword);
            model.addAttribute("med", med);
        }
        return "all-medicine-list";
    }


}



