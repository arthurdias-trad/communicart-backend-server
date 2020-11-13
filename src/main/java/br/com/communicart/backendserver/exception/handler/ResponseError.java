package br.com.communicart.backendserver.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class ResponseError {

	private Long timestamp;
	private Integer status;
	private String name;
	private String message;
	private String path;
	
}
