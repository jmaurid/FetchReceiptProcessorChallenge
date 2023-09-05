package com.fetch.exercise.fetchexercise.receipt;

import java.util.UUID;

public class ReceiptResponse {
    private UUID id;

    public ReceiptResponse(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReceiptResponse{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
