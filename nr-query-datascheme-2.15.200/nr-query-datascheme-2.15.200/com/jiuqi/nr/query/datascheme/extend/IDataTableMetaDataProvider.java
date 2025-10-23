/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.datascheme.extend;

import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.DataTableFolder;
import com.jiuqi.nr.query.datascheme.extend.DataTableNode;
import java.util.ArrayList;
import java.util.List;

public interface IDataTableMetaDataProvider {
    public List<DataTableNode> getTables(String var1) throws DataTableAdaptException;

    public List<DataTableFolder> getTableFolders(String var1) throws DataTableAdaptException;

    default public List<String> getTitlePath(String tableKey) throws DataTableAdaptException {
        return new ArrayList<String>();
    }

    default public List<String> getPath(String tableKey) throws DataTableAdaptException {
        return new ArrayList<String>();
    }

    default public List<DataTableNode> search(String keyStr) throws DataTableAdaptException {
        return new ArrayList<DataTableNode>();
    }

    default public List<DataTableNode> search(String parent, String keyStr) throws DataTableAdaptException {
        return this.search(keyStr);
    }
}

