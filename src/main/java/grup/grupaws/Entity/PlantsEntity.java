package grup.grupaws.Entity;

import grup.grupaws.Dto.PlantsDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Table(name = "plants")
public class PlantsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plantsId")
    private Long plantsId;
    @Column(name = "plantsName")
    private String plantsName;
    @Column(name = "plantsPic")
    private String plantsPic;
    @Column(name = "plantsTemperature")
    private BigDecimal plantsTemperature;
    @Column(name = "plantsLight")
    private BigDecimal plantsLight;
    @Column(name = "plantsWater")
    private BigDecimal plantsWater;
    @Column(name = "plantsLevel")
    private BigDecimal plantsLevel;
    @Column(name = "plantsSelected")
    private Integer plantsSelected;
    @Column(name = "plantsEffect")
    private String plantsEffect;
    @Column(name = "plantsNotice")
    private String plantsNotice;

    public static PlantsEntity toPlantsEntity(PlantsDTO plantsDTO) {
        PlantsEntity plantsEntity = new PlantsEntity();

        plantsEntity.setPlantsId(plantsDTO.getPlantsId());
        plantsEntity.setPlantsName(plantsDTO.getPlantsName());
        plantsEntity.setPlantsPic(plantsDTO.getPlantsPic());
        plantsEntity.setPlantsTemperature(plantsDTO.getPlantsTemperature());
        plantsEntity.setPlantsLight(plantsDTO.getPlantsLight());
        plantsEntity.setPlantsWater(plantsDTO.getPlantsWater());
        plantsEntity.setPlantsLevel(plantsDTO.getPlantsLevel());
        plantsEntity.setPlantsSelected(plantsDTO.getPlantsSelected());
        plantsEntity.setPlantsEffect(plantsDTO.getPlantsEffect());
        plantsEntity.setPlantsNotice(plantsDTO.getPlantsNotice());
        return plantsEntity;
    }
}
