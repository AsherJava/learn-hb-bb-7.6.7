/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.trie.dat;

import com.jiuqi.bi.util.trie.dat.ByteArray;
import java.io.DataOutputStream;
import java.util.TreeMap;

public interface ITrie<V> {
    public int build(TreeMap<String, V> var1);

    public boolean save(DataOutputStream var1);

    public boolean load(ByteArray var1, V[] var2);

    public V get(char[] var1);

    public V get(String var1);

    public V[] getValueArray(V[] var1);

    public boolean containsKey(String var1);

    public int size();
}

