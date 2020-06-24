package mum.edu.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty

//    @Pattern(regexp ="^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
//            "(?<mastercard>5[1-5][0-9]{14})|" +
//            "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
//            "(?<amex>3[47][0-9]{16})|" +
//            "(?<paypal>2(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
//            "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$")
    private String cardNumber;

    @NotEmpty
    private String name;

    @Transient
    private String cardValue;
    //@NotNull
    private CardType cardType;


    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date expiryDate;

    @NotNull
    //@Size(min=3, max=3, message = "{payment.size.cvv}")
    private Integer cvv;

    @ManyToOne
    @JoinColumn(name="buyer_id")
    private User buyer;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date creationDate;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
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

    public String getCardValue() {
        return cardValue;
    }

    public void setCardValue(String cardValue) {
        this.cardValue = cardValue;
    }
}
