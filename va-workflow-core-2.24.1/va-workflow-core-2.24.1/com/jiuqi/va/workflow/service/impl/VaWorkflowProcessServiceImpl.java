/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.ProcessDO
 *  com.jiuqi.va.domain.workflow.ProcessDTO
 *  com.jiuqi.va.domain.workflow.ProcessHistoryDO
 *  com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService
 *  org.apache.shiro.util.Assert
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.workflow.service.impl;

import com.jiuqi.va.domain.workflow.ProcessDO;
import com.jiuqi.va.domain.workflow.ProcessDTO;
import com.jiuqi.va.domain.workflow.ProcessHistoryDO;
import com.jiuqi.va.domain.workflow.service.VaWorkflowProcessService;
import com.jiuqi.va.workflow.dao.VaWorkflowProcessDao;
import com.jiuqi.va.workflow.dao.VaWorkflowProcessHistoryDao;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.shiro.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class VaWorkflowProcessServiceImpl
implements VaWorkflowProcessService {
    @Autowired
    private VaWorkflowProcessDao vaWorkflowProcessDao;
    @Autowired
    private VaWorkflowProcessHistoryDao vaWorkflowProcessHistoryDao;

    public int count(ProcessDTO processDTO) {
        return this.vaWorkflowProcessDao.countProcess(processDTO);
    }

    public List<Map<String, Object>> selectProcess(ProcessDTO processDTO) {
        return new ArrayList<Map<String, Object>>(Optional.ofNullable(this.vaWorkflowProcessDao.selectProcess(processDTO)).orElse(Collections.emptyList()));
    }

    public ProcessDO get(ProcessDTO processDTO) {
        ProcessDO processDO = this.vaWorkflowProcessDao.getProcess(processDTO);
        return processDO;
    }

    @Transactional(rollbackFor={Exception.class})
    public void add(ProcessDO processDO) {
        processDO.setStarttime(new Date());
        this.vaWorkflowProcessDao.insert(processDO);
    }

    @Transactional(rollbackFor={Exception.class})
    public void addHistory(ProcessHistoryDO processHistoryDO) {
        if (ObjectUtils.isEmpty(processHistoryDO.getEndtime())) {
            processHistoryDO.setEndtime(new Date());
        }
        this.vaWorkflowProcessHistoryDao.insert(processHistoryDO);
    }

    @Transactional(rollbackFor={Exception.class})
    public void closeProcess(ProcessDTO processDTO) {
        if (!StringUtils.hasText(processDTO.getId())) {
            throw new RuntimeException(VaWorkFlowI18nUtils.getInfo("va.workflow.stopprocessfailure"));
        }
        ProcessDO processDO = this.get(processDTO);
        this.vaWorkflowProcessDao.delete(processDTO);
        ProcessHistoryDO processHistoryDO = new ProcessHistoryDO();
        BeanUtils.copyProperties(processDO, processHistoryDO);
        processHistoryDO.setEndstatus(processDTO.getEndstatus());
        processHistoryDO.setEndreason(processDTO.getEndreason());
        processHistoryDO.setEndtime(processDTO.getEndtime());
        this.addHistory(processHistoryDO);
    }

    public List<ProcessHistoryDO> listHistory(ProcessDTO processDTO) {
        return this.vaWorkflowProcessHistoryDao.listHistory(processDTO);
    }

    public ProcessDO getBillProcess(ProcessDTO processDTO) {
        List<ProcessHistoryDO> processHistoryList;
        ProcessDO process = this.get(processDTO);
        if (process == null && !CollectionUtils.isEmpty(processHistoryList = this.listHistory(processDTO))) {
            process = new ProcessDO();
            BeanUtils.copyProperties(processHistoryList.get(processHistoryList.size() - 1), process);
        }
        return process;
    }

    public int deleteHistoryById(ProcessHistoryDO historyDO) {
        String id = historyDO.getId();
        Assert.hasText((String)id, (String)VaWorkFlowI18nUtils.getInfo("va.workflow.lacknecessaryparams"));
        ProcessHistoryDO record = new ProcessHistoryDO();
        record.setId(id);
        return this.vaWorkflowProcessHistoryDao.delete(record);
    }
}

