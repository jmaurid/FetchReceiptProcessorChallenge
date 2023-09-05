package com.fetch.exercise.fetchexercise.receipt;

import com.fetch.exercise.fetchexercise.exception.ResourceNotFoundException;
import com.fetch.exercise.fetchexercise.util.Points;
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


    /**
     *
     * Returns a List of Receipt objects that are present in the database
     * @return List of Receipt objects
     *
     */
    public List<Receipt> getReceipts(){
        return receiptRepository.findAll();
    }

    /**
     *
     * Returns a ReceiptResponse object if it's saved into the database
     * @param receipt Receipt object to be validated and then saved
     * @return ReceiptResponse object with the assigned id
     *
     */
    @Transactional
    public ReceiptResponse addNewReceipt(Receipt receipt) {
        receipt.setTotal();
        receiptRepository.save(receipt);
        ReceiptResponse receiptResponse = new ReceiptResponse(receipt.getId());
        return receiptResponse;
    }


    /**
     *
     * Returns a Points object with the total of points based on a set of rules.
     *
     * @param receipt Existing Receipt object to be in search of into the database
     * @return Points object with the total of points
     * @throws ResourceNotFoundException when receipt does not exists on database
     *
     */
    public Points getReceiptPoints(Receipt receipt) {
        if (receiptRepository.existsById(receipt.getId())){
            receipt =  receiptRepository.getReferenceById(receipt.getId());
            return Points.calculatePoints(receipt);
        }else {
            throw new ResourceNotFoundException("Resource not found.");
        }
    }
}
