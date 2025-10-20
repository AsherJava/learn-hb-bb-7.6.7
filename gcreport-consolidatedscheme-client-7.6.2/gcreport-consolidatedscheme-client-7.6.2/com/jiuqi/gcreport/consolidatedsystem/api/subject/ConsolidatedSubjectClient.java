/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  javax.validation.Valid
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.consolidatedsystem.api.subject;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectTreeVO;
import com.jiuqi.gcreport.consolidatedsystem.common.TreeNode;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectTreeNodeVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.SubjectBaseDataParam;
import com.jiuqi.gcreport.unionrule.vo.SubjectITree;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.consolidatedsystem.api.subject.ConsolidatedSubjectClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface ConsolidatedSubjectClient {
    public static final String CONSOLIDATED_SUBJECT_API_BASE_PATH = "/api/gcreport/v1/consolidatedSubjects";

    @GetMapping(value={"/api/gcreport/v1/consolidatedSubjects/{id}"})
    public BusinessResponseEntity<ConsolidatedSubjectVO> getConsolidatedSubjectById(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/consolidatedSubjects/delete/{ids}"})
    public BusinessResponseEntity<String> deleteConsolidatedSubject(@PathVariable(value="ids") @RequestBody String[] var1);

    @GetMapping(value={"/api/gcreport/v1/consolidatedSubjects/systems/{systemId}/{code}"})
    public BusinessResponseEntity<ConsolidatedSubjectVO> getConsolidatedSubjectByCode(@PathVariable(value="systemId") String var1, @PathVariable(value="code") String var2);

    @GetMapping(value={"/api/gcreport/v1/consolidatedSubjects/systems/{systemId}/list"})
    public BusinessResponseEntity<List<SelectOptionVO>> getAllConsolidatedSubjectList(@PathVariable(value="systemId") String var1);

    @GetMapping(value={"/api/gcreport/v1/consolidatedSubjects/systems/{systemId}/tree"})
    public BusinessResponseEntity<List<ConsolidatedSubjectTreeNodeVO>> getAllConsolidatedSubjectTree(@PathVariable(value="systemId") String var1);

    @GetMapping(value={"/api/gcreport/v1/consolidatedSubjects/systems/{systemId}/searchSubjectText"})
    public BusinessResponseEntity<List<ConsolidatedSubjectVO>> searchConsolidatedSubjects(@PathVariable(value="systemId") String var1, @RequestParam(value="searchSubjectText") String var2);

    @GetMapping(value={"/api/gcreport/v1/consolidatedSubjects/{id}/systems/{systemId}/table"})
    public BusinessResponseEntity<PageInfo> getChildrenConsolidatedSubjects(@PathVariable(value="id") String var1, @PathVariable(value="systemId") String var2, @RequestParam(value="isAllChildren") Boolean var3, @RequestParam(value="pageSize") Integer var4, @RequestParam(value="pageNum") Integer var5);

    @PostMapping(value={"/api/gcreport/v1/consolidatedSubjects"})
    public BusinessResponseEntity<ConsolidatedSubjectVO> addConsolidatedSubject(@Valid @RequestBody ConsolidatedSubjectVO var1);

    @PostMapping(value={"/api/gcreport/v1/consolidatedSubjects/batch"})
    public BusinessResponseEntity<List<ConsolidatedSubjectVO>> addConsolidatedSubjects(@Valid @RequestBody List<ConsolidatedSubjectVO> var1);

    @PostMapping(value={"/api/gcreport/v1/consolidatedSubjects/edit/{id}"})
    public BusinessResponseEntity<String> editConsolidatedSubject(@RequestParam(value="id") String var1, @Valid @RequestBody ConsolidatedSubjectVO var2);

    @PostMapping(value={"/api/gcreport/v1/consolidatedSubjects/opnode/{opnodeId}/exnode/{exnodeId}"})
    public BusinessResponseEntity<String> exchangeSort(@PathVariable(value="opnodeId") String var1, @PathVariable(value="exnodeId") String var2);

    @PostMapping(value={"/api/gcreport/v1/consolidatedSubjects/opnode/{opnodeId}/{step}"})
    public BusinessResponseEntity<String> exchangeSort2(@PathVariable(value="opnodeId") String var1, @PathVariable(value="step") Integer var2);

    @PostMapping(value={"/api/gcreport/v1/consolidatedSubjects/{id}/consolidationFlag/{isOpen}"})
    public BusinessResponseEntity<String> exchangeSort2(@PathVariable(value="id") String var1, @PathVariable(value="isOpen") Boolean var2);

    @GetMapping(value={"/api/gcreport/v1/consolidatedSubjects/treeBaseData/systems/{systemId}"})
    public BusinessResponseEntity<List<SubjectTreeVO>> treeBaseData(@PathVariable(value="systemId") String var1);

    @GetMapping(value={"/api/gcreport/v1/consolidatedSubjects/refresh"})
    public BusinessResponseEntity<String> refresh();

    @GetMapping(value={"/api/gcreport/v1/consolidatedSubjects/getMultilingualNamesTitle"})
    public BusinessResponseEntity<Map<String, String>> getMultilingualNamesTitle();

    @GetMapping(value={"/api/gcreport/v1/consolidatedSubjects/fieldDefines/{tableDefineKey}"})
    public BusinessResponseEntity<List<TreeNode>> getFieldDefinesByTable(@PathVariable(value="tableDefineKey") String var1);

    @PostMapping(value={"/api/gcreport/v1/consolidatedSubjects/matchEndZb/{systemId}/{formSchemeKey}/{searchContent}/{forceUpdate}"})
    public BusinessResponseEntity<String> matchEndZb(@PathVariable(value="systemId") String var1, @PathVariable(value="formSchemeKey") String var2, @PathVariable(value="searchContent") String var3, @PathVariable(value="forceUpdate") boolean var4, @RequestBody List<String> var5);

    @PostMapping(value={"/api/gcreport/v1/consolidatedSubjects/getConAccSubjectName"})
    public BusinessResponseEntity<Map<String, String>> getConAccSubjectName(Map<String, Object> var1);

    public List<ConsolidatedSubjectVO> listAllChildrenSubjects(String var1, String var2);

    @RequestMapping(value={"/api/gcreport/v1/consolidatedSubjects/listSubjectsBySystemId/{systemId}"}, method={RequestMethod.POST})
    public List<ConsolidatedSubjectVO> listSubjectsBySystemIdNoSortOrder(@PathVariable(value="systemId") String var1);

    @RequestMapping(value={"/api/gcreport/v1/consolidatedSubjects/listSubjectCodesBySystemId/{systemId}"}, method={RequestMethod.POST})
    public List<String> listSubjectCodesBySystemId(@PathVariable(value="systemId") String var1, @RequestBody List<String> var2);

    @RequestMapping(value={"/api/gcreport/v1/consolidatedSubjects/levelTree"}, method={RequestMethod.POST})
    public BusinessResponseEntity<Object> getBaseDataByParent(@RequestBody SubjectBaseDataParam var1);

    @RequestMapping(value={"/api/gcreport/v1/consolidatedSubjects/searchKey"}, method={RequestMethod.POST})
    public BusinessResponseEntity<List<GcBaseDataVO>> getBaseDataBySearch(@RequestBody SubjectBaseDataParam var1);

    @RequestMapping(value={"/api/gcreport/v1/consolidatedSubjects/singleData"}, method={RequestMethod.POST})
    public BusinessResponseEntity<GcBaseDataVO> getBaseDataByCode(@RequestBody SubjectBaseDataParam var1);

    @RequestMapping(value={"/api/gcreport/v1/consolidatedSubjects/allLevelTree"}, method={RequestMethod.POST})
    public BusinessResponseEntity<Object> listAllBaseDataByParent(@RequestBody SubjectBaseDataParam var1);

    @PostMapping(value={"/api/gcreport/v1/consolidatedSubjects/reloadSubjectTree"})
    public BusinessResponseEntity<List<SubjectITree<GcBaseDataVO>>> reloadSubjectTree(@RequestBody Map<String, Object> var1);
}

