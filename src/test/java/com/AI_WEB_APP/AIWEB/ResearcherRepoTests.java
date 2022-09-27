package com.AI_WEB_APP.AIWEB;

import com.AI_WEB_APP.AIWEB.model.Researcher;
import com.AI_WEB_APP.AIWEB.resporitory.ResearcherRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Java6Assertions.assertThat;
import org.junit.Test;

//@AutoConfigureMockMvc
////@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class ResearcherRepoTests {
    @Autowired
    private ResearcherRepo researcherRepo;
    //public ResearcherRepoTests(ResearcherRepo r){this.researcherRepo = r;}

    Date sDate = new Date();
    @BeforeEach
    void initUseCase() {
        List<Researcher> researchers = Arrays.asList(
                new Researcher("surname", "initials", "title", "institution", "rating", sDate, sDate,
                        "primary", "secondary", "specialisations")
        );
        researcherRepo.saveAll(researchers);
    }
    @AfterEach
    public void destroyAll(){
        researcherRepo.deleteAll();
    }



    @Test
    public void saveAll_success() {

        List<Researcher> customers = Arrays.asList(
                new Researcher("surname", "initials", "title", "institution", "rating", sDate, sDate,
                        "primary", "secondary", "specialisations"),
                new Researcher("surname", "initials", "title", "institution", "rating", sDate, sDate,
                        "primary", "secondary", "specialisations"),
                new Researcher("surname", "initials", "title", "institution", "rating", sDate, sDate,
                        "primary", "secondary", "specialisations")
        );
        Iterable<Researcher> allCustomer = researcherRepo.saveAll(customers);

        AtomicInteger validIdFound = new AtomicInteger();
        allCustomer.forEach(customer -> {
            if(customer.getId()>0){
                validIdFound.getAndIncrement();
            }
        });

        assertThat(validIdFound.intValue()).isEqualTo(3);
    }

    @Test
    public void findAll_success() {
        List<Researcher> allCustomer = researcherRepo.findAll();
        assertThat(allCustomer.size()).isGreaterThanOrEqualTo(1);
    }


}
