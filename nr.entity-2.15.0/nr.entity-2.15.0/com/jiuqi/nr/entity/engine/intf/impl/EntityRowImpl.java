/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.entity.engine.intf.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.entity.engine.common.DataTypesConvert;
import com.jiuqi.nr.entity.engine.common.RowState;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.data.DataTypes;
import com.jiuqi.nr.entity.engine.exception.DataTypeException;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import com.jiuqi.nr.entity.engine.setting.IFieldsInfo;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class EntityRowImpl
implements IEntityRow {
    private static final Logger logger = LoggerFactory.getLogger(EntityRowImpl.class);
    protected ReadonlyTableImpl tableImpl;
    protected DimensionValueSet rowKeys;
    private int rowIndex;
    protected RowState rowState;
    protected Map<String, Object> modifiedDatas;
    private String iconValue;
    private Integer isStoped;
    private Boolean isLeaf;
    private Boolean hasChildren;

    public EntityRowImpl(ReadonlyTableImpl table, DimensionValueSet rowKeys, int rowIndex) {
        this.tableImpl = table;
        this.rowKeys = rowKeys;
        this.rowIndex = rowIndex;
    }

    @Override
    public IFieldsInfo getFieldsInfo() {
        return this.tableImpl.getFieldsInfo();
    }

    @Override
    public DimensionValueSet getRowKeys() {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.assign(this.rowKeys);
        dimensionValueSet.setValue(this.tableImpl.queryInfo.getTableRunInfo().getDimensionName(), (Object)this.getEntityKeyData());
        return dimensionValueSet;
    }

    @Override
    public AbstractData getValue(String code) throws RuntimeException {
        IEntityAttribute fieldDefine = this.getFieldsInfo().getFieldDefine(code);
        if (fieldDefine == null) {
            throw new RuntimeException(String.format("%s\u4e0d\u5728\u67e5\u8be2\u7ed3\u679c\u96c6\u5185", code));
        }
        if (fieldDefine.isSupportI18n()) {
            IEntityModel entityModel = this.tableImpl.getEntityModel();
            String i18nCode = entityModel.getI18nCode(fieldDefine);
            return this.getI18nValue(code, i18nCode);
        }
        Object resultValue = this.getSourceValue(code);
        int dataType = DataTypesConvert.fieldTypeToDataType(this.getFieldsInfo().getDataType(code));
        if (resultValue == null) {
            return DataTypes.getNullValue(dataType);
        }
        if (resultValue instanceof List) {
            resultValue = String.join((CharSequence)";", (List)resultValue);
        }
        return AbstractData.valueOf(resultValue, dataType);
    }

    @Override
    public String getAsString(String code) throws RuntimeException {
        AbstractData value = this.getValue(code);
        IEntityAttribute attribute = this.getFieldsInfo().getFieldDefine(code);
        return this.formatData(value, attribute);
    }

    @Override
    public AbstractData getValue(int index) throws RuntimeException {
        IEntityAttribute fieldDefine = this.getFieldsInfo().getFieldByIndex(index);
        if (fieldDefine == null) {
            throw new RuntimeException(String.format("%s\u4e0d\u5728\u67e5\u8be2\u7ed3\u679c\u96c6\u5185", index));
        }
        return this.getValue(fieldDefine.getCode());
    }

    @Override
    public String getAsString(int index) throws RuntimeException {
        IEntityAttribute fieldDefine = this.getFieldsInfo().getFieldByIndex(index);
        if (fieldDefine == null) {
            throw new RuntimeException(String.format("%s\u4e0d\u5728\u67e5\u8be2\u7ed3\u679c\u96c6\u5185", index));
        }
        return this.getAsString(fieldDefine.getCode());
    }

    @Override
    public Object getAsObject(int index) throws RuntimeException {
        IEntityAttribute fieldDefine = this.getFieldsInfo().getFieldByIndex(index);
        if (fieldDefine == null) {
            throw new RuntimeException(String.format("%s\u4e0d\u5728\u67e5\u8be2\u7ed3\u679c\u96c6\u5185", index));
        }
        if (fieldDefine.isSupportI18n()) {
            IEntityModel entityModel = this.tableImpl.getEntityModel();
            String i18nCode = entityModel.getI18nCode(fieldDefine);
            return this.getSourceValue(i18nCode);
        }
        return this.getSourceValue(fieldDefine.getCode());
    }

    private AbstractData getI18nValue(String code, String i18nCode) throws RuntimeException {
        int dataType = DataTypesConvert.fieldTypeToDataType(this.getFieldsInfo().getDataType(code));
        Object resultValue = this.getSourceValue(i18nCode);
        if (resultValue == null) {
            resultValue = this.getSourceValue(code);
        }
        if (resultValue instanceof List) {
            resultValue = String.join((CharSequence)",", (List)resultValue);
        }
        return AbstractData.valueOf(resultValue, dataType);
    }

    private String formatData(AbstractData valueData, IEntityAttribute fieldDefine) throws DataTypeException {
        if (fieldDefine == null) {
            if (valueData.dataType == 3 || valueData.dataType == 10) {
                Object numValue = valueData.getAsObject();
                if (numValue == null) {
                    return "";
                }
                BigDecimal dataValue = Convert.toBigDecimal((Object)numValue);
                return dataValue.toPlainString();
            }
            return valueData.getAsString();
        }
        if ((fieldDefine.getColumnType() == ColumnModelType.DOUBLE || fieldDefine.getColumnType() == ColumnModelType.BIGDECIMAL) && valueData.dataType != 6) {
            Object numValue = valueData.getAsObject();
            if (numValue == null) {
                return "";
            }
            BigDecimal dataValue = Convert.toBigDecimal((Object)numValue);
            return dataValue.setScale(fieldDefine.getDecimal(), RoundingMode.HALF_EVEN).toPlainString();
        }
        return valueData.getAsString();
    }

    public void setValue(String code, Object value) {
        IEntityAttribute fieldDefine = this.getFieldsInfo().getFieldDefine(code);
        if (fieldDefine == null) {
            throw new RuntimeException(String.format("%s\u4e0d\u5728\u67e5\u8be2\u7ed3\u679c\u96c6\u5185", code));
        }
        this.internalSet(code, value);
    }

    public void internalSet(String code, Object value) {
        IEntityAttribute fieldDefine = this.getFieldsInfo().getFieldDefine(code);
        if (fieldDefine.isMultival()) {
            if (value == null || "".equals(value)) {
                value = new ArrayList();
            } else {
                String[] splitValue = ((Object)value).toString().split(";");
                ArrayList listValue = new ArrayList(splitValue.length);
                Collections.addAll(listValue, splitValue);
                value = listValue;
            }
        }
        this.modifyData(code, value);
    }

    private void modifyData(String code, Object resultValue) {
        if (this.rowState == RowState.NONE) {
            this.rowState = RowState.MODIFIED;
        }
        this.modifiedDatas.put(code.toLowerCase(Locale.ROOT), resultValue);
    }

    @Override
    public String getEntityKeyData() {
        return this.getResultSet().getKey();
    }

    @Override
    public String getParentEntityKey() {
        return this.getResultSet().getParent();
    }

    @Override
    public String[] getParentsEntityKeyDataPath() {
        return this.getResultSet().getParents();
    }

    @Override
    public boolean isLeaf() {
        if (this.isLeaf == null) {
            this.isLeaf = this.getResultSet().isLeaf();
        }
        return this.isLeaf;
    }

    @Override
    public boolean hasChildren() {
        if (this.hasChildren == null) {
            this.hasChildren = this.getResultSet().hasChildren();
        }
        return this.hasChildren;
    }

    @Override
    public Object getEntityOrder() {
        return this.getResultSet().getOrder();
    }

    @Override
    public String getTitle() {
        return this.getResultSet().getTitle();
    }

    @Override
    public String getCode() {
        return this.getResultSet().getCode();
    }

    public EntityRowImpl clone() {
        DimensionValueSet dimensionValueSet = new DimensionValueSet(this.rowKeys);
        return new EntityRowImpl(this.tableImpl, dimensionValueSet, this.rowIndex);
    }

    @Override
    public String getIconValue() {
        if (StringUtils.hasText(this.iconValue)) {
            return this.iconValue;
        }
        IEntityAttribute iconField = this.tableImpl.getEntityModel().getIconField();
        if (iconField != null) {
            this.iconValue = this.getAsString(iconField.getCode());
        }
        return this.iconValue;
    }

    @Override
    public boolean isStoped() {
        if (this.isStoped != null) {
            return this.isStoped == 1;
        }
        IEntityAttribute stoppedField = this.tableImpl.getEntityModel().getStoppedField();
        if (stoppedField != null) {
            this.isStoped = this.getValue(stoppedField.getCode()).getAsBool() ? 1 : 0;
        }
        return this.isStoped == 1;
    }

    public void setRowState(RowState rowState) {
        this.rowState = rowState;
    }

    public RowState getRowState() {
        return this.rowState;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public void setLeaf(Boolean leaf) {
        this.isLeaf = leaf;
    }

    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public Object getSourceValue(String code) {
        return this.getResultSet().getObject(code);
    }

    public EntityResultSet getResultSet() {
        return this.tableImpl.getResultSet().moveCursors(this.rowIndex);
    }

    public Map<String, Object> getModifiedData() {
        return this.modifiedDatas;
    }
}

