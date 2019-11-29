package com.capco.starwars.app.pessoa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.capco.starwars.app.pessoa.service.PessoaService;
import com.capco.starwars.pessoa.api.PessoasApi;
import com.capco.starwars.pessoa.api.model.PeopleBasic;

@Controller
public class PessoasApiController implements PessoasApi {

	@Autowired
	private PessoaService pessoaService;

	public ResponseEntity<List<PeopleBasic>> pessoasListaOrdenadaGet() {

		final List<PeopleBasic> pessoasOrdenado = pessoaService.obterPessoasOrdenadoFilmesNome();

		ResponseEntity<List<PeopleBasic>> responseEntity;
		try {
			responseEntity = new ResponseEntity<>(pessoasOrdenado, HttpStatus.OK);
		} catch (final Exception e) {
			responseEntity = new ResponseEntity<>(pessoasOrdenado, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

}
