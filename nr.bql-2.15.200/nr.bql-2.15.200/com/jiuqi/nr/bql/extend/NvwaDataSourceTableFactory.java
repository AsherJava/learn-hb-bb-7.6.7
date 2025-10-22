/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.storage.DataSetStorageException
 *  com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException
 *  com.jiuqi.nr.query.datascheme.extend.AbstractDataTableAdapter
 *  com.jiuqi.nr.query.datascheme.extend.IDataTableFactory
 *  com.jiuqi.nr.query.datascheme.extend.IDataTableMetaDataProvider
 *  com.jiuqi.nr.query.datascheme.extend.IDataTableQueryExecutor
 */
package com.jiuqi.nr.bql.extend;

import com.jiuqi.bi.dataset.storage.DataSetStorageException;
import com.jiuqi.nr.bql.datasource.ComponentSet;
import com.jiuqi.nr.bql.extend.NvwaDataSourceMetaDataProvider;
import com.jiuqi.nr.bql.extend.NvwaDataSourceTableAdapter;
import com.jiuqi.nr.bql.extend.NvwaDataSourceTableQueryExecuter;
import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.AbstractDataTableAdapter;
import com.jiuqi.nr.query.datascheme.extend.IDataTableFactory;
import com.jiuqi.nr.query.datascheme.extend.IDataTableMetaDataProvider;
import com.jiuqi.nr.query.datascheme.extend.IDataTableQueryExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NvwaDataSourceTableFactory
implements IDataTableFactory {
    public static final String TYPE = "nvwaDataSource";
    public static final String TITLE = "\u5973\u5a32\u6570\u636e\u6e90";
    @Autowired
    private NvwaDataSourceTableAdapter adapter;
    @Autowired
    private NvwaDataSourceMetaDataProvider metaDataProvider;
    @Autowired
    private ComponentSet componentSet;

    public IDataTableMetaDataProvider getMetaDataProvider() {
        return this.metaDataProvider;
    }

    public IDataTableQueryExecutor createQueryExecuter(String srcKey) throws DataTableAdaptException {
        try {
            return new NvwaDataSourceTableQueryExecuter(this.componentSet, srcKey);
        }
        catch (DataSetStorageException e) {
            throw new DataTableAdaptException(e.getMessage(), (Exception)((Object)e));
        }
    }

    public AbstractDataTableAdapter getDataTableAdapter() {
        return this.adapter;
    }

    public String getTitle() {
        return TITLE;
    }

    public String getType() {
        return TYPE;
    }
}

