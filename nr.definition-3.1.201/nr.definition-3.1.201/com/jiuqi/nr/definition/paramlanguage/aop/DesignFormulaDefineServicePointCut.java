/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Pointcut
 *  org.springframework.web.context.request.RequestContextHolder
 */
package com.jiuqi.nr.definition.paramlanguage.aop;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageResourceType;
import com.jiuqi.nr.definition.paramlanguage.dao.DesParamLanguageDao;
import com.jiuqi.nr.definition.paramlanguage.entity.DesParamLanguage;
import com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;

@Aspect
public class DesignFormulaDefineServicePointCut {
    @Autowired
    private DefaultLanguageService defaultLanguageService;
    @Autowired
    private DesParamLanguageDao desParamLanguageDao;

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.service.DesignFormulaDefineService.queryFormulaDefineBySchemeAndForm(..))")
    public void queryFormulaDefineBySchemeAndFormPointcut() {
    }

    @Around(value="queryFormulaDefineBySchemeAndFormPointcut()")
    public Object afterQueryFormulaDefineBySchemeAndForm(ProceedingJoinPoint point) throws Throwable {
        int defaultLanguageType;
        int currentLanguageType;
        List formulaDefineList = (List)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || null == formulaDefineList || formulaDefineList.size() == 0) {
            return formulaDefineList;
        }
        if (RequestContextHolder.getRequestAttributes() == null) {
            return formulaDefineList;
        }
        String languageType = String.valueOf(this.defaultLanguageService.getDefaultLanguage());
        NpContext context = NpContextHolder.getContext();
        Object languageObj = context.getDefaultExtension().get("language");
        if (null != languageObj && StringUtils.isNotEmpty((String)String.valueOf(languageObj))) {
            languageType = String.valueOf(languageObj);
        }
        if (null != languageType && (currentLanguageType = Integer.parseInt(languageType)) != (defaultLanguageType = this.defaultLanguageService.getDefaultLanguage())) {
            for (DesignFormulaDefine designFormulaDefine : formulaDefineList) {
                List<DesParamLanguage> desParamLanguages = this.desParamLanguageDao.queryLanguage(designFormulaDefine.getKey(), LanguageResourceType.FORMULADESCRIPTION, languageType);
                String languageInfo = "";
                if (desParamLanguages.size() > 0) {
                    languageInfo = desParamLanguages.get(0).getLanguageInfo();
                }
                if (!StringUtils.isNotEmpty((String)languageInfo)) continue;
                designFormulaDefine.setDescription(languageInfo);
            }
        }
        return formulaDefineList;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.controller.IFormulaDesignTimeController.getAllFormulaSchemeDefinesByFormScheme(..))")
    public void getAllFormulaSchemeDefinesByFormSchemePointcut() {
    }

    @Around(value="getAllFormulaSchemeDefinesByFormSchemePointcut()")
    public Object afterQuerygetAllFormulaSchemeDefinesByFormSchemePointcut(ProceedingJoinPoint point) throws Throwable {
        int defaultLanguageType;
        int currentLanguageType;
        List designFormulaSchemeDefines = (List)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || null == designFormulaSchemeDefines || designFormulaSchemeDefines.size() == 0) {
            return designFormulaSchemeDefines;
        }
        if (RequestContextHolder.getRequestAttributes() == null) {
            return designFormulaSchemeDefines;
        }
        String languageType = String.valueOf(this.defaultLanguageService.getDefaultLanguage());
        NpContext context = NpContextHolder.getContext();
        Object languageObj = context.getDefaultExtension().get("language");
        if (null != languageObj && StringUtils.isNotEmpty((String)String.valueOf(languageObj))) {
            languageType = String.valueOf(languageObj);
        }
        if (null != languageType && (currentLanguageType = Integer.parseInt(languageType)) != (defaultLanguageType = this.defaultLanguageService.getDefaultLanguage())) {
            for (DesignFormulaSchemeDefine designFormulaSchemeDefine : designFormulaSchemeDefines) {
                List<DesParamLanguage> desParamLanguages = this.desParamLanguageDao.queryLanguage(designFormulaSchemeDefine.getKey(), LanguageResourceType.FORMULASCHEMETITLE, languageType);
                String languageInfo = "";
                if (desParamLanguages.size() > 0) {
                    languageInfo = desParamLanguages.get(0).getLanguageInfo();
                }
                if (!StringUtils.isNotEmpty((String)languageInfo)) continue;
                designFormulaSchemeDefine.setTitle(languageInfo);
            }
        }
        return designFormulaSchemeDefines;
    }
}

