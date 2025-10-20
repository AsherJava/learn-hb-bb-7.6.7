/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.base.template.intf;

import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.EntBatchPreparedStatementSetter;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.EntPreparedStatementSetter;
import com.jiuqi.gcreport.definition.impl.basic.base.template.wapper.EntPreparedStatementWrapper;
import java.sql.SQLException;
import java.util.List;

public class PsSetterFactory {
    public static EntBatchPreparedStatementSetter newBatchSetter(final List<List<Object>> rows, final int size) {
        return new EntBatchPreparedStatementSetter(){

            @Override
            public void setValues(EntPreparedStatementWrapper ps, int i) throws SQLException {
                List row = (List)rows.get(i);
                for (int m = 1; m <= row.size(); ++m) {
                    Object value = row.get(m - 1);
                    ps.setObject(m, value);
                }
            }

            @Override
            public int getBatchSize() {
                return size;
            }
        };
    }

    public static EntPreparedStatementSetter newSetter(List<Object> values) {
        return ps -> {
            for (int m = 1; m <= values.size(); ++m) {
                Object value = values.get(m - 1);
                ps.setObject(m, value);
            }
        };
    }
}

