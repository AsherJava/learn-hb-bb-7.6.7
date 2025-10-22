/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.util.StringUtils
 *  org.aspectj.lang.ProceedingJoinPoint
 *  org.aspectj.lang.annotation.Around
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Pointcut
 *  org.aspectj.lang.reflect.MethodSignature
 */
package com.jiuqi.nr.jtable.aop;

import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.jtable.annotation.LevelAuthRead;
import com.jiuqi.nr.jtable.annotation.LevelAuthWrite;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IUnitAuthorityServive;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.util.StringUtils;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component(value="LevelAuthDealAspect")
public class LevelAuthDealAspect {
    private static final Logger logger = LoggerFactory.getLogger(LevelAuthDealAspect.class);
    private static final String CONTEXT = "context";
    private static final String FORMSCHEMEKEY = "formSchemeKey";
    private static final String DIMENSIONSET = "dimensionSet";
    private static final String VARIABLEMAP = "variableMap";
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private List<IUnitAuthorityServive> iUnitAuthorityServives;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthorityProvider;

    @Pointcut(value="@annotation(com.jiuqi.nr.jtable.annotation.LevelAuthWrite)")
    public void authWriteMethod() {
    }

    @Pointcut(value="@annotation(com.jiuqi.nr.jtable.annotation.LevelAuthRead)")
    public void authReadMethod() {
    }

    @Around(value="authWriteMethod()")
    public Object executeAroundWrite(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        block11: {
            Method method = ((MethodSignature)MethodSignature.class.cast(proceedingJoinPoint.getSignature())).getMethod();
            AnnotatedType annotatedReturnType = method.getAnnotatedReturnType();
            String returnClassName = annotatedReturnType.getType().getTypeName();
            LevelAuthWrite levelAuthWrite = method.getAnnotation(LevelAuthWrite.class);
            try {
                Object[] args;
                if (null == levelAuthWrite || null == (args = proceedingJoinPoint.getArgs()) || args.length <= 0) break block11;
                for (Object object : args) {
                    Date time;
                    boolean canWriteTask;
                    if (null == object) continue;
                    JtableContext context = this.getContext(object);
                    if (context.getTaskKey() != null && !(canWriteTask = this.definitionAuthorityProvider.canWriteTask(context.getTaskKey())) && !annotatedReturnType.equals(Void.TYPE)) {
                        Class<?> className = Class.forName(returnClassName);
                        return className.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    }
                    String formSchemeKey = context.getFormSchemeKey();
                    Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
                    if (formSchemeKey == null || dimensionSet == null) continue;
                    FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
                    String mainDimName = this.getMainDimName(formSchemeKey);
                    DimensionValue dimensionValue = dimensionSet.get(mainDimName);
                    DimensionValue periodDimensionValue = dimensionSet.get("DATATIME");
                    String entityKeyData = dimensionValue.getValue();
                    if (StringUtils.isEmpty((String)entityKeyData) || "00000000-0000-0000-0000-000000000000".equals(entityKeyData)) {
                        return proceedingJoinPoint.proceed();
                    }
                    boolean contains = entityKeyData.contains(";");
                    if (contains) {
                        return proceedingJoinPoint.proceed();
                    }
                    try {
                        time = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime()).getPeriodDateRegion(periodDimensionValue.getValue())[1];
                    }
                    catch (Exception e) {
                        GregorianCalendar period2Calendar = PeriodUtil.period2Calendar((String)periodDimensionValue.getValue());
                        time = period2Calendar.getTime();
                    }
                    try {
                        String entityId = StringUtils.isNotEmpty((String)DsContextHolder.getDsContext().getContextEntityId()) ? DsContextHolder.getDsContext().getContextEntityId() : formScheme.getDw();
                        boolean canWriteEntity = this.entityAuthorityService.canWriteEntity(entityId, entityKeyData, null, time);
                        if (canWriteEntity) {
                            return proceedingJoinPoint.proceed();
                        }
                        if (annotatedReturnType.equals(Void.TYPE)) continue;
                        Class<?> className = Class.forName(returnClassName);
                        return className.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                    }
                    catch (UnauthorizedEntityException e) {
                        logger.error("\u6743\u9650\u4e0d\u7b26\u5408" + (Object)((Object)e));
                    }
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        return null;
    }

    public IUnitAuthorityServive getUnitAuthorityServive(String accessCode) {
        if (StringUtils.isEmpty((String)accessCode)) {
            accessCode = "dataentry";
        }
        for (IUnitAuthorityServive iUnitAuthorityServive : this.iUnitAuthorityServives) {
            String accessCode2 = iUnitAuthorityServive.getAccessCode();
            if (!accessCode.equals(accessCode2)) continue;
            return iUnitAuthorityServive;
        }
        return null;
    }

    @Around(value="authReadMethod()")
    public Object executeAroundRead(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Method method = ((MethodSignature)MethodSignature.class.cast(proceedingJoinPoint.getSignature())).getMethod();
        AnnotatedType annotatedReturnType = method.getAnnotatedReturnType();
        String returnClassName = annotatedReturnType.getType().getTypeName();
        LevelAuthRead levelAuthRead = method.getAnnotation(LevelAuthRead.class);
        try {
            Object[] args;
            if (null != levelAuthRead && null != (args = proceedingJoinPoint.getArgs()) && args.length > 0) {
                for (Object object : args) {
                    IUnitAuthorityServive iUnitAuthorityServive;
                    if (null == object) continue;
                    JtableContext context = this.getContext(object);
                    Map<String, DimensionValue> dimensionSet = context.getDimensionSet();
                    if (context.getFormSchemeKey() == null || dimensionSet == null || (iUnitAuthorityServive = this.getUnitAuthorityServive(context.getAccessCode())) == null) continue;
                    boolean canReadEntity = iUnitAuthorityServive.canRead(context);
                    if (canReadEntity) {
                        return proceedingJoinPoint.proceed();
                    }
                    Class<?> className = Class.forName(returnClassName);
                    return className.newInstance();
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return null;
    }

    private String getMainDimName(String formSchemeKey) {
        EntityViewDefine entityViewDefine = this.runTimeViewController.getViewByFormSchemeKey(formSchemeKey);
        com.jiuqi.nr.entity.engine.executors.ExecutorContext context = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(this.iDataDefinitionRuntimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist((ExecutorContext)context);
        return dataAssist.getDimensionName(entityViewDefine);
    }

    private JtableContext getContext(Object object) {
        JtableContext jtableContext = new JtableContext();
        try {
            Class<?> tempObj = object.getClass();
            Method[] declaredMethods = tempObj.getDeclaredMethods();
            Method getContext = this.containContextMethod(declaredMethods);
            if (getContext != null) {
                JtableContext invoke = (JtableContext)getContext.invoke(object, new Object[0]);
                jtableContext.setTaskKey(invoke.getTaskKey());
                jtableContext.setFormSchemeKey(invoke.getFormSchemeKey());
                jtableContext.setDimensionSet(invoke.getDimensionSet());
                jtableContext.setVariableMap(invoke.getVariableMap());
                jtableContext.setAccessCode(invoke.getAccessCode());
            } else {
                for (Method method : declaredMethods) {
                    Object accessCodeObj;
                    Object variableMapObj;
                    Object dimMapObj;
                    String name = method.getName();
                    if ("getFormSchemeKey".equals(name)) {
                        Object formSchemeKey = method.invoke(object, new Object[0]);
                        jtableContext.setFormSchemeKey(formSchemeKey.toString());
                    }
                    if ("getDimensionSet".equals(name) && (dimMapObj = method.invoke(object, new Object[0])) != null) {
                        Map dimMap = (Map)dimMapObj;
                        jtableContext.setDimensionSet(dimMap);
                    }
                    if ("getVariableMap".equals(name) && (variableMapObj = method.invoke(object, new Object[0])) != null) {
                        Map variableMap = (Map)variableMapObj;
                        jtableContext.setVariableMap(variableMap);
                    }
                    if (!"getAccessCode".equals(name) || (accessCodeObj = method.invoke(object, new Object[0])) == null) continue;
                    jtableContext.setAccessCode(accessCodeObj.toString());
                }
            }
        }
        catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return jtableContext;
    }

    private Field containContextField(Field[] declaredFields) {
        for (Field field : declaredFields) {
            String name = field.getName();
            if (!CONTEXT.equals(name)) continue;
            return field;
        }
        return null;
    }

    private Method containContextMethod(Method[] declaredMethods) {
        for (Method method : declaredMethods) {
            String name = method.getName();
            if (!"getContext".equals(name)) continue;
            return method;
        }
        return null;
    }
}

