package com.digdes.rst.orgstructure.web.controller;

import com.digdes.rst.commons.CommonConstants;
import com.digdes.rst.commons.application.ApplicationUtils;
import com.digdes.rst.commons.model.ApplicationBean;
import com.digdes.rst.commons.model.RoleBean;
import com.digdes.rst.commons.permission.EXOPermission;
import com.digdes.rst.orgstructure.persistance.config.AppConfig;
import com.digdes.rst.orgstructure.persistance.dto.rest.request.PersonAddToOrganDto;
import com.digdes.rst.orgstructure.persistance.model.Application;
import com.digdes.rst.orgstructure.persistance.model.Organ;
import com.digdes.rst.orgstructure.persistance.model.Person;
import com.digdes.rst.orgstructure.persistance.services.ApplicationService;
import com.digdes.rst.orgstructure.persistance.services.AttributeService;
import com.digdes.rst.orgstructure.persistance.services.OrganService;
import com.digdes.rst.orgstructure.persistance.services.PersonService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/orgStructure")
@Log4j
@Transactional
public class OrgStructureController {

    @Autowired
    ApplicationService applicationService;
    @Autowired
    PersonService personService;
    @Autowired
    OrganService organService;

    @ApiOperation(value = "сохранение юзера")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @EXOPermission(roles = {RoleBean.MANAGER, RoleBean.ALL}, secretKey = AppConfig.SECRET_KEY)
    @ResponseBody
    @RequestMapping(value = "/savePerson.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> savePerson(@RequestBody Person person, HttpServletRequest request) {
        try {
            String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
            log.debug("AppId:" + appId);
            log.debug("***************SAVE Person***************");
            personService.savePerson(person, appId, request.getRemoteUser());
            return ResponseEntity.ok(person);
        } catch (Exception e) {
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Удаление юзера")
    @EXOPermission(roles = {RoleBean.MANAGER, RoleBean.ALL}, secretKey = AppConfig.SECRET_KEY)
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @ResponseBody
    @RequestMapping(value = "/deletePerson.action", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deletePerson(@RequestParam Long id, HttpServletRequest request) {
        try {
            log.debug("Take id Person" + id);
            log.debug("***************DELETE Person***************");
            String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
            if (appId == null) {
                return ResponseEntity.badRequest().build();
            }
            personService.deletePerson(id, appId, request.getRemoteUser());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Сохранение органа управления")
    @EXOPermission(roles = {RoleBean.MANAGER, RoleBean.ALL}, secretKey = AppConfig.SECRET_KEY)
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @ResponseBody
    @RequestMapping(value = "/saveOrgan.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Organ> saveOrgan(@RequestBody Organ organ, HttpServletRequest request) {
        try {
            String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
            if (appId == null) return ResponseEntity.badRequest().build();
            organService.saveOrgan(organ, appId, request.getRemoteUser());
            log.debug("***************SAVE Organ***************");
            return ResponseEntity.ok(organ);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Удаление органа управления")
    @EXOPermission(roles = {RoleBean.MANAGER, RoleBean.ALL}, secretKey = AppConfig.SECRET_KEY)
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @ResponseBody
    @RequestMapping(value = "/deleteOrgan.action", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteOrgan(@RequestParam Long id, HttpServletRequest request) {
        try {
            String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
            if (appId == null) return ResponseEntity.badRequest().build();
            log.debug("Take id Organ" + id);
            log.debug("***************DELETE Organ***************");
            organService.deleteOrgan(id, appId, request.getRemoteUser());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Поиск")
    @RequestMapping(value = "/getPerson.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Person> search(@RequestParam String name) {
        return personService.search(name);
    }

    @ApiOperation(value = "Сохранение юзеров по порядку")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @ResponseBody
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @RequestMapping(value = "/savePeople.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> savePeople(@RequestBody List<Person> people) {
        log.debug("Get PEOPLE and SAVE");
        personService.savePersons(people);
        return people;
    }

    @ApiOperation(value = "Сохранение органов управления по порядку")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @ResponseBody
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @RequestMapping(value = "/saveOrgans.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Organ> saveOrgan(@RequestBody List<Organ> organs) {
        organService.saveOrgans(organs);
        return organs;
    }

    @ApiOperation(value = "Получение пользователей органа управления")
    @RequestMapping(value = "/getPeopleByOrgan", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Person> getPeopleByOrgan(@RequestParam Long id) {
        return personService.getPersonByOrgan(id);
    }

    @ApiOperation(value = "Получение всех юзеров по аппId")
    @RequestMapping(value = "/getPeopleByAppId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Person> getPeopleByAppId(@RequestParam String appId, String nodeId) {
        log.debug("Take Application by" + appId + "  " + nodeId);
        Application application = applicationService.initApp(appId, nodeId, null);
        log.debug("Get all People by Application");
        return personService.getPeopleByApp(application);
    }

    @ApiOperation(value = "Юзеры по роли")
    @RequestMapping(value = "/getPeopleByRole.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Person> getPeopleByRole(@RequestParam String role) {
        log.debug("Get role" + role);
        log.debug("Get criteria people with role");
        List<Person> people = personService.getPersonByRole(role);
        log.debug("Return People");
        return people;
    }


    @ApiOperation(value = "Сохранение участника в органе управления")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @ResponseBody
    @RequestMapping(value = "/savePersonOrgan.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> saveOrganizationPerson(@RequestBody @Valid PersonAddToOrganDto dto, BindingResult r, HttpServletRequest request) {
        try {
            String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
            log.debug("AppId:" + appId);
            log.debug("***************SAVE OrganizationPerson***************");
            log.debug(r.hasErrors());
            if (appId != null && !r.hasErrors()) {
                return ResponseEntity.ok(organService.addPersonToOrgan(dto, appId, request.getRemoteUser()));
            } else return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ResponseBody
    @ApiOperation(value = "Удаление участника из органа управления")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @RequestMapping(value = "/removePersonOrgan.action", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> removeOrganizationPerson(
            @ApiParam(value = "Ид органа", required = true)
            @RequestParam Long organId,
            @ApiParam(value = "Ид человека", required = true)
            @RequestParam Long personId, HttpServletRequest request) {
        String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
        try {
            log.debug("***************REMOVE CuratorPerson***************");
            if (organId != null && personId != null && appId != null) {
                return ResponseEntity.ok(organService.removePersonFromOrgan(organId, personId, appId, request.getRemoteUser()));
            } else return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Переиндексировать орган")
    @RequestMapping(value = "/reindexOrgan", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    // @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    public ResponseEntity<Boolean> reindexOrgan(@RequestBody String appId, HttpServletRequest request) {
        //String userId = request.getRemoteUser();
        String userId = "root";
        // String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
        log.debug("reindex organ");
        if (userId == null || appId == null) {
            return ResponseEntity.badRequest().build();
        } else {
            try {
                boolean re = organService.reindexMissing(appId, userId);
                return ResponseEntity.ok(re);
            } catch (Exception ex) {
                log.debug(ex.getMessage(), ex);
            }
        }
        return ResponseEntity.badRequest().build();
    }


    @ApiOperation(value = "Переиндексировать людей")
    @RequestMapping(value = "/reindexPerson", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    // @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    public ResponseEntity<Boolean> reindexPerson(@RequestBody String appId, HttpServletRequest request) {
        //String userId = request.getRemoteUser();
        String userId = "root";
        log.debug("reindex person");
        // String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
        if (userId == null || appId == null) {
            return ResponseEntity.badRequest().build();
        } else {
            try {
                boolean re = personService.reindexMissing(appId, userId);
                return ResponseEntity.ok(re);
            } catch (Exception ex) {
                log.debug(ex.getMessage(), ex);
            }
        }
        return ResponseEntity.badRequest().build();
    }


}
