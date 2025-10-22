/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.sql.type.Convert
 */
package com.jiuqi.nr.entity.engine.data;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.exception.DataTypeException;
import java.util.UUID;

public class GuidData
extends AbstractData {
    private static final long serialVersionUID = -3553515714128826967L;
    private UUID value;
    public static final GuidData NULL = new GuidData();

    public GuidData() {
        super(33, true, 0.0);
        this.value = null;
    }

    public GuidData(UUID value) {
        super(33, value == null);
        this.value = value;
    }

    public GuidData(String value) {
        super(33, StringUtils.isEmpty((String)value));
        this.value = Convert.toUUID((String)value);
    }

    @Override
    public String getAsString() {
        if (this.isNull) {
            return null;
        }
        return this.value.toString();
    }

    @Override
    public Object getAsObject() throws DataTypeException {
        if (this.isNull) {
            return null;
        }
        return this.value;
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
        return this.value.toString().compareTo(another.getAsString());
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
        return this.value.compareTo(((GuidData)another).value);
    }

    public int hashCode() {
        return this.isNull ? 0 : this.value.hashCode();
    }
}

