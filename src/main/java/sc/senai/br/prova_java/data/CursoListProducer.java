package sc.senai.br.prova_java.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import sc.senai.br.prova_java.model.Curso;

@RequestScoped
public class CursoListProducer {
	
	@Inject
    private CursoRepository cursoRepository;
	
	private List<Curso> cursos;

	@Produces
    @Named
    public List<Curso> getCursos() {
        return cursos;
    }
	
	@Produces
    @Named
    public List<Curso> getCursosEmAndamento() {
        return cursoRepository.findAllEmAndamento();
    }
	
	public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Curso curso) {
		retrieveAllCursosOrderedByName();
    }

    @PostConstruct
    public void retrieveAllCursosOrderedByName() {
        cursos = cursoRepository.findAllOrderedByName();
    }
	
}
