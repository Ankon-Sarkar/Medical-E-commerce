package MedicalEcommerce.service;


import MedicalEcommerce.model.OrderDtls;
import MedicalEcommerce.model.UserDtls;
import MedicalEcommerce.repository.MedicineRepository;
import MedicalEcommerce.repository.OrderDtlsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    MedicineRepository medicineRepository;

    @Autowired
    OrderDtlsRepository orderDtlsRepository;


    public List<OrderDtls> getAlNewOrderInfo(UserDtls sellerId) {
        return orderDtlsRepository.findAllnewOrderinfo(sellerId);

    }

    public void markDelivered(int id){
        Optional<OrderDtls> order=orderDtlsRepository.findById(id);
        if (order.isPresent()) {
            OrderDtls product=order.get();
            product.setIsDelivered("Yes");
            orderDtlsRepository.save(product);
        }
    }

    public List<OrderDtls> getPreviousSalesTec(UserDtls sellerid) {
        return orderDtlsRepository.findPreviousSalesRec(sellerid);
    }

    public List<OrderDtls> getMyOrdersInfo(UserDtls customerid) {
        return orderDtlsRepository.findMyordersinfo(customerid);
    }
}
