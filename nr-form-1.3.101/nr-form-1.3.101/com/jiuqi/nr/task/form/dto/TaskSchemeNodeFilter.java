/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataGroup
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.ISchemeNode
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.spi.NodeFilter
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.common.FormType
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataGroup;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.ISchemeNode;
import com.jiuqi.nr.datascheme.api.core.NodeType;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.spi.NodeFilter;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.common.FormType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TaskSchemeNodeFilter
implements NodeFilter {
    private final String dataSchemeKey;
    private final Set<String> dims = new HashSet<String>();
    private IDesignDataSchemeService designDataSchemeService;
    private FormType formType;
    private boolean isTestDim = true;
    private List<DesignDataTable> allDataTable = new ArrayList<DesignDataTable>();
    private List<DesignDataGroup> allDataGroup = new ArrayList<DesignDataGroup>();

    public boolean test(ISchemeNode t) {
        int type = t.getType();
        if (this.isTestDim && NodeType.DIM.getValue() == type) {
            return this.dims.contains(t.getKey());
        }
        if (NodeType.GROUP.getValue() == type && FormType.FORM_TYPE_NEWFMDM.equals((Object)this.formType)) {
            this.allDataTable.clear();
            this.allDataGroup.clear();
            this.allDataTable = this.designDataSchemeService.getAllDataTable(this.dataSchemeKey);
            this.allDataGroup = this.designDataSchemeService.getAllDataGroup(this.dataSchemeKey);
            return this.isShowNode(t.getKey());
        }
        return true;
    }

    private boolean isShowNode(String groupKey) {
        List<DesignDataTable> tableByGroup = this.getTableByGroup(groupKey);
        if (tableByGroup.size() != 0) {
            boolean fixTableHas = false;
            for (DesignDataTable designDataTable : tableByGroup) {
                if (!DataTableType.TABLE.equals((Object)designDataTable.getDataTableType())) continue;
                fixTableHas = true;
                break;
            }
            return fixTableHas;
        }
        List<DesignDataGroup> groupByGroup = this.getGroupByGroup(groupKey);
        if (groupByGroup.size() != 0) {
            boolean allGroupHas = false;
            for (DesignDataGroup designDataGroup : groupByGroup) {
                if (!this.isShowNode(designDataGroup.getKey())) continue;
                allGroupHas = true;
                break;
            }
            return allGroupHas;
        }
        return false;
    }

    private List<DesignDataTable> getTableByGroup(String groupKey) {
        return this.allDataTable.stream().filter(e -> groupKey.equals(e.getDataGroupKey())).collect(Collectors.toList());
    }

    private List<DesignDataGroup> getGroupByGroup(String groupKey) {
        return this.allDataGroup.stream().filter(e -> groupKey.equals(e.getParentKey())).collect(Collectors.toList());
    }

    public TaskSchemeNodeFilter(List<String> unitKey, String dataSchemeKey, Collection<DesignDataDimension> dims, FormType formType, IDesignDataSchemeService designDataSchemeService, boolean isTestDim) {
        this.dataSchemeKey = dataSchemeKey;
        this.designDataSchemeService = designDataSchemeService;
        this.formType = formType;
        this.isTestDim = isTestDim;
        dims.forEach(r -> {
            DimensionType dimensionType = r.getDimensionType();
            if (dimensionType == DimensionType.PERIOD) {
                return;
            }
            String dimKey = r.getDimKey();
            if (dimensionType == DimensionType.DIMENSION && formType != null && FormType.FORM_TYPE_NEWFMDM.getValue() != formType.getValue()) {
                this.dims.add(dataSchemeKey + ":" + dimKey);
            }
            if (unitKey.contains(dimKey)) {
                this.dims.add(dataSchemeKey + ":" + dimKey);
            }
        });
    }
}

