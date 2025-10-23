/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.JavaType
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  javax.annotation.Resource
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.singlequeryimport.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.finalaccountsaudit.common.EntityQueryHelper;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.singlequeryimport.bean.ModalColWarningInfo;
import com.jiuqi.nr.singlequeryimport.bean.QueryConfigInfo;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.bean.QueryResult;
import com.jiuqi.nr.singlequeryimport.controller.QueryModleController;
import com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao;
import com.jiuqi.nr.singlequeryimport.dao.SingleModleDao;
import com.jiuqi.nr.singlequeryimport.service.QueryByCustomService;
import com.jiuqi.nr.singlequeryimport.service.impl.AccountQueryServiceImpl;
import com.jiuqi.nr.singlequeryimport.service.impl.QueryModleServiceImpl;
import com.jiuqi.nr.singlequeryimport.service.impl.SinglerQuserServiceImpl;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryByCustomServiceImpl
implements QueryByCustomService {
    Logger logger = LoggerFactory.getLogger(QueryModleController.class);
    @Autowired
    private QueryModeleDao queryModeleDao;
    @Autowired
    IEntityMetaService iEntityMetaService;
    @Autowired
    IRunTimeViewController iRunTimeViewController;
    @Autowired
    IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    QueryModleServiceImpl queryModleService;
    @Autowired
    DataModelService dataModelService;
    @Autowired
    BaseDataClient baseDataClient;
    @Autowired
    SingleModleDao singleModleDao;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Resource
    @Autowired
    ObjectMapper mapper;
    @Autowired
    SinglerQuserServiceImpl service;
    @Autowired
    EntityQueryHelper entityQueryHelper;
    @Autowired
    AccountQueryServiceImpl accountQueryService;

    @Override
    public QueryResult query(QueryConfigInfo queryConfigInfo) throws Exception {
        JSONObject set;
        QueryModel dataModel = this.queryModeleDao.getData(queryConfigInfo.getModelId());
        JSONObject items = new JSONObject(dataModel.getItem());
        JSONObject model = items.getJSONObject("model");
        queryConfigInfo.setTotalLine(items.getInt("totalLine") == 1);
        queryConfigInfo.setColumnNumber(items.getInt("columnNumber") == 1);
        if (items.has("treeDisplay")) {
            queryConfigInfo.setTreeDisPlay(items.getInt("treeDisplay") == 1);
        }
        if (items.has("zeroEmpty")) {
            queryConfigInfo.setZeroEmpty(items.getBoolean("zeroEmpty"));
        }
        JSONArray data = model.getJSONArray("data");
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(dataModel.getTaskKey());
        HashMap<String, List<Integer>> groupSet = new HashMap<String, List<Integer>>();
        List forewarnConditionList = new ArrayList();
        ArrayList<ModalColWarningInfo> currenPageForewarn = new ArrayList<ModalColWarningInfo>();
        ArrayList<ModalColWarningInfo> allPageForewarn = new ArrayList<ModalColWarningInfo>();
        if (null != dataModel.getForewarnCondition()) {
            JavaType javaType = this.mapper.getTypeFactory().constructParametricType(List.class, new Class[]{ModalColWarningInfo.class});
            forewarnConditionList = (List)this.mapper.readValue(dataModel.getForewarnCondition(), javaType);
            if (!forewarnConditionList.isEmpty()) {
                for (ModalColWarningInfo warningInfo : forewarnConditionList) {
                    if (warningInfo.getSymbol().equals("MIN") || warningInfo.getSymbol().equals("MAX")) {
                        allPageForewarn.add(warningInfo);
                        continue;
                    }
                    currenPageForewarn.add(warningInfo);
                }
            }
        }
        if (items.has("groupSet") && !"null".equals((set = items.getJSONObject("groupSet")).get("level").toString()) && !"null".equals(set.get("zb"))) {
            String zb = set.getString("zb");
            JSONArray level = set.getJSONArray("level");
            List integers = (List)this.mapper.readValue(level.toString(), (TypeReference)new TypeReference<List<Integer>>(){});
            if (!StringUtils.isEmpty((String)zb) && !integers.isEmpty()) {
                groupSet.put(zb, integers);
            }
        }
        String format = null;
        if (items.getJSONObject("filter").has("formulaContent")) {
            String string = items.getJSONObject("filter").getString("formulaContent");
            format = string.isEmpty() ? null : string;
            this.logger.info("\u5355\u4f4d\u6761\u4ef6\u8fc7\u6ee4--->{}", (Object)format);
        }
        List<Map<String, String>> expressionColumnList = this.getExpressionColumnValue(queryConfigInfo.getColumnNumber(), data.getJSONArray(data.length() - 2));
        Map<String, Integer> order = this.getOrder(model, queryConfigInfo, expressionColumnList, queryConfigInfo.getColumnNumber());
        List<String> expList = this.getexpList(data, queryConfigInfo, taskDefine.getDw());
        return this.accountQueryService.search(expList, format, order, dataModel.getTaskKey(), queryConfigInfo, model, expressionColumnList, groupSet, currenPageForewarn, allPageForewarn);
    }

    @Override
    public QueryResult querySamples(QueryConfigInfo queryConfigInfo) throws Exception {
        QueryModel dataModel = this.queryModeleDao.getData(queryConfigInfo.getModelId());
        JSONObject items = new JSONObject(dataModel.getItem());
        JSONObject model = items.getJSONObject("model");
        JSONArray data = model.getJSONArray("data");
        List<Map<String, String>> expressionColumnList = this.getExpressionColumnValue(queryConfigInfo.getColumnNumber(), data.getJSONArray(data.length() - 2));
        JSONArray resultData = new JSONArray();
        QueryResult result = this.accountQueryService.result(model, queryConfigInfo, resultData, expressionColumnList, null);
        result.setPageInfo(queryConfigInfo.getPageInfo());
        result.setCacheId(queryConfigInfo.getCacheId());
        return result;
    }

    public List<String> getexpList(JSONArray data, QueryConfigInfo queryConfigInfo, String orgPerfix) throws Exception {
        int siez;
        Boolean hasName = false;
        String org = orgPerfix.split("@")[0];
        ArrayList<String> expList = new ArrayList<String>();
        expList.add(String.format("%s[ORDINAL]", org));
        expList.add(String.format("%s[CODE]", org));
        JSONArray dataFormtList = data.getJSONArray(data.length() - 2);
        int n = siez = queryConfigInfo.getColumnNumber() != false ? 2 : 1;
        while (siez < dataFormtList.length()) {
            JSONObject dataFormat = dataFormtList.getJSONObject(siez);
            String v = dataFormat.getString("v");
            if (v.equals("NAME")) {
                hasName = true;
            }
            expList.add(v);
            ++siez;
        }
        if (!hasName.booleanValue()) {
            expList.add("NAME");
        }
        queryConfigInfo.setHasName(hasName);
        return expList;
    }

    private List<Map<String, String>> getExpressionColumnValue(boolean columnNumber, JSONArray dataFormtList) {
        int siez;
        ArrayList<Map<String, String>> expressionColumnList = new ArrayList<Map<String, String>>();
        int n = siez = columnNumber ? 2 : 1;
        while (siez < dataFormtList.length()) {
            JSONObject dataFormat = dataFormtList.getJSONObject(siez);
            String v = dataFormat.getString("v");
            HashMap<String, String> expressionInfo = new HashMap<String, String>();
            int index = siez - 1;
            expressionInfo.put("index", index + "");
            expressionInfo.put("data", v);
            expressionColumnList.add(expressionInfo);
            ++siez;
        }
        return expressionColumnList;
    }

    Map<String, Integer> getOrder(JSONObject model, QueryConfigInfo queryConfigInfo, List<Map<String, String>> expressionColumnList, boolean columnNumber) {
        JSONArray modelOdres;
        LinkedHashMap<String, Integer> order = new LinkedHashMap<String, Integer>();
        Integer orderType = null;
        String zbName = null;
        List<Map<String, String>> pageOrders = queryConfigInfo.getOrdersList();
        if (null != pageOrders && !pageOrders.isEmpty()) {
            zbName = pageOrders.get(0).get("fullName");
            orderType = pageOrders.get(0).get("mode").equals("ASC") ? 0 : 1;
        }
        if (!(modelOdres = model.getJSONArray("orders")).isEmpty() && modelOdres.getJSONObject(0).has("fullName")) {
            zbName = modelOdres.getJSONObject(0).getString("fullName");
            orderType = modelOdres.getJSONObject(0).getString("mode").equals("ASC") ? 0 : 1;
        }
        String finalZbName = zbName;
        List data = expressionColumnList.stream().filter(e -> ((String)e.get("data")).equals(finalZbName)).collect(Collectors.toList());
        if (null != orderType && !data.isEmpty()) {
            Integer index = columnNumber ? Integer.parseInt((String)((Map)data.get(0)).get("index")) + 1 : Integer.parseInt((String)((Map)data.get(0)).get("index")) + 2;
            order.put("orderType", orderType);
            order.put("index", index);
        }
        return order;
    }

    public Map<String, String> getTaskInfo(TaskDefine taskDefine, String formSchemeKey) throws Exception {
        HashMap<String, String> parms = new HashMap<String, String>();
        DataScheme dataScheme = this.iRuntimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        String tableName = formScheme.getDw();
        TableModelDefine tableModel = this.iEntityMetaService.getTableModel(tableName);
        String dateTime = formScheme.getDateTime();
        TableModelDefine periodEntityTableModel = this.periodEngineService.getPeriodAdapter().getPeriodEntityTableModel(dateTime);
        String periodType = this.periodEngineService.getPeriodAdapter().getPeriodEntity(dateTime).getType().name();
        parms.put("reportTask", formScheme.getTaskKey());
        parms.put("reportScheme", formScheme.getKey());
        parms.put("period", periodEntityTableModel.getCode());
        parms.put("periodTitle", periodEntityTableModel.getTitle());
        parms.put("periodType", "P_" + periodType);
        parms.put("org", tableModel.getCode());
        parms.put("orgTitle", tableModel.getTitle());
        parms.put("schemeName", dataScheme.getCode());
        parms.put("perfix", "MD_" + dataScheme.getPrefix() + "_");
        parms.put("tableName", formScheme.getDw());
        parms.put("typeFlag", dateTime);
        return parms;
    }
}

