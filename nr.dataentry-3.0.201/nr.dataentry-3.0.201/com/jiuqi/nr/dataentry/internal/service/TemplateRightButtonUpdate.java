/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.PreparedStatementCallback
 *  org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback
 *  org.springframework.jdbc.support.lob.DefaultLobHandler
 *  org.springframework.jdbc.support.lob.LobCreator
 *  org.springframework.jdbc.support.lob.LobHandler
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.dataentry.bean.impl.TemplateConfigImpl;
import com.jiuqi.nr.dataentry.gather.ActionType;
import com.jiuqi.nr.dataentry.model.AllViewConfig;
import com.jiuqi.nr.dataentry.model.GridViewConfig;
import com.jiuqi.nr.dataentry.model.TreeNodeItem;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;

public class TemplateRightButtonUpdate
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(TemplateRightButtonUpdate.class);
    private JdbcTemplate jdbcTemplate = (JdbcTemplate)BeanUtil.getBean(JdbcTemplate.class);

    public void execute(DataSource dataSource) throws Exception {
        List<TemplateConfigImpl> allTemplates = this.getAllTemplates();
        Map<String, AllViewConfig> allViewConfigs = this.getAllViewConfig(allTemplates);
        for (String key : allViewConfigs.keySet()) {
            TreeNodeItem clearCellBut;
            AllViewConfig allViewConfig = allViewConfigs.get(key);
            GridViewConfig gridViewConfig = allViewConfig.getGridViewConfig();
            if (gridViewConfig == null) continue;
            List<TreeNodeItem> menus = gridViewConfig.getMenus();
            String misType = this.judgeMisButton(menus);
            if (misType.equals("CELL")) {
                clearCellBut = this.getClearCellBut();
                menus.add(0, clearCellBut);
            } else if (misType.equals("ENUM")) {
                TreeNodeItem insertEnumBut = this.getInsertEnumBut();
                menus.add(0, insertEnumBut);
            } else {
                if (!misType.equals("ALL")) continue;
                clearCellBut = this.getClearCellBut();
                TreeNodeItem insertEnumBut = this.getInsertEnumBut();
                menus.add(0, clearCellBut);
                menus.add(1, insertEnumBut);
            }
            this.updateTemplateConfig(key, allViewConfig);
        }
    }

    private void updateTemplateConfig(final String key, AllViewConfig allViewConfig) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String sql = "UPDATE DATAENTRY_TEMPLATE T SET TEMPLATE_CONFIG = ? WHERE T.TEMPLATE_ID = ?";
            final String templateConfig = objectMapper.writeValueAsString((Object)allViewConfig);
            this.jdbcTemplate.execute(sql, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                    ps.setString(1, templateConfig);
                    ps.setObject(2, key);
                }
            });
        }
        catch (JsonProcessingException e) {
            logger.warn("\u6570\u636e\u5f55\u5165\u6a21\u677f\uff1a\u6a21\u677fid: " + key + "\u66f4\u65b0\u5931\u8d25");
        }
    }

    private TreeNodeItem getClearCellBut() {
        TreeNodeItem clearCellBut = new TreeNodeItem();
        clearCellBut.setCode("clearCell");
        clearCellBut.setActionType(ActionType.BUTTON);
        clearCellBut.setAccelerator(null);
        clearCellBut.setBgColor(null);
        clearCellBut.setParentCode(null);
        clearCellBut.setIcon("#icon-_GJTqingchu");
        clearCellBut.setNodeKey("3");
        clearCellBut.setEnablePermission(false);
        clearCellBut.setTitle("\u6e05\u9664\u6240\u9009\u5355\u5143\u683c");
        clearCellBut.setParams(null);
        clearCellBut.setSelected(false);
        clearCellBut.setDesc("\u6e05\u9664\u6240\u9009\u5355\u5143\u683c");
        return clearCellBut;
    }

    private TreeNodeItem getInsertEnumBut() {
        TreeNodeItem clearCellBut = new TreeNodeItem();
        clearCellBut.setCode("insertEntityLines");
        clearCellBut.setActionType(ActionType.BUTTON);
        clearCellBut.setAccelerator(null);
        clearCellBut.setBgColor(null);
        clearCellBut.setParentCode(null);
        clearCellBut.setIcon("#icon-_GJThouchaduohang");
        clearCellBut.setNodeKey("3");
        clearCellBut.setEnablePermission(false);
        clearCellBut.setTitle("\u6309\u679a\u4e3e\u63d2\u884c");
        clearCellBut.setParams(null);
        clearCellBut.setSelected(false);
        clearCellBut.setDesc("\u6309\u679a\u4e3e\u63d2\u884c");
        return clearCellBut;
    }

    private String judgeMisButton(List<TreeNodeItem> menus) {
        String misType = "NO";
        if (menus != null && menus.size() > 0) {
            List allCode = menus.stream().map(e -> e.getCode()).collect(Collectors.toList());
            boolean containClearCell = allCode.contains("clearCell");
            boolean containInsertEnum = allCode.contains("insertEntityLines");
            if (containClearCell && !containInsertEnum) {
                misType = "ENUM";
            } else if (!containClearCell && containInsertEnum) {
                misType = "CELL";
            } else if (!containClearCell && !containInsertEnum) {
                misType = "ALL";
            }
            return misType;
        }
        misType = "ALL";
        return misType;
    }

    public Map<String, AllViewConfig> getAllViewConfig(List<TemplateConfigImpl> allTemplates) {
        HashedMap<String, AllViewConfig> viewConfigs = new HashedMap<String, AllViewConfig>();
        for (TemplateConfigImpl template : allTemplates) {
            if (template == null || !StringUtils.isNotEmpty((String)template.getTemplateConfig())) continue;
            ObjectMapper objectMapper = new ObjectMapper();
            AllViewConfig allViewConfig = null;
            try {
                allViewConfig = (AllViewConfig)objectMapper.readValue(template.getTemplateConfig(), AllViewConfig.class);
                viewConfigs.put(template.getTemplateId(), allViewConfig);
            }
            catch (IOException e) {
                logger.warn("\u6570\u636e\u5f55\u5165\u6a21\u677f\uff1a\u6a21\u677fid: " + template.getTemplateId() + "\u8f6cjson\u5931\u8d25");
            }
        }
        return viewConfigs;
    }

    public List<TemplateConfigImpl> getAllTemplates() {
        String sql = "SELECT * FROM DATAENTRY_TEMPLATE T ";
        ArrayList<TemplateConfigImpl> impls = new ArrayList<TemplateConfigImpl>();
        List queryForList = this.jdbcTemplate.queryForList(sql);
        for (Map map : queryForList) {
            if (map.get("TEMPLATE_CODE") == null || map.get("TEMPLATE_TITLE") == null) continue;
            TemplateConfigImpl impl = new TemplateConfigImpl();
            impl.setTemplateId(map.get("TEMPLATE_ID").toString());
            impl.setCode(map.get("TEMPLATE_CODE").toString());
            impl.setTitle(map.get("TEMPLATE_TITLE").toString());
            impl.setTemplate(map.get("TEMPLATE_KIND") != null ? map.get("TEMPLATE_KIND").toString() : null);
            impl.setTemplateConfig(map.get("TEMPLATE_CONFIG").toString());
            impls.add(impl);
        }
        return impls;
    }
}

