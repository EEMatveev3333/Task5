package org.example.response;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
public class CsiResponse {

    private CsiData data = new CsiData();

    private String errorMsg;

    @ToString
    @Getter
    @Setter
    public class CsiData {

        private Integer instanceId;

        private List<Integer> registerId = new ArrayList<>();

        private List<Integer> supplementaryAgreementId = new ArrayList<>();
    }
}

