package org.example;

import jakarta.persistence.NoResultException;
import org.example.entity.*;
import org.example.repository.*;
import org.example.services.implementations.AccountNumService;
import org.example.services.implementations.CreateAccountService;
import org.example.services.implementations.CsiService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesRegex;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @MockBean
    private AccountRepo accountRepo;
    @MockBean
    private AccountPoolRepo poolRepo;
    @MockBean
    private TppProductRegisterRepo registerRepo;
    @MockBean
    private TppProductRepo productRepo;
    @MockBean
    private TppRefProductRegisterTypeRepo registerTypeRepo;
    @MockBean
    private AgreementRepo agreementsRepo;

    @InjectMocks
    private AccountNumService accountNumService;

    @InjectMocks
    private CreateAccountService accountService;

    @InjectMocks
    private CsiService csiService;

    private AccountPoolEntity getAccountPool(){
        AccountPoolEntity accountPool = new AccountPoolEntity();
        accountPool.setBranchCode("001");
        accountPool.setMdmCode("cl001");
        accountPool.setCurrencyCode("A98");
        accountPool.setRegisterTypeCode("03.012.002_47533_ComSoLd");
//        List<String> accounts = new ArrayList<>();
//        accounts.add("475335516415314841861");
//        accounts.add("4753321651354151");
//        accounts.add("4753352543276345");
//        accountPool.setAccounts(accounts);
        AccountEntity acc1 = new AccountEntity();
        acc1.setAccountPoolId(accountPool);
        acc1.setAccountNumber("475335516415314841861");

        AccountEntity acc2 = new AccountEntity();
        acc2.setAccountPoolId(accountPool);
        acc2.setAccountNumber("4753321651354151");

        AccountEntity acc3 = new AccountEntity();
        acc3.setAccountPoolId(accountPool);
        acc3.setAccountNumber("4753352543276345");

        return accountPool;
    }


    @Test
    @DisplayName("Тест работы пула счетов")
    public void accountNumServiceTest(){
        AccountPoolEntity accountPool = getAccountPool();

        TppRefProductRegisterTypeEntity typeEntity = new TppRefProductRegisterTypeEntity();
        typeEntity.setValue("03.012.002_47533_ComSoLd");

        //given(poolRepo.getByBranchCodeAndCurrencyCodeAndMdmCodeAndRegisterTypeCode("001", "A98", "cl001", "03.012.002_47533_ComSoLd"))
        given(poolRepo.getByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegisterTypeCode("001", "A98", "cl001","00", "03.012.002_47533_ComSoLd"))
                .willReturn(accountPool);

        Assertions.assertEquals("475335516415314841861", accountNumService.getAccount("001", "A98", "cl001","00", typeEntity), "Из пула счетов вернули не тот номер счёта");
        Assertions.assertEquals(2, accountPool.getAccounts().size(), "Из пула счетов не удалён использованный счёт");
        Assertions.assertEquals("4753321651354151", accountNumService.getAccount("001", "A98", "cl001","00", typeEntity), "Из пула счетов вернули не тот номер счёта");
        Assertions.assertEquals(1, accountPool.getAccounts().size(), "Из пула счетов не удалён использованный счёт");
        Assertions.assertEquals("4753352543276345", accountNumService.getAccount("001", "A98", "cl001","00", typeEntity), "Из пула счетов вернули не тот номер счёта");
        Assertions.assertEquals(0, accountPool.getAccounts().size(), "Из пула счетов не удалён использованный счёт");
        Assertions.assertThrows(NoResultException.class, ()->accountNumService.getAccount("001", "A98", "cl001", "00", typeEntity), "Не сгенерировалась ошибка при отсутствии счёта в пуле");
    }
/*
    @Test
    @DisplayName("Тест работы сервиса создания счетов")
    public void CreateAccountServiceTest(){
        // Общие структуры
        TppRefProductClassEntity productClass = new TppRefProductClassEntity();
        productClass.setValue("03.012.002");

        // test case 1 - definitions
        TppProductEntity productIdBad = new TppProductEntity();
        productIdBad.setId(2);
        productIdBad.setProductCodeId(productClass);
        CreateAccountRequest accountRequestBad = new CreateAccountRequest();
        accountRequestBad.setInstanceId(productIdBad.getId());
        accountRequestBad.setRegistryTypeCode("03.012.002_47533_ComSoLd");
        // test case 1 - mocks
        given(productRepo.existsById(accountRequestBad.getInstanceId()))
                .willReturn(false);
        // test case 1 - run
        Assertions.assertThrows(NoResultException.class, ()->accountService.createAccount(accountRequestBad), "Не сработала проверка на доступность InstanceId");

        // test case 2 - definitions
        TppProductEntity productIdBad2 = new TppProductEntity();
        productIdBad2.setId(2);
        productIdBad2.setProductCodeId(productClass);
        CreateAccountRequest accountRequestBad2 = new CreateAccountRequest();
        accountRequestBad2.setInstanceId(3);
        accountRequestBad2.setRegistryTypeCode("03.012.002_47533_ComSoLd");
        TppRefProductRegisterTypeEntity registerTypeEntity = new TppRefProductRegisterTypeEntity();
        registerTypeEntity.setValue("03.012.002_47533_ComSoLd");
        registerTypeEntity.setInternalId(10);
        // test case 2 - mocks
        given(productRepo.existsById(accountRequestBad2.getInstanceId()))
                .willReturn(true);
        given(productRepo.getReferenceById(accountRequestBad2.getInstanceId()))
                .willReturn(productIdBad2);
        given(registerTypeRepo.getByValue(accountRequestBad2.getRegistryTypeCode()))
                .willReturn(registerTypeEntity);
        given(registerRepo.existsByProductIdAndRegisterType(productIdBad2, registerTypeEntity))
                .willReturn(true);
        // test case 2 - run
        Assertions.assertThrows(IllegalArgumentException.class, ()->accountService.createAccount(accountRequestBad2), "Не сработала проверка на дубль счёта одного типа");

        // test case 3 - definitions
        TppProductEntity productId = new TppProductEntity();
        productId.setId(1);
        productId.setProductCodeId(productClass);
        CreateAccountRequest accountRequestBad3 = new CreateAccountRequest();
        accountRequestBad3.setInstanceId(productId.getId());
        accountRequestBad3.setRegistryTypeCode("03.012.002_47533_ComSoLd");
        TppRefProductRegisterTypeEntity registerTypeEntity3 = new TppRefProductRegisterTypeEntity();
        registerTypeEntity3.setValue("03.012.002_47533_ComSoLd");
        registerTypeEntity3.setInternalId(10);
        List<TppRefProductRegisterTypeEntity> registerTypes = new ArrayList<>();
        // test case 3 - mocks
        given(productRepo.getReferenceById(accountRequestBad3.getInstanceId()))
                .willReturn(productId);
        given(registerTypeRepo.getByValue(accountRequestBad3.getRegistryTypeCode()))
                .willReturn(registerTypeEntity3);
        given(registerRepo.existsByProductIdAndRegisterType(productId, registerTypeEntity3))
                .willReturn(false);
//        given(registerTypeRepo.findAllByProductClassCodeAndValue(productId.getProductCodeId().getValue(), accountRequestBad3.getRegistryTypeCode()))
        given(registerTypeRepo.findAllByProductClassCodeAndValue(productId.getProductCodeId(), accountRequestBad3.getRegistryTypeCode()))
                .willReturn(registerTypes);
        // test case 3 - run
        Assertions.assertThrows(NoResultException.class, ()->accountService.createAccount(accountRequestBad3), "Не сработала проверка поиск подходящего типа регистра");

    }

    @Test
    @DisplayName("Тест работы сервиса создания экземпляров продукта")
    public void CsiServiceTest(){
        // test case 1 - definitions
        CreateCsiRequest csiRequest = new CreateCsiRequest();
        csiRequest.setInstanceId(1);
        // test case 1 - mocks
        given(productRepo.findById(csiRequest.getInstanceId()))
                .willReturn(Optional.empty());
        // test case 1 - run
        Assertions.assertThrows(IllegalArgumentException.class, ()->csiService.createCsi(csiRequest), "Не сработала проверка на не найденный продукт");

        // test case 2 - definitions
        CreateCsiRequest csiRequest2 = new CreateCsiRequest();
        csiRequest2.setInstanceId(2);
        CreateCsiRequest.Agreement[] requestAgreements = new CreateCsiRequest.Agreement[1];
        requestAgreements[0] = new CreateCsiRequest.Agreement();
        requestAgreements[0].setNumber("10");
        csiRequest2.setInstanceAgreement(requestAgreements);
        TppProductEntity productEntity = new TppProductEntity();
        AgreementEntity agreementsEntity = new AgreementEntity();
        agreementsEntity.setNumber("10");
        // test case 2 - mocks
        given(agreementsRepo.save(agreementsEntity))
                .willReturn(null);
        productEntity.addAgreement(agreementsEntity);
        // test case 2 - run
        Assertions.assertThrows(IllegalArgumentException.class, ()->csiService.createCsi(csiRequest2), "Не сработала проверка на дублирование номеров доп.соглашений");

        // test case 3 - definitions
        CreateCsiRequest csiRequest3 = new CreateCsiRequest();
        csiRequest3.setInstanceId(null);
        csiRequest3.setProductCode("02.001.005_45343_CoDowFF");
        List<TppRefProductRegisterTypeEntity> registerTypes3 = new ArrayList<>();
        // test case 3 - mocks
        given(registerTypeRepo.findAllByProductClassCodeAndAccountType(csiRequest3.getProductCode(), "Клиентский"))
                .willReturn(registerTypes3);
        // test case 3 - run
        Assertions.assertThrows(NoResultException.class, ()->csiService.createCsi(csiRequest3), "Не сработала проверка на не найденный код продукта");

        // test case 4 - definitions
        CreateCsiRequest csiRequest4 = new CreateCsiRequest();
        csiRequest4.setInstanceId(null);
        csiRequest4.setProductCode("03.012.002_47533_ComSoLd");
        csiRequest4.setContractNumber("777");
        List<TppRefProductRegisterTypeEntity> registerTypes4 = new ArrayList<>();
        registerTypes4.add(null);
        TppProductEntity existProduct = new TppProductEntity();
        // test case 4 - mocks
        given(registerTypeRepo.findAllByProductClassCodeAndAccountType(csiRequest4.getProductCode(), "Клиентский"))
                .willReturn(registerTypes4);
        given(productRepo.getByNumber(csiRequest4.getContractNumber()))
                .willReturn(existProduct);
        // test case 4 - run
        Assertions.assertThrows(IllegalArgumentException.class, ()->csiService.createCsi(csiRequest4), "Не сработала проверка на поиск дублей по номеру продукта");

        // test case 5 - definitions
        CreateCsiRequest csiRequest5 = new CreateCsiRequest();
        csiRequest5.setInstanceId(1);
        csiRequest5.setInstanceAgreement(new CreateCsiRequest.Agreement[1]);
        csiRequest5.getInstanceAgreement()[0] = new CreateCsiRequest.Agreement();
        csiRequest5.getInstanceAgreement()[0].setNumber("777");
        Optional<TppProductEntity> product5 = Optional.of(new TppProductEntity());
        product5.get().addAgreement(new AgreementEntity("777"));
        // test case 5 - mocks
        given(productRepo.findById(csiRequest.getInstanceId()))
                .willReturn(product5);
        // test case 5 - run
        Assertions.assertThrows(IllegalArgumentException.class, ()->csiService.createCsi(csiRequest5), "Не сработала проверка на дубль номера дополнительного соглашения");

    }
*/
}