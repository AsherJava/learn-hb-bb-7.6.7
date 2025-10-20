/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.Table
 */
package com.jiuqi.va.bill.bd.core.domain;

import com.jiuqi.va.bill.bd.core.domain.ApplyRegMapConfigItemDTO;
import java.util.Date;
import javax.persistence.Table;

@Table(name="applyreg_mp_grp")
public class ApplyRegMapGroupDO
extends ApplyRegMapConfigItemDTO {
    private static final long serialVersionUID = 1L;
    private String description;
    private Date createtime;

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}

