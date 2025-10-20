/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.impl.BillActionBase
 *  com.jiuqi.va.bill.intf.BillException
 *  com.jiuqi.va.bill.intf.BillModel
 *  com.jiuqi.va.biz.intf.data.DataRow
 *  com.jiuqi.va.biz.intf.data.DataTable
 *  javax.annotation.PostConstruct
 *  org.apache.shiro.util.StringUtils
 */
package com.jiuqi.va.extend.action;

import com.jiuqi.va.bill.impl.BillActionBase;
import com.jiuqi.va.bill.intf.BillException;
import com.jiuqi.va.bill.intf.BillModel;
import com.jiuqi.va.biz.intf.data.DataRow;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.extend.domain.BillDetailCopyDTO;
import com.jiuqi.va.extend.intf.BillDetailCopyBizExtendIntf;
import com.jiuqi.va.extend.utils.BillExtend18nUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class BillDetailCopyAction
extends BillActionBase {
    private static final List<String> fixFields = Arrays.asList("ID", "VER", "MASTERID", "ORDINAL", "BILLCODE");
    @Autowired(required=false)
    private List<BillDetailCopyBizExtendIntf> billDetailCopyBizExtendIntfs;
    private final Map<String, BillDetailCopyBizExtendIntf> extendMap = new HashMap<String, BillDetailCopyBizExtendIntf>();

    @PostConstruct
    public void init() {
        if (this.billDetailCopyBizExtendIntfs == null) {
            return;
        }
        for (BillDetailCopyBizExtendIntf billDetailCopyBizExtendIntf : this.billDetailCopyBizExtendIntfs) {
            this.extendMap.put(billDetailCopyBizExtendIntf.getName(), billDetailCopyBizExtendIntf);
        }
    }

    public String getName() {
        return "bill-detail-copy";
    }

    public String getTitle() {
        return "\u590d\u5236\uff08\u5b50\u8868\u884c\uff09";
    }

    public String getActionPriority() {
        return "015";
    }

    public void execute(BillModel model, Map<String, Object> params) {
        String o = (String)params.get("bizType");
        if (!StringUtils.hasText((String)o)) {
            throw new BillException(BillExtend18nUtil.getMessage("va.billextend.detail.copy.nobiztype"));
        }
        BillDetailCopyBizExtendIntf extend = this.extendMap.get(o);
        if (extend == null) {
            throw new BillException(BillExtend18nUtil.getMessage("va.billextend.detail.copy.noextend"));
        }
        List rowData = (List)params.get("rowData");
        if (CollectionUtils.isEmpty(rowData)) {
            throw new BillException(BillExtend18nUtil.getMessage("va.billextend.detail.copy.norowdata"));
        }
        String tableName = (String)params.get("tableName");
        BillDetailCopyDTO dto = new BillDetailCopyDTO();
        dto.setTableName(tableName);
        dto.setRowData(rowData);
        Boolean check = (Boolean)params.get("check");
        if (check.booleanValue()) {
            boolean validate = extend.validate(dto);
            if (!validate) {
                throw new BillException(BillExtend18nUtil.getMessage("va.billextend.detail.copy.nocopy"));
            }
            return;
        }
        Integer index = (Integer)params.get("index");
        if (index == null) {
            throw new BillException(BillExtend18nUtil.getMessage("va.billextend.detail.copy.noindex"));
        }
        Integer n = index;
        Integer n2 = index = Integer.valueOf(index + 1);
        Integer count = (Integer)params.get("count");
        if (count == null) {
            count = 1;
        }
        ArrayList<Map<String, Object>> billDetailList = new ArrayList<Map<String, Object>>();
        DataTable table = model.getTable(tableName);
        for (int i = 0; i < count; ++i) {
            DataRow dataRow = table.insertRow(index + i);
            for (Map.Entry data : ((Map)rowData.get(0)).entrySet()) {
                if (fixFields.contains(data.getKey())) continue;
                dataRow.setValue((String)data.getKey(), data.getValue());
            }
            Map data = dataRow.getData();
            billDetailList.add(data);
        }
        dto.setCopyRowData(billDetailList);
        boolean execute = extend.execute(dto);
        if (!execute) {
            throw new BillException(BillExtend18nUtil.getMessage("va.billextend.detail.copy.copyerror"));
        }
    }
}

