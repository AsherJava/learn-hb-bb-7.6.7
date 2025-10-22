/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.lwtree.provider;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.lwtree.response.INodeInfos;

public interface ITreeSearchProvider<E extends INode> {
    public INodeInfos<E> searchNode();
}

