/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum
 *  com.jiuqi.gcreport.offsetitem.factory.action.query.QueryActionUtils
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemNotOffsetPageImpl
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  javax.annotation.Resource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.offsetitem.action.query.unoffset;

import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffSetAppInputDataItemService;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetAppInputDataService;
import com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum;
import com.jiuqi.gcreport.offsetitem.factory.action.query.QueryActionUtils;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemNotOffsetPageImpl;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class RuleQueryAction
implements GcOffSetItemAction {
    @Autowired
    private GcOffsetAppInputDataService gcOffsetAppInputDataService;
    @Resource
    private GcOffSetAppInputDataItemService adjustingEntryService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private QueryActionUtils queryActionUtils;

    public String code() {
        return "query";
    }

    public String title() {
        return "\u67e5\u8be2";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Transactional(rollbackFor={Exception.class})
    public Object execute(GcOffsetExecutorVO gcOffsetExecutorVO) {
        Pagination<Map<String, Object>> pagination;
        QueryParamsVO queryParamsVO = (QueryParamsVO)gcOffsetExecutorVO.getParamObject();
        this.adjustingEntryService.handleUnitAndOppUnitParam(queryParamsVO);
        try {
            ArrayList<Object> params = new ArrayList<Object>();
            String sql = this.getQueryUnOffsetRecordsSql(queryParamsVO, params);
            pagination = this.gcOffsetAppInputDataService.listUnOffsetRecords(queryParamsVO, sql, params);
            if (this.queryActionUtils.isUnitTreeSort(FilterMethodEnum.RULE.getCode(), queryParamsVO.getDataSourceCode(), GcOffsetItemNotOffsetPageImpl.newInstance().getPageCode())) {
                pagination.setContent(this.queryActionUtils.sortListByUnitTree(pagination.getContent(), queryParamsVO));
            }
        }
        finally {
            if (!CollectionUtils.isEmpty(queryParamsVO.getTempGroupIdList())) {
                IdTemporaryTableUtils.deteteByGroupIds((Collection)queryParamsVO.getTempGroupIdList());
            }
        }
        String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        this.gcOffsetAppInputDataService.setTitles(pagination, queryParamsVO, systemId);
        return pagination;
    }

    private String getQueryUnOffsetRecordsSql(QueryParamsVO queryParamsVO, List<Object> params) {
        StringBuffer sql = this.gcOffsetAppInputDataService.getQueryUnOffsetRecordsSql(queryParamsVO, params, false);
        if (!StringUtils.hasLength(sql)) {
            return "";
        }
        sql.append("order by record.unionRuleId,(CASE WHEN record.%1$s>record.oppunitid THEN concat(record.%1$s, record.oppunitid) ELSE concat(record.oppunitid, record.%1$s) END),record.DC desc\n");
        return String.format(sql.toString(), "MDCODE");
    }
}

