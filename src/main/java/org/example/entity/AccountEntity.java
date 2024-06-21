package org.example.entity;


import lombok.*;

import javax.persistence.*;

@Table(name = "account", schema = "public", catalog = "postgres")
@Entity
@Data
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class AccountEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "account_pool_id")
    private Integer accountPoolId;

    @Basic
    @Column(name = "account_number")
    private String accountNumber;

}