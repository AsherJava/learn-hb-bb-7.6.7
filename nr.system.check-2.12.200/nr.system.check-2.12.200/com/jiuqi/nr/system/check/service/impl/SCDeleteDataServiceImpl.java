/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.definitions.FormulaCallBack
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IFormulaRunner
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.engine.dataquery.DeleteDataService
 *  com.jiuqi.nr.dataentry.bean.DimensionCacheKey
 *  com.jiuqi.nr.dataentry.internal.service.BatchConditionMonitor
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.paramcheck.ParamClearNotice
 *  com.jiuqi.nr.definition.util.FormDimensionValue
 *  com.jiuqi.nr.definition.util.ParamClearObject
 *  com.jiuqi.nr.definition.util.ParamClearType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableDataEngineService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.system.check.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.engine.dataquery.DeleteDataService;
import com.jiuqi.nr.dataentry.bean.DimensionCacheKey;
import com.jiuqi.nr.dataentry.internal.service.BatchConditionMonitor;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.paramcheck.ParamClearNotice;
import com.jiuqi.nr.definition.util.FormDimensionValue;
import com.jiuqi.nr.definition.util.ParamClearObject;
import com.jiuqi.nr.definition.util.ParamClearType;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.system.check.common.SCCondition;
import com.jiuqi.nr.system.check.common.SCErrorEnum;
import com.jiuqi.nr.system.check.common.SCSelectType;
import com.jiuqi.nr.system.check.model.request.DeleteDataObj;
import com.jiuqi.nr.system.check.model.request.EntityObj;
import com.jiuqi.nr.system.check.model.request.FormObj;
import com.jiuqi.nr.system.check.service.IParamCheckEntityUpgrader;
import com.jiuqi.nr.system.check.service.SCDeleteDataService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SCDeleteDataServiceImpl
implements SCDeleteDataService {
    private static final Logger logger = LoggerFactory.getLogger(SCDeleteDataServiceImpl.class);
    @Autowired
    private DeleteDataService deleteDataService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private IDataAccessProvider iDataAccessProvider;
    @Autowired
    private IParamCheckEntityUpgrader iParamCheckEntityUpgrader;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired(required=false)
    private ParamClearNotice paramClearNotice;
    public static Map<String, List<String>> CLEARINFO = new HashMap<String, List<String>>();
    public static List<FormDimensionValue> formDimensionValues = new ArrayList<FormDimensionValue>();

    @Override
    public void deleteData(DeleteDataObj deleteDataObj) throws Exception {
        CLEARINFO.clear();
        if (deleteDataObj != null && deleteDataObj.isNotEmpty()) {
            if (SCCondition.BLOCK.getName().equals(deleteDataObj.getConditions()) && StringUtils.isNotEmpty((String)deleteDataObj.getPeriod())) {
                this.deleteDataByConditions(deleteDataObj);
            } else if (deleteDataObj.isSelectAll()) {
                this.deleteDataByTask(deleteDataObj);
            } else {
                this.deleteDataByFormDim(deleteDataObj);
            }
        } else {
            logger.info(CLEARINFO.toString());
            throw new JQException((ErrorEnum)SCErrorEnum.SYSTEM_CHECK_EXCEPTION_003);
        }
        LogHelper.info((String)"\u6570\u636e\u6e05\u7406", (String)"\u6267\u884c\u6570\u636e\u6e05\u9664", (String)this.createLog(deleteDataObj));
    }

    private String createLog(DeleteDataObj deleteDataObj) {
        StringBuffer sbf = new StringBuffer();
        sbf.append("\u6e05\u7406\u8303\u56f4\uff1a");
        if (null != CLEARINFO.get("task")) {
            sbf.append("\u4efb\u52a1:");
            sbf.append(CLEARINFO.get("task").toString());
        }
        if (null != CLEARINFO.get("formscheme")) {
            sbf.append(",\u62a5\u8868\u65b9\u6848:");
            sbf.append(CLEARINFO.get("formscheme").toString());
        }
        if (null != CLEARINFO.get("forms")) {
            sbf.append(",\u62a5\u8868:");
            sbf.append(CLEARINFO.get("forms").toString());
        }
        if (null != CLEARINFO.get("unit")) {
            sbf.append(",\u5355\u4f4d:");
            sbf.append(CLEARINFO.get("unit").toString());
        }
        if (null != CLEARINFO.get("period")) {
            sbf.append(",\u65f6\u671f:");
            List<String> list = CLEARINFO.get("period");
            if (list.size() >= 1) {
                String period;
                String periodTitle = period = list.get(0);
                TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(deleteDataObj.getTask());
                IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime());
                List periodItems = periodProvider.getPeriodItems();
                for (IPeriodRow periodItem : periodItems) {
                    if (!period.equals(periodItem.getCode())) continue;
                    periodTitle = periodItem.getTitle();
                }
                sbf.append(periodTitle);
            }
        }
        if (null != CLEARINFO.get("condition")) {
            sbf.append(",\u62a5\u8868\u8fc7\u6ee4\u6761\u4ef6:\u542f\u7528");
        }
        return sbf.toString();
    }

    private void setInfo(String type, String value) {
        if (CLEARINFO.get(type) == null) {
            ArrayList<String> ls = new ArrayList<String>();
            ls.add(value);
            CLEARINFO.put(type, ls);
        } else {
            CLEARINFO.get(type).add(value);
        }
    }

    private void setInfo(String type, List<String> values) {
        if (CLEARINFO.get(type) == null) {
            ArrayList<String> ls = new ArrayList<String>();
            ls.addAll(values);
            CLEARINFO.put(type, ls);
        } else {
            CLEARINFO.get(type).addAll(values);
        }
    }

    private void deleteDataByTask(DeleteDataObj deleteDataObj) throws Exception {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(deleteDataObj.getTask());
        this.setInfo("task", taskDefine.getTitle());
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(deleteDataObj.getScheme());
        if (null != formScheme) {
            this.setInfo("formscheme", formScheme.getTitle());
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        this.setPeriodDataForDim(dimensionValueSet, deleteDataObj.getPeriod());
        this.setInfo("period", deleteDataObj.getPeriod());
        TableModelDefine tableModel = this.iEntityMetaService.getTableModel(taskDefine.getDw());
        EntityObj entityObj = deleteDataObj.getEntitys().get(tableModel.getID());
        RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
        entityViewDefine.setEntityId(taskDefine.getDw());
        this.setUnitDataForDim(dimensionValueSet, entityObj, (EntityViewDefine)entityViewDefine, deleteDataObj.getPeriod());
        if (StringUtils.isNotEmpty((String)taskDefine.getDims())) {
            String[] dims;
            for (String dim : dims = taskDefine.getDims().split(";")) {
                TableModelDefine dtableModel = this.iEntityMetaService.getTableModel(dim);
                EntityObj dentityObj = deleteDataObj.getEntitys().get(dtableModel);
                RunTimeEntityViewDefineImpl dentityViewDefine = new RunTimeEntityViewDefineImpl();
                dentityViewDefine.setEntityId(dim);
                this.setUnitDataForDim(dimensionValueSet, dentityObj, (EntityViewDefine)dentityViewDefine, deleteDataObj.getPeriod());
            }
        }
        this.deleteDataService.deleteDataByTaskDim(deleteDataObj.getTask(), dimensionValueSet);
        this.notice(new ParamClearObject(dimensionValueSet, ParamClearType.TASK, taskDefine.getKey(), formScheme.getKey()));
    }

    private void notice(ParamClearObject paramClearObject) {
        if (null != this.paramClearNotice) {
            try {
                this.paramClearNotice.clearData(paramClearObject);
            }
            catch (Exception e) {
                logger.info("\u6e05\u9664\u6570\u636e\u4e8b\u4ef6\u6d88\u606f\u6267\u884c\u5931\u8d25".concat(this.paramClearNotice.toString()));
            }
        }
    }

    private void deleteDataByConditions(DeleteDataObj deleteDataObj) throws Exception {
        List formDefines = new ArrayList();
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(deleteDataObj.getTask());
        this.setInfo("task", taskDefine.getTitle());
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(deleteDataObj.getScheme());
        if (null != formScheme) {
            this.setInfo("formscheme", formScheme.getTitle());
        }
        FormObj formObj = deleteDataObj.getForms();
        String selectType = formObj.getFormType();
        String formSchemeKey = deleteDataObj.getScheme();
        ArrayList<String> formTitle = new ArrayList<String>();
        if (SCSelectType.ALL.getName().equals(selectType)) {
            List formGroupDefines = this.iRunTimeViewController.getAllFormGroupsInFormScheme(formSchemeKey);
            if (formGroupDefines != null) {
                for (FormGroupDefine formGroupDefine : formGroupDefines) {
                    String formGroupKey = formGroupDefine.getKey();
                    List formDefinesByGroup = this.iRunTimeViewController.getAllFormsInGroup(formGroupKey);
                    if (formDefinesByGroup == null) continue;
                    formDefines.addAll(formDefinesByGroup);
                    for (FormDefine formDefine : formDefinesByGroup) {
                        formTitle.add(formDefine.getTitle());
                    }
                }
            }
        } else if (SCSelectType.CHOOSE.getName().equals(selectType)) {
            List<String> formKeys = formObj.getSelected();
            formDefines = this.iRunTimeViewController.queryFormsById(formKeys);
            for (FormDefine formDefine : formDefines) {
                formTitle.add(formDefine.getTitle());
            }
        }
        if (formDefines != null && formDefines.size() != 0) {
            this.setInfo("forms", formTitle);
            String periodCode = deleteDataObj.getPeriod();
            this.setInfo("period", deleteDataObj.getPeriod());
            Map<String, EntityObj> entitys = deleteDataObj.getEntitys();
            FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(deleteDataObj.getScheme());
            String masterKey = formSchemeDefine.getMasterEntitiesKey();
            String[] masterKeyArr = masterKey.split(";");
            ArrayList<List<String>> allUnitList = new ArrayList<List<String>>();
            for (int i = 0; i < 9; ++i) {
                allUnitList.add(null);
            }
            ArrayList<String> masterKeyUnitList = new ArrayList<String>();
            int entityIndex = 0;
            for (String entityViewKey : masterKeyArr) {
                if (this.periodEngineService.getPeriodAdapter().isPeriodEntity(entityViewKey)) continue;
                TableModelDefine tableModel = this.iEntityMetaService.getTableModel(entityViewKey);
                masterKeyUnitList.add(entityViewKey);
                EntityObj entityObj = entitys.get(tableModel.getID());
                List<Object> allUnitKeyForEntity = new ArrayList();
                if (entityObj != null) {
                    String selectedType = entityObj.getEntityType();
                    if (SCSelectType.ALL.getName().equals(selectedType)) {
                        RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
                        entityViewDefine.setEntityId(entityViewKey);
                        HashMap<String, String> unitTitle = new HashMap<String, String>();
                        allUnitKeyForEntity = this.getAllUnitKeyForEntity((EntityViewDefine)entityViewDefine, periodCode, unitTitle);
                        ArrayList<String> unitTitles = new ArrayList<String>();
                        for (String unitKey : unitTitle.keySet()) {
                            unitTitles.add((String)unitTitle.get(unitKey));
                        }
                        this.setInfo("unit", unitTitles);
                    } else if (SCSelectType.CHOOSE.getName().equals(selectedType)) {
                        HashMap<String, String> unitTitle = new HashMap<String, String>();
                        allUnitKeyForEntity = entityObj.getSelected();
                        RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
                        entityViewDefine.setEntityId(entityViewKey);
                        List<String> allUnitKey = this.getAllUnitKeyForEntity((EntityViewDefine)entityViewDefine, periodCode, unitTitle);
                        ArrayList<String> unitTitles = new ArrayList<String>();
                        for (int i = 0; i < allUnitKeyForEntity.size(); ++i) {
                            if (allUnitKey.contains(allUnitKeyForEntity.get(i))) {
                                unitTitles.add((String)unitTitle.get(allUnitKeyForEntity.get(i)));
                                continue;
                            }
                            unitTitles.add((String)allUnitKeyForEntity.get(i));
                        }
                        this.setInfo("unit", unitTitles);
                    }
                    allUnitList.add(entityIndex, allUnitKeyForEntity);
                }
                ++entityIndex;
            }
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            LinkedHashMap<String, DimensionValue> dimMap = new LinkedHashMap<String, DimensionValue>();
            this.setPeriodDataForDim(dimensionValueSet, periodCode);
            DimensionValue dv = new DimensionValue();
            dv.setName("DATATIME");
            dv.setValue(periodCode);
            dimMap.put("DATATIME", dv);
            this.setInfo("condition", new ArrayList<String>());
            ParamClearObject paramClearObject = new ParamClearObject();
            formDimensionValues.clear();
            paramClearObject.setTask(taskDefine.getKey());
            paramClearObject.setFormScheme(formScheme.getKey());
            paramClearObject.setParamClearType(ParamClearType.CONDITIONAL);
            for (FormDefine formDefine : formDefines) {
                if (StringUtils.isEmpty((String)formDefine.getFormCondition()) || allUnitList.get(0) == null) continue;
                for (int u1 = 0; u1 < ((List)allUnitList.get(0)).size(); ++u1) {
                    if (allUnitList.get(1) != null) {
                        for (int u2 = 0; u2 < ((List)allUnitList.get(1)).size(); ++u2) {
                            if (allUnitList.get(2) != null) {
                                for (int u3 = 0; u3 < ((List)allUnitList.get(2)).size(); ++u3) {
                                    if (allUnitList.get(3) != null) {
                                        for (int u4 = 0; u4 < ((List)allUnitList.get(3)).size(); ++u4) {
                                            if (allUnitList.get(4) != null) {
                                                for (int u5 = 0; u5 < ((List)allUnitList.get(4)).size(); ++u5) {
                                                    if (allUnitList.get(5) != null) {
                                                        for (int u6 = 0; u6 < ((List)allUnitList.get(5)).size(); ++u6) {
                                                            if (allUnitList.get(6) != null) {
                                                                for (int u7 = 0; u7 < ((List)allUnitList.get(6)).size(); ++u7) {
                                                                    if (allUnitList.get(7) != null) {
                                                                        for (int u8 = 0; u8 < ((List)allUnitList.get(7)).size(); ++u8) {
                                                                            if (allUnitList.get(8) != null) {
                                                                                for (int u9 = 0; u9 < ((List)allUnitList.get(8)).size(); ++u9) {
                                                                                    this.setDimensionValue(masterKeyUnitList, entitys, dimensionValueSet, allUnitList, dimMap, u1, u2, u3, u4, u5, u6, u7, u8, u9);
                                                                                    this.filterConditions(formDefine, dimensionValueSet, deleteDataObj, dimMap);
                                                                                }
                                                                                continue;
                                                                            }
                                                                            this.setDimensionValue(masterKeyUnitList, entitys, dimensionValueSet, allUnitList, dimMap, u1, u2, u3, u4, u5, u6, u7, u8, -1);
                                                                            this.filterConditions(formDefine, dimensionValueSet, deleteDataObj, dimMap);
                                                                        }
                                                                        continue;
                                                                    }
                                                                    this.setDimensionValue(masterKeyUnitList, entitys, dimensionValueSet, allUnitList, dimMap, u1, u2, u3, u4, u5, u6, u7, -1, -1);
                                                                    this.filterConditions(formDefine, dimensionValueSet, deleteDataObj, dimMap);
                                                                }
                                                                continue;
                                                            }
                                                            this.setDimensionValue(masterKeyUnitList, entitys, dimensionValueSet, allUnitList, dimMap, u1, u2, u3, u4, u5, u6, -1, -1, -1);
                                                            this.filterConditions(formDefine, dimensionValueSet, deleteDataObj, dimMap);
                                                        }
                                                        continue;
                                                    }
                                                    this.setDimensionValue(masterKeyUnitList, entitys, dimensionValueSet, allUnitList, dimMap, u1, u2, u3, u4, u5, -1, -1, -1, -1);
                                                    this.filterConditions(formDefine, dimensionValueSet, deleteDataObj, dimMap);
                                                }
                                                continue;
                                            }
                                            this.setDimensionValue(masterKeyUnitList, entitys, dimensionValueSet, allUnitList, dimMap, u1, u2, u3, u4, -1, -1, -1, -1, -1);
                                            this.filterConditions(formDefine, dimensionValueSet, deleteDataObj, dimMap);
                                        }
                                        continue;
                                    }
                                    this.setDimensionValue(masterKeyUnitList, entitys, dimensionValueSet, allUnitList, dimMap, u1, u2, u3, -1, -1, -1, -1, -1, -1);
                                    this.filterConditions(formDefine, dimensionValueSet, deleteDataObj, dimMap);
                                }
                                continue;
                            }
                            this.setDimensionValue(masterKeyUnitList, entitys, dimensionValueSet, allUnitList, dimMap, u1, u2, -1, -1, -1, -1, -1, -1, -1);
                            this.filterConditions(formDefine, dimensionValueSet, deleteDataObj, dimMap);
                        }
                        continue;
                    }
                    this.setDimensionValue(masterKeyUnitList, entitys, dimensionValueSet, allUnitList, dimMap, u1, -1, -1, -1, -1, -1, -1, -1, -1);
                    this.filterConditions(formDefine, dimensionValueSet, deleteDataObj, dimMap);
                }
            }
            paramClearObject.setFormDimensionValues(formDimensionValues);
            this.notice(paramClearObject);
        }
    }

    private void filterConditions(FormDefine formDefine, DimensionValueSet dimensionValueSet, DeleteDataObj deleteDataObj, Map<String, DimensionValue> dimMap) {
        JtableContext excutorJtableContext = new JtableContext();
        excutorJtableContext.setFormKey(formDefine.getKey());
        excutorJtableContext.setTaskKey(deleteDataObj.getTask());
        excutorJtableContext.setFormSchemeKey(deleteDataObj.getScheme());
        Map result = null;
        try {
            ExecutorContext executorContext = this.jtableDataEngineService.getExecutorContext(excutorJtableContext);
            FormulaCallBack callback = new FormulaCallBack();
            if (StringUtils.isNotEmpty((String)formDefine.getFormCondition())) {
                ArrayList<Formula> formulas = new ArrayList<Formula>();
                Formula formula = new Formula();
                formula.setId(formDefine.getKey());
                formula.setFormKey(formDefine.getKey());
                formula.setFormula(formDefine.getFormCondition());
                formula.setChecktype(Integer.valueOf(FormulaCheckType.FORMULA_CHECK_ERROR.getValue()));
                formulas.add(formula);
                callback.getFormulas().addAll(formulas);
                IFormulaRunner runner = this.iDataAccessProvider.newFormulaRunner(callback);
                BatchConditionMonitor monitor = new BatchConditionMonitor(formDefine.getFormScheme());
                dimensionValueSet.getDimensionSet();
                monitor.setDimensionValueMap(dimMap);
                runner.prepareCheck(executorContext, dimensionValueSet, (IMonitor)monitor);
                runner.run((IMonitor)monitor);
                result = monitor.getConditionResultList();
                for (Map.Entry dimensionCacheKeySetEntry : result.entrySet()) {
                    DimensionCacheKey dimensionCacheKey = (DimensionCacheKey)dimensionCacheKeySetEntry.getKey();
                    DimensionValueSet curDim = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionCacheKey.getDimensionSet());
                    for (String fk : (Set)dimensionCacheKeySetEntry.getValue()) {
                        this.deleteDataService.deleteDataByFormDim(fk, curDim);
                        formDimensionValues.add(new FormDimensionValue(formDefine.getKey(), curDim));
                    }
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private DimensionValue getDimObject(String name, String value) {
        DimensionValue dimensionValue = new DimensionValue();
        dimensionValue.setName(name);
        dimensionValue.setValue(value);
        return dimensionValue;
    }

    private List<String> getAllUnitKeyForEntity(EntityViewDefine entityViewDefine, String periodCode, Map<String, String> unitTitle) throws Exception {
        return this.iParamCheckEntityUpgrader.getAllUnitKeyForEntity(entityViewDefine, periodCode, unitTitle);
    }

    private void setDimensionValue(List<String> masterKeyUnitList, Map<String, EntityObj> entitys, DimensionValueSet dimensionValueSet, List<List<String>> allUnitList, Map<String, DimensionValue> dimMap, int u1, int u2, int u3, int u4, int u5, int u6, int u7, int u8, int u9) throws Exception {
        block11: for (int mk = 0; mk < masterKeyUnitList.size(); ++mk) {
            EntityObj entityObj;
            String tableKey = this.iEntityMetaService.getTableModel(masterKeyUnitList.get(mk)).getID();
            RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
            entityViewDefine.setEntityId(masterKeyUnitList.get(mk));
            if (this.periodEngineService.getPeriodAdapter().isPeriodEntity(masterKeyUnitList.get(mk)) || (entityObj = entitys.get(tableKey)) == null) continue;
            ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
            IDataAssist iDataAssist = this.iDataAccessProvider.newDataAssist(executorContext);
            String entityDimName = iDataAssist.getDimensionName((EntityViewDefine)entityViewDefine);
            switch (mk) {
                case 0: {
                    dimensionValueSet.setValue(entityDimName, (Object)allUnitList.get(mk).get(u1));
                    dimMap.put(entityDimName, this.getDimObject(entityDimName, allUnitList.get(mk).get(u1)));
                    continue block11;
                }
                case 1: {
                    dimensionValueSet.setValue(entityDimName, (Object)allUnitList.get(mk).get(u2));
                    dimMap.put(entityDimName, this.getDimObject(entityDimName, allUnitList.get(mk).get(u2)));
                    continue block11;
                }
                case 2: {
                    dimensionValueSet.setValue(entityDimName, (Object)allUnitList.get(mk).get(u3));
                    dimMap.put(entityDimName, this.getDimObject(entityDimName, allUnitList.get(mk).get(u3)));
                    continue block11;
                }
                case 3: {
                    dimensionValueSet.setValue(entityDimName, (Object)allUnitList.get(mk).get(u4));
                    dimMap.put(entityDimName, this.getDimObject(entityDimName, allUnitList.get(mk).get(u4)));
                    continue block11;
                }
                case 4: {
                    dimensionValueSet.setValue(entityDimName, (Object)allUnitList.get(mk).get(u5));
                    dimMap.put(entityDimName, this.getDimObject(entityDimName, allUnitList.get(mk).get(u5)));
                    continue block11;
                }
                case 5: {
                    dimensionValueSet.setValue(entityDimName, (Object)allUnitList.get(mk).get(u6));
                    dimMap.put(entityDimName, this.getDimObject(entityDimName, allUnitList.get(mk).get(u6)));
                    continue block11;
                }
                case 6: {
                    dimensionValueSet.setValue(entityDimName, (Object)allUnitList.get(mk).get(u7));
                    dimMap.put(entityDimName, this.getDimObject(entityDimName, allUnitList.get(mk).get(u7)));
                    continue block11;
                }
                case 7: {
                    dimensionValueSet.setValue(entityDimName, (Object)allUnitList.get(mk).get(u8));
                    dimMap.put(entityDimName, this.getDimObject(entityDimName, allUnitList.get(mk).get(u8)));
                    continue block11;
                }
                case 8: {
                    dimensionValueSet.setValue(entityDimName, (Object)allUnitList.get(mk).get(u9));
                    dimMap.put(entityDimName, this.getDimObject(entityDimName, allUnitList.get(mk).get(u9)));
                    continue block11;
                }
            }
        }
    }

    private void deleteDataByFormDim(DeleteDataObj deleteDataObj) throws Exception {
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(deleteDataObj.getTask());
        this.setInfo("task", taskDefine.getTitle());
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(deleteDataObj.getScheme());
        if (null != formScheme) {
            this.setInfo("formscheme", formScheme.getTitle());
        }
        String formSchemeKey = deleteDataObj.getScheme();
        FormObj formObj = deleteDataObj.getForms();
        String selectType = formObj.getFormType();
        List<Object> formKeys = new ArrayList();
        ArrayList<String> formTitle = new ArrayList<String>();
        if (SCSelectType.ALL.getName().equals(selectType)) {
            List formGroupDefines = this.iRunTimeViewController.getAllFormGroupsInFormScheme(formSchemeKey);
            if (formGroupDefines != null) {
                for (FormGroupDefine formGroupDefine : formGroupDefines) {
                    String formGroupKey = formGroupDefine.getKey();
                    List formDefines = this.iRunTimeViewController.getAllFormsInGroup(formGroupKey);
                    if (formDefines == null) continue;
                    for (FormDefine formDefine : formDefines) {
                        FormType formType = formDefine.getFormType();
                        if (FormType.FORM_TYPE_FMDM == formType) continue;
                        String formKey = formDefine.getKey();
                        formKeys.add(formKey);
                        formTitle.add(formDefine.getTitle());
                    }
                }
            }
        } else if (SCSelectType.CHOOSE.getName().equals(selectType)) {
            formKeys = formObj.getSelected();
            List formDefines = this.iRunTimeViewController.queryFormsById(formKeys);
            for (FormDefine formDefine : formDefines) {
                formTitle.add(formDefine.getTitle());
            }
        }
        if (formKeys != null) {
            this.setInfo("forms", formTitle);
            String periodCode = deleteDataObj.getPeriod();
            Map<String, EntityObj> entitys = deleteDataObj.getEntitys();
            FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
            String masterKey = formSchemeDefine.getMasterEntitiesKey();
            String[] masterKeyArr = masterKey.split(";");
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            this.setPeriodDataForDim(dimensionValueSet, periodCode);
            this.setInfo("period", deleteDataObj.getPeriod());
            for (String entityViewKey : masterKeyArr) {
                if (this.periodEngineService.getPeriodAdapter().isPeriodEntity(entityViewKey)) continue;
                String tableKey = this.iEntityMetaService.getTableModel(entityViewKey).getID();
                RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
                entityViewDefine.setEntityId(entityViewKey);
                EntityObj entityObj = entitys.get(tableKey);
                this.setUnitDataForDim(dimensionValueSet, entityObj, (EntityViewDefine)entityViewDefine, periodCode);
            }
            for (String string : formKeys) {
                this.deleteDataService.deleteDataByFormDim(string, dimensionValueSet);
            }
            this.notice(new ParamClearObject(taskDefine.getKey(), formSchemeDefine.getKey(), ParamClearType.OPTIONAL, dimensionValueSet, formKeys));
        }
    }

    private void setPeriodDataForDim(DimensionValueSet dimensionValueSet, String periodCode) throws Exception {
        if (StringUtils.isNotEmpty((String)periodCode)) {
            dimensionValueSet.setValue("DATATIME", (Object)periodCode);
        }
    }

    private void setUnitDataForDim(DimensionValueSet dimensionValueSet, EntityObj entityObj, EntityViewDefine entityViewDefine, String periodCode) throws Exception {
        if (entityObj != null && dimensionValueSet != null && StringUtils.isNotEmpty((String)periodCode)) {
            ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
            IDataAssist iDataAssist = this.iDataAccessProvider.newDataAssist(executorContext);
            String entityDimName = iDataAssist.getDimensionName(entityViewDefine);
            String selectedType = entityObj.getEntityType();
            if (SCSelectType.ALL.getName().equals(selectedType)) {
                HashMap<String, String> unitTitle = new HashMap<String, String>();
                List<String> allUnitKey = this.iParamCheckEntityUpgrader.getAllUnitKeyForEntity(entityViewDefine, periodCode, unitTitle);
                if (allUnitKey != null && allUnitKey.size() > 0) {
                    dimensionValueSet.setValue(entityDimName, allUnitKey);
                }
                ArrayList<String> unitTitles = new ArrayList<String>();
                for (String unitKey : unitTitle.keySet()) {
                    unitTitles.add((String)unitTitle.get(unitKey));
                }
                this.setInfo("unit", unitTitles);
            } else if (SCSelectType.CHOOSE.getName().equals(selectedType)) {
                List<String> unitKeys = entityObj.getSelected();
                dimensionValueSet.setValue(entityDimName, unitKeys);
                HashMap<String, String> unitTitle = new HashMap<String, String>();
                List<String> allUnitKey = this.getAllUnitKeyForEntity(entityViewDefine, periodCode, unitTitle);
                ArrayList<String> unitTitles = new ArrayList<String>();
                for (int i = 0; i < unitKeys.size(); ++i) {
                    if (allUnitKey.contains(unitKeys.get(i))) {
                        unitTitles.add((String)unitTitle.get(unitKeys.get(i)));
                        continue;
                    }
                    unitTitles.add(unitKeys.get(i));
                }
                this.setInfo("unit", unitTitles);
            }
        }
    }
}

