/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.financialcubes.util.FinancialCubesPeriodUtil
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.gc.financialcubes.query.plugin.transformlayerpenetrationpluginimpl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.financialcubes.util.FinancialCubesPeriodUtil;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationParamDTO;
import com.jiuqi.gc.financialcubes.query.enums.UnitType;
import com.jiuqi.gc.financialcubes.query.extend.FinancialCubesPenetrateCacheManage;
import com.jiuqi.gc.financialcubes.query.plugin.TransformLayerPenetrationPlugin;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SingleUnitTypeHandler
implements TransformLayerPenetrationPlugin {
    @Autowired
    private FinancialCubesPenetrateCacheManage financialCubesPenetrateCacheManage;

    @Override
    public UnitType getType() {
        return UnitType.SINGLE;
    }

    @Override
    public String convert(PenetrationParamDTO dto, PenetrationContextInfo context) {
        String tableCode = dto.getPenetrationType();
        String convertJsonResult = "";
        if (tableCode.startsWith("GC_FINCUBES_DIM")) {
            convertJsonResult = this.convertBalanceTable(dto);
        } else if (tableCode.startsWith("GC_FINCUBES_CF")) {
            convertJsonResult = this.convertCashFlowBalanceTable(dto);
        } else if (tableCode.startsWith("GC_FINCUBES_AGING")) {
            convertJsonResult = this.convertAgeBalanceTable(dto);
        } else {
            throw new BusinessRuntimeException("\u4e0d\u652f\u6301\u8868: " + tableCode);
        }
        return this.financialCubesPenetrateCacheManage.savePenetrateContext(convertJsonResult);
    }

    private String convertAgeBalanceTable(PenetrationParamDTO penetrationParamDTO) {
        return JSONUtil.toJSONString((Object)penetrationParamDTO);
    }

    private String convertBalanceTable(PenetrationParamDTO penetrationParamDTO) {
        try {
            HashMap<String, Object> targetFormat = new HashMap<String, Object>();
            HashMap<String, String> row = new HashMap<String, String>();
            row.put("subjectCode", penetrationParamDTO.getSubjectCode());
            Map<String, Object> data = penetrationParamDTO.getData();
            ArrayList<Map<String, String>> dimensionSettings = new ArrayList<Map<String, String>>();
            if (data != null) {
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    this.processDimensionSetting(entry, dimensionSettings);
                }
            }
            row.put("dimensionSetting", JSONUtil.toJSONString(dimensionSettings));
            targetFormat.put("row", row);
            targetFormat.put("unitCode", penetrationParamDTO.getUnitCode());
            PeriodWrapper periodWrapper = new PeriodWrapper(penetrationParamDTO.getDataTime());
            targetFormat.put("acctYear", periodWrapper.getYear());
            int endPeriod = FinancialCubesPeriodUtil.getPeriodMonthScope((int)periodWrapper.getPeriod(), (String)PeriodUtil.convertType2Str((int)periodWrapper.getType()))[1];
            targetFormat.put("startPeriod", endPeriod);
            targetFormat.put("endPeriod", endPeriod);
            targetFormat.put("includeUnPost", true);
            targetFormat.put("penetrationType", penetrationParamDTO.getPenetrationType());
            targetFormat.put("unitType", penetrationParamDTO.getUnitType());
            targetFormat.put("bizModelCode", "BALANCE");
            targetFormat.put("bizModelName", "\u79d1\u76ee\u4f59\u989d");
            HashMap<String, Map<String, Object>> dimensionSet = new HashMap<String, Map<String, Object>>();
            dimensionSet.put("DATATIME", this.createDimension("DATATIME", penetrationParamDTO.getDataTime(), 4));
            dimensionSet.put("MD_ORG", this.createDimension("MD_ORG", penetrationParamDTO.getMdCode(), 0));
            dimensionSet.put("MD_GCORGTYPE", this.createDimension("MD_GCORGTYPE", penetrationParamDTO.getMdGcOrgType(), 0));
            dimensionSet.put("MD_CURRENCY", this.createDimension("MD_CURRENCY", penetrationParamDTO.getMdCurrency(), 0));
            targetFormat.put("dimensionSet", dimensionSet);
            return JSONUtil.toJSONString(targetFormat);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u4f59\u989d\u8868\u53c2\u6570\u6784\u5efa\u5931\u8d25");
        }
    }

    private void processDimensionSetting(Map.Entry<String, Object> entry, List<Map<String, String>> dimensionSettings) {
        if ("MD_GCORGTYPE".equals(entry.getKey())) {
            return;
        }
        if (entry.getKey().equals("MD_AUDITTRAIL")) {
            return;
        }
        if (entry.getKey().equals("OPPUNITCODE")) {
            throw new BusinessRuntimeException("\u76ee\u524d\u5355\u6237\u5355\u4f4d\u7a7f\u900f\u4e0d\u652f\u6301\u6b64\u7ef4\u5ea6:" + entry.getKey());
        }
        HashMap<String, String> dimSetting = new HashMap<String, String>();
        dimSetting.put("dimCode", entry.getKey());
        dimSetting.put("dimValue", StringUtils.toViewString((Object)entry.getValue()));
        dimSetting.put("dimRule", "EQ");
        dimensionSettings.add(dimSetting);
    }

    private String convertCashFlowBalanceTable(PenetrationParamDTO penetrationParamDTO) {
        try {
            HashMap<String, Object> targetFormat = new HashMap<String, Object>();
            HashMap<String, String> row = new HashMap<String, String>();
            if (penetrationParamDTO.getData() == null || !penetrationParamDTO.getData().containsKey("CFITEMCODE")) {
                throw new BusinessRuntimeException("\u9700\u8981\u5305\u542bcashCode\u76f8\u5173\u4fe1\u606f");
            }
            String cfItemCode = StringUtils.toViewString((Object)penetrationParamDTO.getData().get("CFITEMCODE"));
            row.put("cashCode", cfItemCode.replace("%", ""));
            penetrationParamDTO.getData().remove("CFITEMCODE");
            Map<String, Object> data = penetrationParamDTO.getData();
            ArrayList dimensionSettings = new ArrayList();
            if (data != null) {
                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    HashMap<String, String> dimSetting = new HashMap<String, String>();
                    if (entry.getKey().equals("OPPUNITCODE") || entry.getKey().equals("MD_AUDITTRAIL") || entry.getKey().equals("MD_GCORGTYPE")) continue;
                    dimSetting.put("dimCode", entry.getKey());
                    dimSetting.put("dimValue", StringUtils.toViewString((Object)entry.getValue()));
                    dimSetting.put("dimRule", "EQ");
                    dimensionSettings.add(dimSetting);
                }
            }
            row.put("dimensionSetting", JSONUtil.toJSONString(dimensionSettings));
            targetFormat.put("row", row);
            targetFormat.put("unitCode", penetrationParamDTO.getUnitCode());
            PeriodWrapper periodWrapper = new PeriodWrapper(penetrationParamDTO.getDataTime());
            targetFormat.put("acctYear", periodWrapper.getYear());
            int endPeriod = FinancialCubesPeriodUtil.getPeriodMonthScope((int)periodWrapper.getPeriod(), (String)PeriodUtil.convertType2Str((int)periodWrapper.getType()))[1];
            targetFormat.put("startPeriod", endPeriod);
            targetFormat.put("endPeriod", endPeriod);
            targetFormat.put("includeUnPost", true);
            targetFormat.put("penetrationType", penetrationParamDTO.getPenetrationType());
            targetFormat.put("unitType", penetrationParamDTO.getUnitType());
            targetFormat.put("bizModelCode", "XJLLBALANCE");
            targetFormat.put("bizModelName", "\u73b0\u6d41\u4f59\u989d");
            HashMap<String, Map<String, Object>> dimensionSet = new HashMap<String, Map<String, Object>>();
            dimensionSet.put("DATATIME", this.createDimension("DATATIME", penetrationParamDTO.getDataTime(), 4));
            dimensionSet.put("MD_ORG", this.createDimension("MD_ORG", penetrationParamDTO.getMdCode(), 0));
            dimensionSet.put("MD_GCORGTYPE", this.createDimension("MD_GCORGTYPE", penetrationParamDTO.getMdGcOrgType(), 0));
            dimensionSet.put("MD_CURRENCY", this.createDimension("MD_CURRENCY", penetrationParamDTO.getMdCurrency(), 0));
            targetFormat.put("dimensionSet", dimensionSet);
            return JSONUtil.toJSONString(targetFormat);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u73b0\u6d41\u4f59\u989d\u8868\u53c2\u6570\u6784\u5efa\u5931\u8d25");
        }
    }

    private Map<String, Object> createDimension(String name, String value, int type) {
        HashMap<String, Object> dimension = new HashMap<String, Object>();
        dimension.put("name", name);
        dimension.put("value", value);
        dimension.put("type", type);
        return dimension;
    }
}

