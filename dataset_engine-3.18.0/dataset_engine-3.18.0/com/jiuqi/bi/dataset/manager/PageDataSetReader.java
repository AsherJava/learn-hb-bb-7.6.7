/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 */
package com.jiuqi.bi.dataset.manager;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.BIDataSetImpl;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.manager.TimeKeyBuilder;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DSModelFactoryManager;
import com.jiuqi.bi.dataset.model.IPageDataSetProvider;
import com.jiuqi.nvwa.framework.parameter.ParameterException;

public class PageDataSetReader {
    private DSModel model;
    private IPageDataSetProvider provider;
    private boolean isMemory;

    public PageDataSetReader(DSModel model, IPageDataSetProvider provider, boolean isMemory) {
        this.model = model;
        this.provider = provider;
        this.isMemory = isMemory;
    }

    public DSModel getModel() {
        return this.model;
    }

    public int getPageCount(IDSContext context) throws BIDataSetException {
        return this.provider.getPageCount(context);
    }

    public void setPageSize(int pageSize) {
        this.provider.setPageSize(pageSize);
    }

    public BIDataSet open(int pageCount, IDSContext context) throws BIDataSetException {
        MemoryDataSet<BIDataSetFieldInfo> memory = DSModelFactoryManager.createMemoryDataSet(this.model);
        int row = this.provider.open(memory, pageCount, context);
        TimeKeyBuilder.buildTimeKey(memory);
        BIDataSetImpl dataset = new BIDataSetImpl(memory);
        Metadata<BIDataSetFieldInfo> metadata = dataset.getMetadata();
        int pos = metadata.indexOf("SYS_ROWNUM");
        if (pos >= 0 && row > 0) {
            for (BIDataRow dataRow : dataset) {
                Object[] data = dataRow.getBuffer();
                int orgin = (Integer)data[pos];
                data[pos] = orgin + row;
            }
        }
        if (context != null) {
            try {
                dataset.setParameterEnv(context.getEnhancedParameterEnv());
                dataset.setLogger(context.getLogger());
            }
            catch (ParameterException e) {
                throw new BIDataSetException("\u83b7\u53d6\u53c2\u6570\u67e5\u8be2\u5bf9\u8c61\u5931\u8d25\uff1a" + e.getMessage(), e);
            }
        }
        if (!this.isMemory) {
            dataset._calcField(context);
        }
        return dataset;
    }

    public int getRecordCount(IDSContext context) throws BIDataSetException {
        return this.provider.getRecordCount(context);
    }
}

