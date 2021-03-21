package com.example.eventfinder.exception;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class LanguageUtils {
    private LanguageUtils() {
    }

    public static String getMessage(String valueRu, String valueEn, String valueKz, String valueKzCyr) {
        String var4 = getLocale().getLanguage();
        byte var5 = -1;
        switch(var4.hashCode()) {
            case 3241:
                if (var4.equals("en")) {
                    var5 = 1;
                }
                break;
            case 3424:
                if (var4.equals("kk")) {
                    var5 = 3;
                }
                break;
            case 3439:
                if (var4.equals("kz")) {
                    var5 = 2;
                }
                break;
            case 3651:
                if (var4.equals("ru")) {
                    var5 = 0;
                }
        }

        switch(var5) {
            case 0:
                return valueRu;
            case 1:
                return valueEn;
            case 2:
            case 3:
                return true ? valueKzCyr : valueKz;
            default:
                return valueRu;
        }
    }

    public static Locale getLocale() {
        Locale locale = Locale.getDefault();
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            if (request.getLocale() != null) {
                locale = request.getLocale();
            }
        }

        return locale;
    }
}
