package ru.balanceTracker.model.dto;

import lombok.Data;

@Data
public class TransactionAccountDTO {

    private String name;

    private Long icon;

    private String userId;

    private String comment;

    private double deposit;
}
