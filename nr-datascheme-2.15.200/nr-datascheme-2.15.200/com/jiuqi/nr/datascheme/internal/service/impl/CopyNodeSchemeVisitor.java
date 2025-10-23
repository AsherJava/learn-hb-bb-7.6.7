/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.core.SchemeNode
 *  com.jiuqi.nr.datascheme.api.core.VisitorResult
 *  com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.core.SchemeNode;
import com.jiuqi.nr.datascheme.api.core.VisitorResult;
import com.jiuqi.nr.datascheme.api.loader.des.SchemeNodeVisitor;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.datascheme.internal.service.impl.AbstractCopyVisitor;
import com.jiuqi.nr.datascheme.internal.service.impl.CopyAttributes;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CopyNodeSchemeVisitor
extends AbstractCopyVisitor
implements SchemeNodeVisitor<CopyAttributes> {
    private final DesignDataScheme targetDataScheme;
    private DesignDataScheme srcDataScheme;
    private final String target;
    private final NodeType targetNodeType;
    private Set<String> ok = new HashSet<String>(64);
    private final Set<String> yourSelf = new HashSet<String>(32);

    public VisitorResult preVisitNode(SchemeNode<CopyAttributes> ele) {
        if (this.ok.contains(ele.getKey())) {
            return VisitorResult.CONTINUE;
        }
        if (this.yourSelf.contains(ele.getKey())) {
            this.ok.add(ele.getKey());
        }
        return null;
    }

    public CopyAttributes visitRootIsSchemeNode(DesignDataScheme scheme) {
        return null;
    }

    public CopyAttributes visitRootIsTableNode(DesignDataTable table) {
        String key = table.getKey();
        String dataSchemeKey = table.getDataSchemeKey();
        this.srcDataScheme = this.iDesignDataSchemeService.getDataScheme(dataSchemeKey);
        this.yourSelf.add(key);
        SchemeNode ele = new SchemeNode(this.target, this.targetNodeType.getValue());
        ele.setOther((Object)new CopyAttributes(this.target));
        HashMap<String, CopyAttributes> all = new HashMap<String, CopyAttributes>(1);
        this.doCopyTables((SchemeNode<CopyAttributes>)ele, Collections.singletonList(table), all);
        return (CopyAttributes)all.get(key);
    }

    public <DG extends DesignDataGroup, DS extends DesignDataScheme> Map<String, CopyAttributes> visitSchemeGroupNode(SchemeNode<CopyAttributes> next, List<DG> groups, List<DS> schemes) {
        return null;
    }

    public CopyAttributes visitRootIsGroupNode(DesignDataGroup group) {
        String key = group.getKey();
        String dataSchemeKey = group.getDataSchemeKey();
        this.srcDataScheme = this.iDesignDataSchemeService.getDataScheme(dataSchemeKey);
        this.yourSelf.add(key);
        SchemeNode ele = new SchemeNode(this.target, this.targetNodeType.getValue());
        ele.setOther((Object)new CopyAttributes(this.target));
        HashMap<String, CopyAttributes> all = new HashMap<String, CopyAttributes>(1);
        this.doCopyGroups((SchemeNode<CopyAttributes>)ele, Collections.singletonList(group), all);
        return (CopyAttributes)all.get(key);
    }

    public <DG extends DesignDataGroup, DT extends DesignDataTable, DM extends DesignDataDimension> Map<String, CopyAttributes> visitSchemeNode(SchemeNode<CopyAttributes> ele, List<DG> dataGroups, List<DT> dataTables, List<DM> dims) {
        return null;
    }

    public <DA extends ColumnModelDefine> void visitDimNode(SchemeNode<CopyAttributes> ele, List<DA> attributes) {
    }

    public <DG extends DesignDataGroup, DT extends DesignDataTable> Map<String, CopyAttributes> visitGroupNode(SchemeNode<CopyAttributes> ele, List<DG> dataGroups, List<DT> dataTables) {
        HashMap<String, CopyAttributes> all = new HashMap<String, CopyAttributes>(16);
        this.doCopyGroups(ele, dataGroups, all);
        this.doCopyTables(ele, dataTables, all);
        return all;
    }

    private <DG extends DesignDataGroup> void doCopyGroups(SchemeNode<CopyAttributes> ele, List<DG> dataGroups, Map<String, CopyAttributes> all) {
        ArrayList<String> oldKey = new ArrayList<String>(dataGroups.size());
        for (DesignDataGroup dataGroup : dataGroups) {
            String key = dataGroup.getKey();
            oldKey.add(key);
            this.yourSelf.add(key);
        }
        List<DesignDataGroupDO> list = super.doCopyGroup(dataGroups, ele);
        String[] newIds = this.iDesignDataSchemeService.insertDataGroups(list);
        for (int i = 0; i < newIds.length; ++i) {
            all.put((String)oldKey.get(i), new CopyAttributes(newIds[i]));
        }
    }

    private <DT extends DesignDataTable> void doCopyTables(SchemeNode<CopyAttributes> ele, List<DT> dataTables, Map<String, CopyAttributes> all) {
        ArrayList<String> keyTemp = new ArrayList<String>(dataTables.size());
        for (DesignDataTable table : dataTables) {
            this.yourSelf.add(table.getKey());
            keyTemp.add(table.getKey());
        }
        List<DesignDataTableDO> tables = super.doCopyTable(dataTables, ele);
        String[] tableKeys = this.iDesignDataSchemeService.insertDataTables(tables);
        for (int i = 0; i < tableKeys.length; ++i) {
            CopyAttributes copyAttributes = new CopyAttributes(tableKeys[i]);
            all.put((String)keyTemp.get(i), copyAttributes);
        }
    }

    protected <DF extends DesignDataField> void doCopyField(List<DF> dataFields, String schemeKey, String tableKey) {
        if (dataFields.isEmpty()) {
            return;
        }
        ArrayList<DesignDataFieldDO> fields = new ArrayList<DesignDataFieldDO>(dataFields.size());
        int userFieldCopy = DataFieldKind.FIELD_ZB.getValue() | DataFieldKind.FIELD.getValue() | DataFieldKind.TABLE_FIELD_DIM.getValue();
        for (DesignDataField field : dataFields) {
            DataFieldKind dataFieldKind = field.getDataFieldKind();
            if ((userFieldCopy & dataFieldKind.getValue()) == 0) continue;
            field.setKey(UUIDUtils.getKey());
            field.setDataSchemeKey(schemeKey);
            field.setDataTableKey(tableKey);
            field.setUpdateTime(Instant.now());
            fields.add(Convert.iDf2Do(field));
        }
        this.iDesignDataSchemeService.insertDataFields(fields);
    }

    @Override
    protected DataScheme getSrcDataScheme(SchemeNode<CopyAttributes> ele) {
        return this.srcDataScheme;
    }

    @Override
    protected DesignDataScheme getDataScheme(SchemeNode<CopyAttributes> ele) {
        return this.targetDataScheme;
    }

    public <DF extends DesignDataField> void visitTableNode(SchemeNode<CopyAttributes> ele, List<DF> dataFields) {
        if (dataFields.isEmpty()) {
            return;
        }
        CopyAttributes other = (CopyAttributes)ele.getOther();
        this.doCopyField(dataFields, this.targetDataScheme.getKey(), other.getNewKey());
    }

    public CopyNodeSchemeVisitor(String target, NodeType targetNodeType, DesignDataScheme targetDataScheme, IDesignDataSchemeService iDesignDataSchemeService) {
        super(iDesignDataSchemeService);
        this.target = target;
        this.targetNodeType = targetNodeType;
        this.targetDataScheme = targetDataScheme;
    }

    public Set<String> getOk() {
        return this.ok;
    }

    public void setOk(Set<String> ok) {
        this.ok = ok;
    }
}

