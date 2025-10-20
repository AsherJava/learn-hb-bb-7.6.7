/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.framework.nros.cache.NvwaRouteCacheManage
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.org.impl.callback;

import com.jiuqi.nvwa.framework.nros.cache.NvwaRouteCacheManage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateMenService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NvwaRouteCacheManage nvwaRouteCacheManage;

    @Transactional(rollbackFor={Exception.class})
    public void updateMenu() {
        String querySql = "select id, design_id from NVWA_ROUTE where APP_NAME = 'base-unit' and DESIGN_ID is not null";
        List baseUnitMenu = this.jdbcTemplate.queryForList(querySql);
        String updateSql = "update NVWA_ROUTE \nset APP_NAME = 'organization-app/OrgDataMain',PRODUCT_LINE = '@nvwa' \nwhere APP_NAME = 'base-unit'";
        this.jdbcTemplate.update(updateSql);
        String configJson = "{\"customConfig\":{\"categoryName\":\"MD_ORG\",\"startUserMgr\":0}}";
        for (Map unitMenu : baseUnitMenu) {
            String design_id = (String)unitMenu.get("design_id");
            String updateRuntimeParamSql = "update NVWA_ROUTE_PARAM set CONFIG_JSON = ? where ROUTE_ID= ?";
            String updateDesignParamSql = "update NVWA_ROUTE_PARAM set CONFIG_JSON = ? where DESIGN_ID = ?";
            try {
                byte[] bytes = configJson.getBytes("UTF-8");
                this.jdbcTemplate.update(updateRuntimeParamSql, ps -> {
                    ps.setBytes(1, bytes);
                    ps.setString(2, design_id);
                });
                this.jdbcTemplate.update(updateDesignParamSql, ps -> {
                    ps.setBytes(1, bytes);
                    ps.setString(2, design_id);
                });
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        this.nvwaRouteCacheManage.cleanAll();
    }
}

