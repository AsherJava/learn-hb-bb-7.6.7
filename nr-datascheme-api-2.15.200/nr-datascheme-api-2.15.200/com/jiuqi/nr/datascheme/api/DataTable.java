/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.TableGatherType
 *  com.jiuqi.np.definition.common.TableKind
 */
package com.jiuqi.nr.datascheme.api;

import com.jiuqi.np.definition.common.TableGatherType;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.nr.datascheme.api.common.EnumTransUtils;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.Ordered;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import java.util.Arrays;
import java.util.stream.Collectors;

public interface DataTable
extends Basic,
Ordered {
    public String getDataSchemeKey();

    public String getDataGroupKey();

    public DataTableType getDataTableType();

    public String[] getBizKeys();

    public String[] getGatherFieldKeys();

    public DataTableGatherType getDataTableGatherType();

    public String getVersion();

    public String getLevel();

    public Boolean getRepeatCode();

    default public boolean isRepeatCode() {
        return this.getRepeatCode() != null && this.getRepeatCode() != false;
    }

    public String getOwner();

    public Boolean getTrackHistory();

    default public boolean isTrackHistory() {
        return this.getTrackHistory() != null && this.getTrackHistory() != false;
    }

    @Deprecated
    default public String getBizKeyFieldsStr() {
        if (null == this.getBizKeys()) {
            return "";
        }
        return Arrays.stream(this.getBizKeys()).collect(Collectors.joining(";"));
    }

    @Deprecated
    default public TableKind getKind() {
        return TableKind.TABLE_KIND_BIZDATA;
    }

    @Deprecated
    default public String getDescription() {
        return this.getDesc();
    }

    @Deprecated
    default public TableGatherType getGatherType() {
        return EnumTransUtils.valueOf(this.getDataTableGatherType());
    }

    @Deprecated
    default public String[] getBizKeyFieldsID() {
        return this.getBizKeys();
    }

    @Deprecated
    default public String getOwnerLevelAndId() {
        return this.getLevel();
    }

    public Boolean getSyncError();

    public String getExpression();

    public String getAlias();
}

