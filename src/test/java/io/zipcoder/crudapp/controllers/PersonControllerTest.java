package io.zipcoder.crudapp.controllers;

import io.zipcoder.crudapp.models.Person;
import io.zipcoder.crudapp.repositories.PersonRepository;
import io.zipcoder.crudapp.services.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService service;

    @Test
    public void testShow() throws Exception {
        Long givenId = 1L;
        BDDMockito
                .given(service.find(givenId))
                .willReturn(new Person("Leon", "Hunter"));

        String expectedContent = "{\"id\":null,\"firstName\":\"Leon\",\"lastName\":\"Hunter\"}";
        this.mvc.perform(MockMvcRequestBuilders
                .get("/people/" + givenId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void testCreate() throws Exception {
        Person person = new Person("Leon", "Hunter");
        BDDMockito
                .given(service.create(person))
                .willReturn(person);

        String expectedContent = "{\"id\":null,\"firstName\":\"Leon\",\"lastName\":\"Hunter\"}";
        this.mvc.perform(MockMvcRequestBuilders
                .post("/people/")
                .content(expectedContent)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void testFindAll() throws Exception{
        List<Person> test = new ArrayList();
        Person p1 = new Person("Mark", "Hamil");
        Person p2 = new Person("Leon", "Hunter");
        test.add(p1);
        test.add(p2);
        BDDMockito
                .given(service.findAll())
                .willReturn(test);

        String excpectedContent = "[{\"id\":null,\"firstName\":\"Mark\",\"lastName\":\"Hamil\"}," +
                "{\"id\":null,\"firstName\":\"Leon\",\"lastName\":\"Hunter\"}]";
        this.mvc.perform(MockMvcRequestBuilders
                .get("/people/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(excpectedContent));
    }

    @Test
    public void testUpdate() throws Exception{
        Long givenID = 1l;
        Person person = new Person("Leon", "Hunter");
        BDDMockito
                .given(service.update(givenID, person))
                .willReturn(person);

        String expectedContent = "{\"id\":null,\"firstName\":\"Leon\",\"lastName\":\"Hunter\"}";
        this.mvc.perform(MockMvcRequestBuilders
                .put("/people/" + givenID)
                .content(expectedContent)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void testUpdate2() throws Exception{
        Long givenID = 1l;
        Person person = new Person("Leon", "Hunter");
        service.create(person);
        person.setFirstName("Leo");
        person.setLastName("Hunt");
        BDDMockito
                .given(service.update(givenID, person))
                .willReturn(person);

        String expectedContent = "{\"id\":null,\"firstName\":\"Leo\",\"lastName\":\"Hunt\"}";
        this.mvc.perform(MockMvcRequestBuilders
                .put("/people/" + givenID)
                .content(expectedContent)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedContent));
    }

    @Test
    public void testDelete() throws Exception {
        Long givenID = 1l;
        BDDMockito
                .given(service.delete(givenID))
                .willReturn(false);

        String expectedContent = "{\"id\":null,\"firstName\":\"Leon\",\"lastName\":\"Hunter\"}";
        this.mvc.perform(MockMvcRequestBuilders
                .delete("/people/" + givenID)
                .content(expectedContent)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }

    @Test
    public void testDelete2() throws Exception {
        Long givenID = 1L;
        Person person = new Person("Leon", "Hunter");
        service.create(person);
        BDDMockito
                .given(service.delete(givenID))
                .willReturn(true);

        String expectedContent = "{\"id\":null,\"firstName\":\"Leon\",\"lastName\":\"Hunter\"}";
        this.mvc.perform(MockMvcRequestBuilders
                .delete("/people/" + givenID)
                .content(expectedContent)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }
}