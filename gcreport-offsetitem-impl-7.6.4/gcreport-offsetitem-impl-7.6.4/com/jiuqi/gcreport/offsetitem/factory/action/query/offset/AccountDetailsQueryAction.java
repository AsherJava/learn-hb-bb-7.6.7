/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction
 *  com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustCoreServiceImpl
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.offsetitem.factory.action.query.offset;

import com.jiuqi.gcreport.offsetitem.gather.GcOffSetItemAction;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetAppOffsetService;
import com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetItemAdjustCoreServiceImpl;
import com.jiuqi.gcreport.offsetitem.vo.GcOffsetExecutorVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class AccountDetailsQueryAction
implements GcOffSetItemAction {
    @Autowired
    private GcOffSetAppOffsetService gcOffSetItemAdjustService;
    @Autowired
    private GcOffSetItemAdjustCoreServiceImpl offSetItemAdjustService;

    public String code() {
        return "query";
    }

    public String title() {
        return "\u67e5\u8be2";
    }

    public Object execute(GcOffsetExecutorVO gcOffsetExecutorVO) {
        QueryParamsVO queryParamsVO = (QueryParamsVO)gcOffsetExecutorVO.getParamObject();
        queryParamsVO.setFilterDisableItem(false);
        Pagination<Map<String, Object>> offsetMap = this.gcOffSetItemAdjustService.listOffsetEntrys(queryParamsVO);
        if (queryParamsVO.getFilterCondition().containsKey("subjectVo")) {
            List selectedSubjects = (List)queryParamsVO.getFilterCondition().get("subjectVo");
            if (CollectionUtils.isEmpty(selectedSubjects)) {
                return offsetMap;
            }
            List selectedSubjectList = selectedSubjects.stream().map(selectedSubject -> (String)selectedSubject.get("code")).collect(Collectors.toList());
            ArrayList contentListMap = new ArrayList();
            offsetMap.getContent().forEach(entity -> {
                if (selectedSubjectList.contains(entity.getOrDefault("SUBJECTCODE", ""))) {
                    contentListMap.add(entity);
                }
            });
            offsetMap.setContent(this.offSetItemAdjustService.setRowSpanAndSort(contentListMap));
        }
        return offsetMap;
    }
}

