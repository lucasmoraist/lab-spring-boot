package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class AdocaoControllerTest {

    @Autowired
    private MockMvc mockMvc; // Essa é uma classe que simula requisições

    /*
     * Nesse caso não estou dizendo para a classe teste utilizar um outro banco de dados porque nesse caso eu já estaria fazendo
     * um teste de integração e não de unidade
     */

    // Dessa forma eu estou dizendo para o spring injetar uma simulação da classe AdocaoService
    @MockBean
    private AdocaoService adocaoService;
    
    @Test
    @DisplayName("Deve retornar código 400 para solicitação de adoção com erros")
    void case01() throws Exception{
        // ARRANGE
        String json = "{}";

        // ACT
        var response = mockMvc.perform(
            MockMvcRequestBuilders.post("/adocoes")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                
        ).andReturn().getResponse();

        // ASSERT
        assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve retornar código 202 para solicitação de adoção")
    void case02() throws Exception{
        // ARRANGE
        String json = """
                {
                    "idPet": 1,
                    "idTutor": 1,
                    "motivo": "Motivo qualquer"
                }
                """; // Este é um recurso do Java chamado Text Block, quando eu abro aspas três vezes eu não preciso ficar concatenando as informações

        // ACT
        var response = mockMvc.perform(
            MockMvcRequestBuilders.post("/adocoes")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                
        ).andReturn().getResponse();

        // ASSERT
        assertEquals(200, response.getStatus());
    }

}
