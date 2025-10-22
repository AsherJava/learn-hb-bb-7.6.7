/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.reader;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.reader.ICheckEventInfoReader;
import java.sql.ResultSet;

public abstract class JdbcCheckEventInfoReader
implements ICheckEventInfoReader {
    protected ResultSet rs;
    protected int fieldIndex;
    protected int dataType;

    public JdbcCheckEventInfoReader(ResultSet rs, int fieldIndex, int dataType) {
        this.rs = rs;
        this.fieldIndex = fieldIndex;
        this.dataType = dataType;
    }

    protected AbstractData readField(int fieldIndex) throws Exception {
        Object Obj = this.rs.getObject(fieldIndex + 1);
        AbstractData data = AbstractData.valueOf(Obj, this.dataType);
        return data;
    }
}

