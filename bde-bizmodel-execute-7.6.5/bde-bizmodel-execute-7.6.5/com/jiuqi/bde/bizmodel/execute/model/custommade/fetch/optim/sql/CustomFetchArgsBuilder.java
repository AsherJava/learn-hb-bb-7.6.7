/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.common.intf.FetchTaskContext
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.sql;

import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchArgs;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchCondi;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchExecuteSetting;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchFieldMeta;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.sql.CustomFetchCondiFieldHolder;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.sql.CustomFetchFixedConditionHolder;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.sql.CustomFetchGroupFieldHolder;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.sql.CustomFetchSelectFieldHolder;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.sql.CustomFetchTableHolder;
import com.jiuqi.bde.common.intf.FetchTaskContext;
import java.util.List;

public class CustomFetchArgsBuilder {
    private FetchTaskContext fetchTaskContext;
    private CustomBizModelDTO bizModel;
    private List<CustomFetchExecuteSetting> fetchSettingList;
    private CustomFetchTableHolder fetchTableHolder;
    private CustomFetchSelectFieldHolder selectFieldHolder;
    private CustomFetchFixedConditionHolder fixedConditionHolder;
    private CustomFetchCondiFieldHolder condiFieldHolder;
    private CustomFetchGroupFieldHolder groupFieldHolder;

    public CustomFetchArgsBuilder(CustomFetchCondi condi) {
        this.fetchTaskContext = condi.getFetchTaskContext();
        this.bizModel = condi.getBizModel();
        this.fetchSettingList = condi.getFetchSettingList();
        this.fetchTableHolder = new CustomFetchTableHolder(this.bizModel.getFetchTable());
        this.selectFieldHolder = new CustomFetchSelectFieldHolder(this.bizModel.getSelectFieldList(), this.bizModel.getCustomConditions());
        this.fixedConditionHolder = new CustomFetchFixedConditionHolder(this.bizModel.getFixedCondition());
        this.condiFieldHolder = new CustomFetchCondiFieldHolder(this.bizModel.getCustomConditions());
        this.groupFieldHolder = new CustomFetchGroupFieldHolder(this.bizModel.getSelectFieldList(), this.bizModel.getCustomConditions());
    }

    public CustomFetchArgs buildArgs() {
        for (CustomFetchExecuteSetting fetchSetting : this.fetchSettingList) {
            this.selectFieldHolder.doAnalyze(fetchSetting.getFetchType(), fetchSetting.getCustomConditionMap());
            this.groupFieldHolder.doAnalyze(fetchSetting.getFetchType(), fetchSetting.getCustomConditionMap());
            this.condiFieldHolder.doAnalyze(fetchSetting.getCustomConditionMap());
        }
        String fetchTableSql = this.fetchTableHolder.buildSql(this.fetchTaskContext);
        String selectFieldSql = this.selectFieldHolder.buildSql(this.fetchTaskContext);
        String fixedConditionSql = this.fixedConditionHolder.buildSql(this.fetchTaskContext);
        String groupFieldSql = this.groupFieldHolder.buildSql(this.fetchTaskContext);
        String querySql = String.format("SELECT %1$S FROM %2$s T WHERE 1=1 %3$s %4$s", selectFieldSql, fetchTableSql, fixedConditionSql, groupFieldSql);
        CustomFetchFieldMeta fieldMeta = this.selectFieldHolder.buildFieldMeta(this.fetchTaskContext);
        return new CustomFetchArgs(querySql, fieldMeta, this.condiFieldHolder.getCondiFieldList());
    }
}

