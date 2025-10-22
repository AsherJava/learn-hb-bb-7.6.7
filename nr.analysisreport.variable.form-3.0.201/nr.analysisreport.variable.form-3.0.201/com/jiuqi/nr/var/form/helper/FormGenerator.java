/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper
 *  com.jiuqi.nr.analysisreport.utils.Col
 *  com.jiuqi.nr.analysisreport.utils.Table
 *  com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datacrud.DataValueFormatterBuilder
 *  com.jiuqi.nr.datacrud.DataValueFormatterBuilderFactory
 *  com.jiuqi.nr.datacrud.IDataValue
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.IRowData
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.api.DataValueFormatter
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.BigDataDefine
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.RegionSettingDefine
 *  com.jiuqi.nr.definition.facade.RowNumberSetting
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.var.common.uitl.TableUtil
 *  com.jiuqi.nr.var.common.vo.HtmlTableContext
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  io.netty.util.internal.StringUtil
 *  org.jsoup.nodes.Element
 */
package com.jiuqi.nr.var.form.helper;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.analysisreport.helper.AnalysisReportLogHelper;
import com.jiuqi.nr.analysisreport.utils.Col;
import com.jiuqi.nr.analysisreport.utils.Table;
import com.jiuqi.nr.analysisreport.vo.ReportVariableParseVO;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datacrud.DataValueFormatterBuilder;
import com.jiuqi.nr.datacrud.DataValueFormatterBuilderFactory;
import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.api.DataValueFormatter;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.BigDataDefine;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.var.common.uitl.TableUtil;
import com.jiuqi.nr.var.common.vo.HtmlTableContext;
import com.jiuqi.nvwa.grid2.Grid2Data;
import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class FormGenerator {
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private DataValueFormatterBuilderFactory dataValueFormatterBuilderFactory;
    @Autowired
    private IDataQueryService dataQueryService;
    @Autowired
    private IPeriodEntityAdapter periodAdapter;

    public DimensionValueSet buildDim(Map<String, DimensionValue> commonDim, String formKey, String taskId) {
        if (StringUtils.isEmpty((String)taskId)) {
            FormDefine formDefine = this.iRunTimeViewController.queryFormById(formKey);
            FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formDefine.getFormScheme());
            taskId = formScheme.getTaskKey();
        }
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskId);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        String dw = taskDefine.getDw();
        if (commonDim.containsKey(dw)) {
            dimensionValueSet.setValue(commonDim.get(dw).getName(), (Object)commonDim.get(dw).getValue());
        }
        String dateTime = taskDefine.getDateTime();
        String periodDimensionName = this.periodAdapter.getPeriodDimensionName(dateTime);
        dimensionValueSet.setValue(periodDimensionName, (Object)commonDim.get(periodDimensionName).getValue());
        if (StringUtils.isNotEmpty((String)taskDefine.getDims())) {
            String[] dims;
            for (String dim : dims = taskDefine.getDims().split(";")) {
                if (!commonDim.containsKey(dim)) continue;
                dimensionValueSet.setValue(commonDim.get(dim).getName(), (Object)commonDim.get(dim).getValue());
            }
        }
        return dimensionValueSet;
    }

    public String doGenerator(Map<String, DimensionValue> dimensionValueMap, ReportVariableParseVO reportVariableParseVO, Element es) {
        String formKey = es.attr("var-expr");
        Grid2Data grid2Data = this.getGridData(formKey);
        HtmlTableContext htmlTableContext = new HtmlTableContext(grid2Data, "formVar", reportVariableParseVO.getReportBaseVO().getKey());
        TableUtil.dealTableCustomSettings((HtmlTableContext)htmlTableContext, (Element)es, (ReportVariableParseVO)reportVariableParseVO);
        Table table = TableUtil.geneartorTable((HtmlTableContext)htmlTableContext);
        List<DataRegionDefine> allRegionsInForm = this.iRunTimeViewController.getAllRegionsInForm(formKey);
        allRegionsInForm = this.orderRegions(allRegionsInForm);
        DimensionValueSet dimensionValueSet = this.buildDim(dimensionValueMap, formKey, es.attr("var-task"));
        try {
            for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
                this.fillData(dimensionValueSet, dataRegionDefine, table);
            }
        }
        catch (Exception ex) {
            String exTitle = new StringBuffer().append("\u8868\u5355\u53d8\u91cf[").append(formKey).append("]\u8fd0\u7b97\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0[").append(!StringUtil.isNullOrEmpty((String)ex.getMessage()) ? ex.getMessage() : ex.toString()).append("]").toString();
            AnalysisReportLogHelper.log((String)(exTitle.length() > 100 ? exTitle.substring(0, 100) : exTitle), (String)(exTitle + ex.toString()), (Exception)ex);
        }
        return table.toString();
    }

    public RowNumberSetting queryRowNumberSettings(String regionKey) {
        List rowNumberSettings;
        RegionSettingDefine regionSetting = this.iRunTimeViewController.getRegionSetting(regionKey);
        if (regionSetting != null && !CollectionUtils.isEmpty(rowNumberSettings = regionSetting.getRowNumberSetting())) {
            return (RowNumberSetting)rowNumberSettings.get(0);
        }
        return null;
    }

    public IRegionDataSet getRegionDataSet(DimensionValueSet dimensionValueSet, String regionKey) {
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder(dimensionValueSet);
        QueryInfoBuilder queryInfoBuilder = QueryInfoBuilder.create((String)regionKey, (DimensionCombination)builder.getCombination());
        queryInfoBuilder.setDesensitized(true);
        return this.dataQueryService.queryRegionData(queryInfoBuilder.build());
    }

    public Object formatData(IDataValue dataValue, DataValueFormatter dataValueFormatter) {
        if (dataValue.getAsNull()) {
            return "";
        }
        IMetaData metaData = dataValue.getMetaData();
        DataFieldType dataFieldType = metaData.getDataFieldType();
        if (DataFieldType.FILE == dataFieldType || DataFieldType.PICTURE == dataFieldType) {
            return "";
        }
        return dataValueFormatter.format(dataValue);
    }

    private void fillData(DimensionValueSet dimensionValueSet, DataRegionDefine dataRegionDefine, Table table) {
        IRegionDataSet regionDataSet = this.getRegionDataSet(dimensionValueSet, dataRegionDefine.getKey());
        if (regionDataSet == null) {
            return;
        }
        int regionRowNum = 0;
        RowNumberSetting rowNumberSetting = null;
        int maxSequence = 0;
        if (dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_ROW_LIST) {
            regionRowNum = dataRegionDefine.getRegionBottom() - dataRegionDefine.getRegionTop() + 1;
            rowNumberSetting = this.queryRowNumberSettings(dataRegionDefine.getKey());
            if (rowNumberSetting != null) {
                maxSequence = rowNumberSetting.getStartNumber() + (regionDataSet.getRowData().size() * regionRowNum - 1) * rowNumberSetting.getIncrement();
            }
        }
        int regionY = dataRegionDefine.getRegionTop() - 1;
        DataValueFormatterBuilder formatterBuilder = this.dataValueFormatterBuilderFactory.createFormatterBuilder();
        for (int i = regionDataSet.getRowData().size() - 1; i >= 0; --i) {
            for (IDataValue dataValue : ((IRowData)regionDataSet.getRowData().get(i)).getLinkDataValues()) {
                DataLinkDefine dataLinkDefine;
                if (dataValue == null || (dataLinkDefine = dataValue.getMetaData().getDataLinkDefine()) == null) continue;
                Col col = table.getRow(dataLinkDefine.getPosY() - 1).getCol(dataLinkDefine.getPosX() - 1);
                col.setValue(this.formatData(dataValue, formatterBuilder.build()));
            }
            for (int j = regionRowNum; j > 0; --j) {
                if (rowNumberSetting != null) {
                    Col colr = table.getRow(rowNumberSetting.getPosY() - 2 + (i == 0 ? j : regionRowNum)).getCol(rowNumberSetting.getPosX() - 1);
                    colr.setValue((Object)maxSequence);
                    maxSequence -= rowNumberSetting.getIncrement();
                }
                if (i == 0) continue;
                table.copyRowFromSource(regionY, regionY + regionRowNum - 1);
            }
        }
    }

    public Grid2Data getGridData(String formKey) {
        BigDataDefine formDefine = this.iRunTimeViewController.getReportDataFromForm(formKey);
        if (null != formDefine) {
            if (formDefine.getData() != null) {
                return Grid2Data.bytesToGrid((byte[])formDefine.getData());
            }
            Grid2Data griddata = new Grid2Data();
            griddata.setRowCount(10);
            griddata.setColumnCount(10);
            return griddata;
        }
        return null;
    }

    private List<DataRegionDefine> orderRegions(List<DataRegionDefine> dataRegions) {
        ArrayList<DataRegionDefine> fixRegions = new ArrayList<DataRegionDefine>();
        ArrayList<DataRegionDefine> floatRegions = new ArrayList<DataRegionDefine>();
        for (DataRegionDefine regionData : dataRegions) {
            if (DataRegionKind.DATA_REGION_SIMPLE == regionData.getRegionKind()) {
                fixRegions.add(regionData);
                continue;
            }
            if (DataRegionKind.DATA_REGION_ROW_LIST != regionData.getRegionKind()) continue;
            floatRegions.add(regionData);
        }
        floatRegions.sort((o1, o2) -> Integer.compare(o2.getRegionTop(), o1.getRegionTop()));
        fixRegions.addAll(floatRegions);
        return fixRegions;
    }
}

