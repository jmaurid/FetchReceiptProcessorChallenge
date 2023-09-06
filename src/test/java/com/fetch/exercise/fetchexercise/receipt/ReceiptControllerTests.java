package com.fetch.exercise.fetchexercise.receipt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fetch.exercise.fetchexercise.item.Item;
import com.fetch.exercise.fetchexercise.util.Points;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = ReceiptController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ReceiptControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceiptService receiptService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void ReceiptController_AddNewReceipt_ReturnCreated() throws Exception{
        List<Item> items = new ArrayList<>();
        items.add(new Item("Gatorade", 2.25));
        items.add(new Item("Gatorade", 2.25));
        items.add(new Item("Gatorade", 2.25));
        items.add(new Item("Gatorade", 2.25));

        Receipt receipt = new Receipt(
                "M&M Corner Market",
                LocalDate.of(2023, 9,5),
                LocalTime.of(15,18),
                items
        );



        given(receiptService.addNewReceipt(any())).willAnswer((invocation -> invocation.getArgument(0)));

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json = mapper.writeValueAsString(receipt);


        ResultActions resultAction = mockMvc.perform(post("/receipts/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    public void ReceiptController_GetPoints_ReturnPoints() throws Exception{
        List<Item> items = new ArrayList<>();
        items.add(new Item("Gatorade", 2.25));
        items.add(new Item("Gatorade", 2.25));
        items.add(new Item("Gatorade", 2.25));
        items.add(new Item("Gatorade", 2.25));

        Receipt receipt = new Receipt(
                UUID.randomUUID(),
                "M&M Corner Market",
                LocalDate.of(2023, 9,5),
                LocalTime.of(15,18),
                items
        );


        Points points = new Points();
        points.setPoints(109);

        given(receiptService.getReceiptPoints(ArgumentMatchers.any())).willReturn(points);


        ResultActions resultAction = mockMvc.perform(get("/receipts/{id}/points", receipt.getId()))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(points)));

    }

}
