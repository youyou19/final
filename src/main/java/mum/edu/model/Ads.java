package mum.edu.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    @NotEmpty
    private String description;

    //@NotNull
   // @Valid
    @ManyToOne
    @JoinColumn(name="admin_id")
    private User user;

   // @NotNull
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date creationDate;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private  Date updateDate;

    public Ads() {
    }

    public Ads(String description, User user) {
        this.description = description;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
