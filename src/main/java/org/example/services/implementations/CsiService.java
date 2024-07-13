package org.example.services.implementations;


import org.example.entity.*;
import org.example.repository.*;
import org.example.request.CreateCsiRequest;
import org.example.response.CsiResponse;
import org.example.services.interfaces.AccountNumServiceIntf;
import org.example.services.interfaces.CsiServiceIntf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CsiService implements CsiServiceIntf {

    private TppProductRegisterRepo registerRepo;
    private TppProductRepo productRepo;
    private TppRefProductRegisterTypeRepo registerTypeRepo;
    private AccountNumServiceIntf accountNumService;

    private AgreementRepo agreementsRepo;

    //---
    @Autowired
    private TppRefAccountTypeRepo accountTypeRepo;

    @Transactional
    public CsiResponse createCsi(CreateCsiRequest csiRequest){
        CsiResponse csiResponse = new CsiResponse();
        TppProductEntity productEntity;
        Integer productId = csiRequest.getInstanceId();
//
//
//        if (productId == null) {
//
//            //---
//            //TppRefAccountTypeEntity productClassCode = accountTypeRepo.getByValue(csiRequest.getProductCode());
//            // Проверяем корректность переданного значения в поле ProductCode
//            //---
//            List<TppRefProductRegisterTypeEntity> registerTypes = registerTypeRepo.findAllByProductClassCodeAndAccountType(csiRequest.getProductCode(), accountTypeRepo.getByValue( "Клиентский"));
//            //List<TppRefProductRegisterTypeEntity> registerTypes = registerTypeRepo.findAllByProductClassCodeAndAccountType(productClassCode, "Клиентский");
//            if (registerTypes.isEmpty()) {
//                throw new NoResultException("КодПродукта =\""+csiRequest.getProductCode()+"\" не найден в Каталоге продуктов (tpp_ref_product_register_type)");
//            }
//
//            // Проверяем что нет договора с таким же номером
//            TppProductEntity existProduct = productRepo.getByNumber(csiRequest.getContractNumber());
//            if (existProduct != null) {
//                throw new IllegalArgumentException("Параметр ContractNumber \"№ договора\" "+csiRequest.getContractNumber()+" уже существует для \n ЭП с ИД "+existProduct.getId());
//            }
//
//            // Создаем ЭП
//            productEntity = ProductBuilder.createProductEntity(csiRequest);
//
//            // Создаём связанные ПР
//            for (TppRefProductRegisterTypeEntity registerType: registerTypes) {
//                // Получаем номер счёта
//                String accountNum = accountNumService.getAccountNum(csiRequest.getBranchCode(), csiRequest.getIsoCurrencyCode(), csiRequest.getMdmCode(), registerType);
//                TppProductRegisterEntity prEntity = new TppProductRegisterEntity(productEntity.getProductCodeId(), registerType.getValue(), accountNum, csiRequest.getIsoCurrencyCode());
//                registerRepo.save(prEntity);
//                // Созданные счета добавляем в ответ
//                csiResponse.getData().getRegisterId().add(prEntity.getId());
//            }
//
//        } else {
//            Optional<TppProductEntity> product = productRepo.findById(productId);
//            // Проверяем что нашли продукт
//            if (product.isEmpty()) {
//                throw new IllegalArgumentException("Не найден договор соответствующий параметру instanceId \"Идентификатор экземпляра продукта\" = "+csiRequest.getInstanceId());
//            }
//            productEntity = product.get();
//
//            // Проверяем что нет совпадений по номерам доп.соглашений
//            for (CreateCsiRequest.Agreement agreement: csiRequest.getInstanceAgreement()) {
//                Iterator<AgreementEntity> agreements = productEntity.getAgreements();
//                while (agreements.hasNext()) {
//                    AgreementEntity agreementEntity = agreements.next();
//                    if (agreementEntity.getNumber().equals(agreement.getNumber())) {
//                        throw new IllegalArgumentException(" Параметр Number \"№ Дополнительного соглашения (сделки)\" = \""+agreement.getNumber()+"\" уже существует для ЭП с ИД "+productId);
//                    }
//                }
//                // Добавляем новое доп.соглашение
//                AgreementEntity agreementsEntity = new AgreementEntity(agreement.getNumber());
//                productEntity.addAgreement(agreementsEntity);
//                agreementsRepo.save(agreementsEntity);
//                // Созданные доп.соглашения добавляем в ответ
//                csiResponse.getData().getSupplementaryAgreementId().add(agreementsEntity.getId());
//            }
//
//        }
//
//        productRepo.saveAndFlush(productEntity);
//
//        // Дозаполняем ответ
//        csiResponse.getData().setInstanceId(productEntity.getId());

        return csiResponse;
    }
    @Autowired
    public void setProductRepo(TppProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Autowired
    public void setRegisterTypeRepo(TppRefProductRegisterTypeRepo registerTypeRepo) {
        this.registerTypeRepo = registerTypeRepo;
    }

    @Autowired
    public void setRegistryTypeRepo(TppProductRegisterRepo registerRepo) {
        this.registerRepo = registerRepo;
    }

    @Autowired
    public void setAccountNumService(AccountNumServiceIntf accountNumService) {
        this.accountNumService = accountNumService;
    }

    @Autowired
    public void setAgreementsRepo(AgreementRepo agreementsRepo) {
        this.agreementsRepo = agreementsRepo;
    }

}
