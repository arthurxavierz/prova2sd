package com.sd.prj_academia_serv_1.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sd.prj_academia_serv_1.dtos.MatriculaRecordDto;
import com.sd.prj_academia_serv_1.models.Matricula;
import com.sd.prj_academia_serv_1.repositories.AlunoRepository;
import com.sd.prj_academia_serv_1.repositories.MatriculaRepository;

@Service
public class MatriculaService
{
	private final MatriculaRepository matriculaRepository;
	private final AlunoRepository alunoRepository;

	public MatriculaService(MatriculaRepository matriculaRepository, AlunoRepository alunoRepository) {
		this.matriculaRepository = matriculaRepository;
		this.alunoRepository = alunoRepository;
	}

	public List<Matricula> getMatriculas()
	{
		return matriculaRepository.findAll();
	}

	public Matricula getMatricula(Long id)
	{
		return matriculaRepository.findById(id).orElse(null);
	}

	public Matricula salvarMatricula(MatriculaRecordDto matriculaRecordDto)
	{
		Matricula matricula = new Matricula();
		matricula.setId(matriculaRecordDto.id());
		matricula.setAluno(alunoRepository.findById(matriculaRecordDto.aluno_id()).get());
		matricula.setPlano(matriculaRecordDto.plano());
		matricula.setValor(matriculaRecordDto.valor());
		matricula.setData_inicio(matriculaRecordDto.data_inicio());
		matricula.setData_vencimento(matriculaRecordDto.data_vencimento());
		matricula.setStatus(matriculaRecordDto.status());

		return matriculaRepository.save(matricula);
	}

	public void excluirMatricula(Matricula matricula)
	{
		matriculaRepository.deleteById(matricula.getId());
	}
}
