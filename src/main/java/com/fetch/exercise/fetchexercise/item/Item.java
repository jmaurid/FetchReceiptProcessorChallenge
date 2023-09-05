package com.fetch.exercise.fetchexercise.item;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fetch.exercise.fetchexercise.receipt.Receipt;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;

    @Column
    @NotBlank(message = "Item must contain a short description.")
    private String shortDescription;

    @Column
    @NotNull(message = "Item must contain a valid price.")
    @PositiveOrZero(message = "Item must contain a valid price.")
    private Double price;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receiptId", referencedColumnName = "id")
    private Receipt receipt;

    public Item() {
    }

    public Item(UUID id) {
        this.id = id;
    }

    public Item(String shortDescription, Double price) {
        this.shortDescription = shortDescription;
        this.price = price;
    }

    public Item(UUID id, String shortDescription, Double price) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("id=").append(id);
        sb.append(", shortDescription='").append(shortDescription).append('\'');
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) && Objects.equals(shortDescription, item.shortDescription) && Objects.equals(price, item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortDescription, price);
    }
}
