package org.example.entity;


import lombok.*;

/*import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;*/
import jakarta.persistence.*;

@Table(name = "tpp_ref_product_class", schema = "public", catalog = "postgres",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"value"}
        ))
@Entity
@Data
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class TppRefProductClassEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "internal_id")
    private Integer internalId;

    @Basic
    @Column(name = "value")
    private String value;

    @Basic
    @Column(name = "gbi_code")
    private String gbiCode;

    @Basic
    @Column(name = "gbi_name")
    private String gbiName;

    @Basic
    @Column(name = "product_row_code")
    private String productRowCode;

    @Basic
    @Column(name = "product_row_name")
    private String productRowName;

    @Basic
    @Column(name = "subclass_code")
    private String subclassCode;

    @Basic
    @Column(name = "subclass_name")
    private String subclassName;

}


/*
CREATE TABLE IF NOT EXISTS tpp_ref_product_class
        (
                internal_id serial PRIMARY KEY ,
                value VARCHAR(100) UNIQUE NOT NULL,
gbi_code VARCHAR(50),
gbi_name VARCHAR(100),
product_row_code VARCHAR(50),
product_row_name VARCHAR(100),
subclass_code VARCHAR(50),
subclass_name VARCHAR(100)
);
*/
