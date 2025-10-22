/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.reader;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import com.jiuqi.np.dataengine.reader.JdbcCheckEventInfoReader;
import java.sql.ResultSet;
import java.util.UUID;

public class JdbcEntityGuidReader
extends JdbcCheckEventInfoReader {
    public JdbcEntityGuidReader(ResultSet rs, int fieldIndex, int dataType) {
        super(rs, fieldIndex, dataType);
    }

    @Override
    public void readToEvent(FormulaCheckEventImpl event) throws Exception {
        AbstractData data = this.readField(this.fieldIndex);
        event.setEntityId((UUID)data.getAsObject());
    }
}

