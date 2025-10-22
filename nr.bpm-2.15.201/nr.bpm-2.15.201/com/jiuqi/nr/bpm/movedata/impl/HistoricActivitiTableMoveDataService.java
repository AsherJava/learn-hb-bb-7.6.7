/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.impl;

import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.movedata.IHistoricActivitiTableMoveDataService;
import com.jiuqi.nr.bpm.movedata.NrHistoricActivityInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricAttachmentImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricCommentImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricDetailImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricIdentityLinkEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricProcessInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricTaskInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.NrHistoricVariableInstanceEntityImpl;
import com.jiuqi.nr.bpm.movedata.history.IHistoricActivityInstanceEntityDao;
import com.jiuqi.nr.bpm.movedata.history.IHistoricAttachmentEntityDao;
import com.jiuqi.nr.bpm.movedata.history.IHistoricCommentEntityDao;
import com.jiuqi.nr.bpm.movedata.history.IHistoricDetailEntityDao;
import com.jiuqi.nr.bpm.movedata.history.IHistoricProcessInstanceEntityDao;
import com.jiuqi.nr.bpm.movedata.history.IHistoricTaskInstanceEntityDao;
import com.jiuqi.nr.bpm.movedata.history.IHistoricVariableInstanceEntityDao;
import com.jiuqi.nr.bpm.movedata.history.impl.HistoricIdentityLinkEntityDaoImpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoricActivitiTableMoveDataService
implements IHistoricActivitiTableMoveDataService {
    @Autowired
    ProcessEngineProvider processEngineProvider;
    @Autowired
    IHistoricProcessInstanceEntityDao historicProcessInstanceEntityDao;
    @Autowired
    IHistoricActivityInstanceEntityDao historicActivityInstanceEntityDao;
    @Autowired
    HistoricIdentityLinkEntityDaoImpl historicIdentityLinkEntityDao;
    @Autowired
    IHistoricTaskInstanceEntityDao historicTaskInstanceEntityDao;
    @Autowired
    IHistoricVariableInstanceEntityDao historicVariableInstanceEntityDao;
    @Autowired
    IHistoricAttachmentEntityDao historicAttachmentEntityDao;
    @Autowired
    IHistoricCommentEntityDao historicCommentEntityDao;
    @Autowired
    IHistoricDetailEntityDao historicDetailEntityDao;

    @Override
    public void importHistoricActivityInstanceEntityImpl(List<NrHistoricActivityInstanceEntityImpl> nrHistoricActivityInstanceEntityImpl, String processInstanceId) {
        if (processInstanceId != null) {
            this.historicActivityInstanceEntityDao.deleteByProcessId(processInstanceId);
        }
        this.historicActivityInstanceEntityDao.batchInsert(nrHistoricActivityInstanceEntityImpl);
    }

    @Override
    public List<NrHistoricActivityInstanceEntityImpl> exportHistoricActivityInstanceEntityImpl(String processInstanceId) {
        List<NrHistoricActivityInstanceEntityImpl> nrHistoricActivityInstanceEntities = this.historicActivityInstanceEntityDao.queryByProcessId(processInstanceId);
        return nrHistoricActivityInstanceEntities;
    }

    @Override
    public void importHistoricIdentityLinkEntityImpl(List<NrHistoricIdentityLinkEntityImpl> nrHistoricIdentityLinkEntityImpl, String processInstanceId) {
        this.historicIdentityLinkEntityDao.change(nrHistoricIdentityLinkEntityImpl);
    }

    @Override
    public List<NrHistoricIdentityLinkEntityImpl> exportHistoricIdentityLinkEntityImpl(String processInstanceId) {
        List<NrHistoricIdentityLinkEntityImpl> nrHistoricIdentityLinkEntities = this.historicIdentityLinkEntityDao.queryByProcInstId(processInstanceId);
        return nrHistoricIdentityLinkEntities;
    }

    @Override
    public void importHistoricProcessInstanceEntityImpl(List<NrHistoricProcessInstanceEntityImpl> nrHistoricProcessInstanceEntityImpl, String processInstanceId) {
        if (processInstanceId != null) {
            this.historicProcessInstanceEntityDao.deleteByProcessId(processInstanceId);
        }
        this.historicProcessInstanceEntityDao.batchInsert(nrHistoricProcessInstanceEntityImpl);
    }

    @Override
    public List<NrHistoricProcessInstanceEntityImpl> exportHistoricProcessInstanceEntityImpl(String processInstanceId) {
        List<NrHistoricProcessInstanceEntityImpl> nrHistoricProcessInstanceEntitys = this.historicProcessInstanceEntityDao.queryByProcessId(processInstanceId);
        return nrHistoricProcessInstanceEntitys;
    }

    @Override
    public void importHistoricTaskInstanceEntityImpl(List<NrHistoricTaskInstanceEntityImpl> nrHistoricTaskInstanceEntityImpl, String processInstanceId) {
        if (processInstanceId != null) {
            this.historicTaskInstanceEntityDao.deleteByProcessId(processInstanceId);
        }
        this.historicTaskInstanceEntityDao.batchInsert(nrHistoricTaskInstanceEntityImpl);
    }

    @Override
    public List<NrHistoricTaskInstanceEntityImpl> exportHistoricTaskInstanceEntityImpl(String processInstanceId) {
        List<NrHistoricTaskInstanceEntityImpl> nrHistoricTaskInstanceEntities = this.historicTaskInstanceEntityDao.queryByProcessId(processInstanceId);
        return nrHistoricTaskInstanceEntities;
    }

    @Override
    public void importHistoricVariableInstanceEntityImpl(List<NrHistoricVariableInstanceEntityImpl> nrHistoricVariableInstanceEntityImpl, String processInstanceId) {
        if (processInstanceId != null) {
            this.historicVariableInstanceEntityDao.deleteByProcessId(processInstanceId);
        }
        this.historicVariableInstanceEntityDao.batchInsert(nrHistoricVariableInstanceEntityImpl);
    }

    @Override
    public List<NrHistoricVariableInstanceEntityImpl> exportHistoricVariableInstanceEntityImpl(String processInstanceId) {
        List<NrHistoricVariableInstanceEntityImpl> nrHistoricVariableInstanceEntities = this.historicVariableInstanceEntityDao.queryByProcessId(processInstanceId);
        return nrHistoricVariableInstanceEntities;
    }

    @Override
    public void importHistoricAttachmentEntityImpl(List<NrHistoricAttachmentImpl> nrHistoricAttachmentImpls, String processInstanceId) {
        if (processInstanceId != null) {
            this.historicAttachmentEntityDao.deleteByProcessId(processInstanceId);
        }
        this.historicAttachmentEntityDao.batchInsert(nrHistoricAttachmentImpls);
    }

    @Override
    public List<NrHistoricAttachmentImpl> exportHistoricAttachmentEntityImpl(String processInstanceId) {
        List<NrHistoricAttachmentImpl> nrHistoricAttachmentImpls = this.historicAttachmentEntityDao.queryByProcessId(processInstanceId);
        return nrHistoricAttachmentImpls;
    }

    @Override
    public void importHistoricCommentEntityImpl(List<NrHistoricCommentImpl> nrHistoricCommentImpls, String processInstanceId) {
        if (processInstanceId != null) {
            this.historicCommentEntityDao.deleteByProcessId(processInstanceId);
        }
        this.historicCommentEntityDao.batchInsert(nrHistoricCommentImpls);
    }

    @Override
    public List<NrHistoricCommentImpl> exportHistoricCommentEntityImpl(String processInstanceId) {
        List<NrHistoricCommentImpl> nrHistoricCommentImpls = this.historicCommentEntityDao.queryByProcessId(processInstanceId);
        return nrHistoricCommentImpls;
    }

    @Override
    public void importHistoricDetailEntityImpl(List<NrHistoricDetailImpl> nrHistoricDetailImpls, String processInstanceId) {
        if (processInstanceId != null) {
            this.historicDetailEntityDao.deleteByProcessId(processInstanceId);
        }
        this.historicDetailEntityDao.batchInsert(nrHistoricDetailImpls);
    }

    @Override
    public List<NrHistoricDetailImpl> exportHistoricDetailEntityImpl(String processInstanceId) {
        List<NrHistoricDetailImpl> nrHistoricDetailImpls = this.historicDetailEntityDao.queryByProcessId(processInstanceId);
        return nrHistoricDetailImpls;
    }
}

