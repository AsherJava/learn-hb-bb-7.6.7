/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.impl.BillActionBase
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.biz.impl.data.DataDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataFieldDefineImpl
 *  com.jiuqi.va.biz.impl.data.DataTableDefineImpl
 *  com.jiuqi.va.biz.impl.value.NamedContainerImpl
 *  com.jiuqi.va.biz.intf.action.ActionRequest
 *  com.jiuqi.va.biz.intf.action.ActionResponse
 *  com.jiuqi.va.biz.intf.model.Model
 *  com.jiuqi.va.biz.intf.model.Plugin
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.enumdata.EnumDataDO
 *  com.jiuqi.va.domain.enumdata.EnumDataDTO
 *  com.jiuqi.va.domain.org.OrgDO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.EnumDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 */
package com.jiuqi.va.bill.bd.bill.action;

import com.jiuqi.va.bill.bd.bill.model.RegistrationBillModel;
import com.jiuqi.va.bill.bd.core.domain.BillChangeRecordDO;
import com.jiuqi.va.bill.bd.core.domain.BillChangeRecordVO;
import com.jiuqi.va.bill.bd.core.service.BillChangeRecordService;
import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.biz.impl.data.DataDefineImpl;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.impl.data.DataTableDefineImpl;
import com.jiuqi.va.biz.impl.value.NamedContainerImpl;
import com.jiuqi.va.biz.intf.action.ActionRequest;
import com.jiuqi.va.biz.intf.action.ActionResponse;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.enumdata.EnumDataDO;
import com.jiuqi.va.domain.enumdata.EnumDataDTO;
import com.jiuqi.va.domain.org.OrgDO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.EnumDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BillChangeRecordAction
extends BillActionBase {
    @Autowired
    private BillChangeRecordService billChangeRecordService;
    @Autowired
    private BaseDataClient baseDataClient;
    @Autowired
    private OrgDataClient orgDataClient;
    @Autowired
    private EnumDataClient enumDataClient;

    public String getName() {
        return "va-bill-chgrecord";
    }

    public String getTitle() {
        return "\u53d8\u66f4\u8bb0\u5f55";
    }

    public String getActionPriority() {
        return "099";
    }

    public Class<? extends BillModel> getDependModel() {
        return RegistrationBillModel.class;
    }

    public void invoke(Model model, ActionRequest request, ActionResponse response) {
        BillModel billModel = (BillModel)model;
        BillChangeRecordDO billChangeRecordDO = new BillChangeRecordDO();
        billChangeRecordDO.setBillcode(billModel.getMaster().getString("BILLCODE"));
        List<BillChangeRecordVO> billChangeRecordVOS = this.billChangeRecordService.listByBillCode(billChangeRecordDO);
        DataDefineImpl data = (DataDefineImpl)((Plugin)billModel.getPlugins().get("data")).getDefine();
        NamedContainerImpl fields = ((DataTableDefineImpl)data.getTables().getMasterTable()).getFields();
        for (BillChangeRecordVO billChangeRecordVO : billChangeRecordVOS) {
            for (BillChangeRecordDO record : billChangeRecordVO.getRecords()) {
                EnumDataDTO enumDataDTO;
                List list;
                String changefiledname = record.getChangefiledname();
                DataFieldDefineImpl dataFieldDefine = (DataFieldDefineImpl)fields.get(changefiledname);
                if (dataFieldDefine.getRefTableType() == 1) {
                    BaseDataDTO baseDataDTO;
                    if (StringUtils.hasText(record.getChangebefore())) {
                        baseDataDTO = new BaseDataDTO();
                        baseDataDTO.setTableName(dataFieldDefine.getRefTableName());
                        baseDataDTO.setObjectcode(record.getChangebefore());
                        baseDataDTO.setStopflag(Integer.valueOf(-1));
                        baseDataDTO.setVersionDate(record.getCreatetime());
                        baseDataDTO.setAuthType(BaseDataOption.AuthType.NONE);
                        list = this.baseDataClient.list(baseDataDTO);
                        if (list.getRows() != null && list.getRows().size() == 1) {
                            record.setChangebefore(((BaseDataDO)list.getRows().get(0)).getShowTitle());
                        }
                    }
                    if (!StringUtils.hasText(record.getChangeafter())) continue;
                    baseDataDTO = new BaseDataDTO();
                    baseDataDTO.setTableName(dataFieldDefine.getRefTableName());
                    baseDataDTO.setObjectcode(record.getChangeafter());
                    baseDataDTO.setStopflag(Integer.valueOf(-1));
                    baseDataDTO.setVersionDate(record.getCreatetime());
                    baseDataDTO.setAuthType(BaseDataOption.AuthType.NONE);
                    list = this.baseDataClient.list(baseDataDTO);
                    if (list.getRows() == null || list.getRows().size() != 1) continue;
                    record.setChangeafter(((BaseDataDO)list.getRows().get(0)).getShowTitle());
                    continue;
                }
                if (dataFieldDefine.getRefTableType() == 4) {
                    OrgDTO orgDTO;
                    if (StringUtils.hasText(record.getChangebefore())) {
                        orgDTO = new OrgDTO();
                        orgDTO.setCategoryname(dataFieldDefine.getRefTableName());
                        orgDTO.setCode(record.getChangebefore());
                        orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
                        orgDTO.setStopflag(Integer.valueOf(-1));
                        orgDTO.setVersionDate(record.getCreatetime());
                        list = this.orgDataClient.list(orgDTO);
                        if (list.getRows() != null && list.getRows().size() == 1) {
                            record.setChangebefore(((OrgDO)list.getRows().get(0)).getShowTitle());
                        }
                    }
                    if (!StringUtils.hasText(record.getChangeafter())) continue;
                    orgDTO = new OrgDTO();
                    orgDTO.setCategoryname(dataFieldDefine.getRefTableName());
                    orgDTO.setCode(record.getChangeafter());
                    orgDTO.setAuthType(OrgDataOption.AuthType.NONE);
                    orgDTO.setStopflag(Integer.valueOf(-1));
                    orgDTO.setVersionDate(record.getCreatetime());
                    list = this.orgDataClient.list(orgDTO);
                    if (list.getRows() == null || list.getRows().size() != 1) continue;
                    record.setChangeafter(((OrgDO)list.getRows().get(0)).getShowTitle());
                    continue;
                }
                if (dataFieldDefine.getRefTableType() != 2) continue;
                if (StringUtils.hasText(record.getChangebefore())) {
                    enumDataDTO = new EnumDataDTO();
                    enumDataDTO.setVal(record.getChangebefore());
                    enumDataDTO.setBiztype(dataFieldDefine.getRefTableName());
                    list = this.enumDataClient.list(enumDataDTO);
                    if (list != null && list.size() == 1) {
                        record.setChangebefore(((EnumDataDO)list.get(0)).getTitle());
                    }
                }
                if (!StringUtils.hasText(record.getChangeafter())) continue;
                enumDataDTO = new EnumDataDTO();
                enumDataDTO.setVal(record.getChangeafter());
                enumDataDTO.setBiztype(dataFieldDefine.getRefTableName());
                list = this.enumDataClient.list(enumDataDTO);
                if (list == null || list.size() != 1) continue;
                record.setChangeafter(((EnumDataDO)list.get(0)).getTitle());
            }
        }
        R r = R.ok();
        r.put("changeList", billChangeRecordVOS);
        response.setReturnValue((Object)r);
        response.setSuccess(false);
    }
}

