package grup.grupaws.Dto;

import grup.grupaws.Entity.RecommandEntity;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class RecommandDTO {
    private Long answerId;
    private BigDecimal answerLevel;
    private BigDecimal answerLight;
    private BigDecimal answerTemperature;
    private BigDecimal answerWater;

    public static RecommandDTO toRecommandDTO(RecommandEntity recommandEntity){
        RecommandDTO recommandDTO =new RecommandDTO();

        recommandDTO.setAnswerId(recommandEntity.getAnswerId());
        recommandDTO.setAnswerLevel(recommandEntity.getAnswerLevel());
        recommandDTO.setAnswerLight(recommandEntity.getAnswerLight());
        recommandDTO.setAnswerTemperature(recommandEntity.getAnswerTemperature());
        recommandDTO.setAnswerWater(recommandEntity.getAnswerWater());
        return recommandDTO;
    }
}
