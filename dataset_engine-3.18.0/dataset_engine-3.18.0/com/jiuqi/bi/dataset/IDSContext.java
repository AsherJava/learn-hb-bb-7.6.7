/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.sql.RangeValues
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.SortItem;
import com.jiuqi.bi.dataset.logger.ILogger;
import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.syntax.sql.RangeValues;

public interface IDSContext {
    public String getUserGuid();

    public String getI18nLang();

    public String getDataSrcGuid();

    @Deprecated
    public IParameterEnv getParameterEnv() throws ParameterException;

    public com.jiuqi.nvwa.framework.parameter.IParameterEnv getEnhancedParameterEnv() throws com.jiuqi.nvwa.framework.parameter.ParameterException;

    public ILogger getLogger();

    public FilterItem[] getAllFilterItem();

    public RangeValues getTimekeyFiterRange();

    public void markFiltered();

    public boolean isFiltered();

    public SortItem[] getSortItems();

    public void markSorted();

    public boolean isSorted();
}

