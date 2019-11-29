package com.capco.starwars.app.pessoa.integration;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.swapi.api.model.PeopleResult;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Service
public class SwapiIntegration {

	// @Value("${endpoint}") // TODO incluir em properties
	private String endpoint = "https://swapi.co/api/";

	// @Value("${timeout}") TODO incluir em properties
	private Integer timeout = 2;

	// @Value("${useragent}") TODO incluir em properties
	private String useragent = "starwars-pessoa-app";

	/**
	 * WebClient
	 */
	private WebClient webClient;

	private static final String PATH = "/people/?page={page}";

	@PostConstruct
	public void init() {

		final HttpClient httpClient = HttpClient.create()
				.tcpConfiguration(client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout * 1000))
				.tcpConfiguration(
						client -> client.doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(timeout))
								.addHandlerLast(new WriteTimeoutHandler(timeout))));

		final ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

		webClient = WebClient.builder().baseUrl(endpoint).clientConnector(connector)
				.defaultHeader(HttpHeaders.USER_AGENT, useragent).build();
	}

	public PeopleResult obterPessoas(final Integer page) {

		// TODO implementar tratamento de erro

		return webClient.get().uri(PATH, page).retrieve().bodyToMono(PeopleResult.class).block();
	}
}
