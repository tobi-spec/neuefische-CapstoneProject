package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.api.FoodDto;
import de.tobias.intestinalinspector.api.PainDto;
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

    public Map<String, List<FoodDto>> sortFoodByDay(List<FoodDto> list){
        Map<String, List<FoodDto>> entriesPerDay = list.stream()
                .collect(Collectors.groupingBy(FoodDto::getDate));
        return entriesPerDay;
    }

    public Map<String, List<PainDto>> sortPainByDay(List<PainDto> list){
        Map<String, List<PainDto>> entriesPerDay = list.stream()
                .collect(Collectors.groupingBy(PainDto::getDate));
        return entriesPerDay;
    }
}
