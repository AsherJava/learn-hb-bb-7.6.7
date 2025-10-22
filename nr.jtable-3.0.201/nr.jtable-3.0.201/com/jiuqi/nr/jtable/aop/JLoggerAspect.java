/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.np.log.enums.OperResult
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  org.aspectj.lang.JoinPoint
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Before
 *  org.aspectj.lang.annotation.Pointcut
 *  org.aspectj.lang.reflect.MethodSignature
 */
package com.jiuqi.nr.jtable.aop;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.np.log.enums.OperResult;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.jtable.annotation.JLoggable;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormulaSchemeData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.LogParam;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeysInfo;
import com.jiuqi.nr.jtable.params.output.EntityByKeysReturnInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.service.IJtableBase;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component(value="JLoggerAspect")
public class JLoggerAspect {
    private static final Logger logger = LoggerFactory.getLogger(JLoggerAspect.class);
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDataentryFlowService dataentryFlowService;
    private static final String[] FILTER_STR = new String[]{"\u5bfc\u5165\u6570\u636e", "\u6279\u91cf\u5bfc\u51fa\u6587\u4ef6", "\u6267\u884c\u4e0a\u62a5", "\u6267\u884c\u6279\u91cf\u4e0a\u62a5", "\u6dfb\u52a0\u6279\u6ce8", "\u4fee\u6539\u6279\u6ce8"};

    @Pointcut(value="@annotation(com.jiuqi.nr.jtable.annotation.JLoggable)")
    public void loggerMethod() {
    }

    @Before(value="loggerMethod()")
    public void wrapMethod(JoinPoint point) throws Throwable {
        Method method = ((MethodSignature)MethodSignature.class.cast(point.getSignature())).getMethod();
        JLoggable classLogable = method.getAnnotation(JLoggable.class);
        try {
            if (null != classLogable) {
                this.log(point, classLogable);
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    public void log(JtableContext jtableContext, String moudle, String title) {
        StringBuilder valueBuilder = this.buildMessing(jtableContext, null, title);
        String[] splits = title.split(";");
        LogHelper.info((String)moudle, (String)splits[0], (String)valueBuilder.toString(), (OperResult)OperResult.SUCCESS, (OperLevel)OperLevel.USER_OPER);
    }

    public void log(JtableContext jtableContext, String title) {
        this.log(jtableContext, "\u6570\u636e\u5f55\u5165", title);
    }

    private void log(JoinPoint point, JLoggable classLogable) throws Exception {
        String title = classLogable.value();
        String moudle = classLogable.modula();
        JtableContext jtableContext = null;
        Object[] args = point.getArgs();
        if (null != args && args.length > 0) {
            LogParam logParam = null;
            List<String> forms = new ArrayList<String>();
            for (Object object : args) {
                if (null == object) continue;
                if (object instanceof JtableContext) {
                    jtableContext = (JtableContext)object;
                    break;
                }
                if (object instanceof JtableLog) {
                    Object formObject;
                    Map<String, Object> orherMsg;
                    jtableContext = ((JtableLog)object).getContext();
                    logParam = ((JtableLog)object).getLogParam();
                    if (logParam == null) break;
                    if (logParam.getTitle() != null) {
                        title = logParam.getTitle();
                    }
                    if (logParam.getModule() != null) {
                        moudle = logParam.getModule();
                    }
                    if ((orherMsg = logParam.getOrherMsg()) == null || orherMsg.size() <= 0 || (formObject = orherMsg.get("forms")) == null) break;
                    forms = (List)formObject;
                    break;
                }
                if (!(object instanceof IJtableBase)) continue;
                jtableContext = ((IJtableBase)object).getContext();
                break;
            }
            StringBuilder valueBuilder = this.buildMessing(jtableContext, logParam, title, forms);
            LogHelper.info((String)moudle, (String)title, (String)valueBuilder.toString(), (OperResult)OperResult.SUCCESS, (OperLevel)OperLevel.USER_OPER);
        }
    }

    private StringBuilder buildMessing(JtableContext jtableContext, String moudle, String title) {
        return this.buildMessing(jtableContext, null, title, null);
    }

    private StringBuilder buildMessing(JtableContext jtableContext, LogParam logParam, String title, List<String> formKeys) {
        String[] infos = title.split(";");
        title = infos[0];
        StringBuilder valueBuilder = new StringBuilder();
        if (null != jtableContext) {
            Map<String, DimensionValue> dimensionSet;
            jtableContext.toString();
            String taskKey = jtableContext.getTaskKey();
            if (!StringUtils.isEmpty((String)taskKey)) {
                String[] splits = taskKey.split(";");
                String taskTitle = "";
                for (String oneTaskKey : splits) {
                    TaskDefine queryTaskDefine = null;
                    try {
                        queryTaskDefine = this.runtimeView.queryTaskDefine(oneTaskKey);
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                    if (null == queryTaskDefine) continue;
                    taskTitle = "".equals(taskTitle) ? queryTaskDefine.getTitle() : taskTitle + ";" + queryTaskDefine.getTitle();
                }
                taskKey = taskTitle;
            } else {
                taskKey = "\u6240\u6709\u4efb\u52a1";
            }
            valueBuilder.append("\u4efb\u52a1\u540d\u79f0:").append(taskKey).append(", ");
            String formSchemeKey = jtableContext.getFormSchemeKey();
            if (!StringUtils.isEmpty((String)formSchemeKey)) {
                String[] splits = formSchemeKey.split(";");
                String formSchemeKeyTitle = "";
                for (String oneFormSchemeKey : splits) {
                    FormSchemeDefine formSchemeDefine = null;
                    try {
                        formSchemeDefine = this.runtimeView.getFormScheme(oneFormSchemeKey);
                    }
                    catch (Exception exception) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + exception.getMessage(), exception);
                    }
                    if (null == formSchemeDefine) continue;
                    formSchemeKeyTitle = "".equals(formSchemeKeyTitle) ? formSchemeDefine.getTitle() : formSchemeKeyTitle + ";" + formSchemeDefine.getTitle();
                }
                formSchemeKey = formSchemeKeyTitle;
            } else {
                formSchemeKey = "\u6240\u6709\u62a5\u8868\u65b9\u6848";
            }
            valueBuilder.append("\u62a5\u8868\u65b9\u6848\u540d\u79f0:").append(formSchemeKey).append(", ");
            List<String> filterStr = Arrays.asList(FILTER_STR);
            if (!filterStr.contains(title)) {
                String formulaSchemeKey = jtableContext.getFormulaSchemeKey();
                if (!StringUtils.isEmpty((String)formulaSchemeKey)) {
                    String[] splits = formulaSchemeKey.split(";");
                    String formulaSchemeTitle = "";
                    for (String string : splits) {
                        FormulaSchemeData formulaSchemeData = null;
                        try {
                            formulaSchemeData = this.jtableParamService.getFormulaScheme(string);
                        }
                        catch (Exception e) {
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        }
                        if (null == formulaSchemeData) continue;
                        formulaSchemeTitle = "".equals(formulaSchemeTitle) ? formulaSchemeData.getTitle() : formulaSchemeTitle + ";" + formulaSchemeData.getTitle();
                    }
                    formulaSchemeKey = formulaSchemeTitle;
                } else {
                    formulaSchemeKey = "\u6240\u6709\u516c\u5f0f\u65b9\u6848";
                }
                valueBuilder.append("\u516c\u5f0f\u65b9\u6848\u540d\u79f0:").append(formulaSchemeKey).append(", ");
            }
            String unilType = "";
            String dateType = "";
            HashMap<String, String> entityViewsMap = new HashMap<String, String>();
            if (null != jtableContext.getFormSchemeKey() && !"".equals(jtableContext.getFormSchemeKey())) {
                EntityViewData dwEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey());
                unilType = dwEntity.getDimensionName();
                EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
                dateType = dataTimeEntity.getDimensionName();
                for (EntityViewData entityViewData : this.jtableParamService.getEntityList(jtableContext.getFormSchemeKey())) {
                    entityViewsMap.put(entityViewData.getDimensionName(), entityViewData.getKey());
                }
            }
            if ((dimensionSet = jtableContext.getDimensionSet()) == null) {
                if (infos.length == 2) {
                    valueBuilder.append("\u9644\u4ef6\u540d\u79f0:").append(infos[1]);
                } else if (infos.length == 3) {
                    valueBuilder.append("\u9644\u4ef6\u540d\u79f0:").append(infos[1]);
                    valueBuilder.append(", ").append("\u9644\u4ef6\u5bc6\u7ea7:").append(infos[2]);
                }
                return valueBuilder;
            }
            FormSchemeDefine formScheme = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
            for (Map.Entry<String, DimensionValue> entry : dimensionSet.entrySet()) {
                String value;
                if (entry.getKey().equals(dateType)) {
                    String periodTitle = periodProvider.getPeriodTitle(entry.getValue().getValue());
                    valueBuilder.append("\u65f6\u671f:").append(entry.getKey()).append("|").append(periodTitle).append(", ");
                    continue;
                }
                if (entry.getKey().equals(unilType)) {
                    value = entry.getValue().getValue();
                    String unitTitle = null;
                    unitTitle = logParam != null && logParam.getKeyInfo() != null ? "\u76ee\u6807\u5355\u4f4d:" : "\u5355\u4f4d:";
                    if (StringUtils.isEmpty((String)value)) {
                        valueBuilder.append(unitTitle).append(entry.getKey()).append("|").append("\u6240\u6709\u5355\u4f4d").append(", ");
                        continue;
                    }
                    this.appendEntity(jtableContext, valueBuilder, entityViewsMap, entry, unitTitle);
                    continue;
                }
                value = entry.getValue().getValue();
                if (StringUtils.isEmpty((String)value)) {
                    valueBuilder.append("\u7ef4\u5ea6\u540d:").append(entry.getKey()).append("|").append("\u6240\u6709\u503c").append(",");
                    continue;
                }
                this.appendEntity(jtableContext, valueBuilder, entityViewsMap, entry, "\u7ef4\u5ea6");
            }
            StringBuilder stringBuilder = new StringBuilder();
            if (logParam != null && "\u6c47\u603b".equals(logParam.getKeyInfo())) {
                stringBuilder.setLength(0);
                stringBuilder.append("\u6c47\u603b\u8303\u56f4:");
                stringBuilder.append(logParam.getOrherMsg().get("recursive")).append(",\u62a5\u8868:");
            }
            if (logParam != null && "\u9009\u62e9\u6c47\u603b".equals(logParam.getKeyInfo())) {
                stringBuilder.setLength(0);
                stringBuilder.append("\u6c47\u603b\u8303\u56f4:");
                String unitKeys = (String)logParam.getOrherMsg().get("sourceKeys");
                this.appendEntity(jtableContext, stringBuilder, entityViewsMap, unilType, unitKeys);
            }
            valueBuilder.append((CharSequence)stringBuilder);
            String formKey = jtableContext.getFormKey();
            if (logParam != null && logParam.getOrherMsg().get("formKeys") != null) {
                Object object = logParam.getOrherMsg().get("formKeys");
                if (object instanceof List) {
                    List tempFormKeys = (List)object;
                    formKey = tempFormKeys.stream().collect(Collectors.joining(";"));
                } else {
                    formKey = (String)logParam.getOrherMsg().get("formKeys");
                }
            }
            if (!(StringUtils.isEmpty((String)formKey) || "\u6267\u884c\u5168\u5ba1".equals(title) || formKey.equals("all"))) {
                valueBuilder.append("\u62a5\u8868\uff1a");
                String[] splits = formKey.split(";");
                boolean addFlag = false;
                for (String oneFormKey : splits) {
                    FormDefine formDefine = null;
                    try {
                        formDefine = this.runtimeView.queryFormById(oneFormKey);
                    }
                    catch (Exception e) {
                        logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                    }
                    if (null == formDefine) continue;
                    if (addFlag) {
                        valueBuilder.append(",");
                    }
                    valueBuilder.append(formDefine.getFormCode()).append("|").append(formDefine.getTitle());
                    addFlag = true;
                }
            } else {
                valueBuilder.append("\u62a5\u8868\uff1a");
                formKey = "\u6240\u6709\u62a5\u8868";
                valueBuilder.append(formKey);
            }
            if (logParam != null && "\u6d41\u7a0b\u64cd\u4f5c".equals(logParam.getKeyInfo())) {
                WorkFlowType workFlowType = this.dataentryFlowService.queryStartType(jtableContext.getFormSchemeKey());
                if (WorkFlowType.FORM.equals((Object)workFlowType)) {
                    valueBuilder.append("\u62a5\u8868\uff1a");
                    if (jtableContext.getFormKey() != null) {
                        boolean addFlag = false;
                        FormDefine formDefine = null;
                        try {
                            formDefine = this.runtimeView.queryFormById(jtableContext.getFormKey());
                        }
                        catch (Exception e) {
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        }
                        if (null != formDefine) {
                            if (addFlag) {
                                valueBuilder.append(",");
                            }
                            valueBuilder.append(formDefine.getFormCode()).append("|").append(formDefine.getTitle());
                            addFlag = true;
                        }
                    } else {
                        valueBuilder.append("\u6240\u6709\u62a5\u8868");
                    }
                }
                if (WorkFlowType.GROUP.equals((Object)workFlowType)) {
                    valueBuilder.append("\u62a5\u8868\u5206\u7ec4\uff1a");
                    if (jtableContext.getFormGroupKey() != null) {
                        boolean addFlag = false;
                        FormGroupDefine formGroup = null;
                        try {
                            formGroup = this.runtimeView.queryFormGroup(jtableContext.getFormGroupKey());
                        }
                        catch (Exception e) {
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        }
                        if (null != formGroup) {
                            if (addFlag) {
                                valueBuilder.append(",");
                            }
                            valueBuilder.append(formGroup.getCode()).append("|").append(formGroup.getTitle());
                            addFlag = true;
                        }
                    } else {
                        valueBuilder.append("\u6240\u6709\u62a5\u8868\u5206\u7ec4");
                    }
                }
                if (WorkFlowType.ENTITY.equals((Object)workFlowType) && formKeys != null && formKeys.size() > 0) {
                    valueBuilder.append("\u62a5\u8868\uff1a");
                    List forms = this.runtimeView.queryFormsById(formKeys);
                    for (FormDefine formDefine : forms) {
                        valueBuilder.append(formDefine.getFormCode()).append("|").append(formDefine.getTitle()).append(",");
                    }
                }
            }
            if (logParam != null && logParam.getKeyInfo() != null && logParam.getKeyInfo().contains("\u8282\u70b9\u68c0\u67e5")) {
                valueBuilder.append(", \u8bef\u5dee\u8303\u56f4:").append(logParam.getOrherMsg().get("errorRange")).append("");
            }
        } else {
            valueBuilder.append("\u672a\u8bb0\u5f55\u73af\u5883\u4fe1\u606f").append("");
        }
        if (infos.length == 2) {
            valueBuilder.append(", ").append("\u9644\u4ef6\u540d\u79f0:").append(infos[1]);
        } else if (infos.length == 3) {
            valueBuilder.append(", ").append("\u9644\u4ef6\u540d\u79f0:").append(infos[1]);
            valueBuilder.append(", ").append("\u9644\u4ef6\u5bc6\u7ea7:").append(infos[2]);
        }
        return valueBuilder;
    }

    private String toString(Object obj) {
        if (null == obj) {
            return null;
        }
        return obj.toString();
    }

    private void appendEntity(JtableContext jtableContext, StringBuilder valueBuilder, Map<String, String> entityViewsMap, Map.Entry<String, DimensionValue> entry, String title) {
        EntityQueryByKeysInfo entityQueryByKeysInfo = new EntityQueryByKeysInfo();
        String entityViewKey = entityViewsMap.get(entry.getKey());
        Map<String, EntityData> entitys = null;
        if (null != entityViewKey && !"".equals(entityViewKey)) {
            entityQueryByKeysInfo.setEntityViewKey(entityViewKey);
            String[] split = entry.getValue().getValue().split(";");
            entityQueryByKeysInfo.setEntityKeys(Arrays.asList(split));
            entityQueryByKeysInfo.setContext(jtableContext);
            EntityByKeysReturnInfo entityByKeyReturnInfo = this.jtableEntityService.queryEntityDataByKeys(entityQueryByKeysInfo);
            entitys = entityByKeyReturnInfo.getEntitys();
        }
        valueBuilder.append(title);
        if (null != entitys && entitys.size() > 0) {
            boolean addFlag = true;
            for (Map.Entry retrunEnity : entitys.entrySet()) {
                if (addFlag) {
                    valueBuilder.append(((EntityData)retrunEnity.getValue()).getCode()).append("|").append(((EntityData)retrunEnity.getValue()).getTitle());
                } else {
                    valueBuilder.append(",").append(((EntityData)retrunEnity.getValue()).getCode()).append("|").append(((EntityData)retrunEnity.getValue()).getTitle());
                }
                addFlag = false;
            }
        } else {
            valueBuilder.append(title).append("\u503c:").append(entry.getValue().getValue());
        }
        valueBuilder.append(", ");
    }

    private void appendEntity(JtableContext jtableContext, StringBuilder stringBuilder, Map<String, String> entityViewsMap, String unilType, String units) {
        EntityQueryByKeysInfo entityQueryByKeysInfo = new EntityQueryByKeysInfo();
        String entityViewKey = entityViewsMap.get(unilType);
        entityQueryByKeysInfo.setEntityViewKey(entityViewKey);
        String[] split = units.split(";");
        entityQueryByKeysInfo.setEntityKeys(Arrays.asList(split));
        entityQueryByKeysInfo.setContext(jtableContext);
        EntityByKeysReturnInfo entityByKeyReturnInfo = this.jtableEntityService.queryEntityDataByKeys(entityQueryByKeysInfo);
        Map<String, EntityData> entitys = entityByKeyReturnInfo.getEntitys();
        if (null != entitys && entitys.size() > 0) {
            boolean addFlag = true;
            for (Map.Entry<String, EntityData> retrunEnity : entitys.entrySet()) {
                if (addFlag) {
                    stringBuilder.append(retrunEnity.getValue().getCode()).append("|").append(retrunEnity.getValue().getTitle());
                } else {
                    stringBuilder.append(",").append(retrunEnity.getValue().getCode()).append("|").append(retrunEnity.getValue().getTitle());
                }
                addFlag = false;
            }
        }
    }
}

