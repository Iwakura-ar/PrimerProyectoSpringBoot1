package com.hibernate.ferreteria;

import com.hibernate.ferreteria.entity.Articulo;
import com.hibernate.ferreteria.repositorio.Repo_articulos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class FerreteriaApplication implements CommandLineRunner {

	@Autowired //Inyección automatica de dependencias
	private Repo_articulos repo;


	public static void main(String[] args) {
		SpringApplication.run(FerreteriaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Aplicación iniciada correctamente");
		List<Articulo> articulos = repo.findAll(); //para consultar todos los elem
		//Stream, for each y sout para imprimir todos los elem en pantalla
		articulos.stream().forEach(System.out::println);
	}
}
