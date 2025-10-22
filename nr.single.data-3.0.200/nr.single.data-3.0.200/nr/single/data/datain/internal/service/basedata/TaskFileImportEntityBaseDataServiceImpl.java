/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.esotericsoftware.minlog.Log
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.common.EntityUtils
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.single.core.dbf.DbfTableUtil
 *  com.jiuqi.nr.single.core.dbf.IDbfTable
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  javax.annotation.Resource
 *  nr.single.map.configurations.bean.AutoAppendCode
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 *  nr.single.map.configurations.bean.RuleKind
 *  nr.single.map.configurations.bean.RuleMap
 *  nr.single.map.configurations.bean.SkipUnit
 *  nr.single.map.configurations.bean.UnitCustomMapping
 *  nr.single.map.configurations.bean.UpdateWay
 *  nr.single.map.data.DataEntityInfo
 *  nr.single.map.data.PathUtil
 *  nr.single.map.data.SingleDataError
 *  nr.single.map.data.TaskDataContext
 *  nr.single.map.data.bean.RepeatEntityNode
 *  nr.single.map.data.bean.RepeatImportParam
 *  nr.single.map.data.exception.SingleDataException
 *  nr.single.map.data.facade.SingleFileFieldInfo
 *  nr.single.map.data.facade.SingleFileFmdmInfo
 *  nr.single.map.data.facade.SingleFileTableInfo
 *  nr.single.map.data.service.TaskDataService
 */
package nr.single.data.datain.internal.service.basedata;

import com.esotericsoftware.minlog.Log;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.EntityUtils;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.single.core.dbf.DbfTableUtil;
import com.jiuqi.nr.single.core.dbf.IDbfTable;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import nr.single.data.datain.service.ITaskFileImportEntityService;
import nr.single.map.configurations.bean.AutoAppendCode;
import nr.single.map.configurations.bean.ISingleMappingConfig;
import nr.single.map.configurations.bean.RuleKind;
import nr.single.map.configurations.bean.RuleMap;
import nr.single.map.configurations.bean.SkipUnit;
import nr.single.map.configurations.bean.UnitCustomMapping;
import nr.single.map.configurations.bean.UpdateWay;
import nr.single.map.data.DataEntityInfo;
import nr.single.map.data.PathUtil;
import nr.single.map.data.SingleDataError;
import nr.single.map.data.TaskDataContext;
import nr.single.map.data.bean.RepeatEntityNode;
import nr.single.map.data.bean.RepeatImportParam;
import nr.single.map.data.exception.SingleDataException;
import nr.single.map.data.facade.SingleFileFieldInfo;
import nr.single.map.data.facade.SingleFileFmdmInfo;
import nr.single.map.data.facade.SingleFileTableInfo;
import nr.single.map.data.service.TaskDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value="TaskFileImportEntityBaseDataServiceImpl")
public class TaskFileImportEntityBaseDataServiceImpl
implements ITaskFileImportEntityService {
    private static final Logger logger = LoggerFactory.getLogger(TaskFileImportEntityBaseDataServiceImpl.class);
    @Autowired
    private TaskDataService taskDataService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Resource
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeSevice;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void importSingleEnityData(TaskDataContext importContext, String path, AsyncTaskMonitor asyncTaskMonitor) throws SingleDataException {
        try {
            String dataPath = PathUtil.createNewPath((String)path, (String)"DATA");
            String dbfFileName = dataPath + importContext.getSingleFileFlag() + "FMDM.DBF";
            try (IDbfTable dbf = DbfTableUtil.getDbfTable((String)dbfFileName);){
                SingleFileTableInfo table = null;
                if (null == table && importContext.getMapingCache().isMapConfig()) {
                    table = importContext.getMapingCache().getFmdmInfo();
                }
                this.ImportEntityDatasToNet(importContext, importContext.getDwEntityId(), dbf, table, asyncTaskMonitor);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SingleDataException(e.getMessage(), (Throwable)e);
        }
    }

    private void ImportEntityDatasToNet(TaskDataContext importContext, String entityId, IDbfTable dbf, SingleFileTableInfo singeleTable, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        if (dbf.getRecordCount() > 0) {
            DataRow dbfRow = dbf.getRecordByIndex(0);
            String firstZDM = dbfRow.getValueString(0);
            importContext.setFirstZDM(firstZDM);
        }
        this.taskDataService.MapSingleEnityData(importContext);
        logger.info("\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u52a0\u8f7d\u53c2\u6570");
        if (StringUtils.isEmpty((String)importContext.getCurrentPeriod()) && StringUtils.isNotEmpty((String)importContext.getFirstZDM()) && StringUtils.isEmpty((String)importContext.getMapNetPeriodCode())) {
            String zdm = importContext.getFirstZDM();
            String singlePeriod = importContext.getEntityCache().getSinglePeriodByZdm(zdm);
            importContext.setCurrentPeriod(singlePeriod);
            String singlePeriod1 = importContext.getSingleTaskYear() + "@" + singlePeriod;
            String mapNetPeriod = this.taskDataService.getNetPeriodCode(importContext, singlePeriod1);
            importContext.setMapNetPeriodCode(mapNetPeriod);
        }
        HashMap<String, IEntityAttribute> fieldMap = new HashMap<String, IEntityAttribute>();
        HashMap<String, IEntityAttribute> singleFieldMap = new HashMap<String, IEntityAttribute>();
        HashMap<String, SingleFileFieldInfo> mapSingleFieldList = new HashMap<String, SingleFileFieldInfo>();
        HashMap<String, SingleFileFieldInfo> mapNetFieldList = new HashMap<String, SingleFileFieldInfo>();
        HashMap<String, String> fieldValueMap = new HashMap<String, String>();
        HashMap<String, DataField> zdmFieldMap = new HashMap<String, DataField>();
        Map entityKeyCodeMap = importContext.getEntityKeyCodeMap();
        ArrayList<String> singleZdmWithoutPeriodCodes = new ArrayList<String>();
        LinkedHashMap SingleZdmCodeAndFieldCodeMap = new LinkedHashMap();
        ArrayList<DataField> zdmFieldList = new ArrayList<DataField>();
        ArrayList<SingleFileFieldInfo> singeZdmFieldList = new ArrayList<SingleFileFieldInfo>();
        ArrayList<SingleFileFieldInfo> singeOtherFieldList = new ArrayList<SingleFileFieldInfo>();
        int periodIndex = 0;
        int peirodLength = 0;
        int zmdLength = 0;
        SingleFileFmdmInfo fMTable = null;
        boolean isZdmMapExpression = false;
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        importContext.setDwEntityId(entityId);
        Iterator iterator = entityModel.getAttributes();
        while (iterator.hasNext()) {
            IEntityAttribute next = (IEntityAttribute)iterator.next();
            fieldMap.put(next.getCode(), next);
        }
        if (null != importContext.getMapingCache().getMapConfig()) {
            ISingleMappingConfig mapConfig = importContext.getMapingCache().getMapConfig();
            List rules = mapConfig.getMapRule(RuleKind.UNIT_MAP_IMPORT);
            for (RuleMap rule : rules) {
                String fieldCode;
                if (StringUtils.isNotEmpty((String)rule.getSingleCode()) && !StringUtils.isEmpty((String)(fieldCode = rule.getNetCode()))) continue;
            }
        }
        DataField periodField = null;
        String periodCode = "";
        if (null != singeleTable) {
            DataField netField;
            for (SingleFileFieldInfo field : singeleTable.getRegion().getFields()) {
                DataLinkDefine link;
                mapNetFieldList.put(field.getNetFieldCode(), field);
                mapSingleFieldList.put(field.getFieldCode(), field);
                String netCode = field.getNetFieldCode();
                String singleFieldCode = field.getFieldCode();
                if (StringUtils.isNotEmpty((String)netCode)) {
                    netField = this.dataSchemeSevice.getZbKindDataFieldBySchemeKeyAndCode(importContext.getDataSchemeKey(), netCode);
                    if (netField != null && StringUtils.isNotEmpty((String)netField.getAlias())) {
                        singleFieldCode = netField.getAlias();
                    }
                } else if (StringUtils.isNotEmpty((String)field.getNetDataLinkKey()) && (link = this.runTimeAuthViewController.queryDataLinkDefine(field.getNetDataLinkKey())) != null && StringUtils.isNotEmpty((String)link.getLinkExpression())) {
                    String string = link.getLinkExpression();
                }
                if (StringUtils.isNotEmpty((String)netCode) && fieldMap.containsKey(netCode)) {
                    singleFieldMap.put(field.getFieldCode(), (IEntityAttribute)fieldMap.get(netCode));
                    continue;
                }
                if (fieldMap.containsKey(singleFieldCode)) {
                    singleFieldMap.put(field.getFieldCode(), (IEntityAttribute)fieldMap.get(singleFieldCode));
                    continue;
                }
                if (!fieldMap.containsKey(field.getFieldCode())) continue;
                singleFieldMap.put(field.getFieldCode(), (IEntityAttribute)fieldMap.get(field.getFieldCode()));
            }
            fMTable = (SingleFileFmdmInfo)singeleTable;
            for (String code : fMTable.getZdmFieldCodes()) {
                String netCode1;
                if (!code.equalsIgnoreCase(fMTable.getPeriodField())) {
                    singleZdmWithoutPeriodCodes.add(code);
                }
                if (!mapSingleFieldList.containsKey(code)) continue;
                SingleFileFieldInfo singleField = (SingleFileFieldInfo)mapSingleFieldList.get(code);
                singeZdmFieldList.add(singleField);
                zmdLength += singleField.getFieldSize();
                if (!code.equals(fMTable.getPeriodField())) {
                    singeOtherFieldList.add(singleField);
                    periodIndex += singleField.getFieldSize();
                } else {
                    peirodLength = singleField.getFieldSize();
                }
                String netCode = singleField.getNetFieldCode();
                if (SingleZdmCodeAndFieldCodeMap.containsKey(code) && fieldMap.containsKey(netCode1 = (String)SingleZdmCodeAndFieldCodeMap.get(code))) {
                    netCode = netCode1;
                }
                netField = null;
                if (fieldMap.containsKey(netCode)) {
                    netField = (IEntityAttribute)fieldMap.get(netCode);
                    zdmFieldMap.put(code, netField);
                    zdmFieldList.add(netField);
                } else {
                    zdmFieldMap.put(code, null);
                }
                if (!code.equalsIgnoreCase(fMTable.getPeriodField())) continue;
                periodField = netField;
                periodCode = this.taskDataService.getSinglePeriodCode(importContext, importContext.getNetPeriodCode(), singleField.getFieldSize());
                fieldValueMap.put(singleField.getFieldCode(), periodCode);
                importContext.setMapCurrentPeriod(periodCode);
            }
        }
        if (importContext.getEntityCache().getEntityList().size() > 3000000 || dbf.getDataRowCount() > 3000000) {
            this.upateEntityDataNew(importContext, dbf, null, fMTable, fieldMap, singleFieldMap, singleZdmWithoutPeriodCodes, peirodLength, periodIndex, zmdLength, asyncTaskMonitor);
        } else {
            this.upateEntityData(importContext, dbf, null, fMTable, fieldMap, singleFieldMap, singleZdmWithoutPeriodCodes, peirodLength, periodIndex, zmdLength, asyncTaskMonitor);
        }
    }

    private void upateEntityDataNew(TaskDataContext importContext, IDbfTable dbf, IEntityQuery dataQuery, SingleFileFmdmInfo fMTable, Map<String, IEntityAttribute> fieldMap, Map<String, IEntityAttribute> singleFieldMap, List<String> singleZdmWithoutPeriodCodes, int peirodLength, int periodIndex, int zmdLength, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        String netPeriodCode = importContext.getNetPeriodCode();
        if (StringUtils.isNotEmpty((String)importContext.getMapNetPeriodCode())) {
            netPeriodCode = importContext.getMapNetPeriodCode();
        }
        GregorianCalendar startCalendar = PeriodUtil.period2Calendar((PeriodWrapper)PeriodUtil.getPeriodWrapper((String)netPeriodCode));
        IEntityAttribute fjdField = null;
        IEntityAttribute codeField = null;
        IEntityAttribute titleField = null;
        IEntityAttribute autoAppendField = null;
        boolean isIncrement = true;
        boolean isUpdate = true;
        HashMap<String, String> zdmWithoutPeriodTempMap = new HashMap<String, String>();
        HashMap<String, String> zdmTempMap = new HashMap<String, String>();
        HashMap<String, String> tempFJDRows = new HashMap<String, String>();
        HashMap<String, String> tempFJDZdms = new HashMap<String, String>();
        Map entityCodeKeyMap = importContext.getEntityCodeKeyMap();
        Map entityZdmAndKeyMap = importContext.getEntityZdmKeyMap();
        Map entityKeyCodeMap = importContext.getEntityKeyCodeMap();
        Map entityKeyZdmMap = importContext.getEntityKeyZdmMap();
        Map uploadEntityZdmKeyMap = importContext.getUploadEntityZdmKeyMap();
        Map insertEntityZdmKeyMap = importContext.getInsertEntityZdmKeyMap();
        HashMap<String, DataRow> dbfZdmRowMap = new HashMap<String, DataRow>();
        HashMap<String, String> dbfZdmFjdMap = new HashMap<String, String>();
        uploadEntityZdmKeyMap.clear();
        insertEntityZdmKeyMap.clear();
        AutoAppendCode autoCodeCoinfig = null;
        ArrayList<String> skipUnitKeys = new ArrayList<String>();
        if (null != importContext.getMapingCache().getMapConfig()) {
            if (null != importContext.getMapingCache().getMapConfig().getConfig()) {
                UpdateWay way = importContext.getMapingCache().getMapConfig().getConfig().getUnitUpdateWay();
                isIncrement = way.isIncrement();
                isUpdate = way.isUpdate();
                SkipUnit skipUnit = importContext.getMapingCache().getMapConfig().getConfig().getSkipUnit();
                if (null != skipUnit) {
                    List skipUnitKeys3;
                    String skipFormula;
                    List skipUnitKeys2 = skipUnit.getUnitKey();
                    if (null != skipUnitKeys2) {
                        skipUnitKeys.addAll(skipUnitKeys2);
                    }
                    if (StringUtils.isNotEmpty((String)(skipFormula = skipUnit.getFormula())) && null != (skipUnitKeys3 = this.taskDataService.queryFilterUnits(importContext, skipFormula))) {
                        skipUnitKeys.addAll(skipUnitKeys3);
                    }
                }
            }
            if (null != importContext.getMapingCache().getMapConfig().getMapping()) {
                List list = importContext.getMapingCache().getMapConfig().getMapping().getUnitInfos();
                for (UnitCustomMapping unitMap : list) {
                    if (!StringUtils.isNotEmpty((String)unitMap.getNetUnitKey()) || !StringUtils.isEmpty((String)unitMap.getSingleUnitCode())) continue;
                    skipUnitKeys.add(unitMap.getNetUnitKey());
                }
            }
            if (null != importContext.getMapingCache().getMapConfig().getConfig() && null != importContext.getMapingCache().getMapConfig().getConfig().getAutoAppendCode()) {
                autoCodeCoinfig = importContext.getMapingCache().getMapConfig().getConfig().getAutoAppendCode();
            }
        }
        if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_CODE.fieldKey)) {
            codeField = fieldMap.get(Consts.EntityField.ENTITY_FIELD_CODE.fieldKey);
        } else if (fMTable != null && singleFieldMap.containsKey(fMTable.getDWDMField())) {
            codeField = singleFieldMap.get(fMTable.getDWDMField());
        }
        if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_TITLE.fieldKey)) {
            titleField = fieldMap.get(Consts.EntityField.ENTITY_FIELD_TITLE.fieldKey);
        } else if (fMTable != null && singleFieldMap.containsKey(fMTable.getDWMCField())) {
            titleField = singleFieldMap.get(fMTable.getDWMCField());
        }
        if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_PARENTKEY.fieldKey)) {
            fjdField = fieldMap.get(Consts.EntityField.ENTITY_FIELD_PARENTKEY.fieldKey);
        } else if (fieldMap.containsKey("PARENTID")) {
            fjdField = fieldMap.get("PARENTID");
        }
        if (autoCodeCoinfig != null && autoCodeCoinfig.isAutoAppendCode() && StringUtils.isNotEmpty((String)autoCodeCoinfig.getAppendCodeZb()) && fieldMap.containsKey(autoCodeCoinfig.getAppendCodeZb())) {
            autoAppendField = fieldMap.get(autoCodeCoinfig.getAppendCodeZb());
        }
        ArrayList dbfZdmPages = new ArrayList();
        ArrayList<String> dbfZdmCurPage = new ArrayList<String>();
        for (int i = 0; i < dbf.getRecordCount(); ++i) {
            DataRow dbfRow = dbf.getRecordByIndex(i);
            String zdm = dbfRow.getValueString(0);
            if (StringUtils.isEmpty((String)zdm)) continue;
            if (!dbf.isHasLoadAllRec()) {
                dbf.loadDataRow(dbfRow);
            }
            dbfZdmRowMap.put(zdm, dbfRow);
            String fjd = dbfRow.getValueString("SYS_FJD");
            dbfZdmFjdMap.put(zdm, fjd);
            if (dbfZdmCurPage.size() < 1000) {
                dbfZdmCurPage.add(zdm);
            } else {
                dbfZdmPages.add(dbfZdmCurPage);
                dbfZdmCurPage = new ArrayList();
                dbfZdmCurPage.add(zdm);
            }
            if (dbf.isHasLoadAllRec()) continue;
            dbf.clearDataRow(dbfRow);
        }
        if (dbfZdmCurPage.size() > 0) {
            dbfZdmPages.add(dbfZdmCurPage);
        }
        boolean showConsole = dbf.getRecordCount() <= 10;
        double addProgress = 0.0;
        if (dbf.getRecordCount() > 0) {
            addProgress = importContext.getNextProgressLen() / (double)dbf.getRecordCount();
        }
        DataEntityInfo entityInfo = null;
        HashMap<String, String> unitKeyZdms = new HashMap<String, String>();
        HashMap<String, String> zdmMapExCodes = new HashMap<String, String>();
        HashMap<String, String> zdmMapWithOutPeriod = new HashMap<String, String>();
        HashMap<String, String> zdmMapentityJcm = new HashMap<String, String>();
        for (int k = 0; k < dbfZdmPages.size(); ++k) {
            List dbfCurPage = (List)dbfZdmPages.get(k);
            ArrayList unitKeys = new ArrayList();
            ArrayList<String> arrayList = new ArrayList<String>();
            for (String zdm : dbfCurPage) {
                DataRow dbfRow = (DataRow)dbfZdmRowMap.get(zdm);
                if (!dbf.isHasLoadAllRec()) {
                    dbf.loadDataRow(dbfRow);
                }
                String zdmWithOutPeriod = "";
                if (singleZdmWithoutPeriodCodes.size() > 0) {
                    for (String code : singleZdmWithoutPeriodCodes) {
                        zdmWithOutPeriod = zdmWithOutPeriod + dbfRow.getValueString(code);
                    }
                }
                if (StringUtils.isEmpty((String)zdmWithOutPeriod)) {
                    zdmWithOutPeriod = zdm;
                    if (peirodLength > 0 && StringUtils.isNotEmpty((String)zdm) && zdm.length() > periodIndex) {
                        zdmWithOutPeriod = zdm.substring(0, periodIndex - 1) + zdm.substring(periodIndex + peirodLength - 1, zdm.length());
                    }
                }
                Object entityJcm = "";
                if (autoCodeCoinfig != null && autoCodeCoinfig.isAutoAppendCode() && StringUtils.isEmpty((String)(entityJcm = this.getJcmbyZdm(importContext, zdm, zdmWithOutPeriod, dbfZdmFjdMap, autoCodeCoinfig)))) {
                    String zdmCode = zdm;
                    if (StringUtils.isNotEmpty((String)fMTable.getDWDMField())) {
                        zdmCode = dbfRow.getValueString(fMTable.getDWDMField());
                    }
                    SingleDataError errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u65e0\u52a0\u957f\u7801\u914d\u7f6e\u4e0d\u5bfc\u5165", "notJcm", "", zdmWithOutPeriod, zdmCode);
                    importContext.recordLog("FMDM", errorItem);
                    continue;
                }
                String entityAutoExCode = zdmWithOutPeriod + (String)entityJcm;
                entityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityAutoExCodeFinder().get(entityAutoExCode);
                if (null != entityInfo) {
                    if (!unitKeyZdms.containsKey(entityInfo.getEntityKey())) {
                        unitKeys.add(entityInfo.getEntityKey());
                        arrayList.add(entityInfo.getEntityCode());
                        unitKeyZdms.put(entityInfo.getEntityKey(), zdm);
                    } else {
                        logger.info(entityAutoExCode + "\u5b58\u5728\u91cd\u7801");
                    }
                }
                zdmMapExCodes.put(zdm, entityAutoExCode);
                zdmMapWithOutPeriod.put(zdm, zdmWithOutPeriod);
                zdmMapentityJcm.put(zdm, (String)entityJcm);
            }
            logger.info("\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u67e5\u8be2\u6570\u636e");
            String tenantName = null;
            if (NpContextHolder.getContext() != null) {
                tenantName = NpContextHolder.getContext().getTenant();
            }
            BaseDataDTO queryParam = new BaseDataDTO();
            queryParam.setTableName(EntityUtils.getId((String)importContext.getDwEntityId()));
            queryParam.setStopflag(Integer.valueOf(-1));
            queryParam.setRecoveryflag(Integer.valueOf(-1));
            queryParam.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
            queryParam.setBaseDataCodes(arrayList);
            queryParam.setValidtime(startCalendar.getTime());
            PageVO queryRes = this.baseDataClient.list(queryParam);
            HashMap<String, BaseDataDO> dataMap = new HashMap<String, BaseDataDO>();
            if (queryRes != null && queryRes.getRows() != null) {
                for (BaseDataDO data : queryRes.getRows()) {
                    dataMap.put(data.getCode(), data);
                }
            }
            BaseDataBatchOptDTO addBatchOptDTO = new BaseDataBatchOptDTO();
            BaseDataBatchOptDTO updateBatchOptDTO = new BaseDataBatchOptDTO();
            BaseDataBatchOptDTO deleteBatchOptDTO = new BaseDataBatchOptDTO();
            ArrayList addDataList = new ArrayList();
            ArrayList updateDataList = new ArrayList();
            ArrayList deleteDataList = new ArrayList();
            addBatchOptDTO.setDataList(addDataList);
            updateBatchOptDTO.setDataList(updateDataList);
            deleteBatchOptDTO.setDataList(deleteDataList);
            HashMap<String, BaseDataDO> entityAutoExCodeAndRowMap = new HashMap<String, BaseDataDO>();
            logger.info("\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u6784\u9020\u4e3b\u4ee3\u7801\u4e0e\u6570\u636e\u884c\u5173\u7cfb");
            entityInfo = null;
            for (int i = 0; i < queryRes.getRows().size(); ++i) {
                BaseDataDO entityRow = (BaseDataDO)queryRes.getRows().get(i);
                String zdmKey = entityRow.getCode();
                entityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityKeyFinder().get(zdmKey);
                if (entityInfo == null) continue;
                entityAutoExCodeAndRowMap.put(entityInfo.getEntityAutoExCode(), entityRow);
            }
            for (String zdm : dbfCurPage) {
                if (null != asyncTaskMonitor) {
                    importContext.addProgress(addProgress);
                    asyncTaskMonitor.progressAndMessage(importContext.getProgress(), "");
                }
                DataRow dbfRow = (DataRow)dbfZdmRowMap.get(zdm);
                String entityAutoExCode = (String)zdmMapExCodes.get(zdm);
                String zdmWithOutPeriod = (String)zdmMapWithOutPeriod.get(zdm);
                String entityJcm = (String)zdmMapentityJcm.get(zdm);
                this.SetRowValue(importContext, fMTable, dbf, dbfRow, zdm, entityAutoExCode, zdmWithOutPeriod, entityJcm, showConsole, isUpdate, isIncrement, peirodLength, periodIndex, zmdLength, fjdField, codeField, titleField, autoAppendField, entityAutoExCodeAndRowMap, skipUnitKeys, fieldMap, singleFieldMap, singleZdmWithoutPeriodCodes, zdmMapWithOutPeriod, zdmWithoutPeriodTempMap, tempFJDZdms, zdmTempMap, tempFJDRows, zdmMapentityJcm, dbfZdmRowMap, zdmMapExCodes);
            }
            if (!importContext.getDataOption().isUploadEntityData()) continue;
            logger.info("\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u63d0\u4ea4\u4fdd\u5b58\u6570\u636e,\u5206\u9875" + String.valueOf(k));
            R r = null;
            if (addBatchOptDTO.getDataList().size() > 0) {
                r = this.baseDataClient.batchAdd(addBatchOptDTO);
                logger.info("\u5b9e\u4f53\u6570\u636e\u6dfb\u52a0\uff1a" + r.getMsg());
            }
            if (updateBatchOptDTO.getDataList().size() > 0) {
                r = this.baseDataClient.batchUpdate(updateBatchOptDTO);
                logger.info("\u5b9e\u4f53\u6570\u636e\u66f4\u65b0\uff1a" + r.getMsg());
            }
            if (deleteBatchOptDTO.getDataList().size() <= 0) continue;
            r = this.baseDataClient.batchRemove(deleteBatchOptDTO);
            logger.info("\u5b9e\u4f53\u6570\u636e\u5220\u9664\uff1a" + r.getMsg());
        }
        if (null != fjdField) {
            ArrayList unitCode2 = new ArrayList();
            ArrayList<String> unitkeys2 = new ArrayList<String>();
            for (Map.Entry entry : tempFJDRows.entrySet()) {
                String fjdzdmKey = (String)entry.getKey();
                String zdmKey = (String)entry.getValue();
                if (tempFJDZdms.containsKey(fjdzdmKey) || !StringUtils.isNotEmpty((String)zdmKey)) continue;
                unitkeys2.add(zdmKey);
                if (!entityKeyCodeMap.containsKey(zdmKey)) continue;
                unitCode2.add(entityKeyCodeMap.get(zdmKey));
            }
            if (unitCode2.size() > 0) {
                String tenantName = null;
                if (NpContextHolder.getContext() != null) {
                    tenantName = NpContextHolder.getContext().getTenant();
                }
                BaseDataDTO baseDataDTO = new BaseDataDTO();
                baseDataDTO.setTableName(EntityUtils.getId((String)importContext.getDwEntityId()));
                baseDataDTO.setStopflag(Integer.valueOf(-1));
                baseDataDTO.setRecoveryflag(Integer.valueOf(-1));
                baseDataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
                baseDataDTO.setBaseDataCodes(unitCode2);
                baseDataDTO.setValidtime(startCalendar.getTime());
                PageVO queryRes = this.baseDataClient.list(baseDataDTO);
                BaseDataBatchOptDTO updateBatchOptDTO = new BaseDataBatchOptDTO();
                updateBatchOptDTO.setDataList(new ArrayList());
                if (queryRes != null && queryRes.getRows() != null) {
                    for (BaseDataDO data : queryRes.getRows()) {
                        data.setParentcode(null);
                        updateBatchOptDTO.getDataList().add(data);
                        String zdmKey = data.getCode();
                        if (!importContext.getEntityCache().getEntityKeyFinder().containsKey(zdmKey)) continue;
                        entityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityKeyFinder().get(zdmKey);
                        entityInfo.setEntityParentKey("");
                    }
                }
                if (importContext.getDataOption().isUploadEntityData()) {
                    logger.info("\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u66f4\u6539\u4e0a\u7ea7\u5355\u4f4d");
                    if (updateBatchOptDTO.getDataList().size() > 0) {
                        R r = this.baseDataClient.batchUpdate(updateBatchOptDTO);
                        logger.info("\u66f4\u6539\u4e0a\u7ea7\u5355\u4f4d\uff1a" + r.getMsg());
                    }
                }
            }
        }
    }

    private void SetRowValue(TaskDataContext importContext, SingleFileFmdmInfo fMTable, IDbfTable dbf, DataRow dbfRow, String zdm, String entityAutoExCode, String zdmWithOutPeriod, String entityJcm, boolean showConsole, boolean isUpdate, boolean isIncrement, int peirodLength, int periodIndex, int zmdLength, IEntityAttribute fjdField, IEntityAttribute codeField, IEntityAttribute titleField, IEntityAttribute autoAppendField, Map<String, BaseDataDO> entityAutoExCodeAndRowMap, List<String> skipUnitKeys, Map<String, IEntityAttribute> fieldMap, Map<String, IEntityAttribute> singleFieldMap, List<String> singleZdmWithoutPeriodCodes, Map<String, String> zdmMapWithOutPeriod, Map<String, String> zdmWithoutPeriodTempMap, Map<String, String> tempFJDZdms, Map<String, String> zdmTempMap, Map<String, String> tempFJDRows, Map<String, String> zdmMapentityJcm, Map<String, DataRow> dbfZdmRowMap, Map<String, String> zdmMapExCodes) throws ParseException {
        Map entityCodeKeyMap = importContext.getEntityCodeKeyMap();
        Map entityZdmAndKeyMap = importContext.getEntityZdmKeyMap();
        Map entityKeyCodeMap = importContext.getEntityKeyCodeMap();
        Map entityKeyZdmMap = importContext.getEntityKeyZdmMap();
        Map uploadEntityZdmKeyMap = importContext.getUploadEntityZdmKeyMap();
        Map insertEntityZdmKeyMap = importContext.getInsertEntityZdmKeyMap();
        DataEntityInfo entityInfo = null;
        boolean isNewEntity = false;
        BaseDataDO entityRow = null;
        if (entityAutoExCodeAndRowMap.containsKey(entityAutoExCode)) {
            entityRow = entityAutoExCodeAndRowMap.get(entityAutoExCode);
            String zdmKey = entityRow.getCode();
            String zdmTitle = "";
            String zdmCode = "";
            entityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityKeyFinder().get(zdmKey);
            if (entityInfo != null && StringUtils.isNotEmpty((String)entityInfo.getEntityTitle())) {
                zdmTitle = entityInfo.getEntityTitle();
                zdmCode = entityInfo.getEntityCode();
            }
            if (skipUnitKeys.size() > 0 && skipUnitKeys.contains(zdmKey)) {
                SingleDataError errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0d\u5141\u8bb8\u5bfc\u5165", "notMap", zdmKey, zdmTitle, zdmCode);
                importContext.recordLog("FMDM", errorItem);
                if (!dbf.isHasLoadAllRec()) {
                    dbf.loadDataRow(dbfRow);
                }
                return;
            }
            uploadEntityZdmKeyMap.put(zdm, zdmKey);
            if (!isUpdate) {
                SingleDataError errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0d\u5141\u8bb8\u4fee\u6539\u5355\u4f4d\u4fe1\u606f", "notUpdate", zdmKey, zdmTitle, zdmCode);
                importContext.recordLog("FMDM", errorItem);
                if (!dbf.isHasLoadAllRec()) {
                    dbf.loadDataRow(dbfRow);
                }
                return;
            }
            if (showConsole) {
                logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u66f4\u65b0" + entityRow.getCode() + ",\u5355\u673a\u7248\u4e3b\u4ee3\u7801" + zdm + ",\u65f6\u95f4:" + new Date().toString());
            }
        } else {
            isNewEntity = true;
            if (!isIncrement) {
                String zdmCode = zdm;
                if (StringUtils.isNotEmpty((String)fMTable.getDWDMField())) {
                    zdmCode = dbfRow.getValueString(fMTable.getDWDMField());
                }
                SingleDataError errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0d\u5141\u8bb8\u65b0\u589e\u5355\u4f4d", "notAdd", "", zdm, zdmCode);
                importContext.recordLog("FMDM", errorItem);
                if (!dbf.isHasLoadAllRec()) {
                    dbf.loadDataRow(dbfRow);
                }
                return;
            }
            ArrayList<String> keys = new ArrayList<String>();
            String newZdmKeyID = null;
            if (zdmWithoutPeriodTempMap.containsKey(zdmWithOutPeriod)) {
                newZdmKeyID = zdmWithoutPeriodTempMap.get(zdmWithOutPeriod);
                tempFJDZdms.put(newZdmKeyID, zdm);
            } else if (zdmTempMap.containsKey(zdm)) {
                newZdmKeyID = zdmTempMap.get(zdm);
                tempFJDZdms.put(newZdmKeyID, zdm);
            } else {
                newZdmKeyID = UUID.randomUUID().toString();
            }
            if (showConsole) {
                logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u65b0\u589e" + newZdmKeyID.toString() + ",\u5355\u673a\u7248\u4e3b\u4ee3\u7801" + zdm + ",\u65f6\u95f4:" + new Date().toString());
            }
            String newZdmKey = zdmWithOutPeriod;
            keys.add(newZdmKey);
            entityRow = new BaseDataDTO();
            entityRow.setId(UUID.fromString(newZdmKeyID));
            entityInfo = new DataEntityInfo();
            entityCodeKeyMap.put(zdmWithOutPeriod, newZdmKey);
            entityKeyCodeMap.put(newZdmKey, zdmWithOutPeriod);
            entityKeyZdmMap.put(newZdmKey, zdm);
            entityZdmAndKeyMap.put(zdm, newZdmKey);
            insertEntityZdmKeyMap.put(zdm, newZdmKey);
            uploadEntityZdmKeyMap.put(zdm, newZdmKey);
            entityAutoExCodeAndRowMap.put(entityAutoExCode, entityRow);
            if (null != codeField) {
                String dwdmField = null;
                if (null != fMTable) {
                    dwdmField = fMTable.getDWDMField();
                }
                if (StringUtils.isNotEmpty((String)zdmWithOutPeriod)) {
                    entityRow.setCode(zdmWithOutPeriod);
                } else if (StringUtils.isNotEmpty((String)dwdmField)) {
                    entityRow.setCode(dbfRow.getValueString(dwdmField));
                } else {
                    entityRow.setCode(OrderGenerator.newOrder());
                }
            }
            if (null != titleField) {
                String dwmcField = null;
                if (null != fMTable) {
                    dwmcField = fMTable.getDWMCField();
                }
                if (StringUtils.isNotEmpty(dwmcField)) {
                    entityRow.setShowTitle(dbfRow.getValueString(dwmcField));
                } else {
                    entityRow.setShowTitle(OrderGenerator.newOrder());
                }
            }
            String netPeriodCode = importContext.getNetPeriodCode();
            if (StringUtils.isNotEmpty((String)importContext.getMapNetPeriodCode())) {
                netPeriodCode = importContext.getMapNetPeriodCode();
            }
            PeriodWrapper periodWrapper = new PeriodWrapper(netPeriodCode);
            Date beginDate = null;
            String[] periodList = PeriodUtil.getTimesArr((PeriodWrapper)periodWrapper);
            if (periodList != null && periodList.length == 2) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                beginDate = simpleDateFormat.parse(periodList[0]);
            }
            entityRow.setOrdinal(new BigDecimal(OrderGenerator.newOrderID()));
            if (null != beginDate) {
                entityRow.setValidtime(beginDate);
            } else {
                entityRow.setValidtime(Consts.DATE_VERSION_MIN_VALUE);
            }
            if (autoAppendField != null) {
                entityRow.put(autoAppendField.getCode().toLowerCase(), (Object)entityJcm);
            }
            entityInfo = new DataEntityInfo();
            entityInfo.setEntityKey(newZdmKey);
            entityInfo.setEntityExCode(zdmWithOutPeriod);
            entityInfo.setSingleZdm(zdm);
        }
        for (int j = 1; j < dbf.geDbfFields().length; ++j) {
            String fjdZdm;
            String dbfFieldName = dbf.geDbfFields()[j].getFieldName();
            String dbFieldValue = dbfRow.getValueString(j);
            if (singleFieldMap.containsKey(dbfFieldName)) {
                IEntityAttribute field = singleFieldMap.get(dbfFieldName);
                entityRow.put(field.getCode().toLowerCase(), (Object)dbFieldValue);
                continue;
            }
            if (!dbfFieldName.equalsIgnoreCase("SYS_FJD") || !StringUtils.isNotEmpty((String)(fjdZdm = dbFieldValue))) continue;
            String fjdZdmWithoutPeriod = fjdZdm = fjdZdm.toUpperCase();
            if (zdmMapWithOutPeriod.containsKey(fjdZdm)) {
                fjdZdmWithoutPeriod = zdmMapWithOutPeriod.get(fjdZdm);
            } else if (peirodLength > 0 && fjdZdm.length() == zmdLength) {
                fjdZdmWithoutPeriod = fjdZdm.substring(0, periodIndex - 1) + fjdZdm.substring(periodIndex + peirodLength - 1, fjdZdm.length());
            }
            String fjdEntityAutoExCode = zdmMapExCodes.get(fjdZdm);
            String fjdEntityJcm = zdmMapentityJcm.get(fjdZdm);
            if (StringUtils.isEmpty((String)fjdEntityAutoExCode) && StringUtils.isNotEmpty((String)fjdEntityJcm)) {
                fjdEntityAutoExCode = fjdZdmWithoutPeriod + fjdEntityJcm;
            }
            String fjdKey = null;
            String fjdCode = fjdZdmWithoutPeriod;
            entityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityAutoExCodeFinder().get(fjdEntityAutoExCode);
            if (StringUtils.isNotEmpty((String)fjdEntityAutoExCode) && entityInfo != null) {
                fjdKey = entityInfo.getEntityKey();
            } else if (entityCodeKeyMap.containsKey(fjdZdmWithoutPeriod)) {
                fjdKey = (String)entityCodeKeyMap.get(fjdZdmWithoutPeriod);
            } else if (entityZdmAndKeyMap.containsKey(fjdZdm)) {
                fjdKey = (String)entityZdmAndKeyMap.get(fjdZdm);
            } else if (zdmWithoutPeriodTempMap.containsKey(fjdZdmWithoutPeriod)) {
                fjdKey = zdmWithoutPeriodTempMap.get(fjdZdmWithoutPeriod);
                tempFJDRows.put(fjdKey, entityRow.getCode());
            } else if (zdmTempMap.containsKey(fjdZdm)) {
                fjdKey = zdmTempMap.get(fjdZdm);
                tempFJDRows.put(fjdKey, entityRow.getCode());
            }
            if (StringUtils.isEmpty((String)fjdKey) && dbfZdmRowMap.containsKey(fjdZdm)) {
                fjdKey = UUID.randomUUID().toString();
                zdmWithoutPeriodTempMap.put(fjdZdmWithoutPeriod, fjdKey);
                zdmTempMap.put(fjdZdm, fjdKey);
                tempFJDRows.put(fjdKey, entityRow.getCode());
            }
            if (null != fjdField && StringUtils.isNotEmpty((String)fjdKey)) {
                entityRow.setParentcode(fjdCode);
                continue;
            }
            if (!StringUtils.isNotEmpty((String)fjdKey)) continue;
            entityRow.setParentcode(fjdCode);
        }
        if (isNewEntity) {
            String entityCode = "";
            if (codeField != null) {
                entityCode = entityRow.getCode();
            }
            String entityTitle = "";
            if (titleField != null) {
                entityTitle = entityRow.getShowTitle();
            }
            String entityRowCaption = "";
            String entityParentKey = "";
            if (fjdField != null) {
                entityParentKey = entityRow.getParentcode();
            }
            if (null == entityInfo) {
                entityInfo = new DataEntityInfo();
            }
            entityInfo.setEntityTitle(entityTitle);
            entityInfo.setEntityCode(entityCode);
            entityInfo.setEntityParentKey(entityParentKey);
            entityInfo.setEntityRowCaption(entityRowCaption);
            entityInfo.setIsUnitMap(false);
            entityInfo.setExpEntityExCode(zdmWithOutPeriod);
            entityInfo.setExpSingleZdm(zdm);
            entityInfo.setEntityAppendCode(entityJcm);
            importContext.getEntityCache().addEntity(entityInfo);
        }
        if (!dbf.isHasLoadAllRec()) {
            dbf.clearDataRow(dbfRow);
        }
    }

    private void upateEntityData(TaskDataContext importContext, IDbfTable dbf, IEntityQuery dataQuery, SingleFileFmdmInfo fMTable, Map<String, IEntityAttribute> fieldMap, Map<String, IEntityAttribute> singleFieldMap, List<String> singleZdmWithoutPeriodCodes, int peirodLength, int periodIndex, int zmdLength, AsyncTaskMonitor asyncTaskMonitor) throws Exception {
        String netPeriodCode = importContext.getNetPeriodCode();
        if (StringUtils.isNotEmpty((String)importContext.getMapNetPeriodCode())) {
            netPeriodCode = importContext.getMapNetPeriodCode();
        }
        GregorianCalendar startCalendar = PeriodUtil.period2Calendar((PeriodWrapper)PeriodUtil.getPeriodWrapper((String)netPeriodCode));
        logger.info("\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u67e5\u8be2\u6570\u636e");
        String tenantName = null;
        if (NpContextHolder.getContext() != null) {
            tenantName = NpContextHolder.getContext().getTenant();
        }
        String entityTableName = null;
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(importContext.getDwEntityId());
        if (entityDefine != null) {
            entityTableName = entityDefine.getCode();
        }
        BaseDataDTO queryParam = new BaseDataDTO();
        queryParam.setTableName(EntityUtils.getId((String)importContext.getDwEntityId()));
        queryParam.setStopflag(Integer.valueOf(-1));
        queryParam.setRecoveryflag(Integer.valueOf(-1));
        queryParam.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        queryParam.setValidtime(startCalendar.getTime());
        PageVO queryRes = this.baseDataClient.list(queryParam);
        HashMap<String, BaseDataDO> dataMap = new HashMap<String, BaseDataDO>();
        if (queryRes != null && queryRes.getRows() != null) {
            for (BaseDataDO data : queryRes.getRows()) {
                dataMap.put(data.getCode(), data);
            }
        }
        BaseDataBatchOptDTO addBatchOptDTO = new BaseDataBatchOptDTO();
        BaseDataBatchOptDTO updateBatchOptDTO = new BaseDataBatchOptDTO();
        BaseDataBatchOptDTO deleteBatchOptDTO = new BaseDataBatchOptDTO();
        ArrayList addDataList = new ArrayList();
        ArrayList updateDataList = new ArrayList();
        ArrayList deleteDataList = new ArrayList();
        addBatchOptDTO.setDataList(addDataList);
        updateBatchOptDTO.setDataList(updateDataList);
        deleteBatchOptDTO.setDataList(deleteDataList);
        HashMap<String, BaseDataDO> entityAutoExCodeAndRowMap = new HashMap<String, BaseDataDO>();
        logger.info("\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u6784\u9020\u4e3b\u4ee3\u7801\u4e0e\u6570\u636e\u884c\u5173\u7cfb,\u884c\u6578:" + queryRes.getRows().size());
        DataEntityInfo entityInfo = null;
        for (int i = 0; i < queryRes.getRows().size(); ++i) {
            BaseDataDO entityRow = (BaseDataDO)queryRes.getRows().get(i);
            String zdmKey = entityRow.getCode();
            entityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityKeyFinder().get(zdmKey);
            if (entityInfo == null) continue;
            entityAutoExCodeAndRowMap.put(entityInfo.getEntityAutoExCode(), entityRow);
        }
        IEntityAttribute fjdField = null;
        IEntityAttribute codeField = null;
        IEntityAttribute UnitCodeField = null;
        IEntityAttribute titleField = null;
        IEntityAttribute autoAppendField = null;
        boolean isIncrement = true;
        boolean isUpdate = true;
        HashMap<String, String> zdmWithoutPeriodTempMap = new HashMap<String, String>();
        HashMap<String, String> zdmTempMap = new HashMap<String, String>();
        HashMap<String, BaseDataDO> tempFJDRows = new HashMap<String, BaseDataDO>();
        HashMap<String, String> tempFJDZdms = new HashMap<String, String>();
        Map entityCodeKeyMap = importContext.getEntityCodeKeyMap();
        Map entityZdmAndKeyMap = importContext.getEntityZdmKeyMap();
        Map entityKeyCodeMap = importContext.getEntityKeyCodeMap();
        Map entityKeyZdmMap = importContext.getEntityKeyZdmMap();
        Map uploadEntityZdmKeyMap = importContext.getUploadEntityZdmKeyMap();
        Map insertEntityZdmKeyMap = importContext.getInsertEntityZdmKeyMap();
        HashMap<String, DataRow> dbfZdmRowMap = new HashMap<String, DataRow>();
        HashMap<String, String> dbfZdmFjdMap = new HashMap<String, String>();
        uploadEntityZdmKeyMap.clear();
        insertEntityZdmKeyMap.clear();
        AutoAppendCode autoCodeCoinfig = null;
        ArrayList<String> skipUnitKeys = new ArrayList<String>();
        if (null != importContext.getMapingCache().getMapConfig()) {
            if (null != importContext.getMapingCache().getMapConfig().getConfig()) {
                UpdateWay way = importContext.getMapingCache().getMapConfig().getConfig().getUnitUpdateWay();
                isIncrement = way.isIncrement();
                isUpdate = way.isUpdate();
                SkipUnit skipUnit = importContext.getMapingCache().getMapConfig().getConfig().getSkipUnit();
                if (null != skipUnit) {
                    List skipUnitKeys3;
                    String skipFormula;
                    List skipUnitKeys2 = skipUnit.getUnitKey();
                    if (null != skipUnitKeys2) {
                        skipUnitKeys.addAll(skipUnitKeys2);
                    }
                    if (StringUtils.isNotEmpty((String)(skipFormula = skipUnit.getFormula())) && null != (skipUnitKeys3 = this.taskDataService.queryFilterUnits(importContext, skipFormula))) {
                        skipUnitKeys.addAll(skipUnitKeys3);
                    }
                }
            }
            if (null != importContext.getMapingCache().getMapConfig().getMapping()) {
                List list = importContext.getMapingCache().getMapConfig().getMapping().getUnitInfos();
                for (UnitCustomMapping unitMap : list) {
                    if (!StringUtils.isNotEmpty((String)unitMap.getNetUnitKey()) || !StringUtils.isEmpty((String)unitMap.getSingleUnitCode())) continue;
                    skipUnitKeys.add(unitMap.getNetUnitKey());
                }
            }
            if (null != importContext.getMapingCache().getMapConfig().getConfig() && null != importContext.getMapingCache().getMapConfig().getConfig().getAutoAppendCode()) {
                autoCodeCoinfig = importContext.getMapingCache().getMapConfig().getConfig().getAutoAppendCode();
            }
        }
        if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_CODE.fieldKey)) {
            codeField = fieldMap.get(Consts.EntityField.ENTITY_FIELD_CODE.fieldKey);
        } else if (fMTable != null && singleFieldMap.containsKey(fMTable.getDWDMField())) {
            codeField = singleFieldMap.get(fMTable.getDWDMField());
        }
        if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_TITLE.fieldKey)) {
            titleField = fieldMap.get(Consts.EntityField.ENTITY_FIELD_TITLE.fieldKey);
        } else if (fMTable != null && singleFieldMap.containsKey(fMTable.getDWMCField())) {
            titleField = singleFieldMap.get(fMTable.getDWMCField());
        }
        if (fieldMap.containsKey(Consts.EntityField.ENTITY_FIELD_PARENTKEY.fieldKey)) {
            fjdField = fieldMap.get(Consts.EntityField.ENTITY_FIELD_PARENTKEY.fieldKey);
        } else if (fieldMap.containsKey("PARENTID")) {
            fjdField = fieldMap.get("PARENTID");
        }
        if (autoCodeCoinfig != null && autoCodeCoinfig.isAutoAppendCode() && StringUtils.isNotEmpty((String)autoCodeCoinfig.getAppendCodeZb()) && fieldMap.containsKey(autoCodeCoinfig.getAppendCodeZb())) {
            autoAppendField = fieldMap.get(autoCodeCoinfig.getAppendCodeZb());
        }
        if (codeField != null && StringUtils.isNotEmpty((String)importContext.getDwEntityId())) {
            UnitCodeField = codeField;
        }
        RepeatImportParam jioRepeatParam = importContext.getJioRepeatParam();
        RepeatImportParam jioRepeatResult = importContext.getJioRepeatResult();
        if (jioRepeatResult == null) {
            jioRepeatResult = new RepeatImportParam();
        }
        Map repeatNodes = importContext.getRepeatEntityNodes();
        for (int i = 0; i < dbf.getRecordCount(); ++i) {
            DataRow dbfRow = dbf.getRecordByIndex(i);
            String zdm = dbfRow.getValueString(0);
            if (StringUtils.isEmpty((String)zdm)) continue;
            dbfZdmRowMap.put(zdm, dbfRow);
            String fjd = dbfRow.getValueString("SYS_FJD");
            dbfZdmFjdMap.put(zdm, fjd);
        }
        boolean showConsole = dbf.getRecordCount() <= 10;
        double addProgress = 0.0;
        if (dbf.getRecordCount() > 0) {
            addProgress = importContext.getNextProgressLen() / (double)dbf.getRecordCount();
        }
        for (int i = 0; i < dbf.getRecordCount(); ++i) {
            String dbfPeriod;
            String zdm;
            if (null != asyncTaskMonitor) {
                importContext.addProgress(addProgress);
                if (i % 10 == 0) {
                    asyncTaskMonitor.progressAndMessage(importContext.getProgress(), "");
                }
            }
            DataRow dbfRow = dbf.getRecordByIndex(i);
            if (!dbf.isHasLoadAllRec()) {
                dbf.loadDataRow(dbfRow);
            }
            BaseDataDO entityRow = null;
            String oldZdm = zdm = dbfRow.getValueString(0);
            if (StringUtils.isEmpty((String)zdm) || StringUtils.isNotEmpty((String)fMTable.getPeriodField()) && StringUtils.isNotEmpty((String)(dbfPeriod = dbfRow.getValueString(fMTable.getPeriodField()))) && !dbfPeriod.equalsIgnoreCase(importContext.getCurrentPeriod())) continue;
            String tempQYDM = null;
            if (repeatNodes.containsKey(zdm)) {
                RepeatEntityNode repeatNode = (RepeatEntityNode)repeatNodes.get(zdm);
                if (repeatNode.getRepeatMode() == 0) continue;
                if (repeatNode.getRepeatMode() != 1) {
                    if (repeatNode.getRepeatMode() != 2) continue;
                    tempQYDM = repeatNode.getTempQYDM();
                    if (StringUtils.isEmpty((String)tempQYDM)) {
                        Log.info((String)("\u91cd\u7801\u5355\u4f4d\u7684\u4e34\u65f6\u7801\u9519\u8bef:" + zdm + ",\u4e34\u65f6\u7801\uff1a" + tempQYDM));
                        continue;
                    }
                }
            }
            String zdmWithOutPeriod = "";
            String singlePeriodInZdm = "";
            if (singleZdmWithoutPeriodCodes.size() > 0) {
                for (String code : singleZdmWithoutPeriodCodes) {
                    if (StringUtils.isNotEmpty((String)tempQYDM) && StringUtils.isNotEmpty((String)code) && code.equalsIgnoreCase(fMTable.getDWDMField())) {
                        zdmWithOutPeriod = zdmWithOutPeriod + tempQYDM;
                        continue;
                    }
                    zdmWithOutPeriod = zdmWithOutPeriod + dbfRow.getValueString(code);
                }
            }
            if (StringUtils.isEmpty((String)zdmWithOutPeriod)) {
                if (StringUtils.isNotEmpty(tempQYDM) && importContext.getEntityCache().getSingleQYDMIndex() >= 0) {
                    zdm = importContext.getEntityCache().getNewZdmByQydm(zdm, tempQYDM);
                }
                zdmWithOutPeriod = zdm;
                if (peirodLength > 0 && StringUtils.isNotEmpty((String)zdm) && zdm.length() > periodIndex) {
                    singlePeriodInZdm = zdm.substring(periodIndex, periodIndex + peirodLength);
                    zdmWithOutPeriod = zdm.substring(0, periodIndex - 1) + zdm.substring(periodIndex + peirodLength - 1, zdm.length());
                }
            }
            String entityJcm = "";
            if (autoCodeCoinfig != null && autoCodeCoinfig.isAutoAppendCode() && StringUtils.isEmpty((String)(entityJcm = this.getJcmbyZdm(importContext, zdm, zdmWithOutPeriod, dbfZdmFjdMap, autoCodeCoinfig)))) {
                String zdmCode = zdm;
                if (StringUtils.isNotEmpty((String)fMTable.getDWDMField())) {
                    zdmCode = dbfRow.getValueString(fMTable.getDWDMField());
                }
                SingleDataError errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u65e0\u52a0\u957f\u7801\u914d\u7f6e\u4e0d\u5bfc\u5165", "notJcm", "", zdmWithOutPeriod, zdmCode);
                importContext.recordLog("FMDM", errorItem);
                continue;
            }
            String entityAutoExCode = zdmWithOutPeriod + entityJcm;
            entityInfo = null;
            boolean isNewEntity = false;
            if (entityAutoExCodeAndRowMap.containsKey(entityAutoExCode)) {
                SingleDataError errorItem;
                entityRow = (BaseDataDO)entityAutoExCodeAndRowMap.get(entityAutoExCode);
                BaseDataDTO entityRow1 = new BaseDataDTO();
                entityRow1.setTableName(entityTableName);
                entityRow1.setTenantName(tenantName);
                entityRow1.setId(entityRow.getId());
                entityRow1.putAll((Map)entityRow);
                updateBatchOptDTO.getDataList().add(entityRow1);
                entityRow = entityRow1;
                String zdmKey = entityRow.getCode();
                String zdmTitle = "";
                String zdmCode = "";
                entityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityKeyFinder().get(zdmKey);
                if (entityInfo != null && StringUtils.isNotEmpty((String)entityInfo.getEntityTitle())) {
                    zdmTitle = entityInfo.getEntityTitle();
                    zdmCode = entityInfo.getEntityCode();
                }
                if (importContext.isNeedCheckRepeat() && jioRepeatParam == null) {
                    RepeatEntityNode repeatNode = new RepeatEntityNode();
                    repeatNode.setNetBBLX("");
                    repeatNode.setNetCode(zdmKey);
                    repeatNode.setNetTitle(zdmTitle);
                    repeatNode.setNetQYDM("");
                    repeatNode.setSingleZdm(zdm);
                    repeatNode.setSingleCode(zdmWithOutPeriod);
                    repeatNode.setSinglePeriod(singlePeriodInZdm);
                    repeatNode.setSingleQYDM(dbfRow.getValueString(fMTable.getDWDMField()));
                    repeatNode.setSingleBBLX(dbfRow.getValueString(fMTable.getBBLXField()));
                    repeatNode.setSingleTitle(dbfRow.getValueString(fMTable.getDWMCField()));
                    jioRepeatResult.getEntityNodes().add(repeatNode);
                    continue;
                }
                if (skipUnitKeys.size() > 0 && skipUnitKeys.contains(zdmKey)) {
                    errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0d\u5141\u8bb8\u5bfc\u5165", "notMap", zdmKey, zdmTitle, zdmCode);
                    importContext.recordLog("FMDM", errorItem);
                    continue;
                }
                uploadEntityZdmKeyMap.put(zdm, zdmKey);
                if (!isUpdate) {
                    errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0d\u5141\u8bb8\u4fee\u6539\u5355\u4f4d\u4fe1\u606f", "notUpdate", zdmKey, zdmTitle, zdmCode);
                    importContext.recordLog("FMDM", errorItem);
                    continue;
                }
                if (showConsole) {
                    logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u66f4\u65b0" + entityRow.getCode() + ",\u5355\u673a\u7248\u4e3b\u4ee3\u7801" + zdm + ",\u65f6\u95f4:" + new Date().toString());
                }
            } else {
                isNewEntity = true;
                if (!isIncrement) {
                    String zdmCode = zdm;
                    if (StringUtils.isNotEmpty((String)fMTable.getDWDMField())) {
                        zdmCode = dbfRow.getValueString(fMTable.getDWDMField());
                    }
                    SingleDataError errorItem = new SingleDataError("\u5c01\u9762\u4ee3\u7801", "FMDM", "\u4e0d\u5141\u8bb8\u65b0\u589e\u5355\u4f4d", "notAdd", "", zdm, zdmCode);
                    importContext.recordLog("FMDM", errorItem);
                    continue;
                }
                ArrayList<String> keys = new ArrayList<String>();
                String newZdmKeyID = null;
                if (zdmWithoutPeriodTempMap.containsKey(zdmWithOutPeriod)) {
                    newZdmKeyID = (String)zdmWithoutPeriodTempMap.get(zdmWithOutPeriod);
                    tempFJDZdms.put(newZdmKeyID, zdm);
                } else if (zdmTempMap.containsKey(zdm)) {
                    newZdmKeyID = (String)zdmTempMap.get(zdm);
                    tempFJDZdms.put(newZdmKeyID, zdm);
                } else {
                    newZdmKeyID = UUID.randomUUID().toString();
                }
                if (showConsole) {
                    logger.info("\u5bfc\u5165JIO\u6570\u636e\uff1a===\u5b9e\u4f53\u6570\u636e,\u65b0\u589e" + newZdmKeyID.toString() + ",\u5355\u673a\u7248\u4e3b\u4ee3\u7801" + zdm + ",\u65f6\u95f4:" + new Date().toString());
                }
                String newZdmKey = zdmWithOutPeriod;
                keys.add(newZdmKey);
                entityRow = new BaseDataDTO();
                entityRow.setId(UUID.fromString(newZdmKeyID));
                entityRow.setTableName(entityTableName);
                entityRow.setTenantName(tenantName);
                addBatchOptDTO.getDataList().add(entityRow);
                entityInfo = new DataEntityInfo();
                entityCodeKeyMap.put(zdmWithOutPeriod, newZdmKey);
                entityKeyCodeMap.put(newZdmKey, zdmWithOutPeriod);
                entityKeyZdmMap.put(newZdmKey, zdm);
                entityZdmAndKeyMap.put(zdm, newZdmKey);
                insertEntityZdmKeyMap.put(zdm, newZdmKey);
                uploadEntityZdmKeyMap.put(zdm, newZdmKey);
                entityAutoExCodeAndRowMap.put(entityAutoExCode, entityRow);
                if (null != codeField) {
                    String dwdmField = null;
                    if (null != fMTable) {
                        dwdmField = fMTable.getDWDMField();
                    }
                    if (StringUtils.isNotEmpty((String)zdmWithOutPeriod)) {
                        entityRow.setCode(zdmWithOutPeriod);
                    } else if (StringUtils.isNotEmpty((String)dwdmField)) {
                        entityRow.setCode(dbfRow.getValueString(dwdmField));
                    } else {
                        entityRow.setCode(OrderGenerator.newOrder());
                    }
                    if (UnitCodeField != null && StringUtils.isNotEmpty((String)zdmWithOutPeriod)) {
                        entityRow.setCode(zdmWithOutPeriod);
                    }
                }
                if (null != titleField) {
                    String dwmcField = null;
                    if (null != fMTable) {
                        dwmcField = fMTable.getDWMCField();
                    }
                    if (StringUtils.isNotEmpty(dwmcField)) {
                        entityRow.setShowTitle(dbfRow.getValueString(dwmcField));
                        entityRow.put(titleField.getCode().toLowerCase(), (Object)entityRow.getShowTitle());
                    } else {
                        entityRow.setShowTitle(OrderGenerator.newOrder());
                        entityRow.put(titleField.getCode().toLowerCase(), (Object)entityRow.getShowTitle());
                    }
                    entityRow.setName(entityRow.getShowTitle());
                }
                PeriodWrapper periodWrapper = new PeriodWrapper(netPeriodCode);
                Date beginDate = null;
                String[] periodList = PeriodUtil.getTimesArr((PeriodWrapper)periodWrapper);
                if (periodList != null && periodList.length == 2) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    beginDate = simpleDateFormat.parse(periodList[0]);
                }
                entityRow.setOrdinal(new BigDecimal(OrderGenerator.newOrderID()));
                if (null != beginDate) {
                    entityRow.put(fieldMap.get(Consts.EntityField.ENTITY_FIELD_VALIDTIME.fieldKey).getCode().toLowerCase(), (Object)beginDate);
                } else {
                    entityRow.put(fieldMap.get(Consts.EntityField.ENTITY_FIELD_VALIDTIME.fieldKey).getCode().toLowerCase(), (Object)Consts.DATE_VERSION_MIN_VALUE);
                }
                entityRow.put(fieldMap.get(Consts.EntityField.ENTITY_FIELD_INVALIDTIME.fieldKey).getCode().toLowerCase(), (Object)Consts.DATE_VERSION_MAX_VALUE);
                if (autoAppendField != null) {
                    entityRow.put(autoAppendField.getCode().toLowerCase(), (Object)entityJcm);
                }
                entityInfo = new DataEntityInfo();
                entityInfo.setEntityKey(newZdmKey);
                entityInfo.setEntityExCode(zdmWithOutPeriod);
                entityInfo.setSingleZdm(zdm);
            }
            for (int j = 1; j < dbf.geDbfFields().length; ++j) {
                String tempFjdQYDM;
                RepeatEntityNode repeatFjdNode;
                String fjdZdm;
                String dbfFieldName = dbf.geDbfFields()[j].getFieldName();
                String dbFieldValue = dbfRow.getValueString(j);
                if (singleFieldMap.containsKey(dbfFieldName)) {
                    IEntityAttribute field = singleFieldMap.get(dbfFieldName);
                    if (dbfFieldName.equalsIgnoreCase(fMTable.getDWDMField()) && StringUtils.isNotEmpty((String)tempQYDM)) {
                        dbFieldValue = tempQYDM;
                    }
                    entityRow.put(field.getCode().toLowerCase(), (Object)dbFieldValue);
                    continue;
                }
                if (!dbfFieldName.equalsIgnoreCase("SYS_FJD") || !StringUtils.isNotEmpty((String)(fjdZdm = dbFieldValue))) continue;
                if (repeatNodes.containsKey(fjdZdm = fjdZdm.toUpperCase()) && (repeatFjdNode = (RepeatEntityNode)repeatNodes.get(fjdZdm)).getRepeatMode() == 2 && StringUtils.isNotEmpty((String)(tempFjdQYDM = repeatFjdNode.getTempQYDM())) && importContext.getEntityCache().getSingleQYDMIndex() >= 0) {
                    fjdZdm = importContext.getEntityCache().getNewZdmByQydm(fjdZdm, tempFjdQYDM);
                }
                String fjdZdmWithoutPeriod = fjdZdm;
                if (peirodLength > 0 && fjdZdm.length() == zmdLength) {
                    fjdZdmWithoutPeriod = fjdZdm.substring(0, periodIndex - 1) + fjdZdm.substring(periodIndex + peirodLength - 1, fjdZdm.length());
                }
                String jfdEntityJcm = this.getJcmbyZdm(importContext, fjdZdm, fjdZdmWithoutPeriod, dbfZdmFjdMap, autoCodeCoinfig);
                String fjdKey = null;
                String fjdCode = fjdZdmWithoutPeriod;
                String fjdEntityAutoExCode = fjdZdmWithoutPeriod + jfdEntityJcm;
                entityInfo = (DataEntityInfo)importContext.getEntityCache().getEntityAutoExCodeFinder().get(fjdEntityAutoExCode);
                if (StringUtils.isNotEmpty((String)fjdEntityAutoExCode) && entityInfo != null) {
                    fjdKey = entityInfo.getEntityKey();
                } else if (entityCodeKeyMap.containsKey(fjdZdmWithoutPeriod)) {
                    fjdKey = (String)entityCodeKeyMap.get(fjdZdmWithoutPeriod);
                } else if (entityZdmAndKeyMap.containsKey(fjdZdm)) {
                    fjdKey = (String)entityZdmAndKeyMap.get(fjdZdm);
                } else if (zdmWithoutPeriodTempMap.containsKey(fjdZdmWithoutPeriod)) {
                    fjdKey = (String)zdmWithoutPeriodTempMap.get(fjdZdmWithoutPeriod);
                    tempFJDRows.put(fjdKey, entityRow);
                } else if (zdmTempMap.containsKey(fjdZdm)) {
                    fjdKey = (String)zdmTempMap.get(fjdZdm);
                    tempFJDRows.put(fjdKey, entityRow);
                }
                if (StringUtils.isEmpty((String)fjdKey) && dbfZdmRowMap.containsKey(fjdZdm)) {
                    String fjdKeyId = UUID.randomUUID().toString();
                    fjdKey = fjdKeyId.toString();
                    zdmWithoutPeriodTempMap.put(fjdZdmWithoutPeriod, fjdKeyId);
                    zdmTempMap.put(fjdZdm, fjdKeyId);
                    tempFJDRows.put(fjdKeyId, entityRow);
                }
                if (null != fjdField && StringUtils.isNotEmpty((String)fjdKey)) {
                    entityRow.setParentcode(fjdCode);
                    continue;
                }
                if (StringUtils.isNotEmpty((String)fjdKey)) {
                    entityRow.setParentcode(fjdCode);
                    continue;
                }
                if (!StringUtils.isNotEmpty((String)fjdCode)) continue;
                entityRow.setParentcode(fjdCode);
            }
            if (isNewEntity) {
                String entityCode = "";
                if (codeField != null) {
                    entityCode = entityRow.getCode();
                }
                String entityTitle = "";
                if (titleField != null) {
                    entityTitle = entityRow.getShowTitle();
                }
                String entityRowCaption = "";
                String entityParentKey = "";
                if (fjdField != null) {
                    entityParentKey = entityRow.getParentcode();
                }
                if (null == entityInfo) {
                    entityInfo = new DataEntityInfo();
                }
                entityInfo.setEntityTitle(entityTitle);
                entityInfo.setEntityCode(entityCode);
                entityInfo.setEntityParentKey(entityParentKey);
                entityInfo.setEntityRowCaption(entityRowCaption);
                entityInfo.setIsUnitMap(false);
                entityInfo.setExpEntityExCode(zdmWithOutPeriod);
                entityInfo.setExpSingleZdm(zdm);
                entityInfo.setEntityAppendCode(entityJcm);
                importContext.getEntityCache().addEntity(entityInfo);
            }
            if (dbf.isHasLoadAllRec()) continue;
            dbf.clearDataRow(dbfRow);
        }
        if (null != fjdField) {
            for (Map.Entry entry : tempFJDRows.entrySet()) {
                String zdmKey = (String)entry.getKey();
                if (tempFJDZdms.containsKey(zdmKey)) continue;
                ((BaseDataDO)entry.getValue()).setParentcode(null);
                if (!importContext.getEntityCache().getEntityKeyFinder().containsKey(zdmKey)) continue;
                DataEntityInfo entityInfo2 = (DataEntityInfo)importContext.getEntityCache().getEntityKeyFinder().get(zdmKey);
                entityInfo2.setEntityParentKey("");
            }
        }
        if (jioRepeatResult.getEntityNodes().size() > 0) {
            importContext.setJioRepeatResult(jioRepeatResult);
        } else if (importContext.getDataOption().isUploadEntityData() && jioRepeatResult.getEntityNodes().size() == 0) {
            List list;
            logger.info("\u5bfc\u5165\u5b9e\u4f53\u6570\u636e\uff1a\u63d0\u4ea4\u4fdd\u5b58\u6570\u636e");
            R r = null;
            if (addBatchOptDTO.getDataList().size() > 0) {
                r = this.baseDataClient.batchAdd(addBatchOptDTO);
                logger.info("\u5b9e\u4f53\u6570\u636e\u6dfb\u52a0\uff1a" + r.getMsg() + "," + String.valueOf(addBatchOptDTO.getDataList().size()));
                list = (List)r.get((Object)"results");
                if (list != null) {
                    for (R r2 : list) {
                        if (r2.getCode() == 0) continue;
                        logger.info("\u5b9e\u4f53\u6570\u636e\u6dfb\u52a0\uff1a" + r2.getMsg() + " " + r2.get((Object)"BASEDATACODE").toString());
                    }
                }
            }
            if (updateBatchOptDTO.getDataList().size() > 0) {
                r = this.baseDataClient.batchUpdate(updateBatchOptDTO);
                logger.info("\u5b9e\u4f53\u6570\u636e\u66f4\u65b0\uff1a" + r.getMsg() + "," + String.valueOf(updateBatchOptDTO.getDataList().size()));
                list = (List)r.get((Object)"results");
                if (list != null) {
                    for (R r2 : list) {
                        if (r2.getCode() == 0) continue;
                        logger.info("\u5b9e\u4f53\u6570\u636e\u66f4\u65b0\uff1a" + r2.getMsg() + " " + r2.get((Object)"BASEDATACODE").toString());
                    }
                }
            }
            if (deleteBatchOptDTO.getDataList().size() > 0) {
                r = this.baseDataClient.batchRemove(deleteBatchOptDTO);
                logger.info("\u5b9e\u4f53\u6570\u636e\u5220\u9664\uff1a" + r.getMsg() + "," + String.valueOf(deleteBatchOptDTO.getDataList().size()));
            }
        }
    }

    private String getJcmbyZdm(TaskDataContext importContext, String zdm, String zdmWithOutPeriod, Map<String, String> dbfZdmFjdMap, AutoAppendCode autoCodeCoinfig) {
        String entityJcm = "";
        if (autoCodeCoinfig != null && autoCodeCoinfig.isAutoAppendCode()) {
            if (autoCodeCoinfig.getCodeMapping().containsKey(zdmWithOutPeriod)) {
                entityJcm = (String)autoCodeCoinfig.getCodeMapping().get(zdmWithOutPeriod);
            } else {
                String fjdZdm = dbfZdmFjdMap.get(zdm);
                if (StringUtils.isNotEmpty((String)fjdZdm)) {
                    entityJcm = this.getJcm(importContext, zdm, fjdZdm, dbfZdmFjdMap, autoCodeCoinfig);
                }
            }
        }
        return entityJcm;
    }

    private String getJcm(TaskDataContext importContext, String firstzdm, String zdm, Map<String, String> dbfZdmFjdMap, AutoAppendCode autoCodeCoinfig) {
        String jcm = "";
        if (!firstzdm.equalsIgnoreCase(zdm)) {
            String zmdOutPeriodCode = importContext.getEntityCache().getSingleZdmOutPeriodByZdm(zdm);
            if (autoCodeCoinfig.getCodeMapping().containsKey(zmdOutPeriodCode)) {
                jcm = (String)autoCodeCoinfig.getCodeMapping().get(zmdOutPeriodCode);
            } else {
                String fjdZdm = dbfZdmFjdMap.get(zdm);
                if (StringUtils.isNotEmpty((String)fjdZdm)) {
                    String fjdOutPeriodCode = importContext.getEntityCache().getSingleZdmOutPeriodByZdm(fjdZdm);
                    jcm = autoCodeCoinfig.getCodeMapping().containsKey(fjdOutPeriodCode) ? (String)autoCodeCoinfig.getCodeMapping().get(fjdOutPeriodCode) : this.getJcm(importContext, firstzdm, fjdZdm, dbfZdmFjdMap, autoCodeCoinfig);
                }
            }
        }
        return jcm;
    }

    @Override
    public String getType() {
        return "BASE";
    }
}

