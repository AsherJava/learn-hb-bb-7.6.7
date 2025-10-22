/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.ArrayKey
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.bi.util.ArrayKey;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import java.io.Serializable;

public class DataRegion
implements Serializable,
Comparable<DataRegion> {
    private static final long serialVersionUID = -4030354145269481729L;
    private boolean isAccount = false;
    private int funcCalcFlag = 0;
    private ArrayKey queryTables;
    private int hasPeriodModifier;
    private DimensionSet dimensions;
    private ENameSet tableNames;
    private boolean useStatResult;

    public DataRegion(DimensionSet dimensions, ArrayKey queryTables, ENameSet tableNames, int hasPeriodModifier, boolean isAccount) {
        this.setDimensions(new DimensionSet(dimensions));
        this.setQueryTables(queryTables);
        this.setHasPeriodModifier(hasPeriodModifier);
        this.tableNames = tableNames;
        this.isAccount = isAccount;
    }

    public DataRegion(DimensionSet dimensions, ArrayKey queryTables, int hasPeriodModifier, boolean isAccount) {
        this.setDimensions(new DimensionSet(dimensions));
        this.setQueryTables(queryTables);
        this.setHasPeriodModifier(hasPeriodModifier);
        this.isAccount = isAccount;
    }

    public DataRegion(DimensionSet dimensions, ArrayKey queryTables, boolean isAccount) {
        this.setDimensions(dimensions);
        this.setQueryTables(queryTables);
        this.setHasPeriodModifier(99);
        this.isAccount = isAccount;
    }

    public final int getHasPeriodModifier() {
        return this.hasPeriodModifier;
    }

    private void setHasPeriodModifier(int value) {
        this.hasPeriodModifier = value;
    }

    public final DimensionSet getDimensions() {
        return this.dimensions;
    }

    private void setDimensions(DimensionSet value) {
        this.dimensions = value;
    }

    public final boolean getUseStatResult() {
        return this.useStatResult;
    }

    public final void setUseStatResult(boolean value) {
        this.useStatResult = value;
    }

    @Override
    public final int compareTo(DataRegion another) {
        int result = this.funcCalcFlag - another.funcCalcFlag;
        if (result == 0) {
            result = this.getHasPeriodModifier() - another.getHasPeriodModifier();
        }
        if (result == 0) {
            result = this.getDimensions().compareTo(another.getDimensions());
        }
        if (result == 0 && (result = this.getQueryTables().compareTo(another.getQueryTables())) == 0 && !this.getQueryTables().equals((Object)another.getQueryTables())) {
            result = -1;
        }
        if (result == 0) {
            result = new Boolean(this.getUseStatResult()).compareTo(another.getUseStatResult());
        }
        return result;
    }

    public ENameSet getTableNames() {
        return this.tableNames;
    }

    public void setTableNames(ENameSet tableNames) {
        this.tableNames = tableNames;
    }

    public boolean isAccount() {
        return this.isAccount;
    }

    public void setAccount(boolean isAccount) {
        this.isAccount = isAccount;
    }

    public boolean isFuncCalc() {
        return this.funcCalcFlag != 0;
    }

    public int getFuncCalcFlag() {
        return this.funcCalcFlag;
    }

    public void setFuncCalcFlag(int funcCalcFlag) {
        this.funcCalcFlag = funcCalcFlag;
    }

    public ArrayKey getQueryTables() {
        return this.queryTables;
    }

    public void setQueryTables(ArrayKey queryTables) {
        this.queryTables = queryTables;
    }
}

