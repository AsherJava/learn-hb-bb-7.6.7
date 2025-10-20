/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.command;

import com.jiuqi.bi.database.sql.parser.Term;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public enum SQLCommandType {
    CREATE_TABLE,
    CREATE_VIEW,
    CREATE_INDEX,
    ALTER_TABLE,
    ALTER_INDEX,
    DROP_TABLE,
    DROP_VIEW,
    DROP_INDEX,
    INSERT,
    LOAD,
    SELECT,
    UPDATE,
    DELETE,
    TRUNCATE,
    EXTERNAL_CLASS,
    COMBINED,
    UNKNOWN;

    private static final Map<String, SQLCommandType> basic;
    private static Map<String, SQLCommandType> create;
    private static Map<String, SQLCommandType> alter;
    private static Map<String, SQLCommandType> drop;

    public boolean isDDLType() {
        return this == CREATE_TABLE || this == CREATE_VIEW || this == CREATE_INDEX || this == ALTER_TABLE || this == ALTER_INDEX || this == TRUNCATE || this == DROP_TABLE || this == DROP_VIEW || this == DROP_INDEX;
    }

    public boolean isDMLType() {
        return this == INSERT || this == LOAD || this == UPDATE || this == DELETE || this == SELECT;
    }

    public static SQLCommandType exploreType(List<Term> terms) {
        if (Objects.isNull(terms) || terms.isEmpty()) {
            return UNKNOWN;
        }
        String first = terms.get(0).text().toUpperCase();
        SQLCommandType type = basic.get(first);
        if (type == null) {
            return UNKNOWN;
        }
        if (type == UNKNOWN) {
            String second = terms.get(1).text().toUpperCase();
            if (first.equals("CREATE")) {
                if (second.equals("UNIQUE")) {
                    return CREATE_INDEX;
                }
                SQLCommandType v = create.get(second);
                return v == null ? UNKNOWN : v;
            }
            if (first.equals("ALTER")) {
                SQLCommandType v = alter.get(second);
                return v == null ? UNKNOWN : v;
            }
            if (first.equals("DROP")) {
                SQLCommandType v = drop.get(second);
                return v == null ? UNKNOWN : v;
            }
            return UNKNOWN;
        }
        return type;
    }

    static {
        basic = new HashMap<String, SQLCommandType>();
        create = new HashMap<String, SQLCommandType>();
        alter = new HashMap<String, SQLCommandType>();
        drop = new HashMap<String, SQLCommandType>();
        basic.put("CREATE", UNKNOWN);
        basic.put("ALTER", UNKNOWN);
        basic.put("DROP", UNKNOWN);
        create.put("TABLE", CREATE_TABLE);
        create.put("VIEW", CREATE_VIEW);
        create.put("INDEX", CREATE_INDEX);
        alter.put("TABLE", ALTER_TABLE);
        alter.put("INDEX", ALTER_INDEX);
        drop.put("TABLE", DROP_TABLE);
        drop.put("VIEW", DROP_VIEW);
        drop.put("INDEX", DROP_INDEX);
        basic.put("INSERT", INSERT);
        basic.put("DELETE", DELETE);
        basic.put("LOAD", LOAD);
        basic.put("SELECT", SELECT);
        basic.put("UPDATE", UPDATE);
        basic.put("TRUNCATE", TRUNCATE);
        basic.put("CALL", EXTERNAL_CLASS);
    }
}

