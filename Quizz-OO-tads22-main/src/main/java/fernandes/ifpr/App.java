package fernandes.ifpr;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private ControladorQuiz controladorQuiz; // Controlador do quiz para gerenciar questões e respostas
    private Text resultado;
    private VBox root; // Layout principal
    private Scene cena; // Cena da aplicação
    private Button alternativa1;
    private Button alternativa2;
    private Button alternativa3;
    private Button alternativa4;
    private Button alternativa5;
    private Button proxQuestao; // Botão para avançar para a próxima questão
    private Button reiniciarQuiz; // Botão para reiniciar o quiz

    @Override
    public void init() throws Exception {
        super.init();

        // Lista de questões com exatamente 5 questões
        ArrayList<Questao> lista = new ArrayList<>();
        lista.add(new Questao("Qual a capital de Sao Paulo?", "Sao Paulo",
                new String[] { "Sao Paulo", "Manaus", "Curitiba", "Recife", "Florianopolis" }));
        lista.add(new Questao("Qual a capital de Amazonas?", "Manaus",
                new String[] { "Alagoas", "Curitiba", "São Paulo", "Manaus", "Florianopolis" }));
        lista.add(new Questao("Qual a capital do Paraná?", "Curitiba",
                new String[] { "Porto Alegre", "Alagoas", "Curitiba", "Florianopolis" }));
        lista.add(new Questao("Qual a capital de Pernambuco?", "Recife",
                new String[] { "Rio de Janeiro", "Curitiba", "Porto Alegre", "Recife", "Salvador" }));
        lista.add(new Questao("Qual a capital do Rio de Janeiro?", "Rio de Janeiro",
                new String[] { "Rio de Janeiro", "Curitiba", "Porto Alegre", "Florianopolis" }));

        // Instancia o ControladorQuiz com a lista de questões
        controladorQuiz = new ControladorQuiz(lista);
    }

    @Override
    public void start(Stage stage) throws Exception {
        root = new VBox(); // Instancia o VBox
        resultado = new Text("Resultado");

        root.setAlignment(Pos.CENTER); // Alinha no centro
        root.setSpacing(10.0); // Define o espaçamento

        configurarBotoes();
        atualizarQuestao();

        cena = new Scene(root, 800, 800); // tamanho do layout
        cena.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setScene(cena);
        stage.setTitle("Quiz App"); // Define o título da janela
        stage.show();
    }

    private void configurarBotoes() {
        // Configura os botões de resposta
        alternativa1 = new Button();
        alternativa2 = new Button();
        alternativa3 = new Button();
        alternativa4 = new Button();
        alternativa5 = new Button();

        root.getChildren().addAll(alternativa1, alternativa2, alternativa3, alternativa4, alternativa5);

        alternativa1.setOnAction(this::respondeQuestao);
        alternativa2.setOnAction(this::respondeQuestao);
        alternativa3.setOnAction(this::respondeQuestao);
        alternativa4.setOnAction(this::respondeQuestao);
        alternativa5.setOnAction(this::respondeQuestao);

        // Botão para avançar para a próxima questão
        proxQuestao = new Button("Próxima Questão");
        proxQuestao.getStyleClass().add("prox-questao");
        proxQuestao.setOnAction(this::carregarProximaQuestao);

        // Botão para reiniciar o quiz
        reiniciarQuiz = new Button("Reiniciar Quiz");
        reiniciarQuiz.getStyleClass().add("reiniciar-quiz");
        reiniciarQuiz.setOnAction(this::reiniciarQuiz);

        // Adiciona todos os botões e o resultado
        root.getChildren().addAll(proxQuestao, reiniciarQuiz, resultado);
    }

    private void atualizarQuestao() {
        if (controladorQuiz.temProximaQuestao()) {
            // Questão atual do controlador
            Questao questao = controladorQuiz.getQuestao();

            // Remove o texto repetido
            root.getChildren().removeIf(node -> node instanceof Text && node != resultado);

            // Adiciona o novo enunciado da questão
            Text enunciado = new Text(questao.getEnunciado());
            enunciado.getStyleClass().add("enunciado-texto");
            root.getChildren().add(0, enunciado);

            // Define o texto dos botões de resposta com as alternativas da questão
            alternativa1.setText(questao.getTodasAlternativas().get(0));
            alternativa2.setText(questao.getTodasAlternativas().get(1));
            alternativa3.setText(questao.getTodasAlternativas().get(2));
            alternativa4.setText(questao.getTodasAlternativas().get(3));
            alternativa5.setText(questao.getTodasAlternativas().get(4));
            resultado.setText(""); // Limpa o resultado ao carregar nova questão
        } else {
            // Mensagem final ao término do quiz
            int acertos = controladorQuiz.getAcertos();
            int erros = controladorQuiz.getErros();
            if (acertos >= 3) {
                resultado.setText("Parabéns, você ganhou! Acertos: " + acertos + ", Erros: " + erros);
            } else {
                resultado.setText("Fim, você perdeu! Acertos: " + acertos + ", Erros: " + erros);
            }
            resultado.getStyleClass().add("resultado"); // Aplica estilo ao resultado
            proxQuestao.setDisable(true); // Desativa o botão de próxima questão ao final
        }
    }

    private void carregarProximaQuestao(ActionEvent event) {
        if (controladorQuiz.temProximaQuestao()) {
            // Avança para a próxima questão e atualiza a interface
            controladorQuiz.proximaQuestao();
            atualizarQuestao();
        } else {
            // Se não há mais questões, exibe a mensagem final
            int acertos = controladorQuiz.getAcertos();
            int erros = controladorQuiz.getErros();
            if (acertos >= 3) {
                resultado.setText("Parabéns, você ganhou! Acertos: " + acertos + ", Erros: " + erros);
            } else {
                resultado.setText("Fim, você perdeu! Acertos: " + acertos + ", Erros: " + erros);
            }
            resultado.getStyleClass().add("resultado"); // Aplica estilo ao resultado
            proxQuestao.setDisable(true); // Desativa o botão de próxima questão ao final
        }
    }

    private void respondeQuestao(ActionEvent event) {
        // Obtém o botão que foi clicado
        Button source = (Button) event.getSource();
        // Verifica se a resposta está correta
        boolean correta = controladorQuiz.respondeQuestao(source.getText());

        // Atualiza o feedback com base na resposta
        if (correta) {
            resultado.setText("Resposta correta!");
        } else {
            resultado.setText("Resposta errada!");
        }
    }

    private void reiniciarQuiz(ActionEvent event) {
        // Reinicia o quiz e atualiza a interface
        controladorQuiz.reiniciar();
        atualizarQuestao();
        resultado.setText(""); // Limpa o feedback ao reiniciar o quiz
        proxQuestao.setDisable(false); // Reativa o botão de próxima questão
    }

    public static void main(String[] args) {
        launch(args);
    }
}
