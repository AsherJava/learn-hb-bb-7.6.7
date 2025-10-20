/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.basedata.api.itree.INode
 *  com.jiuqi.gcreport.basedata.api.itree.ITree
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.api.subject.ConsolidatedSubjectClient
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectTreeVO
 *  com.jiuqi.gcreport.consolidatedsystem.common.TreeNode
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectTreeNodeVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectTreeVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.SubjectBaseDataParam
 *  com.jiuqi.gcreport.unionrule.vo.SubjectITree
 *  com.jiuqi.va.basedata.domain.BaseDataSyncCacheDTO
 *  com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.consolidatedsystem.web.subject;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.basedata.api.itree.INode;
import com.jiuqi.gcreport.basedata.api.itree.ITree;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.api.subject.ConsolidatedSubjectClient;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectTreeVO;
import com.jiuqi.gcreport.consolidatedsystem.common.TreeNode;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSubjectUIService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectBaseDataService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils;
import com.jiuqi.gcreport.consolidatedsystem.util.SubjectConvertUtil;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectTreeNodeVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectTreeVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.SubjectBaseDataParam;
import com.jiuqi.gcreport.unionrule.vo.SubjectITree;
import com.jiuqi.va.basedata.domain.BaseDataSyncCacheDTO;
import com.jiuqi.va.basedata.service.impl.help.BaseDataCacheService;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.ShiroUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class ConsolidatedSubjectController
implements ConsolidatedSubjectClient {
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private BaseDataCacheService baseDataCacheService;
    @Autowired
    private ConsolidatedSubjectBaseDataService subjectBaseDataService;
    @Autowired
    private ConsolidatedSubjectUIService consolidatedSubjectUIService;

    public BusinessResponseEntity<ConsolidatedSubjectVO> getConsolidatedSubjectById(@PathVariable(value="id") String id) {
        ConsolidatedSubjectVO consolidatedSubjectVO = this.consolidatedSubjectUIService.getSubjectById(id);
        return BusinessResponseEntity.ok((Object)consolidatedSubjectVO);
    }

    public BusinessResponseEntity<String> deleteConsolidatedSubject(@PathVariable(value="ids") @RequestBody String[] ids) {
        this.consolidatedSubjectUIService.deleteSubjects(ids);
        return BusinessResponseEntity.ok((Object)"\u64cd\u4f5c\u6210\u529f.");
    }

    public BusinessResponseEntity<ConsolidatedSubjectVO> getConsolidatedSubjectByCode(@PathVariable(value="systemId") String systemId, @PathVariable(value="code") String code) {
        ConsolidatedSubjectEO consolidatedSubjectEO = this.consolidatedSubjectService.getSubjectByCode(systemId, code);
        return BusinessResponseEntity.ok((Object)SubjectConvertUtil.convertEO2VO(consolidatedSubjectEO));
    }

    public BusinessResponseEntity<List<SelectOptionVO>> getAllConsolidatedSubjectList(@PathVariable(value="systemId") String systemId) {
        List<SelectOptionVO> subjects = this.consolidatedSubjectUIService.listAllSubjectsWithOption(systemId);
        return BusinessResponseEntity.ok(subjects);
    }

    public BusinessResponseEntity<List<ConsolidatedSubjectTreeNodeVO>> getAllConsolidatedSubjectTree(@PathVariable(value="systemId") String systemId) {
        ConsolidatedSubjectTreeVO subjectTreeVO = this.consolidatedSubjectUIService.getSubjectTree(systemId);
        return BusinessResponseEntity.ok((Object)subjectTreeVO.getTree());
    }

    public BusinessResponseEntity<List<ConsolidatedSubjectVO>> searchConsolidatedSubjects(@PathVariable(value="systemId") String systemId, @RequestParam(value="searchSubjectText") String searchSubjectText) {
        return BusinessResponseEntity.ok(this.consolidatedSubjectUIService.listSubjectsBySearchKey(systemId, searchSubjectText));
    }

    public BusinessResponseEntity<PageInfo> getChildrenConsolidatedSubjects(@PathVariable(value="id") String id, @PathVariable(value="systemId") String systemId, @RequestParam(value="isAllChildren") Boolean isAllChildren, @RequestParam(value="pageSize") Integer pageSize, @RequestParam(value="pageNum") Integer pageNum) {
        PageInfo consolidatedSubjectPage = this.consolidatedSubjectUIService.listSubjects(systemId, id, isAllChildren, pageSize, pageNum - 1);
        return BusinessResponseEntity.ok((Object)consolidatedSubjectPage);
    }

    public BusinessResponseEntity<ConsolidatedSubjectVO> addConsolidatedSubject(@Valid @RequestBody ConsolidatedSubjectVO consolidatedSubjectVO) {
        ConsolidatedSubjectVO savedSubject = this.consolidatedSubjectUIService.saveSubject(consolidatedSubjectVO);
        return BusinessResponseEntity.ok((Object)savedSubject);
    }

    public BusinessResponseEntity<List<ConsolidatedSubjectVO>> addConsolidatedSubjects(@Valid @RequestBody List<ConsolidatedSubjectVO> consolidatedSubjectVOS) {
        List<ConsolidatedSubjectVO> savedSubjects = this.consolidatedSubjectUIService.saveSubjects(consolidatedSubjectVOS);
        return BusinessResponseEntity.ok(savedSubjects);
    }

    public BusinessResponseEntity<String> editConsolidatedSubject(@RequestParam(value="id") String id, @Valid @RequestBody ConsolidatedSubjectVO consolidatedSubjectVO) {
        this.consolidatedSubjectUIService.updateSubject(consolidatedSubjectVO);
        return BusinessResponseEntity.ok((Object)"\u64cd\u4f5c\u6210\u529f.");
    }

    public BusinessResponseEntity<String> exchangeSort(@PathVariable(value="opnodeId") String opnodeId, @PathVariable(value="exnodeId") String exnodeId) {
        this.consolidatedSubjectUIService.exchangeSort(opnodeId, exnodeId);
        return BusinessResponseEntity.ok((Object)"\u64cd\u4f5c\u6210\u529f.");
    }

    public BusinessResponseEntity<String> exchangeSort2(@PathVariable(value="opnodeId") String opnodeId, @PathVariable(value="step") Integer step) {
        this.consolidatedSubjectUIService.exchangeSort(opnodeId, step);
        return BusinessResponseEntity.ok((Object)"\u64cd\u4f5c\u6210\u529f.");
    }

    public BusinessResponseEntity<String> exchangeSort2(@PathVariable(value="id") String id, @PathVariable(value="isOpen") Boolean isOpen) {
        this.consolidatedSubjectUIService.enableSubject(id, isOpen);
        return BusinessResponseEntity.ok((Object)"\u64cd\u4f5c\u6210\u529f.");
    }

    public BusinessResponseEntity<List<SubjectTreeVO>> treeBaseData(@PathVariable(value="systemId") String systemId) {
        List<SubjectTreeVO> basedatas = this.consolidatedSubjectService.treeBaseData(systemId);
        if (basedatas == null) {
            return BusinessResponseEntity.error((String)"\u6ca1\u6709\u7b26\u5408\u6761\u4ef6\u7684\u6570\u636e");
        }
        return BusinessResponseEntity.ok(basedatas);
    }

    public BusinessResponseEntity<String> refresh() {
        String tenantName = ShiroUtil.getTenantName();
        BaseDataDTO param = new BaseDataDTO();
        param.setTenantName(tenantName);
        param.setTableName("MD_GCSUBJECT");
        BaseDataSyncCacheDTO bdsc = new BaseDataSyncCacheDTO();
        bdsc.setTenantName(tenantName);
        bdsc.setBaseDataDTO(param);
        bdsc.setClean(true);
        this.baseDataCacheService.pushSyncMsg(bdsc);
        this.consolidatedSubjectUIService.clearCache();
        return BusinessResponseEntity.ok((Object)"");
    }

    public BusinessResponseEntity<Map<String, String>> getMultilingualNamesTitle() {
        return BusinessResponseEntity.ok(ConsolidatedSystemUtils.getMultilingualNamesTitle());
    }

    public BusinessResponseEntity<List<TreeNode>> getFieldDefinesByTable(@PathVariable(value="tableDefineKey") String tableDefineKey) {
        List<TreeNode> fieldDefineTree = this.consolidatedSubjectService.getFieldDefinesByTable(tableDefineKey);
        return BusinessResponseEntity.ok(fieldDefineTree);
    }

    public BusinessResponseEntity<String> matchEndZb(String systemId, String formSchemeKey, String searchContent, boolean forceUpdate, List<String> subjectIds) {
        this.consolidatedSubjectUIService.matchEndZb(systemId, formSchemeKey, searchContent, forceUpdate, subjectIds);
        return BusinessResponseEntity.ok((Object)"\u64cd\u4f5c\u6210\u529f");
    }

    public BusinessResponseEntity<Map<String, String>> getConAccSubjectName(@RequestBody Map<String, Object> params) {
        List conAccSubjectCodes = (List)params.get("conAccSubjectCodes");
        String systemId = (String)params.get("systemId");
        HashMap<String, String> consolidatedSubjects = new HashMap<String, String>();
        for (String code : conAccSubjectCodes) {
            String title = this.consolidatedSubjectService.getTitleByCode(systemId, code);
            consolidatedSubjects.put(code, title);
        }
        return BusinessResponseEntity.ok(consolidatedSubjects);
    }

    public List<ConsolidatedSubjectVO> listAllChildrenSubjects(String systemId, String code) {
        return this.consolidatedSubjectService.listAllChildrenSubjects(systemId, code).stream().map(SubjectConvertUtil::convertEO2VO).collect(Collectors.toList());
    }

    public List<ConsolidatedSubjectVO> listSubjectsBySystemIdNoSortOrder(String systemId) {
        return this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId).stream().map(SubjectConvertUtil::convertEO2VO).collect(Collectors.toList());
    }

    public List<String> listSubjectCodesBySystemId(String systemId, List<String> parentCodes) {
        return this.consolidatedSubjectUIService.listExistCodes(systemId, parentCodes);
    }

    public BusinessResponseEntity<Object> getBaseDataByParent(SubjectBaseDataParam baseDataParam) {
        List<GcBaseData> basedatas = this.subjectBaseDataService.lazyLoadDirectBasedataItemsByParentid(baseDataParam.getSystemId(), baseDataParam.getParentCode(), baseDataParam.getAuthType(), baseDataParam.isFilterStopNode());
        List nodes = basedatas.stream().map(v -> new ITree((INode)GcBaseDataCenterTool.getInstance().convertGcBaseDataVO(v))).collect(Collectors.toList());
        return BusinessResponseEntity.ok(nodes);
    }

    public BusinessResponseEntity<List<GcBaseDataVO>> getBaseDataBySearch(SubjectBaseDataParam baseDataParam) {
        List<GcBaseData> basedatas = this.subjectBaseDataService.queryBasedataItemsBySearch(baseDataParam.getSystemId(), baseDataParam.getSearchText(), baseDataParam.getAuthType(), baseDataParam.isFilterStopNode());
        return BusinessResponseEntity.ok((Object)GcBaseDataCenterTool.getInstance().convertListGcBaseDataVO(basedatas));
    }

    public BusinessResponseEntity<GcBaseDataVO> getBaseDataByCode(SubjectBaseDataParam baseDataParam) {
        GcBaseData baseData = this.subjectBaseDataService.queryBasedataByCode(baseDataParam.getSystemId(), baseDataParam.getCode(), baseDataParam.isFilterStopNode());
        return BusinessResponseEntity.ok((Object)GcBaseDataCenterTool.getInstance().convertGcBaseDataVO(baseData));
    }

    public BusinessResponseEntity<Object> listAllBaseDataByParent(SubjectBaseDataParam baseDataParam) {
        List<GcBaseData> basedatas = this.subjectBaseDataService.queryAllWithSelfItemsByParentid(baseDataParam.getSystemId(), baseDataParam.getParentCode(), baseDataParam.getAuthType(), baseDataParam.isFilterStopNode());
        List nodes = basedatas.stream().map(v -> new ITree((INode)GcBaseDataCenterTool.getInstance().convertGcBaseDataVO(v))).collect(Collectors.toList());
        return BusinessResponseEntity.ok(nodes);
    }

    public BusinessResponseEntity<List<SubjectITree<GcBaseDataVO>>> reloadSubjectTree(Map<String, Object> param) {
        return BusinessResponseEntity.ok(this.consolidatedSubjectUIService.listSubjectTree(param));
    }
}

