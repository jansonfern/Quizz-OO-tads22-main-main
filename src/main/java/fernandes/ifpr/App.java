package fernandes.ifpr;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private ControladorQuiz controladorQuiz;
    private VBox root;
    private Scene cena;
    private Text enunciado;
    private Button alternativa1;
    private Button alternativa2;
    private Button alternativa3;
    private Button alternativa4;
    private Button alternativa5;
    private Text resultado;
    private Button proxima;
    private Button reiniciar;
    private boolean respostaDada = false; // Controle simplificado

    @Override
    public void init() throws Exception {
        super.init();

    ArrayList<Questao> lista = new ArrayList<>();

        lista.add(new Questao("Qual é a capital de São Paulo?", "São Paulo",
                new String[] { "São Paulo", "Rio de Janeiro", "Brasília", "Belo Horizonte", "Curitiba" }));
        lista.add(new Questao("Qual é a capital do Paraná?", "Curitiba",
                new String[] { "São Paulo", "Curitiba", "Rio de Janeiro", "Belo Horizonte", "Brasília" }));
        lista.add(new Questao("Qual é a capital do Rio de Janeiro?", "Rio de Janeiro",
                new String[] { "São Paulo", "Curitiba", "Rio de Janeiro", "Belo Horizonte", "Brasília" }));
        lista.add(new Questao("Qual é a capital de Minas Gerais?", "Belo Horizonte",
                new String[] { "São Paulo", "Curitiba", "Rio de Janeiro", "Belo Horizonte", "Brasília" }));
        lista.add(new Questao("Qual é a capital do Brasil?", "Brasília",
                new String[] { "São Paulo", "Curitiba", "Rio de Janeiro", "Belo Horizonte", "Brasília" }));

        controladorQuiz = new ControladorQuiz(lista);
    }

    @Override
    public void start(Stage stage) throws Exception {

        inicializaComponentes();
        atualizaComponentes();

        cena = new Scene(root, 500, 500);

        cena.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setScene(cena);
        stage.show();
    }

    private void inicializaComponentes() {

        enunciado = new Text("Enunciado");
        enunciado.getStyleClass().add("enunciado");
        alternativa1 = new Button("Questão 1");
        alternativa2 = new Button("Questão 2");
        alternativa3 = new Button("Questão 3");
        alternativa4 = new Button("Questão 4");
        alternativa5 = new Button("Questão 5");

        alternativa1.setPrefWidth(200);
        alternativa1.getStyleClass().add("botao");
        alternativa1.setTooltip(new Tooltip("Clique para responder..."));

        alternativa2.setPrefWidth(200);
        alternativa3.setPrefWidth(200);
        alternativa4.setPrefWidth(200);
        alternativa5.setPrefWidth(200);

        resultado = new Text("Resultado");
        proxima = new Button("Próxima");
        reiniciar = new Button("Reiniciar");

        root = new VBox();
        root.getChildren().add(enunciado);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10.0);

        root.getChildren().add(alternativa1);
        root.getChildren().add(alternativa2);
        root.getChildren().add(alternativa3);
        root.getChildren().add(alternativa4);
        root.getChildren().add(alternativa5);
        root.getChildren().add(resultado);
        root.getChildren().add(proxima);
        root.getChildren().add(reiniciar);

        alternativa1.setOnAction(respondeQuestao());
        alternativa2.setOnAction(respondeQuestao());
        alternativa3.setOnAction(respondeQuestao());
        alternativa4.setOnAction(respondeQuestao());
        alternativa5.setOnAction(respondeQuestao());
        proxima.setOnAction(proximaQuestao());
        reiniciar.setOnAction(reiniciarQuiz());

        resultado.setVisible(false);
        proxima.setVisible(false); // Inicialmente, o botão "Próxima" está oculto
        reiniciar.setVisible(false);
    }

    public void atualizaComponentes() {

        Questao objQuestao = controladorQuiz.getQuestao();
        if (objQuestao != null) {
            List<String> questoes = objQuestao.getTodasAlternativas(); // Usa List ao invés de ArrayList

            enunciado.setText(objQuestao.getEnunciado());
            alternativa1.setText(questoes.get(0));
            alternativa2.setText(questoes.get(1));
            alternativa3.setText(questoes.get(2));
            alternativa4.setText(questoes.get(3));
            alternativa5.setText(questoes.get(4));

            resultado.setVisible(false);
            proxima.setVisible(respostaDada); // Mostra o botão "Próxima" se uma resposta foi dada
            reiniciar.setVisible(false);
        }
    }

    private EventHandler<ActionEvent> respondeQuestao() {
        return new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Button clicado = (Button) event.getSource();
                String alternativa = clicado.getText();

                boolean result = controladorQuiz.respondeQuestao(alternativa);

                if (result) {
                    resultado.setText("Acertou!!");
                } else {
                    resultado.setText("Errou!!!");
                }

                resultado.setVisible(true);
                respostaDada = true;
                proxima.setVisible(true);
            }
        };
    }

    private EventHandler<ActionEvent> proximaQuestao() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (controladorQuiz.temProximaQuestao()) {
                    controladorQuiz.proximaQuestao();
                    atualizaComponentes();
                } else {
                    resultado.setVisible(true);
                    int acertos = controladorQuiz.getAcertos();
                    int erros = controladorQuiz.getErros();
                    if (acertos >= 3) {
                        resultado.setText("Fim, você ganhou! Acertos: " + acertos + ", Erros: " + erros);
                    } else {
                        resultado.setText("Fim, você perdeu. Acertos: " + acertos + ", Erros: " + erros);
                    }
                    proxima.setVisible(false);
                    reiniciar.setVisible(true);
                }
            }
        };
    }

    private EventHandler<ActionEvent> reiniciarQuiz() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controladorQuiz.reiniciar();
                respostaDada = false; // Reseta o controle de resposta dada
                atualizaComponentes();
                resultado.setVisible(false);
                proxima.setVisible(false); // Oculta o botão "Próxima" após reiniciar
                reiniciar.setVisible(false);
            }
        };
    }

    public static void main(String[] args) {
        launch(args);
    }
}
