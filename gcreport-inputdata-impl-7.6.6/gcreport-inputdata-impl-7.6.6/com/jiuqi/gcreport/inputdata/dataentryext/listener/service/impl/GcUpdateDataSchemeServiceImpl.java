/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.definition.internal.controller2.RunTimeViewController
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.inputdata.dataentryext.listener.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSystemService;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.inputdata.dataentryext.listener.service.GcUpdateDataSchemeService;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.definition.internal.controller2.RunTimeViewController;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class GcUpdateDataSchemeServiceImpl
implements GcUpdateDataSchemeService {
    private final Logger logger = LoggerFactory.getLogger(GcUpdateDataSchemeServiceImpl.class);
    @Autowired
    private RunTimeViewController runTimeViewController;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ConsolidatedSystemService consolidatedSystemService;

    @Override
    public void addDataSchemeAdjustDimExecute(DesignDataScheme dataScheme, List<DesignDataDimension> addDimensions) {
        if (CollectionUtils.isEmpty(addDimensions) || ObjectUtils.isEmpty(dataScheme) || StringUtils.isEmpty((String)dataScheme.getKey())) {
            return;
        }
        try {
            this.execute(dataScheme, addDimensions);
        }
        catch (Exception e) {
            this.logger.error("\u4fee\u6539\u6570\u636e\u65b9\u6848\u540e\u5904\u7406\u62b5\u9500\u5206\u5f55\u4fe1\u606f\u53d1\u751f\u5f02\u5e38", e);
        }
    }

    private void execute(DesignDataScheme dataScheme, List<DesignDataDimension> addDimensions) {
        Optional<DesignDataDimension> dimensionOptional = addDimensions.stream().filter(designDataDimension -> "ADJUST".equals(designDataDimension.getDimKey())).findFirst();
        if (dimensionOptional.isPresent()) {
            if (this.checkIsUpgrade(dataScheme.getKey())) {
                return;
            }
            this.updateAdjustItems(dataScheme.getKey());
            this.addUpgradeInfo(dataScheme.getKey());
        }
    }

    private void updateAdjustItems(String dataSchemeKey) {
        this.updateItemAdjustBySystemId(dataSchemeKey);
        this.updateItemAdjustByTaskId(dataSchemeKey);
    }

    private void updateItemAdjustByTaskId(String dataSchemeKey) {
        List<String> taskIds = this.listTaskIdByDataSchemeKey(dataSchemeKey);
        this.updateAdjustTableItems("GC_OFFSETVCHRITEM", "TASKID", taskIds);
        this.updateAdjustTableItems("GC_SAMECTRLOFFSETITEM", "TASKID", taskIds);
    }

    private void updateItemAdjustBySystemId(String dataSchemeKey) {
        List<String> systemIds = this.listSystemIdByDataSchemeKey(dataSchemeKey);
        this.updateAdjustTableItems("GC_OFFSETRELATEDITEM", "SYSTEMID", systemIds);
    }

    private boolean checkIsUpgrade(String dataSchemeKey) {
        String selectSql = "SELECT v.DATASCHEMEKEY FROM GC_OFFSETVCHR_ADJUST v  WHERE v.DATASCHEMEKEY =?";
        JdbcTemplate jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
        List entities = jdbcTemplate.queryForList(selectSql, new Object[]{dataSchemeKey});
        return !CollectionUtils.isEmpty((Collection)entities);
    }

    private void addUpgradeInfo(String dataSchemeKey) {
        String insertSql = "INSERT INTO GC_OFFSETVCHR_ADJUST VALUES('" + dataSchemeKey + "')";
        this.jdbcTemplate.execute(insertSql);
    }

    private List<String> listTaskIdByDataSchemeKey(String dataSchemeKey) {
        List taskDefines = this.runTimeViewController.listAllTask();
        if (CollectionUtils.isEmpty((Collection)taskDefines) || StringUtils.isEmpty((String)dataSchemeKey)) {
            return CollectionUtils.newArrayList();
        }
        return taskDefines.stream().filter(taskDefine -> !StringUtils.isEmpty((String)taskDefine.getDataScheme()) && dataSchemeKey.equals(taskDefine.getDataScheme())).map(IBaseMetaItem::getKey).collect(Collectors.toList());
    }

    private List<String> listSystemIdByDataSchemeKey(String dataSchemeKey) {
        List consolidatedSystems = this.consolidatedSystemService.getConsolidatedSystemEOS();
        if (CollectionUtils.isEmpty((Collection)consolidatedSystems) || StringUtils.isEmpty((String)dataSchemeKey)) {
            return CollectionUtils.newArrayList();
        }
        return consolidatedSystems.stream().filter(consolidatedSystemEO -> !StringUtils.isEmpty((String)consolidatedSystemEO.getDataSchemeKey()) && dataSchemeKey.equals(consolidatedSystemEO.getDataSchemeKey())).map(DefaultTableEntity::getId).collect(Collectors.toList());
    }

    private void updateAdjustTableItems(String tableName, String fieldName, final List<String> params) {
        if (CollectionUtils.isEmpty(params) || StringUtils.isEmpty((String)tableName) || StringUtils.isEmpty((String)fieldName)) {
            return;
        }
        String updateSql = "UPDATE " + tableName + " v  SET v.ADJUST='0' WHERE (v.ADJUST IS NULL OR v.ADJUST ='') AND v." + fieldName + " =?";
        try {
            this.jdbcTemplate.batchUpdate(updateSql, new BatchPreparedStatementSetter(){

                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, (String)params.get(i));
                }

                public int getBatchSize() {
                    return params.size();
                }
            });
        }
        catch (Exception e) {
            this.logger.error("\u5f00\u542f\u8c03\u6574\u671f\uff0c\u66f4\u65b0\u5408\u5e76\u8868\u8c03\u6574\u671f\u9ed8\u8ba4\u503c\u5f02\u5e38\uff0cSQL=" + updateSql, e);
        }
    }
}

