/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.impl.DataRowImpl
 *  com.jiuqi.np.dataengine.intf.impl.DataTableImpl
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.temptable.BaseTempTableDefine
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.param.IBatchZBAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.common.DataSchemeUtils
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fielddatacrud.impl.DataModelLinkFinder
 *  com.jiuqi.nr.io.common.DataIOTempTableDefine
 *  com.jiuqi.nr.io.tz.TzParams
 *  com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable
 *  com.jiuqi.nr.io.tz.bean.JioTempTableInfo
 *  com.jiuqi.nr.io.tz.bean.TzDataImportRes
 *  com.jiuqi.nr.io.tz.service.BatchImportMonitor
 *  com.jiuqi.nr.io.tz.service.BatchImportService
 *  com.jiuqi.nr.io.tz.service.TzBatchImportService
 *  com.jiuqi.nr.io.tz.service.impl.JioTempTableDao
 *  com.jiuqi.nr.io.util.DateUtil
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nr.period.service.PeriodAdapterService
 *  com.jiuqi.nvwa.core.automation.annotation.AutomationType
 *  com.jiuqi.nvwa.core.automation.annotation.MetaOperation
 *  com.jiuqi.nvwa.core.automation.annotation.QueryOperation
 *  com.jiuqi.nvwa.core.automation.annotation.WriteOperation
 *  com.jiuqi.nvwa.framework.automation.api.AutomationFieldInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationInstance
 *  com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo
 *  com.jiuqi.nvwa.framework.automation.api.AutomationParameter
 *  com.jiuqi.nvwa.framework.automation.api.AutomationParameterOption
 *  com.jiuqi.nvwa.framework.automation.bean.ExecuteContext
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum
 *  com.jiuqi.nvwa.framework.automation.enums.AutomationParameterValueModeEnum
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationException
 *  com.jiuqi.nvwa.framework.automation.exception.AutomationExecuteException
 *  com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker
 *  com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker
 *  com.jiuqi.nvwa.framework.automation.result.JsonResult
 *  com.jiuqi.nvwa.framework.automation.result.StreamResult
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.etl.automation;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.DataTableImpl;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.common.temptable.BaseTempTableDefine;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.param.IBatchZBAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.common.DataSchemeUtils;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.etl.automation.DataTableAutomationQueryContext;
import com.jiuqi.nr.etl.automation.DataTableAutomationWriteContext;
import com.jiuqi.nr.fielddatacrud.impl.DataModelLinkFinder;
import com.jiuqi.nr.io.common.DataIOTempTableDefine;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.bean.JioTempTableInfo;
import com.jiuqi.nr.io.tz.bean.TzDataImportRes;
import com.jiuqi.nr.io.tz.service.BatchImportMonitor;
import com.jiuqi.nr.io.tz.service.BatchImportService;
import com.jiuqi.nr.io.tz.service.TzBatchImportService;
import com.jiuqi.nr.io.tz.service.impl.JioTempTableDao;
import com.jiuqi.nr.io.util.DateUtil;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.period.service.PeriodAdapterService;
import com.jiuqi.nvwa.core.automation.annotation.AutomationType;
import com.jiuqi.nvwa.core.automation.annotation.MetaOperation;
import com.jiuqi.nvwa.core.automation.annotation.QueryOperation;
import com.jiuqi.nvwa.core.automation.annotation.WriteOperation;
import com.jiuqi.nvwa.framework.automation.api.AutomationFieldInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationInstance;
import com.jiuqi.nvwa.framework.automation.api.AutomationMetaInfo;
import com.jiuqi.nvwa.framework.automation.api.AutomationParameter;
import com.jiuqi.nvwa.framework.automation.api.AutomationParameterOption;
import com.jiuqi.nvwa.framework.automation.bean.ExecuteContext;
import com.jiuqi.nvwa.framework.automation.enums.AutomationParameterDataTypeEnum;
import com.jiuqi.nvwa.framework.automation.enums.AutomationParameterValueModeEnum;
import com.jiuqi.nvwa.framework.automation.exception.AutomationException;
import com.jiuqi.nvwa.framework.automation.exception.AutomationExecuteException;
import com.jiuqi.nvwa.framework.automation.invoker.IMetaInvoker;
import com.jiuqi.nvwa.framework.automation.invoker.IOperationInvoker;
import com.jiuqi.nvwa.framework.automation.result.JsonResult;
import com.jiuqi.nvwa.framework.automation.result.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;

@AutomationType(category="nr-datatable", id="nr-datatable-type", title="\u6570\u636e\u8868\u81ea\u52a8\u5316\u5bf9\u8c61", icon="icon16_SHU_A_NW_S2")
public class DataTableAutomationType {
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private IDataAccessProvider iDataAccessProvider;
    @Autowired
    private IRunTimeViewController runTimeController;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IEntityDataService iEntityDataService;
    @Autowired
    private PeriodAdapterService periodAdapterService;
    @Autowired
    private IEntityAuthorityService iEntityAuthorityService;
    @Autowired
    private UserService<SystemUser> userService;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private JioTempTableDao jioTempTableDao;
    @Autowired
    private BatchImportService batchImportService;
    @Autowired
    private ITempTableManager iTempTableManager;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TzBatchImportService tzBatchImportService;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    private static final Logger logger = LoggerFactory.getLogger(DataTableAutomationType.class);
    private final SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final String UNMAPPED_FIELD_DEFAULT_DATA = "\"NULL\"";

    private Integer nrDataTypeTobi(DataFieldType nrDataType) {
        switch (nrDataType) {
            case STRING: {
                return 6;
            }
            case INTEGER: {
                return 5;
            }
            case BOOLEAN: {
                return 1;
            }
            case DATE: {
                return 2;
            }
            case DATE_TIME: {
                return 2;
            }
            case UUID: {
                return 33;
            }
            case BIGDECIMAL: {
                return 10;
            }
            case CLOB: {
                return 12;
            }
            case PICTURE: {
                return 9;
            }
            case FILE: {
                return 9;
            }
        }
        return 0;
    }

    private List<String> filterUnitByFormula(String entityId, String formula, Date date) throws AutomationExecuteException {
        List<String> entityList;
        IEntityQuery iEntityQuery = this.iEntityDataService.newEntityQuery();
        ExecutorContext context = new ExecutorContext(this.iDataDefinitionRuntimeController);
        ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(this.runTimeController, this.iDataDefinitionRuntimeController, this.iEntityViewRunTimeController);
        context.setEnv((IFmlExecEnvironment)env);
        iEntityQuery.setEntityView(this.iEntityViewRunTimeController.buildEntityView(entityId, formula));
        if (date != null) {
            iEntityQuery.setQueryVersionDate(date);
        }
        iEntityQuery.sorted(true);
        IEntityTable iEntityTable = null;
        try {
            iEntityTable = iEntityQuery.executeReader((IContext)context);
            entityList = iEntityTable.getAllRows().stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        }
        catch (Exception e) {
            throw new AutomationExecuteException("\u7ec4\u7ec7\u673a\u6784\u8fc7\u6ee4\u516c\u5f0f\u89e3\u6790\u5931\u8d25\uff1a" + formula, (Throwable)e);
        }
        return entityList;
    }

    private StringBuilder queryDataSingle(IDataQuery iDataQuery, DimensionValueSet dimensionValueSet, ExecutorContext executorContext) throws AutomationExecuteException {
        IDataTable iDataTable;
        StringBuilder csvResult = new StringBuilder();
        iDataQuery.setMasterKeys(dimensionValueSet);
        IDataAssist iDataAssist = this.dataAccessProvider.newDataAssist(executorContext);
        try {
            iDataTable = iDataQuery.executeQuery(executorContext);
        }
        catch (Exception e) {
            throw new AutomationExecuteException("\u901a\u8fc7\u7ec4\u7ec7\u673a\u6784\u67e5\u8be2\u6570\u636e\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
        }
        int totalCount1 = iDataTable.getTotalCount();
        for (int i = 0; i < totalCount1; ++i) {
            IDataRow item = iDataTable.getItem(i);
            DimensionValueSet masterKeys = item.getRowKeys();
            if (masterKeys == null) continue;
            ArrayList rowDatas = ((DataRowImpl)item).getRowDatas();
            Object[] dataBuf = rowDatas.toArray();
            IFieldsInfo fieldsInfo = iDataTable.getFieldsInfo();
            for (int j = 0; j < dataBuf.length; ++j) {
                DataField dataField;
                Object o = dataBuf[j];
                FieldDefine fieldDefine = fieldsInfo.getFieldDefine(j);
                if (fieldDefine instanceof DataField && (dataField = (DataField)fieldDefine).getDataFieldKind().equals((Object)DataFieldKind.PUBLIC_FIELD_DIM)) {
                    String dimName = iDataAssist.getDimensionName(fieldsInfo.getFieldDefine(j));
                    csvResult.append(masterKeys.getValue(dimName)).append(",");
                    continue;
                }
                if (o == null) {
                    csvResult.append(",");
                    continue;
                }
                if (o instanceof GregorianCalendar) {
                    csvResult.append(this.formatDateTime.format(((GregorianCalendar)o).getTime())).append(",");
                    continue;
                }
                csvResult.append(o.toString()).append(",");
            }
            if (csvResult.length() <= 0) continue;
            csvResult.deleteCharAt(csvResult.length() - 1);
            csvResult.append("\n");
        }
        return csvResult;
    }

    private IDataTable queryIDataTable(IDataQuery iDataQuery, String dataSchemeKey) throws AutomationExecuteException {
        IDataTable iDataTable;
        iDataQuery.setMasterKeys(new DimensionValueSet());
        ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
        ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(this.runTimeController, this.iDataDefinitionRuntimeController, this.iEntityViewRunTimeController);
        env.setDataScehmeKey(dataSchemeKey);
        DataModelLinkFinder dataModelLinkFinder = new DataModelLinkFinder();
        dataModelLinkFinder.setRuntimeDataSchemeService(this.iRuntimeDataSchemeService);
        env.setDataModelLinkFinder((IDataModelLinkFinder)dataModelLinkFinder);
        executorContext.setEnv((IFmlExecEnvironment)env);
        try {
            iDataTable = iDataQuery.executeQuery(executorContext);
        }
        catch (Exception e) {
            throw new AutomationExecuteException("\u901a\u8fc7\u7ec4\u7ec7\u673a\u6784\u67e5\u8be2\u6570\u636e\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
        }
        return iDataTable;
    }

    private List dimValueSetToList(DimensionValueSet allDimensionValueSet, String dimType) {
        return (List)allDimensionValueSet.getValue(dimType);
    }

    private DataTableAutomationQueryContext handleSingleFiledDimension(String dataSchemeKey, DataTableAutomationQueryContext context) {
        List unitDimension = this.iRuntimeDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.UNIT);
        DataDimension unitDataDimension = this.getDataDimension(unitDimension);
        List customDimension = this.iRuntimeDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.DIMENSION);
        ArrayList<String> singleFields = new ArrayList<String>(customDimension.size());
        HashMap<String, String> filedDimensionMap = new HashMap<String, String>(customDimension.size());
        String unitEntityKey = null;
        if (unitDataDimension != null) {
            unitEntityKey = unitDataDimension.getDimKey();
        }
        IEntityModel entityModel = this.iEntityMetaService.getEntityModel(unitEntityKey);
        List entityRefer = this.iEntityMetaService.getEntityRefer(unitEntityKey);
        for (DataDimension dataDimension : customDimension) {
            String ownField;
            IEntityAttribute attribute;
            String dimKey = dataDimension.getDimKey();
            Optional<IEntityRefer> anyEntityRefer = entityRefer.stream().filter(entity -> entity.getReferEntityId().equals(dimKey)).findAny();
            if (!anyEntityRefer.isPresent() || (attribute = entityModel.getAttribute(ownField = anyEntityRefer.get().getOwnField())) == null || attribute.isMultival()) continue;
            singleFields.add(attribute.getCode());
            String dimensionName = this.iEntityMetaService.getDimensionName(dimKey);
            filedDimensionMap.put(attribute.getCode(), dimensionName);
        }
        context.setSingleFields(singleFields);
        context.setFiledDimensionMap(filedDimensionMap);
        return context;
    }

    private List<IEntityRow> handleAllIEntityRows(DataTableAutomationQueryContext context) throws AutomationExecuteException {
        DimensionValueSet allDimensionValueSet = context.getDimensionValueSet();
        DimensionValueSet singleQueryDimensionValueSet = new DimensionValueSet();
        singleQueryDimensionValueSet.combine(allDimensionValueSet);
        singleQueryDimensionValueSet.setValue("DATATIME", (Object)context.getMaxDate());
        IEntityQuery iEntityQuery = this.iEntityDataService.newEntityQuery();
        iEntityQuery.setEntityView(this.iEntityViewRunTimeController.buildEntityView(context.getUnitEntity().getEntityId()));
        iEntityQuery.setMasterKeys(singleQueryDimensionValueSet);
        IEntityTable iEntityTable = null;
        try {
            iEntityTable = iEntityQuery.executeFullBuild(null);
        }
        catch (Exception e) {
            throw new AutomationExecuteException("\u5b9e\u4f53\u7ed3\u679c\u8868\u67e5\u8be2\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
        }
        return iEntityTable.getAllRows();
    }

    private DimensionValueSet handleSingleDimValueSet(String unitCode, IEntityRow row, DataTableAutomationQueryContext context) {
        DimensionValueSet singledimValueSet = new DimensionValueSet();
        DimensionValueSet allDimensionValueSet = context.getDimensionValueSet();
        singledimValueSet.combine(allDimensionValueSet);
        singledimValueSet.setValue(context.getUnitEntity().getUnitDimensionName(), (Object)unitCode);
        for (String fieldName : context.getSingleFields()) {
            AbstractData dimensionValue = row.getValue(fieldName);
            String dimensionName = context.getFiledDimensionMap().get(fieldName);
            if (!dimensionValue.isNull) {
                singledimValueSet.setValue(dimensionName, (Object)dimensionValue.getAsString());
                continue;
            }
            singledimValueSet.setValue(dimensionName, null);
        }
        return singledimValueSet;
    }

    private DimensionValueSet handleCurrencyDimValueSet(List<String> currencyList, IEntityRow row, DimensionValueSet singledimValueSet) {
        AbstractData currencyValue = row.getValue("CURRENCYID");
        if (!currencyValue.isNull) {
            ArrayList<String> currencyQueryList = new ArrayList<String>();
            currencyQueryList.add(currencyValue.getAsString());
            if (currencyList != null && !currencyList.isEmpty()) {
                for (String o : currencyList) {
                    String currency = o.toString();
                    if ("CURRENCYID".equalsIgnoreCase(currency) || currency.equalsIgnoreCase(currencyValue.getAsString())) continue;
                    currencyQueryList.add(currency);
                }
            }
            singledimValueSet.setValue("MD_CURRENCY", currencyQueryList);
        } else {
            singledimValueSet.setValue("MD_CURRENCY", null);
        }
        return singledimValueSet;
    }

    private RowFilter getRowFilter(DataTableAutomationQueryContext context) throws AutomationExecuteException {
        DimensionValueSet queryMasterKeys = new DimensionValueSet();
        DimensionValueSet allDimensionValueSet = context.getDimensionValueSet();
        List unitList = this.dimValueSetToList(allDimensionValueSet, context.getUnitEntity().getUnitDimensionName());
        if (CollectionUtils.isEmpty(unitList) && !this.isAdmin().booleanValue()) {
            return null;
        }
        RowFilter rowFilter = new RowFilter();
        ArrayList<DimensionValueSet> rowFilterList = new ArrayList<DimensionValueSet>();
        context = this.handleSingleFiledDimension(context.getDataSchemeKey(), context);
        List currencyList = this.dimValueSetToList(allDimensionValueSet, "MD_CURRENCY");
        List datatimeList = this.dimValueSetToList(allDimensionValueSet, "DATATIME");
        List<IEntityRow> allRows = this.handleAllIEntityRows(context);
        ArrayList<String> dws = new ArrayList<String>();
        queryMasterKeys.setValue("DATATIME", (Object)datatimeList);
        for (IEntityRow row : allRows) {
            String unitCode = row.getEntityKeyData();
            if (!CollectionUtils.isEmpty(unitList) && !unitList.contains(unitCode)) continue;
            dws.add(unitCode);
            DimensionValueSet dimensionValueSet = this.handleSingleDimValueSet(unitCode, row, context);
            if (context.getCurrencyID().booleanValue()) {
                List<DimensionValueSet> dimensionValueSets = this.fillCurrencyDimValueSet(currencyList, row, dimensionValueSet);
                for (DimensionValueSet valueSet : dimensionValueSets) {
                    if (datatimeList != null && !datatimeList.isEmpty()) {
                        for (String datatime : datatimeList) {
                            valueSet.setValue("DATATIME", (Object)datatime);
                            rowFilterList.add(new DimensionValueSet(valueSet));
                        }
                        continue;
                    }
                    rowFilterList.add(valueSet);
                }
                continue;
            }
            queryMasterKeys.setValue("MD_CURRENCY", (Object)currencyList);
            if (datatimeList != null && !datatimeList.isEmpty()) {
                for (String datatime : datatimeList) {
                    dimensionValueSet.setValue("DATATIME", (Object)datatime);
                    rowFilterList.add(new DimensionValueSet(dimensionValueSet));
                }
                continue;
            }
            rowFilterList.add(dimensionValueSet);
        }
        rowFilter.queryMasterKeys = new ArrayList();
        if (dws.size() > 1000) {
            List<List<String>> lists = DataTableAutomationType.splitList(dws, 1000);
            for (List<String> list : lists) {
                queryMasterKeys.setValue(context.getUnitEntity().getUnitDimensionName(), list);
                rowFilter.queryMasterKeys.add(new DimensionValueSet(queryMasterKeys));
            }
        } else {
            queryMasterKeys.setValue(context.getUnitEntity().getUnitDimensionName(), dws);
            rowFilter.queryMasterKeys.add(queryMasterKeys);
        }
        HashSet filterSet = new HashSet();
        for (DimensionValueSet filter : rowFilterList) {
            List list = DimensionValueSetUtil.getDimensionSetList((DimensionValueSet)filter);
            filterSet.addAll(list);
        }
        rowFilter.filters = filterSet;
        return rowFilter;
    }

    public static List<List<String>> splitList(List<String> dws, int size) {
        ArrayList<List<String>> subLists = new ArrayList<List<String>>();
        int totalSize = dws.size();
        for (int i = 0; i < totalSize; i += size) {
            subLists.add(new ArrayList<String>(dws.subList(i, Math.min(totalSize, i + size))));
        }
        return subLists;
    }

    private List<DimensionValueSet> fillCurrencyDimValueSet(List<String> currencyList, IEntityRow row, DimensionValueSet singledimValueSet) {
        ArrayList<DimensionValueSet> currencyDimValueSetList = new ArrayList<DimensionValueSet>();
        AbstractData currencyValue = row.getValue("CURRENCYID");
        if (!currencyValue.isNull) {
            ArrayList<String> currencyQueryList = new ArrayList<String>();
            currencyQueryList.add(currencyValue.getAsString());
            if (currencyList != null && !currencyList.isEmpty()) {
                for (Object object : currencyList) {
                    String currency = object.toString();
                    if ("CURRENCYID".equalsIgnoreCase(currency) || currency.equalsIgnoreCase(currencyValue.getAsString())) continue;
                    currencyQueryList.add(currency);
                }
            }
            for (String string : currencyQueryList) {
                singledimValueSet.setValue("MD_CURRENCY", (Object)string);
                currencyDimValueSetList.add(new DimensionValueSet(singledimValueSet));
            }
        } else {
            singledimValueSet.setValue("MD_CURRENCY", null);
            currencyDimValueSetList.add(singledimValueSet);
        }
        return currencyDimValueSetList;
    }

    private StringBuilder queryDataByUnit(IDataQuery iDataQuery, DataTableAutomationQueryContext context) throws AutomationExecuteException {
        DimensionValueSet allDimensionValueSet = context.getDimensionValueSet();
        StringBuilder csvResult = new StringBuilder();
        List unitList = this.dimValueSetToList(allDimensionValueSet, context.getUnitEntity().getUnitDimensionName());
        if (CollectionUtils.isEmpty(unitList) && !this.isAdmin().booleanValue()) {
            return csvResult;
        }
        context = this.handleSingleFiledDimension(context.getDataSchemeKey(), context);
        List currencyList = this.dimValueSetToList(allDimensionValueSet, "MD_CURRENCY");
        List datatimeList = this.dimValueSetToList(allDimensionValueSet, "DATATIME");
        List<IEntityRow> allRows = this.handleAllIEntityRows(context);
        ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
        ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(this.runTimeController, this.iDataDefinitionRuntimeController, this.iEntityViewRunTimeController);
        env.setDataScehmeKey(context.getDataSchemeKey());
        DataModelLinkFinder dataModelLinkFinder = new DataModelLinkFinder();
        dataModelLinkFinder.setRuntimeDataSchemeService(this.iRuntimeDataSchemeService);
        env.setDataModelLinkFinder((IDataModelLinkFinder)dataModelLinkFinder);
        executorContext.setEnv((IFmlExecEnvironment)env);
        executorContext.setOrgEntityId(context.getUnitEntity().getEntityIdL());
        for (IEntityRow row : allRows) {
            String unitCode = row.getEntityKeyData();
            if (!CollectionUtils.isEmpty(unitList) && !unitList.contains(unitCode)) continue;
            DimensionValueSet dimensionValueSet = this.handleSingleDimValueSet(unitCode, row, context);
            if (context.getCurrencyID().booleanValue()) {
                dimensionValueSet = this.handleCurrencyDimValueSet(currencyList, row, dimensionValueSet);
            }
            if (datatimeList != null && !datatimeList.isEmpty()) {
                for (String datatime : datatimeList) {
                    dimensionValueSet.setValue("DATATIME", (Object)datatime);
                    csvResult.append((CharSequence)this.queryDataSingle(iDataQuery, dimensionValueSet, executorContext));
                }
                continue;
            }
            csvResult.append((CharSequence)this.queryDataSingle(iDataQuery, dimensionValueSet, executorContext));
        }
        return csvResult;
    }

    private StringBuilder queryDataByUnit2(IDataQuery iDataQuery, DataTableAutomationQueryContext context) throws AutomationExecuteException {
        RowFilter rowFilter = this.getRowFilter(context);
        StringBuilder csvResult = new StringBuilder();
        if (rowFilter == null) {
            return csvResult;
        }
        for (DimensionValueSet queryMasterKey : rowFilter.queryMasterKeys) {
            IDataTable iDataTable;
            iDataQuery.setMasterKeys(queryMasterKey);
            ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
            IDataAssist iDataAssist = this.dataAccessProvider.newDataAssist(executorContext);
            ReportFmlExecEnvironment fmlExecEnvironment = new ReportFmlExecEnvironment(this.runTimeController, this.iDataDefinitionRuntimeController, this.iEntityViewRunTimeController);
            fmlExecEnvironment.setDataScehmeKey(context.getDataSchemeKey());
            DataModelLinkFinder dataModelLinkFinder = new DataModelLinkFinder();
            dataModelLinkFinder.setRuntimeDataSchemeService(this.iRuntimeDataSchemeService);
            fmlExecEnvironment.setDataModelLinkFinder((IDataModelLinkFinder)dataModelLinkFinder);
            executorContext.setEnv((IFmlExecEnvironment)fmlExecEnvironment);
            executorContext.setOrgEntityId(context.getUnitEntity().getEntityIdL());
            try {
                iDataTable = iDataQuery.executeQuery(executorContext);
            }
            catch (Exception e) {
                throw new AutomationExecuteException("\u901a\u8fc7\u7ec4\u7ec7\u673a\u6784\u67e5\u8be2\u6570\u636e\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
            }
            int totalCount = iDataTable.getTotalCount();
            IFieldsInfo fieldsInfo = iDataTable.getFieldsInfo();
            for (int i = 0; i < totalCount; ++i) {
                IDataRow item = iDataTable.getItem(i);
                DimensionValueSet rowKeys = item.getRowKeys();
                if (rowKeys == null || !rowFilter.filters.contains(rowKeys)) continue;
                ArrayList rowDatas = ((DataRowImpl)item).getRowDatas();
                Object[] dataBuf = rowDatas.toArray();
                for (int j = 0; j < dataBuf.length; ++j) {
                    DataField dataField;
                    Object o = dataBuf[j];
                    FieldDefine fieldDefine = fieldsInfo.getFieldDefine(j);
                    if (fieldDefine instanceof DataField && (dataField = (DataField)fieldDefine).getDataFieldKind().equals((Object)DataFieldKind.PUBLIC_FIELD_DIM)) {
                        String dimName = iDataAssist.getDimensionName(fieldsInfo.getFieldDefine(j));
                        csvResult.append(rowKeys.getValue(dimName)).append(",");
                        continue;
                    }
                    if (o == null) {
                        csvResult.append(",");
                        continue;
                    }
                    if (o instanceof GregorianCalendar) {
                        csvResult.append(this.formatDateTime.format(((GregorianCalendar)o).getTime())).append(",");
                        continue;
                    }
                    csvResult.append(o).append(",");
                }
                if (csvResult.length() <= 0) continue;
                csvResult.deleteCharAt(csvResult.length() - 1);
                csvResult.append("\n");
            }
        }
        return csvResult;
    }

    private Boolean isAdmin() {
        NpContext context = NpContextHolder.getContext();
        ContextUser user = context.getUser();
        String userName = user.getName();
        return this.userService.existsIgnoreCase(userName);
    }

    private StringBuilder queryColumnsData(String dataTableKey) {
        StringBuilder csvResult = new StringBuilder();
        List dataFields = this.iRuntimeDataSchemeService.getDataFieldByTable(dataTableKey);
        for (DataField fieldDefine : dataFields) {
            csvResult.append(fieldDefine.getCode()).append(",");
        }
        if (csvResult.length() > 0) {
            csvResult.deleteCharAt(csvResult.length() - 1);
            csvResult.append("\n");
        }
        return csvResult;
    }

    private StringBuilder queryColumnsData(String dataTableKey, List<String> fieldNameList) {
        if (fieldNameList == null || fieldNameList.isEmpty()) {
            return this.queryColumnsData(dataTableKey);
        }
        ArrayList<String> fieldCodes = new ArrayList<String>(fieldNameList);
        ArrayList<String> columnList = new ArrayList<String>();
        StringBuilder csvResult = new StringBuilder();
        List dataFields = this.iRuntimeDataSchemeService.getDataFieldByTable(dataTableKey);
        for (DataField fieldDefine : dataFields) {
            if (fieldCodes.isEmpty()) break;
            if (!fieldCodes.contains(fieldDefine.getCode())) continue;
            columnList.add(fieldDefine.getCode());
            fieldCodes.remove(fieldDefine.getCode());
        }
        columnList.sort((o1, o2) -> {
            int index1 = fieldNameList.indexOf(o1);
            int index2 = fieldNameList.indexOf(o2);
            return Integer.compare(index1, index2);
        });
        columnList.forEach(e -> csvResult.append((String)e).append(","));
        if (csvResult.length() > 0) {
            csvResult.deleteCharAt(csvResult.length() - 1);
            csvResult.append("\n");
        }
        return csvResult;
    }

    private IDataQuery addColumnsData(String dataTableKey, IDataQuery iDataQuery) {
        List dataFields = this.iRuntimeDataSchemeService.getDataFieldByTable(dataTableKey);
        for (DataField fieldDefine : dataFields) {
            iDataQuery.addColumn((FieldDefine)fieldDefine);
        }
        return iDataQuery;
    }

    private IDataQuery addColumnsData(String dataTableKey, List<String> fieldNameList, IDataQuery iDataQuery) {
        if (fieldNameList == null || fieldNameList.isEmpty()) {
            return this.addColumnsData(dataTableKey, iDataQuery);
        }
        ArrayList<String> fieldCodes = new ArrayList<String>(fieldNameList);
        ArrayList<DataField> columnList = new ArrayList<DataField>();
        List dataFields = this.iRuntimeDataSchemeService.getDataFieldByTable(dataTableKey);
        for (DataField fieldDefine : dataFields) {
            if (fieldCodes.isEmpty()) break;
            if (!fieldCodes.contains(fieldDefine.getCode())) continue;
            columnList.add(fieldDefine);
            fieldCodes.remove(fieldDefine.getCode());
        }
        columnList.sort((o1, o2) -> {
            int index1 = fieldNameList.indexOf(o1.getCode());
            int index2 = fieldNameList.indexOf(o2.getCode());
            return Integer.compare(index1, index2);
        });
        columnList.forEach(e -> iDataQuery.addColumn((FieldDefine)e));
        return iDataQuery;
    }

    private IDataQuery addColumnsData(IDataQuery iDataQuery, List<DataField> dataFields) {
        for (DataField fieldDefine : dataFields) {
            iDataQuery.addColumn((FieldDefine)fieldDefine);
        }
        return iDataQuery;
    }

    private DataDimension getDataDimension(List<DataDimension> dataDimensions) {
        if (!CollectionUtils.isEmpty(dataDimensions)) {
            return dataDimensions.get(0);
        }
        return null;
    }

    private DataTableAutomationQueryContext.UnitEntity getUnitEntity(Map<String, Object> parameterMap, List<DataDimension> unitDataDimensions) throws AutomationExecuteException {
        Object orgCategory = parameterMap.get("P_ORGCATEGORY");
        String entityId = "";
        String entityIdL = "";
        String unitDimensionName = "";
        DataDimension unitDataDimension = this.getDataDimension(unitDataDimensions);
        if (unitDataDimension != null) {
            unitDimensionName = this.iEntityMetaService.getDimensionName(unitDataDimension.getDimKey());
        }
        entityId = orgCategory == null ? this.iEntityMetaService.getEntityCode(unitDimensionName) : orgCategory.toString();
        try {
            entityIdL = this.iEntityMetaService.queryEntity(entityId).getId();
            return new DataTableAutomationQueryContext.UnitEntity(entityId, entityIdL, unitDimensionName);
        }
        catch (Exception e) {
            throw new AutomationExecuteException("\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b\u4f20\u5165\u9519\u8bef\uff0c\u8bf7\u68c0\u67e5\uff1a" + entityId + e.getMessage(), (Throwable)e);
        }
    }

    private DataTableAutomationQueryContext initQueryContext(AutomationInstance automationElement, Map<String, Object> parameterMap) throws AutomationExecuteException {
        String dataTableKey = automationElement.getGuid();
        DataTable dataTable = this.iRuntimeDataSchemeService.getDataTable(dataTableKey);
        String dataSchemeKey = dataTable.getDataSchemeKey();
        List unitDimensions = this.iRuntimeDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.UNIT);
        List periodDimensions = this.iRuntimeDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.PERIOD);
        Boolean isSingleQuery = DataTableType.ACCOUNT == dataTable.getDataTableType();
        Boolean isCurrencyID = false;
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        String maxDate = "";
        Date startDate = null;
        Date endDate = null;
        PeriodType periodType = null;
        DataDimension periodDataDimension = this.getDataDimension(periodDimensions);
        if (periodDataDimension != null) {
            periodType = periodDataDimension.getPeriodType();
        }
        List iPeriodEntityList = this.periodAdapterService.getPeriodByType(periodType);
        IPeriodEntity iPeriodEntity = (IPeriodEntity)iPeriodEntityList.get(0);
        DataTableAutomationQueryContext.UnitEntity unitEntity = this.getUnitEntity(parameterMap, unitDimensions);
        boolean isEnableAuthority = this.iEntityAuthorityService.isEnableAuthority(unitEntity.getEntityIdL());
        DataTableAutomationQueryContext context = new DataTableAutomationQueryContext(dataTableKey, dataSchemeKey, unitEntity, maxDate, isSingleQuery, isCurrencyID, isEnableAuthority);
        context.setStartDate(startDate);
        context.setEndDate(endDate);
        context.setDataTable(dataTable);
        context.setDimensionValueSet(dimensionValueSet);
        context.setiPeriodEntity(iPeriodEntity);
        return context;
    }

    private List<String> parseEntityKeys(String entityKeyStr) {
        if (entityKeyStr.contains(",")) {
            return Arrays.asList(entityKeyStr.split(","));
        }
        return Collections.singletonList(entityKeyStr);
    }

    private DataTableAutomationQueryContext handleEmptyUnitKeyStr(DataTableAutomationQueryContext context) throws UnauthorizedEntityException {
        if (!this.isAdmin().booleanValue() && context.getEnableAuthority().booleanValue()) {
            DataTableAutomationQueryContext.UnitEntity unitEntity = context.getUnitEntity();
            Set canOperateEntityKeysSet = this.iEntityAuthorityService.getCanReadEntityKeys(unitEntity.getEntityIdL(), context.getStartDate(), context.getEndDate());
            context.getDimensionValueSet().setValue(unitEntity.getUnitDimensionName(), new ArrayList(canOperateEntityKeysSet));
        }
        return context;
    }

    private DataTableAutomationQueryContext handleNonEmptyUnitKeyStr(String entityKeyStr, DataTableAutomationQueryContext context) throws AutomationExecuteException {
        List<String> entityList;
        DataTableAutomationQueryContext.UnitEntity unitEntity = context.getUnitEntity();
        if (entityKeyStr.startsWith("FORMULA@")) {
            String formula = entityKeyStr.substring("FORMULA@".length());
            entityList = this.filterUnitByFormula(unitEntity.getEntityId(), formula, context.getEndDate());
        } else {
            entityList = this.parseEntityKeys(entityKeyStr);
        }
        if (CollectionUtils.isEmpty(entityList)) {
            context.getDimensionValueSet().setValue(unitEntity.getUnitDimensionName(), null);
        } else {
            List<String> canOperateEntityList = this.isAdmin() != false || context.getEnableAuthority() == false ? entityList : this.filterUnitByAuthority(entityList, unitEntity.getEntityIdL(), context.getStartDate(), context.getEndDate());
            context.getDimensionValueSet().setValue(unitEntity.getUnitDimensionName(), canOperateEntityList);
        }
        return context;
    }

    private List<String> filterUnitByAuthority(List<String> entityList, String entityIdL, Date startDate, Date endDate) throws AutomationExecuteException {
        ArrayList<String> canOperateEntityList = new ArrayList<String>(16);
        for (String unit : entityList) {
            try {
                if (!this.iEntityAuthorityService.canReadEntity(entityIdL, unit, startDate, endDate)) continue;
                canOperateEntityList.add(unit);
            }
            catch (Exception e) {
                logger.error("\u81ea\u52a8\u5316\u5bf9\u8c61\u6570\u636e\u65b9\u6848\u67e5\u8be2\u5355\u4f4d\u8bfb\u6743\u9650\u5b58\u5728\u5f02\u5e38\uff1a" + e.getMessage(), e);
                throw new AutomationExecuteException(e.getMessage(), (Throwable)e);
            }
        }
        return canOperateEntityList;
    }

    private DataTableAutomationQueryContext handlePeriodDimension(String entityKeyStr, DataTableAutomationQueryContext context) throws AutomationExecuteException {
        try {
            String maxDate = "";
            IPeriodEntity iPeriodEntity = context.getiPeriodEntity();
            List<String> entityList = this.parseEntityKeys(entityKeyStr);
            if (!CollectionUtils.isEmpty(entityList)) {
                context.getDimensionValueSet().setValue("DATATIME", entityList);
                for (String date : entityList) {
                    if (maxDate.compareTo(date) >= 0) continue;
                    maxDate = date;
                }
                IPeriodRow periodData = this.periodAdapterService.getPeriodData(iPeriodEntity.getKey(), maxDate);
                Date startDate = periodData.getStartDate();
                Date endDate = periodData.getEndDate();
                context.setMaxDate(maxDate);
                context.setStartDate(startDate);
                context.setEndDate(endDate);
            }
            return context;
        }
        catch (Exception e) {
            throw new AutomationExecuteException("\u65f6\u671f\u4f20\u5165\u9519\u8bef\uff0c\u8bf7\u68c0\u67e5\uff1a" + entityKeyStr + e.getMessage(), (Throwable)e);
        }
    }

    private DataTableAutomationQueryContext handleUnitDimension(String entityKeyStr, DataTableAutomationQueryContext context) throws AutomationExecuteException {
        try {
            if (entityKeyStr != null && !entityKeyStr.isEmpty()) {
                this.handleNonEmptyUnitKeyStr(entityKeyStr, context);
            } else {
                this.handleEmptyUnitKeyStr(context);
            }
        }
        catch (Exception e) {
            throw new AutomationExecuteException("\u5355\u4f4d\u4f20\u5165\u9519\u8bef\uff0c\u8bf7\u68c0\u67e5\uff1a" + entityKeyStr + e.getMessage(), (Throwable)e);
        }
        return context;
    }

    private DataTableAutomationQueryContext handleCurrencyDimension(String entityKeyStr, DataTableAutomationQueryContext context) throws AutomationExecuteException {
        try {
            if (entityKeyStr != null && !entityKeyStr.isEmpty()) {
                if (entityKeyStr.contains("CURRENCYID")) {
                    context.setCurrencyID(true);
                    context.setSingleQuery(true);
                }
                List<String> entityList = this.parseEntityKeys(entityKeyStr);
                context.getDimensionValueSet().setValue("MD_CURRENCY", entityList);
            }
        }
        catch (Exception e) {
            throw new AutomationExecuteException("\u5e01\u79cd\u4f20\u5165\u9519\u8bef\uff0c\u8bf7\u68c0\u67e5\uff1a" + entityKeyStr + e.getMessage(), (Throwable)e);
        }
        return context;
    }

    private DataTableAutomationQueryContext handleDimension(String parameter, String entityKeyStr, DataTableAutomationQueryContext context) throws AutomationExecuteException {
        try {
            String dimensionTableName = parameter.startsWith("P_") ? parameter.substring(2) : parameter;
            String dimensionName = this.iEntityMetaService.getDimensionNameByCode(dimensionTableName);
            if (dimensionName == null || dimensionName.isEmpty()) {
                dimensionName = dimensionTableName;
            }
            if (entityKeyStr != null && !entityKeyStr.isEmpty()) {
                List<String> entityList = this.parseEntityKeys(entityKeyStr);
                context.getDimensionValueSet().setValue(dimensionName, entityList);
            }
        }
        catch (Exception e) {
            throw new AutomationExecuteException("\u60c5\u666f\u4f20\u5165\u9519\u8bef\uff0c\u8bf7\u68c0\u67e5\uff1a" + entityKeyStr + e.getMessage(), (Throwable)e);
        }
        return context;
    }

    private DataTableAutomationQueryContext handleDimensionQuery(String parameter, Map<String, Object> parameterMap, DataTableAutomationQueryContext context) throws AutomationExecuteException {
        String entityKeyStr = parameterMap.get(parameter).toString();
        switch (parameter) {
            case "P_PERIOD": {
                this.handlePeriodDimension(entityKeyStr, context);
                break;
            }
            case "P_UNIT": {
                this.handleUnitDimension(entityKeyStr, context);
                break;
            }
            case "P_ORGCATEGORY": {
                context.setSingleQuery(true);
                break;
            }
            case "P_MD_CURRENCY": {
                this.handleCurrencyDimension(entityKeyStr, context);
                break;
            }
            default: {
                this.handleDimension(parameter, entityKeyStr, context);
            }
        }
        return context;
    }

    @QueryOperation
    public IOperationInvoker<StreamResult> query() throws AutomationExecuteException {
        return (automationElement, executeContext) -> {
            try {
                StringBuilder csvDataResult;
                Map parameterMap = executeContext.getParameterMap();
                List fieldNameList = executeContext.getFieldNameList();
                DataTableAutomationQueryContext context = this.initQueryContext(automationElement, parameterMap);
                for (String parameter : parameterMap.keySet()) {
                    context = this.handleDimensionQuery(parameter, parameterMap, context);
                }
                String dataTableKey = context.getDataTableKey();
                IDataQuery iDataQuery = this.iDataAccessProvider.newDataQuery();
                DataTable dataTable = this.iRuntimeDataSchemeService.getDataTable(dataTableKey);
                boolean fixed = dataTable.getDataTableType() == DataTableType.TABLE || dataTable.getDataTableType() == DataTableType.MD_INFO;
                iDataQuery = this.addColumnsData(dataTableKey, fieldNameList, iDataQuery);
                StringBuilder csvResult = this.queryColumnsData(dataTableKey, fieldNameList);
                if (context.getSingleQuery().booleanValue()) {
                    csvDataResult = fixed ? this.queryDataByUnit2(iDataQuery, context) : this.queryDataByUnit(iDataQuery, context);
                } else {
                    ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
                    ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(this.runTimeController, this.iDataDefinitionRuntimeController, this.iEntityViewRunTimeController);
                    env.setDataScehmeKey(context.getDataSchemeKey());
                    DataModelLinkFinder dataModelLinkFinder = new DataModelLinkFinder();
                    dataModelLinkFinder.setRuntimeDataSchemeService(this.iRuntimeDataSchemeService);
                    env.setDataModelLinkFinder((IDataModelLinkFinder)dataModelLinkFinder);
                    executorContext.setEnv((IFmlExecEnvironment)env);
                    executorContext.setOrgEntityId(context.getUnitEntity().getEntityIdL());
                    csvDataResult = this.queryDataSingle(iDataQuery, context.getDimensionValueSet(), executorContext);
                }
                csvResult.append((CharSequence)csvDataResult);
                byte[] csvResultBytes = csvResult.toString().getBytes();
                try (ByteArrayInputStream inputStream = new ByteArrayInputStream(csvResultBytes);){
                    StreamResult streamResult = new StreamResult((InputStream)inputStream);
                    return streamResult;
                }
                catch (Exception e) {
                    throw new AutomationExecuteException(e.getMessage(), (Throwable)e);
                }
            }
            catch (Exception e) {
                throw new AutomationExecuteException(e.getMessage(), (Throwable)e);
            }
        };
    }

    private Map<String, Integer> getBizkeyIndexMap(List<String> bizKeyList, List<DataField> dataFields, Boolean isAccount) {
        HashMap<String, Integer> bizkeyMap = new HashMap<String, Integer>();
        for (String bizKey : bizKeyList) {
            for (DataField dataField : dataFields) {
                if (!bizKey.equals(dataField.getKey())) continue;
                String fieldCode = dataField.getCode();
                if (isAccount.booleanValue() && ("FLOATORDER".equals(fieldCode) || "BIZKEYORDER".equals(fieldCode) || "SBID".equals(fieldCode))) continue;
                bizkeyMap.put(dataField.getCode(), null);
            }
        }
        return bizkeyMap;
    }

    private Map<String, Integer> getDimkeyIndexMap(DataTableAutomationWriteContext context) {
        Map<String, Integer> bizKeyIndexMap = context.getBizkeyIndexMap();
        List<DataField> dataFields = context.getDataFields();
        HashMap<String, Integer> dimKeyIndexMap = new HashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : bizKeyIndexMap.entrySet()) {
            String bizKey = entry.getKey();
            Integer bizKeyIndex = entry.getValue();
            for (DataField dataField : dataFields) {
                if (!bizKey.equals(dataField.getCode()) || !DataFieldKind.PUBLIC_FIELD_DIM.equals((Object)dataField.getDataFieldKind())) continue;
                dimKeyIndexMap.put(bizKey, bizKeyIndex);
            }
        }
        return dimKeyIndexMap;
    }

    private void handleZBDataDimCombination(DataTableAutomationWriteContext context, List<Object> zbDataList) {
        if (!CollectionUtils.isEmpty(zbDataList)) {
            DataTableType dataTableType = context.getDataTableType();
            Map<String, Integer> dimKeyIndexMap = context.getDimKeyIndexMap();
            String unitDimName = this.getUnitDimName(context);
            DimensionValueSet rowDVS = new DimensionValueSet();
            for (Map.Entry<String, Integer> entry : dimKeyIndexMap.entrySet()) {
                String dimCode = entry.getKey();
                Integer dimIndex = entry.getValue();
                if ("MDCODE".equals(dimCode)) {
                    rowDVS.setValue(unitDimName, (Object)zbDataList.get(dimIndex).toString());
                    continue;
                }
                if ("DATATIME".equals(dimCode) && DataTableType.ACCOUNT.equals((Object)dataTableType)) {
                    rowDVS.setValue(dimCode, (Object)context.getDatatime());
                    continue;
                }
                rowDVS.setValue(dimCode, (Object)zbDataList.get(dimIndex).toString());
            }
            DimensionCombinationBuilder dimCombinationBuilder = new DimensionCombinationBuilder(rowDVS);
            DimensionCombination dimCombination = dimCombinationBuilder.getCombination();
            List<DimensionCombination> authorityList = context.getAuthorityList();
            authorityList.add(dimCombination);
            context.setAuthorityList(authorityList);
        }
    }

    private void handleZBDataByAuthority(DataTableAutomationWriteContext context) throws AutomationExecuteException {
        IDataAccessService iDataAccessService = context.getiDataAccessService();
        List<List<Object>> zbDataList = context.getZbDataList();
        List<String> zbKeyList = context.getZbKeyList();
        List<DimensionCombination> authorityList = context.getAuthorityList();
        DimensionCollectionBuilder dimensionCollectionBuilder = new DimensionCollectionBuilder();
        List<DataDimension> dataSchemeDimension = context.getDataSchemeDimension();
        HashMap<String, Set<String>> zbDataDimMap = new HashMap<String, Set<String>>();
        this.handleZBDataDimMap(authorityList, zbDataDimMap);
        this.handleDimensionCollectionBuilder(dimensionCollectionBuilder, dataSchemeDimension, zbDataDimMap);
        DimensionCollection collection = dimensionCollectionBuilder.getCollection();
        IBatchZBAccessResult zbWriteAccess = iDataAccessService.getZBWriteAccess(collection, zbKeyList);
        ArrayList<Integer> rowNumsList = new ArrayList<Integer>();
        int rowNum = 0;
        for (DimensionCombination dimCom : authorityList) {
            for (String zbKey : zbKeyList) {
                try {
                    IAccessResult access = zbWriteAccess.getAccess(dimCom, zbKey);
                    if (access.haveAccess()) continue;
                    rowNumsList.add(rowNum);
                    break;
                }
                catch (Exception e) {
                    logger.error("\u81ea\u52a8\u5316\u5bf9\u8c61\u6570\u636e\u65b9\u6848\u5199\u5165\u64cd\u4f5c\uff0c\u6307\u6807\u6743\u9650\u6821\u9a8c\u5b58\u5728\u5f02\u5e38\uff1a" + e.getMessage(), e);
                    throw new AutomationExecuteException(e.getMessage(), (Throwable)e);
                }
            }
            ++rowNum;
        }
        ArrayList<List<Object>> zbDataNoAuthorityList = new ArrayList<List<Object>>();
        for (Integer num : rowNumsList) {
            zbDataNoAuthorityList.add(zbDataList.get(num));
        }
        zbDataList.removeAll(zbDataNoAuthorityList);
        context.setZbDataList(zbDataList);
    }

    private void handleDimensionCollectionBuilder(DimensionCollectionBuilder dimensionCollectionBuilder, List<DataDimension> dataSchemeDimension, Map<String, Set<String>> zbDataDimMap) {
        block0: for (DataDimension dataDimension : dataSchemeDimension) {
            String dimKey;
            DimensionType dimensionType = dataDimension.getDimensionType();
            if (DimensionType.UNIT_SCOPE.equals((Object)dimensionType)) continue;
            String dimNameL = dimKey = dataDimension.getDimKey();
            if (DimensionType.PERIOD.equals((Object)dimensionType)) {
                dimNameL = "DATATIME";
            }
            for (Map.Entry<String, Set<String>> entry : zbDataDimMap.entrySet()) {
                String dimName = entry.getKey();
                Set<String> dimList = entry.getValue();
                if (!dimNameL.contains(dimName)) continue;
                dimensionCollectionBuilder.setEntityValue(dimName, dimKey, dimList.toArray());
                continue block0;
            }
        }
    }

    private void handleZBDataDimMap(List<DimensionCombination> authorityList, Map<String, Set<String>> zbDataDimMap) {
        if (!CollectionUtils.isEmpty(authorityList)) {
            DimensionCombination dimensionCombination = authorityList.get(0);
            Collection names = dimensionCombination.getNames();
            for (String name : names) {
                zbDataDimMap.put(name, new HashSet());
            }
        }
        for (Map.Entry<String, Set<String>> entry : zbDataDimMap.entrySet()) {
            String dimName = entry.getKey();
            Set<String> dimList = entry.getValue();
            for (DimensionCombination dimCom : authorityList) {
                dimList.add(dimCom.getValue(dimName).toString());
                zbDataDimMap.put(dimName, dimList);
            }
        }
    }

    private String getUnitDimName(DataTableAutomationWriteContext context) {
        List unitDimensions = this.iRuntimeDataSchemeService.getDataSchemeDimension(context.getDataSchemeKey(), DimensionType.UNIT);
        DataDimension unitDimension = this.getDataDimension(unitDimensions);
        if (unitDimension == null) {
            return "MD_ORG";
        }
        return this.iEntityMetaService.getDimensionName(unitDimension.getDimKey());
    }

    private void writeTableData(DataTableAutomationWriteContext context) throws AutomationExecuteException {
        IDataTable iDataTable = context.getiDataTable();
        List<List<Object>> zbDataListAll = context.getZbDataList();
        List<DataField> dataFields = context.getDataFields();
        Map<String, Integer> bizkeyIndexMap = context.getBizkeyIndexMap();
        String unitDimName = this.getUnitDimName(context);
        int successNums = 0;
        for (List<Object> zbDataList : zbDataListAll) {
            ++successNums;
            DimensionValueSet rowDVS = new DimensionValueSet();
            for (Map.Entry<String, Integer> entry : bizkeyIndexMap.entrySet()) {
                String dimCode = entry.getKey();
                Integer dimIndex = entry.getValue();
                if ("MDCODE".equals(dimCode)) {
                    rowDVS.setValue(unitDimName, (Object)zbDataList.get(dimIndex).toString());
                    continue;
                }
                rowDVS.setValue(dimCode, (Object)zbDataList.get(dimIndex).toString());
            }
            IDataRow dataRow = iDataTable.findRow(rowDVS);
            if (dataRow == null) {
                try {
                    dataRow = iDataTable.appendRow(rowDVS);
                }
                catch (Exception e) {
                    logger.error("\u81ea\u52a8\u5316\u5bf9\u8c61\u6570\u636e\u65b9\u6848\u6307\u6807\u8868\u5199\u5165\u5b58\u5728\u5f02\u5e38\uff1a" + e.getMessage(), e);
                    throw new AutomationExecuteException(e.getMessage(), (Throwable)e);
                }
                this.insertDataByDataField(dataRow, dataFields, zbDataList);
                continue;
            }
            this.updateDataByDataField(dataRow, dataFields, zbDataList);
        }
        context.setSuccessNums(successNums + context.getSuccessNums());
    }

    private void deleteDetailData(DataTableAutomationWriteContext context) throws AutomationExecuteException {
        IDataTable iDataTable = context.getiDataTable();
        List<List<Object>> zbDataListAll = context.getZbDataList();
        Map<String, Integer> bizkeyIndexMap = context.getBizkeyIndexMap();
        List<DimensionValueSet> detailList = context.getDetailList();
        String unitDimName = this.getUnitDimName(context);
        Boolean needReQuery = false;
        for (List<Object> zbDataList : zbDataListAll) {
            DimensionValueSet rowDVS = new DimensionValueSet();
            for (Map.Entry<String, Integer> entry : bizkeyIndexMap.entrySet()) {
                String dimCode = entry.getKey();
                Integer dimIndex = entry.getValue();
                if ("MDCODE".equals(dimCode)) {
                    rowDVS.setValue(unitDimName, (Object)zbDataList.get(dimIndex).toString());
                    continue;
                }
                rowDVS.setValue(dimCode, (Object)zbDataList.get(dimIndex).toString());
            }
            DimensionValueSet rowNoBizkeyDVS = new DimensionValueSet();
            rowNoBizkeyDVS.combine(rowDVS);
            rowNoBizkeyDVS.clearValue("BIZKEYORDER");
            Boolean hadDelete = false;
            for (DimensionValueSet dvs : detailList) {
                if (!rowNoBizkeyDVS.equals((Object)dvs)) continue;
                hadDelete = true;
            }
            if (!Boolean.FALSE.equals(hadDelete)) continue;
            try {
                iDataTable.deleteRow(rowNoBizkeyDVS);
            }
            catch (IncorrectQueryException e) {
                logger.error("\u81ea\u52a8\u5316\u5bf9\u8c61\u6570\u636e\u65b9\u6848\u660e\u7ec6\u8868\u5199\u5165\uff0c\u5220\u9664\u6570\u636e\u5b58\u5728\u5f02\u5e38\uff1a" + e.getMessage(), e);
                throw new AutomationExecuteException(e.getMessage(), (Throwable)e);
            }
            detailList.add(rowNoBizkeyDVS);
            needReQuery = true;
        }
        if (Boolean.TRUE.equals(needReQuery)) {
            try {
                iDataTable.commitChanges(true, true);
                iDataTable = this.queryIDataTable(context.getiDataQuery(), context.getDataSchemeKey());
            }
            catch (Exception e) {
                logger.error("\u81ea\u52a8\u5316\u5bf9\u8c61\u6570\u636e\u65b9\u6848\u660e\u7ec6\u8868\u5199\u5165\uff0c\u63d0\u4ea4\u6570\u636e\u5b58\u5728\u5f02\u5e38\uff1a" + e.getMessage(), e);
                throw new AutomationExecuteException(e.getMessage(), (Throwable)e);
            }
            context.setiDataTable(iDataTable);
        }
    }

    private void writeDetailData(DataTableAutomationWriteContext context) throws AutomationExecuteException {
        IDataTable iDataTable = context.getiDataTable();
        List<List<Object>> zbDataListAll = context.getZbDataList();
        List<DataField> dataFields = context.getDataFields();
        Map<String, Integer> bizkeyIndexMap = context.getBizkeyIndexMap();
        String unitDimName = this.getUnitDimName(context);
        int successNums = 0;
        for (List<Object> zbDataList : zbDataListAll) {
            ++successNums;
            DimensionValueSet rowDVS = new DimensionValueSet();
            for (Map.Entry<String, Integer> entry : bizkeyIndexMap.entrySet()) {
                String dimCode = entry.getKey();
                Integer dimIndex = entry.getValue();
                if ("MDCODE".equals(dimCode)) {
                    rowDVS.setValue(unitDimName, (Object)zbDataList.get(dimIndex).toString());
                    continue;
                }
                if ("BIZKEYORDER".equals(dimCode) && "\"NULL\"".equals(zbDataList.get(dimIndex))) {
                    String uuid = UUID.randomUUID().toString();
                    zbDataList.set(dimIndex, uuid);
                }
                rowDVS.setValue(dimCode, (Object)zbDataList.get(dimIndex).toString());
            }
            IDataRow dataRow = null;
            try {
                dataRow = iDataTable.appendRow(rowDVS);
            }
            catch (IncorrectQueryException e) {
                logger.error("\u81ea\u52a8\u5316\u5bf9\u8c61\u6570\u636e\u65b9\u6848\u660e\u7ec6\u8868\u5199\u5165\u6570\u636e\u5b58\u5728\u5f02\u5e38\uff1a" + e.getMessage(), e);
                throw new AutomationExecuteException(e.getMessage(), (Throwable)e);
            }
            this.insertDataByDataField(dataRow, dataFields, zbDataList);
        }
        context.setSuccessNums(successNums + context.getSuccessNums());
    }

    private void updateDataByDataField(IDataRow dataRow, List<DataField> dataFields, List<Object> zbDataList) {
        int index = 0;
        for (DataField dataField : dataFields) {
            Object zbData = zbDataList.get(index);
            if (zbData != null && !"\"NULL\"".equals(zbData)) {
                dataRow.setValue((FieldDefine)dataField, zbData);
            }
            ++index;
        }
    }

    private void insertDataByDataField(IDataRow dataRow, List<DataField> dataFields, List<Object> zbDataList) {
        if (dataRow != null) {
            for (int i = 0; i < dataFields.size(); ++i) {
                DataField dataField = dataFields.get(i);
                Object zbData = zbDataList.get(i);
                if ("\"NULL\"".equals(zbData)) continue;
                dataRow.setValue((FieldDefine)dataField, zbData);
            }
        }
    }

    private List<Object> filterDataByFormat(DataTableAutomationWriteContext context, int zbCodeLen, List<Object> zbDataList, Map<String, Integer> bizkeyIndexMap) {
        DataTableType dataTableType = context.getDataTableType();
        if (zbDataList.size() != zbCodeLen) {
            if (zbCodeLen - zbDataList.size() != 1) {
                return null;
            }
            zbDataList.add("");
        }
        for (Map.Entry<String, Integer> entry : bizkeyIndexMap.entrySet()) {
            Integer bizKeyIndex = entry.getValue();
            Object zbData = null;
            if (bizKeyIndex != null) {
                zbData = zbDataList.get(bizKeyIndex);
            }
            if (zbData != null && !zbData.toString().trim().isEmpty() || DataTableType.ACCOUNT.equals((Object)dataTableType)) continue;
            if ("BIZKEYORDER".equals(entry.getKey()) && bizKeyIndex != null) {
                zbDataList.set(bizKeyIndex, UUID.randomUUID().toString());
                continue;
            }
            return null;
        }
        return zbDataList;
    }

    private DataTableAutomationWriteContext handleWriteData(DataTableType dataTableType, DataTableAutomationWriteContext context) throws AutomationExecuteException {
        switch (dataTableType) {
            case ACCOUNT: {
                this.handleZBDataByAuthority(context);
                this.writeAccountTempTable(context);
                this.clearSubmittedData(context);
                break;
            }
            case DETAIL: {
                this.handleZBDataByAuthority(context);
                this.deleteDetailData(context);
                this.writeDetailData(context);
                this.commitChanges(context);
                this.clearSubmittedData(context);
                break;
            }
            default: {
                this.handleZBDataByAuthority(context);
                this.writeTableData(context);
                this.commitChanges(context);
                this.clearSubmittedData(context);
            }
        }
        return context;
    }

    private void writeAccountTempTable(DataTableAutomationWriteContext context) throws AutomationExecuteException {
        List<List<Object>> zbDataList = context.getZbDataList();
        if (!CollectionUtils.isEmpty(zbDataList)) {
            ITempTable tmpTable = context.getTmpTable();
            try {
                this.formatTempTableData(context);
                this.tempTableDataCommit(context, tmpTable);
                context.setSuccessNums(context.getSuccessNums() + zbDataList.size());
            }
            catch (Exception e) {
                logger.error("\u81ea\u52a8\u5316\u5bf9\u8c61\u6570\u636e\u65b9\u6848\u53f0\u8d26\u8868\u5199\u5165\u4e34\u65f6\u8868\u5b58\u5728\u5f02\u5e38\uff1a" + e.getMessage(), e);
                throw new AutomationExecuteException(e.getMessage(), (Throwable)e);
            }
        }
    }

    private void commitChanges(DataTableAutomationWriteContext context) throws AutomationExecuteException {
        IDataTable iDataTable = context.getiDataTable();
        if (iDataTable != null) {
            try {
                iDataTable.commitChanges(true, true);
                iDataTable = this.queryIDataTable(context.getiDataQuery(), context.getDataSchemeKey());
            }
            catch (Exception e) {
                logger.error("\u81ea\u52a8\u5316\u5bf9\u8c61\u6570\u636e\u65b9\u6848\u5199\u5165\uff0c\u63d0\u4ea4\u6570\u636e\u5b58\u5728\u5f02\u5e38\uff1a" + e.getMessage(), e);
                throw new AutomationExecuteException(e.getMessage(), (Throwable)e);
            }
        }
        context.setiDataTable(iDataTable);
    }

    private DataTableAutomationWriteContext clearSubmittedData(DataTableAutomationWriteContext context) {
        List<DimensionCombination> authorityList = context.getAuthorityList();
        List<List<Object>> zbDataList = context.getZbDataList();
        List<Object[]> batchValues = context.getBatchValues();
        authorityList.clear();
        zbDataList.clear();
        batchValues.clear();
        return context;
    }

    /*
     * Exception decompiling
     */
    private DataTableAutomationWriteContext handleParseData(DataTableAutomationWriteContext context) throws AutomationExecuteException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private void commitAccountData(DataTableAutomationWriteContext context) throws AutomationExecuteException, IOException {
        String fullOrAdd = context.getFullOrAdd();
        String datatime = context.getDatatime();
        if (datatime == null || datatime.isEmpty()) {
            context.setSuccessNums(0);
        }
        DataTable dataTable = context.getDataTable();
        String dataTableKey = dataTable.getKey();
        TzDataImportRes tzDataImportRes = new TzDataImportRes();
        try (ITempTable tmpTable = context.getTmpTable();){
            TzParams tzParams = new TzParams(null, datatime, null, tmpTable.getTableName(), dataTableKey, fullOrAdd);
            BatchImportMonitor batchImportMonitor = new BatchImportMonitor();
            AsyncTaskMonitor loggerMonitor = BatchImportMonitor.loggerMonitor;
            this.tzBatchImportService.batchImport(tzParams, loggerMonitor);
        }
    }

    private DataTableAutomationWriteContext formatTempTableData(DataTableAutomationWriteContext context) {
        this.formatDateTimeData(context);
        List<List<Object>> zbDataList = context.getZbDataList();
        this.zbDataRemoveField(zbDataList, context.getFieldIndexList());
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        int id = context.getSuccessNums() + 1;
        for (List<Object> dataRow : zbDataList) {
            dataRow.add(id++);
            batchValues.add(dataRow.toArray(new Object[dataRow.size()]));
        }
        context.setBatchValues(batchValues);
        return context;
    }

    private DataTableAutomationWriteContext formatDateTimeData(DataTableAutomationWriteContext context) {
        List<DataField> dataFields = context.getDataFields();
        List<List<Object>> zbDataList = context.getZbDataList();
        if (!CollectionUtils.isEmpty(dataFields)) {
            block4: for (int i = 0; i < dataFields.size(); ++i) {
                DataField dataField = dataFields.get(i);
                switch (dataField.getDataFieldType()) {
                    case DATE_TIME: {
                        this.dateTimeDataTransfer(zbDataList, i, "yyyy-MM-dd HH:mm:ss");
                        continue block4;
                    }
                    case DATE: {
                        this.dateTimeDataTransfer(zbDataList, i, "yyyy-MM-dd");
                        continue block4;
                    }
                }
            }
        }
        return context;
    }

    private List<List<Object>> dateTimeDataTransfer(List<List<Object>> zbDataList, int index, String dateFormat) {
        for (List<Object> rowDataList : zbDataList) {
            rowDataList.set(index, DateUtil.getOriDatetime((String)rowDataList.get(index).toString(), (String)dateFormat));
        }
        return zbDataList;
    }

    private List<List<Object>> zbDataRemoveField(List<List<Object>> zbDataLists, List<Integer> fieldIndexList) {
        if (!CollectionUtils.isEmpty(fieldIndexList)) {
            for (List<Object> zbDataList : zbDataLists) {
                for (int i = 0; i < fieldIndexList.size(); ++i) {
                    Integer index = fieldIndexList.get(i);
                    zbDataList.remove(index - i);
                }
            }
        }
        return zbDataLists;
    }

    private void tempTableDataCommit(DataTableAutomationWriteContext context, ITempTable tmpTable) throws SQLException {
        List<String> zbCodeList = context.getZbCodeList();
        this.zbCodeRemoveField(zbCodeList, context.getFieldIndexList());
        List<Object[]> batchValues = context.getBatchValues();
        StringBuilder sql = new StringBuilder("insert into " + tmpTable.getTableName());
        StringBuilder valueSql = new StringBuilder();
        sql.append("(").append(zbCodeList.get(0));
        for (int i = 1; i < zbCodeList.size(); ++i) {
            sql.append(",").append(zbCodeList.get(i));
            valueSql.append(",?");
        }
        sql.append(",").append("ID");
        sql.append(") values (?,?").append((CharSequence)valueSql).append(")");
        String batchSql = sql.toString();
        Connection connection = null;
        try {
            connection = this.jdbcTemplate.getDataSource().getConnection();
            DataEngineUtil.batchUpdate((Connection)connection, (String)batchSql, batchValues);
        }
        catch (Exception e) {
            logger.warn(e.getMessage(), e);
            throw e;
        }
        finally {
            DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
        }
    }

    private List<String> zbCodeRemoveField(List<String> zbCodeList, List<Integer> fieldIndexList) {
        if (!CollectionUtils.isEmpty(fieldIndexList)) {
            for (int i = 0; i < fieldIndexList.size(); ++i) {
                Integer index = fieldIndexList.get(i);
                zbCodeList.remove(index - i);
            }
        }
        return zbCodeList;
    }

    private List<Integer> getFieldIndexList(List<DataField> dataFields) {
        ArrayList<Integer> fieldIndexList = new ArrayList<Integer>();
        for (DataField fieldDefine : dataFields) {
            String fieldCode = fieldDefine.getCode();
            if (!"DATATIME".equals(fieldCode) && !"FLOATORDER".equals(fieldCode) && !"BIZKEYORDER".equals(fieldCode) && !"SBID".equals(fieldCode)) continue;
            fieldIndexList.add(dataFields.indexOf(fieldDefine));
        }
        Collections.sort(fieldIndexList);
        return fieldIndexList;
    }

    private DataTableAutomationWriteContext initWriteContext(AutomationInstance automationElement, ExecuteContext executeContext) throws AutomationException {
        String dataTableKey = automationElement.getGuid();
        DataTable dataTable = this.iRuntimeDataSchemeService.getDataTable(dataTableKey);
        DataTableType dataTableType = dataTable.getDataTableType();
        String dataSchemeKey = dataTable.getDataSchemeKey();
        List dataFields = this.iRuntimeDataSchemeService.getDataFieldByTable(dataTableKey);
        List dataSchemeDimension = this.iRuntimeDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        IDataQuery iDataQuery = null;
        IDataTable iDataTable = null;
        ArrayList<DataRowImpl> allDataRows = null;
        if (!DataTableType.ACCOUNT.equals((Object)dataTableType)) {
            iDataQuery = this.iDataAccessProvider.newDataQuery();
            iDataQuery = this.addColumnsData(iDataQuery, dataFields);
            iDataTable = this.queryIDataTable(iDataQuery, dataSchemeKey);
            allDataRows = new ArrayList<DataRowImpl>(((DataTableImpl)iDataTable).getAllDataRows());
        }
        List<String> bizKeyList = Arrays.asList(dataTable.getBizKeys());
        InputStream inputStream = null;
        inputStream = executeContext.getParameterValueAsInputStream("DATASET");
        Map<String, Integer> bizkeyIndexMap = this.getBizkeyIndexMap(bizKeyList, dataFields, DataTableType.ACCOUNT.equals((Object)dataTableType));
        ArrayList<String> zbKeyList = new ArrayList<String>();
        for (DataField dataField : dataFields) {
            DataFieldKind dataFieldKind = dataField.getDataFieldKind();
            if (!dataFieldKind.equals((Object)DataFieldKind.FIELD_ZB) && !dataFieldKind.equals((Object)DataFieldKind.FIELD)) continue;
            zbKeyList.add(dataField.getKey());
        }
        IDataAccessService iDataAccessService = this.dataAccessServiceProvider.getZBDataAccessService();
        DataTableAutomationWriteContext context = new DataTableAutomationWriteContext(inputStream, bizkeyIndexMap, bizKeyList, dataFields, dataTable, dataTableType, iDataTable);
        context.setDataSchemeKey(dataSchemeKey);
        context.setiDataQuery(iDataQuery);
        context.setZbKeyList(zbKeyList);
        context.setiDataAccessService(iDataAccessService);
        context.setAllDataRows(allDataRows);
        context.setDataSchemeDimension(dataSchemeDimension);
        if (DataTableType.ACCOUNT.equals((Object)dataTableType)) {
            ITempTable tmpTable = this.createTempTable(dataTableKey);
            context.setTmpTable(tmpTable);
            context.setFullOrAdd(executeContext.getParameterValueAsString("METHOD"));
            context.setDatatime(executeContext.getParameterValueAsString("DATATIME"));
            context.setFieldIndexList(this.getFieldIndexList(dataFields));
        }
        return context;
    }

    private ITempTable createTempTable(String tableKey) throws AutomationExecuteException {
        ITempTable jioTempTable;
        DataSchemeTmpTable tmpTable = this.jioTempTableDao.dataFieldInit(tableKey);
        DataIOTempTableDefine jioTempTableDefine = new DataIOTempTableDefine(new JioTempTableInfo(tmpTable));
        try {
            jioTempTable = this.iTempTableManager.getTempTableByMeta((BaseTempTableDefine)jioTempTableDefine);
        }
        catch (Exception e) {
            throw new AutomationExecuteException("\u81ea\u52a8\u5316\u5bf9\u8c61\u53f0\u8d26\u8868\u5199\u5165\uff0c\u4e34\u65f6\u8868\u521b\u5efa\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
        }
        return jioTempTable;
    }

    @WriteOperation
    public IOperationInvoker<JsonResult> write() {
        return (automationElement, executeContext) -> {
            try {
                DataTableAutomationWriteContext context = this.initWriteContext(automationElement, executeContext);
                context = this.handleParseData(context);
                int allNums = context.getAllNums();
                int successNums = context.getSuccessNums();
                String jsonString = "\u81ea\u52a8\u5316\u5bf9\u8c61\u6570\u636e\u65b9\u6848\u5199\u5165\uff1a\u5f85\u5199\u5165\u6570\u636e\u5171" + allNums + "\u6761\uff0c\u6210\u529f" + successNums + "\u6761\uff0c\u5931\u8d25" + (allNums - successNums) + "\u6761\u3002";
                logger.info(jsonString);
                return new JsonResult(jsonString);
            }
            catch (Exception e) {
                throw new AutomationExecuteException("\u6570\u636e\u5199\u5165\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
            }
        };
    }

    @MetaOperation
    public IMetaInvoker meta() {
        return instance -> {
            AutomationMetaInfo metaInfo = new AutomationMetaInfo();
            ArrayList<AutomationParameter> parameterList = new ArrayList<AutomationParameter>();
            String dataTableKey = instance.getGuid();
            DataTable dataTable = this.iRuntimeDataSchemeService.getDataTable(dataTableKey);
            String dataSchemeKey = dataTable.getDataSchemeKey();
            DataTableType dataTableType = dataTable.getDataTableType();
            this.handleDimScope(parameterList, dataSchemeKey);
            this.handleQueryParameter(parameterList, dataSchemeKey);
            this.handleWriteParameter(metaInfo, parameterList, dataTableKey, dataTableType);
            return metaInfo;
        };
    }

    private void handleWriteParameter(AutomationMetaInfo metaInfo, List<AutomationParameter> parameterList, String dataTableKey, DataTableType dataTableType) {
        if (DataTableType.ACCOUNT.equals((Object)dataTableType)) {
            AutomationParameter writeParameter1 = new AutomationParameter("METHOD", "\u5bfc\u5165\u65b9\u5f0f", AutomationParameterDataTypeEnum.STRING, "F", true);
            writeParameter1.setValueMode(AutomationParameterValueModeEnum.DEFAULT);
            ArrayList<AutomationParameterOption> writeParameterOptionList = new ArrayList<AutomationParameterOption>();
            writeParameter1.setScopes(new HashSet<String>(Collections.singletonList("write")));
            writeParameterOptionList.add(new AutomationParameterOption("F", "\u5168\u91cf\u5bfc\u5165"));
            writeParameterOptionList.add(new AutomationParameterOption("A", "\u589e\u91cf\u5bfc\u5165"));
            writeParameter1.setOptions(writeParameterOptionList);
            parameterList.add(writeParameter1);
            AutomationParameter writeParameter2 = new AutomationParameter("DATATIME", "\u65f6\u671f", AutomationParameterDataTypeEnum.STRING, null, true);
            writeParameter2.setValueMode(AutomationParameterValueModeEnum.DEFAULT);
            writeParameter2.setScopes(new HashSet<String>(Collections.singletonList("write")));
            parameterList.add(writeParameter2);
        }
        AutomationParameter datasetParameter = new AutomationParameter("DATASET", "\u6570\u636e\u96c6\u53c2\u6570", AutomationParameterDataTypeEnum.FILE, null);
        datasetParameter.setScopes(new HashSet<String>(Arrays.asList("write", "execute")));
        parameterList.add(datasetParameter);
        ArrayList<AutomationFieldInfo> fieldInfoList = new ArrayList<AutomationFieldInfo>();
        List dataFields = this.iRuntimeDataSchemeService.getDataFieldByTable(dataTableKey);
        for (DataField fieldDefine : dataFields) {
            AutomationFieldInfo fieldInfo = new AutomationFieldInfo(fieldDefine.getCode(), fieldDefine.getTitle(), this.nrDataTypeTobi(fieldDefine.getDataFieldType()));
            fieldInfoList.add(fieldInfo);
        }
        metaInfo.setFieldInfoList(fieldInfoList);
        metaInfo.setParameterList(parameterList);
    }

    private void handleQueryParameter(List<AutomationParameter> parameterList, String dataSchemeKey) {
        AutomationParameter unitParam = new AutomationParameter("P_UNIT", "\u5355\u4f4d", AutomationParameterDataTypeEnum.STRING, null);
        unitParam.setValueMode(AutomationParameterValueModeEnum.MULTI_VALUE);
        unitParam.setScopes(new HashSet<String>(Collections.singletonList("query")));
        parameterList.add(unitParam);
        AutomationParameter periodParam = new AutomationParameter("P_PERIOD", "\u65f6\u671f", AutomationParameterDataTypeEnum.STRING, null, true);
        periodParam.setValueMode(AutomationParameterValueModeEnum.MULTI_VALUE);
        periodParam.setScopes(new HashSet<String>(Collections.singletonList("query")));
        parameterList.add(periodParam);
        List unitDimensionList = this.iRuntimeDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.UNIT);
        DataDimension unitDimension = (DataDimension)unitDimensionList.get(0);
        List customDimensionList = this.iRuntimeDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.DIMENSION);
        if (customDimensionList != null && !customDimensionList.isEmpty()) {
            for (DataDimension customDimension : customDimensionList) {
                String customDimensionName = this.iEntityMetaService.getDimensionName(customDimension.getDimKey());
                if (DataSchemeUtils.isSingleSelect((DataDimension)unitDimension, (DataDimension)customDimension)) continue;
                AutomationParameter customParam = "ADJUST".equalsIgnoreCase(customDimension.getDimKey()) ? new AutomationParameter("P_ADJUST", "\u8c03\u6574\u671f", AutomationParameterDataTypeEnum.STRING, null) : new AutomationParameter("P_" + customDimensionName, this.iEntityMetaService.queryEntity(customDimensionName).getTitle(), AutomationParameterDataTypeEnum.STRING, null);
                customParam.setValueMode(AutomationParameterValueModeEnum.MULTI_VALUE);
                customParam.setScopes(new HashSet<String>(Collections.singletonList("query")));
                parameterList.add(customParam);
            }
        }
    }

    private void handleDimScope(List<AutomationParameter> parameterList, String dataSchemeKey) {
        List hasDimScopeList = this.iRuntimeDataSchemeService.getDataSchemeDimension(dataSchemeKey, DimensionType.UNIT_SCOPE);
        if (hasDimScopeList != null && !hasDimScopeList.isEmpty()) {
            IEntityDefine iEntityDefineDefault = this.iEntityMetaService.queryEntity(((DataDimension)hasDimScopeList.get(0)).getDimKey());
            AutomationParameter customParam = new AutomationParameter("P_ORGCATEGORY", "\u7ec4\u7ec7\u673a\u6784\u7c7b\u578b", AutomationParameterDataTypeEnum.STRING, iEntityDefineDefault.getCode(), true);
            customParam.setValueMode(AutomationParameterValueModeEnum.DEFAULT);
            ArrayList<AutomationParameterOption> parameterOptionList = new ArrayList<AutomationParameterOption>();
            customParam.setScopes(new HashSet<String>(Collections.singletonList("query")));
            for (DataDimension dataDimension : hasDimScopeList) {
                IEntityDefine iEntityDefine = this.iEntityMetaService.queryEntity(dataDimension.getDimKey());
                parameterOptionList.add(new AutomationParameterOption(iEntityDefine.getCode(), iEntityDefine.getTitle()));
            }
            customParam.setOptions(parameterOptionList);
            parameterList.add(customParam);
        }
    }

    public static class RowFilter {
        private Set<DimensionValueSet> filters;
        private List<DimensionValueSet> queryMasterKeys;
    }
}

