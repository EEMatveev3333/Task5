import org.example.controller.AccountController;
import org.example.controller.CsiController;
import org.example.entity.TppProductEntity;
import org.example.entity.TppProductRegisterEntity;
import org.example.repository.TppProductRegisterRepo;
import org.example.repository.TppProductRepo;
import org.example.repository.TppRefProductRegisterTypeRepo;
import org.example.request.CreateAccountRequest;
import org.example.request.CreateCsiRequest;
import org.example.response.CreateAccountResponse;
import org.example.response.CsiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import java.util.Date;

//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestServices {
    @Autowired
    private AccountController accountController;
    @Autowired
    private CsiController controllerInstance;
    @Autowired
    private TppProductRegisterRepo productRegisterRepo;
    @Autowired
    private TppRefProductRegisterTypeRepo registerTypeRepo;
    @Autowired
    private TppProductRepo productRepo;

    // проверим, что при некорректных данных в запросе, в ответ возвращается ошибка
    @Test
    public void Test1() {
        CreateAccountRequest requestAccount = new CreateAccountRequest();

        requestAccount.setInstanceId(1);
        requestAccount.setRegistryTypeCode("1");
        requestAccount.setAccountType("2");
        requestAccount.setCurrencyCode("3");
        requestAccount.setBranchCode("4");
        requestAccount.setPriorityCode("5");
        requestAccount.setMdmCode("6");
        requestAccount.setClientCode("7");
        requestAccount.setTrainRegion("8");
        requestAccount.setCounter("9");
        requestAccount.setSalesCode("10");

        try {
            CreateAccountResponse responseAccount = accountController.accountService.createAccount(requestAccount);
        }
        catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("не найден"));
        }
    }

    // аналогично для requestInstance
    @Test
    public void Test2() {
        CreateCsiRequest requestInstance = new CreateCsiRequest();
        requestInstance.setInstanceId(1);
        requestInstance.setProductType("03.012.002_47533_ComSoLd");
        requestInstance.setProductCode("111111");
        requestInstance.setRegisterType("1");
        requestInstance.setMdmCode("15");
        requestInstance.setContractNumber("");
        requestInstance.setContractDate(new Date());
        requestInstance.setPriority("00");
        requestInstance.setInterestRatePenalty(.0);
        requestInstance.setMinimalBalance(.0);
        requestInstance.setThresholdAmount(.0);
        requestInstance.setAccountingDetails(null);
        requestInstance.setRateType(null);
        requestInstance.setTechnicalOverdraftLimitAmount(.0);
        requestInstance.setContractId(0);
        requestInstance.setBranchCode("0022");
        requestInstance.setIsoCurrencyCode("800");
        //requestInstance.setUrgencyCode("1");
        //requestInstance.setReferenceCode(0);
        CreateCsiRequest.Data[] tmpDatas = new CreateCsiRequest.Data[2];
        tmpDatas[0] = new CreateCsiRequest.Data();
        tmpDatas[0].setKey("RailwayRegionOwn");
        tmpDatas[0].setValue("ABC");
        tmpDatas[0].setName("Регион принадлежности железной дороги");
        tmpDatas[1] = new CreateCsiRequest.Data();
        tmpDatas[1].setKey("counter");
        tmpDatas[1].setValue("123");
        tmpDatas[1].setName("Счетчик");

        CreateCsiRequest.AdditionalPropertiesVip tmpAddProp = new CreateCsiRequest.AdditionalPropertiesVip();
        tmpAddProp.setData(tmpDatas);
        requestInstance.setAdditionalPropertiesVip(tmpAddProp);
        CreateCsiRequest.InstanceArrangement[] tmpReqInst = new CreateCsiRequest.InstanceArrangement[3];
        tmpReqInst[0] = new CreateCsiRequest.InstanceArrangement();
        tmpReqInst[0].setNumber("4");
        tmpReqInst[0].setSupplementaryAgreementId(4);//.setOpeningDate(new Date());
        tmpReqInst[1] = new CreateCsiRequest.InstanceArrangement();
        tmpReqInst[1].setNumber("5");
        tmpReqInst[1].setSupplementaryAgreementId(5);//.setOpeningDate(new Date());
        tmpReqInst[2] = new CreateCsiRequest.InstanceArrangement();
        tmpReqInst[2].setNumber("6");
        tmpReqInst[2].setSupplementaryAgreementId(6);//.setOpeningDate(new Date());
        requestInstance.setInstanceAgreement(tmpReqInst);

        try {
            CsiResponse responseInstance = controllerInstance.csiService.createCsi(requestInstance);
        }
        catch (RuntimeException e) {
            assertThat(e.getMessage(), containsString("не найден"));
        }
    }

    /*
    // интеграционный тест на создание ПР и проверку записанных в БД данных
    @Test
    public void Test3(){
        // сначала проверим, что такой записи нет
        // если есть - удалим ее
        if (productRegisterRepo.existsByProductId(55))
            productRegisterRepo.delete(productRegisterRepo.getByProductId(55));

        CreateAccountRequest requestAccount = new CreateAccountRequest();
        requestAccount.setInstanceId(55);
        requestAccount.setRegistryTypeCode("03.012.002_47533_ComSoLd");
        requestAccount.setAccountType("55");
        requestAccount.setCurrencyCode("800");
        requestAccount.setBranchCode("0022");
        requestAccount.setPriorityCode("00");
        requestAccount.setMdmCode("15");
        requestAccount.setClientCode("55");
        requestAccount.setTrainRegion("55");
        requestAccount.setCounter("55");
        requestAccount.setSalesCode("55");

        CreateAccountResponse responseAccount = controllerAccount.createAccount(requestAccount);

        TppProductRegisterEntity tmp = productRegisterRepo.getByProductId(55);

        // проверим сохраненные данные
        Assertions.assertEquals(requestAccount.getInstanceId(), tmp.getProductId(),"Проверили корректность сохраненного productId");
        Assertions.assertEquals(requestAccount.getCurrencyCode(), tmp.getCurrencyCode(),"Проверили корректность сохраненного CurrencyCode");
        Assertions.assertEquals(registerTypeRepo.getByValue(requestAccount.getRegistryTypeCode()).getId(), tmp.getType().getId(),"Проверили корректность сохраненного type");

        // удалим созданные тестовые данные
        if (productRegisterRepo.existsByProductId(55))
            productRegisterRepo.delete(productRegisterRepo.getByProductId(55));
    }

    // интеграционный тест на создание ЭП и проверку записанных в БД данных
    @Test
    public void Test4() {
        CreateCsiRequest requestInstance = new CreateCsiRequest();
        requestInstance.setInstanceId(1);
        requestInstance.setProductType("03.012.002_47533_ComSoLd");
        requestInstance.setProductCode("03.012.002");
        requestInstance.setRegisterType("1");
        requestInstance.setMdmCode("15");
        requestInstance.setContractNumber("55");
        requestInstance.setContractDate(new Date());
        requestInstance.setPriority("00");
        requestInstance.setInterestRatePenalty(.0);
        requestInstance.setMinimalBalance(.0);
        requestInstance.setThresholdAmount(.0);
        requestInstance.setAccountingDetails(null);
        requestInstance.setRateType(null);
        requestInstance.setTechnicalOverdraftLimitAmount(.0);
        requestInstance.setContractId(0);
        requestInstance.setBranchCode("0022");
        requestInstance.setIsoCurrencyCode("800");
        requestInstance.setUrgencyCode("1");
        requestInstance.setReferenceCode(0);
        CreateCsiRequest.Data[] tmpDatas = new CreateCsiRequest.Data[2];
        tmpDatas[0] = new RequestInstance.Data();
        tmpDatas[0].setKey("RailwayRegionOwn");
        tmpDatas[0].setValue("ABC");
        tmpDatas[0].setName("Регион принадлежности железной дороги");
        tmpDatas[1] = new RequestInstance.Data();
        tmpDatas[1].setKey("counter");
        tmpDatas[1].setValue("123");
        tmpDatas[1].setName("Счетчик");

        CreateCsiRequest.AdditionalPropertiesVip tmpAddProp = new CreateCsiRequest.AdditionalPropertiesVip();
        tmpAddProp.setData(tmpDatas);
        requestInstance.setAdditionalPropertiesVip(tmpAddProp);
        CreateCsiRequest.InstanceArrangement[] tmpReqInst = new CreateCsiRequest.InstanceArrangement[3];
        tmpReqInst[0] = new CreateCsiRequest.InstanceArrangement();
        tmpReqInst[0].setNumber("4");
        tmpReqInst[0].setOpeningDate(new Date());
        tmpReqInst[1] = new CreateCsiRequest.InstanceArrangement();
        tmpReqInst[1].setNumber("5");
        tmpReqInst[1].setOpeningDate(new Date());
        tmpReqInst[2] = new CreateCsiRequest.InstanceArrangement();
        tmpReqInst[2].setNumber("6");
        tmpReqInst[2].setOpeningDate(new Date());
        requestInstance.setInstanceAgreement(tmpReqInst);

        CsiResponse responseInstance = controllerInstance.createCsi(requestInstance);

        TppProductEntity tmpProd = productRepo.getById(responseInstance.getData().getInstanceId());

        // проверим сохраненные данные
        Assertions.assertEquals(requestInstance.getProductType(), tmpProd.getType(),"Проверили корректность сохраненного type");
        Assertions.assertEquals(requestInstance.getContractNumber(), tmpProd.getNumber(),"Проверили корректность сохраненного number");
        Assertions.assertEquals(requestInstance.getPriority(), tmpProd.getPriority(),"Проверили корректность сохраненного priority");

        TppProductRegisterEntity tmpProdReg = productRegisterRepo.getByProductId(responseInstance.getData().getRegisterId().get(0));
        // проверим сохраненные данные
        Assertions.assertEquals(registerTypeRepo.getByValue(requestInstance.getProductType()).getId(), tmpProdReg.getType(),"Проверили корректность сохраненного type");
        Assertions.assertEquals(requestInstance.getIsoCurrencyCode(), tmpProdReg.getCurrencyCode(),"Проверили корректность сохраненного currencyCode");

        // удалим созданные тестовые данные
        if (productRegisterRepo.existsByProductId(responseInstance.getData().getRegisterId().get(0)))
            productRegisterRepo.delete(productRegisterRepo.getByProductId(responseInstance.getData().getRegisterId().get(0)));
        if (productRepo.existsById(responseInstance.getData().getInstanceId()))
            productRepo.delete(productRepo.getById(responseInstance.getData().getInstanceId()));

    }
    */

}