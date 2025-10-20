/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.sql.command.SQLCommandType
 *  com.jiuqi.bi.database.sql.command.StatementCommand
 *  com.jiuqi.bi.database.sql.parser.SQLCommandParser
 *  com.jiuqi.bi.database.statement.AlterColumnStatement
 *  com.jiuqi.bi.database.statement.CreateTableStatement
 *  com.jiuqi.bi.database.statement.interpret.ISQLInterpretor
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.datasource.GcBizJdbcTemplate
 *  com.jiuqi.common.base.datasource.OuterTransaction
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.OuterDataSourceUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper
 *  com.jiuqi.gcreport.dimension.basedatasync.dao.BaseDataChangeInfoDao
 *  com.jiuqi.gcreport.dimension.basedatasync.entity.BaseDataChangeInfoEO
 *  com.jiuqi.gcreport.dimension.basedatasync.enums.BaseDataSyncHandleStateEnum
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$EventType
 *  com.jiuqi.va.domain.basedata.BaseDataStorageUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgVersionDO
 *  com.jiuqi.va.domain.org.OrgVersionDTO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.feign.client.OrgVersionClient
 *  org.jetbrains.annotations.Nullable
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.dc.integration.execute.impl.basedatasync.service.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.sql.command.SQLCommandType;
import com.jiuqi.bi.database.sql.command.StatementCommand;
import com.jiuqi.bi.database.sql.parser.SQLCommandParser;
import com.jiuqi.bi.database.statement.AlterColumnStatement;
import com.jiuqi.bi.database.statement.CreateTableStatement;
import com.jiuqi.bi.database.statement.interpret.ISQLInterpretor;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.datasource.GcBizJdbcTemplate;
import com.jiuqi.common.base.datasource.OuterTransaction;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.OuterDataSourceUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.jdbc.extractor.StringRowMapper;
import com.jiuqi.dc.integration.execute.impl.basedatasync.dao.BaseDataSyncDao;
import com.jiuqi.dc.integration.execute.impl.basedatasync.enums.BaseDataSyncTypeEnum;
import com.jiuqi.dc.integration.execute.impl.basedatasync.enums.BaseDataTypeEnum;
import com.jiuqi.dc.integration.execute.impl.basedatasync.mq.BaseDataSyncParam;
import com.jiuqi.dc.integration.execute.impl.basedatasync.service.DcBaseDataSyncService;
import com.jiuqi.gcreport.dimension.basedatasync.dao.BaseDataChangeInfoDao;
import com.jiuqi.gcreport.dimension.basedatasync.entity.BaseDataChangeInfoEO;
import com.jiuqi.gcreport.dimension.basedatasync.enums.BaseDataSyncHandleStateEnum;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.BaseDataStorageUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.org.OrgVersionDO;
import com.jiuqi.va.domain.org.OrgVersionDTO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.feign.client.OrgVersionClient;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BaseDataSyncServiceImpl
implements DcBaseDataSyncService {
    public static final String TYPE_NEW = "NEW";
    public static final String TYPE_UPDATE = "UPDATE";
    private final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private OrgVersionClient orgVersionClient;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private BaseDataSyncDao baseDataSyncDao;
    @Autowired
    private BaseDataChangeInfoDao baseDataChangeInfoDao;
    @Autowired
    private Environment environment;

    @Override
    public String doSync(BaseDataSyncParam baseDataSyncParam) {
        String baseDataCode = baseDataSyncParam.getBaseDataCode();
        DcBaseDataSyncService dcBaseDataSyncService = (DcBaseDataSyncService)SpringContextUtils.getBean(DcBaseDataSyncService.class);
        dcBaseDataSyncService.updateSyncState(baseDataCode, BaseDataSyncHandleStateEnum.UNHANDLED.getCode(), BaseDataSyncHandleStateEnum.MIDDLE.getCode());
        String result = null;
        try {
            result = dcBaseDataSyncService.doSyncByCode(baseDataSyncParam);
            dcBaseDataSyncService.deleteSyncInfo(baseDataCode, BaseDataSyncHandleStateEnum.MIDDLE.getCode());
        }
        catch (Exception e) {
            this.logger.error("\u57fa\u7840\u6570\u636e\u540c\u6b65\u5931\u8d25", e);
            throw e;
        }
        return result;
    }

    @Override
    @OuterTransaction(propagation=Propagation.REQUIRES_NEW)
    @Nullable
    public String doSyncByCode(BaseDataSyncParam baseDataSyncParam) {
        StringBuilder result = new StringBuilder("\n");
        if (BaseDataTypeEnum.ORG.getCode().equals(baseDataSyncParam.getBaseDataType())) {
            result.append(this.syncTableStructure(baseDataSyncParam.getBaseDataCode()));
            result.append(this.syncOrgData(baseDataSyncParam));
            return result.toString();
        }
        result.append(this.syncTableStructure(baseDataSyncParam.getBaseDataCode()));
        result.append(this.syncBaseData(baseDataSyncParam));
        return result.toString();
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void updateSyncState(String baseDataCode, Integer sourceState, Integer targetState) {
        this.baseDataChangeInfoDao.updateSyncState(baseDataCode, sourceState.intValue(), targetState.intValue());
    }

    @Override
    @Transactional(rollbackFor={Exception.class}, propagation=Propagation.REQUIRES_NEW)
    public void deleteSyncInfo(String baseDataCode, Integer state) {
        this.baseDataChangeInfoDao.deleteSyncInfo(baseDataCode, state);
    }

    private String syncOrgData(BaseDataSyncParam baseDataSyncParam) {
        return this.syncAllOrgData(baseDataSyncParam.getBaseDataCode());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String syncBaseData(String baseCodeName) {
        StringJoiner logJoiner = new StringJoiner("\n");
        List unSyncInfoList = this.baseDataChangeInfoDao.listUnSyncInfoByBaseCode(baseCodeName, BaseDataSyncHandleStateEnum.MIDDLE.getCode().intValue());
        if (CollectionUtils.isEmpty((Collection)unSyncInfoList)) {
            return "\u6ca1\u6709\u8981\u540c\u6b65\u7684\u6570\u636e";
        }
        HashSet codeSet = CollectionUtils.newHashSet();
        HashMap lastestInfoMap = CollectionUtils.newHashMap();
        for (BaseDataChangeInfoEO baseDataChangeInfo : unSyncInfoList) {
            codeSet.add(baseDataChangeInfo.getCode());
            if (!lastestInfoMap.containsKey(baseDataChangeInfo.getCode())) {
                lastestInfoMap.put(baseDataChangeInfo.getCode(), baseDataChangeInfo);
                continue;
            }
            BaseDataChangeInfoEO baseDataChangeInfoEO = (BaseDataChangeInfoEO)lastestInfoMap.get(baseDataChangeInfo.getCode());
            if (DateUtils.compare((Date)baseDataChangeInfoEO.getOperatingTime(), (Date)baseDataChangeInfo.getOperatingTime(), (boolean)false) >= 0) continue;
            lastestInfoMap.put(baseDataChangeInfo.getCode(), baseDataChangeInfo);
        }
        List<String> deleteCodeList = lastestInfoMap.values().stream().filter(e -> e.getChangeType().equals(BaseDataOption.EventType.DELETE.name())).map(BaseDataChangeInfoEO::getCode).collect(Collectors.toList());
        int addSuccessCount = 0;
        int updateSuccessCount = 0;
        ArrayList codeList = new ArrayList(CollectionUtils.diff((Collection)codeSet, deleteCodeList));
        BaseDataDTO condi = new BaseDataDTO();
        condi.setTableName(baseCodeName);
        condi.setPagination(Boolean.valueOf(false));
        condi.setAuthType(BaseDataOption.AuthType.NONE);
        condi.setCodeScope(codeList);
        List baseDatas = this.baseDataClient.list(condi).getRows();
        if (!CollectionUtils.isEmpty((Collection)baseDatas)) {
            List<String> tableColumnList = this.getTableColumn(baseCodeName);
            try {
                Map<String, String> columnMap = this.baseDataSyncDao.prepareTempTableData(baseCodeName, baseDatas, tableColumnList);
                updateSuccessCount = this.baseDataSyncDao.updateTableData(baseCodeName, columnMap);
                addSuccessCount = this.baseDataSyncDao.insertTableData(baseCodeName, columnMap);
            }
            finally {
                this.baseDataSyncDao.clearTempData(baseCodeName);
            }
        }
        int deleteSuccessCount = this.baseDataSyncDao.deleteBaseData(baseCodeName, deleteCodeList);
        return String.format("%5$s \r\n\u57fa\u7840\u6570\u636e\u3010%1$s\u3011\u5176\u4e2d\u65b0\u589e\u6210\u529f\u3010%2$d\u3011\u6761\uff0c\u4fee\u6539\u6210\u529f\u3010%3$d\u3011\u6761\uff0c\u5220\u9664\u6210\u529f\u3010%4$d\u3011\u6761", baseCodeName, addSuccessCount, updateSuccessCount, deleteSuccessCount, logJoiner);
    }

    private String syncAllOrgData(String orgType) {
        this.baseDataSyncDao.truncateTable(orgType);
        OrgVersionDTO orgVersionParam = new OrgVersionDTO();
        orgVersionParam.setPagination(false);
        orgVersionParam.setCategoryname(orgType);
        List rows = this.orgVersionClient.list(orgVersionParam).getRows();
        HashMap orgMap = CollectionUtils.newHashMap();
        for (OrgVersionDO version : rows) {
            OrgDTO condi = new OrgDTO();
            condi.setPagination(Boolean.valueOf(false));
            condi.setCategoryname(orgType);
            condi.setAuthType(OrgDataOption.AuthType.NONE);
            condi.setVersionDate(version.getValidtime());
            List addOrgList = this.orgDataClient.list(condi).getRows();
            if (CollectionUtils.isEmpty((Collection)addOrgList)) continue;
            addOrgList.forEach(e -> orgMap.put(e.getCode() + DateUtils.format((Date)e.getValidtime(), (String)"yyyy-MM-dd HH:mm:ss.SSS"), e));
        }
        if (CollectionUtils.isEmpty(orgMap.entrySet())) {
            return "\u6ca1\u6709\u8981\u540c\u6b65\u7684\u6570\u636e";
        }
        int addSuccessCount = this.baseDataSyncDao.insertOrgData(orgType, new ArrayList<OrgDO>(orgMap.values()), this.getTableColumn(orgType));
        return String.format("\u57fa\u7840\u6570\u636e\u3010%1$s\u3011\u5176\u4e2d\u65b0\u589e\u6210\u529f\u3010%2$d\u3011\u6761", orgType, addSuccessCount);
    }

    private String syncAllBaseData(String baseDataCode) {
        this.baseDataSyncDao.truncateTable(baseDataCode);
        BaseDataDTO condi = new BaseDataDTO();
        condi.setTableName(baseDataCode);
        condi.setPagination(Boolean.valueOf(false));
        condi.setAuthType(BaseDataOption.AuthType.NONE);
        List baseDatas = this.baseDataClient.list(condi).getRows();
        int addSuccessCount = this.baseDataSyncDao.insertBaseData(baseDataCode, baseDatas, this.getTableColumn(baseDataCode));
        return String.format("\u57fa\u7840\u6570\u636e\u3010%1$s\u3011\u5176\u4e2d\u65b0\u589e\u6210\u529f\u3010%2$d\u3011\u6761", baseDataCode, addSuccessCount);
    }

    private String syncBaseData(BaseDataSyncParam baseDataSyncParam) {
        if (Objects.equals(baseDataSyncParam.getSyncType(), BaseDataSyncTypeEnum.ALL.getCode())) {
            return this.syncAllBaseData(baseDataSyncParam.getBaseDataCode());
        }
        return this.syncBaseData(baseDataSyncParam.getBaseDataCode());
    }

    private String syncTableStructure(String tableCode) {
        String exculeFieldStr = this.environment.getProperty("jiuqi.gcreport.basedatasync.exculeFields");
        ArrayList exculeFieldList = StringUtils.isEmpty((String)exculeFieldStr) ? CollectionUtils.newArrayList((Object[])new String[]{"VER", "ORGCODE", "OBJECTCODE", "SHORTNAME", "UNITCODE", "VALIDTIME", "INVALIDTIME", "STOPFLAG", "RECOVERYFLAG", "ORDINAL", "CREATEUSER"}) : Stream.of(exculeFieldStr.split(",")).collect(Collectors.toList());
        GcBizJdbcTemplate bizJdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
        String tableColumnSql = bizJdbcTemplate.getIDbSqlHandler().getTableColumnSql(tableCode);
        List bizColumnList = bizJdbcTemplate.query(tableColumnSql, (RowMapper)new StringRowMapper());
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByCode(tableCode);
        List columnModelDefinesList = this.dataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
        List<ColumnModelDefine> addColumnList = columnModelDefinesList.stream().filter(e -> !exculeFieldList.contains(e.getName().toUpperCase()) && !bizColumnList.contains(e.getName().toUpperCase())).collect(Collectors.toList());
        ArrayList excuteSql = CollectionUtils.newArrayList();
        if (CollectionUtils.isEmpty((Collection)bizColumnList) && !CollectionUtils.isEmpty(addColumnList)) {
            String createTableSql = this.createTableSql(tableCode, addColumnList);
            excuteSql.addAll(this.translateTableSql(createTableSql, SQLCommandType.CREATE_TABLE));
            if (!CollectionUtils.isEmpty((Collection)excuteSql)) {
                excuteSql.forEach(arg_0 -> ((GcBizJdbcTemplate)bizJdbcTemplate).update(arg_0));
            }
            return String.format("\u65b0\u5efa\u8868\u7ed3\u6784:%1$s", tableCode);
        }
        if (!CollectionUtils.isEmpty(addColumnList)) {
            StringJoiner joiner = new StringJoiner(";");
            for (ColumnModelDefine field : addColumnList) {
                joiner.add(String.format("ALTER TABLE %1$s ADD %2$s %3$s %4$s", tableCode, field.getCode(), this.getDbTypeSql(field), this.getDbConstraintSql(field, TYPE_UPDATE)));
            }
            excuteSql.addAll(this.translateTableSql(joiner.toString(), SQLCommandType.ALTER_TABLE));
            if (!CollectionUtils.isEmpty((Collection)excuteSql)) {
                excuteSql.forEach(arg_0 -> ((GcBizJdbcTemplate)bizJdbcTemplate).update(arg_0));
            }
            return String.format("\u66f4\u65b0\u8868\u7ed3\u6784:%1$s,\u65b0\u589e\u5b57\u6bb5:[%2$s]", tableCode, addColumnList.stream().map(IModelDefineItem::getCode).collect(Collectors.joining(",")));
        }
        return String.format("%1$s\u8868\u7ed3\u6784\u65e0\u53d8\u5316", tableCode);
    }

    private List<String> getTableColumn(String tableCode) {
        GcBizJdbcTemplate bizJdbcTemplate = OuterDataSourceUtils.getJdbcTemplate();
        String tableColumnSql = bizJdbcTemplate.getIDbSqlHandler().getTableColumnSql(tableCode);
        return bizJdbcTemplate.query(tableColumnSql, (RowMapper)new StringRowMapper());
    }

    private String createTableSql(String tableCode, List<ColumnModelDefine> columnList) {
        StringJoiner joiner = new StringJoiner("\n\t");
        joiner.add("CREATE TABLE " + tableCode + "(");
        if (!CollectionUtils.isEmpty(columnList)) {
            List templateFields = BaseDataStorageUtil.getTemplateFields();
            HashMap orderMap = CollectionUtils.newHashMap();
            for (int i = 0; i < templateFields.size(); ++i) {
                orderMap.put(((DataModelColumn)templateFields.get(i)).getColumnName(), i);
            }
            columnList.sort((o1, o2) -> {
                Integer p1Order = orderMap.getOrDefault(o1.getName(), (int)((double)templateFields.size() + (Objects.nonNull(o1.getOrder()) ? o1.getOrder() : 99.0)));
                Integer p2Order = orderMap.getOrDefault(o2.getName(), (int)((double)templateFields.size() + (Objects.nonNull(o2.getOrder()) ? o2.getOrder() : 99.0)));
                return p1Order.compareTo(p2Order);
            });
        }
        for (int i = 0; i < columnList.size(); ++i) {
            ColumnModelDefine field = columnList.get(i);
            if (i == columnList.size() - 1) {
                joiner.add(String.format("%1$s %2$s %3$s", field.getCode(), this.getDbTypeSql(field), this.getDbConstraintSql(field, TYPE_NEW)));
                continue;
            }
            joiner.add(String.format("%1$s %2$s %3$s,", field.getCode(), this.getDbTypeSql(field), this.getDbConstraintSql(field, TYPE_NEW)));
        }
        joiner.add(");");
        return joiner.toString();
    }

    private String getDbTypeSql(ColumnModelDefine field) {
        StringBuilder builder = new StringBuilder("");
        switch (field.getColumnType()) {
            case STRING: {
                builder.append("NVARCHAR(").append(field.getPrecision()).append(")");
                break;
            }
            case DATETIME: {
                builder.append("TIMESTAMP");
                break;
            }
            case CLOB: {
                builder.append("CLOB");
                break;
            }
            case BLOB: {
                builder.append("BLOB");
                break;
            }
            case BOOLEAN: 
            case INTEGER: {
                builder.append("NUMBER(").append(field.getPrecision()).append(",0)");
                break;
            }
            case DOUBLE: 
            case BIGDECIMAL: {
                builder.append("NUMBER(").append(field.getPrecision()).append(",").append(field.getDecimal()).append(")");
                break;
            }
            default: {
                builder.append("NVARCHAR(").append(field.getPrecision()).append(")");
            }
        }
        return builder.toString();
    }

    private String getDbConstraintSql(ColumnModelDefine field, String type) {
        StringBuilder builder = new StringBuilder("");
        if (!StringUtils.isEmpty((String)field.getDefaultValue())) {
            builder.append("DEFAULT ").append(field.getDefaultValue());
        }
        if (!field.isNullAble() && TYPE_NEW.equals(type)) {
            builder.append(" NOT");
        }
        builder.append(" NULL");
        return builder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private List<String> translateTableSql(String baseSql, SQLCommandType type) {
        try (Connection connection = OuterDataSourceUtils.getDataSource().getConnection();){
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            if (database == null) {
                throw new BusinessRuntimeException("\u83b7\u53d6IDatabase\u5931\u8d25");
            }
            List sqlCommands = new SQLCommandParser().parse(database, baseSql);
            StatementCommand statementCommand = (StatementCommand)sqlCommands.get(0);
            ISQLInterpretor sqlInterpretor = database.createSQLInterpretor(connection);
            if (Objects.equals(type, SQLCommandType.CREATE_TABLE)) {
                List list = sqlInterpretor.createTable((CreateTableStatement)statementCommand.getStatement());
                return list;
            }
            if (Objects.equals(type, SQLCommandType.ALTER_TABLE)) {
                List list = sqlInterpretor.alterColumn((AlterColumnStatement)statementCommand.getStatement());
                return list;
            }
            ArrayList arrayList = CollectionUtils.newArrayList();
            return arrayList;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u57fa\u7840\u6570\u636e\u8868\u521b\u5efa\u8bed\u53e5\u7ffb\u8bd1\u5931\u8d25\uff0c\u8bf7\u5c1d\u8bd5\u624b\u52a8\u521b\u5efa\u57fa\u7840\u6570\u636e\u8868\uff1a\n" + baseSql, (Throwable)e);
        }
    }
}

