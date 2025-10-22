/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.state.dao.impl;

import com.jiuqi.nr.state.dao.IStateDao;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class StateDaoImpl
implements IStateDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(String tableName, Map<String, Object> insertStateRecord) {
        if (insertStateRecord == null) {
            return;
        }
        ArrayList<String> keyStr = new ArrayList<String>();
        ArrayList<Object> valueStr = new ArrayList<Object>();
        Object[] args = new Object[insertStateRecord.size()];
        int i = 0;
        for (Map.Entry<String, Object> record : insertStateRecord.entrySet()) {
            Object value;
            String key = record.getKey();
            args[i] = value = record.getValue();
            ++i;
            keyStr.add(key);
            valueStr.add(value);
        }
        String sql = this.buildDymicSql(keyStr, tableName);
        Object[] dymicArgs = this.buildDymicArgs(keyStr, valueStr);
        this.jdbcTemplate.update(sql, dymicArgs);
    }

    private Object[] buildDymicArgs(List<String> keyStr, List<Object> valueObject) {
        Object[] args = new Object[keyStr.size()];
        for (int i = 0; i < keyStr.size(); ++i) {
            if ("CREATETETIME".equals(keyStr.get(i))) {
                Instant time = (Instant)valueObject.get(i);
                args[i] = Timestamp.from(time);
                continue;
            }
            args[i] = valueObject.get(i);
        }
        return args;
    }

    private String buildDymicSql(List<String> keyStr, String tableName) {
        StringBuilder str = new StringBuilder();
        if (keyStr == null) {
            throw new RuntimeException("\u8868\u5b57\u6bb5\u4e3a\u7a7a!");
        }
        str.append("insert into ").append(tableName).append(" ( ");
        keyStr.forEach(e -> str.append((String)e).append(","));
        str.append(")");
        str.replace(str.length() - 2, str.length() - 1, " ");
        str.append(" values ").append("(");
        keyStr.forEach(e -> str.append("?").append(","));
        str.append(")");
        str.replace(str.length() - 2, str.length() - 1, " ");
        return str.toString();
    }

    private String insertSql(List<String> keyStr, String tableName, List<Object> valueStr) {
        StringBuilder str = new StringBuilder();
        if (keyStr == null) {
            return null;
        }
        str.append("insert into ").append(tableName).append(" ( ");
        for (int i = 0; i < keyStr.size(); ++i) {
            if (i == keyStr.size() - 1) {
                str.append(keyStr.get(i)).append(")");
                break;
            }
            str.append(keyStr.get(i)).append(",");
        }
        str.append(" values (");
        for (int j = 0; j < valueStr.size(); ++j) {
            if (j == valueStr.size() - 1) {
                Object appendStr = valueStr.get(j);
                if (keyStr.get(j).equals("CREATETETIME")) {
                    Instant time = (Instant)valueStr.get(j);
                    appendStr = time.toString();
                }
                str.append("'").append(appendStr).append("'").append(")");
                break;
            }
            str.append("'").append(valueStr.get(j)).append("'").append(",");
        }
        return str.toString();
    }
}

