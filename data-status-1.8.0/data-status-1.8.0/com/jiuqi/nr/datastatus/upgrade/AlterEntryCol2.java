/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.data.logic.internal.util.ParamUtil
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.common.event.DataModelCacheRefreshListener
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 */
package com.jiuqi.nr.datastatus.upgrade;

import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.common.event.DataModelCacheRefreshListener;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.CollectionUtils;

public class AlterEntryCol2
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(AlterEntryCol2.class);

    public void execute(DataSource dataSource) throws Exception {
        NrdbHelper nrdbHelper = (NrdbHelper)BeanUtil.getBean(NrdbHelper.class);
        if (!nrdbHelper.isEnableNrdb()) {
            AlterEntryCol2.modifyColModel();
        }
    }

    private static void modifyColModel() {
        ParamUtil paramUtil = (ParamUtil)BeanUtil.getBean(ParamUtil.class);
        List allFormSchemeCodes = paramUtil.getAllFormScheme();
        if (!CollectionUtils.isEmpty(allFormSchemeCodes)) {
            DataModelCacheRefreshListener dataModelCacheRefreshListener = (DataModelCacheRefreshListener)BeanUtil.getBean(DataModelCacheRefreshListener.class);
            JdbcTemplate jdbcTemplate = (JdbcTemplate)BeanUtil.getBean(JdbcTemplate.class);
            NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate((JdbcOperations)jdbcTemplate);
            List statusTableNames = allFormSchemeCodes.stream().map(o -> "DE_DAST_" + o).collect(Collectors.toList());
            String tIdSql = "SELECT TABLE_ID FROM NVWA_TABLEMODEL WHERE TABLE_NAME IN(:tableNames)";
            HashMap tIdParams = new HashMap();
            tIdParams.put("tableNames", statusTableNames);
            List tableIds = namedParameterJdbcTemplate.queryForList(tIdSql, tIdParams, String.class);
            String updateRunSql = "UPDATE NVWA_COLUMNMODEL SET FIELD_DATATYPE=:colType WHERE TABLE_ID IN(:tableIds) AND FIELD_NAME='DAST_ISENTRY'";
            String updateDesSql = "UPDATE NVWA_COLUMNMODEL_DES SET FIELD_DATATYPE=:colType WHERE TABLE_ID IN(:tableIds) AND FIELD_NAME='DAST_ISENTRY'";
            HashMap<String, Object> upParams = new HashMap<String, Object>();
            upParams.put("colType", ColumnModelType.INTEGER.getValue());
            upParams.put("tableIds", tableIds);
            namedParameterJdbcTemplate.update(updateRunSql, upParams);
            namedParameterJdbcTemplate.update(updateDesSql, upParams);
            dataModelCacheRefreshListener.onClearCache();
            logger.info("DE_DAST\u8868DAST_ISENTRY\u5b57\u6bb5\u5347\u7ea7\u5b8c\u6210");
        }
    }
}

