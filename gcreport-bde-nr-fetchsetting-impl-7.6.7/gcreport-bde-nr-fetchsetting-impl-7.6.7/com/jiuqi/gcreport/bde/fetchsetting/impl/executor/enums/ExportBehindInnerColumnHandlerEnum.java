/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.ColumnAlignment
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.enums;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.enums.ColumnAlignment;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.ImpExpInnerColumnHandler;

public enum ExportBehindInnerColumnHandlerEnum implements ImpExpInnerColumnHandler
{
    ACCTYEAR("acctYear", new String[]{"\u6307\u5b9a\u5e74\u5ea6", "\u6307\u5b9a\u5e74\u5ea6"}, ColumnAlignment.RIGHT, 120){

        @Override
        public Object getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getAcctYear();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setAcctYear(value);
        }
    }
    ,
    ACCTPERIOD("acctPeriod", new String[]{"\u6307\u5b9a\u671f\u95f4", "\u6307\u5b9a\u671f\u95f4"}, ColumnAlignment.RIGHT, 120){

        @Override
        public Object getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getAcctPeriod();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setAcctPeriod(value);
        }
    }
    ,
    CURRENCYCODE("currencyCode", new String[]{"\u6307\u5b9a\u5e01\u79cd", "\u6307\u5b9a\u5e01\u79cd"}, ColumnAlignment.RIGHT, 120){

        @Override
        public Object getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getCurrencyCode();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setCurrencyCode(value);
        }
    }
    ,
    ORGCODE("orgCode", new String[]{"\u6307\u5b9a\u5355\u4f4d", "\u6307\u5b9a\u5355\u4f4d"}, ColumnAlignment.RIGHT, 120){

        @Override
        public Object getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getOrgCode();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setOrgCode(value);
        }
    }
    ,
    DATASOURCECODE("dataSourceCode", new String[]{"\u6307\u5b9a\u6570\u636e\u6e90", "\u6307\u5b9a\u6570\u636e\u6e90"}, ColumnAlignment.RIGHT, 120){

        @Override
        public Object getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getDataSourceCode();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setDataSourceCode(value);
        }
    }
    ,
    FORMULA("formula", new String[]{"\u53d6\u6570SQL", "\u53d6\u6570SQL"}, ColumnAlignment.RIGHT, 120){

        @Override
        public Object getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getFormula();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setFormula(value);
        }
    }
    ,
    AGINGGROUP("agingGroup", new String[]{"\u591a\u7ef4\u8d26\u9f84", "\u8d26\u9f84\u6bb5"}, ColumnAlignment.RIGHT, 120){

        @Override
        public Object getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getAgingGroupName();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setAgingGroupName(value);
        }
    }
    ,
    AGINGRANGETYPENAME("agingRangeTypeName", new String[]{"\u5f80\u6765\u8d26\u9f84", "\u533a\u95f4\u7c7b\u578b"}, ColumnAlignment.RIGHT, 120){

        @Override
        public Object getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getAgingRangeTypeName();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setAgingRangeTypeName(value);
        }
    }
    ,
    AGINGRANGESTART("agingRangeStart", new String[]{"\u5f80\u6765\u8d26\u9f84", "\u8d77\u59cb\u533a\u95f4"}, ColumnAlignment.RIGHT, 120){

        @Override
        public Object getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getAgingRangeStart();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            if (!StringUtils.isEmpty((String)value)) {
                excelRowFetchSettingVO.setAgingRangeStart(Integer.valueOf(value));
            }
        }
    }
    ,
    AGINGRANGEEND("agingRangeEnd", new String[]{"\u5f80\u6765\u8d26\u9f84", "\u622a\u6b62\u533a\u95f4"}, ColumnAlignment.RIGHT, 120){

        @Override
        public Object getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getAgingRangeEnd();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            if (!StringUtils.isEmpty((String)value)) {
                excelRowFetchSettingVO.setAgingRangeEnd(Integer.valueOf(value));
            }
        }
    }
    ,
    RECLASSSUBJCODE("reclassSubjCode", new String[]{"\u5230\u671f\u65e5\u91cd\u5206\u7c7b", "\u91cd\u5206\u7c7b\u540e\u79d1\u76ee"}, ColumnAlignment.RIGHT, 120){

        @Override
        public Object getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getReclassSubjCode();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setReclassSubjCode(value);
        }
    }
    ,
    RECLASSSRCSUBJCODE("reclassSrcSubjCode", new String[]{"\u5230\u671f\u65e5\u91cd\u5206\u7c7b", "\u91cd\u5206\u7c7b\u524d\u79d1\u76ee"}, ColumnAlignment.RIGHT, 120){

        @Override
        public Object getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getReclassSrcSubjCode();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setReclassSrcSubjCode(value);
        }
    }
    ,
    BASEDATACODE("baseDataCode", new String[]{"\u57fa\u7840\u6570\u636e\u4ee3\u7801", "\u57fa\u7840\u6570\u636e\u4ee3\u7801"}, ColumnAlignment.RIGHT, 120){

        @Override
        public Object getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getBaseDataCode();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setBaseDataCode(value);
        }
    }
    ,
    CUSTOMFETCH("customFetch", new String[]{"\u81ea\u5b9a\u4e49\u53d6\u6570", "\u81ea\u5b9a\u4e49\u53d6\u6570"}, ColumnAlignment.RIGHT, 120){

        @Override
        public Object getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getCustomFetch();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setCustomFetch(value);
        }
    }
    ,
    LOGICFORMULA("logicFormula", new String[]{"\u903b\u8f91\u8868\u8fbe\u5f0f", "\u903b\u8f91\u8868\u8fbe\u5f0f"}, ColumnAlignment.RIGHT, 120){

        @Override
        public Object getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getLogicFormula();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setLogicFormula(value);
        }
    }
    ,
    ADAPTFORMULA("adaptFormula", new String[]{"\u9002\u5e94\u6761\u4ef6", "\u9002\u5e94\u6761\u4ef6"}, ColumnAlignment.RIGHT, 120){

        @Override
        public Object getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getAdaptFormula();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setAdaptFormula(value);
        }
    }
    ,
    DESCRIPTION("description", new String[]{"\u89c4\u5219\u542b\u4e49", "\u89c4\u5219\u542b\u4e49"}, ColumnAlignment.RIGHT, 120){

        @Override
        public Object getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getDescription();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setDescription(value);
        }
    };

    private final String key;
    private final String[] label;
    private final ColumnAlignment align;
    private final Integer width;

    private ExportBehindInnerColumnHandlerEnum(String key, String[] label, ColumnAlignment align, Integer width) {
        this.key = key;
        this.label = label;
        this.align = align;
        this.width = width;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String[] getLabel() {
        return this.label;
    }

    @Override
    public ColumnAlignment getAlign() {
        return this.align;
    }

    @Override
    public Integer getWidth() {
        return this.width;
    }
}

