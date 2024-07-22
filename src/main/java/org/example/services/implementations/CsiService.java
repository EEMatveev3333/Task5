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

import java.math.BigDecimal;
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

    //---
    @Autowired
    private TppRefProductClassRepo productClassRepo;

    @Transactional
    public CsiResponse createCsi(CreateCsiRequest csiRequest){
        CsiResponse csiResponse = new CsiResponse();
        TppProductEntity productEntity;
        Integer productId = csiRequest.getInstanceId();

//        Шаг 2
//        Если ИД ЭП в поле Request.Body.instanceId не задано (NULL/Пусто), то выполняется процесс создания нового экземпляра
//•	Перейти на шаг 1.1
//        Если ИД ЭП в поле Request.Body.instanceId не пустое, то изменяется состав ДС // сделка (доп.Соглашение)
//•	Перейти на шаг 2.1

//шаг 1.1
        if (productId == null) {

            //---
            //TppRefAccountTypeEntity productClassCode = accountTypeRepo.getByValue(csiRequest.getProductCode());
            // Проверяем корректность переданного значения в поле ProductCode
            //---

//            Шаг 1.3
//            По КодуПродукта найти связные записи в Каталоге Типа регистра
//            Request.Body.ProductCode == tpp_ref_product_class.value
//            среди найденных записей отобрать те, у которых
//            tpp_ref_product_register_type.account_type имеет значение  “Клиентский”
//
//            Если найдено одна или более записей
//•	Запомнить найденные registerType с целью добавления соответствующего числа строк в таблицу ПР (tpp_product_registry), перейти на Шаг 1.4
//            Если ни одной записи не найдено
//•	вернуть Статус: 404/Not Found, Текст: КодПродукта <значение> не найдено в Каталоге продуктов tpp_ref_product_class

            //List<TppRefProductRegisterTypeEntity> registerTypes = registerTypeRepo.findAllByProductClassCodeAndAccountType(csiRequest.getProductCode(), accountTypeRepo.getByValue( "Клиентский"));
            //List<TppRefProductRegisterTypeEntity> registerTypes = registerTypeRepo.findAllByProductClassCodeAndAccountType(productClassCode, "Клиентский");
            List<TppRefProductRegisterTypeEntity> registerTypes = registerTypeRepo.findAllByProductClassCodeAndAccountType(productClassRepo.getByValue(csiRequest.getProductCode()), accountTypeRepo.getByValue( "Клиентский"));
            if (registerTypes.isEmpty()) {
                throw new NoResultException("КодПродукта =\""+csiRequest.getProductCode()+"\" не найден в Каталоге продуктов (tpp_ref_product_register_type)");
            }

//            Шаг 1.1
//            Проверка таблицы ЭП (tpp_product) на дубли. Для этого необходимо отобрать строки по условию
//            tpp_product.number == Request.Body.ContractNumber
//            Если результат отбора не пуст, значит имеются повторы
//
//            Если повторы найдены
//•	вернуть Статус: 400/Bad Request, Текст: Параметр ContractNumber № договора <значение> уже существует для ЭП с ИД  <значение1>.
//            Если повторов нет
//•	Перейти на Шаг 1.2


            // Проверяем что нет договора с таким же номером
            TppProductEntity existProduct = productRepo.getByNumber(csiRequest.getContractNumber());
            if (existProduct != null) {
                throw new IllegalArgumentException("Параметр ContractNumber \"№ договора\" "+csiRequest.getContractNumber()+" уже существует для \n ЭП с ИД "+existProduct.getId());
            }

//            Шаг 1.4
//            Добавить строку в таблицу tpp_product, заполнить согласно Request.Body:
//            Сформировать/Запомнить новый ИД ЭП tpp_product.id, д
//            Перейти на Шаг 1.5

            // Создаем ЭП
            productEntity = ProductBuilder.createProductEntity(csiRequest);

//            Шаг 1.5
//            Добавить в таблицу ПР (tpp_product_registry) строки, заполненные:
//•	Id - ключ таблицы
//•	product_id - ссылка на таблицу ЭП, где tpp_product.id  == tpp_product_registry.product_id
//•	type – тип ПР (лицевого/внутрибанковского счета)
//•	account_id – ид счета
//•	currency_code – код валюты счета
//•	state – статус счета, enum (0, Закрыт/1, Открыт/2, Зарезервирован/3, Удалён)
//            (см. задачу на создание продуктового регистра по методу corporate-settlement-account/create)

            // Создаём связанные ПР
            for (TppRefProductRegisterTypeEntity registerType: registerTypes) {
                // Получаем номер счёта
                //String accountNum = accountNumService.getAccountNum(csiRequest.getBranchCode(), csiRequest.getIsoCurrencyCode(), csiRequest.getMdmCode(), registerType);
                //TppProductRegisterEntity prEntity = new TppProductRegisterEntity(productEntity.getProductCodeId(), registerType.getValue(), accountNum, csiRequest.getIsoCurrencyCode());
                AccountEntity account = accountNumService.getAccount(csiRequest.getBranchCode(), csiRequest.getIsoCurrencyCode(), csiRequest.getMdmCode(), csiRequest.getPriority(), registerType);
                TppProductRegisterEntity prEntity = new TppProductRegisterEntity(productEntity, registerType, account, csiRequest.getIsoCurrencyCode());

                registerRepo.save(prEntity);
                // Созданные счета добавляем в ответ
                csiResponse.getData().getRegisterId().add(prEntity.getId());
            }

        }
//шаг 2.1
        else
        {
//            Шаг 2.1
//            Проверка таблицы ЭП (tpp_product) на существование ЭП. Для этого необходимо отобрать строки по условию
//            tpp_product.id == Request.Body. instanceId
//
//            Если запись не найдена
//•	вернуть Статус: 404/Not Found, Текст: Экземпляр продукта с параметром instanceId <значение> не найден.
//                Если запись найдена
//•	Перейти на Шаг 2.2


            Optional<TppProductEntity> product = productRepo.findById(productId);
            // Проверяем что нашли продукт
            if (product.isEmpty()) {
                throw new IllegalArgumentException("Не найден договор соответствующий параметру instanceId \"Идентификатор экземпляра продукта\" = "+csiRequest.getInstanceId());
            }
            productEntity = product.get();

//            Шаг 2.2
//            Проверка таблицы ДС (agreement) на дубли
//            Отобрать записи по условию agreement.number == Request.Body.Arrangement[N].Number
//
//            Если записи найдены
//•	вернуть Статус: 400/Bad Request, Текст: Параметр № Дополнительного соглашения (сделки) Number <значение> уже существует для ЭП с ИД  <значение1>.
//            Если записей нет
//•	перейти на Шаг 2.3

            // Проверяем что нет совпадений по номерам доп.соглашений
            // !!! EEM Проверяем что нет совпадений в AgreementEntity по номерам доп.соглашений agreement.getNumber и productEntity
//            for (CreateCsiRequest.Agreement agreement: csiRequest.getInstanceAgreement()) {
//                Iterator<AgreementEntity> agreements = productEntity.getAgreements();
//                while (agreements.hasNext()) {
//                    AgreementEntity agreementEntity = agreements.next();
//                    if (agreementEntity.getNumber().equals(agreement.getNumber())) {
//                        throw new IllegalArgumentException(" Параметр Number \"№ Дополнительного соглашения (сделки)\" = \""+agreement.getNumber()+"\" уже существует для ЭП с ИД "+productId);
//                    }
//                }
            for (CreateCsiRequest.InstanceArrangement agreement: csiRequest.getInstanceAgreement()) {
                List<AgreementEntity> lstAgreementEntity = agreementsRepo.findAllByProductIdAndNumber(productEntity,agreement.getNumber());
                if (!lstAgreementEntity.isEmpty()) {
                        throw new IllegalArgumentException(" Параметр Number \"№ Дополнительного соглашения (сделки)\" = \""+agreement.getNumber()+"\" уже существует для ЭП с ИД "+productId);
                }
//                Iterator<AgreementEntity> agreements = productEntity.getAgreements();
//                while (agreements.hasNext()) {
//                    AgreementEntity agreementEntity = agreements.next();
//                    if (agreementEntity.getNumber().equals(agreement.getNumber())) {
//                        throw new IllegalArgumentException(" Параметр Number \"№ Дополнительного соглашения (сделки)\" = \""+agreement.getNumber()+"\" уже существует для ЭП с ИД "+productId);
//                    }
//                }
//                Шаг 8.
//•	Добавить строку в таблицу ДС (agreement)
//•	заполнить соотв. поля ДС согласно составу Request.Body, см. массив Arrangement[…]
//•	сформировать agreement.Id , связать с таблицей ЭП по ИД ЭП, где tpp_product.id  == agreement.product_id

                // Добавляем новое доп.соглашение
                AgreementEntity agreementsEntity = new AgreementEntity(agreement.getNumber());
                agreementsEntity.setProductId(productEntity);
                agreementsEntity.setSupplementaryAgreementId(Integer.toString(agreement.getSupplementaryAgreementId()));

                //agreementsEntity.setProductId(productId);
                agreementsEntity.setGeneralAgreementId(agreement.getGeneralAgreementId());
                //agreementsEntity.setSupplementaryAgreementId(requestAgreement.getSupplementaryAgreementId());
                //agreementsEntity.setArrangementType(ProductRegistryType.fromValue(agreement.getArrangementType()).name());
                agreementsEntity.setArrangementType(agreement.getArrangementType());
                agreementsEntity.setShedulerJobId((long) agreement.getShedulerJobId());
                agreementsEntity.setNumber(agreement.getNumber());
                agreementsEntity.setOpeningDate((agreement.getOpeningDate()));
                agreementsEntity.setClosingDate((agreement.getClosingDate()));
                agreementsEntity.setCancelDate((agreement.getCancelDate()));
                agreementsEntity.setValidityDuration((long) agreement.getValidityDuration());
                agreementsEntity.setCancellationReason(agreement.getCancellationReason());
                //agreementsEntity.setStatus(ProductRegisterState.fromValue(agreement.getStatus()).name());
                agreementsEntity.setStatus(agreement.getStatus());
                agreementsEntity.setInterestCalculationDate((agreement.getInterestCalculationDate()));
                agreementsEntity.setInterestRate(BigDecimal.valueOf(agreement.getInterestRate()));
                agreementsEntity.setCoefficient(BigDecimal.valueOf(agreement.getCoefficient()));
                agreementsEntity.setCoefficientAction(agreement.getCoefficientAction());
                agreementsEntity.setMinimumInterestRate(BigDecimal.valueOf(agreement.getMinimumInterestRate()));
                agreementsEntity.setMinimumInterestRateCoefficient(BigDecimal.valueOf(agreement.getMinimumInterestRateCoefficient()));
                agreementsEntity.setMinimumInterestRateCoefficientAction(agreement.getMinimumInterestRateCoefficientAction());
                agreementsEntity.setMaximalInterestRate(BigDecimal.valueOf(agreement.getMaximalnterestRate()));
                agreementsEntity.setMaximalInterestRateCoefficient(BigDecimal.valueOf(agreement.getMaximalnterestRateCoefficient()));
                agreementsEntity.setMaximalInterestRateCoefficientAction(agreement.getMaximalnterestRateCoefficientAction());



                //productEntity.addAgreement(agreementsEntity);
                //AgreementEntity agreementsEntity = new AgreementEntity();
                agreementsRepo.save(agreementsEntity);
                // Созданные доп.соглашения добавляем в ответ
                csiResponse.getData().getSupplementaryAgreementId().add(agreementsEntity.getId());
            }

        }

        productRepo.saveAndFlush(productEntity);

        // Дозаполняем ответ
        csiResponse.getData().setInstanceId(productEntity.getId());

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
