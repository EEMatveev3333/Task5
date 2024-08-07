package org.example.entity;


import lombok.*;

/*import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;*/
import jakarta.persistence.*;
import org.example.enums.AccountState;

@Table(name = "tpp_product_register", schema = "public", catalog = "postgres")
@Entity
@Data
@Getter
@Setter
@EqualsAndHashCode(exclude = { "id" })
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class TppProductRegisterEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    //    НЕ ПО СХЕМЕ!!!
    //@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "product_id", referencedColumnName = "id")
    //private TppProductEntity productId;
    //@Basic
    //@Column(name = "product_id")
    //private Long productId;
    //    НЕ ПО СХЕМЕ!!!
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "product_id", referencedColumnName = "id")
    private TppProductEntity productId;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "type", referencedColumnName = "value")
    private TppRefProductRegisterTypeEntity registerType;
    //private String registerType;

//    @Basic
//    @Column(name = "product_id")
//    private Long productId;
//
//    @Basic
//    @Column(name = "type")
//    //private String type;
//    private String registerType;

    @Basic
    @Column(name = "account")
    private Long account;
    //private Long accountNum;

    @Basic
    @Column(name = "currency_code")
    //private String currencyCode;
    private String currency;

    @Basic
    @Column(name = "state")
    private AccountState state;

    @Basic
    @Column(name = "account_number")
    private String accountNum;

//    public TppProductRegisterEntity(TppProductEntity productId, TppRefProductRegisterTypeEntity registerType, String accountNum, String currency) {
////public TppProductRegisterEntity(Long productId, String registerType, String accountNum, String currency) {
//        this.productId = productId;
//        this.registerType = registerType;
//        this.accountNum = accountNum;
//        this.currency = currency;
//        this.state = AccountState.OPEN;
//    }

    public TppProductRegisterEntity(TppProductEntity productId, TppRefProductRegisterTypeEntity registerType, AccountEntity account, String currency) {
//public TppProductRegisterEntity(Long productId, String registerType, String accountNum, String currency) {
        this.productId = productId;
        this.registerType = registerType;
        this.accountNum = account.getAccountNumber();
        this.account = Long.valueOf(account.getId());
        this.currency = currency;
        this.state = AccountState.OPEN;
    }
}

/*CREATE TABLE IF NOT EXISTS tpp_product_register
        (
                id serial PRIMARY KEY ,
                product_id BIGINT,
                type VARCHAR(100) NOT NULL,
account BIGINT,
currency_code VARCHAR(30),
state VARCHAR(50),
account_number VARCHAR(25)
);

ALTER TABLE tpp_product_register
ADD FOREIGN KEY (type) REFERENCES tpp_ref_product_register_type (value);*/

