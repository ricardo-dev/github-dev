package com.ricardo.api.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.api.dto.NovaSenhaDto;
import com.ricardo.api.dto.UsuarioDto;
import com.ricardo.api.event.RecursoCriadoEvent;
import com.ricardo.api.model.Usuario;
import com.ricardo.api.service.UsuarioService;

@RestController
@RequestMapping(value="/usuario")
public class UsuarioResource {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
    private ApplicationEventPublisher publisher;
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Usuario> salvar(@RequestBody @Valid UsuarioDto dto, HttpServletResponse response){
		Usuario usuario = this.usuarioService.salvar(dto);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, usuario.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public ResponseEntity<Page<Usuario>> getTodos(@RequestParam(required=false, defaultValue="") String nome, 
			@RequestParam("page") int page, @RequestParam("size") int size){
		Page<Usuario> usuarios = this.usuarioService.listarUsuarios(nome, size, page);
		return ResponseEntity.ok(usuarios);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public ResponseEntity<Usuario> porId(@PathVariable("id") Long id){
		return ResponseEntity.ok(this.usuarioService.pegarId(id));
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/perfil")
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('read')")
	public ResponseEntity<Usuario> porToken(){
		return ResponseEntity.ok(this.usuarioService.pegarToken());
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') and #oauth2.hasScope('write')")
	public void atualizarSenha(@RequestBody @Valid NovaSenhaDto dto) {
		this.usuarioService.atualizarSenha(dto);
	}
}
