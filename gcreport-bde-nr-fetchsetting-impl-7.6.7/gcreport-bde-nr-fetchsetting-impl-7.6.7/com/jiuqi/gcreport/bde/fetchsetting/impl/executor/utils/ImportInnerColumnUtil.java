/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.dto.BaseDataBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.BizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO
 *  com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.dc.base.common.enums.ColumnAlignment
 *  com.jiuqi.gcreport.dimension.vo.DimensionVO
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.utils;

import com.jiuqi.bde.bizmodel.client.dto.BaseDataBizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.BizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.CustomBizModelDTO;
import com.jiuqi.bde.bizmodel.client.dto.FinBizModelDTO;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.base.common.enums.ColumnAlignment;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.consts.ExcelRowFetchSettingVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.enums.ExportBeforeInnerColumnHandlerEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.enums.ExportBehindInnerColumnHandlerEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.enums.FloatExportBeforeInnerColumnHandlerEnum;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.gather.ImpExpHandleGather;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.ImpExpInnerColumnHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.impl.AssistColumnHandler;
import com.jiuqi.gcreport.dimension.vo.DimensionVO;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ImportInnerColumnUtil {
    public static List<ImpExpInnerColumnHandler> getFixedImportInnerColumns(List<DimensionVO> dimensions) {
        ArrayList<ImpExpInnerColumnHandler> columns = new ArrayList<ImpExpInnerColumnHandler>(64);
        ImpExpHandleGather impExpHandleGather = (ImpExpHandleGather)ApplicationContextRegister.getBean(ImpExpHandleGather.class);
        List<ExportBeforeInnerColumnHandlerEnum> beforeColumns = Arrays.asList(ExportBeforeInnerColumnHandlerEnum.values());
        for (ExportBeforeInnerColumnHandlerEnum beforeColumn : beforeColumns) {
            if (impExpHandleGather.getColumnHandler(beforeColumn.getKey()) != null) {
                columns.add(impExpHandleGather.getColumnHandler(beforeColumn.getKey()));
                continue;
            }
            columns.add(beforeColumn);
        }
        for (DimensionVO dimension : dimensions) {
            columns.add(new AssistColumnHandler(dimension.getCode(), new String[]{"\u8f85\u52a9\u7ef4\u5ea6\u8bbe\u7f6e", dimension.getTitle()}, ColumnAlignment.LEFT, 120, dimension.getMatchRule()));
        }
        columns.addAll(Arrays.asList(ExportBehindInnerColumnHandlerEnum.values()));
        return columns;
    }

    public static List<ImpExpInnerColumnHandler> getFloatImportInnerColumns(List<DimensionVO> dimensions) {
        ArrayList<ImpExpInnerColumnHandler> columns = new ArrayList<ImpExpInnerColumnHandler>(64);
        columns.addAll(Arrays.asList(FloatExportBeforeInnerColumnHandlerEnum.values()));
        for (DimensionVO dimension : dimensions) {
            columns.add(new AssistColumnHandler(dimension.getCode(), new String[]{"\u8f85\u52a9\u7ef4\u5ea6\u8bbe\u7f6e", dimension.getTitle()}, ColumnAlignment.LEFT, 120, dimension.getMatchRule()));
        }
        columns.addAll(Arrays.asList(ExportBehindInnerColumnHandlerEnum.values()));
        return columns;
    }

    public static List<Object[]> getTitle(List<ImpExpInnerColumnHandler> exportInnerColumnHandlers) {
        ArrayList<Object[]> list = new ArrayList<Object[]>();
        int titleIndex = exportInnerColumnHandlers.stream().max(Comparator.comparing(item -> item.getLabel().length)).get().getLabel().length;
        for (int i = 0; i < titleIndex; ++i) {
            Object[] title = new Object[exportInnerColumnHandlers.size()];
            for (int r = 0; r < exportInnerColumnHandlers.size(); ++r) {
                title[r] = StringUtils.isEmpty((String)exportInnerColumnHandlers.get(r).getLabel()[i]) ? "" : exportInnerColumnHandlers.get(r).getLabel()[i];
            }
            list.add(title);
        }
        return list;
    }

    private static Object[] getSetting(List<ImpExpInnerColumnHandler> exportInnerColumnHandlers, ExcelRowFetchSettingVO excelRowFetchSettingVO) {
        Object[] title = new Object[exportInnerColumnHandlers.size()];
        for (int r = 0; r < exportInnerColumnHandlers.size(); ++r) {
            title[r] = exportInnerColumnHandlers.get(r).getFixExportData(excelRowFetchSettingVO);
        }
        return title;
    }

    public static List<Object[]> getSettings(List<ImpExpInnerColumnHandler> exportInnerColumnHandlers, List<ExcelRowFetchSettingVO> excelRowFetchSettingVOS) {
        ArrayList<Object[]> rows = new ArrayList<Object[]>();
        for (ExcelRowFetchSettingVO excelRowFetchSettingVO : excelRowFetchSettingVOS) {
            rows.add(ImportInnerColumnUtil.getSetting(exportInnerColumnHandlers, excelRowFetchSettingVO));
        }
        return rows;
    }

    private static ImpExpInnerColumnHandler getExportInnerColumnHandlerByLabel(List<ImpExpInnerColumnHandler> exportInnerColumnHandlers, String firstTitle, String secondTitle) {
        for (ImpExpInnerColumnHandler exportInnerColumnHandler : exportInnerColumnHandlers) {
            if (!firstTitle.equals(exportInnerColumnHandler.getLabel()[0]) || !secondTitle.equals(exportInnerColumnHandler.getLabel()[1])) continue;
            return exportInnerColumnHandler;
        }
        return null;
    }

    public static List<ImpExpInnerColumnHandler> getExportInnerColumnHandlersByRow(List<ImpExpInnerColumnHandler> exportInnerColumnHandlers, Object[] firstRow, Object[] secondRwo) {
        ArrayList<ImpExpInnerColumnHandler> iExportInnerColumnHandlers = new ArrayList<ImpExpInnerColumnHandler>();
        for (int i = 0; i < firstRow.length; ++i) {
            ImpExpInnerColumnHandler exportInnerColumnHandler = ImportInnerColumnUtil.getExportInnerColumnHandlerByLabel(exportInnerColumnHandlers, firstRow[i].toString(), secondRwo[i].toString());
            if (exportInnerColumnHandler == null) {
                throw new BusinessRuntimeException(String.format("\u5217\u3010%1$s\u3011\u65e0\u6cd5\u89e3\u6790\uff0c\u8bf7\u5bfc\u51fa\u6a21\u677f\u91cd\u65b0\u914d\u7f6e", secondRwo[i].toString()));
            }
            iExportInnerColumnHandlers.add(exportInnerColumnHandler);
        }
        return iExportInnerColumnHandlers;
    }

    public static ExcelRowFetchSettingVO getImportFetchSourceRowImpSetting(List<ImpExpInnerColumnHandler> exportInnerColumnHandlers, Object[] data) {
        ExcelRowFetchSettingVO excelRowFetchSettingVO = new ExcelRowFetchSettingVO();
        if (exportInnerColumnHandlers.size() > data.length) {
            excelRowFetchSettingVO.setErrorLog("\u5bfc\u5165\u6587\u4ef6\u683c\u5f0f\u4e0d\u6b63\u786e\uff0c\u8bf7\u68c0\u67e5\u6587\u4ef6");
            return excelRowFetchSettingVO;
        }
        for (int columnIndex = 0; columnIndex < exportInnerColumnHandlers.size(); ++columnIndex) {
            exportInnerColumnHandlers.get(columnIndex).importData(excelRowFetchSettingVO, data[columnIndex] == null ? "" : data[columnIndex].toString());
        }
        return excelRowFetchSettingVO;
    }

    public static String getBizModelFieldName(BizModelDTO bizModelDTO) {
        if (bizModelDTO instanceof CustomBizModelDTO) {
            return ((CustomBizModelDTO)bizModelDTO).getFetchFieldNames();
        }
        if (bizModelDTO instanceof FinBizModelDTO) {
            return ((FinBizModelDTO)bizModelDTO).getFetchTypeNames();
        }
        if (bizModelDTO instanceof BaseDataBizModelDTO) {
            return ((BaseDataBizModelDTO)bizModelDTO).getFetchFieldNames();
        }
        return "";
    }
}

