/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.transform;

import net.ttddyy.dsproxy.transform.QueryTransformer;
import net.ttddyy.dsproxy.transform.TransformInfo;

public class NoOpQueryTransformer
implements QueryTransformer {
    @Override
    public String transformQuery(TransformInfo transformInfo) {
        return transformInfo.getQuery();
    }
}

