package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.networknt.schema.JsonSchema;
import org.example.request.CreateCsiRequest;
import org.example.response.CsiResponse;
import org.example.services.interfaces.CsiServiceIntf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.NoResultException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.example.utils.JsonUtil.validateAndParseJson;


@RestController
@Component
public class CsiController {

    public CsiServiceIntf csiService;


    @PostMapping("corporate-settlement-instance/create/")
    public ResponseEntity<CsiResponse> createCsi(@RequestBody String requestJsonStr) {

        CsiResponse csiResponse;
        HttpStatus httpStatus;

        try {
            JsonSchema csiRequestJsonSchema = CreateCsiRequest.getJsonSchema();
//            Проверка Request.Body на обязательность.
//                    Если не заполнено хотя бы одно обязательное поле (см. Request.Body)
//•	вернуть Статус: 400/Bad Request, Текст: Имя обязательного параметра <значение> не заполнено.
//                    Если все обязательные поля заполнены
//•	Перейти на Шаг 2

            CreateCsiRequest csiRequest = validateAndParseJson(requestJsonStr, csiRequestJsonSchema, CreateCsiRequest.class);
            csiResponse = csiService.createCsi(csiRequest);
            httpStatus = HttpStatus.OK;
        }
        catch (JsonProcessingException | IllegalArgumentException e) {
            csiResponse = new CsiResponse();
            csiResponse.setErrorMsg(e.getMessage());
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        catch (NoResultException e) {
            csiResponse = new CsiResponse();
            csiResponse.setErrorMsg(e.getMessage());
            httpStatus = HttpStatus.NOT_FOUND;
        }
        catch (Exception e) {
            csiResponse = new CsiResponse();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            csiResponse.setErrorMsg(e.getMessage() +'\n'+ sw);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        // Возвращаем ответ на поступивший запрос
        return ResponseEntity.status(httpStatus).body(csiResponse);
    }

    @Autowired
    public void setCsiService(CsiServiceIntf csiService) {
        this.csiService = csiService;
    }
}



