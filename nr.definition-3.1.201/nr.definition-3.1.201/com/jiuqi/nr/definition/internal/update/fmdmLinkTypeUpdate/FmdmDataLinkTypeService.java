/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.deploy.RefreshDefinitionCacheEvent
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 *  com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.definition.internal.update.fmdmLinkTypeUpdate;

import com.jiuqi.np.definition.deploy.RefreshDefinitionCacheEvent;
import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import com.jiuqi.nr.datascheme.api.event.RefreshSchemeCacheEvent;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.dao.DesignDataLinkDefineDao;
import com.jiuqi.nr.definition.internal.dao.RunTimeDataLinkDefineDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FmdmDataLinkTypeService {
    Logger logger = LoggerFactory.getLogger(FmdmDataLinkTypeService.class);
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private RunTimeDataLinkDefineDao runTimeDataLinkDefineDao;
    @Autowired
    private DesignDataLinkDefineDao designDataLinkDefineDao;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void update() throws Exception {
        this.updateRunTime();
        this.updateDesignTime();
    }

    private void updateRunTime() throws Exception {
        List allTaskDefines = this.jdbcTemplate.queryForList("SELECT * FROM NR_PARAM_TASK");
        ArrayList<TempSqlData> canUpdate = new ArrayList<TempSqlData>();
        for (Map taskDefine : allTaskDefines) {
            List formSchemeDefines = this.jdbcTemplate.queryForList("SELECT * FROM NR_PARAM_FORMSCHEME WHERE FC_TASK_KEY=?", new Object[]{taskDefine.get("TK_KEY")});
            for (Map formSchemeDefine : formSchemeDefines) {
                List formDefines = this.jdbcTemplate.queryForList("SELECT * FROM NR_PARAM_FORM WHERE FM_FORMSCHEME=?", new Object[]{formSchemeDefine.get("FC_KEY")});
                for (Map formDefine : formDefines) {
                    if (FormType.FORM_TYPE_NEWFMDM.getValue() != Integer.parseInt(formDefine.get("FM_TYPE").toString())) continue;
                    this.logger.info("\u5f00\u59cb\u4fee\u6539 \u8fd0\u884c\u671f " + formDefine.get("FM_TITLE") + "\u3010" + formDefine.get("FM_KEY") + "\u3011\u62a5\u8868\u4e0b\u5c01\u9762\u94fe\u63a5\u7c7b\u578b");
                    List allLinksInForm = this.jdbcTemplate.queryForList("SELECT * FROM NR_PARAM_DATALINK WHERE DL_REGION_KEY IN (SELECT DR_KEY FROM NR_PARAM_DATAREGION WHERE DR_FORM_KEY=?)", new Object[]{formDefine.get("FM_KEY")});
                    for (Map define : allLinksInForm) {
                        List dataField = this.jdbcTemplate.queryForList("SELECT * FROM NR_DATASCHEME_FIELD WHERE DF_KEY =?", new Object[]{define.get("DL_EXPRESSION")});
                        if (dataField.size() != 0 || DataLinkType.DATA_LINK_TYPE_FIELD.getValue() != Integer.parseInt(define.get("DL_TYPE").toString())) continue;
                        this.logger.info("\u6536\u96c6\u51c6\u5907\u4fee\u6539\u8303\u56f4" + define.get("DL_KEY"));
                        TempSqlData data = new TempSqlData("UPDATE NR_PARAM_DATALINK SET DL_TYPE=? WHERE DL_KEY=?", DataLinkType.DATA_LINK_TYPE_FMDM.getValue(), define.get("DL_KEY"));
                        canUpdate.add(data);
                    }
                }
            }
        }
        if (canUpdate.size() != 0) {
            for (TempSqlData data : canUpdate) {
                this.jdbcTemplate.update(data.getSql(), new Object[]{data.getP1(), data.getP2()});
            }
        }
        RefreshCache refreshCache = new RefreshCache();
        refreshCache.setRefreshAll(true);
        this.applicationContext.publishEvent((ApplicationEvent)new RefreshSchemeCacheEvent(refreshCache));
        this.applicationContext.publishEvent((ApplicationEvent)new RefreshDefinitionCacheEvent());
    }

    private void updateDesignTime() throws Exception {
        List allTaskDefines = this.jdbcTemplate.queryForList("SELECT * FROM NR_PARAM_TASK_DES");
        ArrayList<TempSqlData> canUpdate = new ArrayList<TempSqlData>();
        for (Map taskDefine : allTaskDefines) {
            List formSchemeDefines = this.jdbcTemplate.queryForList("SELECT * FROM NR_PARAM_FORMSCHEME_DES WHERE FC_TASK_KEY=?", new Object[]{taskDefine.get("TK_KEY")});
            for (Map formSchemeDefine : formSchemeDefines) {
                List formDefines = this.jdbcTemplate.queryForList("SELECT * FROM NR_PARAM_FORM_DES WHERE FM_FORMSCHEME=?", new Object[]{formSchemeDefine.get("FC_KEY")});
                for (Map formDefine : formDefines) {
                    if (FormType.FORM_TYPE_NEWFMDM.getValue() != Integer.parseInt(formDefine.get("FM_TYPE").toString())) continue;
                    this.logger.info("\u5f00\u59cb\u4fee\u6539 \u8bbe\u8ba1\u671f " + formDefine.get("FM_TITLE") + "\u3010" + formDefine.get("FM_KEY") + "\u3011\u62a5\u8868\u4e0b\u5c01\u9762\u94fe\u63a5\u7c7b\u578b");
                    List allLinksInForm = this.jdbcTemplate.queryForList("SELECT * FROM NR_PARAM_DATALINK_DES WHERE DL_REGION_KEY IN (SELECT DR_KEY FROM NR_PARAM_DATAREGION_DES WHERE DR_FORM_KEY=?)", new Object[]{formDefine.get("FM_KEY")});
                    for (Map define : allLinksInForm) {
                        List dataField = this.jdbcTemplate.queryForList("SELECT * FROM NR_DATASCHEME_FIELD_DES WHERE DF_KEY =?", new Object[]{define.get("DL_EXPRESSION")});
                        if (dataField.size() != 0 || DataLinkType.DATA_LINK_TYPE_FIELD.getValue() != Integer.parseInt(define.get("DL_TYPE").toString())) continue;
                        this.logger.info("\u6536\u96c6\u51c6\u5907\u4fee\u6539\u8303\u56f4" + define.get("DL_KEY"));
                        TempSqlData data = new TempSqlData("UPDATE NR_PARAM_DATALINK_DES SET DL_TYPE=? WHERE DL_KEY=?", DataLinkType.DATA_LINK_TYPE_FMDM.getValue(), define.get("DL_KEY"));
                        canUpdate.add(data);
                    }
                }
            }
        }
        if (canUpdate.size() != 0) {
            for (TempSqlData data : canUpdate) {
                this.jdbcTemplate.update(data.getSql(), new Object[]{data.getP1(), data.getP2()});
            }
        }
    }

    class TempSqlData {
        private String sql;
        private Object p1;
        private Object p2;

        public TempSqlData() {
        }

        public TempSqlData(String sql, Object p1, Object p2) {
            this.sql = sql;
            this.p1 = p1;
            this.p2 = p2;
        }

        public String getSql() {
            return this.sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public Object getP1() {
            return this.p1;
        }

        public void setP1(Object p1) {
            this.p1 = p1;
        }

        public Object getP2() {
            return this.p2;
        }

        public void setP2(Object p2) {
            this.p2 = p2;
        }
    }
}

