/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSetException
 *  com.jiuqi.bi.dataset.DSContext
 *  com.jiuqi.bi.dataset.IDSContext
 *  com.jiuqi.bi.dataset.manager.IDataSetManager
 *  com.jiuqi.bi.dataset.manager.PageDataSetReader
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.DataSetTypeNotFoundException
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.quickreport.engine.context.cache;

import com.jiuqi.bi.dataset.BIDataSetException;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.manager.PageDataSetReader;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.DataSetTypeNotFoundException;
import com.jiuqi.bi.quickreport.engine.context.ReportContextException;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.util.StringUtils;
import java.util.Set;
import java.util.TreeSet;

public final class DSPagingInfo {
    private final String id;
    private final String groupField;
    private int recordSize;
    private int pageSize;
    private int pageNum;
    private int pageCount;
    private Set<Region> regions;
    private PageDataSetReader reader;

    public DSPagingInfo(String dsName, String groupField) {
        this.id = dsName;
        this.groupField = groupField;
        this.recordSize = -1;
        this.pageSize = -1;
        this.pageNum = -1;
        this.pageCount = -1;
        this.regions = new TreeSet<Region>();
    }

    public String getId() {
        return this.id;
    }

    public int getRecordSize() {
        return this.recordSize;
    }

    public void setRecordSize(int recordSize) {
        this.recordSize = recordSize;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public Set<Region> getRegions() {
        return this.regions;
    }

    PageDataSetReader openReader(DSModel dsModel, DSContext dsContext, IDataSetManager dataSetManager) throws ReportContextException {
        if (this.reader != null) {
            return this.reader;
        }
        try {
            this.reader = StringUtils.isEmpty((String)this.groupField) ? dataSetManager.openPageDataSet((IDSContext)dsContext, dsModel) : dataSetManager.openGroupPageDataSet((IDSContext)dsContext, this.groupField, dsModel);
        }
        catch (BIDataSetException e) {
            throw new ReportContextException(e.getMessage(), e);
        }
        catch (DataSetTypeNotFoundException e) {
            throw new ReportContextException(e.getMessage(), e);
        }
        this.reader.setPageSize(this.pageSize);
        return this.reader;
    }

    void clear() {
        this.recordSize = -1;
        this.pageCount = -1;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('[').append(this.id).append(", ").append(", pageNum = ").append(this.pageNum).append(", pageCount = ").append(this.pageCount).append(", pageSize = ").append(this.pageSize).append(", recordSize = ").append(this.recordSize);
        this.regions.forEach(r -> buffer.append(", ").append(r));
        buffer.append(']');
        return buffer.toString();
    }
}

