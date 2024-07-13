package org.example.entity;


import lombok.*;

/*import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;*/
import jakarta.persistence.*;

@Table(name = "tpp_ref_account_type", schema = "public", catalog = "postgres",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"value"}
                                            )
)
@Entity
@Data
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class TppRefAccountTypeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "internal_id")
    private Integer internalId;

    @Basic
    @Column(name = "value")
    private String value;

}

/*
CREATE TABLE IF NOT EXISTS tpp_ref_account_type
        (
                internal_id serial PRIMARY KEY ,
                value VARCHAR(100) UNIQUE NOT NULL
);*/
