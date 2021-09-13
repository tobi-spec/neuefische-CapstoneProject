package de.tobias.intestinalinspector.controller;


import de.tobias.intestinalinspector.api.FrontendPainDto;
import de.tobias.intestinalinspector.model.PainEntity;
import de.tobias.intestinalinspector.repository.PainRepository;
import de.tobias.intestinalinspector.service.PainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pain")
public class PainController {

    PainService painService;

    @Autowired
    public PainController(PainService painService) {
        this.painService = painService;
    }

    @PostMapping
    public FrontendPainDto add(@RequestBody FrontendPainDto frontendPainDto){
        PainEntity painToPersist = PainEntity.builder()
                .painLevel(frontendPainDto.getPainLevel())
                .build();

        PainEntity persistedPain = painService.add(painToPersist);

        FrontendPainDto painToReturn = FrontendPainDto.builder()
                .painLevel(persistedPain.getPainLevel())
                .id(persistedPain.getId())
                .date(persistedPain.getDate())
                .build();
        return  painToReturn;
    }
}
