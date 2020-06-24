package mum.edu.model;

import javax.persistence.*;

@Entity
public class OrderItem {
  @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int itemQuantity;
    private double price;

    private Boolean delivered = false;
    @OneToOne
   @JoinColumn(name="product_id")
   private Product product;

    public OrderItem() {
    }

    public OrderItem(Product product, int itemQuantity) {
        this.itemQuantity = itemQuantity;
        this.product = product;
        this.price = product.getPrice()*itemQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }
}
