package de.tobias.intestinalinspector;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@EnableAutoConfiguration
@ComponentScan(basePackages = "de.tobias.intestinalinspector")
@TestConfiguration
// import JPA config?
public class SpringTestContextConfiguration {

    @Primary
    @Bean(name="dataSource", destroyMethod = "shutdown")
    public EmbeddedDatabase database(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }
}
