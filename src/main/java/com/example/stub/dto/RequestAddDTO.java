package com.example.stub.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor // Генерирует конструктор с аргументами для всех полей класса
@NoArgsConstructor // Генерирует конструктор без аргументов
@Data
public class RequestAddDTO extends RequestDTO{
    Info info;
    Add add;
    String uuid;
    String lastUpdate;
    public Object getMethod(){
        return add;
    }
}
