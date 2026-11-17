package com.sd.prj_academia_serv_1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sd.prj_academia_serv_1.models.Aluno;
import com.sd.prj_academia_serv_1.services.AlunoService;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController
{
	@Autowired
	private AlunoService alunoService;

	@CrossOrigin(origins = "*")
	@GetMapping
	public List<Aluno> getAlunos()
	{
		return alunoService.getAlunos();
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/{id}")
	public Aluno getAluno(@PathVariable Long id)
	{
		return alunoService.getAluno(id);
	}

	@CrossOrigin(origins = "*")
	@PostMapping
	public Aluno salvarAluno(@RequestBody Aluno aluno)
	{
		return alunoService.salvarAluno(aluno);
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping
	public void deletarAluno(@RequestBody Aluno aluno)
	{
		alunoService.excluirAluno(aluno);
	}
}
