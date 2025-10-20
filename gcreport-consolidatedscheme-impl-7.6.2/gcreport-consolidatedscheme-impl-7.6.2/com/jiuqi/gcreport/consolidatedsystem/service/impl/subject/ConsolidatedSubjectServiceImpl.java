/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.consolidatedsystem.common.CSConst
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum
 *  com.jiuqi.gcreport.consolidatedsystem.common.SubjectTreeVO
 *  com.jiuqi.gcreport.consolidatedsystem.common.TreeNode
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryChildrenType
 *  com.jiuqi.va.domain.basedata.BaseDataOption$QueryDataStructure
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.consolidatedsystem.service.impl.subject;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.consolidatedsystem.cache.SubjectCache;
import com.jiuqi.gcreport.consolidatedsystem.common.CSConst;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectAttributeEnum;
import com.jiuqi.gcreport.consolidatedsystem.common.SubjectTreeVO;
import com.jiuqi.gcreport.consolidatedsystem.common.TreeNode;
import com.jiuqi.gcreport.consolidatedsystem.entity.subject.ConsolidatedSubjectEO;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.util.SubjectConvertUtil;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsolidatedSubjectServiceImpl
implements ConsolidatedSubjectService {
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private SubjectCache subjectCache;
    @Autowired
    private BaseDataClient baseDataClient;
    private Logger logger = LoggerFactory.getLogger(ConsolidatedSubjectServiceImpl.class);

    @Override
    public ConsolidatedSubjectEO getSubjectById(String subjectId) {
        return this.getSubjectBaseDataById(subjectId);
    }

    @Override
    public List<ConsolidatedSubjectEO> listAllChildrenSubjects(String systemId, String code) {
        if (StringUtils.isEmpty((String)code)) {
            return new ArrayList<ConsolidatedSubjectEO>();
        }
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName("MD_GCSUBJECT");
        param.put("systemid", (Object)systemId);
        param.setQueryChildrenType(BaseDataOption.QueryChildrenType.ALL_CHILDREN);
        param.setCode(code);
        param.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        PageVO pageVO = this.baseDataClient.list(param);
        if (pageVO.getTotal() == 0) {
            return new ArrayList<ConsolidatedSubjectEO>();
        }
        List subjects = pageVO.getRows();
        ArrayList<ConsolidatedSubjectEO> subjectEOs = new ArrayList<ConsolidatedSubjectEO>();
        for (BaseDataDO subject : subjects) {
            ConsolidatedSubjectEO eo = SubjectConvertUtil.convertBdoToGcSubjectEO(subject);
            subjectEOs.add(eo);
        }
        subjectEOs.sort(Comparator.comparing(ConsolidatedSubjectEO::getOrdinal));
        return subjectEOs;
    }

    @Override
    public ConsolidatedSubjectEO getSubjectByCode(String systemId, String code) {
        return this.subjectCache.getSubjectByCode(systemId, code);
    }

    @Override
    public String getTitleByCode(@NotNull String systemId, @NotNull String code) {
        ConsolidatedSubjectEO subjectEO = this.subjectCache.getSubjectByCode(systemId, code);
        return null == subjectEO ? "" : subjectEO.getTitle();
    }

    @Override
    public FieldDefine getFieldDefineBySubject(ConsolidatedSubjectEO subjectEO) {
        FieldDefine fieldDefine = null;
        try {
            String field = subjectEO.getBoundIndexPath();
            if (StringUtils.isEmpty((String)field)) {
                return null;
            }
            int leftBracket = field.indexOf(91);
            int rightBracket = field.indexOf(93);
            String tableCode = field.substring(0, leftBracket);
            String fieldCode = field.substring(leftBracket + 1, rightBracket);
            TableDefine tableDefine = this.iDataDefinitionRuntimeController.queryTableDefineByCode(tableCode);
            if (tableDefine == null) {
                return null;
            }
            return this.iDataDefinitionRuntimeController.queryFieldByCodeInTable(fieldCode, tableDefine.getKey());
        }
        catch (Exception e) {
            e.printStackTrace();
            return fieldDefine;
        }
    }

    @Override
    public FieldDefine getFieldDefineByCode(String systemId, String code) {
        ConsolidatedSubjectEO consolidatedSubjectEO = this.getSubjectByCode(systemId, code);
        FieldDefine fieldDefine = null;
        try {
            fieldDefine = this.getFieldDefineBySubject(consolidatedSubjectEO);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return fieldDefine;
    }

    @Override
    public boolean isLeafByCode(String systemId, String subjectCode) {
        ConsolidatedSubjectEO eo = this.getSubjectByCode(systemId, subjectCode);
        return eo.getLeafFlag();
    }

    @Override
    public List<ConsolidatedSubjectEO> listDirectChildrensByCode(@NotNull String systemId, String code) {
        ArrayList<ConsolidatedSubjectEO> children = new ArrayList<ConsolidatedSubjectEO>();
        List<ConsolidatedSubjectEO> allSubjects = this.subjectCache.listSubjectsBySystemId(systemId);
        for (ConsolidatedSubjectEO subject : allSubjects) {
            if (!code.equals(subject.getParentCode())) continue;
            children.add(subject);
        }
        children.sort(Comparator.comparing(ConsolidatedSubjectEO::getOrdinal));
        return children;
    }

    private List<SubjectTreeVO> listBaseData(String systemId) {
        List<ConsolidatedSubjectEO> allSubjects = this.listAllSubjectsBySystemId(systemId).stream().filter(subjectEO -> subjectEO.getConsolidationFlag() != null && subjectEO.getConsolidationFlag() != false).collect(Collectors.toList());
        List<SubjectTreeVO> baseDatas = this.convertListBaseDataVO(allSubjects);
        return baseDatas;
    }

    private List<SubjectTreeVO> convertListBaseDataVO(List<ConsolidatedSubjectEO> allSubjects) {
        ArrayList<SubjectTreeVO> baseDataVOs = new ArrayList<SubjectTreeVO>();
        allSubjects.forEach(subject -> {
            SubjectTreeVO baseDataVO = new SubjectTreeVO();
            baseDataVO.setId(subject.getId().toString());
            baseDataVO.setCode(subject.getCode());
            baseDataVO.setTitle(subject.getTitle());
            baseDataVO.setParentid(subject.getParentCode());
            baseDataVO.setOrdinal(String.valueOf(subject.getOrdinal()));
            baseDataVOs.add(baseDataVO);
        });
        return baseDataVOs;
    }

    @Override
    public List<ConsolidatedSubjectEO> listAllSubjectsBySystemId(String systemId) {
        return this.subjectCache.listSubjectsBySystemId(systemId);
    }

    @Override
    public List<ConsolidatedSubjectEO> listSubjectsBySystemIdWithSortOrder(String systemId) {
        List<ConsolidatedSubjectEO> eos = this.subjectCache.listSubjectsBySystemId(systemId);
        eos.sort(Comparator.comparing(ConsolidatedSubjectEO::getOrdinal));
        HashMap groupByParentCodeCodeMap = new HashMap();
        ArrayList<ConsolidatedSubjectEO> allSubjects = new ArrayList<ConsolidatedSubjectEO>();
        ArrayList<ConsolidatedSubjectEO> clsSubjects = new ArrayList<ConsolidatedSubjectEO>();
        for (ConsolidatedSubjectEO eo : eos) {
            ArrayList<ConsolidatedSubjectEO> children = groupByParentCodeCodeMap.get(eo.getParentCode()) == null ? new ArrayList<ConsolidatedSubjectEO>() : (List)groupByParentCodeCodeMap.get(eo.getParentCode());
            children.add(eo);
            groupByParentCodeCodeMap.put(eo.getParentCode(), children);
            if (!CSConst.SUBJECT_TOP_CODE.equals(eo.getParentCode())) continue;
            clsSubjects.add(eo);
        }
        Stack stack = new Stack();
        Collections.reverse(clsSubjects);
        clsSubjects.forEach(subject -> stack.push(subject));
        while (!stack.isEmpty()) {
            ConsolidatedSubjectEO pop = (ConsolidatedSubjectEO)stack.pop();
            allSubjects.add(pop);
            List childrenSubjects = (List)groupByParentCodeCodeMap.get(pop.getCode());
            if (childrenSubjects == null || childrenSubjects.isEmpty()) continue;
            Collections.reverse(childrenSubjects);
            childrenSubjects.forEach(subject -> stack.push(subject));
        }
        return allSubjects;
    }

    @Override
    public List<FieldDefine> listAllFieldDefines(String systemId) {
        ArrayList<FieldDefine> fieldDefinelist = new ArrayList<FieldDefine>();
        List<ConsolidatedSubjectEO> subjectEOList = this.listAllSubjectsBySystemId(systemId);
        for (ConsolidatedSubjectEO subjectEO : subjectEOList) {
            fieldDefinelist.add(this.getFieldDefineBySubject(subjectEO));
        }
        return fieldDefinelist;
    }

    @Override
    public List<TreeNode> getFieldDefinesByTable(String tableDefineKey) {
        String[] keys = new String[]{tableDefineKey};
        List fieldDefines = this.runtimeDataSchemeService.getDataFieldByTable(tableDefineKey);
        ArrayList<TreeNode> roots = new ArrayList<TreeNode>();
        if (!CollectionUtils.isEmpty((Collection)fieldDefines)) {
            for (DataField fieldDefine : fieldDefines) {
                TreeNode item = new TreeNode();
                item.setValue(fieldDefine.getKey());
                item.setTitle("[" + fieldDefine.getCode() + "] " + fieldDefine.getTitle());
                item.setData(fieldDefine.getCode());
                roots.add(item);
            }
        }
        return roots;
    }

    @Override
    public Set<String> listAllChildrenCodes(String code, String reportSystemId) {
        List<ConsolidatedSubjectEO> allSubjects = this.listAllChildrenSubjects(reportSystemId, code);
        return allSubjects.stream().map(ConsolidatedSubjectEO::getCode).collect(Collectors.toSet());
    }

    @Override
    public Set<String> listAllChildrenCodesContainsSelf(List<String> codes, String reportSystemId) {
        if (CollectionUtils.isEmpty(codes)) {
            return Collections.emptySet();
        }
        List<ConsolidatedSubjectEO> allSubjects = this.listAllSubjectsBySystemId(reportSystemId);
        if (CollectionUtils.isEmpty(allSubjects)) {
            return Collections.emptySet();
        }
        HashSet<String> codesSet = new HashSet<String>();
        for (String code : codes) {
            if (codesSet.contains(code)) continue;
            Set<String> childrenCodes = this.listAllChildrenCodes(code, reportSystemId);
            codesSet.addAll(childrenCodes);
        }
        codesSet.addAll(codes);
        return codesSet;
    }

    @Override
    public Set<String> listAllCodesByAttr(String systemId, SubjectAttributeEnum subjectAttr) {
        HashSet<String> attriSubjectResult = new HashSet<String>();
        List<ConsolidatedSubjectEO> subjectVOs = this.listAllSubjectsBySystemId(systemId);
        if (null == subjectVOs) {
            return attriSubjectResult;
        }
        for (ConsolidatedSubjectEO subject : subjectVOs) {
            if (null == subject.getAttri() || null == subject.getOrient() || subject.getAttri().intValue() != subjectAttr.getValue()) continue;
            attriSubjectResult.add(subject.getCode());
        }
        return attriSubjectResult;
    }

    private List<String> getChildrenAndSelfCodesByAllSubjects(String code, List<ConsolidatedSubjectEO> allsubjects) {
        return allsubjects.stream().filter(subject -> subject.getCode().indexOf(code) == 0 && subject.getCode().length() >= code.length()).map(ConsolidatedSubjectEO::getCode).collect(Collectors.toList());
    }

    @Override
    public Set<String> filterByExcludeChild(String systemId, Collection<String> codes) {
        List<ConsolidatedSubjectEO> subjectEOList = this.listAllSubjectsBySystemId(systemId);
        return this.filterByExcludeChild(subjectEOList, codes);
    }

    @Override
    public Set<String> filterByExcludeChild(List<ConsolidatedSubjectEO> subjectEOList, Collection<String> codes) {
        LinkedHashSet<String> baseDataCodes = new LinkedHashSet<String>(codes);
        if (CollectionUtils.isEmpty(codes)) {
            return baseDataCodes;
        }
        ArrayList removedCodes = new ArrayList();
        codes.stream().forEach(subjectCode -> {
            if (removedCodes.contains(subjectCode)) {
                return;
            }
            List<String> childrenAndSelfCodes = this.getChildrenAndSelfCodesByAllSubjects((String)subjectCode, subjectEOList);
            ArrayList thisTimeNeedRemovedCodes = new ArrayList();
            childrenAndSelfCodes.stream().forEach(code -> {
                if (!code.equals(subjectCode) && baseDataCodes.contains(code)) {
                    thisTimeNeedRemovedCodes.add(code);
                }
            });
            removedCodes.addAll(thisTimeNeedRemovedCodes);
            baseDataCodes.removeAll(thisTimeNeedRemovedCodes);
        });
        return baseDataCodes;
    }

    @Override
    public List<SubjectTreeVO> treeBaseData(String systemId) {
        List<SubjectTreeVO> list = this.listBaseData(systemId);
        Map<String, SubjectTreeVO> datas = list.stream().collect(Collectors.toMap(BaseDataVO::getCode, baseDate -> baseDate));
        ArrayList<SubjectTreeVO> tree = new ArrayList<SubjectTreeVO>();
        for (SubjectTreeVO node1 : list) {
            SubjectTreeVO pobj = datas.get(node1.getParentid());
            if (pobj != null) {
                pobj.getChildren().add(datas.get(node1.getCode()));
                continue;
            }
            tree.add(datas.get(node1.getCode()));
        }
        return tree;
    }

    private ConsolidatedSubjectEO getSubjectBaseDataById(String subjectId) {
        BaseDataDTO param = new BaseDataDTO();
        param.setTableName("MD_GCSUBJECT");
        param.setId(UUIDUtils.fromString36((String)subjectId));
        param.setQueryDataStructure(BaseDataOption.QueryDataStructure.ALL);
        PageVO pageVO = this.baseDataClient.list(param);
        if (pageVO.getTotal() == 0) {
            return null;
        }
        BaseDataDO baseDataDO = (BaseDataDO)pageVO.getRows().get(0);
        return SubjectConvertUtil.convertBdoToGcSubjectEO(baseDataDO);
    }
}

