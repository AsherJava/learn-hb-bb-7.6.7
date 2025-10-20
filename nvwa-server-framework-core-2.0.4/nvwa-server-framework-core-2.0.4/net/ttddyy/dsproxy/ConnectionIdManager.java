/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy;

import java.sql.Connection;
import java.util.Set;

public interface ConnectionIdManager {
    public String getId(Connection var1);

    public void addClosedId(String var1);

    public Set<String> getOpenConnectionIds();
}

