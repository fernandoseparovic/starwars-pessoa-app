package com.capco.starwars.comparator;

import java.util.Comparator;

import com.capco.starwars.pessoa.api.model.PeopleBasic;

/**
 * Compara por quantidade por maior numero de filmes e como segundo criterio
 * ordem alfabética do nome da Pessoa
 * 
 * @author Fernando Separovic
 *
 */
public class QuantidadeFilmesNomeComparator implements Comparator<PeopleBasic> {

	@Override
	public int compare(final PeopleBasic o1, final PeopleBasic o2) {

		// Maior para o menor
		final Integer compareQuantidadeFilmes = getQuantidadeFilmes(o2).compareTo(getQuantidadeFilmes(o1));
		
		// ordem alfabetica
		final Integer compareNames = getName(o1).compareTo(getName(o2));

		Integer resultCompare = compareQuantidadeFilmes;
		if (resultCompare.equals(0)) {
			resultCompare = compareNames;
		}

		return resultCompare;
	}

	/**
	 * Retorna o a quantidade de filmes de {@link PeopleBasic} caso a lista seja
	 * nula retorna 0
	 * 
	 * @param peopleBasic {@link PeopleBasic}
	 * @return quantidade de filmes de {@link PeopleBasic}
	 */
	private Integer getQuantidadeFilmes(final PeopleBasic peopleBasic) {
		Integer quantidadeFilmes = 0;
		if (peopleBasic.getFilms() != null) {
			quantidadeFilmes = peopleBasic.getFilms().size();
		}
		return quantidadeFilmes;
	}

	/**
	 * Retorna o atributo Name de {@link PeopleBasic} caso nulo retorna string vazia
	 * 
	 * @param peopleBasic {@link PeopleBasic}
	 * @return atributo Name de {@link PeopleBasic}
	 */
	private String getName(final PeopleBasic peopleBasic) {
		String name = peopleBasic.getName();
		if (name == null) {
			name = "";
		}
		return name;
	}

}
