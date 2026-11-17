from flask import Blueprint, request, jsonify
from bson.objectid import ObjectId
from app.database import mongo
from app.models import Acesso

api_bp = Blueprint("api", __name__)


@api_bp.route("/acessos", methods=["POST"])
def create_Acesso():
    data = request.json
    acesso = Acesso.to_dict(data)
    result = mongo.db.acessos.insert_one(acesso)
    return jsonify({"id": str(result.inserted_id), **Acesso.to_dict(data)}), 201

@api_bp.route("/acessos", methods=["GET"])
def get_acessos():
    acessos = mongo.db.acessos.find().sort("data_hora", -1)
    return jsonify([Acesso.to_dict(acesso) for acesso in acessos])

@api_bp.route("/acessos/<string:acesso_id>", methods=["GET"])
def get_Acesso(acesso_id):
    acesso = mongo.db.acessos.find_one({"_id": ObjectId(acesso_id)})
    if acesso:
        return jsonify(Acesso.to_dict(acesso))
    return jsonify({"error": "Acesso not found"}), 404

@api_bp.route("/acessos/aluno/<int:aluno_id>", methods=["GET"])
def get_acessos_Aluno(aluno_id):
    acessos = mongo.db.acessos.find({"aluno_id": aluno_id}).sort("data_hora", -1)
    return jsonify([Acesso.to_dict(acesso) for acesso in acessos])

@api_bp.route("/acessos/<string:acesso_id>", methods=["PUT"])
def update_Acesso(acesso_id):
    data = request.json
    update_data = {"$set": Acesso.to_dict(data)}
    result = mongo.db.acessos.update_one({"_id": ObjectId(acesso_id)}, update_data)

    if result.matched_count:
        updated_Acesso = mongo.db.acessos.find_one({"_id": ObjectId(acesso_id)})
        return jsonify(Acesso.to_dict(updated_Acesso))
    return jsonify({"error": "Acesso not found"}), 404

@api_bp.route("/acessos/<string:acesso_id>", methods=["DELETE"])
def delete_Acesso(acesso_id):
    result = mongo.db.acessos.delete_one({"_id": ObjectId(acesso_id)})
    if result.deleted_count:
        return jsonify({"message": "Acesso deleted successfully"})
    return jsonify({"error": "Acesso not found"}), 404
