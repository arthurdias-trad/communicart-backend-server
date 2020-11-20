package br.com.communicart.backendserver.exception.handler;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.communicart.backendserver.exception.DataIntegrityException;
import br.com.communicart.backendserver.exception.ObjectNotFoundException;
import br.com.communicart.backendserver.exception.UserNotAuthorizedException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<ResponseError> dataIntegrity(DataIntegrityException exception, HttpServletRequest request) {

		ResponseError responseError = ResponseError.builder().timestamp(System.currentTimeMillis())
				.message(exception.getMessage()).name("Erro de integridade de dados").path(request.getRequestURI())
				.status(HttpStatus.BAD_REQUEST.value()).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
	}

	@ExceptionHandler(UserNotAuthorizedException.class)
	public ResponseEntity<ResponseError> userNotAuthorized(UserNotAuthorizedException exception,
			HttpServletRequest request) {

		ResponseError responseError = ResponseError.builder().timestamp(System.currentTimeMillis())
				.message(exception.getMessage()).name("Erro de autorização").path(request.getRequestURI())
				.status(HttpStatus.UNAUTHORIZED.value()).build();

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseError);
	}

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<ResponseError> objectNotFound(ObjectNotFoundException exception, HttpServletRequest request) {

		ResponseError responseError = ResponseError.builder().timestamp(System.currentTimeMillis())
				.message(exception.getMessage()).name("Objeto não encontrado").path(request.getRequestURI())
				.status(HttpStatus.NOT_FOUND.value()).build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseError);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ResponseError> badCredentials(BadCredentialsException exception, HttpServletRequest request) {
		
		ResponseError responseError = ResponseError.builder()
				.timestamp(System.currentTimeMillis())
				.name("Credenciais incorretas")
				.message("Não foi possível encontrar o usuário ou a senha")
				.path(request.getRequestURI())
				.status(HttpStatus.UNAUTHORIZED.value())
				.build();
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseError);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationResponseError> methodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpServletRequest request) {

		List<FieldError> errors = exception.getBindingResult().getFieldErrors().stream()
				.map(fe -> FieldError.builder().field(fe.getField()).errorMessage(fe.getDefaultMessage()).build())
				.collect(Collectors.toList());

		ValidationResponseError err = ValidationResponseError.builder().timestamp(System.currentTimeMillis())
				.message("Campo(s) inválido(s) em: " + exception.getBindingResult().getObjectName())
				.name("Dados inválidos")
				.path(request.getRequestURI()).status(HttpStatus.BAD_REQUEST.value()).errors(errors).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ResponseError> constraintViolationException(ConstraintViolationException exception,
			HttpServletRequest request) {

		ResponseError responseError = ResponseError.builder().timestamp(System.currentTimeMillis())
				.name("Falha de validação")
				.message("Falha de validação").path(request.getRequestURI())
				.status(HttpStatus.BAD_REQUEST.value()).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
	}
}
