package com.ricardo.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("devgit")
public class DevGitApiProperty {
	
	private String originPermitida = "http://localhost:4200";
	
	private String originPermiridaProducao = "http://localhost:4200";
	
	private final Seguranca seguranca = new Seguranca();
	
	private final Mail mail = new Mail();
	
	private final Token token = new Token();
	
	public String getOriginPermitida() {
		return originPermitida;
	}

	public void setOriginPermitida(String originPermitida) {
		this.originPermitida = originPermitida;
	}

	public String getOriginPermiridaProducao() {
		return originPermiridaProducao;
	}

	public void setOriginPermiridaProducao(String originPermiridaProducao) {
		this.originPermiridaProducao = originPermiridaProducao;
	}

	public Seguranca getSeguranca() {
		return seguranca;
	}

	public Mail getMail() {
		return mail;
	}

	public Token getToken() {
		return token;
	}

	public static class Seguranca {
		private boolean enableHttps; //default false
		
		public boolean isEnableHttps() {
			return enableHttps;
		}

		public void setEnableHttps(boolean enableHttps) {
			this.enableHttps = enableHttps;
		}		
	}
	
	public static class Mail {
		private String host;
		
		private Integer port;
		
		private String username;
		
		private String password;

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public Integer getPort() {
			return port;
		}

		public void setPort(Integer port) {
			this.port = port;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
	
	public static class Token {
		private String segredo;
		
		private int tokenDesktop;
		
		private int tokenRefreshDesktop;

		public String getSegredo() {
			return segredo;
		}

		public void setSegredo(String segredo) {
			this.segredo = segredo;
		}

		public int getTokenDesktop() {
			return tokenDesktop;
		}

		public void setTokenDesktop(int tokenDesktop) {
			this.tokenDesktop = tokenDesktop;
		}

		public int getTokenRefreshDesktop() {
			return tokenRefreshDesktop;
		}

		public void setTokenRefreshDesktop(int tokenRefreshDesktop) {
			this.tokenRefreshDesktop = tokenRefreshDesktop;
		}
	}
}
