/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.zb.scheme.internal.dto;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.zb.scheme.common.OperationType;
import com.jiuqi.nr.zb.scheme.common.ZbDiffType;
import com.jiuqi.nr.zb.scheme.core.ZbInfo;
import com.jiuqi.nr.zb.scheme.internal.entity.ZbCheckItemDO;
import com.jiuqi.util.OrderGenerator;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class ZbCheckItemDTO {
    private String checkKey;
    private DataField dataField;
    private ZbInfo zbInfo;
    private String path;
    private List<ZbDiffType> diffTypes = new ArrayList<ZbDiffType>();
    private OperationType operationType;
    private String formSchemeKey;
    private String formGroupKey;
    private String formKey;
    private String formCode;
    private String formTitle;

    public static ZbCheckItemDO buildZbCheckItemDO(ZbCheckItemDTO preCheckItem) {
        List<ZbDiffType> internalDiffTypes;
        ZbCheckItemDO itemDO = new ZbCheckItemDO();
        itemDO.setKey(UUID.randomUUID().toString());
        itemDO.setCheckKey(preCheckItem.getCheckKey());
        itemDO.setDataFieldKey(preCheckItem.getDataField().getKey());
        itemDO.setDataFieldPath(preCheckItem.getPath());
        itemDO.setOperType(preCheckItem.getOperationType().getType());
        itemDO.setFormSchemeKey(preCheckItem.getFormSchemeKey());
        itemDO.setFormGroupKey(preCheckItem.getFormGroupKey());
        itemDO.setFormKey(preCheckItem.getFormKey());
        itemDO.setUpdateTime(Instant.now());
        itemDO.setOrder(OrderGenerator.newOrder());
        ZbInfo internalZbInfo = preCheckItem.getZbInfo();
        if (internalZbInfo != null) {
            itemDO.setZbInfoKey(internalZbInfo.getKey());
        }
        if (!CollectionUtils.isEmpty(internalDiffTypes = preCheckItem.getDiffTypes())) {
            itemDO.setDiffType(internalDiffTypes.stream().mapToInt(ZbDiffType::getType).reduce(0, (a, b) -> a | b));
        }
        return itemDO;
    }

    public String getFormatCode() {
        if (this.diffTypes.contains((Object)ZbDiffType.CODE_DIFF)) {
            return String.format("%s\uff08%s\uff09", this.dataField.getCode(), this.zbInfo.getCode());
        }
        return this.dataField.getCode();
    }

    public String getFormatTitle() {
        if (this.diffTypes.contains((Object)ZbDiffType.TITLE_DIFF)) {
            return String.format("%s\uff08%s\uff09", this.dataField.getTitle(), this.zbInfo.getTitle());
        }
        return this.dataField.getTitle();
    }

    public String getFormatDataType() {
        if (this.diffTypes.contains((Object)ZbDiffType.DATATYPE_DIFF)) {
            return String.format("%s\uff08%s\uff09", this.dataField.getDataFieldType().getTitle(), this.zbInfo.getDataType().getTitle());
        }
        return this.dataField.getDataFieldType().getTitle();
    }

    public String getFormatPrecision() {
        if (this.diffTypes.contains((Object)ZbDiffType.PRECISION_DIFF)) {
            return String.format("%s\uff08%s\uff09", this.dataField.getPrecision() == null ? "\u65e0" : this.dataField.getPrecision(), this.zbInfo.getPrecision() == null ? "\u65e0" : this.zbInfo.getPrecision());
        }
        return this.dataField.getPrecision() == null ? null : String.valueOf(this.dataField.getPrecision());
    }

    public String getFormatDecimal() {
        if (this.diffTypes.contains((Object)ZbDiffType.DECIMAL_DIFF)) {
            return String.format("%s\uff08%s\uff09", this.dataField.getDecimal() == null ? "\u65e0" : this.dataField.getDecimal(), this.zbInfo.getDecimal() == null ? "\u65e0" : this.zbInfo.getDecimal());
        }
        return this.dataField.getDecimal() == null ? null : String.valueOf(this.dataField.getDecimal());
    }

    public String getFormatDiffType() {
        List diffStr = this.diffTypes.stream().map(ZbDiffType::getTitle).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(diffStr)) {
            return String.join((CharSequence)",", diffStr);
        }
        return null;
    }

    public String getCheckKey() {
        return this.checkKey;
    }

    public void setCheckKey(String checkKey) {
        this.checkKey = checkKey;
    }

    public DataField getDataField() {
        return this.dataField;
    }

    public void setDataField(DataField dataField) {
        this.dataField = dataField;
    }

    public ZbInfo getZbInfo() {
        return this.zbInfo;
    }

    public void setZbInfo(ZbInfo zbInfo) {
        this.zbInfo = zbInfo;
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

    public void addDiffType(ZbDiffType type) {
        if (this.diffTypes == null) {
            this.diffTypes = new ArrayList<ZbDiffType>();
        }
        this.diffTypes.add(type);
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public OperationType getOperationType() {
        return this.operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }

    public String getFormCode() {
        return this.formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String toString() {
        return "ZbCheckItemDTO{checkKey='" + this.checkKey + '\'' + ", dataField=" + this.dataField + ", zbInfo=" + this.zbInfo + ", path='" + this.path + '\'' + ", diffTypes=" + this.diffTypes + ", operationType=" + (Object)((Object)this.operationType) + ", formSchemeKey='" + this.formSchemeKey + '\'' + ", formGroupKey='" + this.formGroupKey + '\'' + ", formKey='" + this.formKey + '\'' + ", formCode='" + this.formCode + '\'' + ", formTitle='" + this.formTitle + '\'' + '}';
    }
}

