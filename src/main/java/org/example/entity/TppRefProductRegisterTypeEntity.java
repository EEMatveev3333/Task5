package org.example.entity;


import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "tpp_ref_product_register_type", schema = "public", catalog = "postgres")
@Entity
@Data
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString



public class TppRefProductRegisterTypeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "internal_id")
    private int internalId;

    @Basic
    @Column(name = "value")
    private String value;

    @Basic
    @Column(name = "register_type_name")
    private String registerTypeName;

    @Basic
    @Column(name = "product_class_code")
    private String productClassCode;

    @Basic
    @Column(name = "register_type_start_date")
    private Timestamp registerTypeStartDate;

    @Basic
    @Column(name = "register_type_end_date")
    private Timestamp registerTypeEndDate;

    @Basic
    @Column(name = "account_type")
    private String accountType;

}

//ALTER TABLE tpp_ref_product_register_type
//ADD FOREIGN KEY (product_class_code) REFERENCES tpp_ref_product_class (value);
//
//ALTER TABLE tpp_ref_product_register_type
//ADD FOREIGN KEY (account_type) REFERENCES tpp_ref_account_type (value);
