/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.google.common.collect.Lists
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.samecontrol.SameControlConsts
 *  com.jiuqi.gcreport.samecontrol.dto.samectrlrule.AbstractCommonRule
 *  com.jiuqi.gcreport.samecontrol.dto.samectrlrule.DirectInvestmentDTO
 *  com.jiuqi.gcreport.samecontrol.dto.samectrlrule.DisposerInvestmentDTO
 *  com.jiuqi.gcreport.samecontrol.dto.samectrlrule.SameCtrlGroupRuleDTO
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleDragRuleVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleVO
 *  com.jiuqi.gcreport.unionrule.vo.ImportMessageVO
 *  com.jiuqi.xlib.runtime.Assert
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.samecontrol.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.samecontrol.SameControlConsts;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlRuleDao;
import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.AbstractCommonRule;
import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.DirectInvestmentDTO;
import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.DisposerInvestmentDTO;
import com.jiuqi.gcreport.samecontrol.dto.samectrlrule.SameCtrlGroupRuleDTO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlRuleEO;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlRuleService;
import com.jiuqi.gcreport.samecontrol.util.SameCtrlRuleEOFactory;
import com.jiuqi.gcreport.samecontrol.util.SameCtrlRuleFactory;
import com.jiuqi.gcreport.samecontrol.util.SameCtrlRuleVOFactory;
import com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleDragRuleVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlrule.SameCtrlRuleVO;
import com.jiuqi.gcreport.unionrule.vo.ImportMessageVO;
import com.jiuqi.xlib.runtime.Assert;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SameCtrlRuleServiceImpl
implements SameCtrlRuleService {
    public static final Logger logger = LoggerFactory.getLogger(SameCtrlRuleServiceImpl.class);
    private static final String OVERWRITE_MODE = "\u8986\u5199\u6a21\u5f0f";
    private static final String UPDATE_MODE = "\u66f4\u65b0\u6a21\u5f0f";
    private SameCtrlRuleDao sameCtrlRuleDao;

    public SameCtrlRuleServiceImpl(SameCtrlRuleDao sameCtrlRuleDao) {
        this.sameCtrlRuleDao = sameCtrlRuleDao;
    }

    @Override
    public List<SameCtrlRuleVO> listRuleTree(String reportSystem, String taskId) {
        SameCtrlRuleEO ruleEO = this.initRoot(reportSystem, taskId);
        SameCtrlRuleVO ruleVO = this.selectUnionRuleAndChildrenById(ruleEO.getId());
        return Lists.newArrayList((Object[])new SameCtrlRuleVO[]{ruleVO});
    }

    private SameCtrlRuleEO initRoot(String reportSystem, String taskId) {
        SameCtrlRuleEO root;
        List<SameCtrlRuleEO> ruleNodes = this.sameCtrlRuleDao.findByParentIdAndIsolatedFiled(SameControlConsts.ROOT_PARENT_ID, reportSystem, taskId);
        if (!CollectionUtils.isEmpty(ruleNodes)) {
            root = ruleNodes.get(0);
        } else {
            root = new SameCtrlRuleEO();
            root.setParentId(SameControlConsts.ROOT_PARENT_ID);
            root.setTitle("\u540c\u63a7\u671f\u521d\u5408\u5e76\u89c4\u5219");
            root.setReportSystem(reportSystem);
            root.setTaskId(taskId);
            root.setSortOrder(0);
            root.setLeafFlag(0);
            root.setStartFlag(1);
            root.setCreator("system");
            root.setDataType("root");
            root.setCreateTime(new Date());
            root.setUpdateTime(new Date());
            String rootId = (String)((Object)this.sameCtrlRuleDao.save(root));
            root.setId(rootId);
            if (logger.isDebugEnabled()) {
                logger.debug("\u521b\u5efa\u5408\u5e76\u4f53\u7cfb\uff1a[{}] \u5408\u5e76\u89c4\u5219\u6811\uff0c\u6839\u8282\u70b9ID\uff1a[{}]", (Object)reportSystem, (Object)rootId);
            }
        }
        return root;
    }

    private SameCtrlRuleVO selectUnionRuleAndChildrenById(String id) {
        SameCtrlRuleEO sameCtrlRuleEO = (SameCtrlRuleEO)this.sameCtrlRuleDao.get((Serializable)((Object)id));
        if (Objects.isNull((Object)sameCtrlRuleEO)) {
            throw new BusinessRuntimeException("\u6307\u5b9a\u8282\u70b9\u4e0d\u5b58\u5728\uff01");
        }
        SameCtrlRuleVO parent = SameCtrlRuleVOFactory.newNoSettingInstanceByEntity(sameCtrlRuleEO);
        parent.setBusinessTypeCode(GcBaseDataCenterTool.getInstance().convertGcBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_GCBUSINESSTYPE", sameCtrlRuleEO.getBusinessTypeCode())));
        this.findChildrenRules(parent);
        return parent;
    }

    private void findChildrenRules(SameCtrlRuleVO parent) {
        if (Boolean.TRUE.equals(parent.getLeafFlag())) {
            return;
        }
        List<SameCtrlRuleEO> ruleChildren = this.sameCtrlRuleDao.findByParentId(parent.getId());
        if (CollectionUtils.isEmpty(ruleChildren)) {
            return;
        }
        ruleChildren.sort(Comparator.comparing(SameCtrlRuleEO::getSortOrder));
        List children = parent.getChildren();
        for (SameCtrlRuleEO ruleEO : ruleChildren) {
            SameCtrlRuleVO ruleVO = SameCtrlRuleVOFactory.newNoSettingInstanceByEntity(ruleEO);
            ruleVO.setBusinessTypeCode(GcBaseDataCenterTool.getInstance().convertGcBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_GCBUSINESSTYPE", ruleEO.getBusinessTypeCode())));
            children.add(ruleVO);
            this.findChildrenRules(ruleVO);
        }
    }

    @Override
    public SameCtrlRuleVO saveSameCtrlRule(SameCtrlRuleVO sameCtrlRuleVO) {
        if (!"group".equals(sameCtrlRuleVO.getDataType()) && (Objects.isNull(sameCtrlRuleVO.getBusinessTypeCode()) || StringUtils.isEmpty((String)sameCtrlRuleVO.getBusinessTypeCode().getCode()))) {
            throw new BusinessRuntimeException("\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        }
        this.checkNewUnionRuleVO(sameCtrlRuleVO.getId(), sameCtrlRuleVO.getParentId(), sameCtrlRuleVO.getLeafFlag(), sameCtrlRuleVO.getTitle());
        SameCtrlRuleEO sameCtrlRuleEO = SameCtrlRuleEOFactory.newInstanceByVO(sameCtrlRuleVO);
        this.saveSameCtrlRule(sameCtrlRuleEO);
        return this.assembleUnionRuleVO(sameCtrlRuleEO);
    }

    private SameCtrlRuleVO importRule(AbstractCommonRule unionRuleDTO) {
        this.checkNewUnionRuleVO(unionRuleDTO.getId(), unionRuleDTO.getParentId(), unionRuleDTO.getLeafFlag(), unionRuleDTO.getTitle());
        SameCtrlRuleEO sameCtrlRuleEO = SameCtrlRuleFactory.convertEO(unionRuleDTO);
        BeanUtils.copyProperties(unionRuleDTO, (Object)sameCtrlRuleEO);
        sameCtrlRuleEO.setLeafFlag(Boolean.TRUE.equals(unionRuleDTO.getLeafFlag()) ? 1 : 0);
        sameCtrlRuleEO.setStartFlag(Boolean.TRUE.equals(unionRuleDTO.getStartFlag()) ? 1 : 0);
        this.saveSameCtrlRule(sameCtrlRuleEO);
        return this.assembleUnionRuleVO(sameCtrlRuleEO);
    }

    private void saveSameCtrlRule(SameCtrlRuleEO sameCtrlRuleEO) {
        if (Objects.nonNull(sameCtrlRuleEO.getId())) {
            sameCtrlRuleEO.setUpdateTime(new Date());
            SameCtrlRuleEO beforeUpdateRuleEO = (SameCtrlRuleEO)this.sameCtrlRuleDao.get((Serializable)((Object)sameCtrlRuleEO.getId()));
            if (!Objects.isNull((Object)beforeUpdateRuleEO)) {
                logger.info("\u89c4\u5219\u4fee\u6539\u524d\uff1a[{}]", (Object)beforeUpdateRuleEO);
            }
            this.sameCtrlRuleDao.update((BaseEntity)sameCtrlRuleEO);
            logger.info("\u89c4\u5219\u4fee\u6539\u540e\uff1a[{}]", (Object)sameCtrlRuleEO);
        } else {
            sameCtrlRuleEO.setStartFlag(1);
            sameCtrlRuleEO.setCreator("system");
            sameCtrlRuleEO.setCreateTime(new Date());
            sameCtrlRuleEO.setUpdateTime(new Date());
            sameCtrlRuleEO.setRuleCode(this.generateRuleCode(sameCtrlRuleEO.getLeafFlag()));
            Integer maxOrder = this.sameCtrlRuleDao.findMaxSortOrderByParentId(sameCtrlRuleEO.getParentId()) + 1;
            sameCtrlRuleEO.setSortOrder(maxOrder);
            String id = (String)((Object)this.sameCtrlRuleDao.save(sameCtrlRuleEO));
            sameCtrlRuleEO.setId(id);
        }
    }

    private SameCtrlRuleVO assembleUnionRuleVO(SameCtrlRuleEO sameCtrlRuleEO) {
        SameCtrlRuleVO sameCtrlRuleVO = SameCtrlRuleVOFactory.newNoSettingInstanceByEntity(sameCtrlRuleEO);
        sameCtrlRuleVO.setBusinessTypeCode(GcBaseDataCenterTool.getInstance().convertGcBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_GCBUSINESSTYPE", sameCtrlRuleEO.getBusinessTypeCode())));
        return sameCtrlRuleVO;
    }

    private void checkNewUnionRuleVO(String id, String parentId, boolean leafFlag, String title) {
        SameCtrlRuleEO parentRule = (SameCtrlRuleEO)this.sameCtrlRuleDao.get((Serializable)((Object)parentId));
        if (leafFlag && SameControlConsts.ROOT_PARENT_ID.equals(parentRule.getParentId())) {
            throw new BusinessRuntimeException("\u7b2c\u4e00\u7ea7\u5206\u7ec4\u4e0d\u5141\u8bb8\u65b0\u5efa\u89c4\u5219\uff01");
        }
        if (parentRule.getLeafFlag() == 1) {
            throw new BusinessRuntimeException("\u89c4\u5219\u4e0b\u4e0d\u5141\u8bb8\u65b0\u5efa\u5206\u7ec4\u6216\u89c4\u5219\uff01");
        }
        this.checkUnionRuleName(id, parentId, title);
    }

    private void checkUnionRuleName(String currentId, String parentId, String ruleName) {
        if (StringUtils.isEmpty((String)ruleName)) {
            throw new BusinessRuntimeException("\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if ("\u540c\u63a7\u671f\u521d\u5408\u5e76\u89c4\u5219".equals(ruleName)) {
            throw new BusinessRuntimeException("\u4e0d\u80fd\u7528[\u540c\u63a7\u671f\u521d\u5408\u5e76\u89c4\u5219]\u4f5c\u4e3a\u540d\u79f0\uff01");
        }
        List<SameCtrlRuleEO> brothers = this.sameCtrlRuleDao.findByParentId(parentId);
        brothers.forEach(brother -> {
            if (brother.getTitle().equals(ruleName)) {
                if (brother.getId().equals(currentId)) {
                    return;
                }
                throw new BusinessRuntimeException("\u540c\u4e00\u5206\u7ec4\u4e0b\u540d\u79f0\u4e0d\u80fd\u91cd\u590d\uff01");
            }
        });
    }

    private String generateRuleCode(int leafFlag) {
        if (leafFlag == 1) {
            return "GZ_" + System.currentTimeMillis();
        }
        return "group_" + System.currentTimeMillis();
    }

    @Override
    public SameCtrlRuleVO getSameCtrlRuleById(String id) {
        if (Objects.isNull(id)) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u89c4\u5219\u53c2\u6570[id]\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        SameCtrlRuleEO ruleEO = (SameCtrlRuleEO)this.sameCtrlRuleDao.get((Serializable)((Object)id));
        if (Objects.isNull((Object)ruleEO)) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u89c4\u5219\u4e0d\u5b58\u5728\uff01");
        }
        return SameCtrlRuleVOFactory.newInstanceByEntity(ruleEO);
    }

    @Override
    public SameCtrlRuleVO getSameCtrlRuleGroupByGroupId(String id) {
        return this.selectUnionRuleAndChildrenById(id);
    }

    @Override
    public void deleteSameCtrlRuleOrGroupById(String id) {
        if (Objects.isNull(id)) {
            throw new BusinessRuntimeException("id \u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        SameCtrlRuleEO ruleEO = (SameCtrlRuleEO)this.sameCtrlRuleDao.get((Serializable)((Object)id));
        if (Objects.nonNull((Object)ruleEO) && ruleEO.getParentId().equals(SameControlConsts.ROOT_PARENT_ID)) {
            throw new BusinessRuntimeException("\u6839\u8282\u70b9\u4e0d\u53ef\u5220\u9664\uff01");
        }
        SameCtrlRuleVO sameCtrlRuleVO = this.selectUnionRuleAndChildrenById(id);
        this.deleteSameCtrlRuleChildrenIfNotLeaf(sameCtrlRuleVO);
    }

    private void deleteSameCtrlRuleChildrenIfNotLeaf(SameCtrlRuleVO sameCtrlRuleVO) {
        List children = sameCtrlRuleVO.getChildren();
        if (!CollectionUtils.isEmpty((Collection)children)) {
            children.forEach(entity -> {
                if (entity.getLeafFlag().booleanValue()) {
                    throw new BusinessRuntimeException("\u89c4\u5219\u5206\u7ec4\u4e0b\u6709\u89c4\u5219\uff0c\u8bf7\u5220\u9664\u89c4\u5219\u540e\u518d\u5220\u9664\uff01");
                }
                this.deleteSameCtrlRuleChildrenIfNotLeaf((SameCtrlRuleVO)entity);
            });
        }
        SameCtrlRuleEO eo = new SameCtrlRuleEO();
        eo.setId(sameCtrlRuleVO.getId());
        this.sameCtrlRuleDao.delete((BaseEntity)eo);
    }

    @Override
    public void updateSameCtrlRuleOrGroupNameById(String id, String title) {
        String parentId = this.sameCtrlRuleDao.findParentIdById(id);
        this.checkUnionRuleName(id, parentId, title);
        SameCtrlRuleEO sameCtrlRuleEO = (SameCtrlRuleEO)this.sameCtrlRuleDao.get((Serializable)((Object)id));
        sameCtrlRuleEO.setTitle(title);
        this.sameCtrlRuleDao.update((BaseEntity)sameCtrlRuleEO);
    }

    @Override
    public void setSameCtrlRuleStatus(String id, Boolean startFlag) {
        if (Objects.isNull(id) || Objects.isNull(startFlag)) {
            throw new BusinessRuntimeException("\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        }
        Integer count = this.sameCtrlRuleDao.updateSameCtrlRuleStartFlagById(id, startFlag);
        if (Objects.isNull(count) || count < 1) {
            logger.error("\u66f4\u65b0\u89c4\u5219\u72b6\u6001\u5f02\u5e38\uff0c\u89c4\u5219ID\uff1a[{}]\uff0c\u53c2\u6570 startFlag\uff1a[{}]", (Object)id, (Object)startFlag);
            throw new BusinessRuntimeException("\u66f4\u65b0\u89c4\u5219\u72b6\u6001\u5931\u8d25\uff01");
        }
    }

    @Override
    public Resource exportJson(String reportSystemId, String taskId) {
        if (Objects.isNull(reportSystemId)) {
            throw new BusinessRuntimeException("\u8bf7\u9009\u62e9\u5408\u5e76\u4f53\u7cfb");
        }
        ArrayList<AbstractCommonRule> sameCtrlRuleDTOList = new ArrayList<AbstractCommonRule>();
        List<SameCtrlRuleEO> rulesWithGroup = this.sameCtrlRuleDao.listSameCtrlRuleAndGroup(reportSystemId, taskId);
        for (SameCtrlRuleEO sameCtrlRuleEO : rulesWithGroup) {
            AbstractCommonRule sameCtrlRuleDTO = SameCtrlRuleFactory.convertDTO(sameCtrlRuleEO);
            sameCtrlRuleDTOList.add(sameCtrlRuleDTO);
        }
        String json = JsonUtils.writeValueAsString(sameCtrlRuleDTOList);
        byte[] bytes = json.getBytes(Charset.forName("UTF-8"));
        return new ByteArrayResource(bytes);
    }

    @Override
    public Set<ImportMessageVO> importJson(String reportSystemId, String taskId, MultipartFile multipartFile, boolean isOverwrite) {
        List importJsonList;
        byte[] bytes;
        if (Objects.isNull(reportSystemId)) {
            throw new BusinessRuntimeException("\u8bf7\u9009\u62e9\u5408\u5e76\u4f53\u7cfb");
        }
        if (StringUtils.isEmpty((String)taskId)) {
            throw new BusinessRuntimeException("\u8bf7\u9009\u62e9\u62a5\u8868\u4efb\u52a1");
        }
        HashSet<ImportMessageVO> resultList = new HashSet<ImportMessageVO>();
        try {
            bytes = multipartFile.getBytes();
        }
        catch (IOException e) {
            throw new BusinessRuntimeException("\u65e0\u6cd5\u89e3\u6790\u5bfc\u5165\u6587\u4ef6\u3002", (Throwable)e);
        }
        if (bytes.length == 0) {
            resultList.add(new ImportMessageVO().setERROR(null, "\u6ca1\u6709\u53ef\u4ee5\u5bfc\u5165\u7684\u6570\u636e\u3002"));
            return resultList;
        }
        ArrayList<AbstractCommonRule> unionRuleDTOList = new ArrayList<AbstractCommonRule>();
        try {
            importJsonList = (List)JsonUtils.readValue((byte[])bytes, (TypeReference)new TypeReference<List<Map<String, Object>>>(){});
        }
        catch (Exception e) {
            logger.error("\u5bfc\u5165\u6587\u4ef6\u4ec5\u652f\u6301json\u683c\u5f0f", e);
            throw new BusinessRuntimeException("\u5bfc\u5165\u6587\u4ef6\u4ec5\u652f\u6301json\u683c\u5f0f");
        }
        for (Map importJsonListMap : importJsonList) {
            AbstractCommonRule sameCtrlRuleDTO = this.convertMapToDTO(importJsonListMap);
            int resultListSize = resultList.size();
            if (sameCtrlRuleDTO.getLeafFlag().booleanValue()) {
                this.checkRuleBusinessTypeCode(sameCtrlRuleDTO, resultList);
                this.checkSubjectCode(sameCtrlRuleDTO, resultList);
                if (resultList.size() > resultListSize) continue;
                unionRuleDTOList.add(sameCtrlRuleDTO);
                continue;
            }
            unionRuleDTOList.add(sameCtrlRuleDTO);
        }
        if (CollectionUtils.isEmpty(unionRuleDTOList)) {
            resultList.add(new ImportMessageVO().setERROR(null, "\u6ca1\u6709\u53ef\u4ee5\u5bfc\u5165\u7684\u6570\u636e\u3002"));
            return resultList;
        }
        unionRuleDTOList.forEach(rule -> {
            rule.setReportSystem(reportSystemId);
            rule.setTaskId(taskId);
        });
        Map<String, List<AbstractCommonRule>> parentMap = unionRuleDTOList.stream().collect(Collectors.groupingBy(AbstractCommonRule::getParentId));
        List importRuleIds = unionRuleDTOList.stream().map(AbstractCommonRule::getId).collect(Collectors.toList());
        if (parentMap.keySet().stream().anyMatch(parentId -> !importRuleIds.contains(parentId) && !SameControlConsts.ROOT_PARENT_ID.equals(parentId))) {
            resultList.add(new ImportMessageVO().setERROR(null, "\u5b58\u5728\u95ee\u9898\u6570\u636e\u4e0a\u4e0b\u7ea7\u5173\u7cfb\u4e0d\u4e00\u81f4\uff0c\u8bf7\u68c0\u67e5json\u6587\u4ef6"));
            return resultList;
        }
        List<AbstractCommonRule> rootList = parentMap.get(SameControlConsts.ROOT_PARENT_ID);
        if (CollectionUtils.isEmpty(rootList)) {
            resultList.add(new ImportMessageVO().setERROR(null, "\u6587\u4ef6\u683c\u5f0f\u9519\u8bef\u3002"));
            return resultList;
        }
        AbstractCommonRule oldRoot = rootList.get(0);
        String modeMsg = isOverwrite ? OVERWRITE_MODE : UPDATE_MODE;
        logger.info("\u5bfc\u5165\u89c4\u5219\u6a21\u5f0f\uff1a[{}]", (Object)modeMsg);
        if (isOverwrite) {
            logger.info("\u5220\u9664\u4f53\u7cfb[{}]\u4e0b\u6240\u6709\u89c4\u5219", (Object)reportSystemId);
            this.sameCtrlRuleDao.batchDeleteByReportSystemId(reportSystemId, taskId);
            List<SameCtrlRuleVO> newTree = this.listRuleTree(reportSystemId, taskId);
            SameCtrlRuleVO newRoot = newTree.get(0);
            List<AbstractCommonRule> gourp1List = parentMap.get(oldRoot.getId());
            for (AbstractCommonRule rule2 : gourp1List) {
                String oldParent = rule2.getId();
                rule2.setParentId(newRoot.getId());
                rule2.setId(null);
                SameCtrlRuleVO newRule = this.importRule(rule2);
                this.createChildrenNode(oldParent, newRule.getId(), parentMap);
            }
        } else {
            List<SameCtrlRuleVO> ruleTree = this.listRuleTree(reportSystemId, taskId);
            SameCtrlRuleVO rootRule = this.getRootRule(ruleTree);
            List dbChildren = rootRule.getChildren();
            List<AbstractCommonRule> importChildren = parentMap.get(oldRoot.getId());
            this.cycleCheckChildren(rootRule.getId(), importChildren, dbChildren, parentMap);
        }
        logger.info("\u89c4\u5219\u5bfc\u5165\u6210\u529f");
        return resultList;
    }

    private SameCtrlRuleVO getRootRule(List<SameCtrlRuleVO> ruleTree) {
        Assert.notEmpty(ruleTree, (String)"can not be empty");
        return ruleTree.get(0);
    }

    private void cycleCheckChildren(String parentId, List<AbstractCommonRule> importChildren, List<SameCtrlRuleVO> dbChildren, Map<String, List<AbstractCommonRule>> parentMap) {
        if (CollectionUtils.isEmpty(importChildren)) {
            return;
        }
        for (AbstractCommonRule ruleDTO : importChildren) {
            SameCtrlRuleVO rule = this.findSameRule(dbChildren, ruleDTO);
            if (rule != null) {
                String impId = ruleDTO.getId();
                ruleDTO.setId(rule.getId());
                ruleDTO.setParentId(parentId);
                this.importRule(ruleDTO);
                List<AbstractCommonRule> list = parentMap.get(impId);
                List children = rule.getChildren();
                this.cycleCheckChildren(rule.getId(), list, children, parentMap);
                continue;
            }
            String oldId = ruleDTO.getId();
            ruleDTO.setId(null);
            ruleDTO.setParentId(parentId);
            SameCtrlRuleVO newNode = this.importRule(ruleDTO);
            if (!Objects.nonNull(oldId)) continue;
            this.createChildrenNode(oldId, newNode.getId(), parentMap);
        }
    }

    private SameCtrlRuleVO findSameRule(List<SameCtrlRuleVO> list, AbstractCommonRule rule) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        for (SameCtrlRuleVO ruleVO : list) {
            if (!ruleVO.getTitle().equals(rule.getTitle())) continue;
            return ruleVO;
        }
        return null;
    }

    private void createChildrenNode(String oldParentId, String newParentId, Map<String, List<AbstractCommonRule>> parentMap) {
        List<AbstractCommonRule> abstractUnionRuleList = parentMap.get(oldParentId);
        if (CollectionUtils.isEmpty(abstractUnionRuleList)) {
            return;
        }
        for (AbstractCommonRule ruleDTO : abstractUnionRuleList) {
            String oldParent = ruleDTO.getId();
            ruleDTO.setId(null);
            ruleDTO.setParentId(newParentId);
            SameCtrlRuleVO newRule = this.importRule(ruleDTO);
            this.createChildrenNode(oldParent, newRule.getId(), parentMap);
        }
    }

    private AbstractCommonRule convertMapToDTO(Map<String, Object> importJsonListMap) {
        AbstractCommonRule rule = null;
        String leafFlag = "leafFlag";
        String ruleType = "ruleType";
        if (!Boolean.TRUE.equals(importJsonListMap.get(leafFlag))) {
            rule = (AbstractCommonRule)JsonUtils.readValue((String)JsonUtils.writeValueAsString(importJsonListMap), SameCtrlGroupRuleDTO.class);
        } else if (SameCtrlRuleTypeEnum.DIRECT_INVESTMENT.getCode().equals(importJsonListMap.get(ruleType))) {
            rule = (AbstractCommonRule)JsonUtils.readValue((String)JsonUtils.writeValueAsString(importJsonListMap), DirectInvestmentDTO.class);
        } else if (SameCtrlRuleTypeEnum.DISPOSER_INVESTMENT.getCode().equals(importJsonListMap.get(ruleType))) {
            rule = (AbstractCommonRule)JsonUtils.readValue((String)JsonUtils.writeValueAsString(importJsonListMap), DisposerInvestmentDTO.class);
        }
        return rule;
    }

    private void checkRuleBusinessTypeCode(AbstractCommonRule sameCtrlRuleDTO, Set<ImportMessageVO> resultList) {
        if (org.springframework.util.StringUtils.isEmpty(sameCtrlRuleDTO.getTitle())) {
            resultList.add(new ImportMessageVO().setERROR("", "\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\u3002"));
        }
        if (Objects.isNull(sameCtrlRuleDTO.getRuleType())) {
            resultList.add(new ImportMessageVO().setERROR(sameCtrlRuleDTO.getTitle(), "\u89c4\u5219\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a\u3002"));
        }
        if (!org.springframework.util.StringUtils.isEmpty(sameCtrlRuleDTO.getBusinessTypeCode())) {
            BaseDataVO baseDataVO = GcBaseDataCenterTool.getInstance().convertBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_GCBUSINESSTYPE", sameCtrlRuleDTO.getBusinessTypeCode()));
            if (Objects.isNull(baseDataVO)) {
                resultList.add(new ImportMessageVO().setERROR(sameCtrlRuleDTO.getTitle(), "\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u4e0d\u5b58\u5728\u3002"));
            }
        } else {
            resultList.add(new ImportMessageVO().setERROR(sameCtrlRuleDTO.getTitle(), "\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a\u3002"));
        }
    }

    private void checkSubjectCode(AbstractCommonRule sameCtrlRuleDTO, Set<ImportMessageVO> resultList) {
        if (!CollectionUtils.isEmpty((Collection)sameCtrlRuleDTO.getSrcDebitSubjectCodeList())) {
            this.checkSubjectCode(sameCtrlRuleDTO, sameCtrlRuleDTO.getSrcDebitSubjectCodeList(), resultList);
        }
        if (!CollectionUtils.isEmpty((Collection)sameCtrlRuleDTO.getSrcCreditSubjectCodeList())) {
            this.checkSubjectCode(sameCtrlRuleDTO, sameCtrlRuleDTO.getSrcCreditSubjectCodeList(), resultList);
        }
    }

    private void checkSubjectCode(AbstractCommonRule sameCtrlRuleDTO, List<String> subjectCodes, Set<ImportMessageVO> resultList) {
        subjectCodes.forEach(debitConfigSubject -> {
            BaseDataVO baseDataVO = GcBaseDataCenterTool.getInstance().convertBaseDataVO(GcBaseDataCenterTool.getInstance().queryBasedataByObjCode("MD_GCSUBJECT", GcBaseDataCenterTool.combiningObjectCode((String)debitConfigSubject, (String[])new String[]{sameCtrlRuleDTO.getReportSystem()})));
            if (Objects.isNull(baseDataVO)) {
                resultList.add(new ImportMessageVO().setERROR(sameCtrlRuleDTO.getTitle(), "\u79d1\u76ee\u5728\u5f53\u524d\u4f53\u7cfb\u4e2d\u4e0d\u5b58\u5728\u3002"));
            }
        });
    }

    @Override
    public SameCtrlRuleVO cutOrPaste(String id, String parentId, String action) {
        if (Objects.isNull(id) || Objects.isNull(parentId)) {
            throw new BusinessRuntimeException("\u89c4\u5219\u6216\u5206\u7ec4\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String cut = "cut";
        String copy = "copy";
        if (!StringUtils.equalsIgnoreCaseAny((String)action, (String[])new String[]{cut, copy})) {
            throw new BusinessRuntimeException("\u53c2\u6570\u6709\u8bef");
        }
        SameCtrlRuleEO ruleEO = (SameCtrlRuleEO)this.sameCtrlRuleDao.get((Serializable)((Object)id));
        if (Objects.isNull((Object)ruleEO)) {
            throw new BusinessRuntimeException("\u5f85\u79fb\u52a8\u89c4\u5219\u6216\u5206\u7ec4\u6570\u636e\u4e22\u5931\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        String lastParentId = ruleEO.getParentId();
        ruleEO.setParentId(parentId);
        ruleEO.setRuleCode(this.generateRuleCode(ruleEO.getLeafFlag()));
        Integer maxOrder = this.sameCtrlRuleDao.findMaxSortOrderByParentId(parentId);
        maxOrder = maxOrder + 1;
        ruleEO.setSortOrder(maxOrder);
        String newTitle = cut.equalsIgnoreCase(action) ? ruleEO.getTitle() : ruleEO.getTitle() + " Copy";
        ruleEO.setTitle(newTitle);
        this.checkUnionRuleName(id, parentId, newTitle);
        if (cut.equalsIgnoreCase(action)) {
            if (lastParentId.equals(parentId)) {
                throw new BusinessRuntimeException("\u8bf7\u526a\u5207\u5230\u4e0d\u540c\u5206\u7ec4\u4e0b");
            }
            this.sameCtrlRuleDao.update((BaseEntity)ruleEO);
        } else if (copy.equalsIgnoreCase(action)) {
            ruleEO.setId(null);
            ruleEO.setCreateTime(new Date());
            ruleEO.setUpdateTime(new Date());
            String newId = (String)((Object)this.sameCtrlRuleDao.save(ruleEO));
            ruleEO.setId(newId);
        }
        return this.assembleUnionRuleVO(ruleEO);
    }

    @Override
    public List<AbstractCommonRule> findRuleListByIsolatedFiledAndRuleTypes(String reportSystemId, String taskId, Collection<SameCtrlRuleTypeEnum> ruleTypeEnumList) {
        Assert.notNull((Object)reportSystemId, (String)"\u5408\u5e76\u4f53\u7cfbID\u4e0d\u80fd\u4e3a\u7a7a");
        List<SameCtrlRuleEO> unionRuleEOList = this.sameCtrlRuleDao.findRuleList(reportSystemId, taskId);
        if (CollectionUtils.isEmpty(ruleTypeEnumList)) {
            return unionRuleEOList.stream().map(this::convertDTO).collect(Collectors.toList());
        }
        ArrayList finalResult = Lists.newArrayList();
        for (SameCtrlRuleEO entity : unionRuleEOList) {
            SameCtrlRuleTypeEnum ruleTypeEnum = SameCtrlRuleTypeEnum.codeOf((String)entity.getRuleType());
            if (!ruleTypeEnumList.contains(ruleTypeEnum)) continue;
            finalResult.add(this.convertDTO(entity));
        }
        return finalResult;
    }

    private AbstractCommonRule convertDTO(SameCtrlRuleEO sameCtrlRuleEO) {
        AbstractCommonRule rule;
        String jsonString = sameCtrlRuleEO.getJsonString();
        if (!Objects.equals(1, sameCtrlRuleEO.getLeafFlag())) {
            SameCtrlGroupRuleDTO rule2 = new SameCtrlGroupRuleDTO();
            BeanUtils.copyProperties((Object)sameCtrlRuleEO, rule2);
            rule2.setLeafFlag(Boolean.valueOf(Objects.equals(sameCtrlRuleEO.getLeafFlag(), 1)));
            rule2.setStartFlag(Boolean.valueOf(Objects.equals(sameCtrlRuleEO.getStartFlag(), 1)));
            rule2.setRuleType(null);
            return rule2;
        }
        SameCtrlRuleTypeEnum ruleType = SameCtrlRuleTypeEnum.codeOf((String)sameCtrlRuleEO.getRuleType());
        if (SameCtrlRuleTypeEnum.DIRECT_INVESTMENT.equals((Object)ruleType)) {
            rule = (AbstractCommonRule)JsonUtils.readValue((String)jsonString, DirectInvestmentDTO.class);
        } else if (SameCtrlRuleTypeEnum.DISPOSER_INVESTMENT.equals((Object)ruleType)) {
            rule = (AbstractCommonRule)JsonUtils.readValue((String)jsonString, DisposerInvestmentDTO.class);
        } else {
            throw new BusinessRuntimeException("\u65e0\u6548\u7684\u89c4\u5219\u7c7b\u578b");
        }
        assert (rule != null);
        BeanUtils.copyProperties((Object)sameCtrlRuleEO, rule);
        rule.setLeafFlag(Boolean.valueOf(Objects.equals(sameCtrlRuleEO.getLeafFlag(), 1)));
        rule.setStartFlag(Boolean.valueOf(Objects.equals(sameCtrlRuleEO.getStartFlag(), 1)));
        rule.setRuleType(ruleType);
        return rule;
    }

    @Override
    public void dragRuleNode(SameCtrlRuleDragRuleVO dragRuleVO) {
        List<SameCtrlRuleEO> allNodeBetweenDragTargetAndDraggingList;
        String targetNodeBefore = "before";
        SameCtrlRuleVO draggingNodeRuleVO = dragRuleVO.getDraggingNode();
        SameCtrlRuleVO dragTargetNodeRuleVO = dragRuleVO.getDragPositionNode();
        String dragNodeUpAndDown = dragRuleVO.getDragNodeUpAndDown();
        SameCtrlRuleEO draggingNodeRuleEO = (SameCtrlRuleEO)this.sameCtrlRuleDao.get((Serializable)((Object)draggingNodeRuleVO.getId()));
        SameCtrlRuleEO dragTargetNodeRuleEO = (SameCtrlRuleEO)this.sameCtrlRuleDao.get((Serializable)((Object)dragTargetNodeRuleVO.getId()));
        String parentId = dragTargetNodeRuleEO.getParentId();
        int draggingSortOrder = draggingNodeRuleEO.getSortOrder();
        int dragTargetSortOrder = dragTargetNodeRuleEO.getSortOrder();
        int dragSortOrderSubscript = draggingSortOrder;
        if (draggingSortOrder > dragTargetSortOrder) {
            int dragSortOrderSuperscript = targetNodeBefore.equals(dragNodeUpAndDown) ? dragTargetSortOrder : dragTargetSortOrder + 1;
            allNodeBetweenDragTargetAndDraggingList = this.sameCtrlRuleDao.findBetweenDragTargetAndDraggingByParentIdAndSortOrder(parentId, dragSortOrderSuperscript, dragSortOrderSubscript);
            SameCtrlRuleEO sameCtrlRuleEO = allNodeBetweenDragTargetAndDraggingList.get(allNodeBetweenDragTargetAndDraggingList.size() - 1);
            sameCtrlRuleEO.setSortOrder(dragSortOrderSuperscript);
            this.drag(allNodeBetweenDragTargetAndDraggingList.subList(0, allNodeBetweenDragTargetAndDraggingList.size() - 1), sortOrder -> sortOrder + 1);
        } else {
            int dragSortOrderSuperscript = targetNodeBefore.equals(dragNodeUpAndDown) ? dragTargetSortOrder - 1 : dragTargetSortOrder;
            allNodeBetweenDragTargetAndDraggingList = this.sameCtrlRuleDao.findBetweenDragTargetAndDraggingByParentIdAndSortOrder(parentId, dragSortOrderSubscript, dragSortOrderSuperscript);
            SameCtrlRuleEO sameCtrlRuleEO = allNodeBetweenDragTargetAndDraggingList.get(0);
            sameCtrlRuleEO.setSortOrder(dragSortOrderSuperscript);
            this.drag(allNodeBetweenDragTargetAndDraggingList.subList(1, allNodeBetweenDragTargetAndDraggingList.size()), sortOrder -> sortOrder - 1);
        }
        this.sameCtrlRuleDao.updateSortOrder(allNodeBetweenDragTargetAndDraggingList);
    }

    void drag(List<SameCtrlRuleEO> allNodeBetweenDragTargetAndDraggingList, Function<Integer, Integer> sortOrderCalcFunc) {
        allNodeBetweenDragTargetAndDraggingList.forEach(unionRuleEO -> unionRuleEO.setSortOrder((Integer)sortOrderCalcFunc.apply(unionRuleEO.getSortOrder())));
    }
}

