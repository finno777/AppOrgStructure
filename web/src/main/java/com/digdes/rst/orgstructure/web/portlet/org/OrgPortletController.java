package com.digdes.rst.orgstructure.web.portlet.org;
import com.digdes.rst.commons.application.ApplicationUtils;
import com.digdes.rst.commons.model.ApplicationBean;
import com.digdes.rst.commons.model.RoleBean;
import com.digdes.rst.commons.permission.PermissionUtils;
import com.digdes.rst.orgstructure.persistance.config.AppConfig;
import com.digdes.rst.orgstructure.persistance.model.Application;
import com.digdes.rst.orgstructure.persistance.model.Organ;
import com.digdes.rst.orgstructure.persistance.model.Person;
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

import static com.digdes.rst.commons.portal.PortalUtils.getCurrentSkin;
import static com.digdes.rst.commons.portal.PortalUtils.getPortalName;
import static com.digdes.rst.orgstructure.persistance.config.AppConfig.GTN_ACCESS;
@Controller
@Log4j
@RequestMapping("VIEW")
public class OrgPortletController {

    static private final String M="MANAGER";
    static private final String D="DEPUTY";
    static private final String A="ADVISER";

    @Autowired
    ApplicationService applicationService;
    @Autowired
    PersonService personService;
    @Autowired
    AttributeService attributeService;
    @Autowired
    OrganService organService;
    @Autowired
    GovernmentService governmentService;
    @Autowired
    AttachmentService attachmentService;

    @RenderMapping(params = "action=redirectMain")
    public ModelAndView redirectMain(RenderRequest request, RenderResponse response) throws Exception {
        return view(request, response);
    }

    @RenderMapping
    public ModelAndView view(RenderRequest request, RenderResponse response) throws Exception{
        String currentPortal = getCurrentSkin();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        String editMode=request.getParameter("editMode");
        Application application = applicationService.initApp(applicationBean.getAppId(), applicationBean.getNodeId(),applicationBean.getPageUri());
        ModelAndView model=new ModelAndView(currentPortal + "/orgStr");
            model.addObject("headerAppId", GTN_ACCESS);
            model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
            model.addObject("people", personService.getPeopleByApp(application));
            model.addObject("nodeId", applicationBean.getNodeId());
            model.addObject("organs", organService.getOrgansByApp(application));
            model.addObject("managers",personService.getPersonByRole(M));
            model.addObject("deputes",personService.getPersonByRole(D));
            model.addObject("advisers",personService.getPersonByRole(A));
            model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
            model.addObject("attributes",attributeService.getAllAttributes());
            model.addObject("editMode", editMode);
            model.addObject("allAttachOrgan", organService.getAllOrganAttach());
            model.addObject("allAttachPerson",personService.getAllPersonAttach());
            try {
                String personId = request.getParameter("id");
                Long l = Long.valueOf(personId);
                model.addObject("person", personService.findPerson(l));
                model.addObject("attachPerson", personService.getAttachPerson(l));
                model.addObject("attribute",attributeService.getAttributeByPerson(l));
                String organId = request.getParameter("idOrgan");
                Long o = Long.valueOf(organId);
                model.addObject("organ", organService.findOrganById(o));
                model.addObject("attachOrgan", organService.getAttachOrgan(o));
            } catch (Exception e) {
                e.getMessage();
            }
        return  model;
    }
    @RenderMapping(params = "action=renderPerson")
    public ModelAndView renderPerson(RenderRequest request, RenderResponse response){
        String currentPortal = getCurrentSkin();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        Application application = applicationService.initApp(applicationBean.getAppId(), applicationBean.getNodeId(), applicationBean.getPageUri());
        ModelAndView model =new ModelAndView(currentPortal +"/person");
        model.addObject("headerAppId", GTN_ACCESS);
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        model.addObject("nodeId", applicationBean.getNodeId());
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        try {
            String personKey="person";
            String personId = request.getParameter("id");
            Long l= Long.valueOf(personId);
            Person person = personService.findPerson(l);
            model.addObject("attachPerson", personService.getAttachPerson(l));
            person.setCuratorGovernments(governmentService.getGovernmentByCuratorPerson(l));
            model.addObject("adviser", personService.getAdviser(personKey,personId));
            model.addObject("person", person);
            model.addObject("attribute",attributeService.getAttributeByPerson(l));
            model.addObject("email", person.getEmail());
            model.addObject("people",personService.getPeopleByApp(application));
            model.addObject("managers",personService.getPersonByRole(M));
            model.addObject("deputes",personService.getPersonByRole(D));
            model.addObject("organs", organService.getOrgansByApp(application));
            model.addObject("allAttachPerson",personService.getAllPersonAttach());
        }
        catch ( Exception e){
            e.getMessage();
        }
        return model;
    }
    @RenderMapping(params = "action=renderOrgan")
    public ModelAndView renderOrgan(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        Application application = applicationService.initApp(applicationBean.getAppId(), applicationBean.getNodeId(), applicationBean.getPageUri());
        ModelAndView model =new ModelAndView(currentPortal+"/organ");
            model.addObject("headerAppId", GTN_ACCESS);
            model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
            model.addObject("nodeId", applicationBean.getNodeId());
            model.addObject("appId", application.getAppId());
            model.addObject("portalName", Util.getPortalRequestContext().getUserPortalConfig().getPortalName());
            model.addObject("appName", applicationBean.getAppName());
            model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
            model.addObject("people",personService.getPeopleByApp(application));
            model.addObject("managers",personService.getPersonByRole(M));
            model.addObject("deputes",personService.getPersonByRole(D));
            try {
                String organId = request.getParameter("idOrgan");
                Long o = Long.valueOf(organId);
                Organ organById = organService.findOrganById(o);
                model.addObject("organ", organById);
                model.addObject("attachOrgan", organService.getAttachOrgan(o));
                model.addObject("organs", organService.getOrgansByApp(application));
            } catch (Exception e) {
                e.getMessage();
            }
        return model;
    }
    @RenderMapping(params = "action=renderAddOrgan")
    public ModelAndView renderAddOrgan(RenderRequest request, RenderResponse response){
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        Application application = applicationService.initApp(applicationBean.getAppId(), applicationBean.getNodeId(), applicationBean.getPageUri());
        ModelAndView model =new ModelAndView(currentPortal+"/addOrgan");
        String skinName = getCurrentSkin();;
        model.addObject("skinName", skinName);
        String organId= request.getParameter("idOrgan");
        String role=request.getParameter("role");
        if (PermissionUtils.getRoles(request, response, AppConfig.SECRET_KEY).contains(RoleBean.MANAGER)) {
            if(organId!=null && !organId.isEmpty()) {
                Long o = Long.valueOf(organId);
                model.addObject("organ", organService.findOrganById(o));
                model.addObject("attachOrgan", organService.getAttachOrgan(o));
            }
            model.addObject("headerAppId", GTN_ACCESS);
            model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
            model.addObject("nodeId", applicationBean.getNodeId());
            model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(), applicationBean));
            model.addObject("appId", application.getAppId());
            model.addObject("portalName", Util.getPortalRequestContext().getUserPortalConfig().getPortalName());
            model.addObject("appName", applicationBean.getAppName());
            model.addObject("role", role);
        }
        return  model;
    }
    @RenderMapping(params = "action=createPerson")
    public ModelAndView createPerson(RenderRequest request, RenderResponse response) throws Exception {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        Application application = applicationService.initApp(applicationBean.getAppId(), applicationBean.getNodeId(), applicationBean.getPageUri());
        ModelAndView model=new ModelAndView(currentPortal+"/createPerson");
        String skinName = getCurrentSkin();;
        model.addObject("skinName", skinName);
        String role=request.getParameter("role");
        String managerId=request.getParameter("managerId");
        String organId=request.getParameter("organId");
        String personId = request.getParameter("id");
        if (PermissionUtils.getRoles(request, response, AppConfig.SECRET_KEY).contains(RoleBean.MANAGER)) {
            if(personId!=null && !personId.isEmpty()) {
                Long l= Long.valueOf(personId);
                model.addObject("person",personService.findPerson(l));
                model.addObject("attachPerson", personService.getAttachPerson(l));
                model.addObject("attribute",attributeService.getAttributeByPerson(l));
            }
            model.addObject("appId",application.getAppId());
            model.addObject("portalName",Util.getPortalRequestContext().getUserPortalConfig().getPortalName());
            model.addObject("appName",applicationBean.getAppName());
            model.addObject("headerAppId", GTN_ACCESS);
            model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
            model.addObject("nodeId", applicationBean.getNodeId());
            model.addObject("role", role);
            model.addObject("managerId", managerId);
            model.addObject("organId", organId);
            model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
            model.addObject("governments",governmentService.getAllGovernments());
            model.addObject("people",personService.getPeopleByApp(application));
            model.addObject("managers",personService.getPersonByRole(M));
            model.addObject("deputes",personService.getPersonByRole(D));
            model.addObject("organs", organService.getOrgansByApp(application));
        }
        return model;
    }

    private String getCurrentSite() {
        log.debug("Get portlet");
        return Util.getPortalRequestContext().getUserPortalConfig().getPortalName();
    }

}