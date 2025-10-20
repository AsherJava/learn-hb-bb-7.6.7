/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.context.AnalysisContext
 *  com.alibaba.excel.event.AnalysisEventListener
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.datamapping.client.dto.DataRefDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO
 */
package com.jiuqi.dc.datamapping.impl.expimp;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.datamapping.client.dto.DataRefDTO;
import com.jiuqi.dc.datamapping.impl.enums.RefDynamicField;
import com.jiuqi.dc.datamapping.impl.expimp.DataRefImpParsePojo;
import com.jiuqi.dc.datamapping.impl.utils.IsolationUtil;
import com.jiuqi.dc.mappingscheme.client.dto.BaseDataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.client.dto.FieldMappingDefineDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataRefImpExcelListener
extends AnalysisEventListener<LinkedHashMap<Integer, String>> {
    private List<DataRefDTO> impParseData;
    private Integer totalCount;
    private List<FieldMappingDefineDTO> defineItems = CollectionUtils.newArrayList();
    private DataRefImpParsePojo impParseVo;
    public static final Set<String> REQUIRED_FIELD_SET = new HashSet<String>();

    public DataRefImpExcelListener(BaseDataMappingDefineDTO baseDataDefine) {
        for (RefDynamicField field : IsolationUtil.listDynamicRefFieldAndTitle(baseDataDefine)) {
            FieldMappingDefineDTO fieldMappingDefineDTO = new FieldMappingDefineDTO();
            fieldMappingDefineDTO.setFieldName(field.getFieldName());
            fieldMappingDefineDTO.setFieldTitle(field.getFieldTitle());
            this.defineItems.add(fieldMappingDefineDTO);
        }
        FieldMappingDefineDTO odsCode = new FieldMappingDefineDTO();
        odsCode.setFieldName("ODS_CODE");
        odsCode.setFieldTitle("\u6e90\u7cfb\u7edf\u4ee3\u7801");
        this.defineItems.add(odsCode);
        FieldMappingDefineDTO odsName = new FieldMappingDefineDTO();
        odsName.setFieldName("ODS_NAME");
        odsName.setFieldTitle("\u6e90\u7cfb\u7edf\u540d\u79f0");
        this.defineItems.add(odsName);
        FieldMappingDefineDTO code = new FieldMappingDefineDTO();
        code.setFieldName("CODE");
        code.setFieldTitle("\u4ee3\u7801");
        this.defineItems.add(code);
        this.impParseVo = new DataRefImpParsePojo();
    }

    public void clear() {
        if (this.impParseData != null) {
            this.impParseData.clear();
            this.impParseData = null;
        }
    }

    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        if ("\u6570\u636e\u6620\u5c04\u65b9\u6848".equals(headMap.get(0))) {
            FieldMappingDefineDTO fieldMappingDefineDTO = new FieldMappingDefineDTO();
            fieldMappingDefineDTO.setFieldName("DATASCHEMECODE");
            fieldMappingDefineDTO.setFieldTitle("\u6570\u636e\u6620\u5c04\u65b9\u6848");
            this.defineItems.add(0, fieldMappingDefineDTO);
        }
    }

    public void invoke(LinkedHashMap<Integer, String> data, AnalysisContext context) {
        DataRefDTO row;
        if (context.readSheetHolder().getSheetNo() != 0 || context.readRowHolder().getRowIndex() == 0) {
            return;
        }
        if (this.totalCount == null) {
            this.totalCount = context.readSheetHolder().getApproximateTotalRowNumber();
            this.impParseData = new ArrayList<DataRefDTO>(this.totalCount);
        }
        if ((row = this.parseRowData(context.readRowHolder().getRowIndex(), data)) == null) {
            return;
        }
        this.impParseData.add(row);
    }

    private DataRefDTO parseRowData(Integer idx, LinkedHashMap<Integer, String> data) {
        if (data.isEmpty()) {
            this.impParseVo.putFailData(idx + 1, "\u5bfc\u5165\u7684\u6570\u636e\u4e3a\u7a7a");
            return null;
        }
        DataRefDTO row = new DataRefDTO();
        for (int colIdx = 0; colIdx < this.defineItems.size(); ++colIdx) {
            FieldMappingDefineDTO fieldMappingDefine = this.defineItems.get(colIdx);
            if (REQUIRED_FIELD_SET.contains(fieldMappingDefine.getFieldName()) && (data.get(colIdx) == null || data.get(colIdx).isEmpty())) {
                this.impParseVo.putFailData(idx + 1, String.format("%1$s\u5fc5\u586b", fieldMappingDefine.getFieldTitle()));
                return null;
            }
            row.put((Object)fieldMappingDefine.getFieldName(), (Object)data.get(colIdx));
        }
        row.setIdx(Integer.valueOf(idx + 1));
        this.impParseVo.success();
        return row;
    }

    public void doAfterAllAnalysed(AnalysisContext context) {
    }

    public boolean needParse(FieldMappingDefineDTO fieldMappingDefine) {
        return fieldMappingDefine.getFieldName().startsWith("ODS") || fieldMappingDefine.getFieldName().equals("CODE");
    }

    public DataRefImpParsePojo getImpParseVo() {
        return this.impParseVo;
    }

    public List<DataRefDTO> getImpParseData() {
        return this.impParseData;
    }

    static {
        REQUIRED_FIELD_SET.add("ODS_CODE");
        REQUIRED_FIELD_SET.add("ODS_NAME");
        REQUIRED_FIELD_SET.add("CODE");
    }
}

