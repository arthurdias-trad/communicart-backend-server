package br.com.communicart.backendserver.model.dto;

import java.math.BigDecimal;
import java.net.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.communicart.backendserver.model.entity.ContactFormsAvailableForJob;
import br.com.communicart.backendserver.model.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVagaDto {
	@NotBlank
	private String titleJob;
	@NotNull
	private int typeJob;
	@NotEmpty
	private String description;
	@NotNull
	private BigDecimal price;
	@NotNull
	private PaymentType paymentType;
	private Boolean paymentToNegotiate;
	private URL fileURL;
	@NotNull
	private ContactFormsAvailableForJob contactForms;
	
	private Long paymentDate;
	
}
		