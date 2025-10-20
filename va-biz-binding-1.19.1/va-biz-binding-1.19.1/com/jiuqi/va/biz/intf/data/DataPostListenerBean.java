/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataPostEvent;

public interface DataPostListenerBean
extends DataPostEvent {
    default public void beforeSave(Data data) {
    }

    default public void afterSave(Data data) {
    }

    default public void beforeDelete(Data data) {
    }

    default public void afterDelete(Data data) {
    }
}

