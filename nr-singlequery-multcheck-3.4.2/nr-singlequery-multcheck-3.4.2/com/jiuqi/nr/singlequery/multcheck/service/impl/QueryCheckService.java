/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.singlequeryimport.bean.QueryConfigInfo
 *  com.jiuqi.nr.singlequeryimport.bean.QueryModel
 *  com.jiuqi.nr.singlequeryimport.bean.QueryResult
 *  com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao
 *  com.jiuqi.nr.singlequeryimport.intf.bean.SearchModelItem
 *  com.jiuqi.nr.singlequeryimport.service.impl.QueryByCustomServiceImpl
 *  com.jiuqi.nr.singlequeryimport.service.impl.QueryModleServiceImpl
 *  com.jiuqi.nr.singlequeryimport.service.impl.SinglerQuserServiceImpl
 *  com.jiuqi.nr.zbquery.model.ConditionValues
 *  com.jiuqi.nr.zbquery.model.PageInfo
 */
package com.jiuqi.nr.singlequery.multcheck.service.impl;

import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.singlequery.multcheck.service.IQueryCheckService;
import com.jiuqi.nr.singlequeryimport.bean.QueryConfigInfo;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.bean.QueryResult;
import com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao;
import com.jiuqi.nr.singlequeryimport.intf.bean.SearchModelItem;
import com.jiuqi.nr.singlequeryimport.service.impl.QueryByCustomServiceImpl;
import com.jiuqi.nr.singlequeryimport.service.impl.QueryModleServiceImpl;
import com.jiuqi.nr.singlequeryimport.service.impl.SinglerQuserServiceImpl;
import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nr.zbquery.model.PageInfo;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryCheckService
implements IQueryCheckService {
    Logger logger = LoggerFactory.getLogger(QueryCheckService.class);
    @Autowired
    SinglerQuserServiceImpl service;
    @Autowired
    IRunTimeViewController iRunTimeViewController;
    @Autowired
    QueryModleServiceImpl queryModleService;
    @Autowired
    QueryByCustomServiceImpl queryByCustom;
    @Autowired
    QueryModeleDao queryModeleDao;
    @Autowired
    IEntityMetaService iEntityMetaService;
    @Autowired
    QueryByCustomServiceImpl queryByCustomService;

    @Override
    public SearchModelItem queryCheck(String modelKey, List<String> orgList) throws Exception {
        this.logger.info(String.format("%s\u5f00\u59cb\u6a21\u677f", modelKey));
        SearchModelItem item = new SearchModelItem();
        QueryModel dataModel = this.queryModeleDao.getData(modelKey);
        item.setModelId(dataModel.getKey());
        item.setType(dataModel.getCustom());
        item.setName(dataModel.getItemTitle());
        item.setResult(false);
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(dataModel.getTaskKey());
        Map taskInfo = this.queryByCustomService.getTaskInfo(taskDefine, dataModel.getFormschemeKey());
        try {
            QueryResult queryResult;
            FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(dataModel.getFormschemeKey());
            item.setOrg(formScheme.getDw());
            String period = (String)taskInfo.get("period");
            String org = (String)taskInfo.get("org");
            if (orgList.size() > 0) {
                ConditionValues conditionValues = new ConditionValues();
                conditionValues.putValue(org, orgList.toArray(new String[0]));
                conditionValues.putValue(period, new String[]{(String)taskInfo.get("fromPeriod"), (String)taskInfo.get("toPPeriod")});
                this.logger.info("\u6a21\u677f\u5ba1\u6838\u5355\u4f4d--\u300b" + Arrays.toString(orgList.toArray(new String[0])));
                queryResult = this.searchCustomModel(modelKey, conditionValues);
            } else {
                this.logger.info("\u6a21\u677f\u5ba1\u6838\u5355\u4f4d--\u300b\u5ba1\u6838\u5168\u90e8\u5355\u4f4d");
                queryResult = this.searchCustomModel(modelKey, null);
            }
            queryResult.setOrg(formScheme.getDw());
            item.setResult(queryResult.getPageInfo().getRecordSize() == 0);
            this.logger.info(String.format("%s\u6a21\u677f\u7ed3\u675f\uff0c\u7ed3\u679c%s", modelKey, queryResult.getPageInfo().getRecordSize()));
            return item;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return item;
        }
    }

    QueryResult searchCustomModel(String modelKey, ConditionValues conditionValues) throws Exception {
        QueryConfigInfo queryConfigInfo = new QueryConfigInfo();
        queryConfigInfo.setModelId(modelKey);
        queryConfigInfo.setPageInfo(new PageInfo(50, 1));
        queryConfigInfo.setConditionValues(conditionValues);
        queryConfigInfo.setCheckModel(Boolean.valueOf(true));
        return this.queryByCustom.query(queryConfigInfo);
    }
}

