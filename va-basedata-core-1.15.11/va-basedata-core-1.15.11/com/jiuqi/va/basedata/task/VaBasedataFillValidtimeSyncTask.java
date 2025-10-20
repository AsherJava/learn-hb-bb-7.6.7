/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.basedata.BaseDataConsts
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.task.StorageSyncTask
 *  com.jiuqi.va.feign.client.TenantInfoClient
 *  com.jiuqi.va.mapper.common.TenantUtil
 *  com.jiuqi.va.mapper.dao.CommonDao
 *  com.jiuqi.va.mapper.domain.SqlDTO
 */
package com.jiuqi.va.basedata.task;

import com.jiuqi.va.basedata.service.BaseDataDefineService;
import com.jiuqi.va.domain.basedata.BaseDataConsts;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import com.jiuqi.va.feign.client.TenantInfoClient;
import com.jiuqi.va.mapper.common.TenantUtil;
import com.jiuqi.va.mapper.dao.CommonDao;
import com.jiuqi.va.mapper.domain.SqlDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VaBasedataFillValidtimeSyncTask
implements StorageSyncTask {
    @Autowired
    private TenantInfoClient tenantInfoClient;
    @Autowired
    private BaseDataDefineService baseDataDefineService;
    @Autowired
    private CommonDao commonDao;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void execute(String oldVersion) {
        if (this.getVersion().equals(oldVersion)) {
            return;
        }
        try {
            if (TenantUtil.isMultiTenant()) {
                List list = this.tenantInfoClient.nameList();
                if (list != null && !list.isEmpty()) {
                    for (String name : list) {
                        ShiroUtil.bindTenantName((String)name);
                        this.run(name);
                    }
                }
            } else {
                ShiroUtil.bindTenantName((String)"__default_tenant__");
                this.run("__default_tenant__");
            }
        }
        finally {
            ShiroUtil.unbindTenantName();
        }
    }

    private void run(String tenantName) {
        BaseDataDefineDTO bdDefineParam = new BaseDataDefineDTO();
        bdDefineParam.setTenantName(tenantName);
        bdDefineParam.setDeepClone(Boolean.valueOf(false));
        PageVO<BaseDataDefineDO> definePage = this.baseDataDefineService.list(bdDefineParam);
        if (definePage.getTotal() == 0) {
            return;
        }
        String sqlStr = " set validtime=#{param.validtime,jdbcType=TIMESTAMP},invalidtime=#{param.invalidtime,jdbcType=TIMESTAMP} where validtime is null";
        SqlDTO sqlDTO = new SqlDTO(tenantName, null);
        sqlDTO.addParam("validtime", (Object)BaseDataConsts.VERSION_MIN_DATE);
        sqlDTO.addParam("invalidtime", (Object)BaseDataConsts.VERSION_MAX_DATE);
        for (BaseDataDefineDO define : definePage.getRows()) {
            sqlDTO.setSql("update " + define.getName() + sqlStr);
            try {
                this.commonDao.executeBySql(sqlDTO);
            }
            catch (Exception exception) {}
        }
    }

    public int getSortNum() {
        return -2147481638;
    }

    public String getVersion() {
        return "one-off";
    }
}

