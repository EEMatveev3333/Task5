package org.example.entity;


import lombok.*;

//import javax.persistence.Basic;
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import jakarta.persistence.*;

@Table(name = "agreement", schema = "public", catalog = "postgres")
@Entity
@Data
@Getter
@Setter
@EqualsAndHashCode(exclude = { "id" })
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class AgreementEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

//    @Basic
//    @Column(name = "product_id")
//    private Integer productId;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Integer productId;
//    private TppProductEntity productId;

    @Basic
    @Column(name = "general_agreement_id")
    private String generalAgreementId;

    @Basic
    @Column(name = "supplementary_agreement_id")
    private String supplementaryAgreementId;

    @Basic
    @Column(name = "arrangement_type")
    private String arrangementType;

    @Basic
    @Column(name = "sheduler_job_id")
    private Long shedulerJobId;

    @Basic
    @Column(name = "number")
    private String number;

    @Basic
    @Column(name = "opening_date")
    private Date openingDate;

    @Basic
    @Column(name = "closing_date")
    private Date closingDate;

    @Basic
    @Column(name = "cancel_date")
    private Date cancelDate;

    @Basic
    @Column(name = "validity_duration")
    private Long validityDuration;

    @Basic
    @Column(name = "cancellation_reason")
    private String cancellationReason;

    @Basic
    @Column(name = "status")
    private String status;

    @Basic
    @Column(name = "interest_calculation_date")
    private Date interestCalculationDate;

    @Basic
    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Basic
    @Column(name = "coefficient")
    private BigDecimal coefficient;

    @Basic
    @Column(name = "coefficient_action")
    private String coefficientAction;

    @Basic
    @Column(name = "minimum_interest_rate")
    private BigDecimal minimumInterestRate;

    @Basic
    @Column(name = "minimum_interest_rate_coefficient")
    private BigDecimal minimumInterestRateCoefficient;

    @Basic
    @Column(name = "minimum_interest_rate_coefficient_action")
    private String minimumInterestRateCoefficientAction;

    @Basic
    @Column(name = "maximal_interest_rate")
    private BigDecimal maximalInterestRate;

    @Basic
    @Column(name = "maximal_interest_rate_coefficient")
    private BigDecimal maximalInterestRateCoefficient;

    @Basic
    @Column(name = "maximal_interest_rate_coefficient_action")
    private String maximalInterestRateCoefficientAction;

//    public AgreementEntity(String number) {
//        this.number = number;
//    }
}



/*
CREATE TABLE IF NOT EXISTS agreement
        (
                id serial PRIMARY KEY,
                product_id integer,
                general_agreement_id VARCHAR(50),
supplementary_agreement_id VARCHAR(50),
arrangement_type VARCHAR(50),
sheduler_job_id BIGINT,
number VARCHAR(50),
opening_date TIMESTAMP,
closing_date TIMESTAMP,
cancel_date TIMESTAMP,
validity_duration BIGINT,
cancellation_reason VARCHAR(100),
status VARCHAR(50),
interest_calculation_date TIMESTAMP,
interest_rate DECIMAL,
coefficient DECIMAL,
coefficient_action VARCHAR(50),
minimum_interest_rate DECIMAL,
minimum_interest_rate_coefficient DECIMAL,
minimum_interest_rate_coefficient_action VARCHAR(50),
maximal_interest_rate DECIMAL,
maximal_interest_rate_coefficient DECIMAL,
maximal_interest_rate_coefficient_action VARCHAR(50)
);

ALTER TABLE agreement
ADD FOREIGN KEY (product_id) REFERENCES tpp_product (id);*/
