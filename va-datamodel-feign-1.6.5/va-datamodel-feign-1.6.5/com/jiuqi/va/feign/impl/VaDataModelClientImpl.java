/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.http.ResponseEntity
 */
package com.jiuqi.va.feign.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.remote.DataModelFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class VaDataModelClientImpl
implements DataModelClient {
    @Autowired
    private DataModelFeign dataModelFeign;

    @Override
    public DataModelDO get(DataModelDTO param) {
        ResponseEntity<byte[]> respond = this.dataModelFeign.get(JSONUtil.toBytes((Object)((Object)param)));
        if (respond != null) {
            return (DataModelDO)((Object)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), DataModelDO.class));
        }
        return null;
    }

    @Override
    public PageVO<DataModelDO> list(DataModelDTO param) {
        ResponseEntity<byte[]> respond = this.dataModelFeign.list(JSONUtil.toBytes((Object)((Object)param)));
        if (respond != null) {
            return (PageVO)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), (TypeReference)new TypeReference<PageVO<DataModelDO>>(){});
        }
        return null;
    }

    @Override
    public R push(DataModelDO param) {
        ResponseEntity<byte[]> respond = this.dataModelFeign.push(JSONUtil.toBytes((Object)((Object)param)));
        if (respond != null) {
            return (R)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), R.class);
        }
        return null;
    }

    @Override
    public R pushComplete(DataModelDO param) {
        ResponseEntity<byte[]> respond = this.dataModelFeign.pushComplete(JSONUtil.toBytes((Object)((Object)param)));
        if (respond != null) {
            return (R)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), R.class);
        }
        return null;
    }

    @Override
    public R pushIncrement(DataModelDO param) {
        ResponseEntity<byte[]> respond = this.dataModelFeign.pushIncrement(JSONUtil.toBytes((Object)((Object)param)));
        if (respond != null) {
            return (R)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), R.class);
        }
        return null;
    }

    @Override
    public R remove(DataModelDO param) {
        ResponseEntity<byte[]> respond = this.dataModelFeign.remove(JSONUtil.toBytes((Object)((Object)param)));
        if (respond != null) {
            return (R)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), R.class);
        }
        return null;
    }

    @Override
    public R updateBaseInfo(DataModelDO param) {
        ResponseEntity<byte[]> respond = this.dataModelFeign.updateBaseInfo(JSONUtil.toBytes((Object)((Object)param)));
        if (respond != null) {
            return (R)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), R.class);
        }
        return null;
    }

    @Override
    public R syncCache(DataModelDTO param) {
        ResponseEntity<byte[]> respond = this.dataModelFeign.syncCache(JSONUtil.toBytes((Object)((Object)param)));
        if (respond != null) {
            return (R)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), R.class);
        }
        return null;
    }
}

