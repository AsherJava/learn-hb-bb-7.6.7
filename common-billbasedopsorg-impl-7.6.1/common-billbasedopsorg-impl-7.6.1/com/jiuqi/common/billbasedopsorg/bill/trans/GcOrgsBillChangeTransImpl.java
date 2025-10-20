/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.ReflectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.bill.action.SaveAction
 *  com.jiuqi.va.bill.domain.ChangeBillInfoDO
 *  com.jiuqi.va.bill.domain.TriggerTypeEnum
 *  com.jiuqi.va.bill.domain.VaBillChangeTransMessageDTO
 *  com.jiuqi.va.bill.impl.BillContextImpl
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillContext
 *  com.jiuqi.va.bill.intf.BillDefineService
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.bill.intf.BillState
 *  com.jiuqi.va.bill.service.ChangeBillInfoService
 *  com.jiuqi.va.bill.trans.VaBillChangeTransIntf
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.model.ModelType
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.organization.service.OrgDataService
 *  com.jiuqi.va.organization.service.impl.help.OrgDataParamService
 *  org.apache.commons.collections4.MapUtils
 */
package com.jiuqi.common.billbasedopsorg.bill.trans;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.ReflectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.billbasedopsorg.bill.model.GcBillPushOrgsModelType;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.bill.action.SaveAction;
import com.jiuqi.va.bill.domain.ChangeBillInfoDO;
import com.jiuqi.va.bill.domain.TriggerTypeEnum;
import com.jiuqi.va.bill.domain.VaBillChangeTransMessageDTO;
import com.jiuqi.va.bill.impl.BillContextImpl;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillContext;
import com.jiuqi.va.bill.intf.BillDefineService;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillState;
import com.jiuqi.va.bill.service.ChangeBillInfoService;
import com.jiuqi.va.bill.trans.VaBillChangeTransIntf;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.model.ModelType;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.organization.service.OrgDataService;
import com.jiuqi.va.organization.service.impl.help.OrgDataParamService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcOrgsBillChangeTransImpl
implements VaBillChangeTransIntf {
    @Autowired
    private ChangeBillInfoService changeBillInfoService;
    @Autowired
    private OrgDataService orgDataService;
    @Autowired
    private OrgDataParamService orgDataParamService;

    public String getName() {
        return "BillChangePushOrgs";
    }

    public String getDesc() {
        return "\u53d8\u66f4\u5355\u5ba1\u6279\u540e\u540c\u6b65\u7ec4\u7ec7\u673a\u6784";
    }

    public Class<? extends ModelType> getModelType() {
        return GcBillPushOrgsModelType.class;
    }

    public int getOrder() {
        return 0;
    }

    public TriggerTypeEnum getTriggerType() {
        return TriggerTypeEnum.AFTER_WRITE;
    }

    public boolean execute(VaBillChangeTransMessageDTO vaBillChangeTransMessageDTO) {
        try {
            this.syncOrg(vaBillChangeTransMessageDTO);
        }
        catch (Exception e) {
            logger.error("\u53d8\u66f4\u5355\u5ba1\u6279\u540e\u540c\u6b65\u7ec4\u7ec7\u673a\u6784\u9519\u8bef:" + e.getMessage(), e);
            return true;
        }
        return true;
    }

    private void syncOrg(VaBillChangeTransMessageDTO vaBillChangeTransMessageDTO) {
        Set updateIds;
        Map updateSubItemInfos;
        List<ChangeBillInfoDO> changeInfos = this.getChangeBillInfoDOS(vaBillChangeTransMessageDTO.getBillDefine(), vaBillChangeTransMessageDTO.getBillCode());
        if (CollectionUtils.isEmpty(changeInfos)) {
            logger.info("\u540c\u6b65\u7ec4\u7ec7\u673a\u6784\u4fe1\u606f\uff1a\u672a\u67e5\u627e\u5230\u53d8\u66f4\u4fe1\u606f");
            return;
        }
        Map<String, Object> subChangeInfo = this.getSubChangeInfo(changeInfos);
        ArrayList<Map<String, Object>> needSyncOrgs = new ArrayList<Map<String, Object>>();
        List deleteSubItems = (List)subChangeInfo.get("delete");
        if (!CollectionUtils.isEmpty((Collection)deleteSubItems)) {
            deleteSubItems.stream().forEach(item -> item.put("ORG_recoveryflag", 1));
            needSyncOrgs.addAll(deleteSubItems);
        }
        BillModelImpl srcBillModal = this.getModel(vaBillChangeTransMessageDTO.getSrcBillDefine());
        srcBillModal.loadByCode(vaBillChangeTransMessageDTO.getSrcBillCode());
        List subItems = srcBillModal.getTable("GC_BILLPUSHORGSITEM").getRowsData();
        List insertIds = (List)subChangeInfo.get("insert");
        if (!CollectionUtils.isEmpty((Collection)insertIds)) {
            List addSubItems = subItems.stream().filter(item -> insertIds.contains(item.get("ID").toString())).collect(Collectors.toList());
            needSyncOrgs.addAll(addSubItems);
        }
        if (MapUtils.isNotEmpty((Map)(updateSubItemInfos = (Map)subChangeInfo.get("update"))) && !CollectionUtils.isEmpty(updateSubItemInfos.keySet()) && !CollectionUtils.isEmpty(updateIds = updateSubItemInfos.keySet())) {
            List updateSubItems = subItems.stream().filter(item -> updateIds.contains(item.get("ID").toString())).collect(Collectors.toList());
            needSyncOrgs.addAll(updateSubItems);
        }
        this.pushToOrg(srcBillModal, needSyncOrgs);
    }

    private Map<String, Object> getSubChangeInfo(List<ChangeBillInfoDO> changeInfos) {
        Map changeData = JSONUtil.parseMap((String)changeInfos.get(0).getChangedata());
        Map subChangeInfo = (Map)changeData.get("GC_BILLPUSHORGSITEM");
        return subChangeInfo;
    }

    private List<ChangeBillInfoDO> getChangeBillInfoDOS(String billDefine, String billCode) {
        BillModelImpl bgdModel = this.getModel(billDefine);
        bgdModel.loadByCode(billCode);
        String bgdMasterId = bgdModel.getMaster().getId().toString();
        ChangeBillInfoDO changeBillInfoDO = new ChangeBillInfoDO();
        changeBillInfoDO.setId(bgdMasterId);
        List changeInfos = this.changeBillInfoService.getChangeInfo(changeBillInfoDO);
        return changeInfos;
    }

    private void pushToOrg(BillModelImpl srcBillModal, List<Map<String, Object>> orgItemList) {
        Map masterData = srcBillModal.getMaster().getData();
        int allowPushOrg = ConverterUtils.getAsIntValue(masterData.get("ALLOWPUSHORG"));
        if (allowPushOrg == 1) {
            return;
        }
        String orgType = null == masterData.get("SYNCORGTYPE") ? "MD_ORG" : ConverterUtils.getAsString(masterData.get("SYNCORGTYPE"));
        boolean syncMdOrgFlag = orgType.equalsIgnoreCase("MD_ORG");
        Set<String> mdOrgCodeSet = this.initMdOrgCodeSet();
        OrgDTO orgDTO = new OrgDTO();
        this.initOrgDTO(orgDTO, orgType, masterData);
        StringBuilder pushOrgLogs = new StringBuilder();
        for (Map<String, Object> orgItem : orgItemList) {
            OrgDTO orgDTONew = new OrgDTO();
            BeanUtils.copyProperties(orgDTO, orgDTONew);
            orgItem.forEach((fieldCode, fieldValue) -> {
                if (fieldCode.startsWith("ORG_") && fieldValue != null) {
                    if (fieldValue instanceof Map) {
                        fieldValue = ((Map)fieldValue).get("name");
                    }
                    ReflectionUtils.setFieldValue((Object)orgDTONew, (String)fieldCode.substring(4).toLowerCase(), (Object)fieldValue);
                }
            });
            OrgDataParamService orgDataParamService = (OrgDataParamService)SpringContextUtils.getBean(OrgDataParamService.class);
            orgDTONew.setForceUpdateHistoryVersionData(Boolean.valueOf(true));
            R rs = orgDataParamService.checkModify(orgDTONew, false);
            if (rs.getCode() != 0) {
                logger.error(orgDTO.getCode() + "\u65e0\u6cd5\u8fdb\u884c\u540c\u6b65\uff0c\u539f\u56e0\u4e3a\uff1a" + rs.getMsg());
                pushOrgLogs.append(orgDTO.getCode()).append("\u65e0\u6cd5\u8fdb\u884c\u540c\u6b65\uff0c\u539f\u56e0\u4e3a\uff1a").append(rs.getMsg()).append("\n");
                continue;
            }
            OrgDO oldOrg = (OrgDO)rs.get((Object)"oldOrg");
            boolean addFlag = true;
            if (oldOrg != null && oldOrg.getRecoveryflag() == 0) {
                addFlag = false;
            }
            if (addFlag) {
                this.addOrg(this.orgDataService, syncMdOrgFlag, mdOrgCodeSet, orgDTONew, pushOrgLogs);
                continue;
            }
            boolean parentcodeUnchangedFlag = this.isParentcodeUnchangedFlag(orgDTONew, oldOrg);
            this.updateOrg(this.orgDataService, orgDTONew, pushOrgLogs, parentcodeUnchangedFlag);
        }
        this.appendLogs(srcBillModal, pushOrgLogs);
    }

    private void appendLogs(BillModelImpl srcBillModal, StringBuilder pushOrgLogs) {
        if (pushOrgLogs != null && pushOrgLogs.length() > 0) {
            srcBillModal.getMaster().setValue("BILLSTATE", (Object)BillState.AUDITPASSEDCANEDIT);
            srcBillModal.edit();
            srcBillModal.getMaster().setValue("SYNCLOG", (Object)("\u540c\u6b65\u7ec4\u7ec7\u673a\u6784\u65e5\u5fd7\u4e3a\uff1a" + pushOrgLogs));
            try {
                ActionRequest request = new ActionRequest();
                request.setParams(new HashMap());
                ActionResponse response = new ActionResponse();
                SaveAction saveAction = (SaveAction)SpringContextUtils.getBean(SaveAction.class);
                srcBillModal.executeAction((Action)saveAction, request, response);
            }
            catch (BillException e) {
                List checkMessages = e.getCheckMessages();
                String checkMessage = CollectionUtils.isEmpty((Collection)checkMessages) ? "" : "\u6821\u9a8c\u7ed3\u679c:" + checkMessages.stream().map(item -> item.getCheckMessage()).collect(Collectors.joining(";"));
                logger.error("\u6e90\u5355\u4fdd\u5b58\u65e5\u5fd7\u4fe1\u606f\u65f6\u5931\u8d25:" + checkMessage + e.getMessage(), e);
            }
            catch (Exception e) {
                logger.error("\u6e90\u5355\u4fdd\u5b58\u65e5\u5fd7\u4fe1\u606f\u65f6\u5931\u8d25:" + e.getMessage(), e);
            }
            LogHelper.info((String)"\u5408\u5e76-\u6279\u91cf\u751f\u6210\u7ec4\u7ec7\u673a\u6784", (String)"\u540c\u6b65\u7ec4\u7ec7\u673a\u6784", (String)pushOrgLogs.toString());
        }
    }

    private boolean isParentcodeUnchangedFlag(OrgDTO orgDTONew, OrgDO oldOrg) {
        boolean parentcodeUnchangedFlag = true;
        if (orgDTONew.getParentcode() == null && oldOrg.get((Object)"parentcode") != null) {
            return false;
        }
        if (orgDTONew.getParentcode() != null && oldOrg.get((Object)"parentcode") == null) {
            return false;
        }
        if (orgDTONew.getParentcode() != null && oldOrg.get((Object)"parentcode") != null) {
            parentcodeUnchangedFlag = orgDTONew.getParentcode().equals(oldOrg.get((Object)"parentcode"));
        }
        return parentcodeUnchangedFlag;
    }

    private BillModelImpl getModel(String modelDefine) {
        BillContextImpl billContext = new BillContextImpl();
        billContext.setTenantName(ShiroUtil.getTenantName());
        billContext.setDisableVerify(true);
        BillDefineService billDefineService = (BillDefineService)SpringContextUtils.getBean(BillDefineService.class);
        BillModelImpl model = (BillModelImpl)billDefineService.createModel((BillContext)billContext, modelDefine);
        model.getRuler().getRulerExecutor().setEnable(true);
        return model;
    }

    private Set<String> initMdOrgCodeSet() {
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname("MD_ORG");
        orgDTO.setQueryDataStructure(OrgDataOption.QueryDataStructure.ALL);
        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
        List orgDOList = this.orgDataService.list(orgDTO).getRows();
        return orgDOList.stream().map(orgDO -> orgDO.getCode()).collect(Collectors.toSet());
    }

    private void notMdOrgAdd(OrgDataService orgDataService, OrgDTO orgDTO, StringBuilder pushOrgLogs) {
        orgDTO.put("ignoreCategoryAdd", (Object)true);
        R resultInfo = orgDataService.add(orgDTO);
        if (resultInfo.getCode() == 0) {
            logger.info("\u7ec4\u7ec7\u673a\u6784\u65b0\u589e\u6210\u529f");
        } else {
            logger.error(orgDTO.getCode() + "\u5355\u4f4d\u65b0\u589e\u5931\u8d25\uff1a" + resultInfo.getMsg());
            pushOrgLogs.append(orgDTO.getCode()).append("\u5355\u4f4d\u65b0\u589e\u5931\u8d25\uff1a").append(resultInfo.getMsg()).append("\n");
        }
    }

    private void addOrg(OrgDataService orgDataService, boolean syncMdOrgFlag, Set<String> mdOrgCodeSet, OrgDTO orgDTO, StringBuilder pushOrgLogs) {
        try {
            if (syncMdOrgFlag) {
                orgDTO.setVersionDate(new Date());
                orgDataService.add(orgDTO);
            } else if (mdOrgCodeSet.contains(orgDTO.getCode())) {
                this.notMdOrgAdd(orgDataService, orgDTO, pushOrgLogs);
            } else {
                this.mdOrgAdd(orgDataService, orgDTO, pushOrgLogs);
                this.notMdOrgAdd(orgDataService, orgDTO, pushOrgLogs);
            }
        }
        catch (Exception e) {
            logger.error(orgDTO.getCode() + "\u5355\u4f4d\u4fe1\u606f\u65b0\u589e\u5931\u8d25\uff1a" + e.getMessage(), e);
            pushOrgLogs.append(orgDTO.getCode()).append("\u5355\u4f4d\u4fe1\u606f\u4fee\u6539\u5931\u8d25\uff1a").append(e.getMessage()).append("\n");
        }
    }

    private void updateOrg(OrgDataService orgDataService, OrgDTO orgDTO, StringBuilder pushOrgLogs, boolean parentcodeUnchanged) {
        try {
            R resultInfo;
            if (orgDTO.getRecoveryflag() != null && orgDTO.getRecoveryflag() == 1) {
                orgDTO.put("_updateBizType", (Object)"changeRecoveryflag");
            }
            if ((resultInfo = parentcodeUnchanged ? orgDataService.update(orgDTO) : orgDataService.move(orgDTO)).getCode() == 0) {
                logger.info("\u7ec4\u7ec7\u673a\u6784\u4fee\u6539\u6210\u529f");
            } else {
                logger.error(orgDTO.getCode() + "\u5355\u4f4d\u4fe1\u606f\u4fee\u6539\u5931\u8d25\uff1a" + resultInfo.getMsg());
                pushOrgLogs.append(orgDTO.getCode()).append("\u5355\u4f4d\u4fe1\u606f\u4fee\u6539\u5931\u8d25\uff1a").append(resultInfo.getMsg()).append("\n");
            }
        }
        catch (Exception e) {
            logger.error(orgDTO.getCode() + "\u5355\u4f4d\u4fe1\u606f\u4fee\u6539\u5931\u8d25\uff1a" + e.getMessage(), e);
            pushOrgLogs.append(orgDTO.getCode()).append("\u5355\u4f4d\u4fe1\u606f\u4fee\u6539\u5931\u8d25\uff1a").append(e.getMessage()).append("\n");
        }
    }

    private void mdOrgAdd(OrgDataService orgDataService, OrgDTO orgDTO, StringBuilder pushOrgLogs) {
        OrgDTO mdOrg = new OrgDTO();
        BeanUtils.copyProperties(orgDTO, mdOrg);
        mdOrg.setCategoryname("MD_ORG");
        mdOrg.setVersionDate(new Date());
        R resultInfo = orgDataService.add(mdOrg);
        if (resultInfo.getCode() == 0) {
            logger.info("\u884c\u653f\u7ec4\u7ec7\u65b0\u589e\u6210\u529f");
        } else {
            logger.error("\u884c\u653f\u7ec4\u7ec7" + orgDTO.getCode() + "\u5355\u4f4d\u65b0\u589e\u5931\u8d25\uff1a" + resultInfo.getMsg());
            pushOrgLogs.append("\u884c\u653f\u7ec4\u7ec7").append(orgDTO.getCode()).append("\u5355\u4f4d\u65b0\u589e\u5931\u8d25\uff1a").append(resultInfo.getMsg()).append("\n");
        }
    }

    private void initOrgDTO(OrgDTO orgDTO, String orgType, Map<String, Object> masterData) {
        orgDTO.setCategoryname(orgType);
        if (orgType.equalsIgnoreCase("MD_ORG")) {
            orgDTO.setVersionDate(new Date());
            return;
        }
        String period = ConverterUtils.getAsString((Object)masterData.get("SYNCPERIOD"));
        if (StringUtils.isEmpty((String)period)) {
            orgDTO.setVersionDate(new Date());
        } else {
            String[] split = period.split("-");
            if (split.length == 2) {
                orgDTO.setVersionDate(DateUtils.firstDateOf((int)Integer.parseInt(split[0]), (int)Integer.parseInt(split[1])));
            } else {
                orgDTO.setVersionDate(new Date());
            }
        }
    }
}

