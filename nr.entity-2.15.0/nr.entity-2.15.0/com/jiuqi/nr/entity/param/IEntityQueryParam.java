/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.param;

import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.executors.QueryContext;
import com.jiuqi.nr.entity.engine.filter.IEntityDataFilter;
import com.jiuqi.nr.entity.engine.setting.OrderAttribute;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IEntityQueryParam {
    public String getEntityId();

    public Date getVersionDate();

    public boolean isIgnoreAuth();

    public boolean isHasReadAuth();

    public boolean isHasWriteAuth();

    public IEntityDataFilter getFilter();

    public List<String> getMasterKey();

    public List<String> getCodes();

    public Integer getLimit();

    public Integer getOffSet();

    public ExecutorContext getContext();

    public List<OrderAttribute> getOrderField();

    public String getIsolationCondition();

    public boolean isSortedByQuery();

    public boolean isLazy();

    public boolean isMarkLeaf();

    public boolean isMaskingData();

    public Map<String, Object> getExt();

    public boolean isDbMode();

    public QueryContext getQueryContext();

    public Integer getQueryStop();
}

