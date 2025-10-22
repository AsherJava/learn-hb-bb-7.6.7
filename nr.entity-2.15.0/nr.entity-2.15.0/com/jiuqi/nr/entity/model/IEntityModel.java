/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.model;

import com.jiuqi.nr.entity.model.IEntityAttribute;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public interface IEntityModel
extends Serializable {
    public String getEntityId();

    public int getAttributeCount();

    public IEntityAttribute getAttribute(String var1);

    public Iterator<IEntityAttribute> getAttributes();

    public IEntityAttribute getRecordKeyField();

    public IEntityAttribute getBizKeyField();

    public IEntityAttribute getBblxField();

    public List<IEntityAttribute> getShowFields();

    public IEntityAttribute getNameField();

    public IEntityAttribute getCodeField();

    public IEntityAttribute getParentField();

    public IEntityAttribute getOrderField();

    public IEntityAttribute getIconField();

    public IEntityAttribute getBeginDateField();

    public IEntityAttribute getEndDateField();

    public IEntityAttribute getStoppedField();

    public String getI18nCode(IEntityAttribute var1);
}

