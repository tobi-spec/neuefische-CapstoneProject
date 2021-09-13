package de.tobias.intestinalinspector.service;

import de.tobias.intestinalinspector.SpringBootTests;
import de.tobias.intestinalinspector.model.PainEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

class PainServiceTest extends SpringBootTests {

    @Autowired
    PainService painService;

    @Test
    public void testAddPain(){
        //GIVEN
        PainEntity painToAdd = PainEntity.builder()
                .painLevel(7)
                .userName("Frank")
                .build();
        //WHEN
        PainEntity addedPain = painService.add(painToAdd);
        //THEN
        assertEquals(7, addedPain.getPainLevel());
    }

    @Test
    public void testAddDate(){
        //GIVEN
        PainEntity painToAdd = PainEntity.builder()
                .painLevel(7)
                .userName("Frank")
                .build();
        //WHEN
        PainEntity addedPain = painService.add(painToAdd);
        //THEN
        assertThat(addedPain.getDate(), is(not("")));
    }


}