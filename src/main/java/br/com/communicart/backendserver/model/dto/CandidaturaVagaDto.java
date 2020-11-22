package br.com.communicart.backendserver.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CandidaturaVagaDto {
	private BigDecimal price;
	private LocalDateTime deliveryDate;
	private String observations;
}
