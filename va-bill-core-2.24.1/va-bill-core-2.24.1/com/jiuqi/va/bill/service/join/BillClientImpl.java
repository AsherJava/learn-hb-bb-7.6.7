/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionManager
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.action.group.ActionGroup
 *  com.jiuqi.va.biz.intf.action.group.ActionGroupManager
 *  com.jiuqi.va.biz.intf.model.ModelManager
 *  com.jiuqi.va.biz.intf.model.ModelType
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.biz.utils.R
 *  com.jiuqi.va.domain.attachement.AttachmentComponentInfo
 *  com.jiuqi.va.domain.attachement.AttachmentConfigInfo
 *  com.jiuqi.va.domain.attachement.QueryAttachmentInfoParam
 *  com.jiuqi.va.domain.bill.BillDataDTO
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BillClient
 *  com.jiuqi.va.feign.client.BillCodeClient
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.service.join;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.va.bill.action.DeleteAction;
import com.jiuqi.va.bill.controller.BillDataController;
import com.jiuqi.va.bill.controller.BillMetaController;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDataService;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.service.MetaInfoService;
import com.jiuqi.va.bill.service.attachment.BillAttachmentService;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionManager;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.action.group.ActionGroup;
import com.jiuqi.va.biz.intf.action.group.ActionGroupManager;
import com.jiuqi.va.biz.intf.model.ModelManager;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.domain.attachement.AttachmentComponentInfo;
import com.jiuqi.va.domain.attachement.AttachmentConfigInfo;
import com.jiuqi.va.domain.attachement.QueryAttachmentInfoParam;
import com.jiuqi.va.domain.bill.BillDataDTO;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BillClient;
import com.jiuqi.va.feign.client.BillCodeClient;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Primary
public class BillClientImpl
implements BillClient {
    private static final Logger log = LoggerFactory.getLogger(BillClientImpl.class);
    @Autowired
    private BillMetaController BillMetaService;
    @Autowired
    private BillDataController billDataController;
    @Autowired
    private BillDataService billDataService;
    @Autowired
    private BillCodeClient billCodeClient;
    @Autowired
    private BillDefineService billDefineService;
    @Autowired
    private DeleteAction deleteAction;
    @Autowired
    private ActionManager actionManager;
    @Autowired
    private ActionGroupManager actionGroupManager;
    @Autowired
    private ModelManager modelManager;
    @Autowired
    private MetaInfoService metaInfoService;
    @Autowired
    private BillAttachmentService billAttachmentService;

    public R getBillModelType() {
        return this.BillMetaService.getBillModelType();
    }

    public R getBillDefineByCode(TenantDO param) {
        com.jiuqi.va.biz.utils.R<Map<String, BillDefine>> rs = this.billDataController.getBillDefineByCode(param);
        if (rs != null) {
            ObjectMapper o = new ObjectMapper();
            try {
                String writeValueAsString = o.writeValueAsString(rs);
                return (R)JSONUtil.parseObject((String)writeValueAsString, R.class);
            }
            catch (JsonProcessingException e) {
                log.error("\u83b7\u53d6\u5355\u636e\u5b9a\u4e49\u5f02\u5e38", e);
                return R.error();
            }
        }
        return R.error();
    }

    public R load(BillDataDTO params) {
        String billCode = params.getBillCode();
        if (!StringUtils.hasText(billCode)) {
            return R.error((String)BillCoreI18nUtil.getMessage("va.billcore.fieldrequired", new Object[]{"billCode"}));
        }
        String defineCode = params.getDefineCode();
        try {
            if (!StringUtils.hasText(defineCode)) {
                HashMap<String, String> extInfo = new HashMap<String, String>();
                extInfo.put("billCode", billCode);
                TenantDO tenant = new TenantDO();
                tenant.setExtInfo(extInfo);
                R r = this.billCodeClient.getUniqueCodeByBillCode(tenant);
                if (Integer.valueOf(r.get((Object)"code").toString()) != 0) {
                    return R.error((String)r.get((Object)"msg").toString());
                }
                String value = r.get((Object)"value").toString();
                if (!StringUtils.hasText(value)) {
                    return R.error((String)BillCoreI18nUtil.getMessage("va.billcore.billclient.findfailedbybillcode"));
                }
                defineCode = value;
            }
            BillContextImpl contextImpl = new BillContextImpl();
            contextImpl.setDisableVerify(true);
            contextImpl.setVerifyCode(params.getVerifyCode());
            Map<String, List<Map<String, Object>>> data = this.billDataService.load(contextImpl, defineCode, billCode);
            return R.ok(Stream.of(data).collect(Collectors.toMap(o -> "data", o -> data)));
        }
        catch (BillException e) {
            log.error(e.getMessage(), e);
            List<CheckResult> checkMessages = e.getCheckMessages();
            if (checkMessages != null && checkMessages.size() > 0) {
                List collect = checkMessages.stream().map(CheckResult::getCheckMessage).collect(Collectors.toList());
                return R.error((String)StringUtils.arrayToDelimitedString(collect.toArray(), ","));
            }
            return R.error((String)e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    public R delete(BillDataDTO params) {
        String billCode = params.getBillCode();
        if (!StringUtils.hasText(billCode)) {
            return R.error((String)BillCoreI18nUtil.getMessage("va.billcore.fieldrequired", new Object[]{"billCode"}));
        }
        try {
            HashMap<String, String> extInfo = new HashMap<String, String>();
            extInfo.put("billCode", billCode);
            TenantDO tenant = new TenantDO();
            tenant.setExtInfo(extInfo);
            BillDefine billDefine = this.billDataService.getBillDefineByCode(tenant);
            BillContextImpl billContextImpl = new BillContextImpl();
            billContextImpl.setVerifyCode(params.getVerifyCode());
            BillModelImpl model = (BillModelImpl)this.billDefineService.createModel((BillContext)billContextImpl, billDefine);
            model.loadByCode(billCode);
            ActionResponse actionResponse = new ActionResponse();
            model.executeAction((Action)this.deleteAction, new ActionRequest(), actionResponse);
            if (actionResponse.getCheckMessages() != null && actionResponse.getCheckMessages().size() > 0) {
                List collect = actionResponse.getCheckMessages().stream().map(CheckResult::getCheckMessage).collect(Collectors.toList());
                return R.error((String)StringUtils.arrayToDelimitedString(collect.toArray(), ","));
            }
        }
        catch (BillException e) {
            log.error(e.getMessage(), e);
            List<CheckResult> checkMessages = e.getCheckMessages();
            if (checkMessages != null && checkMessages.size() > 0) {
                List collect = checkMessages.stream().map(CheckResult::getCheckMessage).collect(Collectors.toList());
                return R.error((String)StringUtils.arrayToDelimitedString(collect.toArray(), ","));
            }
            return R.error((String)e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)(BillCoreI18nUtil.getMessage("va.billcore.deletefailed_1") + e.getMessage()));
        }
        return R.ok();
    }

    public R add(BillDataDTO params) {
        if (!StringUtils.hasText(params.getDefineCode())) {
            return R.error((String)BillCoreI18nUtil.getMessage("va.billcore.fieldrequired", new Object[]{"defineCode"}));
        }
        BillContextImpl contextImpl = new BillContextImpl();
        contextImpl.setVerifyCode(params.getVerifyCode());
        try {
            Map<String, List<Map<String, Object>>> data = this.billDataService.save(contextImpl, params.getDefineCode(), true, params.getBillData());
            return R.ok(Stream.of(data).collect(Collectors.toMap(o -> "data", o -> data)));
        }
        catch (BillException e) {
            log.error(e.getMessage(), e);
            List<CheckResult> checkMessages = e.getCheckMessages();
            if (checkMessages != null && checkMessages.size() > 0) {
                List collect = checkMessages.stream().map(CheckResult::getCheckMessage).collect(Collectors.toList());
                return R.error((String)StringUtils.arrayToDelimitedString(collect.toArray(), ","));
            }
            return R.error((String)e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    public R update(BillDataDTO params) {
        String billCode = params.getBillCode();
        if (!StringUtils.hasText(billCode)) {
            return R.error((String)BillCoreI18nUtil.getMessage("va.billcore.fieldrequired", new Object[]{"billCode"}));
        }
        String defineCode = params.getDefineCode();
        try {
            if (!StringUtils.hasText(defineCode)) {
                HashMap<String, String> extInfo = new HashMap<String, String>();
                extInfo.put("billCode", billCode);
                TenantDO tenant = new TenantDO();
                tenant.setExtInfo(extInfo);
                R r = this.billCodeClient.getUniqueCodeByBillCode(tenant);
                if (Integer.valueOf(r.get((Object)"code").toString()) != 0) {
                    return R.error((String)r.get((Object)"msg").toString());
                }
                String value = r.get((Object)"value").toString();
                if (!StringUtils.hasText(value)) {
                    return R.error((String)BillCoreI18nUtil.getMessage("va.billcore.billclient.findfailedbybillcode"));
                }
                defineCode = value;
            }
            BillContextImpl contextImpl = new BillContextImpl();
            contextImpl.setDisableVerify(true);
            contextImpl.setVerifyCode(params.getVerifyCode());
            Map<String, List<Map<String, Object>>> data = this.billDataService.save(contextImpl, defineCode, false, params.getBillData());
            return R.ok(Stream.of(data).collect(Collectors.toMap(o -> "data", o -> data)));
        }
        catch (BillException e) {
            log.error(e.getMessage(), e);
            List<CheckResult> checkMessages = e.getCheckMessages();
            if (checkMessages != null && checkMessages.size() > 0) {
                List collect = checkMessages.stream().map(CheckResult::getCheckMessage).collect(Collectors.toList());
                return R.error((String)StringUtils.arrayToDelimitedString(collect.toArray(), ","));
            }
            return R.error((String)e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    public R getMasterTableName(BillDataDTO params) {
        Map<String, Object> tablesName;
        String defineCode = params.getDefineCode();
        String billCode = params.getBillCode();
        if (!StringUtils.hasText(defineCode) && !StringUtils.hasText(billCode)) {
            return R.error((String)"\u5355\u636e\u7f16\u53f7\u548c\u5355\u636e\u5b9a\u4e49\u6807\u8bc6\u4e0d\u80fd\u90fd\u4e3a\u7a7a");
        }
        if (!StringUtils.hasText(defineCode)) {
            HashMap<String, String> extInfo = new HashMap<String, String>();
            extInfo.put("billCode", billCode);
            TenantDO tenant = new TenantDO();
            tenant.setExtInfo(extInfo);
            R r = this.billCodeClient.getUniqueCodeByBillCode(tenant);
            if (Integer.valueOf(r.get((Object)"code").toString()) != 0) {
                return R.error((String)r.get((Object)"msg").toString());
            }
            String value = r.get((Object)"value").toString();
            if (!StringUtils.hasText(value)) {
                return R.error((String)"\u6839\u636e\u5355\u636e\u7f16\u53f7\u67e5\u8be2\u5355\u636e\u5b9a\u4e49\u5931\u8d25");
            }
            defineCode = value;
        }
        if (CollectionUtils.isEmpty(tablesName = this.metaInfoService.getTablesName(defineCode))) {
            return R.error((String)"\u83b7\u53d6\u4e3b\u8868\u540d\u79f0\u5931\u8d25");
        }
        String tablename = tablesName.get("name").toString();
        return R.ok().put("data", (Object)tablename);
    }

    public R getBillActionsByModelName(String modelName) {
        ArrayList result = new ArrayList();
        List actions = this.actionManager.getActionList(((ModelType)this.modelManager.get(modelName)).getModelClass());
        actions.sort((o1, o2) -> o1.getSortFlag() > o2.getSortFlag() ? 1 : -1);
        actions.stream().filter(o -> !o.isInner()).forEach(action -> {
            ActionGroup actionGroup = this.actionGroupManager.getActionGroupById(action.getGroupId());
            if (actionGroup == null) {
                actionGroup = this.actionGroupManager.getActionGroupById("00");
            }
            HashMap<String, String> tableObject = new HashMap<String, String>();
            result.add(tableObject);
            tableObject.put("name", action.getName());
            tableObject.put("title", action.getTitle());
            tableObject.put("icon", action.getIcon());
            tableObject.put("groupTitle", actionGroup.getTitle());
            tableObject.put("groupName", actionGroup.getName());
        });
        HashMap map = new HashMap();
        map.put("data", result);
        return R.ok(map);
    }

    public R listAttachmentTableFieldConfig(QueryAttachmentInfoParam param) {
        try {
            List<AttachmentConfigInfo> configInfos = this.billAttachmentService.listAttachmentTableFieldConfig(param);
            R r = R.ok();
            r.put("data", configInfos);
            return r;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            return R.error((String)e.getMessage());
        }
    }

    public List<AttachmentComponentInfo> queryAttachmentComponentInfo(TenantDO param) {
        return this.billAttachmentService.queryAttachmentComponentInfo((String)param.getExtInfo("defineCode"));
    }
}

