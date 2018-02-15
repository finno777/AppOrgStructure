package com.digdes.rst.orgstructuretest;


import com.digdes.rst.orgstructure.persistance.dao.AttributeDao;
import com.digdes.rst.orgstructure.persistance.model.Application;
import com.digdes.rst.orgstructure.persistance.model.Attribute;
import com.digdes.rst.orgstructure.persistance.model.Person;
import com.digdes.rst.orgstructure.persistance.services.ApplicationService;
import com.digdes.rst.orgstructure.persistance.services.AttributeService;
import com.digdes.rst.orgstructure.persistance.services.OrganService;
import com.digdes.rst.orgstructure.persistance.services.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:app.xml"})
public class PersonServiceTest {

    @Autowired
    PersonService personService;
    @Autowired
    ApplicationService applicationService;
    @Autowired
    AttributeService attributeService;
    @Autowired
    OrganService organService;
    @Autowired
    AttributeDao attributeDao;

    @Test
    public void save(){

        Application application=applicationService.initApp("76f63571-b4ae-485c-abd9-81debb6d40b9_bacfef297f0000012e5bf046f09c9f2b","bacfef297f0000012e5bf046f09c9f2b","/portal/gost//home/about/leadership?portal:componentId=76f63571-b4ae-485c-abd9-81debb6d40b9");
        System.out.println(personService.getPeopleByApp(application));
    }

    @Test
    public  void set(){
        Person person=new Person( "test@test");
//        Organ organ=organService.findOrganById((long)31);
//        personService.savePersonByOrgan(person,organ);
      //      personService.savePerson(person,"76f63571-b4ae-485c-abd9-81debb6d40b9_bacfef297f0000012e5bf046f09c9f2b");
//        personService.savePersonByOrgan(person,organ);

    }

    @Test
    public  void setPerson(){
        Person person = personService.findPerson((long) 38);
        person.setName("Ilya");
      //  personService.savePerson(person,"76f63571-b4ae-485c-abd9-81debb6d40b9_bacfef297f0000012e5bf046f09c9f2b");

    }

    @Test
    public void getAdviser(){

        System.out.println(attributeDao.findPeopleByAdviser("20","person"));
        List<Person> people=new ArrayList<>();
        for(Attribute attribute:attributeDao.findPeopleByAdviser("20","person")){
            Person person=attribute.getPerson();
            people.add(person);
        }
        System.out.println(people);

    }

    @Test
    public  void set2(){
        System.out.println(personService.getPersonByRole("DEPUTY"));
    }

    @Test
    public  void set1(){
        System.out.println(personService.getAttachPerson((long) 1));
    }

}
