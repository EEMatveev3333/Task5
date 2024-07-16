package org.example.services.implementations;


import org.example.entity.AccountEntity;
import org.example.entity.TppProductEntity;
import org.example.entity.TppProductRegisterEntity;
import org.example.entity.TppRefProductRegisterTypeEntity;
import org.example.repository.TppProductRegisterRepo;
import org.example.repository.TppProductRepo;
import org.example.repository.TppRefProductRegisterTypeRepo;
import org.example.request.CreateAccountRequest;
import org.example.response.CreateAccountResponse;
import org.example.services.interfaces.AccountNumServiceIntf;
import org.example.services.interfaces.CreateAccountServiceIntf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class CreateAccountService implements CreateAccountServiceIntf {

    private TppProductRegisterRepo registerRepo;
    private TppProductRepo productRepo;
    private TppRefProductRegisterTypeRepo registerTypeRepo;
    private AccountNumServiceIntf accountNumService;


    @Transactional
    public CreateAccountResponse createAccount(CreateAccountRequest accountRequest) throws NoResultException {
        TppProductEntity productId;

        CreateAccountResponse accountResponse = new CreateAccountResponse();

        // Ищем экземпляр продукта по переданному Id
        if (productRepo.existsById(accountRequest.getInstanceId())) {
            productId = productRepo.getReferenceById(accountRequest.getInstanceId());
        } else {
            throw new NoResultException("По instanceId \"Идентификатор ЭП\" <"+accountRequest.getInstanceId()+"> не найден экземпляр продукта.");
        }

        //        Шаг 2.
        //        Проверка таблицы ПР (таблица tpp_product_register) на дубли. Для этого необходимо отобрать строки по условию tpp_product_register.product_id == Request.Body.InstanceID и у результата отобрать строки по условию tpp_product_register.type == Request.Body.registryTypeCode. Если результат отбора не пуст, значит имеются повторы
        //
        //        Если повторы найдены
        //•	вернуть Статус: 400/Bad Request, Текст: Параметр registryTypeCode тип регистра <значение_кода> уже существует для ЭП с ИД  <значение_ИД_ЭП>.
        //        Если повторов нет
        //•	Перейти на Шаг 3.

        // Проверяем на дубли
        TppRefProductRegisterTypeEntity registerTypeEntity = registerTypeRepo.getByValue(accountRequest.getRegistryTypeCode());
        //if (registerRepo.existsByProductIdAndRegisterType(productId, registerTypeEntity.getRegisterTypeName())) {
        if (registerRepo.existsByProductIdAndRegisterType(productId, registerTypeEntity)) {
            throw new IllegalArgumentException("Параметр registryTypeCode \"Тип регистра\" <"+accountRequest.getRegistryTypeCode()+"> уже существует для ЭП с ИД <"+accountRequest.getInstanceId()+">.");
        }
////////////////

        //        Шаг 3.
        //        Взять значение из Request.Body.registryTypeCode и найти соответствующие ему записи в tpp_ref_product_register_type.value.
        //
        //                Если найдено совпадение
        //•	перейти на Шаг 4.
        //        Если совпадений не обнаружено
        //•	вернуть Статус: 404/Not found, Текст: Код Продукта <значение> не найдено в Каталоге продуктов <схема.имя таблицы БД> для данного типа Регистра

        // Определяем тип регистра
        List<TppRefProductRegisterTypeEntity> registerTypes = registerTypeRepo.findAllByProductClassCodeAndValue(productId.getProductCodeId().getValue(), accountRequest.getRegistryTypeCode());
        if (registerTypes.isEmpty()) {
            throw new NoResultException("Код Продукта <"+productId.getProductCodeId().getValue()+"> не найдено в Каталоге продуктов для данного типа Регистра \""+accountRequest.getRegistryTypeCode()+"\"");
        }
        ////////////////

//        //шаг 4 находим ссылку на aacount по параметрам
//        AccountEntity account = registerUtils.getAccount(request.getBranchCode(),
//                request.getCurrencyCode(),request.getMdmCode(),
//                request.getPriorityCode(),request.getRegistryTypeCode());

        // Получаем номер счёта
        AccountEntity account = accountNumService.getAccount(
                accountRequest.getBranchCode(),
                accountRequest.getCurrencyCode(),
                accountRequest.getMdmCode(),
                accountRequest.getPriorityCode(),
                registerTypeEntity
        );

        // Создаём ПР
        TppProductRegisterEntity accountEntity = new TppProductRegisterEntity(productId, registerTypeEntity, account, accountRequest.getCurrencyCode());

        registerRepo.save(accountEntity);

        accountResponse.getData().setAccountId(accountEntity.getId().toString());

        return accountResponse;
    }

    @Autowired
    public void setRegisterRepo(TppProductRegisterRepo registerRepo) {
        this.registerRepo = registerRepo;
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
    public void setAccountNumService(AccountNumServiceIntf accountNumService) {
        this.accountNumService = accountNumService;
    }
}
