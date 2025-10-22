/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
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

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.designer.paramlanguage.service.LanguageTypeService;
import com.jiuqi.nr.designer.paramlanguage.service.ParamLanguageService;
import com.jiuqi.nr.designer.web.facade.FormulaSchemeObj;
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
public class FormulaSchemeControllerPointCut {
    @Autowired
    private ParamLanguageService paramLanguageService;
    @Autowired
    private LanguageTypeService languageTypeService;

    @Pointcut(value="execution (* com.jiuqi.nr.designer.web.rest.FormulaSchemeController..*(..))")
    public void formulaSchemeControllerPointcut() {
    }

    @Before(value="formulaSchemeControllerPointcut()")
    public void setlanguage(JoinPoint joinPoint) {
    }

    @Pointcut(value="execution (* com.jiuqi.nr.designer.web.rest.FormulaSchemeController.query(..))")
    public void queryPointcut() {
    }

    @Around(value="queryPointcut()")
    public Object afterQuery(ProceedingJoinPoint point) throws Throwable {
        String defaultLanguage;
        List list = (List)point.proceed();
        boolean enableMultiLanguage = this.paramLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || null == list || list.size() == 0) {
            return list;
        }
        NpContext context = NpContextHolder.getContext();
        String languageType = defaultLanguage = this.languageTypeService.queryDefaultLanguage();
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        Map parameterMap = request.getParameterMap();
        if (parameterMap.get("language") != null && ((String[])parameterMap.get("language")).length != 0) {
            languageType = ((String[])parameterMap.get("language"))[0];
        }
        if (!languageType.equals(defaultLanguage)) {
            ArrayList<FormulaSchemeObj> languageList = new ArrayList<FormulaSchemeObj>();
            for (FormulaSchemeObj formulaScheme : list) {
                String languageInfo = this.paramLanguageService.queryLanguage(formulaScheme.getKey(), LanguageResourceType.FORMULASCHEMETITLE, languageType);
                if (languageInfo != null) {
                    formulaScheme.setTitle(languageInfo);
                }
                languageList.add(formulaScheme);
            }
            list.clear();
            list.addAll(languageList);
            return list;
        }
        return list;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.designer.web.rest.FormulaSchemeController.queryByKey(..))")
    public void queryByKeyPointcut() {
    }

    @Around(value="queryByKeyPointcut()")
    public Object afterQueryByKey(ProceedingJoinPoint point) throws Throwable {
        String defaultLanguage;
        FormulaSchemeObj formulaSchemeObj = (FormulaSchemeObj)point.proceed();
        boolean enableMultiLanguage = this.paramLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || null == formulaSchemeObj) {
            return formulaSchemeObj;
        }
        NpContext context = NpContextHolder.getContext();
        String languageType = defaultLanguage = this.languageTypeService.queryDefaultLanguage();
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        Map parameterMap = request.getParameterMap();
        if (parameterMap.get("language") != null && ((String[])parameterMap.get("language")).length != 0) {
            languageType = ((String[])parameterMap.get("language"))[0];
        }
        if (!languageType.equals(defaultLanguage)) {
            String languageInfo = this.paramLanguageService.queryLanguage(formulaSchemeObj.getKey(), LanguageResourceType.FORMULASCHEMETITLE, languageType);
            if (languageInfo != null) {
                formulaSchemeObj.setTitle(languageInfo);
            }
            return formulaSchemeObj;
        }
        return formulaSchemeObj;
    }
}

