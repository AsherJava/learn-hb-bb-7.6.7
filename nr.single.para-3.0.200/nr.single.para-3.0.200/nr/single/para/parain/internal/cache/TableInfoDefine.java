/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 */
package nr.single.para.parain.internal.cache;

import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import java.util.ArrayList;
import java.util.List;

public class TableInfoDefine {
    private DesignTableDefine tableDefine;
    private DesignDataTable dataTable;
    private BaseDataDefineDO baseTable;
    private IPeriodEntity periodTable;
    private IEntityDefine entityDefine;
    private OrgCategoryDO orgTable;
    private Boolean hasData;

    public TableInfoDefine(DesignTableDefine tableDefine) {
        this.tableDefine = tableDefine;
    }

    public TableInfoDefine(DesignDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public TableInfoDefine(BaseDataDefineDO baseTable) {
        this.baseTable = baseTable;
    }

    public TableInfoDefine(IPeriodEntity periodTable) {
        this.periodTable = periodTable;
    }

    public TableInfoDefine(IEntityDefine entityDefine) {
        this.entityDefine = entityDefine;
    }

    public TableInfoDefine(OrgCategoryDO orgTable) {
        this.orgTable = orgTable;
    }

    public DesignTableDefine getTableDefine() {
        return this.tableDefine;
    }

    public void setTableDefine(DesignTableDefine tableDefine) {
        this.tableDefine = tableDefine;
    }

    public DesignDataTable getDataTable() {
        return this.dataTable;
    }

    public void setDataTable(DesignDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public IPeriodEntity getPeriodTable() {
        return this.periodTable;
    }

    public void setPeriodTable(IPeriodEntity periodTable) {
        this.periodTable = periodTable;
    }

    public String getKey() {
        if (this.tableDefine != null) {
            return this.tableDefine.getKey();
        }
        if (this.dataTable != null) {
            return this.dataTable.getKey();
        }
        if (this.baseTable != null) {
            return this.baseTable.getId().toString();
        }
        if (this.periodTable != null) {
            return this.periodTable.getKey();
        }
        if (this.entityDefine != null) {
            return this.entityDefine.getId();
        }
        if (this.orgTable != null) {
            return this.orgTable.getId().toString();
        }
        return null;
    }

    public String getCode() {
        if (this.tableDefine != null) {
            return this.tableDefine.getCode();
        }
        if (this.dataTable != null) {
            return this.dataTable.getCode();
        }
        if (this.baseTable != null) {
            return this.baseTable.getName();
        }
        if (this.periodTable != null) {
            return this.periodTable.getCode();
        }
        if (this.entityDefine != null) {
            return this.entityDefine.getCode();
        }
        if (this.orgTable != null) {
            return this.orgTable.getName();
        }
        return null;
    }

    public String getTitle() {
        if (this.tableDefine != null) {
            return this.tableDefine.getTitle();
        }
        if (this.dataTable != null) {
            return this.dataTable.getTitle();
        }
        if (this.baseTable != null) {
            return this.baseTable.getTitle();
        }
        if (this.periodTable != null) {
            return this.periodTable.getTitle();
        }
        if (this.entityDefine != null) {
            return this.entityDefine.getTitle();
        }
        if (this.orgTable != null) {
            return this.orgTable.getTitle();
        }
        return null;
    }

    public int getBizKeysCount() {
        if (this.tableDefine != null) {
            return 0;
        }
        if (this.dataTable != null) {
            if (this.dataTable.getBizKeys() != null) {
                return this.dataTable.getBizKeys().length;
            }
            return 0;
        }
        if (this.baseTable != null) {
            return 0;
        }
        if (this.periodTable != null) {
            return 0;
        }
        if (this.entityDefine != null) {
            return 0;
        }
        if (this.orgTable != null) {
            return 0;
        }
        return 0;
    }

    public static List<TableInfoDefine> getFieldInfos(List<DesignTableDefine> oldList) {
        ArrayList<TableInfoDefine> list = new ArrayList<TableInfoDefine>();
        if (oldList != null) {
            for (DesignTableDefine table : oldList) {
                list.add(new TableInfoDefine(table));
            }
        }
        return list;
    }

    public static List<TableInfoDefine> getFieldInfos2(List<DesignDataTable> oldList) {
        ArrayList<TableInfoDefine> list = new ArrayList<TableInfoDefine>();
        if (oldList != null) {
            for (DesignDataTable table : oldList) {
                list.add(new TableInfoDefine(table));
            }
        }
        return list;
    }

    public IEntityDefine getEntityDefine() {
        return this.entityDefine;
    }

    public void setEntityDefine(IEntityDefine entityDefine) {
        this.entityDefine = entityDefine;
    }

    public Boolean getHasData() {
        return this.hasData;
    }

    public void setHasData(Boolean hasData) {
        this.hasData = hasData;
    }
}

