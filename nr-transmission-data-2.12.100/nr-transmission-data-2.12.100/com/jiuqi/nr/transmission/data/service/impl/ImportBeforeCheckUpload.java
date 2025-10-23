/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.bpm.common.UploadStateNew
 *  com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.option.internal.UnitEdit
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.transmission.data.service.impl;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.option.internal.UnitEdit;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.transmission.data.api.IExecuteParam;
import com.jiuqi.nr.transmission.data.common.MultilingualLog;
import com.jiuqi.nr.transmission.data.common.Utils;
import com.jiuqi.nr.transmission.data.intf.ContextExpendParam;
import com.jiuqi.nr.transmission.data.intf.DataImportMessage;
import com.jiuqi.nr.transmission.data.intf.DataImportResult;
import com.jiuqi.nr.transmission.data.intf.EntityInfoParam;
import com.jiuqi.nr.transmission.data.log.ILogHelper;
import com.jiuqi.nr.transmission.data.service.IImportBeforeService;
import com.jiuqi.nr.transmission.data.var.ITransmissionContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class ImportBeforeCheckUpload
implements IImportBeforeService {
    private static final Logger logger = LoggerFactory.getLogger(ImportBeforeCheckUpload.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IQueryUploadStateService queryUploadStateService;
    @Autowired
    private ITaskOptionController taskOptionController;

    @Override
    public Double getOrder() {
        return 1.0;
    }

    @Override
    public String getTitle() {
        return MultilingualLog.checkUploadMessage(1, "");
    }

    @Override
    public Object beforeImport(ITransmissionContext transmissionContext) throws Exception {
        String message;
        IExecuteParam executeParam = transmissionContext.getExecuteParam();
        ContextExpendParam contextExpendParam = transmissionContext.getContextExpendParam();
        boolean hasWorkFlowData = contextExpendParam.getHasWorkFlowData();
        ILogHelper logHelper = transmissionContext.getLogHelper();
        String dimensionName = contextExpendParam.getDimensionName();
        WorkFlowType startType = contextExpendParam.getWorkFlowType();
        DataImportResult dataImportResult = transmissionContext.getDataImportResult();
        Map<String, EntityInfoParam> entityRowMap = contextExpendParam.getUnits();
        List<String> formKeys = executeParam.getForms();
        DimensionValueSet dimensionValueSet = executeParam.getDimensionValueSet();
        Object value = dimensionValueSet.getValue(dimensionName);
        List entitys = (List)value;
        List<String> adjustPeriod = executeParam.getAdjustPeriod();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(executeParam.getFormSchemeKey());
        String dw = formScheme.getDw();
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(dw);
        FlowsType flowsType = formScheme.getFlowsSetting().getFlowsType();
        ArrayList<String> needUploadUnit = new ArrayList<String>();
        HashMap<String, List<DimensionValueSet>> notNeedImportEntityMaps = new HashMap<String, List<DimensionValueSet>>();
        HashMap<String, List> notNeedImportFlowMaps = new HashMap<String, List>();
        HashMap<String, Object> notNeedImportFormMaps = new HashMap<String, Object>();
        if (!FlowsType.NOSTARTUP.equals((Object)flowsType)) {
            DimensionValueSet entities;
            int adjustSize;
            ArrayList<FormGroupDefine> formGroups = new ArrayList<FormGroupDefine>();
            List<UploadStateNew> uploadStateNews = this.queryUpload(transmissionContext, formGroups);
            ArrayList<UploadStateNew> uploadStateWithOutImport = new ArrayList<UploadStateNew>();
            List<Object> needUpload = new ArrayList();
            if (!CollectionUtils.isEmpty(uploadStateNews)) {
                if (!hasWorkFlowData) {
                    String UploadedString = UploadState.UPLOADED.toString();
                    for (UploadStateNew uploadStateNew : uploadStateNews) {
                        ActionStateBean actionStateBean = uploadStateNew.getActionStateBean();
                        String code = actionStateBean.getCode();
                        if (UploadedString.equals(code)) {
                            uploadStateWithOutImport.add(uploadStateNew);
                            continue;
                        }
                        needUpload.add(uploadStateNew);
                    }
                } else {
                    transmissionContext.getLogHelper().appendLog("\u6570\u636e\u5305\u4e2d\u5305\u542b\u6d41\u7a0b\u6570\u636e\u3002");
                    needUpload = uploadStateNews;
                }
            }
            int n = adjustSize = adjustPeriod.size() > 0 ? adjustPeriod.size() : 1;
            if (WorkFlowType.ENTITY.equals((Object)startType)) {
                if (!CollectionUtils.isEmpty(uploadStateWithOutImport)) {
                    for (UploadStateNew uploadStateNew : uploadStateWithOutImport) {
                        entities = uploadStateNew.getEntities();
                        notNeedImportEntityMaps.computeIfAbsent(uploadStateNew.getEntities().getValue(dimensionName).toString(), key -> new ArrayList()).add(entities);
                    }
                    Iterator allDimsission = uploadStateWithOutImport.stream().map(UploadStateNew::getEntities).collect(Collectors.toList());
                    for (String formKey : executeParam.getForms()) {
                        notNeedImportFormMaps.put(formKey, allDimsission);
                    }
                }
                this.getNeedUploadUnit(entitys, notNeedImportEntityMaps, adjustSize, needUploadUnit);
            } else if (WorkFlowType.FORM.equals((Object)startType)) {
                if (!CollectionUtils.isEmpty(uploadStateWithOutImport)) {
                    for (UploadStateNew uploadStateNew : uploadStateWithOutImport) {
                        entities = uploadStateNew.getEntities();
                        notNeedImportFormMaps.computeIfAbsent(uploadStateNew.getFormId(), key -> new ArrayList()).add(entities);
                        notNeedImportEntityMaps.computeIfAbsent(uploadStateNew.getEntities().getValue(dimensionName).toString(), key -> new ArrayList()).add(entities);
                    }
                }
                this.getNeedUploadUnit(entitys, notNeedImportEntityMaps, formKeys.size() * adjustSize, needUploadUnit);
            } else {
                if (!CollectionUtils.isEmpty(uploadStateWithOutImport)) {
                    for (UploadStateNew uploadStateNew : uploadStateWithOutImport) {
                        entities = uploadStateNew.getEntities();
                        notNeedImportFlowMaps.computeIfAbsent(uploadStateNew.getFormId(), key -> new ArrayList()).add(entities);
                        notNeedImportEntityMaps.computeIfAbsent(uploadStateNew.getEntities().getValue(dimensionName).toString(), key -> new ArrayList()).add(entities);
                    }
                    for (Map.Entry entry : notNeedImportFlowMaps.entrySet()) {
                        String formGroupKey = (String)entry.getKey();
                        if (CollectionUtils.isEmpty((Collection)entry.getValue())) continue;
                        List formKeysFroGroup = this.runTimeViewController.getAllFormsInGroup(formGroupKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
                        for (String formKey : formKeysFroGroup) {
                            notNeedImportFormMaps.computeIfAbsent(formKey, key -> new ArrayList()).addAll((Collection)entry.getValue());
                        }
                    }
                }
                List<String> formGroupLists = contextExpendParam.getFormGroupLists();
                this.getNeedUploadUnit(entitys, notNeedImportEntityMaps, formGroupLists.size() * adjustSize, needUploadUnit);
            }
            Map<String, List<DimensionValueSet>> notNeedImportFormMaps1 = contextExpendParam.getNotNeedImportFormMaps();
            notNeedImportFormMaps1.putAll(notNeedImportFormMaps);
            if (needUploadUnit.size() == 0) {
                contextExpendParam.setAddUnitUpload(true);
                String string = this.taskOptionController.getValue(executeParam.getTaskKey(), ((UnitEdit)SpringBeanUtils.getBean(UnitEdit.class)).getKey());
                if (contextExpendParam.getNoExistUnit().size() == 0 || !StringUtils.hasText(transmissionContext.getFmdmForm()) || !"1".equals(string)) {
                    dataImportResult.setResult(false);
                }
            }
            if (uploadStateWithOutImport.size() > 0) {
                int n2 = dataImportResult.getSyncErrorNum();
                dataImportResult.setSyncErrorNum(n2 + uploadStateWithOutImport.size());
            }
            message = this.logEntityInfoBeforeImport(transmissionContext, entityDefine, needUploadUnit, uploadStateWithOutImport, formGroups);
        } else {
            needUploadUnit.addAll(entitys);
            StringBuilder log = new StringBuilder(MultilingualLog.checkUploadMessage(3, entityDefine.getTitle() + "[" + entityDefine.getCode() + "]"));
            this.doMessageForUnit(entityRowMap, log);
            message = log.toString();
            logHelper.appendLog(message);
        }
        if (needUploadUnit.size() > 0) {
            String successMessage = MultilingualLog.checkUploadMessage(2, "");
            needUploadUnit.forEach(t -> {
                EntityInfoParam entityInfoParam = (EntityInfoParam)entityRowMap.get(t);
                if (entityInfoParam != null) {
                    dataImportResult.addSuccessEntity(entityInfoParam.getTitle(), entityInfoParam.getEntityKeyData(), successMessage);
                }
            });
        }
        return message;
    }

    private void getNeedUploadUnit(List<String> entitys, Map<String, List<DimensionValueSet>> notNeedImportEntityMaps, int flowAndAdjustNumForEntity, List<String> needUploadUnit) {
        for (String entity : entitys) {
            if (!CollectionUtils.isEmpty((Collection)notNeedImportEntityMaps.get(entity))) {
                int entityNotImportFlowSize = notNeedImportEntityMaps.get(entity).size();
                if (entityNotImportFlowSize == flowAndAdjustNumForEntity) continue;
                needUploadUnit.add(entity);
                continue;
            }
            needUploadUnit.add(entity);
        }
    }

    private List<UploadStateNew> queryUpload(ITransmissionContext defaultTransmissionContext, List<FormGroupDefine> formGroups) throws Exception {
        IExecuteParam executeParam = defaultTransmissionContext.getExecuteParam();
        List<String> formGroupLists = defaultTransmissionContext.getContextExpendParam().getFormGroupLists();
        List<String> allForms = executeParam.getForms();
        for (String form : allForms) {
            List groups = this.runTimeViewController.getFormGroupsByFormKey(form);
            formGroups.addAll(groups);
        }
        Set formGroupSets = formGroups.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        formGroupLists.addAll(formGroupSets);
        return this.queryUploadStateService.queryUploadStates(executeParam.getFormSchemeKey(), executeParam.getDimensionValueSet(), allForms, formGroupLists);
    }

    private String logEntityInfoBeforeImport(ITransmissionContext transmissionContext, IEntityDefine entityDefine, List<String> needUploadUnit, List<UploadStateNew> uploadStateWithOutImport, List<FormGroupDefine> formGroups) throws Exception {
        Map<String, EntityInfoParam> entityRowMap = transmissionContext.getContextExpendParam().getUnits();
        String dimensionName = transmissionContext.getContextExpendParam().getDimensionName();
        List<String> entitiesWithOutImport = uploadStateWithOutImport.stream().map(t -> t.getEntities().getValue(dimensionName).toString()).distinct().collect(Collectors.toList());
        StringBuilder sbs = new StringBuilder(MultilingualLog.checkUploadMessage(3, entityDefine.getTitle() + "[" + entityDefine.getCode() + "]"));
        this.doMessageForUnit(entityRowMap, sbs);
        if (entitiesWithOutImport.size() > 0) {
            String s1 = this.doWithoutMessage(transmissionContext, entitiesWithOutImport, uploadStateWithOutImport, formGroups);
            sbs.append(s1);
        }
        if (needUploadUnit.size() > 0) {
            sbs.append("\r\n").append(MultilingualLog.checkUploadMessage(8, String.valueOf(needUploadUnit.size())));
            Utils.setMessage(needUploadUnit, entityRowMap, sbs);
        } else {
            sbs.append("\r\n").append(MultilingualLog.checkUploadMessage(9, ""));
        }
        transmissionContext.getLogHelper().appendLog(sbs.toString());
        return sbs.toString();
    }

    private void doMessageForUnit(Map<String, EntityInfoParam> entityRowMap, StringBuilder sbs) {
        int i = 0;
        for (Map.Entry<String, EntityInfoParam> unit : entityRowMap.entrySet()) {
            EntityInfoParam row = unit.getValue();
            sbs.append(row.getTitle()).append("[").append(row.getEntityKeyData()).append("]");
            if (i < entityRowMap.size() - 1) {
                sbs.append("\uff0c");
            } else {
                sbs.append("\u3002");
            }
            ++i;
        }
    }

    private String doWithoutMessage(ITransmissionContext transmissionContext, List<String> entitiesWithOutImport, List<UploadStateNew> uploadStateWithOutImport, List<FormGroupDefine> formGroups) throws Exception {
        IExecuteParam executeParam = transmissionContext.getExecuteParam();
        ContextExpendParam contextExpendParam = transmissionContext.getContextExpendParam();
        List<String> allFormKeys = executeParam.getForms();
        WorkFlowType startType = contextExpendParam.getWorkFlowType();
        String dimensionName = contextExpendParam.getDimensionName();
        DataImportResult syncResult = transmissionContext.getDataImportResult();
        Map<String, EntityInfoParam> entityRowMap = contextExpendParam.getUnits();
        List formDefines = this.runTimeViewController.queryFormsById(allFormKeys);
        StringBuilder sbs = new StringBuilder();
        sbs.append("\r\n").append(MultilingualLog.checkUploadMessage(4, ""));
        if (WorkFlowType.ENTITY.equals((Object)startType)) {
            String uploadMessage = MultilingualLog.checkUploadMessage(5, "");
            for (String s : entitiesWithOutImport) {
                EntityInfoParam entityInfoParam = entityRowMap.get(s);
                sbs.append(entityInfoParam.getTitle()).append("[").append(entityInfoParam.getEntityKeyData()).append("]");
                syncResult.getFailUnits().computeIfAbsent(s, key -> new ArrayList()).add(new DataImportMessage(entityInfoParam.getTitle(), s, uploadMessage));
                sbs.append("\uff0c");
            }
        } else if (WorkFlowType.FORM.equals((Object)startType)) {
            Map<String, FormDefine> formCodeToFormDefine = formDefines.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a, (k1, k2) -> k1));
            HashMap<String, Set> entityToFormKeys = new HashMap<String, Set>();
            for (UploadStateNew uploadStateNew : uploadStateWithOutImport) {
                DimensionValueSet dimensionValueSet = uploadStateNew.getEntities();
                entityToFormKeys.computeIfAbsent(dimensionValueSet.getValue(dimensionName).toString(), key -> new HashSet()).add(uploadStateNew.getEntities().getValue("FORMID").toString());
            }
            String formUploadMessage = MultilingualLog.checkUploadMessage(6, "");
            for (Map.Entry entry : entityToFormKeys.entrySet()) {
                String entityKey = (String)entry.getKey();
                Set formKeys = (Set)entry.getValue();
                EntityInfoParam entityRow = entityRowMap.get(entityKey);
                sbs.append(entityRow.getTitle()).append("[").append(entityRow.getEntityKeyData()).append("]\uff1a");
                int i = 0;
                int length = formKeys.size();
                StringBuilder formMessage = new StringBuilder();
                for (String formKey : formKeys) {
                    FormDefine formDefine = formCodeToFormDefine.get(formKey);
                    if (i < length - 1) {
                        sbs.append(formDefine.getTitle()).append("[").append(formDefine.getFormCode()).append("]\u3001");
                        formMessage.append(formDefine.getTitle()).append("[").append(formDefine.getFormCode()).append("]\u3001");
                    } else {
                        sbs.append(formDefine.getTitle()).append("[").append(formDefine.getFormCode()).append("]\uff0c");
                        formMessage.append(formDefine.getTitle()).append("[").append(formDefine.getFormCode()).append("]");
                    }
                    ++i;
                }
                String message = String.format(formUploadMessage, formMessage.toString());
                syncResult.getFailUnits().computeIfAbsent(entityRow.getEntityKeyData(), key -> new ArrayList()).add(new DataImportMessage(entityRow.getTitle(), entityRow.getEntityKeyData(), message.toString()));
            }
        } else {
            Map<String, FormGroupDefine> formGroupKeyToFormGroupDefine = formGroups.stream().collect(Collectors.toMap(IBaseMetaItem::getKey, a -> a, (k1, k2) -> k1));
            HashMap<String, Set> entityToFormGroupKeys = new HashMap<String, Set>();
            for (UploadStateNew uploadStateNew : uploadStateWithOutImport) {
                DimensionValueSet dimensionValueSet = uploadStateNew.getEntities();
                entityToFormGroupKeys.computeIfAbsent(dimensionValueSet.getValue(dimensionName).toString(), key -> new HashSet()).add(dimensionValueSet.getValue("FORMID").toString());
            }
            for (String string : entitiesWithOutImport) {
                entityToFormGroupKeys.put(string, new HashSet());
            }
            for (UploadStateNew uploadStateNew : uploadStateWithOutImport) {
                String string = uploadStateNew.getEntities().getValue(dimensionName).toString();
                Set formGroupKeysWithoutImport = (Set)entityToFormGroupKeys.get(string);
                formGroupKeysWithoutImport.add(uploadStateNew.getEntities().getValue("FORMID").toString());
            }
            String formGroupUploadMessage = MultilingualLog.checkUploadMessage(7, "");
            for (Map.Entry entry : entityToFormGroupKeys.entrySet()) {
                String entityKey = (String)entry.getKey();
                Set formGroupKeys = (Set)entry.getValue();
                EntityInfoParam entityRow = entityRowMap.get(entityKey);
                sbs.append(entityRow.getTitle()).append("[").append(entityRow.getEntityKeyData()).append("]\uff1a");
                StringBuilder formGroupMessage = new StringBuilder();
                int i = 0;
                int length = formGroupKeys.size();
                for (String formGroupKey : formGroupKeys) {
                    FormGroupDefine formGroupDefine = formGroupKeyToFormGroupDefine.get(formGroupKey);
                    if (i < length - 1) {
                        sbs.append(formGroupDefine.getTitle()).append("[").append(formGroupDefine.getCode()).append("]\u3001");
                        formGroupMessage.append(formGroupDefine.getTitle()).append("[").append(formGroupDefine.getCode()).append("]\u3001");
                    } else {
                        sbs.append(formGroupDefine.getTitle()).append("[").append(formGroupDefine.getCode()).append("],");
                        formGroupMessage.append(formGroupDefine.getTitle()).append("[").append(formGroupDefine.getCode()).append("]");
                    }
                    ++i;
                }
                String message = String.format(formGroupUploadMessage, formGroupMessage.toString());
                syncResult.getFailUnits().computeIfAbsent(entityRow.getEntityKeyData(), key -> new ArrayList()).add(new DataImportMessage(entityRow.getTitle(), entityRow.getEntityKeyData(), message.toString()));
            }
        }
        String s = sbs.toString();
        s = s.substring(0, s.length() - 1);
        return s;
    }
}

