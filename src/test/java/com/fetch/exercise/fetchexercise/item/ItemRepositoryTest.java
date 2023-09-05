package com.fetch.exercise.fetchexercise.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ItemRepositoryTest {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemRepositoryTest(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Test
    public void ItemRepository_Save_ReturnItem() {

        Item item = new Item("Description", 5.00);

        Item itemSaved = itemRepository.save(item);

        Assertions.assertThat(itemSaved).isNotNull();
        Assertions.assertThat(itemSaved.getId())
                .matches(uuid -> uuid.toString().matches("([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})"));

    }

    @Test
    public void ItemRepository_GetAll_ReturnMoreThanItem() {

        Item item = new Item("Description", 5.00);
        Item item2 = new Item("Description", 5.00);

        itemRepository.save(item);
        itemRepository.save(item2);

        List<Item> items = itemRepository.findAll();

        Assertions.assertThat(items).isNotNull();
        Assertions.assertThat(items.size()).isEqualTo(2);
    }

    @Test
    public void ItemRepository_GetById_ReturnItem() {

        Item item = new Item("Description", 5.00);
        itemRepository.save(item);

        Optional<Item> itemFound = itemRepository.findById(item.getId());

        Assertions.assertThat(itemFound).isNotNull();
        Assertions.assertThat(itemFound.get().getId()).isEqualTo(item.getId());
    }
}
