/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.http.ResponseEntity
 */
package com.jiuqi.va.feign.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.DataModelMaintainClient;
import com.jiuqi.va.feign.remote.DataModelMaintainFeign;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class VaDataModelMaintainClientImpl
implements DataModelMaintainClient {
    @Autowired
    private DataModelMaintainFeign dataModelMaintainFeign;

    @Override
    public DataModelDO get(DataModelDTO param) {
        ResponseEntity<byte[]> respond = this.dataModelMaintainFeign.get(JSONUtil.toBytes((Object)((Object)param)));
        if (respond != null) {
            return (DataModelDO)((Object)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), DataModelDO.class));
        }
        return null;
    }

    @Override
    public PageVO<DataModelDO> listAll(DataModelDTO param) {
        ResponseEntity<byte[]> respond = this.dataModelMaintainFeign.listAll(JSONUtil.toBytes((Object)((Object)param)));
        if (respond != null) {
            return (PageVO)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), (TypeReference)new TypeReference<PageVO<DataModelDO>>(){});
        }
        return null;
    }

    @Override
    public R add(DataModelDTO param) {
        ResponseEntity<byte[]> respond = this.dataModelMaintainFeign.add(JSONUtil.toBytes((Object)((Object)param)));
        if (respond != null) {
            return (R)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), R.class);
        }
        return null;
    }

    @Override
    public R update(DataModelDTO param) {
        ResponseEntity<byte[]> respond = this.dataModelMaintainFeign.update(JSONUtil.toBytes((Object)((Object)param)));
        if (respond != null) {
            return (R)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), R.class);
        }
        return null;
    }

    @Override
    public R publish(List<DataModelDTO> paramList) {
        ResponseEntity<byte[]> respond = this.dataModelMaintainFeign.publish(JSONUtil.toBytes(paramList));
        if (respond != null) {
            return (R)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), R.class);
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getBizTypes() {
        ResponseEntity<byte[]> respond = this.dataModelMaintainFeign.getBizTypes(JSONUtil.toBytes((Object)new TenantDO()));
        if (respond != null) {
            return (List)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        }
        return null;
    }
}

