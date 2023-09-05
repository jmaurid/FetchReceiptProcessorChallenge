package com.fetch.exercise.fetchexercise.receipt;

import com.fetch.exercise.fetchexercise.item.Item;
import com.fetch.exercise.fetchexercise.util.Points;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceiptServiceTests {

    @Mock
    private ReceiptRepository receiptRepository;

    @InjectMocks
    private ReceiptService receiptService;

    @Test
    public void ReceiptService_GetReceipts_ReturnListOfReceipts(){
        List<Item> items = new ArrayList<>();
        items.add(new Item("Description", 5.00));
        items.add(new Item("Description 1", 2.00));

        Receipt receipt = new Receipt(
                "Retailer",
                LocalDate.now(),
                LocalTime.now(),
                items
        );

        Receipt receipt2 = new Receipt(
                "Retailer",
                LocalDate.now(),
                LocalTime.now(),
                items
        );

        List<Receipt> receipts = new ArrayList<>();
        receipts.add(receipt);
        receipts.add(receipt2);

        when(receiptRepository.findAll()).thenReturn(receipts);

        List<Receipt> receiptList = receiptRepository.findAll();

        Assertions.assertThat(receiptList).isNotNull();
        Assertions.assertThat(receiptList.size()).isEqualTo(2);

    }

    @Test
    public void ReceiptService_AddNewReceipt_ReturnReceiptResponse(){
        List<Item> items = new ArrayList<>();
        items.add(new Item("Description", 5.00));
        items.add(new Item("Description 1", 2.00));

        Receipt receipt = new Receipt(
                UUID.randomUUID(),
                "Retailer",
                LocalDate.now(),
                LocalTime.now(),
                items
        );

        when(receiptRepository.save(any(Receipt.class))).thenReturn(receipt);

        ReceiptResponse receiptResponse = receiptService.addNewReceipt(receipt);

        Assertions.assertThat(receiptResponse).isNotNull();
        Assertions.assertThat(receiptResponse.getId()).isInstanceOf(UUID.class);
    }

    @Test
    public void ReceiptService_GetPoints_ReturnReceiptResponse(){
        List<Item> items = new ArrayList<>();
        items.add(new Item("Mountain Dew 12PK", 6.49));
        items.add(new Item("Emils Cheese Pizza", 12.25));
        items.add(new Item("Knorr Creamy Chicken", 1.26));
        items.add(new Item("Doritos Nacho Cheese", 3.35));
        items.add(new Item("   Klarbrunn 12-PK 12 FL OZ  ", 12.00));

        Receipt receipt = new Receipt(
                "Target",
                LocalDate.of(2022,01,01),
                LocalTime.of(13,01),
                items
        );

        when(receiptRepository.existsById(Mockito.any())).thenReturn(true);
        when(receiptRepository.getReferenceById(Mockito.any())).thenReturn(receipt);

        Points points = receiptService.getReceiptPoints(receipt);

        Assertions.assertThat(points).isNotNull();
        Assertions.assertThat(points.getPoints()).isEqualTo(28);
    }


}
