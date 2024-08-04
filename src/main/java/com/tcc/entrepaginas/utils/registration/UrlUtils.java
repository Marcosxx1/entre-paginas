package com.tcc.entrepaginas.utils.registration;

import jakarta.servlet.http.HttpServletRequest;


public class UrlUtils {

    public static String getApplicationUrl(HttpServletRequest request) {
        String appUrl =  request.getRequestURL().toString();
        return appUrl.replace(request.getServletPath(), "");
        //return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
