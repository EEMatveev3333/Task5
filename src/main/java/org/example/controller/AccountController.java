package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.request.CreateAccountRequest;
import org.example.response.CreateAccountResponse;
import org.example.services.interfaces.CreateAccountServiceIntf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.NoResultException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.example.utils.JsonUtil.validateAndParseJson;

@RestController
public class AccountController {

    private CreateAccountServiceIntf accountService;
    //Метод POST corporate-settlement-account/create предназначен для создания нового объекта ПРОДУКТОВЫЙ РЕГИСТР (ПР)
    @PostMapping("corporate-settlement-account/create/")
    public ResponseEntity<CreateAccountResponse> createAccount(@RequestBody String requestJsonStr) {
        CreateAccountResponse accountResponse;
        HttpStatus httpStatus;

        try {

            CreateAccountRequest accountRequest = validateAndParseJson(requestJsonStr, CreateAccountRequest.getJsonSchema(), CreateAccountRequest.class);
            accountResponse = accountService.createAccount(accountRequest);
            httpStatus = HttpStatus.OK;
        }
        //        ШАГ 1.
        //        Проверка Request.Body на обязательность.
        //                Если не заполнено хотя бы одно обязательное поле (см. Request.Body)
        //•	вернуть Статус: 400/Bad Request, Текст: Имя обязательного параметра <значение> не заполнено.
        //                Если все обязательные поля заполнены
        //•	Перейти на Шаг 2.
        catch (JsonProcessingException | IllegalArgumentException e) {
            accountResponse = new CreateAccountResponse();
            accountResponse.setErrorMsg(e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        //        Шаг 3.
        //        Взять значение из Request.Body.registryTypeCode и найти соответствующие ему записи в tpp_ref_product_register_type.value.
        //
        //                Если найдено совпадение
        //•	перейти на Шаг 4.
        //        Если совпадений не обнаружено
        //•	вернуть Статус: 404/Not found, Текст: Код Продукта <значение> не найдено в Каталоге продуктов <схема.имя таблицы БД> для данного типа Регистра

        catch (NoResultException e) {
            accountResponse = new CreateAccountResponse();
            accountResponse.setErrorMsg(e.getMessage());
            httpStatus = HttpStatus.NOT_FOUND;
        }
        catch (Exception e) {
            accountResponse = new CreateAccountResponse();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            accountResponse.setErrorMsg(e.getMessage() +'\n'+ sw);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        // Возвращаем ответ на поступивший запрос
        return ResponseEntity.status(httpStatus).body(accountResponse);

    }

    @Autowired
    public void setAccountService(CreateAccountServiceIntf accountService) {
        this.accountService = accountService;
    }
}


