/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.definition.internal.impl.DesignTableDefineImpl
 */
package com.jiuqi.np.definition.impl.internal;

import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.definition.internal.impl.DesignTableDefineImpl;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

public class TableDefineImpl
extends DesignTableDefineImpl
implements TableDefine {
    private static final long serialVersionUID = 4849718008534907412L;
    private DataTable dataTable;
    private String[] bizKeyFields;

    public TableDefineImpl(DataTable dataTable) {
        this.dataTable = dataTable;
    }

    public DataTable getDataTable() {
        return this.dataTable;
    }

    public String getBizKeyFieldsStr() {
        String bizKeyFieldsStr = super.getBizKeyFieldsStr();
        if (!StringUtils.hasLength(bizKeyFieldsStr) && null != this.bizKeyFields && 0 != this.bizKeyFields.length) {
            bizKeyFieldsStr = Arrays.stream(this.bizKeyFields).collect(Collectors.joining(";"));
            this.setBizKeyFields(bizKeyFieldsStr);
        }
        return bizKeyFieldsStr;
    }

    public void setBizKeyFields(String[] bizKeysArray) {
        this.bizKeyFields = bizKeysArray;
    }

    public String[] getBizKeyFieldsID() {
        if (null == this.bizKeyFields) {
            this.bizKeyFields = super.getBizKeyFieldsID();
        }
        return this.bizKeyFields;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj) {
            return false;
        }
        if (obj instanceof TableDefine) {
            return Objects.equals(((TableDefine)obj).getKey(), this.getKey());
        }
        return false;
    }

    public int hashCode() {
        return null == this.getKey() ? 0 : this.getKey().hashCode();
    }
}

