package com.FordCare.API.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@JsonPropertyOrder({ "data", "km" })
@Data
public class RegistroManutencao {
   private LocalDate data;
   private Integer km;
}