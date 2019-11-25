package com.capco.starwars.app.pessoa.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.capco.starwars.pessoa.api.PessoasApi;
import com.capco.starwars.pessoa.api.model.PeopleType;

@Controller
public class PessoasApiController implements PessoasApi {

	@Inject

	public ResponseEntity<List<PeopleType>> pessoasListaGet() {
		// do some magic!
		return new ResponseEntity<List<PeopleType>>(HttpStatus.OK);
	}

}
