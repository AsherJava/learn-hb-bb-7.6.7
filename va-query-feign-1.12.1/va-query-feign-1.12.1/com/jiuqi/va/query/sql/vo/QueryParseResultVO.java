/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.sql.vo;

import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import com.jiuqi.va.query.template.vo.TemplateParamsVO;
import java.util.List;
import java.util.Map;

public class QueryParseResultVO {
    private List<TemplateFieldSettingVO> fields;
    private Map<String, TemplateFieldSettingVO> columnMap;
    private List<TemplateParamsVO> params;

    public List<TemplateFieldSettingVO> getFields() {
        return this.fields;
    }

    public void setFields(List<TemplateFieldSettingVO> fields) {
        this.fields = fields;
    }

    public List<TemplateParamsVO> getParams() {
        return this.params;
    }

    public void setParams(List<TemplateParamsVO> params) {
        this.params = params;
    }

    public Map<String, TemplateFieldSettingVO> getColumnMap() {
        return this.columnMap;
    }

    public void setColumnMap(Map<String, TemplateFieldSettingVO> columnMap) {
        this.columnMap = columnMap;
    }
}

