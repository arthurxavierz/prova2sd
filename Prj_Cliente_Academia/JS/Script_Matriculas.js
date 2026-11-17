jQuery.fn.exists = function(){ return this.length > 0; }
var itensPagina = 5;
var contarPagina = 0;
var lstI;
var conteudoCampos;
var serv_porta = ":8080";
var serv_local = "localhost"+serv_porta;
var servico = ['matriculas', 'alunos'];

function salvar()
{
	var url = 'http://'+serv_local+'/api/sistemas/'+servico[0];
	const obj_Item = new Map();
	obj_Item.set('aluno_id', $("#selAluno option:selected").val());
	obj_Item.set('plano', $("#txtPlano").val());
	obj_Item.set('valor', $("#txtValor").val().replace(',', '.'));
	obj_Item.set('data_inicio', $("#txtDataInicio").val());
	obj_Item.set('data_vencimento', $("#txtDataVencimento").val());
	obj_Item.set('status', $("#selStatus option:selected").val());

	if($("#txtId").exists())
	{
		obj_Item.set('id', $("#txtId").val());
	}

	const obj = Object.fromEntries(obj_Item);
	const json = JSON.stringify(obj);
	var xmlHTTP_POST = new XMLHttpRequest();
	xmlHTTP_POST.open("POST", url, false);
	xmlHTTP_POST.setRequestHeader('Content-type', 'application/json');
	xmlHTTP_POST.send(json);
	consultar();
}

function preencherCombo()
{
	var url = 'http://'+serv_local+'/api/sistemas/'+servico[1];
	var xmlHTTP_GET = new XMLHttpRequest();
	var lstA = [];
	xmlHTTP_GET.onreadystatechange = function()
	{
		if(xmlHTTP_GET.readyState == 4 && xmlHTTP_GET.status == 200)
		{
			lstA = JSON.parse(xmlHTTP_GET.responseText);
		}
	}

	xmlHTTP_GET.open("GET", url, false);
	xmlHTTP_GET.send();

	for (var i = 0; i < lstA.length; i++) {
		$("#selAluno").append('<option value="'+lstA[i].id+'">'+lstA[i].nome+'</option>');
	}
}

function editar(indexItem)
{
	if(!$("#txtId").exists())
	{
		conteudoCampos = $('#campoId').html();
		$('#campoId').prepend('<div class="col-sm-3"><label for="txtId">ID:</label><input type="text" class="form-control" id="txtId" name="txtId" value ="'+lstI[indexItem].id+'" disabled></div>')
	}
	$('#txtId').val(lstI[indexItem].id)
	$('#selAluno').val(lstI[indexItem].aluno.id)
	$('#txtPlano').val(lstI[indexItem].plano)
	$('#txtValor').val(lstI[indexItem].valor)
	$('#txtDataInicio').val(lstI[indexItem].data_inicio)
	$('#txtDataVencimento').val(lstI[indexItem].data_vencimento)
	$('#selStatus').val(lstI[indexItem].status)
	$('#btnNovo').prop('disabled', false);
}

function excluir(indexItem)
{
	const obj_Item = new Map();
	obj_Item.set('id', lstI[indexItem].id);

	const obj = Object.fromEntries(obj_Item);
	const json = JSON.stringify(obj);

	var url = 'http://'+serv_local+'/api/sistemas/'+servico[0];
	var xmlHTTP_DELETE = new XMLHttpRequest();
	xmlHTTP_DELETE.open("DELETE", url, false);
	xmlHTTP_DELETE.setRequestHeader('Content-type', 'application/json');
	xmlHTTP_DELETE.send(json);

	consultar();
}

function novo()
{
	if($("#txtId").exists())
	{
		$('#campoId').empty();
		$('#campoId').append(conteudoCampos)
		$('#selAluno option:first').prop('selected', "selected");
		$('#txtPlano').val("");
		$('#txtValor').val("");
		$('#txtDataInicio').val("");
		$('#txtDataVencimento').val("");
		$('#selStatus option:first').prop('selected', "selected");
		$('#btnNovo').prop('disabled', true);
	}
}

function consultar()
{
	var url = 'http://'+serv_local+'/api/sistemas/'+servico[0];
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
		$('#btnProximo').off('click').click(function() {
			if (contarPagina < lstI.length / itensPagina - 1) {
				contarPagina++;
				preencherTabela(lstI);
				botoesConfig(lstI);
			}
		});
		$('#btnAnterior').off('click').click(function() {
			if (contarPagina > 0) {
				contarPagina--;
				preencherTabela(lstI);
				botoesConfig(lstI);
			}
		});
		preencherTabela(lstI);
		botoesConfig(lstI);
	});
}

function preencherTabela(lstI) {
	$('table > tbody > tr').remove();
	var tbody = $('table > tbody');
	for (var i = contarPagina * itensPagina; i < lstI.length && i < (contarPagina + 1) *  itensPagina; i++) {
		tbody.append(
			$('<tr>')
				.append($('<td>').append(lstI[i].aluno.nome))
				.append($('<td>').append(lstI[i].plano))
				.append($('<td>').append('R$ ' + lstI[i].valor.toFixed(2)))
				.append($('<td>').append(formatarData(lstI[i].data_inicio)))
				.append($('<td>').append(formatarData(lstI[i].data_vencimento)))
				.append($('<td>').append(lstI[i].status))
				.append($('<td>').append('<button type="submit" class="btn btn-info" onclick="editar('+i+')">Editar</button>'))
				.append($('<td>').append('<button type="submit" class="btn btn-danger" onclick="excluir('+i+')">Excluir</button>'))
		)
	}
	$('#numeracao').text('Página ' + (contarPagina + 1) + ' de ' + Math.ceil(lstI.length / itensPagina));
}

function botoesConfig(lstI) {
	$('#btnProximo').prop('disabled', lstI.length <= itensPagina || contarPagina > lstI.length / itensPagina - 1);
	$('#btnAnterior').prop('disabled', lstI.length <= itensPagina || contarPagina == 0);
}

function formatarData(data)
{
	var partes = data.substring(0, 10).split('-');
	return partes[2] + '/' + partes[1] + '/' + partes[0];
}
