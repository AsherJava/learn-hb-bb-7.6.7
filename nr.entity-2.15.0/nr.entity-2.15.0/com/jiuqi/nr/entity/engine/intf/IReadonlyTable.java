/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.entity.engine.intf;

import com.jiuqi.nr.entity.engine.setting.IFieldsInfo;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public interface IReadonlyTable {
    public IFieldsInfo getFieldsInfo();

    public TableModelDefine getEntityTableDefine();

    public IEntityModel getEntityModel();

    public boolean isI18nAttribute(String var1);
}

