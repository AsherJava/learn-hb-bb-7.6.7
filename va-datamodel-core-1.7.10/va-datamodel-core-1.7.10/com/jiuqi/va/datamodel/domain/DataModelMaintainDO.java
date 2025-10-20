/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  javax.persistence.Table
 *  javax.persistence.Transient
 */
package com.jiuqi.va.datamodel.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.datamodel.domain.DataModelPublishedDO;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="DATAMODEL_DEFINE_MAINTAIN")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class DataModelMaintainDO
extends DataModelPublishedDO {
    private static final long serialVersionUID = 1L;
    @Transient
    private Integer state;

    public Integer getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}

