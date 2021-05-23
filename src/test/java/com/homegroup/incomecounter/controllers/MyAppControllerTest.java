package com.homegroup.incomecounter.controllers;

import com.homegroup.incomecounter.dtos.PersonDailySpending;
import com.homegroup.incomecounter.dtos.PersonSpendingInPeriod;
import com.homegroup.incomecounter.models.PersonDailySpendingSearch;
import com.homegroup.incomecounter.models.PersonSpendingInPeriodSearch;
import com.homegroup.incomecounter.services.PersonSpendingService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.homegroup.incomecounter.util.StringConstants.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = MyAppController.class)
@RunWith(SpringRunner.class)
public class MyAppControllerTest {
    private final String catFood = "Food";
    private final String catStudy = "Study";
    private final String perAlex = "Alex";
    private final String perBrian = "Brian";
    private final LocalDate date1 = LocalDate.of(1996, 07, 22);
    private final LocalDate date2 = LocalDate.of(2018, 05, 15);
    private final LocalDate date3 = LocalDate.of(2021, 05, 21);
    private final LocalDate date4 = LocalDate.of(2021, 07, 22);
    private final long amount1 = -1000L;
    private final long amount2 = 600L;
    private final long amount3 = 7000L;
    private final long amount4 = -14500L;
    private final long amount5 = 10750L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonSpendingService service;

    @Test
    @DisplayName("readPersonSpendingInPeriod successful")
    public void readPersonSpendingInPeriodSuccessful() throws Exception {
        //given
        final String requestUrl = MYAPP + SPENDING_IN_PERIOD;
        List<PersonSpendingInPeriod> list = List.of(new PersonSpendingInPeriod(null, perAlex, amount1), new PersonSpendingInPeriod(null, perBrian, amount2));

        //when
        when(service.readPersonSpendingInPeriod(any(PersonSpendingInPeriodSearch.class)))
                .thenReturn(list);

        //then
        mockMvc.perform(get(requestUrl)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("startDate", date1.toString())
                .param("endDate", date1.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0]").exists())
                .andExpect(jsonPath("$.[0].person").value(perAlex))
                .andExpect(jsonPath("$.[0].spending").value(amount1))
                .andExpect(jsonPath("$.[1].person").value(perBrian))
                .andExpect(jsonPath("$.[1].spending").value(amount2));
    }

    @Test
    @DisplayName("readPersonDailySpending successful")
    public void readPersonDailySpendingSuccessful() throws Exception {
        //given
        final String requestUrl = MYAPP + DAILY_SPENDING;
        List<PersonDailySpending> list = List.of(new PersonDailySpending(date1, amount1), new PersonDailySpending(date2, amount2));

        //when
        when(service.readPersonDailySpending(any(PersonDailySpendingSearch.class)))
                .thenReturn(list);

        //then
        mockMvc.perform(get(requestUrl)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("startDate", date1.toString())
                .param("endDate", date1.toString())
                .param("person", UUID.randomUUID().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0]").exists())
                .andExpect(jsonPath("$.[0].date").value(date1.toString()))
                .andExpect(jsonPath("$.[0].spending").value(amount1))
                .andExpect(jsonPath("$.[1]").exists())
                .andExpect(jsonPath("$.[1].date").value(date2.toString()))
                .andExpect(jsonPath("$.[1].spending").value(amount2));
    }

    @Test
    @DisplayName("readPersonDailySpending not found")
    public void readPersonDailySpendingNotFound() throws Exception {
        //given
        final String requestUrl = MYAPP + DAILY_SPENDING;

        //when
        when(service.readPersonDailySpending(any(PersonDailySpendingSearch.class)))
                .thenThrow(new EntityNotFoundException());

        //then
        mockMvc.perform(get(requestUrl)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("startDate", date1.toString())
                .param("endDate", date1.toString())
                .param("person", UUID.randomUUID().toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
