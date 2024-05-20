package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidacaoTutorComLimiteDeAdocoes implements ValidacaoSolicitacaoAdocao {

    @Autowired
    private AdocaoRepository adocaoRepository;

    public void validar(SolicitacaoAdocaoDto dto) {
        long qtdMaximaDeAdocoesDoTutor = adocaoRepository
                .countByTutorIdAndStatus(dto.idTutor(), StatusAdocao.APROVADO);

        if(qtdMaximaDeAdocoesDoTutor >= 5){
            throw new ValidacaoException("Tutor já atingiu número máximo de adoção");
        }
    }

}
