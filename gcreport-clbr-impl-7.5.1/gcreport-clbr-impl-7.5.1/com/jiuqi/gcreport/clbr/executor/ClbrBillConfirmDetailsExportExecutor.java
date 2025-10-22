/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor
 *  com.jiuqi.gcreport.clbr.vo.ClbrBillVO
 *  com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon
 */
package com.jiuqi.gcreport.clbr.executor;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor;
import com.jiuqi.gcreport.clbr.executor.model.ClbrBillConfirmDetailsExcelModel;
import com.jiuqi.gcreport.clbr.service.ClbrDataQueryService;
import com.jiuqi.gcreport.clbr.vo.ClbrBillVO;
import com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClbrBillConfirmDetailsExportExecutor
extends AbstractExportExcelModelExecutor<ClbrBillConfirmDetailsExcelModel> {
    @Autowired
    private ClbrDataQueryService clbrDataQueryService;

    protected ClbrBillConfirmDetailsExportExecutor() {
        super(ClbrBillConfirmDetailsExcelModel.class);
    }

    public String getName() {
        return "ClbrBillConfirmDetailsExportExecutor";
    }

    protected List<ClbrBillConfirmDetailsExcelModel> exportExcelModels(ExportContext context) {
        ClbrDataQueryConditon clbrDataQueryConditon = (ClbrDataQueryConditon)JsonUtils.readValue((String)context.getParam(), ClbrDataQueryConditon.class);
        clbrDataQueryConditon.setPageSelect(false);
        context.getProgressData().setProgressValueAndRefresh(0.1);
        PageInfo<ClbrBillVO> clbrBillVOPageInfo = this.clbrDataQueryService.queryConfirmDetails(clbrDataQueryConditon);
        context.getProgressData().setProgressValueAndRefresh(0.8);
        return this.listClbrBillConfirmDetailsExcelModel(clbrBillVOPageInfo.getList());
    }

    private List<ClbrBillConfirmDetailsExcelModel> listClbrBillConfirmDetailsExcelModel(List<ClbrBillVO> clbrBills) {
        return clbrBills.stream().map(clbrBill -> {
            ClbrBillConfirmDetailsExcelModel clbrBillConfirmDetailsExcelModel = new ClbrBillConfirmDetailsExcelModel();
            BeanUtils.copyProperties(clbrBill, clbrBillConfirmDetailsExcelModel);
            clbrBillConfirmDetailsExcelModel.setCreateTime(DateUtils.format((Date)clbrBill.getCreateTime()));
            clbrBillConfirmDetailsExcelModel.setClbrTime(DateUtils.format((Date)clbrBill.getClbrTime()));
            return clbrBillConfirmDetailsExcelModel;
        }).collect(Collectors.toList());
    }
}

