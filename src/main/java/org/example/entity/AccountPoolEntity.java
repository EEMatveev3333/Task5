package org.example.entity;


import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

@Table(name = "account_pool", schema = "public", catalog = "postgres")
@Entity
@Data
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class AccountPoolEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "branch_code")
    private String branchCode;

    @Basic
    @Column(name = "currency_code")
    private String currencyCode;

    @Basic
    @Column(name = "mdm_code")
    private String mdmCode;

    @Basic
    @Column(name = "priority_code")
    private String priorityCode;

    @Basic
    @Column(name = "register_type_code")
    private String registerTypeCode;

}
