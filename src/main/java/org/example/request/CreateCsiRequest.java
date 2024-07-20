package org.example.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;
import org.example.utils.JsonUtil;
//import jakarta.validation.Valid;
//import jakarta.validation.constraints.NotNull;
//import static org.example.utils.JsonUtil.getJsonSchema;

import java.util.Date;

@Getter
@Setter
@ToString
public class CreateCsiRequest {
    private static final com.networknt.schema.JsonSchema jsonSchema = JsonUtil.getJsonSchema(CreateCsiRequest.class);
    //private static final com.networknt.schema.JsonSchema jsonSchema = getJsonSchema(CreateCsiRequest.class);

    private Integer instanceId;
    //@NotNull(message = "Имя обязательного параметра <productType> должно быть заполнено")
    private String productType;

    private String productCode;

    private String registerType;

    private String mdmCode;

    private String contractNumber;

    private Date contractDate;

    private String priority;

    private Double interestRatePenalty;

    private Double minimalBalance;

    private Double thresholdAmount;

    private String accountingDetails;

    private String rateType;

    private Double taxPercentageRate;

    private Double technicalOverdraftLimitAmount;

    private Integer contractId;

    private String isoCurrencyCode;

    private String branchCode;

    private InstanceArrangement[] instanceAgreement;

    private AdditionalPropertiesVip additionalPropertiesVip;

    @NoArgsConstructor
    @ToString
    @Getter @Setter
    //public static class Agreement {
    public static class InstanceArrangement {
        private Integer supplementaryAgreementId;

        private String number;
    }

    @NoArgsConstructor
    @ToString
    @Getter @Setter
    public static class AdditionalPropertiesVip {
        private Data[] data;
    }

    @NoArgsConstructor
    @ToString
    @Getter @Setter
    public static class Data {
        private String key;

        private String value;

        private String name;
    }

    public static com.networknt.schema.JsonSchema getJsonSchema() {
        System.out.println("jsonSchema = "+jsonSchema);
        return  jsonSchema;
    }

}

