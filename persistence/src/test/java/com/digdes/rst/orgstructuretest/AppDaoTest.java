package com.digdes.rst.orgstructuretest;


import com.digdes.rst.orgstructure.persistance.dao.AttributeDao;
import com.digdes.rst.orgstructure.persistance.dao.PersonDao;
import com.digdes.rst.orgstructure.persistance.services.ApplicationService;
import com.digdes.rst.orgstructure.persistance.services.AttributeService;
import com.digdes.rst.orgstructure.persistance.services.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:app.xml"})
public class AppDaoTest {
    @Autowired
    ApplicationService applicationService;

    @Autowired
    AttributeDao attributeDao;

    @Autowired
    PersonService personService;
    @Autowired
    PersonDao personDao;
    @Autowired
    AttributeService attributeService;



    @Test
    public void save(){
//        Application application=new Application("first2");
//        applicationService.saveApp(application);
//        Person person= new Person("test", application, Person.Role.MANAGER);
//        personService.savePerson(person,application.getAppId());
//        System.out.println(personService.getPersonByRole(Person.Role.MANAGER));
//        Attribute attribute=new Attribute("key",person);
//        Attribute attribute1=new Attribute("key1",person);
//        Attribute attribute2=new Attribute("key2",person);
//        attributeService.saveAttribute(attribute,person);
//        attributeService.saveAttribute(attribute1,person);
//        attributeService.saveAttribute(attribute2,person);
    }
    @Test
    public void find(){
//        Application application=new Application("first2");
//        Person person= new Person("Ilya", application, Person.Role.MANAGER);
    }

}
