/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.dto.Pagination
 *  com.jiuqi.gcreport.common.exportprocess.service.GcExportProcessService
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.journalsingle.condition.JournalSubjectTreeCondition
 *  com.jiuqi.gcreport.journalsingle.enums.AdjustTypeEnum
 *  com.jiuqi.gcreport.journalsingle.enums.TreeNodeDataTypeEnum
 *  com.jiuqi.gcreport.journalsingle.vo.JournalSubjectTreeVO
 *  com.jiuqi.gcreport.journalsingle.vo.JournalSubjectVO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.journalsingle.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.dto.Pagination;
import com.jiuqi.gcreport.common.exportprocess.service.GcExportProcessService;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.journalsingle.condition.JournalSubjectTreeCondition;
import com.jiuqi.gcreport.journalsingle.dao.IJournalSubjectDao;
import com.jiuqi.gcreport.journalsingle.entity.JournalSingleSchemeExcelModel;
import com.jiuqi.gcreport.journalsingle.entity.JournalSubjectEO;
import com.jiuqi.gcreport.journalsingle.enums.AdjustTypeEnum;
import com.jiuqi.gcreport.journalsingle.enums.TreeNodeDataTypeEnum;
import com.jiuqi.gcreport.journalsingle.service.IJournalSingleSchemeService;
import com.jiuqi.gcreport.journalsingle.service.IJournalSingleSubjectService;
import com.jiuqi.gcreport.journalsingle.utils.GcJournalSubjectImportUtil;
import com.jiuqi.gcreport.journalsingle.utils.GcNpUtil;
import com.jiuqi.gcreport.journalsingle.vo.JournalSubjectTreeVO;
import com.jiuqi.gcreport.journalsingle.vo.JournalSubjectVO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class JournalSingleSubjectServiceImpl
implements IJournalSingleSubjectService {
    @Autowired
    private IJournalSubjectDao journalSubjectDao;
    @Autowired
    private IJournalSingleSchemeService schemeService;
    @Autowired
    private GcExportProcessService gcExportProcessService;

    @Override
    public void insertSubject(JournalSubjectVO journalSubjectVO) {
        Assert.isNotEmpty((String)journalSubjectVO.getCode(), (String)"\u9879\u76ee\u7f16\u7801\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Set<Integer> subjectCodeStructureSet = GcJournalSubjectImportUtil.getSubjectCodeStructureSet();
        if (!subjectCodeStructureSet.contains(journalSubjectVO.getCode().length())) {
            throw new BusinessRuntimeException("\u9879\u76ee\u7f16\u7801\u4e0d\u6ee1\u8db3\u6b64\u7ed3\u6784\uff1a'" + GcJournalSubjectImportUtil.getSubjectCodeStructure() + "'<br>");
        }
        JournalSubjectEO oldSubjectEO = this.journalSubjectDao.getSubjectEOByCode(journalSubjectVO.getjRelateSchemeId(), journalSubjectVO.getCode());
        Assert.isNull((Object)((Object)oldSubjectEO), (String)"\u9879\u76ee\u7f16\u7801\u91cd\u590d", (Object[])new Object[0]);
        JournalSubjectEO journalSubjectEO = new JournalSubjectEO();
        BeanUtils.copyProperties(journalSubjectVO, (Object)journalSubjectEO);
        journalSubjectEO.setNeedShow(journalSubjectVO.getNeedShow());
        this.repairZbInfo(journalSubjectEO);
        journalSubjectEO.setCreateTime(new Date());
        journalSubjectEO.setSortOrder(OrderGenerator.newOrder());
        this.journalSubjectDao.insertSubject(journalSubjectEO);
    }

    private void updateSubject(JournalSubjectVO subjectVO) {
        JournalSubjectEO journalSubjectEO = new JournalSubjectEO();
        BeanUtils.copyProperties(subjectVO, (Object)journalSubjectEO);
        journalSubjectEO.setNeedShow(subjectVO.getNeedShow());
        this.repairZbInfo(journalSubjectEO);
        journalSubjectEO.setModifyTime(new Date());
        journalSubjectEO.setParents(this.journalSubjectDao.generateNewParents(journalSubjectEO));
        this.journalSubjectDao.update((BaseEntity)journalSubjectEO);
    }

    @Override
    public void batchUpdateSubject(JournalSubjectVO[] journalSubjectVOs) {
        if (CollectionUtils.isEmpty((Object[])journalSubjectVOs)) {
            return;
        }
        for (JournalSubjectVO subjectVO : journalSubjectVOs) {
            if (subjectVO.getId() == null) {
                this.insertSubject(subjectVO);
                continue;
            }
            this.updateSubject(subjectVO);
        }
    }

    @Override
    public void batchDeleteSubject(String[] ids) {
        if (CollectionUtils.isEmpty((Object[])ids)) {
            return;
        }
        for (String id : ids) {
            if (null == id) continue;
            this.journalSubjectDao.deleteSubject(id);
        }
    }

    @Override
    public Integer deleteAllSubjects(String jRelateSchemeId) {
        return this.journalSubjectDao.deleteAllSubjects(jRelateSchemeId);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void exchangeSort(String opNodeId, int step) {
        JournalSubjectEO opeNode = (JournalSubjectEO)this.journalSubjectDao.get((Serializable)((Object)opNodeId));
        if (null == opeNode) {
            return;
        }
        JournalSubjectEO exeNode = step < 0 ? this.journalSubjectDao.getPreNodeByParentIdAndOrder(opeNode.getParentId(), opeNode.getSortOrder()) : this.journalSubjectDao.getNextNodeByParentIdAndOrder(opeNode.getParentId(), opeNode.getSortOrder());
        if (null == exeNode) {
            throw new BusinessRuntimeException(step < 0 ? "\u4e0d\u80fd\u518d\u79fb\u4e86\uff0c\u5df2\u7ecf\u4e3a\u7b2c\u4e00\u6761\u4e86" : "\u4e0d\u80fd\u518d\u79fb\u4e86\uff0c\u5df2\u7ecf\u4e3a\u6700\u540e\u4e00\u6761\u4e86");
        }
        String tempSort = opeNode.getSortOrder();
        opeNode.setSortOrder(exeNode.getSortOrder());
        exeNode.setSortOrder(tempSort);
        this.journalSubjectDao.update((BaseEntity)opeNode);
        this.journalSubjectDao.update((BaseEntity)exeNode);
    }

    @Override
    public Pagination<JournalSubjectVO> listChildSubjectsOrSelf(String parentId, boolean isAllChildren, int pageNum, int pageSize) {
        List<Object> content = new ArrayList<JournalSubjectVO>();
        int totalCount = this.listChildSubjects(parentId, isAllChildren);
        if (totalCount > 0) {
            List<JournalSubjectEO> subjectEOS = this.listChildSubjects(parentId, isAllChildren, isAllChildren ? -1 : pageNum, pageSize);
            if (isAllChildren) {
                Map<String, List<JournalSubjectEO>> parentId2DirectChildNode = subjectEOS.stream().collect(Collectors.groupingBy(JournalSubjectEO::getParentId));
                List<JournalSubjectEO> sortList = this.sortJournalSubject(parentId2DirectChildNode, parentId);
                totalCount = sortList.size();
                if (pageNum < 0 || pageSize < 0) {
                    subjectEOS = sortList;
                } else {
                    int startIndex = (pageNum - 1) * pageSize;
                    int endIndex = pageNum * pageSize < sortList.size() ? pageNum * pageSize : sortList.size();
                    subjectEOS = sortList.subList(startIndex, endIndex);
                }
            }
            content = subjectEOS.stream().map(subjectEo -> this.convertEO2VO((JournalSubjectEO)((Object)subjectEo))).collect(Collectors.toList());
        } else {
            JournalSubjectEO journalSubjectEO = (JournalSubjectEO)this.journalSubjectDao.get((Serializable)((Object)parentId));
            if (null != journalSubjectEO) {
                content.add(this.convertEO2VO(journalSubjectEO));
                totalCount = 1;
            }
        }
        return new Pagination(content, Integer.valueOf(totalCount), Integer.valueOf(pageNum), Integer.valueOf(pageSize));
    }

    private List<JournalSubjectEO> sortJournalSubject(Map<String, List<JournalSubjectEO>> parentId2DirectChildNode, String parentId) {
        LinkedList<JournalSubjectEO> journalSubject = new LinkedList<JournalSubjectEO>();
        List<JournalSubjectEO> journalSubjectEOS = parentId2DirectChildNode.get(parentId);
        if (CollectionUtils.isEmpty(journalSubjectEOS)) {
            return journalSubject;
        }
        journalSubjectEOS.sort(Comparator.comparing(JournalSubjectEO::getSortOrder));
        for (JournalSubjectEO eo : journalSubjectEOS) {
            journalSubject.add(eo);
            if (!parentId2DirectChildNode.containsKey(eo.getId())) continue;
            journalSubject.addAll(this.sortJournalSubject(parentId2DirectChildNode, eo.getId()));
        }
        return journalSubject;
    }

    @Override
    public JournalSubjectEO getJournalSubjectEO(String guid) {
        return (JournalSubjectEO)this.journalSubjectDao.get((Serializable)((Object)guid));
    }

    @Override
    public String getSubjectTitleByCode(String jRelateSchemeId, String subjectCode) {
        return this.journalSubjectDao.getSubjectTitleByCode(jRelateSchemeId, subjectCode);
    }

    @Override
    public JournalSubjectEO getSubjectEOByCode(String jRelateSchemeId, String subjectCode) {
        return this.journalSubjectDao.getSubjectEOByCode(jRelateSchemeId, subjectCode);
    }

    @Override
    public List<JournalSubjectTreeVO> listSubjectTree(String jRelateSchemeId, String expandId, boolean onlyLoadNeedShow) {
        List<JournalSubjectEO> allSubjects = this.journalSubjectDao.listAllSubjects(jRelateSchemeId);
        Set<String> expandIdSet = this.expandIdSet(expandId);
        Map<String, List<JournalSubjectEO>> parentId2DirectChildNode = allSubjects.stream().collect(Collectors.groupingBy(JournalSubjectEO::getParentId));
        JournalSubjectTreeVO simpleTreeVo = this.loadChildTreeNode(new JournalSubjectTreeVO(jRelateSchemeId, "", "\u6240\u6709\u9879\u76ee", null), parentId2DirectChildNode, expandIdSet, onlyLoadNeedShow);
        simpleTreeVo.setDataType(TreeNodeDataTypeEnum.DATATYPE_ROOT.getValue());
        simpleTreeVo.setExpand(Boolean.valueOf(true));
        ArrayList<JournalSubjectTreeVO> root = new ArrayList<JournalSubjectTreeVO>();
        root.add(simpleTreeVo);
        return root;
    }

    @Override
    public List<JournalSubjectTreeVO> listSubjectFilterTree(JournalSubjectTreeCondition condition) {
        String jRelateSchemeId = this.schemeService.getRelateSchemeId(condition.getTaskId(), condition.getSchemeId(), AdjustTypeEnum.VIRTUAL_TABLE.getCode());
        if (null == jRelateSchemeId) {
            return this.emptyRootTree(null);
        }
        return this.listSubjectTree(jRelateSchemeId, null, true);
    }

    @Override
    public List<JournalSubjectEO> listAllSubjects(String jRelateSchemeId) {
        return this.journalSubjectDao.listAllSubjects(jRelateSchemeId);
    }

    @Override
    public StringBuffer importSubject(List<JournalSingleSchemeExcelModel> excelModels, String jRelateSchemeId) {
        StringBuffer log = new StringBuffer(512);
        try {
            GcJournalSubjectImportUtil.parse(excelModels, jRelateSchemeId, log);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u5bfc\u5165\u5931\u8d25", (Throwable)e);
        }
        return log;
    }

    @Override
    public JournalSubjectEO getSubjectEOByZbCode(String zbCode) {
        return this.journalSubjectDao.getSubjectEOByZbCode(zbCode);
    }

    private List<JournalSubjectEO> listChildSubjects(String parentId, boolean isAllChildren, int pageNum, int pageSize) {
        if (isAllChildren) {
            return this.journalSubjectDao.listAllChildSubjects(parentId, pageNum, pageSize);
        }
        return this.journalSubjectDao.listDirectChildSubjects(parentId, pageNum, pageSize);
    }

    private int listChildSubjects(String parentId, boolean isAllChildren) {
        if (isAllChildren) {
            return this.journalSubjectDao.countAllChildSubjects(parentId);
        }
        return this.journalSubjectDao.countDirectChildSubjects(parentId);
    }

    private List<JournalSubjectEO> filterSubjectEOS(String jRelateSchemeId, String formKey) {
        ArrayList<JournalSubjectEO> filterSubjectEOS = new ArrayList<JournalSubjectEO>();
        Set<String> fieldKeySet = GcNpUtil.getInstance().getFieldKeySet(formKey);
        List<JournalSubjectEO> subjectEOS = this.journalSubjectDao.listAllSubjects(jRelateSchemeId);
        for (JournalSubjectEO subjectEO : subjectEOS) {
            boolean needShow = !Boolean.FALSE.equals(subjectEO.getNeedShow());
            if (!needShow || !fieldKeySet.contains(subjectEO.getAfterZbId()) && !fieldKeySet.contains(subjectEO.getBeforeZbId())) continue;
            filterSubjectEOS.add(subjectEO);
        }
        filterSubjectEOS.sort(new Comparator<JournalSubjectEO>(){

            @Override
            public int compare(JournalSubjectEO o1, JournalSubjectEO o2) {
                return o1.getCode().compareTo(o2.getCode());
            }
        });
        return filterSubjectEOS;
    }

    private List<JournalSubjectTreeVO> emptyRootTree(String jRelateSchemeId) {
        JournalSubjectTreeVO simpleTreeVo = new JournalSubjectTreeVO(jRelateSchemeId, "", "\u6240\u6709\u9879\u76ee", null);
        simpleTreeVo.setDataType(TreeNodeDataTypeEnum.DATATYPE_ROOT.getValue());
        simpleTreeVo.setExpand(Boolean.valueOf(true));
        ArrayList<JournalSubjectTreeVO> root = new ArrayList<JournalSubjectTreeVO>();
        root.add(simpleTreeVo);
        return root;
    }

    private Set<String> expandIdSet(String expandId) {
        HashSet<String> expandIds = new HashSet<String>(5);
        if (null == expandId) {
            return expandIds;
        }
        JournalSubjectEO subjectEO = (JournalSubjectEO)this.journalSubjectDao.get((Serializable)((Object)expandId));
        if (null == subjectEO) {
            return expandIds;
        }
        String parents = subjectEO.getParents();
        if (StringUtils.isEmpty(parents)) {
            return expandIds;
        }
        String[] parentArr = parents.split("/");
        try {
            for (String uuid : parentArr) {
                expandIds.add(uuid);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return expandIds;
    }

    private JournalSubjectTreeVO loadChildTreeNode(JournalSubjectTreeVO simpleTreeVo, Map<String, List<JournalSubjectEO>> parentId2DirectChildNode, Set<String> expandIdSet, boolean onlyLoadNeedShow) {
        List<JournalSubjectEO> directChildren = parentId2DirectChildNode.get(simpleTreeVo.getId());
        if (!CollectionUtils.isEmpty(directChildren)) {
            for (JournalSubjectEO directChild : directChildren) {
                if (onlyLoadNeedShow && Boolean.FALSE.equals(directChild.getNeedShow())) continue;
                simpleTreeVo.addChildren(this.loadChildTreeNode(new JournalSubjectTreeVO(directChild.getId(), directChild.getCode(), directChild.getTitle(), directChild.getParentId()), parentId2DirectChildNode, expandIdSet, onlyLoadNeedShow));
            }
        }
        if (simpleTreeVo.getChildren().isEmpty()) {
            simpleTreeVo.setDataType(TreeNodeDataTypeEnum.DATATYPE_FOLDER.getValue());
        } else {
            simpleTreeVo.setDataType(TreeNodeDataTypeEnum.DATATYPE_LEAF.getValue());
        }
        if (expandIdSet.contains(simpleTreeVo.getId())) {
            simpleTreeVo.setExpand(Boolean.valueOf(true));
        }
        return simpleTreeVo;
    }

    private JournalSubjectVO convertEO2VO(JournalSubjectEO subjectEo) {
        JournalSubjectVO journalSubjectVO = new JournalSubjectVO();
        BeanUtils.copyProperties((Object)subjectEo, journalSubjectVO);
        journalSubjectVO.setNeedShow(Integer.valueOf(subjectEo.getNeedShow() != null && subjectEo.getNeedShow() == 1 ? 1 : 0));
        return journalSubjectVO;
    }

    private void repairZbInfo(JournalSubjectEO subjectEO) {
        subjectEO.repairBeforeZbInfo();
        subjectEO.repairAfterZbInfo();
    }

    @Override
    public List<JournalSingleSchemeExcelModel> exportSubjectData(String parentId, Boolean isAllChildren) {
        Pagination<JournalSubjectVO> journalSubjectVOPagination = this.listChildSubjectsOrSelf(parentId, isAllChildren, -1, -1);
        List journalSubjectVOList = journalSubjectVOPagination.getContent();
        ArrayList<JournalSingleSchemeExcelModel> result = new ArrayList<JournalSingleSchemeExcelModel>();
        for (int i = 0; i < journalSubjectVOList.size(); ++i) {
            JournalSubjectVO journalSubjectVO = (JournalSubjectVO)journalSubjectVOList.get(i);
            JournalSingleSchemeExcelModel excelModel = new JournalSingleSchemeExcelModel();
            excelModel.setIndex("" + (i + 1));
            excelModel.setCode(journalSubjectVO.getCode());
            excelModel.setTitle(journalSubjectVO.getTitle());
            if (journalSubjectVO.getOrient() == 1) {
                excelModel.setOrient("\u501f");
            } else if (journalSubjectVO.getOrient() == -1) {
                excelModel.setOrient("\u8d37");
            }
            excelModel.setBeforeZbCode(journalSubjectVO.getBeforeZbCode());
            excelModel.setAfterZbCode(journalSubjectVO.getAfterZbCode());
            if (journalSubjectVO.getNeedShow() == 1) {
                excelModel.setNeedShow("\u662f");
            } else if (journalSubjectVO.getOrient() == 0) {
                excelModel.setNeedShow("\u5426");
            }
            result.add(excelModel);
        }
        return result;
    }
}

