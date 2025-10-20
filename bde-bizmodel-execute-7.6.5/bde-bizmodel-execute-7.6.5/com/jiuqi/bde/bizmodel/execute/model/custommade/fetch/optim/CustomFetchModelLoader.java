/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.define.IBdePluginType
 *  com.jiuqi.bde.bizmodel.define.IBizDataModelLoader
 *  com.jiuqi.bde.common.constant.BizDataModelEnum
 *  com.jiuqi.bde.log.utils.BdeLogUtil
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 *  org.springframework.jdbc.core.ResultSetExtractor
 */
package com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim;

import com.jiuqi.bde.bizmodel.define.IBdePluginType;
import com.jiuqi.bde.bizmodel.define.IBizDataModelLoader;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchArgs;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchCondi;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.CustomFetchResultFactory;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.intf.result.AbstractCustomFetchResult;
import com.jiuqi.bde.bizmodel.execute.model.custommade.fetch.optim.sql.CustomFetchArgsBuilder;
import com.jiuqi.bde.common.constant.BizDataModelEnum;
import com.jiuqi.bde.log.utils.BdeLogUtil;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class CustomFetchModelLoader
implements IBizDataModelLoader<CustomFetchCondi, AbstractCustomFetchResult> {
    @Autowired
    protected DataSourceService dataSourceService;

    public IBdePluginType getPluginType() {
        return null;
    }

    public String getBizDataModelCode() {
        return BizDataModelEnum.CUSTOMFETCHMODEL.getCode();
    }

    public AbstractCustomFetchResult loadData(CustomFetchCondi condi) {
        CustomFetchArgs fetchArgs = new CustomFetchArgsBuilder(condi).buildArgs();
        BdeLogUtil.recordLog((String)condi.getFetchTaskContext().getRequestTaskId(), (String)"\u6e90\u5e93\u8f6c\u6362-\u81ea\u5b9a\u4e49\u6a21\u578b\u53d6\u6570", (Object)new Object[0], (String)JsonUtils.writeValueAsString((Object)fetchArgs));
        return this.getResult(condi, fetchArgs);
    }

    protected AbstractCustomFetchResult getResult(CustomFetchCondi condi, CustomFetchArgs fetchArgs) {
        return (AbstractCustomFetchResult)this.dataSourceService.query(condi.getFetchTaskContext().getOrgMapping().getDataSourceCode(), fetchArgs.getSql(), new Object[0], (ResultSetExtractor)CustomFetchResultFactory.createResultSetExtractor(condi.getBizModel(), fetchArgs, null));
    }
}

