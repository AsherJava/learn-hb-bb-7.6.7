/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.constant.RequestSourceTypeEnum
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.gcreport.bde.common.utils.BdeClientUtil
 *  com.jiuqi.gcreport.billextract.client.BillExtractSettingClient
 *  com.jiuqi.gcreport.billextract.client.dto.BillSchemeConfigDTO
 *  com.jiuqi.va.bill.impl.BillActionBase
 *  com.jiuqi.va.bill.intf.BillConsts
 *  com.jiuqi.va.bill.intf.BillModel
 */
package com.jiuqi.gcreport.bde.bill.extract.impl.action;

import com.jiuqi.bde.common.constant.RequestSourceTypeEnum;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.gcreport.bde.bill.extract.impl.intf.BillExtractHandleMessage;
import com.jiuqi.gcreport.bde.bill.extract.impl.service.BillExtractExecuteService;
import com.jiuqi.gcreport.bde.common.utils.BdeClientUtil;
import com.jiuqi.gcreport.billextract.client.BillExtractSettingClient;
import com.jiuqi.gcreport.billextract.client.dto.BillSchemeConfigDTO;
import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillConsts;
import com.jiuqi.va.bill.intf.BillModel;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

public class GcBillExtractAction
extends BillActionBase {
    @Autowired
    private BillExtractExecuteService extractExecuteService;
    @Autowired
    private BillExtractSettingClient settingClient;
    private String FN_PARAM_FETCHDATE_FD = "FETCHDATE";

    public String getName() {
        return "hnzyExtractAction";
    }

    public String getTitle() {
        return "HNZY-\u53d6\u6570";
    }

    public void execute(BillModel model, Map<String, Object> params) {
        String fetchDateFd = this.parseFetchDateFd(params);
        String defineName = model.getDefine().getName();
        String unitCode = model.getMaster().getString("UNITCODE");
        String fetchDate = DateUtils.format((Date)model.getMaster().getDate(fetchDateFd));
        Assert.isNotEmpty((String)defineName, (String)"\u5355\u636e\u5b9a\u4e49\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        Assert.isNotEmpty((String)unitCode, (String)"\u7ec4\u7ec7\u673a\u6784\u4e0d\u80fd\u4e3a\u7a7a", (Object[])new Object[0]);
        BillSchemeConfigDTO schemeConfig = (BillSchemeConfigDTO)BdeClientUtil.parseResponse((BusinessResponseEntity)this.settingClient.getSchemeByOrgId(defineName, unitCode));
        if (schemeConfig == null || schemeConfig.getFetchSchemeId() == null) {
            throw new BusinessRuntimeException(String.format("\u5355\u4f4d\u3010%1$s\u3011\u6ca1\u6709\u83b7\u53d6\u5230\u53d6\u6570\u65b9\u6848\uff0c\u8bf7\u68c0\u67e5\u53d6\u6570\u65b9\u6848\u914d\u7f6e", unitCode));
        }
        BillExtractHandleMessage handleMessage = new BillExtractHandleMessage();
        String id = UUIDUtils.newHalfGUIDStr();
        handleMessage.setRequestRunnerId(id);
        handleMessage.setRequestInstcId(id);
        handleMessage.setBblx("1");
        handleMessage.setBillDefine(model.getDefine().getName());
        handleMessage.setUnitCode(unitCode);
        handleMessage.setStartDateStr(fetchDate);
        handleMessage.setEndDateStr(fetchDate);
        handleMessage.setUsername(model.getContext().getUserCode());
        handleMessage.setMasterTableName(model.getMasterTable().getDefine().getName());
        handleMessage.setFetchSchemeId(schemeConfig.getFetchSchemeId());
        handleMessage.setRequestSourceType(RequestSourceTypeEnum.BILL_FETCH.getCode());
        handleMessage.setIncludeUncharged(true);
        this.extractExecuteService.doExecute(model, handleMessage);
    }

    private String parseFetchDateFd(Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return "BILLDATE";
        }
        String fetchDateFd = (String)params.get(this.FN_PARAM_FETCHDATE_FD);
        if (StringUtils.isEmpty((String)fetchDateFd)) {
            return "BILLDATE";
        }
        return fetchDateFd;
    }

    public String[] getModelParams() {
        return BillConsts.ACTION_PARAM_DEFINE;
    }
}

