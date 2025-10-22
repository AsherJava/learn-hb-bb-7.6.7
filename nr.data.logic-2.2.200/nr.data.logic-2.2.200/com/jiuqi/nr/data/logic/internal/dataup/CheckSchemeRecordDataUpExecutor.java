/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.SerializationFeature
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.data.logic.internal.dataup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.GroupType;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.internal.dataup.BatchCheckResultGroupInfo;
import com.jiuqi.nr.data.logic.internal.entity.CheckSchemeRecordDO;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.data.logic.internal.util.SerializationUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nvwa.dataengine.INvwaDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

public class CheckSchemeRecordDataUpExecutor
implements CustomClassExecutor {
    private static final Logger logger = LoggerFactory.getLogger(CheckSchemeRecordDataUpExecutor.class);
    private static final JdbcTemplate jdbcTemplate = (JdbcTemplate)BeanUtil.getBean(JdbcTemplate.class);
    private static final DataModelService dataModelService = (DataModelService)BeanUtil.getBean(DataModelService.class);
    private static final INvwaDataAccessProvider dataAccessProvider = (INvwaDataAccessProvider)BeanUtil.getBean(INvwaDataAccessProvider.class);
    private static final ParamUtil paramUtil = (ParamUtil)BeanUtil.getBean(ParamUtil.class);
    private static final IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)BeanUtil.getBean(IFormulaRunTimeController.class);
    private static final DimensionCollectionUtil dimensionCollectionUtil = (DimensionCollectionUtil)BeanUtil.getBean(DimensionCollectionUtil.class);
    public static final String TABLENAME_REVIEW_INFO = "DE_RWIF_";
    public static final String FIELDNAME_RWIF_RECID = "RWIF_RECID";
    public static final String FIELDNAME_RWIF_USER = "RWIF_USER";
    public static final String FIELDNAME_RWIF_CHECKINFO = "RWIF_CHECKINFO";
    public static final String FIELDNAME_RWIF_DATE = "RWIF_DATE";
    public static final String FIELDNAME_RWIF_TASKNAME = "RWIF_TASKNAME";
    public static final String FIELDNAME_RWIF_FORMSCHEMEKEY = "RWIF_FORMSCHEMEKEY";
    public static final String FIELDNAME_RWIF_FORMSCHEMETITLE = "RWIF_FORMSCHEMETITLE";
    public static final String FIELDNAME_RWIF_FORMSCHEMEDATE = "RWIF_FORMSCHEMEDATE";
    public static final String FIELDNAME_RWIF_FORMULASCHEMETITLE = "RWIF_FORMULASCHEMETITLE";
    public static final String FIELDNAME_RWIF_UNITFORMCLOB = "RWIF_UNITFORMCLOB";

    public void execute(DataSource dataSource) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        for (String formSchemeCode : paramUtil.getAllFormScheme()) {
            String rwifTableName = CheckTableNameUtil.getRWIFTableName(formSchemeCode);
            try {
                TableModelDefine table = dataModelService.getTableModelDefineByCode(rwifTableName);
                NvwaQueryModel queryModel = new NvwaQueryModel();
                List columns = dataModelService.getColumnModelDefinesByTable(table.getID());
                for (ColumnModelDefine column : columns) {
                    queryModel.getColumns().add(new NvwaQueryColumn(column));
                }
                DataAccessContext dataAccessContext = new DataAccessContext(dataModelService);
                INvwaDataAccess dataAccess = dataAccessProvider.createReadOnlyDataAccess(queryModel);
                MemoryDataSet dataRows = dataAccess.executeQuery(dataAccessContext);
                this.dataUpByTable(this.getCheckSchemeRecordDOS(columns, (MemoryDataSet<NvwaQueryColumn>)dataRows, objectMapper));
                logger.info("\u5ba1\u6838\u65b9\u6848\u8bb0\u5f55\u8868{}\u6570\u636e\u5347\u7ea7\u5b8c\u6210", (Object)rwifTableName);
            }
            catch (Exception e) {
                logger.error("\u5ba1\u6838\u65b9\u6848\u8bb0\u5f55\u8868" + rwifTableName + "\u6570\u636e\u5347\u7ea7\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
        }
    }

    private void dataUpByTable(List<CheckSchemeRecordDO> list) {
        if (!CollectionUtils.isEmpty(list)) {
            List<Object[]> args = this.getArgs(list);
            jdbcTemplate.batchUpdate(String.format("INSERT INTO %s(%s,%s,%s,%s,%s) VALUES (?,?,?,?,?)", "NR_PARAM_REVIEW_INFO", "RWIF_KEY", FIELDNAME_RWIF_USER, "RWIF_FM_SCHEME", "RWIF_DATETIME", "RWIF_CHECK_INFO"), args);
        }
    }

    private List<CheckSchemeRecordDO> getCheckSchemeRecordDOS(List<ColumnModelDefine> columns, MemoryDataSet<NvwaQueryColumn> dataRows, ObjectMapper mapper) throws JsonProcessingException {
        ArrayList<CheckSchemeRecordDO> list = new ArrayList<CheckSchemeRecordDO>();
        for (DataRow dataRow : dataRows) {
            CheckSchemeRecordDO checkSchemeRecordDO = new CheckSchemeRecordDO();
            checkSchemeRecordDO.setKey(UUID.randomUUID().toString());
            block11: for (int i = 0; i < columns.size(); ++i) {
                ColumnModelDefine column = columns.get(i);
                switch (column.getCode()) {
                    case "RWIF_DATE": {
                        long time = ((GregorianCalendar)dataRow.getValue(i)).getTime().getTime();
                        checkSchemeRecordDO.setCheckTime(time);
                        continue block11;
                    }
                    case "RWIF_USER": {
                        String userId = dataRow.getValue(i).toString();
                        checkSchemeRecordDO.setUserId(userId);
                        continue block11;
                    }
                    case "RWIF_CHECKINFO": {
                        String checkInfo = new String((byte[])dataRow.getValue(i), StandardCharsets.UTF_8);
                        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
                        BatchCheckResultGroupInfo batchCheckResultGroupInfo = (BatchCheckResultGroupInfo)mapper.readValue(checkInfo, BatchCheckResultGroupInfo.class);
                        checkSchemeRecordDO.setFormSchemeKey(batchCheckResultGroupInfo.getContext().getFormSchemeKey());
                        CheckResultQueryParam checkResultQueryParam = this.getCheckResultQueryParam(batchCheckResultGroupInfo);
                        byte[] serializeParam = SerializationUtil.serialize(checkResultQueryParam);
                        checkSchemeRecordDO.setCheckResultQueryParamJson(serializeParam);
                        continue block11;
                    }
                }
            }
            list.add(checkSchemeRecordDO);
        }
        return list;
    }

    private CheckResultQueryParam getCheckResultQueryParam(BatchCheckResultGroupInfo batchCheckResultGroupInfo) {
        CheckResultQueryParam checkResultQueryParam = new CheckResultQueryParam();
        Map<String, DimensionValue> dimensionSet = batchCheckResultGroupInfo.getContext().getDimensionSet();
        DimensionCollection dimensionCollection = dimensionCollectionUtil.getDimensionCollection(dimensionSet, batchCheckResultGroupInfo.getContext().getFormSchemeKey());
        checkResultQueryParam.setDimensionCollection(dimensionCollection);
        Map<String, List<String>> formulas = batchCheckResultGroupInfo.getFormulas();
        if (formulas.isEmpty()) {
            checkResultQueryParam.setMode(Mode.FORM);
            checkResultQueryParam.setRangeKeys(new ArrayList<String>());
        } else {
            Iterator<Map.Entry<String, List<String>>> iterator = formulas.entrySet().iterator();
            if (iterator.hasNext()) {
                Map.Entry<String, List<String>> entry = iterator.next();
                if (entry.getValue().isEmpty()) {
                    checkResultQueryParam.setMode(Mode.FORM);
                    checkResultQueryParam.setRangeKeys(new ArrayList<String>(formulas.keySet()));
                } else {
                    checkResultQueryParam.setMode(Mode.FORMULA);
                    ArrayList<String> formulaKeys = new ArrayList<String>();
                    for (List<String> list : formulas.values()) {
                        formulaKeys.addAll(list);
                    }
                    checkResultQueryParam.setRangeKeys(formulaKeys);
                }
            }
        }
        checkResultQueryParam.setVariableMap(batchCheckResultGroupInfo.getContext().getVariableMap());
        checkResultQueryParam.setPagerInfo(batchCheckResultGroupInfo.getPagerInfo());
        HashMap<Integer, Boolean> checkTypes = new HashMap<Integer, Boolean>();
        boolean checkDesNull = batchCheckResultGroupInfo.isCheckDesNull();
        List<Integer> uploadCheckTypes = batchCheckResultGroupInfo.getUploadCheckTypes();
        List<Integer> checkTypes1 = batchCheckResultGroupInfo.getCheckTypes();
        if (uploadCheckTypes.isEmpty()) {
            for (Integer integer : checkTypes1) {
                checkTypes.put(integer, null);
            }
        } else if (checkDesNull) {
            for (Integer integer : checkTypes1) {
                checkTypes.put(integer, false);
            }
        } else {
            for (Integer integer : checkTypes1) {
                checkTypes.put(integer, true);
            }
        }
        checkResultQueryParam.setCheckTypes(checkTypes);
        List<String> list = this.getFormulaSchemeList(batchCheckResultGroupInfo.getContext().getFormSchemeKey(), batchCheckResultGroupInfo.getFormulaSchemeKeys()).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        checkResultQueryParam.setFormulaSchemeKeys(list);
        GroupType groupType = GroupType.unit;
        if (StringUtils.isNotEmpty((String)batchCheckResultGroupInfo.getOrderField())) {
            groupType = GroupType.getByKey(batchCheckResultGroupInfo.getOrderField().toLowerCase());
        }
        checkResultQueryParam.setGroupType(groupType);
        return checkResultQueryParam;
    }

    private List<FormulaSchemeDefine> getFormulaSchemeList(String formSchemeKey, String formulaSchemeKeyStr) {
        List formulaSchemes;
        ArrayList<FormulaSchemeDefine> formulaSchemeDefine = new ArrayList<FormulaSchemeDefine>();
        if (StringUtils.isNotEmpty((String)formulaSchemeKeyStr)) {
            String[] idArray = formulaSchemeKeyStr.split(";");
            for (String formulaSchemeKey : idArray) {
                formulaSchemeDefine.add(formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemeKey));
            }
        }
        if (!formulaSchemeDefine.isEmpty()) {
            return formulaSchemeDefine;
        }
        if (StringUtils.isNotEmpty((String)formSchemeKey) && (formulaSchemes = formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeKey)) != null && formulaSchemes.size() > 0) {
            for (FormulaSchemeDefine formulaScheme : formulaSchemes) {
                if (!formulaScheme.isDefault()) continue;
                formulaSchemeDefine.add(formulaScheme);
            }
        }
        return formulaSchemeDefine;
    }

    private List<Object[]> getArgs(List<CheckSchemeRecordDO> list) {
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (CheckSchemeRecordDO checkSchemeRecordDO : list) {
            Object[] arg = new Object[]{checkSchemeRecordDO.getKey(), checkSchemeRecordDO.getUserId(), checkSchemeRecordDO.getFormSchemeKey(), checkSchemeRecordDO.getCheckTime(), checkSchemeRecordDO.getCheckResultQueryParamJson()};
            args.add(arg);
        }
        return args;
    }
}

