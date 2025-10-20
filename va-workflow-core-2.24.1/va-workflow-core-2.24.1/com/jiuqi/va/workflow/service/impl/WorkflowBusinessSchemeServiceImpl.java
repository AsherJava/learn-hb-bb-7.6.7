/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.workflow.businessscheme.WorkflowBusinessSchemeDO
 *  com.jiuqi.va.domain.workflow.businessscheme.WorkflowBusinessSchemeDTO
 *  com.jiuqi.va.domain.workflow.businessscheme.WorkflowBusinessSchemeDataDO
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.service.impl;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.workflow.businessscheme.WorkflowBusinessSchemeDO;
import com.jiuqi.va.domain.workflow.businessscheme.WorkflowBusinessSchemeDTO;
import com.jiuqi.va.domain.workflow.businessscheme.WorkflowBusinessSchemeDataDO;
import com.jiuqi.va.workflow.dao.WorkflowBusinessSchemeDao;
import com.jiuqi.va.workflow.dao.WorkflowBusinessSchemeDataDao;
import com.jiuqi.va.workflow.domain.WorkflowOption;
import com.jiuqi.va.workflow.service.WorkflowBusinessSchemeService;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkflowBusinessSchemeServiceImpl
implements WorkflowBusinessSchemeService {
    @Autowired
    private WorkflowBusinessSchemeDao businessSchemeDao;
    @Autowired
    private WorkflowBusinessSchemeDataDao businessSchemeDataDao;

    @Override
    public List<WorkflowBusinessSchemeDTO> list(WorkflowBusinessSchemeDTO schemeDTO) {
        if (WorkflowOption.SchemeType.ACTION.name().equals(schemeDTO.getSchemetype())) {
            schemeDTO.setBusinesscode(null);
        }
        schemeDTO.setOrdertype(WorkflowOption.OrderType.DESC.name());
        return this.businessSchemeDao.list(schemeDTO);
    }

    @Override
    public List<Map<String, Object>> get(WorkflowBusinessSchemeDTO schemeDTO) {
        WorkflowBusinessSchemeDataDO dataDO = new WorkflowBusinessSchemeDataDO();
        dataDO.setId(schemeDTO.getId());
        WorkflowBusinessSchemeDataDO schemeDataDO = (WorkflowBusinessSchemeDataDO)this.businessSchemeDataDao.selectOne(dataDO);
        if (schemeDataDO == null) {
            return new ArrayList<Map<String, Object>>();
        }
        return JSONUtil.parseMapArray((String)schemeDataDO.getSchemedata());
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void update(WorkflowBusinessSchemeDTO schemeDTO) {
        WorkflowBusinessSchemeDO schemeDO = new WorkflowBusinessSchemeDO();
        schemeDO.setId(schemeDTO.getId());
        schemeDO.setModifytime(new Date());
        schemeDO.setModifyuser(ShiroUtil.getUser().getId());
        schemeDO.setTitle(schemeDTO.getTitle());
        schemeDO.setRemark(schemeDTO.getRemark());
        int cnt = this.businessSchemeDao.updateByPrimaryKeySelective(schemeDO);
        if (cnt < 1) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.updateerror"));
        }
        List schemedata = schemeDTO.getSchemedata();
        if (schemedata == null) {
            return;
        }
        WorkflowBusinessSchemeDataDO schemeDataDO = new WorkflowBusinessSchemeDataDO();
        schemeDataDO.setId(schemeDTO.getId());
        schemeDataDO.setSchemedata(JSONUtil.toJSONString((Object)schemedata));
        cnt = this.businessSchemeDataDao.updateByPrimaryKeySelective(schemeDataDO);
        if (cnt < 1) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.updateerror"));
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void delete(WorkflowBusinessSchemeDTO schemeDTO) {
        WorkflowBusinessSchemeDO schemeDO = new WorkflowBusinessSchemeDO();
        schemeDO.setId(schemeDTO.getId());
        int cnt = this.businessSchemeDao.delete(schemeDO);
        if (cnt < 1) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.deleteerror"));
        }
        WorkflowBusinessSchemeDataDO schemeDataDO = new WorkflowBusinessSchemeDataDO();
        schemeDataDO.setId(schemeDTO.getId());
        cnt = this.businessSchemeDataDao.delete(schemeDataDO);
        if (cnt < 1) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.deleteerror"));
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void add(WorkflowBusinessSchemeDTO schemeDTO) {
        if (WorkflowOption.SchemeType.ACTION.name().equals(schemeDTO.getSchemetype())) {
            schemeDTO.setBusinesscode(null);
        }
        WorkflowBusinessSchemeDO schemeDO = new WorkflowBusinessSchemeDO();
        schemeDO.setName(schemeDTO.getName());
        schemeDO.setBusinesscode(schemeDTO.getBusinesscode());
        schemeDO.setSchemetype(schemeDTO.getSchemetype());
        int cnt = this.businessSchemeDao.selectCount(schemeDO);
        if (cnt > 0) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.identifyduplicates"));
        }
        String id = UUID.randomUUID().toString();
        schemeDO.setId(id);
        schemeDO.setModifytime(new Date());
        schemeDO.setModifyuser(ShiroUtil.getUser().getId());
        schemeDO.setTitle(schemeDTO.getTitle());
        schemeDO.setRemark(schemeDTO.getRemark());
        this.businessSchemeDao.insert(schemeDO);
        WorkflowBusinessSchemeDataDO schemeDataDO = new WorkflowBusinessSchemeDataDO();
        schemeDataDO.setId(id);
        schemeDataDO.setSchemedata(JSONUtil.toJSONString((Object)schemeDTO.getSchemedata()));
        this.businessSchemeDataDao.insert(schemeDataDO);
    }
}

