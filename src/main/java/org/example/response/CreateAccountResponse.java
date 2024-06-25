package org.example.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class CreateAccountResponse {

    @Getter
    @Setter
    private CsiData data = new CsiData();
    @Getter
    @Setter
    private String errorMsg;

    @ToString
    public class CsiData {
        @Getter
        @Setter
        private String accountId;
    }
}

