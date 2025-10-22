/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.deploy;

public interface IDeployObject {
    public void modify() throws Exception;

    public void commit() throws Exception;

    public void rollback() throws Exception;
}

