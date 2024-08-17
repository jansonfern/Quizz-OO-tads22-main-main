package fernandes.ifpr;

import java.util.ArrayList;
import java.util.Collections;

public class ControladorQuiz {

    private ArrayList<Questao> questoes;
    private int questaoAtual;
    private int acertos;
    private int erros;

    public ControladorQuiz(ArrayList<Questao> questoes) {
        this.questoes = questoes;
        reiniciar();
    }

    public void reiniciar() {
        Collections.shuffle(this.questoes);
        this.acertos = 0;
        this.questaoAtual = 0;
        this.erros = 0;
    }

    public int getTotalQuestao() {
        return this.questoes.size();
    }

    public boolean temProximaQuestao() {
        return questaoAtual < getTotalQuestao();
    }

    public int getQuestaoAtual() {
        return this.questaoAtual;
    }

    public Questao getQuestao() {
        if (temProximaQuestao()) {
            return this.questoes.get(questaoAtual);
        }
        return null;
    }

    public boolean respondeQuestao(String alternativa) {
        if (getQuestao() != null && getQuestao().getRespostaCorreta().equals(alternativa)) {
            acertos++;
            return true;
        } else {
            erros++;
            return false;
        }
    }

    public int getAcertos() {
        return this.acertos;
    }

    public int getErros() {
        return this.erros;
    }

    public void proximaQuestao() {
        if (temProximaQuestao()) {
            questaoAtual++;
        }
    }
}
