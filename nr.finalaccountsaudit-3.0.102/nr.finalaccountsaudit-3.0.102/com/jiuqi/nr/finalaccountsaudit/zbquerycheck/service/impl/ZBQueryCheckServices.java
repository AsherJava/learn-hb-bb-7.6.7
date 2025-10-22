/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.zbquery.engine.ZBQueryEngine
 *  com.jiuqi.nr.zbquery.engine.ZBQueryResult
 *  com.jiuqi.nr.zbquery.model.ConditionValues
 *  com.jiuqi.nr.zbquery.model.PageInfo
 *  com.jiuqi.nr.zbquery.model.ZBQueryModel
 *  com.jiuqi.nr.zbquery.rest.vo.QueryConfigVO
 *  com.jiuqi.nr.zbquery.service.impl.ZBQueryInfoServiceImpl
 *  com.jiuqi.nvwa.dataanalyze.api.ResourceTreeApi
 *  com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode
 */
package com.jiuqi.nr.finalaccountsaudit.zbquerycheck.service.impl;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.DataAnalysisItem;
import com.jiuqi.nr.finalaccountsaudit.zbquerycheck.common.ZBQueryCheckUtil;
import com.jiuqi.nr.finalaccountsaudit.zbquerycheck.service.IZBQueryCheckServices;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.zbquery.engine.ZBQueryEngine;
import com.jiuqi.nr.zbquery.engine.ZBQueryResult;
import com.jiuqi.nr.zbquery.model.ConditionValues;
import com.jiuqi.nr.zbquery.model.PageInfo;
import com.jiuqi.nr.zbquery.model.ZBQueryModel;
import com.jiuqi.nr.zbquery.rest.vo.QueryConfigVO;
import com.jiuqi.nr.zbquery.service.impl.ZBQueryInfoServiceImpl;
import com.jiuqi.nvwa.dataanalyze.api.ResourceTreeApi;
import com.jiuqi.nvwa.dataanalyze.dto.ResourceTreeNode;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZBQueryCheckServices
implements IZBQueryCheckServices {
    private static final Logger logger = LoggerFactory.getLogger(ZBQueryCheckServices.class);
    @Autowired
    private ZBQueryInfoServiceImpl zbQueryServiceImpl;
    @Autowired
    ZBQueryCheckUtil zbQueryCheckUtil;
    @Autowired
    ResourceTreeApi resourceTreeApi;

    @Override
    public DataAnalysisItem hasQueryData(String modelId, JtableContext context) {
        DataAnalysisItem analysisItem = new DataAnalysisItem();
        try {
            ZBQueryModel zbQueryModel = this.zbQueryServiceImpl.getQueryInfoData(modelId);
            ResourceTreeNode resourceTreeNode = this.resourceTreeApi.get(modelId);
            analysisItem.setModelId(modelId);
            analysisItem.setType("zbQueryTemplate");
            analysisItem.setName(resourceTreeNode.getTitle());
            analysisItem.setResult(true);
            QueryConfigVO queryConfig = new QueryConfigVO();
            queryConfig.setQueryModel(zbQueryModel);
            ConditionValues conditionValues = this.getConditionValues(context, zbQueryModel);
            queryConfig.setConditionValues(conditionValues);
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPageIndex(1);
            pageInfo.setPageSize(50);
            pageInfo.setRecordSize(-1);
            queryConfig.setPageInfo(pageInfo);
            ZBQueryEngine queryEngine = new ZBQueryEngine(queryConfig.getCacheId(), queryConfig.getQueryModel());
            ZBQueryResult queryResult = queryEngine.query(queryConfig.getConditionValues(), queryConfig.getPageInfo());
            if (queryResult != null && queryResult.getPageInfo() != null && queryResult.getPageInfo().getRecordSize() > 0) {
                analysisItem.setResult(false);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            analysisItem.setResult(false);
        }
        return analysisItem;
    }

    private ConditionValues getConditionValues(JtableContext context, ZBQueryModel zbQueryModel) {
        ConditionValues conditionValues = null;
        if (context == null) {
            return new ConditionValues();
        }
        Map<String, DimensionValue> dimensionValueSet = this.zbQueryCheckUtil.getDimensionValueSet(context);
        conditionValues = this.zbQueryCheckUtil.dimensionToConditionValues(dimensionValueSet, zbQueryModel.getConditions());
        return conditionValues;
    }
}

