/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import net.ttddyy.dsproxy.proxy.ProxyLogicSupport;

public interface StatementMethodNames {
    public static final String PARAMETER_METHOD_SET_NULL = "setNull";
    public static final String PARAMETER_METHOD_REGISTER_OUT_PARAMETER = "registerOutParameter";
    public static final String GET_GENERATED_KEYS_METHOD = "getGeneratedKeys";
    public static final String GET_CONNECTION_METHOD = "getConnection";
    public static final String GET_RESULTSET_METHOD = "getResultSet";
    public static final Set<String> PARAMETER_METHODS = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("setArray", "setAsciiStream", "setBigDecimal", "setBinaryStream", "setBlob", "setBoolean", "setByte", "setBytes", "setCharacterStream", "setClob", "setDate", "setDouble", "setFloat", "setInt", "setLong", "setNull", "setObject", "setRef", "setShort", "setString", "setTime", "setTimestamp", "setUnicodeStream", "setURL", "setRowId", "setNString", "setNCharacterStream", "setNClob", "setSQLXML", "clearParameters", "registerOutParameter")));
    public static final Set<String> BATCH_PARAM_METHODS = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("addBatch", "clearBatch")));
    public static final Set<String> BATCH_EXEC_METHODS = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("executeBatch", "executeLargeBatch")));
    public static final Set<String> QUERY_EXEC_METHODS = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("executeQuery", "executeUpdate", "execute", "executeLargeUpdate")));
    public static final Set<String> EXEC_METHODS = Collections.unmodifiableSet(new HashSet<String>(){
        {
            this.addAll(BATCH_EXEC_METHODS);
            this.addAll(QUERY_EXEC_METHODS);
        }
    });
    public static final Set<String> JDBC4_METHODS = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("unwrap", "isWrapperFor")));
    public static final Set<String> METHODS_TO_RETURN_RESULTSET = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList("executeQuery", "getGeneratedKeys", "getResultSet")));
    public static final Set<String> METHODS_TO_INTERCEPT = Collections.unmodifiableSet(new HashSet<String>(){
        {
            this.addAll(PARAMETER_METHODS);
            this.addAll(BATCH_PARAM_METHODS);
            this.addAll(EXEC_METHODS);
            this.addAll(JDBC4_METHODS);
            this.addAll(METHODS_TO_RETURN_RESULTSET);
            this.addAll(ProxyLogicSupport.COMMON_METHOD_NAMES);
            this.add(StatementMethodNames.GET_CONNECTION_METHOD);
        }
    });
    public static final Set<String> METHODS_TO_OPERATE_PARAMETER = Collections.unmodifiableSet(new HashSet<String>(){
        {
            this.addAll(PARAMETER_METHODS);
            this.addAll(BATCH_PARAM_METHODS);
        }
    });
}

