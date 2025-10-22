/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definitionext.taskExtConfig.controller.ITaskExtConfigController
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.dao.define;

import com.google.gson.Gson;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definitionext.taskExtConfig.controller.ITaskExtConfigController;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.controller.MultCheckTableController;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.dao.IMultCheckTableDao;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.entity.MultCheckTableData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MultCheckTableDao
implements IMultCheckTableDao {
    private static final Logger logger = LoggerFactory.getLogger(IMultCheckTableDao.class);
    Gson gson = new Gson();
    private static final String MENU_TYPE = "taskextension-multcheck";
    @Autowired
    public JdbcTemplate jdbcTemplate;
    @Autowired
    MultCheckTableController multCheckTableController;
    @Autowired
    IRunTimeViewController viewController;
    @Autowired
    private ITaskExtConfigController TaskExtConfigController;

    @Override
    public int insertData(Object[] objects) {
        String insertSql = "insert into SYS_MULTCHECK_SCHEME(s_key, s_name, s_order, s_dw, s_content, s_taskkey, s_formschemekey) values (?, ?, ?, ?, ?, ?, ?)";
        int num = 0;
        try {
            num = this.jdbcTemplate.update(insertSql, objects);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return num;
    }

    @Override
    public int deleteData(String key) {
        String deleteSql = "delete from SYS_MULTCHECK_SCHEME where s_key = ?";
        int num = 0;
        try {
            logger.info("deleteSql: " + deleteSql + "  param: s_key = " + key);
            num = this.jdbcTemplate.update(deleteSql, new Object[]{key});
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return num;
    }

    @Override
    public int updateData(String updateSql, List<Object> paramList) {
        int num = 0;
        try {
            num = this.jdbcTemplate.update(updateSql, paramList.toArray(new Object[paramList.size()]));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return num;
    }

    @Override
    public List<Map<String, Object>> selectData(String key) {
        String selectSql = "select s_key, s_name, s_order, s_dw, s_content from SYS_MULTCHECK_SCHEME where s_key = ?";
        logger.info("selectSql: " + selectSql + "  param: s_key = " + key);
        List dataList = null;
        try {
            dataList = this.jdbcTemplate.queryForList(selectSql, new Object[]{key});
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return dataList;
    }

    @Override
    public void updateDwCode(String key, String dwCodeList) {
        String updateDwCodeSql = "update SYS_MULTCHECK_SCHEME set s_dw = ? where s_key = ?";
        logger.info("updateDwCodeSql: " + updateDwCodeSql + " param: { s_key = " + key + ", s_dw = " + dwCodeList + "}");
        try {
            this.jdbcTemplate.update(updateDwCodeSql, new Object[]{dwCodeList, key});
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> getCheckItemList() {
        String selectSql = "select s_content from SYS_MULTCHECK_SCHEME";
        logger.info("selectSql: " + selectSql);
        List dataList = null;
        try {
            dataList = this.jdbcTemplate.queryForList(selectSql);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return dataList;
    }

    @Override
    public boolean deleteLibTableName(String libTableName, String formSchemeKey, StringBuilder checkItemType) {
        StringBuilder sql = new StringBuilder();
        sql.append("delete  from ").append(libTableName);
        sql.append(" where ").append("ZHSH_FORMSCHEMEKEY").append(" = '").append(formSchemeKey).append("'").append(" and ").append("ZHSH_CHECKITEMKEY").append(" not in ").append((CharSequence)checkItemType);
        int t = 0;
        try {
            JdbcTemplate jdbcT = new JdbcTemplate(this.jdbcTemplate.getDataSource());
            t = jdbcT.update(sql.toString());
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return t > 0;
    }

    @Override
    public List<Map<String, Object>> getSchemeList(String formschemeKey) throws Exception {
        FormSchemeDefine formSchemeDefine = this.viewController.getFormScheme(formschemeKey);
        List<Map<String, Object>> schemeList = new ArrayList<Map<String, Object>>();
        String selectSql = "select s_key, s_name, s_dw from SYS_MULTCHECK_SCHEME where s_formschemekey = ? order by s_order";
        try {
            schemeList = this.jdbcTemplate.queryForList(selectSql, new Object[]{formschemeKey});
            if (schemeList.size() == 0) {
                this.initDefaultShcheme(formschemeKey, formSchemeDefine, schemeList);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return schemeList;
    }

    private void initDefaultShcheme(String formschemeKey, FormSchemeDefine formSchemeDefine, List<Map<String, Object>> schemeList) {
        MultCheckTableData mData = new MultCheckTableData();
        mData.setFormSchemeKey(formschemeKey);
        mData.setS_dw(new ArrayList<String>());
        mData.setS_key("defaultScheme-" + UUID.randomUUID().toString());
        mData.setS_name("\u9ed8\u8ba4\u5ba1\u6838\u65b9\u6848");
        mData.setTaskkey(formSchemeDefine.getTaskKey());
        mData.setS_order(1);
        this.multCheckTableController.insertData(new Gson().toJson((Object)mData));
        HashMap<String, String> defaultScheme = new HashMap<String, String>();
        defaultScheme.put("s_key", mData.getS_key());
        defaultScheme.put("s_name", mData.getS_name());
        defaultScheme.put("s_dw", "");
        schemeList.add(defaultScheme);
    }
}

