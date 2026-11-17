jQuery.fn.exists = function(){ return this.length > 0; }
var itensPagina = 5;
var contarPagina = 0;
var lstI;
var conteudoCampos;
var dataCadastro;
var serv_porta = ":8080";
var serv_local = "localhost"+serv_porta;
var servico = 'alunos';

function salvar()
{
	var url = 'http://'+serv_local+'/api/sistemas/'+servico;
	const obj_Item = new Map();
	obj_Item.set('nome', $("#txtNome").val());
	obj_Item.set('cpf', $("#txtCpf").val());
	obj_Item.set('email', $("#txtEmail").val());
	obj_Item.set('telefone', $("#txtTelefone").val());

	if($("#txtId").exists())
	{
		obj_Item.set('id', $("#txtId").val());
		obj_Item.set('data_cadastro', dataCadastro);
	}

	const obj = Object.fromEntries(obj_Item);
	const json = JSON.stringify(obj);
	var xmlHTTP_POST = new XMLHttpRequest();
	xmlHTTP_POST.open("POST", url, false);
	xmlHTTP_POST.setRequestHeader('Content-type', 'application/json');
	xmlHTTP_POST.send(json);
	consultar();
}

function editar(indexItem)
{
	if(!$("#txtId").exists())
	{
		conteudoCampos = $('#campoId').html();
		$('#campoId').prepend('<div class="col-sm-3"><label for="txtId">ID:</label><input type="text" class="form-control" id="txtId" name="txtId" value ="'+lstI[indexItem].id+'" disabled></div>')
	}
	$('#txtId').val(lstI[indexItem].id)
	$('#txtNome').val(lstI[indexItem].nome)
	$('#txtCpf').val(lstI[indexItem].cpf)
	$('#txtEmail').val(lstI[indexItem].email)
	$('#txtTelefone').val(lstI[indexItem].telefone)
	dataCadastro = lstI[indexItem].data_cadastro;
	$('#btnNovo').prop('disabled', false);
}

function excluir(indexItem)
{
	const obj_Item = new Map();
	obj_Item.set('id', lstI[indexItem].id);

	const obj = Object.fromEntries(obj_Item);
	const json = JSON.stringify(obj);

	var url = 'http://'+serv_local+'/api/sistemas/'+servico;
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
		$('#txtNome').val("");
		$('#txtCpf').val("");
		$('#txtEmail').val("");
		$('#txtTelefone').val("");
		$('#btnNovo').prop('disabled', true);
	}
}

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
		else if(xmlHTTP_GET.readyState == 4)
		{
			alert(xmlHTTP_GET.responseText);
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
				.append($('<td>').append(lstI[i].nome))
				.append($('<td>').append(lstI[i].cpf))
				.append($('<td>').append(lstI[i].email))
				.append($('<td>').append(lstI[i].telefone))
				.append($('<td>').append(formatarData(lstI[i].data_cadastro)))
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
