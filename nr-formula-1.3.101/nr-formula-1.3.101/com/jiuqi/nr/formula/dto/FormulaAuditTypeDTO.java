/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.AuditType
 */
package com.jiuqi.nr.formula.dto;

import com.jiuqi.nr.definition.facade.AuditType;

public class FormulaAuditTypeDTO {
    private Integer code;
    private String order;
    private String title;
    private String icon;
    private String backGroundColor;
    private String foregroundColor;
    private String cellColor;

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBackGroundColor() {
        return this.backGroundColor;
    }

    public void setBackGroundColor(String backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public String getForegroundColor() {
        return this.foregroundColor;
    }

    public void setForegroundColor(String foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public String getCellColor() {
        return this.cellColor;
    }

    public void setCellColor(String cellColor) {
        this.cellColor = cellColor;
    }

    public static FormulaAuditTypeDTO toDto(AuditType auditType) {
        if (auditType == null) {
            return null;
        }
        FormulaAuditTypeDTO formulaAuditTypeDTO = new FormulaAuditTypeDTO();
        formulaAuditTypeDTO.setCode(auditType.getCode());
        formulaAuditTypeDTO.setOrder(auditType.getOrder());
        formulaAuditTypeDTO.setTitle(auditType.getTitle());
        formulaAuditTypeDTO.setIcon(auditType.getIcon());
        formulaAuditTypeDTO.setBackGroundColor(auditType.getBackGroundColor());
        formulaAuditTypeDTO.setForegroundColor(auditType.getFontColor());
        formulaAuditTypeDTO.setCellColor(auditType.getGridColor());
        return formulaAuditTypeDTO;
    }
}

