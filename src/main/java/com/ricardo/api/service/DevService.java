package com.ricardo.api.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.ricardo.api.dto.GithubDto;
import com.ricardo.api.dto.GithubModel;
import com.ricardo.api.service.exceptionhandler.ApiExternaNotFoundException;

@Service
public class DevService {

	public List<GithubDto> getDesenvovedores() {
		List<GithubModel> ghList = this.getUsers(0);
		ghList.forEach(aux -> {
			System.out.println("ID: "+aux.getId()+" Nome: "+aux.getLogin());
		});
		System.out.println("Tamanho: "+ghList.size());
		return null;
	}
	
	private List<GithubModel> getUsers(int since){
		RestTemplate template = new RestTemplate();
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme("https")
				.host("api.github.com")
				.path("users")
				.query("since="+since)
				.query("per_page="+100)
				.build();
		ResponseEntity<GithubModel[]> gh = template
				.getForEntity(uri.toUriString(), GithubModel[].class);
		if(!gh.getStatusCode().is2xxSuccessful()) {
			throw new ApiExternaNotFoundException();
		}
		
		return Arrays.asList(gh.getBody());
	}
	
	
}
/*
 *public List<BairroModel> getBairro(String uf, String cidadeNome){
		RestTemplate template = new RestTemplate();
		
		//.scheme("https")
		//.host("pepin.app:8081")
		
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme(this.property.getCep().getHttp())
				.host(this.property.getCep().getHost())
				.path("api/bairro/"+uf+"/"+cidadeNome)
				//.query("")
				.build();
		ResponseEntity<BairroModel[]> bairros = template
				.getForEntity(uri.toUriString(), BairroModel[].class);
		if(!bairros.getStatusCode().is2xxSuccessful()) {
			throw new ApiExternaNotFoundException();
		}
		List<BairroModel> l = Arrays.asList(bairros.getBody());
		return l;
	}
	
	public List<LocalidadeModel> getLocalidade(String uf, String nomeCidade){
		RestTemplate template = new RestTemplate();
		
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme(this.property.getCep().getHttp())
				.host(this.property.getCep().getHost())
				.path("api/localidade/"+uf+"/"+nomeCidade)
				//.query("")
				.build();
		ResponseEntity<LocalidadeModel[]> localidades = template
				.getForEntity(uri.toUriString(), LocalidadeModel[].class);
		if(!localidades.getStatusCode().is2xxSuccessful()) {
			throw new ApiExternaNotFoundException();
		}
		List<LocalidadeModel> l = Arrays.asList(localidades.getBody());
		return l;
	}
	
	public List<LogradouroModel> getLogradouro(String uf, String nomeCidade, String nomeRua){
		RestTemplate template = new RestTemplate();
		
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme(this.property.getCep().getHttp())
				.host(this.property.getCep().getHost())
				.path("api/logradoro/"+uf+"/"+nomeCidade+"/"+nomeRua)
				//.query("")
				.build();
		ResponseEntity<LogradouroModel[]> logradoros = template
				.getForEntity(uri.toUriString(), LogradouroModel[].class);
		if(!logradoros.getStatusCode().is2xxSuccessful()) {
			throw new ApiExternaNotFoundException();
		}
		List<LogradouroModel> l = Arrays.asList(logradoros.getBody());
		return l;
	}
	
	public LogradouroModel getLogradouroCep(String cep){
		RestTemplate template = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		
		headers.add("Authorization", "Bearer jashnsakjaksjnakjaksjnksjkasjaksn");
		HttpEntity<HttpHeaders> request = new HttpEntity<HttpHeaders>(headers);
		
		UriComponents uri = UriComponentsBuilder.newInstance()
				.scheme(this.property.getCep().getHttp())
				.host(this.property.getCep().getHost())
				.path("api/logradoro/"+cep)
				//.query("")
				.build();
		ResponseEntity<LogradouroModel> logradoro = template
				.exchange(uri.toUriString(), HttpMethod.GET, request, LogradouroModel.class);
		if(!logradoro.getStatusCode().is2xxSuccessful()) {
			throw new ApiExternaNotFoundException();
		}
		return logradoro.getBody();
	}
 
 * 
 */