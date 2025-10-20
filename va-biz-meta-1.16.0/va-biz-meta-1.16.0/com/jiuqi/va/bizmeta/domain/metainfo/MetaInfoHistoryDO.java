/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.meta.MetaInfoDO
 *  javax.persistence.Table
 *  javax.persistence.Transient
 */
package com.jiuqi.va.bizmeta.domain.metainfo;

import com.jiuqi.va.domain.meta.MetaInfoDO;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="META_INFO_VERSION")
public class MetaInfoHistoryDO
extends MetaInfoDO {
    private static final long serialVersionUID = 1L;
    @Transient
    private String info;

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

