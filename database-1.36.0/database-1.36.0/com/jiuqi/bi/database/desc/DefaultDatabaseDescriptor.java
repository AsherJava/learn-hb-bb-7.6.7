/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.desc;

import com.jiuqi.bi.database.desc.CaseSensitity;
import com.jiuqi.bi.database.desc.IDatabaseDescriptor;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class DefaultDatabaseDescriptor
implements IDatabaseDescriptor {
    protected static final Set<String> KEY_WORDS = Collections.unmodifiableSet(new TreeSet<String>(Arrays.asList("AUDIT", "GROUPS", "CREATE", "OLD", "TRIGGER", "AFTER", "MAXINSTANCES", "BETWEEN", "RAW", "MINVALUE", "CLOSE", "NUMBER_BASE", "MINUS", "EXPLAIN", "RECOVER", "PRAGMA", "PARTITION", "STORAGE", "SUBTYPE", "EACH", "STOP", "CONTROLFILE", "PRIOR", "SIZE", "SUM", "NOAUDIT", "RELEASE", "WHERE", "LIMITED", "UID", "AS", "MIN", "AT", "DATABASE", "EXCEPTION_INIT", "ROLES", "VARCHAR", "THEN", "XOR", "KEY", "ALTER", "MAXLOGMEMBERS", "INTO", "SET", "DELTA", "INDICATOR", "EXCEPTION", "CONSTRAINT", "PRECISION", "NOCOMPRESS", "ROLE", "SPACE", "ASC", "GROUP", "CRASH", "DELETE", "COLAUTH", "BY", "TEMPORARY", "CHARACTER", "VARIANCE", "UNLIMITED", "RESOURCE", "LONG", "PROCEDURE", "SNAPSHOT", "STATISTICS", "COBOL", "ANALYZE", "UNDER", "RUN", "DATA_BASE", "OPEN", "CONTINUE", "NOTFOUND", "REFERENCING", "CANCEL", "MANAGE", "CURRVAL", "TO", "FREELISTS", "UNION", "TRUNCATE", "CURSOR", "PARALLEL", "INDEXES", "SYSTEM", "STATEMENT", "ADD", "NOCACHE", "SQLERROR", "LOOP", "ARRAYLEN", "ROWTYPE", "DO", "VIEW", "IMMEDIATE", "DESC", "EXCEPTIONS", "VIEWS", "ARCHIVE", "PACKAGE", "CONSTRAINTS", "ADMIN", "INDEX", "REVERSE", "FOUND", "INTEGER", "DISABLE", "NOARCHIVELOG", "NUMBER", "ABORT", "MAXDATAFILES", "FOR", "UNIQUE", "TABAUTH", "CACHE", "BOOLEAN", "CURRENT", "USING", "EXEC", "AVG", "NOT", "ROWLABEL", "MAXVALUE", "END", "HAVING", "DELAY", "SQLSTATE", "POSITIVE", "QUOTA", "DIGITS", "DROP", "RETURN", "SOME", "FOREIGN", "SCHEMA", "SEQUENCE", "NEXT", "MINEXTENTS", "COLUMNS", "MANUAL", "RENAME", "EXCLUSIVE", "MOD", "CHAR_BASE", "EXISTS", "ASSERT", "FORM", "GO", "IDENTIFIED", "TIME", "INTERSECT", "SYSDATE", "ESCAPE", "WITH", "REUSE", "PCTUSED", "GRANT", "OTHERS", "NOMINVALUE", "MAXLOGFILES", "VALIDATE", "START", "FALSE", "NOMAXVALUE", "ROWID", "SECTION", "BINARY_INTEGER", "DEFAULT", "EXTERNALLY", "ROWNUM", "LOCK", "PLAN", "SHARE", "TABLE", "WHEN", "ELSE", "FLUSH", "NONE", "CLUSTER", "IF", "TYPE", "CYCLE", "LANGUAGE", "DEFINITION", "LISTS", "IN", "DISTINCT", "OPTION", "INSTANCE", "WHENEVER", "IS", "LEVEL", "VARCHAR2", "FUNCTION", "NOWAIT", "CASE", "OUT", "SYNONYM", "DISMOUNT", "OPTIMAL", "TERMINATE", "FORCE", "FORTRAN", "WORK", "CHECK", "PUBLIC", "COUNT", "REMR", "EXIT", "GOTO", "MAX", "NOSORT", "CASCADE", "CHAR", "TRANSACTION", "CONNECT", "BEGIN", "BECOME", "ASSIGN", "OFF", "COMMENT", "WRITE", "ORDER", "ELSIF", "UPDATE", "VALUES", "DOUBLE", "FILE", "COMPILE", "FETCH", "NUMERIC", "SEGMENT", "REVOKE", "MODIFY", "USE", "OWN", "CONSTANT", "SESSION", "NOCYCLE", "RAISE", "SELECT", "SEPARATE", "ONLINE", "TABLES", "PRIVATE", "EXECUTE", "DEBUGOFF", "INCREMENT", "MAXEXTENTS", "STDDEV", "DATAFILE", "NOORDER", "CHECKPOINT", "PCTINCREASE", "SQLERRM", "LAYER", "AUTHORIZATION", "THREAD", "NORMAL", "NEW", "ALL", "DBA", "EVENTS", "ARRAY", "TRIGGERS", "MODE", "COLUMN", "RECORD", "ONLY", "DECIMAL", "FROM", "TRACING", "COMPRESS", "DISPOSE", "BACKUP", "LOGFILE", "ALLOCATE", "EXTENT", "MAXLOGHISTORY", "INCLUDING", "MOUNT", "RESETLOGS", "NORESETLOGS", "TASK", "NULL", "ENTRY", "BLOCK", "TRUE", "PCTFREE", "ENABLE", "EXCEPT", "TABLESPACE", "SQL", "PRIVILEGES", "READ", "DATE", "MODULE", "ACCEPT", "GENERIC", "LIKE", "CLUSTERS", "SUCCESSFUL", "SQLCODE", "AND", "REAL", "SORT", "SWITCH", "INSERT", "ROW", "BODY", "OFFLINE", "RANGE", "FLOAT", "MLSLABEL", "INITIAL", "PLI", "ANY", "RESTRICTED", "NEXTVAL", "ROLLBACK", "INT", "CONTENTS", "NATURAL", "SHARED", "PROFILE", "DUMP", "STATEMENT_ID", "OF", "LINK", "CHANGE", "MAXTRANS", "ACCESS", "SQLBUF", "ON", "DEC", "OR", "COMMIT", "PRIMARY", "ARCHIVELOG", "SAVEPOINT", "UNTIL", "USER", "BEFORE", "DECLARE", "FREELIST", "INITRANS", "SMALLINT", "BASE_TABLE", "WHILE", "DEBUGON", "REFERENCES", "SCN", "ROWS")));
    private static final String[] KEYWORD_QUOTES = new String[]{"\"", "\""};

    @Override
    public boolean supportEmptyString() {
        return true;
    }

    @Override
    public boolean supportInnerOrderBy() {
        return false;
    }

    @Override
    public boolean supportLookupField() {
        return true;
    }

    @Override
    public boolean supportLookupOrderBy() {
        return this.supportLookupField();
    }

    @Override
    public boolean supportOrderNulls() {
        return true;
    }

    @Override
    public boolean supportWithClause() {
        return true;
    }

    @Override
    public boolean supportFullJoin() {
        return true;
    }

    @Override
    public String getStrcatOperator() {
        return "+";
    }

    @Override
    public String getStrcatFunction() {
        return null;
    }

    @Override
    public String getNVLName() {
        return "NVL";
    }

    @Override
    public boolean supportFastInsert() {
        return false;
    }

    @Override
    public boolean supportMerge() {
        return false;
    }

    @Override
    public boolean supportDropIfExists() {
        return false;
    }

    @Override
    public boolean supportCreateIfNotExists() {
        return false;
    }

    @Override
    public boolean supportWindowFunctions() {
        return false;
    }

    @Override
    public int defaultPort() {
        return -1;
    }

    @Override
    public Set<String> getKeyWords() {
        return KEY_WORDS;
    }

    @Override
    public final boolean isKeyword(String word) {
        return this.getKeyWords().contains(word.toUpperCase());
    }

    @Override
    public String storageMode() {
        return "row";
    }

    @Override
    public boolean supportView() {
        return true;
    }

    @Override
    public CaseSensitity getCaseSensitity() {
        return CaseSensitity.DEFAULT;
    }

    @Override
    public String[] getKeywordQuotes() {
        return KEYWORD_QUOTES;
    }
}

