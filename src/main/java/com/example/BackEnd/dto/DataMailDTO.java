package com.example.BackEnd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Objects;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataMailDTO {
    private String to;
    private String subject;
    private String content;
    private Map<String, Object> props;
}
