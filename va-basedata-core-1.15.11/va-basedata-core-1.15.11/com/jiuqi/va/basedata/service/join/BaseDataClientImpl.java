/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.basedata.common.BaseDataContext
 *  com.jiuqi.va.domain.basedata.BaseDataCacheDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataColumnValueDTO
 *  com.jiuqi.va.domain.basedata.handle.BaseDataMoveDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.va.basedata.service.join;

import com.jiuqi.va.basedata.common.BaseDataContext;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.domain.basedata.BaseDataCacheDTO;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataColumnValueDTO;
import com.jiuqi.va.domain.basedata.handle.BaseDataMoveDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Primary
@Component(value="vaCoreBaseDataClientImpl")
public class BaseDataClientImpl
implements BaseDataClient {
    private BaseDataService baseDataService;

    public BaseDataService getBaseDataService() {
        if (this.baseDataService == null) {
            this.baseDataService = (BaseDataService)ApplicationContextRegister.getBean(BaseDataService.class);
        }
        return this.baseDataService;
    }

    public R exist(BaseDataDTO basedataDTO) {
        return this.getBaseDataService().exist(basedataDTO);
    }

    public PageVO<BaseDataDO> list(BaseDataDTO basedataDTO) {
        Map context;
        BaseDataContext.removeContext((String)basedataDTO.getTableName());
        PageVO<BaseDataDO> page = this.getBaseDataService().list(basedataDTO);
        if (page != null && page.getTotal() > 0 && (context = BaseDataContext.getDataContext((String)basedataDTO.getTableName())) != null) {
            page.getRs().putAll(context);
        }
        return page;
    }

    public Map<String, Object[]> columnValueList(BaseDataColumnValueDTO param) {
        return this.getBaseDataService().columnValueList(param);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R add(BaseDataDTO basedataDTO) {
        return this.getBaseDataService().add(basedataDTO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R update(BaseDataDTO basedataDTO) {
        return this.getBaseDataService().update(basedataDTO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R remove(BaseDataDTO basedataDTO) {
        return this.getBaseDataService().remove(basedataDTO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R batchRemove(BaseDataBatchOptDTO basedataBatchOptDTO) {
        return this.getBaseDataService().batchRemove(basedataBatchOptDTO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R stop(BaseDataDTO basedataDTO) {
        return this.getBaseDataService().stop(basedataDTO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R batchStop(BaseDataBatchOptDTO basedataBatchOptDTO) {
        return this.getBaseDataService().batchStop(basedataBatchOptDTO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R recover(BaseDataDTO basedataDTO) {
        return this.getBaseDataService().recover(basedataDTO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R batchRecover(BaseDataBatchOptDTO basedataBatchOptDTO) {
        return this.getBaseDataService().batchRecover(basedataBatchOptDTO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R move(BaseDataMoveDTO basedataMoveDTO) {
        return this.getBaseDataService().move(basedataMoveDTO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R sync(@RequestBody BaseDataBatchOptDTO basedataBatchOptDTO) {
        return this.getBaseDataService().sync(basedataBatchOptDTO);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public R changeShare(BaseDataBatchOptDTO basedataBatchOptDTO) {
        return this.getBaseDataService().changeShare(basedataBatchOptDTO);
    }

    public int count(BaseDataDTO basedataDTO) {
        return this.getBaseDataService().count(basedataDTO);
    }

    public List<BaseDataDO> verDiffList(@RequestBody BaseDataDTO basedataDTO) {
        return this.getBaseDataService().verDiffList(basedataDTO);
    }

    public R initCache(BaseDataCacheDTO baseDataCacheDTO) {
        return this.getBaseDataService().initCache(baseDataCacheDTO);
    }

    public R syncCache(BaseDataCacheDTO baseDataCacheDTO) {
        return this.getBaseDataService().syncCache(baseDataCacheDTO);
    }

    public R cleanCache(BaseDataCacheDTO baseDataCacheDTO) {
        return this.getBaseDataService().cleanCache(baseDataCacheDTO);
    }
}

