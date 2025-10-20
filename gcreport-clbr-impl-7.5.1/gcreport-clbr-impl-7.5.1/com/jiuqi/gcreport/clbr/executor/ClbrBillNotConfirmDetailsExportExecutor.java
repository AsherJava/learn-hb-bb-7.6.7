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
import com.jiuqi.gcreport.clbr.executor.model.ClbrBillNotConfirmDetailsExcelModel;
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
public class ClbrBillNotConfirmDetailsExportExecutor
extends AbstractExportExcelModelExecutor<ClbrBillNotConfirmDetailsExcelModel> {
    @Autowired
    private ClbrDataQueryService clbrDataQueryService;

    protected ClbrBillNotConfirmDetailsExportExecutor() {
        super(ClbrBillNotConfirmDetailsExcelModel.class);
    }

    public String getName() {
        return "ClbrBillNotConfirmDetailsExportExecutor";
    }

    protected List<ClbrBillNotConfirmDetailsExcelModel> exportExcelModels(ExportContext context) {
        ClbrDataQueryConditon clbrDataQueryConditon = (ClbrDataQueryConditon)JsonUtils.readValue((String)context.getParam(), ClbrDataQueryConditon.class);
        clbrDataQueryConditon.setPageSelect(false);
        context.getProgressData().setProgressValueAndRefresh(0.1);
        PageInfo<ClbrBillVO> clbrBillVOPageInfo = this.clbrDataQueryService.queryNotConfirmDetails(clbrDataQueryConditon);
        context.getProgressData().setProgressValueAndRefresh(0.8);
        return this.listClbrBillNotConfirmDetailsExcelModel(clbrBillVOPageInfo.getList());
    }

    private List<ClbrBillNotConfirmDetailsExcelModel> listClbrBillNotConfirmDetailsExcelModel(List<ClbrBillVO> clbrBills) {
        return clbrBills.stream().map(clbrBill -> {
            ClbrBillNotConfirmDetailsExcelModel clbrBillNotConfirmDetailsExcelModel = new ClbrBillNotConfirmDetailsExcelModel();
            BeanUtils.copyProperties(clbrBill, clbrBillNotConfirmDetailsExcelModel);
            clbrBillNotConfirmDetailsExcelModel.setCreateTime(DateUtils.format((Date)clbrBill.getCreateTime()));
            return clbrBillNotConfirmDetailsExcelModel;
        }).collect(Collectors.toList());
    }
}

