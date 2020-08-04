package sc.senai.br.prova_java.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import sc.senai.br.prova_java.data.AlunoRepository;
import sc.senai.br.prova_java.data.CursoRepository;
import sc.senai.br.prova_java.data.MatriculaRepository;
import sc.senai.br.prova_java.model.Aluno;
import sc.senai.br.prova_java.model.Curso;
import sc.senai.br.prova_java.model.Matricula;
import sc.senai.br.prova_java.service.MatriculaBusiness;

@Model
public class MatriculaController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private MatriculaBusiness matriculaBusiness;

    @Inject
    private MatriculaRepository matriculaRepository;

    @Inject
    private AlunoRepository alunoRepository;

    @Inject
    private CursoRepository cursoRepository;

    @Produces
    @Named
    private Matricula matricula = new Matricula();

    @PostConstruct
    public void initNewMember() {
        matricula = new Matricula();
    }

    public void salvar() throws Exception {
        try {
            if (isMatriculaValida()) {
                matriculaBusiness.salvar(matricula);
                FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo!", "Salvo com sucesso");
                facesContext.addMessage(null, m);
                initNewMember();
            }
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Erro ao salvar.");
            facesContext.addMessage(null, m);
        }
    }

    public boolean isMatriculaValida() {
        return hasVagasDisponiveis() && !isAlunoJaMatriculadoNoCurso();
    }

    public boolean hasVagasDisponiveis() {
        Long numeroMatriculas = matriculaRepository.getnumeroMatriculasByIdCurso(matricula.getCurso().getId());
        if (numeroMatriculas >= matricula.getCurso().getNumeroVagas()) {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!", "Número de vagas no curso excedido");
            facesContext.addMessage(null, m);
            return false;
        }
        return true;
    }
    
    public boolean isAlunoJaMatriculadoNoCurso() {
        List<Matricula> matriculas = matriculaRepository.matriculasByidAlunoAndIdCurso(matricula.getAluno().getId(), matricula.getCurso().getId());
        if (!matriculas.isEmpty()) {
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro!", "Aluno já matriculado no curso");
            facesContext.addMessage(null, m);
            return true;
        }
        return false;
    }

    public void excluir(Matricula matricula) throws Exception {
        try {
            matriculaBusiness.excluir(matricula);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido!", "Removido com sucesso");
            facesContext.addMessage(null, m);
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Erro ao remover.");
            facesContext.addMessage(null, m);
        }
    }

    private String getRootErrorMessage(Exception e) {
        String errorMessage = "Erro ao salvar. Verificar o log.";
        if (e == null) {
            return errorMessage;
        }

        Throwable t = e;
        while (t != null) {
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        return errorMessage;
    }

    public void carregarMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public void aprovarMatricula(Matricula matricula) {
        matriculaBusiness.aprovarMatricula(matricula);
    }

    public List<Matricula> getMatriculasEmAndamento() {
        return matriculaRepository.findMatriculasEmAndamento();
    }

    public List<Aluno> getAllAlunos() {
        return alunoRepository.findAllOrderedByName();
    }

    public List<Curso> getAllCursos() {
        return cursoRepository.findAllOrderedByName();
    }

    public boolean isRenderizarAprovarMatricula(Matricula matricula) {
        return matricula.getSituacao().equals("Em Andamento");
    }

}
