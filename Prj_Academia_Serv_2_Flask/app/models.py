class Acesso:
    @staticmethod
    def to_dict(acesso):
        if acesso.get("_id", "") == '':
            return {
                "aluno_id": int(acesso["aluno_id"]),
                "data_hora": acesso["data_hora"],
                "tipo": acesso["tipo"],
                "local": acesso.get("local", ""),
            }
        else:
            return {
                "id": str(acesso["_id"]),
                "aluno_id": int(acesso["aluno_id"]),
                "data_hora": acesso["data_hora"],
                "tipo": acesso["tipo"],
                "local": acesso.get("local", ""),
            }
