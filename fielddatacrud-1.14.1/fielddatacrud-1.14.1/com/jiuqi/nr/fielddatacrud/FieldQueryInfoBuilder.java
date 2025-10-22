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
import com.jiuqi.nr.fielddatacrud.IFieldQueryInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import org.springframework.util.CollectionUtils;

public final class FieldQueryInfoBuilder {
    private DimensionCollection dimensionCollection;
    private final List<String> selectFields = new ArrayList<String>();
    private LinkedHashSet<RowFilter> rowFilter;
    private LinkedHashSet<FieldSort> sorts;
    private PageInfo pageInfo;
    private List<Variable> variables;
    private boolean desensitized = false;
    private ResouceType resouceType = ResouceType.FORM;

    private FieldQueryInfoBuilder(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    public static FieldQueryInfoBuilder create(DimensionCollection dimension) {
        return new FieldQueryInfoBuilder(dimension);
    }

    public FieldQueryInfoBuilder select(String fieldKey) {
        this.selectFields.add(fieldKey);
        return this;
    }

    public FieldQueryInfoBuilder select(List<String> fieldKeys) {
        this.selectFields.addAll(fieldKeys);
        return this;
    }

    public FieldQueryInfoBuilder where(RowFilter filter) {
        if (this.rowFilter == null) {
            this.rowFilter = new LinkedHashSet();
        }
        this.rowFilter.add(filter);
        return this;
    }

    public FieldQueryInfoBuilder orderBy(FieldSort fieldSort) {
        if (this.sorts == null) {
            this.sorts = new LinkedHashSet();
        }
        this.sorts.add(fieldSort);
        return this;
    }

    public FieldQueryInfoBuilder setAuthType(ResouceType type) {
        this.resouceType = type;
        return this;
    }

    public FieldQueryInfoBuilder setPage(PageInfo page) {
        this.pageInfo = page;
        return this;
    }

    public FieldQueryInfoBuilder addVariable(Variable variable) {
        if (this.variables == null) {
            this.variables = new ArrayList<Variable>();
        }
        this.variables.add(variable);
        return this;
    }

    public void setDesensitized(boolean desensitized) {
        this.desensitized = desensitized;
    }

    public FieldQueryInfoBuilder setPage(int rowsPerPage, int pageIndex) {
        this.pageInfo = new PageInfo();
        this.pageInfo.setPageIndex(pageIndex);
        this.pageInfo.setRowsPerPage(rowsPerPage);
        return this;
    }

    private FieldQueryInfoBuilder() {
    }

    public IFieldQueryInfo build() {
        if (CollectionUtils.isEmpty(this.selectFields)) {
            throw new IllegalArgumentException("\u8bf7\u5148\u8bbe\u7f6e\u8981\u5bfc\u51fa\u7684\u6307\u6807");
        }
        return new IFieldQueryInfo(){

            @Override
            public DimensionCollection getDimensionCollection() {
                return FieldQueryInfoBuilder.this.dimensionCollection;
            }

            @Override
            public Iterator<String> selectFieldItr() {
                return FieldQueryInfoBuilder.this.selectFields.iterator();
            }

            @Override
            public Iterator<RowFilter> rowFilterItr() {
                if (FieldQueryInfoBuilder.this.rowFilter == null) {
                    return null;
                }
                return FieldQueryInfoBuilder.this.rowFilter.iterator();
            }

            @Override
            public Iterator<FieldSort> fieldSortItr() {
                if (FieldQueryInfoBuilder.this.sorts == null) {
                    return null;
                }
                return FieldQueryInfoBuilder.this.sorts.iterator();
            }

            @Override
            public Iterator<Variable> variableItr() {
                if (FieldQueryInfoBuilder.this.variables == null) {
                    return null;
                }
                return FieldQueryInfoBuilder.this.variables.iterator();
            }

            @Override
            public PageInfo getPageInfo() {
                return FieldQueryInfoBuilder.this.pageInfo;
            }

            @Override
            public ResouceType getAuthMode() {
                return FieldQueryInfoBuilder.this.resouceType;
            }

            @Override
            public boolean isDesensitized() {
                return FieldQueryInfoBuilder.this.desensitized;
            }
        };
    }
}

