package org.example.entity;
//    НЕ ПО СХЕМЕ!!!
//@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "product_class_code", referencedColumnName = "internal_id")
//private TppRefProductClassEntity productClassCode;
//    НЕ ПО СХЕМЕ!!!

import lombok.*;

/*import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;*/
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

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
    private Integer internalId;

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
    private Date registerTypeStartDate;

    @Basic
    @Column(name = "register_type_end_date")
    private Date registerTypeEndDate;

//    @Basic
//    @Column(name = "account_type")
//    private String accountType;
@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "account_type", referencedColumnName = "internal_id")
private TppRefAccountTypeEntity accountType;

}
/*

CREATE TABLE IF NOT EXISTS tpp_ref_product_register_type
        (
                internal_id serial PRIMARY KEY ,
                value VARCHAR(100) UNIQUE NOT NULL,
register_type_name VARCHAR(100) NOT NULL,
product_class_code VARCHAR(100) NOT NULL,
register_type_start_date TIMESTAMP,
register_type_end_date TIMESTAMP,
account_type VARCHAR(100)
);
ALTER TABLE tpp_ref_product_register_type
ADD FOREIGN KEY (product_class_code) REFERENCES tpp_ref_product_class (value);

ALTER TABLE tpp_ref_product_register_type
ADD FOREIGN KEY (account_type) REFERENCES tpp_ref_account_type (value);
*/

