package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.ProbabilidadeAdocao;
import br.com.alura.adopet.api.model.TipoPet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculadoraProbabilidadeAdocaoTest {

    /*
     *  Triple A e o GWT, são formas de organizar o código dos cenários de teste com objetivo de tornar os testes mais
     *  legíveis e fáceis de entender.
     *
     * # Triple A
     * - ARRANGE
     *  Nesta etapa, são realizadas todas as configurações iniciais necessárias para que o cenário de teste possa ser
     *  executado. Isso pode incluir a criação de objetos, definição de variáveis, configuração de ambiente e qualquer
     *  outra preparação necessária para que o teste seja executado em um estado específico.
     *
     * - ACT
     *  Nesta fase, a ação que se deseja testar é executada. Pode ser a chamada de um método, uma interação com a
     *  interface do usuário ou qualquer outra operação que seja o foco do teste.
     *
     * - ASSERT
     *  Na última etapa, os resultados são verificados em relação ao comportamento esperado. É onde se avalia se o
     *  resultado obtido após a ação está de acordo com o que se esperava do teste. Caso haja alguma discrepância
     *  entre o resultado real e o esperado, o teste falhará.
     *
     * # GWT ou BDD
     *
     * - GIVEN
     *  Nesta etapa, é descrito o cenário inicial ou o contexto do teste. São definidas as condições iniciais
     *  necessárias para a execução do cenário de teste.
     *
     * - WHEN
     *  Aqui, a ação específica que está sendo testada é executada. É a etapa em que a ação do usuário ou do
     *  sistema acontece.
     *
     * - THEN
     *  Nesta etapa, os resultados esperados são verificados. São definidos os critérios de sucesso para o cenário
     *  de teste, e o teste é aprovado ou reprovado com base nesses resultados.
     *
     */

    @Test
    @DisplayName("Deve retornar probabilidade alta para gato com peso baixo e idade baixa")
    void case01() {

        // ARRANGE
        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto(
                "Abrigo Feliz",
                "9499999999",
                "abrigofeliz@gmail.com"
        ));

        Pet pet = new Pet(new CadastroPetDto(
                TipoPet.GATO,
                "Miau",
                "Siames",
                4,
                "Cinza",
                4.0f
        ), abrigo);

        // ACT
        CalculadoraProbabilidadeAdocao calculator = new CalculadoraProbabilidadeAdocao();
        ProbabilidadeAdocao probabilidade = calculator.calcular(pet);

        // Verificando se o valor que está vindo da calculadora é ALTO
        // Primeiro parâmetro é oque esperamos e o segundo é oque recebemos de resultado
        // Para forçar um erro eu posso alterar a probabilidade que irá retornar lá na classe da calculadora
        // ASSERT
        Assertions.assertEquals(ProbabilidadeAdocao.ALTA, probabilidade);
    }

    @Test
    @DisplayName("Deve retornar probabilidade media para gato com peso baixo e idade alta")
    void case02() {

        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto(
                "Abrigo Feliz",
                "9499999999",
                "abrigofeliz@gmail.com"
        ));

        Pet pet = new Pet(new CadastroPetDto(
                TipoPet.GATO,
                "Miau",
                "Siames",
                15,
                "Cinza",
                4.0f
        ), abrigo);

        CalculadoraProbabilidadeAdocao calculator = new CalculadoraProbabilidadeAdocao();
        ProbabilidadeAdocao probabilidade = calculator.calcular(pet);

        Assertions.assertEquals(ProbabilidadeAdocao.MEDIA, probabilidade);
    }

    @Test
    @DisplayName("Deve retornar probabilidade baixa para gato com peso alto e idade alta")
    void case03() {

        Abrigo abrigo = new Abrigo(new CadastroAbrigoDto(
                "Abrigo Feliz",
                "9499999999",
                "abrigofeliz@gmail.com"
        ));

        Pet pet = new Pet(new CadastroPetDto(
                TipoPet.GATO,
                "Miau",
                "Siames",
                15,
                "Cinza",
                11.0f
        ), abrigo);

        CalculadoraProbabilidadeAdocao calculator = new CalculadoraProbabilidadeAdocao();
        ProbabilidadeAdocao probabilidade = calculator.calcular(pet);

        Assertions.assertEquals(ProbabilidadeAdocao.BAIXA, probabilidade);
    }
}