/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.dimension.update.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.dimension.update.DimensionMenuUpdateService;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DimensionMenuUpdateServiceImpl
implements DimensionMenuUpdateService {
    private static final Logger logger = LoggerFactory.getLogger(DimensionMenuUpdateServiceImpl.class);
    private static final String INSERT_SQL = "INSERT INTO NVWA_ROUTE ( %1$s ) values (%2$s) ";
    private static final String R_TITLE = "R_TITLE";
    private static final String APP_NAME = "APP_NAME";
    private static final String PRODUCT_LINE = "PRODUCT_LINE";
    private static final String BD_DIM_TITLE = "\u7ef4\u5ea6\u7ba1\u7406";
    private static final String BD_DIM_APP_NAME = "dimension";
    private static final String BD_MEASUREMENT_TITLE = "\u6307\u6807\u7ba1\u7406";
    private static final String BD_MEASUREMENT_APP_NAME = "measurement";
    private static final String BD_PRODUCT_LINE = "@bd";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void updateMenu() {
        logger.info("\u5f00\u59cb\u5347\u7ea7\u5386\u53f2\u5408\u5e76\u7ef4\u5ea6\u7ba1\u7406\u529f\u80fd\u83dc\u5355");
        String sql = "SELECT * FROM  NVWA_ROUTE WHERE APP_NAME='dimension' AND PRODUCT_LINE = '@gc' ORDER BY R_ORDINAL";
        List oldDimMenuList = this.jdbcTemplate.queryForList(sql);
        if (CollectionUtils.isEmpty((Collection)oldDimMenuList)) {
            logger.info("\u4e0d\u5b58\u5728\u5386\u53f2\u5408\u5e76\u7ef4\u5ea6\u7ba1\u7406\u529f\u80fd\u83dc\u5355\uff0c\u8df3\u8fc7\u5347\u7ea7");
            return;
        }
        String firstOldDimId = null;
        String firstOldDimDesignId = null;
        Map<String, Map<String, Object>> idToRowDataMap = oldDimMenuList.stream().collect(Collectors.toMap(item -> ConverterUtils.getAsString(item.get("ID")), item -> item));
        for (Map rowData : oldDimMenuList) {
            String designId = ConverterUtils.getAsString(rowData.get("DESIGN_ID"));
            if (StringUtils.isEmpty((String)designId) || !idToRowDataMap.containsKey(designId)) continue;
            firstOldDimId = ConverterUtils.getAsString(rowData.get("ID"));
            firstOldDimDesignId = designId;
            break;
        }
        this.addDimAndMeasurement(idToRowDataMap, firstOldDimDesignId, firstOldDimId);
        String updateSql = "UPDATE NVWA_ROUTE SET APP_NAME = 'hypermodel',PRODUCT_LINE = '@bd',R_TITLE = '\u591a\u7ef4\u6a21\u578b\u7ba1\u7406\uff08\u7ef4\u5ea6\u7ba1\u7406\uff09' WHERE APP_NAME='dimension' AND PRODUCT_LINE = '@gc' ";
        this.jdbcTemplate.update(updateSql);
    }

    private void addDimAndMeasurement(Map<String, Map<String, Object>> idToRowDataMap, String firstOldDimDesignId, String firstOldDimId) {
        String dimDesignId = UUIDUtils.newUUIDStr();
        Map<String, Object> oldDimMenuDesign = idToRowDataMap.get(firstOldDimDesignId);
        HashMap<String, Object> newDimMenuDesign = new HashMap<String, Object>();
        newDimMenuDesign.putAll(oldDimMenuDesign);
        newDimMenuDesign.put("ID", dimDesignId);
        newDimMenuDesign.put(R_TITLE, BD_DIM_TITLE);
        newDimMenuDesign.put(APP_NAME, BD_DIM_APP_NAME);
        newDimMenuDesign.put(PRODUCT_LINE, BD_PRODUCT_LINE);
        String measurementDesignId = UUIDUtils.newUUIDStr();
        HashMap<String, Object> newMeasurementMenuDesign = new HashMap<String, Object>();
        newMeasurementMenuDesign.putAll(oldDimMenuDesign);
        newMeasurementMenuDesign.put("ID", measurementDesignId);
        newMeasurementMenuDesign.put(R_TITLE, BD_MEASUREMENT_TITLE);
        newMeasurementMenuDesign.put(APP_NAME, BD_MEASUREMENT_APP_NAME);
        newMeasurementMenuDesign.put(PRODUCT_LINE, BD_PRODUCT_LINE);
        Map<String, Object> oldDimMenu = idToRowDataMap.get(firstOldDimId);
        HashMap<String, Object> newDimMenu = new HashMap<String, Object>();
        newDimMenu.putAll(oldDimMenu);
        newDimMenu.put("ID", UUIDUtils.newUUIDStr());
        newDimMenu.put("DESIGN_ID", dimDesignId);
        newDimMenu.put(R_TITLE, BD_DIM_TITLE);
        newDimMenu.put(APP_NAME, BD_DIM_APP_NAME);
        newDimMenu.put(PRODUCT_LINE, BD_PRODUCT_LINE);
        HashMap<String, Object> newMeasurementMenu = new HashMap<String, Object>();
        newMeasurementMenu.putAll(oldDimMenu);
        newMeasurementMenu.put("ID", UUIDUtils.newUUIDStr());
        newMeasurementMenu.put("DESIGN_ID", measurementDesignId);
        newMeasurementMenu.put(R_TITLE, BD_MEASUREMENT_TITLE);
        newMeasurementMenu.put(APP_NAME, BD_MEASUREMENT_APP_NAME);
        newMeasurementMenu.put(PRODUCT_LINE, BD_PRODUCT_LINE);
        this.addMenu(newDimMenuDesign);
        this.addMenu(newMeasurementMenuDesign);
        this.addMenu(newDimMenu);
        this.addMenu(newMeasurementMenu);
    }

    private void addMenu(Map<String, Object> menuData) {
        StringBuilder columnSql = new StringBuilder();
        StringBuilder columnValueSql = new StringBuilder();
        for (String column : menuData.keySet()) {
            columnSql.append(column).append(",");
            if (menuData.get(column) == null) {
                columnValueSql.append("null,");
                continue;
            }
            columnValueSql.append("'").append(menuData.get(column)).append("',");
        }
        this.jdbcTemplate.update(String.format(INSERT_SQL, columnSql.substring(0, columnSql.length() - 1), columnValueSql.substring(0, columnValueSql.length() - 1)));
    }
}

