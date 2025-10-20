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
import com.jiuqi.bi.dataset.BIDataSet;
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
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.syntax.sql.RangeValues;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DefaultGroupPageDataSetProvider
implements IPageDataSetProvider {
    private DSModel model;
    private IDataSetProvider provider;
    private BIDataSetImpl wholeData;
    private int pageSize;
    private int groupFieldIndex = -1;
    private List<List<Object>> pageValues = null;

    public DefaultGroupPageDataSetProvider(DSModel model, IDataSetProvider provider, String groupFieldName) {
        this.model = model;
        this.provider = provider;
        List<DSField> fields = model.getCommonFields();
        for (int i = 0; i < fields.size(); ++i) {
            DSField field = fields.get(i);
            if (!field.getName().equalsIgnoreCase(groupFieldName)) continue;
            this.groupFieldIndex = i;
            break;
        }
        if (this.groupFieldIndex == -1) {
            throw new IllegalArgumentException("\u65e0\u6cd5\u627e\u5230\u7ef4\u5ea6\u5b57\u6bb5" + groupFieldName);
        }
    }

    @Override
    public int open(MemoryDataSet<BIDataSetFieldInfo> memory, int pageNum, IDSContext context) throws BIDataSetException {
        if (pageNum < 1) {
            throw new IllegalArgumentException("pageNum : " + pageNum);
        }
        if (pageNum > this.getPageCount(context)) {
            return this.wholeData.getRecordCount();
        }
        BIDataSet pageSet = this.getPagedData(pageNum);
        int rowLen = Math.min(pageSet.getMetadata().size(), memory.getMetadata().size());
        for (BIDataRow curRow : pageSet) {
            DataRow newRow = memory.add();
            System.arraycopy(curRow.getBuffer(), 0, newRow.getBuffer(), 0, rowLen);
        }
        return -1;
    }

    private BIDataSet getPagedData(int pageNum) throws BIDataSetException {
        List<Object> filterValues = this.pageValues.get(pageNum - 1);
        String groupFieldName = this.wholeData.getMetadata().getColumn(this.groupFieldIndex).getName();
        FilterItem filter = new FilterItem(groupFieldName, filterValues);
        ArrayList<FilterItem> filters = new ArrayList<FilterItem>(1);
        filters.add(filter);
        return this.wholeData.filter(filters);
    }

    @Override
    public int getRecordCount(IDSContext context) throws BIDataSetException {
        this.init(context);
        return this.wholeData.getRecordCount();
    }

    @Override
    public int getPageCount(IDSContext context) throws BIDataSetException {
        this.init(context);
        if (this.pageValues == null) {
            this.calcPageCount();
        }
        return this.pageValues.size();
    }

    @Override
    public void setPageSize(int pageSize) {
        if (pageSize < 1) {
            throw new IllegalArgumentException("pageSize : " + pageSize);
        }
        this.pageSize = pageSize;
        this.pageValues = null;
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
                    throw new BIDataSetException("\u83b7\u53d6\u53c2\u6570\u67e5\u8be2\u5bf9\u8c61\u5931\u8d25\uff1a" + e.getMessage(), e);
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
            }
        }
    }

    private void calcPageCount() {
        List<GroupInfo> groupInfos = this.scanGroupInfos();
        this.pageValues = this.makePageValue(groupInfos);
    }

    private List<GroupInfo> scanGroupInfos() {
        ArrayList<GroupInfo> groupInfos = new ArrayList<GroupInfo>();
        HashMap<Object, GroupInfo> finder = new HashMap<Object, GroupInfo>();
        for (BIDataRow row : this.wholeData) {
            Object key = row.getValue(this.groupFieldIndex);
            GroupInfo info = (GroupInfo)finder.get(key);
            if (info == null) {
                info = new GroupInfo(key, 1);
                groupInfos.add(info);
                finder.put(key, info);
                continue;
            }
            ++info.size;
        }
        return groupInfos;
    }

    private List<List<Object>> makePageValue(List<GroupInfo> groupInfos) {
        ArrayList<List<Object>> pageInfos = new ArrayList<List<Object>>();
        int curSize = 0;
        ArrayList<Object> curValues = new ArrayList<Object>();
        for (GroupInfo groupInfo : groupInfos) {
            curValues.add(groupInfo.value);
            if ((curSize += groupInfo.size) < this.pageSize) continue;
            pageInfos.add(curValues);
            curSize = 0;
            curValues = new ArrayList();
        }
        if (!curValues.isEmpty()) {
            pageInfos.add(curValues);
        }
        return pageInfos;
    }

    private static final class GroupInfo {
        public final Object value;
        public int size;

        public GroupInfo(Object value, int size) {
            this.value = value;
            this.size = size;
        }
    }
}

