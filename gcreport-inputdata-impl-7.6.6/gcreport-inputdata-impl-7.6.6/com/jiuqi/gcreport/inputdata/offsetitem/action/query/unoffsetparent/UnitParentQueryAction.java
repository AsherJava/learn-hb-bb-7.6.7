/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService
 *  com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum
 *  com.jiuqi.gcreport.offsetitem.factory.action.query.QueryActionUtils
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemNotOffsetPageImpl
 *  com.jiuqi.gcreport.offsetitem.utils.GcOffsetItemUtils
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.inputdata.offsetitem.action.query.unoffsetparent;

import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.inputdata.offsetitem.action.query.unoffset.UnitQueryAction;
import com.jiuqi.gcreport.inputdata.offsetitem.service.GcOffsetAppInputDataService;
import com.jiuqi.gcreport.offsetitem.enums.FilterMethodEnum;
import com.jiuqi.gcreport.offsetitem.factory.action.query.QueryActionUtils;
import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.gather.impl.GcOffsetItemNotOffsetPageImpl;
import com.jiuqi.gcreport.offsetitem.utils.GcOffsetItemUtils;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Component
public class UnitParentQueryAction
implements GcOffSetItemAction {
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private GcOffsetAppInputDataService gcOffsetAppInputDataService;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private UnitQueryAction unitQueryAction;
    @Autowired
    private QueryActionUtils queryActionUtils;

    public String code() {
        return "query";
    }

    public String title() {
        return "\u67e5\u8be2";
    }

    @Transactional(rollbackFor={Exception.class})
    public Object execute(GcOffsetExecutorVO gcOffsetExecutorVO) {
        QueryParamsVO queryParamsVO = (QueryParamsVO)gcOffsetExecutorVO.getParamObject();
        GcOffsetItemUtils.logOffsetEntryFilterCondition((QueryParamsVO)queryParamsVO, (String)"\u4e0a\u7ea7\u672a\u62b5\u9500");
        if (StringUtils.isEmpty(queryParamsVO.getOrgId())) {
            return this.unitQueryAction.execute(gcOffsetExecutorVO);
        }
        ArrayList<Object> params = new ArrayList<Object>();
        String sql = this.getQueryUnOffsetRecordsSql(queryParamsVO, params);
        Pagination<Map<String, Object>> pagination = this.gcOffsetAppInputDataService.listUnOffsetRecords(queryParamsVO, sql, params);
        if (CollectionUtils.isEmpty(pagination.getContent())) {
            return pagination;
        }
        if (this.queryActionUtils.isUnitTreeSort(FilterMethodEnum.UNIT.getCode(), queryParamsVO.getDataSourceCode(), GcOffsetItemNotOffsetPageImpl.newInstance().getPageCode())) {
            pagination.setContent(this.queryActionUtils.sortListByUnitTree(pagination.getContent(), queryParamsVO));
        }
        String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(queryParamsVO.getSchemeId(), queryParamsVO.getPeriodStr());
        this.gcOffsetAppInputDataService.setTitles(pagination, queryParamsVO, systemId);
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(queryParamsVO.getTaskId());
        LogHelper.info((String)"\u5408\u5e76-\u8c03\u6574\u62b5\u9500\u5206\u5f55", (String)("\u4e0a\u7ea7\u672a\u62b5\u9500\u67e5\u770b-\u4efb\u52a1-" + taskDefine.getTitle() + "-\u65f6\u671f-" + queryParamsVO.getAcctYear() + "\u5e74" + queryParamsVO.getAcctPeriod() + "\u6708"));
        return pagination;
    }

    private String getQueryUnOffsetRecordsSql(QueryParamsVO queryParamsVO, List<Object> params) {
        StringBuffer sql = this.gcOffsetAppInputDataService.getQueryUnOffsetRecordsSql(queryParamsVO, params, true);
        if (sql == null) {
            return null;
        }
        sql.append("order by (CASE WHEN record.%1$s>record.oppunitid THEN concat(record.%1$s, record.oppunitid) ELSE concat(record.oppunitid, record.%1$s) END),record.unionRuleId,record.DC desc\n");
        return String.format(sql.toString(), "MDCODE");
    }
}

