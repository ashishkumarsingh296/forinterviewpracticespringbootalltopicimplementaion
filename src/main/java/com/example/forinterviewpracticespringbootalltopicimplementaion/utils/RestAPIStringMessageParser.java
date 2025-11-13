package com.example.forinterviewpracticespringbootalltopicimplementaion.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;

@Component
public class RestAPIStringMessageParser {

    private final MessageSource messageSource;

    public RestAPIStringMessageParser(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private Locale getLocaleFromRequest() {
        Locale locale = LocaleContextHolder.getLocale(); // default
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest request = attrs.getRequest();
            if (request != null) {
                String language = request.getHeader("language");
                String country = request.getHeader("country");
                if (language != null && country != null) {
                    locale = new Locale(language, country);
                }
            }
        }
        return locale;
    }

    public String getMessage(String code) {
        return messageSource.getMessage(code, null, getLocaleFromRequest());
    }

    public String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, getLocaleFromRequest());
    }
}
