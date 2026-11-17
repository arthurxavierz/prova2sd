var lstI;
var serv_porta = ":8080";
var serv_local = "localhost"+serv_porta;
var servico = 'alunosmatriculas';

function consultar()
{
	var url = 'http://'+serv_local+'/api/sistemas/'+servico;
	var xmlHTTP_GET = new XMLHttpRequest();
	xmlHTTP_GET.onreadystatechange = function()
	{
		if(xmlHTTP_GET.readyState == 4 && xmlHTTP_GET.status == 200)
		{
			lstI = JSON.parse(xmlHTTP_GET.responseText);
		}
	}

	xmlHTTP_GET.open("GET", url, false);
	xmlHTTP_GET.send();

	$(function() {
		preencherTabela(lstI);
	});
}

function preencherTabela(lstI) {
	$('table > tbody > tr').remove();
	var tbody = $('table > tbody');
	for (var i = 0; i < lstI.length; i++) {
		var matriculas = lstI[i].matriculas;
		if(matriculas.length == 0)
		{
			tbody.append(
				$('<tr>')
					.append($('<td>').append(lstI[i].nome))
					.append($('<td>').append(lstI[i].cpf))
					.append($('<td colspan="5">').append('Nenhuma matrícula'))
			)
		}
		for (var j = 0; j < matriculas.length; j++) {
			var linha = $('<tr>');
			if(j == 0)
			{
				linha.append($('<td rowspan="'+matriculas.length+'">').append(lstI[i].nome));
				linha.append($('<td rowspan="'+matriculas.length+'">').append(lstI[i].cpf));
			}
			linha.append($('<td>').append(matriculas[j].plano))
				.append($('<td>').append('R$ ' + matriculas[j].valor.toFixed(2)))
				.append($('<td>').append(formatarData(matriculas[j].data_inicio)))
				.append($('<td>').append(formatarData(matriculas[j].data_vencimento)))
				.append($('<td>').append(matriculas[j].status));
			tbody.append(linha);
		}
	}
}

function formatarData(data)
{
	var partes = data.substring(0, 10).split('-');
	return partes[2] + '/' + partes[1] + '/' + partes[0];
}
