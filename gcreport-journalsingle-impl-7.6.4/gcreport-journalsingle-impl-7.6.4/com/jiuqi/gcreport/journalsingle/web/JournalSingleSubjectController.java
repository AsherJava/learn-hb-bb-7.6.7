/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.common.dto.Pagination
 *  com.jiuqi.gcreport.journalsingle.api.JournalSingleSubjectClient
 *  com.jiuqi.gcreport.journalsingle.condition.JournalSubjectTreeCondition
 *  com.jiuqi.gcreport.journalsingle.vo.JournalSubjectTreeVO
 *  com.jiuqi.gcreport.journalsingle.vo.JournalSubjectVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.journalsingle.web;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.common.dto.Pagination;
import com.jiuqi.gcreport.journalsingle.api.JournalSingleSubjectClient;
import com.jiuqi.gcreport.journalsingle.condition.JournalSubjectTreeCondition;
import com.jiuqi.gcreport.journalsingle.service.IJournalSingleSubjectService;
import com.jiuqi.gcreport.journalsingle.vo.JournalSubjectTreeVO;
import com.jiuqi.gcreport.journalsingle.vo.JournalSubjectVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class JournalSingleSubjectController
implements JournalSingleSubjectClient {
    @Autowired
    private IJournalSingleSubjectService journalSingleSubjectService;

    public BusinessResponseEntity<String> insertSubject(JournalSubjectVO journalSubjectVO) {
        this.journalSingleSubjectService.insertSubject(journalSubjectVO);
        return BusinessResponseEntity.ok((Object)"\u65b0\u589e\u6210\u529f");
    }

    public BusinessResponseEntity<String> batchUpdateSubject(JournalSubjectVO[] journalSubjectVOs) {
        try {
            this.journalSingleSubjectService.batchUpdateSubject(journalSubjectVOs);
            return BusinessResponseEntity.ok((Object)"\u4fee\u6539\u6210\u529f");
        }
        catch (BusinessRuntimeException e) {
            return BusinessResponseEntity.error((String)e.getMessage());
        }
    }

    public BusinessResponseEntity<String> deleteSubject(String[] ids) {
        this.journalSingleSubjectService.batchDeleteSubject(ids);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
    }

    public BusinessResponseEntity<String> exchangeSort(String opNodeId, int step) {
        try {
            this.journalSingleSubjectService.exchangeSort(opNodeId, step);
            return BusinessResponseEntity.ok((Object)"\u64cd\u4f5c\u6210\u529f");
        }
        catch (BusinessRuntimeException e) {
            return BusinessResponseEntity.error((String)e.getMessage());
        }
    }

    public BusinessResponseEntity<Pagination<JournalSubjectVO>> listChildSubjectsOrSelf(String parentId, int pageNum, int pageSize, boolean isAllChildren) {
        return BusinessResponseEntity.ok(this.journalSingleSubjectService.listChildSubjectsOrSelf(parentId, isAllChildren, pageNum, pageSize));
    }

    public BusinessResponseEntity<List<JournalSubjectTreeVO>> listSubjectTree(String jRelateSchemeId, String expandId) {
        return BusinessResponseEntity.ok(this.journalSingleSubjectService.listSubjectTree(jRelateSchemeId, expandId, false));
    }

    public BusinessResponseEntity<List<JournalSubjectTreeVO>> listSubjectFilterTree(JournalSubjectTreeCondition condition) {
        return BusinessResponseEntity.ok(this.journalSingleSubjectService.listSubjectFilterTree(condition));
    }
}

