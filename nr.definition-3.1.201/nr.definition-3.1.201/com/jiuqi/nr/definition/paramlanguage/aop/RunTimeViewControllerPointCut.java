/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Pointcut
 */
package com.jiuqi.nr.definition.paramlanguage.aop;

import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DesignRegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.impl.RegionTabSettingData;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeBigDataDefine;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeFormDefine;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeFormGroupDefine;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeFormSchemeDefine;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeRegionSettingDefine;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeTaskDefine;
import com.jiuqi.nr.definition.paramlanguage.cache.ParamLanguageCache;
import com.jiuqi.nr.definition.paramlanguage.common.LanguageTypeUtil;
import com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService;
import java.util.ArrayList;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

@Aspect
public class RunTimeViewControllerPointCut {
    @Autowired
    private ParamLanguageCache paramLanguageCache;
    @Autowired
    private DefaultLanguageService defaultLanguageService;
    @Autowired
    private LanguageTypeUtil languageTypeUtil;

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController.queryTaskDefine(..))")
    public void queryTaskDefinePointcut() {
    }

    @Around(value="queryTaskDefinePointcut()")
    public Object afterQueryTaskDefine(ProceedingJoinPoint point) throws Throwable {
        TaskDefine taskDefine = (TaskDefine)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || null == taskDefine || this.isDefaultLanguage()) {
            return taskDefine;
        }
        I18nRunTimeTaskDefine i18nTaskDefine = new I18nRunTimeTaskDefine(taskDefine);
        i18nTaskDefine.setTitle(this.paramLanguageCache.queryParamLanguage(taskDefine.getKey(), this.languageTypeUtil.getCurrentLanguageType()));
        return i18nTaskDefine;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController.queryFormSchemeByTask(..))")
    public void queryFormSchemeByTaskPointcut() {
    }

    @Around(value="queryFormSchemeByTaskPointcut()")
    public Object afterQueryFormSchemeByTask(ProceedingJoinPoint point) throws Throwable {
        List formSchemeList = (List)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || CollectionUtils.isEmpty(formSchemeList) || this.isDefaultLanguage()) {
            return formSchemeList;
        }
        ArrayList<I18nRunTimeFormSchemeDefine> list = new ArrayList<I18nRunTimeFormSchemeDefine>(formSchemeList.size());
        for (FormSchemeDefine formSchemeDefine : formSchemeList) {
            I18nRunTimeFormSchemeDefine schemeDefine = new I18nRunTimeFormSchemeDefine(formSchemeDefine);
            schemeDefine.setTitle(this.paramLanguageCache.queryParamLanguage(schemeDefine.getKey(), this.languageTypeUtil.getCurrentLanguageType()));
            list.add(schemeDefine);
        }
        return list;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController.getFormScheme(..))")
    public void queryFormSchemeByKeyPointcut() {
    }

    @Around(value="queryFormSchemeByKeyPointcut()")
    public Object afterQueryFormSchemeByKey(ProceedingJoinPoint point) throws Throwable {
        FormSchemeDefine formScheme = (FormSchemeDefine)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || null == formScheme || this.isDefaultLanguage()) {
            return formScheme;
        }
        I18nRunTimeFormSchemeDefine schemeDefine = new I18nRunTimeFormSchemeDefine(formScheme);
        schemeDefine.setTitle(this.paramLanguageCache.queryParamLanguage(schemeDefine.getKey(), this.languageTypeUtil.getCurrentLanguageType()));
        return schemeDefine;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController.queryRootGroupsByFormScheme(..))")
    public void queryRootGroupsByFormSchemePointcut() {
    }

    @Around(value="queryRootGroupsByFormSchemePointcut()")
    public Object afterQueryRootGroupsByFormScheme(ProceedingJoinPoint point) throws Throwable {
        List formGroupDefines = (List)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || CollectionUtils.isEmpty(formGroupDefines) || this.isDefaultLanguage()) {
            return formGroupDefines;
        }
        ArrayList<I18nRunTimeFormGroupDefine> list = new ArrayList<I18nRunTimeFormGroupDefine>(formGroupDefines.size());
        for (FormGroupDefine formGroupDefine : formGroupDefines) {
            I18nRunTimeFormGroupDefine i18nRunTimeFormGroupDefine = new I18nRunTimeFormGroupDefine(formGroupDefine);
            i18nRunTimeFormGroupDefine.setTitle(this.paramLanguageCache.queryParamLanguage(formGroupDefine.getKey(), this.languageTypeUtil.getCurrentLanguageType()));
            list.add(i18nRunTimeFormGroupDefine);
        }
        return list;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController.queryAllFormDefinesByFormScheme(..))")
    public void getAllFormsInFormSchemePointcut() {
    }

    @Around(value="getAllFormsInFormSchemePointcut()")
    public Object afterGetAllFormsInFormScheme(ProceedingJoinPoint point) throws Throwable {
        List formDefines = (List)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || CollectionUtils.isEmpty(formDefines) || this.isDefaultLanguage()) {
            return formDefines;
        }
        ArrayList<I18nRunTimeFormDefine> list = new ArrayList<I18nRunTimeFormDefine>(formDefines.size());
        for (FormDefine formDefine : formDefines) {
            I18nRunTimeFormDefine i18nRunTimeFormDefine = new I18nRunTimeFormDefine(formDefine);
            i18nRunTimeFormDefine.setTitle(this.paramLanguageCache.queryParamLanguage(i18nRunTimeFormDefine.getKey(), this.languageTypeUtil.getCurrentLanguageType()));
            byte[] bytes = this.paramLanguageCache.queryBigDataParamLanguage(i18nRunTimeFormDefine.getKey(), "FILLING_GUIDE", this.languageTypeUtil.getCurrentLanguageType());
            if (null != bytes) {
                i18nRunTimeFormDefine.setFillingGuide(DesignFormDefineBigDataUtil.bytesToString(bytes));
            }
            list.add(i18nRunTimeFormDefine);
        }
        return list;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController.getAllFormsInGroup(..))")
    public void getAllFormsInGroupPointcut() {
    }

    @Around(value="getAllFormsInGroupPointcut()")
    public Object afterGetAllFormsInGroup(ProceedingJoinPoint point) throws Throwable {
        List formDefines = (List)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || CollectionUtils.isEmpty(formDefines) || this.isDefaultLanguage()) {
            return formDefines;
        }
        ArrayList<I18nRunTimeFormDefine> list = new ArrayList<I18nRunTimeFormDefine>(formDefines.size());
        for (FormDefine formDefine : formDefines) {
            I18nRunTimeFormDefine i18nRunTimeFormDefine = new I18nRunTimeFormDefine(formDefine);
            i18nRunTimeFormDefine.setTitle(this.paramLanguageCache.queryParamLanguage(i18nRunTimeFormDefine.getKey(), this.languageTypeUtil.getCurrentLanguageType()));
            byte[] bytes = this.paramLanguageCache.queryBigDataParamLanguage(i18nRunTimeFormDefine.getKey(), "FILLING_GUIDE", this.languageTypeUtil.getCurrentLanguageType());
            if (null != bytes) {
                i18nRunTimeFormDefine.setFillingGuide(DesignFormDefineBigDataUtil.bytesToString(bytes));
            }
            list.add(i18nRunTimeFormDefine);
        }
        return list;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController.getAllFormsInGroupWithoutOrder(..))")
    public void getAllFormsInGroupWithoutOrderPointcut() {
    }

    @Around(value="getAllFormsInGroupWithoutOrderPointcut()")
    public Object afterGetAllFormsInGroupWithoutOrder(ProceedingJoinPoint point) throws Throwable {
        List formDefines = (List)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || CollectionUtils.isEmpty(formDefines) || this.isDefaultLanguage()) {
            return formDefines;
        }
        ArrayList<I18nRunTimeFormDefine> list = new ArrayList<I18nRunTimeFormDefine>(formDefines.size());
        for (FormDefine formDefine : formDefines) {
            I18nRunTimeFormDefine i18nRunTimeFormDefine = new I18nRunTimeFormDefine(formDefine);
            i18nRunTimeFormDefine.setTitle(this.paramLanguageCache.queryParamLanguage(i18nRunTimeFormDefine.getKey(), this.languageTypeUtil.getCurrentLanguageType()));
            byte[] bytes = this.paramLanguageCache.queryBigDataParamLanguage(i18nRunTimeFormDefine.getKey(), "FILLING_GUIDE", this.languageTypeUtil.getCurrentLanguageType());
            if (null != bytes) {
                i18nRunTimeFormDefine.setFillingGuide(DesignFormDefineBigDataUtil.bytesToString(bytes));
            }
            list.add(i18nRunTimeFormDefine);
        }
        return list;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController.queryFormById(..))")
    public void getFormByIdPointcut() {
    }

    @Around(value="getFormByIdPointcut()")
    public Object afterGetFormById(ProceedingJoinPoint point) throws Throwable {
        FormDefine resourFormDefine = (FormDefine)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || null == resourFormDefine || this.isDefaultLanguage()) {
            return resourFormDefine;
        }
        I18nRunTimeFormDefine i18nRunTimeFormDefine = new I18nRunTimeFormDefine(resourFormDefine);
        i18nRunTimeFormDefine.setTitle(this.paramLanguageCache.queryParamLanguage(i18nRunTimeFormDefine.getKey(), this.languageTypeUtil.getCurrentLanguageType()));
        byte[] bytes = this.paramLanguageCache.queryBigDataParamLanguage(i18nRunTimeFormDefine.getKey(), "FILLING_GUIDE", this.languageTypeUtil.getCurrentLanguageType());
        if (null != bytes) {
            i18nRunTimeFormDefine.setFillingGuide(DesignFormDefineBigDataUtil.bytesToString(bytes));
        }
        return i18nRunTimeFormDefine;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController.getReportDataFromForm(..))")
    public void getReportDataFromFormPointcut() {
    }

    @Around(value="getReportDataFromFormPointcut()")
    public Object afterGetReportDataFromForm(ProceedingJoinPoint point) throws Throwable {
        BigDataDefine bigDataDefine = (BigDataDefine)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || null == bigDataDefine || this.isDefaultLanguage()) {
            return bigDataDefine;
        }
        I18nRunTimeBigDataDefine i18nRunTimeBigDataDefine = new I18nRunTimeBigDataDefine(bigDataDefine);
        i18nRunTimeBigDataDefine.setData(this.paramLanguageCache.queryBigDataParamLanguage(bigDataDefine.getKey(), "FORM_DATA", this.languageTypeUtil.getCurrentLanguageType()));
        return i18nRunTimeBigDataDefine;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController.getAllReportTaskDefines(..))")
    public void getAllReportTaskDefinesPointcut() {
    }

    @Around(value="getAllReportTaskDefinesPointcut()")
    public Object afterGetAllReportTaskDefines(ProceedingJoinPoint point) throws Throwable {
        List taskDefineList = (List)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || CollectionUtils.isEmpty(taskDefineList) || this.isDefaultLanguage()) {
            return taskDefineList;
        }
        ArrayList<I18nRunTimeTaskDefine> list = new ArrayList<I18nRunTimeTaskDefine>(taskDefineList.size());
        for (TaskDefine t : taskDefineList) {
            I18nRunTimeTaskDefine i18nRunTimeTaskDefine = new I18nRunTimeTaskDefine(t);
            i18nRunTimeTaskDefine.setTitle(this.paramLanguageCache.queryParamLanguage(t.getKey(), this.languageTypeUtil.getCurrentLanguageType()));
            list.add(i18nRunTimeTaskDefine);
        }
        return list;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController.getRegionSetting(..))")
    public void getRegionSettingPointcut() {
    }

    @Around(value="getRegionSettingPointcut()")
    public Object afterGetRegionSetting(ProceedingJoinPoint point) throws Throwable {
        RegionSettingDefine regionSettingDefine = (RegionSettingDefine)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || null == regionSettingDefine || this.isDefaultLanguage()) {
            return regionSettingDefine;
        }
        I18nRunTimeRegionSettingDefine i18nRunTimeRegionSettingDefine = new I18nRunTimeRegionSettingDefine(regionSettingDefine);
        byte[] otherData = this.paramLanguageCache.queryBigDataParamLanguage(regionSettingDefine.getKey(), "REGION_TAB", this.languageTypeUtil.getCurrentLanguageType());
        byte[] defaultData = this.paramLanguageCache.queryBigDataParamLanguage(regionSettingDefine.getKey(), "REGION_TAB", this.defaultLanguageService.getDefaultLanguage());
        if (null == otherData) {
            if (defaultData != null) {
                ArrayList<RegionTabSettingDefine> regionTabSettingDataList = new ArrayList<RegionTabSettingDefine>(RegionTabSettingData.bytesToRegionTabSettingData(defaultData));
                i18nRunTimeRegionSettingDefine.setRegionTabSetting(regionTabSettingDataList);
            }
        } else if (defaultData != null) {
            List<DesignRegionTabSettingDefine> defaultDatas = RegionTabSettingData.bytesToRegionTabSettingData(defaultData);
            List<DesignRegionTabSettingDefine> otherDatas = RegionTabSettingData.bytesToRegionTabSettingData(otherData);
            RegionTabSettingData.mergeTabSetting(defaultDatas, otherDatas);
            RegionTabSettingData.transAllData(defaultDatas);
            ArrayList<RegionTabSettingDefine> regionTabSettingDataList = new ArrayList<RegionTabSettingDefine>(defaultDatas);
            i18nRunTimeRegionSettingDefine.setRegionTabSetting(regionTabSettingDataList);
        }
        return i18nRunTimeRegionSettingDefine;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController.getAllRunTimeTasksInGroup(..))")
    public void queryTaskDefineInGroupPointcut() {
    }

    @Around(value="queryTaskDefineInGroupPointcut()")
    public Object afterqueryTaskDefineInGroupPointcut(ProceedingJoinPoint point) throws Throwable {
        List taskDefines = (List)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || CollectionUtils.isEmpty(taskDefines) || this.isDefaultLanguage()) {
            return taskDefines;
        }
        ArrayList<I18nRunTimeTaskDefine> list = new ArrayList<I18nRunTimeTaskDefine>(taskDefines.size());
        for (TaskDefine t : taskDefines) {
            I18nRunTimeTaskDefine i18nRunTimeTaskDefine = new I18nRunTimeTaskDefine(t);
            i18nRunTimeTaskDefine.setTitle(this.paramLanguageCache.queryParamLanguage(t.getKey(), this.languageTypeUtil.getCurrentLanguageType()));
            list.add(i18nRunTimeTaskDefine);
        }
        return list;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController.getAllTaskDefines(..))")
    public void queryAllTaskDefinePointcut() {
    }

    @Around(value="queryAllTaskDefinePointcut()")
    public Object afterqueryAllTaskDefinePointcut(ProceedingJoinPoint point) throws Throwable {
        List taskDefines = (List)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || CollectionUtils.isEmpty(taskDefines) || this.isDefaultLanguage()) {
            return taskDefines;
        }
        ArrayList<I18nRunTimeTaskDefine> list = new ArrayList<I18nRunTimeTaskDefine>(taskDefines.size());
        for (TaskDefine t : taskDefines) {
            I18nRunTimeTaskDefine i18nRunTimeTaskDefine = new I18nRunTimeTaskDefine(t);
            i18nRunTimeTaskDefine.setTitle(this.paramLanguageCache.queryParamLanguage(t.getKey(), this.languageTypeUtil.getCurrentLanguageType()));
            list.add(i18nRunTimeTaskDefine);
        }
        return list;
    }

    @Pointcut(value="execution (* com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController.getAllFormGroupsInFormScheme(..))")
    public void queryAllFormGroupInSchemePointcut() {
    }

    @Around(value="queryAllFormGroupInSchemePointcut()")
    public Object afterqueryAllFormGroupInSchemePointcutPointcut(ProceedingJoinPoint point) throws Throwable {
        List formGroupDefines = (List)point.proceed();
        boolean enableMultiLanguage = this.defaultLanguageService.checkEnableMultiLanguage();
        if (!enableMultiLanguage || CollectionUtils.isEmpty(formGroupDefines) || this.isDefaultLanguage()) {
            return formGroupDefines;
        }
        ArrayList<I18nRunTimeFormGroupDefine> list = new ArrayList<I18nRunTimeFormGroupDefine>(formGroupDefines.size());
        for (FormGroupDefine formGroupDefine : formGroupDefines) {
            I18nRunTimeFormGroupDefine i18nRunTimeFormGroupDefine = new I18nRunTimeFormGroupDefine(formGroupDefine);
            i18nRunTimeFormGroupDefine.setTitle(this.paramLanguageCache.queryParamLanguage(formGroupDefine.getKey(), this.languageTypeUtil.getCurrentLanguageType()));
            list.add(i18nRunTimeFormGroupDefine);
        }
        return list;
    }

    private boolean isDefaultLanguage() {
        return this.languageTypeUtil.getCurrentLanguageType() == this.defaultLanguageService.getDefaultLanguage();
    }
}

