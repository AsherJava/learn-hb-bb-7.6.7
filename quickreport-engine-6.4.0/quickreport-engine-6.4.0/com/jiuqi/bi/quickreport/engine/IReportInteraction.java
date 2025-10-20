/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 */
package com.jiuqi.bi.quickreport.engine;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.quickreport.engine.interaction.ReportInteractionException;
import com.jiuqi.bi.quickreport.engine.result.InteractiveInfo;
import com.jiuqi.bi.quickreport.model.OrderMode;
import java.util.List;

public interface IReportInteraction {
    public static final String COL_ITEM_NAME = "ItemName";
    public static final String COL_ITEM_TITLE = "ItemTitle";
    public static final String COL_ITEM_PARENT = "ItemParent";

    public void reset() throws ReportInteractionException;

    public void orderBy(InteractiveInfo var1, OrderMode var2) throws ReportInteractionException;

    public void filterBy(InteractiveInfo var1, List<Object> var2) throws ReportInteractionException;

    public void filterBy(InteractiveInfo var1, String var2) throws ReportInteractionException;

    public MemoryDataSet<Object> queryFilterItems(InteractiveInfo var1) throws ReportInteractionException;

    @Deprecated
    public void setSortMode(int var1, OrderMode var2) throws ReportInteractionException;

    @Deprecated
    public OrderMode changeSortMode(int var1) throws ReportInteractionException;

    @Deprecated
    public OrderMode getSortMode(int var1);
}

