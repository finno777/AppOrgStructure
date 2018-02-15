package com.digdes.rst.orgstructure.web.portlet.government;


import com.digdes.rst.commons.application.ApplicationUtils;
import com.digdes.rst.commons.model.ApplicationBean;
import com.digdes.rst.commons.permission.PermissionUtils;
import com.digdes.rst.orgstructure.persistance.config.AppConfig;
import com.digdes.rst.orgstructure.persistance.model.*;
import com.digdes.rst.orgstructure.persistance.services.*;
import lombok.extern.log4j.Log4j;
import org.exoplatform.portal.webui.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import java.util.List;

import static com.digdes.rst.commons.portal.PortalUtils.getCurrentSkin;

@Log4j
@Controller
@RequestMapping("VIEW")
public class    GovernmentPortletController {

    @Autowired
    GovernmentApplicationService governmentApplicationService;

    @Autowired
    GovernmentService governmentService;

    @Autowired
    DivisionService divisionService;

    @Autowired
    SubGroupOrganizationsService subGroupOrganizationsService;

    @Autowired
    GroupOrganizationsService groupOrganizationsService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    PersonService personService;

    static private final String MAIN_PAGE = "government";
    static private final String DIVISIONS_PAGE = "divisions";
    static private final String GOVERNMENT_PAGE = "governmentView";
    static private final String EDIT_DIVISIONS_PAGE = "editDivisions";
    static private final String EDIT_GOVERNMENT_PAGE = "editGovernment";
    static private final String SINGLE_DIVISION_PAGE = "singleDivision";
    static private final String EDIT_LIST_GOVERNMENT_PAGE = "editListGovernment";
    static private final String ADD_GOVERNMENT_PERSON_PAGE = "addGovernmentPerson";
    static private final String ADD_DIVISION_PERSON_PAGE = "addDivisionPerson";


    @RenderMapping
    public ModelAndView renderMain(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        String userId = request.getRemoteUser();
        GovernmentApplication application = governmentApplicationService.initApp(applicationBean.getAppId(),applicationBean.getPageUri());
        ModelAndView model = new ModelAndView(currentPortal + "/" +MAIN_PAGE);
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        model.addObject("governments", governmentService.getAllGovernmentByApp(application));
        String skinName = getCurrentSkin();
        model.addObject("skinName", skinName);
        return model;
    }

    @RenderMapping(params = "action=divisionsView")
    public ModelAndView renderDivisions(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        String userId = request.getRemoteUser();
        GovernmentApplication application = governmentApplicationService.initApp(applicationBean.getAppId(), applicationBean.getPageUri());
        ModelAndView model = new ModelAndView(currentPortal + "/" +DIVISIONS_PAGE);
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        String skinName = getCurrentSkin();
        model.addObject("skinName", skinName);
        String Id = request.getParameter("id");
        Long governmentID  = Long.valueOf(Id);
        List<Division> divisions = divisionService.getDivisionsByGovernment(governmentID);
        model.addObject("divisions", divisions);
        return model;
    }

    @RenderMapping(params = "action=editGovernmentView")
    public ModelAndView renderEditGovernment(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        String userId = request.getRemoteUser();
        GovernmentApplication application = governmentApplicationService.initApp(applicationBean.getAppId(), applicationBean.getPageUri());
        ModelAndView model = new ModelAndView(currentPortal + "/" +EDIT_GOVERNMENT_PAGE);
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        String skinName = getCurrentSkin();
        model.addObject("skinName", skinName);
        String Id = request.getParameter("id");
        if(Id!=null && !Id.isEmpty()) {
            Long governmentID = Long.valueOf(Id);
            Government government = governmentService.getGovernmentById(governmentID);
            model.addObject("government", government);
        }
        model.addObject("organizations", organizationService.getOrganizationGovernment());
        model.addObject("groupOrganizations", groupOrganizationsService.getGroupOrganizationGovernment());
        model.addObject("subGroupOrganizations", subGroupOrganizationsService.getSubGroupOrganizationGovernment());
        List<Person> manager = personService.getPersonByRole("MANAGER");
        manager.addAll(personService.getPersonByRole("DEPUTY"));
        model.addObject("curators", manager);

        model.addObject("appId",application.getAppId());
        model.addObject("portalName",Util.getPortalRequestContext().getUserPortalConfig().getPortalName());
        model.addObject("appName",applicationBean.getAppName());

        return model;
    }

    @RenderMapping(params = "action=singleGovernmentView")
    public ModelAndView renderGovernment(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        ModelAndView model = new ModelAndView(currentPortal + "/" +GOVERNMENT_PAGE);
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        String skinName = getCurrentSkin();
        model.addObject("skinName", skinName);
        String Id = request.getParameter("id");
        Long governmentID = Long.valueOf(Id);
        model.addObject("government", governmentService.getGovernmentById(governmentID));
        return model;
    }

    @RenderMapping(params = "action=editListGovernmentView")
    public ModelAndView renderEditListGovernment(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        String userId = request.getRemoteUser();
        GovernmentApplication application = governmentApplicationService.initApp(applicationBean.getAppId(), applicationBean.getPageUri());
        ModelAndView model = new ModelAndView(currentPortal + "/" +EDIT_LIST_GOVERNMENT_PAGE);
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        String skinName = getCurrentSkin();
        model.addObject("skinName", skinName);
        List<Government> governments = governmentService.getAllGovernmentByApp(application);
        model.addObject("governments", governments);

        model.addObject("appId",application.getAppId());
        model.addObject("portalName",Util.getPortalRequestContext().getUserPortalConfig().getPortalName());
        model.addObject("appName",applicationBean.getAppName());



        return model;
    }

    @RenderMapping(params = "action=singledDivisionsView")
    public ModelAndView renderSingledDivisions(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        String Id = request.getParameter("id");
        Long divisionID=Long.valueOf(Id);
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        String userId = request.getRemoteUser();
        GovernmentApplication application = governmentApplicationService.initApp(applicationBean.getAppId(), applicationBean.getPageUri());
        ModelAndView model = new ModelAndView(currentPortal + "/" +SINGLE_DIVISION_PAGE);

        String governmentId = request.getParameter("governmentId");
        Long governmentID=Long.valueOf(governmentId);
        model.addObject("government", governmentService.getGovernmentById(governmentID));
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        String skinName = getCurrentSkin();
        model.addObject("skinName", skinName);
        Division division = divisionService.getDivisionById(divisionID);
        model.addObject("division", division);
        List<Government> governments = governmentService.getAllGovernmentByApp(application);
        model.addObject("governments", governments);
        List<Division> divisions = divisionService.getDivisionsByGovernment(division.getGovernment().getId());
        model.addObject("divisions", divisions);
        return model;
    }

    @RenderMapping(params = "action=editDivisionsView")
    public ModelAndView renderEditDivisions(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        String userId = request.getRemoteUser();
        GovernmentApplication application = governmentApplicationService.initApp(applicationBean.getAppId(), applicationBean.getPageUri());
        ModelAndView model = new ModelAndView(currentPortal + "/" +EDIT_DIVISIONS_PAGE);
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        String skinName = getCurrentSkin();
        model.addObject("skinName", skinName);
        String Id = request.getParameter("id");
        if(Id!=null && !Id.isEmpty()) {
            Long divisionID = Long.valueOf(Id);
            Division division = divisionService.getDivisionById(divisionID);
            model.addObject("divisions", division);
        }
        String Id_g = request.getParameter("governmentId");
        if(Id_g!=null && !Id_g.isEmpty()) {
            Long governmentID = Long.valueOf(Id_g);
            Government government = governmentService.getGovernmentById(governmentID);
            model.addObject("government", government);
        }
        model.addObject("appId",application.getAppId());
        model.addObject("portalName",Util.getPortalRequestContext().getUserPortalConfig().getPortalName());
        model.addObject("appName",applicationBean.getAppName());
        return model;
    }

    @RenderMapping(params = "action=addGovernmentPersonView")
    public ModelAndView renderGovernmentPerson(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        String userId = request.getRemoteUser();
        GovernmentApplication application = governmentApplicationService.initApp(applicationBean.getAppId(), applicationBean.getPageUri());
        ModelAndView model = new ModelAndView(currentPortal + "/" + ADD_GOVERNMENT_PERSON_PAGE);
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        String skinName = getCurrentSkin();
        model.addObject("skinName", skinName);
        String Id = request.getParameter("id");
        Long governmentID=Long.valueOf(Id);
        String Id_p = request.getParameter("personId");
        log.debug("personId : " + Id_p );
        Government governmentById = governmentService.getGovernmentById(governmentID);
        model.addObject("government", governmentById);
        if(Id_p!=null && !Id_p.isEmpty()) {
            Long personID = Long.valueOf(Id_p);
            GovernmentPerson governmentPerson = governmentService.getGovernmentPersonByID(governmentById.getPerson().getId());
            //Person person = personService.getPerson(personID);
            model.addObject("person", governmentPerson);
        }

        model.addObject("appId",application.getAppId());
        model.addObject("portalName",Util.getPortalRequestContext().getUserPortalConfig().getPortalName());
        model.addObject("appName",applicationBean.getAppName());
        return model;
    }

    @RenderMapping(params = "action=addDivisionPersonView")
    public ModelAndView renderDivisionPerson(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        String Id = request.getParameter("id");
        Long divisionID=Long.valueOf(Id);
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        String userId = request.getRemoteUser();

        GovernmentApplication application = governmentApplicationService.initApp(applicationBean.getAppId(), applicationBean.getPageUri());
        ModelAndView model = new ModelAndView(currentPortal + "/" + ADD_DIVISION_PERSON_PAGE);
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        String Id_g = request.getParameter("governmentId");
        if(Id_g!=null && !Id_g.isEmpty()) {
            Long governmentID = Long.valueOf(Id_g);
            Government government = governmentService.getGovernmentById(governmentID);
            model.addObject("government", government);
        }
        String Id_p = request.getParameter("divisionPersonId");
        if(Id_p!=null && !Id_p.isEmpty()) {
            Long divisionPersonId = Long.valueOf(Id_p);
            DivisionPerson person = divisionService.getDivisionPerson(divisionPersonId);
            model.addObject("person", person);
        }
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        String skinName = getCurrentSkin();
        model.addObject("skinName", skinName);
        model.addObject("division",divisionService.getDivisionById(divisionID));
        model.addObject("appId",application.getAppId());
        model.addObject("portalName",Util.getPortalRequestContext().getUserPortalConfig().getPortalName());
        model.addObject("appName",applicationBean.getAppName());
        return model;
    }

    @RenderMapping(params = "action=redirectMain")
    public ModelAndView redirectMain(RenderRequest request, RenderResponse response) {
        return renderMain(request, response);
    }


    private String getCurrentSite() {
        return Util.getPortalRequestContext().getUserPortalConfig().getPortalName();
    }

}
