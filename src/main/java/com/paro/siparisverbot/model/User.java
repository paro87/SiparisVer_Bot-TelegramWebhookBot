package com.paro.siparisverbot.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "TG_user")
public class User {
    @Id
    private int id;
    private String name;
    private String surname;
    private String deliveryAddress;
    private String botState;

}
