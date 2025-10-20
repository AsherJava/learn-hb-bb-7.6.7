/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.workflow.WorkflowOptionDO
 *  com.jiuqi.va.domain.workflow.service.WorkflowOptionService
 *  com.jiuqi.va.utils.VaI18nParamUtil
 */
package com.jiuqi.va.workflow.service.impl;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.workflow.WorkflowOptionDO;
import com.jiuqi.va.domain.workflow.service.WorkflowOptionService;
import com.jiuqi.va.utils.VaI18nParamUtil;
import com.jiuqi.va.workflow.dao.WorkflowOptionDao;
import com.jiuqi.va.workflow.domain.WorkflowOptionConsts;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WorkflowOptionServiceImpl
implements WorkflowOptionService {
    @Autowired
    private WorkflowOptionDao workflowOptionDao;

    public List<OptionItemVO> list(OptionItemDTO param) {
        HashMap<String, WorkflowOptionDO> vals = new HashMap<String, WorkflowOptionDO>();
        List list = this.workflowOptionDao.select(new WorkflowOptionDO());
        if (list != null && list.size() > 0) {
            for (WorkflowOptionDO optionDO : list) {
                if (!StringUtils.hasText(optionDO.getVal())) continue;
                vals.put(optionDO.getName(), optionDO);
            }
        }
        ArrayList<OptionItemVO> endList = new ArrayList<OptionItemVO>();
        LinkedHashMap<String, OptionItemVO> infos = WorkflowOptionConsts.optionFoMap(param.getGroupName());
        for (OptionItemVO optionItemVO : infos.values()) {
            if (StringUtils.hasText(param.getSearchKey()) && !optionItemVO.getName().contains(param.getSearchKey()) && !optionItemVO.getTitle().contains(param.getSearchKey()) || StringUtils.hasText(param.getName()) && !optionItemVO.getName().equals(param.getName())) continue;
            if (vals.containsKey(optionItemVO.getName())) {
                WorkflowOptionDO hadDo = (WorkflowOptionDO)vals.get(optionItemVO.getName());
                optionItemVO.setVal(hadDo.getVal());
                optionItemVO.setModifyuser(hadDo.getModifyuser());
                optionItemVO.setModifytime(hadDo.getModifytime());
            } else {
                optionItemVO.setVal(optionItemVO.getDefauleVal());
            }
            endList.add(optionItemVO);
        }
        if (VaI18nParamUtil.getTranslationEnabled().booleanValue()) {
            VaWorkFlowI18nUtils.handleWorkflowOptionI18n(endList);
        }
        return endList;
    }

    public R update(WorkflowOptionDO option) {
        WorkflowOptionDO param = new WorkflowOptionDO();
        param.setName(option.getName());
        WorkflowOptionDO old = (WorkflowOptionDO)this.workflowOptionDao.selectOne(param);
        if (old != null) {
            option.setId(old.getId());
            option.setModifyuser(ShiroUtil.getUser().getUsername());
            option.setModifytime(new Date());
            this.workflowOptionDao.updateByPrimaryKey(option);
        } else {
            option.setId(UUID.randomUUID());
            option.setModifyuser(ShiroUtil.getUser().getUsername());
            option.setModifytime(new Date());
            this.workflowOptionDao.insert(option);
        }
        return R.ok();
    }
}

