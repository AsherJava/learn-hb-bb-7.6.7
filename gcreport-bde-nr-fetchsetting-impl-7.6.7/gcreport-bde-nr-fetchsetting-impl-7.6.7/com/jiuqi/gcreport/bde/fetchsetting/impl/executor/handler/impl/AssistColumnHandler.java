/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.MatchRuleEnum
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.ColumnAlignment
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.impl;

import com.jiuqi.bde.common.constant.MatchRuleEnum;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.enums.ColumnAlignment;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.ImpExpInnerColumnHandler;

public class AssistColumnHandler
implements ImpExpInnerColumnHandler {
    private String key;
    private String[] label;
    private ColumnAlignment align;
    private Integer width;
    private String matchValue;

    public AssistColumnHandler(String key, String[] label, ColumnAlignment align, Integer width, String matchValue) {
        this.key = key;
        this.label = label;
        this.align = align;
        this.width = width;
        this.matchValue = StringUtils.isEmpty((String)matchValue) ? MatchRuleEnum.LIKE.getCode() : matchValue;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String[] getLabel() {
        return this.label;
    }

    public void setLabel(String[] label) {
        this.label = label;
    }

    @Override
    public ColumnAlignment getAlign() {
        return this.align;
    }

    public void setAlign(ColumnAlignment align) {
        this.align = align;
    }

    @Override
    public Integer getWidth() {
        return this.width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Override
    public String getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
        return excelRowFetchSettingVO.getDimSettingValueMap().get(this.key) == null ? "" : excelRowFetchSettingVO.getDimSettingValueMap().get(this.key).getDimValue();
    }

    @Override
    public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
        if (StringUtils.isEmpty((String)value)) {
            return;
        }
        Dimension dimension = new Dimension();
        dimension.setDimCode(this.key);
        dimension.setDimRule(this.matchValue);
        dimension.setDimValue(value);
        excelRowFetchSettingVO.getDimSettingValueMap().put(this.key, dimension);
    }
}

