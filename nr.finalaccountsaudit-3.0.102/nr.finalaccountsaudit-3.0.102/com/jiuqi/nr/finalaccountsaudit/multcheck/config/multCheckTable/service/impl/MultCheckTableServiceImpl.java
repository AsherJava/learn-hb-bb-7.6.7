/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.reflect.TypeToken
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.common.MultCheckEnum;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.dao.IMultCheckTableDao;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.entity.MultCheckTableBean;
import com.jiuqi.nr.finalaccountsaudit.multcheck.config.multCheckTable.service.IMultCheckTableService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MultCheckTableServiceImpl
implements IMultCheckTableService {
    private static final Logger logger = LoggerFactory.getLogger(MultCheckTableServiceImpl.class);
    Gson gson = new Gson();
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public IMultCheckTableDao iMultCheckTableDao;
    @Resource
    private IRunTimeViewController runtimeView;

    @Override
    public int insertData(MultCheckTableBean multCheckTableBean) {
        int num = 0;
        try {
            Object[] objects = new Object[]{multCheckTableBean.getS_key(), multCheckTableBean.getS_name(), multCheckTableBean.getS_order(), this.gson.toJson(multCheckTableBean.getS_dw()), this.gson.toJson(multCheckTableBean.getMultCheckTableDataItem()), multCheckTableBean.getS_taskkey(), multCheckTableBean.getS_formschemekey()};
            num = this.iMultCheckTableDao.insertData(objects);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return num;
    }

    @Override
    public int deleteData(String key) {
        int num = this.iMultCheckTableDao.deleteData(key);
        return num;
    }

    @Override
    public int updateData(String updateStr) {
        JsonObject jsonObject = (JsonObject)this.gson.fromJson(updateStr, JsonObject.class);
        String key = jsonObject.get("s_key").getAsString();
        StringBuffer sqlSb = new StringBuffer();
        ArrayList<Object> paramList = new ArrayList<Object>();
        sqlSb.append("update SYS_MULTCHECK_SCHEME set ");
        if (jsonObject.get("s_name") != null || jsonObject.get("s_name").getAsString().length() != 0) {
            sqlSb.append("s_name = ?,");
            paramList.add(jsonObject.get("s_name").getAsString());
        }
        if (jsonObject.get("s_order") != null) {
            sqlSb.append("s_order = ?,");
            paramList.add(Integer.parseInt(jsonObject.get("s_order").getAsString()));
        }
        if (jsonObject.get("s_dw") != null) {
            sqlSb.append("s_dw = ?,");
            paramList.add(this.gson.toJson((JsonElement)jsonObject.getAsJsonArray("s_dw")));
        }
        if (jsonObject.get("s_content") != null) {
            sqlSb.append("s_content = ?,");
            paramList.add(this.gson.toJson((JsonElement)jsonObject.getAsJsonArray("s_content")));
        }
        sqlSb.deleteCharAt(sqlSb.length() - 1);
        sqlSb.append(" where s_key = ?");
        paramList.add(key);
        String updateSql = sqlSb.toString();
        logger.info("updateSql: " + updateSql);
        StringBuffer paramSb = new StringBuffer();
        paramSb.append("updateSql  param: (");
        for (int i = 0; i < paramList.size(); ++i) {
            paramSb.append(paramList.get(i)).append(",");
        }
        paramSb.deleteCharAt(paramSb.length() - 1);
        paramSb.append(")");
        logger.info(paramSb.toString());
        int num = this.iMultCheckTableDao.updateData(updateSql, paramList);
        return num;
    }

    @Override
    public MultCheckTableBean selectData(String key) {
        MultCheckTableBean multCheckTableBean = new MultCheckTableBean();
        List<Map<String, Object>> dataList = null;
        try {
            dataList = this.iMultCheckTableDao.selectData(key);
            Map<String, Object> dataMap = dataList.get(0);
            multCheckTableBean.setS_key(key);
            multCheckTableBean.setS_name(dataMap.get("s_name").toString());
            multCheckTableBean.setS_order(Integer.parseInt(dataMap.get("s_order").toString()));
            List dwcodeList = (List)this.gson.fromJson(dataMap.get("s_dw").toString(), new TypeToken<List<String>>(){}.getType());
            multCheckTableBean.setS_dw(dwcodeList);
            List scheme_contentList = (List)this.gson.fromJson(dataMap.get("s_content").toString(), new TypeToken<List<Map<String, Object>>>(){}.getType());
            multCheckTableBean.setMultCheckTableDataItem(this.getMultCheckTableDataItemList(scheme_contentList));
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return multCheckTableBean;
    }

    @Override
    public void tabaleUpdateOperation(Map map) {
        String formId;
        Set entrySet = map.entrySet();
        HashMap<String, StringBuffer> map1 = new HashMap<String, StringBuffer>();
        for (Map.Entry entry : entrySet) {
            StringBuffer sbKey;
            String string = (String)entry.getKey();
            formId = entry.getValue().toString();
            if (!map1.containsKey(formId)) {
                sbKey = new StringBuffer();
                sbKey.append(string).append(",");
                map1.put(formId, sbKey);
                continue;
            }
            sbKey = (StringBuffer)map1.get(formId);
            sbKey.append(string).append(",");
            map1.put(formId, sbKey);
        }
        Set entrySet1 = map1.entrySet();
        try {
            for (Map.Entry entry : entrySet1) {
                formId = (String)entry.getKey();
                String dwCode = ((StringBuffer)entry.getValue()).toString();
                List<String> dwCodeList = Arrays.asList(dwCode.split(","));
                this.iMultCheckTableDao.updateDwCode(formId, this.gson.toJson(dwCodeList));
            }
        }
        catch (Exception exception) {
            logger.error(exception.getMessage());
        }
    }

    @Override
    public List<String> getCheckItemList() {
        ArrayList<String> allKeyList = new ArrayList<String>();
        try {
            List<Map<String, Object>> allDataList = this.iMultCheckTableDao.getCheckItemList();
            for (int i = 0; i < allDataList.size(); ++i) {
                Map<String, Object> dataMap = allDataList.get(i);
                List scheme_contentList = (List)this.gson.fromJson(dataMap.get("s_content").toString(), new TypeToken<List<Map<String, Object>>>(){}.getType());
                for (int j = 0; j < scheme_contentList.size(); ++j) {
                    Map map = (Map)scheme_contentList.get(j);
                    String key = map.get("key").toString();
                    allKeyList.add(key);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return allKeyList;
    }

    @Override
    public boolean deleteCheckResult(String taskKey, String formSchemeKey, List<String> checkItemList) {
        String tableName;
        FormSchemeDefine formSchemeDefine = this.runtimeView.getFormScheme(formSchemeKey);
        String libTableName = tableName = "SYS_MULTCHECK_" + formSchemeDefine.getFormSchemeCode();
        if (checkItemList.size() <= 0) {
            return false;
        }
        StringBuilder checkItemType = new StringBuilder();
        checkItemType.append("(");
        for (int i = 0; i < checkItemList.size(); ++i) {
            if (i == checkItemList.size() - 1) {
                checkItemType.append("'").append(checkItemList.get(i)).append("'").append(")");
                continue;
            }
            checkItemType.append("'").append(checkItemList.get(i)).append("'").append(",");
        }
        return this.iMultCheckTableDao.deleteLibTableName(libTableName, formSchemeKey, checkItemType);
    }

    @Override
    public List<Map<String, Object>> getSchemeList(String formschemeKey) {
        ArrayList<Map<String, Object>> schemeList = new ArrayList<Map<String, Object>>();
        try {
            List<Map<String, Object>> objectList = this.iMultCheckTableDao.getSchemeList(formschemeKey);
            for (int i = 0; i < objectList.size(); ++i) {
                HashMap<String, Object> schemeMap = new HashMap<String, Object>();
                Map<String, Object> map = objectList.get(i);
                String s_key = map.get("s_key").toString();
                String s_name = map.get("s_name").toString();
                List dwList = (List)this.gson.fromJson(map.get("s_dw").toString(), new TypeToken<List<String>>(){}.getType());
                schemeMap.put("s_key", s_key);
                schemeMap.put("s_name", s_name);
                schemeMap.put("s_dw", dwList);
                schemeList.add(schemeMap);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return schemeList;
    }

    public List<MultCheckTableBean.MultCheckTableDataItem> getMultCheckTableDataItemList(List<Map<String, Object>> scheme_contentList) {
        ArrayList<MultCheckTableBean.MultCheckTableDataItem> list = new ArrayList<MultCheckTableBean.MultCheckTableDataItem>();
        try {
            for (int i = 0; i < scheme_contentList.size(); ++i) {
                MultCheckTableBean.MultCheckTableDataItem<Object> multCheckTableDataItem = new MultCheckTableBean.MultCheckTableDataItem<Object>();
                Map<String, Object> map = scheme_contentList.get(i);
                int index = (int)Double.parseDouble(map.get("index").toString());
                if (map.get("auditScope") != null) {
                    String s = this.gson.toJson(map.get("auditScope"));
                    Object auditScope = this.gson.fromJson(s, new TypeToken<Object>(){}.getType());
                    multCheckTableDataItem.setAuditScope(auditScope);
                }
                multCheckTableDataItem.setIndex(index);
                multCheckTableDataItem.setKey(map.get("key").toString());
                multCheckTableDataItem.setMultCheckKey(map.get("multCheckKey").toString());
                multCheckTableDataItem.setMultCheckType(map.get("multCheckType").toString());
                multCheckTableDataItem.setMultCheckTypeName(MultCheckEnum.findByKey(map.get("multCheckType").toString()).getDesc());
                multCheckTableDataItem.setMultCheckName(map.get("multCheckName").toString());
                list.add(multCheckTableDataItem);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return list;
    }
}

