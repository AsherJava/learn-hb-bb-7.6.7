/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataGroupKind
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataGroupKind;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.internal.service.impl.CopyAttributes;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public abstract class AbstractCopyVisitor {
    @Autowired
    protected final IDesignDataSchemeService iDesignDataSchemeService;

    public AbstractCopyVisitor(IDesignDataSchemeService iDesignDataSchemeService) {
        this.iDesignDataSchemeService = iDesignDataSchemeService;
    }

    protected <DG extends DesignDataGroup> List<DesignDataGroupDO> doCopyGroup(List<DG> dataGroups, SchemeNode<CopyAttributes> ele) {
        if (dataGroups.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<DesignDataGroupDO> group = new ArrayList<DesignDataGroupDO>(dataGroups.size());
        for (DesignDataGroup dataGroup : dataGroups) {
            dataGroup.setKey(null);
            dataGroup.setDataSchemeKey(this.getDataScheme(ele).getKey());
            if (ele.getType() != NodeType.SCHEME.getValue()) {
                dataGroup.setParentKey(((CopyAttributes)ele.getOther()).getNewKey());
            } else {
                dataGroup.setParentKey(null);
            }
            dataGroup.setUpdateTime(Instant.now());
            dataGroup.setDataGroupKind(DataGroupKind.TABLE_GROUP);
            group.add(Convert.iDg2Do(dataGroup));
        }
        return group;
    }

    protected <DT extends DesignDataTable> List<DesignDataTableDO> doCopyTable(List<DT> dataTables, SchemeNode<CopyAttributes> ele) {
        if (dataTables.isEmpty()) {
            return Collections.emptyList();
        }
        DesignDataScheme newScheme = this.getDataScheme(ele);
        String oldPrefix = this.getSrcDataScheme(ele).getPrefix();
        String newPrefix = newScheme.getPrefix();
        ArrayList<DesignDataTableDO> tables = new ArrayList<DesignDataTableDO>(dataTables.size());
        for (DesignDataTable dataTable : dataTables) {
            dataTable.setKey(null);
            dataTable.setCode(AbstractCopyVisitor.getNewCode(oldPrefix, newPrefix, dataTable.getCode()));
            dataTable.setDataSchemeKey(newScheme.getKey());
            dataTable.setUpdateTime(Instant.now());
            if (ele.getType() != NodeType.SCHEME.getValue()) {
                dataTable.setDataGroupKey(((CopyAttributes)ele.getOther()).getNewKey());
            } else {
                dataTable.setDataGroupKey(null);
            }
            dataTable.setBizKeys(null);
            tables.add(Convert.iDt2Do(dataTable));
        }
        return tables;
    }

    private static String getNewCode(String oldPrefix, String newPrefix, String code) {
        if (StringUtils.hasText(oldPrefix)) {
            code = StringUtils.hasText(newPrefix) ? code.replaceFirst(oldPrefix, newPrefix) : code.replaceFirst(oldPrefix + "_", "");
        } else if (StringUtils.hasText(newPrefix)) {
            code = newPrefix + "_" + code;
        }
        return code;
    }

    protected abstract DataScheme getSrcDataScheme(SchemeNode<CopyAttributes> var1);

    protected abstract DesignDataScheme getDataScheme(SchemeNode<CopyAttributes> var1);
}

