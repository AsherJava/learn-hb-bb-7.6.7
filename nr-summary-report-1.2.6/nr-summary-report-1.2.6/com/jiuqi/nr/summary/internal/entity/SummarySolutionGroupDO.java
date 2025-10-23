/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBField
 *  com.jiuqi.nr.datascheme.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.summary.internal.entity;

import com.jiuqi.nr.datascheme.internal.anno.DBAnno;
import com.jiuqi.nr.summary.api.Ordered;
import com.jiuqi.nr.summary.api.SummarySolutionGroup;
import java.sql.Timestamp;
import java.time.Instant;

@DBAnno.DBTable(dbTable="NR_SUMMARY_SOLUTIONGROUP")
public class SummarySolutionGroupDO
implements SummarySolutionGroup {
    @DBAnno.DBField(dbField="SG_KEY", isPk=true)
    private String key;
    @DBAnno.DBField(dbField="SG_TITLE")
    private String title;
    @DBAnno.DBField(dbField="SG_PARENT")
    private String parent;
    @DBAnno.DBField(dbField="SG_DESC")
    private String desc;
    @DBAnno.DBField(dbField="SG_ORDER", notUpdate=true, isOrder=true)
    private String order;
    @DBAnno.DBField(dbField="SG_MODIFY_TIME", tranWith="transInstant", dbType=Timestamp.class, appType=Instant.class)
    private Instant modifyTime;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    @Override
    public Instant getModifyTime() {
        return this.modifyTime;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    @Override
    public String getParent() {
        return this.parent;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setName(String name) {
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public int compareTo(Ordered o) {
        if (o == null || o.getOrder() == null) {
            return 1;
        }
        if (this.order == null) {
            return -1;
        }
        return this.order.compareTo(o.getOrder());
    }
}

