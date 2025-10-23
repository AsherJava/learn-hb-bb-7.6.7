/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 */
package com.jiuqi.nr.zb.scheme.web.vo;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.zb.scheme.common.OperationType;
import com.jiuqi.nr.zb.scheme.common.ZbDiffType;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.internal.dto.ZbCheckItemDTO;
import java.util.List;

public class ZbCheckItemVO {
    private String ownFormCode;
    private String ownFormTitle;
    private String dataFieldCode;
    private String dataFieldTitle;
    private String dataFieldDataType;
    private Integer dataFieldPrecision;
    private Integer dataFieldDecimal;
    private String zbInfoCode;
    private String zbInfoTitle;
    private String zbInfoDataType;
    private Integer zbInfoPrecision;
    private Integer zbInfoDecimal;
    private String path;
    private List<ZbDiffType> diffTypes;
    private OperationType operationType;

    public static ZbCheckItemVO build(ZbCheckItemDTO zbCheckItemDTO) {
        ZbCheckItemVO itemVO = new ZbCheckItemVO();
        DataField dataField = zbCheckItemDTO.getDataField();
        ZbInfo zbInfo = zbCheckItemDTO.getZbInfo();
        itemVO.setOwnFormCode(zbCheckItemDTO.getFormCode());
        itemVO.setOwnFormTitle(zbCheckItemDTO.getFormTitle());
        itemVO.setDataFieldCode(dataField.getCode());
        itemVO.setDataFieldTitle(dataField.getTitle());
        itemVO.setDataFieldDataType(dataField.getDataFieldType().getTitle());
        itemVO.setDataFieldPrecision(dataField.getPrecision() == null ? 0 : dataField.getPrecision());
        itemVO.setDataFieldDecimal(dataField.getDecimal() == null ? 0 : dataField.getDecimal());
        itemVO.setPath(zbCheckItemDTO.getPath());
        itemVO.setDiffTypes(zbCheckItemDTO.getDiffTypes());
        itemVO.setOperationType(zbCheckItemDTO.getOperationType());
        if (zbInfo != null) {
            itemVO.setZbInfoCode(zbInfo.getCode());
            itemVO.setZbInfoTitle(zbInfo.getTitle());
            itemVO.setZbInfoDataType(zbInfo.getDataType().getTitle());
            itemVO.setZbInfoPrecision(zbInfo.getPrecision() == null ? 0 : zbInfo.getPrecision());
            itemVO.setZbInfoDecimal(zbInfo.getDecimal() == null ? 0 : zbInfo.getDecimal());
        }
        return itemVO;
    }

    public String getOwnFormCode() {
        return this.ownFormCode;
    }

    public void setOwnFormCode(String ownFormCode) {
        this.ownFormCode = ownFormCode;
    }

    public String getOwnFormTitle() {
        return this.ownFormTitle;
    }

    public void setOwnFormTitle(String ownFormTitle) {
        this.ownFormTitle = ownFormTitle;
    }

    public String getDataFieldCode() {
        return this.dataFieldCode;
    }

    public void setDataFieldCode(String dataFieldCode) {
        this.dataFieldCode = dataFieldCode;
    }

    public String getZbInfoCode() {
        return this.zbInfoCode;
    }

    public void setZbInfoCode(String zbInfoCode) {
        this.zbInfoCode = zbInfoCode;
    }

    public String getDataFieldTitle() {
        return this.dataFieldTitle;
    }

    public void setDataFieldTitle(String dataFieldTitle) {
        this.dataFieldTitle = dataFieldTitle;
    }

    public String getZbInfoTitle() {
        return this.zbInfoTitle;
    }

    public void setZbInfoTitle(String zbInfoTitle) {
        this.zbInfoTitle = zbInfoTitle;
    }

    public String getDataFieldDataType() {
        return this.dataFieldDataType;
    }

    public void setDataFieldDataType(String dataFieldDataType) {
        this.dataFieldDataType = dataFieldDataType;
    }

    public String getZbInfoDataType() {
        return this.zbInfoDataType;
    }

    public void setZbInfoDataType(String zbInfoDataType) {
        this.zbInfoDataType = zbInfoDataType;
    }

    public Integer getDataFieldPrecision() {
        return this.dataFieldPrecision;
    }

    public void setDataFieldPrecision(int dataFieldPrecision) {
        this.dataFieldPrecision = dataFieldPrecision;
    }

    public Integer getZbInfoPrecision() {
        return this.zbInfoPrecision;
    }

    public void setZbInfoPrecision(int zbInfoPrecision) {
        this.zbInfoPrecision = zbInfoPrecision;
    }

    public Integer getDataFieldDecimal() {
        return this.dataFieldDecimal;
    }

    public void setDataFieldDecimal(int dataFieldDecimal) {
        this.dataFieldDecimal = dataFieldDecimal;
    }

    public Integer getZbInfoDecimal() {
        return this.zbInfoDecimal;
    }

    public void setZbInfoDecimal(int zbInfoDecimal) {
        this.zbInfoDecimal = zbInfoDecimal;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<ZbDiffType> getDiffTypes() {
        return this.diffTypes;
    }

    public void setDiffTypes(List<ZbDiffType> diffTypes) {
        this.diffTypes = diffTypes;
    }

    public OperationType getOperationType() {
        return this.operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String toString() {
        return "ZbCheckItemVO{dataFieldCode='" + this.dataFieldCode + '\'' + ", dataFieldTitle='" + this.dataFieldTitle + '\'' + ", dataFieldDataType='" + this.dataFieldDataType + '\'' + ", dataFieldPrecision=" + this.dataFieldPrecision + ", dataFieldDecimal=" + this.dataFieldDecimal + ", zbInfoCode='" + this.zbInfoCode + '\'' + ", zbInfoTitle='" + this.zbInfoTitle + '\'' + ", zbInfoDataType='" + this.zbInfoDataType + '\'' + ", zbInfoPrecision=" + this.zbInfoPrecision + ", zbInfoDecimal=" + this.zbInfoDecimal + ", path='" + this.path + '\'' + ", diffTypes=" + this.diffTypes + ", operationType=" + (Object)((Object)this.operationType) + '}';
    }
}

