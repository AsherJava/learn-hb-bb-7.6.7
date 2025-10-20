/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelGroupExternalDO
 *  com.jiuqi.va.domain.datamodel.DataModelGroupExternalDTO
 *  com.jiuqi.va.feign.client.DataModelGroupClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.datamodel.service.join;

import com.jiuqi.va.datamodel.service.VaDataModelGroupService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelGroupExternalDO;
import com.jiuqi.va.domain.datamodel.DataModelGroupExternalDTO;
import com.jiuqi.va.feign.client.DataModelGroupClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class VaDataModelGroupClientImpl
implements DataModelGroupClient {
    private VaDataModelGroupService vaDataModelGroupService;

    private VaDataModelGroupService getVaDataModelGroupService() {
        if (this.vaDataModelGroupService == null) {
            this.vaDataModelGroupService = (VaDataModelGroupService)ApplicationContextRegister.getBean(VaDataModelGroupService.class);
        }
        return this.vaDataModelGroupService;
    }

    public DataModelGroupExternalDO get(DataModelGroupExternalDTO param) {
        return this.getVaDataModelGroupService().externalGet(param);
    }

    public List<DataModelGroupExternalDO> list(DataModelGroupExternalDTO param) {
        return this.getVaDataModelGroupService().externalList(param);
    }

    public R add(DataModelGroupExternalDTO param) {
        return this.getVaDataModelGroupService().externalAdd(param);
    }
}

