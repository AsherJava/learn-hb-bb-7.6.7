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
import com.jiuqi.gcreport.clbr.executor.model.ClbrBillRejectDetailsExcelModel;
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
public class ClbrBillRejectDetailsExportExecutor
extends AbstractExportExcelModelExecutor<ClbrBillRejectDetailsExcelModel> {
    @Autowired
    private ClbrDataQueryService clbrDataQueryService;

    protected ClbrBillRejectDetailsExportExecutor() {
        super(ClbrBillRejectDetailsExcelModel.class);
    }

    public String getName() {
        return "ClbrBillRejectDetailsExportExecutor";
    }

    protected List<ClbrBillRejectDetailsExcelModel> exportExcelModels(ExportContext context) {
        ClbrDataQueryConditon clbrDataQueryConditon = (ClbrDataQueryConditon)JsonUtils.readValue((String)context.getParam(), ClbrDataQueryConditon.class);
        clbrDataQueryConditon.setPageSelect(false);
        context.getProgressData().setProgressValueAndRefresh(0.1);
        PageInfo<ClbrBillVO> clbrBillVOPageInfo = this.clbrDataQueryService.queryRejectDetails(clbrDataQueryConditon);
        context.getProgressData().setProgressValueAndRefresh(0.8);
        return this.listClbrBillTotalDetailsExcelModel(clbrBillVOPageInfo.getList());
    }

    private List<ClbrBillRejectDetailsExcelModel> listClbrBillTotalDetailsExcelModel(List<ClbrBillVO> clbrBills) {
        return clbrBills.stream().map(clbrBill -> {
            ClbrBillRejectDetailsExcelModel clbrBillRejectDetailsExcelModel = new ClbrBillRejectDetailsExcelModel();
            BeanUtils.copyProperties(clbrBill, clbrBillRejectDetailsExcelModel);
            clbrBillRejectDetailsExcelModel.setCreateTime(DateUtils.format((Date)clbrBill.getCreateTime()));
            clbrBillRejectDetailsExcelModel.setRejectTime(DateUtils.format((Date)clbrBill.getRejectTime()));
            return clbrBillRejectDetailsExcelModel;
        }).collect(Collectors.toList());
    }
}

