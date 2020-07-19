package com.ing.savingsaccount.controller;

import com.ing.savingsaccount.config.AllowedTimeframe;
import com.ing.savingsaccount.model.SavingsAccount;
import com.ing.savingsaccount.repo.SavingsAccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {SavingsAccountController.class, SavingsAccountRepository.class})
@WebMvcTest(SavingsAccountController.class)
public class SavingsAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SavingsAccountRepository savingsAccountRepository;

    @MockBean
    private AllowedTimeframe allowedTimeframe;

    @Test
    @WithMockUser
    public void testLandingPageLoadsWithAuthentication() throws Exception {
        MvcResult result = mockMvc.perform(get("/welcome"))
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        assertTrue(result.getModelAndView().toString().contains("account"));
    }

    @Test
    @WithMockUser
    public void whenAccountIsOutsideHours_thenConstraintViolation() throws Exception {
        when(allowedTimeframe.getDays()).thenReturn(List.of("Tuesday"));
        when(allowedTimeframe.getStartHour()).thenReturn(8);
        when(allowedTimeframe.getEndHour()).thenReturn(20);

        MvcResult result = mockMvc.perform(
                post("/addacount")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("account", invalidAccountHours())
                        .with(csrf())
        ).andExpect(status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertTrue(result.getResolvedException().getMessage().contains("Can't create the account because the creation date is outside working hours"));
    }

    @Test
    @WithMockUser
    public void whenAccountHasBelowMinAmount_thenConstraintViolation() throws Exception {
        when(allowedTimeframe.getDays()).thenReturn(List.of("Tuesday"));
        when(allowedTimeframe.getStartHour()).thenReturn(8);
        when(allowedTimeframe.getEndHour()).thenReturn(20);

        MvcResult result = mockMvc.perform(
                post("/addacount")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("account", invalidAccountAmount())
                        .with(csrf())
        ).andExpect(status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertTrue(result.getResolvedException().getMessage().contains("Amount should not be less than 10"));
    }

    @Test
    @WithMockUser
    public void whenAccountIsValid_thenNoConstraintViolationIsThrown_andRedirectToWelcome() throws Exception {
        when(allowedTimeframe.getDays()).thenReturn(List.of("Tuesday"));
        when(allowedTimeframe.getStartHour()).thenReturn(8);
        when(allowedTimeframe.getEndHour()).thenReturn(20);

        MvcResult result = mockMvc.perform(
                post("/addacount")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("account", validAccount())
                        .with(csrf())
        ).andExpect(status().is3xxRedirection())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        assertFalse(result.getFlashMap().containsValue("Can't create the account because the creation date is outside working hours"));
    }

    private SavingsAccount invalidAccountHours() {
        Calendar calendar = Calendar.getInstance();
        //Monday 13 july
        calendar.set(2020, Calendar.JULY, 13, 12, 30);
        return new SavingsAccount(-1L, 20, "user", calendar.getTime());
    }

    private SavingsAccount invalidAccountAmount() {
        Calendar calendar = Calendar.getInstance();
        //Tuesday 14 july
        calendar.set(2020, Calendar.JULY, 14, 12, 30);
        return new SavingsAccount(-1L, 9, "user", calendar.getTime());
    }

    private SavingsAccount validAccount() {
        Calendar calendar = Calendar.getInstance();
        //Tuesday 14 july
        calendar.set(2020, Calendar.JULY, 14, 12, 30);
        return new SavingsAccount(-1L, 20, "user", calendar.getTime());
    }

}
