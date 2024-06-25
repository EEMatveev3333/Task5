package org.example.services.interfaces;

import org.example.entity.TppRefProductRegisterTypeEntity;

public interface AccountNumServiceIntf {
    String getAccountNum(String branchCode, String currencyCode, String mdmCode, TppRefProductRegisterTypeEntity registerType);
}
