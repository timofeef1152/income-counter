package com.homegroup.incomecounter.controllers;

import com.homegroup.incomecounter.dtos.PersonIncomeDto;
import com.homegroup.incomecounter.models.TransactionSearch;
import com.homegroup.incomecounter.services.PersonIncomeService;
import com.homegroup.incomecounter.services.PersonIncomeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/myapp",
//TODO uncomment
//        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class MyAppController {
    private final PersonIncomeService personIncomeService;

    @Autowired
    public MyAppController(PersonIncomeServiceImpl personIncomeService) {
        this.personIncomeService = personIncomeService;
    }

    @GetMapping("spending-in-period")
    public List<PersonIncomeDto> readPeopleIncome(@Valid TransactionSearch search) {
        return personIncomeService.readPeopleIncome(search);
    }
}

/*
Написать REST endpoint /myapp/spending-in-period
Эндпоинт для получения агрегированных значений расходов/доходов за заданный период
Обязательные параметры
● startDate - дата начала периода
● endDate - дата окончания периода
Опциональные параметры (в реквесте их может не быть, или могут быть не все)
● categoryFilter - категории включенные в расчет
● commentFilter - фильтр по комментариям, например учитывать только транзакции в
комментарии к которым есть слово Такси
● minAmount - учитывать транзакции с минимальной тратой X, например
minAmount=-1000 исключает покупки дороже 1000
● maxAmount - учитывать транзакции с максимальной тратой X, например
maxAmount=0 исключает прибыль, учитывает только расходы

 */