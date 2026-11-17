var lstI;
var serv_porta = ":8080";
var serv_local = "localhost"+serv_porta;
var servico = 'alunosacessos';

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
		var acessos = lstI[i].acessos;
		if(acessos.length == 0)
		{
			tbody.append(
				$('<tr>')
					.append($('<td>').append(lstI[i].nome))
					.append($('<td colspan="3">').append('Nenhum acesso registrado'))
			)
		}
		for (var j = 0; j < acessos.length; j++) {
			var linha = $('<tr>');
			if(j == 0)
			{
				linha.append($('<td rowspan="'+acessos.length+'">').append(lstI[i].nome));
			}
			linha.append($('<td>').append(acessos[j].data_hora))
				.append($('<td>').append(acessos[j].tipo))
				.append($('<td>').append(acessos[j].local));
			tbody.append(linha);
		}
	}
}
