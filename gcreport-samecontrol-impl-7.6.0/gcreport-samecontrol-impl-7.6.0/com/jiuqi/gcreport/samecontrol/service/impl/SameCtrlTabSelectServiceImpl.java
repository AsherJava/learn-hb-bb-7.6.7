/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlZbAttrEnum
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.GridEnums
 *  com.jiuqi.nvwa.grid2.GridEnums$DataType
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlChgSettingZbAttrEO;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlZbAttrEnum;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlFormTabSelectService;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.GridEnums;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SameCtrlTabSelectServiceImpl
implements SameCtrlFormTabSelectService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    @Override
    public String queryFormData(String formKey, Consumer<GridCellData> consumer) {
        try {
            Grid2Data griddata = this.getGridDataByRunTime(formKey);
            this.fillZbCode(formKey, griddata, consumer);
            String result = this.serialize(griddata);
            return result;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private String serialize(Grid2Data griddata) throws JsonProcessingException {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
        module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule((Module)module);
        return mapper.writeValueAsString((Object)griddata);
    }

    private void fillZbCode(String formKey, Grid2Data gridData, Consumer<GridCellData> consumer) throws Exception {
        this.fillZbCode(formKey, gridData, consumer, null, null);
    }

    private void fillZbCode(String formKey, Grid2Data gridData, Consumer<GridCellData> consumer, Map<String, String> mutiCriteridMap, Map<String, SameCtrlChgSettingZbAttrEO> zbAttrsMap) throws Exception {
        List regions = this.runTimeViewController.getAllRegionsInForm(formKey);
        for (DataRegionDefine region : regions) {
            String regionKey = region.getKey();
            List dataLinks = this.runTimeViewController.getAllLinksInRegion(regionKey);
            for (DataLinkDefine link : dataLinks) {
                GridCellData cellData;
                DataField fieldDefine = this.runtimeDataSchemeService.getDataField(link.getLinkExpression());
                String tableName = "";
                String fieldCode = "\u672a\u77e5";
                boolean isNumberField = true;
                if (fieldDefine != null) {
                    tableName = this.runtimeDataSchemeService.getDataTable(fieldDefine.getDataTableKey()).getCode();
                    fieldCode = fieldDefine.getCode();
                    FieldType fieldType = fieldDefine.getType();
                    if (fieldType != FieldType.FIELD_TYPE_FLOAT && fieldType != FieldType.FIELD_TYPE_INTEGER && fieldType != FieldType.FIELD_TYPE_DECIMAL) {
                        isNumberField = false;
                    }
                }
                if ((cellData = gridData.getGridCellData(link.getPosX(), link.getPosY())) == null) continue;
                cellData.setShowText(tableName + "[" + fieldCode + "]");
                cellData.setHorzAlign(3);
                cellData.setForeGroundColor(255);
                if (null != consumer) {
                    consumer.accept(cellData);
                }
                cellData.setEditable(true);
                if (isNumberField) {
                    cellData.setDataType(GridEnums.getIntValue((Enum)GridEnums.DataType.Number));
                }
                if (mutiCriteridMap != null) {
                    cellData.setShowText(null);
                    cellData.setEditText(tableName + "[" + fieldCode + "]");
                    if (mutiCriteridMap.get(fieldDefine.getKey()) == null) continue;
                    cellData.setBackGroundColor(Integer.parseInt("AFEEEE", 16));
                    cellData.setShowText(mutiCriteridMap.get(fieldDefine.getKey()));
                    continue;
                }
                if (zbAttrsMap == null) continue;
                cellData.setShowText(null);
                cellData.setEditText(tableName + "[" + fieldCode + "];" + link.getKey());
                if (!zbAttrsMap.containsKey(link.getKey())) continue;
                cellData.setBackGroundColor(Integer.parseInt("AFEEEE", 16));
                SameCtrlChgSettingZbAttrEO zbAttrEO = zbAttrsMap.get(link.getKey());
                cellData.setShowText(SameCtrlZbAttrEnum.getTitleByCode((String)zbAttrEO.getZbAttribure()));
            }
        }
        if (null == gridData) {
            gridData = new Grid2Data();
            gridData.insertRows(0, 1, -1);
            gridData.insertColumns(0, 1);
            gridData.setRowHidden(0, true);
            gridData.setColumnHidden(0, true);
        }
    }

    private Grid2Data getGridDataByRunTime(String formKey) {
        BigDataDefine dataDefine = this.runTimeViewController.getReportDataFromForm(formKey);
        Grid2Data gridData = null;
        if (null != dataDefine) {
            if (dataDefine.getData() != null) {
                gridData = Grid2Data.bytesToGrid((byte[])dataDefine.getData());
            } else {
                gridData = new Grid2Data();
                gridData.setRowCount(10);
                gridData.setColumnCount(10);
            }
        }
        return gridData;
    }
}

