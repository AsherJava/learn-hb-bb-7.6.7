/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.http.ResponseEntity
 */
package com.jiuqi.va.feign.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelGroupExternalDO;
import com.jiuqi.va.domain.datamodel.DataModelGroupExternalDTO;
import com.jiuqi.va.feign.client.DataModelGroupClient;
import com.jiuqi.va.feign.remote.DataModelGroupFeign;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class VaDataModelGroupClientImpl
implements DataModelGroupClient {
    @Autowired
    private DataModelGroupFeign dataModelGroupFeign;

    @Override
    public DataModelGroupExternalDO get(DataModelGroupExternalDTO param) {
        ResponseEntity<byte[]> respond = this.dataModelGroupFeign.get(JSONUtil.toBytes((Object)((Object)param)));
        if (respond != null) {
            return (DataModelGroupExternalDO)((Object)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), DataModelGroupExternalDO.class));
        }
        return null;
    }

    @Override
    public List<DataModelGroupExternalDO> list(DataModelGroupExternalDTO param) {
        ResponseEntity<byte[]> respond = this.dataModelGroupFeign.list(JSONUtil.toBytes((Object)((Object)param)));
        if (respond != null) {
            return (List)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), (TypeReference)new TypeReference<List<DataModelGroupExternalDO>>(){});
        }
        return null;
    }

    @Override
    public R add(DataModelGroupExternalDTO param) {
        ResponseEntity<byte[]> respond = this.dataModelGroupFeign.add(JSONUtil.toBytes((Object)((Object)param)));
        if (respond != null) {
            return (R)JSONUtil.parseObject((byte[])((byte[])respond.getBody()), R.class);
        }
        return null;
    }
}

