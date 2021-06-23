package com.ricardo.api.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ricardo.api.dto.NovaSenhaDto;
import com.ricardo.api.dto.UsuarioDto;
import com.ricardo.api.model.Usuario;
import com.ricardo.api.model.enums.Role;
import com.ricardo.api.repository.UsuarioRepository;
import com.ricardo.api.service.exceptionhandler.EmailJaCadastradoException;
import com.ricardo.api.service.exceptionhandler.SenhaInvalidaException;
import com.ricardo.api.service.exceptionhandler.UsuarioNotFoundException;
import com.ricardo.api.utils.PasswordUtils;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario salvar(UsuarioDto dto) {
		this.verificarEmail(dto.getEmail());
		Usuario usuario = this.converterDtoToUsuario(dto);
		return this.usuarioRepository.save(usuario);
	}
	
	public Page<Usuario> listarUsuarios(String nome, int size, int page){
		return this.usuarioRepository.findByNomeContaining(nome, PageRequest.of(page, size));
	}
	
	public Usuario pegarId(Long id) {
		Usuario usuario = this.usuarioRepository.findById(id)
				.orElseThrow(()-> new UsuarioNotFoundException());
		return usuario;
	}
	
	public void atualizarSenha(@Valid NovaSenhaDto dto) {
		Usuario usuario = this.pegarToken();
		if(!PasswordUtils.compare(dto.getSenha(), usuario.getPassword())) {
			throw new SenhaInvalidaException();
		} 
		usuario.setPassword(PasswordUtils.gerarBCrypt(dto.getNovaSenha()));
		this.usuarioRepository.save(usuario);
	}
	
	public Usuario pegarToken() {
		String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<Usuario> usuario = this.usuarioRepository.findByEmail(principal);
		return usuario.get();
	}
	
	private void verificarEmail(String email) {
		Optional<Usuario> usuario = this.usuarioRepository.findByEmail(email);
		if(usuario.isPresent()) {
			throw new EmailJaCadastradoException();
		}
	}
	
	private Usuario converterDtoToUsuario(UsuarioDto dto) {
		Usuario u = new Usuario();
		u.setEmail(dto.getEmail());
		u.setNome(dto.getNome());
		u.setPassword(PasswordUtils.gerarBCrypt(dto.getPassword())); //bcrypt
		u.setRole(Role.ROLE_ADMIN);
		return u;
	}
}
