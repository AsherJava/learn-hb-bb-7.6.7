/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.organization.domain.OrgDataSyncCacheDTO
 *  com.jiuqi.va.organization.service.impl.help.OrgDataCacheService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.org.impl.callback;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.org.impl.base.GcOrgCodeConfig;
import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
import com.jiuqi.gcreport.org.impl.cache.dao.FGcOrgTypeVersionDao;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.organization.domain.OrgDataSyncCacheDTO;
import com.jiuqi.va.organization.service.impl.help.OrgDataCacheService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Deprecated
@Component
public class GcOrgMessageReceiver {
    @Autowired
    private OrgDataCacheService orgDataCacheService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void receiveMessage(String message, String channel) {
        OrgDataSyncCacheDTO dmsc = (OrgDataSyncCacheDTO)JSONUtil.parseObject((String)message, OrgDataSyncCacheDTO.class);
        String categoryname = dmsc.getOrgDTO().getCategoryname();
        if (!categoryname.equalsIgnoreCase("MD_ORG")) {
            return;
        }
        this.updateGcParents(dmsc, categoryname);
    }

    private void updateGcParents(OrgDataSyncCacheDTO dmsc, String tableName) {
        GcOrgCodeConfig gcOrgCodeConfig = ((FGcOrgTypeVersionDao)SpringContextUtils.getBean(FGcOrgTypeVersionDao.class)).getGcOrgCodeConfig();
        List mapList = this.jdbcTemplate.queryForList("select id, parents, gcparents from " + tableName);
        ArrayList updateList = new ArrayList();
        mapList.forEach(org -> {
            String newGcParents;
            String oldGcParents;
            String id = (String)org.get("id");
            String parents = org.get("PARENTS") == null ? "-" : String.valueOf(org.get("PARENTS"));
            String string = oldGcParents = org.get("GCPARENTS") == null ? "" : String.valueOf(org.get("GCPARENTS"));
            if (StringUtils.hasText(parents) && !(newGcParents = InspectOrgUtils.getGcParentsByOldParents(parents, gcOrgCodeConfig)).equalsIgnoreCase(oldGcParents)) {
                Object[] args = new Object[]{newGcParents, id};
                updateList.add(args);
            }
        });
        if (!CollectionUtils.isEmpty(updateList)) {
            this.jdbcTemplate.batchUpdate("update " + tableName + " set gcparents = ? where id = ?", updateList);
            this.orgDataCacheService.syncCache(dmsc.getOrgDTO(), true);
        }
    }
}

