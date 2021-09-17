package de.tobias.intestinalinspector.controller;


import de.tobias.intestinalinspector.api.FoodDto;
import de.tobias.intestinalinspector.api.PainDto;
import de.tobias.intestinalinspector.api.PainListDto;
import de.tobias.intestinalinspector.api.UpdateDto;
import de.tobias.intestinalinspector.model.AppUserEntity;
import de.tobias.intestinalinspector.model.FoodEntity;
import de.tobias.intestinalinspector.model.PainEntity;
import de.tobias.intestinalinspector.service.PainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<PainDto> add(@AuthenticationPrincipal AppUserEntity appUser,
                                       @RequestBody PainDto painDto){

        PainEntity painToPersist = map(appUser, painDto);
        PainEntity persistedPain = painService.add(painToPersist);
        PainDto painToReturn = map(persistedPain);
        return  ok(painToReturn);
    }

    @GetMapping
    public ResponseEntity<PainListDto> getAll(@AuthenticationPrincipal AppUserEntity appUser){
        List<PainEntity> listOfPain = painService.getAll(appUser.getUserName());
        PainListDto painListDto = new PainListDto();
        map(listOfPain, painListDto);
        return ok(painListDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PainDto> update(@PathVariable Long id, @RequestBody UpdateDto updateDto){
        int newNumber = updateDto.getNewNumber();
        PainEntity changedEntity = painService.update(id, newNumber);
        PainDto returnDto = map(changedEntity);
        return ok(returnDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<PainDto> delete(@PathVariable Long id) {
        PainEntity deleteEntity = painService.delete(id);
        PainDto returnDto = map(deleteEntity);
        return ok(returnDto);
    }

    private void map(List<PainEntity> listOfPain, PainListDto painListDto) {
        for(PainEntity painItem: listOfPain){
            PainDto painDto = PainDto.builder()
                    .painLevel(painItem.getPainLevel())
                    .id(painItem.getId())
                    .date(painItem.getDate())
                    .build();
            painListDto.addPain(painDto);
        }
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
