package MedicalEcommerce.model;

import javax.persistence.*;
import java.util.List;


@Entity
public class UserDtls {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "full_name")
    private String name;
    private String email;
    private String password;

    private String role;

    @OneToMany(mappedBy="seller", cascade = CascadeType.REMOVE)
    private List<Medicine> medicines;

    @OneToMany(mappedBy="buyer", cascade = CascadeType.REMOVE)
    private List<OrderDtls> orderDtls;

    @OneToMany(mappedBy="customer", cascade = CascadeType.REMOVE)
    private List<Cart> cart;

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    @OneToMany(mappedBy="sellerID", cascade = CascadeType.REMOVE)
    private List<OrderDtls> orderitem;

    public UserDtls() {
    }

    public UserDtls( String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    public List<OrderDtls> getOrderDtls() {
        return orderDtls;
    }

    public void setOrderDtls(List<OrderDtls> orderDtls) {
        this.orderDtls = orderDtls;
    }

    public List<OrderDtls> getOrderitem() {
        return orderitem;
    }

    public void setOrderitem(List<OrderDtls> orderitem) {
        this.orderitem = orderitem;
    }
}
