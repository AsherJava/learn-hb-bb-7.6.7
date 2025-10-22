/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor
 *  com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon
 *  com.jiuqi.gcreport.clbr.vo.ClbrOverViewVO
 */
package com.jiuqi.gcreport.clbr.executor;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor;
import com.jiuqi.gcreport.clbr.executor.model.ClbrBillPandectDetailsExcelModel;
import com.jiuqi.gcreport.clbr.service.ClbrDataQueryService;
import com.jiuqi.gcreport.clbr.vo.ClbrDataQueryConditon;
import com.jiuqi.gcreport.clbr.vo.ClbrOverViewVO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClbrBillPandectDetailsExportExecutor
extends AbstractExportExcelModelExecutor<ClbrBillPandectDetailsExcelModel> {
    @Autowired
    private ClbrDataQueryService clbrDataQueryService;

    protected ClbrBillPandectDetailsExportExecutor() {
        super(ClbrBillPandectDetailsExcelModel.class);
    }

    public String getName() {
        return "ClbrBillPandectDetailsExportExecutor";
    }

    protected List<ClbrBillPandectDetailsExcelModel> exportExcelModels(ExportContext context) {
        ClbrDataQueryConditon clbrDataQueryConditon = (ClbrDataQueryConditon)JsonUtils.readValue((String)context.getParam(), ClbrDataQueryConditon.class);
        clbrDataQueryConditon.setPageSelect(false);
        context.getProgressData().setProgressValueAndRefresh(0.1);
        List<ClbrOverViewVO> clbrOverViews = this.clbrDataQueryService.listClbrOverView(clbrDataQueryConditon);
        context.getProgressData().setProgressValueAndRefresh(0.8);
        return this.listClbrSchemeExcelModel(clbrOverViews);
    }

    private List<ClbrBillPandectDetailsExcelModel> listClbrSchemeExcelModel(List<ClbrOverViewVO> clbrOverViews) {
        return clbrOverViews.stream().map(clbrOverViewVO -> {
            ClbrBillPandectDetailsExcelModel clbrBillPandectDetailsExcelModel = new ClbrBillPandectDetailsExcelModel();
            BeanUtils.copyProperties(clbrOverViewVO, clbrBillPandectDetailsExcelModel);
            return clbrBillPandectDetailsExcelModel;
        }).collect(Collectors.toList());
    }
}

