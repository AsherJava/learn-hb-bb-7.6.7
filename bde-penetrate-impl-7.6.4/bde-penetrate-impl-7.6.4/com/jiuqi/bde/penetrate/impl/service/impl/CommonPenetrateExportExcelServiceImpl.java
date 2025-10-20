/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.EasyExcelFactory
 *  com.alibaba.excel.support.ExcelTypeEnum
 *  com.alibaba.excel.write.builder.ExcelWriterBuilder
 *  com.alibaba.excel.write.builder.ExcelWriterSheetBuilder
 *  com.alibaba.excel.write.handler.WriteHandler
 *  com.jiuqi.bde.common.dto.PenetrateInitDTO
 *  com.jiuqi.bde.common.intf.Dimension
 *  com.jiuqi.bde.common.intf.PenetrateColumn
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.va.domain.common.PageVO
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.bde.penetrate.impl.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.jiuqi.bde.common.dto.PenetrateInitDTO;
import com.jiuqi.bde.common.intf.Dimension;
import com.jiuqi.bde.common.intf.PenetrateColumn;
import com.jiuqi.bde.penetrate.impl.common.PenetrateTypeEnum;
import com.jiuqi.bde.penetrate.impl.expimp.common.PenetrateExcelExportParam;
import com.jiuqi.bde.penetrate.impl.expimp.common.PenetrateExportCellStyleStrategy;
import com.jiuqi.bde.penetrate.impl.expimp.common.VoucherPenetrateExportRow;
import com.jiuqi.bde.penetrate.impl.service.BdePenetrateMainService;
import com.jiuqi.bde.penetrate.impl.service.CommonPenetrateExportExcelService;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.va.domain.common.PageVO;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CommonPenetrateExportExcelServiceImpl
implements CommonPenetrateExportExcelService {
    public static final String FN_REQUEST_TASKID = "requestTaskId";

    @Override
    public String exportExcel(HttpServletResponse response, boolean templateExportFlag, PenetrateExcelExportParam param) {
        BdePenetrateMainService penetrateMainService = (BdePenetrateMainService)SpringContextUtils.getBean(BdePenetrateMainService.class);
        PenetrateInitDTO columnParam = penetrateMainService.init(param.getBizModel(), param.getPenetrateType(), param.getCondi());
        PenetrateTypeEnum penetrateType = PenetrateTypeEnum.fromCode(param.getPenetrateType());
        Map<String, Object> condi = param.getCondi();
        condi.put("offset", 0);
        condi.put("limit", 4999);
        if (condi.get(FN_REQUEST_TASKID) == null || StringUtils.isEmpty((String)((String)condi.get(FN_REQUEST_TASKID)))) {
            condi.put(FN_REQUEST_TASKID, UUIDUtils.newHalfGUIDStr());
        }
        PageVO valueParam = (PageVO)penetrateMainService.doQuery(param.getBizModel(), param.getPenetrateType(), condi);
        try {
            String fileName = URLEncoder.encode(columnParam.getTitle());
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8.name()), StandardCharsets.ISO_8859_1.name()) + ExcelTypeEnum.XLS.getValue());
            ArrayList<String> cloumnKeyList = new ArrayList<String>();
            List<List<String>> headerColumns = PenetrateTypeEnum.VOUCHER.equals((Object)penetrateType) ? this.getImpExpHeader(columnParam, cloumnKeyList, param.getVoucherRow()) : this.getImpExpHeader(columnParam, cloumnKeyList);
            ArrayList expDatas = templateExportFlag ? CollectionUtils.newArrayList() : this.getExpData((PageVO<HashMap<String, Object>>)valueParam, cloumnKeyList);
            ((ExcelWriterSheetBuilder)((ExcelWriterBuilder)EasyExcelFactory.write((OutputStream)response.getOutputStream()).registerWriteHandler((WriteHandler)new PenetrateExportCellStyleStrategy(columnParam.getColumns()))).sheet(columnParam.getTitle()).head(headerColumns)).doWrite((Collection)expDatas);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        return "\u5bfc\u51fa\u5b8c\u6210";
    }

    protected List<List<String>> getImpExpHeader(PenetrateInitDTO columnParam, List<String> cloumnKeyList) {
        ArrayList<List<String>> headerColumns = new ArrayList<List<String>>();
        List columns = columnParam.getColumns();
        for (PenetrateColumn column : columns) {
            ArrayList<String> cloumns;
            if (CollectionUtils.isEmpty((Collection)column.getChildren())) {
                cloumns = new ArrayList<String>();
                cloumns.add(this.getPenetrateTitle(columnParam));
                cloumns.add(this.getPenetrateCondiTitle(columnParam));
                cloumns.add(column.getTitle());
                cloumnKeyList.add(column.getName());
                headerColumns.add(cloumns);
                continue;
            }
            for (PenetrateColumn child : column.getChildren()) {
                cloumns = new ArrayList();
                cloumns.add(this.getPenetrateTitle(columnParam));
                cloumns.add(this.getPenetrateCondiTitle(columnParam));
                cloumns.add(column.getTitle());
                cloumns.add(child.getTitle());
                cloumnKeyList.add(child.getName());
                headerColumns.add(cloumns);
            }
        }
        return headerColumns;
    }

    protected List<List<String>> getImpExpHeader(PenetrateInitDTO columnParam, List<String> cloumnKeyList, VoucherPenetrateExportRow voucherRow) {
        ArrayList<List<String>> headerColumns = new ArrayList<List<String>>();
        List columns = columnParam.getColumns();
        for (PenetrateColumn column : columns) {
            ArrayList<String> cloumns;
            if (CollectionUtils.isEmpty((Collection)column.getChildren())) {
                cloumns = new ArrayList<String>();
                cloumns.add(this.getPenetrateTitle(columnParam));
                cloumns.add(this.getPenetrateCondiTitle(columnParam, voucherRow));
                cloumns.add(column.getTitle());
                cloumnKeyList.add(column.getName());
                headerColumns.add(cloumns);
                continue;
            }
            for (PenetrateColumn child : column.getChildren()) {
                cloumns = new ArrayList();
                cloumns.add(this.getPenetrateTitle(columnParam));
                cloumns.add(this.getPenetrateCondiTitle(columnParam, voucherRow));
                cloumns.add(column.getTitle());
                cloumns.add(child.getTitle());
                cloumnKeyList.add(child.getName());
                headerColumns.add(cloumns);
            }
        }
        return headerColumns;
    }

    private String getPenetrateCondiTitle(PenetrateInitDTO columnParam, VoucherPenetrateExportRow voucherRow) {
        String voucherWord = "";
        String voucherNum = "";
        if (!StringUtils.isEmpty((String)voucherRow.getVchrType()) && voucherRow.getVchrType().lastIndexOf("-") > 0) {
            voucherWord = voucherRow.getVchrType().substring(0, voucherRow.getVchrType().lastIndexOf("-")).trim();
            voucherNum = voucherRow.getVchrType().substring(voucherRow.getVchrType().lastIndexOf("-") + 1).trim();
        }
        return String.format("\u7ec4\u7ec7\u673a\u6784\uff1a%1$s %2$s    \u5b57\uff1a%3$s \u53f7\uff1a%4$s    \u51ed\u8bc1\u65e5\u671f\uff1a%5$s/%6$s/%7$s", columnParam.getUnitName(), columnParam.getUnitCode(), voucherWord, voucherNum, voucherRow.getAcctYear(), voucherRow.getAcctPeriod(), voucherRow.getAcctDay());
    }

    String getPenetrateTitle(PenetrateInitDTO columnParam) {
        return columnParam.getTitle();
    }

    private String getPenetrateCondiTitle(PenetrateInitDTO columnParam) {
        List<String> unitNames = Arrays.asList(columnParam.getUnitName().split(","));
        List<String> unitCodes = Arrays.asList(columnParam.getUnitCode().split(","));
        boolean isMulUnitName = columnParam.getUnitName().contains(",");
        ArrayList<String> unitTitleList = new ArrayList<String>();
        for (int i = 0; i < unitCodes.size(); ++i) {
            unitTitleList.add(String.format("%1$s|%2$s", unitCodes.get(i), isMulUnitName ? unitNames.get(i) : columnParam.getUnitName()));
        }
        String unitTitle = String.join((CharSequence)",", unitTitleList);
        return String.format("\u7ec4\u7ec7\u673a\u6784\uff1a%1$s                        %2$s\u5e74\u7b2c%3$s\u671f \u5230%4$s\u5e74\u7b2c%5$s\u671f                       \u5e01\u522b\uff1a\u672c\u4f4d\u5e01", unitTitle, columnParam.getStartAcctYear(), columnParam.getStartPeriod(), columnParam.getEndAcctYear(), columnParam.getEndPeriod());
    }

    private List<List<Object>> getExpData(PageVO<HashMap<String, Object>> valueParam, List<String> cloumnKeyList) {
        ArrayList<List<Object>> expRefDatas = new ArrayList<List<Object>>(valueParam.getTotal() * 2);
        List rows = valueParam.getRows();
        for (HashMap row : rows) {
            this.buildVoucherAssistComb(row, cloumnKeyList);
            LinkedList<String> rowDatas = new LinkedList<String>();
            for (String key : cloumnKeyList) {
                rowDatas.add(Objects.isNull(row.get(key)) ? "" : row.get(key));
            }
            expRefDatas.add(rowDatas);
        }
        return expRefDatas;
    }

    private void buildVoucherAssistComb(HashMap<String, Object> row, List<String> cloumnKeyList) {
        if (!row.containsKey("ASSTYPELIST") || !cloumnKeyList.contains("ASSISTCOMB")) {
            return;
        }
        StringBuilder assStr = new StringBuilder();
        List asstypelist = (List)row.get("ASSTYPELIST");
        for (Dimension dimension : asstypelist) {
            assStr.append(String.format("\u3010%1$s\uff1a%2$s\u3011", dimension.getDimName(), StringUtils.isEmpty((String)dimension.getDimValue()) ? "" : dimension.getDimValue()));
        }
        row.put("ASSISTCOMB", assStr.toString());
    }
}

