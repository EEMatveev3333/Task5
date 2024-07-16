package org.example.services.interfaces;

import org.example.entity.AccountEntity;
import org.example.entity.TppProductEntity;
import org.example.entity.TppRefProductRegisterTypeEntity;

public interface AccountNumServiceIntf {
//    String getAccountNum(String branchCode, String currencyCode, String mdmCode, String priorityCode, TppRefProductRegisterTypeEntity registerType);
AccountEntity getAccount(String branchCode, String currencyCode, String mdmCode, String priorityCode, TppRefProductRegisterTypeEntity registerType);

}
