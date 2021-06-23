package com.ricardo.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ricardo.api.service.exceptionhandler.ApiExternaNotFoundException;
import com.ricardo.api.service.exceptionhandler.EmailJaCadastradoException;
import com.ricardo.api.service.exceptionhandler.SenhaInvalidaException;
import com.ricardo.api.service.exceptionhandler.UsuarioNotFoundException;



@ControllerAdvice
public class DevGitApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(DevGitApiExceptionHandler.class);
	
	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String body = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());

		String messageDevelop = Optional.ofNullable(ex.getCause()).orElse(ex).toString();

		List<Erro> erros = Arrays.asList(new Erro(body, messageDevelop));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.info("Entrou no erro!!");
		List<Erro> erros = this.criarListaErros(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
			WebRequest request) {

		String body = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());

		String messageDevelop = ex.toString();

		List<Erro> erros = Arrays.asList(new Erro(body, messageDevelop));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest request) {
		String body = messageSource.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());

		// causa raiz do erro
		String messageDevelop = ExceptionUtils.getRootCauseMessage(ex);

		List<Erro> erros = Arrays.asList(new Erro(body, messageDevelop));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({ UsuarioNotFoundException.class })
	public ResponseEntity<Object> handleUsuarioNotFoundException(UsuarioNotFoundException ex){
		String body = messageSource.getMessage("usuario.not-found", null, LocaleContextHolder.getLocale());
        String messageDevelop = ex.toString();
        List<Erro> erros = Arrays.asList(new Erro(body, messageDevelop));
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erros);
	}
	
	@ExceptionHandler({ EmailJaCadastradoException.class })
	public ResponseEntity<Object> handleEmailJaCadastradoException(EmailJaCadastradoException ex){
		String body = messageSource.getMessage("email.ja-cadastrado", null, LocaleContextHolder.getLocale());
        String messageDevelop = ex.toString();
        List<Erro> erros = Arrays.asList(new Erro(body, messageDevelop));
    	return ResponseEntity.status(HttpStatus.CONFLICT).body(erros);
	}
	
	@ExceptionHandler({ SenhaInvalidaException.class })
	public ResponseEntity<Object> handleSenhaInvalidaException(SenhaInvalidaException ex){
		String body = messageSource.getMessage("senha.invalida", null, LocaleContextHolder.getLocale());
        String messageDevelop = ex.toString();
        List<Erro> erros = Arrays.asList(new Erro(body, messageDevelop));
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
	}
	
	@ExceptionHandler({ ApiExternaNotFoundException.class })
	public ResponseEntity<Object> handleApiExternaNotFoundException(ApiExternaNotFoundException ex){
		String body = messageSource.getMessage("apiexterna.falha", null, LocaleContextHolder.getLocale());
        String messageDevelop = ex.toString();
        List<Erro> erros = Arrays.asList(new Erro(body, messageDevelop));
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erros);
	}
	
	private List<Erro> criarListaErros(BindingResult result) {
		List<Erro> erros = new ArrayList<>();

		for (FieldError fieldError : result.getFieldErrors()) {
			String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String mensagemDesenvolvedor = fieldError.toString();
			erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		}

		return erros;
	}

	public static class Erro {
		private String mensagemUsuario;
		private String mensagemDesenvolvedor;

		public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
			this.mensagemDesenvolvedor = mensagemDesenvolvedor;
			this.mensagemUsuario = mensagemUsuario;
		}

		public String getMensagemUsuario() {
			return mensagemUsuario;
		}

		public String getMensagemDesenvolvedor() {
			return mensagemDesenvolvedor;
		}
	}
}
