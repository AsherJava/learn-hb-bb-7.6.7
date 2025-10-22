/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.datacrud.IRegionInfo;
import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.Measure;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.Iterator;

public interface IQueryInfo
extends IRegionInfo {
    public DimensionCollection getDimensionCollection();

    public Iterator<String> selectLinkItr();

    public Iterator<RowFilter> rowFilterItr();

    public Iterator<LinkSort> linkSortItr();

    public Iterator<String> groupItr();

    public Measure getMeasure();

    public String getFormulaSchemeKey();

    public PageInfo getPageInfo();

    public Iterator<Variable> variableItr();

    @Override
    public RegionRelation getRegionRelation();

    public boolean isEnableEnumFill();

    public boolean isDesensitized();
}

