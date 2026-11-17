	package com.sd.prj_academia_serv_gestor.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SistemasService {

	private RestTemplate restTemplate;
	private String url_serv1_alunos;
	private String url_serv1_matriculas;
	private String url_serv2_acessos;

	public SistemasService() {
		restTemplate = new RestTemplate();
		this.url_serv1_alunos = "http://localhost:8081/api/alunos";
		this.url_serv1_matriculas = "http://localhost:8081/api/matriculas";
		this.url_serv2_acessos = "http://localhost:5000/api/acessos";
	}

	private HttpEntity<String> corpo(String json)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<String>(json, headers);
	}

	private ResponseEntity<String> resposta(ResponseEntity<String> response)
	{
		return ResponseEntity.status(response.getStatusCode()).contentType(MediaType.APPLICATION_JSON).body(response.getBody());
	}

	public ResponseEntity<String> getAlunos()
	{
		ResponseEntity<String> response = restTemplate.getForEntity(url_serv1_alunos, String.class);
		return resposta(response);
	}

	public ResponseEntity<String> salvarAluno(RequestEntity<String> aluno)
	{
		ResponseEntity<String> response = restTemplate.postForEntity(url_serv1_alunos, corpo(aluno.getBody()), String.class);
		return resposta(response);
	}

	public void deletarAluno(RequestEntity<String> aluno)
	{
		restTemplate.exchange(url_serv1_alunos, HttpMethod.DELETE, corpo(aluno.getBody()), String.class);
	}

	public ResponseEntity<String> getMatriculas()
	{
		ResponseEntity<String> response = restTemplate.getForEntity(url_serv1_matriculas, String.class);
		return resposta(response);
	}

	public ResponseEntity<String> salvarMatricula(RequestEntity<String> matricula)
	{
		ResponseEntity<String> response = restTemplate.postForEntity(url_serv1_matriculas, corpo(matricula.getBody()), String.class);
		return resposta(response);
	}

	public void deletarMatricula(RequestEntity<String> matricula)
	{
		restTemplate.exchange(url_serv1_matriculas, HttpMethod.DELETE, corpo(matricula.getBody()), String.class);
	}

	public ResponseEntity<String> getAcessos()
	{
		ResponseEntity<String> response = restTemplate.getForEntity(url_serv2_acessos, String.class);
		return resposta(response);
	}

	public ResponseEntity<String> salvarAcesso(RequestEntity<String> acesso)
	{
		ResponseEntity<String> response = null;
		try
		{
			HashMap<String, Object> mapping = new ObjectMapper().readValue(acesso.getBody(), HashMap.class);
			if(mapping.get("id") != null && !mapping.get("id").toString().equals(""))
			{
				response = restTemplate.exchange(url_serv2_acessos + "/" + mapping.get("id"), HttpMethod.PUT, corpo(acesso.getBody()), String.class);
			}
			else
			{
				response = restTemplate.postForEntity(url_serv2_acessos, corpo(acesso.getBody()), String.class);
			}
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return resposta(response);
	}

	public void deletarAcesso(RequestEntity<String> acesso)
	{
		try
		{
			HashMap<String, Object> mapping = new ObjectMapper().readValue(acesso.getBody(), HashMap.class);
			restTemplate.delete(url_serv2_acessos + "/" + mapping.get("id"));
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public String getAlunosAcessos()
	{
		ResponseEntity<String> responseAlunos = restTemplate.getForEntity(url_serv1_alunos, String.class);
		ResponseEntity<String> responseAcessos = restTemplate.getForEntity(url_serv2_acessos, String.class);
		HashMap<String, Object> mappingAlunos[];
		HashMap<String, Object> mappingAcessos[];
		String saida = "";
		try
		{
			mappingAlunos = new ObjectMapper().readValue(responseAlunos.getBody().toString(), HashMap[].class);
			mappingAcessos = new ObjectMapper().readValue(responseAcessos.getBody().toString(), HashMap[].class);

			for (HashMap<String, Object> aluno : mappingAlunos) {
				List<HashMap<String, Object>> acessosAluno = new ArrayList<HashMap<String, Object>>();
				for (HashMap<String, Object> acesso : mappingAcessos) {
					if(acesso.get("aluno_id").toString().equals(aluno.get("id").toString()))
					{
						acessosAluno.add(acesso);
					}
				}
				aluno.put("acessos", acessosAluno);
			}
			saida = new ObjectMapper().writeValueAsString(mappingAlunos);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return saida;
	}

	public String getAlunosMatriculas()
	{
		ResponseEntity<String> responseAlunos = restTemplate.getForEntity(url_serv1_alunos, String.class);
		ResponseEntity<String> responseMatriculas = restTemplate.getForEntity(url_serv1_matriculas, String.class);
		HashMap<String, Object> mappingAlunos[];
		HashMap<String, Object> mappingMatriculas[];
		String saida = "";
		try
		{
			mappingAlunos = new ObjectMapper().readValue(responseAlunos.getBody().toString(), HashMap[].class);
			mappingMatriculas = new ObjectMapper().readValue(responseMatriculas.getBody().toString(), HashMap[].class);

			for (HashMap<String, Object> aluno : mappingAlunos) {
				List<HashMap<String, Object>> matriculasAluno = new ArrayList<HashMap<String, Object>>();
				for (HashMap<String, Object> matricula : mappingMatriculas) {
					HashMap<String, Object> alunoMatricula = (HashMap<String, Object>) matricula.get("aluno");
					if(alunoMatricula.get("id").toString().equals(aluno.get("id").toString()))
					{
						matriculasAluno.add(matricula);
					}
				}
				aluno.put("matriculas", matriculasAluno);
			}
			saida = new ObjectMapper().writeValueAsString(mappingAlunos);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return saida;
	}
}
