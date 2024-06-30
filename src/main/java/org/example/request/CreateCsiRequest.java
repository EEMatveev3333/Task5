package org.example.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.utils.JsonUtil;

//import static org.example.utils.JsonUtil.getJsonSchema;

import java.util.Date;

@Getter
@Setter
@ToString
public class CreateCsiRequest {
    private static final com.networknt.schema.JsonSchema jsonSchema = JsonUtil.getJsonSchema(CreateCsiRequest.class);
    //private static final com.networknt.schema.JsonSchema jsonSchema = getJsonSchema(CreateCsiRequest.class);

    private Integer instanceId;

    private String productType;

    private String productCode;

    private String registerType;

    private String mdmCode;

    private String contractNumber;

    private Date contractDate;

    private Integer priority;

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

    private Agreement[] instanceAgreement;

    private AdditionalPropertiesVip additionalPropertiesVip;

    @NoArgsConstructor
    @ToString
    @Getter @Setter
    public static class Agreement {

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

