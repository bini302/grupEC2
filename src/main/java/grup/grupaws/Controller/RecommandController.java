package grup.grupaws.Controller;

import grup.grupaws.Dto.PlantsDTO;
import grup.grupaws.Dto.RecommandDTO;
import grup.grupaws.Entity.PlantsEntity;
import grup.grupaws.Service.RecommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class RecommandController {
    private final RecommandService recommandService;

    @PostMapping("/api/question")
    public String question(@RequestBody RecommandDTO recommandDTO, HttpSession session) {
        //session에 answerId 담기
        //answerResult= question의 값이 저장된 dto
        // JSON 형식의 데이터를 받을 때는 @RequestBody 어노테이션 사용
        try {
            // 저장
            RecommandDTO answerResult = recommandService.question(recommandDTO);
            session.setAttribute("userAnswerId", answerResult.getAnswerId());
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }

    @GetMapping("/api/resulting")
    public List<Optional<PlantsEntity>> resulting(HttpSession session, Model model) {

        Long userAnswerId = (Long) session.getAttribute("userAnswerId");
        List<Map<Long, Float>> resultingPlantsList = new ArrayList<>();

        RecommandDTO recommandDTO = recommandService.getAnswerById(userAnswerId);
        PlantsDTO plantsDTO;

        Long levelABSId = recommandService.findLevelABS(userAnswerId);
        model.addAttribute("levelABSId", levelABSId);

        plantsDTO = recommandService.getPlantsById(levelABSId);
        if (!containsPlantsId(resultingPlantsList, levelABSId)) {
            Map<Long, Float> resultingMap = new HashMap<>();
            getResulting(levelABSId, recommandDTO, plantsDTO, resultingMap, resultingPlantsList);
        }
        Long temperatureABSId = recommandService.findTemperatureABS(userAnswerId);
        model.addAttribute("temperatureABSId", temperatureABSId);

        plantsDTO = recommandService.getPlantsById(temperatureABSId);

        if (!containsPlantsId(resultingPlantsList, temperatureABSId)) {
            Map<Long, Float> resultingMap = new HashMap<>();
            getResulting(temperatureABSId, recommandDTO, plantsDTO, resultingMap, resultingPlantsList);
        }
        Long lightABSId = recommandService.findLightABS(userAnswerId);
        model.addAttribute("lightABSId", lightABSId);

        plantsDTO = recommandService.getPlantsById(lightABSId);
        if (!containsPlantsId(resultingPlantsList, lightABSId)) {
            Map<Long, Float> resultingMap = new HashMap<>();
            getResulting(lightABSId, recommandDTO, plantsDTO, resultingMap, resultingPlantsList);
        }
        Long waterABSId = recommandService.findWaterABS(userAnswerId);
        model.addAttribute("waterABSID", waterABSId);

        plantsDTO = recommandService.getPlantsById(waterABSId);
        if (!containsPlantsId(resultingPlantsList, waterABSId)) {
            Map<Long, Float> resultingMap = new HashMap<>();
            getResulting(waterABSId, recommandDTO, plantsDTO, resultingMap, resultingPlantsList);
        }

        resultingPlantsList.sort(Comparator.comparing(map -> map.values().iterator().next()));

        List<Long> resultPlantsId = new ArrayList<>();
        List<Optional<PlantsEntity>> resultPlantsEntityList = new ArrayList<>();

        int count = 0;
        for (Map<Long, Float> resultMap : resultingPlantsList) {
            Long plantId = resultMap.keySet().iterator().next();
            resultPlantsId.add(plantId);
            Optional<PlantsEntity> resultEntity = recommandService.getPlantsEntityById(plantId);
            resultPlantsEntityList.add(resultEntity);
            count++;
            if (count == 3) {
                break;
            }
        }
        return resultPlantsEntityList;
    }

    private boolean containsPlantsId(List<Map<Long, Float>> list, Long plantsId) {
        for (Map<Long, Float> map : list) {
            if (map.containsKey(plantsId)) {
                return true;
            }
        }
        return false;
    }

    private void getResulting(Long plantsId, RecommandDTO recommandDTO, PlantsDTO plantsDTO, Map resultingMap, List<Map<Long, Float>> resultingPlantsList) {
        Float similarity;

        BigDecimal temperatureABS = plantsDTO.getPlantsTemperature().subtract(recommandDTO.getAnswerTemperature());
        BigDecimal lightABS = plantsDTO.getPlantsLight().subtract(recommandDTO.getAnswerLight());
        BigDecimal waterABS = plantsDTO.getPlantsWater().subtract(recommandDTO.getAnswerWater());
        BigDecimal levelABS = plantsDTO.getPlantsLevel().subtract(recommandDTO.getAnswerLevel());

        if (plantsDTO.getPlantsSelected() == 0) {
            similarity = ((temperatureABS.add(lightABS.add(waterABS.add(levelABS)))).divide(BigDecimal.valueOf(4), 3, RoundingMode.HALF_EVEN)).floatValue();
        } else {
            similarity = ((temperatureABS.add(lightABS.add(waterABS.add(levelABS)))).divide(BigDecimal.valueOf(4), 3, RoundingMode.HALF_EVEN)
                    .subtract(BigDecimal.valueOf(plantsDTO.getPlantsSelected() / 10))).floatValue();
        }
        resultingMap.put(plantsId, similarity);
        resultingPlantsList.add(resultingMap);
    }
}