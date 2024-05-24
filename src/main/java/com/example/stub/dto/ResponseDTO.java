package com.example.stub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor // Генерирует конструктор с аргументами для всех полей класса
@NoArgsConstructor // Генерирует конструктор без аргументов
@Data
public class ResponseDTO {
    Info info;
    String uuid;
    String lastUpdate;
}
