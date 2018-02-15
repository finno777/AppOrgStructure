package com.digdes.rst.orgstructure.web.portlet.subdivision;

import com.digdes.rst.commons.application.ApplicationUtils;
import com.digdes.rst.commons.model.ApplicationBean;
import com.digdes.rst.commons.permission.PermissionUtils;
import com.digdes.rst.orgstructure.persistance.config.AppConfig;
import com.digdes.rst.orgstructure.persistance.model.*;
import com.digdes.rst.orgstructure.persistance.model.SubdivisionApplication;
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

import java.util.ArrayList;
import java.util.List;

import static com.digdes.rst.commons.portal.PortalUtils.getCurrentSkin;


@Controller
@Log4j
@RequestMapping("VIEW")
public class SubdivisionPortletController {
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

    @Autowired
    OrganizationPersonService organizationPersonService;

    static private final String MAIN_PAGE = "subdivision";
    static private final String GROUP_PAGE = "group";
    static private final String ORGANIZATION_PAGE = "organization";
    static private final String EDIT_TYPE_PAGE = "editType";
    static private final String EDIT_GROUP_PAGE = "editGroup";
    static private final String EDIT_SUBGROUP_PAGE = "editSubGroup";
    static private final String EDIT_ORGANIZATION_PAGE = "editOrganization";
    static private final String EDIT_ORGANIZATION_PERSON_PAGE = "editOrganizationPerson";


    static private final String EDIT_PORTLET_PAGE = "editPortlet";


    @RenderMapping
    public ModelAndView renderMain(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        String userId = request.getRemoteUser();
        SubdivisionApplication application = subdivisionApplicationService.initApp(applicationBean.getAppId(), applicationBean.getPageUri());
        ModelAndView model = new ModelAndView(currentPortal + "/" +MAIN_PAGE);
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        String skinName = getCurrentSkin();
        model.addObject("skinName", skinName);

        List<TypeOrganization> typeOrganizations = typeOrganizationService.getFullByApp(application);
        model.addObject("typeOrganizations",typeOrganizations);

        return model;
    }

    @RenderMapping(params = "action=groupView")
    public ModelAndView renderGroup(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        String userId = request.getRemoteUser();
        SubdivisionApplication application = subdivisionApplicationService.initApp(applicationBean.getAppId(), applicationBean.getPageUri());
        ModelAndView model = new ModelAndView(currentPortal + "/" +GROUP_PAGE);
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        String skinName = getCurrentSkin();
        model.addObject("skinName", skinName);
        String Id = request.getParameter("idGroup");
        log.debug("idGroup : " + Id);
        if(Id!=null) {
            model.addObject("idSubGroup", request.getParameter("idSubGroup"));
            Long idGroup = Long.valueOf(Id);
            GroupOrganizations groupOrganization = groupOrganizationsService.getFullGroupById(idGroup);
            model.addObject("group", groupOrganization);
            if (groupOrganization.getTypeOrganization() != null && groupOrganization.getTypeOrganization().getId() != null) {
                Long idType = groupOrganization.getTypeOrganization().getId();
                log.debug("idType : " + idType);
                List<GroupOrganizations> groupOrganizations = groupOrganizationsService.getSimilarGroupByTypeId(idType);
                model.addObject("groupSimilar", groupOrganizations);
            }
            List<TypeOrganization> typeOrganizations = typeOrganizationService.getByApp(application);
            model.addObject("typeOrganizations", typeOrganizations);
        }else{
            return renderMain(request,response);
        }
        return model;
    }

    @RenderMapping(params = "action=organizationView")
    public ModelAndView renderOrganization(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        String userId = request.getRemoteUser();
        SubdivisionApplication application = subdivisionApplicationService.initApp(applicationBean.getAppId(), applicationBean.getPageUri());
        ModelAndView model = new ModelAndView(currentPortal + "/" +ORGANIZATION_PAGE);
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        String skinName = getCurrentSkin();
        model.addObject("skinName", skinName);
        String Id = request.getParameter("idOrganization");
        log.debug("idOrganization : " + Id);
        Long idOrganization  = Long.valueOf(Id);
        Organization organization = organizationService.getById(idOrganization);
        model.addObject("organization",organization);
        List<Organization> organizations = organizationService.getSimilarOrganizationByID(idOrganization);
        model.addObject("organizationsSimilar",organizations);
        SubGroupOrganizations organizationSubGroupOrganizations = organization.getSubGroupOrganizations();
        List<SubGroupOrganizations> subGroupOrganizations = new ArrayList<>();
        if(organizationSubGroupOrganizations!=null && organizationSubGroupOrganizations.getId()!=null){
            Long idSubGroup = organizationSubGroupOrganizations.getId();
            log.debug("idSubGroup : " + idSubGroup);
            subGroupOrganizations = subGroupOrganizationsService.getSimilarSubGroupByID(idSubGroup);
        }
        model.addObject("subGroupOrganizations",subGroupOrganizations);
        return model;
    }

    @RenderMapping(params = "action=editTypeView")
    public ModelAndView renderEditType(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        String userId = request.getRemoteUser();
        SubdivisionApplication application = subdivisionApplicationService.initApp(applicationBean.getAppId(),applicationBean.getPageUri());
        ModelAndView model = new ModelAndView(currentPortal + "/" +EDIT_TYPE_PAGE);
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        String skinName = getCurrentSkin();
        model.addObject("skinName", skinName);

        String Id = request.getParameter("idType");
        if(Id!=null && !Id.isEmpty()) {
            Long idType = Long.valueOf(Id);
            model.addObject("typeOrganization", typeOrganizationService.getById(idType));

        }

        model.addObject("appId",application.getAppId());
        model.addObject("portalName",Util.getPortalRequestContext().getUserPortalConfig().getPortalName());
        model.addObject("appName",applicationBean.getAppName());

        return model;
    }

    @RenderMapping(params = "action=editGroupView")
    public ModelAndView renderEditGroup(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        String userId = request.getRemoteUser();
        SubdivisionApplication application = subdivisionApplicationService.initApp(applicationBean.getAppId(), applicationBean.getPageUri());
        ModelAndView model = new ModelAndView(currentPortal + "/" +EDIT_GROUP_PAGE);
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        String skinName = getCurrentSkin();
        model.addObject("skinName", skinName);

        String Id = request.getParameter("idGroup");
        if(Id!=null && !Id.isEmpty()) {
            Long idGroup = Long.valueOf(Id);
            GroupOrganizations groupOrganizations = groupOrganizationsService.getFullGroupById(idGroup);
            model.addObject("group", groupOrganizations);
        }
        model.addObject("typeOrganizations",typeOrganizationService.getByApp(application));
        model.addObject("appId",application.getAppId());
        model.addObject("portalName",Util.getPortalRequestContext().getUserPortalConfig().getPortalName());
        model.addObject("appName",applicationBean.getAppName());

        return model;
    }

    @RenderMapping(params = "action=editSubgroupView")
    public ModelAndView renderEditSubgroup(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        String userId = request.getRemoteUser();
        SubdivisionApplication application = subdivisionApplicationService.initApp(applicationBean.getAppId(), applicationBean.getPageUri());
        ModelAndView model = new ModelAndView(currentPortal + "/" +EDIT_SUBGROUP_PAGE);
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        String skinName = getCurrentSkin();
        model.addObject("skinName", skinName);

        String Id = request.getParameter("idSubgroup");
        if(Id!=null && !Id.isEmpty()) {
            Long idSubgroup = Long.valueOf(Id);
            SubGroupOrganizations subGroupOrganizations = subGroupOrganizationsService.getById(idSubgroup);
            model.addObject("subgroup", subGroupOrganizations);
        }
        model.addObject("appId",application.getAppId());
        model.addObject("portalName",Util.getPortalRequestContext().getUserPortalConfig().getPortalName());
        model.addObject("appName",applicationBean.getAppName());

        return model;
    }

    @RenderMapping(params = "action=editOrganizationView")
    public ModelAndView renderEditOrganization(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        String userId = request.getRemoteUser();
        SubdivisionApplication application = subdivisionApplicationService.initApp(applicationBean.getAppId(), applicationBean.getPageUri());
        ModelAndView model = new ModelAndView(currentPortal + "/" +EDIT_ORGANIZATION_PAGE);
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        String skinName = getCurrentSkin();
        model.addObject("skinName", skinName);

        String Id = request.getParameter("idOrganization");
        if(Id!=null && !Id.isEmpty()) {
            Long idOrganization = Long.valueOf(Id);
            model.addObject("organization", organizationService.getById(idOrganization));
        }
        model.addObject("typeOrganizations",typeOrganizationService.getByApp(application));
        model.addObject("subGroupOrganizations",subGroupOrganizationsService.getAllActive());
        model.addObject("appId",application.getAppId());
        model.addObject("portalName",Util.getPortalRequestContext().getUserPortalConfig().getPortalName());
        model.addObject("appName",applicationBean.getAppName());

        return model;
    }

    @RenderMapping(params = "action=editPortletView")
    public ModelAndView renderEditPortlet(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        String userId = request.getRemoteUser();
        SubdivisionApplication application = subdivisionApplicationService.initApp(applicationBean.getAppId(), applicationBean.getPageUri());
        ModelAndView model = new ModelAndView(currentPortal + "/" +EDIT_PORTLET_PAGE);
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        String skinName = getCurrentSkin();
        model.addObject("skinName", skinName);
        return model;
    }

    @RenderMapping(params = "action=editOrganizationPersonView")
    public ModelAndView renderEditOrganizationPerson(RenderRequest request, RenderResponse response) {
        String currentPortal = getCurrentSite().toLowerCase().trim();
        ApplicationBean applicationBean = ApplicationUtils.getCurrentApp(request, response, AppConfig.SECRET_KEY);
        String userId = request.getRemoteUser();
        SubdivisionApplication application = subdivisionApplicationService.initApp(applicationBean.getAppId(), applicationBean.getPageUri());
        ModelAndView model = new ModelAndView(currentPortal + "/" +EDIT_ORGANIZATION_PERSON_PAGE);
        model.addObject("secretAppIdHeader", applicationBean.getSecretAppId());
        model.addObject("roles", PermissionUtils.getRoles(request.getRemoteUser(),applicationBean));
        String skinName = getCurrentSkin();
        model.addObject("skinName", skinName);
        model.addObject("appId",application.getAppId());
        model.addObject("portalName",Util.getPortalRequestContext().getUserPortalConfig().getPortalName());
        model.addObject("appName",applicationBean.getAppName());

        String Id = request.getParameter("idOrganization");
        if(Id!=null && !Id.isEmpty()) {
            Long idOrganization = Long.valueOf(Id);
            model.addObject("organization", organizationService.getById(idOrganization));
        }
        String Id_p = request.getParameter("idOrganizationPerson");
        if(Id_p!=null && !Id_p.isEmpty()) {
            Long idOrganizationPerson = Long.valueOf(Id_p);
            model.addObject("organizationPerson", organizationPersonService.getById(idOrganizationPerson));
        }
        return model;
    }

    private String getCurrentSite() {
        return Util.getPortalRequestContext().getUserPortalConfig().getPortalName();
    }
}
