/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.grid.GridData
 *  com.jiuqi.bi.grid.ncell.GridDataConverter
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter
 *  com.jiuqi.nvwa.cellbook.converter.CellBookGriddataConverter
 *  com.jiuqi.nvwa.cellbook.model.CellBook
 *  com.jiuqi.nvwa.cellbook.model.CellSheet
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  com.jiuqi.util.OrderGenerator
 *  nr.single.para.parain.bean.SingleQueryTemplateDefine
 *  nr.single.para.parain.bean.exception.SingleParaImportException
 *  nr.single.para.parain.bean.result.SingleQueryImportResult
 *  nr.single.para.parain.service.extension.ISingleQueryTemplateImportService
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.singlequeryimport.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.grid.ncell.GridDataConverter;
import com.jiuqi.nr.singlequeryimport.auth.FinalaccountQueryAuthResourceType;
import com.jiuqi.nr.singlequeryimport.auth.share.service.AuthShareService;
import com.jiuqi.nr.singlequeryimport.bean.DbSelectIndex;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao;
import com.jiuqi.nvwa.cellbook.converter.CellBookGrid2dataConverter;
import com.jiuqi.nvwa.cellbook.converter.CellBookGriddataConverter;
import com.jiuqi.nvwa.cellbook.model.CellBook;
import com.jiuqi.nvwa.cellbook.model.CellSheet;
import com.jiuqi.nvwa.grid2.Grid2Data;
import com.jiuqi.util.OrderGenerator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nr.single.para.parain.bean.SingleQueryTemplateDefine;
import nr.single.para.parain.bean.exception.SingleParaImportException;
import nr.single.para.parain.bean.result.SingleQueryImportResult;
import nr.single.para.parain.service.extension.ISingleQueryTemplateImportService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ISingleQueryTemplateImportServiceImpl
implements ISingleQueryTemplateImportService {
    Logger logger = LoggerFactory.getLogger(ISingleQueryTemplateImportServiceImpl.class);
    @Autowired
    private QueryModeleDao queryModeleDao;
    @Autowired
    AuthShareService authShareService;

    public SingleQueryImportResult importSingleQueryTemplates(List<SingleQueryTemplateDefine> queryModals) throws SingleParaImportException {
        try {
            for (SingleQueryTemplateDefine singleQueryTemplateDefine : queryModals) {
                JSONObject item = this.changeItme(singleQueryTemplateDefine);
                QueryModel queryModel = new QueryModel();
                queryModel.setKey(UUID.randomUUID().toString());
                queryModel.setOrder(OrderGenerator.newOrder());
                queryModel.setItem(item.toString());
                queryModel.setTaskKey(singleQueryTemplateDefine.getTaskKey());
                queryModel.setFormschemeKey(singleQueryTemplateDefine.getFormSchemeKey());
                queryModel.setItemTitle(singleQueryTemplateDefine.getTitle());
                queryModel.setCustom(0);
                queryModel.setDisUse(0);
                queryModel.setGroup(singleQueryTemplateDefine.getGroupNames().isEmpty() ? "\u9ed8\u8ba4\u5206\u7ec4" : singleQueryTemplateDefine.getGroupNames());
                QueryModel modelById = this.queryModeleDao.getModelById(queryModel);
                if (modelById != null) {
                    modelById.setItem(item.toString());
                    this.queryModeleDao.update(modelById);
                    this.logger.info("JIO\u53c2\u6570\u5bfc\u5165\u67e5\u8be2\u6a21\u677f\uff0c\u66f4\u65b0\u6a21\u677f---\u300b" + queryModel.getItemTitle());
                    continue;
                }
                this.queryModeleDao.insert(queryModel);
                this.logger.info("JIO\u53c2\u6570\u5bfc\u5165\u67e5\u8be2\u6a21\u677f\uff0c\u65b0\u589e\u6a21\u677f---\u300b" + queryModel.getItemTitle());
                if (StringUtils.hasText(queryModel.getItemTitle())) {
                    this.authShareService.addCurUserPrivilege(queryModel.getKey(), FinalaccountQueryAuthResourceType.FQ_MODEL_NODE);
                }
                if (!StringUtils.hasText(queryModel.getGroup())) continue;
                this.authShareService.addCurUserGroupPrivilege(queryModel.getFormschemeKey(), queryModel.getGroup(), FinalaccountQueryAuthResourceType.FQ_GROUP);
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        return null;
    }

    JSONObject changeItme(SingleQueryTemplateDefine singleQueryTemplateDefine) throws IOException {
        JSONObject item = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<DbSelectIndex> dbSelectIndexLists = new ArrayList<DbSelectIndex>();
        byte[] reportData = singleQueryTemplateDefine.getReportData();
        Grid2Data grid2Data = Grid2Data.bytesToGrid((byte[])reportData);
        CellSheet sheet = CellBookGrid2dataConverter.grid2DataToCellBook((Grid2Data)grid2Data, (CellBook)new CellBook(), (String)"sheet");
        GridData gridData = CellBookGriddataConverter.cellBookToGridData((CellSheet)sheet, (GridData)new GridData());
        JSONObject nCellData = GridDataConverter.buildNCellData((GridData)gridData, (boolean)false);
        int anInt = nCellData.getJSONObject("options").getJSONObject("header").getInt("rowHeader");
        if (anInt > 0) {
            nCellData.getJSONObject("options").getJSONObject("header").put("rowHeader", anInt - 1);
        }
        this.changeStyle(nCellData.getJSONArray("styles"));
        this.changeZbName(nCellData.getJSONArray("data"));
        JSONArray orders = new JSONArray();
        nCellData.put("orders", (Object)orders);
        this.deleteFistRow(nCellData);
        this.addSelectIndexLists(nCellData.getJSONArray("data"), dbSelectIndexLists, singleQueryTemplateDefine.getColNum() == 1, singleQueryTemplateDefine.isDoSumary());
        this.addIdentity(nCellData, singleQueryTemplateDefine.isDoSumary());
        item.put("model", (Object)nCellData);
        item.put("dbSelectIndexList", (Object)new JSONArray(objectMapper.writeValueAsString(dbSelectIndexLists)));
        item.put("custom", 0);
        item.put("headerRowNum", 1);
        item.put("totalLine", singleQueryTemplateDefine.isDoSumary() ? 1 : 0);
        item.put("columnNumber", singleQueryTemplateDefine.getColNum());
        JSONObject filter = new JSONObject();
        filter.put("formulaContent", (Object)singleQueryTemplateDefine.getCondition());
        filter.put("filterCondition", (Object)singleQueryTemplateDefine.getCondition());
        item.put("filter", (Object)filter);
        return item;
    }

    void addIdentity(JSONObject nCellData, Boolean totalLine) {
        JSONArray dataList = nCellData.getJSONArray("data");
        dataList.getJSONArray(0).getJSONObject(0).put("v", (Object)"\u8868\u5934\u680f");
        dataList.getJSONArray(dataList.length() - 1).getJSONObject(0).put("v", (Object)"\u5c0f\u6570\u4f4d");
        dataList.getJSONArray(dataList.length() - 2).getJSONObject(0).put("v", (Object)"\u8868\u8fbe\u5f0f");
        if (totalLine.booleanValue()) {
            dataList.getJSONArray(dataList.length() - 3).getJSONObject(0).put("v", (Object)"\u5408\u8ba1\u884c");
        }
    }

    void deleteFistRow(JSONObject nCellData) {
        JSONArray dataList = nCellData.getJSONArray("data");
        JSONArray rows = nCellData.getJSONArray("rows");
        JSONArray mergeInfoList = nCellData.getJSONArray("mergeInfo");
        dataList.remove(0);
        for (int i = 0; i < mergeInfoList.length(); ++i) {
            JSONObject mergeInfo = mergeInfoList.getJSONObject(i);
            int anInt = mergeInfo.getInt("rowIndex");
            mergeInfo.put("rowIndex", anInt - 1);
        }
        rows.remove(0);
    }

    void addSelectIndexLists(JSONArray dataModel, List<DbSelectIndex> dbSelectIndexLists, Boolean colNum, Boolean totalLine) {
        Integer ints;
        JSONArray zb = dataModel.getJSONArray(dataModel.length() - 2);
        ArrayList<String> nameList = new ArrayList<String>();
        JSONArray name = new JSONArray();
        Integer nameIndex = 3;
        if (totalLine.booleanValue()) {
            nameIndex = nameIndex + 1;
        }
        if ((name = dataModel.getJSONArray(dataModel.length() - nameIndex)).getJSONObject(0).getString("v").equals("\u680f\u6b21") || name.getJSONObject(1).getString("v").equals("\u680f\u6b21")) {
            nameIndex = nameIndex + 1;
        }
        Integer i = 0;
        while (i <= dataModel.length() - nameIndex) {
            Object jsonArray = dataModel.getJSONArray(i.intValue());
            for (int m = 0; m < jsonArray.length(); ++m) {
                if (i == 0) {
                    nameList.add(m, jsonArray.getJSONObject(m).getString("v"));
                    continue;
                }
                if (jsonArray.getJSONObject(m).getString("v").isEmpty()) continue;
                nameList.set(m, jsonArray.getJSONObject(m).getString("v"));
            }
            jsonArray = i;
            Integer m = i = Integer.valueOf(i + 1);
        }
        String patternZb = "[A-Z]*[0-9]*[_]*[0-9]*\\[[0-9]*,[0-9]*](@[0-9]*)?";
        Integer index = ints = Integer.valueOf(colNum != false ? 1 : 0);
        while (index < zb.length()) {
            DbSelectIndex dbSelectIndex = new DbSelectIndex();
            String expression = zb.getJSONObject(index.intValue()).getString("v");
            String zbName = (String)nameList.get(index);
            if (null != expression && expression.contains("DWMC")) {
                expression = expression.replace("DWMC", "NAME");
            }
            if (null != expression && expression.contains("SYS_ZDM")) {
                expression = expression.replace("SYS_ZDM", "ORGCODE");
            }
            dbSelectIndex.setTitle(zbName);
            dbSelectIndex.setData(expression);
            Matcher m = Pattern.compile(patternZb).matcher(expression);
            if (m.find()) {
                dbSelectIndex.setType("ZB");
            } else {
                dbSelectIndex.setType("FMDM");
            }
            dbSelectIndexLists.add(dbSelectIndex);
            Integer n = index;
            Integer n2 = index = Integer.valueOf(index + 1);
        }
    }

    void changeZbName(JSONArray modelList) {
        JSONArray zbList = modelList.getJSONArray(modelList.length() - 2);
        for (int index = 0; index < zbList.length(); ++index) {
            String string = zbList.getJSONObject(index).getString("v");
            if (string.contains("DWMC")) {
                string = string.replace("DWMC", "NAME");
                zbList.getJSONObject(index).put("v", (Object)string);
            }
            if (!string.contains("SYS_ZDM")) continue;
            string = string.replace("SYS_ZDM", "ORGCODE");
            zbList.getJSONObject(index).put("v", (Object)string);
        }
    }

    void changeStyle(JSONArray styleList) {
        for (int index = 0; index < styleList.length(); ++index) {
            JSONObject style = styleList.getJSONObject(index);
            if (style.has("wrapLine")) {
                style.put("wrapLine", false);
            }
            if (style.has("fitFontSize")) {
                style.put("fitFontSize", false);
            }
            if (!style.has("mode")) continue;
            style.put("mode", 0);
        }
    }

    void changeColumns(JSONArray columns) {
        for (int index = 0; index < columns.length(); ++index) {
            JSONObject column = columns.getJSONObject(index);
            int anInt = column.getInt("size");
            column.put("size", anInt + 15);
        }
    }
}

