/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.floatmodel.client.vo.FloatRegionHandlerVO
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.dto;

import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.floatmodel.client.vo.FloatRegionHandlerVO;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.dto.FailedSettingLog;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.ImpExpInnerColumnHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.intf.ImpExpFormDefine;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FetchSettingExcelContext {
    private String fetchSchemeId;
    private BizTypeEnum bizType;
    private String formSchemeId;
    private List<ImpExpFormDefine> formDefines;
    private List<BizModelDTO> BizModelDTOS;
    private Map<String, BizModelDTO> bizModelDTOMap;
    private Map<String, BizModelDTO> bizModelNameMap;
    private List<DimensionVO> dimensions;
    private Map<String, DimensionVO> dimensionVOCodeMap;
    private Map<String, DimensionVO> dimensionVONameMap;
    private List<ImpExpInnerColumnHandler> fixColumns;
    private List<ImpExpInnerColumnHandler> floatColumns;
    private List<FailedSettingLog> failedSettingLogList = new ArrayList<FailedSettingLog>();
    private List<FloatRegionHandlerVO> floatRegionHandlerVOS;
    private Map<String, FloatRegionHandlerVO> floatRegionHandlerVONameMap;
    private Map<String, FloatRegionHandlerVO> floatRegionHandlerVOCodeMap;
    private List<GcBaseData> agingBaseDataList;
    private Map<String, GcBaseData> agingBaseDataCodeMap;
    private Map<String, GcBaseData> agingBaseDataNameMap;

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public List<ImpExpFormDefine> getFormDefines() {
        return this.formDefines;
    }

    public void setFormDefines(List<ImpExpFormDefine> formDefines) {
        this.formDefines = formDefines;
    }

    public BizTypeEnum getBizType() {
        return this.bizType;
    }

    public void setBizType(BizTypeEnum bizType) {
        this.bizType = bizType;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public List<BizModelDTO> getBizModels() {
        return this.BizModelDTOS;
    }

    public void setBizModelDTOs(List<BizModelDTO> BizModelDTOS) {
        this.BizModelDTOS = BizModelDTOS;
        if (BizModelDTOS != null) {
            this.bizModelDTOMap = BizModelDTOS.stream().collect(Collectors.toMap(BizModelDTO::getCode, Function.identity(), (K1, K2) -> K1));
            this.bizModelNameMap = BizModelDTOS.stream().collect(Collectors.toMap(BizModelDTO::getName, Function.identity(), (K1, K2) -> K1));
        }
    }

    public List<DimensionVO> getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(List<DimensionVO> dimensions) {
        this.dimensions = dimensions;
        if (dimensions != null) {
            this.dimensionVOCodeMap = dimensions.stream().collect(Collectors.toMap(DimensionVO::getCode, Function.identity(), (K1, K2) -> K1));
            this.dimensionVONameMap = dimensions.stream().collect(Collectors.toMap(DimensionVO::getTitle, Function.identity(), (K1, K2) -> K1));
        }
    }

    public BizModelDTO getBizModelByName(String fetchSourceName) {
        return this.bizModelNameMap.get(fetchSourceName);
    }

    public BizModelDTO getBizModelByCode(String fetchSourceCode) {
        return this.bizModelDTOMap.get(fetchSourceCode);
    }

    public List<ImpExpInnerColumnHandler> getFixColumns() {
        return this.fixColumns;
    }

    public void setFixColumns(List<ImpExpInnerColumnHandler> fixColumns) {
        this.fixColumns = fixColumns;
    }

    public List<ImpExpInnerColumnHandler> getFloatColumns() {
        return this.floatColumns;
    }

    public void setFloatColumns(List<ImpExpInnerColumnHandler> floatColumns) {
        this.floatColumns = floatColumns;
    }

    public DimensionVO getDimensionVOByCode(String dimCode) {
        return this.dimensionVOCodeMap.get(dimCode);
    }

    public DimensionVO getDimensionVOByName(String dimName) {
        return this.dimensionVONameMap.get(dimName);
    }

    public void addFailedSettingLog(FailedSettingLog failedSettingLog) {
        this.failedSettingLogList.add(failedSettingLog);
    }

    public void setFloatRegionHandlerVOS(List<FloatRegionHandlerVO> floatRegionHandlerVOS) {
        this.floatRegionHandlerVOS = floatRegionHandlerVOS;
        this.floatRegionHandlerVONameMap = floatRegionHandlerVOS.stream().collect(Collectors.toMap(FloatRegionHandlerVO::getTitle, Function.identity(), (K1, K2) -> K1));
        this.floatRegionHandlerVOCodeMap = floatRegionHandlerVOS.stream().collect(Collectors.toMap(FloatRegionHandlerVO::getCode, Function.identity(), (K1, K2) -> K1));
    }

    public void setAgingBaseDataList(List<GcBaseData> agingBaseDataList) {
        this.agingBaseDataList = agingBaseDataList;
        if (agingBaseDataList != null) {
            this.agingBaseDataCodeMap = agingBaseDataList.stream().collect(Collectors.toMap(GcBaseData::getCode, Function.identity(), (K1, K2) -> K1));
            this.agingBaseDataNameMap = agingBaseDataList.stream().collect(Collectors.toMap(GcBaseData::getTitle, Function.identity(), (K1, K2) -> K1));
        }
    }

    public List<GcBaseData> getAgingBaseDataList() {
        return this.agingBaseDataList;
    }

    public Map<String, GcBaseData> getAgingBaseDataCodeMap() {
        return this.agingBaseDataCodeMap;
    }

    public Map<String, GcBaseData> getAgingBaseDataNameMap() {
        return this.agingBaseDataNameMap;
    }

    public FloatRegionHandlerVO getFloatRegionHandlerByCode(String floatHandlerCode) {
        return this.floatRegionHandlerVOCodeMap.get(floatHandlerCode);
    }

    public FloatRegionHandlerVO getFloatRegionHandlerByName(String floatHandlerName) {
        return this.floatRegionHandlerVONameMap.get(floatHandlerName);
    }

    public String getErrorLog() {
        StringBuilder errorLog = new StringBuilder();
        Map<String, List<FailedSettingLog>> failedSettingMap = this.failedSettingLogList.stream().collect(Collectors.groupingBy(FailedSettingLog::getSheetName));
        for (Map.Entry<String, List<FailedSettingLog>> failedSettingLogEntry : failedSettingMap.entrySet()) {
            errorLog.append(String.format("\u3010%1$s\u3011: %2$s", failedSettingLogEntry.getKey(), "<br/>"));
            for (FailedSettingLog failedSettingLog : failedSettingLogEntry.getValue()) {
                errorLog.append(failedSettingLog.getErrorLog());
            }
            errorLog.append("\n");
        }
        return errorLog.toString().trim();
    }
}

