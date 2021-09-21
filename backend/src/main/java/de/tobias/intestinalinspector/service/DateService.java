package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.api.FoodDto;
import de.tobias.intestinalinspector.api.PainDto;
import de.tobias.intestinalinspector.model.FoodEntity;
import de.tobias.intestinalinspector.model.PainEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DateService {

    public String getDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        return now.format(dtf);
    }

    public Map<String, List<FoodDto>> sortFoodByWeek(List<FoodDto> list){
        Map<String, List<FoodDto>> entriesPerWeek = list.stream()
                .collect(Collectors.groupingBy(FoodDto::getDate));
        return entriesPerWeek;
    }

    public Map<String, List<PainDto>> sortPainByWeek(List<PainDto> list){
        Map<String, List<PainDto>> entriesPerWeek = list.stream()
                .collect(Collectors.groupingBy(PainDto::getDate));
        return entriesPerWeek;
    }
}
