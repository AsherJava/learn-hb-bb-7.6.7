/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

import java.sql.ResultSet;
import net.ttddyy.dsproxy.ConnectionInfo;
import net.ttddyy.dsproxy.proxy.ProxyConfig;
import net.ttddyy.dsproxy.proxy.ResultSetProxyLogic;
import net.ttddyy.dsproxy.proxy.SimpleResultSetProxyLogicFactory;

public interface ResultSetProxyLogicFactory {
    public static final ResultSetProxyLogicFactory DEFAULT = new SimpleResultSetProxyLogicFactory();

    public ResultSetProxyLogic create(ResultSet var1, ConnectionInfo var2, ProxyConfig var3);
}

