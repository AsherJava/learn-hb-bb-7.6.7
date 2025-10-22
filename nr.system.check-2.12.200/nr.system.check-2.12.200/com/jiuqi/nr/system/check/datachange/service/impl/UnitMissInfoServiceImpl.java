/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.system.check.datachange.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.system.check.datachange.bean.UnitInfo;
import com.jiuqi.nr.system.check.datachange.dao.impl.UnitInfoDao;
import com.jiuqi.nr.system.check.datachange.service.UnitMissInfoService;
import com.jiuqi.nr.system.check.datachange.util.DwUpper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class UnitMissInfoServiceImpl
implements UnitMissInfoService {
    private static final Logger logger = LoggerFactory.getLogger(UnitMissInfoServiceImpl.class);
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private UnitInfoDao unitInfoDao;
    private final List<Integer> dataTableType = new ArrayList<Integer>(Arrays.asList(DataTableType.TABLE.getValue(), DataTableType.MD_INFO.getValue(), DataTableType.DETAIL.getValue()));
    private static final String MDCODE = "MDCODE";
    private static final String DATATIME = "DATATIME";
    private static final String dealSql = "SELECT %s,%s FROM %s GROUP BY %s,%s";

    @Override
    public List<UnitInfo> getMissUnitInfo(String dataSchemeKey) {
        this.unitInfoDao.deleteOldInfoByDataSchemeKey(dataSchemeKey);
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(dataSchemeKey);
        DwUpper dwUpper = new DwUpper(this.jdbcTemplate);
        ArrayList<UnitInfo> result = new ArrayList<UnitInfo>();
        logger.info("\u6570\u636e\u65b9\u6848{}\u5f00\u59cb\u5224\u65ad\u6570\u636e\u8868\u4e2d\u5355\u4f4d\u4ee3\u7801\u662f\u5426\u5728\u5f53\u524d\u7ec4\u7ec7\u673a\u6784\u4e2d\u3002", (Object)dataScheme.getTitle());
        List dataSchemeDimension = this.dataSchemeService.getDataSchemeDimension(dataScheme.getKey(), DimensionType.UNIT_SCOPE);
        if (CollectionUtils.isEmpty(dataSchemeDimension)) {
            dataSchemeDimension = this.dataSchemeService.getDataSchemeDimension(dataScheme.getKey(), DimensionType.UNIT);
        }
        List periodDim = this.dataSchemeService.getDataSchemeDimension(dataScheme.getKey(), DimensionType.PERIOD);
        DataDimension entityDimension = (DataDimension)dataSchemeDimension.get(0);
        List allDataTable = this.dataSchemeService.getAllDataTable(dataScheme.getKey());
        HashSet<UnitInfo> allInfos = new HashSet<UnitInfo>();
        for (DataTable dataTable : allDataTable) {
            if (!this.dataTableType.contains(dataTable.getDataTableType().getValue())) continue;
            DataField unitField = this.dataSchemeService.getDataFieldByTableKeyAndCode(dataTable.getKey(), MDCODE);
            List list = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{unitField.getKey()});
            Map<String, DataFieldDeployInfo> collect = list.stream().collect(Collectors.toMap(DataFieldDeployInfo::getTableName, a -> a));
            for (String tableName : collect.keySet()) {
                allInfos.addAll(this.getUnitInfo(dwUpper, dataSchemeKey, tableName));
            }
        }
        Map<String, List<UnitInfo>> collect = allInfos.stream().collect(Collectors.groupingBy(UnitInfo::getPeriod));
        HashSet<UnitInfo> missResult = new HashSet<UnitInfo>();
        try {
            for (Map.Entry entry : collect.entrySet()) {
                IEntityTable entityTable = this.getEntityTable(entityDimension.getDimKey(), ((DataDimension)periodDim.get(0)).getDimKey(), (String)entry.getKey());
                List value = (List)entry.getValue();
                for (UnitInfo unitInfo : value) {
                    IEntityRow byEntityKey = entityTable.findByEntityKey(unitInfo.getUnit());
                    if (byEntityKey != null) continue;
                    missResult.add(unitInfo);
                }
            }
            result.addAll(missResult);
            this.unitInfoDao.insert(new ArrayList<UnitInfo>(missResult));
        }
        catch (Exception e) {
            logger.error("\u6570\u636e\u65b9\u6848" + dataScheme.getTitle() + "\u8bb0\u5f55\u5355\u4f4d\u662f\u5426\u5b58\u5728\u4e0e\u5f53\u524d\u7ec4\u7ec7\u673a\u6784\u5931\u8d25!", e);
        }
        logger.info("\u6570\u636e\u65b9\u6848{}\u5224\u65ad\u6570\u636e\u8868\u4e2d\u5355\u4f4d\u4ee3\u7801\u662f\u5426\u5728\u5f53\u524d\u7ec4\u7ec7\u673a\u6784\u4e2d\u5b8c\u6210\u3002", (Object)dataScheme.getTitle());
        return result;
    }

    @Override
    public String export(String dataSchemeKey, Sheet tableSheet) {
        DataScheme dataScheme = this.dataSchemeService.getDataScheme(dataSchemeKey);
        List<UnitInfo> unitInfos = this.unitInfoDao.findByOrg(dataSchemeKey);
        Row row = tableSheet.createRow(0);
        row.createCell(0).setCellValue("\u6570\u636e\u65b9\u6848");
        row.createCell(1).setCellValue("\u5355\u4f4d\u4ee3\u7801");
        row.createCell(2).setCellValue("\u65f6\u671f");
        if (CollectionUtils.isEmpty(unitInfos)) {
            return "\u6a21\u677f";
        }
        int offset = 1;
        for (int i = 0; i < unitInfos.size(); ++i) {
            Row dataRow = tableSheet.createRow(i + offset);
            dataRow.createCell(0).setCellValue(unitInfos.get(i).getEntityId());
            dataRow.createCell(1).setCellValue(unitInfos.get(i).getUnit());
            dataRow.createCell(2).setCellValue(unitInfos.get(i).getPeriod());
        }
        return "\u6570\u636e\u65b9\u6848" + dataScheme.getTitle() + "\u7684\u6570\u636e\u8868\u4e2d\u5355\u4f4d\u4e22\u5931\u7684\u4fe1\u606f";
    }

    private Set<UnitInfo> getUnitInfo(DwUpper dwUpper, String entityId, String tableName) {
        String deal = String.format(dealSql, MDCODE, DATATIME, tableName, MDCODE, DATATIME);
        return (Set)dwUpper.query(deal, null, rs -> {
            HashSet<UnitInfo> infos = new HashSet<UnitInfo>();
            while (rs.next()) {
                String unit = rs.getString(1);
                String period = rs.getString(2);
                UnitInfo unitInfo = new UnitInfo();
                unitInfo.setUnit(unit);
                unitInfo.setPeriod(period);
                unitInfo.setEntityId(entityId);
                infos.add(unitInfo);
            }
            return infos;
        });
    }

    private IEntityTable getEntityTable(String entityId, String periodView, String period) throws Exception {
        EntityViewDefine entityViewDefine = this.iEntityViewRunTimeController.buildEntityView(entityId);
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue(DATATIME, (Object)period);
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.sorted(true);
        query.setEntityView(entityViewDefine);
        query.setMasterKeys(dimensionValueSet);
        query.setAuthorityOperations(AuthorityType.Read);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setVarDimensionValueSet(dimensionValueSet);
        context.setPeriodView(periodView);
        return query.executeReader((IContext)context);
    }
}

