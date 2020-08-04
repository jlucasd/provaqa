package sc.senai.br.prova_java.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import sc.senai.br.prova_java.model.Matricula;

@RequestScoped
public class MatriculaListProducer {

    @Inject
    private MatriculaRepository matriculaRepository;

    private List<Matricula> matriculas;

    @Produces
    @Named
    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    @Produces
    @Named
    public List<Matricula> getMatriculasEmAndamento() {
        return matriculaRepository.findMatriculasEmAndamento();
    }
    
    @Produces
    @Named
    public List<Matricula> getMatriculasConcluidas() {
        return matriculaRepository.findMatriculasConcluidas();
    }

    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Matricula matricula) {
        retrieveAllMatriculas();
    }

    @PostConstruct
    public void retrieveAllMatriculas() {
        matriculas = matriculaRepository.findAll();
    }

}
