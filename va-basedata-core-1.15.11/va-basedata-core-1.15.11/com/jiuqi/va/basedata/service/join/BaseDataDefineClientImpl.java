/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDO
 *  com.jiuqi.va.domain.basedata.BaseDataGroupDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.basedata.service.join;

import com.jiuqi.va.basedata.service.BaseDataDefineService;
import com.jiuqi.va.basedata.service.BaseDataGroupService;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDO;
import com.jiuqi.va.domain.basedata.BaseDataGroupDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Primary
@Component(value="vaCoreBaseDataDefineClientImpl")
public class BaseDataDefineClientImpl
implements BaseDataDefineClient {
    private BaseDataGroupService baseDataGroupService;
    private BaseDataDefineService baseDataDefineService;

    public BaseDataDefineService getBaseDataDefineService() {
        if (this.baseDataDefineService == null) {
            this.baseDataDefineService = (BaseDataDefineService)ApplicationContextRegister.getBean(BaseDataDefineService.class);
        }
        return this.baseDataDefineService;
    }

    public BaseDataGroupService getBaseDataGroupService() {
        if (this.baseDataGroupService == null) {
            this.baseDataGroupService = (BaseDataGroupService)ApplicationContextRegister.getBean(BaseDataGroupService.class);
        }
        return this.baseDataGroupService;
    }

    public R exist(BaseDataGroupDTO param) {
        return this.getBaseDataGroupService().exist(param);
    }

    public BaseDataGroupDO get(BaseDataGroupDTO param) {
        return this.getBaseDataGroupService().get(param);
    }

    public PageVO<BaseDataGroupDO> list(BaseDataGroupDTO param) {
        return this.getBaseDataGroupService().list(param);
    }

    public R add(BaseDataGroupDTO basedataGroupDO) {
        return this.getBaseDataGroupService().add(basedataGroupDO);
    }

    public R update(BaseDataGroupDTO basedataGroupDO) {
        return this.getBaseDataGroupService().update(basedataGroupDO);
    }

    public BaseDataDefineDO get(BaseDataDefineDTO basedataDO) {
        return this.getBaseDataDefineService().get(basedataDO);
    }

    public PageVO<BaseDataDefineDO> list(BaseDataDefineDTO basedataDO) {
        return this.getBaseDataDefineService().list(basedataDO);
    }

    public R exist(BaseDataDefineDTO basedataDO) {
        return this.getBaseDataDefineService().exist(basedataDO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R add(BaseDataDefineDTO basedataDO) {
        return this.getBaseDataDefineService().add(basedataDO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R update(BaseDataDefineDTO basedataDO) {
        return this.getBaseDataDefineService().update(basedataDO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R remove(BaseDataDefineDTO basedataDO) {
        return this.getBaseDataDefineService().remove(basedataDO);
    }
}

