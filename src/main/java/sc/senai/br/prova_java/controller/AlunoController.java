package sc.senai.br.prova_java.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import sc.senai.br.prova_java.data.AlunoListProducer;
import sc.senai.br.prova_java.data.MatriculaRepository;
import sc.senai.br.prova_java.model.Aluno;
import sc.senai.br.prova_java.model.Matricula;
import sc.senai.br.prova_java.service.AlunoBusiness;

@Model
public class AlunoController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private AlunoBusiness alunoBusiness;
    
    @Inject
    private AlunoListProducer alunoListProducer;

    @Inject
    private MatriculaRepository matriculaRepository;
    
    @Produces
    @Named
    private Aluno aluno = new Aluno();

    private String alunoBusca;

    @PostConstruct
    public void initNewMember() {
        aluno = new Aluno();
    }

    public void salvar() throws Exception {
        try {
            alunoBusiness.salvar(aluno);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo!", "Salvo com sucesso");
            facesContext.addMessage(null, m);
            initNewMember();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Erro ao salvar.");
            facesContext.addMessage(null, m);
        }
    }

    public void excluir(Aluno aluno) throws Exception {
        try {
            if (!isAlunoJaMatriculado(aluno)) {            
                alunoBusiness.excluir(aluno);
                FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Removido!", "Removido com sucesso");
                facesContext.addMessage(null, m);
            } else {
                FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Não removido!", "O Possui uma matrícula. O mesmo não pode ser removido.");
                facesContext.addMessage(null, m);
            }
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Erro ao remover.");
            facesContext.addMessage(null, m);
        }
    }
    
    private boolean isAlunoJaMatriculado(Aluno aluno) {
        List<Matricula> matriculasAluno = matriculaRepository.matriculasByAluno(aluno.getId());
        return !matriculasAluno.isEmpty();
    }
    
    public void filtrarAlunos() {
        alunoListProducer.filtrarAlunos(alunoBusca);
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

    public void carregarPessoa(Aluno aluno) {
        this.aluno = aluno;
    }

    public String getAlunoBusca() {
        return alunoBusca;
    }

    public void setAlunoBusca(String alunoBusca) {
        this.alunoBusca = alunoBusca;
    }

}
