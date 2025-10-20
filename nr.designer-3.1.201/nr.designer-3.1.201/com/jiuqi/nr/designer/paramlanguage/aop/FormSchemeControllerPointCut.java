/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType
 *  javax.servlet.http.HttpServletRequest
 *  org.aspectj.lang.JoinPoint
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Before
 *  org.aspectj.lang.annotation.Pointcut
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.nr.designer.paramlanguage.aop;

import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.designer.paramlanguage.service.LanguageTypeService;
import com.jiuqi.nr.designer.paramlanguage.service.ParamLanguageService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
public class FormSchemeControllerPointCut {
    @Autowired
    private ParamLanguageService paramLanguageService;
    @Autowired
    private LanguageTypeService languageTypeService;

    @Pointcut(value="execution (* com.jiuqi.nr.designer.web.rest.FormSchemeController..*(..))")
    public void formSchemeControllerPointcut() {
    }

    @Before(value="formSchemeControllerPointcut()")
    public void setlanguage(JoinPoint joinPoint) {
    }

    @Pointcut(value="execution (* com.jiuqi.nr.designer.web.rest.FormSchemeController.init(..))")
    public void formSchemesInitPointcut() {
    }

    @Around(value="formSchemesInitPointcut()")
    public Object afterInitSchemesDefine(ProceedingJoinPoint point) throws Throwable {
        String defaultLanguage;
        List list = (List)point.proceed();
        boolean enableMultiLanguage = this.paramLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || null == list || list.size() == 0) {
            return list;
        }
        String languageType = defaultLanguage = this.languageTypeService.queryDefaultLanguage();
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        Map parameterMap = request.getParameterMap();
        if (parameterMap.get("language") != null && ((String[])parameterMap.get("language")).length != 0) {
            languageType = ((String[])parameterMap.get("language"))[0];
        }
        if (!languageType.equals(defaultLanguage)) {
            ArrayList<Map> languageList = new ArrayList<Map>();
            for (Map map : list) {
                String formSchemeKey = map.get("key").toString();
                String languageInfo = this.paramLanguageService.queryLanguage(formSchemeKey, LanguageResourceType.SCHEMETITLE, languageType);
                if (languageInfo != null) {
                    map.put("title", languageInfo);
                }
                languageList.add(map);
            }
            return languageList;
        }
        return list;
    }
}

