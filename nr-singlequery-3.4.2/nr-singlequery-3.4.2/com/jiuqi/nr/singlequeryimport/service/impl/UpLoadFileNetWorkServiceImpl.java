/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.nr.dataentry.service.IDataEntryParamService
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.designer.service.StepSaveService
 *  com.jiuqi.nr.designer.web.treebean.TaskLinkObject
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.internal.service.ZBQueryService
 *  com.jiuqi.nr.zbquery.rest.param.FieldSelectParam
 *  com.jiuqi.util.OrderGenerator
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.nr.singlequeryimport.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.designer.service.StepSaveService;
import com.jiuqi.nr.designer.web.treebean.TaskLinkObject;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.internal.service.ZBQueryService;
import com.jiuqi.nr.singlequeryimport.auth.service.impl.FinalaccountQueryAuthServiceImpl;
import com.jiuqi.nr.singlequeryimport.auth.share.service.AuthShareService;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao;
import com.jiuqi.nr.singlequeryimport.service.QueryModleService;
import com.jiuqi.nr.singlequeryimport.service.UpLoadFileNetWorkService;
import com.jiuqi.nr.singlequeryimport.service.impl.QueryModleServiceImpl;
import com.jiuqi.nr.singlequeryimport.service.impl.SinglerQuserServiceImpl;
import com.jiuqi.nr.zbquery.rest.param.FieldSelectParam;
import com.jiuqi.util.OrderGenerator;
import java.util.ArrayList;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpLoadFileNetWorkServiceImpl
implements UpLoadFileNetWorkService {
    private static final Logger logger = LoggerFactory.getLogger(QueryModleServiceImpl.class);
    @Autowired
    private QueryModeleDao queryModeleDao;
    @Autowired
    IRunTimeViewController iRunTimeViewController;
    @Autowired
    IRuntimeDataSchemeService iRuntimeDataSchemeService;
    @Autowired
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private StepSaveService stepSaveService;
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

    @Override
    public StringBuffer saveNetWorkMyModle(JSONObject modalDefine, String taskKey, String formSchemeKey) throws Exception {
        StringBuffer info = new StringBuffer();
        String title = modalDefine.getString("title");
        String groupTitle = modalDefine.getString("group");
        modalDefine.remove("title");
        modalDefine.remove("group");
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null) {
            throw new Exception(taskKey + "\u6ca1\u6709\u8be5\u4efb\u52a1\u6807\u8bc6");
        }
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        if (null == formSchemeDefine) {
            throw new Exception("\u6ca1\u6709\u8be5\u62a5\u8868\u65b9\u6848" + formSchemeKey);
        }
        this.changeItem(modalDefine, taskKey, formSchemeKey);
        String uuid = UUID.randomUUID().toString();
        QueryModel queryModel = new QueryModel();
        queryModel.setKey(uuid);
        queryModel.setOrder(OrderGenerator.newOrder());
        queryModel.setItem(modalDefine.toString());
        queryModel.setTaskKey(taskKey);
        queryModel.setFormschemeKey(formSchemeKey);
        queryModel.setItemTitle(title);
        queryModel.setCustom(0);
        queryModel.setDisUse(0);
        queryModel.setOrder(modalDefine.getString("order"));
        queryModel.setGroup(groupTitle == null ? "\u9ed8\u8ba4\u5206\u7ec4" : groupTitle);
        QueryModel modelById = this.queryModeleDao.getModelById(queryModel);
        if (modelById != null) {
            modelById.setItem(modalDefine.toString());
            this.queryModleService.upData(modelById);
            logger.info("{}--\u66f4\u65b0\u6210\u529f", (Object)title);
        } else {
            this.queryModleService.saveData(queryModel);
            logger.info("{}--\u5bfc\u5165\u6210\u529f", (Object)title);
        }
        if (info.length() > 0) {
            info.insert(0, title + "--------->");
        }
        return info;
    }

    void changeItem(JSONObject modalDefine, String taskKey, String formSchemeKey) throws Exception {
        modalDefine.remove("dbSelectIndexList");
        modalDefine.remove("params");
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
        DataScheme dataScheme = this.iRuntimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String prefix = dataScheme.getPrefix();
        FormSchemeDefine formSchemeDefine = this.iRunTimeViewController.getFormScheme(formSchemeKey);
        ArrayList taskLinkObjBySchemeKey = this.stepSaveService.getTaskLinkObjBySchemeKey(formSchemeDefine.getKey());
        String relatePrefix = "";
        if (!taskLinkObjBySchemeKey.isEmpty()) {
            FormSchemeDefine relateFormScheme = this.iRunTimeViewController.getFormScheme(((TaskLinkObject)taskLinkObjBySchemeKey.get(0)).getRelatedFormSchemeKey());
            TaskDefine relateTaskDefine = this.iRunTimeViewController.queryTaskDefine(relateFormScheme.getTaskKey());
            DataScheme relateDataScheme = this.iRuntimeDataSchemeService.getDataScheme(relateTaskDefine.getDataScheme());
            relatePrefix = relateDataScheme.getPrefix();
        }
        JSONObject filter = modalDefine.getJSONObject("filter");
        int isTotal = modalDefine.getInt("totalLine");
        if (!filter.isEmpty()) {
            String code;
            if (filter.has("filterCondition")) {
                code = this.changZb(filter.getString("filterCondition"), relatePrefix, prefix);
                filter.put("filterCondition", (Object)code);
            }
            if (filter.has("formulaContent")) {
                code = this.changZb(filter.getString("formulaContent"), relatePrefix, prefix);
                filter.put("formulaContent", (Object)code);
            }
        }
        JSONObject model = modalDefine.getJSONObject("model");
        JSONArray data = model.getJSONArray("data");
        JSONArray zbList = data.getJSONArray(data.length() - 2);
        JSONArray hearList = isTotal == 0 ? data.getJSONArray(data.length() - 3) : data.getJSONArray(data.length() - 4);
        for (int index = 0; index < zbList.length(); ++index) {
            String zb = zbList.getJSONObject(index).getString("v");
            String code = this.changZb(zb, relatePrefix, prefix);
            zbList.getJSONObject(index).put("v", (Object)code);
        }
        FieldSelectParam param = new FieldSelectParam();
        ArrayList zbs = new ArrayList();
        ArrayList dbSelectIndexLists = new ArrayList();
        param.setZbs(zbs);
        modalDefine.put("params", (Object)new JSONObject(this.mapper.writeValueAsString((Object)param)));
        modalDefine.put("dbSelectIndexList", (Object)new JSONArray(this.mapper.writeValueAsString(dbSelectIndexLists)));
    }

    String changZb(String code, String relatePrefix, String prefix) {
        if (code.contains("RELATE")) {
            code = code.replace("RELATE", relatePrefix);
        }
        if (code.contains("MAIN")) {
            code = code.replace("MAIN", prefix);
        }
        return code;
    }
}

