package br.com.sistemaescola.sistemaEscola.repository;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import br.com.sistemaescola.sistemaEscola.codecs.AlunoCodec;
import br.com.sistemaescola.sistemaEscola.models.Aluno;

@Repository
public class AlunoRepository {
	
	private MongoClient cliente;
	private MongoDatabase bancoDeDados; 
	
	private void criarConexao() {
		  Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
		  AlunoCodec alunoCodec = new AlunoCodec(codec);
		  CodecRegistry registro = CodecRegistries.fromRegistries(
		  MongoClient.getDefaultCodecRegistry(), 
		  CodecRegistries.fromCodecs(alunoCodec));
		  MongoClientOptions options = MongoClientOptions.builder().codecRegistry(registro).build();
		  this.cliente = new MongoClient("localhost:27017", options);
		  this.bancoDeDados = cliente.getDatabase("config");
	}
	
	public void salvar(Aluno aluno) {		
		  criarConexao();
		  MongoCollection<Aluno> alunos = this.bancoDeDados.getCollection("alunos", Aluno.class);
		  alunos.insertOne(aluno);
		  cliente.close();
	}

	
	
	public List<Aluno> obterTodos(){
		  criarConexao();
		  MongoCollection<Aluno> alunos = this.bancoDeDados.getCollection("alunos", Aluno.class);		  
		  MongoCursor<Aluno> resultado= alunos.find().iterator();
		  List<Aluno> alunosEncontrados = new ArrayList<>();
		  
		  while(resultado.hasNext()) {
			  Aluno aluno = resultado.next();
			  alunosEncontrados.add(aluno);
		  }
		  
		  cliente.close();	
		  return alunosEncontrados;		
	}
	
	
	public Aluno buscarAluno(String id) {
		criarConexao();
		MongoCollection<Aluno> alunos = this.bancoDeDados.getCollection("alunos", Aluno.class);
		Aluno aluno = alunos.find(Filters.eq("_id", new ObjectId(id))).first();
		cliente.close();	
		return aluno;
		
	}

}
