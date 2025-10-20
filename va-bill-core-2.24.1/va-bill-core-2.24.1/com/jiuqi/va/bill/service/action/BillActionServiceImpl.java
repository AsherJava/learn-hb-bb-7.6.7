/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionManager
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.utils.R
 *  com.jiuqi.va.domain.common.JSONUtil
 */
package com.jiuqi.va.bill.service.action;

import com.jiuqi.va.bill.domain.action.BillActionParamDTO;
import com.jiuqi.va.bill.domain.action.CustomActionParamDTO;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.service.action.BillActionService;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionManager;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.utils.R;
import com.jiuqi.va.domain.common.JSONUtil;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class BillActionServiceImpl
implements BillActionService {
    private static final Logger logger = LoggerFactory.getLogger(BillActionServiceImpl.class);
    @Autowired
    private BillDefineService billDefineService;
    @Autowired
    private ActionManager actionManager;

    @Override
    public R<String> billListCustomActionExecute(CustomActionParamDTO customActionParamDTO) {
        StringBuffer resultMsg = new StringBuffer();
        List<BillActionParamDTO> billActionParamDTOList = customActionParamDTO.getBillActionParamList();
        Assert.notEmpty(billActionParamDTOList, "Please check param!");
        for (BillActionParamDTO billListActionDTO : billActionParamDTOList) {
            String msg;
            R<String> r = this.executeAction(billListActionDTO);
            String billCode = billListActionDTO.getBillCode();
            String actionName = billListActionDTO.getActionName();
            if (r.getCode() == 0) {
                msg = BillCoreI18nUtil.getMessage("va.bill.core.billlist.custom-action-execute.info1", new Object[]{billCode, actionName});
                resultMsg.append(msg).append("<br/>");
                continue;
            }
            msg = BillCoreI18nUtil.getMessage("va.bill.core.billlist.custom-action-execute.info2", new Object[]{billCode, actionName});
            resultMsg.append(msg).append("<br/>");
            resultMsg.append("- ").append(r.getMsg()).append("<br/>");
        }
        return new R(0, null, null, (Object)resultMsg.toString());
    }

    private R<String> executeAction(BillActionParamDTO billActionParamDTO) {
        logger.debug("\u3010executeAction\u65b9\u6cd5\u3011\u5165\u53c2 billActionParamDTO\uff1a{}", (Object)billActionParamDTO);
        StringBuilder resultMsg = new StringBuilder();
        ActionResponse actionResponse = new ActionResponse();
        ActionRequest actionRequest = new ActionRequest();
        actionRequest.setParams(billActionParamDTO.getActionParams() != null ? billActionParamDTO.getActionParams() : new HashMap());
        BillContextImpl context = new BillContextImpl();
        context.setDisableVerify(true);
        try {
            BillModelImpl billModel = (BillModelImpl)this.billDefineService.createModel((BillContext)context, billActionParamDTO.getBillDefineCode());
            billModel.loadByCode(billActionParamDTO.getBillCode());
            billModel.executeAction((Action)this.actionManager.get(billActionParamDTO.getActionType()), actionRequest, actionResponse);
            List checkResultList = actionResponse.getCheckMessages();
            if (logger.isDebugEnabled()) {
                logger.debug("\u3010executeAction\u65b9\u6cd5\u3011\u6267\u884c\u7ed3\u679ccheckResultList\uff1a{}", (Object)JSONUtil.toJSONString((Object)checkResultList));
            }
            resultMsg.append(!CollectionUtils.isEmpty(checkResultList) ? checkResultList.stream().map(CheckResult::getCheckMessage).collect(Collectors.joining(";")) : "");
        }
        catch (BillException e) {
            resultMsg.append(!CollectionUtils.isEmpty(e.getCheckMessages()) ? e.getCheckMessages().stream().map(r -> r.getFormulaName().concat(":").concat(r.getCheckMessage())).collect(Collectors.joining(";")) : e.getMessage());
            return new R(500, resultMsg.toString(), e.toString(), null);
        }
        catch (Exception e) {
            resultMsg.append(e.getMessage());
            return new R(500, resultMsg.toString(), e.toString(), null);
        }
        return new R(StringUtils.hasText(resultMsg.toString()) ? 500 : 0, resultMsg.toString(), "", (Object)"");
    }
}

