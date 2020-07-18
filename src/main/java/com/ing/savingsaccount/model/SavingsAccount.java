package com.ing.savingsaccount.model;

import com.ing.savingsaccount.validation.WorkingHoursConstraint;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class SavingsAccount {

    private long id;

    @NotNull
    @Min(value = 10, message = "{account.save.min}")
    @Max(value = 10000, message = "{account.save.max}")
    private Integer amount;

    private String username;

    @WorkingHoursConstraint
    private Date creationDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public SavingsAccount(long id, Integer amount, String username, Date date) {
        this.id = id;
        this.amount = amount;
        this.username = username;
        this.creationDate = date;
    }

    public SavingsAccount() {
        creationDate = new Date();
    }
}
