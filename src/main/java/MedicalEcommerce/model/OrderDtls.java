package MedicalEcommerce.model;

import javax.persistence.*;

@Entity
public class OrderDtls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_id;

    private String buyer_name;
    private String buyer_address;
    private String buyer_phoneNo;
    private String payment;
    private String delivery_status;

    private  int quantity;

    private int total_bill;
    private int deliveryman_id;


    @ManyToOne
    private UserDtls buyer;

    @ManyToOne
    private Medicine product;

    @ManyToOne
    private UserDtls sellerID;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getBuyer_name() {
        return buyer_name;
    }

    public void setBuyer_name(String buyer_name) {
        this.buyer_name = buyer_name;
    }

    public String getBuyer_address() {
        return buyer_address;
    }

    public void setBuyer_address(String buyer_address) {
        this.buyer_address = buyer_address;
    }

    public String getBuyer_phoneNo() {
        return buyer_phoneNo;
    }

    public void setBuyer_phoneNo(String buyer_phoneNo) {
        this.buyer_phoneNo = buyer_phoneNo;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal_bill() {
        return total_bill;
    }

    public void setTotal_bill(int total_bill) {
        this.total_bill = total_bill;
    }

    public int getDeliveryman_id() {
        return deliveryman_id;
    }

    public void setDeliveryman_id(int deliveryman_id) {
        this.deliveryman_id = deliveryman_id;
    }

    public UserDtls getBuyer() {
        return buyer;
    }

    public void setBuyer(UserDtls buyer) {
        this.buyer = buyer;
    }

    public Medicine getProduct() {
        return product;
    }

    public void setProduct(Medicine product) {
        this.product = product;
    }

    public UserDtls getSellerID() {
        return sellerID;
    }

    public void setSellerID(UserDtls sellerID) {
        this.sellerID = sellerID;
    }
}
