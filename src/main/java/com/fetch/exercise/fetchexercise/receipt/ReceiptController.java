package com.fetch.exercise.fetchexercise.receipt;

import com.fetch.exercise.fetchexercise.util.Points;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @GetMapping
    public List<Receipt> getReceipts(){
        return receiptService.getReceipts();
    }

    @PostMapping("/process")
    public ResponseEntity<ReceiptResponse> addNewReceipt(@Valid @RequestBody Receipt receipt) {
        ReceiptResponse receiptResponse = receiptService.addNewReceipt(receipt);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(receiptResponse.getId()).toUri();
        return ResponseEntity.created(location).body(receiptResponse);
    }

    @GetMapping("/{id}/pints")
    public ResponseEntity<Points> getPoints(@PathVariable UUID id){
        return ResponseEntity.ok(receiptService.getReceiptPoints(new Receipt(id)));
    }

}
