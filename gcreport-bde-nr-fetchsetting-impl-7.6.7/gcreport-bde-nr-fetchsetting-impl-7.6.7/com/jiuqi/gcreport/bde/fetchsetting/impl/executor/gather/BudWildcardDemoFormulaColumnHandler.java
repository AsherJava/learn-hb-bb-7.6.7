/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.enums.ColumnAlignment
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.gather;

import com.jiuqi.dc.base.common.enums.ColumnAlignment;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.ImpExpInnerColumnHandler;
import org.springframework.util.StringUtils;

public class BudWildcardDemoFormulaColumnHandler
implements ImpExpInnerColumnHandler {
    @Override
    public String getKey() {
        return "fieldDefineCode";
    }

    @Override
    public String[] getLabel() {
        return new String[]{"\u6307\u6807\u4fe1\u606f", "\u6307\u6807\u4ee3\u7801"};
    }

    @Override
    public ColumnAlignment getAlign() {
        return ColumnAlignment.RIGHT;
    }

    @Override
    public Integer getWidth() {
        return 200;
    }

    @Override
    public Object getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
        if (StringUtils.hasText(excelRowFetchSettingVO.getWildcardFormula())) {
            return excelRowFetchSettingVO.getWildcardFormula();
        }
        return excelRowFetchSettingVO.getFieldDefineCode();
    }

    @Override
    public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
        excelRowFetchSettingVO.setFieldDefineCode(value);
    }
}

