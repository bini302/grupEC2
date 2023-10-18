package grup.grupaws.Dto;

import grup.grupaws.Entity.PlantsEntity;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter // 클래스 내 모든 필드의 getter 메서드 자동생성
@Setter
@NoArgsConstructor // 기본 생성자 자동 추가
@AllArgsConstructor
@ToString

public class PlantsDTO {
    private Long plantsId;
    private String plantsName;
    private String plantsPic;
    private BigDecimal plantsTemperature;
    private BigDecimal plantsLight;
    private BigDecimal plantsWater;
    private BigDecimal plantsLevel;
    private Integer plantsSelected;
    private String plantsEffect;
    private String plantsNotice;

    public static PlantsDTO toPlantsDTO(PlantsEntity plantsEntity){
        PlantsDTO plantsDTO =new PlantsDTO();

        plantsDTO.setPlantsId(plantsEntity.getPlantsId());
        plantsDTO.setPlantsName(plantsEntity.getPlantsName());
        plantsDTO.setPlantsPic(plantsEntity.getPlantsPic());
        plantsDTO.setPlantsTemperature(plantsEntity.getPlantsTemperature());
        plantsDTO.setPlantsLight(plantsEntity.getPlantsLight());
        plantsDTO.setPlantsWater(plantsEntity.getPlantsWater());
        plantsDTO.setPlantsLevel(plantsEntity.getPlantsLevel());
        plantsDTO.setPlantsSelected(plantsEntity.getPlantsSelected());
        plantsDTO.setPlantsEffect(plantsEntity.getPlantsEffect());
        plantsDTO.setPlantsNotice(plantsEntity.getPlantsNotice());

        return plantsDTO;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // 전제조건

        PlantsDTO other = (PlantsDTO) obj;
        // plantsId로 중복확인
        // 필드들이 동일하다면 true를 반환하고, 그렇지 않다면 false를 반환합니다.
        return Objects.equals(this.plantsId, other.plantsId);
    }
}

