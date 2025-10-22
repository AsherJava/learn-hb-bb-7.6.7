/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.data.access.param.SecretLevel
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.data.access.param.SecretLevel;
import com.jiuqi.nr.data.excel.config.ConfigProperties;
import com.jiuqi.nr.data.excel.obj.DimensionData;
import com.jiuqi.nr.data.excel.param.TitleShowSetting;
import com.jiuqi.nr.data.excel.service.internal.IExportOptionsService;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExportOptionServiceImpl
implements IExportOptionsService {
    @Autowired
    private INvwaSystemOptionService systemOptionService;
    @Autowired
    private ConfigProperties configProperties;
    @Autowired
    private ITaskOptionController taskOptionController;
    private static final String SEPARATOR_ONE = " ";
    private static final String SEPARATOR_TWO = "_";
    private static final String SEPARATOR_THREE = "&";
    private static final String EXCEL_FORMULA_CONDITION = "EXCELFORMULA_CONDITION_123";

    @Override
    public String getFormShow(TitleShowSetting titleShowSetting, FormDefine formDefine) {
        StringBuilder show = new StringBuilder();
        String sheetNameOp = titleShowSetting != null && titleShowSetting.getFormShowSetting() != null ? titleShowSetting.getFormShowSetting() : this.systemOptionService.get("nr-data-entry-export", "SHEET_NAME");
        String[] sheetNameCom = sheetNameOp.split("[\\[\\]\",\\s]");
        String sysSeparator = this.getSysSeparator(titleShowSetting);
        for (String op : sheetNameCom) {
            if ("0".equals(op)) {
                show.append(formDefine.getTitle()).append(sysSeparator);
                continue;
            }
            if ("1".equals(op)) {
                show.append(formDefine.getFormCode()).append(sysSeparator);
                continue;
            }
            if (!"2".equals(op) || !org.springframework.util.StringUtils.hasText(formDefine.getSerialNumber())) continue;
            show.append(formDefine.getSerialNumber()).append(sysSeparator);
        }
        if (!org.springframework.util.StringUtils.hasText(show)) {
            show.append(formDefine.getFormCode()).append(sysSeparator).append(formDefine.getTitle()).append(sysSeparator);
        }
        show.setLength(show.length() - sysSeparator.length());
        return show.toString();
    }

    @Override
    public String getDWShow(TitleShowSetting titleShowSetting, DimensionData dimensionData, String periodTitle, String taskName, SecretLevel secretLevel) {
        StringBuilder show = new StringBuilder();
        String excelNameOp = titleShowSetting != null && titleShowSetting.getDwShowSetting() != null ? titleShowSetting.getDwShowSetting() : this.systemOptionService.get("nr-data-entry-export", "EXCEL_NAME");
        String[] excelNameCom = excelNameOp.split("[\\[\\]\",\\s]");
        String sysSeparator = this.getSysSeparator(titleShowSetting);
        for (String op : excelNameCom) {
            if ("0".equals(op)) {
                show.append(dimensionData.getTitle()).append(sysSeparator);
                continue;
            }
            if ("1".equals(op)) {
                show.append(dimensionData.getCode()).append(sysSeparator);
                continue;
            }
            if ("2".equals(op)) {
                show.append(periodTitle).append(sysSeparator);
                continue;
            }
            if (!"3".equals(op)) continue;
            show.append(taskName).append(sysSeparator);
        }
        if (!org.springframework.util.StringUtils.hasText(show)) {
            show.append(dimensionData.getTitle()).append(sysSeparator);
        }
        if (secretLevel != null) {
            show.append(secretLevel.getTitle()).append(sysSeparator);
        }
        show.setLength(show.length() - sysSeparator.length());
        return show.toString();
    }

    @Override
    public String getDWShow(TitleShowSetting titleShowSetting, DimensionData dimensionData, String periodTitle, String taskName) {
        return this.getDWShow(titleShowSetting, dimensionData, periodTitle, taskName, null);
    }

    @Override
    public String getSysSeparator(TitleShowSetting titleShowSetting) {
        String separatorMessage = titleShowSetting != null && titleShowSetting.getSplitCharSetting() != null ? titleShowSetting.getSplitCharSetting() : this.systemOptionService.get("nr-data-entry-export", "SEPARATOR_CODE");
        String separator = SEPARATOR_ONE;
        if (separatorMessage.equals("1")) {
            separator = SEPARATOR_TWO;
        } else if (separatorMessage.equals("2")) {
            separator = SEPARATOR_THREE;
        }
        return separator;
    }

    @Override
    public int getMaxUnitNum() {
        String s = this.systemOptionService.get("nr-data-entry-export", "BATCH_EXPORT_MAX_DW_NUM");
        if (org.springframework.util.StringUtils.hasText(s)) {
            return Integer.parseInt(s);
        }
        return -1;
    }

    @Override
    public boolean autoFillIsNullTable() {
        return "1".equals(this.systemOptionService.get("nr-data-entry-export", "EXTENT_GRID_IS_NULL_TABLE"));
    }

    @Override
    public boolean simplifyExpFileHierarchy(TitleShowSetting titleShowSetting) {
        if (titleShowSetting != null && titleShowSetting.getSimplifyExpFileHierarchy() != null) {
            return titleShowSetting.getSimplifyExpFileHierarchy();
        }
        return "1".equals(this.systemOptionService.get("nr-data-entry-export", "SIMPLIFY_EXPORT_FILE_HIERARCHY"));
    }

    @Override
    public String getZeroShow(String taskKey) {
        String value = this.taskOptionController.getValue(taskKey, "NUMBER_ZERO_SHOW");
        return value == null ? "" : value;
    }

    @Override
    public Integer getMeasureChangeDefaultDecimal(String taskKey) {
        String value = this.taskOptionController.getValue(taskKey, "DEFAULT_DECIMAL");
        if (StringUtils.isNotEmpty((String)value) && StringUtils.isNumeric((String)value)) {
            int parseInt = Integer.parseInt(value);
            if (parseInt < 0) {
                return null;
            }
            return parseInt;
        }
        return null;
    }

    @Override
    public boolean expExcelFormula(String taskKey) {
        String value = this.taskOptionController.getValue(taskKey, EXCEL_FORMULA_CONDITION);
        return "1".equals(value);
    }

    @Override
    public int getMemPerfLevel() {
        int parseInt;
        String memLevel = this.systemOptionService.get("nr-data-entry-export", "@nr/excel/EXPORT_EXCEL_MEM_LEVEL");
        if (org.springframework.util.StringUtils.hasText(memLevel) && (parseInt = Integer.parseInt(memLevel)) >= 0 && parseInt <= 10) {
            return parseInt;
        }
        return 1;
    }

    @Override
    public int getSheetSplitMaxNum() {
        if (this.configProperties == null) {
            return 11;
        }
        return this.configProperties.getExpSheetSplitMax();
    }

    @Override
    public int getSheetFloatMax() {
        if (this.configProperties == null) {
            return 100000;
        }
        return this.configProperties.getExpSheetFloatMax();
    }

    @Override
    public int getQueryPageLimit() {
        if (this.configProperties == null) {
            return 20000;
        }
        return this.configProperties.getExpQueryPageLimit();
    }
}

