/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.common.dto.Pagination
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.journalsingle.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.common.dto.Pagination;
import com.jiuqi.gcreport.journalsingle.condition.JournalSubjectTreeCondition;
import com.jiuqi.gcreport.journalsingle.vo.JournalSubjectTreeVO;
import com.jiuqi.gcreport.journalsingle.vo.JournalSubjectVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.journalsingle.api.JournalSingleSubjectClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface JournalSingleSubjectClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/journalSingleSubject";

    @PostMapping(value={"/api/gcreport/v1/journalSingleSubject/insertSubject"})
    public BusinessResponseEntity<String> insertSubject(@RequestBody JournalSubjectVO var1);

    @PostMapping(value={"/api/gcreport/v1/journalSingleSubject/batchUpdateSubject"})
    public BusinessResponseEntity<String> batchUpdateSubject(@RequestBody JournalSubjectVO[] var1);

    @PostMapping(value={"/api/gcreport/v1/journalSingleSubject/deleteSubject/{ids}"})
    public BusinessResponseEntity<String> deleteSubject(@PathVariable(value="ids") String[] var1);

    @PostMapping(value={"/api/gcreport/v1/journalSingleSubject/exchangeSort/{opNodeId}/{step}"})
    public BusinessResponseEntity<String> exchangeSort(@PathVariable(value="opNodeId") String var1, @PathVariable(value="step") int var2);

    @GetMapping(value={"/api/gcreport/v1/journalSingleSubject/listChildSubjectsOrSelf/{parentId}"})
    public BusinessResponseEntity<Pagination<JournalSubjectVO>> listChildSubjectsOrSelf(@PathVariable(value="parentId") String var1, @RequestParam(value="pageNum") int var2, @RequestParam(value="pageSize") int var3, @RequestParam(value="isAllChildren") boolean var4);

    @GetMapping(value={"/api/gcreport/v1/journalSingleSubject/listSubjectTree/{jRelateSchemeId}/{expandId}"})
    public BusinessResponseEntity<List<JournalSubjectTreeVO>> listSubjectTree(@PathVariable(value="jRelateSchemeId") String var1, @PathVariable(value="expandId") String var2);

    @PostMapping(value={"/api/gcreport/v1/journalSingleSubject/listSubjectFilterTree"})
    public BusinessResponseEntity<List<JournalSubjectTreeVO>> listSubjectFilterTree(@RequestBody JournalSubjectTreeCondition var1);
}

