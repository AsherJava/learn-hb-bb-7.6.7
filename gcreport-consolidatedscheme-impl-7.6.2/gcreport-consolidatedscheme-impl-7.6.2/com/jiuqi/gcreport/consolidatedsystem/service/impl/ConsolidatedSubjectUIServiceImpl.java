/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.subject.impl.subject.dto.SubjectDTO
 *  com.jiuqi.common.subject.impl.subject.enums.SubjectClassEnum
 *  com.jiuqi.common.subject.impl.subject.service.SubjectService
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.basedata.api.itree.ITree
 *  com.jiuqi.gcreport.basedata.impl.util.BaseDataObjConverter
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.common.CSConst
 *  com.jiuqi.gcreport.consolidatedsystem.common.ReturnObject
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectTreeNodeVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectTreeVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.GcConsolidatedSubjectVo
 *  com.jiuqi.gcreport.unionrule.vo.SubjectITree
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.jtable.params.output.FormTableFields
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.va.basedata.service.impl.help.BaseDataSyncService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  javax.annotation.Resource
 *  javax.validation.constraints.NotNull
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.consolidatedsystem.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.enums.SubjectClassEnum;
import com.jiuqi.common.subject.impl.subject.service.SubjectService;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.basedata.api.itree.ITree;
import com.jiuqi.gcreport.basedata.impl.util.BaseDataObjConverter;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.cache.SubjectCache;
import com.jiuqi.gcreport.consolidatedsystem.common.CSConst;
import com.jiuqi.gcreport.consolidatedsystem.common.ReturnObject;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum;
import com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ConsolidatedSubjectUIService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectValidator;
import com.jiuqi.gcreport.consolidatedsystem.util.SubjectConvertUtil;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectTreeNodeVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectTreeVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.GcConsolidatedSubjectVo;
import com.jiuqi.gcreport.unionrule.vo.SubjectITree;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.jtable.params.output.FormTableFields;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.va.basedata.service.impl.help.BaseDataSyncService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.basedata.handle.BaseDataBatchOptDTO;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsolidatedSubjectUIServiceImpl
implements ConsolidatedSubjectUIService {
    private static final Logger logger = LoggerFactory.getLogger(ConsolidatedSubjectUIServiceImpl.class);
    private static final String LOG_TEMPLATE = "\u4f53\u7cfb\uff1a\u3010%1$s\u3011\uff0c\u79d1\u76ee\uff1a\u3010%2$s\u3011";
    @Resource
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private ConsolidatedSystemDao consolidatedSystemDao;
    @Autowired
    private SubjectCache subjectCache;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private BaseDataSyncService baseDataSyncService;
    private List<ConsolidatedSubjectValidator> validatorList;
    private SubjectService subjectService;

    @Override
    public void clearCache() {
        this.subjectCache.clearCache();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public ConsolidatedSubjectVO saveSubject(ConsolidatedSubjectVO subject) {
        this.isValidStdcode(subject);
        ConsolidatedSubjectEO eo = this.convertVO2EO(subject);
        ArrayList<ConsolidatedSubjectEO> eos = new ArrayList<ConsolidatedSubjectEO>();
        eos.add(eo);
        ReturnObject ro = this.checkSubjects(eos);
        Assert.isTrue((boolean)ro.isSuccess(), (String)ro.getErrorMessage(), (Object[])new Object[0]);
        this.saveSubjectEO(eo);
        subject.setId(eo.getId());
        return subject;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<ConsolidatedSubjectVO> saveSubjects(List<ConsolidatedSubjectVO> subjects) {
        if (CollectionUtils.isEmpty(subjects)) {
            return null;
        }
        this.convertSubjectDtoToVo(subjects);
        ArrayList<ConsolidatedSubjectEO> allEos = new ArrayList<ConsolidatedSubjectEO>();
        subjects.forEach(vo -> {
            this.isValidStdcode((ConsolidatedSubjectVO)vo);
            ConsolidatedSubjectEO eo = this.convertVO2EO((ConsolidatedSubjectVO)vo);
            allEos.add(eo);
        });
        ReturnObject ro = this.checkSubjects(allEos);
        Assert.isTrue((boolean)ro.isSuccess(), (String)ro.getErrorMessage(), (Object[])new Object[0]);
        Map<String, List<ConsolidatedSubjectEO>> systemId2SubjectDatasMap = allEos.stream().filter(v -> v.getSystemId() != null).collect(Collectors.groupingBy(ConsolidatedSubjectEO::getSystemId));
        for (String systemId : systemId2SubjectDatasMap.keySet()) {
            List<ConsolidatedSubjectEO> eos = systemId2SubjectDatasMap.get(systemId);
            BaseDataBatchOptDTO saveDTO = this.getBaseDataBatchOptDTO(systemId);
            ArrayList<BaseDataDTO> baseDataDOs = new ArrayList<BaseDataDTO>();
            for (ConsolidatedSubjectEO eo : eos) {
                BaseDataDTO removeBaseDataDO = SubjectConvertUtil.convertGcSubjectEOToBaseDataDTO(eo);
                baseDataDOs.add(removeBaseDataDO);
            }
            saveDTO.setDataList(baseDataDOs);
            this.baseDataSyncService.sync(saveDTO);
        }
        block2: for (ConsolidatedSubjectEO eo : allEos) {
            for (ConsolidatedSubjectVO vo2 : subjects) {
                if (!eo.getCode().equals(vo2.getCode())) continue;
                vo2.setId(eo.getId());
                continue block2;
            }
        }
        return subjects;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void updateSubject(ConsolidatedSubjectVO consolidatedSubjectVO) {
        this.isValidStdcode(consolidatedSubjectVO);
        ConsolidatedSubjectEO eo = this.convertVO2EO(consolidatedSubjectVO);
        this.updateSubjectEO(eo);
    }

    @Override
    public ConsolidatedSubjectVO getSubjectById(@NotNull String subjectId) {
        ConsolidatedSubjectEO eo = this.consolidatedSubjectService.getSubjectById(subjectId);
        return eo == null ? null : SubjectConvertUtil.convertEO2VO(eo);
    }

    @Override
    public ReturnObject checkSubjects(List<ConsolidatedSubjectEO> addSubjects) {
        String systemId = addSubjects.get(0).getSystemId();
        List<ConsolidatedSubjectEO> originalSubjects = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId);
        ReturnObject ro = this.verifyZB(addSubjects, originalSubjects);
        if (!ro.isSuccess()) {
            return ro;
        }
        List addParentids = addSubjects.stream().map(asubject -> asubject.getParentCode()).filter(parentCode -> !StringUtils.isEmpty((String)parentCode)).collect(Collectors.toList());
        List addCodes = addSubjects.stream().map(asubject -> asubject.getCode()).filter(code -> !StringUtils.isEmpty((String)code)).collect(Collectors.toList());
        List originalCodes = originalSubjects.stream().map(osubject -> osubject.getCode()).collect(Collectors.toList());
        originalCodes.add(CSConst.SUBJECT_TOP_CODE);
        addParentids.removeAll(originalCodes);
        addParentids.removeAll(addCodes);
        if (addParentids != null && addParentids.size() > 0) {
            ro.setSuccess(false);
            ro.setErrorMessage("\u4e0a\u7ea7\u7f16\u7801\uff1a " + addParentids.toString() + " \u4e0d\u5b58\u5728");
            return ro;
        }
        Boolean globalAdd = addSubjects.get(0).getGlobalAdd();
        if (Boolean.TRUE.equals(globalAdd)) {
            Map<String, String> originalSubjectsCode2IdMap = originalSubjects.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getCode, ConsolidatedSubjectEO::getId));
            Iterator<ConsolidatedSubjectEO> iterator = addSubjects.iterator();
            while (iterator.hasNext()) {
                ConsolidatedSubjectEO addSubject = iterator.next();
                if (!originalSubjectsCode2IdMap.containsKey(addSubject.getCode())) continue;
                iterator.remove();
            }
            if (CollectionUtils.isEmpty(addSubjects)) {
                ro.setSuccess(false);
                ro.setErrorMessage("\u5f53\u524d\u6240\u9009\u79d1\u76ee\u5df2\u5b58\u5728");
            }
            return ro;
        }
        Map<String, String> originalSubjectsCodeMap = originalSubjects.stream().collect(Collectors.toMap(ConsolidatedSubjectEO::getId, ConsolidatedSubjectEO::getCode));
        addSubjects = addSubjects.stream().filter(item -> {
            if (item.getId() != null) {
                return !item.getCode().equals(originalSubjectsCodeMap.get(item.getId()));
            }
            return true;
        }).collect(Collectors.toList());
        ArrayList<ConsolidatedSubjectEO> newSubjects = new ArrayList<ConsolidatedSubjectEO>();
        newSubjects.addAll(addSubjects);
        newSubjects.addAll(originalSubjects);
        Map<String, Long> code2Length = newSubjects.stream().collect(Collectors.groupingBy(subject -> subject.getCode(), Collectors.counting()));
        for (Map.Entry<String, Long> entry : code2Length.entrySet()) {
            if (entry.getValue() == 1L) continue;
            ro.setSuccess(false);
            ro.setErrorMessage("\u68c0\u67e5\u5230\u79d1\u76ee\u7f16\u7801" + entry.getKey() + "\u5df2\u5b58\u5728");
            return ro;
        }
        return ro;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void exchangeSort(String opnodeId, String exnodeId) {
        ConsolidatedSubjectEO opeo = this.consolidatedSubjectService.getSubjectById(opnodeId);
        ConsolidatedSubjectEO exeo = this.consolidatedSubjectService.getSubjectById(exnodeId);
        BigDecimal tempsort = opeo.getOrdinal();
        opeo.setOrdinal(exeo.getOrdinal());
        exeo.setOrdinal(tempsort);
        this.updateSubjectEO(opeo);
        this.updateSubjectEO(exeo);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void exchangeSort(String opnodeId, Integer step) {
        Assert.isTrue((1 == step || -1 == step ? 1 : 0) != 0, (String)"\u9875\u9762\u53c2\u6570\u9519\u8bef.", (Object[])new Object[0]);
        ConsolidatedSubjectEO moveEO = this.consolidatedSubjectService.getSubjectById(opnodeId);
        ConsolidatedSubjectEO exeo = this.getExchengeSortSubject(moveEO, step);
        BigDecimal tempSort = moveEO.getOrdinal();
        moveEO.setOrdinal(exeo.getOrdinal());
        exeo.setOrdinal(tempSort);
        this.updateSubjectEO(moveEO);
        this.updateSubjectEO(exeo);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void enableSubject(String id, boolean isOpen) {
        ConsolidatedSubjectEO eo = this.consolidatedSubjectService.getSubjectById(id);
        List<ConsolidatedSubjectEO> allChildrenSubjects = this.consolidatedSubjectService.listAllChildrenSubjects(eo.getSystemId(), eo.getCode());
        allChildrenSubjects.forEach(subject -> subject.setConsolidationFlag(isOpen));
        eo.setConsolidationFlag(isOpen);
        allChildrenSubjects.add(eo);
        this.updateSubjectEOS(allChildrenSubjects);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteSubjects(String[] ids) {
        LinkedHashSet<String> delIds = new LinkedHashSet<String>();
        ArrayList<ConsolidatedSubjectEO> allDelSubject = new ArrayList<ConsolidatedSubjectEO>();
        String systemId = null;
        if (ids != null && ids.length > 0) {
            for (String id2 : ids) {
                if (delIds.contains(id2)) continue;
                ConsolidatedSubjectEO subjectEO = this.consolidatedSubjectService.getSubjectById(id2);
                systemId = subjectEO.getSystemId();
                allDelSubject.add(subjectEO);
                delIds.add(id2);
                List<ConsolidatedSubjectEO> children = this.consolidatedSubjectService.listAllChildrenSubjects(subjectEO.getSystemId(), subjectEO.getCode());
                allDelSubject.addAll(children);
                children.stream().forEach(eo -> delIds.add(eo.getId()));
            }
        }
        if (StringUtils.isEmpty(systemId)) {
            return;
        }
        this.checkSubjectReference(allDelSubject);
        if (delIds.size() > 0) {
            ArrayList<String> dellist = new ArrayList<String>(delIds);
            this.deleteSubjectsBySystemId(systemId, dellist);
            StringBuffer mLog = new StringBuffer();
            delIds.forEach(id -> mLog.append((String)id).append(","));
            logger.info("\u5220\u9664\u79d1\u76ee:" + mLog.toString());
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void matchEndZb(String systemId, String formSchemeKey, String searchContent, boolean forceUpdate, List<String> subjectIds) {
        searchContent = this.checkAndHandle(searchContent);
        Map<String, String> zbMap = this.getZbMap(formSchemeKey, searchContent);
        ArrayList<ConsolidatedSubjectEO> subjectEOS = new ArrayList(16);
        if (UUIDUtils.emptyUUIDStr().equals(subjectIds.get(0))) {
            subjectEOS = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId);
        } else {
            for (String subjectId : subjectIds) {
                subjectEOS.addAll(this.listAllChildrenSubjects(subjectId));
                if (!CollectionUtils.isEmpty(subjectEOS)) continue;
                subjectEOS.add(this.consolidatedSubjectService.getSubjectById(subjectIds.get(0)));
            }
        }
        if (!forceUpdate) {
            subjectEOS = subjectEOS.stream().filter(obj -> obj.getBoundIndexPath() == null).collect(Collectors.toList());
        }
        for (ConsolidatedSubjectEO subjectEO : subjectEOS) {
            String targetName = subjectEO.getTitle() + searchContent;
            if (!zbMap.containsKey(targetName)) continue;
            subjectEO.setBoundIndexPath(zbMap.get(targetName));
        }
        subjectEOS = subjectEOS.stream().filter(obj -> obj.getBoundIndexPath() != null).collect(Collectors.toList());
        this.updateSubjectEOS(subjectEOS);
        zbMap.clear();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteSubjectsBySystemId(@NotNull String systemId) {
        logger.info("\u5220\u9664\u5408\u5e76\u79d1\u76ee\u64cd\u4f5c:\u7528\u6237{}\uff0c\u65f6\u95f4{}\uff0c\u6570\u636e{}\uff0c\u5806\u6808\uff1a{}", this.getUserName(), DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss"), systemId, Thread.currentThread().getStackTrace());
        List<ConsolidatedSubjectEO> subjectEOS = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId);
        ArrayList<BaseDataDTO> removeBaseDataDOs = new ArrayList<BaseDataDTO>();
        for (ConsolidatedSubjectEO eo : subjectEOS) {
            BaseDataDTO removeBaseDataDO = new BaseDataDTO();
            removeBaseDataDO.setTableName("MD_GCSUBJECT");
            removeBaseDataDO.setId(UUIDUtils.fromString36((String)eo.getId()));
            removeBaseDataDO.put("systemid", (Object)systemId);
            removeBaseDataDOs.add(removeBaseDataDO);
        }
        BaseDataBatchOptDTO removeDTO = this.getBaseDataBatchOptDTO(systemId);
        removeDTO.setDataList(removeBaseDataDOs);
        removeDTO.addExtInfo("forceDelete", (Object)true);
        this.baseDataClient.batchRemove(removeDTO);
        String systemName = ((ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)systemId))).getSystemName();
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u79d1\u76ee", (String)("\u79d1\u76ee\u5220\u9664" + systemName + "\u5408\u5e76\u4f53\u7cfb"), (String)("\u6839\u636e\u4f53\u7cfbid\u5220\u9664\u5408\u5e76\u79d1\u76ee\u4f53\u7cfbid:" + systemId));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteSubjectsBySystemId(String systemId, List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        Collections.reverse(ids);
        logger.info("\u5220\u9664\u5408\u5e76\u79d1\u76ee\u64cd\u4f5c:\u7528\u6237{}\uff0c\u65f6\u95f4{}\uff0c\u6570\u636e{}\uff0c\u5806\u6808\uff1a{}", this.getUserName(), DateUtils.format((Date)new Date(), (String)"yyyy-MM-dd HH:mm:ss"), systemId, Thread.currentThread().getStackTrace());
        ArrayList<BaseDataDTO> removeBaseDataDOs = new ArrayList<BaseDataDTO>();
        for (String subjectId : ids) {
            BaseDataDTO removeBaseDataDO = new BaseDataDTO();
            removeBaseDataDO.setTableName("MD_GCSUBJECT");
            removeBaseDataDO.setId(UUIDUtils.fromString36((String)subjectId));
            removeBaseDataDO.put("systemid", (Object)systemId);
            removeBaseDataDOs.add(removeBaseDataDO);
        }
        BaseDataBatchOptDTO removeDTO = this.getBaseDataBatchOptDTO(systemId);
        removeDTO.setDataList(removeBaseDataDOs);
        removeDTO.addExtInfo("forceDelete", (Object)true);
        R res = this.baseDataClient.batchRemove(removeDTO);
        if (res.getCode() == 1) {
            throw new RuntimeException(res.getMsg());
        }
    }

    @Override
    public ConsolidatedSubjectTreeVO getSubjectTree(String systemId) {
        List<ConsolidatedSubjectEO> eos = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId);
        return this.getSubjectTree(eos);
    }

    @Override
    public List<ConsolidatedSubjectVO> listSubjectsBySearchKey(String systemId, String searchText) {
        List<ConsolidatedSubjectEO> eos = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId);
        ArrayList<ConsolidatedSubjectVO> vos = new ArrayList<ConsolidatedSubjectVO>();
        for (ConsolidatedSubjectEO eo : eos) {
            if (!eo.getCode().contains(searchText) && !eo.getTitle().contains(searchText)) continue;
            ConsolidatedSubjectVO vo = SubjectConvertUtil.convertEO2VO(eo);
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public List<SubjectITree<GcBaseDataVO>> listSubjectTree(Map<String, Object> param) {
        String reportSystemId = String.valueOf(param.get("reportSystem"));
        if (!param.containsKey("subjects") || CollectionUtils.isEmpty((Collection)((List)param.get("subjects")))) {
            return new ArrayList<SubjectITree<GcBaseDataVO>>();
        }
        List subjects = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)param.get("subjects")), (TypeReference)new TypeReference<List<GcBaseDataVO>>(){});
        List<BaseDataDO> allSubject = this.listSubjectsBaseData(reportSystemId);
        Set<String> onlyParentSubjectCodes = this.consolidatedSubjectService.filterByExcludeChild(reportSystemId, (Collection<String>)subjects.stream().sorted(Comparator.comparing(GcBaseDataVO::getOrdinal)).map(GcBaseDataVO::getCode).collect(Collectors.toList()));
        ArrayList<SubjectITree<GcBaseDataVO>> subjectTree = new ArrayList<SubjectITree<GcBaseDataVO>>();
        onlyParentSubjectCodes.forEach(code -> {
            List cur_subjects = allSubject.stream().filter(subject -> code.equals(subject.getCode())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(cur_subjects)) {
                BaseDataDO cur_subject = (BaseDataDO)cur_subjects.get(0);
                SubjectITree gcBaseDataNode = new SubjectITree(GcBaseDataCenterTool.getInstance().convertGcBaseDataVO(BaseDataObjConverter.convert((BaseDataDO)cur_subject)));
                this.findChildrenSubject((SubjectITree<GcBaseDataVO>)gcBaseDataNode, allSubject);
                subjectTree.add(gcBaseDataNode);
            }
        });
        return subjectTree;
    }

    @Override
    public PageInfo listSubjects(String systemId, String id, Boolean isAllChildren, Integer pageSize, Integer pageNum) {
        Integer count = 0;
        if (UUIDUtils.emptyUUIDStr().equals(id)) {
            List<ConsolidatedSubjectEO> allSubjects;
            List<ConsolidatedSubjectEO> clsEOS = null;
            if (!isAllChildren.booleanValue()) {
                allSubjects = this.listClsSubjects(systemId);
                count = allSubjects.size();
                clsEOS = this.getSubListByPaging(allSubjects, pageNum, pageSize);
            } else {
                allSubjects = this.consolidatedSubjectService.listSubjectsBySystemIdWithSortOrder(systemId);
                count = allSubjects.size();
                clsEOS = this.getSubListByPaging(allSubjects, pageNum, pageSize);
            }
            List vos = clsEOS.stream().map(SubjectConvertUtil::convertEO2VO).collect(Collectors.toList());
            return PageInfo.of(vos, (int)count);
        }
        List<ConsolidatedSubjectEO> eos = null;
        if (isAllChildren.booleanValue()) {
            List<ConsolidatedSubjectEO> allSubjects = this.listAllChildrenSubjects(id);
            eos = this.getSubListByPaging(allSubjects, pageNum, pageSize);
            count = allSubjects.size();
        } else {
            ConsolidatedSubjectEO eo = this.consolidatedSubjectService.getSubjectById(id);
            List<ConsolidatedSubjectEO> allSubjects = this.consolidatedSubjectService.listDirectChildrensByCode(systemId, eo.getCode());
            count = allSubjects.size();
            eos = this.getSubListByPaging(allSubjects, pageNum, pageSize);
        }
        ConsolidatedSubjectEO currentEO = this.consolidatedSubjectService.getSubjectById(id);
        if (eos.isEmpty()) {
            eos = new ArrayList<ConsolidatedSubjectEO>();
            eos.add(currentEO);
        } else if (pageNum == 0) {
            eos.add(0, currentEO);
        }
        List vos = eos.stream().map(SubjectConvertUtil::convertEO2VO).collect(Collectors.toList());
        return PageInfo.of(vos, (int)count);
    }

    @Override
    public List<String> listExistCodes(String systemId, List<String> codes) {
        List<ConsolidatedSubjectEO> allSubjects = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId);
        if (CollectionUtils.isEmpty(allSubjects)) {
            return new ArrayList<String>();
        }
        return allSubjects.stream().map(ConsolidatedSubjectEO::getCode).filter(codes::contains).collect(Collectors.toList());
    }

    @Override
    public List<SelectOptionVO> listAllSubjectsWithOption(String systemId) {
        List<ConsolidatedSubjectEO> eos = this.consolidatedSubjectService.listAllSubjectsBySystemId(systemId);
        ArrayList<SelectOptionVO> optionVOS = new ArrayList<SelectOptionVO>();
        eos.forEach(eo -> {
            SelectOptionVO vo = new SelectOptionVO();
            vo.setValue((Object)eo.getCode());
            vo.setLabel(eo.getTitle());
            optionVOS.add(vo);
        });
        return optionVOS;
    }

    private void isValidStdcode(ConsolidatedSubjectVO vo) {
        Assert.isFalse((boolean)StringUtils.isEmpty((String)vo.getCode()), (String)"\u79d1\u76ee\u7f16\u7801\u4e0d\u80fd\u4e3a\u7a7a.", (Object[])new Object[0]);
        Assert.isFalse((boolean)StringUtils.isEmpty((String)vo.getTitle()), (String)"\u79d1\u76ee\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a.", (Object[])new Object[0]);
        Assert.isTrue((boolean)vo.getCode().matches("^[a-zA-Z_0-9-]+$"), (String)("\u79d1\u76ee\u7f16\u7801\uff1a" + vo.getCode() + " \u4e0d\u7b26\u5408\u89c4\u8303."), (Object[])new Object[0]);
    }

    public Collection<ConsolidatedSubjectValidator> getValidatorList() {
        if (this.validatorList == null) {
            Collection beans = SpringContextUtils.getBeans(ConsolidatedSubjectValidator.class);
            this.validatorList = beans == null ? new ArrayList<ConsolidatedSubjectValidator>() : new ArrayList(beans);
        }
        return this.validatorList;
    }

    public SubjectService getSubjectService() {
        if (this.subjectService == null) {
            this.subjectService = (SubjectService)SpringContextUtils.getBean(SubjectService.class);
        }
        return this.subjectService;
    }

    private void saveSubjectEO(ConsolidatedSubjectEO subject) {
        ConsolidatedSubjectEO eo;
        String systemName = ((ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)subject.getSystemId()))).getSystemName();
        BaseDataDTO dto = SubjectConvertUtil.convertGcSubjectEOToBaseDataDTO(subject);
        BaseDataBatchOptDTO batchOptDTO = new BaseDataBatchOptDTO();
        R res = subject.getId() == null ? this.baseDataClient.add(dto) : ((eo = this.consolidatedSubjectService.getSubjectById(subject.getId())) == null ? this.baseDataClient.add(dto) : this.baseDataClient.update(dto));
        if (res.getCode() == 1) {
            throw new RuntimeException(res.getMsg());
        }
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u79d1\u76ee", (String)("\u79d1\u76ee" + subject.getId() == null ? "\u65b0\u589e" : "\u4fee\u6539-" + systemName + "\u5408\u5e76\u4f53\u7cfb"), (String)this.getSingleSubjectUpdateInfoString(subject));
    }

    private void updateSubjectEO(ConsolidatedSubjectEO subject) {
        BaseDataDTO dto = SubjectConvertUtil.convertGcSubjectEOToBaseDataDTO(subject);
        R res = this.baseDataClient.update(dto);
        if (res.getCode() == 1) {
            throw new RuntimeException(res.getMsg());
        }
        String systemName = ((ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)subject.getSystemId()))).getSystemName();
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u79d1\u76ee", (String)("\u79d1\u76ee\u4fee\u6539-" + systemName + "\u5408\u5e76\u4f53\u7cfb"), (String)this.getSingleSubjectUpdateInfoString(subject));
    }

    private void updateSubjectEOS(List<ConsolidatedSubjectEO> subjects) {
        if (CollectionUtils.isEmpty(subjects)) {
            return;
        }
        Map<String, List<ConsolidatedSubjectEO>> systemId2SubjectDatasMap = subjects.stream().filter(v -> v.getSystemId() != null).collect(Collectors.groupingBy(ConsolidatedSubjectEO::getSystemId));
        systemId2SubjectDatasMap.forEach((systemId, subjectEOs) -> {
            String systemName = ((ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)((ConsolidatedSubjectEO)subjects.get(0)).getSystemId()))).getSystemName();
            List subjectBdos = subjectEOs.stream().map(SubjectConvertUtil::convertGcSubjectEOToBaseDataDO).collect(Collectors.toList());
            BaseDataBatchOptDTO batchOptDTO = this.getBaseDataBatchOptDTO((String)systemId);
            batchOptDTO.setDataList(subjectBdos);
            R res = this.baseDataClient.batchUpdate(batchOptDTO);
            if (res.getCode() == 1) {
                throw new RuntimeException(res.getMsg());
            }
            LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u79d1\u76ee", (String)("\u79d1\u76ee\u6279\u91cf\u4fee\u6539-" + systemName + "\u5408\u5e76\u4f53\u7cfb"), (String)this.getMultiSubjectUpdateInfoString(subjects));
        });
    }

    private void checkSubjectReference(List<ConsolidatedSubjectEO> allDelSubject) {
        for (ConsolidatedSubjectValidator validator : this.getValidatorList()) {
            ConsolidatedSubjectValidator.ValidatorResult validatorResult = validator.deleteValidator(allDelSubject);
            if (validatorResult.isSuccess()) continue;
            throw new BusinessRuntimeException("\u5220\u9664\u6821\u9a8c\u5931\u8d25\uff1a" + validatorResult.getMessage());
        }
    }

    private BaseDataBatchOptDTO getBaseDataBatchOptDTO(String systemId) {
        BaseDataBatchOptDTO baseDataBatchOptDTO = new BaseDataBatchOptDTO();
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName("MD_GCSUBJECT");
        param.put("systemid", (Object)systemId);
        baseDataBatchOptDTO.setQueryParam(param);
        return baseDataBatchOptDTO;
    }

    private String getMultiSubjectUpdateInfoString(List<ConsolidatedSubjectEO> subjectList) {
        if (CollectionUtils.isEmpty(subjectList)) {
            return "";
        }
        String systemName = "";
        String systemId = subjectList.get(0).getSystemId();
        if (!StringUtils.isEmpty((String)systemId)) {
            ConsolidatedSystemEO consolidatedSystemEO = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)systemId));
            systemName = consolidatedSystemEO.getSystemName();
        }
        StringBuffer subjectCodeAndTitleString = new StringBuffer();
        for (ConsolidatedSubjectEO subject : subjectList) {
            subjectCodeAndTitleString.append(subject.getCode()).append("|").append(subject.getTitle()).append(",");
        }
        subjectCodeAndTitleString.deleteCharAt(subjectCodeAndTitleString.length() - 1);
        return String.format(LOG_TEMPLATE, systemName, subjectCodeAndTitleString);
    }

    private String getSingleSubjectUpdateInfoString(ConsolidatedSubjectEO subject) {
        String systemName = "";
        String systemId = subject.getSystemId();
        if (!StringUtils.isEmpty((String)systemId)) {
            ConsolidatedSystemEO consolidatedSystemEO = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)systemId));
            systemName = consolidatedSystemEO.getSystemName();
        }
        return String.format(LOG_TEMPLATE, systemName, subject.getCode() + "|" + subject.getTitle());
    }

    private ConsolidatedSubjectEO convertVO2EO(ConsolidatedSubjectVO vo) {
        ConsolidatedSubjectEO eo = new ConsolidatedSubjectEO();
        this.setItemParent(vo);
        if (vo.getId() == null) {
            vo.setOrdinal(new BigDecimal(OrderGenerator.newOrderID()));
        }
        BeanUtils.copyProperties(vo, eo);
        return eo;
    }

    private ConsolidatedSubjectEO getParentSubject(ConsolidatedSubjectVO vo) {
        String parentCode = vo.getParentCode();
        if (parentCode != null) {
            return this.consolidatedSubjectService.getSubjectByCode(vo.getSystemId(), parentCode);
        }
        return null;
    }

    private void setItemParent(ConsolidatedSubjectVO vo) {
        Assert.isTrue((!vo.getCode().equals(vo.getParentCode()) ? 1 : 0) != 0, (String)("\u79d1\u76ee\u4e0a\u7ea7\u7f16\u7801\u4e0e\u7f16\u7801\u76f8\u540c. \u9519\u8bef\u79d1\u76ee\uff1a" + vo.getCode()), (Object[])new Object[0]);
        ConsolidatedSubjectEO parentEO = this.getParentSubject(vo);
        if (parentEO != null) {
            vo.setParentCode(parentEO.getCode());
            vo.setCreatetime(new Date());
        }
    }

    private ReturnObject verifyZB(List<ConsolidatedSubjectEO> addSubjects, List<ConsolidatedSubjectEO> originalSubjects) {
        ReturnObject ro = new ReturnObject(true, null);
        StringBuffer errorString = new StringBuffer();
        HashSet<String> addSubjectIdSet = new HashSet<String>();
        HashMap zbSubjectNameMap = new HashMap();
        for (ConsolidatedSubjectEO eo : addSubjects) {
            if (eo.getId() != null) {
                addSubjectIdSet.add(eo.getId());
            }
            if (StringUtils.isEmpty((String)eo.getBoundIndexPath())) continue;
            if (zbSubjectNameMap.containsKey(eo.getBoundIndexPath())) {
                ((List)zbSubjectNameMap.get(eo.getBoundIndexPath())).add(eo.getTitle());
                continue;
            }
            ArrayList<String> subjectNameList = new ArrayList<String>();
            subjectNameList.add(eo.getTitle());
            zbSubjectNameMap.put(eo.getBoundIndexPath(), subjectNameList);
        }
        if (zbSubjectNameMap.isEmpty()) {
            return ro;
        }
        for (String zb : zbSubjectNameMap.keySet()) {
            if (((List)zbSubjectNameMap.get(zb)).size() <= 1) continue;
            errorString.append("\u672c\u6b21\u4fee\u6539\u7684\u79d1\u76ee[");
            for (String subjectName : (List)zbSubjectNameMap.get(zb)) {
                errorString.append(subjectName).append(",");
            }
            errorString.deleteCharAt(errorString.length() - 1);
            errorString.append("]\u5173\u8054\u4e86\u76f8\u540c\u7684\u6307\u6807[").append(zb).append("]<br>");
        }
        for (ConsolidatedSubjectEO eo : originalSubjects) {
            if (StringUtils.isEmpty((String)eo.getBoundIndexPath()) || addSubjectIdSet.contains(eo.getId()) || !zbSubjectNameMap.containsKey(eo.getBoundIndexPath())) continue;
            errorString.append("[");
            for (String subjectName : (List)zbSubjectNameMap.get(eo.getBoundIndexPath())) {
                errorString.append(subjectName).append(",");
            }
            errorString.deleteCharAt(errorString.length() - 1);
            errorString.append("]\u79d1\u76ee\u5173\u8054\u7684\u6307\u6807[").append(eo.getBoundIndexPath()).append("]\u5df2\u5173\u8054\u4e86[").append(eo.getTitle()).append("]\u79d1\u76ee\uff0c\u8bf7\u91cd\u65b0\u5173\u8054\u3002<br>");
        }
        if (errorString.length() > 0) {
            ro.setSuccess(false);
            ro.setErrorMessage(errorString.toString());
        }
        return ro;
    }

    private void convertSubjectDtoToVo(List<ConsolidatedSubjectVO> consolidatedSubjectVOS) {
        if (CollectionUtils.isEmpty(consolidatedSubjectVOS) || !Boolean.TRUE.equals(consolidatedSubjectVOS.get(0).getGlobalAdd())) {
            return;
        }
        for (ConsolidatedSubjectVO subjectVO : consolidatedSubjectVOS) {
            SubjectDTO subjectDTO = this.getSubjectService().findByCode(subjectVO.getCode());
            if (subjectDTO == null) continue;
            subjectVO.setAsstype(subjectDTO.getAssType());
            subjectVO.setGlobalAdd(Boolean.valueOf(true));
            String generalType = subjectDTO.getGeneralType();
            subjectVO.setAttri(this.getSubjectAttri(generalType));
            subjectVO.setOrient(subjectDTO.getOrient());
            subjectVO.setAsstype(subjectDTO.getAssType());
            subjectVO.setOrdinal(subjectDTO.getOrdinal());
        }
    }

    private Integer getSubjectAttri(String generalType) {
        Integer attri = SubjectAttributeEnum.OTHER.getValue();
        if (SubjectClassEnum.ASSET.getCode().equals(generalType)) {
            attri = SubjectAttributeEnum.ASSET.getValue();
        } else if (SubjectClassEnum.COST.getCode().equals(generalType)) {
            attri = SubjectAttributeEnum.PROFITLOSS.getValue();
        } else if (SubjectClassEnum.EQUITY.getCode().equals(generalType)) {
            attri = SubjectAttributeEnum.RIGHT.getValue();
        } else if (SubjectClassEnum.GAIN_LOSS.getCode().equals(generalType)) {
            attri = SubjectAttributeEnum.PROFITLOSS.getValue();
        } else if (SubjectClassEnum.LIABILITY.getCode().equals(generalType)) {
            attri = SubjectAttributeEnum.DEBT.getValue();
        } else if (SubjectClassEnum.CASH.getCode().equals(generalType)) {
            attri = SubjectAttributeEnum.CASH.getValue();
        }
        return attri;
    }

    private ConsolidatedSubjectEO getExchengeSortSubject(ConsolidatedSubjectEO moveEO, Integer step) {
        List<Object> simlevelsubjects = new ArrayList();
        ConsolidatedSubjectEO parentSubject = this.getParentSubject(moveEO);
        simlevelsubjects = parentSubject == null ? this.listClsSubjects(moveEO.getSystemId()) : this.listDirectChildrenSubjects(parentSubject.getId());
        Assert.isTrue((simlevelsubjects.size() > 0 ? 1 : 0) != 0, (String)"\u79fb\u52a8\u65f6\u53d1\u751f\u5185\u90e8\u9519\u8bef.", (Object[])new Object[0]);
        int index = -1;
        for (int i = 0; i < simlevelsubjects.size(); ++i) {
            if (!((ConsolidatedSubjectEO)simlevelsubjects.get(i)).getId().equals(moveEO.getId())) continue;
            index = i;
            break;
        }
        Assert.isTrue((index != -1 ? 1 : 0) != 0, (String)"\u79fb\u52a8\u65f6\u53d1\u751f\u5185\u90e8\u9519\u8bef.", (Object[])new Object[0]);
        Assert.isFalse((-1 == step && 0 == index ? 1 : 0) != 0, (String)"\u5df2\u79fb\u81f3\u672c\u7ea7\u79d1\u76ee\u9996\u4f4d.", (Object[])new Object[0]);
        Assert.isFalse((1 == step && simlevelsubjects.size() - 1 == index ? 1 : 0) != 0, (String)"\u5df2\u79fb\u81f3\u672c\u7ea7\u79d1\u76ee\u672b\u4f4d.", (Object[])new Object[0]);
        if (-1 == step) {
            return (ConsolidatedSubjectEO)simlevelsubjects.get(index - 1);
        }
        return (ConsolidatedSubjectEO)simlevelsubjects.get(index + 1);
    }

    private ConsolidatedSubjectEO getParentSubject(ConsolidatedSubjectEO eo) {
        String parentCode = eo.getParentCode();
        if (parentCode != null) {
            return this.consolidatedSubjectService.getSubjectByCode(eo.getSystemId(), parentCode);
        }
        return null;
    }

    private List<ConsolidatedSubjectEO> listClsSubjects(String systemId) {
        List<ConsolidatedSubjectEO> clsSubjects = this.consolidatedSubjectService.listSubjectsBySystemIdWithSortOrder(systemId).stream().filter(subject -> CSConst.SUBJECT_TOP_CODE.equals(subject.getParentCode())).collect(Collectors.toList());
        clsSubjects.sort(Comparator.comparing(ConsolidatedSubjectEO::getOrdinal));
        return clsSubjects;
    }

    private List<ConsolidatedSubjectEO> listDirectChildrenSubjects(String id) {
        ConsolidatedSubjectVO vo = this.getSubjectById(id);
        List<ConsolidatedSubjectEO> childrenSubjects = this.consolidatedSubjectService.listDirectChildrensByCode(vo.getSystemId(), vo.getCode());
        return childrenSubjects;
    }

    private List<ConsolidatedSubjectEO> getSubListByPaging(List<ConsolidatedSubjectEO> subjects, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageSize == null) {
            return subjects;
        }
        int start = pageNum * pageSize;
        int end = (pageNum + 1) * pageSize;
        int size = subjects.size();
        if (start > size) {
            return new ArrayList<ConsolidatedSubjectEO>();
        }
        end = end < size ? end : size;
        return subjects.subList(start, end);
    }

    private String getUserName() {
        return NpContextHolder.getContext().getUser() == null ? "" : NpContextHolder.getContext().getUser().getName();
    }

    private String checkAndHandle(String searchContent) {
        if (StringUtils.isEmpty((String)searchContent)) {
            throw new BusinessRuntimeException("\u8bf7\u586b\u5199\u5339\u914d\u89c4\u5219\uff0c\u793a\u4f8b\u4e2d\u89c4\u5219\u53ef\u590d\u5236\uff01");
        }
        if (searchContent.length() < 8) {
            throw new BusinessRuntimeException("\u8bf7\u586b\u5199\u6b63\u786e\u7684\u5339\u914d\u89c4\u5219\uff0c\u793a\u4f8b\u4e2d\u89c4\u5219\u53ef\u590d\u5236\uff01");
        }
        if (!(searchContent.startsWith("{\u79d1\u76ee\u540d\u79f0}_") || searchContent.startsWith("{\u79d1\u76ee\u540d\u79f0} ") || searchContent.startsWith("{\u79d1\u76ee\u540d\u79f0}."))) {
            throw new BusinessRuntimeException("\u8bf7\u6309\u7167\u793a\u4f8b\u6765\u586b\u5199\u89c4\u5219\uff0c\u793a\u4f8b\u53ef\u590d\u5236\uff01");
        }
        return searchContent.substring(6);
    }

    private Map<String, String> getZbMap(String formSchemeKey, String searchContent) {
        HashMap<String, String> zbMap = new HashMap<String, String>(16);
        ArrayList<FormTableFields> tables = new ArrayList<FormTableFields>();
        try {
            List formDefines = this.runTimeAuthViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
            if (!CollectionUtils.isEmpty((Collection)formDefines)) {
                for (FormDefine form : formDefines) {
                    FormTableFields formFieldData = this.jtableParamService.getForm(form.getKey(), searchContent);
                    tables.add(formFieldData);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        tables.forEach(tableItem -> tableItem.getFields().forEach(fieldData -> {
            if (!zbMap.containsKey(fieldData.getFieldTitle())) {
                zbMap.put(fieldData.getFieldTitle(), fieldData.getTableName() + "[" + fieldData.getFieldCode() + "]");
            }
        }));
        return zbMap;
    }

    private List<ConsolidatedSubjectEO> listAllChildrenSubjects(String id) {
        ConsolidatedSubjectEO parentEO = this.consolidatedSubjectService.getSubjectById(id);
        if (parentEO == null) {
            return new ArrayList<ConsolidatedSubjectEO>();
        }
        return this.consolidatedSubjectService.listAllChildrenSubjects(parentEO.getSystemId(), parentEO.getCode());
    }

    private ConsolidatedSubjectTreeVO getSubjectTree(List<ConsolidatedSubjectEO> eos) {
        eos.sort(Comparator.comparing(ConsolidatedSubjectEO::getOrdinal));
        ArrayList<ConsolidatedSubjectTreeNodeVO> subjectnodes = new ArrayList<ConsolidatedSubjectTreeNodeVO>();
        ArrayList<GcConsolidatedSubjectVo> vos = new ArrayList<GcConsolidatedSubjectVo>();
        for (ConsolidatedSubjectEO consolidatedSubjectEO : eos) {
            GcConsolidatedSubjectVo vo2 = new GcConsolidatedSubjectVo();
            BeanUtils.copyProperties(consolidatedSubjectEO, vo2);
            vos.add(vo2);
        }
        Map<String, ConsolidatedSubjectTreeNodeVO> id2treeVO = vos.stream().collect(Collectors.toMap(GcConsolidatedSubjectVo::getCode, vo -> new ConsolidatedSubjectTreeNodeVO(vo)));
        for (ConsolidatedSubjectEO eo : eos) {
            ConsolidatedSubjectEO parent = this.getParentSubject(eo);
            ConsolidatedSubjectTreeNodeVO nodeVO = id2treeVO.get(eo.getCode());
            if (parent == null) {
                subjectnodes.add(nodeVO);
                continue;
            }
            ConsolidatedSubjectTreeNodeVO treeVO = id2treeVO.get(this.getParentSubject(eo).getCode());
            treeVO.getChildren().add(nodeVO);
        }
        ConsolidatedSubjectTreeNodeVO consolidatedSubjectTreeNodeVO = new ConsolidatedSubjectTreeNodeVO();
        consolidatedSubjectTreeNodeVO.setId(UUIDUtils.emptyUUIDStr());
        consolidatedSubjectTreeNodeVO.setCode(CSConst.SUBJECT_TOP_CODE);
        consolidatedSubjectTreeNodeVO.setTitle(CSConst.SUBJECT_TOP_TITLE);
        consolidatedSubjectTreeNodeVO.setDataType("root");
        consolidatedSubjectTreeNodeVO.setExpand(Boolean.valueOf(true));
        consolidatedSubjectTreeNodeVO.setChildren(subjectnodes);
        LinkedList queue = new LinkedList();
        subjectnodes.forEach(subject -> queue.offer(subject));
        while (!queue.isEmpty()) {
            ConsolidatedSubjectTreeNodeVO poll = (ConsolidatedSubjectTreeNodeVO)queue.poll();
            List children = poll.getChildren();
            if (children == null || children.isEmpty()) {
                poll.setDataType("leaf");
                continue;
            }
            poll.setDataType("folder");
            children.forEach(subject -> queue.offer(subject));
        }
        ConsolidatedSubjectTreeVO resultTree = new ConsolidatedSubjectTreeVO(consolidatedSubjectTreeNodeVO);
        return resultTree;
    }

    private List<BaseDataDO> listSubjectsBaseData(String systemId) {
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName("MD_GCSUBJECT");
        param.put("systemid", (Object)systemId);
        param.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        PageVO pageVO = this.baseDataClient.list(param);
        if (pageVO.getTotal() == 0) {
            return Collections.emptyList();
        }
        List subjects = pageVO.getRows();
        return subjects;
    }

    private void findChildrenSubject(SubjectITree<GcBaseDataVO> parent, List<BaseDataDO> allSubject) {
        List<BaseDataDO> childSubjects = allSubject.stream().filter(subject -> subject.getParentcode().equals(parent.getCode())).sorted(Comparator.comparing(BaseDataDO::getOrdinal)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(childSubjects)) {
            childSubjects.forEach(child -> {
                SubjectITree childNode = new SubjectITree(GcBaseDataCenterTool.getInstance().convertGcBaseDataVO(BaseDataObjConverter.convert((BaseDataDO)child)));
                this.findChildrenSubject((SubjectITree<GcBaseDataVO>)childNode, allSubject);
                parent.appendChild((ITree)childNode);
            });
            parent.setLeaf(false);
        } else {
            parent.setLeaf(true);
        }
    }
}

