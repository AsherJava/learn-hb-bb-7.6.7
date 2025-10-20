/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.feign.client.DataModelMaintainClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.datamodel.service.join;

import com.jiuqi.va.datamodel.common.DataModelCoreI18nUtil;
import com.jiuqi.va.datamodel.service.VaDataModelMaintainService;
import com.jiuqi.va.datamodel.service.impl.help.VaDataModelBizTypeService;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.DataModelMaintainClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Primary
@Component
public class VaDataModelMaintainClientImpl
implements DataModelMaintainClient {
    private VaDataModelMaintainService vaDataModelMaintainService;
    private VaDataModelBizTypeService vaDataModelBizTypeService;

    private VaDataModelMaintainService getVaDataModelMaintainService() {
        if (this.vaDataModelMaintainService == null) {
            this.vaDataModelMaintainService = (VaDataModelMaintainService)ApplicationContextRegister.getBean(VaDataModelMaintainService.class);
        }
        return this.vaDataModelMaintainService;
    }

    private VaDataModelBizTypeService getVaDataModelBizTypeService() {
        if (this.vaDataModelBizTypeService == null) {
            this.vaDataModelBizTypeService = (VaDataModelBizTypeService)ApplicationContextRegister.getBean(VaDataModelBizTypeService.class);
        }
        return this.vaDataModelBizTypeService;
    }

    public DataModelDO get(DataModelDTO param) {
        if (!StringUtils.hasText(param.getName())) {
            return null;
        }
        return this.getVaDataModelMaintainService().get(param);
    }

    public PageVO<DataModelDO> listAll(DataModelDTO param) {
        boolean isPage = param.isPagination();
        param.setPagination(false);
        ArrayList list = this.getVaDataModelMaintainService().list(param);
        list = list == null ? new ArrayList() : list;
        PageVO res = new PageVO();
        res.setTotal(list.size());
        res.setRs(R.ok((String)DataModelCoreI18nUtil.getMessage("datamodel.success.common.operate", new Object[0])));
        if (isPage) {
            int endIndex = param.getOffset() + param.getLimit();
            endIndex = endIndex < list.size() ? endIndex : list.size();
            ArrayList resultList = new ArrayList();
            resultList.addAll(list.subList(param.getOffset(), endIndex));
            res.setRows(resultList);
        } else {
            ArrayList resultList = new ArrayList();
            resultList.addAll(list);
            res.setRows(resultList);
        }
        return res;
    }

    public R add(DataModelDTO param) {
        return this.getVaDataModelMaintainService().add(param);
    }

    public R update(DataModelDTO param) {
        return this.getVaDataModelMaintainService().update(param);
    }

    public R publish(List<DataModelDTO> paramList) {
        return this.getVaDataModelMaintainService().publishModel(paramList);
    }

    public List<Map<String, Object>> getBizTypes() {
        return this.getVaDataModelBizTypeService().listBizTypeSimple();
    }
}

