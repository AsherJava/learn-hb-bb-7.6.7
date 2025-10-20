/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.common;

import java.util.ArrayList;
import java.util.List;

public class EntityChangeInfo {
    public static final int TYPE_TASK = 1;
    public static final int TYPE_FORMSCHEM = 2;
    public static final int TYPE_FORM = 3;
    public static final int TYPE_REGION = 4;
    public boolean hasChange;
    public String oldKeys;
    public String newKeys;
    public int type;
    public List<String> removeKeys = new ArrayList<String>();
    public List<String> addKeys = new ArrayList<String>();
}

