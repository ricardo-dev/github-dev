package com.ricardo.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.api.dto.GithubDto;
import com.ricardo.api.service.DevService;

@RestController
@RequestMapping(value="/desenvolvedores")
public class DevResource {
	
	@Autowired
	private DevService devService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<GithubDto>> getDesenvolvedores(){
		return ResponseEntity.ok(this.devService.getDesenvovedores());
	}

}
