package mum.edu.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;

@Entity(name="ORDERS")
public class Order {
   @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="buyer_id")
    private User buyer;

    @OneToOne
    @JoinColumn(name="payment_id")
    private Payment payment;

    @OneToOne
    @JoinColumn(name="billing_id")
    private Address billingAddress;

    @OneToOne
    @JoinColumn(name="shipping_id")
    private Address shippingAddress;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="order_id")
    @Fetch(FetchMode.JOIN)
    private Map<Long, OrderItem> orderItems=new HashMap<Long, OrderItem>();
   // private List<OrderItem> orderItems=new ArrayList<OrderItem>();

    private Boolean shipping;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date creationDate;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date updateDate;

    public Order() {
     }

    public Order(Payment payment, Map<Long, OrderItem> orderItems) {
        this.payment = payment;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    public Boolean getShipping() {
        return shipping;
    }

    public void setShipping(Boolean shipping) {
        this.shipping = shipping;
    }

    public Map<Long, OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Map<Long, OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

//
//    public List<OrderItem> getOrderItems() {
//        return orderItems;
//    }
//
//    public void setOrderItems(List<OrderItem> orderItems) {
//        this.orderItems = orderItems;
//    }

//    public void addOrderItem(OrderItem orderItem){
//        orderItems.add(orderItem);
//    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.put(orderItem.getId(), orderItem);
    }

}
