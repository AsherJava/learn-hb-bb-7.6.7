/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataRow
 */
package com.jiuqi.bi.dataset.scroll;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.MemoryDataRow;
import com.jiuqi.bi.dataset.SortItem;
import com.jiuqi.bi.dataset.manager.DataSetManagerFactory;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DataSetTypeNotFoundException;
import com.jiuqi.bi.dataset.scroll.DatasetReader;
import com.jiuqi.bi.dataset.scroll.IDataProcessor;
import java.util.Arrays;
import java.util.Iterator;

public class MemoryDatasetReader
extends DatasetReader {
    public MemoryDatasetReader(DSModel model) {
        super(model);
    }

    @Override
    public void read(IDSContext context, IDataProcessor processor) throws BIDataSetException {
        BIDataSet dataset;
        IDataSetManager dsMgr = DataSetManagerFactory.create();
        try {
            dataset = dsMgr.open(context, this.model);
        }
        catch (DataSetTypeNotFoundException e) {
            throw new BIDataSetException(e.getMessage(), e);
        }
        SortItem[] sortItems = context.getSortItems();
        if (sortItems != null && sortItems.length > 0) {
            dataset.sort(Arrays.asList(sortItems));
        }
        Iterator<BIDataRow> itor = dataset.iterator();
        try {
            while (itor.hasNext()) {
                BIDataRow dataRow = itor.next();
                MemoryDataRow row = new MemoryDataRow();
                row._setBuffer(dataRow.getBuffer());
                processor.process((DataRow)row);
            }
            processor.complete(true);
        }
        catch (BIDataSetException e1) {
            processor.complete(false);
            throw e1;
        }
    }
}

