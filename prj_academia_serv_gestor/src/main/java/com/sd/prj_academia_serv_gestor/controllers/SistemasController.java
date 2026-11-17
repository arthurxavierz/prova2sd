package com.sd.prj_academia_serv_gestor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sd.prj_academia_serv_gestor.services.SistemasService;

@RestController
@RequestMapping("/api/sistemas")
public class SistemasController {

	@Autowired
	private SistemasService sistemasService;

	@CrossOrigin(origins = "*")
	@GetMapping("/alunos")
	public ResponseEntity<String> getAlunos()
	{
		return sistemasService.getAlunos();
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/alunos")
	public ResponseEntity<String> salvarAluno(RequestEntity<String> aluno)
	{
		return sistemasService.salvarAluno(aluno);
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping("/alunos")
	public void deletarAluno(RequestEntity<String> aluno)
	{
		sistemasService.deletarAluno(aluno);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/matriculas")
	public ResponseEntity<String> getMatriculas()
	{
		return sistemasService.getMatriculas();
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/matriculas")
	public ResponseEntity<String> salvarMatricula(RequestEntity<String> matricula)
	{
		return sistemasService.salvarMatricula(matricula);
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping("/matriculas")
	public void deletarMatricula(RequestEntity<String> matricula)
	{
		sistemasService.deletarMatricula(matricula);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/acessos")
	public ResponseEntity<String> getAcessos()
	{
		return sistemasService.getAcessos();
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/acessos")
	public ResponseEntity<String> salvarAcesso(RequestEntity<String> acesso)
	{
		return sistemasService.salvarAcesso(acesso);
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping("/acessos")
	public void deletarAcesso(RequestEntity<String> acesso)
	{
		sistemasService.deletarAcesso(acesso);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/alunosacessos")
	public String getAlunosAcessos()
	{
		return sistemasService.getAlunosAcessos();
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/alunosmatriculas")
	public String getAlunosMatriculas()
	{
		return sistemasService.getAlunosMatriculas();
	}
}
