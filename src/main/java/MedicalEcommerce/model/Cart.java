package MedicalEcommerce.model;

import javax.persistence.*;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cart_id;

    @ManyToOne
    private UserDtls customer;

    public Cart() {
    }

    public Cart(UserDtls customer, Medicine medicine) {
        this.customer = customer;
        this.medicine = medicine;
    }

    @ManyToOne
    private Medicine medicine;

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public UserDtls getCustomer() {
        return customer;
    }

    public void setCustomer(UserDtls customer) {
        this.customer = customer;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }
}
