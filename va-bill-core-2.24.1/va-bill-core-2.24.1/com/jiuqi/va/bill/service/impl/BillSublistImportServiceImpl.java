/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.va.biz.cache.BaseDataDefineCache
 *  com.jiuqi.va.biz.impl.data.DataFieldDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataRowImpl
 *  com.jiuqi.va.biz.impl.data.DataTableDefineImpl
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.data.Data
 *  com.jiuqi.va.biz.intf.data.DataFieldDefine
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.biz.intf.value.NamedContainer
 *  com.jiuqi.va.biz.intf.value.ValueType
 *  com.jiuqi.va.biz.ruler.ModelParamNode
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.ruler.impl.FormulaImpl
 *  com.jiuqi.va.biz.ruler.impl.RulerDefineImpl
 *  com.jiuqi.va.biz.ruler.intf.Ruler
 *  com.jiuqi.va.biz.ruler.intf.RulerConsts
 *  com.jiuqi.va.biz.utils.Env
 *  com.jiuqi.va.biz.utils.FormulaUtils
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDO
 *  com.jiuqi.va.domain.basedata.BaseDataDefineDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryParentType
 *  com.jiuqi.va.domain.user.UserLoginDTO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.BaseDataDefineClient
 *  com.jiuqi.va.feign.client.DataModelClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  org.springframework.data.redis.core.StringRedisTemplate
 */
package com.jiuqi.va.bill.service.impl;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.va.bill.catche.LocalCacheEntity;
import com.jiuqi.va.bill.catche.LocalCacheHandler;
import com.jiuqi.va.bill.domain.BillSublistImportVO;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.intf.IBillSublistImportValidator;
import com.jiuqi.va.bill.service.BillSublistImportService;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.bill.utils.BillUtils;
import com.jiuqi.va.biz.cache.BaseDataDefineCache;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataRowImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.Data;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.biz.intf.value.NamedContainer;
import com.jiuqi.va.biz.intf.value.ValueType;
import com.jiuqi.va.biz.ruler.ModelParamNode;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.ruler.impl.FormulaImpl;
import com.jiuqi.va.biz.ruler.impl.RulerDefineImpl;
import com.jiuqi.va.biz.ruler.intf.Ruler;
import com.jiuqi.va.biz.ruler.intf.RulerConsts;
import com.jiuqi.va.biz.utils.Env;
import com.jiuqi.va.biz.utils.FormulaUtils;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDO;
import com.jiuqi.va.domain.basedata.BaseDataDefineDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserLoginDTO;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.BaseDataDefineClient;
import com.jiuqi.va.feign.client.DataModelClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class BillSublistImportServiceImpl
implements BillSublistImportService {
    private static final Logger logger = LoggerFactory.getLogger(BillSublistImportServiceImpl.class);
    private static final List<String> filedTypes = new ArrayList<String>();
    private static final String ORIGINROWID = "_originRowId";
    private static final String STATEMESSAGE = "_stateMessage";
    private static final String INDEX = "_index";
    private static final String STATE = "_state";
    @Autowired(required=false)
    private StringRedisTemplate redisTemplate;
    @Autowired(required=false)
    private List<IBillSublistImportValidator> validators;
    @Autowired(required=false)
    private DataModelClient dataModelClient;
    @Autowired(required=false)
    private BaseDataClient baseDataClient;
    @Autowired
    private BaseDataDefineClient baseDataDefineClient;
    @Autowired
    private OrgDataClient orgDataClient;

    public BillSublistImportServiceImpl() {
        filedTypes.add("CODE");
        filedTypes.add("SHOWTITLE");
        filedTypes.add("OBJECTCODE");
        filedTypes.add("NAME");
        filedTypes.add("TITLE");
    }

    @Override
    public BillSublistImportVO getProgress(String key) {
        if (EnvConfig.getRedisEnable()) {
            String s = (String)this.redisTemplate.opsForValue().get((Object)key);
            if (!StringUtils.hasText(s)) {
                return null;
            }
            return (BillSublistImportVO)JSONUtil.parseObject((String)s, BillSublistImportVO.class);
        }
        LocalCacheEntity cache = LocalCacheHandler.getCache(key);
        if (cache == null) {
            return null;
        }
        return (BillSublistImportVO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)cache.getCacheContent()), BillSublistImportVO.class);
    }

    @Override
    public void syncSublistProgress(String key, BillSublistImportVO billSublistImportVO) {
        if (EnvConfig.getRedisEnable()) {
            this.redisTemplate.opsForValue().set((Object)key, (Object)JSONUtil.toJSONString((Object)billSublistImportVO), 1L, TimeUnit.MINUTES);
        } else {
            LocalCacheEntity localCacheEntity = new LocalCacheEntity(key, billSublistImportVO);
            LocalCacheHandler.addCache(localCacheEntity, 60);
        }
    }

    public void delProgress(String key) {
        if (EnvConfig.getRedisEnable()) {
            this.redisTemplate.delete((Object)key);
        } else {
            LocalCacheHandler.removeCache(key);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @Async
    public void checkExcelData(BillModel model, Map<String, Object> params, String cacheKey, UserLoginDTO loginUser, Locale locale) {
        BillModelImpl billModel = (BillModelImpl)model;
        billModel.getRuler().getRulerExecutor().setEnable(true);
        ShiroUtil.unbindUser();
        LocaleContextHolder.setLocale(locale);
        try {
            Object timeZone = billModel.getContext().getContextValue("X--timeZone");
            ShiroUtil.bindUser((UserLoginDTO)loginUser);
            boolean importType = false;
            if (params.get("importType") != null) {
                importType = (Boolean)params.get("importType");
            }
            String tableName = (String)params.get("tableName");
            Object contextValue = billModel.getContext().getContextValue("X--detailFilterDataId");
            if (contextValue != null && ((Map)contextValue).containsKey(tableName) && !importType) {
                throw new BillException(String.format("\u5f53\u524d\u5b50\u8868%s\u542f\u7528\u8fc7\u6ee4\u60c5\u51b5\u4e0b\u7981\u6b62\u5168\u91cf\u5bfc\u5165", tableName));
            }
            List tableData = (List)params.get("tableData");
            List template = (List)params.get("template");
            StringBuilder errorInfo = new StringBuilder();
            BillModelImpl modelImpl = (BillModelImpl)model;
            modelImpl.getData().stopRecord();
            HashMap<String, HashMap<String, Object>> cash = new HashMap<String, HashMap<String, Object>>();
            HashMap<String, Map<String, Map<String, Object>>> refDataValuesCache = new HashMap<String, Map<String, Map<String, Object>>>();
            HashMap<String, List<DataModelColumn>> refDataFieldsTypeCache = new HashMap<String, List<DataModelColumn>>();
            DataTable table = modelImpl.getTable(tableName);
            NamedContainer fields = table.getDefine().getFields();
            ArrayList<String> primaryKeys = new ArrayList<String>();
            for (Map map : template) {
                if (map.get("primaryKey") == null || !((Boolean)map.get("primaryKey")).booleanValue()) continue;
                primaryKeys.add(map.get("fieldName").toString());
            }
            ArrayList<List<Object>> primaryValues = new ArrayList<List<Object>>();
            if (importType && !CollectionUtils.isEmpty(primaryKeys)) {
                int j = table.getRows().size();
                for (int i = 0; i < j; ++i) {
                    DataRow dataRow = (DataRow)table.getRows().get(i);
                    ArrayList<Object> key = new ArrayList<Object>();
                    block21: for (String primaryKey : primaryKeys) {
                        DataFieldDefine dataFieldDefine = (DataFieldDefine)fields.get(primaryKey);
                        ValueType valueType = dataFieldDefine.getValueType();
                        switch (valueType) {
                            case TIME: 
                            case DATETIME: 
                            case DATE: {
                                Date date = dataRow.getDate(primaryKey);
                                if (date == null) {
                                    key.add(null);
                                    continue block21;
                                }
                                key.add(dataRow.getDate(primaryKey).getTime());
                                continue block21;
                            }
                            case DECIMAL: 
                            case LONG: 
                            case INTEGER: 
                            case SHORT: 
                            case DOUBLE: {
                                key.add(dataRow.getBigDecimal(primaryKey));
                                continue block21;
                            }
                        }
                        key.add(dataRow.getString(primaryKey));
                    }
                    primaryValues.add(key);
                }
            }
            ArrayList<List<Map<String, String>>> checkResults = new ArrayList<List<Map<String, String>>>();
            if (this.validators != null) {
                for (IBillSublistImportValidator validator : this.validators) {
                    List<Map<String, String>> result;
                    if (!validator.isEnable(model, tableName) || (result = validator.validator(tableData, template, modelImpl, tableName)) == null) continue;
                    checkResults.add(result);
                }
            }
            ArrayList fieldsData = new ArrayList();
            if (importType) {
                List rowsData = model.getTable(tableName).getRowsData();
                fieldsData.addAll(rowsData);
            }
            boolean checkDigits = false;
            if (params.get("checkDigits") != null) {
                checkDigits = (Boolean)params.get("checkDigits");
            }
            int errorData = 0;
            int successData = 0;
            ArrayList<Map<String, Object>> appendData = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < tableData.size(); ++i) {
                DataRowImpl appendRow;
                Map map = (Map)tableData.get(i);
                UUID originRowId = null;
                boolean isAdd = true;
                Map<String, String> fieldData = new HashMap();
                if (primaryValues.isEmpty()) {
                    appendRow = (DataRowImpl)table.appendRow();
                } else {
                    ArrayList<Object> keys = new ArrayList<Object>();
                    try {
                        block24: for (String string : primaryKeys) {
                            DataFieldDefine dataFieldDefine = (DataFieldDefine)fields.get(string);
                            int refTableType = dataFieldDefine.getRefTableType();
                            if (refTableType == 1) {
                                keys.add(this.getObjectCode(dataFieldDefine, map, billModel.getMaster(), template));
                                continue;
                            }
                            if (refTableType == 4) {
                                keys.add(this.getOrgCode(dataFieldDefine, map, template));
                                continue;
                            }
                            ValueType valueType = dataFieldDefine.getValueType();
                            switch (valueType) {
                                case TIME: 
                                case DATETIME: 
                                case DATE: {
                                    Object o2 = map.get(string);
                                    if (o2 == null) {
                                        keys.add(null);
                                        continue block24;
                                    }
                                    keys.add(Utils.parseDate((String)((String)o2)).getTime());
                                    continue block24;
                                }
                                case DECIMAL: 
                                case LONG: 
                                case INTEGER: 
                                case SHORT: 
                                case DOUBLE: {
                                    Object value2 = map.get(string);
                                    if (value2 == null) {
                                        keys.add(null);
                                        continue block24;
                                    }
                                    keys.add(new BigDecimal(String.valueOf(value2)));
                                    continue block24;
                                }
                            }
                            keys.add(map.get(string));
                        }
                    }
                    catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.importfailed"));
                    }
                    int keyIndex = this.getKeyIndex(primaryValues, keys);
                    if (keyIndex != -1) {
                        appendRow = (DataRowImpl)table.getRows().get(keyIndex);
                        originRowId = appendRow.getUUID("ID");
                        for (Map map2 : fieldsData) {
                            if (map2.get("ID") == null || !map2.get("ID").equals(originRowId)) continue;
                            fieldData = map2;
                            break;
                        }
                        isAdd = false;
                    } else {
                        appendRow = (DataRowImpl)table.appendRow();
                    }
                }
                int index = i;
                errorInfo.setLength(0);
                errorInfo.append(BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.datacheckfailed"));
                LinkedHashMap<String, Object> append = new LinkedHashMap<String, Object>();
                append.put(INDEX, map.get(INDEX));
                map.put(STATE, 1);
                map.put(STATEMESSAGE, "");
                for (Map map3 : template) {
                    StringBuilder fieldErrorInfo = new StringBuilder();
                    String key = map3.get("fieldName").toString();
                    String fieldValue = Optional.ofNullable(map.get(key)).orElse("");
                    if (key.equals("ID") || key.equals("VER") || key.equals("MASTERID") || key.equals("BILLCODE") || key.equals(STATE) || key.equals(STATEMESSAGE) || key.equals(INDEX)) continue;
                    DataFieldDefine field = (DataFieldDefine)fields.get(key);
                    checkResults.forEach(var1 -> {
                        Map map2 = (Map)var1.get(index);
                        if (map2 == null) {
                            return;
                        }
                        String var2 = (String)map2.get(key);
                        if (StringUtils.hasText(var2)) {
                            if (fieldErrorInfo.length() == 0) {
                                fieldErrorInfo.append("\u3010").append(field.getTitle()).append("\u3011").append(fieldValue);
                            }
                            fieldErrorInfo.append(var2).append("\uff0c");
                        }
                    });
                    if (fieldErrorInfo.length() > 0) {
                        errorInfo.append(fieldErrorInfo.toString());
                        map.put(STATE, 0);
                        continue;
                    }
                    Object checkParam = map3.get("checkParam");
                    if (checkParam != null && !CollectionUtils.isEmpty((List)checkParam)) {
                        List param = (List)checkParam;
                        if (!StringUtils.hasText(fieldValue)) {
                            if (param.size() != 2 && !"require".equals(param.get(0))) continue;
                            errorInfo.append("\u3010").append(field.getTitle()).append("\u3011").append(BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.cannotempty"));
                            map.put(STATE, 0);
                            continue;
                        }
                        if ((param.size() == 2 || "isSingle".equals(param.get(0))) && fieldsData.stream().filter(o -> fieldValue.equals(o.get(key))).findFirst().isPresent()) {
                            errorInfo.append("\u3010").append(field.getTitle()).append("\u3011").append(fieldValue).append(BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.unique"));
                            map.put(STATE, 0);
                        }
                        fieldData.put(key, fieldValue);
                    }
                    if (!StringUtils.hasText(fieldValue)) continue;
                    if (field.getRefTableName() != null) {
                        String option;
                        try {
                            option = map3.get("importField") == "" ? map3.get("importType").toString() : map3.get("importField").toString();
                        }
                        catch (NullPointerException e) {
                            this.delProgress(cacheKey);
                            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.importfailed"));
                        }
                        boolean filter = true;
                        IExpression expression = null;
                        if (map3.get("disabledFilter") == null || !((Boolean)map3.get("disabledFilter")).booleanValue()) {
                            RulerDefineImpl pluginDefine = (RulerDefineImpl)((Ruler)modelImpl.getPlugins().get(Ruler.class)).getDefine();
                            ListContainer formulas = pluginDefine.getFormulas();
                            DataTableDefineImpl dataTableDefine = (DataTableDefineImpl)((DataTable)((Data)modelImpl.getPlugins().get(Data.class)).getTables().get(tableName)).getDefine();
                            DataFieldDefineImpl dataFieldDefine = (DataFieldDefineImpl)dataTableDefine.getFields().get(key);
                            UUID id = dataFieldDefine.getId();
                            List list = formulas.stream().filter(o -> o.getObjectId().equals(id) && "field".equals(o.getObjectType()) && FormulaType.FILTER.equals((Object)o.getFormulaType()) && RulerConsts.FORMULA_OBJECT_PROP_FIELD_FILTER.equals(o.getPropertyType()) && o.isUsed()).collect(Collectors.toList());
                            if (!list.isEmpty()) {
                                expression = ((FormulaImpl)list.get(0)).getCompiledExpression();
                            }
                        }
                        if (field.isMultiChoice()) {
                            DataTableDefineImpl fieldValues = fieldValue.split(",");
                            ArrayList<DataTableDefineImpl> list = new ArrayList<DataTableDefineImpl>();
                            for (DataTableDefineImpl dataTableDefineImpl : fieldValues) {
                                list.add(dataTableDefineImpl);
                            }
                            List collect = list.stream().map(value -> this.getViewValue((String)value, option, field, modelImpl, cash, (Map<String, Map<String, Map<String, Object>>>)refDataValuesCache, appendRow)).collect(Collectors.toList());
                            for (int i1 = 0; i1 < collect.size(); ++i1) {
                                if (!(collect.get(i1) instanceof String)) continue;
                                errorInfo.append("\u3010").append(field.getTitle()).append("\u3011").append((String)list.get(i1)).append(collect.get(i1));
                                map.put(STATE, 0);
                            }
                            if (map.get(STATE).equals(0)) continue;
                            String filterValue = null;
                            if (expression != null) {
                                for (String string : list) {
                                    filter = this.checkRefDataFilter(string, expression, appendRow, model, field, refDataValuesCache, tableName, refDataFieldsTypeCache);
                                    if (filter) continue;
                                    filterValue = string;
                                    break;
                                }
                            }
                            if (filter) {
                                appendRow.setValue(key, collect);
                                append.put(key, collect);
                                continue;
                            }
                            errorInfo.append("\u3010").append(field.getTitle()).append("\u3011").append(filterValue).append(BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.filtered"));
                            map.put(STATE, 0);
                            continue;
                        }
                        Object viewValue = this.getViewValue(fieldValue, option, field, modelImpl, cash, refDataValuesCache, appendRow);
                        if (viewValue instanceof String) {
                            errorInfo.append("\u3010").append(field.getTitle()).append("\u3011").append(fieldValue).append(viewValue);
                            map.put(STATE, 0);
                            continue;
                        }
                        if (expression != null) {
                            filter = this.checkRefDataFilter(fieldValue, expression, appendRow, model, field, refDataValuesCache, tableName, refDataFieldsTypeCache);
                        }
                        if (filter) {
                            appendRow.setValue(key, viewValue);
                            append.put(key, viewValue);
                            continue;
                        }
                        errorInfo.append("\u3010").append(field.getTitle()).append("\u3011").append(fieldValue).append(BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.filtered"));
                        map.put(STATE, 0);
                        continue;
                    }
                    try {
                        if (timeZone != null && ValueType.DATETIME.equals((Object)field.getValueType())) {
                            String stringDate = BillUtils.convertToShanghaiTime(fieldValue, (String)timeZone);
                            appendRow.setValue(key, (Object)stringDate);
                        } else if (checkDigits && ValueType.DECIMAL.equals((Object)field.getValueType())) {
                            if (this.checkFieldDigits(field, fieldValue)) {
                                appendRow.setValue(key, (Object)fieldValue);
                            } else {
                                errorInfo.append("\u3010").append(field.getTitle()).append("\u3011").append(fieldValue).append(BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.checkDigits"));
                                map.put(STATE, 0);
                            }
                        } else {
                            appendRow.setValue(key, (Object)fieldValue);
                        }
                        append.put(key, fieldValue);
                    }
                    catch (Exception e) {
                        logger.error("\u5b50\u8868\u5bfc\u5165:" + field.getTitle() + fieldValue + e.getMessage(), e);
                        if (e.getMessage() != null && e.getMessage().startsWith("java.text.ParseException: Unparseable date:")) {
                            errorInfo.append("\u3010").append(field.getTitle()).append("\u3011").append(fieldValue).append(BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.unparseabledate"));
                        } else {
                            errorInfo.append("\u3010").append(field.getTitle()).append("\u3011").append(fieldValue).append(BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.importformat"));
                        }
                        map.put(STATE, 0);
                    }
                }
                if (map.get(STATE).equals(1)) {
                    if (originRowId != null) {
                        append.put(ORIGINROWID, originRowId);
                    }
                    appendData.add(append);
                    map.put(STATEMESSAGE, BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.datachecksuccess"));
                    if (isAdd) {
                        fieldsData.add(fieldData);
                    }
                    ++successData;
                } else {
                    errorInfo.deleteCharAt(errorInfo.length() - 1);
                    map.put(STATEMESSAGE, errorInfo.toString());
                    ++errorData;
                }
                BillSublistImportVO billSublistImportVO = new BillSublistImportVO();
                billSublistImportVO.setErrorData(errorData);
                billSublistImportVO.setSuccessData(successData);
                if (i == tableData.size() - 1) {
                    billSublistImportVO.setTableData(tableData);
                    billSublistImportVO.setAppendData(appendData);
                }
                this.syncSublistProgress(cacheKey, billSublistImportVO);
            }
        }
        catch (Exception e) {
            BillSublistImportVO billSublistImportVO = new BillSublistImportVO();
            billSublistImportVO.setException(BillCoreI18nUtil.getMessage("va.bill.core.import.check.error") + "\uff1a" + e.getMessage());
            this.syncSublistProgress(cacheKey, billSublistImportVO);
            logger.error("\u5bfc\u5165\u5f02\u5e38\uff1a" + e.getMessage(), e);
        }
        finally {
            billModel.getRuler().getRulerExecutor().setEnable(false);
            ShiroUtil.unbindUser();
        }
    }

    private Object getOrgCode(DataFieldDefine dataFieldDefine, Map<String, Object> map, List<Map<String, Object>> template) {
        String value = (String)map.get(dataFieldDefine.getName());
        if (value == null) {
            return null;
        }
        List templateField = template.stream().filter(o -> dataFieldDefine.getName().equals(o.get("fieldName"))).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(templateField)) {
            logger.info("\u672a\u627e\u5230\u5b57\u6bb5\u6a21\u677f:{}", (Object)dataFieldDefine.getName());
            return null;
        }
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname(dataFieldDefine.getRefTableName());
        String importType = (String)((Map)templateField.get(0)).get("importType");
        if ("1".equals(importType)) {
            orgDTO.setCode(value);
        } else if ("2".equals(importType)) {
            orgDTO.setName(value);
        } else {
            String[] values = value.split(" ", 2);
            if (values.length < 2) {
                return BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.importformat");
            }
            String code = values[1];
            orgDTO.setCode(code);
            PageVO list = this.orgDataClient.list(new OrgDTO((Map)orgDTO));
            if (CollectionUtils.isEmpty(list.getRows())) {
                orgDTO.setCode(values[0]);
            } else {
                logger.info("\u67e5\u8be2\u5230\u7ec4\u7ec7:{}", (Object)StringUtils.collectionToCommaDelimitedString(list.getRows().stream().map(OrgDO::getCode).collect(Collectors.toList())));
                return ((OrgDO)list.getRows().get(0)).getCode();
            }
        }
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        orgDTO.setTenantName(ShiroUtil.getTenantName());
        PageVO orgDO = this.orgDataClient.list(orgDTO);
        if (CollectionUtils.isEmpty(orgDO.getRows())) {
            logger.info("\u672a\u627e\u5230\u7ec4\u7ec7:{}", (Object)value);
            return null;
        }
        logger.info("\u67e5\u8be2\u5230\u7ec4\u7ec7:{}", (Object)StringUtils.collectionToCommaDelimitedString(orgDO.getRows().stream().map(OrgDO::getCode).collect(Collectors.toList())));
        return ((OrgDO)orgDO.getRows().get(0)).getCode();
    }

    private Object getObjectCode(DataFieldDefine dataFieldDefine, Map<String, Object> map, DataRow master, List<Map<String, Object>> template) {
        String importType;
        Date bizDate;
        String value = (String)map.get(dataFieldDefine.getName());
        if (value == null) {
            return null;
        }
        List templateField = template.stream().filter(o -> dataFieldDefine.getName().equals(o.get("fieldName"))).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(templateField)) {
            logger.info("\u672a\u627e\u5230\u5b57\u6bb5\u6a21\u677f:{}", (Object)dataFieldDefine.getName());
            return null;
        }
        BaseDataDefineDO baseDataDefine = BaseDataDefineCache.getBaseDataDefine((String)dataFieldDefine.getRefTableName());
        if (baseDataDefine == null) {
            logger.info("\u672a\u627e\u5230\u57fa\u7840\u6570\u636e\u5b9a\u4e49:{}", (Object)dataFieldDefine.getRefTableName());
            return null;
        }
        BaseDataDTO baseDataDTO = new BaseDataDTO();
        baseDataDTO.setTableName(baseDataDefine.getName());
        if (baseDataDefine.getStructtype() != 0) {
            Map shareFieldMapping = dataFieldDefine.getShareFieldMapping();
            if (!CollectionUtils.isEmpty(shareFieldMapping)) {
                for (Map.Entry entry : shareFieldMapping.entrySet()) {
                    Object o2;
                    String field = (String)entry.getKey();
                    String fieldValue = (String)entry.getValue();
                    if (StringUtils.hasText(fieldValue)) {
                        o2 = map.get(fieldValue);
                        if (o2 == null) {
                            o2 = master.getValue(field);
                        }
                        baseDataDTO.put(field.toLowerCase(), o2);
                        continue;
                    }
                    o2 = map.get(field);
                    if (o2 == null) {
                        o2 = master.getValue(field);
                    }
                    baseDataDTO.put(field.toLowerCase(), o2);
                }
            } else {
                baseDataDTO.setUnitcode(master.getString("UNITCODE"));
            }
        }
        if ((bizDate = master.getDate("BILLDATE")) == null) {
            bizDate = Env.getBizDate();
        }
        baseDataDTO.setVersionDate(bizDate);
        baseDataDTO.setAuthType(((DataFieldDefineImpl)dataFieldDefine).isIgnorePermission() ? BaseDataOption.AuthType.NONE : BaseDataOption.AuthType.ACCESS);
        if (dataFieldDefine.isQueryStop()) {
            baseDataDTO.setStopflag(Integer.valueOf(-1));
        }
        if (Utils.isEmpty((String)baseDataDTO.getTenantName())) {
            baseDataDTO.setTenantName(Env.getTenantName());
        }
        if ("1".equals(importType = (String)((Map)templateField.get(0)).get("importType"))) {
            baseDataDTO.setCode(value);
        } else if ("2".equals(importType)) {
            baseDataDTO.setName(value);
        } else {
            String[] values = value.split(" ", 2);
            if (values.length < 2) {
                return BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.importformat");
            }
            String code = values[1];
            baseDataDTO.setCode(code);
            PageVO list = this.baseDataClient.list(new BaseDataDTO((Map)baseDataDTO));
            if (CollectionUtils.isEmpty(list.getRows())) {
                baseDataDTO.setCode(values[0]);
            } else {
                logger.info("\u67e5\u8be2\u5230\u6570\u636e:{}", (Object)StringUtils.collectionToCommaDelimitedString(list.getRows().stream().map(BaseDataDO::getObjectcode).collect(Collectors.toList())));
                return ((BaseDataDO)list.getRows().get(0)).getObjectcode();
            }
        }
        PageVO list = this.baseDataClient.list(new BaseDataDTO((Map)baseDataDTO));
        if (CollectionUtils.isEmpty(list.getRows())) {
            logger.info("\u672a\u627e\u5230\u57fa\u7840\u6570\u636e:{}", (Object)value);
            return null;
        }
        logger.info("\u67e5\u8be2\u5230\u6570\u636e:{}", (Object)StringUtils.collectionToCommaDelimitedString(list.getRows().stream().map(BaseDataDO::getObjectcode).collect(Collectors.toList())));
        return ((BaseDataDO)list.getRows().get(0)).getObjectcode();
    }

    private int getKeyIndex(List<List<Object>> primaryValues, List<Object> keys) {
        for (int i = 0; i < primaryValues.size(); ++i) {
            List<Object> primaryValue = primaryValues.get(i);
            boolean flag = true;
            for (int i1 = 0; i1 < primaryValue.size(); ++i1) {
                Object o = primaryValue.get(i1);
                if (o == null) {
                    if (keys.get(i1) == null) continue;
                    flag = false;
                    break;
                }
                if (primaryValue.get(i1) instanceof BigDecimal) {
                    int i2 = ((BigDecimal)primaryValue.get(i1)).compareTo((BigDecimal)keys.get(i1));
                    if (i2 == 0) continue;
                    flag = false;
                    break;
                }
                if (primaryValue.get(i1).equals(keys.get(i1))) continue;
                flag = false;
                break;
            }
            if (!flag) continue;
            return i;
        }
        return -1;
    }

    private boolean checkFieldDigits(DataFieldDefine field, String fieldValue) {
        if (fieldValue == null || fieldValue.isEmpty()) {
            return true;
        }
        int digits = field.getDigits();
        if (digits == 0) {
            return true;
        }
        try {
            BigDecimal value = new BigDecimal(fieldValue);
            BigDecimal roundedValue = value.setScale(digits, RoundingMode.DOWN);
            BigDecimal difference = value.subtract(roundedValue);
            return difference.compareTo(BigDecimal.ZERO) == 0;
        }
        catch (NumberFormatException e) {
            logger.error("Invalid number format for fieldValue: {}", (Object)fieldValue, (Object)e);
            return false;
        }
    }

    @Override
    public List<Map<String, Object>> saveExcelData(BillModel model, Map<String, Object> params) {
        List appendData = (List)params.get("appendData");
        String tableName = (String)params.get("tableName");
        BillModelImpl billModel = (BillModelImpl)model;
        DataTable table = billModel.getTable(tableName);
        boolean importType = false;
        if (params.get("importType") != null) {
            importType = (Boolean)params.get("importType");
        }
        if (!importType) {
            for (int i = table.getRows().size() - 1; i >= 0; --i) {
                table.deleteRow(i);
            }
        }
        List tableData = (List)params.get("tableData");
        for (Map tableDatum : tableData) {
            tableDatum.put(STATE, 0);
            tableDatum.put(STATEMESSAGE, BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.dataimportfailed"));
        }
        String curTimezone = (String)params.get("curTimezone");
        NamedContainer fields = table.getDefine().getFields();
        for (Map data : appendData) {
            try {
                int index;
                DataRow dataRow;
                if (data.get(ORIGINROWID) != null) {
                    dataRow = table.getRowById((Object)UUID.fromString((String)data.get(ORIGINROWID)));
                    BillSublistImportServiceImpl.setRowValue(data, (NamedContainer<? extends DataFieldDefine>)fields, curTimezone, dataRow);
                    index = (Integer)data.get(INDEX) - 1;
                    ((Map)tableData.get(index)).put(STATEMESSAGE, BillCoreI18nUtil.getMessage("va.bill.core.data.update.success"));
                    ((Map)tableData.get(index)).put(STATE, 1);
                    continue;
                }
                dataRow = table.appendRow();
                BillSublistImportServiceImpl.setRowValue(data, (NamedContainer<? extends DataFieldDefine>)fields, curTimezone, dataRow);
                index = (Integer)data.get(INDEX) - 1;
                ((Map)tableData.get(index)).put(STATEMESSAGE, BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.dataimportsuccess"));
                ((Map)tableData.get(index)).put(STATE, 1);
            }
            catch (Exception e) {
                logger.error("\u5b50\u8868\u5bfc\u5165\u5f02\u5e38" + e.getMessage(), e);
            }
        }
        return tableData;
    }

    private static void setRowValue(Map<String, Object> data, NamedContainer<? extends DataFieldDefine> fields, String curTimezone, DataRow dataRow) {
        for (String s : data.keySet()) {
            Object o;
            DataFieldDefine dataFieldDefine;
            if (INDEX.equals(s) || ORIGINROWID.equals(s) || (dataFieldDefine = (DataFieldDefine)fields.get(s)).getMaskFlag() && String.valueOf(o = data.get(s)).contains("*")) continue;
            if (curTimezone != null && ValueType.DATETIME.equals((Object)((DataFieldDefine)fields.get(s)).getValueType())) {
                dataRow.setValue(s, (Object)BillUtils.convertToShanghaiTime((String)data.get(s), curTimezone));
                continue;
            }
            dataRow.setValue(s, data.get(s));
        }
    }

    private boolean checkRefDataFilter(String fieldValue, IExpression expression, DataRowImpl appendRow, BillModel model, DataFieldDefine field, Map<String, Map<String, Map<String, Object>>> refDataValuesCache, String tableName, Map<String, List<DataModelColumn>> refDataFieldsTypeCache) {
        ModelDataContext context = new ModelDataContext((Model)model);
        Map<String, Object> refDataValues = refDataValuesCache.get(field.getRefTableName()).get(fieldValue);
        expression.forEach(node -> {
            if (node instanceof ModelParamNode) {
                String name = ((ModelParamNode)node).getName().toUpperCase();
                if (filedTypes.contains(name) || field.getRefTableType() == 2 && (name.equals("VAL") || name.equals("DESCRIPTION"))) {
                    context.setFieldValueType(name, ValueType.STRING);
                    context.put(name, refDataValues.get(name.toLowerCase()));
                } else {
                    String refTableName = field.getRefTableName();
                    if (!refDataFieldsTypeCache.containsKey(refTableName)) {
                        DataModelDTO dataModelDTO = new DataModelDTO();
                        dataModelDTO.setName(refTableName);
                        DataModelDO dataModelDO = this.dataModelClient.get(dataModelDTO);
                        if (dataModelDO != null) {
                            refDataFieldsTypeCache.put(refTableName, dataModelDO.getColumns());
                        } else {
                            BaseDataDefineDTO baseDataDefineDTO = new BaseDataDefineDTO();
                            baseDataDefineDTO.setName(refTableName);
                            BaseDataDefineDO baseDataDefineDO = this.baseDataDefineClient.get(baseDataDefineDTO);
                            if (baseDataDefineDO == null) {
                                logger.error("\u672a\u67e5\u8be2\u5230\u5f53\u524d\u5f15\u7528\u6570\u636e\u6a21\u578b\u5b9a\u4e49:" + refTableName);
                                context.put(name, refDataValues.get(name.toLowerCase()));
                                refDataFieldsTypeCache.put(refTableName, new ArrayList());
                                return;
                            }
                            Object columns = JSONUtil.parseMap((String)baseDataDefineDO.getDefine()).get("columns");
                            if (!ObjectUtils.isEmpty(columns)) {
                                List columnsDefine = (List)columns;
                                ArrayList<DataModelColumn> dataModelColumns = new ArrayList<DataModelColumn>();
                                for (Map map : columnsDefine) {
                                    DataModelColumn dataModelColumn = new DataModelColumn();
                                    dataModelColumn.setColumnName(BillUtils.valueToString(map.get("columnName")));
                                    dataModelColumn.setColumnTitle(BillUtils.valueToString(map.get("columnTitle")));
                                    dataModelColumn.setColumnType(DataModelType.ColumnType.valueOf((String)BillUtils.valueToString(map.get("columnType"))));
                                    dataModelColumns.add(dataModelColumn);
                                }
                                refDataFieldsTypeCache.put(refTableName, dataModelColumns);
                            } else {
                                context.put(name, refDataValues.get(name.toLowerCase()));
                                refDataFieldsTypeCache.put(refTableName, new ArrayList());
                                logger.error("\u672a\u67e5\u8be2\u5230\u5f53\u524d\u5f15\u7528\u6570\u636e\u6a21\u578b\u5b9a\u4e49\u4e2d\u5b57\u6bb5\u7c7b\u578b:" + refTableName);
                                return;
                            }
                        }
                    }
                    List dataModelColumns = (List)refDataFieldsTypeCache.get(refTableName);
                    for (DataModelColumn dataModelColumn : dataModelColumns) {
                        if (!dataModelColumn.getColumnName().equals(name)) continue;
                        context.setFieldValueType(name, this.getValueType(dataModelColumn.getColumnType()));
                        context.put(name, refDataValues.get(name.toLowerCase()));
                        return;
                    }
                    context.put(name, refDataValues.get(name.toLowerCase()));
                }
            }
        });
        Map<String, DataRow> rowMap = Stream.of(appendRow).collect(Collectors.toMap(k -> tableName, v -> v));
        FormulaUtils.adjustFormulaRows((Data)((Data)model.getPlugins().get(Data.class)), rowMap);
        for (String s : rowMap.keySet()) {
            context.put(s, (Object)rowMap.get(s));
        }
        try {
            return expression.judge((IContext)context);
        }
        catch (SyntaxException e) {
            logger.error("\u516c\u5f0f\u6267\u884c\u5931\u8d25\uff1a" + e.getMessage(), e);
            return false;
        }
    }

    private ValueType getValueType(DataModelType.ColumnType columnType) {
        switch (columnType) {
            case NUMERIC: {
                return ValueType.DECIMAL;
            }
            case UUID: {
                return ValueType.IDENTIFY;
            }
            case NVARCHAR: {
                return ValueType.STRING;
            }
            case INTEGER: {
                return ValueType.INTEGER;
            }
            case DATE: {
                return ValueType.DATE;
            }
            case TIMESTAMP: {
                return ValueType.DATETIME;
            }
            case CLOB: {
                return ValueType.TEXT;
            }
        }
        return ValueType.AUTO;
    }

    private Object getViewValue(String value, String option, DataFieldDefine field, BillModelImpl model, HashMap<String, HashMap<String, Object>> cash, Map<String, Map<String, Map<String, Object>>> refDataValuesCache, DataRowImpl row) {
        int refTableType = field.getRefTableType();
        String refTableName = field.getRefTableName();
        String cashKey = field.getRefTableName() + ":" + field.getFieldName();
        if (!cash.containsKey(cashKey)) {
            cash.put(cashKey, new HashMap());
        }
        if (!refDataValuesCache.containsKey(refTableName)) {
            refDataValuesCache.put(refTableName, new HashMap());
        }
        HashMap<String, Object> table = cash.get(cashKey);
        Map<String, Map<String, Object>> valuesCache = refDataValuesCache.get(refTableName);
        Object viewValue = null;
        if ("1".equals(option)) {
            String code = value;
            if (!table.containsKey(code)) {
                Map refValue;
                if (field.getRefTableType() == 1) {
                    Object baseDataValue = this.getBaseDataValue(code, field, row, option, model);
                    if (baseDataValue instanceof String) {
                        return baseDataValue;
                    }
                    refValue = (Map)baseDataValue;
                } else {
                    refValue = model.getRefValue(refTableType, refTableName, code);
                }
                if (refValue == null) {
                    return BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.absent");
                }
                valuesCache.put(code, refValue);
                viewValue = model.toViewValue(refTableType, refTableName, field.getShowType(), refValue);
                table.put(code, viewValue);
            } else {
                viewValue = table.get(code);
            }
        } else if ("2".equals(option)) {
            if (!table.containsKey(value)) {
                Map map;
                if (field.getRefTableType() == 1) {
                    Object baseDataValue = this.getBaseDataValue(value, field, row, option, model);
                    if (baseDataValue instanceof String) {
                        return baseDataValue;
                    }
                    map = (Map)baseDataValue;
                } else {
                    map = model.matchValue(refTableType, refTableName, value);
                }
                if (map == null) {
                    return BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.absent");
                }
                valuesCache.put(value, map);
                viewValue = model.toViewValue(refTableType, refTableName, field.getShowType(), map);
                table.put(value, viewValue);
            } else {
                viewValue = table.get(value);
            }
        } else if ("3".equals(option)) {
            String[] values = value.split(" ", 2);
            if (values.length < 2) {
                return BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.importformat");
            }
            String code = values[0];
            if (!table.containsKey(value)) {
                Map refValue;
                if (field.getRefTableType() == 1) {
                    Object baseDataValue = this.getBaseDataValue(value, field, row, option, model);
                    if (baseDataValue instanceof String) {
                        return baseDataValue;
                    }
                    refValue = (Map)baseDataValue;
                } else {
                    refValue = model.getRefValue(refTableType, refTableName, code);
                }
                if (refValue == null) {
                    return BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.absent");
                }
                valuesCache.put(value, refValue);
                viewValue = model.toViewValue(refTableType, refTableName, field.getShowType(), refValue);
                if (viewValue != null && !values[1].equals(((Map)viewValue).get("title"))) {
                    viewValue = null;
                }
                table.put(value, viewValue);
            } else {
                viewValue = table.get(value);
            }
        } else if (!table.containsKey(value)) {
            Map result;
            if (field.getRefTableType() == 1) {
                Object baseDataValue = this.getBaseDataValue(value, field, row, option, model);
                if (baseDataValue instanceof String) {
                    return baseDataValue;
                }
                result = (Map)baseDataValue;
            } else {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(option, value);
                result = model.findRefObjectsByParam(refTableType, refTableName, map).findFirst().orElse(null);
            }
            if (result == null) {
                return BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.absent");
            }
            valuesCache.put(value, result);
            viewValue = model.toViewValue(refTableType, refTableName, field.getShowType(), result);
            table.put(value, viewValue);
        } else {
            viewValue = table.get(value);
        }
        return viewValue;
    }

    private Object getBaseDataValue(String value, DataFieldDefine field, DataRowImpl row, String option, BillModelImpl model) {
        PageVO list = new PageVO(true);
        String mdControl = ((DataFieldDefineImpl)field).getMdControl();
        boolean isOnlyLeaf = mdControl != null && mdControl.equals("ONLYLEAF");
        boolean isOnlyNotLeaf = mdControl != null && mdControl.equals("ONLYNOTLEAF");
        BaseDataDTO baseDataDTO = this.setBaseDataDTO(field, row, model, isOnlyLeaf || isOnlyNotLeaf);
        if ("1".equals(option)) {
            if (StringUtils.hasText(value) && value.contains("||")) {
                baseDataDTO.setObjectcode(value);
            } else {
                baseDataDTO.setCode(value);
            }
        } else if ("2".equals(option)) {
            baseDataDTO.setName(value);
        } else if ("3".equals(option)) {
            String[] values = value.split(" ", 2);
            if (values.length < 2) {
                return BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.importformat");
            }
            String code = values[1];
            baseDataDTO.setCode(code);
            list = this.baseDataClient.list(new BaseDataDTO((Map)baseDataDTO));
            if (CollectionUtils.isEmpty(list.getRows())) {
                baseDataDTO.setCode(values[0]);
            }
        } else {
            baseDataDTO.put(option.toLowerCase(), (Object)value);
        }
        if (CollectionUtils.isEmpty(list.getRows())) {
            list = this.baseDataClient.list(new BaseDataDTO((Map)baseDataDTO));
        }
        if (CollectionUtils.isEmpty(list.getRows())) {
            return BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.absent");
        }
        if (list.getRows().size() > 1) {
            return BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.matchmultiple");
        }
        baseDataDTO.setObjectcode(((BaseDataDO)list.getRows().get(0)).getObjectcode());
        PageVO<BaseDataDO> baseDataDOPageVO = this.searchBaseDataByObjectCode(baseDataDTO);
        if (CollectionUtils.isEmpty(baseDataDOPageVO.getRows())) {
            return BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.filtered");
        }
        Map baseDataDO = (Map)baseDataDOPageVO.getRows().get(0);
        if (!this.checkBaseDataMdControl(isOnlyLeaf, isOnlyNotLeaf, baseDataDO.get("isLeaf"))) {
            return BillCoreI18nUtil.getMessage("va.billcore.subtableimportaction.filtered");
        }
        return baseDataDO;
    }

    private BaseDataDTO setBaseDataDTO(DataFieldDefine field, DataRowImpl row, BillModelImpl model, boolean isLeaf) {
        BaseDataDTO basedataDTO = new BaseDataDTO();
        basedataDTO.setTableName(field.getRefTableName());
        basedataDTO.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        Map<String, Object> dimValues = model.getDimValues(field, (DataRow)row);
        basedataDTO.setVersionDate((Date)dimValues.get("BIZDATE"));
        for (Map.Entry<String, Object> entry : dimValues.entrySet()) {
            if (entry.getKey().equals("BIZDATE")) continue;
            basedataDTO.put(entry.getKey().toLowerCase(), entry.getValue());
        }
        basedataDTO.setAuthType(((DataFieldDefineImpl)field).isIgnorePermission() ? BaseDataOption.AuthType.NONE : BaseDataOption.AuthType.ACCESS);
        basedataDTO.setLeafFlag(Boolean.valueOf(isLeaf));
        if (field.isQueryStop()) {
            basedataDTO.setStopflag(Integer.valueOf(-1));
        }
        if (Utils.isEmpty((String)basedataDTO.getTenantName())) {
            basedataDTO.setTenantName(Env.getTenantName());
        }
        return basedataDTO;
    }

    private PageVO<BaseDataDO> searchBaseDataByObjectCode(BaseDataDTO basedataDTO) {
        BaseDataDefineDO baseDataDefine = BaseDataDefineCache.getBaseDataDefine((String)basedataDTO.getTableName());
        if (baseDataDefine == null) {
            return new PageVO(true);
        }
        if (baseDataDefine.getSharetype() == 0) {
            return this.baseDataClient.list(basedataDTO);
        }
        basedataDTO.put("shareForceCheck", (Object)true);
        if (baseDataDefine.getSharetype() == 3) {
            basedataDTO.put("shareUnitcodes", Arrays.asList(basedataDTO.get((Object)"UNITCODE".toLowerCase()), "-"));
        } else if (baseDataDefine.getSharetype() == 2) {
            OrgDTO orgDTO = new OrgDTO();
            orgDTO.setCode((String)basedataDTO.get((Object)"UNITCODE".toLowerCase()));
            orgDTO.setQueryParentType(OrgDataOption.QueryParentType.ALL_PARENT);
            orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
            orgDTO.setStopflag(Integer.valueOf(-1));
            PageVO list = this.orgDataClient.list(orgDTO);
            if (!CollectionUtils.isEmpty(list.getRows())) {
                List unitcodeList = list.getRows().stream().map(o -> o.getCode()).collect(Collectors.toList());
                unitcodeList.add((String)basedataDTO.get((Object)"UNITCODE".toLowerCase()));
                basedataDTO.put("shareUnitcodes", unitcodeList);
            } else {
                basedataDTO.put("shareUnitcodes", Arrays.asList(basedataDTO.get((Object)"UNITCODE".toLowerCase())));
            }
        } else {
            basedataDTO.put("shareUnitcodes", Arrays.asList(basedataDTO.get((Object)"UNITCODE".toLowerCase())));
        }
        return this.baseDataClient.list(basedataDTO);
    }

    private boolean checkBaseDataMdControl(boolean isOnlyLeaf, boolean isOnlyNotLeaf, Object isLeaf) {
        if (isOnlyLeaf) {
            if (isLeaf == null) {
                return false;
            }
            return (Boolean)isLeaf;
        }
        if (isOnlyNotLeaf) {
            if (isLeaf == null) {
                return false;
            }
            return (Boolean)isLeaf == false;
        }
        return true;
    }
}

