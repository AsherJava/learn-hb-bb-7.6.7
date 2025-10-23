/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.quickreport.model.ValueConvertMode
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem
 *  com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator
 *  com.jiuqi.nvwa.systemoption.util.SystemOptionConst$EditMode
 */
package com.jiuqi.nr.zbquery.systemoption;

import com.jiuqi.bi.quickreport.model.ValueConvertMode;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionDeclare;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionOperator;
import com.jiuqi.nvwa.systemoption.extend.ISystemOptionalValue;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionItem;
import com.jiuqi.nvwa.systemoption.extend.impl.SystemOptionOperator;
import com.jiuqi.nvwa.systemoption.util.SystemOptionConst;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZbQuerySystemOptionDeclare
implements ISystemOptionDeclare {
    public static final String ID = "NrZbQuerySystemOption";
    private static final String PATTERN = "^(?:[1-9]|[1-9][0-9]|[1-4][0-9]{2}|500)$";
    public static final String COLUMN_WIDTH_MAIN_DIMENSION = "COLUMN_WIDTH_MAIN_DIMENSION";
    public static final String COLUMN_WIDTH_MAIN_DIMENSION_TITLE = "\u4e3b\u7ef4\u5ea6\u6807\u9898\u5217\u5bbd\uff08px\uff09";
    public static final String COLUMN_WIDTH_DIMENSION = "COLUMN_WIDTH_DIMENSION";
    public static final String COLUMN_WIDTH_DIMENSION_TITLE = "\u7ef4\u5ea6\u5217\u5bbd\uff08px\uff09";
    public static final String COLUMN_WIDTH_ZB = "COLUMN_WIDTH_ZB";
    public static final String COLUMN_WIDTH_ZB_TITLE = "\u6307\u6807\u5217\u5bbd \uff08px\uff09";
    public static final String QUERY_DETAIL_RECODE = "QUERY_DETAIL_RECODE";
    public static final String QUERY_DETAIL_RECODE_TITLE = "\u67e5\u8be2\u660e\u7ec6\u8bb0\u5f55";
    public static final String BOLD_SUM_NODE = "BOLD_SUM_NODE";
    public static final String BOLD_SUM_NODE_TITLE = "\u6c47\u603b\u5355\u4f4d\u52a0\u7c97";
    public static final String ENABLE_FAVORITES = "ENABLE_FAVORITES";
    public static final String ENABLE_FAVORITES_TITLE = "\u542f\u7528\u6536\u85cf";
    public static final String ENABLE_QUICK_TOOLBAR = "ENABLE_QUICK_TOOLBAR";
    public static final String ENABLE_QUICK_TOOLBAR_TITLE = "\u542f\u7528\u5feb\u901f\u5de5\u5177\u680f";
    public static final String DISPLAY_TIERED = "DISPLAY_TIERED";
    public static final String DISPLAY_TIERED_TITLE = "\u5206\u7ea7\u663e\u793a";
    public static final String ZERO_DISPLAY_MODE = "ZERO_Display_MODE";
    public static final String ZERO_DISPLAY_MODE_TITLE = "\u96f6\u503c\u663e\u793a\u6a21\u5f0f";
    public static final String NULL_DISPLAY_MODE = "NULL_Display_MODE";
    public static final String NULL_DISPLAY_MODE_TITLE = "\u7a7a\u503c\u663e\u793a\u6a21\u5f0f";
    public static final String SIMPLEEXPORT_VALUE = "ZBQUERY_SIMPLEEXPORT_VALUE";
    public static final String SIMPLEEXPORT_VALUE_TITLE = "\u65e0\u8868\u6837\u5bfc\u51fa\u63a7\u5236\u9608\u503c(\u4e07)";
    @Autowired
    private SystemOptionOperator systemOptionOperator;

    public String getId() {
        return ID;
    }

    public String getTitle() {
        return "\u7efc\u5408\u67e5\u8be2";
    }

    public String getNameSpace() {
        return "\u6570\u636e\u5206\u6790";
    }

    public List<ISystemOptionItem> getOptionItems() {
        ArrayList<ISystemOptionItem> items = new ArrayList<ISystemOptionItem>();
        SystemOptionItem fieldWidthGroup = new SystemOptionItem();
        fieldWidthGroup.setEditMode(SystemOptionConst.EditMode.GROUP);
        fieldWidthGroup.setTitle("\u5217\u5bbd\u7ba1\u7406");
        fieldWidthGroup.setId("ZBQUERY_COL_WIDTH_GROUP");
        items.add((ISystemOptionItem)fieldWidthGroup);
        this.getColumnWidthSQItems(items);
        SystemOptionItem defaultSettingGroup = new SystemOptionItem();
        defaultSettingGroup.setEditMode(SystemOptionConst.EditMode.GROUP);
        defaultSettingGroup.setTitle("\u9ed8\u8ba4\u8bbe\u7f6e");
        defaultSettingGroup.setId("DEFAULT_SETTING");
        items.add((ISystemOptionItem)defaultSettingGroup);
        this.getDefaultSettingSQItems(items);
        return items;
    }

    private void getDefaultSettingSQItems(List<ISystemOptionItem> items) {
        SystemOptionItem queryDetailRecord = new SystemOptionItem();
        queryDetailRecord.setDefaultValue("1");
        queryDetailRecord.setId(QUERY_DETAIL_RECODE);
        queryDetailRecord.setTitle(QUERY_DETAIL_RECODE_TITLE);
        queryDetailRecord.setEditMode(SystemOptionConst.EditMode.TRUE_FALSE);
        items.add((ISystemOptionItem)queryDetailRecord);
        SystemOptionItem boldSumNode = new SystemOptionItem();
        boldSumNode.setDefaultValue("0");
        boldSumNode.setId(BOLD_SUM_NODE);
        boldSumNode.setTitle(BOLD_SUM_NODE_TITLE);
        boldSumNode.setEditMode(SystemOptionConst.EditMode.TRUE_FALSE);
        items.add((ISystemOptionItem)boldSumNode);
        SystemOptionItem enableFavorites = new SystemOptionItem();
        enableFavorites.setDefaultValue("0");
        enableFavorites.setId(ENABLE_FAVORITES);
        enableFavorites.setTitle(ENABLE_FAVORITES_TITLE);
        enableFavorites.setEditMode(SystemOptionConst.EditMode.TRUE_FALSE);
        items.add((ISystemOptionItem)enableFavorites);
        SystemOptionItem displayTiered = new SystemOptionItem();
        displayTiered.setDefaultValue("0");
        displayTiered.setId(DISPLAY_TIERED);
        displayTiered.setTitle(DISPLAY_TIERED_TITLE);
        displayTiered.setEditMode(SystemOptionConst.EditMode.TRUE_FALSE);
        items.add((ISystemOptionItem)displayTiered);
        items.add(this.enableQuickToolbar());
        this.setCellDisplayModeOption(items);
        SystemOptionItem simpleExport = new SystemOptionItem();
        simpleExport.setDefaultValue("1000");
        simpleExport.setId(SIMPLEEXPORT_VALUE);
        simpleExport.setTitle(SIMPLEEXPORT_VALUE_TITLE);
        simpleExport.setDescribe("\u67e5\u8be2\u7ed3\u679c\u7684\u5355\u5143\u683c\u603b\u6570\u8d85\u8fc7\u7ed9\u5b9a\u9608\u503c\u65f6\uff0c\u542f\u7528\u65e0\u8868\u6837\u5bfc\u51fa\u6a21\u5f0f\u4f18\u5316\u6548\u7387\uff1b\u4ea4\u53c9\u8868\u6837\u3001\u6307\u6807\u7eb5\u5411\u7f57\u5217\u7b49\u573a\u666f\u4e0d\u652f\u6301\u65e0\u8868\u6837\u5bfc\u51fa\u3002");
        simpleExport.setEditMode(SystemOptionConst.EditMode.NUMBER_INPUT);
        items.add((ISystemOptionItem)simpleExport);
    }

    private ISystemOptionItem enableQuickToolbar() {
        SystemOptionItem enableQuickToolbar = new SystemOptionItem();
        enableQuickToolbar.setDefaultValue("0");
        enableQuickToolbar.setId(ENABLE_QUICK_TOOLBAR);
        enableQuickToolbar.setTitle(ENABLE_QUICK_TOOLBAR_TITLE);
        enableQuickToolbar.setEditMode(SystemOptionConst.EditMode.TRUE_FALSE);
        return enableQuickToolbar;
    }

    private void setCellDisplayModeOption(List<ISystemOptionItem> items) {
        SystemOptionItem zeroDisplayMode = new SystemOptionItem();
        zeroDisplayMode.setEditMode(SystemOptionConst.EditMode.DROP_DOWN_SINGLE);
        zeroDisplayMode.setId(ZERO_DISPLAY_MODE);
        zeroDisplayMode.setTitle(ZERO_DISPLAY_MODE_TITLE);
        ArrayList<1> zero_optionalValue = new ArrayList<1>();
        SystemOptionItem nullDisplayMode = new SystemOptionItem();
        nullDisplayMode.setEditMode(SystemOptionConst.EditMode.DROP_DOWN_SINGLE);
        nullDisplayMode.setId(NULL_DISPLAY_MODE);
        nullDisplayMode.setTitle(NULL_DISPLAY_MODE_TITLE);
        ArrayList<2> null_optionalValue = new ArrayList<2>();
        Map<String, String> valueConvertModeMap = this.valueConvertModeToMap();
        for (final Map.Entry<String, String> entry : valueConvertModeMap.entrySet()) {
            if (!entry.getKey().equals(ValueConvertMode.ASZERO.toString())) {
                zero_optionalValue.add(new ISystemOptionalValue(){

                    public String getValue() {
                        return (String)entry.getKey();
                    }

                    public String getTitle() {
                        return (String)entry.getValue();
                    }
                });
            }
            if (entry.getKey().equals(ValueConvertMode.ASNULL.toString())) continue;
            null_optionalValue.add(new ISystemOptionalValue(){

                public String getValue() {
                    return (String)entry.getKey();
                }

                public String getTitle() {
                    return (String)entry.getValue();
                }
            });
        }
        zeroDisplayMode.setOptionalValues(zero_optionalValue);
        zeroDisplayMode.setDefaultValue(ValueConvertMode.NONE.toString());
        nullDisplayMode.setOptionalValues(null_optionalValue);
        nullDisplayMode.setDefaultValue(ValueConvertMode.NONE.toString());
        items.add((ISystemOptionItem)nullDisplayMode);
        items.add((ISystemOptionItem)zeroDisplayMode);
    }

    private Map<String, String> valueConvertModeToMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put(ValueConvertMode.NONE.toString(), "\u9ed8\u8ba4");
        map.put(ValueConvertMode.ASZERO.toString(), "\u663e\u793a\u4e3a0");
        map.put(ValueConvertMode.ASNULL.toString(), "\u663e\u793a\u4e3a\u7a7a\u503c");
        map.put(ValueConvertMode.ASBLANK.toString(), "\u663e\u793a\u4e3a\u6a2a\u7ebf");
        return map;
    }

    private void getColumnWidthSQItems(List<ISystemOptionItem> items) {
        SystemOptionItem mainDimension = new SystemOptionItem();
        mainDimension.setDefaultValue("270");
        mainDimension.setId(COLUMN_WIDTH_MAIN_DIMENSION);
        mainDimension.setTitle(COLUMN_WIDTH_MAIN_DIMENSION_TITLE);
        mainDimension.setEditMode(SystemOptionConst.EditMode.INPUT);
        mainDimension.setVerifyRegex(PATTERN);
        items.add((ISystemOptionItem)mainDimension);
        SystemOptionItem dimeinsion = new SystemOptionItem();
        dimeinsion.setDefaultValue("130");
        dimeinsion.setId(COLUMN_WIDTH_DIMENSION);
        dimeinsion.setTitle(COLUMN_WIDTH_DIMENSION_TITLE);
        dimeinsion.setEditMode(SystemOptionConst.EditMode.INPUT);
        dimeinsion.setVerifyRegex(PATTERN);
        items.add((ISystemOptionItem)dimeinsion);
        SystemOptionItem zbField = new SystemOptionItem();
        zbField.setDefaultValue("130");
        zbField.setId(COLUMN_WIDTH_ZB);
        zbField.setTitle(COLUMN_WIDTH_ZB_TITLE);
        zbField.setEditMode(SystemOptionConst.EditMode.INPUT);
        zbField.setVerifyRegex(PATTERN);
        items.add((ISystemOptionItem)zbField);
    }

    public ISystemOptionOperator getSystemOptionOperator() {
        return this.systemOptionOperator;
    }
}

