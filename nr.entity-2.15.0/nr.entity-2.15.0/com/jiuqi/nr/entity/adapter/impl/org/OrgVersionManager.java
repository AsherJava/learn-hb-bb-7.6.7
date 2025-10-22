/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.feign.client.OrgVersionClient
 */
package com.jiuqi.nr.entity.adapter.impl.org;

import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.feign.client.OrgVersionClient;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrgVersionManager {
    @Autowired
    private OrgVersionClient orgVersionClient;

    public OrgVersionDO getVersion(String tableName, Date date) {
        OrgVersionDTO param = new OrgVersionDTO();
        param.setCategoryname(tableName);
        param.setVersionDate(date);
        return this.orgVersionClient.get(param);
    }

    public List<OrgVersionDO> listVersion(String tableName) {
        OrgVersionDTO param = new OrgVersionDTO();
        param.setCategoryname(tableName);
        PageVO orgVersions = this.orgVersionClient.list(param);
        return orgVersions.getRows();
    }

    public R addVersion(OrgVersionDO version) {
        return this.orgVersionClient.add(version);
    }

    public R deleteVersion(OrgVersionDO version) {
        return this.orgVersionClient.remove(version);
    }
}

