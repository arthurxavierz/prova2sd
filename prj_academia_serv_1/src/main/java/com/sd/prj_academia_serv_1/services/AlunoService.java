package com.sd.prj_academia_serv_1.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sd.prj_academia_serv_1.models.Aluno;
import com.sd.prj_academia_serv_1.repositories.AlunoRepository;

@Service
public class AlunoService
{
	private final AlunoRepository alunoRepository;

	public AlunoService(AlunoRepository alunoRepository) {
		this.alunoRepository = alunoRepository;
	}

	public List<Aluno> getAlunos()
	{
		return alunoRepository.findAll();
	}

	public Aluno getAluno(Long id)
	{
		return alunoRepository.findById(id).orElse(null);
	}

	public Aluno salvarAluno(Aluno aluno)
	{
		if(aluno.getData_cadastro() == null)
		{
			aluno.setData_cadastro(LocalDateTime.now());
		}
		return alunoRepository.save(aluno);
	}

	public void excluirAluno(Aluno aluno)
	{
		alunoRepository.deleteById(aluno.getId());
	}
}
