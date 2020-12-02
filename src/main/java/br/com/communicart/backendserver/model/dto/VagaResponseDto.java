package br.com.communicart.backendserver.model.dto;

import java.math.BigDecimal;
import java.net.URL;

import br.com.communicart.backendserver.model.entity.ContactFormsAvailableForJob;
import br.com.communicart.backendserver.model.enums.PaymentType;
import br.com.communicart.backendserver.model.enums.StatusVaga;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VagaResponseDto {
	private Long id;
	private Long perfilId;
	private String titleJob;
	private String typeJob;
	private String jobOwner;
	private String email;
	private String description;
	private BigDecimal price;
	private PaymentType paymentType;
	private Boolean paymentToNegotiate;
	private ContactFormsAvailableForJob contactForms;
	private StatusVaga statusVaga;
	private URL fileURL;
	private String paymentDate;
	private PerfilResponseDTO selectedFreelancer;
}
	
	
