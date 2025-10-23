/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonSerializer
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.jiuqi.bi.grid.GridCell
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.np.definition.impl.common.DefinitionTransUtils
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.editor.EditorParam
 *  com.jiuqi.nr.definition.editor.LinkData
 *  com.jiuqi.nr.definition.editor.Service.EditorStyleCustom
 *  com.jiuqi.nr.definition.editor.StyleData
 *  com.jiuqi.nr.definition.util.GridDataTransform
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  com.jiuqi.nvwa.grid2.json.Grid2DataSerialize
 *  com.jiuqi.nvwa.grid2.json.GridCellDataSerialize
 */
package com.jiuqi.nr.summary.formula;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.jiuqi.bi.grid.GridCell;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.np.definition.impl.common.DefinitionTransUtils;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.editor.EditorParam;
import com.jiuqi.nr.definition.editor.LinkData;
import com.jiuqi.nr.definition.editor.Service.EditorStyleCustom;
import com.jiuqi.nr.definition.editor.StyleData;
import com.jiuqi.nr.definition.util.GridDataTransform;
import com.jiuqi.nr.summary.api.service.IDesignSummarySolutionService;
import com.jiuqi.nr.summary.executor.query.grid.GridHelper;
import com.jiuqi.nr.summary.model.cell.DataCell;
import com.jiuqi.nr.summary.model.cell.MainCell;
import com.jiuqi.nr.summary.model.cell.SummaryZb;
import com.jiuqi.nr.summary.model.report.SummaryReportModel;
import com.jiuqi.nr.summary.utils.SummaryReportUtil;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import com.jiuqi.nvwa.grid2.json.Grid2DataSerialize;
import com.jiuqi.nvwa.grid2.json.GridCellDataSerialize;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
public class SummaryReportEditorDataService
implements EditorStyleCustom {
    private static final String KEY = "summaryReport_formulaEditor_extend";
    @Autowired
    private IDesignSummarySolutionService designSolutionService;

    public String getKey() {
        return KEY;
    }

    public StyleData getEditData(EditorParam editorParam) throws Exception {
        StyleData formData = new StyleData();
        String formKey = editorParam.getFormKey();
        int viewType = 2;
        formData.setFormKey(formKey);
        if (StringUtils.hasLength(formKey)) {
            SummaryReportModel summaryReportModel = this.designSolutionService.getSummaryReportModel(formKey);
            if (ObjectUtils.isEmpty(summaryReportModel)) {
                formData.setGriddata(this.getGridDataBytes(null));
            } else {
                ArrayList<LinkData> links = new ArrayList<LinkData>();
                List<DataCell> dataCells = summaryReportModel.getDataCells();
                if (!CollectionUtils.isEmpty(dataCells)) {
                    for (DataCell dataCell : dataCells) {
                        links.add(this.DC2LD(dataCell));
                    }
                }
                formData.setLinks(links);
                GridData griddata = summaryReportModel.getGridData();
                this.handleGridData(summaryReportModel, viewType);
                formData.setGriddata(this.getGridDataBytes(griddata));
            }
        }
        return formData;
    }

    private void handleGridData(SummaryReportModel summaryReportModel, int viewType) {
        List<DataCell> dataCells;
        GridData griddata = summaryReportModel.getGridData();
        if (ObjectUtils.isEmpty(griddata)) {
            return;
        }
        GridHelper gridHelper = new GridHelper(griddata);
        List<MainCell> mainCells = summaryReportModel.getConfig().getMainCells();
        if (!CollectionUtils.isEmpty(mainCells)) {
            mainCells.forEach(mainCell -> {
                SummaryZb innerDimZb = mainCell.getInnerDimZb();
                if (!ObjectUtils.isEmpty(innerDimZb)) {
                    GridCell gridCell = gridHelper.getCellEx(SummaryReportUtil.getPosition(mainCell));
                    gridCell.setShowText(innerDimZb.getTitle());
                }
            });
        }
        if (!CollectionUtils.isEmpty(dataCells = summaryReportModel.getDataCells())) {
            dataCells.forEach(dataCell -> {
                SummaryZb summaryZb;
                GridCell gridCell = gridHelper.getCellEx(SummaryReportUtil.getPosition(dataCell));
                String showText = "[" + dataCell.getRowNum() + "," + dataCell.getColNum() + "]";
                if (viewType == 1 && !ObjectUtils.isEmpty(summaryZb = dataCell.getSummaryZb())) {
                    showText = summaryZb.getTableName() + "[" + summaryZb.getName() + "]";
                }
                gridCell.setShowText(showText);
                gridCell.setFontColor(255);
                griddata.setCell(gridCell);
            });
        }
    }

    private byte[] getGridDataBytes(GridData griddata) throws JsonProcessingException {
        Grid2Data grid2Data = new Grid2Data();
        if (!ObjectUtils.isEmpty(griddata)) {
            GridDataTransform.gridDataToGrid2Data((GridData)griddata, (Grid2Data)grid2Data);
            GridDataTransform.Grid2DataTextFilter((Grid2Data)grid2Data);
        } else {
            grid2Data.setRowCount(10);
            grid2Data.setColumnCount(10);
        }
        SimpleModule module = new SimpleModule();
        module.addSerializer(Grid2Data.class, (JsonSerializer)new Grid2DataSerialize());
        module.addSerializer(GridCellData.class, (JsonSerializer)new GridCellDataSerialize());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule((Module)module);
        String result = mapper.writeValueAsString((Object)grid2Data);
        return result.getBytes();
    }

    private LinkData DC2LD(DataCell dataCell) {
        if (ObjectUtils.isEmpty(dataCell)) {
            return null;
        }
        LinkData linkData = new LinkData();
        linkData.setCol(dataCell.getColNum());
        linkData.setRow(dataCell.getRowNum());
        linkData.setX(dataCell.getY() + 1);
        linkData.setY(dataCell.getX() + 1);
        SummaryZb summaryZb = dataCell.getSummaryZb();
        if (!ObjectUtils.isEmpty(summaryZb)) {
            linkData.setLinkExpression(summaryZb.getFieldKey());
            linkData.setFieldcode(summaryZb.getName());
            linkData.setFieldtitle(summaryZb.getTitle());
            linkData.setDatatype(DefinitionTransUtils.valueOf((DataFieldType)summaryZb.getDataType()));
            linkData.setTableCode(summaryZb.getTableName());
            linkData.setLinkType(DataLinkType.DATA_LINK_TYPE_FIELD.getValue());
        } else {
            linkData.setLinkExpression(dataCell.getExp());
            linkData.setFieldtitle(dataCell.getExpTitle());
            linkData.setLinkType(DataLinkType.DATA_LINK_TYPE_FORMULA.getValue());
        }
        return linkData;
    }
}

