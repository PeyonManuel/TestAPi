package com.example.encuentro2;

import com.example.encuentro2.handle.MessageErrorHandle;
import com.example.encuentro2.model.Producto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPost.*;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Encuentro2ApplicationTests {

	static Logger logger = LogManager.getLogger(Encuentro2ApplicationTests.class);

	private String url;
	private ObjectMapper mapper = new ObjectMapper();
	private static long comienzoDeLosTests = System.nanoTime();

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@BeforeEach
	public void setUp(){
		url = String.format("http://localhost:%d/api/", port);
	}

	@BeforeEach
	public void antesQueCadaUno() {
		logger.info("Iniciando test");
	}

	@BeforeAll
	public static void antesQueTodos() {
		logger.info("Iniciando tests");
	}

	@AfterAll
	public static void despuesQueTodos() {
		logger.info("Se ejecutaron los tests con una duración de {} ms", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - comienzoDeLosTests));
	}

	@Test
	public void obtenerTodosLosProductos() {
		String urlTest = url + "productos";
		List<Producto> productosResult = this.restTemplate.getForObject(urlTest, List.class);
		Assert.notNull(productosResult, "Lista de productos es nula");
		Assert.notEmpty(productosResult, "Lista de productos es vacia");
	}

	@Test
	public void obtenerProductoPorId() {
		String urlTest = url + "productos/0";
		Producto productoResult = this.restTemplate.getForObject(urlTest, Producto.class);
		Assert.notNull(productoResult, "Producto nulo");
		Assert.isTrue(productoResult.getId() == 0, "Mensaje id bad");
		Assert.isTrue(productoResult.getTitle().equals("Silla"), "Mensaje nombre bad");
	}

	@Test
	public void crearProducto() {
		String urlTest = url + "productos";
		Producto productoPost = this.restTemplate.postForObject(urlTest, (new Producto((long)0, "Mantel", (float) 100, "")) , Producto.class);
		Assert.notNull(productoPost, "Producto nulo");
		Assert.isTrue(productoPost.getId() == 2, "Mensaje id bad");
		Assert.isTrue(productoPost.getTitle().equals("Mantel"), "Mensaje nombre bad");
	}

	@Test
	public void obtenerTodosLosProductosHttp() throws IOException {
		String urlTest = url + "productos";

		HttpUriRequest request = new HttpGet(urlTest);

		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

		String content = EntityUtils.toString((httpResponse.getEntity()));

		List<Producto> productosResult = mapper.readValue(content, List.class);

		Assert.notNull(productosResult, "Lista de productos es nula");
		Assert.notEmpty(productosResult, "Lista de productos es vacia");
	}

	@Test
	public void obtenerProductoPorIdHttp() throws IOException {
		String urlTest = url + "productos/0";

		HttpUriRequest request = new HttpGet(urlTest);

		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

		String content = EntityUtils.toString((httpResponse.getEntity()));

		Producto productoResult = mapper.readValue(content, Producto.class);

		Assert.notNull(productoResult, "Producto nulo");
		Assert.isTrue(productoResult.getId() == 0, "Mensaje id bad");
		Assert.isTrue(productoResult.getTitle().equals("Silla"), "Mensaje nombre bad");
	}

}
