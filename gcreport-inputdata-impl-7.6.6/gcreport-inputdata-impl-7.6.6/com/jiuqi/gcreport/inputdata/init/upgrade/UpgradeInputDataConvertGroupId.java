/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.consolidatedsystem.dao.InputDataSchemeDao
 *  com.jiuqi.gcreport.consolidatedsystem.entity.InputDataSchemeEO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.init.upgrade;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.consolidatedsystem.dao.InputDataSchemeDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.InputDataSchemeEO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Component
public class UpgradeInputDataConvertGroupId {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional(rollbackFor={Exception.class})
    public void execute() {
        EntNativeSqlDefaultDao dao = EntNativeSqlDefaultDao.getInstance();
        if (this.checkRepairRecord((EntNativeSqlDefaultDao<DefaultTableEntity>)dao)) {
            return;
        }
        this.repairInputData((EntNativeSqlDefaultDao<DefaultTableEntity>)dao);
        this.addRepairRecord((EntNativeSqlDefaultDao<DefaultTableEntity>)dao);
    }

    private void addIndex() {
        InputDataSchemeDao inputDataSchemeDao = (InputDataSchemeDao)SpringContextUtils.getBean(InputDataSchemeDao.class);
        IRuntimeDataSchemeService iRuntimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
        DesignDataModelService designDataModelService = (DesignDataModelService)SpringContextUtils.getBean(DesignDataModelService.class);
        DataModelDeployService dataModelDeployService = (DataModelDeployService)SpringContextUtils.getBean(DataModelDeployService.class);
        List inputDataSchemeList = inputDataSchemeDao.loadAll();
        for (InputDataSchemeEO inputDataScheme : inputDataSchemeList) {
            List dataFieldDeployInfos = iRuntimeDataSchemeService.getDeployInfoByDataTableKey(inputDataScheme.getTableKey());
            if (CollectionUtils.isEmpty(dataFieldDeployInfos) && dataFieldDeployInfos.size() > 1) {
                return;
            }
            String tableModelId = ((DataFieldDeployInfo)dataFieldDeployInfos.get(0)).getTableModelKey();
            String tableName = ((DataFieldDeployInfo)dataFieldDeployInfos.get(0)).getTableName();
            Map<String, String> fieldGroupByCode = dataFieldDeployInfos.stream().collect(Collectors.toMap(DataFieldDeployInfo::getFieldName, DataFieldDeployInfo::getColumnModelKey));
            String convertGroupidFieldId = fieldGroupByCode.get("CONVERTGROUPID");
            designDataModelService.addIndexToTable(tableModelId, new String[]{convertGroupidFieldId}, "IDX_" + tableName + "COM4", IndexModelType.NORMAL);
            try {
                dataModelDeployService.deployTable(tableModelId);
            }
            catch (Exception e) {
                this.logger.error("\u8868" + tableName + "\u589e\u52a0\u7d22\u5f15\u5f02\u5e38", e);
                throw new RuntimeException(e);
            }
        }
    }

    private void repairInputData(EntNativeSqlDefaultDao<DefaultTableEntity> dao) {
        InputDataSchemeDao inputDataSchemeDao = (InputDataSchemeDao)SpringContextUtils.getBean(InputDataSchemeDao.class);
        List inputDataSchemeList = inputDataSchemeDao.loadAll();
        for (InputDataSchemeEO inputDataScheme : inputDataSchemeList) {
            String tableCode = inputDataScheme.getTableCode();
            Map<String, String> idToConvertSrcIdMap = this.getIdToConvertSrcIdMap(dao, tableCode);
            if (idToConvertSrcIdMap == null || idToConvertSrcIdMap.isEmpty()) continue;
            List<Set<String>> convertGroupList = this.getConvertGroupList(idToConvertSrcIdMap);
            this.batchUpdateConvertGroupId(convertGroupList, dao, tableCode);
        }
    }

    private void batchUpdateConvertGroupId(List<Set<String>> convertGroupList, EntNativeSqlDefaultDao<DefaultTableEntity> dao, String tableCode) {
        String updateSqlTemplate = " update " + tableCode + " set ConvertGroupId = ? where %1$s ";
        for (Set<String> idSet : convertGroupList) {
            String updateSql = String.format(updateSqlTemplate, SqlUtils.getConditionOfMulStrUseOr(idSet, (String)"id"));
            dao.execute(updateSql, new Object[]{UUIDUtils.newUUIDStr()});
        }
    }

    private List<Set<String>> getConvertGroupList(Map<String, String> idToConvertSrcIdMap) {
        ArrayList<Set<String>> result = new ArrayList<Set<String>>();
        HashSet processedIdSet = new HashSet();
        for (String id : idToConvertSrcIdMap.keySet()) {
            if (processedIdSet.contains(id)) continue;
            HashSet<String> idSet = new HashSet<String>();
            idSet.add(id);
            String convertSrcId = idToConvertSrcIdMap.get(id);
            while (!idSet.contains(convertSrcId) && !processedIdSet.contains(convertSrcId)) {
                idSet.add(convertSrcId);
                if (!StringUtils.isEmpty((String)(convertSrcId = idToConvertSrcIdMap.get(convertSrcId)))) continue;
            }
            result.add(idSet);
            processedIdSet.addAll(idSet);
        }
        return result;
    }

    private Map<String, String> getIdToConvertSrcIdMap(EntNativeSqlDefaultDao<DefaultTableEntity> dao, String tableCode) {
        String sql = "SELECT ID,CONVERTSRCID FROM " + tableCode + " WHERE CONVERTSRCID IS NOT NULL AND CONVERTSRCID <> '' ";
        return (Map)dao.selectEntityAssignResultExtractor(sql, ps -> {}, rs -> {
            HashMap<Object, Object> result = new HashMap<Object, Object>(256);
            while (rs.next()) {
                result.put(rs.getObject(1, String.class), rs.getObject(2, String.class));
            }
            return result;
        });
    }

    private boolean checkRepairRecord(EntNativeSqlDefaultDao<DefaultTableEntity> dao) {
        String sql = "SELECT CODE FROM INPUTDATA_REPAIR_RECORD WHERE CODE = 'CONVERTGROUPID'";
        String code = (String)dao.selectFirst(String.class, sql, new Object[0]);
        return !StringUtils.isEmpty((String)code);
    }

    private void addRepairRecord(EntNativeSqlDefaultDao<DefaultTableEntity> dao) {
        dao.execute("INSERT INTO INPUTDATA_REPAIR_RECORD VALUES('CONVERTGROUPID')");
    }
}

