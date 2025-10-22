/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO
 *  com.jiuqi.nvwa.login.service.NvwaLoginService
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.bill.intf.BillDataService
 *  com.jiuqi.va.bill.intf.BillDefine
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.bill.utils.BillCoreI18nUtil
 *  com.jiuqi.va.biz.intf.action.Action
 *  com.jiuqi.va.biz.intf.action.ActionManager
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.value.Convert
 *  com.jiuqi.va.biz.ruler.intf.CheckResult
 *  com.jiuqi.va.bizmeta.service.IMetaInfoService
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.domain.meta.MetaInfoDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.gcreport.clbrbill.utils;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.nvwa.login.domain.NvwaLoginUserDTO;
import com.jiuqi.nvwa.login.service.NvwaLoginService;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.bill.intf.BillDataService;
import com.jiuqi.va.bill.intf.BillDefine;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.utils.BillCoreI18nUtil;
import com.jiuqi.va.biz.intf.action.Action;
import com.jiuqi.va.biz.intf.action.ActionManager;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.value.Convert;
import com.jiuqi.va.biz.ruler.intf.CheckResult;
import com.jiuqi.va.bizmeta.service.IMetaInfoService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.domain.meta.MetaInfoDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClbrBillUtils {
    private static final Logger logger = LoggerFactory.getLogger(ClbrBillUtils.class);

    private ClbrBillUtils() {
    }

    public static MetaInfoDTO getMetaInfoByClbrCode(String clbrCode) {
        BillDefine billDefine = ClbrBillUtils.getBillDefineByClbrCode(clbrCode);
        IMetaInfoService metaInfoService = (IMetaInfoService)SpringContextUtils.getBean(IMetaInfoService.class);
        MetaInfoDTO metaData = metaInfoService.getMetaInfoByUniqueCode(billDefine.getName());
        if (Objects.isNull(metaData)) {
            throw new BusinessRuntimeException("\u5173\u8054\u4ea4\u6613\u5e73\u53f0\u672a\u627e\u5230\u5355\u636e\u5b9a\u4e49\uff1a" + billDefine.getName());
        }
        return metaData;
    }

    public static BillDefine getBillDefineByClbrCode(String clbrCode) {
        TenantDO param = new TenantDO();
        HashMap<String, String> extInfo = new HashMap<String, String>();
        param.setExtInfo(extInfo);
        extInfo.put("billCode", clbrCode);
        BillDataService billDataService = (BillDataService)SpringContextUtils.getBean(BillDataService.class);
        return billDataService.getBillDefineByCode(param);
    }

    public static MetaInfoDTO getMetaInfo(String name) {
        IMetaInfoService metaInfoService = (IMetaInfoService)SpringContextUtils.getBean(IMetaInfoService.class);
        MetaInfoDTO metaData = metaInfoService.findMetaData("GCBILL", "bill", name);
        if (Objects.isNull(metaData)) {
            throw new BusinessRuntimeException("\u534f\u540c\u5e73\u53f0\u672a\u627e\u5230\u5355\u636e\u5b9a\u4e49\uff1a" + name);
        }
        return metaData;
    }

    public static String getToken(String userName) {
        NvwaLoginUserDTO userDTO = new NvwaLoginUserDTO();
        userDTO.setUsername(userName);
        userDTO.setTenant("__default_tenant__");
        userDTO.setCheckPwd(false);
        NvwaLoginService loginService = (NvwaLoginService)SpringContextUtils.getBean(NvwaLoginService.class);
        R r = loginService.tryLogin(userDTO, true);
        if (r.getCode() != 0) {
            throw new BusinessRuntimeException("\u7528\u6237\u3010" + userName + "\u3011\u6a21\u62df\u767b\u9646\u5931\u8d25:" + r.getMsg());
        }
        return r.get((Object)"token").toString();
    }

    public static List<DataModelColumn> mergeDataModel(DataModelDO origalDataModel, DataModelDO dataModelDO) {
        if (origalDataModel == null || origalDataModel.getColumns() == null) {
            return dataModelDO == null ? null : dataModelDO.getColumns();
        }
        if (dataModelDO == null || dataModelDO.getColumns() == null) {
            return origalDataModel.getColumns();
        }
        Set oldCols = dataModelDO.getColumns().stream().map(DataModelColumn::getColumnName).collect(Collectors.toSet());
        List newCol = origalDataModel.getColumns().stream().filter(item -> !oldCols.contains(item.getColumnName().toUpperCase(Locale.ROOT))).collect(Collectors.toList());
        List columns = dataModelDO.getColumns();
        columns.addAll(newCol);
        return columns;
    }

    public static void saveModel(BillModelImpl model) {
        model.getRuler().getRulerExecutor().setEnable(true);
        try {
            List checkMessages;
            long oldVer = model.getMaster().getVersion();
            long newVer = (Long)Convert.cast(((Map)((List)model.getData().getTablesData().get(model.getMasterTable().getName())).get(0)).get("VER"), Long.TYPE);
            if (oldVer != newVer) {
                throw new BillException(BillCoreI18nUtil.getMessage((String)"va.billcore.billeditservice.datachangerefresh"));
            }
            ActionManager actionManager = (ActionManager)SpringContextUtils.getBean(ActionManager.class);
            Action action = (Action)actionManager.get("bill-save");
            ActionRequest request = new ActionRequest();
            HashMap params = new HashMap();
            request.setParams(params);
            ActionResponse response = new ActionResponse();
            model.executeAction(action, request, response);
            if (!response.isSuccess() && !CollectionUtils.isEmpty((Collection)(checkMessages = response.getCheckMessages()))) {
                List collect = checkMessages.stream().map(CheckResult::getCheckMessage).collect(Collectors.toList());
                String msg = org.springframework.util.StringUtils.arrayToDelimitedString(collect.toArray(), ",");
                logger.info("\u5355\u636e\u4fdd\u5b58\u5931\u8d25\uff1a{}", (Object)msg);
                throw new BusinessRuntimeException(msg);
            }
        }
        catch (BillException e) {
            String msg;
            logger.info(e.getMessage(), e);
            List checkMessages = e.getCheckMessages();
            if (!CollectionUtils.isEmpty((Collection)checkMessages)) {
                List collect = checkMessages.stream().map(CheckResult::getCheckMessage).collect(Collectors.toList());
                msg = BillCoreI18nUtil.getMessage((String)"va.billcore.saveaction.savefailed") + org.springframework.util.StringUtils.arrayToDelimitedString(collect.toArray(), ",");
                logger.info(msg);
            } else {
                msg = e.getMessage();
            }
            throw new BusinessRuntimeException(msg, (Throwable)e);
        }
        catch (Exception e) {
            logger.info(BillCoreI18nUtil.getMessage((String)"va.billcore.saveaction.savefailed") + e.getMessage(), e);
            throw new BusinessRuntimeException((Throwable)e);
        }
        finally {
            model.getRuler().getRulerExecutor().setEnable(false);
        }
    }

    public static GcBaseData queryBillMappingBaseData(String srcBillDefine, String srcBusinessType) {
        if (StringUtils.isNull((String)srcBillDefine)) {
            srcBillDefine = "";
        }
        if (StringUtils.isNull((String)srcBusinessType)) {
            srcBusinessType = "";
        }
        String billMappingCode = srcBillDefine + "|" + srcBusinessType;
        return GcBaseDataCenterTool.getInstance().queryBasedataByCode("MD_CLBRMAPPINGSCHEME", billMappingCode.toUpperCase(Locale.ROOT));
    }
}

