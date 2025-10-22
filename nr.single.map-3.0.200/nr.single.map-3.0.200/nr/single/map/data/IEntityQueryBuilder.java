/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IEntityQuery
 *  com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo
 *  com.jiuqi.np.dataengine.setting.AuthorityType
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodWrapper
 */
package nr.single.map.data;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IEntityQuery;
import com.jiuqi.np.dataengine.intf.impl.ReloadTreeInfo;
import com.jiuqi.np.dataengine.setting.AuthorityType;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.Date;
import java.util.List;

public class IEntityQueryBuilder {
    private IEntityQuery query;
    private EntityViewDefine view;
    private DimensionValueSet masterKeys = new DimensionValueSet();
    private PeriodWrapper period;

    public IEntityQueryBuilder(IDataAccessProvider dataAccessProvider, EntityViewDefine view) {
        this.view = view;
        this.query = dataAccessProvider.newEntityQuery();
    }

    public IEntityQuery buildQuery() {
        this.query.setEntityView(this.view);
        this.query.setMasterKeys(this.masterKeys);
        return this.query;
    }

    public IEntityQueryBuilder withMasterKeys(DimensionValueSet masterKeys) {
        if (masterKeys != null) {
            this.masterKeys = masterKeys;
            if (this.period != null && !masterKeys.hasValue("DATATIME")) {
                this.masterKeys.setValue("DATATIME", (Object)this.period.toString());
            }
        }
        return this;
    }

    public IEntityQueryBuilder withQueryColunms(List<FieldDefine> colunms) {
        if (colunms != null) {
            colunms.forEach(field -> this.query.addColumn(field));
        }
        return this;
    }

    public IEntityQueryBuilder ignoreParentView(boolean ignoreParentView) {
        this.query.setIgnoreParentView(ignoreParentView);
        return this;
    }

    public IEntityQueryBuilder withReloadTreeInfo(ReloadTreeInfo summrayInfo) {
        if (summrayInfo != null) {
            this.query.setReloadTreeInfo(summrayInfo);
        }
        return this;
    }

    public IEntityQueryBuilder withOrderField(FieldDefine orderField, boolean descending) {
        if (orderField != null) {
            this.query.addOrderByItem(orderField, descending);
        }
        return this;
    }

    public IEntityQueryBuilder withVStartDate(Date vStartDate) {
        if (vStartDate != null) {
            this.query.setQueryVersionStartDate(vStartDate);
        }
        return this;
    }

    public IEntityQueryBuilder withVEndDate(Date vEndDate) {
        if (vEndDate != null) {
            this.query.setQueryVersionDate(vEndDate);
        }
        return this;
    }

    public IEntityQueryBuilder withPeriodWrapper(PeriodWrapper period) {
        this.period = period;
        if (period != null && !this.masterKeys.hasValue("DATATIME")) {
            this.masterKeys.setValue("DATATIME", (Object)period.toString());
        }
        return this;
    }

    public IEntityQueryBuilder withRowFilter(String formula) {
        if (StringUtils.isNotEmpty((String)formula)) {
            this.query.setRowFilter(formula);
        }
        return this;
    }

    public IEntityQueryBuilder withAuthorityType(AuthorityType authType) {
        if (authType != null) {
            this.query.setAuthorityOperations(authType);
        }
        return this;
    }
}

