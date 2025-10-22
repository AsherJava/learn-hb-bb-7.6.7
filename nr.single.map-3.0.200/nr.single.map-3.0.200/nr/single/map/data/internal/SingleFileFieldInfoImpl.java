/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.jiuqi.np.definition.common.FieldType
 */
package nr.single.map.data.internal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jiuqi.np.definition.common.FieldType;
import nr.single.map.configurations.deserializer.SingleFileFieldInfoDeserializer;
import nr.single.map.data.facade.SingleFileFieldInfo;

@JsonDeserialize(using=SingleFileFieldInfoDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown=true)
public class SingleFileFieldInfoImpl
implements SingleFileFieldInfo {
    private static final long serialVersionUID = -6584092243838982218L;
    private String fieldCode;
    private FieldType fieldType;
    private int fieldSize;
    private int fieldDecimal;
    private String tableCode;
    private String formCode;
    private String enumCode;
    private String defaultValue;
    private String fieldValue;
    private String netTableCode;
    private String netFieldCode;
    private String netFieldKey;
    private String netDataLinkKey;
    private String netFormCode;
    private String netFieldValue;
    private String importIndex;
    private int floatEnumType;
    private String floatEnumCode;
    private int floatEnumOrder;
    private String regionKey;
    private String regionCode;

    @Override
    public String getFieldCode() {
        return this.fieldCode;
    }

    @Override
    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    @Override
    public FieldType getFieldType() {
        return this.fieldType;
    }

    @Override
    public void setFieldType(FieldType type) {
        this.fieldType = type;
    }

    @Override
    public int getFieldSize() {
        return this.fieldSize;
    }

    @Override
    public void setFieldSize(int size) {
        this.fieldSize = size;
    }

    @Override
    public int getFieldDecimal() {
        return this.fieldDecimal;
    }

    @Override
    public void setFieldDecimal(int fieldDecimal) {
        this.fieldDecimal = fieldDecimal;
    }

    @Override
    public String getTableCode() {
        if (this.tableCode == null) {
            return this.getNetFormCode();
        }
        return this.tableCode;
    }

    @Override
    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    @Override
    public String getEnumCode() {
        return this.enumCode;
    }

    @Override
    public void setEnumCode(String enumCode) {
        this.enumCode = enumCode;
    }

    @Override
    public String getNetTableCode() {
        return this.netTableCode;
    }

    @Override
    public void setNetTableCode(String tableCode) {
        this.netTableCode = tableCode;
    }

    @Override
    public String getNetFieldCode() {
        return this.netFieldCode;
    }

    @Override
    public void setNetFieldCode(String feildCode) {
        this.netFieldCode = feildCode;
    }

    @Override
    public String getNetFieldKey() {
        return this.netFieldKey;
    }

    @Override
    public void setNetFieldKey(String fieldKey) {
        this.netFieldKey = fieldKey;
    }

    @Override
    public String getNetDataLinkKey() {
        return this.netDataLinkKey;
    }

    @Override
    public void setNetDataLinkKey(String linkKey) {
        this.netDataLinkKey = linkKey;
    }

    @Override
    public String getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public void copyFrom(SingleFileFieldInfo info) {
        this.fieldCode = info.getFieldCode();
        this.fieldType = info.getFieldType();
        this.fieldSize = info.getFieldSize();
        this.fieldDecimal = info.getFieldDecimal();
        this.tableCode = info.getTableCode();
        this.formCode = info.getFormCode();
        this.enumCode = info.getEnumCode();
        this.defaultValue = info.getDefaultValue();
        this.fieldValue = info.getFieldValue();
        this.netTableCode = info.getNetTableCode();
        this.netFieldCode = info.getNetFieldCode();
        this.netFieldKey = info.getNetFieldKey();
        this.netDataLinkKey = info.getNetDataLinkKey();
        this.netFieldValue = info.getNetFieldValue();
        this.regionKey = info.getRegionKey();
        this.importIndex = info.getImportIndex();
        this.floatEnumType = info.getFloatEnumType();
        this.floatEnumCode = info.getFloatEnumCode();
        this.floatEnumOrder = info.getFloatEnumOrder();
        this.regionCode = info.getRegionCode();
    }

    @Override
    public String getFormCode() {
        if (this.formCode == null) {
            return this.getNetFormCode();
        }
        return this.formCode;
    }

    @Override
    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    @Override
    public String getNetFormCode() {
        return this.netFormCode;
    }

    @Override
    public void setNetFormCode(String formCode) {
        this.netFormCode = formCode;
    }

    @Override
    public String getFieldValue() {
        return this.fieldValue;
    }

    @Override
    public void setFieldValue(String fieldVaue) {
        this.fieldValue = fieldVaue;
    }

    @Override
    public String getNetFieldValue() {
        return this.netFieldValue;
    }

    @Override
    public void setNetFieldValue(String fieldVaue) {
        this.netFieldValue = this.fieldValue;
    }

    @Override
    public String getImportIndex() {
        return this.importIndex;
    }

    @Override
    public void setImportIndex(String importIndex) {
        this.importIndex = importIndex;
    }

    @Override
    public int getFloatEnumType() {
        return this.floatEnumType;
    }

    @Override
    public void setFloatEnumType(int floatEnumType) {
        this.floatEnumType = floatEnumType;
    }

    @Override
    public String getFloatEnumCode() {
        return this.floatEnumCode;
    }

    @Override
    public void setFloatEnumCode(String floatEnumCode) {
        this.floatEnumCode = floatEnumCode;
    }

    @Override
    public int getFloatEnumOrder() {
        return this.floatEnumOrder;
    }

    @Override
    public void setFloatEnumOrder(int floatEnumOrder) {
        this.floatEnumOrder = floatEnumOrder;
    }

    @Override
    public String getRegionKey() {
        return this.regionKey;
    }

    @Override
    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    @Override
    public String getRegionCode() {
        return this.regionCode;
    }

    @Override
    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
}

