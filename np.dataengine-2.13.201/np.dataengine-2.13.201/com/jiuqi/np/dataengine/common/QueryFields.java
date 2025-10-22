/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataRegion;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class QueryFields
implements Iterable<QueryField>,
Serializable {
    private static final long serialVersionUID = 7728571268991484018L;
    private LinkedHashSet<QueryField> list = new LinkedHashSet();
    private boolean hasLj;
    private String singleTableAlias = "";
    protected DataRegion cacheRegion;

    public final int getCount() {
        return this.list.size();
    }

    public final QueryField getItem(int index) {
        List queryFields = this.list.stream().collect(Collectors.toList());
        return (QueryField)queryFields.get(index);
    }

    public final boolean getHasLj() {
        return this.hasLj;
    }

    private void setHasLj(boolean value) {
        this.hasLj = value;
    }

    public final int indexOfFieldName(String fieldName) {
        int index = 0;
        for (QueryField queryField : this.list) {
            if (fieldName.equals(queryField.getFieldName())) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    public boolean hasField(QueryField queryField) {
        return this.list.contains(queryField);
    }

    public final boolean add(QueryField queryField) {
        boolean isNew;
        boolean bl = isNew = !this.list.contains(queryField);
        if (isNew) {
            this.list.add(queryField);
            if (queryField.getIsLj()) {
                this.hasLj = true;
            }
            this.setSingleTableAlias("");
        }
        return isNew;
    }

    public QueryField addOrGetExist(QueryField queryField) {
        boolean isNew;
        boolean bl = isNew = !this.list.contains(queryField);
        if (isNew) {
            this.list.add(queryField);
            if (queryField.getIsLj()) {
                this.hasLj = true;
            }
            this.setSingleTableAlias("");
            return queryField;
        }
        return queryField;
    }

    public final void addAll(QueryFields queryFields) {
        for (QueryField queryField : queryFields) {
            this.add(queryField);
        }
    }

    public final void clear() {
        this.list.clear();
        this.setHasLj(false);
        this.cacheRegion = null;
        this.singleTableAlias = "";
    }

    public final boolean hasSpecifiedField() {
        for (QueryField field : this.list) {
            if (field.getPeriodModifier() == null && field.getDimensionRestriction() == null) continue;
            return true;
        }
        return false;
    }

    public final String getSingleTableAlias() {
        if (StringUtils.isEmpty((String)this.singleTableAlias)) {
            for (QueryField field : this.list) {
                if (StringUtils.isEmpty((String)this.singleTableAlias)) {
                    this.singleTableAlias = field.getTable().getAlias();
                    continue;
                }
                if (this.singleTableAlias.equals(field.getTable().getAlias())) continue;
                this.singleTableAlias = null;
                break;
            }
        }
        return this.singleTableAlias;
    }

    public final void setSingleTableAlias(String value) {
        this.singleTableAlias = value;
    }

    public DataRegion getDataRegion() {
        int hasPeriodModifier = 0;
        DimensionSet dimensions = new DimensionSet();
        ArrayKey queryTables = new ArrayKey(this.list.stream().map(QueryField::getTable).distinct().toArray());
        ENameSet tableNames = new ENameSet();
        boolean isAccount = false;
        for (QueryField field : this.list) {
            QueryTable queryTable = field.getTable();
            if (queryTable.isAccountTable() || queryTable.isAccountRptTable()) {
                isAccount = true;
            }
            dimensions.addAll(queryTable.getOpenDimensions());
            tableNames.add(queryTable.getTableName());
            if (queryTable.getPeriodModifier() != null && hasPeriodModifier != 1 && hasPeriodModifier <= 50) {
                ++hasPeriodModifier;
            }
            if (!queryTable.getIsLj() || hasPeriodModifier >= 50) continue;
            hasPeriodModifier += 50;
        }
        return new DataRegion(dimensions, queryTables, tableNames, hasPeriodModifier, isAccount);
    }

    public DataRegion getExecRegion(DimensionSet masterDimensions) {
        if (this.cacheRegion == null) {
            this.cacheRegion = this.doGetExecRegion(masterDimensions);
        }
        return this.cacheRegion;
    }

    protected final DataRegion doGetExecRegion(DimensionSet masterDimensions) {
        DataRegion dataRegion = this.getDataRegion();
        HashSet<String> doneTables = new HashSet<String>();
        DimensionSet dimensions = new DimensionSet();
        dimensions.addAll(dataRegion.getDimensions());
        if (masterDimensions != null) {
            dimensions.removeAll(masterDimensions);
        }
        boolean isAccount = false;
        for (QueryField field : this.list) {
            QueryTable queryTable = field.getTable();
            if (queryTable.isAccountTable() || queryTable.isAccountRptTable()) {
                isAccount = true;
            }
            if (doneTables.contains(field.getRegion())) continue;
            DimensionValueSet restriction = queryTable.getDimensionRestriction();
            if (restriction != null) {
                int c = restriction.getDimensionSet().size();
                for (int i = 0; i < c; ++i) {
                    if (restriction.getValue(i) != DimensionValueSet.NoStrictDimValue) continue;
                    dimensions.addDimension(restriction.getName(i));
                }
            }
            doneTables.add(field.getRegion());
        }
        return new DataRegion(dimensions, dataRegion.getQueryTables(), dataRegion.getHasPeriodModifier(), isAccount);
    }

    public final void checkConsistency(DimensionSet masterDimensions) throws ExpressionException {
        if (this.list.size() <= 0) {
            return;
        }
        DimensionSet loopDimenisons = this.getExecRegion(masterDimensions).getDimensions();
        int count = this.getCount();
        boolean hasLoopLeader = false;
        boolean hasPeriod = masterDimensions.contains("DATATIME");
        for (QueryField field : this.list) {
            QueryTable queryTable = field.getTable();
            if (!hasPeriod && queryTable.getPeriodModifier() != null) {
                throw new ExpressionException("\u65f6\u671f\u7ef4\u5ea6\u6ca1\u6709\u786e\u5207\u503c\uff0c\u4e0d\u80fd\u8bbe\u7f6e\u65f6\u671f\u5c5e\u6027");
            }
            if (!queryTable.getOpenDimensions().containsAll(loopDimenisons)) continue;
            hasLoopLeader = true;
        }
        if (count > 0 && !hasLoopLeader) {
            throw new ExpressionException(String.format("\u4e0d\u652f\u6301\u8868\u95f4\u4ea4\u53c9\u67e5\u8be2\uff0c\u7f3a\u5c11\u5305\u542b\u6240\u6709\u7ef4\u5ea6\u96c6%s\u7684\u8868", loopDimenisons.toString()));
        }
    }

    @Override
    public Iterator<QueryField> iterator() {
        return this.list.iterator();
    }
}

