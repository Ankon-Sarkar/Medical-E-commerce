package MedicalEcommerce.service;


import MedicalEcommerce.model.OrderDtls;
import MedicalEcommerce.model.UserDtls;
import MedicalEcommerce.repository.MedicineRepository;
import MedicalEcommerce.repository.OrderDtlsRepository;
import MedicalEcommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OrderService {

    @Autowired
    MedicineRepository medicineRepository;

    @Autowired
    OrderDtlsRepository orderDtlsRepository;

    @Autowired
    UserRepository userRepository;


    public List<OrderDtls> getAlNewOrderInfo(UserDtls sellerId) {
        return orderDtlsRepository.findAllnewOrderinfo(sellerId);
    }

    public void shipped(int id){
        Optional<OrderDtls> order=orderDtlsRepository.findById(id);
        if (order.isPresent()) {
            OrderDtls product=order.get();
            product.setDelivery_status("Shipped");
            assignDeliveryMan(product);
            orderDtlsRepository.save(product);
        }
    }

    public List<OrderDtls> getPreviousSalesRec(UserDtls sellerid) {
        return orderDtlsRepository.findPreviousSalesRec(sellerid);
    }

    public List<OrderDtls> getMyOrdersInfo(UserDtls customerid) {
        return orderDtlsRepository.findMyordersinfo(customerid);
    }

    public void assignDeliveryMan(OrderDtls product){
        List<UserDtls> deliveryman_list=userRepository.findAllDeliveryMan();
        Random rand = new Random();
        int randomIndex = rand.nextInt(deliveryman_list.size());
        UserDtls deliveryman=deliveryman_list.get(randomIndex);
        System.out.println(deliveryman.getId());
        product.setDeliveryman_id(deliveryman.getId());
    }

    public List<OrderDtls> getPendingDelivery(int deliveryman_id) {
        return orderDtlsRepository.findPendingDelivery(deliveryman_id);
    }


    public void markDelivered(int id){
        Optional<OrderDtls> order=orderDtlsRepository.findById(id);
        if (order.isPresent()) {
            OrderDtls product=order.get();
            product.setDelivery_status("Delivered");
            orderDtlsRepository.save(product);
        }
    }

    public List<OrderDtls> getDeliveredItems(int id) {
        return orderDtlsRepository.findDeliveredItems(id);
    }
}

