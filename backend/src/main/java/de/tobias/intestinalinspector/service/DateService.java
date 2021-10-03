package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.api.BasicDtoInterface;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DateService {

    public String getDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        return now.format(dtf);
    }

    public <T extends BasicDtoInterface> Map<String, List<T>> sortByDay(List<T> list){
        Map<String, List<T>> entriesPerDay = list.stream()
                .collect(Collectors.groupingBy(T::getDate));
        return entriesPerDay;
    }
}
