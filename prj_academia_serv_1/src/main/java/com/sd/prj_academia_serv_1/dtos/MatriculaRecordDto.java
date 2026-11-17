package com.sd.prj_academia_serv_1.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MatriculaRecordDto(long id,
		long aluno_id,
		String plano,
		BigDecimal valor,
		LocalDate data_inicio,
		LocalDate data_vencimento,
		String status) {
}
