/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.transform;

import net.ttddyy.dsproxy.transform.NoOpQueryTransformer;
import net.ttddyy.dsproxy.transform.TransformInfo;

public interface QueryTransformer {
    public static final QueryTransformer DEFAULT = new NoOpQueryTransformer();

    public String transformQuery(TransformInfo var1);
}

