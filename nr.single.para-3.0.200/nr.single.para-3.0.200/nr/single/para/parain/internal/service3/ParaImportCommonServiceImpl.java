/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.Consts$EntityField
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.jtable.params.output.MeasureData
 *  com.jiuqi.nr.single.core.para.ParaInfo
 *  com.jiuqi.nr.single.core.para.consts.ZBDataType
 *  com.jiuqi.nr.single.core.para.parser.eoums.DataInfo
 *  com.jiuqi.nr.single.core.para.parser.eoums.EnumInfo
 *  com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel
 *  com.jiuqi.nr.single.core.para.parser.table.FMRepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.FieldDefs
 *  com.jiuqi.nr.single.core.para.parser.table.RepInfo
 *  com.jiuqi.nr.single.core.para.parser.table.ReportTableType
 *  com.jiuqi.nr.single.core.para.parser.table.ZBInfo
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.dataengine.model.OrderByItem
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  nr.single.map.common.ImportConsts
 *  nr.single.map.data.facade.SingleFileEnumInfo
 *  nr.single.map.data.facade.SingleFileEnumItem
 */
package nr.single.para.parain.internal.service3;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.jtable.params.output.MeasureData;
import com.jiuqi.nr.single.core.para.ParaInfo;
import com.jiuqi.nr.single.core.para.consts.ZBDataType;
import com.jiuqi.nr.single.core.para.parser.eoums.DataInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumInfo;
import com.jiuqi.nr.single.core.para.parser.eoums.EnumsItemModel;
import com.jiuqi.nr.single.core.para.parser.table.FMRepInfo;
import com.jiuqi.nr.single.core.para.parser.table.FieldDefs;
import com.jiuqi.nr.single.core.para.parser.table.RepInfo;
import com.jiuqi.nr.single.core.para.parser.table.ReportTableType;
import com.jiuqi.nr.single.core.para.parser.table.ZBInfo;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.dataengine.model.OrderByItem;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.domain.datamodel.DataModelType;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import nr.single.map.common.ImportConsts;
import nr.single.map.data.facade.SingleFileEnumInfo;
import nr.single.map.data.facade.SingleFileEnumItem;
import nr.single.para.compare.definition.CompareDataEnumDTO;
import nr.single.para.compare.definition.CompareMapFieldDTO;
import nr.single.para.compare.definition.ISingleCompareDataEnumService;
import nr.single.para.compare.definition.ISingleCompareMapFieldService;
import nr.single.para.compare.definition.common.CompareDataType;
import nr.single.para.parain.internal.cache.FieldInfoDefine;
import nr.single.para.parain.internal.cache.MeasureUnitInfoCache;
import nr.single.para.parain.internal.cache.SchemeInfoCache;
import nr.single.para.parain.internal.cache.TableInfoDefine;
import nr.single.para.parain.internal.cache.TaskImportContext;
import nr.single.para.parain.service.IParaImportCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ParaImportCommonServiceImpl
implements IParaImportCommonService {
    private static final Logger log = LoggerFactory.getLogger(ParaImportCommonServiceImpl.class);
    @Autowired
    private IDesignTimeViewController viewController;
    @Autowired
    private IDataDefinitionDesignTimeController dataController;
    @Autowired
    private IDesignDataSchemeService dataSchemeService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private INvwaDataAccessProvider iNvwaDataAccessProvider;
    @Autowired
    private ISingleCompareMapFieldService mapFieldService;
    @Autowired
    private ISingleCompareDataEnumService enumDataService;

    @Override
    public void MakeTaskFieldsCache(TaskImportContext importContext) throws Exception {
        List<Object> schemes = null;
        if (importContext.getImportOption() != null && StringUtils.isNotEmpty((String)importContext.getImportOption().getHistoryFormSchemes())) {
            String[] ids;
            schemes = new ArrayList<DesignFormSchemeDefine>();
            for (String formSchemeKey : ids = importContext.getImportOption().getHistoryFormSchemes().split(";")) {
                DesignFormSchemeDefine formScheme = this.viewController.queryFormSchemeDefine(formSchemeKey);
                if (formScheme == null) continue;
                schemes.add(formScheme);
            }
            if (schemes.size() == 0) {
                schemes = this.viewController.queryFormSchemeByTask(importContext.getTaskKey());
            }
        } else {
            schemes = this.viewController.queryFormSchemeByTask(importContext.getTaskKey());
        }
        this.MakeTaskFieldsCacheByFormScheme(importContext.getSchemeInfoCache(), schemes);
    }

    @Override
    public void MakeTaskFieldsCache(TaskImportContext importContext, String taskKey) throws Exception {
        List schemes = this.viewController.queryFormSchemeByTask(taskKey);
        this.MakeTaskFieldsCacheByFormScheme(importContext.getSchemeInfoCache(), schemes);
    }

    private void MakeTaskFieldsCacheByFormScheme(SchemeInfoCache schemeInfoCache, List<DesignFormSchemeDefine> schemes) throws Exception {
        HashMap<String, String> dataShcemeDic = new HashMap<String, String>();
        ArrayList<String> dataSchemes = new ArrayList<String>();
        for (DesignFormSchemeDefine scheme : schemes) {
            DesignTaskDefine task = this.viewController.queryTaskDefine(scheme.getTaskKey());
            if (dataShcemeDic.containsKey(task.getDataScheme())) continue;
            dataShcemeDic.put(task.getDataScheme(), task.getDataScheme());
            dataSchemes.add(task.getDataScheme());
        }
        this.MakeTaskFieldsCacheByDataScheme(schemeInfoCache, dataSchemes);
    }

    @Override
    public void MakeTaskFieldsCacheByDataScheme(SchemeInfoCache schemeInfoCache, List<String> dataSchemes) throws Exception {
        Map<String, List<TableInfoDefine>> schemeTableCache = schemeInfoCache.getSchemeTableCache();
        Map<String, List<FieldInfoDefine>> schemeFieldCache = schemeInfoCache.getSchemeFieldCache();
        Map<String, List<FieldInfoDefine>> titleFieldCache = schemeInfoCache.getTitleFieldCache();
        Map<String, List<FieldInfoDefine>> codeFieldCache = schemeInfoCache.getCodeFieldCache();
        Map<String, List<FieldInfoDefine>> AliasFieldCache = schemeInfoCache.getAliasFieldCache();
        Map<String, List<FieldInfoDefine>> matchFieldCache = schemeInfoCache.getMatchTitleFieldCache();
        Map<String, TableInfoDefine> taskTableCache = schemeInfoCache.getTaskTableCache();
        Map<String, List<FieldInfoDefine>> tableFieldCache = schemeInfoCache.getTableFieldCache();
        for (String dataSchemeKey : dataSchemes) {
            ArrayList<FieldInfoDefine> schemeFields = new ArrayList<FieldInfoDefine>();
            List<Object> titleFields = null;
            List<Object> codeFields = null;
            List<Object> AliasFields = null;
            List<Object> matchFields = null;
            DesignDataScheme dataScheme = this.dataSchemeService.getDataScheme(dataSchemeKey);
            ArrayList tables = this.dataSchemeService.getAllDataTable(dataSchemeKey);
            if (tables == null) {
                tables = new ArrayList();
            }
            HashMap<String, CompareMapFieldDTO> matchFieldDic = new HashMap<String, CompareMapFieldDTO>();
            CompareMapFieldDTO mapFieldParam = new CompareMapFieldDTO();
            List<CompareMapFieldDTO> matchFieldList = null;
            mapFieldParam.setDataSchemeKey(dataSchemeKey);
            matchFieldList = this.mapFieldService.list(mapFieldParam);
            for (CompareMapFieldDTO data : matchFieldList) {
                matchFieldDic.put(data.getFieldKey(), data);
            }
            ArrayList<TableInfoDefine> newTables = new ArrayList<TableInfoDefine>();
            for (DesignDataTable table : tables) {
                if (taskTableCache.containsKey(table.getKey())) continue;
                newTables.add(new TableInfoDefine(table));
                taskTableCache.put(table.getKey(), new TableInfoDefine(table));
                List fields = this.dataSchemeService.getDataFieldByTable(table.getKey());
                if (fields == null) continue;
                tableFieldCache.put(table.getKey(), FieldInfoDefine.getFieldInfos2(fields));
                schemeFields.addAll(FieldInfoDefine.getFieldInfos2(fields));
                for (DesignDataField field : fields) {
                    FieldInfoDefine fieldInfo = new FieldInfoDefine(field);
                    schemeInfoCache.getIdFieldCache().put(fieldInfo.getKey(), fieldInfo);
                    if (titleFieldCache.containsKey(field.getTitle())) {
                        titleFields = titleFieldCache.get(field.getTitle());
                    } else {
                        titleFields = new ArrayList();
                        titleFieldCache.put(field.getTitle(), titleFields);
                    }
                    titleFields.add(fieldInfo);
                    if (codeFieldCache.containsKey(field.getCode())) {
                        codeFields = codeFieldCache.get(field.getCode());
                    } else {
                        codeFields = new ArrayList();
                        codeFieldCache.put(field.getCode(), codeFields);
                    }
                    codeFields.add(fieldInfo);
                    String fieldAlias = field.getAlias();
                    if (StringUtils.isEmpty((String)fieldAlias)) {
                        fieldAlias = field.getCode();
                        String tableName = table.getCode();
                        if (StringUtils.isNotEmpty((String)fieldAlias) && StringUtils.isNotEmpty((String)tableName)) {
                            int idPos = fieldAlias.indexOf(tableName);
                            if (idPos >= 0) {
                                fieldAlias = fieldAlias.substring(idPos + tableName.length(), fieldAlias.length());
                            } else {
                                String formCode;
                                String dataPrefix = dataScheme.getPrefix() + "_";
                                if (tableName.startsWith(dataPrefix) && fieldAlias.startsWith(formCode = tableName.substring(dataPrefix.length(), tableName.length()) + "_")) {
                                    fieldAlias = fieldAlias.substring(formCode.length(), fieldAlias.length());
                                }
                            }
                        }
                    }
                    if (AliasFieldCache.containsKey(fieldAlias)) {
                        AliasFields = AliasFieldCache.get(fieldAlias);
                    } else {
                        AliasFields = new ArrayList();
                        AliasFieldCache.put(fieldAlias, AliasFields);
                    }
                    AliasFields.add(fieldInfo);
                    if (!matchFieldDic.containsKey(field.getKey())) continue;
                    CompareMapFieldDTO data = (CompareMapFieldDTO)matchFieldDic.get(field.getKey());
                    if (matchFieldCache.containsKey(data.getMatchTitle())) {
                        matchFields = matchFieldCache.get(data.getMatchTitle());
                    } else {
                        matchFields = new ArrayList();
                        matchFieldCache.put(data.getMatchTitle(), matchFields);
                    }
                    matchFields.add(fieldInfo);
                }
            }
            schemeTableCache.put(dataSchemeKey, newTables);
            schemeFieldCache.put(dataSchemeKey, schemeFields);
        }
    }

    @Override
    public String[] getBizKeyFieldsID(String bizKeyFieldsID) {
        if (StringUtils.isEmpty((String)bizKeyFieldsID)) {
            return null;
        }
        String[] idArray = bizKeyFieldsID.split(";");
        String[] ids = new String[idArray.length];
        for (int i = 0; i < idArray.length; ++i) {
            ids[i] = idArray[i];
        }
        return ids;
    }

    @Override
    public void doMeasureUnitMap(TaskImportContext importContext) {
        DesignFormSchemeDefine formScheme = importContext.getSchemeInfoCache().getFormScheme();
        String measureUnit = formScheme.getMeasureUnit();
        if (StringUtils.isEmpty((String)measureUnit)) {
            DesignTaskDefine task = importContext.getTaskDefine();
            measureUnit = task.getMeasureUnit();
        }
        if (StringUtils.isNotEmpty((String)measureUnit)) {
            String[] units = measureUnit.split(";");
            if (units.length < 2) {
                return;
            }
            String tableKey = units[0];
            String unitCode = units[1];
            try {
                importContext.getSchemeInfoCache().setMeasureUnit(measureUnit);
                importContext.getSchemeInfoCache().setMeasureUnitCode(unitCode);
                importContext.getSchemeInfoCache().setMeasureUnitTableKey(tableKey);
                this.queryMeasureUnitMap(importContext, tableKey);
            }
            catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

    private void queryMeasureUnitMap(TaskImportContext importContext, String tableKey) throws Exception {
        List<MeasureData> measureDatas = this.queryMeasureData(tableKey);
        for (MeasureData entityRow : measureDatas) {
            String code = entityRow.getCode();
            String title = entityRow.getTitle();
            MeasureUnitInfoCache measureUnit = new MeasureUnitInfoCache();
            measureUnit.setCode(code);
            measureUnit.setTilte(title);
            importContext.getSchemeInfoCache().getMeasureCahce().put(title, measureUnit);
        }
    }

    private List<MeasureData> queryMeasureData(String measureTableKey) {
        ArrayList<MeasureData> measureDatas = new ArrayList<MeasureData>();
        if (StringUtils.isEmpty((String)measureTableKey)) {
            return measureDatas;
        }
        NvwaQueryModel queryModel = new NvwaQueryModel();
        TableModelDefine table = this.dataModelService.getTableModelDefineById(measureTableKey);
        List allColumns = this.dataModelService.getColumnModelDefinesByTable(table.getID());
        allColumns = allColumns.stream().sorted((a, b) -> Double.compare(a.getOrder(), b.getOrder())).collect(Collectors.toList());
        for (ColumnModelDefine column : allColumns) {
            if (column.getCode().equalsIgnoreCase("MN_ORDERL")) {
                queryModel.getOrderByItems().add(new OrderByItem(column));
            }
            queryModel.getColumns().add(new NvwaQueryColumn(column));
        }
        try {
            INvwaDataAccess dataAccess = this.iNvwaDataAccessProvider.createReadOnlyDataAccess(queryModel);
            DataAccessContext context = new DataAccessContext(this.dataModelService);
            MemoryDataSet result = dataAccess.executeQuery(context);
            for (DataRow dataRow : result) {
                MeasureData measureData = this.getMeasureData(dataRow);
                if (measureData == null) continue;
                measureDatas.add(measureData);
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return measureDatas;
    }

    private MeasureData getMeasureData(DataRow row) {
        if (row == null || row.getString(3).equals("0")) {
            return null;
        }
        MeasureData measureData = new MeasureData();
        measureData.setId(row.getString(0));
        measureData.setCode(row.getString(1));
        measureData.setTitle(row.getString(2));
        measureData.setRate(row.getString(4) != null ? new BigDecimal(row.getString(4)).doubleValue() : new BigDecimal(0).doubleValue());
        measureData.setBase(row.getInt(5) != 0);
        return measureData;
    }

    @Override
    public int getTaskPeriodCount(TaskImportContext importContext) {
        String taskType;
        int aCount = 0;
        PeriodType atype = PeriodType.CUSTOM;
        ParaInfo para = importContext.getParaInfo();
        switch (taskType = para.getTaskType()) {
            case "N": {
                atype = PeriodType.YEAR;
                aCount = 1;
                break;
            }
            case "H": {
                atype = PeriodType.HALFYEAR;
                aCount = 2;
                break;
            }
            case "J": {
                atype = PeriodType.SEASON;
                aCount = 4;
                break;
            }
            case "Y": {
                atype = PeriodType.MONTH;
                aCount = 12;
                break;
            }
            case "X": {
                atype = PeriodType.TENDAY;
                aCount = 36;
                break;
            }
            case "Z": {
                atype = PeriodType.WEEK;
                aCount = 54;
                break;
            }
            case "R": {
                atype = PeriodType.DAY;
                aCount = 366;
                break;
            }
            case "B": {
                atype = PeriodType.CUSTOM;
                aCount = 0;
            }
        }
        return aCount;
    }

    @Override
    public EnumsItemModel getPeriodEnum(TaskImportContext importContext, EnumsItemModel oldenum) {
        PeriodType netPeriodType = PeriodType.MONTH;
        boolean isMap = false;
        if (null != oldenum) {
            isMap = this.getPeriodEnumMap(importContext, oldenum);
            netPeriodType = this.getNetPeriodTypeFromScheme(importContext);
        }
        EnumsItemModel singleEnum = this.getPeriodEnumByPeriodType(importContext, oldenum, netPeriodType, isMap);
        return singleEnum;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public EnumsItemModel getPeriodEnumByPeriodType(TaskImportContext importContext, EnumsItemModel oldenum, PeriodType netPeriodType, boolean isMap) {
        block18: {
            block24: {
                block23: {
                    block22: {
                        block21: {
                            block20: {
                                block19: {
                                    block15: {
                                        singleEnum = new EnumsItemModel();
                                        para = importContext.getParaInfo();
                                        taskType = para.getTaskType();
                                        allZBs = para.getFmRepInfo().getDefs().getAllZbInfosPair();
                                        periodFieldLen = 0;
                                        if (StringUtils.isNotEmpty((String)para.getFmRepInfo().getSQField()) && allZBs.containsKey(para.getFmRepInfo().getSQField())) {
                                            periodField = (ZBInfo)allZBs.get(para.getFmRepInfo().getSQField());
                                            periodFieldLen = periodField.getLength();
                                        }
                                        netPeriodTypeCode = this.getPeriodCode(netPeriodType);
                                        netPeriodTypeName = this.GetPeriodTypeName(netPeriodType);
                                        item = null;
                                        if (null == oldenum) break block15;
                                        i = 1;
                                        if (isMap) {
                                            taskType = netPeriodTypeCode;
                                        }
                                        decimalFormat = new DecimalFormat("0000");
                                        periodIndx = 0;
                                        for (DataInfo oldItem : oldenum.getEnumItemList().values()) {
                                            block17: {
                                                block16: {
                                                    acode = oldItem.getCode();
                                                    ++periodIndx;
                                                    mapCode = acode;
                                                    aOldCode = acode;
                                                    if (!isMap) break block16;
                                                    if (netPeriodType == PeriodType.CUSTOM) break block16;
                                                    mapCode = this.getPeriodMapCode(netPeriodType, taskType, para.getTaskYear(), acode, oldItem.getName());
                                                    if (oldenum.getCodeLen() < periodFieldLen) {
                                                        aOldCode = String.valueOf(periodIndx);
                                                    }
                                                    break block17;
                                                }
                                                if (isMap) ** GOTO lbl-1000
                                                if (netPeriodType != PeriodType.CUSTOM) {
                                                    mapCode = this.getPeriodCodeByType(acode, oldItem.getName(), netPeriodTypeName, netPeriodTypeCode);
                                                } else if (netPeriodType == PeriodType.CUSTOM) {
                                                    numFormatStr = decimalFormat.format(i);
                                                    mapCode = para.getTaskYear() + "B" + numFormatStr;
                                                }
                                            }
                                            if (periodFieldLen > 0 && aOldCode.length() < periodFieldLen) {
                                                aOldCode = "000000000000000".substring(0, periodFieldLen - aOldCode.length()) + aOldCode;
                                            }
                                            item = this.getEnumData(mapCode, oldItem.getName(), "", aOldCode, i, singleEnum);
                                            ++i;
                                        }
                                        break block18;
                                    }
                                    if (!taskType.equals("N")) break block19;
                                    code1 = this.getPeriodCodeByLen("0001", periodFieldLen);
                                    item = this.getEnumData(para.getTaskYear() + "N0001", para.getTaskYear() + "\u5e74\u5ea6", "", code1, 1, singleEnum);
                                    break block18;
                                }
                                if (!taskType.equals("H")) break block20;
                                code1 = this.getPeriodCodeByLen("0001", periodFieldLen);
                                code2 = this.getPeriodCodeByLen("0002", periodFieldLen);
                                item = this.getEnumData(para.getTaskYear() + "H0001", para.getTaskYear() + "\u5e74\u4e0a\u534a\u5e74", "", code1, 1, singleEnum);
                                item = this.getEnumData(para.getTaskYear() + "H0002", para.getTaskYear() + "\u5e74\u4e0a\u534a\u5e74", "", code2, 2, singleEnum);
                                break block18;
                            }
                            if (!taskType.equals("J")) break block21;
                            code1 = this.getPeriodCodeByLen("0001", periodFieldLen);
                            code2 = this.getPeriodCodeByLen("0002", periodFieldLen);
                            code3 = this.getPeriodCodeByLen("0003", periodFieldLen);
                            code4 = this.getPeriodCodeByLen("0004", periodFieldLen);
                            item = this.getEnumData(para.getTaskYear() + "J0001", "\u7b2c\u4e00\u5b63\u5ea6", "", code1, 1, singleEnum);
                            item = this.getEnumData(para.getTaskYear() + "J0002", "\u7b2c\u4e8c\u5b63\u5ea6", "", code2, 2, singleEnum);
                            item = this.getEnumData(para.getTaskYear() + "J0003", "\u7b2c\u4e09\u5b63\u5ea6", "", code3, 3, singleEnum);
                            item = this.getEnumData(para.getTaskYear() + "J0004", "\u7b2c\u56db\u5b63\u5ea6", "", code4, 4, singleEnum);
                            break block18;
                        }
                        if (!taskType.equals("Y")) break block22;
                        for (i = 1; i <= 12; ++i) {
                            acode = "00" + String.valueOf(i);
                            if (acode.length() < 4) {
                                acode = "0" + acode;
                            }
                            acode1 = this.getPeriodCodeByLen(acode, periodFieldLen);
                            item = this.getEnumData(para.getTaskYear() + "Y" + acode, "\u7b2c" + i + "\u6708", "", acode1, i, singleEnum);
                        }
                        break block18;
                    }
                    if (!taskType.equals("X")) break block23;
                    for (i = 1; i <= 36; ++i) {
                        acode = "00" + String.valueOf(i);
                        if (acode.length() < 4) {
                            acode = "0" + acode;
                        }
                        acode1 = this.getPeriodCodeByLen(acode, periodFieldLen);
                        item = this.getEnumData(para.getTaskYear() + "X" + acode, "\u7b2c" + i + "\u65ec", "", acode1, i, singleEnum);
                    }
                    break block18;
                }
                if (!taskType.equals("Z")) break block24;
                for (i = 1; i <= 36; ++i) {
                    acode = "00" + String.valueOf(i);
                    if (acode.length() < 4) {
                        acode = "0" + acode;
                    }
                    acode1 = this.getPeriodCodeByLen(acode, periodFieldLen);
                    item = this.getEnumData(para.getTaskYear() + "Z" + acode, "\u7b2c" + i + "\u5468", "", acode1, i, singleEnum);
                }
                break block18;
            }
            if (!taskType.equals("R")) break block18;
            for (i = 1; i <= 366; ++i) {
                acode = String.valueOf(i);
                acode = "0000".substring(0, 4 - acode.length() - 1) + acode;
                acode1 = this.getPeriodCodeByLen(acode, periodFieldLen);
                item = this.getEnumData(para.getTaskYear() + "R" + acode, "\u7b2c" + i + "\u5929", "", acode1, i, singleEnum);
            }
        }
        return singleEnum;
    }

    private String getPeriodCodeByType(String periodCode, String periodTilte, String PeriodTypeName, String PeriodTypeCode) {
        String code = periodCode;
        try {
            String pattern = "(\\d+)\u5e74(\\d+)" + PeriodTypeName + "(.*)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(periodTilte);
            if (m.find()) {
                String year = m.group(1);
                String p1 = m.group(2);
                if (year.length() == 2) {
                    year = "20" + year;
                }
                if (p1.length() < 4) {
                    p1 = "00000".substring(0, 4 - p1.length()) + p1;
                } else if (p1.length() > 4) {
                    p1 = p1.substring(p1.length() - 4, p1.length());
                }
                code = year + PeriodTypeCode + p1;
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return code;
    }

    private String getPeriodCodeByLen(String oldCode, int len) {
        String code = oldCode;
        if (len > 0 && StringUtils.isNotEmpty((String)code)) {
            if (code.length() < len) {
                code = "000000000000000".substring(0, len - code.length()) + code;
            } else if (code.length() > len) {
                code = code.substring(code.length() - len, code.length());
            }
        }
        return code;
    }

    private String getPeriodMapCode(PeriodType periodType, String taskType, String taskYear, String enumCode, String enumTitle) {
        boolean isHasYear;
        String mapCode = enumCode;
        boolean bl = isHasYear = enumTitle.indexOf("\u5e74") >= 0;
        if (isHasYear) {
            String aYear = taskYear;
            String aSq = enumCode;
            if (periodType == PeriodType.YEAR) {
                aYear = aSq;
                aSq = "0001";
            } else if (enumCode.length() == 4) {
                aYear = "20" + enumCode.substring(0, 2);
                aSq = "00" + enumCode.substring(2, 4);
            } else if (enumCode.length() == 6) {
                String aYearHead = enumCode.substring(0, 2);
                if (aYearHead.equals("19") || aYearHead.equals("20") || aYearHead.equals("21")) {
                    aYear = "" + enumCode.substring(0, 4);
                    aSq = "00" + enumCode.substring(4, 6);
                } else {
                    aYear = "20" + enumCode.substring(0, 2);
                    aSq = "" + enumCode.substring(2, 6);
                }
            } else if (enumCode.length() == 8) {
                String aYearHead = enumCode.substring(0, 2);
                if (aYearHead.equals("19") || aYearHead.equals("20") || aYearHead.equals("21")) {
                    aYear = "" + enumCode.substring(0, 4);
                    aSq = "" + enumCode.substring(4, 8);
                } else {
                    aYear = "20" + enumCode.substring(0, 2);
                    aSq = "" + enumCode.substring(2, 8);
                }
            }
            if (aSq.length() < 4) {
                aSq = "00000".substring(0, 4 - aSq.length()) + aSq;
            } else if (aSq.length() > 4) {
                aSq = aSq.substring(0, 4);
            }
            mapCode = aYear + taskType + aSq;
        } else {
            String aYear = taskYear;
            String aSq = enumCode;
            if (enumCode.length() < 4) {
                aSq = "00000".substring(0, 4 - enumCode.length()) + enumCode;
            } else if (enumCode.length() == 4) {
                String aYearHead = enumCode.substring(0, 2);
                if (aYearHead.equals("19") || aYearHead.equals("20") || aYearHead.equals("21")) {
                    aYear = "" + enumCode.substring(0, 2);
                    aSq = "00" + enumCode.substring(2, 4);
                } else {
                    aSq = enumCode.substring(0, 4);
                }
            } else if (enumCode.length() == 6) {
                String aYearHead = enumCode.substring(0, 2);
                if ("0000".equalsIgnoreCase(enumCode)) {
                    aSq = enumCode.substring(2, 6);
                } else if (aYearHead.equals("19") || aYearHead.equals("20") || aYearHead.equals("21")) {
                    aYear = "" + enumCode.substring(0, 4);
                    aSq = "00" + enumCode.substring(4, 6);
                } else {
                    aYear = "20" + enumCode.substring(0, 2);
                    aSq = "" + enumCode.substring(2, 6);
                }
            } else if (enumCode.length() == 8) {
                String aYearHead = enumCode.substring(0, 2);
                if (aYearHead.equals("19") || aYearHead.equals("20") || aYearHead.equals("21")) {
                    aYear = "" + enumCode.substring(0, 4);
                    aSq = "" + enumCode.substring(4, 8);
                } else {
                    aYear = "20" + enumCode.substring(0, 2);
                    aSq = "" + enumCode.substring(4, 8);
                }
            } else {
                aSq = enumCode.substring(enumCode.length() - 4, enumCode.length());
            }
            if (aSq.length() < 4) {
                aSq = "00000".substring(0, 4 - aSq.length()) + aSq;
            } else if (aSq.length() > 4) {
                aSq = aSq.substring(0, 4);
            }
            mapCode = aYear + taskType + aSq;
        }
        return mapCode;
    }

    private boolean getPeriodEnumMap(TaskImportContext importContext, EnumsItemModel oldenum) {
        PeriodType mapPeriodType;
        boolean isMap = false;
        ParaInfo para = importContext.getParaInfo();
        String taskType = para.getTaskType();
        PeriodType periodType = this.getNetPeriodTypeFromScheme(importContext);
        boolean bl = isMap = periodType == (mapPeriodType = this.getTaskPeriod(importContext));
        if (taskType.equals("N")) {
            isMap = periodType == PeriodType.YEAR;
        } else if (taskType.equals("H")) {
            isMap = periodType == PeriodType.HALFYEAR;
        } else if (taskType.equals("J")) {
            isMap = periodType == PeriodType.SEASON;
        } else if (taskType.equals("Y")) {
            isMap = periodType == PeriodType.MONTH;
        } else if (taskType.equals("X")) {
            isMap = periodType == PeriodType.TENDAY;
        } else if (taskType.equals("Z")) {
            isMap = periodType == PeriodType.WEEK;
        } else if (taskType.equals("R")) {
            isMap = periodType == PeriodType.DAY;
        } else if (taskType.equals("B")) {
            isMap = periodType == PeriodType.CUSTOM;
        }
        return isMap;
    }

    private PeriodType getNetPeriodTypeFromScheme(TaskImportContext importContext) {
        DesignTaskDefine task;
        DesignFormSchemeDefine formScheme = importContext.getSchemeInfoCache().getFormScheme();
        PeriodType periodType = formScheme.getPeriodType();
        if (StringUtils.isEmpty((String)formScheme.getMasterEntitiesKey()) && (task = importContext.getTaskDefine()) != null) {
            periodType = task.getPeriodType();
        }
        return periodType;
    }

    private DataInfo getEnumData(String code, String title, String pCode, String mapCode, int row, EnumsItemModel singleEnum) {
        DataInfo item = new DataInfo();
        item.setCode(code);
        item.setName(title);
        item.setParent(pCode);
        item.setRowNum(row);
        item.setMapCode(mapCode);
        if (null != singleEnum) {
            singleEnum.getEnumItemList().put(item.getCode(), item);
            singleEnum.getItemDataList().add(item);
        }
        return item;
    }

    private String getPeriodCode(PeriodType periodType) {
        String aCode = "";
        if (periodType == PeriodType.YEAR) {
            aCode = "N";
        } else if (periodType == PeriodType.HALFYEAR) {
            aCode = "H";
        } else if (periodType == PeriodType.SEASON) {
            aCode = "J";
        } else if (periodType == PeriodType.MONTH) {
            aCode = "Y";
        } else if (periodType == PeriodType.TENDAY) {
            aCode = "X";
        } else if (periodType == PeriodType.WEEK) {
            aCode = "Z";
        } else if (periodType == PeriodType.DAY) {
            aCode = "R";
        } else if (periodType == PeriodType.CUSTOM) {
            aCode = "B";
        }
        return aCode;
    }

    @Override
    public PeriodType getTaskPeriod(TaskImportContext importContext) {
        ParaInfo para = importContext.getParaInfo();
        String taskType = para.getTaskType();
        return this.getTaskPeriod(taskType);
    }

    @Override
    public PeriodType getTaskPeriod(String taskType) {
        PeriodType atype = PeriodType.CUSTOM;
        switch (taskType) {
            case "N": {
                atype = PeriodType.YEAR;
                break;
            }
            case "H": {
                atype = PeriodType.HALFYEAR;
                break;
            }
            case "J": {
                atype = PeriodType.SEASON;
                break;
            }
            case "Y": {
                atype = PeriodType.MONTH;
                break;
            }
            case "X": {
                atype = PeriodType.TENDAY;
                break;
            }
            case "Z": {
                atype = PeriodType.WEEK;
                break;
            }
            case "R": {
                atype = PeriodType.DAY;
                break;
            }
            case "B": {
                atype = PeriodType.CUSTOM;
            }
        }
        return atype;
    }

    @Override
    public String getPeriodTypeCode(PeriodType type) {
        String code = "N";
        if (PeriodType.YEAR == type) {
            code = "N";
        } else if (PeriodType.HALFYEAR == type) {
            code = "H";
        } else if (PeriodType.SEASON == type) {
            code = "J";
        } else if (PeriodType.MONTH == type) {
            code = "Y";
        } else if (PeriodType.TENDAY == type) {
            code = "X";
        } else if (PeriodType.WEEK == type) {
            code = "Z";
        } else if (PeriodType.DAY == type) {
            code = "R";
        } else if (PeriodType.CUSTOM == type) {
            code = "B";
        }
        return code;
    }

    @Override
    public String getLasPeriodCodeType(String taskYear, PeriodType type) {
        int periodCount = this.getMaxPeriodCount(type);
        String toPeriodCode = String.valueOf(periodCount);
        if (toPeriodCode.length() < 4) {
            toPeriodCode = "000000".substring(0, 4 - toPeriodCode.length()) + toPeriodCode;
        } else if (toPeriodCode.length() > 4) {
            toPeriodCode = toPeriodCode.substring(toPeriodCode.length() - 4, toPeriodCode.length() - 1);
        }
        return taskYear + this.getPeriodTypeCode(type) + toPeriodCode;
    }

    private int getMaxPeriodCount(PeriodType type) {
        int aCount = 0;
        if (PeriodType.YEAR == type) {
            aCount = 1;
        } else if (PeriodType.HALFYEAR == type) {
            aCount = 2;
        } else if (PeriodType.SEASON == type) {
            aCount = 4;
        } else if (PeriodType.MONTH == type) {
            aCount = 12;
        } else if (PeriodType.TENDAY == type) {
            aCount = 36;
        } else if (PeriodType.WEEK == type) {
            aCount = 54;
        } else if (PeriodType.DAY == type) {
            aCount = 366;
        } else if (PeriodType.CUSTOM == type) {
            aCount = 0;
        }
        return aCount;
    }

    public String GetPeriodTypeName(PeriodType type) {
        String code = "\u5e74";
        if (PeriodType.YEAR == type) {
            code = "\u5e74";
        } else if (PeriodType.HALFYEAR == type) {
            code = "\u534a\u5e74";
        } else if (PeriodType.SEASON == type) {
            code = "\u5b63";
        } else if (PeriodType.MONTH == type) {
            code = "\u6708";
        } else if (PeriodType.TENDAY == type) {
            code = "\u65ec";
        } else if (PeriodType.WEEK == type) {
            code = "\u5468";
        } else if (PeriodType.DAY == type) {
            code = "\u65e5";
        } else if (PeriodType.CUSTOM == type) {
            code = "\u4e0d\u5b9a\u671f";
        }
        return code;
    }

    @Override
    public FieldType getFieldType(ZBDataType zbtype) {
        FieldType atype = FieldType.FIELD_TYPE_STRING;
        int value = zbtype.getValue();
        switch (value) {
            case 1: {
                atype = FieldType.FIELD_TYPE_STRING;
                break;
            }
            case 2: {
                atype = FieldType.FIELD_TYPE_INTEGER;
                break;
            }
            case 3: {
                atype = FieldType.FIELD_TYPE_DECIMAL;
                break;
            }
            case 4: {
                atype = FieldType.FIELD_TYPE_DATE;
                break;
            }
            case 5: {
                atype = FieldType.FIELD_TYPE_TEXT;
                break;
            }
            case 6: {
                atype = FieldType.FIELD_TYPE_FILE;
                break;
            }
            case 7: {
                atype = FieldType.FIELD_TYPE_DECIMAL;
                break;
            }
            case 8: {
                atype = FieldType.FIELD_TYPE_LOGIC;
                break;
            }
            case 9: {
                atype = FieldType.FIELD_TYPE_INTEGER;
                break;
            }
            case 17: {
                atype = FieldType.FIELD_TYPE_PICTURE;
                break;
            }
            case 39: {
                atype = FieldType.FIELD_TYPE_UUID;
            }
        }
        return atype;
    }

    @Override
    public DataFieldType getDataFieldType(ZBDataType zbtype) {
        DataFieldType atype = DataFieldType.STRING;
        int value = zbtype.getValue();
        switch (value) {
            case 1: {
                atype = DataFieldType.STRING;
                break;
            }
            case 2: {
                atype = DataFieldType.BIGDECIMAL;
                break;
            }
            case 3: {
                atype = DataFieldType.BIGDECIMAL;
                break;
            }
            case 4: {
                atype = DataFieldType.DATE;
                break;
            }
            case 5: {
                atype = DataFieldType.CLOB;
                break;
            }
            case 6: {
                atype = DataFieldType.FILE;
                break;
            }
            case 7: {
                atype = DataFieldType.BIGDECIMAL;
                break;
            }
            case 8: {
                atype = DataFieldType.BOOLEAN;
                break;
            }
            case 9: {
                atype = DataFieldType.BIGDECIMAL;
                break;
            }
            case 17: {
                atype = DataFieldType.PICTURE;
                break;
            }
            case 39: {
                atype = DataFieldType.UUID;
            }
        }
        return atype;
    }

    @Override
    public DataModelType.ColumnType getColumnType(ZBDataType zbtype) {
        DataModelType.ColumnType atype = DataModelType.ColumnType.NVARCHAR;
        int value = zbtype.getValue();
        switch (value) {
            case 1: {
                atype = DataModelType.ColumnType.NVARCHAR;
                break;
            }
            case 2: {
                atype = DataModelType.ColumnType.INTEGER;
                break;
            }
            case 3: {
                atype = DataModelType.ColumnType.NUMERIC;
                break;
            }
            case 4: {
                atype = DataModelType.ColumnType.DATE;
                break;
            }
            case 5: {
                atype = DataModelType.ColumnType.CLOB;
                break;
            }
            case 6: {
                atype = DataModelType.ColumnType.CLOB;
                break;
            }
            case 7: {
                atype = DataModelType.ColumnType.NUMERIC;
                break;
            }
            case 8: {
                atype = DataModelType.ColumnType.INTEGER;
                break;
            }
            case 9: {
                atype = DataModelType.ColumnType.INTEGER;
                break;
            }
            case 17: {
                atype = DataModelType.ColumnType.CLOB;
                break;
            }
            case 39: {
                atype = DataModelType.ColumnType.UUID;
            }
        }
        return atype;
    }

    @Override
    public int getDataModelType(ZBDataType zbtype) {
        int atype = 2;
        int value = zbtype.getValue();
        switch (value) {
            case 1: {
                atype = 2;
                break;
            }
            case 2: {
                atype = 3;
                break;
            }
            case 3: {
                atype = 4;
                break;
            }
            case 4: {
                atype = 5;
                break;
            }
            case 5: {
                atype = 7;
                break;
            }
            case 6: {
                atype = 7;
                break;
            }
            case 7: {
                atype = 4;
                break;
            }
            case 8: {
                atype = 3;
                break;
            }
            case 9: {
                atype = 3;
                break;
            }
            case 17: {
                atype = 7;
                break;
            }
            case 39: {
                atype = 1;
            }
        }
        return atype;
    }

    @Override
    public FieldType getFieldTypeByColumType(ColumnModelType zbType) {
        FieldType atype = FieldType.FIELD_TYPE_STRING;
        if (zbType == ColumnModelType.STRING) {
            atype = FieldType.FIELD_TYPE_STRING;
        } else if (zbType == ColumnModelType.INTEGER) {
            atype = FieldType.FIELD_TYPE_INTEGER;
        } else if (zbType == ColumnModelType.BIGDECIMAL) {
            atype = FieldType.FIELD_TYPE_DECIMAL;
        } else if (zbType == ColumnModelType.DATETIME) {
            atype = FieldType.FIELD_TYPE_DATE;
        } else if (zbType == ColumnModelType.CLOB) {
            atype = FieldType.FIELD_TYPE_TEXT;
        } else if (zbType == ColumnModelType.BLOB) {
            atype = FieldType.FIELD_TYPE_FILE;
        } else if (zbType == ColumnModelType.BOOLEAN) {
            atype = FieldType.FIELD_TYPE_LOGIC;
        } else if (zbType == ColumnModelType.UUID) {
            atype = FieldType.FIELD_TYPE_UUID;
        }
        return atype;
    }

    @Override
    public FormType getFormTypeFromSingle(ReportTableType aSingleType, boolean isFMDM) {
        FormType atype = FormType.FORM_TYPE_FIX;
        switch (aSingleType) {
            case RTT_BLOBTABLE: {
                atype = FormType.FORM_TYPE_FIX;
                break;
            }
            case RTT_WORDTABLE: {
                atype = FormType.FORM_TYPE_FIX;
                break;
            }
            case RTT_FIXTABLE: {
                atype = FormType.FORM_TYPE_FIX;
                break;
            }
            case RTT_ROWFLOATTABLE: {
                atype = FormType.FORM_TYPE_FLOAT;
                break;
            }
            case RTT_COLFLOATTALBE: {
                atype = FormType.FORM_TYPE_FLOAT;
            }
        }
        if (isFMDM) {
            atype = FormType.FORM_TYPE_NEWFMDM;
        }
        return atype;
    }

    @Override
    public boolean getFMDMIsData(TaskImportContext importContext) {
        boolean fmdmIsData = false;
        DesignDataScheme dataScheme = importContext.getDataScheme();
        if (dataScheme != null && null != importContext.getEntityTable()) {
            String oldTableCode;
            String tableCode = importContext.getEntityTable().getCode();
            String fileFlag = "";
            if (StringUtils.isNotEmpty((String)dataScheme.getPrefix())) {
                fileFlag = "_" + dataScheme.getPrefix().toUpperCase();
            }
            if (!(oldTableCode = String.format("MD%s_%s", fileFlag, "FMDM")).equalsIgnoreCase(tableCode)) {
                fmdmIsData = true;
            }
        }
        return fmdmIsData;
    }

    @Override
    public String UpdatePeriodEntity(TaskImportContext importContext) throws Exception {
        ZBInfo zb;
        ParaInfo para = importContext.getParaInfo();
        DesignFormSchemeDefine formScheme = importContext.getSchemeInfoCache().getFormScheme();
        DesignDataScheme dataScheme = importContext.getDataScheme();
        PeriodType periodType = this.getTaskPeriod(importContext);
        int periodCount = this.getTaskPeriodCount(importContext);
        int sqid = -1;
        if (para.getFmRepInfo() != null) {
            sqid = para.getFmRepInfo().getSQFieldId();
        }
        EnumsItemModel oldEnum = null;
        if (sqid > 0 && null != (zb = (ZBInfo)para.getFmRepInfo().getDefs().getZbs().get(sqid)).getEnumInfo()) {
            oldEnum = (EnumsItemModel)para.getEnunList().get(zb.getEnumInfo().getEnumIdenty());
        }
        String periodKey = null;
        if (periodType == PeriodType.CUSTOM || oldEnum != null && periodCount != oldEnum.getItemDataList().size()) {
            DesignTableDefine periodTable = null;
            if (null != dataScheme && StringUtils.isNotEmpty((String)dataScheme.getPrefix())) {
                String periodTableCode = String.format("%s_%s", dataScheme.getPrefix().toUpperCase(), "PERIOD");
                periodTable = this.dataController.queryTableDefinesByCode(periodTableCode);
            }
            EnumsItemModel newEnum = this.getPeriodEnum(importContext, oldEnum);
            this.UpdateEnumMap(importContext, newEnum, new TableInfoDefine(periodTable));
        } else {
            periodKey = "14c2e870-c4dc-4fa8-8384-a48b52207cd1";
            EnumsItemModel newEnum = this.getPeriodEnum(importContext, oldEnum);
            this.UpdateEnumMap(importContext, newEnum, null);
        }
        return periodKey;
    }

    @Override
    public void UpdateEnumMap(TaskImportContext importContext, EnumsItemModel singleEnum, TableInfoDefine table) {
        if (importContext.getMapScheme() == null) {
            return;
        }
        SingleFileEnumInfo mapEnum = importContext.getMapScheme().getTaskPeriodEnum();
        mapEnum.setEnumCode(singleEnum.getCode());
        mapEnum.setEnumTitle(singleEnum.getTitle());
        if (null != table) {
            mapEnum.setNetTableCode(table.getCode());
            mapEnum.setNetTableKey(table.getKey());
        }
        Map enums = singleEnum.getEnumItemList();
        for (DataInfo item : enums.values()) {
            SingleFileEnumItem mapItem = mapEnum.getNewEnumItem();
            mapItem.setItemCode(item.getMapCode());
            mapItem.setItemTitle(item.getName());
            mapItem.setNetItemCode(item.getCode());
            mapItem.setNetItemTitle(item.getName());
            mapEnum.getEnumItems().add(mapItem);
        }
    }

    @Override
    public void setEnityParentField(ParaInfo para, String parentFieldid) {
        if (!para.getFmRepInfo().getDefs().getZbInfosPair().containsKey(ImportConsts.ENTITY_PARENT_FIELD)) {
            Consts.EntityField parentField = Consts.EntityField.ENTITY_FIELD_PARENTKEY;
            ZBInfo zb = new ZBInfo();
            zb.setFieldName(parentField.fieldKey);
            if (parentField.type == FieldType.FIELD_TYPE_STRING) {
                zb.setDataType(ZBDataType.STRING);
            } else if (parentField.type == FieldType.FIELD_TYPE_UUID) {
                zb.setDataType(ZBDataType.UUID);
            } else {
                zb.setDataType(ZBDataType.STRING);
            }
            zb.setDecimal((byte)0);
            zb.setLength(parentField.size);
            zb.setGridPos(new int[]{2, para.getFmRepInfo().getDefs().getZbs().size()});
            zb.setNumPos(new int[]{0, 0});
            zb.setZbTitle("\u7236\u8282\u70b9");
            zb.setEnumId("FMDM");
            if (null != parentFieldid) {
                zb.setuUIDCode(parentFieldid);
            }
            EnumInfo enumInfo = new EnumInfo();
            enumInfo.setEnumIdenty("FMDM");
            zb.setEnumInfo(enumInfo);
            para.getFmRepInfo().getDefs().getZbs().add(zb);
            para.getFmzbList().add(parentField.fieldKey);
            para.getFmRepInfo().getDefs().clearDatas();
        }
    }

    @Override
    public void setEnityCodeField(ParaInfo para, String codeFieldid) {
        if (StringUtils.isEmpty((String)codeFieldid)) {
            codeFieldid = ImportConsts.ENTITY_CODE_FIELD;
        }
        if (!para.getFmRepInfo().getDefs().getZbInfosPair().containsKey(codeFieldid)) {
            Consts.EntityField codeField = Consts.EntityField.ENTITY_FIELD_CODE;
            ZBInfo zb = new ZBInfo();
            zb.setFieldName(codeFieldid);
            if (codeField.type == FieldType.FIELD_TYPE_STRING) {
                zb.setDataType(ZBDataType.STRING);
            } else if (codeField.type == FieldType.FIELD_TYPE_UUID) {
                zb.setDataType(ZBDataType.UUID);
            } else {
                zb.setDataType(ZBDataType.STRING);
            }
            zb.setDecimal((byte)0);
            zb.setLength(codeField.size);
            zb.setGridPos(new int[]{2, para.getFmRepInfo().getDefs().getZbs().size()});
            zb.setNumPos(new int[]{0, 0});
            zb.setZbTitle("\u4ee3\u7801");
            zb.setEnumId("FMDM");
            if (null != codeFieldid) {
                zb.setuUIDCode(codeFieldid);
            }
            EnumInfo enumInfo = new EnumInfo();
            enumInfo.setEnumIdenty("FMDM");
            zb.setEnumInfo(enumInfo);
            para.getFmRepInfo().getDefs().getZbs().add(zb);
            para.getFmzbList().add(0, codeFieldid);
            para.getFmRepInfo().getDefs().clearDatas();
        }
    }

    @Override
    public boolean getUniqueField(FMRepInfo singleFmdm, List<RepInfo> sortRepList) {
        Map<String, Set<String>> singleRepeatRepDic = this.getReportRepeatFields(singleFmdm, sortRepList);
        return singleRepeatRepDic != null && singleRepeatRepDic.size() == 0;
    }

    @Override
    public Map<String, Set<String>> getReportRepeatFields(FMRepInfo singleFmdm, List<RepInfo> sortRepList) {
        int repeatCount = 0;
        HashMap<String, String> singleFieldDic = new HashMap<String, String>();
        HashMap singleFieldRepDic = new HashMap();
        HashMap<String, Set<String>> singleRepeatRepDic = new HashMap<String, Set<String>>();
        for (RepInfo rep : sortRepList) {
            if (singleFmdm == rep || rep.isFMDM() || "FMDM".equalsIgnoreCase(rep.getCode()) || rep.getTableType() == ReportTableType.RTT_BLOBTABLE || rep.getTableType() == ReportTableType.RTT_WORDTABLE) continue;
            FieldDefs def = rep.getDefs();
            List singleZbs = def.getZbsNoZDM();
            for (ZBInfo zb : singleZbs) {
                Set<String> formCodes;
                if (singleFieldDic.containsKey(zb.getFieldName())) {
                    if (++repeatCount == 1) {
                        log.info("\u6307\u6807" + rep.getCode() + "[" + zb.getFieldName() + "]\u4e0e\u6307\u6807" + (String)singleFieldDic.get(zb.getFieldName()) + "[" + zb.getFieldName() + "]\u4ee3\u7801\u76f8\u540c\uff0c\u5c06\u4f7f\u7528\u6307\u6807\u8868\u6807\u8bc6\u4f5c\u4e3a\u6307\u6807\u4ee3\u7801\u524d\u7f00");
                    }
                    if (!singleFieldRepDic.containsKey(zb.getFieldName())) continue;
                    formCodes = (Set)singleFieldRepDic.get(zb.getFieldName());
                    formCodes.add(rep.getCode());
                    for (String singleFormCode : formCodes) {
                        Set repeatFields = null;
                        repeatFields = singleRepeatRepDic.containsKey(singleFormCode) ? (Set)singleRepeatRepDic.get(singleFormCode) : new HashSet();
                        repeatFields.add(zb.getFieldName());
                        singleRepeatRepDic.put(singleFormCode, repeatFields);
                    }
                    continue;
                }
                singleFieldDic.put(zb.getFieldName(), rep.getCode());
                formCodes = new HashSet<String>();
                formCodes.add(rep.getCode());
                singleFieldRepDic.put(zb.getFieldName(), formCodes);
            }
            if (!singleZbs.isEmpty()) continue;
            singleRepeatRepDic.put(rep.getCode(), new HashSet());
        }
        return singleRepeatRepDic;
    }

    @Override
    public Map<String, String> getEnumMapNets(TaskImportContext importContext) {
        Map<Object, Object> enumMapDic = new HashMap();
        if (importContext.getCompareEnumMap().size() > 0) {
            enumMapDic = importContext.getCompareEnumMap();
        }
        if (importContext.getMapScheme() != null && importContext.getMapScheme().getEnumInfos().size() > 0) {
            List enumMapList = importContext.getMapScheme().getEnumInfos();
            Iterator iterator = enumMapList.iterator();
            while (iterator.hasNext()) {
                SingleFileEnumInfo e = (SingleFileEnumInfo)iterator.next();
                if (enumMapDic.containsKey(e.getEnumCode().toUpperCase())) continue;
                enumMapDic.put(e.getEnumCode().toUpperCase(), e.getNetTableCode());
            }
        }
        if (enumMapDic.size() == 0 && importContext.getCompareEnumDic() != null && importContext.getCompareEnumDic().size() > 0) {
            for (String singleCode : importContext.getCompareEnumDic().keySet()) {
                if (enumMapDic.containsKey(singleCode.toUpperCase())) continue;
                enumMapDic.put(singleCode.toUpperCase(), importContext.getCompareEnumDic().get(singleCode).getNetCode());
            }
        }
        if (importContext.getCompareInfo() != null && enumMapDic.size() == 0) {
            CompareDataEnumDTO enumQueryParam = new CompareDataEnumDTO();
            enumQueryParam.setInfoKey(importContext.getCompareInfo().getKey());
            enumQueryParam.setDataType(CompareDataType.DATA_ENUM);
            List<CompareDataEnumDTO> oldEnumList = this.enumDataService.list(enumQueryParam);
            for (CompareDataEnumDTO oldData : oldEnumList) {
                if (enumMapDic.containsKey(oldData.getSingleCode().toUpperCase())) continue;
                enumMapDic.put(oldData.getSingleCode().toUpperCase(), oldData.getNetCode());
            }
        }
        importContext.setCompareEnumMap(enumMapDic);
        return enumMapDic;
    }
}

