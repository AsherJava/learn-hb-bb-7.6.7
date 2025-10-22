/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.reader;

import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.NodeAdapter;
import com.jiuqi.np.dataengine.reader.JdbcCheckEventInfoReader;
import java.sql.ResultSet;

public class JdbcCheckDynamicNodeReader
extends JdbcCheckEventInfoReader {
    private DynamicDataNode node;

    public JdbcCheckDynamicNodeReader(ResultSet rs, int fieldIndex, int dataType, DynamicDataNode node) {
        super(rs, fieldIndex, dataType);
        this.node = node;
    }

    @Override
    public void readToEvent(FormulaCheckEventImpl event) throws Exception {
        AbstractData data = this.readField(this.fieldIndex);
        NodeAdapter nodeAdapter = new NodeAdapter(this.node, data);
        event.getNodes().add(nodeAdapter);
    }
}

