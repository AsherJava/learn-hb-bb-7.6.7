/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  org.json.JSONObject
 */
package com.jiuqi.nr.tag.manager.entitydata.query;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.Date;
import java.util.Map;
import org.json.JSONObject;

public interface TagQueryParam {
    public String getPeriod();

    public String getRowFilterExpression();

    public Date getVersionDate();

    public Map<String, DimensionValue> getDimValueSet();

    public JSONObject getCustomVariable();

    public TaskDefine getTaskDefine();

    public FormSchemeDefine getFormScheme();

    public IEntityDefine getEntityDefine();

    public IPeriodEntity getPeriodEntity();
}

