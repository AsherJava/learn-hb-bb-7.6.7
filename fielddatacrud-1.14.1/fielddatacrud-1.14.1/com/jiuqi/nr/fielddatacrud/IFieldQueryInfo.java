/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.dataservice.core.access.ResouceType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.fielddatacrud;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.dataservice.core.access.ResouceType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.fielddatacrud.FieldSort;
import java.util.Iterator;

public interface IFieldQueryInfo {
    public DimensionCollection getDimensionCollection();

    public Iterator<String> selectFieldItr();

    public Iterator<RowFilter> rowFilterItr();

    public Iterator<FieldSort> fieldSortItr();

    public Iterator<Variable> variableItr();

    public PageInfo getPageInfo();

    public ResouceType getAuthMode();

    public boolean isDesensitized();
}

