/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO
 *  com.jiuqi.gcreport.basedata.api.vo.BaseDataVO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.i18n.util.GcI18nHelper
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.gcreport.unionrule.constant.RuleConst
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.gcreport.unionrule.dto.RuleChangeEvent
 *  com.jiuqi.gcreport.unionrule.dto.RuleChangeEvent$RuleChangedInfo
 *  com.jiuqi.gcreport.unionrule.dto.UnionGroupRuleDTO
 *  com.jiuqi.gcreport.unionrule.vo.BaseRuleVO
 *  com.jiuqi.gcreport.unionrule.vo.DragRuleVO
 *  com.jiuqi.gcreport.unionrule.vo.ITree
 *  com.jiuqi.gcreport.unionrule.vo.SelectFloatLineOptionTreeVO
 *  com.jiuqi.gcreport.unionrule.vo.UnionRuleVO
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.unionrule.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.api.itree.GcBaseDataVO;
import com.jiuqi.gcreport.basedata.api.vo.BaseDataVO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.service.ManagementDimensionCacheService;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.consolidatedsystem.service.subject.ConsolidatedSubjectService;
import com.jiuqi.gcreport.consolidatedsystem.service.task.ConsolidatedTaskService;
import com.jiuqi.gcreport.consolidatedsystem.util.ConsolidatedSystemUtils;
import com.jiuqi.gcreport.consolidatedsystem.vo.option.ConsolidatedOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.task.ConsolidatedTaskVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.i18n.util.GcI18nHelper;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.gcreport.unionrule.base.RuleManagerFactory;
import com.jiuqi.gcreport.unionrule.base.UnionRuleManager;
import com.jiuqi.gcreport.unionrule.cache.UnionRuleCacheService;
import com.jiuqi.gcreport.unionrule.cache.UnionRuleChangedEvent;
import com.jiuqi.gcreport.unionrule.constant.RuleConst;
import com.jiuqi.gcreport.unionrule.dao.UnionRuleDao;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.dto.RuleChangeEvent;
import com.jiuqi.gcreport.unionrule.dto.UnionGroupRuleDTO;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.gcreport.unionrule.util.UnionRuleConverter;
import com.jiuqi.gcreport.unionrule.util.UnionRuleUtils;
import com.jiuqi.gcreport.unionrule.util.UnionRuleVOFactory;
import com.jiuqi.gcreport.unionrule.vo.BaseRuleVO;
import com.jiuqi.gcreport.unionrule.vo.DragRuleVO;
import com.jiuqi.gcreport.unionrule.vo.ITree;
import com.jiuqi.gcreport.unionrule.vo.SelectFloatLineOptionTreeVO;
import com.jiuqi.gcreport.unionrule.vo.UnionRuleVO;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

@Service
public class UnionRuleServiceImpl
implements UnionRuleService {
    public static final Logger logger = LoggerFactory.getLogger(UnionRuleServiceImpl.class);
    @Autowired
    private UnionRuleDao unionRuleDao;
    @Autowired
    private ConsolidatedTaskService consolidatedTaskService;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private ApplicationContext applicationContext;
    private ExecutorService executorService = Executors.newFixedThreadPool(1);
    @Autowired
    private ConsolidatedSystemDao consolidatedSystemDao;
    @Autowired
    private ConsolidatedSubjectService consolidatedSubjectService;
    @Autowired
    private RuleManagerFactory ruleManagerFactory;
    @Autowired
    private UnionRuleCacheService unionRuleCacheService;
    @Autowired
    private GcI18nHelper i18Provider;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public UnionRuleVO saveOrUpdateRule(UnionRuleVO unionRuleVO) {
        this.checkNewUnionRuleVO(unionRuleVO);
        UnionRuleEO ruleEO = this.convertVo2Eo(unionRuleVO);
        String systemName = "";
        ConsolidatedSystemEO consolidatedSystem = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)ruleEO.getReportSystem()));
        if (consolidatedSystem != null) {
            systemName = consolidatedSystem.getSystemName();
        }
        if (Objects.nonNull(unionRuleVO.getId())) {
            ruleEO.setUpdateTime(new Date());
            UnionRuleEO beforeUpdateRuleEO = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)unionRuleVO.getId()));
            if (!Objects.isNull((Object)beforeUpdateRuleEO)) {
                logger.info("\u89c4\u5219\u4fee\u6539\u524d\uff1a[{}]", (Object)beforeUpdateRuleEO);
            }
            this.updateDb(ruleEO);
            LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u89c4\u5219", (String)("\u4fee\u6539-" + systemName + "\u5408\u5e76\u4f53\u7cfb-" + ruleEO.getTitle() + "\u5408\u5e76\u89c4\u5219"), (String)("\u4fee\u6539\u540e:\n" + JsonUtils.writeValueAsString((Object)((Object)ruleEO))));
        } else {
            ruleEO.setStartFlag(1);
            ruleEO.setCreator("system");
            ruleEO.setCreateTime(new Date());
            ruleEO.setUpdateTime(new Date());
            ruleEO.setRuleCode(this.generateRuleCode(ruleEO.getLeafFlag()));
            Integer maxOrder = this.unionRuleDao.findMaxSortOrderByParentId(ruleEO.getParentId());
            maxOrder = maxOrder + 1;
            ruleEO.setSortOrder(maxOrder);
            String id = (String)((Object)this.unionRuleDao.save(ruleEO));
            ruleEO.setId(id);
            LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u89c4\u5219", (String)("\u65b0\u589e-" + systemName + "\u5408\u5e76\u4f53\u7cfb-" + ruleEO.getTitle() + "\u5408\u5e76\u89c4\u5219"), (String)("\u65b0\u589e\uff1a\n" + JsonUtils.writeValueAsString((Object)((Object)ruleEO))));
        }
        NpContext context = NpContextHolder.getContext();
        this.applicationContext.publishEvent(new UnionRuleChangedEvent(new UnionRuleChangedEvent.UnionRuleChangedInfo(ruleEO.getReportSystem()), context));
        return UnionRuleVOFactory.newInstanceByEntity(ruleEO);
    }

    private UnionRuleEO convertVo2Eo(UnionRuleVO unionRuleVO) {
        UnionRuleEO ruleEO = new UnionRuleEO();
        String root = "root";
        String group = "group";
        if (!root.equalsIgnoreCase(unionRuleVO.getRuleType()) && !group.equalsIgnoreCase(unionRuleVO.getRuleType())) {
            UnionRuleManager ruleManager = UnionRuleUtils.getManagerByRuleTypeCode(unionRuleVO.getRuleType());
            unionRuleVO.setJsonString(ruleManager.getRuleHandler().convertJsonStringWhenVO2EO(unionRuleVO));
        }
        BeanUtils.copyProperties(unionRuleVO, (Object)ruleEO);
        ruleEO.setLeafFlag(Boolean.TRUE.equals(unionRuleVO.getLeafFlag()) ? 1 : 0);
        ruleEO.setStartFlag(Boolean.TRUE.equals(unionRuleVO.getStartFlag()) ? 1 : 0);
        ruleEO.setInitTypeFlag(Boolean.TRUE.equals(unionRuleVO.getInitTypeFlag()) ? 1 : 0);
        ruleEO.setEnableToleranceFlag(Boolean.TRUE.equals(unionRuleVO.getEnableToleranceFlag()) ? 1 : 0);
        ruleEO.setBusinessTypeCode(unionRuleVO.getBusinessTypeCode().getCode());
        ruleEO.setApplyGcUnits(CollectionUtils.isEmpty(unionRuleVO.getApplyGcUnits()) ? "" : unionRuleVO.getApplyGcUnits().stream().collect(Collectors.joining(",")));
        return ruleEO;
    }

    private void updateDb(UnionRuleEO ruleEO) {
        this.unionRuleDao.update((BaseEntity)ruleEO);
        logger.info("\u89c4\u5219\u4fee\u6539\u540e\uff1a[{}]", (Object)ruleEO);
        NpContext context = NpContextHolder.getContext();
        this.executorService.execute(() -> this.applicationContext.publishEvent((ApplicationEvent)new RuleChangeEvent(new RuleChangeEvent.RuleChangedInfo(ruleEO.getId(), ruleEO.getRuleType()), context)));
    }

    private String generateRuleCode(int leafFlag) {
        if (leafFlag == 1) {
            return "GZ_" + System.currentTimeMillis();
        }
        return "group_" + System.currentTimeMillis();
    }

    private void checkNewUnionRuleVO(UnionRuleVO unionRuleVO) {
        if (!"group".equals(unionRuleVO.getRuleType()) && (Objects.isNull(unionRuleVO.getBusinessTypeCode()) || StringUtils.isEmpty((String)unionRuleVO.getBusinessTypeCode().getCode()))) {
            throw new BusinessRuntimeException("\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String parentId = unionRuleVO.getParentId();
        UnionRuleEO parentRule = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)parentId));
        if (unionRuleVO.getLeafFlag().booleanValue() && RuleConst.ROOT_PARENT_ID.equals(parentRule.getParentId())) {
            throw new BusinessRuntimeException("\u7b2c\u4e00\u7ea7\u5206\u7ec4\u4e0d\u5141\u8bb8\u65b0\u5efa\u89c4\u5219\uff01");
        }
        if (parentRule.getLeafFlag() == 1) {
            throw new BusinessRuntimeException("\u89c4\u5219\u4e0b\u4e0d\u5141\u8bb8\u65b0\u5efa\u5206\u7ec4\u6216\u89c4\u5219\uff01");
        }
        if (Boolean.TRUE.equals(unionRuleVO.getEnableToleranceFlag())) {
            if (StringUtils.isEmpty((String)unionRuleVO.getOffsetType())) {
                throw new BusinessRuntimeException("\u62b5\u9500\u91d1\u989d\u7b56\u7565\u4e0d\u80fd\u4e3a\u7a7a");
            }
            if (StringUtils.isEmpty((String)unionRuleVO.getToleranceType())) {
                throw new BusinessRuntimeException("\u5bb9\u5dee\u65b9\u5f0f\u4e0d\u80fd\u4e3a\u7a7a");
            }
        }
        this.checkUnionRuleName(unionRuleVO.getId(), parentId, unionRuleVO.getTitle());
    }

    private void checkUnionRuleName(String currentId, String parentId, String ruleName) {
        if (StringUtils.isEmpty((String)ruleName)) {
            throw new BusinessRuntimeException("\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if ("\u5408\u5e76\u89c4\u5219".equals(ruleName)) {
            throw new BusinessRuntimeException("\u4e0d\u80fd\u7528[\u5408\u5e76\u89c4\u5219]\u4f5c\u4e3a\u540d\u79f0\uff01");
        }
        List<UnionRuleEO> brothers = this.unionRuleDao.findByParentId(parentId);
        brothers.forEach(brother -> {
            if (brother.getTitle().equals(ruleName)) {
                if (brother.getId().equals(currentId)) {
                    return;
                }
                throw new BusinessRuntimeException("\u540c\u4e00\u5206\u7ec4\u4e0b\u540d\u79f0\u4e0d\u80fd\u91cd\u590d\uff01");
            }
        });
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public UnionRuleVO importRule(AbstractUnionRule unionRuleDTO) {
        this.checkNewImportUnionRuleVO(unionRuleDTO);
        UnionRuleEO ruleEO = UnionRuleConverter.convertUnionRuleDTOToEO(unionRuleDTO);
        BeanUtils.copyProperties(unionRuleDTO, (Object)ruleEO);
        ruleEO.setLeafFlag(Boolean.TRUE.equals(unionRuleDTO.getLeafFlag()) ? 1 : 0);
        ruleEO.setStartFlag(Boolean.TRUE.equals(unionRuleDTO.getStartFlag()) ? 1 : 0);
        ruleEO.setInitTypeFlag(Boolean.TRUE.equals(unionRuleDTO.getInitTypeFlag()) ? 1 : 0);
        ruleEO.setEnableToleranceFlag(unionRuleDTO.getEnableToleranceFlag() != false ? 1 : 0);
        if (unionRuleDTO.getOffsetType() != null) {
            String offsetType = unionRuleDTO.getOffsetType().getCode();
            ruleEO.setOffsetType(offsetType);
        }
        if (unionRuleDTO.getToleranceType() != null) {
            String toleranceType = unionRuleDTO.getToleranceType().getCode();
            ruleEO.setToleranceType(toleranceType);
        }
        String systemName = "";
        ConsolidatedSystemEO consolidatedSystem = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)ruleEO.getReportSystem()));
        if (consolidatedSystem != null) {
            systemName = consolidatedSystem.getSystemName();
        }
        if (Objects.nonNull(unionRuleDTO.getId())) {
            ruleEO.setUpdateTime(new Date());
            UnionRuleEO beforeUpdateRuleEO = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)unionRuleDTO.getId()));
            if (!Objects.isNull((Object)beforeUpdateRuleEO)) {
                logger.info("\u89c4\u5219\u4fee\u6539\u524d\uff1a[{}]", (Object)beforeUpdateRuleEO);
            }
            this.updateDb(ruleEO);
            LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u89c4\u5219", (String)("\u5bfc\u5165-" + systemName + "\u5408\u5e76\u4f53\u7cfb-" + ruleEO.getTitle() + "\u5408\u5e76\u89c4\u5219"), (String)("\u5bfc\u5165\u65f6\u4fee\u6539\u89c4\u5219\uff1a\n" + JsonUtils.writeValueAsString((Object)((Object)ruleEO))));
        } else {
            ruleEO.setCreator("system");
            ruleEO.setCreateTime(new Date());
            ruleEO.setUpdateTime(new Date());
            ruleEO.setRuleCode(this.generateRuleCode(ruleEO.getLeafFlag()));
            Integer maxOrder = this.unionRuleDao.findMaxSortOrderByParentId(ruleEO.getParentId());
            maxOrder = maxOrder + 1;
            ruleEO.setSortOrder(maxOrder);
            String id = (String)((Object)this.unionRuleDao.save(ruleEO));
            ruleEO.setId(id);
            LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u89c4\u5219", (String)("\u5bfc\u5165-" + systemName + "\u5408\u5e76\u4f53\u7cfb-" + ruleEO.getTitle() + "\u5408\u5e76\u89c4\u5219"), (String)("\u5bfc\u5165\u65f6\u65b0\u5efa\u89c4\u5219\uff1a\n" + JsonUtils.writeValueAsString((Object)((Object)ruleEO))));
        }
        return UnionRuleVOFactory.newNoSettingInstanceByEntity(ruleEO);
    }

    private void checkNewImportUnionRuleVO(AbstractUnionRule abstractUnionRule) {
        String parentId = abstractUnionRule.getParentId();
        UnionRuleEO parentRule = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)parentId));
        if (Boolean.TRUE.equals(abstractUnionRule.getLeafFlag()) && RuleConst.ROOT_PARENT_ID.equals(parentRule.getParentId())) {
            throw new BusinessRuntimeException("\u7b2c\u4e00\u7ea7\u5206\u7ec4\u4e0d\u5141\u8bb8\u65b0\u5efa\u89c4\u5219\uff01");
        }
        if (parentRule.getLeafFlag() == 1) {
            throw new BusinessRuntimeException("\u89c4\u5219\u4e0b\u4e0d\u5141\u8bb8\u65b0\u5efa\u5206\u7ec4\u6216\u89c4\u5219\uff01");
        }
        if (Boolean.TRUE.equals(abstractUnionRule.getEnableToleranceFlag())) {
            if (StringUtils.isEmpty((String)abstractUnionRule.getOffsetType().getName())) {
                throw new BusinessRuntimeException("\u62b5\u9500\u91d1\u989d\u7b56\u7565\u4e0d\u80fd\u4e3a\u7a7a");
            }
            if (StringUtils.isEmpty((String)abstractUnionRule.getToleranceType().getName())) {
                throw new BusinessRuntimeException("\u5bb9\u5dee\u65b9\u5f0f\u4e0d\u80fd\u4e3a\u7a7a");
            }
        }
        this.checkImportUnionRuleName(abstractUnionRule.getId(), parentId, abstractUnionRule.getTitle());
    }

    private void checkImportUnionRuleName(String currentId, String parentId, String ruleName) {
        if (StringUtils.isEmpty((String)ruleName)) {
            throw new BusinessRuntimeException("\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if ("\u5408\u5e76\u89c4\u5219".equals(ruleName)) {
            throw new BusinessRuntimeException("\u4e0d\u80fd\u7528[\u5408\u5e76\u89c4\u5219]\u4f5c\u4e3a\u540d\u79f0\uff01");
        }
        List<UnionRuleEO> brothers = this.unionRuleDao.findByParentId(parentId);
        brothers.forEach(brother -> {
            if (brother.getTitle().equals(ruleName)) {
                if (brother.getId().equals(currentId)) {
                    return;
                }
                throw new BusinessRuntimeException("\u540c\u4e00\u5206\u7ec4\u4e0b\u540d\u79f0\u4e0d\u80fd\u91cd\u590d\uff01");
            }
        });
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteUnionRule(String id) {
        if (Objects.isNull(id)) {
            throw new BusinessRuntimeException("id \u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        UnionRuleEO ruleEO = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)id));
        if (Objects.nonNull((Object)ruleEO) && ruleEO.getParentId().equals(RuleConst.ROOT_PARENT_ID)) {
            throw new BusinessRuntimeException("\u6839\u8282\u70b9\u4e0d\u53ef\u5220\u9664\uff01");
        }
        UnionRuleVO ruleVO = this.selectUnionRuleAndChildrenById(id, false);
        this.deleteUnionRuleChildrenIfNotLeaf(ruleVO);
        NpContext context = NpContextHolder.getContext();
        this.applicationContext.publishEvent(new UnionRuleChangedEvent(new UnionRuleChangedEvent.UnionRuleChangedInfo(ruleEO.getReportSystem()), context));
    }

    private void deleteUnionRuleChildrenIfNotLeaf(UnionRuleVO ruleVO) {
        int count;
        List children = ruleVO.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            children.stream().forEach(entity -> {
                if (Boolean.valueOf(entity.getLeafFlag()).booleanValue()) {
                    throw new BusinessRuntimeException("\u89c4\u5219\u5206\u7ec4\u4e0b\u6709\u89c4\u5219\uff0c\u8bf7\u5220\u9664\u89c4\u5219\u540e\u518d\u5220\u9664\uff01");
                }
                this.deleteUnionRuleChildrenIfNotLeaf((UnionRuleVO)entity);
            });
        }
        if ((count = this.unionRuleDao.countOffsetEntryByRuleId(ruleVO.getId())) > 0) {
            throw new BusinessRuntimeException("\u8be5\u89c4\u5219\u5b58\u5728\u5173\u8054\u62b5\u6d88\u5206\u5f55\uff0c\u7981\u6b62\u5220\u9664");
        }
        UnionRuleEO eo = new UnionRuleEO();
        eo.setId(ruleVO.getId());
        this.unionRuleDao.delete((BaseEntity)eo);
        String systemName = "";
        ConsolidatedSystemEO consolidatedSystem = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)ruleVO.getReportSystem()));
        if (consolidatedSystem != null) {
            systemName = consolidatedSystem.getSystemName();
        }
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u89c4\u5219", (String)("\u5220\u9664-" + systemName + "\u5408\u5e76\u4f53\u7cfb-" + ruleVO.getTitle() + "\u5408\u5e76\u89c4\u5219"), (String)("\u5220\u9664\uff1a\n" + JsonUtils.writeValueAsString((Object)ruleVO)));
    }

    @Override
    public UnionRuleVO selectUnionRuleAndChildrenById(String id, boolean filterStopped) {
        UnionRuleEO ruleEO = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)id));
        if (Objects.isNull((Object)ruleEO)) {
            throw new BusinessRuntimeException("\u6307\u5b9a\u8282\u70b9\u4e0d\u5b58\u5728\uff01");
        }
        UnionRuleVO parent = UnionRuleVOFactory.newNoSettingInstanceByEntity(ruleEO);
        this.findChildrenRules(parent, filterStopped);
        return parent;
    }

    @Override
    public List<UnionRuleVO> selectUnionRuleChildrenByGroup(String id) {
        HashSet set = Sets.newHashSet();
        this.getChildrenById(set, id);
        if (CollectionUtils.isEmpty(set)) {
            return Collections.emptyList();
        }
        List<UnionRuleVO> ruleVOList = set.stream().map(UnionRuleVOFactory::newNoSettingInstanceByEntity).collect(Collectors.toList());
        return ruleVOList;
    }

    @Override
    public List<UnionRuleEO> selectAllChildrenRuleEo(String parentRuleId) {
        HashSet<UnionRuleEO> set = new HashSet<UnionRuleEO>();
        this.getChildrenById(set, parentRuleId);
        return new ArrayList<UnionRuleEO>(set);
    }

    private void getChildrenById(Set<UnionRuleEO> set, String id) {
        List<UnionRuleEO> children = this.unionRuleDao.findActiveRuleByParentId(id);
        if (CollectionUtils.isEmpty(children)) {
            return;
        }
        for (UnionRuleEO ruleEO : children) {
            set.add(ruleEO);
            this.getChildrenById(set, ruleEO.getId());
        }
    }

    private void findChildrenRules(UnionRuleVO parent, boolean filterStopped) {
        if (Boolean.TRUE.equals(parent.getLeafFlag())) {
            return;
        }
        List<UnionRuleEO> ruleChildren = filterStopped ? this.unionRuleDao.findActiveRuleByParentId(parent.getId()) : this.unionRuleDao.findByParentId(parent.getId());
        if (CollectionUtils.isEmpty(ruleChildren)) {
            return;
        }
        ruleChildren.sort(Comparator.comparing(UnionRuleEO::getSortOrder));
        List children = parent.getChildren();
        for (UnionRuleEO ruleEO : ruleChildren) {
            UnionRuleVO ruleVO = UnionRuleVOFactory.newNoSettingInstanceByEntity(ruleEO);
            children.add(ruleVO);
            this.findChildrenRules(ruleVO, filterStopped);
        }
    }

    @Override
    public UnionRuleVO selectUnionRuleById(String id) {
        if (Objects.isNull(id)) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u89c4\u5219\u53c2\u6570[id]\u4e0d\u80fd\u4e3a\u7a7a\uff01");
        }
        UnionRuleEO ruleEO = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)id));
        if (Objects.isNull((Object)ruleEO)) {
            throw new BusinessRuntimeException("\u67e5\u8be2\u89c4\u5219\u4e0d\u5b58\u5728\uff01");
        }
        return UnionRuleVOFactory.newInstanceByEntity((UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)id)));
    }

    @Override
    public List<UnionRuleVO> selectUnionRuleByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        List<UnionRuleEO> unionRuleEOS = this.unionRuleDao.listRulesByIds(ids);
        if (CollectionUtils.isEmpty(unionRuleEOS)) {
            return Collections.emptyList();
        }
        return unionRuleEOS.stream().map(UnionRuleVOFactory::newNoSettingInstanceByEntity).collect(Collectors.toList());
    }

    @Override
    public AbstractUnionRule selectUnionRuleDTOById(String id) {
        if (null == id) {
            return null;
        }
        UnionRuleEO ruleEO = this.unionRuleDao.findLeafById(id);
        if (Objects.isNull((Object)ruleEO)) {
            return null;
        }
        AbstractUnionRule dto = UnionRuleConverter.convert(ruleEO);
        return dto;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public List<UnionRuleVO> selectRuleTreeByReportSystem(String reportSystemId, boolean filterStopped) {
        UnionRuleEO ruleEO = this.initRoot(reportSystemId);
        UnionRuleVO ruleVO = this.selectUnionRuleAndChildrenById(ruleEO.getId(), filterStopped);
        return Lists.newArrayList((Object[])new UnionRuleVO[]{ruleVO});
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String importRule(String reportSystemId, List<Map<String, Object>> importJsonList) {
        ArrayList<AbstractUnionRule> unionRuleDTOList = new ArrayList<AbstractUnionRule>();
        for (Map<String, Object> importJsonListMap : importJsonList) {
            AbstractUnionRule unionRuleDTO = this.convertMapToDTO(importJsonListMap);
            unionRuleDTOList.add(unionRuleDTO);
        }
        Map<String, List<AbstractUnionRule>> parentMap = unionRuleDTOList.stream().collect(Collectors.groupingBy(AbstractUnionRule::getParentId));
        List<AbstractUnionRule> rootList = parentMap.get(RuleConst.ROOT_PARENT_ID);
        AbstractUnionRule ruleRoot = rootList.get(0);
        List<UnionRuleVO> ruleTree = this.selectRuleTreeByReportSystem(reportSystemId, false);
        String importRootId = ruleRoot.getId();
        List<AbstractUnionRule> importChildren = parentMap.get(importRootId);
        if (com.jiuqi.common.base.util.CollectionUtils.isEmpty(ruleTree)) {
            this.importRule(ruleRoot);
        } else {
            String oldRootId = ruleTree.get(0).getId();
            for (AbstractUnionRule rule : importChildren) {
                rule.setParentId(oldRootId);
            }
        }
        this.cycleCheckChildren(importChildren, parentMap);
        NpContext context = NpContextHolder.getContext();
        this.applicationContext.publishEvent(new UnionRuleChangedEvent(new UnionRuleChangedEvent.UnionRuleChangedInfo(reportSystemId), context));
        return null;
    }

    @Override
    public List<AbstractUnionRule> selectRuleListByReportSystemAndRuleTypes(String reportSystemId, Collection<String> ruleTypes) {
        Assert.notNull((Object)reportSystemId, "\u5408\u5e76\u4f53\u7cfbID\u4e0d\u80fd\u4e3a\u7a7a");
        List<AbstractUnionRule> unionRules = this.unionRuleCacheService.getRulesBySystemId(reportSystemId);
        if (CollectionUtils.isEmpty(unionRules)) {
            return Lists.newArrayList();
        }
        if (CollectionUtils.isEmpty(ruleTypes)) {
            return unionRules;
        }
        return unionRules.stream().filter(entity -> ruleTypes.contains(entity.getRuleType())).collect(Collectors.toList());
    }

    @Override
    public List<AbstractUnionRule> selectRuleListBySchemeIdAndRuleTypes(String schemeId, String periodStr, List<String> ruleTypes) {
        Assert.notNull((Object)schemeId, "schemeId\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)periodStr, "periodStr\u4e0d\u80fd\u4e3a\u7a7a");
        String systemId = this.consolidatedTaskService.getConsolidatedSystemIdBySchemeId(schemeId, periodStr);
        Assert.notNull((Object)systemId, "\u5f53\u524d\u62a5\u8868\u65b9\u6848\u672a\u5173\u8054\u5408\u5e76\u4f53\u7cfb\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u3002");
        return this.selectRuleListByReportSystemAndRuleTypes(systemId, ruleTypes);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void updateNodeName(String id, String title) {
        String parentId = this.unionRuleDao.findParentIdById(id);
        this.checkUnionRuleName(id, parentId, title);
        UnionRuleEO updateEO = new UnionRuleEO();
        updateEO.setId(id);
        updateEO.setTitle(title);
        this.unionRuleDao.updateSelective((BaseEntity)updateEO);
        UnionRuleEO unionRule = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)id));
        NpContext context = NpContextHolder.getContext();
        this.applicationContext.publishEvent(new UnionRuleChangedEvent(new UnionRuleChangedEvent.UnionRuleChangedInfo(unionRule.getReportSystem()), context));
    }

    @Override
    public List<UnionRuleEO> selectRuleEoListByTaskId(String taskId, String periodStr) {
        Assert.notNull((Object)taskId, "taskId\u4e0d\u80fd\u4e3a\u7a7a");
        ConsolidatedTaskVO taskVO = ((ConsolidatedTaskService)SpringContextUtils.getBean(ConsolidatedTaskService.class)).getTaskByTaskKeyAndPeriodStr(taskId, periodStr);
        if (null == taskVO) {
            return new ArrayList<UnionRuleEO>();
        }
        return this.unionRuleDao.findRuleListByReportSystem(taskVO.getSystemId());
    }

    @Override
    public List<UnionRuleVO> selectRuleTreeBySystemId(String systemId) {
        Assert.notNull((Object)systemId, "\u4f53\u7cfbID\u4e0d\u80fd\u4e3a\u7a7a");
        return this.selectRuleTreeByReportSystem(systemId, true);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void stopUnionRule(String id, Boolean startFlag) {
        if (Objects.isNull(id) || Objects.isNull(startFlag)) {
            throw new BusinessRuntimeException("\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        }
        UnionRuleEO unionRule = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)id));
        Integer count = this.unionRuleDao.updateUnionRuleStartFlag(id, startFlag);
        if (Objects.isNull(count) || count < 1) {
            logger.error("\u66f4\u65b0\u89c4\u5219\u72b6\u6001\u5f02\u5e38\uff0c\u89c4\u5219ID\uff1a[{}]\uff0c\u53c2\u6570 startFlag\uff1a[{}]", (Object)id, (Object)startFlag);
            throw new BusinessRuntimeException("\u66f4\u65b0\u89c4\u5219\u72b6\u6001\u5931\u8d25\uff01");
        }
        String systemName = "";
        ConsolidatedSystemEO consolidatedSystem = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)unionRule.getReportSystem()));
        if (consolidatedSystem != null) {
            systemName = consolidatedSystem.getSystemName();
        }
        NpContext context = NpContextHolder.getContext();
        this.applicationContext.publishEvent(new UnionRuleChangedEvent(new UnionRuleChangedEvent.UnionRuleChangedInfo(unionRule.getReportSystem()), context));
        LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u89c4\u5219", (String)((startFlag != false ? "\u542f\u7528-" : "\u505c\u7528-") + systemName + "\u5408\u5e76\u4f53\u7cfb-" + unionRule.getTitle() + "\u5408\u5e76\u89c4\u5219"), (String)"");
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public boolean hasRulesByReportSystemId(String systemId) {
        Assert.notNull((Object)systemId, "\u5408\u5e76\u4f53\u7cfbID\u4e0d\u80fd\u4e3a\u7a7a");
        int count = this.unionRuleDao.countRulesByReportSystemId(systemId);
        if (count > 0) {
            return true;
        }
        logger.info("\u7ea7\u8054\u68c0\u67e5\u901a\u8fc7\uff0c\u5f00\u59cb\u7ea7\u8054\u5220\u9664\u5408\u5e76\u4f53\u7cfb[{}]\u4e0b\u7684\u89c4\u5219\u6811", (Object)systemId);
        this.unionRuleDao.batchDeleteByReportSysId(systemId);
        return false;
    }

    @Override
    public List<UnionRuleVO> selectRuleTreeBySystemIdAndRules(String systemId, String ruleTypes, boolean filterInitRule, GcOrgCacheVO org) {
        Assert.notNull((Object)systemId, "\u4f53\u7cfbID\u4e0d\u80fd\u4e3a\u7a7a");
        UnionRuleEO root = this.initRoot(systemId);
        ArrayList ruleTypeList = Lists.newArrayList();
        if (!StringUtils.isEmpty((String)ruleTypes)) {
            String[] ruleTypeArr = ruleTypes.split(",");
            Collections.addAll(ruleTypeList, ruleTypeArr);
            ruleTypeList.add("group");
        }
        UnionRuleVO ruleVO = UnionRuleVOFactory.newNoSettingInstanceByEntity(root);
        this.i18Convert(ruleVO);
        List<UnionRuleEO> rules = this.unionRuleDao.findByReportSystemIdAndRuleTypesOrGroup(systemId, ruleTypeList);
        if (CollectionUtils.isEmpty(rules)) {
            return Lists.newArrayList((Object[])new UnionRuleVO[]{ruleVO});
        }
        rules = rules.stream().filter(rule -> {
            if (rule.getLeafFlag() == 0 || StringUtils.isEmpty((String)rule.getApplyGcUnits())) {
                return true;
            }
            List applyUnits = Arrays.stream(rule.getApplyGcUnits().split(",")).collect(Collectors.toList());
            return applyUnits.contains(org.getCode());
        }).collect(Collectors.toList());
        this.findChildrenRules(ruleVO, Lists.newArrayList(), rules, filterInitRule);
        return Lists.newArrayList((Object[])new UnionRuleVO[]{ruleVO});
    }

    @Override
    public List<UnionRuleVO> selectRuleTreeByTaskIdAndRules(String taskId, String periodStr, String ruleTypes, boolean filterInitRule, String orgType, String orgCode) {
        Assert.notNull((Object)taskId, "\u4efb\u52a1ID\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)orgType, "\u5408\u5e76\u5355\u4f4d\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a");
        Assert.notNull((Object)orgCode, "\u5408\u5e76\u5355\u4f4d\u4e0d\u80fd\u4e3a\u7a7a");
        String reportSystem = this.consolidatedTaskService.getSystemIdByTaskIdAndPeriodStr(taskId, periodStr);
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO org = tool.getOrgByCode(orgCode);
        Assert.notNull((Object)org, "\u5408\u5e76\u5355\u4f4d" + orgCode + "\u4e0d\u5b58\u5728");
        if (!String.valueOf("9").equals(org.getBblx())) {
            return Collections.emptyList();
        }
        return this.selectRuleTreeBySystemIdAndRules(reportSystem, ruleTypes, filterInitRule, org);
    }

    @Override
    public List<UnionRuleVO> selectInitRuleBySystemIdAndRuleTypes(String systemId, String ruleTypes) {
        Assert.notNull((Object)systemId, "\u4f53\u7cfbID\u4e0d\u80fd\u4e3a\u7a7a");
        ArrayList ruleTypeList = Lists.newArrayList();
        if (!StringUtils.isEmpty((String)ruleTypes)) {
            String[] ruleTypeArr;
            for (String ruleType : ruleTypeArr = ruleTypes.split(",")) {
                ruleTypeList.add(ruleType);
            }
        }
        List<UnionRuleEO> ruleEOList = this.unionRuleDao.findInitRulesBySystem(systemId, ruleTypeList);
        List<UnionRuleVO> tree = this.buildTree(ruleEOList);
        return tree;
    }

    private List<UnionRuleVO> buildTree(List<UnionRuleEO> ruleEOList) {
        if (CollectionUtils.isEmpty(ruleEOList)) {
            return new ArrayList<UnionRuleVO>();
        }
        List<UnionRuleEO> build = this.preBuild(ruleEOList);
        Map<String, List<UnionRuleVO>> parentMap = build.stream().map(UnionRuleVOFactory::newNoSettingInstanceByEntity).collect(Collectors.groupingBy(UnionRuleVO::getParentId));
        List<UnionRuleVO> rootList = parentMap.get(RuleConst.ROOT_PARENT_ID);
        UnionRuleVO root = rootList.get(0);
        ArrayList<UnionRuleVO> list = new ArrayList<UnionRuleVO>();
        List<UnionRuleVO> leve1Group = parentMap.get(root.getId());
        if (!CollectionUtils.isEmpty(leve1Group)) {
            list.addAll(leve1Group);
            for (UnionRuleVO menuVO : leve1Group) {
                this.getChildren(menuVO, parentMap);
            }
        }
        return list;
    }

    private void getChildren(UnionRuleVO ruleVO, Map<String, List<UnionRuleVO>> parentMap) {
        List<UnionRuleVO> ruleVOList = parentMap.get(ruleVO.getId());
        if (!CollectionUtils.isEmpty(ruleVOList)) {
            ruleVO.getChildren().addAll(ruleVOList);
            for (UnionRuleVO vo : ruleVOList) {
                List<UnionRuleVO> leve2Group = parentMap.get(vo.getId());
                if (CollectionUtils.isEmpty(leve2Group)) continue;
                this.getChildren(vo, parentMap);
            }
        }
    }

    private List<UnionRuleEO> preBuild(List<UnionRuleEO> ruleEOList) {
        ArrayList<UnionRuleEO> resultList = new ArrayList<UnionRuleEO>(ruleEOList);
        String lastParentId = null;
        for (UnionRuleEO ruleEO : ruleEOList) {
            if (ruleEO.getParentId().equals(lastParentId)) continue;
            Queue<UnionRuleEO> queue = this.getPathQueue(ruleEO);
            UnionRuleEO parent = queue.poll();
            while (parent != null) {
                if (!resultList.contains((Object)parent)) {
                    resultList.add(parent);
                }
                parent = queue.poll();
            }
            lastParentId = ruleEO.getParentId();
        }
        Collections.sort(resultList, Comparator.comparing(UnionRuleEO::getSortOrder));
        return resultList;
    }

    private Queue<UnionRuleEO> getPathQueue(UnionRuleEO ruleEO) {
        Assert.notNull((Object)ruleEO, "ruleEO \u4e0d\u80fd\u4e3a\u7a7a");
        ArrayDeque<UnionRuleEO> queue = new ArrayDeque<UnionRuleEO>();
        String parentId = ruleEO.getParentId();
        while (Objects.nonNull(parentId) && !RuleConst.ROOT_PARENT_ID.equals(parentId)) {
            UnionRuleEO parent = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)parentId));
            queue.add(parent);
            parentId = parent.getParentId();
        }
        return queue;
    }

    private void findChildrenRules(UnionRuleVO ruleVO, List<UnionRuleVO> parentChildren, List<UnionRuleEO> rules, boolean filterInitRule) {
        if (Boolean.TRUE.equals(ruleVO.getLeafFlag())) {
            return;
        }
        List ruleChildren = rules.stream().filter(rule -> ruleVO.getId().equals(rule.getParentId())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(ruleChildren)) {
            parentChildren.remove(ruleVO);
            return;
        }
        List children = ruleVO.getChildren();
        for (UnionRuleEO ruleEO : ruleChildren) {
            UnionRuleVO child = UnionRuleVOFactory.newNoSettingInstanceByEntity(ruleEO);
            this.i18Convert(child);
            if (Boolean.TRUE.equals(child.getLeafFlag())) {
                if (!filterInitRule || !Boolean.TRUE.equals(child.getInitTypeFlag())) {
                    children.add(child);
                }
            } else {
                children.add(child);
            }
            this.findChildrenRules(child, children, rules, filterInitRule);
        }
        if (CollectionUtils.isEmpty(children)) {
            parentChildren.remove(ruleVO);
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public String moveRuleNode(String id, Integer step) {
        if (Objects.isNull(id) || Objects.isNull(step)) {
            throw new BusinessRuntimeException("\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (step == 0) {
            throw new BusinessRuntimeException("\u53c2\u6570\u9519\u8bef\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        UnionRuleEO ruleEO = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)id));
        if (Objects.isNull((Object)ruleEO)) {
            logger.info("\u79fb\u52a8\u8282\u70b9\uff0c\u6570\u636e\u5f02\u5e38\uff0c\u89c4\u5219\uff1a[{}] \u4e0d\u5b58\u5728", (Object)id);
            throw new BusinessRuntimeException("\u6570\u636e\u4e0d\u5b58\u5728\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        UnionRuleEO node = null;
        if (step > 0) {
            node = this.unionRuleDao.findPreNodeByParentIdAndOrder(ruleEO.getParentId(), ruleEO.getSortOrder());
        } else if (step < 0) {
            node = this.unionRuleDao.findNextNodeByParentIdAndOrder(ruleEO.getParentId(), ruleEO.getSortOrder());
        }
        if (Objects.isNull(node)) {
            throw new BusinessRuntimeException("\u5df2\u5230\u8fbe\u6307\u5b9a\u4f4d\u7f6e");
        }
        Integer temp = ruleEO.getSortOrder();
        ruleEO.setSortOrder(node.getSortOrder());
        node.setSortOrder(temp);
        this.unionRuleDao.update((BaseEntity)ruleEO);
        this.unionRuleDao.update((BaseEntity)node);
        return node.getId();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void dragRuleNode(DragRuleVO dragRuleVO) {
        UnionRuleEO unionRuleEO;
        String targetNodeBefore = "before";
        List<UnionRuleEO> allNodeBetweenDragTargetAndDraggingList = null;
        UnionRuleVO draggingNodeRuleVO = dragRuleVO.getDraggingNode();
        UnionRuleVO dragTargetNodeRuleVO = dragRuleVO.getDragPositionNode();
        String dragNodeUpAndDown = dragRuleVO.getDragNodeUpAndDown();
        UnionRuleEO draggingNodeRuleEO = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)draggingNodeRuleVO.getId()));
        UnionRuleEO dragTargetNodeRuleEO = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)dragTargetNodeRuleVO.getId()));
        String parentId = dragTargetNodeRuleEO.getParentId();
        int draggingSortOrder = draggingNodeRuleEO.getSortOrder();
        int dragTargetSortOrder = dragTargetNodeRuleEO.getSortOrder();
        int dragSortOrderSubscript = draggingSortOrder;
        if (draggingSortOrder > dragTargetSortOrder) {
            int dragSortOrderSuperscript = targetNodeBefore.equals(dragNodeUpAndDown) ? dragTargetSortOrder : dragTargetSortOrder + 1;
            allNodeBetweenDragTargetAndDraggingList = this.unionRuleDao.findBetweenDragTargetAndDraggingByParentIdAndSortOrder(parentId, dragSortOrderSuperscript, dragSortOrderSubscript);
            unionRuleEO = allNodeBetweenDragTargetAndDraggingList.get(allNodeBetweenDragTargetAndDraggingList.size() - 1);
            unionRuleEO.setSortOrder(dragSortOrderSuperscript);
            this.drag(allNodeBetweenDragTargetAndDraggingList.subList(0, allNodeBetweenDragTargetAndDraggingList.size() - 1), sortOrder -> sortOrder + 1);
        } else {
            int dragSortOrderSuperscript = targetNodeBefore.equals(dragNodeUpAndDown) ? dragTargetSortOrder - 1 : dragTargetSortOrder;
            allNodeBetweenDragTargetAndDraggingList = this.unionRuleDao.findBetweenDragTargetAndDraggingByParentIdAndSortOrder(parentId, dragSortOrderSubscript, dragSortOrderSuperscript);
            unionRuleEO = allNodeBetweenDragTargetAndDraggingList.get(0);
            unionRuleEO.setSortOrder(dragSortOrderSuperscript);
            this.drag(allNodeBetweenDragTargetAndDraggingList.subList(1, allNodeBetweenDragTargetAndDraggingList.size()), sortOrder -> sortOrder - 1);
        }
        this.unionRuleDao.updateSortOrder(allNodeBetweenDragTargetAndDraggingList);
        NpContext context = NpContextHolder.getContext();
        this.applicationContext.publishEvent(new UnionRuleChangedEvent(new UnionRuleChangedEvent.UnionRuleChangedInfo(unionRuleEO.getReportSystem()), context));
    }

    void drag(List<UnionRuleEO> allNodeBetweenDragTargetAndDraggingList, Function<Integer, Integer> sortOrderCalcFunc) {
        allNodeBetweenDragTargetAndDraggingList.forEach(unionRuleEO -> unionRuleEO.setSortOrder((Integer)sortOrderCalcFunc.apply(unionRuleEO.getSortOrder())));
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public UnionRuleVO cutOrPaste(String id, String parentId, String action) {
        if (Objects.isNull(id) || Objects.isNull(parentId)) {
            throw new BusinessRuntimeException("\u89c4\u5219\u6216\u5206\u7ec4\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (!StringUtils.equalsIgnoreCaseAny((String)action, (String[])new String[]{"cut", "copy"})) {
            throw new BusinessRuntimeException("\u53c2\u6570\u6709\u8bef");
        }
        UnionRuleEO ruleEO = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)id));
        if (Objects.isNull((Object)ruleEO)) {
            throw new BusinessRuntimeException("\u5f85\u79fb\u52a8\u89c4\u5219\u6216\u5206\u7ec4\u6570\u636e\u4e22\u5931\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        UnionRuleEO parentRule = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)parentId));
        if (Objects.isNull((Object)parentRule)) {
            throw new BusinessRuntimeException("\u76ee\u6807\u5206\u7ec4\u4e22\u5931\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5");
        }
        if (!parentRule.getReportSystem().equals(ruleEO.getReportSystem())) {
            if ("cut".equalsIgnoreCase(action)) {
                throw new BusinessRuntimeException("\u8de8\u4f53\u7cfb\u4e0d\u652f\u6301\u526a\u5207");
            }
            ruleEO.setReportSystem(parentRule.getReportSystem());
        }
        String lastParentId = ruleEO.getParentId();
        ruleEO.setParentId(parentId);
        ruleEO.setRuleCode(this.generateRuleCode(ruleEO.getLeafFlag()));
        Integer maxOrder = this.unionRuleDao.findMaxSortOrderByParentId(parentId);
        maxOrder = maxOrder + 1;
        ruleEO.setSortOrder(maxOrder);
        String newTitle = "cut".equalsIgnoreCase(action) ? ruleEO.getTitle() : ruleEO.getTitle() + " Copy";
        ruleEO.setTitle(newTitle);
        this.checkUnionRuleName(id, parentId, newTitle);
        String systemName = "";
        ConsolidatedSystemEO consolidatedSystem = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)ruleEO.getReportSystem()));
        if (consolidatedSystem != null) {
            systemName = consolidatedSystem.getSystemName();
        }
        if ("cut".equalsIgnoreCase(action)) {
            if (lastParentId.equals(parentId)) {
                throw new BusinessRuntimeException("\u8bf7\u526a\u5207\u5230\u4e0d\u540c\u5206\u7ec4\u4e0b");
            }
            this.unionRuleDao.update((BaseEntity)ruleEO);
            LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u89c4\u5219", (String)("\u7c98\u8d34" + systemName + "\u5408\u5e76\u4f53\u7cfb-" + ruleEO.getTitle() + "\u5408\u5e76\u89c4\u5219"), (String)("\u526a\u5207\u7c98\u8d34\u7684\u89c4\u5219\uff1a\n" + JsonUtils.writeValueAsString((Object)((Object)ruleEO))));
        } else if ("copy".equalsIgnoreCase(action)) {
            ruleEO.setId(null);
            ruleEO.setCreateTime(new Date());
            ruleEO.setUpdateTime(new Date());
            UnionRuleManager ruleManager = UnionRuleUtils.getManagerByRuleTypeCode(ruleEO.getRuleType());
            ruleEO.setJsonString(ruleManager.getRuleHandler().reorganizeJsonStringWhenCopy(ruleEO.getJsonString()));
            String newId = (String)((Object)this.unionRuleDao.save(ruleEO));
            LogHelper.info((String)"\u5408\u5e76-\u5408\u5e76\u89c4\u5219", (String)("\u7c98\u8d34" + systemName + "\u5408\u5e76\u4f53\u7cfb-" + ruleEO.getTitle() + "\u5408\u5e76\u89c4\u5219"), (String)("\u7c98\u8d34\u7684\u89c4\u5219\uff1a\n" + JsonUtils.writeValueAsString((Object)((Object)ruleEO))));
            ruleEO.setId(newId);
        }
        NpContext context = NpContextHolder.getContext();
        this.applicationContext.publishEvent(new UnionRuleChangedEvent(new UnionRuleChangedEvent.UnionRuleChangedInfo(ruleEO.getReportSystem()), context));
        return UnionRuleVOFactory.newNoSettingInstanceByEntity(ruleEO);
    }

    @Override
    public Stack<UnionRuleVO> getParentsByChildId(String childId) {
        if (Objects.isNull(childId)) {
            throw new BusinessRuntimeException("\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a");
        }
        UnionRuleEO ruleEO = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)childId));
        Stack<UnionRuleVO> parentStack = new Stack<UnionRuleVO>();
        this.getParent(UnionRuleVOFactory.newNoSettingInstanceByEntity(ruleEO), parentStack);
        return parentStack;
    }

    private void getParent(UnionRuleVO ruleVO, Stack<UnionRuleVO> stack) {
        stack.push(ruleVO);
        if (ruleVO.getParentId().equals(RuleConst.ROOT_PARENT_ID)) {
            return;
        }
        UnionRuleEO ruleEO = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)ruleVO.getParentId()));
        this.getParent(UnionRuleVOFactory.newNoSettingInstanceByEntity(ruleEO), stack);
    }

    private UnionRuleEO initRoot(String reportSystem) {
        List<UnionRuleEO> ruleNodes = this.unionRuleDao.findByParentIdAndReportSystem(RuleConst.ROOT_PARENT_ID, reportSystem);
        UnionRuleEO root = null;
        if (!CollectionUtils.isEmpty(ruleNodes)) {
            root = ruleNodes.get(0);
        } else {
            root = new UnionRuleEO();
            root.setParentId(RuleConst.ROOT_PARENT_ID);
            root.setTitle("\u5408\u5e76\u89c4\u5219");
            root.setReportSystem(reportSystem);
            root.setSortOrder(0);
            root.setLeafFlag(0);
            root.setStartFlag(1);
            root.setInitTypeFlag(0);
            root.setCreator("system");
            root.setRuleType("root");
            root.setCreateTime(new Date());
            root.setUpdateTime(new Date());
            String rootId = (String)((Object)this.unionRuleDao.save(root));
            String systemName = "";
            ConsolidatedSystemEO consolidatedSystem = (ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)reportSystem));
            if (consolidatedSystem != null) {
                systemName = consolidatedSystem.getSystemName();
            }
            root.setId(rootId);
            if (logger.isDebugEnabled()) {
                logger.debug("\u521b\u5efa\u5408\u5e76\u4f53\u7cfb\uff1a[{}] \u5408\u5e76\u89c4\u5219\u6811\uff0c\u6839\u8282\u70b9ID\uff1a[{}]", (Object)reportSystem, (Object)rootId);
            }
        }
        return root;
    }

    @Override
    public List<AbstractUnionRule> selectUnionRuleDTOByIdList(Collection<String> ids) {
        Assert.notEmpty(ids, "\u89c4\u5219id\u4e0d\u80fd\u4e3a\u7a7a");
        List<Object> ruleList = Lists.newArrayList();
        if (ids.size() == 1) {
            AbstractUnionRule rule = this.selectUnionRuleDTOById(ids.iterator().next());
            if (rule != null) {
                ruleList.add(rule);
            }
        } else {
            List<UnionRuleEO> ruleEOList = this.unionRuleDao.findLeafByIdList(ids);
            ruleList = ruleEOList.stream().map(entity -> UnionRuleConverter.convert(entity)).collect(Collectors.toList());
        }
        return ruleList;
    }

    @Override
    public List<SelectOptionVO> getManagementDimensionVOByReportSystem(String reportSystem) {
        return ((ManagementDimensionCacheService)SpringContextUtils.getBean(ManagementDimensionCacheService.class)).getManagementDimsBySystemId(reportSystem).stream().map(dim -> {
            SelectOptionVO selectOptionVO = new SelectOptionVO();
            selectOptionVO.setValue((Object)dim.getCode());
            selectOptionVO.setLabel(dim.getTitle());
            return selectOptionVO;
        }).collect(Collectors.toList());
    }

    @Override
    public Map<String, List<String>> getFilterRepeatFetchSubject(Map<String, Object> fetchSubjectMap) {
        HashMap<String, List<String>> fetchSubject = new HashMap<String, List<String>>();
        String tbName = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)fetchSubjectMap.get("tbName")), String.class);
        String reportSystem = (String)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)fetchSubjectMap.get("reportSystem")), String.class);
        List debitItemList = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)fetchSubjectMap.get("debitItemList")), (TypeReference)new TypeReference<List<GcBaseDataVO>>(){});
        List creditItemList = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString((Object)fetchSubjectMap.get("creditItemList")), (TypeReference)new TypeReference<List<GcBaseDataVO>>(){});
        List<String> debitItemSelectedCodeList = debitItemList.stream().map(GcBaseDataVO::getCode).distinct().collect(Collectors.toList());
        List<String> creditItemSelectedCodeList = creditItemList.stream().map(GcBaseDataVO::getCode).distinct().collect(Collectors.toList());
        fetchSubject.put("debitItemList", new ArrayList<String>(this.consolidatedSubjectService.filterByExcludeChild(reportSystem, debitItemSelectedCodeList)));
        fetchSubject.put("creditItemList", new ArrayList<String>(this.consolidatedSubjectService.filterByExcludeChild(reportSystem, creditItemSelectedCodeList)));
        return fetchSubject;
    }

    @Override
    public List<BaseRuleVO> findAllRuleTitles(String systemId) {
        List<UnionRuleEO> unionRuleEOs = this.unionRuleDao.findAllRuleTitles(systemId);
        return unionRuleEOs.stream().map(item -> {
            BaseRuleVO baseRuleVO = new BaseRuleVO();
            BeanUtils.copyProperties(item, baseRuleVO);
            return baseRuleVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<String> getDataSourceSubject(String ruleId) {
        UnionRuleVO selectRule = this.selectUnionRuleById(ruleId);
        String jsonString = selectRule.getJsonString();
        Map mapJson = (Map)JsonUtils.readValue((String)jsonString, (TypeReference)new TypeReference<Map<String, Object>>(){});
        List debitItemList = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(mapJson.get("debitItemList")), (TypeReference)new TypeReference<List<BaseDataVO>>(){});
        List creditItemList = (List)JsonUtils.readValue((String)JsonUtils.writeValueAsString(mapJson.get("creditItemList")), (TypeReference)new TypeReference<List<BaseDataVO>>(){});
        List<String> debitItemSelectedCodeList = debitItemList.stream().map(BaseDataVO::getCode).distinct().collect(Collectors.toList());
        List<String> creditItemSelectedCodeList = creditItemList.stream().map(BaseDataVO::getCode).distinct().collect(Collectors.toList());
        ConsolidatedSubjectService consolidatedSubjectService = (ConsolidatedSubjectService)SpringContextUtils.getBean(ConsolidatedSubjectService.class);
        ArrayList<String> DataSourceSubjectCode = new ArrayList<String>(consolidatedSubjectService.filterByExcludeChild(selectRule.getReportSystem(), debitItemSelectedCodeList));
        DataSourceSubjectCode.addAll(consolidatedSubjectService.filterByExcludeChild(selectRule.getReportSystem(), creditItemSelectedCodeList));
        return DataSourceSubjectCode;
    }

    @Override
    public List<SelectFloatLineOptionTreeVO> getFloatLineRuleOption(String reportSystem) {
        ArrayList<SelectFloatLineOptionTreeVO> SelectFloatLineOptionTree = new ArrayList<SelectFloatLineOptionTreeVO>();
        HashMap filterSameTaskMap = new HashMap();
        List<ConsolidatedTaskVO> consolidatedTasks = this.consolidatedTaskService.getConsolidatedTasks(reportSystem);
        consolidatedTasks.forEach(consolidatedTask -> {
            List<String> allInputSchemeList = ConsolidatedSystemUtils.listAllInputSchemeByConTaskVO(consolidatedTask);
            if (CollectionUtils.isEmpty(allInputSchemeList)) {
                return;
            }
            allInputSchemeList.forEach(schemeId -> {
                FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(schemeId);
                List formGroupDefines = this.iRunTimeViewController.getAllFormGroupsInFormScheme(schemeId);
                if (!CollectionUtils.isEmpty(formGroupDefines)) {
                    SelectFloatLineOptionTreeVO selectTaskVO = new SelectFloatLineOptionTreeVO();
                    if (!filterSameTaskMap.containsKey(consolidatedTask.getTaskKey())) {
                        SelectFloatLineOptionTree.add(selectTaskVO);
                        filterSameTaskMap.put(consolidatedTask.getTaskKey(), SelectFloatLineOptionTree);
                    } else {
                        ((List)filterSameTaskMap.get(consolidatedTask.getTaskKey())).add(selectTaskVO);
                    }
                    ArrayList<SelectFloatLineOptionTreeVO> selectInputSchemeList = new ArrayList<SelectFloatLineOptionTreeVO>();
                    selectTaskVO.setValue((Object)consolidatedTask.getTaskKey());
                    selectTaskVO.setLabel(consolidatedTask.getTaskTitle());
                    selectTaskVO.setChildren(selectInputSchemeList);
                    ArrayList<SelectFloatLineOptionTreeVO> selectFormGroupList = new ArrayList<SelectFloatLineOptionTreeVO>();
                    this.getFormGroupByInputScheme(formGroupDefines, selectFormGroupList);
                    if (!CollectionUtils.isEmpty(selectFormGroupList)) {
                        SelectFloatLineOptionTreeVO selectInputSchemeVO = new SelectFloatLineOptionTreeVO();
                        selectInputSchemeVO.setLabel(formScheme.getTitle());
                        selectInputSchemeVO.setValue((Object)formScheme.getKey());
                        selectInputSchemeVO.setChildren(selectFormGroupList);
                        selectInputSchemeList.add(selectInputSchemeVO);
                    }
                }
            });
        });
        return SelectFloatLineOptionTree;
    }

    public void getFormGroupByInputScheme(List<FormGroupDefine> formGroupDefines, List<SelectFloatLineOptionTreeVO> selectFormGroupList) {
        formGroupDefines.forEach(formGroupDefine -> {
            try {
                List formDefines = this.iRunTimeViewController.getAllFormsInGroup(formGroupDefine.getKey());
                if (!CollectionUtils.isEmpty(formDefines)) {
                    ArrayList<SelectFloatLineOptionTreeVO> selectFormDefineList = new ArrayList<SelectFloatLineOptionTreeVO>();
                    this.getFormDefineByFormGroup(formDefines, selectFormDefineList);
                    if (!CollectionUtils.isEmpty(selectFormDefineList)) {
                        SelectFloatLineOptionTreeVO selectFormGroupVO = new SelectFloatLineOptionTreeVO();
                        selectFormGroupVO.setValue((Object)formGroupDefine.getKey());
                        selectFormGroupVO.setLabel(formGroupDefine.getTitle());
                        selectFormGroupVO.setChildren(selectFormDefineList);
                        selectFormGroupList.add(selectFormGroupVO);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getFormDefineByFormGroup(List<FormDefine> formDefines, List<SelectFloatLineOptionTreeVO> selectFormDefineList) {
        try {
            formDefines.forEach(formDefine -> {
                List<DataRegionDefine> dataRegionDefines = this.filterDataRegionDefine(this.iRunTimeViewController.getAllRegionsInForm(formDefine.getKey()));
                if (!CollectionUtils.isEmpty(dataRegionDefines)) {
                    ArrayList<SelectFloatLineOptionTreeVO> selectDataRegionList = new ArrayList<SelectFloatLineOptionTreeVO>();
                    this.getDataRegionDefineByFormDefine(dataRegionDefines, selectDataRegionList);
                    if (!CollectionUtils.isEmpty(selectDataRegionList)) {
                        SelectFloatLineOptionTreeVO selectFormDefineVO = new SelectFloatLineOptionTreeVO();
                        selectFormDefineVO.setValue((Object)formDefine.getKey());
                        selectFormDefineVO.setLabel(formDefine.getTitle());
                        selectFormDefineVO.setChildren(selectDataRegionList);
                        selectFormDefineList.add(selectFormDefineVO);
                    }
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDataRegionDefineByFormDefine(List<DataRegionDefine> dataRegionDefines, List<SelectFloatLineOptionTreeVO> selectDataRegionList) {
        dataRegionDefines.forEach(dataRegionDefine -> {
            List fieldKeys = this.iRunTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
            DataField fieldDefine = this.runtimeDataSchemeService.getDataField((String)fieldKeys.get(0));
            try {
                DataTable tableDefine = this.runtimeDataSchemeService.getDataTable(fieldDefine.getDataTableKey());
                SelectFloatLineOptionTreeVO selectDataRegionVO = new SelectFloatLineOptionTreeVO();
                selectDataRegionVO.setValue((Object)dataRegionDefine.getKey());
                selectDataRegionVO.setLabel(tableDefine.getCode() + "|" + tableDefine.getTitle());
                selectDataRegionVO.setCode(tableDefine.getCode());
                selectDataRegionList.add(selectDataRegionVO);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u67e5\u8be2\u8868\u5b9a\u4e49\u5f02\u5e38", (Throwable)e);
            }
        });
        Collections.sort(selectDataRegionList, new Comparator<SelectFloatLineOptionTreeVO>(){

            @Override
            public int compare(SelectFloatLineOptionTreeVO selectFloatLine1, SelectFloatLineOptionTreeVO selectFloatLine2) {
                String[] selectFloatLineStr1 = selectFloatLine1.getCode().split("_");
                String[] selectFloatLineStr2 = selectFloatLine2.getCode().split("_");
                String orderStr1 = selectFloatLineStr1[selectFloatLineStr1.length - 1].substring(0).matches("^[0-9]*$") ? selectFloatLineStr1[selectFloatLineStr1.length - 1].substring(0) : "99";
                String orderStr2 = selectFloatLineStr2[selectFloatLineStr2.length - 1].substring(0).matches("^[0-9]*$") ? selectFloatLineStr2[selectFloatLineStr2.length - 1].substring(0) : "99";
                return Integer.parseInt(orderStr1) - Integer.parseInt(orderStr2);
            }
        });
    }

    public List<DataRegionDefine> filterDataRegionDefine(List<DataRegionDefine> dataRegionDefines) {
        ArrayList<DataRegionDefine> dataRegionDefinesList = new ArrayList<DataRegionDefine>();
        for (DataRegionDefine dataRegionDefine : dataRegionDefines) {
            if (dataRegionDefine.getRegionKind().equals((Object)DataRegionKind.DATA_REGION_SIMPLE)) continue;
            List fieldKeys = this.iRunTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
            boolean inputDataTable = false;
            for (DataFieldDeployInfo fieldDeployInfo : this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(fieldKeys.toArray(new String[0]))) {
                if (Objects.isNull(fieldDeployInfo) || !fieldDeployInfo.getTableName().contains("GC_INPUTDATA")) continue;
                inputDataTable = true;
                break;
            }
            if (inputDataTable) continue;
            dataRegionDefinesList.add(dataRegionDefine);
        }
        return dataRegionDefinesList;
    }

    @Override
    public List<SelectOptionVO> getDataRegionFieldOption(String dataRegionKey) {
        if (StringUtils.isEmpty((String)dataRegionKey)) {
            return Collections.emptyList();
        }
        ArrayList<SelectOptionVO> SelectFloatLineOption = new ArrayList<SelectOptionVO>();
        List dataLinkDefines = this.iRunTimeViewController.getAllLinksInRegion(dataRegionKey);
        dataLinkDefines.forEach(dataLinkDefine -> {
            try {
                DataField fieldDefine = this.runtimeDataSchemeService.getDataField(dataLinkDefine.getLinkExpression());
                SelectOptionVO selectOptionVO = new SelectOptionVO();
                selectOptionVO.setValue((Object)fieldDefine.getKey());
                selectOptionVO.setLabel(fieldDefine.getTitle());
                SelectFloatLineOption.add(selectOptionVO);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u67e5\u8be2\u5b57\u6bb5\u5b9a\u4e49\u5f02\u5e38", (Throwable)e);
            }
        });
        return SelectFloatLineOption;
    }

    @Override
    public Map<String, Object> getChangeRatioOption(String reportSystem) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        ConsolidatedOptionService conOptionService = (ConsolidatedOptionService)SpringContextUtils.getBean(ConsolidatedOptionService.class);
        ConsolidatedOptionVO consolidatedOption = conOptionService.getOptionData(reportSystem);
        if (consolidatedOption == null || Objects.isNull(consolidatedOption.getMonthlyIncrement())) {
            map.put("monthlyIncrement", false);
        }
        map.put("monthlyIncrement", consolidatedOption.getMonthlyIncrement());
        List changeRatios = GcBaseDataCenterTool.getInstance().queryBasedataItemsVO("MD_CHANGERATIO");
        if (CollectionUtils.isEmpty(changeRatios)) {
            map.put("changeRatios", new ArrayList());
        }
        ArrayList selectOptions = new ArrayList();
        changeRatios.forEach(changeRatio -> {
            SelectOptionVO selectOptionVO = new SelectOptionVO();
            selectOptionVO.setValue((Object)changeRatio.getCode());
            selectOptionVO.setLabel(changeRatio.getTitle());
            selectOptions.add(selectOptionVO);
        });
        map.put("changeRatios", selectOptions);
        return map;
    }

    @Override
    public List<ITree<UnionRuleVO>> listRuleTree(Map<String, Object> param) {
        this.checkParam(param);
        String reportSystemId = String.valueOf(param.get("reportSystem"));
        List<UnionRuleEO> allRules = this.unionRuleDao.findByParentIdAndReportSystem(RuleConst.ROOT_PARENT_ID, reportSystemId);
        if (CollectionUtils.isEmpty(allRules)) {
            return Collections.emptyList();
        }
        UnionRuleEO rootRule = allRules.get(0);
        UnionRuleVO rootRuleVO = UnionRuleVOFactory.newNoSettingInstanceByEntity(rootRule);
        this.i18Convert(rootRuleVO);
        ITree rootNode = new ITree(rootRuleVO);
        this.findChildrenRulesWithFilter(rootRule, (ITree<UnionRuleVO>)rootNode, param);
        return Lists.newArrayList((Object[])new ITree[]{rootNode});
    }

    private void i18Convert(UnionRuleVO rule) {
        String message = this.i18Provider.getMessage(rule.getId());
        if (!StringUtils.isEmpty((String)message)) {
            rule.setTitle(message);
        }
    }

    private void findChildrenRulesWithFilter(UnionRuleEO parent, ITree<UnionRuleVO> parentVO, Map<String, Object> param) {
        if (parent.getLeafFlag() == 1 && param.containsKey("ruleHasChild") && ((Boolean)param.get("ruleHasChild")).booleanValue()) {
            UnionRuleManager managerByRuleTypeCode = UnionRuleUtils.getManagerByRuleTypeCode(parent.getRuleType());
            if (Objects.isNull(managerByRuleTypeCode)) {
                return;
            }
            List<UnionRuleVO> unionRuleChildNodes = managerByRuleTypeCode.getRuleHandler().getUnionRuleChildNodes(parent);
            if (!CollectionUtils.isEmpty(unionRuleChildNodes)) {
                parentVO.setLeaf(false);
                unionRuleChildNodes.stream().forEach(v -> {
                    v.setLeafFlag(Boolean.valueOf(true));
                    this.i18Convert((UnionRuleVO)v);
                    parentVO.appendChild(v);
                });
            }
            return;
        }
        List<UnionRuleEO> children = this.unionRuleDao.findByParentId(parent.getId());
        if (CollectionUtils.isEmpty(children)) {
            parentVO.setLeaf(true);
            return;
        }
        List<UnionRuleEO> childRuleSByFilter = children.stream().filter(rule -> this.filterRule((UnionRuleEO)((Object)rule), param)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(childRuleSByFilter)) {
            parentVO.setLeaf(true);
            return;
        }
        childRuleSByFilter.forEach(child -> {
            UnionRuleVO unionRuleVO = UnionRuleVOFactory.newNoSettingInstanceByEntity(child);
            this.i18Convert(unionRuleVO);
            ITree ruleVO = new ITree(unionRuleVO);
            this.findChildrenRulesWithFilter((UnionRuleEO)((Object)child), (ITree<UnionRuleVO>)ruleVO, param);
            if (!ruleVO.hasChildren() && child.getLeafFlag() == 0 && param.containsKey("filterEmptyGroup") && ((Boolean)param.get("filterEmptyGroup")).booleanValue()) {
                return;
            }
            parentVO.appendChild(ruleVO);
        });
    }

    private void checkParam(Map<String, Object> param) {
        String periodStr;
        String orgCode;
        if (Objects.isNull(param.get("reportSystem"))) {
            throw new BusinessRuntimeException("\u5408\u5e76\u4f53\u7cfb\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String string = orgCode = Objects.isNull(param.get("orgCode")) ? null : param.get("orgCode").toString();
        if (StringUtils.isEmpty((String)orgCode)) {
            return;
        }
        String orgType = Objects.isNull(param.get("orgType")) ? null : param.get("orgType").toString();
        String string2 = periodStr = Objects.isNull(param.get("periodStr")) ? null : param.get("periodStr").toString();
        if (!UnionRuleServiceImpl.allNonEmpty(orgType, periodStr)) {
            throw new BusinessRuntimeException("\u5408\u5e76\u5355\u4f4d\u4ee3\u7801\u3010orgCode\u3011\u6709\u503c\u7684\u60c5\u51b5\u4e0b\u5408\u5e76\u5355\u4f4d\u7c7b\u578b\u3010orgType\u3011\u3001\u65f6\u671f\u3010periodStr\u3011\u4e0d\u80fd\u4e3a\u7a7a");
        }
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService tool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO org = tool.getOrgByCode(orgCode);
        Assert.notNull((Object)org, "\u5408\u5e76\u5355\u4f4d" + orgCode + "\u4e0d\u5b58\u5728");
        param.put("org", org);
    }

    private static boolean allNonEmpty(String ... values) {
        for (String value : values) {
            if (value != null && !value.trim().isEmpty()) continue;
            return false;
        }
        return true;
    }

    private static boolean allEmpty(String ... values) {
        for (String value : values) {
            if (value == null || value.trim().isEmpty()) continue;
            return false;
        }
        return true;
    }

    private boolean filterRule(UnionRuleEO rule, Map<String, Object> param) {
        ArrayList ruleIds;
        ArrayList ruleTypes;
        if (param.containsKey("org")) {
            List applyUnits;
            String applyGcUnits;
            GcOrgCacheVO org = (GcOrgCacheVO)param.get("org");
            if (String.valueOf("9").equals(org.getBblx()) && !StringUtils.isEmpty((String)(applyGcUnits = rule.getApplyGcUnits())) && rule.getLeafFlag() == 1 && !(applyUnits = Arrays.stream(applyGcUnits.split(",")).collect(Collectors.toList())).contains(org.getCode())) {
                return false;
            }
        }
        if (param.containsKey("isActive") && rule.getLeafFlag() == 1 && ((Boolean)param.get("isActive") != false ? Objects.equals(0, rule.getStartFlag()) : (Boolean)param.get("isActive") == false && Objects.equals(1, rule.getStartFlag()))) {
            return false;
        }
        if (param.containsKey("isInit") && rule.getLeafFlag() == 1 && ((Boolean)param.get("isInit") != false ? Objects.equals(0, rule.getInitTypeFlag()) : (Boolean)param.get("isInit") == false && Objects.equals(1, rule.getInitTypeFlag()))) {
            return false;
        }
        if (param.containsKey("ruleTypes") && rule.getLeafFlag() == 1 && !CollectionUtils.isEmpty((List)param.get("ruleTypes")) && !(ruleTypes = (ArrayList)param.get("ruleTypes")).contains(rule.getRuleType())) {
            return false;
        }
        if (param.containsKey("ruleIds") && rule.getLeafFlag() == 1 && !CollectionUtils.isEmpty((List)param.get("ruleIds")) && !(ruleIds = (ArrayList)param.get("ruleIds")).contains(rule.getId())) {
            return false;
        }
        if (rule.getLeafFlag() == 1) {
            UnionRuleManager manager = this.ruleManagerFactory.getUnionRuleManager(rule.getRuleType());
            if (Objects.isNull(manager)) {
                return false;
            }
            if (!manager.getRuleHandler().filterRuleByParams(rule, param)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<String> findAllRuleIdsBySystemIdAndRuleTypes(String systemId, Collection<String> ruleTypes) {
        if (CollectionUtils.isEmpty(ruleTypes)) {
            return Collections.emptyList();
        }
        return this.unionRuleDao.findAllRuleIdsBySystemIdAndRuleTypes(systemId, ruleTypes);
    }

    @Override
    public Set<String> findRuleIdsByRuleType(String systemId, String ruleType) {
        return this.unionRuleDao.findRuleIdsByRuleType(systemId, ruleType);
    }

    @Override
    public String selectUnionRuleTitleById(String id) {
        if (Objects.isNull(id)) {
            return "";
        }
        UnionRuleEO ruleEO = (UnionRuleEO)this.unionRuleDao.get((Serializable)((Object)id));
        if (null == ruleEO) {
            return "";
        }
        return ruleEO.getTitle();
    }

    @Override
    public List<AbstractUnionRule> selectAllRuleListByReportSystemAndRuleTypes(String reportSystemId, Collection<String> ruleTypes) {
        Assert.notNull((Object)reportSystemId, "\u5408\u5e76\u4f53\u7cfbID\u4e0d\u80fd\u4e3a\u7a7a");
        List<UnionRuleEO> unionRules = this.unionRuleDao.findAllRuleListByReportSystem(reportSystemId);
        if (CollectionUtils.isEmpty(unionRules)) {
            return Lists.newArrayList();
        }
        if (CollectionUtils.isEmpty(ruleTypes)) {
            return unionRules.stream().filter(rule -> Objects.equals(1, rule.getStartFlag())).map(UnionRuleConverter::convert).collect(Collectors.toList());
        }
        return unionRules.stream().filter(entity -> ruleTypes.contains(entity.getRuleType())).map(UnionRuleConverter::convert).collect(Collectors.toList());
    }

    public AbstractUnionRule convertMapToDTO(Map<String, Object> importJsonListMap) {
        if (!Boolean.TRUE.equals(importJsonListMap.get("leafFlag"))) {
            return (AbstractUnionRule)JsonUtils.readValue((String)JsonUtils.writeValueAsString(importJsonListMap), UnionGroupRuleDTO.class);
        }
        Object ruleType = importJsonListMap.get("ruleType");
        UnionRuleManager ruleManager = UnionRuleUtils.getManagerByRuleTypeCode(String.valueOf(ruleType));
        return ruleManager.getRuleHandler().convertMapToDTO(importJsonListMap);
    }

    private void cycleCheckChildren(List<AbstractUnionRule> importChildren, Map<String, List<AbstractUnionRule>> parentMap) {
        if (com.jiuqi.common.base.util.CollectionUtils.isEmpty(importChildren)) {
            return;
        }
        for (AbstractUnionRule ruleDTO : importChildren) {
            String impId = ruleDTO.getId();
            UnionRuleEO ruleEO = UnionRuleConverter.convertUnionRuleDTOToEO(ruleDTO);
            BeanUtils.copyProperties(ruleDTO, (Object)ruleEO);
            ruleEO.setLeafFlag(Boolean.TRUE.equals(ruleDTO.getLeafFlag()) ? 1 : 0);
            ruleEO.setStartFlag(Boolean.TRUE.equals(ruleDTO.getStartFlag()) ? 1 : 0);
            ruleEO.setInitTypeFlag(Boolean.TRUE.equals(ruleDTO.getInitTypeFlag()) ? 1 : 0);
            ruleEO.setEnableToleranceFlag(Boolean.TRUE.equals(ruleDTO.getEnableToleranceFlag()) ? 1 : 0);
            if (null == this.unionRuleDao.get((Serializable)((Object)impId))) {
                this.unionRuleDao.save(ruleEO);
            } else {
                this.unionRuleDao.update((BaseEntity)ruleEO);
            }
            List<AbstractUnionRule> list = parentMap.get(impId);
            this.cycleCheckChildren(list, parentMap);
        }
    }
}

