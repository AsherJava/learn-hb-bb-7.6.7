/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.np.dataengine.reader.QueryFieldInfo
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 */
package com.jiuqi.nr.data.engine.analysis.exe;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DataRowComparator
implements Comparator<DataRow> {
    private static final Logger logger = LogFactory.getLogger(DataRowComparator.class);
    private List<Integer> orderIndexes = new ArrayList<Integer>();
    private List<Boolean> descs = new ArrayList<Boolean>();
    private Metadata<QueryFieldInfo> metadata;

    public DataRowComparator(Metadata<QueryFieldInfo> metadata) {
        this.metadata = metadata;
    }

    public void addOrderColumn(int columnIndex, boolean desc) {
        this.orderIndexes.add(columnIndex);
        this.descs.add(desc);
    }

    public int orderSize() {
        return this.orderIndexes.size();
    }

    @Override
    public int compare(DataRow o1, DataRow o2) {
        int result = 0;
        for (int i = 0; i < this.orderIndexes.size(); ++i) {
            int columnIndex = this.orderIndexes.get(i);
            Object value1 = o1.getValue(columnIndex);
            Object value2 = o2.getValue(columnIndex);
            try {
                result = DataType.compare((int)this.metadata.getColumn(columnIndex).getDataType(), (Object)value1, (Object)value2);
            }
            catch (SyntaxException e) {
                logger.error(e.getMessage(), (Throwable)e);
            }
            if (this.descs.get(i).booleanValue()) {
                result = 0 - result;
            }
            if (result == 0) continue;
            return result;
        }
        return 0;
    }
}

