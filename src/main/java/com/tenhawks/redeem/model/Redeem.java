package com.tenhawks.redeem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Ahmed on 7/31/2019.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "redeem_token")
public class Redeem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "redeem_token", unique = true, length = 12)
    private String redeemToken;

    @Column(name = "redeem_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date redeemTime;

    @Column(name = "redeem_user",  length = 100)
    private String redeemUser;


}
