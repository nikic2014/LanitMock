package com.example.stub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor // Генерирует конструктор с аргументами для всех полей класса
@NoArgsConstructor // Генерирует конструктор без аргументов
@Data
public class Info {
    private String userID;
    private List<Ticker> tickers;
}
