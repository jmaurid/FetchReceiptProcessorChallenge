package com.fetch.exercise.fetchexercise.receipt;

import com.fetch.exercise.fetchexercise.item.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReceiptRepositoryTests {

    private final ReceiptRepository receiptRepository;

    @Autowired
    public ReceiptRepositoryTests(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Test
    public void ReceiptRepository_Save_ReturnReceipt() {

        List<Item> items = new ArrayList<>();
        items.add(new Item("Description", 5.00));
        items.add(new Item("Description 1", 2.00));

        Receipt receipt = new Receipt(
                "Retailer",
                LocalDate.now(),
                LocalTime.now(),
                items
        );

        Receipt receiptSaved = receiptRepository.save(receipt);

        Assertions.assertThat(receiptSaved).isNotNull();
        Assertions.assertThat(receiptSaved.getId())
                .matches(uuid -> uuid.toString().matches("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})"));

    }

    @Test
    public void ReceiptRepository_GetAll_ReturnMoreThanReceipt() {

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

        receiptRepository.save(receipt);
        receiptRepository.save(receipt2);

        List<Receipt> receipts = receiptRepository.findAll();

        Assertions.assertThat(receipts).isNotNull();
        Assertions.assertThat(receipts.size()).isEqualTo(2);
    }

    @Test
    public void ReceiptRepository_GetById_ReturnReceipt() {

        List<Item> items = new ArrayList<>();
        items.add(new Item("Description", 5.00));
        items.add(new Item("Description 1", 2.00));

        Receipt receipt = new Receipt(
                "Retailer",
                LocalDate.now(),
                LocalTime.now(),
                items
        );

        receiptRepository.save(receipt);

        Optional<Receipt> receiptFound = receiptRepository.findById(receipt.getId());

        Assertions.assertThat(receiptFound).isNotNull();
        Assertions.assertThat(receiptFound.get().getId()).isEqualTo(receipt.getId());
    }
}
