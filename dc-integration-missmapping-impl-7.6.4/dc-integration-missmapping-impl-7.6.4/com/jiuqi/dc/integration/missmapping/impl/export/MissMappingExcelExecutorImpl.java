/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  com.jiuqi.dc.base.common.vo.BaseDataShowVO
 *  com.jiuqi.dc.integration.missmapping.client.vo.MissMappingDimVO
 *  com.jiuqi.dc.integration.missmapping.client.vo.MissMappingQueryVO
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 *  javax.persistence.NoResultException
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.dc.integration.missmapping.impl.export;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.dc.base.common.vo.BaseDataShowVO;
import com.jiuqi.dc.integration.missmapping.client.vo.MissMappingDimVO;
import com.jiuqi.dc.integration.missmapping.client.vo.MissMappingQueryVO;
import com.jiuqi.dc.integration.missmapping.impl.service.MissMappingService;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.persistence.NoResultException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MissMappingExcelExecutorImpl
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    private MissMappingService missMappingService;
    @Autowired
    private DataSchemeService dataSchemeService;

    public String getName() {
        return "MissMappingExcelExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        ArrayList<ExportExcelSheet> exportExcelSheets = new ArrayList<ExportExcelSheet>();
        ExportExcelSheet detailSheet = new ExportExcelSheet(Integer.valueOf(0), "\u660e\u7ec6", Integer.valueOf(1));
        ExportExcelSheet gatherSheet = new ExportExcelSheet(Integer.valueOf(1), "\u6c47\u603b", Integer.valueOf(1));
        MissMappingQueryVO missMappingQueryVO = (MissMappingQueryVO)JsonUtils.readValue((String)context.getParam(), MissMappingQueryVO.class);
        List<BaseDataShowVO> missMappingDimList = this.missMappingService.getMissMappingDim(missMappingQueryVO);
        if (CollectionUtils.isEmpty(missMappingDimList)) {
            throw new NoResultException("\u6ca1\u6709\u8981\u5bfc\u51fa\u7684\u6570\u636e");
        }
        ArrayList<Object[]> detailRowDatas = new ArrayList<Object[]>();
        ArrayList<Object[]> gatherRowDatas = new ArrayList<Object[]>();
        ArrayList detailHeadList = CollectionUtils.newArrayList((Object[])new String[]{"\u6570\u636e\u6620\u5c04\u65b9\u6848", "\u6e90\u5355\u4f4d\u4ee3\u7801", "\u6e90\u51ed\u8bc1\u53f7"});
        missMappingDimList.forEach(e -> {
            detailHeadList.add("\u7f3a\u6620\u5c04" + e.getTitle() + "ID");
            detailHeadList.add("\u7f3a\u6620\u5c04" + e.getTitle() + "\u4ee3\u7801");
        });
        detailRowDatas.add(detailHeadList.toArray());
        gatherRowDatas.add(Arrays.asList("\u6570\u636e\u6620\u5c04\u65b9\u6848", "\u6570\u636e\u7c7b\u578b", "\u7f3a\u6620\u5c04\u6e90ID", "\u7f3a\u6620\u5c04\u6e90\u4ee3\u7801").toArray());
        List detailData = this.missMappingService.getMissMappingDetail(missMappingQueryVO).getRows();
        List gatherData = this.missMappingService.getMissMappingGather(missMappingQueryVO).getRows();
        detailData.forEach(missMappingDTO -> {
            int i = 0;
            Object[] detailRowData = new Object[((Object[])detailRowDatas.get(0)).length];
            detailRowData[i++] = this.dataSchemeService.getByCode(missMappingDTO.getDataSchemeCode()).getName();
            detailRowData[i++] = missMappingDTO.getUnitCode();
            detailRowData[i++] = missMappingDTO.getVchrNum();
            Map<String, String> dimIdMap = Arrays.stream(missMappingDTO.getDimVOs()).filter(Objects::nonNull).collect(Collectors.toMap(BaseDataShowVO::getCode, BaseDataShowVO::getId, (k1, k2) -> k1));
            Map<String, String> dimValueMap = Arrays.stream(missMappingDTO.getDimVOs()).filter(Objects::nonNull).collect(Collectors.toMap(BaseDataShowVO::getCode, MissMappingDimVO::getDimValue, (k1, k2) -> k1));
            for (BaseDataShowVO dimVO : missMappingDimList) {
                detailRowData[i++] = dimIdMap.get(dimVO.getCode());
                detailRowData[i++] = dimValueMap.get(dimVO.getCode());
            }
            detailRowDatas.add(detailRowData);
        });
        gatherData.forEach(missMappingGatherDTO -> {
            int i = 0;
            Object[] gatherRowData = new Object[((Object[])gatherRowDatas.get(0)).length];
            gatherRowData[i++] = this.dataSchemeService.getByCode(missMappingGatherDTO.getDataSchemeCode()).getName();
            gatherRowData[i++] = missMappingGatherDTO.getDimVO().getTitle();
            gatherRowData[i++] = missMappingGatherDTO.getDimVO().getId();
            gatherRowData[i] = missMappingGatherDTO.getDimVO().getDimValue();
            gatherRowDatas.add(gatherRowData);
        });
        detailSheet.getRowDatas().addAll(detailRowDatas);
        gatherSheet.getRowDatas().addAll(gatherRowDatas);
        exportExcelSheets.add(detailSheet);
        exportExcelSheets.add(gatherSheet);
        return exportExcelSheets;
    }
}

