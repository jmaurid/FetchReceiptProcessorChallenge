package com.fetch.exercise.fetchexercise.receipt;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    @Autowired
    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public List<Receipt> getReceipts(){
        return receiptRepository.findAll();
    }

    @Transactional
    public ResponseEntity addNewReceipt(Receipt receipt) {
        receipt.setTotal();
        receiptRepository.save(receipt);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id]").buildAndExpand(receipt.getId()).toUri();
        ReceiptResponse receiptResponse = new ReceiptResponse(receipt.getId());
        return ResponseEntity.created(location).body(receiptResponse);
    }

    public ResponseEntity getReceiptPoints(Receipt receipt) {
        // TODO Method implementation
        return null;
    }
}
