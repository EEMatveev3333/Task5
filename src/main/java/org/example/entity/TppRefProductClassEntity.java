package org.example.entity;


import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

@Table(name = "tpp_ref_product_class", schema = "public", catalog = "postgres")
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
    private int internalId;

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