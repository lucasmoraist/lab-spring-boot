package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @Mock
    private AbrigoRepository abrigoRepository;
    @Mock
    private PetRepository petRepository;
    @Mock
    private Abrigo abrigo;
    @Mock
    private CadastroAbrigoDto dto;
    @InjectMocks
    private AbrigoService service;

    @Test
    @DisplayName("Deve listar todos os abrigos")
    void case01(){
        this.service.listar();

        then(abrigoRepository).should().findAll();
    }

    @Test
    @DisplayName("Deve chamar lista de pets do abrigo através do nome")
    void case02(){
        String nome = "Miau";
        given(abrigoRepository.findByNome(nome)).willReturn(Optional.of(abrigo));

        this.service.listarPetsDoAbrigo(nome);

        then(petRepository).should().findByAbrigo(abrigo);
    }

    @Test
    @DisplayName("Deve informar que já foi cadastrado em outro abrigo")
    void case03(){
        given(abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email())).willReturn(true);

        assertThrows(ValidacaoException.class, () -> service.cadatrar(dto));
    }

    @Test
    @DisplayName("Deve informar o abrigo através do id")
    void case04(){
        Long id = 1L;
        given(abrigoRepository.findById(id)).willReturn(Optional.of(abrigo));

        Abrigo result = this.service.carregarAbrigo("1");

        assertEquals(abrigo, result);
    }

    @Test
    @DisplayName("Deve informar o abrigo através do nome")
    void case05(){
        String nome = "PetLove";
        given(abrigoRepository.findByNome(nome)).willReturn(Optional.of(abrigo));

        Abrigo result = this.service.carregarAbrigo("PetLove");

        assertEquals(abrigo, result);
    }

    @Test
    @DisplayName("Deve lançar exceção de abrigo não encontrado")
    void case06(){
        given(abrigoRepository.findById(1L)).willReturn(Optional.empty());
        given(abrigoRepository.findByNome("PetLove")).willReturn(Optional.empty());

        // Act and Assert
        assertThrows(ValidacaoException.class, () -> service.carregarAbrigo("1"));
        assertThrows(ValidacaoException.class, () -> service.carregarAbrigo("PetLove"));
    }

}