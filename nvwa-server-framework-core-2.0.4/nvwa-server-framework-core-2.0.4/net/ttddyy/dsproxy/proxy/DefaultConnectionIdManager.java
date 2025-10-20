/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.proxy;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import net.ttddyy.dsproxy.ConnectionIdManager;

public class DefaultConnectionIdManager
implements ConnectionIdManager {
    private final AtomicLong idCounter = new AtomicLong(0L);

    @Override
    public String getId(Connection connection) {
        return String.valueOf(this.idCounter.incrementAndGet());
    }

    @Override
    public void addClosedId(String closedId) {
    }

    @Override
    public Set<String> getOpenConnectionIds() {
        return new HashSet<String>();
    }
}

