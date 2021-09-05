package com.paro.siparisverbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Picture {
    @Id
    private String id;
    private String picture_id;
    private String market;
    private Date start_date;
    private Date end_date;
}
