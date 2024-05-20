package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetComAdocaoEmAndamentoTest {

    @Mock
    private SolicitacaoAdocaoDto dto;
    @Mock
    private AdocaoRepository repository;
    @InjectMocks
    private ValidacaoPetComAdocaoEmAndamento validador;

    @Test
    @DisplayName("Deve informar que está aguardando a avaliação")
    void case01(){
        given(repository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);

        assertThrows(ValidacaoException.class, () -> validador.validar(dto));
    }

    @Test
    @DisplayName("Deve informar que a avaliação já foi enviada")
    void case02(){
        given(repository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(false);

        assertDoesNotThrow(() -> validador.validar(dto));
    }

}