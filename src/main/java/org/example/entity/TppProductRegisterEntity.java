package org.example.entity;


import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

@Table(name = "tpp_product_register", schema = "public", catalog = "postgres")
@Entity
@Data
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class TppProductRegisterEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "product_id")
    private Long productId;

    @Basic
    @Column(name = "type")
    private String type;

    @Basic
    @Column(name = "account")
    private Long account;

    @Basic
    @Column(name = "currency_code")
    private String currencyCode;

    @Basic
    @Column(name = "state")
    private String state;

    @Basic
    @Column(name = "account_number")
    private String accountNumber;

}