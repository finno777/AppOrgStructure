package com.digdes.rst.orgstructure.persistance.config;

import com.digdes.rst.commons.application.ApplicationUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class AppConfig {

    private AppConfig() {
    }

    public static final String SECRET_KEY = "Rsts1a8Orgstruct";
    public static final String APP_NAME = "OgrStructure";
    public static final String GTN_ACCESS = "gtn.access.appid";
    public static final Locale DEFAULT_LOCALE = new Locale("ru");

    public static final String CONCRETE_PERSON_ACTION = "renderPerson";
    public static final String CONCRETE_ORGAN_ACTION = "renderOrgan";

    public static String getConcretePersonRelativeUrl(Long personId, String appId) {
        if (personId == null || appId == null) {
            return null;
        } else {
            Map<String, String[]> params = new HashMap<>();
            params.put("id", new String[]{String.valueOf(personId)});
            params.put("action", new String[]{CONCRETE_PERSON_ACTION});
            return ApplicationUtils.getUri(appId, params);
        }
    }

    public static String getConcreteOrganRelativeUrl(Long organId, String appId) {
        if (organId == null || appId == null) {
            return null;
        } else {
            Map<String, String[]> params = new HashMap<>();
            params.put("idOrgan", new String[]{String.valueOf(organId)});
            params.put("action", new String[]{CONCRETE_ORGAN_ACTION});
            return ApplicationUtils.getUri(appId, params);
        }
    }




}
