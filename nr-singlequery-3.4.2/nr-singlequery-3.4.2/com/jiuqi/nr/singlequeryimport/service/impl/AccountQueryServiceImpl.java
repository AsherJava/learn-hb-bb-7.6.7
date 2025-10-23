/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.dataset.model.field.FieldType
 *  com.jiuqi.bi.dataset.report.builder.ReportDSModelBuilder
 *  com.jiuqi.bi.dataset.report.model.DefaultValueMode
 *  com.jiuqi.bi.dataset.report.model.ReportDsModelDefine
 *  com.jiuqi.bi.dataset.report.model.ReportDsParameter
 *  com.jiuqi.bi.dataset.report.model.ReportExpField
 *  com.jiuqi.bi.dataset.report.model.ReportFieldType
 *  com.jiuqi.bi.dataset.report.query.ReportQueryExecutor
 *  com.jiuqi.bi.dataset.report.remote.controller.vo.PreviewResultVo
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.grid.ncell.GridDataConverter
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.designer.service.StepSaveService
 *  com.jiuqi.nr.designer.web.rest.FormulaParserController
 *  com.jiuqi.nr.designer.web.treebean.TaskLinkObject
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper
 *  com.jiuqi.nr.fmdm.internal.service.ZBQueryService
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 *  com.jiuqi.nr.period.common.utils.StringUtils
 *  com.jiuqi.nr.zbquery.model.ConditionValues
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGriddataConverter
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.singlequeryimport.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.dataset.model.field.FieldType;
import com.jiuqi.bi.dataset.report.builder.ReportDSModelBuilder;
import com.jiuqi.bi.dataset.report.model.DefaultValueMode;
import com.jiuqi.bi.dataset.report.model.ReportDsModelDefine;
import com.jiuqi.bi.dataset.report.model.ReportDsParameter;
import com.jiuqi.bi.dataset.report.model.ReportExpField;
import com.jiuqi.bi.dataset.report.model.ReportFieldType;
import com.jiuqi.bi.dataset.report.query.ReportQueryExecutor;
import com.jiuqi.bi.dataset.report.remote.controller.vo.PreviewResultVo;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.ncell.GridDataConverter;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.designer.service.StepSaveService;
import com.jiuqi.nr.designer.web.rest.FormulaParserController;
import com.jiuqi.nr.designer.web.treebean.TaskLinkObject;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.fmdm.internal.service.ZBQueryService;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nr.period.common.utils.StringUtils;
import com.jiuqi.nr.singlequeryimport.auth.service.impl.FinalaccountQueryAuthServiceImpl;
import com.jiuqi.nr.singlequeryimport.auth.share.service.AuthShareService;
import com.jiuqi.nr.singlequeryimport.bean.ModalColWarningInfo;
import com.jiuqi.nr.singlequeryimport.bean.ParamVo.QueryResultVo;
import com.jiuqi.nr.singlequeryimport.bean.QueryConfigInfo;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.bean.QueryResult;
import com.jiuqi.nr.singlequeryimport.bean.WarningInfo;
import com.jiuqi.nr.singlequeryimport.service.AccountQueryService;
import com.jiuqi.nr.singlequeryimport.service.QueryModleService;
import com.jiuqi.nr.singlequeryimport.service.cache.GroupSetQueryResultCacheHandler;
import com.jiuqi.nr.singlequeryimport.service.impl.SinglerQuserServiceImpl;
import com.jiuqi.nr.singlequeryimport.utils.EmptyStringEnum;
import com.jiuqi.nr.singlequeryimport.utils.FileUtil;
import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nvwa.cellbook.converter.CellBookGriddataConverter;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountQueryServiceImpl
implements AccountQueryService {
    private static final Logger logger = LoggerFactory.getLogger(AccountQueryServiceImpl.class);
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    ReportDSModelBuilder reportDSModelBuilder;
    @Autowired
    IEntityMetaService entityMetaService;
    @Autowired
    IRunTimeViewController iRunTimeViewController;
    @Autowired
    IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    StepSaveService stepSaveService;
    @Autowired
    SinglerQuserServiceImpl service;
    @Autowired
    IEntityMetaService iEntityMetaService;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    ZBQueryService zbQueryService;
    @Autowired
    IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    FinalaccountQueryAuthServiceImpl finalaccountQueryAuthService;
    @Autowired
    AuthShareService authShareService;
    @Autowired
    QueryModleService queryModleService;
    @Autowired
    EntityQueryHelper entityQueryHelper;
    @Autowired
    FormulaParserController formulaParserController;
    @Autowired
    GroupSetQueryResultCacheHandler groupSetQueryResultCacheHandler;
    @Autowired
    IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private ReportQueryExecutor executor;

    public static BigDecimal isNum(Object value) {
        if (null != value) {
            try {
                if (value instanceof BigDecimal) {
                    String data = value.toString();
                    return new BigDecimal(data);
                }
                return new BigDecimal(0.0);
            }
            catch (Exception e) {
                return new BigDecimal(0.0);
            }
        }
        new BigDecimal(0.0);
        return new BigDecimal(0.0);
    }

    @Override
    public QueryResult search(List<String> expList, String filter, Map<String, Integer> order, String taskKey, QueryConfigInfo queryConfigInfo, JSONObject model, List<Map<String, String>> expressionColumnList, Map<String, List<Integer>> groupSet, List<ModalColWarningInfo> currenPageForewarn, List<ModalColWarningInfo> allPageForewarn) throws Exception {
        QueryResultVo initResult = new QueryResultVo();
        Boolean isGroupSet = false;
        Map<String, String> taskInfo = this.getTaskInfo(taskKey, queryConfigInfo.getModelId());
        if (StringUtils.isEmpty((String)queryConfigInfo.getCacheId())) {
            String cacheID = UUID.randomUUID().toString();
            ReportDsModelDefine reportDsModelDefine = this.createReportDsModelDefine(expList, filter, taskInfo, queryConfigInfo);
            PreviewResultVo preview = this.executor.preview(reportDsModelDefine, -1, 0);
            initResult.setResult(preview.getResult());
            initResult.setTotalCount(preview.getTotalCount());
            this.groupSetQueryResultCacheHandler.setQueryDataToCache(cacheID, initResult);
            queryConfigInfo.setCacheId(cacheID);
        } else {
            initResult = this.groupSetQueryResultCacheHandler.getQueryDataFromCache(queryConfigInfo.getCacheId());
        }
        if (null != initResult && initResult.getTotalCount() > 0) {
            if (!order.isEmpty()) {
                this.sort(initResult.getResult(), order.get("index"), order.get("orderType"));
            }
            List<List<Object>> resultData = this.changeDataFormat(initResult);
            Map<String, List<WarningInfo>> forewarn = this.allPageForewarn(resultData, allPageForewarn, expList, queryConfigInfo);
            if (queryConfigInfo.getTotalLine().booleanValue() && !resultData.isEmpty()) {
                this.totalLine(resultData);
            }
            if (!groupSet.isEmpty()) {
                ArrayList<Integer> levels = new ArrayList<Integer>();
                String groupByCode = null;
                for (String key : groupSet.keySet()) {
                    levels.addAll((Collection)groupSet.get(key));
                    groupByCode = key;
                }
                ArrayList<List<Object>> groupData = new ArrayList<List<Object>>();
                Integer rowLength = resultData.get(0).size();
                Map<String, String> emnuList = this.getEmnuLsit(taskInfo, groupByCode);
                String finalGroupByCode = groupByCode;
                String index = (String)expressionColumnList.stream().filter(e -> ((String)e.get("data")).equals(finalGroupByCode)).findFirst().get().get("index");
                int zbIndex = queryConfigInfo.getColumnNumber() != false ? Integer.parseInt(index) + 1 : Integer.parseInt(index) + 2;
                this.groupData(this.groupBySet(resultData, zbIndex, levels), groupData, rowLength, emnuList, (Integer)levels.get(levels.size() - 1), 0, zbIndex);
                resultData = groupData;
            }
            resultData = this.getResultByPage(resultData, queryConfigInfo);
            this.buildTree(resultData, taskInfo, isGroupSet == false, queryConfigInfo);
            if (null != queryConfigInfo.getIndexSelectList() && !queryConfigInfo.getIndexSelectList().isEmpty()) {
                BigDecimal divideValue = new BigDecimal(Integer.parseInt(null == queryConfigInfo.getDivideValue() ? "1" : queryConfigInfo.getDivideValue()));
                this.amountConversion(queryConfigInfo.getIndexSelectList(), expressionColumnList, divideValue, resultData, queryConfigInfo.getColumnNumber());
            }
            if (queryConfigInfo.getColumnNumber().booleanValue()) {
                this.addColumnNumber(resultData, queryConfigInfo.getTotalLine(), queryConfigInfo.getHasName());
            }
            if (queryConfigInfo.getZeroEmpty().booleanValue()) {
                this.ZeroEmpty(resultData);
            }
            this.currenPageForewarn(resultData, currenPageForewarn, expList, queryConfigInfo.getColumnNumber(), forewarn);
            JSONArray decimalPlaceList = model.getJSONArray("data").getJSONArray(model.getJSONArray("data").length() - 1);
            JSONArray dataToJson = this.saveDataToJson(decimalPlaceList, resultData, queryConfigInfo.getColumnNumber());
            QueryResult result = this.result(model, queryConfigInfo, dataToJson, expressionColumnList, forewarn);
            result.setPageInfo(queryConfigInfo.getPageInfo());
            result.setCacheId(queryConfigInfo.getCacheId());
            return result;
        }
        QueryResult result = this.result(model, queryConfigInfo, new JSONArray(), expressionColumnList, null);
        result.setPageInfo(queryConfigInfo.getPageInfo());
        return result;
    }

    Map<String, List<WarningInfo>> allPageForewarn(List<List<Object>> resultData, List<ModalColWarningInfo> forewarnConditionList, List<String> expList, QueryConfigInfo queryConfigInfo) {
        LinkedHashMap<String, List<WarningInfo>> result = new LinkedHashMap<String, List<WarningInfo>>();
        Integer order = 2;
        if (queryConfigInfo.getColumnNumber().booleanValue()) {
            order = 1;
        }
        if (!resultData.isEmpty()) {
            for (ModalColWarningInfo forewarnCondition : forewarnConditionList) {
                int index;
                int pageIndex;
                ArrayList<WarningInfo> infos = new ArrayList<WarningInfo>();
                List re = null;
                if (forewarnCondition.getSymbol().equals("MAX")) {
                    re = resultData.stream().max(Comparator.comparing(list -> new BigDecimal(list.get(expList.indexOf(forewarnCondition.getName())) == null ? "0" : list.get(expList.indexOf(forewarnCondition.getName())).toString()))).get();
                }
                if (forewarnCondition.getSymbol().equals("MIN")) {
                    re = resultData.stream().min(Comparator.comparing(list -> new BigDecimal(list.get(expList.indexOf(forewarnCondition.getName())) == null ? "0" : list.get(expList.indexOf(forewarnCondition.getName())).toString()))).get();
                }
                if (null != re && (pageIndex = (index = resultData.indexOf(re)) / queryConfigInfo.getPageInfo().getPageSize() + 1) == queryConfigInfo.getPageInfo().getPageIndex()) {
                    int currenPageIndex = queryConfigInfo.getTotalLine() != false ? index % queryConfigInfo.getPageInfo().getPageSize() + 1 : index % queryConfigInfo.getPageInfo().getPageSize();
                    infos.add(new WarningInfo(currenPageIndex, expList.indexOf(forewarnCondition.getName()) - order, forewarnCondition.getColor()));
                }
                result.put(forewarnCondition.getName() + forewarnCondition.getSymbol(), infos);
            }
        }
        return result;
    }

    void currenPageForewarn(List<List<Object>> resultData, List<ModalColWarningInfo> forewarnConditionList, List<String> expList, Boolean columnNumber, Map<String, List<WarningInfo>> forewarnResult) {
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        expList.remove(0);
        if (!columnNumber.booleanValue()) {
            expList.remove(0);
        }
        if (!resultData.isEmpty()) {
            for (ModalColWarningInfo forewarnCondition : forewarnConditionList) {
                if (null == forewarnCondition.getName() || null == forewarnCondition.getSymbol()) continue;
                ArrayList<WarningInfo> infos = new ArrayList<WarningInfo>();
                for (List<Object> datum : resultData) {
                    if (!expList.contains(forewarnCondition.getName())) continue;
                    BigDecimal bigDecimal = null;
                    int index = expList.indexOf(forewarnCondition.getName());
                    if (!(datum.get(expList.indexOf(forewarnCondition.getName())) instanceof BigDecimal)) continue;
                    BigDecimal dataValue = (BigDecimal)datum.get(expList.indexOf(forewarnCondition.getName()));
                    String value = forewarnCondition.getValue();
                    if (pattern.matcher(value).matches()) {
                        bigDecimal = new BigDecimal(forewarnCondition.getValue());
                    } else if (expList.contains(forewarnCondition.getValue())) {
                        bigDecimal = (BigDecimal)datum.get(expList.indexOf(forewarnCondition.getValue()));
                    }
                    if (null == bigDecimal) continue;
                    int sign = dataValue.compareTo(bigDecimal);
                    switch (forewarnCondition.getSymbol()) {
                        case ">": {
                            if (sign <= 0) break;
                            infos.add(new WarningInfo(resultData.indexOf(datum), index, forewarnCondition.getColor()));
                            break;
                        }
                        case "<": {
                            if (sign >= 0) break;
                            infos.add(new WarningInfo(resultData.indexOf(datum), index, forewarnCondition.getColor()));
                            break;
                        }
                        case "=": {
                            if (sign != 0) break;
                            infos.add(new WarningInfo(resultData.indexOf(datum), index, forewarnCondition.getColor()));
                            break;
                        }
                        case ">=": {
                            if (sign < 0) break;
                            infos.add(new WarningInfo(resultData.indexOf(datum), index, forewarnCondition.getColor()));
                            break;
                        }
                        case "<=": {
                            if (sign > 0) break;
                            infos.add(new WarningInfo(resultData.indexOf(datum), index, forewarnCondition.getColor()));
                            break;
                        }
                    }
                }
                forewarnResult.put(forewarnCondition.getName() + forewarnCondition.getSymbol() + forewarnCondition.getColor(), infos);
            }
        }
    }

    List<List<Object>> changeDataFormat(QueryResultVo result) {
        ArrayList<List<Object>> dataList = new ArrayList<List<Object>>();
        result.getResult().stream().forEach(e -> dataList.add(new ArrayList<Object>(Arrays.asList(e))));
        return dataList;
    }

    Object groupBySet(List<List<Object>> resultData, Integer index, List<Integer> levels) {
        Map<String, Map<String, Object>> groupData = new Map<String, Map<String, List<List>>>();
        List collect = resultData.stream().filter(data -> null == data.get(index) || data.get(index).toString().isEmpty() || data.get(index).toString().length() < (Integer)levels.get(levels.size() - 1)).collect(Collectors.toList());
        resultData.removeAll(collect);
        if (levels.size() == 2) {
            groupData = resultData.stream().collect(Collectors.groupingBy(data -> data.get(index).toString().substring(0, (Integer)levels.get(0)), Collectors.groupingBy(data -> data.get(index).toString().substring(0, (Integer)levels.get(1)))));
        }
        if (levels.size() == 3) {
            groupData = resultData.stream().collect(Collectors.groupingBy(data -> data.get(index).toString().substring(0, (Integer)levels.get(0)), Collectors.groupingBy(data -> data.get(index).toString().substring(0, (Integer)levels.get(1)), Collectors.groupingBy(data -> data.get(index).toString().substring(0, (Integer)levels.get(2))))));
        }
        if (levels.size() == 4) {
            groupData = resultData.stream().collect(Collectors.groupingBy(data -> data.get(index).toString().substring(0, (Integer)levels.get(0)), Collectors.groupingBy(data -> data.get(index).toString().substring(0, (Integer)levels.get(1)), Collectors.groupingBy(data -> data.get(index).toString().substring(0, (Integer)levels.get(2)), Collectors.groupingBy(data -> data.get(index).toString().substring(0, (Integer)levels.get(3)))))));
        }
        if (collect.size() > 0) {
            groupData.put("noGroup", (Map<String, List<List>>)((Object)collect));
        }
        return groupData;
    }

    void groupData(Object groupData, List<List<Object>> groupDataList, Integer rowLength, Map<String, String> emnuLsit, Integer levelLength, Integer emptyLength, Integer zbIndex) {
        Set levelCodes = ((Map)groupData).keySet();
        for (String Key : levelCodes) {
            String per;
            Object o = ((Map)groupData).get(Key);
            if (o instanceof Map) {
                per = Key;
                Set nextLevelCodes = ((Map)o).keySet();
                List<Object> row = this.createRow(rowLength);
                while (per.length() < levelLength) {
                    per = per + "0";
                }
                String value = Key + "|" + emnuLsit.get(per);
                String emptyString = EmptyStringEnum.getEmptyString(emptyLength, value);
                row.set(2, emptyString);
                groupDataList.add(row);
                Integer lastLevelEmptyLength = emptyLength + 1;
                this.groupData(o, groupDataList, rowLength, emnuLsit, levelLength, lastLevelEmptyLength, zbIndex);
                List collect = groupDataList.stream().filter(d -> nextLevelCodes.contains(d.get(2).toString().trim().split("\\|")[0])).collect(Collectors.toList());
                for (int index = 3; index < rowLength; ++index) {
                    int finalIndex = index;
                    BigDecimal reduce = collect.stream().map(data -> AccountQueryServiceImpl.isNum(data.get(finalIndex))).reduce(BigDecimal.ZERO, BigDecimal::add);
                    if (reduce.compareTo(new BigDecimal(0)) == 0) {
                        row.set(index, null);
                        continue;
                    }
                    row.set(index, reduce);
                }
            }
            if (!(o instanceof List)) continue;
            per = Key;
            List<Object> row = this.createRow(rowLength);
            while (per.length() < levelLength) {
                per = per + "0";
            }
            String value = Key + "|" + emnuLsit.get(per);
            String emptyString = EmptyStringEnum.getEmptyString(emptyLength, value);
            row.set(2, emptyString);
            for (int index = 3; index < rowLength; ++index) {
                int finalIndex = index;
                BigDecimal reduce = ((List)o).stream().map(data -> AccountQueryServiceImpl.isNum(data.get(finalIndex))).reduce(BigDecimal.ZERO, BigDecimal::add);
                if (reduce.compareTo(new BigDecimal(0)) == 0) {
                    row.set(index, null);
                    continue;
                }
                row.set(index, reduce);
            }
            groupDataList.add(row);
            groupDataList.addAll((List)o);
        }
    }

    Map<String, String> getEmnuLsit(Map<String, String> taskInfo, String code) throws Exception {
        HashMap emnu = new HashMap();
        HashMap<String, String> emnuList = new HashMap<String, String>();
        List allDataFieldByKind = this.runtimeDataSchemeService.getAllDataFieldByKind(taskInfo.get("schemeKey"), new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM});
        List entityRefer = this.iEntityMetaService.getEntityRefer(taskInfo.get("dw"));
        entityRefer.forEach(e -> emnu.put(e.getOwnField(), e.getReferEntityId()));
        allDataFieldByKind.forEach(e -> emnu.put(e.getCode(), e.getRefDataEntityKey()));
        List collect = emnu.keySet().stream().filter(e -> code.contains((CharSequence)e)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            List maps = this.formulaParserController.allEnumContentByEnum((String)emnu.get(collect.get(0)));
            maps.forEach(m -> {
                String cfr_ignored_0 = (String)emnuList.put((String)m.get("value"), (String)m.get("label"));
            });
        }
        return emnuList;
    }

    List<Object> createRow(Integer clos) {
        ArrayList<Object> newRow = new ArrayList<Object>();
        for (int index = 0; index < clos; ++index) {
            newRow.add(index, null);
        }
        return newRow;
    }

    void ZeroEmpty(List<List<Object>> resultData) {
        for (List<Object> data : resultData) {
            Integer index = 0;
            while (index < data.size()) {
                BigDecimal bigDecimal;
                if (data.get(index) instanceof BigDecimal && 0 == (bigDecimal = (BigDecimal)data.get(index)).compareTo(new BigDecimal("0"))) {
                    data.set(index, null);
                }
                Integer n = index;
                Integer n2 = index = Integer.valueOf(index + 1);
            }
        }
    }

    void decimalPlace(JSONArray decimalPlaceList, List<List<Object>> resultData, Boolean addColumnNumber) {
        for (List<Object> data : resultData) {
            Integer index = 0;
            while (index < data.size()) {
                if (data.get(index) instanceof BigDecimal) {
                    boolean isNegative;
                    BigDecimal bigDecimal = (BigDecimal)data.get(index);
                    String decimal = decimalPlaceList.getJSONObject(index + 1).getString("v");
                    int decimalPlace = decimal.isEmpty() ? bigDecimal.scale() : Integer.parseInt(decimal);
                    boolean bl = isNegative = bigDecimal.compareTo(new BigDecimal("0")) < 0;
                    if (isNegative) {
                        bigDecimal = bigDecimal.abs();
                    }
                    bigDecimal = FileUtil.trimDouble(bigDecimal, decimalPlace);
                    String s = FileUtil.formatNumberString(bigDecimal, decimalPlace, true);
                    if (isNegative) {
                        s = "-" + s;
                    }
                    data.set(index, s);
                }
                Integer n = index;
                Integer n2 = index = Integer.valueOf(index + 1);
            }
        }
    }

    void amountConversion(List<String> indexSelectList, List<Map<String, String>> expressionColumnList, BigDecimal divideValue, List<List<Object>> resultData, Boolean columnNumber) {
        ArrayList<Integer> expIndex = new ArrayList<Integer>();
        for (Map<String, String> map : expressionColumnList) {
            if (!indexSelectList.contains(map.get("data"))) continue;
            expIndex.add(Integer.parseInt(map.get("index")));
        }
        for (List list : resultData) {
            for (Integer index : expIndex) {
                if (columnNumber.booleanValue()) {
                    index = index - 1;
                }
                if (!(list.get(index) instanceof BigDecimal)) continue;
                list.set(index, ((BigDecimal)list.get(index)).divide(divideValue));
            }
        }
    }

    void addColumnNumber(List<List<Object>> resultData, Boolean total, Boolean hasName) {
        Integer index = 0;
        while (index < resultData.size()) {
            if (total.booleanValue()) {
                if (index == 0) {
                    resultData.get(index).add(0, "\u5408\u8ba1\u884c");
                    if (hasName.booleanValue()) {
                        resultData.get(index).set(1, null);
                    }
                } else {
                    resultData.get(index).add(0, String.valueOf(index));
                }
            } else {
                resultData.get(index).add(0, String.valueOf(index + 1));
            }
            Integer n = index;
            Integer n2 = index = Integer.valueOf(index + 1);
        }
    }

    public QueryResult result(JSONObject dataJson, QueryConfigInfo queryConfigInfo, JSONArray resultData, List<Map<String, String>> expressionColumnList, Map<String, List<WarningInfo>> forewarn) throws Exception {
        QueryResult query = new QueryResult();
        JSONObject gridData = new JSONObject();
        dataJson = this.tableSample(dataJson, gridData);
        JSONArray rows = dataJson.getJSONArray("rows");
        dataJson.getJSONArray("columns").remove(0);
        dataJson.getJSONObject("options").put("hiddenSerialNumberHeader", true);
        JSONArray mergeInfo = dataJson.getJSONArray("mergeInfo");
        gridData.put("mergeInfo", (Object)mergeInfo);
        gridData.put("columns", (Object)dataJson.getJSONArray("columns"));
        gridData.put("options", (Object)dataJson.getJSONObject("options"));
        JSONArray data = dataJson.getJSONArray("data");
        JSONArray dataFormat = data.getJSONArray(data.length() - 2);
        data.remove(data.length() - 1);
        data.remove(data.length() - 1);
        if (queryConfigInfo.getTotalLine().booleanValue()) {
            if (resultData.isEmpty()) {
                data.getJSONArray(data.length() - 1).getJSONObject(0).put("v", (Object)"\u5408\u8ba1\u884c");
            } else {
                data.remove(data.length() - 1);
            }
        }
        int headerLength = data.length();
        for (int i = 0; i < resultData.length(); ++i) {
            for (int m = 0; m < resultData.getJSONArray(i).length(); ++m) {
                resultData.getJSONArray(i).getJSONObject(m).put("s", dataFormat.getJSONObject(m).get("s"));
                resultData.getJSONArray(i).getJSONObject(m).put("t", (Integer)resultData.getJSONArray(i).getJSONObject(m).get("t") != 0 ? resultData.getJSONArray(i).getJSONObject(m).get("t") : Integer.valueOf(1));
            }
        }
        data.putAll(resultData);
        rows.remove(rows.length() - 1);
        if (rows.length() < data.length()) {
            int length = data.length() - rows.length();
            int index = rows.length() - 2;
            for (int i = 0; i < length; ++i) {
                rows.put(rows.get(index));
            }
        }
        if (rows.length() > data.length()) {
            for (int sum = rows.length() - data.length(); sum > 0; --sum) {
                int length = rows.length();
                rows.remove(length - 1);
            }
        }
        this.warnColor(dataJson.getJSONArray("styles"), data, forewarn, headerLength);
        gridData.put("rows", (Object)rows);
        gridData.put("data", (Object)data);
        gridData.put("expressionColumnList", expressionColumnList);
        gridData.put("styles", (Object)dataJson.getJSONArray("styles"));
        if (queryConfigInfo.getHasName().booleanValue()) {
            this.merger(gridData, queryConfigInfo.getColumnNumber());
        }
        query.setGridData(gridData.toString());
        CellBook cellBook = new CellBook();
        CellBookGriddataConverter.gridDataToCellBook((GridData)GridDataConverter.buildGridData((JSONObject)gridData), (CellBook)cellBook, (String)"result", (String)"result");
        query.setCellBook(cellBook);
        return query;
    }

    void warnColor(JSONArray styles, JSONArray data, Map<String, List<WarningInfo>> forewarn, Integer headerLength) {
        if (null != forewarn && !forewarn.isEmpty()) {
            for (List<WarningInfo> warningInfos : forewarn.values()) {
                Boolean isFist = true;
                Integer index = -1;
                for (WarningInfo warningInfo : warningInfos) {
                    int col = warningInfo.getCol();
                    int row = warningInfo.getRow();
                    JSONObject cell = data.getJSONArray(row + headerLength).getJSONObject(col);
                    int anInt = cell.getInt("s");
                    if (isFist.booleanValue()) {
                        JSONObject newStyle;
                        if (anInt < 0) {
                            newStyle = new JSONObject();
                            newStyle.put("backGroundColor", (Object)warningInfo.getColor());
                            styles.put((Object)newStyle);
                        } else {
                            newStyle = this.createNewStyle(styles.getJSONObject(anInt)).put("backGroundColor", (Object)warningInfo.getColor());
                            styles.put((Object)newStyle);
                        }
                        index = styles.length() - 1;
                        data.getJSONArray(row + headerLength).getJSONObject(col).put("s", (Object)index);
                    } else {
                        data.getJSONArray(row + headerLength).getJSONObject(col).put("s", (Object)index);
                    }
                    isFist = false;
                }
            }
        }
    }

    JSONObject createNewStyle(JSONObject styleObject) {
        JSONObject newStyle = new JSONObject();
        if (null != styleObject) {
            Set strings = styleObject.keySet();
            for (String key : strings) {
                newStyle.put(key, styleObject.get(key));
            }
        }
        return newStyle;
    }

    public void buildTree(List<List<Object>> data, Map<String, String> taskInfo, Boolean isRetract, QueryConfigInfo queryConfigInfo) throws Exception {
        Boolean hasName = queryConfigInfo.getHasName();
        IEntityTable entityTable = this.getEntityTable(taskInfo.get("reportScheme"), taskInfo.get("fromPeriod"));
        if (!data.isEmpty()) {
            for (List<Object> datum : data) {
                Object name = datum.get(2);
                Object code = datum.get(1);
                if (isRetract.booleanValue() && queryConfigInfo.getTreeDisPlay().booleanValue()) {
                    String[] parentsEntityKeyDataPath;
                    if (code != null && null != name) {
                        parentsEntityKeyDataPath = entityTable.getParentsEntityKeyDataPath((String)code);
                        String emptyString = EmptyStringEnum.getEmptyString(parentsEntityKeyDataPath.length, (String)name);
                        datum.set(2, emptyString);
                    }
                    if (null != datum.get(0) && "\u5408\u8ba1\u884c".equals(datum.get(0).toString())) {
                        datum.set(2, "\u5408\u8ba1\u884c");
                    }
                    datum.remove(0);
                    datum.remove(0);
                    parentsEntityKeyDataPath = hasName != false ? null : datum.remove(datum.size() - 1);
                    continue;
                }
                if (code != null && null != name) {
                    if (!isRetract.booleanValue()) {
                        String emptyString = EmptyStringEnum.getEmptyString(4, (String)name);
                        datum.set(2, emptyString);
                    } else {
                        datum.set(2, name);
                    }
                }
                if (null != datum.get(0) && "\u5408\u8ba1\u884c".equals(datum.get(0).toString()) && queryConfigInfo.getHasName().booleanValue()) {
                    datum.set(2, "\u5408\u8ba1\u884c");
                }
                datum.remove(0);
                datum.remove(0);
                Object object = hasName != false ? null : datum.remove(datum.size() - 1);
            }
        }
    }

    public IEntityTable getEntityTable(String formSchemeKey, String period) throws Exception {
        IEntityQuery query = this.entityQueryHelper.getEntityQuery(formSchemeKey, period);
        IEntityTable iEntityTable = this.entityQueryHelper.buildEntityTable(query, formSchemeKey, false);
        return iEntityTable;
    }

    void merger(JSONObject gridData, Boolean ColumnNumber) {
        JSONArray mergeInfo = gridData.getJSONArray("mergeInfo");
        JSONArray resultData = gridData.getJSONArray("data");
        Integer n = ColumnNumber != false ? 1 : 0;
        for (int m = 0; m < resultData.length(); ++m) {
            Object nextValue;
            JSONArray data = resultData.getJSONArray(m);
            Object value = data.getJSONObject(n.intValue()).get("v");
            if (m >= resultData.length() - 1 || value.toString().isEmpty() || !value.equals(nextValue = resultData.getJSONArray(m + 1).getJSONObject(n.intValue()).get("v"))) continue;
            Integer start = m;
            Integer end = m + 1;
            end = this.nextValue(value.toString(), resultData, m + 2, n, end);
            m = end;
            JSONObject merge = new JSONObject();
            merge.put("rowSpan", end - start + 1);
            merge.put("columnSpan", 1);
            merge.put("rowIndex", (Object)start);
            merge.put("columnIndex", (Object)n);
            mergeInfo.put((Object)merge);
        }
    }

    Integer nextValue(String value, JSONArray resultData, Integer index, Integer n, Integer end) {
        String data;
        for (int m = index.intValue(); m < resultData.length() && value.equals(data = resultData.getJSONArray(m).getJSONObject(n.intValue()).getString("v")); ++m) {
            end = m;
        }
        return end;
    }

    public JSONObject tableSample(JSONObject dataJson, JSONObject gridData) {
        int i;
        for (i = 0; i < dataJson.getJSONArray("data").length(); ++i) {
            dataJson.getJSONArray("data").getJSONArray(i).remove(0);
        }
        for (i = 0; i < dataJson.getJSONArray("mergeInfo").length(); ++i) {
            int columnIndex = dataJson.getJSONArray("mergeInfo").getJSONObject(i).getInt("columnIndex");
            dataJson.getJSONArray("mergeInfo").getJSONObject(i).put("columnIndex", columnIndex - 1);
        }
        return dataJson;
    }

    JSONArray saveDataToJson(JSONArray decimalPlaceList, List<List<Object>> rows, Boolean addColumnNumber) {
        JSONArray resultData = new JSONArray();
        for (int i = 0; i < rows.size(); ++i) {
            JSONArray data = new JSONArray();
            for (int n = 0; n < rows.get(i).size(); ++n) {
                JSONObject value = new JSONObject();
                value.put("s", 0);
                value.put("v", rows.get(i).get(n) == null ? "" : rows.get(i).get(n));
                if (rows.get(i).get(n) instanceof BigDecimal) {
                    boolean isNegative;
                    value.put("t", 2);
                    BigDecimal bigDecimal = (BigDecimal)rows.get(i).get(n);
                    String decimal = decimalPlaceList.getJSONObject(n + 1).getString("v");
                    int decimalPlace = decimal.isEmpty() ? bigDecimal.scale() : Integer.parseInt(decimal);
                    boolean bl = isNegative = bigDecimal.compareTo(new BigDecimal("0")) < 0;
                    if (isNegative) {
                        bigDecimal = bigDecimal.abs();
                    }
                    bigDecimal = FileUtil.trimDouble(bigDecimal, decimalPlace);
                    String s = FileUtil.formatNumberString(bigDecimal, decimalPlace, true);
                    if (isNegative) {
                        s = "-" + s;
                    }
                    value.put("v", (Object)s);
                    JSONObject dataFormatJson = new JSONObject();
                    dataFormatJson.put("format", 2);
                    dataFormatJson.put("decimal", bigDecimal.scale());
                    dataFormatJson.put("showSeparator", true);
                    value.put("formatter", (Object)dataFormatJson);
                } else {
                    value.put("t", 0);
                }
                data.put((Object)value);
            }
            resultData.put((Object)data);
        }
        return resultData;
    }

    void totalLine(List<List<Object>> resultData) {
        ArrayList<Object> totalLine = new ArrayList<Object>();
        resultData.get(0).stream().forEach(d -> totalLine.add(null));
        for (int m = 0; m < resultData.size(); ++m) {
            List<Object> data = resultData.get(m);
            for (int index = 0; index < data.size(); ++index) {
                if (data.get(index) instanceof String) {
                    totalLine.set(index, null);
                }
                if (!(data.get(index) instanceof BigDecimal) && null != data.get(index)) continue;
                BigDecimal dataValue = (BigDecimal)data.get(index);
                BigDecimal totalValue = (BigDecimal)totalLine.get(index);
                dataValue = dataValue == null ? new BigDecimal("0.00") : dataValue;
                totalValue = totalValue == null ? new BigDecimal("0.00") : totalValue;
                totalLine.set(index, totalValue.add(dataValue));
            }
        }
        totalLine.set(0, "\u5408\u8ba1\u884c");
        resultData.add(0, totalLine);
    }

    List<List<Object>> getResultByPage(List<List<Object>> result, QueryConfigInfo queryConfigInfo) {
        List<Object> totalLine = null;
        if (queryConfigInfo.getTotalLine().booleanValue()) {
            totalLine = result.get(0);
            result.remove(0);
        }
        int size = result.size();
        int strat = 0;
        int end = size;
        if (null != queryConfigInfo.getPageInfo()) {
            queryConfigInfo.getPageInfo().setRecordSize(size);
            strat = queryConfigInfo.getPageInfo().getPageSize() * (queryConfigInfo.getPageInfo().getPageIndex() - 1);
            end = queryConfigInfo.getPageInfo().getPageSize() * queryConfigInfo.getPageInfo().getPageIndex();
            end = Math.min(end, size);
        }
        List<List<Object>> lists = result.subList(strat, end);
        if (queryConfigInfo.getTotalLine().booleanValue() && null != totalLine) {
            lists.add(0, totalLine);
        }
        return lists;
    }

    public void sort(List<Object[]> rows, final Integer index, final Integer orderType) {
        Comparator<Object[]> rowComparator = new Comparator<Object[]>(){

            @Override
            public int compare(Object[] o1, Object[] o2) {
                Object value = o1[index];
                Object value2 = o2[index];
                if (null == value && null == value2) {
                    return 0;
                }
                if (null != value2 && value == null) {
                    value = value2;
                }
                if (value instanceof String) {
                    String a = (String)o1[index];
                    String b = (String)o2[index];
                    a = a == null ? "" : a;
                    b = b == null ? "" : b;
                    return orderType == 0 ? a.compareTo(b) : b.compareTo(a);
                }
                if (value instanceof BigDecimal) {
                    BigDecimal a = (BigDecimal)o1[index];
                    BigDecimal b = (BigDecimal)o2[index];
                    if (a == null) {
                        return orderType == 0 ? -1 : 1;
                    }
                    if (b == null) {
                        return orderType == 0 ? 1 : -1;
                    }
                    return orderType == 0 ? a.compareTo(b) : b.compareTo(a);
                }
                return 0;
            }
        };
        Collections.sort(rows, rowComparator);
    }

    Map<String, String> getTaskInfo(String taskKey, String modelId) throws Exception {
        HashMap<String, String> parms = new HashMap<String, String>();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskKey);
        DataScheme dataScheme = this.iRuntimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String prefix = dataScheme.getPrefix();
        ArrayList taskLinkObjBySchemeKey = this.stepSaveService.getTaskLinkObjBySchemeKey(((FormSchemeDefine)formSchemeDefines.get(0)).getKey());
        if (!taskLinkObjBySchemeKey.isEmpty()) {
            FormSchemeDefine relateFormScheme = this.iRunTimeViewController.getFormScheme(((TaskLinkObject)taskLinkObjBySchemeKey.get(0)).getRelatedFormSchemeKey());
            if (null != relateFormScheme) {
                TaskDefine relateTaskDefine = this.iRunTimeViewController.queryTaskDefine(relateFormScheme.getTaskKey());
                DataScheme relateDataScheme = this.iRuntimeDataSchemeService.getDataScheme(relateTaskDefine.getDataScheme());
                String relatePrefix = relateDataScheme.getPrefix();
                parms.put("relatePrefix", relatePrefix);
            } else {
                parms.put("relatePrefix", "RELATE_PERFIX");
            }
        } else {
            parms.put("relatePrefix", "RELATE_PERFIX");
        }
        parms.put("perfix", prefix);
        parms.put("reportScheme", ((FormSchemeDefine)formSchemeDefines.get(0)).getKey());
        parms.put("fromPeriod", taskDefine.getFromPeriod());
        parms.put("org", taskDefine.getDw().split("@")[0]);
        parms.put("dw", taskDefine.getDw());
        parms.put("data", taskDefine.getDateTime());
        parms.put("reportTask", taskDefine.getKey());
        parms.put("schemeKey", dataScheme.getKey());
        QueryModel modelData = this.queryModleService.getQueryModelByKey(modelId);
        if (modelData != null && org.springframework.util.StringUtils.hasText(modelData.getFormschemeKey())) {
            boolean isFind = false;
            List schemePeriodLinkDefineList = this.iRunTimeViewController.querySchemePeriodLinkByTask(taskKey);
            for (SchemePeriodLinkDefine define : schemePeriodLinkDefineList) {
                if (!modelData.getFormschemeKey().equals(define.getSchemeKey())) continue;
                parms.put("period", define.getPeriodKey());
                isFind = true;
                break;
            }
            if (!isFind) {
                for (SchemePeriodLinkDefine define : formSchemeDefines) {
                    if (!modelData.getFormschemeKey().equals(define.getKey())) continue;
                    parms.put("period", define.getFromPeriod().replace("N0001", "0101"));
                    isFind = true;
                    break;
                }
            }
            if (!isFind) {
                parms.put("period", taskDefine.getFromPeriod().replace("N0001", "0101"));
            }
        } else {
            parms.put("period", taskDefine.getFromPeriod().replace("N0001", "0101"));
        }
        return parms;
    }

    private ReportDsModelDefine createReportDsModelDefine(List<String> expList, String filter, Map<String, String> taskInfo, QueryConfigInfo queryConfigInfo) {
        ReportDsModelDefine reportDsModelDefine = new ReportDsModelDefine();
        Integer index = 0;
        for (String exp : expList) {
            Integer n = index;
            Integer n2 = index = Integer.valueOf(index + 1);
            reportDsModelDefine.getFields().add(this.createReportExpField(exp, taskInfo, n));
        }
        if (taskInfo.containsKey("hasFloat") && taskInfo.containsKey("hasZb") && Boolean.valueOf(taskInfo.get("hasFloat")).booleanValue() && Boolean.valueOf(taskInfo.get("hasZb")).booleanValue()) {
            logger.info("\u67e5\u8be2\u6a21\u677f\u4e2d\u6709\u5f53\u524d\u5e74\u5ea6\u7684\u6d6e\u52a8\u8868\u6307\u6807\u65f6\uff0c\u4e0d\u80fd\u540c\u65f6\u67e5\u8be2\u4e0a\u5e74\u5ea6\u6307\u6807");
        }
        reportDsModelDefine.setFilter(filter);
        reportDsModelDefine.setReportTaskKey(taskInfo.get("reportTask"));
        this.setReportDsParameters(taskInfo, reportDsModelDefine.getParameters(), queryConfigInfo);
        return reportDsModelDefine;
    }

    private ReportExpField createReportExpField(String exp, Map<String, String> taskInfo, Integer index) {
        ReportExpField reportExpField = new ReportExpField();
        reportExpField.setCode(String.format("t_%d", index));
        reportExpField.setTitle(reportExpField.getCode());
        this.judgmentType(exp, taskInfo, reportExpField);
        if (exp.equals("LEVEL")) {
            this.handleSpecialLevelField(reportExpField, taskInfo);
        }
        return reportExpField;
    }

    private void handleSpecialLevelField(ReportExpField reportExpField, Map<String, String> taskInfo) {
        String taskKey = taskInfo.get("reportTask");
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        String dw = taskDefine.getDw();
        String dwDefineCode = dw.split("@")[0];
        String specialFieldName = String.format("%s_LEVEL", dwDefineCode);
        IEntityModel entityModel = this.iEntityMetaService.getEntityModel(dw);
        Iterator attributeIte = entityModel.getAttributes();
        boolean hasLevelInDw = false;
        while (attributeIte.hasNext()) {
            IEntityAttribute attribute = (IEntityAttribute)attributeIte.next();
            if (!attribute.getCode().equals(specialFieldName)) continue;
            reportExpField.setExp(specialFieldName);
            reportExpField.setFieldType(FieldType.GENERAL_DIM);
            reportExpField.setReportFieldType(ReportFieldType.UNIT);
            hasLevelInDw = true;
            break;
        }
        if (!hasLevelInDw) {
            reportExpField.setExp("UNITLEVEL(ORGCODE)");
        }
    }

    ReportExpField judgmentType(String code, Map<String, String> taskInfo, ReportExpField reportExpField) {
        reportExpField.setExp(code);
        if (code.contains("MD_ORG")) {
            reportExpField.setDataType(6);
            if (code.contains("CODE") || code.contains("NAME") || code.contains("PARENTCODE") || code.contains("ORGCODE") || code.contains("ORDINAL")) {
                reportExpField.setFieldType(FieldType.GENERAL_DIM);
                reportExpField.setReportFieldType(ReportFieldType.UNIT);
                return reportExpField;
            }
            reportExpField.setFieldType(FieldType.DESCRIPTION);
            return reportExpField;
        }
        return reportExpField;
    }

    private void setReportDsParameters(Map<String, String> taskInfo, List<ReportDsParameter> parameters, QueryConfigInfo queryConfigInfo) {
        String catagoryName = taskInfo.get("org");
        String taskTypeFlag = taskInfo.get("data");
        String period = taskInfo.get("period");
        ReportDsParameter periodParameter = new ReportDsParameter();
        periodParameter.setName(String.format("NR_PERIOD_%S", taskTypeFlag));
        periodParameter.setTitle("\u65f6\u671f");
        periodParameter.setDataType(6);
        periodParameter.setSelectMode(ParameterSelectMode.SINGLE);
        periodParameter.setDefaultValueMode(DefaultValueMode.APPOINT);
        periodParameter.setMessageAlias(PeriodTableColumn.TIMEKEY.getCode());
        periodParameter.setEntityId(taskTypeFlag);
        periodParameter.setDefaultValues(new String[]{period});
        IEntityDefine entity = this.entityMetaService.queryEntity(catagoryName);
        ReportDsParameter orgParameter = new ReportDsParameter();
        orgParameter.setName(catagoryName);
        orgParameter.setTitle(entity.getTitle());
        orgParameter.setDataType(6);
        orgParameter.setEntityId(catagoryName);
        orgParameter.setSelectMode(ParameterSelectMode.MUTIPLE);
        ConditionValues conditionValues = queryConfigInfo.getConditionValues();
        if (null != conditionValues) {
            String[] values;
            String[] orgLists = conditionValues.getValues(catagoryName);
            if (orgLists.length > 0) {
                orgParameter.setDefaultValues(queryConfigInfo.getConditionValues().getValues(catagoryName));
                orgParameter.setDefaultValueMode(DefaultValueMode.APPOINT);
            } else {
                orgParameter.setDefaultValueMode(DefaultValueMode.NONE);
            }
            String periodKey = "NR_PERIOD_" + taskTypeFlag;
            if (conditionValues.contains(periodKey) && null != (values = conditionValues.getValues(periodKey))[0]) {
                periodParameter.setDefaultValues(new String[]{values[0]});
            }
        }
        orgParameter.setMessageAlias(catagoryName + ".ORGCODE");
        parameters.add(orgParameter);
        parameters.add(periodParameter);
    }
}

