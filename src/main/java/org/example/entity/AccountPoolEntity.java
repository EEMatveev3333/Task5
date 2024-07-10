package org.example.entity;


import lombok.*;

//import javax.persistence.Basic;
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "account_pool", schema = "public", catalog = "postgres")
@Entity
@Data
@Getter
@Setter
@EqualsAndHashCode(exclude = { "id" })
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

//    @ElementCollection @Getter @Setter
//    @CollectionTable(name = "account", joinColumns = @JoinColumn(name = "account_coll"))
//    @Column(name = "account")
//    private List<String> accounts = new ArrayList<>();

}
/*
CREATE TABLE IF NOT EXISTS account_pool(
        id serial PRIMARY KEY,
        branch_code VARCHAR(50),
currency_code VARCHAR(30),
mdm_code VARCHAR(50),
priority_code VARCHAR(30),
registry_type_code VARCHAR(50)
);
*/
