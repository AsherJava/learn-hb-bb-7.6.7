/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.SelectOptionVO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillDefineDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractDefineDTO
 *  com.jiuqi.gcreport.billextract.client.vo.TreeVO
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelDTO
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.service;

import com.jiuqi.bde.common.dto.SelectOptionVO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillDefineDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractDefineDTO;
import com.jiuqi.gcreport.billextract.client.vo.TreeVO;
import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelDTO;
import java.util.List;

public interface BillExtractDefineService {
    public List<TreeVO> getExtractDefineTree();

    public List<SelectOptionVO> getBillDefineList(String var1);

    public List<BillDefineDTO> getBillDefineListWithState(String var1);

    public String saveExtractDefine(String var1, List<BillExtractDefineDTO> var2);

    public boolean exchangeExtractDefineOrdinal(String var1, String var2);

    public void cleanCache(String var1);

    public List<DataModelColumn> getBillDataModelList(DataModelDTO var1);

    public List<DataModelColumn> getBillDataModelListByBillId(String var1);
}

