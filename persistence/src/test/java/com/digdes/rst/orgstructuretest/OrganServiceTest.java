package com.digdes.rst.orgstructuretest;

import com.digdes.rst.orgstructure.persistance.model.Organ;
import com.digdes.rst.orgstructure.persistance.services.ApplicationService;
import com.digdes.rst.orgstructure.persistance.services.OrganService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:app.xml"})
public class OrganServiceTest {

    @Autowired
    OrganService organService;
    @Autowired
    ApplicationService applicationService;

    @Test
    public  void set(){
        System.out.println(organService.getAttachOrgan((long) 10));
    }
    @Test
    public void save(){
        Organ organ=new Organ("ololo");
       // organService.saveOrgan(organ,"76f63571-b4ae-485c-abd9-81debb6d40b9_bacfef297f0000012e5bf046f09c9f2b");

    }


    @Test
    public void find(){
        Organ organ=organService.findOrganById((long) 1);
        organService.getPersonByOrgan(organ);
        System.out.println("********");
        System.out.println(organService.getPersonByOrgan(organ));
    }
}
