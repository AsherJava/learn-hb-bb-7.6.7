/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.utils.Utils
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 *  com.jiuqi.va.domain.workflow.WorkflowBusinessDTO
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.feign.client.MetaDataClient
 *  com.jiuqi.va.feign.client.TodoClient
 *  com.jiuqi.va.feign.client.VaSystemOptionClient
 *  com.jiuqi.va.feign.client.WorkflowServerClient
 *  com.jiuqi.va.trans.domain.BizErrorDO
 *  com.jiuqi.va.trans.domain.BizErrorDTO
 *  com.jiuqi.va.trans.domain.VaTransMessageDO
 *  com.jiuqi.va.trans.domain.VaTransMessageDTO
 *  com.jiuqi.va.trans.service.VaBizErrorService
 *  com.jiuqi.va.trans.service.VaTransMessageService
 */
package com.jiuqi.va.bill.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.service.BillDataEditService;
import com.jiuqi.va.bill.service.BillLockService;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.utils.Utils;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import com.jiuqi.va.domain.workflow.WorkflowBusinessDTO;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.feign.client.MetaDataClient;
import com.jiuqi.va.feign.client.TodoClient;
import com.jiuqi.va.feign.client.VaSystemOptionClient;
import com.jiuqi.va.feign.client.WorkflowServerClient;
import com.jiuqi.va.trans.domain.BizErrorDO;
import com.jiuqi.va.trans.domain.BizErrorDTO;
import com.jiuqi.va.trans.domain.VaTransMessageDO;
import com.jiuqi.va.trans.domain.VaTransMessageDTO;
import com.jiuqi.va.trans.service.VaBizErrorService;
import com.jiuqi.va.trans.service.VaTransMessageService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public abstract class BillWorkflowActionBase
extends BillActionBase {
    private static final Logger log = LoggerFactory.getLogger(BillWorkflowActionBase.class);
    @Autowired
    protected WorkflowServerClient workflowServerClient;
    @Autowired
    protected VaTransMessageService vaTransMessageService;
    @Autowired
    protected VaBizErrorService vaBizErrorService;
    @Autowired
    protected BillLockService billLockService;
    @Autowired
    protected TodoClient todoClient;
    @Autowired
    protected MetaDataClient metaDataClient;
    @Autowired
    protected VaSystemOptionClient vaSystemOptionClient;
    @Autowired
    protected BillDataEditService billDataEditService;

    protected Map<String, Object> getbillWorkflowRelation(WorkflowDTO workflowDTO, String definecode) {
        WorkflowBusinessDTO business = new WorkflowBusinessDTO();
        business.setBusinesscode(definecode);
        business.setWorkflowdefinekey(workflowDTO.getUniqueCode());
        business.setWorkflowdefineversion(workflowDTO.getProcessDefineVersion());
        business.setStopflag(Integer.valueOf(-1));
        business.setShowTitle(false);
        business.setTraceId(Utils.getTraceId());
        R r = this.workflowServerClient.getBusinessBoundedWorkflow(business);
        if (0 != r.getCode()) {
            throw new BillException(r.getMsg());
        }
        Map mapdata = (Map)r.get((Object)"data");
        List ja = (List)mapdata.get("workflows");
        Map billWorkflowRelation = (Map)ja.get(0);
        return billWorkflowRelation;
    }

    protected void checkLock(String billCode) {
        VaTransMessageDTO vaTransMessageDTO = new VaTransMessageDTO();
        vaTransMessageDTO.setBizcode(billCode);
        vaTransMessageDTO.setStatus(Integer.valueOf(0));
        vaTransMessageDTO.setFilterRoot(true);
        List transMsgs = this.vaTransMessageService.listTransMessage(vaTransMessageDTO);
        if (!CollectionUtils.isEmpty(transMsgs)) {
            VaTransMessageDO transMsg = (VaTransMessageDO)transMsgs.get(0);
            String defineName = transMsg.getDefinename();
            if ("bill-commit".equals(this.getName()) && "bill-commit".equals(defineName)) {
                this.throwEx(transMsg);
            } else if ("bill-agree".equals(this.getName()) && "bill-complete".equals(defineName)) {
                this.throwEx(transMsg);
            } else if ("bill-reject".equals(this.getName()) && "bill-reject".equals(defineName)) {
                this.throwEx(transMsg);
            } else if ("bill-retract".equals(this.getName()) && "bill-retract".equals(defineName)) {
                this.throwEx(transMsg);
            } else {
                throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.workflowaction.processing"));
            }
        }
    }

    private void throwEx(VaTransMessageDO transMsg) {
        String msgId = transMsg.getId();
        BizErrorDTO bizErrorDTO = new BizErrorDTO();
        bizErrorDTO.setMsgid(msgId);
        BizErrorDO bizErrorDO = this.vaBizErrorService.get(bizErrorDTO);
        if (bizErrorDO != null) {
            String error = bizErrorDO.getErrorreason();
            if (StringUtils.hasText(error) && error.contains("va_activiti_")) {
                error = BillCoreI18nUtil.getWorkflowActivitiMessage(error);
            }
            throw new BillException(error != null ? error : BillCoreI18nUtil.getMessage("va.billcore.workflowaction.handlefailed"));
        }
        throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.workflowaction.handlefailed"));
    }

    protected boolean isChooseApprover() {
        OptionItemDTO optionItemDTO = new OptionItemDTO();
        optionItemDTO.setName("WF1006");
        List optionItemVOs = this.workflowServerClient.listWorkflowOption(optionItemDTO);
        return optionItemVOs.size() != 0 && !"0".equals(((OptionItemVO)optionItemVOs.get(0)).getVal());
    }
}

