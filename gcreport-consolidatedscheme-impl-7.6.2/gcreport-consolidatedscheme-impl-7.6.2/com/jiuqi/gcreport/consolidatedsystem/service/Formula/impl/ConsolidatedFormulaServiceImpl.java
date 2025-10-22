/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.consolidatedsystem.common.OrderGenerator
 *  com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO
 *  com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 *  com.jiuqi.np.log.LogHelper
 *  org.activiti.engine.impl.util.CollectionUtil
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.consolidatedsystem.service.Formula.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.common.OrderGenerator;
import com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao;
import com.jiuqi.gcreport.consolidatedsystem.dao.formula.ConsolidatedFormulaDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.entity.Formula.ConsolidatedFormulaEO;
import com.jiuqi.gcreport.consolidatedsystem.service.Formula.ConsolidatedFormulaService;
import com.jiuqi.gcreport.consolidatedsystem.vo.formula.ConsolidatedFormulaVO;
import com.jiuqi.gcreport.definition.impl.basic.entity.BaseEntity;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import com.jiuqi.gcreport.unionrule.service.UnionRuleService;
import com.jiuqi.np.log.LogHelper;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.activiti.engine.impl.util.CollectionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class ConsolidatedFormulaServiceImpl
implements ConsolidatedFormulaService {
    @Autowired
    private ConsolidatedFormulaDao consolidatedFormulaDao;
    @Autowired
    private ConsolidatedSystemDao consolidatedSystemDao;
    @Autowired
    private UnionRuleService unionRuleService;

    @Override
    public List<ConsolidatedFormulaVO> listConsFormulas(String systemId) {
        return this.listConsFormulas(systemId, false);
    }

    @Override
    public List<ConsolidatedFormulaVO> listConsFormulas(String systemId, boolean containRuleDTO) {
        List<ConsolidatedFormulaEO> consolidatedFormulaEOS = this.consolidatedFormulaDao.listConsFormulas(systemId);
        return consolidatedFormulaEOS.stream().map(consolidatedFormulaEO -> {
            ConsolidatedFormulaVO consolidatedFormulaVO = new ConsolidatedFormulaVO();
            BeanUtils.copyProperties(consolidatedFormulaEO, consolidatedFormulaVO);
            String ruleIds = consolidatedFormulaEO.getRuleIds();
            List ruleIdList = null;
            if (!StringUtils.isEmpty((String)ruleIds)) {
                Map map = (Map)JsonUtils.readValue((String)ruleIds, Map.class);
                ruleIdList = (List)map.get("ruleIds");
            }
            if (CollectionUtils.isEmpty(ruleIdList)) {
                return consolidatedFormulaVO;
            }
            if (containRuleDTO) {
                List<AbstractUnionRule> unionRules = this.unionRuleService.selectUnionRuleDTOByIdList(ruleIdList);
                consolidatedFormulaVO.setRuleBaseData(unionRules);
            }
            consolidatedFormulaVO.setRuleIds(ruleIdList);
            return consolidatedFormulaVO;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void saveConsFormula(List<ConsolidatedFormulaVO> consolidatedFormulaVOS) {
        List consolidatedFormulaEOS = consolidatedFormulaVOS.stream().map(consolidatedFormulaVO -> {
            ConsolidatedFormulaEO consolidatedFormulaEO = new ConsolidatedFormulaEO();
            BeanUtils.copyProperties(consolidatedFormulaVO, (Object)consolidatedFormulaEO);
            if (!CollectionUtil.isEmpty((Collection)consolidatedFormulaVO.getRuleIds())) {
                HashMap<String, List> jsonObject = new HashMap<String, List>();
                jsonObject.put("ruleIds", consolidatedFormulaVO.getRuleIds());
                consolidatedFormulaEO.setRuleIds(JsonUtils.writeValueAsString(jsonObject));
            }
            return consolidatedFormulaEO;
        }).collect(Collectors.toList());
        String systemName = ((ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)consolidatedFormulaVOS.get(0).getSystemId()))).getSystemName();
        for (ConsolidatedFormulaEO consolidatedFormulaEO : consolidatedFormulaEOS) {
            if (StringUtils.isEmpty((String)consolidatedFormulaEO.getSortOrder())) {
                consolidatedFormulaEO.setSortOrder(OrderGenerator.newOrderShort());
            }
            if (StringUtils.isEmpty((String)consolidatedFormulaEO.getId()) || this.consolidatedFormulaDao.get((Serializable)((Object)consolidatedFormulaEO.getId())) == null) {
                this.consolidatedFormulaDao.save(consolidatedFormulaEO);
                LogHelper.info((String)"\u5408\u5e76-\u4f53\u7cfb\u516c\u5f0f", (String)("\u516c\u5f0f\u65b0\u589e-" + systemName + "\u5408\u5e76\u4f53\u7cfb"), (String)("\u65b0\u589e\u516c\u5f0f\u4fe1\u606f" + JsonUtils.writeValueAsString((Object)((Object)consolidatedFormulaEO))));
                continue;
            }
            this.consolidatedFormulaDao.update((BaseEntity)consolidatedFormulaEO);
            LogHelper.info((String)"\u5408\u5e76-\u4f53\u7cfb\u516c\u5f0f", (String)("\u516c\u5f0f\u4fee\u6539-" + systemName + "\u5408\u5e76\u4f53\u7cfb"), (String)("\u4fee\u6539\u540e\u516c\u5f0f\u4fe1\u606f:" + JsonUtils.writeValueAsString((Object)((Object)consolidatedFormulaEO))));
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void batchDeleteConsFormula(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        ConsolidatedFormulaEO eo = (ConsolidatedFormulaEO)this.consolidatedFormulaDao.get((Serializable)((Object)ids.get(0)));
        String systemName = ((ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)eo.getSystemId()))).getSystemName();
        LogHelper.info((String)"\u5408\u5e76-\u4f53\u7cfb\u516c\u5f0f", (String)("\u516c\u5f0f\u5220\u9664-" + systemName + "\u5408\u5e76\u4f53\u7cfb"), (String)("\u5220\u9664" + ids.size() + "\u6761\u516c\u5f0f"));
        this.consolidatedFormulaDao.batchDeleteByIds(ids);
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void exchangeSort(String opNodeId, int step) {
        ConsolidatedFormulaEO opeNode = (ConsolidatedFormulaEO)this.consolidatedFormulaDao.get((Serializable)((Object)opNodeId));
        if (null == opeNode) {
            return;
        }
        ConsolidatedFormulaEO exeNode = step < 0 ? this.consolidatedFormulaDao.getPreNodeBySystemIdAndOrder(opeNode.getSystemId(), opeNode.getSortOrder()) : this.consolidatedFormulaDao.getNextNodeBySystemIdAndOrder(opeNode.getSystemId(), opeNode.getSortOrder());
        if (null == exeNode) {
            throw new BusinessRuntimeException(step < 0 ? "\u4e0d\u80fd\u518d\u79fb\u4e86\uff0c\u5df2\u7ecf\u4e3a\u7b2c\u4e00\u6761\u4e86" : "\u4e0d\u80fd\u518d\u79fb\u4e86\uff0c\u5df2\u7ecf\u4e3a\u6700\u540e\u4e00\u6761\u4e86");
        }
        String tempSort = opeNode.getSortOrder();
        opeNode.setSortOrder(exeNode.getSortOrder());
        exeNode.setSortOrder(tempSort);
        this.consolidatedFormulaDao.update((BaseEntity)opeNode);
        this.consolidatedFormulaDao.update((BaseEntity)exeNode);
    }
}

