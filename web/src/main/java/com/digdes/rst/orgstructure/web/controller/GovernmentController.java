package com.digdes.rst.orgstructure.web.controller;

import com.digdes.rst.commons.CommonConstants;
import com.digdes.rst.commons.application.ApplicationUtils;
import com.digdes.rst.commons.model.RoleBean;
import com.digdes.rst.commons.permission.EXOPermission;
import com.digdes.rst.orgstructure.persistance.config.AppConfig;
import com.digdes.rst.orgstructure.persistance.dto.PersonSearchDto;
import com.digdes.rst.orgstructure.persistance.model.*;
import com.digdes.rst.orgstructure.persistance.services.DivisionService;
import com.digdes.rst.orgstructure.persistance.services.GovernmentService;
import com.digdes.rst.orgstructure.persistance.services.PersonService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/government")
@Log4j
public class GovernmentController {
    @Autowired
    DivisionService divisionService;

    @Autowired
    GovernmentService governmentService;

    @Autowired
    PersonService personService;

    @ApiOperation(value = "Сохранение управления")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @ResponseBody
    @RequestMapping(value = "/saveGovernment.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Government> saveGovernment(@RequestBody Government government, HttpServletRequest request){
        try {
            String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
            log.debug("AppId:" + appId);
            log.debug("***************SAVE government***************");
            governmentService.saveGovernment(government, appId);

            Government government1 = new Government();
            government1.setId(government.getId());
            return ResponseEntity.ok(government1);
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Сохранение руководителя")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @ResponseBody
    @RequestMapping(value = "/saveGovernmentPerson.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GovernmentPerson> saveGovernmentPerson(@RequestBody GovernmentPerson governmentPerson, HttpServletRequest request){
        try {
            String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
            log.debug("AppId:" + appId);
            log.debug("***************SAVE GovernmentPerson***************");
            governmentService.saveGovernmentPerson(governmentPerson);

            GovernmentPerson governmentPerson1 = new GovernmentPerson();
            governmentPerson1.setId(governmentPerson.getId());
            return ResponseEntity.ok(governmentPerson1);
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Сохранение участника")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @ResponseBody
    @RequestMapping(value = "/saveDivisionPerson.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DivisionPerson> saveDivisionPerson(@RequestBody DivisionPerson divisionPerson, HttpServletRequest request){
        try {
            String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
            log.debug("AppId:" + appId);
            log.debug("***************SAVE DivisionPerson***************");
            divisionService.saveDivisionPerson(divisionPerson);
            DivisionPerson divisionPerson1 = new DivisionPerson();
            divisionPerson1.setId(divisionPerson.getId());
            return ResponseEntity.ok(divisionPerson1);
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Сохранение отдела")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @ResponseBody
    @RequestMapping(value = "/saveDivision.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Division> saveDivision(@RequestBody Division division, HttpServletRequest request){
        try {
            String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
            log.debug("AppId:" + appId);
            log.debug("***************SAVE division***************");
            divisionService.saveDivision(division);
            Division division1 = new Division();
            division1.setId(division.getId());
            return ResponseEntity.ok(division1);
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @ApiOperation(value = "Добавление пользователя в отдел")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @ResponseBody
    @RequestMapping(value = "/addDivisionPerson.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DivisionPerson> addDivisionPerson(@RequestBody DivisionPerson divisionPerson, HttpServletRequest request){
        try {
            String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
            log.debug("AppId:" + appId);
            log.debug("***************SAVE add person to division***************");
            divisionService.addDivisionPerson(divisionPerson);
            DivisionPerson divisionPerson1 = new DivisionPerson();
            divisionPerson1.setId(divisionPerson.getId());
            return ResponseEntity.ok(divisionPerson1);
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Добавление куратора в управление")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @ResponseBody
    @RequestMapping(value = "/addCuratorToGovernment.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CuratorPerson> addCuratorToGovernment(@RequestBody CuratorPerson curatorPerson, HttpServletRequest request){
        try {
            String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
            log.debug("AppId:" + appId);
            log.debug("***************SAVE CuratorPerson***************");
            governmentService.addCuratorToGovernment(curatorPerson);
            CuratorPerson curatorPerson1 = new CuratorPerson();
            curatorPerson1.setId(curatorPerson.getId());
            return ResponseEntity.ok(curatorPerson1);
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //--------------------------------------Удаление----------------------------------------------------------

    @ApiOperation(value = "Удаление управления")
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @ResponseBody
    @RequestMapping(value="/deleteGovernment.action",method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteGovernment(@RequestParam Long id){
        try {
            log.debug("Take id Government " + id);
            log.debug("***************DELETE Government***************");
            governmentService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Удаление отдела")
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @ResponseBody
    @RequestMapping(value="/deleteDivision.action",method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteDivision(@RequestParam Long id){
        try {
            log.debug("Take id Division " + id);
            log.debug("***************DELETE Division***************");
            divisionService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Удаление пользователя из отдела")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @ResponseBody
    @RequestMapping(value = "/removeDivisionPerson.action", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DivisionPerson> removeDivisionPerson(@RequestParam("id") Long idDivisionPerson, HttpServletRequest request){
        try {
            String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
            log.debug("AppId:" + appId);
            log.debug("***************REMOVE DivisionPerson***************");
            DivisionPerson divisionPerson = divisionService.removeDivisionPerson(idDivisionPerson);
            return ResponseEntity.ok(divisionPerson);
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ResponseBody
    @ApiOperation(value = "Удалить куратора из управления")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @RequestMapping(value = "/removeCurator.action", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CuratorPerson> removeCurator(@RequestParam("id") Long idCurator, HttpServletRequest request) {
        String secretKey = AppConfig.SECRET_KEY;
        String appId = ApplicationUtils.getAppId(request, secretKey);
        try {
            log.debug("***************REMOVE CuratorPerson***************");
            CuratorPerson curatorPerson = governmentService.removeCuratorFromGovernment(idCurator);
            return ResponseEntity.ok(curatorPerson);
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //--------------------------------------получение ланных----------------------------------------------------------


    @ApiOperation(value = "Получить отдел")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @RequestMapping(value = "/getDivision.action", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Division getDivision(@RequestParam("id") Long idDivision, HttpServletRequest request) {
        String secretKey = AppConfig.SECRET_KEY;
        String appId = ApplicationUtils.getAppId(request, secretKey);
        return divisionService.getDivisionById(idDivision);
    }

    @ApiOperation(value = "Получить пользователей")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @RequestMapping(value = "/getPersons.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Person> getPersons(@RequestBody PersonSearchDto personSearchDto, HttpServletRequest request) {
        String secretKey = AppConfig.SECRET_KEY;
        String appId = ApplicationUtils.getAppId(request, secretKey);
        return personService.filterByRequest(personSearchDto);
    }

}
