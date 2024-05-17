package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @InjectMocks
    private AdocaoService service;

    @Mock
    private AdocaoRepository repository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private EmailService emailService;

    /*
     *  A anotação @Spy é utilizada no contexto de testes unitários com o framework Mockito para criar um "spy object"
     *  (objeto espião).
     *
     *  Um objeto espião é uma versão real de um objeto que mantém seus comportamentos originais, mas também permite
     *  que você faça alterações específicas quando necessário.
     */
    @Spy
    private List<ValidacaoSolicitacaoAdocao> validacoes = new ArrayList<>();
    @Mock
    private ValidacaoSolicitacaoAdocao validador1;
    @Mock
    private ValidacaoSolicitacaoAdocao validador2;

    @Mock
    private Pet pet;

    @Mock
    private Tutor tutor;

    @Mock
    private Abrigo abrigo;

    private SolicitacaoAdocaoDto dto;

    /*
     * O @Captor é uma anotação utilizada em testes unitários com Mockito para capturar argumentos de métodos que foram
     * chamados durante a execução do teste.
     *
     * O ArgumentCaptor é uma classe do framework Mockito que permite capturar argumentos passados para métodos
     * durante a execução de testes unitários.
     */
    @Captor
    private ArgumentCaptor<Adocao> adocaoCaptor;

    @Test
    @DisplayName("Deve salvar adoção ao solicitar")
    void case01(){
        // ARRANGE
        this.dto = new SolicitacaoAdocaoDto(10L, 20L, "Motivo qualquer");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        // ACT
        this.service.solicitar(dto);

        // ASSERT
        // Aqui estamos checando se o método save do petRepository está sendo chamado corretamente
        BDDMockito.then(repository).should().save(adocaoCaptor.capture());
        // Aqui estamos recuperando o obj que foi passado
        Adocao adocaoSalva = adocaoCaptor.getValue();

        Assertions.assertEquals(pet, adocaoSalva.getPet());
        Assertions.assertEquals(tutor, adocaoSalva.getTutor());
        Assertions.assertEquals(dto.motivo(), adocaoSalva.getMotivo());
    }

    @Test
    @DisplayName("Deve chamar validadores de adoção ao solicitar")
    void case02(){
        // ARRANGE
        this.dto = new SolicitacaoAdocaoDto(10L, 20L, "Motivo qualquer");
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        validacoes.add(validador1);
        validacoes.add(validador2);

        // ACT
        this.service.solicitar(dto);

        // ASSERT
        BDDMockito.then(validador1).should().validar(dto);
        BDDMockito.then(validador2).should().validar(dto);
    }

}