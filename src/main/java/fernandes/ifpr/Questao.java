package fernandes.ifpr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Questao {

    private final String enunciado;
    private final String respostaCorreta;
    private final List<String> todasAlternativas;

    public Questao(String enunciado, String respostaCorreta, String[] outrasAlternativas) {
        this.enunciado = enunciado;
        this.respostaCorreta = respostaCorreta;
        this.todasAlternativas = new ArrayList<>();

        for (String alternativa : outrasAlternativas) {
            this.todasAlternativas.add(alternativa);
        }

        if (!this.todasAlternativas.contains(respostaCorreta)) {
            this.todasAlternativas.add(respostaCorreta);
        }

        Collections.shuffle(this.todasAlternativas);
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String getRespostaCorreta() {
        return respostaCorreta;
    }

    public List<String> getTodasAlternativas() {
        return todasAlternativas;
    }
}
