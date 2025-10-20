/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.subject.impl.subject.dto.SubjectDTO
 *  com.jiuqi.common.subject.impl.subject.service.SubjectService
 *  com.jiuqi.va.bill.impl.BillModelImpl
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.value.ListContainer
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.org.OrgDataOption$QueryDataStructure
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  com.jiuqi.va.feign.client.OrgDataClient
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.dc.bill.model.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.subject.impl.subject.dto.SubjectDTO;
import com.jiuqi.common.subject.impl.subject.service.SubjectService;
import com.jiuqi.va.bill.impl.BillModelImpl;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.value.ListContainer;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.feign.client.BaseDataClient;
import com.jiuqi.va.feign.client.OrgDataClient;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class DcBillAgeUnClearedModelImpl
extends BillModelImpl {
    public void save() {
        this.dataCheck();
        super.save();
    }

    public void delete() {
        DataRow master = this.getMaster();
        if (master.getBoolean("LOCKSTATE")) {
            throw new BusinessRuntimeException("\u6570\u636e\u5904\u4e8e\u88ab\u9501\u5b9a\u72b6\u6001\uff0c\u8bf7\u89e3\u9664\u9501\u5b9a\u540e\u5728\u5220\u9664\uff01");
        }
        super.delete();
    }

    private void dataCheck() {
        this.masterRequiredCheck();
        DataRow master = this.getMaster();
        int year = master.getInt("ACCTYEAR");
        int period = master.getInt("ACCTPERIOD");
        ListContainer dcBillVoucherItem = this.getTable("DC_BILL_AGEUNCLEAREDITEM").getRows();
        if (dcBillVoucherItem.size() < 1) {
            throw new BusinessRuntimeException("\u5b50\u8868\u81f3\u5c11\u67091\u6761\u6570\u636e!");
        }
        for (int i = 0; i < dcBillVoucherItem.size(); ++i) {
            int rowNumber = i + 1;
            DataRow dataRow = (DataRow)dcBillVoucherItem.get(i);
            this.itemCheck(dataRow, rowNumber, year, period);
        }
    }

    private void masterRequiredCheck() {
        DataRow master = this.getMaster();
        String unitCode = master.getString("UNITCODE");
        if (StringUtils.isEmpty((String)unitCode)) {
            throw new BusinessRuntimeException("\u7ec4\u7ec7\u673a\u6784\u4e0d\u80fd\u4e3a\u7a7a!");
        }
        if (StringUtils.isEmpty((String)master.getString("ACCTYEAR"))) {
            throw new BusinessRuntimeException("\u5e74\u5ea6\u4e0d\u80fd\u4e3a\u7a7a!");
        }
        if (StringUtils.isEmpty((String)master.getString("ACCTPERIOD"))) {
            throw new BusinessRuntimeException("\u65f6\u671f\u4e0d\u80fd\u4e3a\u7a7a!");
        }
        OrgDTO param = new OrgDTO();
        param.setAuthType(OrgDataOption.AuthType.NONE);
        param.setCategoryname("MD_ORG");
        param.setQueryDataStructure(OrgDataOption.QueryDataStructure.SIMPLE);
        param.setCode(unitCode);
        OrgDataClient orgDataClient = (OrgDataClient)ApplicationContextRegister.getBean(OrgDataClient.class);
        List rows = orgDataClient.listSubordinate(param).getRows();
        if (CollectionUtils.isEmpty((Collection)rows)) {
            throw new BusinessRuntimeException("\u7ec4\u7ec7\u673a\u6784\u5728\u7cfb\u7edf\u4e2d\u4e0d\u5b58\u5728!");
        }
        if (rows.size() > 1) {
            throw new BusinessRuntimeException("\u975e\u672b\u7ea7\u5355\u4f4d\u4e0d\u5141\u8bb8\u4fdd\u5b58!");
        }
    }

    private void itemCheck(DataRow dataRow, int rowNumber, int year, int period) {
        String subjectCode = dataRow.getString("SUBJECTCODE");
        if (StringUtils.isEmpty((String)subjectCode)) {
            throw new BusinessRuntimeException("\u7b2c" + rowNumber + "\u884c\u79d1\u76ee\u4ee3\u7801\u4e0d\u80fd\u4e3a\u7a7a!");
        }
        String currencyCode = dataRow.getString("CURRENCYCODE");
        if (StringUtils.isEmpty((String)currencyCode)) {
            throw new BusinessRuntimeException("\u7b2c" + rowNumber + "\u884c\u5e01\u79cd\u4e0d\u80fd\u4e3a\u7a7a!");
        }
        if (dataRow.getBigDecimal("ORGNBALANCE") == null) {
            throw new BusinessRuntimeException("\u7b2c" + rowNumber + "\u884c\u539f\u5e01\u4f59\u989d\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (dataRow.getBigDecimal("BALANCE") == null) {
            throw new BusinessRuntimeException("\u7b2c" + rowNumber + "\u884c\u672c\u5e01\u4f59\u989d\u4e0d\u80fd\u4e3a\u7a7a");
        }
        Date bizDate = dataRow.getDate("BIZDATE");
        if (bizDate == null) {
            throw new BusinessRuntimeException("\u7b2c" + rowNumber + "\u884c\u4e1a\u52a1\u65e5\u671f\u4e0d\u80fd\u4e3a\u7a7a");
        }
        int bizDateYear = DateUtils.getYearOfDate((Date)bizDate);
        int bizDateMonth = DateUtils.getDateFieldValue((Date)bizDate, (int)2);
        if (bizDateYear > year || bizDateYear == year && bizDateMonth > period) {
            throw new BusinessRuntimeException("\u7b2c" + rowNumber + "\u884c\u4e1a\u52a1\u65e5\u671f\u7684\u5e74\u5ea6\u671f\u95f4\u4e0d\u80fd\u5927\u4e8e\u4e3b\u8868\u5e74\u5ea6\u671f\u95f4");
        }
        SubjectService subjectService = (SubjectService)ApplicationContextRegister.getBean(SubjectService.class);
        SubjectDTO subjectDTO = subjectService.findByCode(subjectCode);
        if (subjectDTO == null) {
            throw new BusinessRuntimeException("\u7b2c" + rowNumber + "\u884c\u79d1\u76ee\u4ee3\u7801\u5728\u7cfb\u7edf\u4e2d\u4e0d\u5b58\u5728!");
        }
        BaseDataClient baseDataClient = (BaseDataClient)ApplicationContextRegister.getBean(BaseDataClient.class);
        BaseDataDTO baseDataCondi = new BaseDataDTO();
        baseDataCondi.setTableName("MD_CURRENCY");
        baseDataCondi.setAuthType(BaseDataOption.AuthType.NONE);
        baseDataCondi.setCode(currencyCode);
        R exist = baseDataClient.exist(baseDataCondi);
        if (!((Boolean)exist.get((Object)"exist")).booleanValue()) {
            throw new BusinessRuntimeException("\u7b2c" + rowNumber + "\u884c\u5e01\u79cd\u5728\u7cfb\u7edf\u4e2d\u4e0d\u5b58\u5728!");
        }
    }
}

