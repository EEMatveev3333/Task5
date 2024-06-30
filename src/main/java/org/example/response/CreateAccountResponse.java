package org.example.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CreateAccountResponse {


    private CsiData data = new CsiData();

    private String errorMsg;

    @ToString
    @Getter
    @Setter
    public class CsiData {

        private String accountId;
    }
}

