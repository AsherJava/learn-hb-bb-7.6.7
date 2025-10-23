/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataGroup
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.DataTableRel
 *  com.jiuqi.nr.datascheme.api.FieldSearchQuery
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.core.DataSchemeDeployStatus
 *  com.jiuqi.nr.datascheme.api.core.DeployStatusEnum
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nvwa.definition.interval.dao.TableCheckDao
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.datascheme.internal.service.impl;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataGroup;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.DataTableRel;
import com.jiuqi.nr.datascheme.api.FieldSearchQuery;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.core.DataSchemeDeployStatus;
import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.dao.IDataDimDao;
import com.jiuqi.nr.datascheme.internal.dao.IDataGroupDao;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataFieldDeployInfoDaoImpl;
import com.jiuqi.nr.datascheme.internal.dao.impl.DataSchemeDeployStatusDaoImpl;
import com.jiuqi.nr.datascheme.internal.dto.DataDimDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.dto.DataTableDTO;
import com.jiuqi.nr.datascheme.internal.entity.DataDimDO;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.datascheme.internal.entity.DataGroupDO;
import com.jiuqi.nr.datascheme.internal.entity.DataSchemeDeployStatusDO;
import com.jiuqi.nr.datascheme.internal.service.DataFieldService;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.datascheme.internal.service.DataTableRelService;
import com.jiuqi.nr.datascheme.internal.service.DataTableService;
import com.jiuqi.nr.datascheme.internal.service.impl.RuntimeDataSchemeServiceImpl;
import com.jiuqi.nvwa.definition.interval.dao.TableCheckDao;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service(value="RuntimeDataSchemeNoCacheServiceImpl-NO_CACHE")
public class RuntimeDataSchemeNoCacheServiceImpl
implements IRuntimeDataSchemeService {
    private static final Logger logger = LoggerFactory.getLogger(RuntimeDataSchemeServiceImpl.class);
    @Autowired
    @Qualifier(value="RuntimeDataFieldServiceImpl-NO_CACHE")
    private DataFieldService dataFieldService;
    @Autowired
    @Qualifier(value="RuntimeDataTableServiceImpl-NO_CACHE")
    private DataTableService dataTableService;
    @Autowired
    @Qualifier(value="RuntimeDataTableRelServiceImpl-NO_CACHE")
    private DataTableRelService dataTableRelService;
    @Autowired
    @Qualifier(value="RuntimeSchemeServiceImpl-NO_CACHE")
    private DataSchemeService dataSchemeService;
    @Autowired
    private DataFieldDeployInfoDaoImpl deployInfoService;
    @Autowired
    private IDataGroupDao<DataGroupDO> dataGroupDao;
    @Autowired
    private IDataDimDao<DataDimDO> dataDimDao;
    @Autowired
    private DataSchemeDeployStatusDaoImpl deployStatusDao;
    @Autowired
    private TableCheckDao tableCheckDao;

    public DataScheme getDataScheme(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        return this.dataSchemeService.getDataScheme(key);
    }

    public DataScheme getDataSchemeByCode(String code) {
        Assert.notNull((Object)code, "code must not be null.");
        return this.dataSchemeService.getDataSchemeByCode(code);
    }

    public List<DataScheme> getDataSchemeByParent(String parent) {
        if (parent == null) {
            parent = "00000000-0000-0000-0000-000000000000";
        }
        List<DataSchemeDTO> byParent = this.dataSchemeService.getDataSchemeByParent(parent);
        return new ArrayList<DataScheme>(byParent);
    }

    public List<DataScheme> getDataSchemeByPeriodType(PeriodType periodType) {
        Assert.notNull((Object)periodType, "periodType must not be null.");
        List<DataDimDO> dim = this.dataDimDao.getByPeriodType(periodType);
        if (dim.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<String> dataSchemeKeys = new ArrayList<String>();
        for (DataDimDO dataDimDO : dim) {
            String dataSchemeKey = dataDimDO.getDataSchemeKey();
            dataSchemeKeys.add(dataSchemeKey);
        }
        List<DataSchemeDTO> dataSchemes = this.dataSchemeService.getDataSchemes(dataSchemeKeys);
        if (dataSchemes == null || dataSchemes.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<DataScheme>(dataSchemes);
    }

    public List<DataScheme> getDataSchemeByDimKey(String ... dimKey) {
        Assert.notNull((Object)dimKey, "dimKey must not be null.");
        List<DataDimDO> byDimKey = this.dataDimDao.getByDimKey(dimKey);
        if (byDimKey.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<String> dataSchemeKey = new ArrayList<String>();
        for (DataDimDO dataDimDO : byDimKey) {
            dataSchemeKey.add(dataDimDO.getDataSchemeKey());
        }
        List<DataSchemeDTO> dataSchemes = this.dataSchemeService.getDataSchemes(dataSchemeKey);
        if (dataSchemes == null || dataSchemes.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<DataScheme>(dataSchemes);
    }

    public List<DataScheme> getDataSchemes(List<String> keys) {
        Assert.notNull(keys, "keys must not be null.");
        List<DataSchemeDTO> dataScheme = this.dataSchemeService.getDataSchemes(keys);
        return new ArrayList<DataScheme>(dataScheme);
    }

    public List<DataScheme> getAllDataScheme() {
        List<DataSchemeDTO> all = this.dataSchemeService.getAllDataScheme();
        return new ArrayList<DataScheme>(all);
    }

    public DataGroup getDataGroup(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        DataGroupDO dataGroupDO = this.dataGroupDao.get(key);
        return Convert.iDg2Dto(dataGroupDO);
    }

    public List<DataGroup> getDataGroups(List<String> keys) {
        Assert.notNull(keys, "keys must not be null.");
        List<DataGroupDO> list = this.dataGroupDao.batchGet(keys);
        return this.buildGroupResult(list);
    }

    public List<DataGroup> getAllDataGroup(String scheme) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        List<DataGroupDO> list = this.dataGroupDao.getByScheme(scheme);
        return this.buildGroupResult(list);
    }

    public List<DataGroup> getDataGroupByScheme(String scheme) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        List<DataGroupDO> list = this.dataGroupDao.getByCondition(scheme, null);
        return this.buildGroupResult(list);
    }

    public List<DataGroup> getDataGroupByParent(String parentKey) {
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        List<DataGroupDO> list = this.dataGroupDao.getByParent(parentKey);
        return this.buildGroupResult(list);
    }

    public DataTable getDataTable(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        return this.dataTableService.getDataTable(key);
    }

    public DataTable getDataTableByCode(String code) {
        Assert.notNull((Object)code, "code must not be null.");
        return this.dataTableService.getDataTableByCode(code);
    }

    public DataTable getLatestDataTableByScheme(String scheme) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        return this.dataTableService.getLatestDataTableByScheme(scheme).stream().findFirst().orElse(null);
    }

    public List<DataTable> getLatestDataTablesByScheme(String scheme) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        List<DataTableDTO> list = this.dataTableService.getLatestDataTableByScheme(scheme);
        if (list == null) {
            return Collections.emptyList();
        }
        return new ArrayList<DataTable>(list);
    }

    public Instant getLatestDataTableUpdateTime(String scheme) {
        return this.dataTableService.getLatestDataTableUpdateTime(scheme);
    }

    public DataTable getFmDataTableBySchemeAndDimKey(String scheme, String dimKey) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        Assert.notNull((Object)dimKey, "dimKey must not be null.");
        List<DataTable> tables = this.getFmDataTableBySchemeKey(scheme);
        if (CollectionUtils.isEmpty(tables)) {
            return null;
        }
        for (DataTable table : tables) {
            String dataEntityKey;
            String idKey;
            DataFieldDTO dataField;
            String[] idKeys = table.getBizKeys();
            if (idKeys == null || idKeys.length == 0 || (dataField = this.dataFieldService.getDataField(idKey = idKeys[0])) == null || !dimKey.equals(dataEntityKey = dataField.getRefDataEntityKey())) continue;
            return table;
        }
        return null;
    }

    public List<DataTable> getFmDataTableBySchemeKey(String scheme) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        List<DataTableDTO> tables = this.dataTableService.getDataTableByScheme(scheme);
        if (CollectionUtils.isEmpty(tables)) {
            return Collections.emptyList();
        }
        if (CollectionUtils.isEmpty(tables = tables.stream().filter(t -> t.getDataTableType() == null).collect(Collectors.toList()))) {
            return Collections.emptyList();
        }
        return new ArrayList<DataTable>(tables);
    }

    public List<DataTable> getDataTables(List<String> keys) {
        Assert.notNull(keys, "keys must not be null.");
        List<DataTableDTO> dataTables = this.dataTableService.getDataTables(keys);
        return new ArrayList<DataTable>(dataTables);
    }

    public List<DataTable> getAllDataTable(String scheme) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        List<DataTableDTO> allDataTable = this.dataTableService.getAllDataTable(scheme);
        return new ArrayList<DataTable>(allDataTable);
    }

    public List<DataTable> getAllDataTableBySchemeAndTypes(String schemeKey, DataTableType ... types) {
        Assert.notNull((Object)schemeKey, "scheme must not be null.");
        List<DataTableDTO> allDataTable = this.dataTableService.getAllDataTableBySchemeAndTypes(schemeKey, types);
        return new ArrayList<DataTable>(allDataTable);
    }

    public List<DataTable> getDataTableByGroup(String parentKey) {
        Assert.notNull((Object)parentKey, "parentKey must not be null.");
        List<DataTableDTO> dataTableByGroup = this.dataTableService.getDataTableByGroup(parentKey);
        return new ArrayList<DataTable>(dataTableByGroup);
    }

    public List<DataTable> getDataTableByScheme(String schemeKey) {
        Assert.notNull((Object)schemeKey, "schemeKey must not be null.");
        List<DataTableDTO> dataTableByScheme = this.dataTableService.getDataTableByScheme(schemeKey);
        return new ArrayList<DataTable>(dataTableByScheme);
    }

    public DataField getDataField(String key) {
        Assert.notNull((Object)key, "key must not be null.");
        return this.dataFieldService.getDataField(key);
    }

    public DataField getDataFieldByColumnKey(String columnKey) {
        DataFieldDeployInfo deployInfo = this.getDeployInfoByColumnKey(columnKey);
        if (deployInfo != null) {
            return this.getDataField(deployInfo.getDataFieldKey());
        }
        return null;
    }

    public DataField getDataFieldByTableKeyAndCode(String table, String code) {
        Assert.notNull((Object)table, "table must not be null.");
        Assert.notNull((Object)code, "code must not be null.");
        return this.dataFieldService.getDataFieldByTableKeyAndCode(table, code);
    }

    public DataField getZbKindDataFieldBySchemeKeyAndCode(String scheme, String code) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        Assert.notNull((Object)code, "code must not be null.");
        return this.dataFieldService.getZbKindDataFieldBySchemeKeyAndCode(scheme, code);
    }

    public List<DataField> getFmKindDataFieldsBySchemeAndKeys(String scheme, String keys) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        Assert.notNull((Object)keys, "bizKey must not be null.");
        List<DataTable> tables = this.getFmDataTableBySchemeKey(scheme);
        if (CollectionUtils.isEmpty(tables)) {
            return Collections.emptyList();
        }
        for (DataTable table : tables) {
            String idKey;
            DataFieldDTO dataField;
            String refDataFieldKey;
            String[] idKeys = table.getBizKeys();
            if (idKeys == null || idKeys.length == 0 || !keys.equals(refDataFieldKey = (dataField = this.dataFieldService.getDataField(idKey = idKeys[0])).getRefDataFieldKey())) continue;
            String tableKey = dataField.getDataTableKey();
            return this.getDataFieldByTable(tableKey);
        }
        return Collections.emptyList();
    }

    public List<DataField> getFmKindDataFieldsBySchemeAndDimKey(String scheme, String dimKey) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        Assert.notNull((Object)dimKey, "dimKey must not be null.");
        DataTable table = this.getFmDataTableBySchemeAndDimKey(scheme, dimKey);
        if (table != null) {
            return this.getDataFieldByTable(table.getKey());
        }
        return Collections.emptyList();
    }

    public List<DataField> getDataFieldByTableCode(String tableCode) {
        Assert.notNull((Object)tableCode, "tableCode must not be null.");
        List<DataFieldDTO> list = this.dataFieldService.getDataFieldByTableCode(tableCode);
        return this.buildResult(list);
    }

    public List<DataField> getBizDataFieldByTableKey(String tableKey) {
        Assert.notNull((Object)tableKey, "tableKey must not be null.");
        List<DataFieldDTO> list = this.dataFieldService.getBizDataFieldByTableKey(tableKey);
        return this.buildResult(list);
    }

    public List<DataField> getBizDataFieldByTableCode(String tableCode) {
        Assert.notNull((Object)tableCode, "tableCode must not be null.");
        List<DataFieldDTO> list = this.dataFieldService.getBizDataFieldByTableCode(tableCode);
        return this.buildResult(list);
    }

    public List<DataField> getDataFieldByTableKeyAndType(String tableKey, DataFieldType ... dataFieldType) {
        Assert.notNull((Object)tableKey, "tableKey must not be null.");
        List<DataFieldDTO> list = dataFieldType == null || 0 == dataFieldType.length ? this.dataFieldService.getDataFieldByTable(tableKey) : this.dataFieldService.getDataFieldByTableKeyAndType(tableKey, dataFieldType);
        return this.buildResult(list);
    }

    public List<DataField> getDataFieldByTableKeyAndKind(String tableKey, DataFieldKind ... dataFieldKinds) {
        Assert.notNull((Object)tableKey, "tableKey must not be null.");
        List<DataFieldDTO> list = dataFieldKinds == null || 0 == dataFieldKinds.length ? this.dataFieldService.getDataFieldByTable(tableKey) : this.dataFieldService.getDataFieldByTableKeyAndKind(tableKey, dataFieldKinds);
        return this.buildResult(list);
    }

    public List<DataField> getDataFieldByTableCodeAndType(String tableCode, DataFieldType ... dataFieldType) {
        Assert.notNull((Object)tableCode, "tableCode must not be null.");
        List<DataFieldDTO> list = dataFieldType == null || 0 == dataFieldType.length ? this.dataFieldService.getDataFieldByTableCode(tableCode) : this.dataFieldService.getDataFieldByTableCodeAndType(tableCode, dataFieldType);
        return this.buildResult(list);
    }

    public List<DataField> getDataFieldByTableCodeAndKind(String tableCode, DataFieldKind ... dataFieldKinds) {
        Assert.notNull((Object)tableCode, "tableCode must not be null.");
        List<DataFieldDTO> list = dataFieldKinds == null || 0 == dataFieldKinds.length ? this.dataFieldService.getDataFieldByTableCode(tableCode) : this.dataFieldService.getDataFieldByTableCodeAndKind(tableCode, dataFieldKinds);
        return this.buildResult(list);
    }

    private List<DataGroup> buildGroupResult(List<DataGroupDO> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return list.stream().map(Convert::iDg2Dto).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private List<DataField> buildResult(List<DataFieldDTO> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return new ArrayList<DataField>(list);
    }

    public List<DataField> getDataFields(List<String> keys) {
        Assert.notNull(keys, "keys must not be null.");
        List<DataFieldDTO> list = this.dataFieldService.getDataFields(keys);
        return this.buildResult(list);
    }

    public List<DataField> getAllDataField(String scheme) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        List<DataFieldDTO> list = this.dataFieldService.getAllDataField(scheme);
        return this.buildResult(list);
    }

    public List<DataField> getAllDataFieldByKind(String scheme, DataFieldKind ... kind) {
        Assert.notNull((Object)scheme, "scheme must not be null.");
        Assert.notNull((Object)kind, "kind must not be null.");
        List<DataFieldDTO> list = this.dataFieldService.getDataFieldBySchemeAndKind(scheme, kind);
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return this.buildResult(list);
    }

    public List<DataField> getDataFieldByTable(String tableKey) {
        Assert.notNull((Object)tableKey, "tableKey must not be null.");
        List<DataFieldDTO> list = this.dataFieldService.getDataFieldByTable(tableKey);
        return this.buildResult(list);
    }

    public List<DataField> getDataFieldByTables(List<String> tableKeys) {
        Assert.notNull(tableKeys, "tableKeys must not be null.");
        List<DataFieldDTO> list = this.dataFieldService.getDataFieldByTables(tableKeys);
        return this.buildResult(list);
    }

    public List<DataDimension> getDataSchemeDimension(String dataSchemeKey) {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null.");
        List<DataDimDTO> dataSchemeDimension = this.dataSchemeService.getDataSchemeDimension(dataSchemeKey);
        if (dataSchemeDimension == null) {
            return null;
        }
        return new ArrayList<DataDimension>(dataSchemeDimension);
    }

    public List<DataDimension> getDataSchemeDimension(String dataSchemeKey, DimensionType dimensionType) {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null.");
        Assert.notNull((Object)dimensionType, "dimensionType must not be null.");
        List<DataDimension> dimension = this.getDataSchemeDimension(dataSchemeKey);
        return dimension.stream().filter(r -> r.getDimensionType() == dimensionType).collect(Collectors.toList());
    }

    public List<DataDimension> getDataDimensionByDimKey(String dimKey) {
        Assert.notNull((Object)dimKey, "dimKey must not be null.");
        List<DataDimDO> byDimKey = this.dataDimDao.getByDimKey(dimKey);
        if (byDimKey.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<DataDimension>(byDimKey);
    }

    @NonNull
    public Boolean enableAdjustPeriod(String dataSchemeKey) {
        List<DataDimDTO> dimensions = this.dataSchemeService.getDataSchemeDimension(dataSchemeKey);
        if (dimensions == null) {
            return Boolean.FALSE;
        }
        return dimensions.stream().anyMatch(x -> "ADJUST".equals(x.getDimKey()));
    }

    @Nullable
    public String getDimAttributeByReportDim(String dataSchemeKey, String dimKey) {
        List<DataDimension> dimensions = this.getReportDimension(dataSchemeKey);
        DataDimension dimension = dimensions.stream().filter(x -> dimKey.equals(x.getDimKey())).findFirst().orElse(null);
        return dimension == null ? null : dimension.getDimAttribute();
    }

    public List<DataDimension> getReportDimension(String dataSchemeKey) {
        Assert.notNull((Object)dataSchemeKey, "dataSchemeKey must not be null.");
        List<DataDimDTO> dimensions = this.dataSchemeService.getDataSchemeDimension(dataSchemeKey);
        if (Objects.isNull(dimensions)) {
            return Collections.emptyList();
        }
        return dimensions.stream().filter(DataDimension::getReportDim).collect(Collectors.toList());
    }

    private <T> List<T> build(List<? extends T> list) {
        if (list == null) {
            return Collections.emptyList();
        }
        return new ArrayList<T>(list);
    }

    public List<DataFieldDeployInfo> getDeployInfoBySchemeKey(String dataSchemeKey) {
        return this.build(this.deployInfoService.getByDataSchemeKey(dataSchemeKey));
    }

    public List<DataFieldDeployInfo> getDeployInfoByDataTableKey(String dataTableKey) {
        return this.build(this.deployInfoService.getByDataTableKey(dataTableKey));
    }

    public List<DataFieldDeployInfo> getDeployInfoByTableName(String tableName) {
        return this.build(this.deployInfoService.getByDataTableName(tableName));
    }

    public List<DataFieldDeployInfo> getDeployInfoByTableModelKey(String tableModelKey) {
        return this.build(this.deployInfoService.getByTableModelKey(tableModelKey));
    }

    public DataFieldDeployInfo getDeployInfoByColumnKey(String columnKey) {
        return this.deployInfoService.getByColumnModelKey(columnKey);
    }

    public List<DataFieldDeployInfo> getDeployInfoByColumnKeys(List<String> columnKeys) {
        return this.build(this.deployInfoService.getByColumnModelKeys(columnKeys.toArray(new String[0])));
    }

    public List<DataFieldDeployInfo> getDeployInfoByDataFieldKeys(String ... dataFieldKeys) {
        if (dataFieldKeys == null || dataFieldKeys.length == 0) {
            return Collections.emptyList();
        }
        return this.build(this.deployInfoService.getByDataFieldKeys(dataFieldKeys));
    }

    public DeployStatusEnum getDataSchemeDeployStatus(String dataSchemeKey) {
        DataSchemeDeployStatusDO byDataSchemeKey = this.deployStatusDao.getByDataSchemeKey(dataSchemeKey);
        if (null == byDataSchemeKey) {
            return DeployStatusEnum.NEVER_DEPLOY;
        }
        return byDataSchemeKey.getDeployStatus();
    }

    public DataSchemeDeployStatus getDeployStatus(String dataSchemeKey) {
        return this.deployStatusDao.getByDataSchemeKey(dataSchemeKey);
    }

    public Map<String, DataSchemeDeployStatus> getDeployStatus(List<String> dataSchemeKeys) {
        if (CollectionUtils.isEmpty(dataSchemeKeys)) {
            return Collections.emptyMap();
        }
        List<DataSchemeDeployStatusDO> status = this.deployStatusDao.getByDataSchemeKeys(dataSchemeKeys.toArray(new String[0]));
        HashMap<String, DataSchemeDeployStatus> map = new HashMap<String, DataSchemeDeployStatus>();
        for (DataSchemeDeployStatusDO item : status) {
            map.put(item.getDataSchemeKey(), item);
        }
        return map;
    }

    public Map<String, DeployStatusEnum> getDataSchemeDeployStatus(String[] dataSchemeKeys) {
        HashMap<String, DeployStatusEnum> status = new HashMap<String, DeployStatusEnum>();
        List<DataSchemeDeployStatusDO> byDataSchemeKeys = this.deployStatusDao.getByDataSchemeKeys(dataSchemeKeys);
        for (DataSchemeDeployStatusDO s : byDataSchemeKeys) {
            status.put(s.getDataSchemeKey(), s.getDeployStatus());
        }
        for (String key : dataSchemeKeys) {
            if (status.containsKey(key)) continue;
            status.put(key, DeployStatusEnum.NEVER_DEPLOY);
        }
        return status;
    }

    @Transactional(readOnly=true, propagation=Propagation.REQUIRES_NEW)
    public boolean dataSchemeCheckData(String dataSchemeKey) {
        List<DataFieldDeployInfo> deployInfos = this.getDeployInfoBySchemeKey(dataSchemeKey);
        if (null == deployInfos || deployInfos.isEmpty()) {
            return false;
        }
        HashSet<String> tableNameSet = new HashSet<String>();
        for (DataFieldDeployInfo deployInfo : deployInfos) {
            tableNameSet.add(deployInfo.getTableName());
        }
        for (String tableName : tableNameSet) {
            boolean tableExistData;
            try {
                tableExistData = this.tableCheckDao.checkTableExistData(tableName);
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (!tableExistData) continue;
            return true;
        }
        return false;
    }

    @Transactional(readOnly=true, propagation=Propagation.REQUIRES_NEW)
    public boolean dataTableGroupCheckData(String dataTableGroupKey) {
        List<String> dataTableKeys = this.getDataTableKeysByGroup(dataTableGroupKey);
        return this.dataTableCheckData(dataTableKeys.toArray(new String[0]));
    }

    private List<String> getDataTableKeysByGroup(String groupKey) {
        List<DataGroup> dataGroupByParent;
        ArrayList<String> dataTableKeys = new ArrayList<String>();
        List<DataTable> dataTableByGroup = this.getDataTableByGroup(groupKey);
        if (!dataTableByGroup.isEmpty()) {
            for (DataTable dataTable : dataTableByGroup) {
                dataTableKeys.add(dataTable.getKey());
            }
        }
        if (!(dataGroupByParent = this.getDataGroupByParent(groupKey)).isEmpty()) {
            for (DataGroup dataGroup : dataGroupByParent) {
                List<String> dataTableKeysByGroup = this.getDataTableKeysByGroup(dataGroup.getKey());
                if (dataTableKeysByGroup.isEmpty()) continue;
                dataTableKeys.addAll(dataTableKeysByGroup);
            }
        }
        return dataTableKeys;
    }

    @Transactional(readOnly=true, propagation=Propagation.REQUIRES_NEW)
    public boolean dataTableCheckData(String ... dataTableKeys) {
        List<String> tableNames = this.deployInfoService.getTableNames(dataTableKeys);
        if (null == tableNames || tableNames.isEmpty()) {
            return false;
        }
        for (String tableName : tableNames) {
            boolean tableExistData;
            try {
                tableExistData = this.tableCheckDao.checkTableExistData(tableName);
            }
            catch (Exception e) {
                logger.error(DataSchemeEnum.DATA_SCHEME_CHECKDATA_1.getMessage(), e);
                throw new SchemeDataException((Throwable)e);
            }
            if (!tableExistData) continue;
            return true;
        }
        return false;
    }

    @Transactional(readOnly=true, propagation=Propagation.REQUIRES_NEW)
    public boolean dataFieldCheckData(String ... dataFieldKeys) {
        List<DataFieldDeployInfo> dataFieldDeployInfo = this.getDeployInfoByDataFieldKeys(dataFieldKeys);
        if (null == dataFieldDeployInfo || dataFieldDeployInfo.isEmpty()) {
            return false;
        }
        Set collect = dataFieldDeployInfo.stream().map(DataFieldDeployInfo::getTableName).collect(Collectors.toSet());
        for (String tableName : collect) {
            boolean tableExistData;
            try {
                tableExistData = this.tableCheckDao.checkTableExistData(tableName);
            }
            catch (Exception e) {
                logger.error(DataSchemeEnum.DATA_SCHEME_CHECKDATA_1.getMessage(), e);
                throw new SchemeDataException((Throwable)e);
            }
            if (!tableExistData) continue;
            return true;
        }
        return false;
    }

    public String getDataTableByTableModel(String tableModelKey) {
        List<DataFieldDeployInfoDO> infos = this.deployInfoService.getByTableModelKey(tableModelKey);
        if (null == infos || infos.isEmpty()) {
            return null;
        }
        return infos.get(0).getDataTableKey();
    }

    public boolean isDataTable(String tableKey) {
        if (null != this.dataTableService.getDataTable(tableKey)) {
            return true;
        }
        return StringUtils.hasLength(this.getDataTableByTableModel(tableKey));
    }

    public DataTableRel getDataTableRelBySrcTable(String srcTableKey) {
        return this.dataTableRelService.getBySrcTable(srcTableKey);
    }

    public List<DataTableRel> getDataTableRelByDesTable(String desTableKey) {
        return this.dataTableRelService.getByDesTable(desTableKey);
    }

    public List<DataField> searchField(FieldSearchQuery fieldSearchQuery) {
        return new ArrayList<DataField>(this.dataFieldService.searchField(fieldSearchQuery));
    }

    public List<DataTable> searchBy(String scheme, String keyword, int type) {
        return new ArrayList<DataTable>(this.dataTableService.searchBy(scheme, keyword, type));
    }

    public List<DataTable> searchBy(List<String> schemes, String keyword, int type) {
        return new ArrayList<DataTable>(this.dataTableService.searchBy(schemes, keyword, type));
    }
}

