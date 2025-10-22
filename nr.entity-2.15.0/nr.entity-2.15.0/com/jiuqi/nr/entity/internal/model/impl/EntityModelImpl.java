/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.internal.model.impl;

import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import org.springframework.util.CollectionUtils;

public class EntityModelImpl
implements IEntityModel {
    private String entityId;
    private int attributeCount;
    private List<IEntityAttribute> IEntityAttributes;
    private IEntityAttribute bizKeyField;
    private IEntityAttribute bblxField;
    private IEntityAttribute recKeyField;
    private List<IEntityAttribute> showFields;
    private IEntityAttribute nameField;
    private IEntityAttribute codeField;
    private IEntityAttribute parentField;
    private IEntityAttribute orderField;
    private IEntityAttribute iconField;
    private IEntityAttribute beginDateField;
    private IEntityAttribute endDateField;
    private IEntityAttribute stoppedField;
    private Map<String, String> i18nCode;

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public void setBizKeyField(IEntityAttribute bizKeyField) {
        this.bizKeyField = bizKeyField;
    }

    public void setBblxField(IEntityAttribute bblxField) {
        this.bblxField = bblxField;
    }

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    @Override
    public int getAttributeCount() {
        return this.attributeCount;
    }

    @Override
    public IEntityAttribute getAttribute(String attributeName) {
        if (CollectionUtils.isEmpty(this.IEntityAttributes)) {
            this.IEntityAttributes = new ArrayList<IEntityAttribute>();
        }
        Optional<IEntityAttribute> findAttribute = this.IEntityAttributes.stream().filter(e -> e.getName().equalsIgnoreCase(attributeName)).findFirst();
        return findAttribute.orElse(null);
    }

    @Override
    public Iterator<IEntityAttribute> getAttributes() {
        if (CollectionUtils.isEmpty(this.IEntityAttributes)) {
            this.IEntityAttributes = new ArrayList<IEntityAttribute>();
        }
        return this.IEntityAttributes.iterator();
    }

    @Override
    public IEntityAttribute getRecordKeyField() {
        return this.recKeyField;
    }

    public List<IEntityAttribute> getEntityAttributes() {
        return this.IEntityAttributes;
    }

    public void setEntityAttributes(List<IEntityAttribute> IEntityAttributes) {
        this.attributeCount = IEntityAttributes.size();
        this.IEntityAttributes = IEntityAttributes;
    }

    @Override
    public IEntityAttribute getBizKeyField() {
        return this.bizKeyField;
    }

    @Override
    public IEntityAttribute getBblxField() {
        return this.bblxField;
    }

    @Override
    public List<IEntityAttribute> getShowFields() {
        return this.showFields;
    }

    @Override
    public IEntityAttribute getNameField() {
        return this.nameField;
    }

    @Override
    public IEntityAttribute getCodeField() {
        return this.codeField;
    }

    @Override
    public IEntityAttribute getParentField() {
        return this.parentField;
    }

    @Override
    public IEntityAttribute getOrderField() {
        return this.orderField;
    }

    public void setOrderField(IEntityAttribute orderField) {
        this.orderField = orderField;
    }

    @Override
    public IEntityAttribute getIconField() {
        return this.iconField;
    }

    public void setIconField(IEntityAttribute iconField) {
        this.iconField = iconField;
    }

    @Override
    public IEntityAttribute getBeginDateField() {
        return this.beginDateField;
    }

    public void setBeginDateField(IEntityAttribute beginDateField) {
        this.beginDateField = beginDateField;
    }

    @Override
    public IEntityAttribute getEndDateField() {
        return this.endDateField;
    }

    public void setEndDateField(IEntityAttribute endDateField) {
        this.endDateField = endDateField;
    }

    @Override
    public IEntityAttribute getStoppedField() {
        return this.stoppedField;
    }

    public void setStoppedField(IEntityAttribute stoppedField) {
        this.stoppedField = stoppedField;
    }

    public void setRecKeyField(IEntityAttribute recKeyField) {
        this.recKeyField = recKeyField;
    }

    public void setShowFields(List<IEntityAttribute> showFields) {
        this.showFields = showFields;
    }

    public void setNameField(IEntityAttribute nameField) {
        this.nameField = nameField;
    }

    public void setCodeField(IEntityAttribute codeField) {
        this.codeField = codeField;
    }

    public void setParentField(IEntityAttribute parentField) {
        this.parentField = parentField;
    }

    @Override
    public String getI18nCode(IEntityAttribute attribute) {
        if (!attribute.isSupportI18n()) {
            return null;
        }
        if (CollectionUtils.isEmpty(this.i18nCode)) {
            return null;
        }
        return this.i18nCode.get(attribute.getCode().toLowerCase(Locale.ROOT));
    }

    public void putI18nCode(String attributeCode, String i18n) {
        if (CollectionUtils.isEmpty(this.i18nCode)) {
            this.i18nCode = new HashMap<String, String>();
        }
        this.i18nCode.put(attributeCode.toLowerCase(Locale.ROOT), i18n);
    }
}

