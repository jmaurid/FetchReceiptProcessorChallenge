package com.fetch.exercise.fetchexercise.receipt;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fetch.exercise.fetchexercise.item.Item;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "receipt")
public class Receipt {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;
    @Column
    @NotBlank(message = "Invalid retailer")
    private String retailer;
    @Column
    @NotNull(message = "Invalid purchase date.")
    @PastOrPresent(message = "Purchase Date is not a valid date")
    private LocalDate purchaseDate;

    @Column
    @NotNull(message = "Invalid purchase time")
    @PastOrPresent(message = "Purchase Time is not a valid time")
    private LocalTime purchaseTime;
    @OneToMany(
            mappedBy = "receipt",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    @NotEmpty(message = "Receipt must be contain at least one item.")
    private List<Item> items = new ArrayList<>();

    private Double total;

    public Receipt() {
    }

    public Receipt(UUID id) {
        this.id = id;

    }

    public Receipt(String retailer, LocalDate purchaseDate, LocalTime purchaseTime, List<Item> items) {
        this.retailer = retailer;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.items = items;
        this.total = items != null ? this.items.stream().mapToDouble(Item::getPrice).sum() : 0;
    }

    public Receipt(UUID id, String retailer, LocalDate purchaseDate, LocalTime purchaseTime, List<Item> items) {
        this.id = id;
        this.retailer = retailer;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.items = items;
        this.total = items != null ? this.items.stream().mapToDouble(Item::getPrice).sum() : 0;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseTime() {
        return purchaseTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public void setPurchaseTime(LocalTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setTotal() {
        this.total = this.items != null ? this.items.stream().mapToDouble(Item::getPrice).sum() : 0;
    }

    public Double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Receipt{");
        sb.append("id=").append(id);
        sb.append(", retailer='").append(retailer).append('\'');
        sb.append(", purchaseDate=").append(purchaseDate);
        sb.append(", purchaseTime=").append(purchaseTime);
        sb.append(", items=").append(items);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return Objects.equals(id, receipt.id) && Objects.equals(retailer, receipt.retailer) && Objects.equals(purchaseDate, receipt.purchaseDate) && Objects.equals(purchaseTime, receipt.purchaseTime) && Objects.equals(items, receipt.items) && Objects.equals(total, receipt.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, retailer, purchaseDate, purchaseTime, items, total);
    }
}
