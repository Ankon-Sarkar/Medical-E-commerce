package MedicalEcommerce.controller;


import MedicalEcommerce.model.Medicine;
import MedicalEcommerce.model.UserDtls;
import MedicalEcommerce.service.MedicineService;
import MedicalEcommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Base64;
import java.util.List;

@Controller
public class MedicineController {

    @Autowired
    MedicineService medicineservice;
    @Autowired
    UserService userService;

    int med_id;

    @GetMapping("/addMedicine")
    public String addMedicinepage(){
        return "addMedicine";

    }

    @PostMapping("/addMedicine")
    public String saveProduct(@ModelAttribute Medicine medicine, Model model, HttpSession session, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

            Principal principal = request.getUserPrincipal();
            String sellername=principal.getName();
            medicine.setSeller(sellername);
            UserDtls sellerid=userService.getuserid(sellername);
            medicine.setUser(sellerid);
            medicineservice.saveProduct(medicine, file);
            session.setAttribute("msg", "Product Added");
            return "addMedicine";
        }

    @GetMapping("/ViewSellerStock")
    public String CustomerView(Model model,HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        String sellername=principal.getName();
        List<Medicine> details = medicineservice.getSockInfo(sellername);
        model.addAttribute("med", details);
        return "ViewStock";
    }

    @GetMapping("/editMedicine/{id}")
    public String edit(@PathVariable int id, Model m) {
         med_id=id;
        Medicine medicine = medicineservice.getMedById(id);
        m.addAttribute("med", medicine);
        return "EditMedicineInfo";
    }

    @PostMapping("/updateMedicine")
    public String updateUser(@ModelAttribute Medicine medicine, HttpSession session, MultipartFile imagefile) {
        Medicine med=medicineservice.getMedById(med_id);

        med.setMedicine_name(medicine.getMedicine_name());
        med.setMedicine_composition(medicine.getMedicine_composition());
        med.setManufacturing_company(medicine.getManufacturing_company());
        med.setPrice(medicine.getPrice());
        med.setQuantity(medicine.getQuantity());
        med.setAbout(medicine.getAbout());

        medicineservice.update_Medinfo(med);
        session.setAttribute("msg", "Medicine Data  has been Successfully Updated..");
        return "redirect:/editMedicine/"+med.getMedicine_id();
    }

    @GetMapping("/deleteMedicine/{id}")
    public String deleteUser(@PathVariable int id, HttpSession session) {
        medicineservice.deleteMedicine(id);
        session.setAttribute("msg", "Medicine has been Successfully Deleted..");
        return "redirect:/ViewSellerStock";
    }

    @GetMapping("/ViewMedicineDetails/{id}")
    public String ViewMedDetails(@PathVariable int id, Model m) {
        med_id=id;
        Medicine medicine = medicineservice.getMedById(id);
        m.addAttribute("med", medicine);
        return "SellersideProductDetails";
    }



        }



