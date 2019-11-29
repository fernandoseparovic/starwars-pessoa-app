package com.capco.starwars.app.pessoa.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.capco.starwars.app.pessoa.integration.SwapiIntegration;
import com.capco.starwars.comparator.QuantidadeFilmesNomeComparator;
import com.capco.starwars.pessoa.api.model.PeopleBasic;
import com.swapi.api.model.People;
import com.swapi.api.model.PeopleResult;

@Service
public class PessoaService {

	private static final Integer MAX_PAGES = 10;

	@Autowired
	private SwapiIntegration swapiIntegration;

	// TODO verificar possibilidade de utilização de cache
	/**
	 * Ordena os personagens por quantidade de filmes que aparecem, usando o nome do
	 * personagem como segundo critério de ordenação (ordem alfabética).
	 * 
	 * @return {@link List} {@link PeopleBasic}
	 */
	public List<PeopleBasic> obterPessoasOrdenadoFilmesNome() {
		
		// TODO implementar tratamento de erro
		
		final List<PeopleBasic> peopleBasicList = new ArrayList<>();
		final List<People> peopleList = obterTodasPessoas();

		for (final People people : peopleList) {
			final ModelMapper modelMapper = new ModelMapper();
			final PeopleBasic peopleBasic = modelMapper.map(people, PeopleBasic.class);
			peopleBasicList.add(peopleBasic);
		}

		Collections.sort(peopleBasicList, new QuantidadeFilmesNomeComparator());

		return peopleBasicList;
	}

	// TODO verificar possibilidade de utilização de cache
	/**
	 * Obtem todas as pessoas disponiveis no serviço swapi, utiliza MAX_PAGES como
	 * quantidade maxima de chamadas ao serviço
	 *
	 * TODO verificar regra de negocio para averiguar se faz sentido ter uma
	 * quantidade Maxima de paginas
	 * 
	 * @return {@link List} {@link People}
	 */
	private List<People> obterTodasPessoas() {
		
		final List<People> peopleList = new ArrayList<>();

		for (int page = 1; page <= MAX_PAGES; page++) {
			final PeopleResult peopleResult = swapiIntegration.obterPessoas(page);

			if (peopleResult != null) {
				peopleList.addAll(peopleResult.getResults());
			}

			if (StringUtils.isEmpty(peopleResult.getNext())) {
				break;
			}
		}

		return peopleList;
	}
}
