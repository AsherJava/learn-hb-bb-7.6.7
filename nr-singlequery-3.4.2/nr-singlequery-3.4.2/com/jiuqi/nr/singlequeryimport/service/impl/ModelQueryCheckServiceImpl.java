/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.singlequeryimport.intf.bean.IMultCheckItemBase
 *  com.jiuqi.nr.singlequeryimport.intf.bean.SearchModelItem
 *  com.jiuqi.nr.singlequeryimport.intf.service.IModelQueryCheckService
 *  com.jiuqi.nr.zbquery.model.ConditionValues
 *  com.jiuqi.nr.zbquery.model.PageInfo
 */
package com.jiuqi.nr.singlequeryimport.service.impl;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.singlequeryimport.bean.QueryConfigInfo;
import com.jiuqi.nr.singlequeryimport.bean.QueryModel;
import com.jiuqi.nr.singlequeryimport.bean.QueryResult;
import com.jiuqi.nr.singlequeryimport.dao.QueryModeleDao;
import com.jiuqi.nr.singlequeryimport.intf.bean.IMultCheckItemBase;
import com.jiuqi.nr.singlequeryimport.intf.bean.SearchModelItem;
import com.jiuqi.nr.singlequeryimport.intf.service.IModelQueryCheckService;
import com.jiuqi.nr.singlequeryimport.service.impl.QueryByCustomServiceImpl;
import com.jiuqi.nr.singlequeryimport.service.impl.QueryModleServiceImpl;
import com.jiuqi.nr.singlequeryimport.service.impl.SinglerQuserServiceImpl;
import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nr.zbquery.model.PageInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelQueryCheckServiceImpl
implements IModelQueryCheckService {
    Logger logger = LoggerFactory.getLogger(ModelQueryCheckServiceImpl.class);
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

    public SearchModelItem hasQueryData(String key, DimensionValue dw) throws Exception {
        this.logger.info(String.format("%s\u5f00\u59cb\u6a21\u677f", key));
        SearchModelItem item = new SearchModelItem();
        QueryModel dataModel = this.queryModeleDao.getData(key);
        item.setModelId(dataModel.getKey());
        item.setType(dataModel.getCustom());
        item.setName(dataModel.getItemTitle());
        item.setResult(false);
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(dataModel.getTaskKey());
        Map<String, String> taskInfo = this.queryByCustomService.getTaskInfo(taskDefine, dataModel.getFormschemeKey());
        try {
            QueryResult queryResult;
            FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(dataModel.getFormschemeKey());
            item.setOrg(formScheme.getDw());
            String period = taskInfo.get("period");
            String org = taskInfo.get("org");
            if (!dw.getValue().isEmpty()) {
                Object[] split = dw.getValue().split(";");
                ConditionValues conditionValues = new ConditionValues();
                conditionValues.putValue(org, (String[])split);
                conditionValues.putValue(period, new String[]{taskInfo.get("fromPeriod"), taskInfo.get("toPPeriod")});
                this.logger.info("\u6a21\u677f\u5ba1\u6838\u5355\u4f4d--\u300b" + Arrays.toString(split));
                queryResult = this.searchCustomModel(key, conditionValues);
            } else {
                this.logger.info("\u6a21\u677f\u5ba1\u6838\u5355\u4f4d--\u300b\u5ba1\u6838\u5168\u90e8\u5355\u4f4d");
                queryResult = this.searchCustomModel(key, null);
            }
            queryResult.setOrg(formScheme.getDw());
            item.setResult(queryResult.getPageInfo().getRecordSize() <= 0);
            this.logger.info(String.format("%s\u6a21\u677f\u7ed3\u675f\uff0c\u7ed3\u679c%s", key, queryResult.getPageInfo().getRecordSize()));
            return item;
        }
        catch (Exception e) {
            this.logger.error(e.getMessage());
            return item;
        }
    }

    public IMultCheckItemBase getCheckModel(String taskKey, String formSchemeKey) throws Exception {
        IMultCheckItemBase item = new IMultCheckItemBase("bmjsInquriy", "bmjsInquriy", "\u51b3\u7b97\u67e5\u8be2\u6a21\u7248");
        ArrayList<IMultCheckItemBase> childrenItem = new ArrayList<IMultCheckItemBase>();
        List<QueryModel> checkModel = this.queryModeleDao.getCheckModel(taskKey, formSchemeKey);
        Map<String, List<QueryModel>> collect = checkModel.stream().collect(Collectors.groupingBy(m -> m.getGroup()));
        for (String key : collect.keySet()) {
            List<QueryModel> queryModels = collect.get(key);
            ArrayList<IMultCheckItemBase> childrenGroupItem = new ArrayList<IMultCheckItemBase>();
            IMultCheckItemBase itemGroup = new IMultCheckItemBase(key, "bmjsInquriy", key);
            for (QueryModel model : queryModels) {
                childrenGroupItem.add(new IMultCheckItemBase(model.getKey(), "bmjsInquriy", model.getItemTitle()));
            }
            itemGroup.setChildren(childrenGroupItem);
            childrenItem.add(itemGroup);
        }
        item.setChildren(childrenItem);
        return item;
    }

    QueryResult searchCustomModel(String key, ConditionValues conditionValues) throws Exception {
        QueryConfigInfo queryConfigInfo = new QueryConfigInfo();
        queryConfigInfo.setModelId(key);
        queryConfigInfo.setPageInfo(new PageInfo(50, 1));
        queryConfigInfo.setConditionValues(conditionValues);
        queryConfigInfo.setCheckModel(true);
        return this.queryByCustom.query(queryConfigInfo);
    }
}

