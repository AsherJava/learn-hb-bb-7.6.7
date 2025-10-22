/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.data;

import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.data.IntData;
import com.jiuqi.nr.entity.engine.data.StringData;
import com.jiuqi.nr.entity.engine.exception.DataTypeException;

public final class BoolData
extends AbstractData {
    private static final long serialVersionUID = -2035128311228978195L;
    private boolean value;
    public static final BoolData NULL = new BoolData();
    public static final BoolData TRUE = new BoolData(true);
    public static final BoolData FALSE = new BoolData(false);

    public BoolData() {
        super(1, true);
        this.value = false;
    }

    public BoolData(boolean value) {
        super(1, false);
        this.value = value;
    }

    public BoolData(boolean value, boolean isNull) {
        super(1, isNull);
        this.value = isNull ? false : value;
    }

    public BoolData(boolean value, boolean isNull, double maxDouble) {
        super(1, isNull, maxDouble);
        this.value = isNull ? false : value;
    }

    @Override
    public boolean getAsBool() throws DataTypeException {
        return this.value;
    }

    @Override
    public int getAsInt() throws DataTypeException {
        return this.value ? 1 : 0;
    }

    @Override
    public String getAsString() {
        if (this.isNull) {
            return null;
        }
        return this.value ? "\u662f" : "\u5426";
    }

    @Override
    public Object getAsObject() throws DataTypeException {
        if (this.isNull) {
            return null;
        }
        return this.value ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public AbstractData convertTo(int dataType) throws DataTypeException {
        switch (dataType) {
            case 1: {
                return this;
            }
            case 4: {
                IntData result = new IntData(this.getAsInt(), this.isNull, this.maxDouble);
                return result;
            }
            case 6: {
                StringData result = new StringData(this.getAsString(), this.maxDouble);
                return result;
            }
        }
        throw new DataTypeException();
    }

    @Override
    public int compareTo(AbstractData another) throws DataTypeException {
        if (this.getAsNull() && another.getAsNull()) {
            return 0;
        }
        if (this.getAsNull()) {
            return -1;
        }
        if (another.getAsNull()) {
            return 1;
        }
        return (this.value ? 1 : 0) - (another.getAsBool() ? 1 : 0);
    }

    @Override
    public int compareSameType(AbstractData another) {
        if (this.getAsNull() && another.getAsNull()) {
            return 0;
        }
        if (this.getAsNull()) {
            return -1;
        }
        if (another.getAsNull()) {
            return 1;
        }
        return (this.value ? 1 : 0) - (((BoolData)another).value ? 1 : 0);
    }

    public int hashCode() {
        return (this.isNull ? 0 : 1) + (this.value ? 2 : 4);
    }
}

