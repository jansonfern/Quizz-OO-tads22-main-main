package fernandes.ifpr;

import java.util.ArrayList;
import java.util.Collections;

public class Questao {

    private final String enunciado;
    private final String respostaCorreta;
    private final ArrayList<String> todasAlternativas;

    public Questao(String enunciado, String respostaCorreta, String[] outrasAlternativas) {
        this.enunciado = enunciado;
        this.respostaCorreta = respostaCorreta;
        this.todasAlternativas = new ArrayList<>();

        for (String alternativa : outrasAlternativas) {
            this.todasAlternativas.add(alternativa);
        }
        this.todasAlternativas.add(respostaCorreta);

        // Embaralha a lista para garantir aleatoriedade
        Collections.shuffle(this.todasAlternativas);
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String getRespostaCorreta() {
        return respostaCorreta;
    }

    public ArrayList<String> getTodasAlternativas() {
        return todasAlternativas;
    }
}
