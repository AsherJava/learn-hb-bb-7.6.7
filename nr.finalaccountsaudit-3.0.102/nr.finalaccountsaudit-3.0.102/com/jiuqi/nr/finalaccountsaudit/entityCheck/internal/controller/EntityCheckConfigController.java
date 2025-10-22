/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.DefaultPeriodAdapter
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.dataentry.bean.CustomPeriodData
 *  com.jiuqi.nr.dataentry.bean.FormSchemeResult
 *  com.jiuqi.nr.dataentry.bean.FormulaVariable
 *  com.jiuqi.nr.dataentry.bean.FuncExecResult
 *  com.jiuqi.nr.dataentry.internal.service.util.DateTimeUtil
 *  com.jiuqi.nr.dataentry.internal.service.util.SchemePeriodHelper
 *  com.jiuqi.nr.dataentry.model.FuncExecuteConfig
 *  com.jiuqi.nr.dataentry.paramInfo.FormSchemeData
 *  com.jiuqi.nr.dataentry.paramInfo.TaskData
 *  com.jiuqi.nr.dataentry.service.IDataEntryParamService
 *  com.jiuqi.nr.dataentry.service.ITemplateConfigService
 *  com.jiuqi.nr.dataentry.util.DataEntryUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.FillDateType
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRunTimeFormulaVariableService
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.definitionext.taskExtConfig.internal.controller.TaskExtConfigController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.exception.NotFoundTaskException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo
 *  com.jiuqi.nr.jtable.params.output.EntityData
 *  com.jiuqi.nr.jtable.params.output.EntityReturnInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.period.common.utils.PeriodUtils
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.period.DefaultPeriodAdapter;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.dataentry.bean.CustomPeriodData;
import com.jiuqi.nr.dataentry.bean.FormSchemeResult;
import com.jiuqi.nr.dataentry.bean.FormulaVariable;
import com.jiuqi.nr.dataentry.bean.FuncExecResult;
import com.jiuqi.nr.dataentry.internal.service.util.DateTimeUtil;
import com.jiuqi.nr.dataentry.internal.service.util.SchemePeriodHelper;
import com.jiuqi.nr.dataentry.model.FuncExecuteConfig;
import com.jiuqi.nr.dataentry.paramInfo.FormSchemeData;
import com.jiuqi.nr.dataentry.paramInfo.TaskData;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.service.ITemplateConfigService;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.runtime.controller.IRunTimeFormulaVariableService;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.definitionext.taskExtConfig.internal.controller.TaskExtConfigController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.controller.IEntityCheckConfigController;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.Association;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.ConfigInfo;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.EntityCheckConfigData;
import com.jiuqi.nr.finalaccountsaudit.entityCheck.taskextensionEntitycheck.common.SelectStructure;
import com.jiuqi.nr.jtable.exception.NotFoundTaskException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.EntityQueryByViewInfo;
import com.jiuqi.nr.jtable.params.output.EntityData;
import com.jiuqi.nr.jtable.params.output.EntityReturnInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.period.common.utils.PeriodUtils;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityCheckConfigController
implements IEntityCheckConfigController {
    private static final Logger logger = LoggerFactory.getLogger(EntityCheckConfigController.class);
    @Resource
    private ITemplateConfigService templateConfigService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Resource
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private SchemePeriodHelper schemePeriodHelper;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IJtableEntityService jtableEntityService;
    @Resource
    private IRunTimeFormulaVariableService formulaRuntimeTimeController;
    @Autowired
    private IFormSchemeService iFormSchemeService;
    @Autowired
    TaskExtConfigController taskExtConfigController;

    @Override
    public FuncExecResult getDataEntryInit(String taskKey) throws Exception {
        FuncExecResult result = new FuncExecResult();
        FuncExecuteConfig templateConfig = this.templateConfigService.getFuncExecuteConfigByCode("dataentry_defaultFuncode");
        if (StringUtils.isEmpty((String)taskKey)) {
            result.getFuncParam().setHaveTask(false);
            return result;
        }
        result.getFuncParam().setTemplateConfig(templateConfig);
        TaskData taskData = this.dataEntryParamService.getRuntimeTaskByKey(taskKey);
        if (taskData == null) {
            throw new NotFoundTaskException(new String[]{taskKey});
        }
        result.setTask(taskData);
        List<FormSchemeResult> schemeListResult = this.getDataEntryFormScheme(taskKey, result, templateConfig);
        List schemeKeyList = schemeListResult.stream().map(item -> item.getScheme().getKey()).collect(Collectors.toList());
        result.setSchemes(schemeListResult);
        PeriodWrapper currentPeriod = null;
        IPeriodProvider periodProvider = null;
        for (EntityViewData view : taskData.getEntitys()) {
            if (!this.periodEntityAdapter.isPeriodEntity(view.getKey())) continue;
            periodProvider = this.periodEntityAdapter.getPeriodProvider(view.getKey());
            result.setPeriodModifyTitles(periodProvider.getPeriodDataByModifyTitle());
        }
        String period = templateConfig.getConfig().getDataEntryViewConfig().getPeriod();
        List schemePeriodLinkDefineList = null;
        HashMap<String, Integer> periodSchemeMap = new HashMap<String, Integer>();
        schemePeriodLinkDefineList = this.runTimeViewController.querySchemePeriodLinkByTask(taskKey);
        Date date = DateTimeUtil.getDay();
        FillDateType fillingDateType = taskData.getFillingDateType();
        Object beginPeriodModify = null;
        if (!fillingDateType.equals((Object)FillDateType.NONE) && taskData.getFillingDateDays() >= 0) {
            Date dateAfterFormat = date;
            if (fillingDateType.equals((Object)FillDateType.NATURAL_DAY)) {
                dateAfterFormat = DateTimeUtil.getDateOfBeforeDay((Date)date, (int)(taskData.getFillingDateDays() - 1));
            } else if (fillingDateType.equals((Object)FillDateType.WORK_DAY)) {
                dateAfterFormat = DateTimeUtil.getDateOfBeforeWorkDay((Date)date, (int)(taskData.getFillingDateDays() - 1));
            }
            if (taskData.getPeriodType() != PeriodType.CUSTOM.type()) {
                Iterator periodOfFormate = PeriodUtils.getPeriodFromDate((int)taskData.getPeriodType(), (Date)dateAfterFormat);
                beginPeriodModify = periodOfFormate;
                if (taskData.getPeriodOffset() != 0) {
                    PeriodModifier periodModifier = new PeriodModifier();
                    periodModifier.setPeriodModifier(taskData.getPeriodOffset());
                    beginPeriodModify = periodProvider.modify(beginPeriodModify, periodModifier);
                }
                if (StringUtils.isNotEmpty((String)period)) {
                    try {
                        int periodOffset = Integer.parseInt(period);
                        PeriodModifier periodModifierOftemp = new PeriodModifier();
                        periodModifierOftemp.setPeriodModifier(periodOffset);
                        beginPeriodModify = periodProvider.modify((String)beginPeriodModify, periodModifierOftemp);
                    }
                    catch (Exception e) {
                        logger.info("\u5f55\u5165\u6a21\u677f\u914d\u7f6e\u7684\u65f6\u671f\u4fe1\u606f\u4e3a\u6307\u5b9a\u65f6\u671f\uff1a" + period + ",\u65e0\u9700\u8fdb\u884c\u65f6\u671f\u504f\u79fb\uff01");
                        throw new Exception("\u5f55\u5165\u6a21\u677f\u914d\u7f6e\u7684\u65f6\u671f\u4fe1\u606f\u4e3a\u6307\u5b9a\u65f6\u671f\uff1a" + period + ",\u65e0\u9700\u8fdb\u884c\u65f6\u671f\u504f\u79fb\uff01");
                    }
                }
            } else if (taskData.getPeriodOffset() != 0 || StringUtils.isNotEmpty((String)period) || taskData.getFillingDateDays() >= 0) {
                if (taskData.getFillingDateDays() >= 0) {
                    if (fillingDateType.equals((Object)FillDateType.NATURAL_DAY)) {
                        dateAfterFormat = DateTimeUtil.getDateOfBeforeDay((Date)date, (int)(taskData.getFillingDateDays() - 1));
                    } else if (fillingDateType.equals((Object)FillDateType.WORK_DAY)) {
                        dateAfterFormat = DateTimeUtil.getDateOfBeforeWorkDay((Date)date, (int)(taskData.getFillingDateDays() - 1));
                    }
                }
                for (SchemePeriodLinkDefine schemePeriodLinkDefine : schemePeriodLinkDefineList) {
                    String modify = schemePeriodLinkDefine.getPeriodKey();
                    if (taskData.getPeriodOffset() != 0) {
                        PeriodModifier periodModifier = new PeriodModifier();
                        periodModifier.setPeriodModifier(taskData.getPeriodOffset());
                        modify = periodProvider.modify(modify, periodModifier);
                    }
                    if (StringUtils.isNotEmpty((String)period)) {
                        try {
                            int periodOffset = Integer.parseInt(period);
                            PeriodModifier periodModifierOftemp = new PeriodModifier();
                            periodModifierOftemp.setPeriodModifier(periodOffset);
                            modify = periodProvider.modify(modify, periodModifierOftemp);
                        }
                        catch (Exception e) {
                            logger.info("\u5f55\u5165\u6a21\u677f\u914d\u7f6e\u7684\u65f6\u671f\u4fe1\u606f\u4e3a\u6307\u5b9a\u65f6\u671f\uff1a" + period + ",\u65e0\u9700\u8fdb\u884c\u65f6\u671f\u504f\u79fb\uff01");
                            throw new Exception("\u5f55\u5165\u6a21\u677f\u914d\u7f6e\u7684\u65f6\u671f\u4fe1\u606f\u4e3a\u6307\u5b9a\u65f6\u671f\uff1a" + period + ",\u65e0\u9700\u8fdb\u884c\u65f6\u671f\u504f\u79fb\uff01");
                        }
                    }
                    FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
                    Date[] periodRegion = new Date[]{};
                    try {
                        periodRegion = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime()).getPeriodDateRegion(modify);
                    }
                    catch (ParseException e) {
                        logger.info("\u83b7\u53d6\u81ea\u5b9a\u4e49\u65f6\u671f\u8d77\u59cb\u7ed3\u675f\u65f6\u95f4\u5931\u8d25\uff01");
                        throw new Exception("\u83b7\u53d6\u81ea\u5b9a\u4e49\u65f6\u671f\u8d77\u59cb\u7ed3\u675f\u65f6\u95f4\u5931\u8d25\uff01");
                    }
                    Date beginDate = periodRegion[0];
                    Date endDate = periodRegion[1];
                    if (!DateTimeUtil.isEffectiveDate((Date)dateAfterFormat, (Date)beginDate, (Date)endDate)) continue;
                    beginPeriodModify = schemePeriodLinkDefine.getPeriodKey();
                    break;
                }
            }
        }
        List schemePeriodLinkDefineListAfterFormat = schemePeriodLinkDefineList;
        if (taskData.getPeriodType() != PeriodType.CUSTOM.type()) {
            schemePeriodLinkDefineListAfterFormat = schemePeriodLinkDefineList.stream().sorted((periodOne, periodTwo) -> PeriodUtils.comparePeriod((String)periodOne.getPeriodKey(), (String)periodTwo.getPeriodKey())).collect(Collectors.toList());
        }
        for (Object schemePeriodLinkDefineInfo : schemePeriodLinkDefineListAfterFormat) {
            if (beginPeriodModify != null && !fillingDateType.equals((Object)FillDateType.NONE) && taskData.getFillingDateDays() >= 0) {
                if (beginPeriodModify == null || !StringUtils.isNotEmpty((String)schemePeriodLinkDefineInfo.getPeriodKey()) || !StringUtils.isNotEmpty((String)schemePeriodLinkDefineInfo.getSchemeKey()) || PeriodUtils.comparePeriod(beginPeriodModify, (String)schemePeriodLinkDefineInfo.getPeriodKey()) < 0) continue;
                periodSchemeMap.put(schemePeriodLinkDefineInfo.getPeriodKey(), schemeKeyList.indexOf(schemePeriodLinkDefineInfo.getSchemeKey()));
                continue;
            }
            if (!StringUtils.isNotEmpty((String)schemePeriodLinkDefineInfo.getPeriodKey()) || !StringUtils.isNotEmpty((String)schemePeriodLinkDefineInfo.getSchemeKey())) continue;
            periodSchemeMap.put(schemePeriodLinkDefineInfo.getPeriodKey(), schemeKeyList.indexOf(schemePeriodLinkDefineInfo.getSchemeKey()));
        }
        if (taskData.getPeriodType() == PeriodType.CUSTOM.type()) {
            ArrayList<CustomPeriodData> customPeriodDatas = new ArrayList<CustomPeriodData>();
            for (IPeriodRow periodRow : periodProvider.getPeriodItems()) {
                CustomPeriodData data = new CustomPeriodData();
                data.setCode(periodRow.getCode());
                data.setTitle(periodRow.getTitle());
                customPeriodDatas.add(data);
            }
            result.setCustomPeriodDatas(customPeriodDatas);
            IPeriodRow curPeriod = periodProvider.getCurPeriod();
            PeriodModifier periodModifier = new PeriodModifier();
            periodModifier.setPeriodModifier(taskData.getPeriodOffset());
            String modify = periodProvider.modify(curPeriod.getCode(), periodModifier);
            if (StringUtils.isNotEmpty((String)period)) {
                currentPeriod = new PeriodWrapper(modify);
                if (!periodSchemeMap.containsKey((currentPeriod = this.modifyPeriod(periodProvider, currentPeriod, period, taskData)).toString())) {
                    currentPeriod = new PeriodWrapper(modify);
                }
            } else {
                currentPeriod = new PeriodWrapper(modify);
            }
        } else {
            currentPeriod = DataEntryUtil.getCurrPeriod((int)taskData.getPeriodType(), (int)taskData.getPeriodOffset(), (String)taskData.getFromPeriod(), (String)taskData.getToPeriod());
            String currentPeriodString = currentPeriod.toString();
            if (StringUtils.isNotEmpty((String)period) && !periodSchemeMap.containsKey((currentPeriod = this.modifyPeriod(periodProvider, currentPeriod, period, taskData)).toString())) {
                currentPeriod = new PeriodWrapper(currentPeriodString);
            }
        }
        String currPeriodString = currentPeriod.toString();
        DefaultPeriodAdapter iPeriodAdapter = new DefaultPeriodAdapter();
        boolean priorPeriod = true;
        while (!periodSchemeMap.containsKey(currPeriodString)) {
            PeriodWrapper periodWrapper;
            if (priorPeriod) {
                periodWrapper = new PeriodWrapper(currPeriodString);
                boolean prevState = iPeriodAdapter.priorPeriod(periodWrapper);
                if (prevState && currPeriodString.compareTo(taskData.getFromPeriod()) >= 0) {
                    currPeriodString = periodWrapper.toString();
                    continue;
                }
                if (currPeriodString.compareTo(taskData.getFromPeriod()) >= 0) continue;
                priorPeriod = false;
                currPeriodString = currentPeriod.toString();
                continue;
            }
            periodWrapper = new PeriodWrapper(currPeriodString);
            boolean prevState = iPeriodAdapter.nextPeriod(periodWrapper);
            if (prevState && currPeriodString.compareTo(taskData.getToPeriod()) <= 0) {
                currPeriodString = periodWrapper.toString();
                continue;
            }
            if (currPeriodString.compareTo(taskData.getToPeriod()) <= 0) continue;
            currPeriodString = currentPeriod.toString();
            break;
        }
        if (StringUtils.isNotEmpty((String)period) && periodSchemeMap.containsKey(period)) {
            currPeriodString = period;
        }
        if (!periodSchemeMap.containsKey(currPeriodString)) {
            throw new Exception("\u6ca1\u6709\u5bf9\u5e94\u586b\u62a5\u671f");
        }
        List schemes = this.runTimeViewController.queryFormSchemeByTask(taskKey);
        ArrayList periodRegionList = new ArrayList();
        for (FormSchemeDefine formSchemeDefine : schemes) {
            IPeriodEntity iPeriod = this.schemePeriodHelper.schemeSearchEntity(formSchemeDefine);
            List schemePeriodLinkDefineListOfScheme = this.runTimeViewController.querySchemePeriodLinkByScheme(formSchemeDefine.getKey());
            ArrayList<SchemePeriodLinkDefine> schemePeriodLinkDefineListOfSchemeAfterFormat = new ArrayList<SchemePeriodLinkDefine>();
            if (!fillingDateType.equals((Object)FillDateType.NONE) && taskData.getFillingDateDays() >= 0) {
                for (SchemePeriodLinkDefine schemePeriodLinkDefine : schemePeriodLinkDefineListOfScheme) {
                    if (beginPeriodModify == null || PeriodUtils.comparePeriod((String)beginPeriodModify, (String)schemePeriodLinkDefine.getPeriodKey()) < 0) continue;
                    schemePeriodLinkDefineListOfSchemeAfterFormat.add(schemePeriodLinkDefine);
                }
            } else {
                schemePeriodLinkDefineListOfSchemeAfterFormat.addAll(schemePeriodLinkDefineListOfScheme);
            }
            periodRegionList.addAll(this.schemePeriodHelper.unSplitPeriod(schemePeriodLinkDefineListOfSchemeAfterFormat, iPeriod.getKey()));
        }
        result.setPeriodRangeList(periodRegionList);
        result.setCurrentPeriodInfo(currPeriodString);
        String formSchemeKey = (String)schemeKeyList.get((Integer)periodSchemeMap.get(currPeriodString));
        result.setFormSchemeKey(formSchemeKey);
        result.setPeriodSchemeMap(periodSchemeMap);
        result.setSchemeKeyList(schemeKeyList);
        return result;
    }

    private List<FormSchemeResult> getDataEntryFormScheme(String taskKey, FuncExecResult result, FuncExecuteConfig templateConfig) throws Exception {
        List schemeList = this.dataEntryParamService.runtimeFormSchemeList(taskKey);
        ArrayList<FormSchemeResult> schemeListResult = new ArrayList<FormSchemeResult>(schemeList.size());
        for (int i = 0; i < schemeList.size(); ++i) {
            JtableContext jtableContext;
            EntityQueryByViewInfo entityQueryInfo;
            FormSchemeData scheme = (FormSchemeData)schemeList.get(i);
            FormSchemeResult schemeResult = new FormSchemeResult(scheme);
            schemeListResult.add(schemeResult);
            List entitys = scheme.getEntitys();
            HashMap<String, DimensionValue> dimensions = new HashMap<String, DimensionValue>();
            if (StringUtils.isNotEmpty((String)DsContextHolder.getDsContext().getContextEntityId())) {
                Iterator entityId = DsContextHolder.getDsContext().getContextEntityId();
                EntityViewData entityViewData = new EntityViewData();
                entityViewData.initialize(this.iEntityMetaService.queryEntity(entityId));
                for (int index = 0; index < entitys.size(); ++index) {
                    if (!((EntityViewData)entitys.get(index)).isMasterEntity()) continue;
                    EntityViewData masterEntity = (EntityViewData)entitys.get(index);
                    entityViewData.setKind(masterEntity.getKind());
                    entityViewData.setMasterEntity(true);
                    entitys.set(index, entityViewData);
                }
            }
            for (EntityViewData entity : entitys) {
                if (this.periodEntityAdapter.isPeriodEntity(entity.getKey())) {
                    DimensionValue value = new DimensionValue();
                    value.setName(entity.getDimensionName());
                    if (8 == scheme.getPeriodType()) {
                        value.setType(8);
                        entityQueryInfo = new EntityQueryByViewInfo();
                        jtableContext = new JtableContext();
                        entityQueryInfo.setContext(jtableContext);
                        jtableContext.setFormSchemeKey(scheme.getKey());
                        jtableContext.setTaskKey(taskKey);
                        entityQueryInfo.setEntityViewKey(entity.getKey());
                        EntityReturnInfo queryEntityData = this.jtableEntityService.queryCustomPeriodData(entityQueryInfo);
                        ArrayList<CustomPeriodData> customPeriods = new ArrayList<CustomPeriodData>();
                        String currValue = "";
                        for (EntityData entityData : queryEntityData.getEntitys()) {
                            CustomPeriodData customPeriod = new CustomPeriodData();
                            customPeriod.setCode(entityData.getCode());
                            customPeriod.setTitle(entityData.getTitle());
                            customPeriods.add(customPeriod);
                            if (!StringUtils.isEmpty((String)currValue)) continue;
                            currValue = entityData.getCode();
                        }
                        schemeResult.setCustomPeriodDatas(customPeriods);
                        if (customPeriods.size() > 0) {
                            if (StringUtils.isNotEmpty((String)currValue)) {
                                value.setValue(currValue);
                            } else {
                                value.setValue(((CustomPeriodData)customPeriods.get(customPeriods.size() - 1)).getCode());
                            }
                            String period = templateConfig.getConfig().getDataEntryViewConfig().getPeriod();
                            if (StringUtils.isNotEmpty((String)period) && customPeriods.stream().filter(item -> item.getCode().equals(period)).count() > 0L) {
                                value.setValue(period);
                            }
                        } else {
                            value.setValue("1900N0001");
                        }
                    } else {
                        value.setType(scheme.getPeriodType());
                        PeriodWrapper currentPeriod = DataEntryUtil.getCurrPeriod((int)scheme.getPeriodType(), (int)scheme.getPeriodOffset(), (String)scheme.getFromPeriod(), (String)scheme.getToPeriod());
                        String period = templateConfig.getConfig().getDataEntryViewConfig().getPeriod();
                        if (StringUtils.isNotEmpty((String)period)) {
                            try {
                                int periodOffset = Integer.parseInt(period);
                                currentPeriod.modifyPeriod(periodOffset);
                                value.setValue(currentPeriod.toString());
                                PeriodWrapper fromPeriod = new PeriodWrapper(scheme.getFromPeriod());
                                PeriodWrapper toPeriod = new PeriodWrapper(scheme.getToPeriod());
                                if (periodOffset > 0) {
                                    if (currentPeriod.compareTo((Object)toPeriod) > 0) {
                                        value.setValue(scheme.getToPeriod());
                                    }
                                } else if (periodOffset < 0 && currentPeriod.compareTo((Object)fromPeriod) < 0) {
                                    value.setValue(scheme.getFromPeriod());
                                }
                            }
                            catch (Exception e2) {
                                value.setValue(period);
                            }
                        } else {
                            value.setValue(currentPeriod.toString());
                        }
                    }
                    dimensions.put(entity.getDimensionName(), value);
                    continue;
                }
                if (!entity.isMasterEntity()) continue;
                DimensionValue value = new DimensionValue();
                value.setName(entity.getDimensionName());
                dimensions.put(entity.getDimensionName(), value);
                value.setValue("");
            }
            for (EntityViewData entity : entitys) {
                if (this.judgementPeriodView(entity.getKey()) || entity.isMasterEntity()) continue;
                DimensionValue value = new DimensionValue();
                value.setName(entity.getDimensionName());
                value.setValue("");
                dimensions.put(entity.getDimensionName(), value);
                entityQueryInfo = new EntityQueryByViewInfo();
                jtableContext = new JtableContext();
                jtableContext.setDimensionSet(dimensions);
                entityQueryInfo.setContext(jtableContext);
                jtableContext.setFormSchemeKey(scheme.getKey());
                jtableContext.setTaskKey(taskKey);
                entityQueryInfo.setEntityViewKey(entity.getKey());
                if (result.getEntityDatas().containsKey(entity.getDimensionName())) continue;
                result.getEntityDatas().put(entity.getDimensionName(), this.jtableEntityService.queryDimEntityData(entityQueryInfo).getEntitys());
            }
            schemeResult.setDimensionSet(dimensions);
            ArrayList formulaVariableList = new ArrayList();
            List queryAllFormulaVariable = this.formulaRuntimeTimeController.queryAllFormulaVariable(scheme.getKey());
            if (queryAllFormulaVariable.isEmpty()) {
                schemeResult.setFormulaVariables(formulaVariableList);
            } else {
                queryAllFormulaVariable.stream().filter(e -> e.getInitType() == 1).forEach(f -> formulaVariableList.add(new FormulaVariable().convertToFormulaVariable(f)));
                schemeResult.setFormulaVariables(formulaVariableList);
            }
            if (this.iFormSchemeService.enableAdjustPeriod(scheme.getKey())) {
                DimensionValue value = new DimensionValue();
                value.setName("ADJUST");
                value.setValue("0");
                dimensions.put("ADJUST", value);
                schemeResult.setOpenAdJustPeriod(this.iFormSchemeService.enableAdjustPeriod(scheme.getKey()));
            }
            schemeResult.setExistCurrencyAttributes(this.iFormSchemeService.existCurrencyAttributes(scheme.getKey()).booleanValue());
            ArrayList reportDimsList = new ArrayList();
            reportDimsList.addAll(this.iFormSchemeService.getReportDimensionKey(scheme.getKey()));
            schemeResult.setReportDimensionList(reportDimsList);
        }
        return schemeListResult;
    }

    private boolean judgementPeriodView(String viewKey) {
        return this.periodEntityAdapter.isPeriodEntity(viewKey);
    }

    private PeriodWrapper modifyPeriod(IPeriodProvider periodProvider, PeriodWrapper currentPeriod, String period, TaskData taskData) {
        try {
            int periodOffset = Integer.parseInt(period);
            periodProvider.modifyPeriod(currentPeriod, periodOffset);
            PeriodWrapper fromPeriod = new PeriodWrapper(taskData.getFromPeriod());
            PeriodWrapper toPeriod = new PeriodWrapper(taskData.getToPeriod());
            if (periodOffset > 0) {
                if (currentPeriod.compareTo((Object)toPeriod) > 0) {
                    currentPeriod = new PeriodWrapper(taskData.getToPeriod());
                }
            } else if (periodOffset < 0 && currentPeriod.compareTo((Object)fromPeriod) < 0) {
                currentPeriod = new PeriodWrapper(taskData.getFromPeriod());
            }
        }
        catch (Exception e) {
            currentPeriod = new PeriodWrapper(period);
        }
        return currentPeriod;
    }

    @Override
    public List<SelectStructure> getAssTasks(String taskKey, String formSchemeKey) {
        ArrayList<SelectStructure> list = new ArrayList<SelectStructure>();
        try {
            Object basicCheckItems = this.taskExtConfigController.getTaskExtConfigDefineBySchemakeyCache(taskKey, formSchemeKey, "taskextension-entitycheck");
            if (basicCheckItems == null) {
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            EntityCheckConfigData entityCheckConfigData = (EntityCheckConfigData)basicCheckItems;
            if (entityCheckConfigData.getEntityCheckEnable()) {
                List<ConfigInfo> configInfos = entityCheckConfigData.getConfigInfos();
                for (int i = 0; i < configInfos.size(); ++i) {
                    ConfigInfo configInfo = configInfos.get(i);
                    String asstaskKey = configInfo.getAssTaskKey();
                    TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(asstaskKey);
                    if (taskDefine == null) continue;
                    SelectStructure selectStructure = new SelectStructure();
                    selectStructure.setKey(taskDefine.getKey());
                    selectStructure.setTitle(taskDefine.getTitle());
                    if (list.contains(selectStructure)) continue;
                    list.add(selectStructure);
                }
                return list;
            }
            return null;
        }
        catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<SelectStructure> getAssFormSchemes(String taskKey, String formSchemeKey, String assTaskKey) {
        ArrayList<SelectStructure> list = new ArrayList<SelectStructure>();
        try {
            Object basicCheckItems = this.taskExtConfigController.getTaskExtConfigDefineBySchemakeyCache(taskKey, formSchemeKey, "taskextension-entitycheck");
            if (basicCheckItems == null) {
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            EntityCheckConfigData entityCheckConfigData = (EntityCheckConfigData)basicCheckItems;
            if (entityCheckConfigData.getEntityCheckEnable()) {
                List<ConfigInfo> configInfos = entityCheckConfigData.getConfigInfos();
                for (int i = 0; i < configInfos.size(); ++i) {
                    FormSchemeDefine formSchemeDefine;
                    ConfigInfo configInfo = configInfos.get(i);
                    if (!configInfo.getAssTaskKey().equals(assTaskKey) || (formSchemeDefine = this.runTimeViewController.getFormScheme(configInfo.getAssFormSchemeKey())) == null) continue;
                    SelectStructure selectStructure = new SelectStructure();
                    selectStructure.setKey(formSchemeDefine.getKey());
                    selectStructure.setTitle(formSchemeDefine.getTitle());
                    if (list.contains(selectStructure)) continue;
                    list.add(selectStructure);
                }
                return list;
            }
            return null;
        }
        catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Association getAssociation(String taskKey, String formSchemeKey, String assTaskKey, String assFormSchemeKey) {
        try {
            Object basicCheckItems = this.taskExtConfigController.getTaskExtConfigDefineBySchemakeyCache(taskKey, formSchemeKey, "taskextension-entitycheck");
            if (basicCheckItems == null) {
                return null;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            EntityCheckConfigData entityCheckConfigData = (EntityCheckConfigData)basicCheckItems;
            if (entityCheckConfigData.getEntityCheckEnable()) {
                List<ConfigInfo> configInfos = entityCheckConfigData.getConfigInfos();
                for (int i = 0; i < configInfos.size(); ++i) {
                    ConfigInfo configInfo = configInfos.get(i);
                    if (!configInfo.getAssTaskKey().equals(assTaskKey) || !configInfo.getAssFormSchemeKey().equals(assFormSchemeKey)) continue;
                    return configInfo.getAssociation();
                }
                return null;
            }
            return null;
        }
        catch (Exception ex) {
            return null;
        }
    }
}

