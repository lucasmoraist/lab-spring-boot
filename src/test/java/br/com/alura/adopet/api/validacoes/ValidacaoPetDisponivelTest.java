package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetDisponivelTest {

    /*
     * O Mockito é uma biblioteca de teste para Java que permite criar objetos fictícios (também conhecidos
     * como "mocks") para simular o comportamento de classes reais.
     *
     * Isso é útil para isolar a classe que está sendo testada, garantindo que apenas o comportamento da classe em
     * questão seja verificado, sem depender de outras classes ou recursos externos, como bancos de dados ou
     * serviços web.
     *
     * - @ExtendWith(MockitoExtension.class): Esta anotação é usada para integrar o Mockito com o JUnit 5, permitindo
     * que o Mockito seja estendido para fornecer suporte para anotações como @Mock e @InjectMocks.
     *
     * - @Mock: Esta anotação é usada para criar um mock de uma classe ou interface. Quando você anota um campo com
     * @Mock, o Mockito cria um mock desse tipo automaticamente.
     *
     * - @InjectMocks: Esta anotação é usada para injetar os mocks criados automaticamente (anotados com @Mock)
     * em uma instância da classe que está sendo testada.
     */

    @Mock
    private PetRepository petRepository;
    @Mock
    private Pet pet;
    @Mock
    private SolicitacaoAdocaoDto dto;
    @InjectMocks
    private ValidacaoPetDisponivel validacao;

    @Test
    @DisplayName("Deve permitir solicitação de adoção do pet")
    void case01(){

        BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        BDDMockito.given(pet.getAdotado()).willReturn(false);

        // .assertThrows: espera não receber uma exceção
        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));

    }

    @Test
    @DisplayName("Não deve permitir solicitação de adoção do pet")
    void case02(){

        // Este método é utilizado para indicar ao mock o que ele deve fazer quando um determinado método é chamado
        BDDMockito.given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        BDDMockito.given(pet.getAdotado()).willReturn(true);

        // .assertThrows: espera receber o tipo da exceção e da onde ela estara vindo
        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));

    }

}