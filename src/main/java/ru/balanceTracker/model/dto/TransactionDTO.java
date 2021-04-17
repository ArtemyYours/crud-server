package ru.balanceTracker.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    private Long sourceId;

    private Long destinationId;

    private Double amount;

    private LocalDateTime transactionDate;

    private String userId;

    private String comment;

}
