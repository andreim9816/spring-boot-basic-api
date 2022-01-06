package com.example.patients.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EntityNotFoundException extends RuntimeException {

    private String entityType;

    private Long entityId;
}
