/*
 * Decompiled with CFR 0.152.
 */
package net.ttddyy.dsproxy.transform;

import net.ttddyy.dsproxy.transform.ParameterReplacer;
import net.ttddyy.dsproxy.transform.TransformInfo;

public interface ParameterTransformer {
    default public void transformParameters(ParameterReplacer replacer, TransformInfo transformInfo) {
    }
}

