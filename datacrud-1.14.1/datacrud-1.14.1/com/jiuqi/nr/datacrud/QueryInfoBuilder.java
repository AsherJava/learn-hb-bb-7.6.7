/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.LinkSort;
import com.jiuqi.nr.datacrud.Measure;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datacrud.spi.filter.EntityDefaultValueFilter;
import com.jiuqi.nr.datacrud.spi.filter.FormulaFilter;
import com.jiuqi.nr.datacrud.spi.filter.LinkFilter;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

public final class QueryInfoBuilder {
    private RegionRelation relation;
    private String regionKey;
    private String formulaSchemeKey;
    private DimensionCombination dimension;
    private DimensionCollection dimensionCollection;
    private List<String> selectLink;
    private LinkedHashSet<RowFilter> rowFilter;
    private LinkedHashSet<LinkSort> sorts;
    private LinkedHashSet<String> groups;
    private Measure measure;
    private PageInfo pageInfo;
    private List<Variable> variables;
    private boolean enableEnumFill = true;
    private boolean desensitized = false;

    private QueryInfoBuilder() {
    }

    public QueryInfoBuilder(String regionKey, DimensionCombination dimension) {
        if (regionKey == null || dimension == null) {
            throw new IllegalArgumentException("\u533a\u57df\u53c2\u6570\u5fc5\u4f20");
        }
        this.regionKey = regionKey;
        this.dimension = dimension;
        RegionRelationFactory relationFactory = (RegionRelationFactory)SpringBeanUtils.getBean(RegionRelationFactory.class);
        this.relation = relationFactory.getRegionRelation(regionKey);
    }

    public QueryInfoBuilder(String regionKey, DimensionCollection dimension) {
        if (regionKey == null || dimension == null) {
            throw new IllegalArgumentException("\u533a\u57df\u53c2\u6570\u5fc5\u4f20");
        }
        this.regionKey = regionKey;
        RegionRelationFactory relationFactory = (RegionRelationFactory)SpringBeanUtils.getBean(RegionRelationFactory.class);
        this.relation = relationFactory.getRegionRelation(regionKey);
        this.dimensionCollection = dimension;
    }

    public static QueryInfoBuilder create(String regionKey, DimensionCombination dimension) {
        return new QueryInfoBuilder(regionKey, dimension);
    }

    public static QueryInfoBuilder create(String regionKey, DimensionCollection dimension) {
        return new QueryInfoBuilder(regionKey, dimension);
    }

    public QueryInfoBuilder select(String linkKey) {
        if (this.selectLink == null) {
            this.selectLink = new ArrayList<String>();
        }
        this.selectLink.add(linkKey);
        return this;
    }

    public QueryInfoBuilder where(RowFilter filter) {
        if (this.rowFilter == null) {
            this.rowFilter = new LinkedHashSet();
        }
        if (filter == null) {
            return this;
        }
        if (filter instanceof LinkFilter) {
            ((LinkFilter)filter).setRelation(this.relation);
        }
        this.rowFilter.add(filter);
        return this;
    }

    public QueryInfoBuilder whereRegionFilter() {
        EntityDefaultValueFilter entityDefaultValueFilter;
        String formula;
        DataRegionDefine regionDefine = this.relation.getRegionDefine();
        String filterCondition = regionDefine.getFilterCondition();
        if (StringUtils.hasLength(filterCondition)) {
            FormulaFilter filter = new FormulaFilter(filterCondition);
            this.where(filter);
        }
        if (StringUtils.hasLength(formula = (entityDefaultValueFilter = new EntityDefaultValueFilter(this.relation)).toFormula())) {
            FormulaFilter filter = new FormulaFilter(formula);
            this.where(filter);
        }
        return this;
    }

    public QueryInfoBuilder orderBy(LinkSort linkSort) {
        if (this.sorts == null) {
            this.sorts = new LinkedHashSet();
        }
        this.sorts.add(linkSort);
        return this;
    }

    public void orderRegionDefaultOrder() {
        if (this.sorts == null) {
            List<LinkSort> regionDefaultOrder = this.relation.getRegionDefaultOrder();
            for (LinkSort linkSort : regionDefaultOrder) {
                this.orderBy(linkSort);
            }
        }
    }

    public QueryInfoBuilder groupBy(String link) {
        if (this.groups == null) {
            this.groups = new LinkedHashSet();
        }
        this.groups.add(link);
        return this;
    }

    public QueryInfoBuilder setPage(PageInfo page) {
        this.pageInfo = page;
        return this;
    }

    public QueryInfoBuilder setPage(int rowsPerPage, int pageIndex) {
        this.pageInfo = new PageInfo();
        this.pageInfo.setRowsPerPage(rowsPerPage);
        this.pageInfo.setPageIndex(pageIndex);
        return this;
    }

    public QueryInfoBuilder setMeasure(Measure measure) {
        this.measure = measure;
        return this;
    }

    @Deprecated
    public QueryInfoBuilder setVariable(Map<String, Object> variable) {
        if (this.variables == null) {
            this.variables = new ArrayList<Variable>();
        }
        for (Map.Entry<String, Object> entry : variable.entrySet()) {
            Object value = entry.getValue();
            if (value == null) continue;
            Variable var = new Variable(entry.getKey(), 6);
            var.setVarValue(value);
            this.variables.add(var);
        }
        return this;
    }

    public QueryInfoBuilder addVariable(Variable variable) {
        if (this.variables == null) {
            this.variables = new ArrayList<Variable>();
        }
        this.variables.add(variable);
        return this;
    }

    public QueryInfoBuilder setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
        return this;
    }

    public void setEnableEnumFill(boolean enableEnumFill) {
        this.enableEnumFill = enableEnumFill;
    }

    public void setDesensitized(boolean desensitized) {
        this.desensitized = desensitized;
    }

    public IQueryInfo build() {
        return new QueryInfo();
    }

    private class QueryInfo
    implements IQueryInfo {
        private QueryInfo() {
        }

        @Override
        public String getRegionKey() {
            return QueryInfoBuilder.this.regionKey;
        }

        @Override
        public DimensionCombination getDimensionCombination() {
            return QueryInfoBuilder.this.dimension;
        }

        @Override
        public DimensionCollection getDimensionCollection() {
            if (QueryInfoBuilder.this.dimensionCollection == null && QueryInfoBuilder.this.dimension != null) {
                DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
                for (FixedDimensionValue fixed : QueryInfoBuilder.this.dimension) {
                    builder.setEntityValue(fixed.getName(), fixed.getEntityID(), new Object[]{fixed.getValue()});
                }
                QueryInfoBuilder.this.dimensionCollection = builder.getCollection();
            }
            return QueryInfoBuilder.this.dimensionCollection;
        }

        @Override
        public Iterator<String> selectLinkItr() {
            if (QueryInfoBuilder.this.selectLink == null) {
                return null;
            }
            return QueryInfoBuilder.this.selectLink.iterator();
        }

        @Override
        public Iterator<RowFilter> rowFilterItr() {
            if (QueryInfoBuilder.this.rowFilter == null) {
                return null;
            }
            return QueryInfoBuilder.this.rowFilter.iterator();
        }

        @Override
        public Iterator<LinkSort> linkSortItr() {
            if (QueryInfoBuilder.this.sorts == null) {
                return QueryInfoBuilder.this.relation.getRegionDefaultOrder().iterator();
            }
            return QueryInfoBuilder.this.sorts.iterator();
        }

        @Override
        public Iterator<String> groupItr() {
            if (QueryInfoBuilder.this.groups == null) {
                return null;
            }
            return QueryInfoBuilder.this.groups.iterator();
        }

        @Override
        public Measure getMeasure() {
            return QueryInfoBuilder.this.measure;
        }

        @Override
        public PageInfo getPageInfo() {
            return QueryInfoBuilder.this.pageInfo;
        }

        @Override
        public Iterator<Variable> variableItr() {
            if (QueryInfoBuilder.this.variables == null) {
                return null;
            }
            return QueryInfoBuilder.this.variables.iterator();
        }

        @Override
        public RegionRelation getRegionRelation() {
            return QueryInfoBuilder.this.relation;
        }

        @Override
        public String getFormulaSchemeKey() {
            return QueryInfoBuilder.this.formulaSchemeKey;
        }

        @Override
        public boolean isEnableEnumFill() {
            return QueryInfoBuilder.this.enableEnumFill;
        }

        @Override
        public boolean isDesensitized() {
            return QueryInfoBuilder.this.desensitized;
        }
    }
}

