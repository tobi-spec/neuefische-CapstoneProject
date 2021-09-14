package de.tobias.intestinalinspector.controller;


import de.tobias.intestinalinspector.api.FrontendPainDto;
import de.tobias.intestinalinspector.api.FrontendPainListDto;
import de.tobias.intestinalinspector.model.AppUserEntity;
import de.tobias.intestinalinspector.model.PainEntity;
import de.tobias.intestinalinspector.service.PainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/pain")
public class PainController {

    private final PainService painService;

    @Autowired
    public PainController(PainService painService) {
        this.painService = painService;
    }

    @PostMapping
    public ResponseEntity<FrontendPainDto> add(@AuthenticationPrincipal AppUserEntity appUser,
                                              @RequestBody FrontendPainDto frontendPainDto){

        PainEntity painToPersist = map(appUser, frontendPainDto);

        PainEntity persistedPain = painService.add(painToPersist);

        FrontendPainDto painToReturn = map(persistedPain);

        return  ok(painToReturn);
    }

    @GetMapping
    public ResponseEntity<FrontendPainListDto> getAll(@AuthenticationPrincipal AppUserEntity appUser){

        List<PainEntity> listOfPain = painService.getAll(appUser.getUserName());
        FrontendPainListDto painList = new FrontendPainListDto();
        for(PainEntity painItem: listOfPain){
            FrontendPainDto painDto = FrontendPainDto.builder()
                    .painLevel(painItem.getPainLevel())
                    .id(painItem.getId())
                    .date(painItem.getDate())
                    .build();
            painList.addPain(painDto);
        }
        return ok(painList);
    }

    private FrontendPainDto map(PainEntity persistedPain) {
        return FrontendPainDto.builder()
                .painLevel(persistedPain.getPainLevel())
                .id(persistedPain.getId())
                .date(persistedPain.getDate())
                .build();
    }

    private PainEntity map(AppUserEntity appUser, FrontendPainDto frontendPainDto) {
        return PainEntity.builder()
                .painLevel(frontendPainDto.getPainLevel())
                .userName(appUser.getUserName())
                .build();
    }
}
