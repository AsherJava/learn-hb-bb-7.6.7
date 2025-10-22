/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.formsetting.vo.DataLinkSettingVO
 *  com.jiuqi.gcreport.formsetting.vo.DataRegionSettingVO
 *  com.jiuqi.gcreport.formsetting.vo.SettingVO
 *  com.jiuqi.gcreport.inputdata.util.InputDataNameProvider
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.bpm.impl.common.NvwaDataModelCreateUtil
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaDesignTimeController
 *  com.jiuqi.nr.definition.facade.DesignDataLinkDefine
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaDefine
 *  com.jiuqi.nr.definition.facade.DesignRegionSettingDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignRowNumberSettingImpl
 *  com.jiuqi.nr.definition.internal.impl.RegionTabSettingData
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.nvwa.grid2.GridCellData
 *  org.apache.commons.lang3.ArrayUtils
 */
package com.jiuqi.gcreport.formsetting.util;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.formsetting.template.ColumnOrderTemplate;
import com.jiuqi.gcreport.formsetting.template.FloatTemplate;
import com.jiuqi.gcreport.formsetting.template.HeadTemplate;
import com.jiuqi.gcreport.formsetting.template.SumTemplate;
import com.jiuqi.gcreport.formsetting.util.BaseDataUtils;
import com.jiuqi.gcreport.formsetting.util.CodeGenerator;
import com.jiuqi.gcreport.formsetting.util.FixedTableGenerator;
import com.jiuqi.gcreport.formsetting.vo.DataLinkSettingVO;
import com.jiuqi.gcreport.formsetting.vo.DataRegionSettingVO;
import com.jiuqi.gcreport.formsetting.vo.SettingVO;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.bpm.impl.common.NvwaDataModelCreateUtil;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.facade.DesignDataLinkDefine;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaDefine;
import com.jiuqi.nr.definition.facade.DesignRegionSettingDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.internal.impl.DesignRowNumberSettingImpl;
import com.jiuqi.nr.definition.internal.impl.RegionTabSettingData;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.nvwa.grid2.GridCellData;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class FormSettingProcessor {
    private final IDesignTimeViewController designTimeViewController;
    private final IDesignDataSchemeService designDataSchemeService;
    private final IFormulaDesignTimeController formulaDesignTimeController;
    private final ConsolidatedTaskService conTaskServie;
    private final SettingVO setting;
    private final DesignFormDefine formDefine;
    private final InputDataNameProvider inputDataNameProvider;
    private DesignDataTable inputDataTable;
    private final NvwaDataModelCreateUtil nvwaDataModelCreateUtil;
    private final DataModelService dataModelService;
    private DesignDataRegionDefine fixedDataRegionDefine;
    private DesignDataRegionDefine floatDataRegionDefine;
    private Grid2Data grid;
    private static final String FIELDNAME_FLOATORDER = "FLOATORDER";
    private DesignRegionSettingDefine regionSettingDefine;
    private boolean isNewReginSetting;
    private static final int GIRD_COLUMN_BEGIN_INDEX = 1;
    private List<DesignDataLinkDefine> sumDataLinks;
    private List<DesignDataLinkDefine> sumDataLinksNew = new ArrayList<DesignDataLinkDefine>();
    private List<DesignDataLinkDefine> sumDataLinksUpdate = new ArrayList<DesignDataLinkDefine>();
    private List<DesignDataField> sumFieldsNew = new ArrayList<DesignDataField>();
    private DesignDataTable sumTable;
    private boolean isSumTableNew = false;
    private List<DesignFormulaDefine> formulaDefinesDel;
    private String maxFormulaCode;
    private List<DesignFormulaDefine> formulaDefinesNew = new ArrayList<DesignFormulaDefine>();
    private List<DesignFormulaDefine> formulaDefinesUpdate = new ArrayList<DesignFormulaDefine>();
    private Map<String, DesignDataLinkDefine> floatDataLinksOld;
    private List<DesignDataLinkDefine> floatDataLinksNew = new ArrayList<DesignDataLinkDefine>();
    private List<DesignDataLinkDefine> floatDataLinksUpdate = new ArrayList<DesignDataLinkDefine>();
    private static final String FIELDNAME_CODE = "CODE";
    private static final String FIELDNAME_NAME = "NAME";

    public FormSettingProcessor(SettingVO setting) {
        this.setting = setting;
        this.designTimeViewController = (IDesignTimeViewController)SpringContextUtils.getBean(IDesignTimeViewController.class);
        this.designDataSchemeService = (IDesignDataSchemeService)SpringContextUtils.getBean(IDesignDataSchemeService.class);
        this.formulaDesignTimeController = (IFormulaDesignTimeController)SpringContextUtils.getBean(IFormulaDesignTimeController.class);
        this.formDefine = this.designTimeViewController.queryFormById(setting.getFormSetting().getId());
        this.conTaskServie = (ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class);
        this.inputDataNameProvider = (InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class);
        this.nvwaDataModelCreateUtil = (NvwaDataModelCreateUtil)SpringContextUtils.getBean(NvwaDataModelCreateUtil.class);
        this.dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        if (this.formDefine == null) {
            throw new BusinessRuntimeException("\u8868\u5355\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u8bbe\u7f6e\u3002");
        }
        this.initDesignDataTable();
    }

    public void updateFormSetting() {
        if (this.setting.getFormSetting().getId() == null || this.designTimeViewController.queryFormById(this.setting.getFormSetting().getId()) == null) {
            return;
        }
        this.updateSetting();
    }

    private void updateSetting() {
        this.saveSettingFields();
        this.buildDataRegin();
        this.buildGrid();
        this.saveSetting();
    }

    private void buildDataRegin() {
        this.checkDataRegin();
        this.buildFixedDataReginDefine(this.setting.getDataRegionSetting(), this.setting.getFormSetting().getId(), this.setting.getFormSetting().getTitle(), this.setting.getDataLinks().size());
        this.buildFloatDataReginDefine(this.setting.getDataRegionSetting(), this.setting.getFormSetting().getId(), this.setting.getFormSetting().getTitle(), this.setting.getDataLinks().size());
    }

    private void checkDataRegin() {
        List existsRegions = this.designTimeViewController.getAllRegionsInForm(this.setting.getFormSetting().getId());
        if (existsRegions.size() > 2) {
            throw new BusinessRuntimeException("\u8868\u5355\u4e2d\u533a\u57df\u6570\u91cf\u5927\u4e8e2\uff0c\u65e0\u6cd5\u4f7f\u7528\u6b64\u529f\u80fd\u3002");
        }
        int needCreateRegions = 0;
        if (this.setting.getDataRegionSetting().getFixedRegionId() == null) {
            ++needCreateRegions;
        }
        if (this.setting.getDataRegionSetting().getFloatRegionId() == null) {
            ++needCreateRegions;
        }
        if (existsRegions.size() + needCreateRegions > 2) {
            throw new BusinessRuntimeException("\u8868\u5355\u4e2d\u5df2\u5b58\u5728\u533a\u57df\u6570\u91cf\uff0c\u52a0\u9700\u8981\u521b\u5efa\u7684\u533a\u57df\u6570\u91cf\u5927\u4e8e2\uff0c\u65e0\u6cd5\u4f7f\u7528\u6b64\u529f\u80fd\u3002");
        }
        for (int index = 0; index < existsRegions.size(); ++index) {
            DesignDataRegionDefine compareDataRegion;
            DesignDataRegionDefine designDataRegionDefine = compareDataRegion = DataRegionKind.DATA_REGION_SIMPLE.equals((Object)((DesignDataRegionDefine)existsRegions.get(0)).getRegionKind()) ? this.fixedDataRegionDefine : this.floatDataRegionDefine;
            if (compareDataRegion == null || ((DesignDataRegionDefine)existsRegions.get(0)).getKey().equals(compareDataRegion.getKey())) continue;
            throw new BusinessRuntimeException(String.format("\u65b0\u589e\u201c%1$s\u201d\u533a\u57df\u6807\u8bc6\u4e0e\u5df2\u6709\u201c$1$s\u201d\u533a\u57df\u6807\u8bc6\u4e0d\u4e00\u81f4\u3002", DataRegionKind.DATA_REGION_SIMPLE.equals((Object)((DesignDataRegionDefine)existsRegions.get(0)).getRegionKind()) ? "\u56fa\u5b9a" : "\u6d6e\u52a8"));
        }
    }

    private DesignDataRegionDefine buildFixedDataReginDefine(DataRegionSettingVO dataRegionDesign, String formId, String formTitle, int dataLinkNum) {
        if (dataRegionDesign.getFixedRegionId() != null) {
            this.fixedDataRegionDefine = this.designTimeViewController.queryDataRegionDefine(dataRegionDesign.getFixedRegionId());
        }
        if (this.fixedDataRegionDefine == null) {
            this.fixedDataRegionDefine = this.designTimeViewController.createDataRegionDefine();
            this.fixedDataRegionDefine.setFormKey(formId);
            this.fixedDataRegionDefine.setRegionKind(DataRegionKind.DATA_REGION_SIMPLE);
            this.fixedDataRegionDefine.setOrder(OrderGenerator.newOrder());
            this.fixedDataRegionDefine.setRegionLeft(1);
            this.fixedDataRegionDefine.setRegionRight(dataLinkNum + 1);
            this.fixedDataRegionDefine.setRegionTop(1);
            this.fixedDataRegionDefine.setRegionBottom(dataRegionDesign.getFloatBeginRowNum());
        } else {
            this.fixedDataRegionDefine.setRegionRight(dataLinkNum + 1);
            this.fixedDataRegionDefine.setRegionBottom(dataRegionDesign.getFloatBeginRowNum());
        }
        this.fixedDataRegionDefine.setTitle(formTitle + "\u56fa\u5b9a\u533a\u57df");
        this.fixedDataRegionDefine.setUpdateTime(new Date());
        return this.fixedDataRegionDefine;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void buildFloatDataReginDefine(DataRegionSettingVO dataRegionSetting, String formId, String formTitle, int dataLinkNum) {
        if (dataRegionSetting.getFloatRegionId() != null) {
            this.floatDataRegionDefine = this.designTimeViewController.queryDataRegionDefine(dataRegionSetting.getFloatRegionId());
        }
        if (this.floatDataRegionDefine == null) {
            this.floatDataRegionDefine = this.designTimeViewController.createDataRegionDefine();
            this.floatDataRegionDefine.setFormKey(formId);
            this.floatDataRegionDefine.setRegionKind(DataRegionKind.DATA_REGION_ROW_LIST);
            this.floatDataRegionDefine.setOrder(OrderGenerator.newOrder());
            this.floatDataRegionDefine.setShowGatherDetailRows(true);
            this.floatDataRegionDefine.setShowGatherSummaryRow(true);
            this.floatDataRegionDefine.setMaxRowCount(0);
            this.floatDataRegionDefine.setRowsInFloatRegion(1);
            this.floatDataRegionDefine.setAllowDuplicateKey(true);
        }
        if (ObjectUtils.isEmpty(this.floatDataRegionDefine.getInputOrderFieldKey())) {
            if (this.inputDataTable == null) throw new BusinessRuntimeException("\u5185\u90e8\u5f55\u5165\u8868[GC_INPUTDATA]\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u8bbe\u7f6e\u3002");
            DesignDataField inputDataField = this.designDataSchemeService.getDataFieldByTableKeyAndCode(this.inputDataTable.getKey(), FIELDNAME_FLOATORDER);
            if (inputDataField == null) throw new BusinessRuntimeException("\u5185\u90e8\u5f55\u5165\u8868[GC_INPUTDATA]\u4e2d\u6307\u6807[FLOATORDER]\u4e0d\u5b58\u5728\uff0c\u65e0\u6cd5\u8bbe\u7f6e\u3002");
            this.floatDataRegionDefine.setInputOrderFieldKey(inputDataField.getKey());
        }
        this.floatDataRegionDefine.setTitle(formTitle + "\u884c\u6d6e\u52a8\u533a\u57df");
        this.floatDataRegionDefine.setRegionLeft(1);
        this.floatDataRegionDefine.setRegionRight(dataLinkNum + 1);
        this.floatDataRegionDefine.setRegionTop(dataRegionSetting.getFloatBeginRowNum());
        this.floatDataRegionDefine.setRegionBottom(dataRegionSetting.getFloatBeginRowNum());
        this.floatDataRegionDefine.setUpdateTime(new Date());
        this.floatDataRegionDefine.setReadOnlyCondition(this.setting.getReadOnlyFormula());
        this.buildFloatRegionSetting();
    }

    private void buildFloatRegionSetting() {
        if (this.floatDataRegionDefine.getRegionSettingKey() != null) {
            this.regionSettingDefine = this.designTimeViewController.getRegionSetting(this.floatDataRegionDefine.getRegionSettingKey());
        }
        if (this.regionSettingDefine == null) {
            this.regionSettingDefine = this.designTimeViewController.createRegionSetting();
            this.floatDataRegionDefine.setRegionSettingKey(this.regionSettingDefine.getKey());
            this.isNewReginSetting = true;
        }
        ArrayList regionTabSettings = new ArrayList();
        this.regionSettingDefine.setRegionTabSetting(regionTabSettings);
        this.setting.getPageSettings().forEach(pageSetting -> {
            RegionTabSettingData regionTabSettingDefine = new RegionTabSettingData();
            regionTabSettings.add(regionTabSettingDefine);
            regionTabSettingDefine.setTitle(pageSetting.getTitle());
            regionTabSettingDefine.setFilterCondition(pageSetting.getFilterCondition());
            regionTabSettingDefine.setDisplayCondition(pageSetting.getDisplayCondition());
            regionTabSettingDefine.setOrder(OrderGenerator.newOrder());
            regionTabSettingDefine.setBindingExpression("");
        });
        ArrayList<DesignRowNumberSettingImpl> rowNumberSettings = new ArrayList<DesignRowNumberSettingImpl>();
        this.regionSettingDefine.setRowNumberSetting(rowNumberSettings);
        DesignRowNumberSettingImpl designRowNumberSettingImpl = new DesignRowNumberSettingImpl();
        designRowNumberSettingImpl.setPosX(this.floatDataRegionDefine.getRegionLeft());
        designRowNumberSettingImpl.setPosY(this.floatDataRegionDefine.getRegionBottom());
        designRowNumberSettingImpl.setStartNumber(1);
        designRowNumberSettingImpl.setIncrement(1);
        rowNumberSettings.add(designRowNumberSettingImpl);
    }

    private void buildGrid() {
        if (ArrayUtils.isEmpty((byte[])this.designTimeViewController.getReportDataFromForm(this.formDefine.getKey(), 1))) {
            this.grid = new Grid2Data();
            this.grid.setHeaderColumnCount(0);
            this.grid.setHeaderRowCount(0);
        } else {
            this.grid = Grid2Data.bytesToGrid((byte[])this.designTimeViewController.getReportDataFromForm(this.formDefine.getKey(), 1));
        }
        this.grid.setColumnCount(this.fixedDataRegionDefine.getRegionRight() + 1);
        this.grid.setRowCount(this.fixedDataRegionDefine.getRegionBottom() + 1);
        this.setGridHead();
        this.setGridColumnOrderNum();
        this.setGridFloat();
        this.setGridSum();
    }

    private void setGridHead() {
        int rowIndex = this.setting.getDataRegionSetting().getHeadRowIndex();
        if (rowIndex < 0) {
            return;
        }
        int columnIndex = 1;
        for (Map.Entry<String, Integer> entry : HeadTemplate.getFixedColumns().entrySet()) {
            GridCellData cellData = this.grid.getGridCellData(columnIndex, rowIndex);
            this.setHeadCellStyle(cellData);
            if (Objects.equals(entry.getKey(), cellData.getShowText())) {
                columnIndex += entry.getValue().intValue();
                continue;
            }
            cellData.setShowText(entry.getKey());
            cellData.setEditText(entry.getKey());
            cellData.setColSpan(entry.getValue().intValue());
            columnIndex += entry.getValue().intValue();
        }
        for (int linkIndex = 0; linkIndex < this.setting.getDataLinks().size(); ++linkIndex) {
            GridCellData cellData = this.grid.getGridCellData(columnIndex, rowIndex);
            this.setHeadCellStyle(cellData);
            if (!Objects.equals(this.getDataLinkTitle((DataLinkSettingVO)this.setting.getDataLinks().get(linkIndex)), cellData.getShowText())) {
                cellData.setShowText(this.getDataLinkTitle((DataLinkSettingVO)this.setting.getDataLinks().get(linkIndex)));
                cellData.setEditText(this.getDataLinkTitle((DataLinkSettingVO)this.setting.getDataLinks().get(linkIndex)));
            }
            ++columnIndex;
        }
    }

    private void setHeadCellStyle(GridCellData cellData) {
        if (StringUtils.isEmpty(cellData.getShowText())) {
            cellData.setCellStyleData(HeadTemplate.getGridCellStyleData());
        }
    }

    private String getDataLinkTitle(DataLinkSettingVO dataLink) {
        if (!StringUtils.isEmpty(dataLink.getTitle())) {
            return dataLink.getTitle();
        }
        FieldInfo field = this.getDesignFieldDefineById(dataLink.getExpression());
        return field == null ? "" : field.getTitle();
    }

    private void setGridColumnOrderNum() {
        int rowIndex = this.setting.getDataRegionSetting().getColumnOrderRowIndex();
        if (rowIndex < 0) {
            return;
        }
        int columnIndex = 1;
        for (Map.Entry<String, Integer> entry : ColumnOrderTemplate.getFixedColumns().entrySet()) {
            GridCellData cellData = this.grid.getGridCellData(columnIndex, rowIndex);
            this.setHeadCellStyle(cellData);
            if (Objects.equals(entry.getKey(), cellData.getShowText())) {
                columnIndex += entry.getValue().intValue();
                continue;
            }
            cellData.setShowText(entry.getKey());
            cellData.setColSpan(entry.getValue().intValue());
            columnIndex += entry.getValue().intValue();
        }
        for (int linkIndex = 1; linkIndex <= this.setting.getDataLinks().size(); ++linkIndex) {
            GridCellData cellData = this.grid.getGridCellData(columnIndex, rowIndex);
            this.setHeadCellStyle(cellData);
            if (!Objects.equals(ColumnOrderTemplate.getShowText(linkIndex), cellData.getShowText())) {
                cellData.setShowText(ColumnOrderTemplate.getShowText(linkIndex));
            }
            ++columnIndex;
        }
    }

    private void setGridSum() {
        int rowIndexEnd = this.setting.getDataRegionSetting().getRowNums();
        GridCellData cellDataEnd1 = this.grid.getGridCellData(1, rowIndexEnd);
        cellDataEnd1.setCellStyleData(SumTemplate.getGridCellStyleData());
        cellDataEnd1.setShowText(null);
        int rowIndex = this.setting.getDataRegionSetting().getSumRowIndex();
        if (rowIndex < 0) {
            return;
        }
        this.readFixedDataLinks();
        this.readFormula();
        int columnIndex = 1;
        GridCellData cellDataEnd2 = this.grid.getGridCellData(2, rowIndex);
        if (cellDataEnd2 != null) {
            cellDataEnd2.setCellStyleData(SumTemplate.getGridCellStyleData());
            cellDataEnd2.setShowText(null);
        }
        for (Map.Entry<String, Integer> entry : SumTemplate.getFixedColumns().entrySet()) {
            GridCellData cellData = this.grid.getGridCellData(columnIndex, rowIndex);
            this.setHeadCellStyle(cellData);
            cellData.setShowText(entry.getKey());
            cellData.setColSpan(entry.getValue().intValue());
            columnIndex += entry.getValue().intValue();
        }
        for (int linkIndex = columnIndex - SumTemplate.getFixedColumns().size() - 1; linkIndex < this.setting.getDataLinks().size(); ++linkIndex) {
            GridCellData cellData = this.grid.getGridCellData(columnIndex, rowIndex);
            this.setHeadCellStyle(cellData);
            if (this.isNumberType(((DataLinkSettingVO)this.setting.getDataLinks().get(linkIndex)).getExpression(), linkIndex + 1)) {
                DesignDataLinkDefine designDataLinkDefine = this.buildFixedDataLink(columnIndex, rowIndex, (DataLinkSettingVO)this.setting.getDataLinks().get(linkIndex), cellData);
            } else {
                cellData.setShowText(SumTemplate.getDefaultShowText());
                cellData.setCellStyleData(HeadTemplate.getGridCellStyleData());
            }
            ++columnIndex;
        }
    }

    private boolean isNumberType(String expression, int dataLinkOrder) {
        try {
            UUID.fromString(expression);
        }
        catch (IllegalArgumentException e) {
            return false;
        }
        FieldInfo fieldInfo = this.getDesignFieldDefineById(expression);
        if (fieldInfo == null) {
            throw new BusinessRuntimeException("\u7b2c" + dataLinkOrder + "\u884c\u6307\u6807\u8bbe\u7f6e\u6709\u8bef\uff0cexpression\u5173\u8054\u7684\u6807\u8bc6\u4e3a\u201c" + expression + "\u201d\u7684\u5b57\u6bb5\u4e0d\u5b58\u5728\u3002");
        }
        return fieldInfo.getType() == FieldType.FIELD_TYPE_DECIMAL || fieldInfo.getType() == FieldType.FIELD_TYPE_INTEGER || fieldInfo.getType() == FieldType.FIELD_TYPE_FLOAT;
    }

    private boolean isNumberType(Integer type) {
        if (type == null) {
            return false;
        }
        return type.intValue() == DataFieldType.BIGDECIMAL.getValue() || type.intValue() == DataFieldType.INTEGER.getValue();
    }

    private FieldInfo getDesignFieldDefineById(String fieldId) {
        final DesignDataField fieldDefine = this.designDataSchemeService.getDataField(fieldId);
        if (fieldDefine == null) {
            return null;
        }
        return new FieldInfo(){

            @Override
            public String getCode() {
                return fieldDefine.getCode();
            }

            @Override
            public FieldType getType() {
                return fieldDefine.getType();
            }

            @Override
            public FieldValueType getValueType() {
                return fieldDefine.getValueType();
            }

            @Override
            public int getSize() {
                return fieldDefine.getSize();
            }

            @Override
            public String getTitle() {
                return fieldDefine.getTitle();
            }

            @Override
            public String getRefDataEntityKey() {
                return fieldDefine.getRefDataEntityKey();
            }

            @Override
            public FieldGatherType getGatherType() {
                return fieldDefine.getGatherType();
            }

            @Override
            public DataFieldType getDataFieldType() {
                return fieldDefine.getDataFieldType();
            }
        };
    }

    private DesignDataLinkDefine buildFixedDataLink(int posX, int posY, DataLinkSettingVO dataLink, GridCellData cellData) {
        FieldInfo fieldInfo = this.getDesignFieldDefineById(dataLink.getExpression());
        if (fieldInfo == null) {
            return null;
        }
        if (!"1".equals(dataLink.getFieldTypeSelect()) || !this.isNumberType(fieldInfo.getDataFieldType().getValue())) {
            cellData.setShowText(SumTemplate.getDefaultShowText());
            cellData.setCellStyleData(SumTemplate.getGridCellStyleData());
            return null;
        }
        cellData.setCellStyleData(FloatTemplate.getGridCellRightStyleData());
        cellData.setShowText("");
        cellData.setEditText("");
        DesignDataLinkDefine dataLinkOld = this.designTimeViewController.queryDataLinkDefine(this.formDefine.getKey(), posX, posY);
        boolean isFixDateLink = true;
        for (DesignDataLinkDefine define : this.sumDataLinks) {
            if (dataLinkOld == null || !define.getKey().equals(dataLinkOld.getKey())) continue;
            dataLinkOld = define;
            isFixDateLink = false;
        }
        if (isFixDateLink) {
            dataLinkOld = null;
        }
        if (dataLinkOld == null) {
            DesignDataField designDataField = null;
            List<String> fieldCodes = this.designDataSchemeService.getDataFieldByTable(this.sumTable.getKey()).stream().map(Basic::getCode).collect(Collectors.toList());
            String fieldCode = CodeGenerator.generateFieldCode(this.sumTable.getCode(), fieldCodes, posX);
            try {
                designDataField = this.designDataSchemeService.getDataFieldByTableKeyAndCode(this.sumTable.getKey(), fieldCode);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u83b7\u53d6\u6307\u6807\u5931\u8d25", (Throwable)e);
            }
            if (designDataField == null) {
                try {
                    designDataField = this.designDataSchemeService.initDataField();
                    designDataField.setKey(UUIDUtils.newUUIDStr());
                    designDataField.setCode(fieldCode);
                    FieldType fieldType = FieldType.forValue((int)fieldInfo.getType().getValue());
                    if (FieldType.FIELD_TYPE_FLOAT == fieldType || FieldType.FIELD_TYPE_DECIMAL == fieldType) {
                        designDataField.setDataFieldType(DataFieldType.BIGDECIMAL);
                        designDataField.setDecimal(Integer.valueOf(2));
                    } else if (FieldType.FIELD_TYPE_INTEGER == fieldType) {
                        designDataField.setDataFieldType(DataFieldType.INTEGER);
                    } else if (fieldType.name().contains(FieldType.FIELD_TYPE_DATE.name())) {
                        designDataField.setDataFieldType(DataFieldType.DATE);
                    } else {
                        designDataField.setDataFieldType(DataFieldType.STRING);
                    }
                    designDataField.setPrecision(Integer.valueOf(fieldInfo.getSize()));
                    designDataField.setTitle("\u5408\u8ba1 " + dataLink.getTitle());
                    designDataField.setOrder(OrderGenerator.newOrder());
                    designDataField.setNullable(Boolean.valueOf(true));
                    designDataField.setDataSchemeKey(this.sumTable.getDataSchemeKey());
                    designDataField.setDataTableKey(this.sumTable.getKey());
                    FormatProperties formatProperties = new FormatProperties();
                    StringBuilder pattern = new StringBuilder("#,##0.00");
                    formatProperties.setPattern(pattern.toString());
                    formatProperties.setFormatType(Integer.valueOf(1));
                    designDataField.setFormatProperties(formatProperties);
                    designDataField.setDataFieldKind(DataFieldKind.FIELD_ZB);
                    fieldCodes.add(designDataField.getCode());
                    this.sumFieldsNew.add(designDataField);
                }
                catch (Exception e) {
                    throw new BusinessRuntimeException("\u521b\u5efa\u5b57\u6bb5\u5931\u8d25", (Throwable)e);
                }
            }
            dataLinkOld = this.designTimeViewController.createDataLinkDefine();
            dataLinkOld.setUniqueCode(OrderGenerator.newOrder());
            dataLinkOld.setOrder(OrderGenerator.newOrder());
            dataLinkOld.setRegionKey(this.fixedDataRegionDefine.getKey());
            dataLinkOld.setLinkExpression(designDataField.getKey());
            dataLinkOld.setPosX(posX);
            dataLinkOld.setColNum(posX);
            dataLinkOld.setPosY(posY);
            dataLinkOld.setRowNum(posY);
            this.sumDataLinksNew.add(dataLinkOld);
        } else {
            dataLinkOld.setPosX(posX);
            dataLinkOld.setPosY(posY);
            this.sumDataLinksUpdate.add(dataLinkOld);
        }
        String formulaSUMHBZBWithSum = String.format("[%s,%s]=SUMHBZB(%s[%s,SUM])", dataLinkOld.getRowNum(), dataLinkOld.getColNum(), this.setting.getFormSetting().getCode().toUpperCase(), this.getDataLinkCellPos(dataLink));
        Pattern patternZBWithSum = Pattern.compile("^\\[\\d+,\\d+\\]=(?i)(SUMHBZB)\\([0-9a-zA-Z\\_]+\\[[0-9a-zA-Z\\_,]{1,29}[,SUM]\\].*\\)$");
        String formulaSUMHB = String.format("[%s,%s]=SUMHB(\"%s\",\"\",\"%s\")", dataLinkOld.getRowNum(), dataLinkOld.getColNum(), "[" + this.getDataLinkCellPos(dataLink) + "]", this.setting.getFormSetting().getCode().toUpperCase());
        Pattern patternHB = Pattern.compile("^\\[\\d+,\\d+\\]=(?i)(SUMHB)\\(\\\"[0-9a-zA-Z\\_,\\[\\]]{1,29}\\\",\\\"\\\",\\\"[0-9a-zA-Z\\_]+\\\"\\)$");
        Optional<DesignFormulaDefine> matchedFormulaDefineZBWithSum = this.formulaDefinesDel.stream().filter(formulaDefine -> {
            Matcher matcher = patternZBWithSum.matcher(formulaDefine.getExpression().replace(" ", ""));
            return matcher.matches();
        }).findFirst();
        if (matchedFormulaDefineZBWithSum.isPresent()) {
            this.formulaDefinesDel.remove(matchedFormulaDefineZBWithSum.get());
            if (!formulaSUMHBZBWithSum.equalsIgnoreCase(matchedFormulaDefineZBWithSum.get().getExpression().replace(" ", ""))) {
                this.formulaDefinesUpdate.add(matchedFormulaDefineZBWithSum.get());
                matchedFormulaDefineZBWithSum.get().setExpression(formulaSUMHBZBWithSum);
            }
        } else {
            Optional<DesignFormulaDefine> matchedFormulaDefineHB = this.formulaDefinesDel.stream().filter(formulaDefine -> {
                Matcher matcher = patternHB.matcher(formulaDefine.getExpression().replace(" ", ""));
                return matcher.matches();
            }).findFirst();
            if (matchedFormulaDefineHB.isPresent()) {
                this.formulaDefinesDel.remove(matchedFormulaDefineHB.get());
                if (!formulaSUMHB.equalsIgnoreCase(matchedFormulaDefineHB.get().getExpression().replace(" ", ""))) {
                    this.formulaDefinesUpdate.add(matchedFormulaDefineHB.get());
                    matchedFormulaDefineHB.get().setExpression(formulaSUMHB);
                }
            } else {
                DesignFormulaDefine formulaDefine2 = this.formulaDesignTimeController.createFormulaDefine();
                this.formulaDefinesNew.add(formulaDefine2);
                formulaDefine2.setExpression(formulaSUMHBZBWithSum);
                formulaDefine2.setCheckType(FormulaCheckType.FORMULA_CHECK_NONE.getValue());
                formulaDefine2.setFormKey(this.setting.getFormSetting().getId());
                formulaDefine2.setFormulaSchemeKey(this.setting.getFormulaSchemeId());
                formulaDefine2.setIsAutoExecute(true);
                formulaDefine2.setUseCalculate(true);
                formulaDefine2.setOrder(OrderGenerator.newOrder());
                formulaDefine2.setCode(this.getFormulaNewCode());
            }
        }
        return dataLinkOld;
    }

    private String getDataLinkCellPos(DataLinkSettingVO dataLink) {
        ArrayList<DesignDataLinkDefine> designDataLinkDefine = new ArrayList<DesignDataLinkDefine>();
        designDataLinkDefine.addAll(this.floatDataLinksNew);
        designDataLinkDefine.addAll(this.floatDataLinksUpdate);
        for (DesignDataLinkDefine dataLinkDefine : designDataLinkDefine) {
            if (!dataLinkDefine.getLinkExpression().equals(dataLink.getExpression())) continue;
            return dataLinkDefine.getRowNum() + "," + dataLinkDefine.getColNum();
        }
        FieldInfo field = this.getDesignFieldDefineById(dataLink.getExpression());
        return field == null ? "" : field.getCode();
    }

    private String getFormulaNewCode() {
        int num;
        if (StringUtils.isEmpty(this.maxFormulaCode) || this.maxFormulaCode.length() < 3) {
            this.maxFormulaCode = this.formDefine.getFormCode() + "001";
            return this.maxFormulaCode;
        }
        String prefix = this.maxFormulaCode.substring(0, this.maxFormulaCode.length() - 3);
        try {
            num = 1000 + Integer.valueOf(this.maxFormulaCode.substring(this.maxFormulaCode.length() - 3));
        }
        catch (NumberFormatException e) {
            num = 1000;
        }
        this.maxFormulaCode = prefix + (++num + "").substring(1, 4);
        return this.maxFormulaCode;
    }

    private void readFixedDataLinks() {
        String formSchemeKey = this.setting.getFormSetting().getFormSchemeKey();
        List allTable = this.designTimeViewController.getAllTableDefineInRegion(this.fixedDataRegionDefine.getKey());
        if (CollectionUtils.isEmpty(allTable)) {
            try {
                this.sumTable = new FixedTableGenerator().next(CodeGenerator.nextTableCode(DataRegionKind.DATA_REGION_SIMPLE, this.fixedDataRegionDefine.getRegionLeft(), this.fixedDataRegionDefine.getRegionTop()), formSchemeKey);
                this.isSumTableNew = true;
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u521b\u5efa\u56fa\u5b9a\u533a\u57df\u8868\u5931\u8d25", (Throwable)e);
            }
        } else {
            this.sumTable = this.designDataSchemeService.getDataTableByCode(((DesignTableDefine)allTable.get(0)).getCode());
        }
        this.sumDataLinks = this.designTimeViewController.getAllLinksInRegion(this.fixedDataRegionDefine.getKey());
        if (this.sumDataLinks == null) {
            this.sumDataLinks = new ArrayList<DesignDataLinkDefine>();
        }
    }

    private void readFormula() {
        ArrayList formulaDefines = null;
        try {
            formulaDefines = this.formulaDesignTimeController.getCalculateFormulasInForm(this.setting.getFormulaSchemeId(), this.setting.getFormSetting().getId());
        }
        catch (JQException e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u8868\u5355\u516c\u5f0f\u5931\u8d25\u3002", (Throwable)e);
        }
        if (formulaDefines == null) {
            formulaDefines = new ArrayList();
            return;
        }
        this.maxFormulaCode = formulaDefines.stream().map(FormulaDefine::getCode).max(String::compareTo).orElse("");
        String patternZBWithSum = "^\\[\\d+,\\d+\\]=(?i)(SUMHBZB)\\([0-9a-zA-Z\\_]+\\[[0-9a-zA-Z\\_,]{1,29}[,SUM]\\].*\\)$";
        this.formulaDefinesDel = formulaDefines.stream().filter(formulaDefine -> formulaDefine.getExpression().replace(" ", "").matches(patternZBWithSum)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(this.formulaDefinesDel)) {
            String patternHB = "^\\[\\d+,\\d+\\]=(?i)(SUMHB)\\(\\\"[0-9a-zA-Z\\_,\\[\\]]{1,29}\\\",\\\"\\\",\\\"[0-9a-zA-Z\\_]+\\\"\\)$";
            this.formulaDefinesDel = formulaDefines.stream().filter(formulaDefine -> formulaDefine.getExpression().replace(" ", "").matches(patternHB)).collect(Collectors.toList());
        }
    }

    private void setGridFloat() {
        this.readFloatDataLinks();
        int columnIndex = 2;
        int rowIndex = this.setting.getDataRegionSetting().getFloatBeginRowNum();
        this.grid.getGridCellData(1, rowIndex).setCellStyleData(FloatTemplate.getGridCellCenterStyleData());
        for (int linkIndex = 0; linkIndex < this.setting.getDataLinks().size(); ++linkIndex) {
            GridCellData cellData = this.grid.getGridCellData(columnIndex, rowIndex);
            this.buildFloatDataLink((DataLinkSettingVO)this.setting.getDataLinks().get(linkIndex), this.floatDataRegionDefine.getKey(), columnIndex, rowIndex, cellData);
            ++columnIndex;
        }
    }

    private void readFloatDataLinks() {
        this.floatDataLinksOld = this.toMap(this.designTimeViewController.getAllLinksInRegion(this.floatDataRegionDefine.getKey()));
    }

    private void buildFloatDataLink(DataLinkSettingVO dataLinkSetting, String regionId, int posX, int posY, GridCellData cellData) {
        DesignDataLinkDefine dataLink = this.floatDataLinksOld.get(dataLinkSetting.getExpression());
        boolean isNew = false;
        if (dataLink == null) {
            dataLink = this.designTimeViewController.createDataLinkDefine();
            dataLink.setUniqueCode(OrderGenerator.newOrder());
            dataLink.setOrder(OrderGenerator.newOrder());
            dataLink.setRegionKey(regionId);
            dataLink.setLinkExpression(dataLinkSetting.getExpression());
            isNew = true;
        }
        dataLink.setTitle(dataLinkSetting.getTitle());
        dataLink.setPosX(posX);
        dataLink.setPosY(posY);
        dataLink.setRowNum(posY);
        dataLink.setColNum(posX);
        dataLink.setDropDownFieldsString(this.getReferTableCodeAndTitleIds(dataLinkSetting.getExpression()));
        dataLink.setCaptionFieldsString(this.getReferTableCodeAndTitleIds(dataLinkSetting.getExpression()));
        this.floatDataLinksOld.put(dataLink.getKey(), dataLink);
        if (isNew) {
            this.floatDataLinksNew.add(dataLink);
        } else {
            this.floatDataLinksUpdate.add(dataLink);
        }
        if (this.isNumberType(dataLink.getLinkExpression(), posY)) {
            cellData.setCellStyleData(FloatTemplate.getGridCellRightStyleData());
        } else {
            cellData.setCellStyleData(FloatTemplate.getGridCellStyleData());
        }
        FieldInfo fieldInfo = this.getDesignFieldDefineById(dataLink.getLinkExpression());
        if (fieldInfo != null) {
            if ("ORGCODE".equals(fieldInfo.getCode()) || "OFFSETSTATE".equals(fieldInfo.getCode())) {
                cellData.setEditable(false);
            } else if ("OPPUNITID".equals(fieldInfo.getCode())) {
                dataLink.setIgnorePermissions(true);
            }
        }
        this.setSelectView(dataLink, dataLinkSetting);
    }

    private void setSelectView(DesignDataLinkDefine dataLink, DataLinkSettingVO dataLinkSetting) {
        FieldInfo field = this.getDesignFieldDefineById(dataLink.getLinkExpression());
        if (field == null || ObjectUtils.isEmpty(field.getRefDataEntityKey())) {
            dataLink.setFilterExpression("");
            return;
        }
        TableModelDefine referTable = this.nvwaDataModelCreateUtil.queryTableModel(field.getRefDataEntityKey());
        if (CollectionUtils.isEmpty(dataLinkSetting.getCanSelected()) || referTable == null) {
            this.selectedToFilter(referTable, dataLinkSetting.getCanSelected());
            dataLink.setFilterExpression("");
            return;
        }
        String filter = this.selectedToFilter(referTable, dataLinkSetting.getCanSelected());
        dataLink.setFilterExpression(filter);
    }

    private DesignTaskDefine getTaskDefine() {
        DesignFormSchemeDefine formSchemeDefine = this.getFormSchemeDefine();
        DesignTaskDefine taskDefine = this.designTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        if (taskDefine == null) {
            throw new BusinessRuntimeException("\u4efb\u52a1\u4e0d\u5b58\u5728\u3002");
        }
        return taskDefine;
    }

    private DesignFormSchemeDefine getFormSchemeDefine() {
        DesignFormGroupDefine formGroup = this.designTimeViewController.queryFormGroup(this.setting.getFormSetting().getFormGroupId());
        if (formGroup == null) {
            throw new BusinessRuntimeException("\u8868\u5355\u5206\u7ec4\u4e0d\u5b58\u5728\u3002");
        }
        DesignFormSchemeDefine formSchemeDefine = this.designTimeViewController.queryFormSchemeDefine(formGroup.getFormSchemeKey());
        if (formSchemeDefine == null) {
            throw new BusinessRuntimeException("\u62a5\u8868\u65b9\u6848\u4e0d\u5b58\u5728\u3002");
        }
        return formSchemeDefine;
    }

    private String selectedToFilter(TableModelDefine table, List<BaseDataVO> selected) {
        String codeCondition;
        String codeCondTem;
        StringBuilder filter = new StringBuilder();
        if (CollectionUtils.isEmpty(selected)) {
            if ("MD_GCSUBJECT".equals(table.getName())) {
                this.floatDataRegionDefine.setFilterCondition("");
            }
            return filter.toString();
        }
        List selectedCodeList = selected.stream().map(BaseDataVO::getCode).distinct().collect(Collectors.toList());
        HashSet<Object> subjectCodes = "MD_GCSUBJECT".equals(table.getName()) ? new HashSet(selectedCodeList) : new HashSet<String>(BaseDataUtils.getBaseDataCodeOnlyParent(selected, table.getName()));
        if (CollectionUtils.isEmpty(subjectCodes)) {
            return filter.toString();
        }
        HashSet<Object> finalSubjectCodes = subjectCodes;
        if ("MD_GCSUBJECT".equals(table.getName())) {
            codeCondTem = "[CODE] like \"%s%%\"";
            codeCondition = String.join((CharSequence)" OR ", (CharSequence[])subjectCodes.stream().map(subjectCode -> String.format(codeCondTem, subjectCode)).toArray(condition -> new String[finalSubjectCodes.size()]));
        } else {
            codeCondTem = "left([CODE],%d)=\"%s\"";
            codeCondition = String.join((CharSequence)" OR ", (CharSequence[])subjectCodes.stream().map(subjectCode -> String.format(codeCondTem, subjectCode.length(), subjectCode)).toArray(condition -> new String[finalSubjectCodes.size()]));
        }
        filter.append("(").append(codeCondition).append(")");
        String dataReginCondTem = this.inputDataTable.getCode() + "[" + "SUBJECTCODE" + "] like \"%s%%\"";
        String dataReginFilter = String.join((CharSequence)" OR ", (CharSequence[])subjectCodes.stream().map(subjectCode -> String.format(dataReginCondTem, subjectCode)).toArray(condition -> new String[finalSubjectCodes.size()]));
        if ("MD_GCSUBJECT".equals(table.getName())) {
            this.floatDataRegionDefine.setFilterCondition(dataReginFilter);
        }
        return filter.toString();
    }

    private String getReportSystemId() {
        DesignFormSchemeDefine formSchemeDefine = this.getFormSchemeDefine();
        List conTasks = this.conTaskServie.getConsolidatedTasksBySchemeId(formSchemeDefine.getKey());
        if (CollectionUtils.isEmpty(conTasks)) {
            throw new BusinessRuntimeException("\u65b9\u6848\u672a\u5173\u8054\u5408\u5e76\u4f53\u7cfb\uff0c\u8bf7\u5148\u5173\u8054\u5408\u5e76\u4f53\u7cfb\u518d\u4f7f\u7528\u6b64\u529f\u80fd\u3002");
        }
        return ((ConsolidatedTaskVO)conTasks.get(0)).getSystemId();
    }

    private String getReferTableCodeAndTitleIds(String expression) {
        FieldInfo fieldInfo = this.getDesignFieldDefineById(expression);
        if (fieldInfo == null) {
            return "";
        }
        if (ObjectUtils.isEmpty(fieldInfo.getRefDataEntityKey())) {
            return "";
        }
        TableModelDefine referTable = this.nvwaDataModelCreateUtil.queryTableModel(fieldInfo.getRefDataEntityKey());
        CharSequence[] codeAndTitleIds = this.getReferTableCodeAndTitleIdsByTableId(referTable);
        return String.join((CharSequence)";", codeAndTitleIds);
    }

    private String[] getReferTableCodeAndTitleIdsByTableId(TableModelDefine table) {
        List fields = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        List fieldDefineList = fields.stream().filter(FormSettingProcessor::isCodeOrTitle).sorted(FormSettingProcessor::codeBeforeTitleCompare).collect(Collectors.toList());
        return (String[])fieldDefineList.stream().map(IModelDefineItem::getCode).toArray(code -> new String[fieldDefineList.size()]);
    }

    private static boolean isCodeOrTitle(ColumnModelDefine field) {
        return FIELDNAME_CODE.equalsIgnoreCase(field.getCode()) || FIELDNAME_NAME.equalsIgnoreCase(field.getCode());
    }

    private static int codeBeforeTitleCompare(ColumnModelDefine field1, ColumnModelDefine field2) {
        return field1.getCode().toLowerCase().compareTo(field2.getCode().toLowerCase());
    }

    private Map<String, DesignDataLinkDefine> toMap(List<DesignDataLinkDefine> dataLinks) {
        HashMap<String, DesignDataLinkDefine> re = new HashMap<String, DesignDataLinkDefine>();
        if (CollectionUtils.isEmpty(dataLinks)) {
            return re;
        }
        dataLinks.forEach(dataLink -> re.put(dataLink.getLinkExpression(), (DesignDataLinkDefine)dataLink));
        return re;
    }

    private void saveSetting() {
        this.saveSumFields();
        this.updateFormDefine();
        this.saveRegion(this.fixedDataRegionDefine, this.setting.getDataRegionSetting().getFixedRegionId());
        this.saveRegion(this.floatDataRegionDefine, this.setting.getDataRegionSetting().getFloatRegionId());
        this.saveRegionSetting();
        this.saveDataLinks();
    }

    private void saveSettingFields() {
        if (CollectionUtils.isEmpty(this.setting.getFields())) {
            return;
        }
        try {
            this.designDataSchemeService.insertDataFields(this.setting.getFields().stream().map(fieldSetting -> {
                DesignDataField designDataField;
                try {
                    designDataField = this.designDataSchemeService.initDataField();
                }
                catch (Exception e) {
                    throw new BusinessRuntimeException("\u521b\u5efa\u5b57\u6bb5\u5931\u8d25\u3002");
                }
                designDataField.setKey(fieldSetting.getId());
                designDataField.setDataTableKey(this.inputDataTable.getKey());
                designDataField.setDataSchemeKey(this.inputDataTable.getDataSchemeKey());
                designDataField.setCode(fieldSetting.getCode());
                designDataField.setTitle(fieldSetting.getTitle());
                designDataField.setDataFieldKind(DataFieldKind.FIELD);
                DataFieldType dataFieldType = DataFieldType.valueOf((int)fieldSetting.getDataFieldType().getValue());
                designDataField.setDataFieldType(dataFieldType);
                if (this.isNumberType(fieldSetting.getDataFieldType().getValue())) {
                    designDataField.setDataFieldGatherType(DataFieldGatherType.SUM);
                }
                designDataField.setPrecision(fieldSetting.getSize());
                designDataField.setDecimal(fieldSetting.getFractionDigits());
                designDataField.setRefDataEntityKey(StringUtils.hasText(fieldSetting.getReferField()) ? fieldSetting.getReferField() : null);
                designDataField.setDesc(fieldSetting.getDescription());
                designDataField.setDefaultValue(fieldSetting.getDefaultValue());
                return designDataField;
            }).collect(Collectors.toList()));
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u4fdd\u5b58\u5b57\u6bb5\u5931\u8d25\u3002", (Throwable)e);
        }
    }

    private void saveSumFields() {
        if (this.isSumTableNew) {
            try {
                this.designDataSchemeService.insertDataTable(this.sumTable);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u4fdd\u5b58\u8868\u5931\u8d25\u3002", (Throwable)e);
            }
        }
        if (!CollectionUtils.isEmpty(this.sumFieldsNew)) {
            try {
                this.designDataSchemeService.insertDataFields(this.sumFieldsNew);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u4fdd\u5b58\u5b57\u6bb5\u5931\u8d25\u3002", (Throwable)e);
            }
        }
    }

    private void updateFormDefine() {
        this.formDefine.setBinaryData(Grid2Data.gridToBytes((Grid2Data)this.grid));
        this.formDefine.setUpdateTime(new Date());
        this.designTimeViewController.updateFormDefine(this.formDefine);
    }

    private void saveRegion(DesignDataRegionDefine region, String reginSettingId) {
        boolean existsFixedRegion = region.getKey().equals(reginSettingId != null ? reginSettingId.toString() : null);
        if (!existsFixedRegion) {
            this.designTimeViewController.insertDataRegionDefine(region);
        } else {
            this.designTimeViewController.updateDataRegionDefine(region);
        }
    }

    private void saveRegionSetting() {
        if (this.isNewReginSetting) {
            this.designTimeViewController.addRegionSetting(this.regionSettingDefine);
        } else {
            this.designTimeViewController.updateRegionSetting(this.regionSettingDefine);
        }
    }

    private void saveDataLinks() {
        DesignDataLinkDefine[] dataLinksUpdate;
        DesignDataLinkDefine[] dataLinksNew;
        List designDataLinkDefinesOld = this.designTimeViewController.getAllLinksInRegion(this.floatDataRegionDefine.getKey());
        List designDataLinkDefinesFixedOld = this.designTimeViewController.getAllLinksInRegion(this.fixedDataRegionDefine.getKey());
        ArrayList<String> defineOldList = new ArrayList<String>();
        ArrayList<String> defineUpdateList = new ArrayList<String>();
        ArrayList<String> defineDeleteList = new ArrayList<String>();
        if (designDataLinkDefinesOld.size() > 0) {
            for (DesignDataLinkDefine defineOld : designDataLinkDefinesOld) {
                defineOldList.add(defineOld.getKey());
            }
        }
        if (this.floatDataLinksUpdate.size() > 0) {
            for (DesignDataLinkDefine defineUpdate : this.floatDataLinksUpdate) {
                defineUpdateList.add(defineUpdate.getKey());
            }
        }
        for (String key : defineOldList) {
            if (defineUpdateList.contains(key)) continue;
            defineDeleteList.add(key);
        }
        ArrayList<String> defineFixOldList = new ArrayList<String>();
        ArrayList<String> defineFixUpdateList = new ArrayList<String>();
        if (designDataLinkDefinesFixedOld.size() > 0) {
            for (DesignDataLinkDefine defineOld : designDataLinkDefinesFixedOld) {
                defineFixOldList.add(defineOld.getKey());
            }
        }
        if (this.sumDataLinksUpdate.size() > 0) {
            for (DesignDataLinkDefine defineUpdate : this.sumDataLinksUpdate) {
                defineFixUpdateList.add(defineUpdate.getKey());
            }
        }
        for (String key : defineFixOldList) {
            if (defineFixUpdateList.contains(key)) continue;
            defineDeleteList.add(key);
        }
        if (defineDeleteList.size() > 0) {
            this.designTimeViewController.deleteDataLinkDefines(defineDeleteList.toArray(new String[defineDeleteList.size()]));
        }
        if ((dataLinksNew = (DesignDataLinkDefine[])Stream.concat(this.floatDataLinksNew.stream(), this.sumDataLinksNew.stream()).toArray(dataLink -> new DesignDataLinkDefine[this.floatDataLinksNew.size() + this.sumDataLinksNew.size()])).length > 0) {
            this.designTimeViewController.insertDataLinkDefines(dataLinksNew);
        }
        if ((dataLinksUpdate = (DesignDataLinkDefine[])Stream.concat(this.floatDataLinksUpdate.stream(), this.sumDataLinksUpdate.stream()).toArray(dataLink -> new DesignDataLinkDefine[this.floatDataLinksUpdate.size() + this.sumDataLinksUpdate.size()])).length > 0) {
            this.designTimeViewController.updateDataLinkDefines(dataLinksUpdate);
        }
    }

    public void updateFormulas() {
        if (!CollectionUtils.isEmpty(this.formulaDefinesDel)) {
            this.formulaDesignTimeController.deleteFormulaDefines((String[])this.formulaDefinesDel.stream().map(IBaseMetaItem::getKey).toArray(key -> new String[this.formulaDefinesDel.size()]));
        }
        if (!CollectionUtils.isEmpty(this.formulaDefinesUpdate)) {
            try {
                this.formulaDesignTimeController.updateFormulaDefines(this.formulaDefinesUpdate.toArray(new DesignFormulaDefine[0]));
            }
            catch (JQException e) {
                throw new BusinessRuntimeException("\u516c\u5f0f\u66f4\u65b0\u51fa\u73b0\u5f02\u5e38\u3002", (Throwable)e);
            }
        }
        if (!CollectionUtils.isEmpty(this.formulaDefinesNew)) {
            try {
                this.formulaDesignTimeController.insertFormulaDefines(this.formulaDefinesNew.toArray(new DesignFormulaDefine[0]));
            }
            catch (JQException e) {
                throw new BusinessRuntimeException("\u516c\u5f0f\u521b\u5efa\u51fa\u73b0\u5f02\u5e38\u3002", (Throwable)e);
            }
        }
    }

    private void initDesignDataTable() {
        DesignFormSchemeDefine queryFormScheme = this.designTimeViewController.queryFormSchemeDefine(this.setting.getFormSetting().getFormSchemeKey());
        String dataTableKey = this.inputDataNameProvider.getDataTableKeyByTaskId(queryFormScheme.getTaskKey());
        this.inputDataTable = this.designDataSchemeService.getDataTable(dataTableKey);
    }

    private static interface FieldInfo {
        public String getCode();

        public FieldType getType();

        public FieldValueType getValueType();

        public int getSize();

        public String getTitle();

        public String getRefDataEntityKey();

        public FieldGatherType getGatherType();

        public DataFieldType getDataFieldType();
    }
}

