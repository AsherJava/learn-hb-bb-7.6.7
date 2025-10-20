/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.template.vo.TemplateFieldSettingVO
 */
package com.jiuqi.va.query.sql.dto;

import com.jiuqi.va.query.template.vo.TemplateFieldSettingVO;
import java.util.List;

public class SqlExecConditionDTO {
    private String sql;
    private Object[] args;
    private List<TemplateFieldSettingVO> fields;
    private Boolean enableTempTable;
    private String sn;
    private List<TemplateFieldSettingVO> collSpanColumns;

    public SqlExecConditionDTO() {
    }

    public SqlExecConditionDTO(String sql, Object[] args, List<TemplateFieldSettingVO> fields) {
        this.sql = sql;
        this.args = args;
        this.fields = fields;
    }

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public List<TemplateFieldSettingVO> getFields() {
        return this.fields;
    }

    public void setFields(List<TemplateFieldSettingVO> fields) {
        this.fields = fields;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Boolean getEnableTempTable() {
        return this.enableTempTable;
    }

    public void setEnableTempTable(Boolean enableTempTable) {
        this.enableTempTable = enableTempTable;
    }

    public List<TemplateFieldSettingVO> getCollSpanColumns() {
        return this.collSpanColumns;
    }

    public void setCollSpanColumns(List<TemplateFieldSettingVO> collSpanColumns) {
        this.collSpanColumns = collSpanColumns;
    }
}

