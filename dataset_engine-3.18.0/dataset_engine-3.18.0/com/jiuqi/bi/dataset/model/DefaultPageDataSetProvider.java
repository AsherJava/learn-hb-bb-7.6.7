/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.sql.RangeValues
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 */
package com.jiuqi.bi.dataset.model;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.FilterItem;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.SortItem;
import com.jiuqi.bi.dataset.manager.TimeKeyBuilder;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DSModelFactoryManager;
import com.jiuqi.bi.dataset.model.IDataSetProvider;
import com.jiuqi.bi.dataset.model.IPageDataSetProvider;
import com.jiuqi.bi.syntax.sql.RangeValues;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import java.util.ArrayList;
import java.util.Arrays;

public class DefaultPageDataSetProvider
implements IPageDataSetProvider {
    private DSModel model;
    private IDataSetProvider provider;
    private BIDataSetImpl wholeData;
    private int pageSize;
    private int totalSize;

    public DefaultPageDataSetProvider(DSModel model, IDataSetProvider provider) {
        this.model = model;
        this.provider = provider;
    }

    @Override
    public void setPageSize(int pageSize) {
        if (pageSize < 1) {
            throw new IllegalArgumentException("pageSize : " + pageSize);
        }
        this.pageSize = pageSize;
    }

    @Override
    public int open(MemoryDataSet<BIDataSetFieldInfo> memory, int pageNum, IDSContext context) throws BIDataSetException {
        if (pageNum < 1) {
            throw new IllegalArgumentException("pageNum = " + pageNum);
        }
        this.init(context);
        int startRow = (pageNum - 1) * this.pageSize;
        if (startRow > this.wholeData.getRecordCount() - 1) {
            return startRow;
        }
        int endRow = Math.min(pageNum * this.pageSize - 1, this.wholeData.getRecordCount() - 1);
        for (int current = startRow; current <= endRow; ++current) {
            BIDataRow row = this.wholeData.get(current);
            DataRow newRow = memory.add(current - startRow);
            int length = Math.min(row.getBuffer().length, newRow.getBuffer().length);
            for (int i = 0; i < length; ++i) {
                newRow.setValue(i, row.getValue(i));
            }
        }
        return startRow;
    }

    @Override
    public int getRecordCount(IDSContext context) throws BIDataSetException {
        this.init(context);
        return this.totalSize;
    }

    @Override
    public int getPageCount(IDSContext context) throws BIDataSetException {
        this.init(context);
        return (this.totalSize - 1) / this.pageSize + 1;
    }

    private synchronized void init(IDSContext context) throws BIDataSetException {
        if (this.wholeData == null) {
            SortItem[] sortItems;
            MemoryDataSet<BIDataSetFieldInfo> memory = DSModelFactoryManager.createMemoryDataSet(this.model);
            this.provider.open(memory, context);
            TimeKeyBuilder.buildTimeKey(memory);
            this.wholeData = new BIDataSetImpl(memory);
            if (context != null) {
                try {
                    this.wholeData.setParameterEnv(context.getEnhancedParameterEnv());
                    this.wholeData.setLogger(context.getLogger());
                }
                catch (ParameterException e) {
                    throw new BIDataSetException("\u521b\u5efa\u53c2\u6570\u67e5\u8be2\u5bf9\u8c61\u5931\u8d25\uff1a" + e.getMessage(), e);
                }
            }
            this.wholeData._calcField(context);
            if (!context.isFiltered()) {
                RangeValues timekeyRange;
                ArrayList<FilterItem> filterItemList = new ArrayList<FilterItem>();
                FilterItem[] filters = context.getAllFilterItem();
                if (filters != null) {
                    filterItemList.addAll(Arrays.asList(filters));
                }
                if ((timekeyRange = context.getTimekeyFiterRange()) != null) {
                    FilterItem fi = new FilterItem("SYS_TIMEKEY", timekeyRange);
                    filterItemList.add(fi);
                }
                if (filterItemList.size() > 0) {
                    this.wholeData = (BIDataSetImpl)this.wholeData.filter(filterItemList);
                    context.markFiltered();
                }
            }
            if (!context.isSorted() && (sortItems = context.getSortItems()) != null && sortItems.length > 0) {
                this.wholeData = (BIDataSetImpl)this.wholeData.sort(Arrays.asList(sortItems));
                context.markSorted();
            }
            this.totalSize = this.wholeData.getRecordCount();
        }
    }
}

