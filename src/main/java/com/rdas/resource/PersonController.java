package com.rdas.resource;

import com.rdas.model.Person;
import com.rdas.model.PersonType;
import com.rdas.service.InMemoryPersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.List;

/**
 * Created by rdas on 22/04/2018.
 */
@Slf4j
@RestController
public class PersonController {
    private InMemoryPersonService  personService;


    @Autowired
    public PersonController (InMemoryPersonService  personService) {
        this.personService=personService;
    }

    @GetMapping("/persons")
    public List<Person> getPersons() {
        return personService.getPersons();
    }

    @GetMapping("/personsbytype")
    public List<Person> getPersonsByType(@RequestParam("type") PersonType type) {
        return personService.getPersons(type);
    }

    @PostMapping("/create")
    public boolean createPersons(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(PersonType.class, new PersonTypeConverter());
    }

    public class PersonTypeConverter extends PropertyEditorSupport {
        public void setAsText(final String text) throws IllegalArgumentException {
            setValue(PersonType.fromValue(text));
        }
    }
}