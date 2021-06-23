package com.ricardo.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(PasswordUtils.class);
	
	public PasswordUtils() {
			
	}
	
	public static String gerarBCrypt(String senha) {
		if(senha == null)
			return senha;
		
		log.info("Gerando Hash com BCrypt");
		BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
		String s = bCryptEncoder.encode(senha);
		log.info(s);
		return bCryptEncoder.encode(senha);
	}
	
	public static boolean compare(String senha, String hashSenha) {
		BCryptPasswordEncoder bCryptEncoder = new BCryptPasswordEncoder();
		return bCryptEncoder.matches(senha, hashSenha);
	}

}
