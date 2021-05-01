package br.com.sistemaescola.sistemaEscola.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.sistemaescola.sistemaEscola.models.Aluno;
import br.com.sistemaescola.sistemaEscola.models.Habilidade;
import br.com.sistemaescola.sistemaEscola.repository.AlunoRepository;

@Controller
public class HabilidadesController {
	
	@Autowired
	private AlunoRepository alunoRepository;

	@GetMapping("/habilidade/cadastrar/{id}")
	public String salvar(@PathVariable String id, Model model) {
	    Aluno aluno = alunoRepository.buscarAluno(id);
		model.addAttribute("aluno",aluno);
		model.addAttribute("habilidade", new Habilidade());
		return "habilidade/cadastrar";
	}
	
}
