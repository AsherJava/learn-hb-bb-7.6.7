/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.biz.impl.model.ModelImpl
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  com.jiuqi.va.biz.intf.data.DataUpdate
 *  com.jiuqi.va.biz.ruler.impl.CheckResultImpl
 *  com.jiuqi.va.biz.ruler.impl.RulerImpl
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.utils.R
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BillCodeClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.va.bill.service.impl;

import com.jiuqi.va.bill.action.DeleteAction;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDataService;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.impl.model.ModelImpl;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.data.DataUpdate;
import com.jiuqi.va.biz.ruler.impl.CheckResultImpl;
import com.jiuqi.va.biz.ruler.impl.RulerImpl;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.biz.utils.R;
import com.jiuqi.va.feign.client.BillCodeClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class BillDataServiceImpl
implements BillDataService {
    @Autowired
    private BillDefineService billDefineService;
    @Autowired
    private DeleteAction deleteAction;
    @Autowired
    private BillCodeClient billCodeClient;

    @Override
    public Map<String, List<Map<String, Object>>> load(BillContext context, String billType, String billCode) {
        BillModel model = this.billDefineService.createModel(context, billType);
        model.loadByCode(billCode);
        return model.getData().getTablesData();
    }

    @Override
    public Map<String, List<List<Object>>> load(BillContext context, String billType, String billCode, boolean usInc) {
        BillModel model = this.billDefineService.createModel(context, billType);
        model.loadByCode(billCode);
        return model.getData().getFrontTablesData();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Map<String, List<Map<String, Object>>> save(BillContext context, String billType, boolean create, Map<String, List<Map<String, Object>>> billData) {
        BillModel model = this.billDefineService.createModel(context, billType);
        List<Map<String, Object>> masterData = billData.get(((DataTable)model.getData().getTables().getMasterTable()).getName());
        if (masterData == null || masterData.size() != 1) {
            throw new BillException(BillCoreI18nUtil.getMessage("va.billcore.billdataservice.billdataunqualified"));
        }
        if (create) {
            model.add();
        } else {
            model.loadById(masterData.get(0).get("ID"));
            model.getData().edit();
        }
        model.getData().setTablesData(billData);
        model.getData().save();
        return model.getData().getTablesData();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Map<String, List<Map<String, Object>>> update(BillContext context, String billType, String billCode, Map<String, ? extends DataUpdate> update) {
        BillModel model = this.billDefineService.createModel(context, billType);
        model.loadByCode(billCode);
        model.edit();
        model.getData().setUpdate(update);
        model.save();
        return model.getData().getTablesData();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void delete(BillContext context, String billType, String billCode) {
        CharSequence message;
        List<CheckResult> checkMessages;
        ActionResponse response;
        block6: {
            BillModel model = this.billDefineService.createModel(context, billType);
            model.loadByCode(billCode);
            ActionRequest request = new ActionRequest();
            response = new ActionResponse();
            RulerImpl rulerImpl = (RulerImpl)model.getRuler();
            rulerImpl.getRulerExecutor().setEnable(true);
            checkMessages = new ArrayList();
            try {
                ((ModelImpl)model).executeAction((Action)this.deleteAction, request, response);
            }
            catch (BillException billException) {
                message = billException.getMessage();
                checkMessages = Optional.ofNullable(billException.getCheckMessages()).orElse(new ArrayList());
                if (!StringUtils.hasText((String)message)) break block6;
                CheckResultImpl checkResult = new CheckResultImpl();
                checkResult.setCheckMessage((String)message);
                checkMessages.add((CheckResult)checkResult);
            }
        }
        List checkResults = response.getCheckMessages();
        if (!CollectionUtils.isEmpty(checkResults)) {
            checkMessages.addAll(checkResults);
        }
        if (!checkMessages.isEmpty()) {
            message = new StringBuilder(16);
            for (CheckResult checkMessage : checkMessages) {
                String tempMessage = this.dealCheckMessage(checkMessage.getCheckMessage());
                String formulaName = checkMessage.getFormulaName();
                if (StringUtils.hasText(formulaName)) {
                    ((StringBuilder)message).append(BillCoreI18nUtil.getMessage("va.bill.core.formula")).append(formulaName).append(BillCoreI18nUtil.getMessage("va.bill.core.check.result")).append(tempMessage).append(";");
                    continue;
                }
                ((StringBuilder)message).append(BillCoreI18nUtil.getMessage("va.bill.core.check.result")).append(tempMessage).append(";");
            }
            throw new BillException(((StringBuilder)message).toString(), checkMessages);
        }
    }

    private String dealCheckMessage(String checkMessage) {
        if (!StringUtils.hasText(checkMessage)) {
            return checkMessage;
        }
        if (checkMessage.endsWith("\u3002")) {
            return checkMessage.split("\u3002")[0];
        }
        return checkMessage;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public Map<String, R<?>> batchDelete(BillContext context, Map<String, List<String>> billCodeMap) {
        HashMap result = new HashMap();
        billCodeMap.forEach((k, v) -> {
            BillModel model = this.billDefineService.createModel(context, (String)k);
            for (int i = 0; i < v.size(); ++i) {
                String billCode = (String)v.get(i);
                try {
                    model.deleteByCode(billCode);
                    result.put(billCode, R.ok((Object)BillCoreI18nUtil.getMessage("va.billcore.billdataservice.deletebillsuccess", new Object[]{billCode})));
                    continue;
                }
                catch (Exception e) {
                    result.put(billCode, R.error((String)(BillCoreI18nUtil.getMessage("va.billcore.billdataservice.deletebillfailed", new Object[]{billCode}) + String.format(":%s", e.getMessage())), (Throwable)e));
                }
            }
        });
        return result;
    }

    @Override
    public BillDefine getBillDefineByCode(@RequestBody TenantDO param) {
        if (param.getExtInfo() != null && param.getExtInfo("uniqueCode") != null) {
            return this.billDefineService.getDefine((String)param.getExtInfo("uniqueCode"));
        }
        com.jiuqi.va.domain.common.R r = this.billCodeClient.getUniqueCodeByBillCode(param);
        if (Integer.valueOf(r.get((Object)"code").toString()) != 0) {
            throw new RuntimeException(r.get((Object)"msg").toString());
        }
        String uniqueCode = r.get((Object)"value").toString();
        return this.billDefineService.getDefine(uniqueCode);
    }
}

