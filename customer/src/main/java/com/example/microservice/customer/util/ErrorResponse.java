package com.example.microservice.customer.util;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors) {
}
