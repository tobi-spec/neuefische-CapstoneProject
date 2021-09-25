package de.tobias.intestinalinspector.controller;


import de.tobias.intestinalinspector.api.PainDto;
import de.tobias.intestinalinspector.api.PainMapDto;
import de.tobias.intestinalinspector.api.PainMapsDto;
import de.tobias.intestinalinspector.api.PainUpdateDto;
import de.tobias.intestinalinspector.model.AppUserEntity;
import de.tobias.intestinalinspector.model.PainEntity;
import de.tobias.intestinalinspector.service.DateService;
import de.tobias.intestinalinspector.service.PainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/pain")
public class PainController {

    private final PainService painService;
    private final DateService dateService;

    @Autowired
    public PainController(PainService painService, DateService dateService) {
        this.painService = painService;
        this.dateService = dateService;
    }

    @PostMapping
    public ResponseEntity<PainDto> add(@AuthenticationPrincipal AppUserEntity appUser,
                                       @RequestBody PainDto painDto){

        PainEntity painToPersist = map(appUser, painDto);
        PainEntity persistedPain = painService.add(painToPersist);
        PainDto painToReturn = map(persistedPain);
        return  ok(painToReturn);
    }

    @GetMapping
    public ResponseEntity<PainMapsDto> getAll(@AuthenticationPrincipal AppUserEntity appUser){
        List<PainEntity> listOfPain = painService.getAll(appUser.getUserName());
        List<PainDto> painListToMap = map(listOfPain);
        Map<String, List<PainDto>> results = dateService.sortPainByDay(painListToMap);
        PainMapsDto painMapsDto = new PainMapsDto();

        for(Map.Entry<String, List<PainDto>> entry: results.entrySet()){
            String date = entry.getKey();
            List<PainDto> pains = entry.getValue();
            PainMapDto painMapDto = new PainMapDto();
            painMapDto.setDate(date);
            painMapDto.setPains(pains);
            painMapsDto.getPainMaps().add(painMapDto);
        }
        Collections.sort(painMapsDto.getPainMaps());
        return ok(painMapsDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<PainDto> update(@PathVariable Long id, @RequestBody PainUpdateDto updateDto){
        int newNumber = updateDto.getNewValue();
        PainEntity changedEntity = painService.update(id, newNumber);
        PainDto returnDto = map(changedEntity);
        return ok(returnDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<PainDto> delete(@PathVariable Long id) {
        PainEntity deleteEntity = painService.delete(id);
        PainDto returnDto = map(deleteEntity);
        return ok(returnDto);
    }

    private List<PainDto> map(List<PainEntity> listOfPain) {
        List<PainDto> painListDto = new ArrayList<>();
        for(PainEntity painItem: listOfPain){
            PainDto painDto = PainDto.builder()
                    .painLevel(painItem.getPainLevel())
                    .id(painItem.getId())
                    .date(painItem.getDate())
                    .build();
            painListDto.add(painDto);
        }
        return painListDto;
    }

    private PainDto map(PainEntity persistedPain) {
        return PainDto.builder()
                .painLevel(persistedPain.getPainLevel())
                .id(persistedPain.getId())
                .date(persistedPain.getDate())
                .build();
    }

    private PainEntity map(AppUserEntity appUser, PainDto painDto) {
        return PainEntity.builder()
                .painLevel(painDto.getPainLevel())
                .userName(appUser.getUserName())
                .build();
    }
}
