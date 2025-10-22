/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2
 *  com.jiuqi.nr.attachment.input.CommonParamsDTO
 *  com.jiuqi.nr.attachment.message.FileInfo
 *  com.jiuqi.nr.attachment.output.FileInfosAndGroup
 *  com.jiuqi.nr.attachment.service.FilePoolService
 *  com.jiuqi.nr.data.access.util.DataPermissionUtil
 *  com.jiuqi.nr.datacheckcommon.helper.EntityQueryHelper
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService
 *  com.jiuqi.nr.definition.util.AttachmentObj
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.nr.attachmentcheck.service.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.impl.controller.DataDefinitionRuntimeController2;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.output.FileInfosAndGroup;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckFieldResultItem;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckFileItem;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckParam;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckResultItem;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckReturnInfo;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentFieldInfo;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentFieldStruct;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentFormStruct;
import com.jiuqi.nr.attachmentcheck.bean.ChineseCharToEn;
import com.jiuqi.nr.attachmentcheck.bean.FieldAndFileInfo;
import com.jiuqi.nr.attachmentcheck.bean.FileKeyAndInfo;
import com.jiuqi.nr.attachmentcheck.bean.MapWrapper;
import com.jiuqi.nr.attachmentcheck.service.IAttachmentCheckService;
import com.jiuqi.nr.data.access.util.DataPermissionUtil;
import com.jiuqi.nr.datacheckcommon.helper.EntityQueryHelper;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.impl.DesignFormDefineBigDataUtil;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeDataLinkService;
import com.jiuqi.nr.definition.util.AttachmentObj;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.va.domain.common.JSONUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AttachmentCheckServiceImpl
implements IAttachmentCheckService {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentCheckServiceImpl.class);
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataDefinitionRuntimeController2 dataDefinitionRuntimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IRuntimeDataLinkService dataLinkService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private FilePoolService filePoolService;
    @Autowired
    private EntityQueryHelper entityQueryHelper;
    private static final String NO_ROWID = "NO_ROWID";

    @Override
    public AttachmentCheckReturnInfo attachmentCheck(AttachmentCheckParam param, AsyncTaskMonitor monitor) throws Exception {
        AttachmentCheckReturnInfo returnInfo = new AttachmentCheckReturnInfo();
        if (null != monitor) {
            monitor.progressAndMessage(0.1, "\u6b63\u5728\u8fdb\u884c\u9644\u62a5\u6587\u4ef6\u68c0\u67e5");
        }
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(param.getFormSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(param.getTaskKey());
        String mainDimName = this.entityMetaService.queryEntity(taskDefine.getDw()).getDimensionName();
        ArrayList<MapWrapper> curUnitKeys = new ArrayList<MapWrapper>();
        ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>();
        List dimensionCombinations = param.getDims().getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            dimensionValueSets.add(dimensionCombination.toDimensionValueSet());
            HashMap<String, String> dimNameValueMap = new HashMap<String, String>();
            for (String dimName : dimensionCombination.getNames()) {
                dimNameValueMap.put(dimName, (String)dimensionCombination.getValue(dimName));
            }
            curUnitKeys.add(new MapWrapper(dimNameValueMap));
        }
        if (param.getSelBlobItem().size() == 0) {
            ArrayList<AttachmentCheckResultItem> errItems = new ArrayList<AttachmentCheckResultItem>();
            ArrayList<AttachmentCheckResultItem> allItems = new ArrayList<AttachmentCheckResultItem>();
            returnInfo.setErrItems(errItems);
            returnInfo.setAllItems(allItems);
            returnInfo.setSelZBCount(0);
            returnInfo.setUnitCount(curUnitKeys.size());
            if (null != monitor) {
                monitor.progressAndMessage(0.99, "\u6b63\u5728\u8fdb\u884c\u9644\u62a5\u6587\u4ef6\u68c0\u67e5");
            }
            return returnInfo;
        }
        String period = (String)param.getDims().combineDim().getValue("DATATIME");
        IEntityQuery query = this.entityQueryHelper.getEntityQuery(param.getFormSchemeKey(), period, AuthorityType.None);
        IEntityTable entityTable = this.entityQueryHelper.buildEntityTable(query, param.getFormSchemeKey(), false);
        HashMap<String, String> entityMap = new HashMap<String, String>();
        LinkedHashMap<MapWrapper, AttachmentCheckResultItem> resultMap = new LinkedHashMap<MapWrapper, AttachmentCheckResultItem>();
        for (MapWrapper mapWrapper : curUnitKeys) {
            if (resultMap.containsKey(mapWrapper)) continue;
            AttachmentCheckResultItem item = this.getCheckResultItem(param, entityTable, entityMap, null, mapWrapper, mainDimName, taskDefine, formSchemeDefine);
            resultMap.put(mapWrapper, item);
        }
        HashMap<FieldDefine, AttachmentFieldInfo> fixFields = new HashMap<FieldDefine, AttachmentFieldInfo>();
        HashMap<FieldDefine, AttachmentFieldInfo> floatFields = new HashMap<FieldDefine, AttachmentFieldInfo>();
        if (null != monitor && monitor.isCancel()) {
            return returnInfo;
        }
        int fieldCount = this.parseFields(param.getSelBlobItem(), fixFields, floatFields, monitor);
        if (null != monitor && monitor.isCancel()) {
            return returnInfo;
        }
        HashMap<String, String> dataLinkRegionCatch = new HashMap<String, String>();
        this.getFixFieldsCheckResult(param, fixFields, dimensionValueSets, mainDimName, dataLinkRegionCatch, resultMap, entityTable, entityMap, taskDefine, formSchemeDefine, monitor);
        if (null != monitor && monitor.isCancel()) {
            return returnInfo;
        }
        this.getFloatFieldsCheckResult(param, fieldCount, floatFields, dimensionValueSets, mainDimName, dataLinkRegionCatch, resultMap, entityTable, entityMap, taskDefine, formSchemeDefine, monitor);
        if (null != monitor && monitor.isCancel()) {
            return returnInfo;
        }
        ArrayList<AttachmentCheckResultItem> allResultItems = new ArrayList<AttachmentCheckResultItem>(resultMap.values());
        ArrayList<AttachmentCheckResultItem> errItems = new ArrayList<AttachmentCheckResultItem>();
        ArrayList<AttachmentCheckResultItem> allItems = new ArrayList<AttachmentCheckResultItem>();
        for (AttachmentCheckResultItem resultItem : allResultItems) {
            for (AttachmentCheckFieldResultItem fieldItem : resultItem.getFieldItems()) {
                List<AttachmentCheckFileItem> errFileItems = fieldItem.getErrFilesInField();
                if (fieldItem.getErrFilesInField().size() >= 2) {
                    Collections.sort(errFileItems, new Comparator<AttachmentCheckFileItem>(){

                        @Override
                        public int compare(AttachmentCheckFileItem o1, AttachmentCheckFileItem o2) {
                            if (null == o1 || null == o2) {
                                return -1;
                            }
                            String s1 = ChineseCharToEn.getFirstLetter(o1.getFileName());
                            String s2 = ChineseCharToEn.getFirstLetter(o2.getFileName());
                            return s1.compareTo(s2);
                        }
                    });
                }
                if (fieldItem.getFilesInField().size() < 2) continue;
                List<AttachmentCheckFileItem> fileItems = fieldItem.getFilesInField();
                fileItems.removeAll(errFileItems);
                ArrayList<AttachmentCheckFileItem> newFileItems = new ArrayList<AttachmentCheckFileItem>(fileItems);
                Collections.sort(newFileItems, new Comparator<AttachmentCheckFileItem>(){

                    @Override
                    public int compare(AttachmentCheckFileItem o1, AttachmentCheckFileItem o2) {
                        if (null == o1 || null == o2) {
                            return -1;
                        }
                        String s1 = ChineseCharToEn.getFirstLetter(o1.getFileName());
                        String s2 = ChineseCharToEn.getFirstLetter(o2.getFileName());
                        return s1.compareTo(s2);
                    }
                });
                fieldItem.getFilesInField().clear();
                fieldItem.getFilesInField().addAll(errFileItems);
                fieldItem.getFilesInField().addAll(newFileItems);
            }
            allItems.add(resultItem);
        }
        for (AttachmentCheckResultItem resultItem : allResultItems) {
            boolean isNeedAdd = false;
            List<AttachmentCheckFieldResultItem> fieldItems = resultItem.getFieldItems();
            for (AttachmentCheckFieldResultItem fieldItem : fieldItems) {
                if (fieldItem.getErrFilesInField().size() > 0) {
                    isNeedAdd = true;
                    break;
                }
                if (0 == fieldItem.getFilesInField().size()) {
                    if (fieldItem.getNullable()) continue;
                    isNeedAdd = true;
                    break;
                }
                if (fieldItem.isLimitMaxNum()) {
                    isNeedAdd = true;
                    break;
                }
                if (!fieldItem.isLimitMinNum()) continue;
                isNeedAdd = true;
                break;
            }
            if (!isNeedAdd) continue;
            errItems.add(resultItem);
        }
        if (errItems.size() > 0) {
            final HashMap untityOrderDic = this.entityQueryHelper.entityOrderByKey(entityTable);
            Collections.sort(allItems, new Comparator<AttachmentCheckResultItem>(){

                @Override
                public int compare(AttachmentCheckResultItem o1, AttachmentCheckResultItem o2) {
                    if (o1 == null || o2 == null) {
                        return -1;
                    }
                    int level1 = untityOrderDic.containsKey(o1.getUnitCode()) ? (Integer)untityOrderDic.get(o1.getUnitCode()) : 0;
                    int level2 = untityOrderDic.containsKey(o2.getUnitCode()) ? (Integer)untityOrderDic.get(o2.getUnitCode()) : 0;
                    return level1 - level2;
                }
            });
            Collections.sort(errItems, new Comparator<AttachmentCheckResultItem>(){

                @Override
                public int compare(AttachmentCheckResultItem o1, AttachmentCheckResultItem o2) {
                    if (o1 == null || o2 == null) {
                        return -1;
                    }
                    int level1 = untityOrderDic.containsKey(o1.getUnitCode()) ? (Integer)untityOrderDic.get(o1.getUnitCode()) : 0;
                    int level2 = untityOrderDic.containsKey(o2.getUnitCode()) ? (Integer)untityOrderDic.get(o2.getUnitCode()) : 0;
                    return level1 - level2;
                }
            });
        }
        returnInfo.setErrItems(errItems);
        returnInfo.setAllItems(allItems);
        int iCount = 0;
        for (AttachmentFormStruct formStruct : param.getSelBlobItem()) {
            if (null == formStruct.getChildren()) continue;
            iCount += formStruct.getChildren().size();
        }
        returnInfo.setSelZBCount(iCount);
        returnInfo.setUnitCount(curUnitKeys.size());
        if (null != monitor) {
            monitor.progressAndMessage(0.99, "\u6b63\u5728\u8fdb\u884c\u9644\u62a5\u6587\u4ef6\u68c0\u67e5");
        }
        return returnInfo;
    }

    private int parseFields(List<AttachmentFormStruct> blobItems, Map<FieldDefine, AttachmentFieldInfo> fixFields, Map<FieldDefine, AttachmentFieldInfo> floatFields, AsyncTaskMonitor monitor) {
        if (null != monitor) {
            monitor.progressAndMessage(0.1, "\u6b63\u5728\u8fdb\u884c\u9644\u62a5\u6587\u4ef6\u68c0\u67e5");
        }
        int fieldCount = 0;
        if (0 == blobItems.size()) {
            return fieldCount;
        }
        for (AttachmentFormStruct blobItem : blobItems) {
            for (AttachmentFieldStruct childBlobItem : blobItem.getChildren()) {
                try {
                    DataRegionDefine region;
                    AttachmentFieldInfo info;
                    int maxNumber;
                    int minNumber;
                    double maxValue;
                    double minValue;
                    DataLinkDefine linkDefine;
                    FieldDefine field;
                    block33: {
                        FormDefine formDefine;
                        String formCode = childBlobItem.getFormCode();
                        if (!StringUtils.hasText(formCode) && null != (formDefine = this.runTimeViewController.queryFormById(childBlobItem.getFormKey()))) {
                            formCode = formDefine.getFormCode();
                            childBlobItem.setFormCode(formCode);
                        }
                        if (null == (field = this.dataDefinitionRuntimeController.queryFieldDefine(childBlobItem.getKey())) || null == (linkDefine = this.runTimeViewController.queryDataLinkDefine(childBlobItem.getDataLinkKey()))) continue;
                        minValue = -1.0;
                        maxValue = -1.0;
                        minNumber = -1;
                        maxNumber = -1;
                        info = new AttachmentFieldInfo();
                        try {
                            String[] extArr;
                            ArrayList alist;
                            String attachment;
                            info.setFormCode(formCode);
                            info.setCanNull(field.getNullable());
                            BigDataDefine bigData = this.dataLinkService.getAttachmentDataFromLink(linkDefine.getKey());
                            if (null == bigData || null == bigData.getData() || "".equals(attachment = DesignFormDefineBigDataUtil.bytesToString((byte[])bigData.getData()))) break block33;
                            AttachmentObj attachmentObj = (AttachmentObj)JSONUtil.parseObject((String)attachment, AttachmentObj.class);
                            String maxValueStr = "".equals(attachmentObj.getMaxSize()) ? String.valueOf(-1) : attachmentObj.getMaxSize();
                            String minValueStr = "".equals(attachmentObj.getMinSize()) ? String.valueOf(-1) : attachmentObj.getMinSize();
                            String maxNumberStr = StringUtils.hasText(attachmentObj.getMaxNumber()) ? attachmentObj.getMaxNumber() : "-1";
                            String minNumberStr = StringUtils.hasText(attachmentObj.getMinNumber()) ? attachmentObj.getMinNumber() : "-1";
                            minValue = Double.parseDouble(minValueStr);
                            maxValue = Double.parseDouble(maxValueStr);
                            minNumber = Integer.parseInt(minNumberStr);
                            maxNumber = Integer.parseInt(maxNumberStr);
                            if (attachmentObj.getDocument().size() > 0) {
                                alist = attachmentObj.getDocument();
                                for (String ext : alist) {
                                    if (ext.contains("/")) {
                                        for (String s : extArr = ext.split("/")) {
                                            info.getExtList().add("." + s);
                                        }
                                        continue;
                                    }
                                    info.getExtList().add("." + ext);
                                }
                            }
                            if (attachmentObj.getImg().size() > 0) {
                                alist = attachmentObj.getImg();
                                for (String ext : alist) {
                                    if (ext.contains("/")) {
                                        for (String s : extArr = ext.split("/")) {
                                            info.getExtList().add("." + s);
                                        }
                                        continue;
                                    }
                                    info.getExtList().add("." + ext);
                                }
                            }
                            if (attachmentObj.getStadio().size() > 0) {
                                alist = attachmentObj.getStadio();
                                for (String ext : alist) {
                                    if (ext.contains("/")) {
                                        for (String s : extArr = ext.split("/")) {
                                            info.getExtList().add("." + s);
                                        }
                                        continue;
                                    }
                                    info.getExtList().add("." + ext);
                                }
                            }
                            if (attachmentObj.getVedio().size() > 0) {
                                alist = attachmentObj.getVedio();
                                for (String ext : alist) {
                                    if (ext.contains("/")) {
                                        for (String s : extArr = ext.split("/")) {
                                            info.getExtList().add("." + s);
                                        }
                                        continue;
                                    }
                                    info.getExtList().add("." + ext);
                                }
                            }
                            if (attachmentObj.getZip().size() <= 0) break block33;
                            alist = attachmentObj.getZip();
                            for (String ext : alist) {
                                if (ext.contains("/")) {
                                    for (String s : extArr = ext.split("/")) {
                                        info.getExtList().add("." + s);
                                    }
                                    continue;
                                }
                                info.getExtList().add("." + ext);
                            }
                        }
                        catch (Exception e) {
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                            continue;
                        }
                    }
                    if (minValue == -1.0 && maxValue == -1.0) {
                        info.setCheckFileSize(false);
                    } else {
                        info.setCheckFileSize(true);
                        if (maxValue == -1.0) {
                            maxValue = 2.147483647E9;
                        }
                    }
                    if ((region = this.runTimeViewController.queryDataRegionDefine(linkDefine.getRegionKey())) == null) continue;
                    ++fieldCount;
                    info.setBlobFieldStruct(childBlobItem);
                    info.setMinValue(minValue);
                    info.setMaxValue(maxValue);
                    info.setMinNumber(minNumber);
                    info.setMaxNumber(maxNumber);
                    if (region.getRegionKind() != DataRegionKind.DATA_REGION_SIMPLE) {
                        floatFields.put(field, info);
                        continue;
                    }
                    fixFields.put(field, info);
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
        }
        if (null != monitor) {
            monitor.progressAndMessage(0.2, "\u6b63\u5728\u8fdb\u884c\u9644\u62a5\u6587\u4ef6\u68c0\u67e5");
        }
        return fieldCount;
    }

    private void getFixFieldsCheckResult(AttachmentCheckParam param, Map<FieldDefine, AttachmentFieldInfo> fixFields, List<DimensionValueSet> dimensionValueSets, String mainDimName, Map<String, String> dataLinkRegionCatch, LinkedHashMap<MapWrapper, AttachmentCheckResultItem> resultMap, IEntityTable entityTable, Map<String, String> entityMap, TaskDefine taskDefine, FormSchemeDefine formSchemeDefine, AsyncTaskMonitor monitor) {
        if (0 == fixFields.size()) {
            return;
        }
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormulaSchemeKey(param.getFormulaSchemeKey());
        queryEnvironment.setFormSchemeKey(param.getFormSchemeKey());
        IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
        for (Map.Entry<FieldDefine, AttachmentFieldInfo> fieldEntry : fixFields.entrySet()) {
            dataQuery.addColumn(fieldEntry.getKey());
        }
        try {
            ExecutorContext executorContext = new ExecutorContext((IDataDefinitionRuntimeController)this.dataDefinitionRuntimeController);
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, (IDataDefinitionRuntimeController)this.dataDefinitionRuntimeController, this.entityViewRunTimeController, param.getFormSchemeKey());
            executorContext.setEnv((IFmlExecEnvironment)environment);
            executorContext.setJQReportModel(true);
            Collection filterDimValueSet = DataPermissionUtil.mergeDimensionValue(dimensionValueSets, (String)mainDimName);
            ArrayList<String> addedUnitKeys = new ArrayList<String>();
            for (DimensionValueSet dimensionValueSet : filterDimValueSet) {
                Object dimNameValue;
                dataQuery.setMasterKeys(dimensionValueSet);
                IDataTable dataTable = dataQuery.executeQuery((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext);
                double stepValue = 0.0;
                stepValue = 0.3 / (double)resultMap.size();
                HashMap<String, FieldAndFileInfo> groupAndFileInfoMap = new HashMap<String, FieldAndFileInfo>();
                for (int i = 0; i < dataTable.getCount(); ++i) {
                    if (null != monitor) {
                        monitor.progressAndMessage(0.2 + stepValue * (double)i, "\u6b63\u5728\u8fdb\u884c\u9644\u62a5\u6587\u4ef6\u68c0\u67e5");
                    }
                    IDataRow dataRow = dataTable.getItem(i);
                    DimensionValueSet dimSet = dataRow.getRowKeys();
                    dimNameValue = new HashMap();
                    for (int j = 0; j < dimensionValueSet.size(); ++j) {
                        String dimName = dimensionValueSet.getName(j);
                        dimNameValue.put(dimName, (String)dimSet.getValue(dimName));
                    }
                    for (Map.Entry<FieldDefine, AttachmentFieldInfo> fieldEntry : fixFields.entrySet()) {
                        FieldAndFileInfo fieldAndFileInfo = new FieldAndFileInfo();
                        fieldAndFileInfo.setDimNameValue(new MapWrapper((Map<String, String>)dimNameValue));
                        fieldAndFileInfo.setFieldDefine(fieldEntry.getKey());
                        fieldAndFileInfo.setBlobFieldInfo(fieldEntry.getValue());
                        String value = dataRow.getAsString(fieldEntry.getKey());
                        if (!StringUtils.hasText(value)) continue;
                        groupAndFileInfoMap.put(value, fieldAndFileInfo);
                    }
                }
                this.getFileInfo(param.getTaskKey(), groupAndFileInfoMap);
                for (String groupKey : groupAndFileInfoMap.keySet()) {
                    AttachmentCheckResultItem item;
                    FieldAndFileInfo fieldAndFileInfo = (FieldAndFileInfo)groupAndFileInfoMap.get(groupKey);
                    dimNameValue = fieldAndFileInfo.getDimNameValue();
                    if (resultMap.containsKey(dimNameValue)) {
                        item = resultMap.get(dimNameValue);
                    } else {
                        item = this.getCheckResultItem(param, entityTable, entityMap, null, (MapWrapper)dimNameValue, mainDimName, taskDefine, formSchemeDefine);
                        resultMap.put((MapWrapper)dimNameValue, item);
                    }
                    Map<String, FileKeyAndInfo> fileKeyAndInfoMap = fieldAndFileInfo.getFileKeyAndInfoMap();
                    FieldDefine fieldDefine = fieldAndFileInfo.getFieldDefine();
                    AttachmentFieldInfo blobFieldInfo = fieldAndFileInfo.getBlobFieldInfo();
                    int maxNumber = blobFieldInfo.getMaxNumber();
                    int minNumber = blobFieldInfo.getMinNumber();
                    int fileNum = fileKeyAndInfoMap.size();
                    if (fileKeyAndInfoMap.keySet().isEmpty()) {
                        if (blobFieldInfo.isCanNull()) continue;
                        AttachmentCheckFieldResultItem fieldItem = new AttachmentCheckFieldResultItem();
                        if (minNumber > 0) {
                            fieldItem.setLimitMinNum(true);
                        }
                        fieldItem.setFormCode(blobFieldInfo.getFormCode());
                        fieldItem.setFormKey(blobFieldInfo.getFormKey());
                        fieldItem.setFormTitle(blobFieldInfo.getFormTitle());
                        String dataLinkKey = blobFieldInfo.getDataLinkKey();
                        String dataRegionKey = dataLinkRegionCatch.get(dataLinkKey);
                        if (null == dataRegionKey) {
                            DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(dataLinkKey);
                            dataRegionKey = dataLinkDefine.getRegionKey();
                            dataLinkRegionCatch.put(dataLinkKey, dataRegionKey);
                        }
                        fieldItem.setDataRegionKey(dataRegionKey);
                        fieldItem.setDataLinkKey(dataLinkKey);
                        fieldItem.setFieldCode(fieldDefine.getCode());
                        fieldItem.setFieldKey(fieldDefine.getKey());
                        fieldItem.setFieldTitle(fieldDefine.getTitle());
                        fieldItem.setRowId(null);
                        fieldItem.setNullable(fieldDefine.getNullable());
                        item.addFieldItem(fieldItem);
                        fieldItem.setNullable(false);
                        addedUnitKeys.add(String.format("%s_%s_%s", ((MapWrapper)dimNameValue).getDimNameValueMap().toString(), blobFieldInfo.getFormCode(), fieldDefine.getCode()));
                        continue;
                    }
                    double minValue = blobFieldInfo.getMinValue();
                    double maxValue = blobFieldInfo.getMaxValue();
                    List<String> extList = blobFieldInfo.getExtList();
                    AttachmentCheckFieldResultItem fieldItem = new AttachmentCheckFieldResultItem();
                    if (maxNumber > 0 && fileNum > maxNumber) {
                        fieldItem.setLimitMaxNum(true);
                    } else if (minNumber > 0 && fileNum < minNumber) {
                        fieldItem.setLimitMinNum(true);
                    }
                    fieldItem.setFormCode(blobFieldInfo.getFormCode());
                    fieldItem.setFormKey(blobFieldInfo.getFormKey());
                    fieldItem.setFormTitle(blobFieldInfo.getFormTitle());
                    String dataLinkKey = blobFieldInfo.getDataLinkKey();
                    String dataRegionKey = dataLinkRegionCatch.get(dataLinkKey);
                    if (null == dataRegionKey) {
                        DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(dataLinkKey);
                        dataRegionKey = dataLinkDefine.getRegionKey();
                        dataLinkRegionCatch.put(dataLinkKey, dataRegionKey);
                    }
                    fieldItem.setDataRegionKey(dataRegionKey);
                    fieldItem.setDataLinkKey(dataLinkKey);
                    fieldItem.setFieldCode(fieldDefine.getCode());
                    fieldItem.setFieldKey(fieldDefine.getKey());
                    fieldItem.setFieldTitle(fieldDefine.getTitle());
                    fieldItem.setRowId(null);
                    fieldItem.setNullable(fieldDefine.getNullable());
                    item.addFieldItem(fieldItem);
                    addedUnitKeys.add(String.format("%s_%s_%s", ((MapWrapper)dimNameValue).getDimNameValueMap().toString(), blobFieldInfo.getFormCode(), fieldDefine.getCode()));
                    for (String fileKey : fileKeyAndInfoMap.keySet()) {
                        FileKeyAndInfo fileKeyAndInfo = fileKeyAndInfoMap.get(fileKey);
                        double fileSize = fileKeyAndInfo.getSize();
                        fileSize = fileSize / 1024.0 / 1024.0;
                        BigDecimal b = BigDecimal.valueOf(fileSize);
                        fileSize = b.setScale(2, 4).doubleValue();
                        fieldItem.addFileSize(fileSize);
                        AttachmentCheckFileItem fileItem = new AttachmentCheckFileItem();
                        fileItem.setFileName(fileKeyAndInfo.getName());
                        fileItem.setFileSize(fileSize);
                        boolean hasAdded = false;
                        if (blobFieldInfo.isCheckFileSize() && (fileSize < minValue || fileSize > maxValue)) {
                            fieldItem.addErrFile(fileItem);
                            hasAdded = true;
                        }
                        if (extList.size() > 0 && !hasAdded && !extList.contains(fileKeyAndInfo.getExtension())) {
                            fieldItem.addErrFile(fileItem);
                        }
                        fieldItem.addFile(fileItem);
                    }
                }
            }
            for (Map.Entry entry : resultMap.entrySet()) {
                if (null != monitor) {
                    monitor.progressAndMessage(0.5, "\u6b63\u5728\u8fdb\u884c\u9644\u62a5\u6587\u4ef6\u68c0\u67e5");
                }
                AttachmentCheckResultItem item = (AttachmentCheckResultItem)entry.getValue();
                for (Map.Entry<FieldDefine, AttachmentFieldInfo> fieldEntry : fixFields.entrySet()) {
                    if (addedUnitKeys.contains(String.format("%s_%s_%s", ((MapWrapper)entry.getKey()).getDimNameValueMap().toString(), fieldEntry.getValue().getFormCode(), fieldEntry.getKey().getCode()))) continue;
                    AttachmentCheckFieldResultItem fieldItem = new AttachmentCheckFieldResultItem();
                    int minNumber = fieldEntry.getValue().getMinNumber();
                    if (minNumber > 0) {
                        fieldItem.setLimitMinNum(true);
                    }
                    fieldItem.setFormCode(fieldEntry.getValue().getFormCode());
                    fieldItem.setFormKey(fieldEntry.getValue().getFormKey());
                    fieldItem.setFormTitle(fieldEntry.getValue().getFormTitle());
                    String dataLinkKey = fieldEntry.getValue().getDataLinkKey();
                    String dataRegionKey = dataLinkRegionCatch.get(dataLinkKey);
                    if (null == dataRegionKey) {
                        DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(dataLinkKey);
                        dataRegionKey = dataLinkDefine.getRegionKey();
                        dataLinkRegionCatch.put(dataLinkKey, dataRegionKey);
                    }
                    fieldItem.setDataRegionKey(dataRegionKey);
                    fieldItem.setDataLinkKey(dataLinkKey);
                    fieldItem.setFieldCode(fieldEntry.getKey().getCode());
                    fieldItem.setFieldKey(fieldEntry.getKey().getKey());
                    fieldItem.setFieldTitle(fieldEntry.getKey().getTitle());
                    fieldItem.setRowId(null);
                    fieldItem.setNullable(fieldEntry.getKey().getNullable());
                    item.addFieldItem(fieldItem);
                }
            }
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    private void getFileInfo(String taskKey, Map<String, FieldAndFileInfo> groupAndFileInfoMap) {
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        ArrayList<String> groupKeys = new ArrayList<String>(groupAndFileInfoMap.keySet());
        CommonParamsDTO params = new CommonParamsDTO();
        params.setTaskKey(taskKey);
        params.setDataSchemeKey(dataScheme.getKey());
        List fileInfoByGroup = this.filePoolService.getFileInfoByGroup(groupKeys, params);
        for (FileInfosAndGroup fileInfosAndGroup : fileInfoByGroup) {
            FieldAndFileInfo fieldAndFileInfo = groupAndFileInfoMap.get(fileInfosAndGroup.getGroupKey());
            Map<String, FileKeyAndInfo> fileKeyAndInfoMap = fieldAndFileInfo.getFileKeyAndInfoMap();
            List fileInfos = fileInfosAndGroup.getFileInfos();
            for (FileInfo fileInfo : fileInfos) {
                FileKeyAndInfo fileKeyAndInfo = fileKeyAndInfoMap.get(fileInfo.getKey());
                if (null == fileKeyAndInfo) {
                    fileKeyAndInfo = new FileKeyAndInfo();
                    fileKeyAndInfo.setFileKey(fileInfo.getKey());
                    fileKeyAndInfoMap.put(fileInfo.getKey(), fileKeyAndInfo);
                }
                fileKeyAndInfo.setName(fileInfo.getName());
                fileKeyAndInfo.setExtension(fileInfo.getExtension());
                fileKeyAndInfo.setSize(fileInfo.getSize());
            }
        }
    }

    private void getFloatFieldsCheckResult(AttachmentCheckParam param, int fieldCount, Map<FieldDefine, AttachmentFieldInfo> floatFields, List<DimensionValueSet> dimensionValueSets, String mainDimName, Map<String, String> dataLinkRegionCatch, LinkedHashMap<MapWrapper, AttachmentCheckResultItem> resultMap, IEntityTable entityTable, Map<String, String> entityMap, TaskDefine taskDefine, FormSchemeDefine formSchemeDefine, AsyncTaskMonitor monitor) {
        if (0 == floatFields.size()) {
            return;
        }
        int index = 0;
        double stepValue = 0.3 / (double)fieldCount;
        for (Map.Entry<FieldDefine, AttachmentFieldInfo> fieldEntry : floatFields.entrySet()) {
            if (null != monitor) {
                monitor.progressAndMessage(0.5 + stepValue * (double)index, "\u6b63\u5728\u8fdb\u884c\u9644\u62a5\u6587\u4ef6\u68c0\u67e5");
                ++index;
            }
            double minValue = fieldEntry.getValue().getMinValue();
            double maxValue = fieldEntry.getValue().getMaxValue();
            int maxNumber = fieldEntry.getValue().getMaxNumber();
            int minNumber = fieldEntry.getValue().getMinNumber();
            List<String> extList = fieldEntry.getValue().getExtList();
            QueryEnvironment queryEnvironment = new QueryEnvironment();
            queryEnvironment.setFormulaSchemeKey(param.getFormulaSchemeKey());
            queryEnvironment.setFormSchemeKey(param.getFormSchemeKey());
            IDataQuery dataQuery = this.dataAccessProvider.newDataQuery(queryEnvironment);
            dataQuery.addColumn(fieldEntry.getKey());
            try {
                ExecutorContext executorContext = new ExecutorContext((IDataDefinitionRuntimeController)this.dataDefinitionRuntimeController);
                ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, (IDataDefinitionRuntimeController)this.dataDefinitionRuntimeController, this.entityViewRunTimeController, param.getFormSchemeKey());
                executorContext.setEnv((IFmlExecEnvironment)environment);
                executorContext.setJQReportModel(true);
                Collection filterDimValueSet = DataPermissionUtil.mergeDimensionValue(dimensionValueSets, (String)mainDimName);
                for (DimensionValueSet dimensionValueSet : filterDimValueSet) {
                    dataQuery.setMasterKeys(dimensionValueSet);
                    IDataTable dataTable = dataQuery.executeQuery((com.jiuqi.np.dataengine.executors.ExecutorContext)executorContext);
                    ArrayList<MapWrapper> addedDimsKeys = new ArrayList<MapWrapper>();
                    for (int i = 0; i < dataTable.getCount(); ++i) {
                        IDataRow dataRow = dataTable.getItem(i);
                        DimensionValueSet dimensionValueSet2 = dataRow.getRowKeys();
                        HashMap<String, String> dimNameValue = new HashMap<String, String>();
                        for (int j = 0; j < dimensionValueSet.size(); ++j) {
                            String dimName = dimensionValueSet.getName(j);
                            dimNameValue.put(dimName, (String)dimensionValueSet2.getValue(dimName));
                        }
                        MapWrapper mapWrapper = new MapWrapper(dimNameValue);
                        String value = dataRow.getAsString(fieldEntry.getKey());
                        if (!StringUtils.hasText(value)) continue;
                        HashMap<String, FieldAndFileInfo> groupAndFileInfoMap = new HashMap<String, FieldAndFileInfo>();
                        FieldAndFileInfo fieldAndFileInfo = new FieldAndFileInfo();
                        fieldAndFileInfo.setDimNameValue(mapWrapper);
                        fieldAndFileInfo.setFieldDefine(fieldEntry.getKey());
                        fieldAndFileInfo.setBlobFieldInfo(fieldEntry.getValue());
                        groupAndFileInfoMap.put(value, fieldAndFileInfo);
                        String rowId = dimensionValueSet2.getValue("RECORDKEY").toString();
                        this.getFileInfo(param.getTaskKey(), groupAndFileInfoMap);
                        Map<String, FileKeyAndInfo> fileKeyAndInfoMap = fieldAndFileInfo.getFileKeyAndInfoMap();
                        addedDimsKeys.add(mapWrapper);
                        AttachmentCheckResultItem item = null;
                        if (resultMap.containsKey(mapWrapper)) {
                            item = resultMap.get(mapWrapper);
                        } else {
                            item = this.getCheckResultItem(param, entityTable, entityMap, fieldEntry, mapWrapper, mainDimName, taskDefine, formSchemeDefine);
                            resultMap.put(mapWrapper, item);
                        }
                        AttachmentCheckFieldResultItem fieldItem = new AttachmentCheckFieldResultItem();
                        int fileNum = fileKeyAndInfoMap.size();
                        if (maxNumber > 0 && fileNum > maxNumber) {
                            fieldItem.setLimitMaxNum(true);
                        } else if (minNumber > 0 && fileNum < minNumber) {
                            fieldItem.setLimitMinNum(true);
                        }
                        fieldItem.setFormCode(fieldEntry.getValue().getFormCode());
                        fieldItem.setFormKey(fieldEntry.getValue().getFormKey());
                        fieldItem.setFormTitle(fieldEntry.getValue().getFormTitle());
                        String dataLinkKey = fieldEntry.getValue().getDataLinkKey();
                        String dataRegionKey = dataLinkRegionCatch.get(dataLinkKey);
                        if (null == dataRegionKey) {
                            DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(dataLinkKey);
                            dataRegionKey = dataLinkDefine.getRegionKey();
                            dataLinkRegionCatch.put(dataLinkKey, dataRegionKey);
                        }
                        fieldItem.setDataRegionKey(dataRegionKey);
                        fieldItem.setDataLinkKey(dataLinkKey);
                        fieldItem.setFieldCode(fieldEntry.getKey().getCode());
                        fieldItem.setFieldKey(fieldEntry.getKey().getKey());
                        fieldItem.setFieldTitle(fieldEntry.getKey().getTitle());
                        fieldItem.setRowId(rowId);
                        fieldItem.setNullable(fieldEntry.getKey().getNullable());
                        item.addFieldItem(fieldItem);
                        for (String fileKey : fileKeyAndInfoMap.keySet()) {
                            FileKeyAndInfo fileKeyAndInfo = fileKeyAndInfoMap.get(fileKey);
                            double fileSize = fileKeyAndInfo.getSize();
                            fileSize = fileSize / 1024.0 / 1024.0;
                            BigDecimal b = BigDecimal.valueOf(fileSize);
                            fileSize = b.setScale(2, 4).doubleValue();
                            fieldItem.addFileSize(fileSize);
                            AttachmentCheckFileItem fileItem = new AttachmentCheckFileItem();
                            fileItem.setFileName(fileKeyAndInfo.getName());
                            fileItem.setFileSize(fileSize);
                            boolean hasAdded = false;
                            if (fieldEntry.getValue().isCheckFileSize() && (fileSize < minValue || fileSize > maxValue)) {
                                fieldItem.addErrFile(fileItem);
                                hasAdded = true;
                            }
                            if (extList.size() > 0 && !hasAdded && !extList.contains(fileKeyAndInfo.getExtension())) {
                                fieldItem.addErrFile(fileItem);
                            }
                            fieldItem.addFile(fileItem);
                        }
                    }
                    int maxFieldItemNum = 0;
                    for (MapWrapper mapWrapper : addedDimsKeys) {
                        AttachmentCheckResultItem attachmentCheckResultItem = resultMap.get(mapWrapper);
                        List<AttachmentCheckFieldResultItem> fieldItems = attachmentCheckResultItem.getFieldItems();
                        int fieldItemSize = fieldItems.size();
                        if (fieldItemSize <= maxFieldItemNum) continue;
                        maxFieldItemNum = fieldItemSize;
                    }
                    for (Map.Entry entry : resultMap.entrySet()) {
                        int fieldItemSize = ((AttachmentCheckResultItem)entry.getValue()).getFieldItems().size();
                        if (fieldItemSize == maxFieldItemNum) continue;
                        for (int i = 0; i < maxFieldItemNum - fieldItemSize; ++i) {
                            AttachmentCheckResultItem item = (AttachmentCheckResultItem)entry.getValue();
                            AttachmentCheckFieldResultItem fieldItem = new AttachmentCheckFieldResultItem();
                            fieldItem.setFormCode(fieldEntry.getValue().getFormCode());
                            fieldItem.setFormKey(fieldEntry.getValue().getFormKey());
                            fieldItem.setFormTitle(fieldEntry.getValue().getFormTitle());
                            String dataLinkKey = fieldEntry.getValue().getDataLinkKey();
                            String dataRegionKey = dataLinkRegionCatch.get(dataLinkKey);
                            if (null == dataRegionKey) {
                                DataLinkDefine dataLinkDefine = this.runTimeViewController.queryDataLinkDefine(dataLinkKey);
                                dataRegionKey = dataLinkDefine.getRegionKey();
                                dataLinkRegionCatch.put(dataLinkKey, dataRegionKey);
                            }
                            fieldItem.setDataRegionKey(dataRegionKey);
                            fieldItem.setDataLinkKey(dataLinkKey);
                            fieldItem.setFieldCode(fieldEntry.getKey().getCode());
                            fieldItem.setFieldKey(fieldEntry.getKey().getKey());
                            fieldItem.setFieldTitle(fieldEntry.getKey().getTitle());
                            fieldItem.setRowId(NO_ROWID);
                            fieldItem.setNullable(fieldEntry.getKey().getNullable());
                            item.addFieldItem(fieldItem);
                        }
                    }
                    for (MapWrapper mapWrapper : addedDimsKeys) {
                        int recordIndex = 0;
                        AttachmentCheckResultItem attachmentCheckResultItem = resultMap.get(mapWrapper);
                        List<AttachmentCheckFieldResultItem> fieldItems = attachmentCheckResultItem.getFieldItems();
                        for (AttachmentCheckFieldResultItem fieldItem : fieldItems) {
                            if (!fieldItem.getFieldKey().equals(fieldEntry.getKey().getKey())) continue;
                            fieldItem.setRecordIndex(recordIndex++);
                        }
                    }
                }
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                return;
            }
        }
    }

    private AttachmentCheckResultItem getCheckResultItem(AttachmentCheckParam param, IEntityTable entityTable, Map<String, String> entityMap, Map.Entry<FieldDefine, AttachmentFieldInfo> fieldEntry, MapWrapper mapWrapper, String unitDimName, TaskDefine taskDefine, FormSchemeDefine formSchemeDefine) {
        AttachmentCheckResultItem item = new AttachmentCheckResultItem();
        String unitKey = mapWrapper.getDimNameValueMap().get(unitDimName);
        item.setUnitKey(unitKey);
        item.setUnitCode(unitKey);
        item.setDimNameValue(mapWrapper.getDimNameValueMap());
        if (entityMap.containsKey(unitKey)) {
            item.setUnitTitle(entityMap.get(unitKey));
            item.setUnitDimName(unitDimName);
        } else {
            IEntityRow entityRow = entityTable.findByEntityKey(unitKey);
            item.setUnitTitle(entityRow.getTitle());
            item.setUnitCode(entityRow.getCode());
            item.setUnitDimName(unitDimName);
            entityMap.put(unitKey, entityRow.getTitle());
        }
        item.setTaskKey(param.getTaskKey());
        item.setFormSchemeKey(param.getFormSchemeKey());
        item.setTaskTitle(taskDefine.getTitle());
        item.setFormSchemeTitle(formSchemeDefine.getTitle());
        if (null != fieldEntry) {
            item.setFormCode(fieldEntry.getValue().getFormCode());
            item.setFormKey(fieldEntry.getValue().getFormKey());
            item.setFormTitle(fieldEntry.getValue().getFormTitle());
            item.setFieldCode(fieldEntry.getKey().getCode());
            item.setFieldKey(fieldEntry.getKey().getKey());
            item.setFieldTitle(fieldEntry.getKey().getTitle());
        }
        item.setFileSize(0.0);
        return item;
    }
}

