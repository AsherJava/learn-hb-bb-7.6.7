/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package nr.single.map.data.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.nr.common.params.DimensionValue;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.service.SingleDimissionServcie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SingleDimissionServcieImpl
implements SingleDimissionServcie {
    private static final Logger logger = LoggerFactory.getLogger(SingleDimissionServcieImpl.class);
    private static final String TEMP_TABLE_NAME = "TempAssistantTable";

    @Override
    public void doAnalSingleUnitDims(TaskDataContext context, List<Map<String, DimensionValue>> sourceDimList, boolean useSingleDim, List<Map<String, DimensionValue>> mapNoUnitList, List<String> downLoadList, List<String> entityKeys2, Map<String, DimensionValue> unitDic) {
        HashMap<String, LinkedHashMap<String, DimensionValue>> dimDic = new HashMap<String, LinkedHashMap<String, DimensionValue>>();
        for (Map<String, DimensionValue> mapNoUnit2 : sourceDimList) {
            LinkedHashMap<String, DimensionValue> newDim;
            String dimCode = this.getSingleUnitDimsCode(context, mapNoUnit2, useSingleDim, downLoadList, entityKeys2, unitDic, newDim = new LinkedHashMap<String, DimensionValue>());
            if (dimDic.containsKey(dimCode)) continue;
            dimDic.put(dimCode, newDim);
            mapNoUnitList.add(newDim);
        }
    }

    public String getSingleUnitDimsCode(TaskDataContext context, Map<String, DimensionValue> mapNoUnit2, boolean useSingleDim, List<String> downLoadList, List<String> entityKeys2, Map<String, DimensionValue> unitDic, Map<String, DimensionValue> newDim) {
        LinkedHashMap<String, DimensionValue> entitySingleDim = new LinkedHashMap<String, DimensionValue>();
        LinkedHashMap<String, DimensionValue> classUnitDic = new LinkedHashMap<String, DimensionValue>();
        StringBuilder dimCodesb = new StringBuilder();
        for (String code : mapNoUnit2.keySet()) {
            DimensionValue dimValue = mapNoUnit2.get(code);
            if (useSingleDim && context.getDimEntityCache().getEntitySingleDims().contains(code)) {
                entitySingleDim.put(code, dimValue);
                this.addDimValue(context, code, dimValue);
                continue;
            }
            if (code.equals(context.getEntityCompanyType())) {
                String unitCode = dimValue.getValue();
                if (!unitDic.containsKey(unitCode)) {
                    unitDic.put(unitCode, dimValue);
                    downLoadList.add(unitCode);
                    if (useSingleDim && context.getDimEntityCache().getEntitySingleDims().size() > 0) {
                        context.getDimEntityCache().getEntitySingleDimList().put(unitCode, entitySingleDim);
                    }
                }
                if (classUnitDic.containsKey(unitCode)) continue;
                classUnitDic.put(unitCode, dimValue);
                continue;
            }
            newDim.put(code, dimValue);
            dimCodesb.append("_").append(dimValue.getValue());
            if ("ADJUST".equalsIgnoreCase(code)) continue;
            String dimEntityId = context.getDimEntityCache().getEntityDimAndEntityIds().get(code);
            entityKeys2.add(dimEntityId);
        }
        if (!context.getDimEntityCache().getEntityClassDimList().containsKey(dimCodesb.toString())) {
            context.getDimEntityCache().getEntityClassDimList().put(dimCodesb.toString(), classUnitDic);
        } else {
            Map<String, DimensionValue> classUnitDic2 = context.getDimEntityCache().getEntityClassDimList().get(dimCodesb.toString());
            classUnitDic2.putAll(classUnitDic);
        }
        return dimCodesb.toString();
    }

    private void addDimValue(TaskDataContext context, String code, DimensionValue dimValue) {
        Map<Object, Object> entitySingleDimValue = null;
        if (context.getDimEntityCache().getEntitySingleDimValues().containsKey(code)) {
            entitySingleDimValue = context.getDimEntityCache().getEntitySingleDimValues().get(code);
            if (!entitySingleDimValue.containsKey(dimValue.getValue())) {
                entitySingleDimValue.put(dimValue.getValue(), dimValue);
            }
        } else {
            entitySingleDimValue = new HashMap<String, DimensionValue>();
            entitySingleDimValue.put(dimValue.getValue(), dimValue);
            context.getDimEntityCache().getEntitySingleDimValues().put(code, entitySingleDimValue);
        }
    }

    @Override
    public Map<String, DimensionValue> getDimensionMapFromSet(DimensionValueSet dimensionValueSet) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            DimensionValue value = new DimensionValue();
            value.setName(dimensionValueSet.getName(i));
            Object dimensionValue = dimensionValueSet.getValue(i);
            if (dimensionValue == null) continue;
            if (dimensionValue instanceof List) {
                value.setValue(StringUtils.join(((List)dimensionValue).iterator(), (String)";"));
            } else {
                value.setValue(dimensionValue.toString());
            }
            dimensionSet.put(value.getName(), value);
        }
        return dimensionSet;
    }

    @Override
    public List<Map<String, DimensionValue>> getDimensionMaspFromSet(List<DimensionValueSet> dimensionValueSets) {
        ArrayList<Map<String, DimensionValue>> dimensionSets = new ArrayList<Map<String, DimensionValue>>();
        for (DimensionValueSet oldSet : dimensionValueSets) {
            Map<String, DimensionValue> newSet = this.getDimensionMapFromSet(oldSet);
            dimensionSets.add(newSet);
        }
        return dimensionSets;
    }

    @Override
    public void setDimensionByUnitSingleDim(TaskDataContext context, Map<String, DimensionValue> dimensionValueMap) {
        if (context.getDimEntityCache().getEntitySingleDimValues().size() > 0) {
            for (String dimCode : context.getDimEntityCache().getEntitySingleDimValues().keySet()) {
                StringBuilder sb1 = new StringBuilder();
                Map<String, DimensionValue> dimValues = context.getDimEntityCache().getEntitySingleDimValues().get(dimCode);
                if (dimValues.size() <= 0) continue;
                for (String dimValue : dimValues.keySet()) {
                    sb1.append(dimValue).append(";");
                }
                sb1.deleteCharAt(sb1.length() - 1);
                DimensionValue newDim1 = new DimensionValue();
                newDim1.setName(dimCode);
                newDim1.setValue(sb1.toString());
                dimensionValueMap.put(dimCode, newDim1);
            }
        }
    }

    @Override
    public void judgeAndUseTempTable(TaskDataContext importContext) {
        Map<String, String> uploadEntityZdmKeyMap = importContext.getUploadEntityZdmKeyMap();
        ArrayList<String> listEntity = new ArrayList<String>();
        for (Map.Entry<String, String> entry : uploadEntityZdmKeyMap.entrySet()) {
            String dwMasterCode;
            String netEntityKey = uploadEntityZdmKeyMap.get(entry.getKey());
            if (!StringUtils.isNotEmpty((String)netEntityKey) || !StringUtils.isNotEmpty((String)(dwMasterCode = importContext.getEntityMasterCodeBykey(netEntityKey)))) continue;
            listEntity.add(dwMasterCode);
        }
        if (listEntity.size() > 499 && listEntity.size() < 5000) {
            this.judgeAndUseTempTableByUnits(importContext, listEntity);
        }
    }

    @Override
    public void judgeAndUseTempTableByUnits(TaskDataContext importContext, List<String> unitCodes) {
        if (unitCodes != null && !unitCodes.isEmpty()) {
            TempAssistantTable tempTable = new TempAssistantTable(unitCodes, 6);
            try {
                tempTable.createTempTable();
                tempTable.insertIntoTempTable();
                importContext.getIntfObjects().put(TEMP_TABLE_NAME, tempTable);
                logger.info("\u521b\u5efa\u4e3b\u4f53\u4e34\u65f6\u8868,\u65f6\u95f4:" + new Date().toString() + "," + tempTable.getSelectSql());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void judgeAndFreeTempTable(TaskDataContext importContext) {
        if (importContext.getIntfObjects().containsKey(TEMP_TABLE_NAME)) {
            TempAssistantTable tempTable = (TempAssistantTable)importContext.getIntfObjects().get(TEMP_TABLE_NAME);
            try {
                tempTable.close();
                importContext.getIntfObjects().remove(TEMP_TABLE_NAME);
                logger.info("\u91ca\u653e\u4e3b\u4f53\u4e34\u65f6\u8868,\u65f6\u95f4:" + new Date().toString() + "," + tempTable.getSelectSql());
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}

