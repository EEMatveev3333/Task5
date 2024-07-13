package org.example.entity;


import jakarta.persistence.*;
import lombok.*;

//import javax.persistence.*;

@Table(name = "account", schema = "public", catalog = "postgres")
@Entity
@Data
@Getter
@Setter
@EqualsAndHashCode(exclude = { "id" })
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class AccountEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

//    @Basic
//    @Column(name = "account_pool_id")
//    private Integer accountPoolId;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "account_pool_id", referencedColumnName = "id")
//    private AccountPoolEntity accountPoolId;
    private Integer accountPoolId;


    @Basic
    @Column(name = "account_number")
    private String accountNumber;

    @Basic
    @Column(name = "bussy")
    private Boolean bussy;
}


/*CREATE TABLE IF NOT EXISTS account(
        id serial PRIMARY KEY,
        account_pool_id integer,
        account_number VARCHAR(25),
bussy BOOLEAN
);

ALTER TABLE account
ADD FOREIGN KEY (account_pool_id) REFERENCES account_pool (id);
        --ON DELETE CASCADE;*/
