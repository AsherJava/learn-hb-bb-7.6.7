/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.feign.client.DataModelClient
 */
package com.jiuqi.va.datamodel.service.join;

import com.jiuqi.va.datamodel.service.VaDataModelPublishedService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.DataModelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class VaDataModelClientImpl
implements DataModelClient {
    @Autowired
    private VaDataModelPublishedService dataModelService;

    public DataModelDO get(DataModelDTO param) {
        return this.dataModelService.get(param);
    }

    public PageVO<DataModelDO> list(DataModelDTO param) {
        return this.dataModelService.list(param);
    }

    public R push(DataModelDO dataModelDO) {
        return this.dataModelService.push(dataModelDO);
    }

    public R pushComplete(DataModelDO dataModelDO) {
        return this.dataModelService.pushComplete(dataModelDO);
    }

    public R pushIncrement(DataModelDO dataModelDO) {
        return this.dataModelService.pushIncrement(dataModelDO);
    }

    public R remove(DataModelDO dataModelDO) {
        return this.dataModelService.remove(dataModelDO);
    }

    public R updateBaseInfo(DataModelDO dataModelDO) {
        return this.dataModelService.updateBaseInfo(dataModelDO);
    }

    public R syncCache(DataModelDTO param) {
        return this.dataModelService.syncCache(param);
    }
}

