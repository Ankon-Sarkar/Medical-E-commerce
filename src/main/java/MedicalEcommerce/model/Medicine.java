package MedicalEcommerce.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int medicine_id;


    private String medicine_name;
    private String seller_email;
    private String price;
    private int quantity;
    private String medicine_composition;
    private String about;
    private  String manufacturing_company;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    public Medicine() {
    }

    public Medicine(String name, String seller) {
        this.medicine_name=name;
        this.seller_email=seller;
    }


    @ManyToOne
    private  UserDtls seller;

    @OneToMany(mappedBy="medicine", cascade = CascadeType.REMOVE)
    private List<Cart> cart;

    @OneToMany(mappedBy="product", cascade = CascadeType.REMOVE)
    private List<OrderDtls> orders;




    public int getMedicine_id() {
        return medicine_id;
    }

    public void setMedicine_id(int medicine_id) {
        this.medicine_id = medicine_id;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getSeller_email() {
        return seller_email;
    }

    public void setSeller_email(String seller_email) {
        this.seller_email = seller_email;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMedicine_composition() {
        return medicine_composition;
    }

    public void setMedicine_composition(String medicine_compostion) {
        this.medicine_composition = medicine_compostion;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getManufacturing_company() {
        return manufacturing_company;
    }

    public void setManufacturing_company(String manufacturing_company) {
        this.manufacturing_company = manufacturing_company;
    }

    public UserDtls getSeller() {
        return seller;
    }

    public void setSeller(UserDtls seller) {
        this.seller = seller;
    }



    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public List<OrderDtls> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDtls> orders) {
        this.orders = orders;
    }
}


