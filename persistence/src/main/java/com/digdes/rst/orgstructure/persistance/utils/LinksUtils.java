package com.digdes.rst.orgstructure.persistance.utils;

import org.gatein.pc.api.StateString;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Martynov.I
 * @since 15.12.2017
 */
public class LinksUtils {

    private static final String PARAM_ACTION = "action";

    public String createURI(String componentId, String relativeUrl,Map<String, String[]> ps){
        //Map<String, String[]> ps = new HashMap<>();
        //ps.put(PARAM_ACTION, new String[]{renderView});
        relativeUrl += "&portal:isSecure=false";
        relativeUrl += "&navigationalstate=" + StateString.encodeAsOpaqueValue(ps);
        relativeUrl += "&portal:componentId=" + componentId;
        return relativeUrl;
    }

}
