/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelModelExecutor
 *  com.jiuqi.gcreport.intermediatelibrary.condition.ILCondition
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.intermediatelibrary.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelModelExecutor;
import com.jiuqi.gcreport.intermediatelibrary.condition.ILCondition;
import com.jiuqi.gcreport.intermediatelibrary.dao.IntermediateLibraryDao;
import com.jiuqi.gcreport.intermediatelibrary.entity.ILEntity;
import com.jiuqi.gcreport.intermediatelibrary.entity.IntermediateLibraryExcelModel;
import com.jiuqi.gcreport.intermediatelibrary.service.IntermediateProgrammeService;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class IntermediateLibraryImportImpl
extends AbstractImportExcelModelExecutor<IntermediateLibraryExcelModel> {
    private static final Logger logger = LoggerFactory.getLogger(IntermediateLibraryImportImpl.class);
    @Resource
    private IRunTimeViewController iRunTimeViewController;
    @Resource
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Resource
    private IntermediateProgrammeService iPService;
    @Resource
    private IntermediateLibraryDao intermediateLibraryDao;

    protected IntermediateLibraryImportImpl() {
        super(IntermediateLibraryExcelModel.class);
    }

    protected Object importExcelModels(ImportContext context, List<IntermediateLibraryExcelModel> rowDataList) {
        String result;
        if (rowDataList.size() == 0) {
            return "\u5bfc\u5165\u5931\u8d25\uff0c\u6570\u636e\u4e3a\u7a7a\r\n";
        }
        if (!StringUtils.isNotEmpty((String)rowDataList.get(0).getFieldType())) {
            return "\u5bfc\u5165\u5931\u8d25\uff0c\u65b9\u6848\u540d\u79f0\u4e3a\u7a7a\r\n";
        }
        String programmeName = rowDataList.get(0).getFieldType();
        ILCondition iLCondition = new ILCondition();
        iLCondition.setProgrammeName(programmeName);
        ILEntity ilEntity = this.iPService.getProgrammeOfName(iLCondition);
        if (ilEntity == null) {
            return "\u5bfc\u5165\u5931\u8d25\uff0c\u65b9\u6848\u4e0d\u5b58\u5728\r\n";
        }
        Map<String, List<FieldDefine>> zbCode2FieldMap = this.getAllFieldForTaskId(ilEntity);
        try {
            result = this.insertZbForProgramme(zbCode2FieldMap, rowDataList, ilEntity);
        }
        catch (Exception e) {
            logger.error("\u5bfc\u5165\u5f02\u5e38", e);
            return "\u5bfc\u5165\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002";
        }
        if (StringUtils.isNotEmpty((String)result)) {
            return "\u5bfc\u5165\u6210\u529f\uff0c\u5bfc\u5165\u65e5\u5fd7\uff1a\r\n" + result;
        }
        return "\u5bfc\u5165\u6210\u529f";
    }

    private String insertZbForProgramme(Map<String, List<FieldDefine>> zbCode2FieldMap, List<IntermediateLibraryExcelModel> rowDataList, ILEntity ilEntity) throws Exception {
        List<IntermediateLibraryExcelModel> dataList = rowDataList.subList(2, rowDataList.size());
        StringBuilder result = new StringBuilder();
        result.append("\u6307\u6807\uff1a");
        HashSet fileIdList = new HashSet();
        AtomicBoolean havaNoContains = new AtomicBoolean(false);
        List<String> fieldVOList = this.intermediateLibraryDao.getFieldIdOfProgrammeId(ilEntity.getId());
        List fieldDefineList = this.iDataDefinitionRuntimeController.queryFieldDefines(fieldVOList);
        List fieldCodeList = fieldDefineList.stream().map(fieldDefine -> {
            try {
                return this.iDataDefinitionRuntimeController.queryTableDefine(fieldDefine.getOwnerTableKey()).getCode() + "[" + fieldDefine.getCode() + "]";
            }
            catch (Exception e) {
                logger.error("[" + fieldDefine.getKey() + "]\u6307\u6807\u83b7\u53d6\u5931\u8d25", e);
                return null;
            }
        }).collect(Collectors.toList());
        dataList.forEach(rowData -> {
            if (StringUtils.isEmpty((String)rowData.getFieldCode())) {
                return;
            }
            if (zbCode2FieldMap.containsKey(rowData.getFieldCode())) {
                if (!fieldCodeList.contains(rowData.getFieldCode())) {
                    fileIdList.add(rowData.getFieldCode());
                }
            } else {
                havaNoContains.set(true);
                result.append(rowData.getFieldCode()).append(",");
            }
        });
        result.append("\u5728\u65b9\u6848\u6240\u9009\u4efb\u52a1\u4e0b\u4e0d\u5b58\u5728\u3002");
        ILCondition iLCondition = new ILCondition();
        iLCondition.setId(ilEntity.getId());
        iLCondition.setFieldIdList(new ArrayList(fileIdList));
        iLCondition.setFormTreeKeyList(new ArrayList());
        this.iPService.addProgrammeOfField(iLCondition, false);
        if (havaNoContains.get()) {
            return result.toString();
        }
        return "";
    }

    private Map<String, List<FieldDefine>> getAllFieldForTaskId(ILEntity ilEntity) {
        String[] taskIdArr = ilEntity.getTaskId().split(",");
        ArrayList formDefineList = new ArrayList();
        for (String taskId : taskIdArr) {
            formDefineList.addAll(this.iRunTimeViewController.queryAllFormDefinesByTask(taskId));
        }
        ArrayList fieldKeyList = new ArrayList();
        formDefineList.forEach(formDefine -> fieldKeyList.addAll(this.iRunTimeViewController.getFieldKeysInForm(formDefine.getKey())));
        List fieldDefineList = this.iDataDefinitionRuntimeController.queryFieldDefinesInRange(fieldKeyList);
        return fieldDefineList.stream().collect(Collectors.groupingBy(fieldDefine -> {
            try {
                TableDefine tableDefine = this.iDataDefinitionRuntimeController.queryTableDefine(fieldDefine.getOwnerTableKey());
                if (tableDefine != null) {
                    return tableDefine.getCode() + "[" + fieldDefine.getCode() + "]";
                }
            }
            catch (Exception e) {
                logger.error("\u83b7\u53d6\u6307\u6807\u6240\u5728\u5b58\u50a8\u8868\u5931\u8d25", e);
            }
            return null;
        }));
    }

    public String getName() {
        return "IntermediateImportExecutor";
    }
}

