package sc.senai.br.prova_java.data;

import java.util.*;
import java.lang.String;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import sc.senai.br.prova_java.model.Aluno;

@RequestScoped
public class AlunoListProducer {
	
	@Inject
    private AlunoRepository alunoRepository;
	
	private List<Aluno> alunos;

	@Produces
    @Named
    public List<Aluno> getAlunos() {
        return alunos;
	}
	
	@Produces
	@Named
	public List<Aluno> getAlunosSemCurso() {
	    return alunoRepository.findAllSemCurso();
	}
	
	public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Aluno aluno) {
		retrieveAllPessoasOrderedByName();
    }

    @PostConstruct
    public void retrieveAllPessoasOrderedByName() {
        alunos = alunoRepository.findAllOrderedByName();
    }
    
    public void filtrarAlunos(String nome) {
        alunos = alunoRepository.findAllFilteredByName(nome);
    }
	
}
