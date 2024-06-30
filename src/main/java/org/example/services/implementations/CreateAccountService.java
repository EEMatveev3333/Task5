package org.example.services.implementations;


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

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
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

        // Проверяем на дубли
        TppRefProductRegisterTypeEntity registerTypeEntity = registerTypeRepo.getByValue(accountRequest.getRegistryTypeCode());
        if (registerRepo.existsByProductIdAndRegisterType(productId, registerTypeEntity)) {
            throw new IllegalArgumentException("Параметр registryTypeCode \"Тип регистра\" <"+accountRequest.getRegistryTypeCode()+"> уже существует для ЭП с ИД <"+accountRequest.getInstanceId()+">.");
        }

        // Определяем тип регистра
        List<TppRefProductRegisterTypeEntity> registerTypes = registerTypeRepo.findAllByProductClassCodeAndValue(productId.getProductCodeId().getValue(), accountRequest.getRegistryTypeCode());
        if (registerTypes.isEmpty()) {
            throw new NoResultException("КодПродукта <"+productId.getProductCodeId().getValue()+"> не найдено в Каталоге продуктов для данного типа Регистра \""+accountRequest.getRegistryTypeCode()+"\"");
        }

        // Получаем номер счёта
        String accountNum = accountNumService.getAccountNum(
                accountRequest.getBranchCode(),
                accountRequest.getCurrencyCode(),
                accountRequest.getMdmCode(),
                registerTypeEntity
        );

        // Создаём ПР
        TppProductRegisterEntity accountEntity = new TppProductRegisterEntity(productId, registerTypeEntity, accountNum, accountRequest.getCurrencyCode());

        registerRepo.save(accountEntity);

        accountResponse.getData().setAccountId (accountEntity.getId().toString());
        return accountResponse;
    }

    @Autowired
    public void setRegistryTypeRepo(TppProductRegisterRepo registerRepo) {
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
