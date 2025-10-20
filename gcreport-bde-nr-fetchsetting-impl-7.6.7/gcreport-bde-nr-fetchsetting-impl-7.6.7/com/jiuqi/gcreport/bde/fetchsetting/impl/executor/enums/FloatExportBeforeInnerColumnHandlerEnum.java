/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.enums.ColumnAlignment
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.enums;

import com.jiuqi.dc.base.common.enums.ColumnAlignment;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.ImpExpInnerColumnHandler;

public enum FloatExportBeforeInnerColumnHandlerEnum implements ImpExpInnerColumnHandler
{
    FIELDDEFINECODE("fieldDefineCode", new String[]{"\u6307\u6807\u4fe1\u606f", "\u6307\u6807\u4ee3\u7801"}, ColumnAlignment.RIGHT, 120){

        @Override
        public String getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getFieldDefineCode();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setFieldDefineCode(value);
        }
    }
    ,
    FIELDDEFINETITLE("fieldDefineTitle", new String[]{"\u6307\u6807\u4fe1\u606f", "\u6307\u6807\u540d\u79f0"}, ColumnAlignment.RIGHT, 120){

        @Override
        public String getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getFieldDefineTitle();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setFieldDefineTitle(value);
        }
    }
    ,
    FIELDTYPE("fieldType", new String[]{"\u6d6e\u52a8\u5217\u8bbe\u7f6e\u7c7b\u578b", "\u6d6e\u52a8\u5217\u8bbe\u7f6e\u7c7b\u578b"}, ColumnAlignment.RIGHT, 120){

        @Override
        public String getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getFieldType();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setFieldType(value);
        }
    }
    ,
    FIELDVALUE("fieldValue", new String[]{"\u6d6e\u52a8\u5217\u8bbe\u7f6e\u5185\u5bb9", "\u6d6e\u52a8\u5217\u8bbe\u7f6e\u5185\u5bb9"}, ColumnAlignment.RIGHT, 120){

        @Override
        public String getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getFieldValue();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setFieldValue(value);
        }
    }
    ,
    SIGN("sign", new String[]{"\u8fd0\u7b97\u7b26", "\u8fd0\u7b97\u7b26"}, ColumnAlignment.RIGHT, 120){

        @Override
        public String getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getSign();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setSign(value);
        }
    }
    ,
    FETCHSOURCENAME("fetchSourceName", new String[]{"\u4e1a\u52a1\u6a21\u578b", "\u4e1a\u52a1\u6a21\u578b"}, ColumnAlignment.RIGHT, 120){

        @Override
        public String getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getFetchSourceName();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setFetchSourceName(value);
        }
    }
    ,
    FETCHTYPENAME("fetchTypeName", new String[]{"\u53d6\u6570\u7c7b\u578b", "\u53d6\u6570\u7c7b\u578b"}, ColumnAlignment.RIGHT, 120){

        @Override
        public String getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getFetchTypeName();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setFetchTypeName(value);
        }
    }
    ,
    SUBJECTCODE("subjectCode", new String[]{"\u79d1\u76ee", "\u79d1\u76ee"}, ColumnAlignment.RIGHT, 120){

        @Override
        public String getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getSubjectCode();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setSubjectCode(value);
        }
    }
    ,
    EXCLUDESUBJECTCODE("excludeSubjectCode", new String[]{"\u6392\u9664\u79d1\u76ee", "\u6392\u9664\u79d1\u76ee"}, ColumnAlignment.RIGHT, 120){

        @Override
        public String getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getExcludeSubjectCode();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setExcludeSubjectCode(value);
        }
    }
    ,
    DIMTYPENAME("dimTypeName", new String[]{"\u91cd\u5206\u7c7b/\u62b5\u51cf/\u8d26\u9f84\u7ef4\u5ea6", "\u91cd\u5206\u7c7b/\u62b5\u51cf\u7ef4\u5ea6"}, ColumnAlignment.RIGHT, 120){

        @Override
        public String getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getDimTypeName();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setDimTypeName(value);
        }
    }
    ,
    SUMTYPENAME("sumTypeName", new String[]{"\u91cd\u5206\u7c7b/\u62b5\u51cf/\u8d26\u9f84\u7ef4\u5ea6", "\u91cd\u5206\u7c7b\u6c47\u603b\u7c7b\u578b"}, ColumnAlignment.RIGHT, 120){

        @Override
        public String getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getSumTypeName();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setSumTypeName(value);
        }
    }
    ,
    CASHCODE("cashCode", new String[]{"\u73b0\u6d41\u9879\u76ee", "\u73b0\u6d41\u9879\u76ee"}, ColumnAlignment.RIGHT, 120){

        @Override
        public String getFixExportData(ExcelRowFetchSettingVO excelRowFetchSettingVO) {
            return excelRowFetchSettingVO.getCashCode();
        }

        @Override
        public void importData(ExcelRowFetchSettingVO excelRowFetchSettingVO, String value) {
            excelRowFetchSettingVO.setCashCode(value);
        }
    };

    private final String key;
    private final String[] label;
    private final ColumnAlignment align;
    private final Integer width;

    private FloatExportBeforeInnerColumnHandlerEnum(String key, String[] label, ColumnAlignment align, Integer width) {
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

