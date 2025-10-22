/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table;

import com.jiuqi.nr.batch.summary.service.table.IPowerTableColumn;
import com.jiuqi.nr.batch.summary.service.table.IPowerTableColumnMap;
import com.jiuqi.nr.batch.summary.service.table.PowerTableColumn;
import java.util.Objects;

public class PowerTableColumnMap
implements IPowerTableColumnMap {
    private IPowerTableColumn ownerColumn;
    private IPowerTableColumn referColumn;

    public PowerTableColumnMap(String ownerColumnCode, String referColumnCode) {
        this.ownerColumn = new PowerTableColumn(ownerColumnCode);
        this.referColumn = new PowerTableColumn(referColumnCode);
    }

    @Override
    public IPowerTableColumn getOwnerColumn() {
        return this.ownerColumn;
    }

    public void setOwnerColumn(IPowerTableColumn ownerColumn) {
        this.ownerColumn = ownerColumn;
    }

    @Override
    public IPowerTableColumn getReferColumn() {
        return this.referColumn;
    }

    public void setReferColumn(IPowerTableColumn referColumn) {
        this.referColumn = referColumn;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        PowerTableColumnMap that = (PowerTableColumnMap)o;
        return Objects.equals(this.ownerColumn, that.ownerColumn) && Objects.equals(this.referColumn, that.referColumn);
    }

    public int hashCode() {
        return Objects.hash(this.ownerColumn, this.referColumn);
    }
}

