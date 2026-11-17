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

import com.sd.prj_academia_serv_1.dtos.MatriculaRecordDto;
import com.sd.prj_academia_serv_1.models.Matricula;
import com.sd.prj_academia_serv_1.services.MatriculaService;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController
{
	@Autowired
	private MatriculaService matriculaService;

	@CrossOrigin(origins = "*")
	@GetMapping
	public List<Matricula> getMatriculas()
	{
		return matriculaService.getMatriculas();
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/{id}")
	public Matricula getMatricula(@PathVariable Long id)
	{
		return matriculaService.getMatricula(id);
	}

	@CrossOrigin(origins = "*")
	@PostMapping
	public Matricula salvarMatricula(@RequestBody MatriculaRecordDto matriculaRecordDto)
	{
		return matriculaService.salvarMatricula(matriculaRecordDto);
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping
	public void deletarMatricula(@RequestBody Matricula matricula)
	{
		matriculaService.excluirMatricula(matricula);
	}
}
