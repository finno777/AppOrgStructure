package com.digdes.rst.orgstructure.web.controller;


import com.digdes.rst.commons.CommonConstants;
import com.digdes.rst.commons.application.ApplicationUtils;
import com.digdes.rst.commons.model.RoleBean;
import com.digdes.rst.commons.permission.EXOPermission;
import com.digdes.rst.orgstructure.persistance.config.AppConfig;
import com.digdes.rst.orgstructure.persistance.model.*;
import com.digdes.rst.orgstructure.persistance.services.*;
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

@Controller
@RequestMapping("/subdivision")
@Log4j
public class SubDivisionController {
    @Autowired
    SubdivisionApplicationService subdivisionApplicationService;

    @Autowired
    TypeOrganizationService typeOrganizationService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    GroupOrganizationsService groupOrganizationsService;

    @Autowired
    SubGroupOrganizationsService subGroupOrganizationsService;

    //--------------------------------------Сохранение----------------------------------------------------------

    @ApiOperation(value = "Сохранение Типа организации")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @ResponseBody
    @RequestMapping(value = "/saveTypeOrganization.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TypeOrganization> saveTypeOrganization(@RequestBody TypeOrganization typeOrganization, HttpServletRequest request){
        try {
            String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
            typeOrganization.setApplication(subdivisionApplicationService.findByAppId(appId));
            log.debug("AppId:" + appId);
            log.debug("***************SAVE typeOrganization***************");
            typeOrganizationService.save(typeOrganization);
            return ResponseEntity.ok(typeOrganization);
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Сохранение Организации")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @ResponseBody
    @RequestMapping(value = "/saveOrganization.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Organization> saveOrganization(@RequestBody Organization organization, HttpServletRequest request){
        try {
            String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
            log.debug("AppId:" + appId);
            log.debug("***************SAVE Organization***************");
            organizationService.save(organization);
            return ResponseEntity.ok(organization);
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Сохранение Группы")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @ResponseBody
    @RequestMapping(value = "/saveGroupOrganizations.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupOrganizations> saveGroupOrganizations(@RequestBody GroupOrganizations groupOrganizations, HttpServletRequest request){
        try {
            String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
            log.debug("AppId:" + appId);
            log.debug("***************SAVE GroupOrganizations***************");
            groupOrganizationsService.save(groupOrganizations);
            return ResponseEntity.ok(groupOrganizations);
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Сохранение Подгруппы")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @ResponseBody
    @RequestMapping(value = "/saveSubGroupOrganizations.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubGroupOrganizations> saveSubGroupOrganizations(@RequestBody SubGroupOrganizations subGroupOrganizations, HttpServletRequest request){
        try {
            String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
            log.debug("AppId:" + appId);
            log.debug("***************SAVE SubGroupOrganizations***************");
            subGroupOrganizationsService.save(subGroupOrganizations);
            return ResponseEntity.ok(subGroupOrganizations);
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
    @RequestMapping(value = "/saveOrganizationPerson.action", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizationPerson> saveOrganizationPerson(@RequestBody OrganizationPerson organizationPerson, HttpServletRequest request){
        try {
            String appId = ApplicationUtils.getAppId(request, AppConfig.SECRET_KEY);
            log.debug("AppId:" + appId);
            log.debug("***************SAVE OrganizationPerson***************");
            organizationService.savePerson(organizationPerson);
            OrganizationPerson person = new OrganizationPerson();
            person.setId(organizationPerson.getId());
            return ResponseEntity.ok(person);
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //--------------------------------------Удаление----------------------------------------------------------

    @ApiOperation(value = "Удаление Типа организации")
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @ResponseBody
    @RequestMapping(value="/deleteTypeOrganization.action",method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteTypeOrganization(@RequestParam Long id){
        try {
            log.debug("Take id TypeOrganization " + id);
            log.debug("***************DELETE TypeOrganization***************");
            typeOrganizationService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Удаление Организации")
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @ResponseBody
    @RequestMapping(value="/deleteOrganization.action",method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteOrganization(@RequestParam Long id){
        try {
            log.debug("Take id organization " + id);
            log.debug("***************DELETE Organization***************");
            organizationService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Удаление Группы")
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @ResponseBody
    @RequestMapping(value="/deleteGroupOrganizations.action",method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteGroupOrganizations(@RequestParam Long id){
        try {
            log.debug("Take id Person" + id);
            log.debug("***************DELETE GroupOrganizations***************");
            groupOrganizationsService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Удаление Подгруппы")
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @ResponseBody
    @RequestMapping(value="/deleteSubGroupOrganizations.action",method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSubGroupOrganizations(@RequestParam Long id){
        try {
            log.debug("Take id Person" + id);
            log.debug("***************DELETE SubGroupOrganizations***************");
            subGroupOrganizationsService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ResponseBody
    @ApiOperation(value = "Удалить Участника из организации")
    @ApiImplicitParams({@ApiImplicitParam(name = CommonConstants.HEADER_APP_ID,
            dataType = "string",
            paramType = "header")})
    @EXOPermission(roles = {RoleBean.MANAGER}, secretKey = AppConfig.SECRET_KEY)
    @RequestMapping(value = "/removeOrganizationPerson.action", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganizationPerson> removeOrganizationPerson(@RequestParam("id") Long idOrganizationPerson, HttpServletRequest request) {
        String secretKey = AppConfig.SECRET_KEY;
        String appId = ApplicationUtils.getAppId(request, secretKey);
        try {
            log.debug("***************REMOVE CuratorPerson***************");
            OrganizationPerson organizationPerson = organizationService.removePerson(idOrganizationPerson);
            return ResponseEntity.ok(organizationPerson);
        }
        catch (Exception e){
            log.error("********ERROR********");
            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
