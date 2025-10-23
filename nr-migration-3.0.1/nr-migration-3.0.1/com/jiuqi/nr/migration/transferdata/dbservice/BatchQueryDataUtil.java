/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService
 *  com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController
 *  com.jiuqi.nr.fielddatacrud.FieldRelation
 *  com.jiuqi.nr.fielddatacrud.FieldRelationFactory
 */
package com.jiuqi.nr.migration.transferdata.dbservice;

import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormService;
import com.jiuqi.nr.definition.internal.runtime.controller.RuntimeViewController;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.FieldRelationFactory;
import com.jiuqi.nr.migration.transferdata.bean.DimInfo;
import com.jiuqi.nr.migration.transferdata.bean.FetchDataParam;
import com.jiuqi.nr.migration.transferdata.bean.TransMainBody;
import com.jiuqi.nr.migration.transferdata.bean.TransZbValue;
import com.jiuqi.nr.migration.transferdata.common.DataTransUtil;
import com.jiuqi.nr.migration.transferdata.dbservice.service.IQueryDataService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchQueryDataUtil {
    private static final Logger logger = LoggerFactory.getLogger(BatchQueryDataUtil.class);

    public static Map<String, List<TransMainBody>> testBatchQuery(String taskKey, String formSchemeKey, List<DimInfo> dimInfos, RuntimeViewController runTimeViewController, IRuntimeFormService iRuntimeFormService, IQueryDataService queryDataExecutorImpl, FieldRelationFactory fieldRelationFactory) {
        HashMap<String, List<TransMainBody>> result = new HashMap<String, List<TransMainBody>>();
        List formDefines = iRuntimeFormService.queryFormDefinesByFormScheme(formSchemeKey);
        FetchDataParam fetchDataParam = new FetchDataParam();
        fetchDataParam.setTaskKey(taskKey);
        fetchDataParam.setFormSchemeKey(formSchemeKey);
        fetchDataParam.setDimInfos(dimInfos);
        int accumulationIdx = 0;
        for (FormDefine formDefine : formDefines) {
            fetchDataParam.setFormKey(formDefine.getKey());
            List regions = runTimeViewController.getAllRegionsInForm(formDefine.getKey());
            int size = regions.size();
            accumulationIdx += size;
            for (int i = 0; i < size; ++i) {
                DataRegionDefine region = (DataRegionDefine)regions.get(i);
                TransMainBody mb = new TransMainBody();
                mb.setIdx(i + accumulationIdx - size);
                mb.setFormTitle(formDefine.getTitle());
                mb.setFloat(region.getRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST || region.getRegionKind() == DataRegionKind.DATA_REGION_COLUMN_LIST);
                List dataFieldKeys = runTimeViewController.getFieldKeysInRegion(region.getKey());
                if (dataFieldKeys.size() == 0) continue;
                fetchDataParam.setDataFieldKeys(dataFieldKeys);
                List<IRowData> iRowData = queryDataExecutorImpl.batchQueryReportData(fetchDataParam);
                if (iRowData.size() == 0) {
                    logger.warn("\u62a5\u8868\uff1a{}\uff0c\u533a\u57df\uff1a{} \u7684\u6307\u6807\u6570\u636e\u4e3a\u7a7a\u3002", (Object)formDefine.getFormCode(), (Object)region.getCode());
                }
                FieldRelation fieldRelation = fieldRelationFactory.getFieldRelation(dataFieldKeys.listIterator());
                List metaData = fieldRelation.getMetaData();
                List<String> dataFieldCodes = metaData.stream().map(imd -> imd.getDataField().getCode()).collect(Collectors.toList());
                mb.setZbCodes(dataFieldCodes);
                BatchQueryDataUtil.analysisIRowDatas(iRowData, mb, result);
            }
        }
        return result;
    }

    private static void analysisIRowDatas(List<IRowData> iRowData, TransMainBody mb, Map<String, List<TransMainBody>> result) {
        if (!mb.isFloat()) {
            for (IRowData rowData : iRowData) {
                DimensionCombination dimension = rowData.getDimension();
                String dimKey = BatchQueryDataUtil.getDimKey(dimension, false);
                if (!result.containsKey(dimKey)) {
                    result.put(dimKey, new ArrayList());
                }
                ArrayList<IRowData> l = new ArrayList<IRowData>();
                l.add(rowData);
                result.get(dimKey).add(BatchQueryDataUtil.createMbRegion(mb, l, new HashMap<String, String>()));
            }
        } else {
            Map<DimensionCombination, List<IRowData>> collect = iRowData.stream().collect(Collectors.groupingBy(IRowData::getMasterDimension));
            for (DimensionCombination dimension : collect.keySet()) {
                String dimKey = BatchQueryDataUtil.getDimKey(dimension, true);
                if (!result.containsKey(dimKey)) {
                    result.put(dimKey, new ArrayList());
                }
                result.get(dimKey).add(BatchQueryDataUtil.createMbRegion(mb, collect.get(dimension), new HashMap<String, String>()));
            }
        }
    }

    private static String getDimKey(DimensionCombination dimension, boolean isFloat) {
        StringBuilder dimKey = new StringBuilder(isFloat ? "true;" : "false;");
        Collection names = dimension.getNames();
        for (String dimCom : names) {
            dimKey.append(dimension.getValue(dimCom)).append(";");
        }
        return dimKey.toString();
    }

    private static TransMainBody createMbRegion(TransMainBody mb, List<IRowData> rowDataList, Map<String, String> zbMapping) {
        if (rowDataList.size() > 0) {
            for (IRowData rowData : rowDataList) {
                List linkDataValues = rowData.getLinkDataValues();
                ArrayList<TransZbValue> fieldValues = new ArrayList<TransZbValue>();
                int zbIdx = 0;
                for (IDataValue iDataValue : linkDataValues) {
                    TransZbValue fieldValue = new TransZbValue();
                    String zbCode = iDataValue.getMetaData().getCode();
                    fieldValue.setZbCode(zbMapping != null ? zbMapping.getOrDefault(zbCode, zbCode) : zbCode);
                    fieldValue.setZbType(DataTransUtil.getJqrZbType(iDataValue.getMetaData().getDataType()));
                    fieldValue.setValue(DataTransUtil.getFieldValue(iDataValue));
                    fieldValue.setIdx(zbIdx++);
                    fieldValues.add(fieldValue);
                }
                mb.addFieldValues(fieldValues);
            }
        }
        return mb;
    }
}

