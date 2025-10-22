/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.data.checkdes.internal.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.data.checkdes.api.ICKDParamMapping;
import com.jiuqi.nr.data.checkdes.facade.obj.FieldMappingObj;
import com.jiuqi.nr.data.checkdes.facade.obj.FormulaMappingObj;
import com.jiuqi.nr.data.checkdes.internal.io.BnDim;
import com.jiuqi.nr.data.checkdes.internal.io.CKDExpEntity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

public class IOUtils {
    private IOUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static CKDExpEntity handleMapping(ICKDParamMapping paramMapping, CKDExpEntity ckdExpEntity) {
        String period;
        if (paramMapping == null || ckdExpEntity == null) {
            return ckdExpEntity;
        }
        String mdCode = paramMapping.getMDCode(ckdExpEntity.getMdCode());
        if (org.springframework.util.StringUtils.hasText(mdCode)) {
            ckdExpEntity.setMdCode(mdCode);
        }
        if (org.springframework.util.StringUtils.hasText(period = paramMapping.getPeriod(ckdExpEntity.getPeriod()))) {
            ckdExpEntity.setPeriod(period);
        }
        IOUtils.handleEntityDimsMapping(paramMapping, ckdExpEntity);
        IOUtils.handleFormulaMapping(paramMapping, ckdExpEntity);
        IOUtils.handleBNDimMapping(paramMapping, ckdExpEntity);
        return ckdExpEntity;
    }

    private static void handleBNDimMapping(ICKDParamMapping paramMapping, CKDExpEntity ckdExpEntity) {
        List<BnDim> bnDims = IOUtils.parseBNDim(ckdExpEntity.getDimStr());
        if (!CollectionUtils.isEmpty(bnDims)) {
            for (BnDim bnDim : bnDims) {
                if (bnDim.isBizKey()) continue;
                FieldMappingObj fieldMappingObj = new FieldMappingObj(bnDim.getDataFieldCode(), bnDim.getDataTableCode());
                FieldMappingObj field = paramMapping.getField(fieldMappingObj);
                if (field != null) {
                    bnDim.setDataTableCode(field.getDataTableCode());
                    bnDim.setDataFieldCode(field.getDataFieldCode());
                }
                if (!org.springframework.util.StringUtils.hasText(bnDim.getRefEntityId())) continue;
                String mappedEntityId = paramMapping.getEntityId(bnDim.getRefEntityId());
                String mappedEntityData = paramMapping.getEntityData(bnDim.getRefEntityId(), bnDim.getDimValue());
                if (org.springframework.util.StringUtils.hasText(mappedEntityId)) {
                    bnDim.setRefEntityId(mappedEntityId);
                }
                if (!org.springframework.util.StringUtils.hasText(mappedEntityData)) continue;
                bnDim.setDimValue(mappedEntityData);
            }
            String expDimStr = IOUtils.getExpDimStr(bnDims);
            ckdExpEntity.setDimStr(expDimStr);
        }
    }

    private static void handleEntityDimsMapping(ICKDParamMapping paramMapping, CKDExpEntity ckdExpEntity) {
        Map<String, String> dims = ckdExpEntity.getDims();
        HashMap<String, String> mappedDims = new HashMap<String, String>();
        for (Map.Entry<String, String> e : dims.entrySet()) {
            String entityId = e.getKey();
            String entityValue = e.getValue();
            if (!"ADJUST".equals(entityId)) {
                String mappingEntityId = paramMapping.getEntityId(entityId);
                String entityData = paramMapping.getEntityData(entityId, entityValue);
                if (org.springframework.util.StringUtils.hasText(mappingEntityId)) {
                    entityId = mappingEntityId;
                }
                if (org.springframework.util.StringUtils.hasText(entityData)) {
                    entityValue = entityData;
                }
            }
            mappedDims.put(entityId, entityValue);
        }
        ckdExpEntity.setDims(mappedDims);
    }

    private static void handleFormulaMapping(ICKDParamMapping paramMapping, CKDExpEntity ckdExpEntity) {
        FormulaMappingObj formulaMappingObj = new FormulaMappingObj(ckdExpEntity.getFormulaCode(), ckdExpEntity.getFormulaSchemeTitle(), ckdExpEntity.getFormCode(), ckdExpEntity.getGlobRow(), ckdExpEntity.getGlobCol());
        FormulaMappingObj formula = paramMapping.getFormula(formulaMappingObj);
        if (formula != null) {
            String formCode;
            ckdExpEntity.setFormulaCode(formula.getCode());
            ckdExpEntity.setGlobCol(formula.getGlobCol());
            ckdExpEntity.setGlobRow(formula.getGlobRow());
            String formulaSchemeTitle = formula.getFormulaSchemeTitle();
            if (org.springframework.util.StringUtils.hasText(formulaSchemeTitle)) {
                ckdExpEntity.setFormulaSchemeTitle(formulaSchemeTitle);
            }
            if (org.springframework.util.StringUtils.hasText(formCode = formula.getFormCode())) {
                ckdExpEntity.setFormCode(formCode);
            }
        }
    }

    public static List<BnDim> parseBNDim(String expDimStr) {
        ArrayList<BnDim> result = new ArrayList<BnDim>();
        if (org.springframework.util.StringUtils.hasText(expDimStr)) {
            Map<String, String> dimMap = IOUtils.getBNDimMap(expDimStr);
            for (Map.Entry<String, String> e : dimMap.entrySet()) {
                BnDim bnDim = IOUtils.getBnDim(e);
                if (bnDim == null) continue;
                result.add(bnDim);
            }
        }
        return result;
    }

    @Nullable
    private static BnDim getBnDim(Map.Entry<String, String> bnDimEntry) {
        BnDim bnDim = null;
        String key = bnDimEntry.getKey();
        String value = bnDimEntry.getValue();
        if ("ID".equals(key)) {
            bnDim = new BnDim(true, null, null, null, value);
        } else {
            String regex = "[\\[*\\]]";
            String[] split = key.split(regex);
            if (split.length >= 2) {
                String tableCode = split[0];
                String fieldCode = split[1];
                String refEntityId = null;
                if (split.length == 3) {
                    refEntityId = split[2];
                }
                bnDim = new BnDim(false, tableCode, fieldCode, refEntityId, value);
            }
        }
        return bnDim;
    }

    @NonNull
    private static Map<String, String> getBNDimMap(String expDimStr) {
        String[] bnDims;
        LinkedHashMap<String, String> dimMap = new LinkedHashMap<String, String>();
        for (String dim : bnDims = expDimStr.split(";")) {
            String[] dimValues = dim.split(":");
            if (dimValues.length != 2) continue;
            dimMap.put(dimValues[0], dimValues[1]);
        }
        return dimMap;
    }

    public static String getExpDimStr(List<BnDim> bnDims) {
        if (CollectionUtils.isEmpty(bnDims)) {
            return null;
        }
        return bnDims.stream().map(BnDim::toString).collect(Collectors.joining(";"));
    }

    @NonNull
    public static Map<String, String> parseDataBaseDimStr(String dataBaseDimStr) {
        LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
        if (StringUtils.isNotEmpty((String)dataBaseDimStr)) {
            String[] dims;
            for (String dim : dims = dataBaseDimStr.split(";")) {
                String[] dimValues = dim.split(":");
                if (dimValues.length != 2) continue;
                result.put(dimValues[0], dimValues[1]);
            }
        }
        return result;
    }
}

