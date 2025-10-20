/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.UUIDUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDO
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.OrientEnum
 *  com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils
 *  com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao
 *  com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO
 *  com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.offsetitem.check.OffsetItemCheckResult
 *  com.jiuqi.gcreport.offsetitem.check.impl.OffSetCoreGroupChecker
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.offsetitem.dto.OffsetVchrCodeDTO
 *  com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO
 *  com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO
 *  com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum
 *  com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO
 *  com.jiuqi.gcreport.offsetitem.inputdata.service.GcInputDataOffsetItemService
 *  com.jiuqi.gcreport.offsetitem.service.GcInputAdjustService
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService
 *  com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetAppOffsetServiceImpl
 *  com.jiuqi.gcreport.offsetitem.util.OffsetCoreConvertUtil
 *  com.jiuqi.gcreport.offsetitem.util.OffsetItemComparatorUtil
 *  com.jiuqi.gcreport.offsetitem.util.OffsetVchrCodeUtil
 *  com.jiuqi.gcreport.offsetitem.vo.GcBusinessTypeCountVO
 *  com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.query.GcOffsetItemQueryCondi
 *  com.jiuqi.gcreport.unionrule.util.ExcelUtils$ExportColumnTypeEnum
 *  com.jiuqi.np.log.LogHelper
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.ss.util.CellRangeAddress
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.offsetitem.init.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseDataDO;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.OrientEnum;
import com.jiuqi.gcreport.common.util.UUIDOrderSnowUtils;
import com.jiuqi.gcreport.consolidatedsystem.dao.ConsolidatedSystemDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import com.jiuqi.gcreport.consolidatedsystem.service.option.ConsolidatedOptionService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.offsetitem.check.OffsetItemCheckResult;
import com.jiuqi.gcreport.offsetitem.check.impl.OffSetCoreGroupChecker;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrDTO;
import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.offsetitem.dto.OffsetVchrCodeDTO;
import com.jiuqi.gcreport.offsetitem.dto.QueryParamsDTO;
import com.jiuqi.gcreport.offsetitem.entity.GcOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.offsetitem.enums.OffSetSrcTypeEnum;
import com.jiuqi.gcreport.offsetitem.init.dao.GcOffSetVchrItemInitDao;
import com.jiuqi.gcreport.offsetitem.init.entity.GcOffSetVchrItemInitEO;
import com.jiuqi.gcreport.offsetitem.init.monitor.IOffsetInitMonitor;
import com.jiuqi.gcreport.offsetitem.init.service.GcOffSetInitService;
import com.jiuqi.gcreport.offsetitem.init.utils.GcOffSetItemImportUtil;
import com.jiuqi.gcreport.offsetitem.init.vo.OffsetItemInitQueryParamsVO;
import com.jiuqi.gcreport.offsetitem.inputdata.service.GcInputDataOffsetItemService;
import com.jiuqi.gcreport.offsetitem.service.GcInputAdjustService;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCoreService;
import com.jiuqi.gcreport.offsetitem.service.impl.GcOffSetAppOffsetServiceImpl;
import com.jiuqi.gcreport.offsetitem.util.OffsetCoreConvertUtil;
import com.jiuqi.gcreport.offsetitem.util.OffsetItemComparatorUtil;
import com.jiuqi.gcreport.offsetitem.util.OffsetVchrCodeUtil;
import com.jiuqi.gcreport.offsetitem.vo.GcBusinessTypeCountVO;
import com.jiuqi.gcreport.offsetitem.vo.GcOffSetVchrItemVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.query.GcOffsetItemQueryCondi;
import com.jiuqi.gcreport.unionrule.util.ExcelUtils;
import com.jiuqi.np.log.LogHelper;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GcOffSetInitServiceImpl
implements GcOffSetInitService {
    private static Logger LOGGER = LoggerFactory.getLogger(GcOffSetInitServiceImpl.class);
    @Autowired
    private GcOffSetVchrItemInitDao offSetVchrItemInitDao;
    @Autowired
    private GcOffSetItemAdjustCoreService offsetCoreService;
    @Autowired
    private GcOffSetAppOffsetServiceImpl offSetItemAdjustService;
    @Autowired(required=false)
    private GcInputDataOffsetItemService inputDataOffsetCoreService;
    @Autowired
    private ConsolidatedOptionService optionService;
    @Autowired
    private ConsolidatedSystemDao consolidatedSystemDao;
    @Autowired
    private GcInputAdjustService inputAdjustService;
    @Autowired
    private OffSetCoreGroupChecker offSetCoreGroupChecker;
    @Lazy
    @Autowired(required=false)
    private List<IOffsetInitMonitor> offsetInitMonitors;
    public static final String ErrorFileName = "\u5206\u5f55\u521d\u59cb\u5316\u95ee\u9898";
    private static final DecimalFormat df = new DecimalFormat("###,##0.00");

    @Override
    public StringBuffer importData(MultipartFile importData, QueryParamsVO queryParamsVO, String sn) {
        StringBuffer log = new StringBuffer(512);
        try {
            Workbook workbook = GcOffSetItemImportUtil.parse(importData, queryParamsVO, log);
            String systemName = ((ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)queryParamsVO.getSystemId()))).getSystemName();
            LogHelper.info((String)"\u5408\u5e76-\u5206\u5f55\u521d\u59cb\u5316", (String)("\u6279\u91cf\u5bfc\u5165-\u4f53\u7cfb" + systemName + "-\u65f6\u671f" + queryParamsVO.getAcctYear()), (String)log.toString());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u5bfc\u5165\u62b5\u9500\u5206\u5f55\u5931\u8d25\uff1a" + e.getMessage(), (Throwable)e);
        }
        return log;
    }

    @Override
    public ExportExcelSheet exportSheet(int sheetNo, OffsetItemInitQueryParamsVO queryParamsVO, GcBusinessTypeCountVO gcBusinessTypeCountVO, Map<String, CellStyle> cellStyleMap, ExportContext context) {
        Map filterCondition = queryParamsVO.getFilterCondition();
        filterCondition.put("gcbusinesstypecode", gcBusinessTypeCountVO.getCode());
        Pagination<Map<String, Object>> offsetEntry = this.queryOffsetEntry(queryParamsVO);
        this.initSheetIntervalRowSet(sheetNo, context, offsetEntry);
        String sheetTitle = gcBusinessTypeCountVO.getTitle();
        if (context.isTemplateExportFlag()) {
            sheetTitle = GcI18nUtil.getMessage((String)"gc.calculate.offsetitem.all.sort");
        }
        ExportExcelSheet sheet = new ExportExcelSheet(Integer.valueOf(sheetNo), sheetTitle);
        CellStyle headString = cellStyleMap.get("headString");
        CellStyle headAmt = cellStyleMap.get("headAmt");
        CellStyle contentString = cellStyleMap.get("contentString");
        CellStyle contentAmt = cellStyleMap.get("contentAmt");
        CellStyle[] headStyles = new CellStyle[]{headString, headString, headString, headString, headString, headString, headString, headAmt, headAmt, headString};
        CellStyle[] contentStyles = new CellStyle[]{contentString, contentString, contentString, contentString, contentString, contentString, contentString, contentAmt, contentAmt, contentString};
        boolean showAssetTitle = false;
        if (sheetTitle.startsWith(GcI18nUtil.getMessage((String)"gc.calculate.offsetitem.all.sort")) || sheetTitle.startsWith(GcI18nUtil.getMessage((String)"gc.offset.init.valueAdjustment"))) {
            headStyles = new CellStyle[]{headString, headString, headString, headString, headString, headString, headString, headAmt, headAmt, headString, headString, headString};
            contentStyles = new CellStyle[]{contentString, contentString, contentString, contentString, contentString, contentString, contentString, contentAmt, contentAmt, contentString, contentString, contentString};
            showAssetTitle = true;
        }
        for (int i = 0; i < headStyles.length; ++i) {
            if (context.isTemplateExportFlag() && i > 0) {
                sheet.getHeadCellStyleCache().put(i, headStyles[i + 1]);
                sheet.getContentCellStyleCache().put(i, contentStyles[i + 1]);
                if (i != headStyles.length - 2) continue;
                break;
            }
            sheet.getHeadCellStyleCache().put(i, headStyles[i]);
            sheet.getContentCellStyleCache().put(i, contentStyles[i]);
        }
        sheet.getContentCellTypeCache().put(7, ExcelUtils.ExportColumnTypeEnum.NUMERIC.getCode());
        sheet.getContentCellTypeCache().put(8, ExcelUtils.ExportColumnTypeEnum.NUMERIC.getCode());
        ArrayList<Object[]> rowDatas = new ArrayList<Object[]>();
        Object[] titles = this.excelHead(showAssetTitle, queryParamsVO.getOtherShowColumnTitles(), context);
        rowDatas.add(titles);
        if (context.isTemplateExportFlag()) {
            this.getExcelHeadDescribe(titles, rowDatas, sheet, cellStyleMap.get("headDescribe"));
            sheet.getRowDatas().addAll(rowDatas);
            return sheet;
        }
        int rowIndex = 1;
        int lastMergeBeginRow = 1;
        int serialNumber = 1;
        ArrayList<Integer> mergedStartRow = new ArrayList<Integer>();
        ArrayList<Integer> mergedEndRow = new ArrayList<Integer>();
        if (offsetEntry.getContent().size() > 1) {
            String mrecid = (String)((Map)offsetEntry.getContent().get(0)).get("MRECID");
            for (Map item : offsetEntry.getContent()) {
                int currRowIndex = rowIndex++;
                if (!mrecid.equals(item.get("MRECID"))) {
                    ++serialNumber;
                    mrecid = (String)item.get("MRECID");
                    this.addMergedRegion(sheet, lastMergeBeginRow, currRowIndex - 1, 0, 0);
                    mergedStartRow.add(lastMergeBeginRow);
                    mergedEndRow.add(currRowIndex - 1);
                    lastMergeBeginRow = currRowIndex;
                }
                ArrayList<String> otherShowColumns = new ArrayList<String>(queryParamsVO.getOtherShowColumns());
                otherShowColumns.remove("OFFSETSRCTYPE");
                otherShowColumns.remove("ASSETTITLE");
                otherShowColumns.remove("EFFECTTYPE");
                rowDatas.add(this.excelOneRow(titles.length, item, serialNumber, showAssetTitle, otherShowColumns));
            }
            this.addMergedRegion(sheet, lastMergeBeginRow, rowIndex - 1, 0, 0);
            for (int i = 0; i < mergedStartRow.size(); ++i) {
                this.addMergedRegion(sheet, (Integer)mergedStartRow.get(i), (Integer)mergedEndRow.get(i), 1, 1);
            }
            this.addMergedRegion(sheet, lastMergeBeginRow, rowIndex - 1, 1, 1);
        }
        sheet.getRowDatas().addAll(rowDatas);
        return sheet;
    }

    private void getExcelHeadDescribe(Object[] titles, List<Object[]> rowDatas, ExportExcelSheet sheet, CellStyle contentString) {
        ArrayList<String> titleDescribeList = new ArrayList<String>();
        block26: for (Object title : titles) {
            switch (title.toString()) {
                case "\u5e8f\u53f7": {
                    titleDescribeList.add("\u5fc5\u586b\uff0c\u8f93\u5165\u521d\u59cb\u5316\u5206\u5f55\u7684\u5e8f\u53f7\uff0c\u5e8f\u53f7\u76f8\u540c\u7684\u5206\u5f55\u884c\u89c6\u4e3a\u4e00\u7ec4\u8c03\u6574\u62b5\u9500\u5206\u5f55\uff0c\u4f8b\u5982\u201c1\u201d\u3001\u201c2\u201d");
                    continue block26;
                }
                case "\u5408\u5e76\u89c4\u5219": {
                    titleDescribeList.add("\u5fc5\u586b\uff0c\u8f93\u5165\u521d\u59cb\u5316\u5206\u5f55\u7684\u6240\u5c5e\u89c4\u5219\u540d\u79f0\uff0c\u4f8b\u5982\uff1a\u201c\u5e94\u6536\u5e94\u4ed8\u62b5\u9500\u89c4\u5219\u201d");
                    continue block26;
                }
                case "\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b": {
                    titleDescribeList.add("\u5fc5\u586b\uff0c\u8f93\u5165\u521d\u59cb\u5316\u5206\u5f55\u6240\u5c5e\u89c4\u5219\u7684\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u540d\u79f0\uff0c\u4f8b\u5982\u201c\u5f80\u6765\u7c7b\u201d");
                    continue block26;
                }
                case "\u672c\u65b9\u5355\u4f4d": {
                    titleDescribeList.add("\u5fc5\u586b\uff0c\u8f93\u5165\u521d\u59cb\u5316\u5206\u5f55\u8bb0\u5f55\u884c\u7684\u672c\u65b9\u5355\u4f4d\uff0c\u652f\u6301\u5f55\u5165\u5355\u4f4d\u7f16\u7801\u3001\u540d\u79f0\u3001\u7f16\u7801|\u540d\u79f0\uff0c\u4f8b\u5982\u201c2002280\u201d\u3001\u201c\u4e45\u5176\u5929\u6d25\u516c\u53f8\u672c\u90e8\u201d\u3001\u201c2002280|\u4e45\u5176\u5929\u6d25\u516c\u53f8\u672c\u90e8\u201d");
                    continue block26;
                }
                case "\u5bf9\u65b9\u5355\u4f4d": {
                    titleDescribeList.add("\u5fc5\u586b\uff0c\u8f93\u5165\u521d\u59cb\u5316\u5206\u5f55\u8bb0\u5f55\u884c\u7684\u5bf9\u65b9\u5355\u4f4d\uff0c\u652f\u6301\u5f55\u5165\u5355\u4f4d\u7f16\u7801\u3001\u540d\u79f0\u3001\u7f16\u7801|\u540d\u79f0\uff0c\u4f8b\u5982\u201c2002280\u201d\u3001\u201c\u4e45\u5176\u5929\u6d25\u516c\u53f8\u672c\u90e8\u201d\u3001\u201c2002280|\u4e45\u5176\u5929\u6d25\u516c\u53f8\u672c\u90e8\u201d");
                    continue block26;
                }
                case "\u79d1\u76ee": {
                    titleDescribeList.add("\u5fc5\u586b\uff0c\u8f93\u5165\u521d\u59cb\u5316\u5206\u5f55\u8bb0\u5f55\u884c\u7684\u79d1\u76ee\uff0c\u652f\u6301\u5f55\u5165\u79d1\u76ee\u7f16\u7801\u3001\u540d\u79f0\u3001\u7f16\u7801|\u540d\u79f0\uff0c\u4f8b\u5982\u201c1001\u201d\u3001\u201c\u5e93\u5b58\u73b0\u91d1\u201d\u3001\u201c1001|\u5e93\u5b58\u73b0\u91d1\u201d");
                    continue block26;
                }
                case "\u501f\u65b9\u91d1\u989d": {
                    titleDescribeList.add("\u521d\u59cb\u5316\u5206\u5f55\u5bf9\u5e94\u79d1\u76ee\u501f\u65b9\u91d1\u989d\uff0c\u975e\u5fc5\u586b");
                    continue block26;
                }
                case "\u8d37\u65b9\u91d1\u989d": {
                    titleDescribeList.add("\u521d\u59cb\u5316\u5206\u5f55\u5bf9\u5e94\u79d1\u76ee\u8d37\u65b9\u91d1\u989d\uff0c\u975e\u5fc5\u586b");
                    continue block26;
                }
                case "\u8d44\u4ea7\u540d\u79f0": {
                    titleDescribeList.add("\u4ec5\u5bfc\u5165\u516c\u5141\u4ef7\u503c\u53f0\u8d26\u8c03\u6574\u5206\u5f55\u9700\u8981\u586b\u5199\uff0c\u8868\u793a\u9700\u8981\u8fdb\u884c\u516c\u5141\u4ef7\u503c\u8c03\u6574\u7684\u8d44\u4ea7\u540d\u79f0\uff0c\u652f\u6301\u586b\u5199\u4ee3\u7801\u3001\u540d\u79f0\u3001\u4ee3\u7801|\u540d\u79f0\uff0c\u4f8b\u5982\u201c01\u201d\u3001\u201c\u56fa\u5b9a\u8d44\u4ea7\u201d\u3001\u201c01|\u56fa\u5b9a\u8d44\u4ea7\u201d");
                    continue block26;
                }
                case "\u5f71\u54cd\u4e0b\u5e74": {
                    titleDescribeList.add("\u786e\u5b9a\u521d\u59cb\u5316\u5206\u5f55\u662f\u5426\u9700\u8981\u5e74\u7ed3\uff0c\u8bf7\u586b\u201c\u662f\u201d\u6216\u201c\u5426\u201d\uff0c\u586b\u201c\u662f\u201d\u65f6\uff0c\u5bfc\u5165\u540e\u8be5\u7ec4\u62b5\u9500\u5206\u5f55\u7684\u201c\u5f71\u54cd\u4e0b\u5e74\u201d\u52fe\u9009\uff0c\u586b\u201c\u5426\u201d\u6216\u8005\u4e3a\u7a7a\u65f6\uff0c\u5bfc\u5165\u540e\u8be5\u7ec4\u62b5\u9500\u5206\u5f55\u7684\u201c\u5f71\u54cd\u4e0b\u5e74\u201d\u4e0d\u52fe\u9009");
                    continue block26;
                }
                case "\u63cf\u8ff0": {
                    titleDescribeList.add("\u521d\u59cb\u5316\u5206\u5f55\u8bb0\u5f55\u884c\u7684\u63cf\u8ff0\u4fe1\u606f\uff0c\u975e\u5fc5\u586b");
                    continue block26;
                }
            }
        }
        titleDescribeList.add("\u586b\u5199\u8bf4\u660e\uff0c\u5bfc\u5165\u65f6\u8bf7\u5220\u9664\u6b64\u884c");
        for (int i = 0; i < titleDescribeList.size(); ++i) {
            sheet.getContentCellStyleCache().put(i, contentString);
            sheet.getContentCellTypeCache().put(i, ExcelUtils.ExportColumnTypeEnum.STRING.getCode());
        }
        rowDatas.add(titleDescribeList.toArray());
    }

    private void initSheetIntervalRowSet(int sheetNo, ExportContext context, Pagination<Map<String, Object>> offsetEntry) {
        List content = offsetEntry.getContent();
        if (CollectionUtils.isEmpty((Collection)content)) {
            return;
        }
        HashSet<Integer> rowNumber = new HashSet<Integer>();
        HashSet<String> mrecids = new HashSet<String>();
        for (int i = 0; i < content.size(); ++i) {
            Map item = (Map)content.get(i);
            if (item.get("MRECID") != null) {
                mrecids.add(item.get("MRECID").toString());
            }
            if (mrecids.size() % 2 != 1) continue;
            rowNumber.add(i + 1);
        }
        context.getVarMap().put(sheetNo + "", rowNumber);
    }

    private void addMergedRegion(ExportExcelSheet sheet, int rowStart, int rowEnd, int colStart, int colEnd) {
        if (rowStart == rowEnd && colStart == colEnd) {
            return;
        }
        CellRangeAddress region = new CellRangeAddress(rowStart, rowEnd, colStart, colEnd);
        sheet.getCellRangeAddresses().add(region);
    }

    private void initBusinessTypeCode(QueryParamsVO queryParamsVO, String gcbusinesstypecode) {
        Map filterCondition = queryParamsVO.getFilterCondition();
        filterCondition.put("gcbusinesstypecode", gcbusinesstypecode);
        ArrayList<String> allChildCodes = new ArrayList<String>();
        String rootCode = (String)filterCondition.get("gcbusinesstypecode");
        if (StringUtils.isEmpty(rootCode)) {
            filterCondition.remove("gcbusinesstypecode");
        } else if (!"OnlyQueryEmpty".equals(rootCode)) {
            allChildCodes.add(rootCode);
            GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
            List baseDatas = tool.queryAllBasedataItemsByParentid("MD_GCBUSINESSTYPE", rootCode);
            for (GcBaseData iBaseData : baseDatas) {
                allChildCodes.add(iBaseData.getCode());
            }
            filterCondition.put("gcbusinesstypecode", allChildCodes);
        }
    }

    private Object[] excelOneRow(int colLength, Map<String, Object> item, int index, boolean showAssetTitle, List<String> otherShowColumns) {
        Object[] dataRow = new Object[colLength];
        int col = 0;
        dataRow[col++] = String.valueOf(index);
        dataRow[col++] = item.get("OFFSETSRCTYPENAME");
        dataRow[col++] = item.get("RULETITLE");
        dataRow[col++] = item.get("GCBUSINESSTYPE");
        dataRow[col++] = item.get("UNITTITLE");
        dataRow[col++] = item.get("OPPUNITTITLE");
        dataRow[col++] = item.get("SUBJECTTITLE");
        String offsetDebit = (String)item.get("OFFSETDEBIT");
        dataRow[col++] = StringUtils.isEmpty(offsetDebit) ? null : Double.valueOf(offsetDebit.replace(",", ""));
        String offsetCredit = (String)item.get("OFFSETCREDIT");
        Object object = dataRow[col++] = StringUtils.isEmpty(offsetCredit) ? null : Double.valueOf(offsetCredit.replace(",", ""));
        if (showAssetTitle) {
            dataRow[col++] = item.get("ASSETTITLE");
        }
        dataRow[col++] = item.get("MEMO");
        if (showAssetTitle) {
            dataRow[col++] = item.get("EFFECTTYPE");
        }
        for (String column : otherShowColumns) {
            dataRow[col++] = item.get(column);
        }
        return dataRow;
    }

    private String getString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    private Object[] excelHead(boolean showAssetTitle, List<String> otherShowColumnTitles, ExportContext context) {
        String[] titles = showAssetTitle ? new String[]{GcI18nUtil.getMessage((String)"gc.offset.init.index"), GcI18nUtil.getMessage((String)"gc.offset.init.source"), GcI18nUtil.getMessage((String)"gc.offset.init.ruleTitle"), GcI18nUtil.getMessage((String)"gc.offset.init.gcBusinessType"), GcI18nUtil.getMessage((String)"gc.offset.init.thisUnit"), GcI18nUtil.getMessage((String)"gc.offset.init.oppUnit"), GcI18nUtil.getMessage((String)"gc.offset.init.subject"), GcI18nUtil.getMessage((String)"gc.offset.init.debitAmount"), GcI18nUtil.getMessage((String)"gc.offset.init.creditAmount"), GcI18nUtil.getMessage((String)"gc.offset.init.propertyName"), GcI18nUtil.getMessage((String)"gc.offset.init.memo"), GcI18nUtil.getMessage((String)"gc.offset.init.impactNextYear")} : new String[]{GcI18nUtil.getMessage((String)"gc.offset.init.index"), GcI18nUtil.getMessage((String)"gc.offset.init.source"), GcI18nUtil.getMessage((String)"gc.offset.init.ruleTitle"), GcI18nUtil.getMessage((String)"gc.offset.init.gcBusinessType"), GcI18nUtil.getMessage((String)"gc.offset.init.thisUnit"), GcI18nUtil.getMessage((String)"gc.offset.init.oppUnit"), GcI18nUtil.getMessage((String)"gc.offset.init.subject"), GcI18nUtil.getMessage((String)"gc.offset.init.debitAmount"), GcI18nUtil.getMessage((String)"gc.offset.init.creditAmount"), GcI18nUtil.getMessage((String)"gc.offset.init.memo")};
        ArrayList<String> titleList = new ArrayList<String>();
        for (String title : titles) {
            if (context.isTemplateExportFlag() && GcI18nUtil.getMessage((String)"gc.offset.init.source").equals(title)) continue;
            titleList.add(title);
        }
        if (!CollectionUtils.isEmpty(otherShowColumnTitles)) {
            titleList.addAll(otherShowColumnTitles);
        }
        return titleList.toArray();
    }

    @Override
    public Map<String, Integer> countEveryBusinessType(OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO) {
        return this.offSetVchrItemInitDao.countEveryBusinessType(offsetItemInitQueryParamsVO);
    }

    @Override
    public Pagination<Map<String, Object>> queryOffsetEntry(OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO) {
        this.initQueryParams(offsetItemInitQueryParamsVO);
        Pagination<Map<String, Object>> offsetEntry = this.getOffsetEntry(offsetItemInitQueryParamsVO);
        offsetEntry.getContent().forEach(itemMap -> this.initOffSetItemMap((Map<String, Object>)itemMap));
        return offsetEntry;
    }

    private void initQueryParams(OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO) {
        Map filterCondition = offsetItemInitQueryParamsVO.getFilterCondition();
        if (null != filterCondition && filterCondition.size() > 0) {
            ArrayList<String> allChildCodes = new ArrayList<String>();
            String rootCode = (String)filterCondition.get("gcbusinesstypecode");
            if (!StringUtils.isEmpty(rootCode) && !"OnlyQueryEmpty".equals(rootCode)) {
                GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
                List baseDatas = tool.queryAllBasedataItemsByParentid("MD_GCBUSINESSTYPE", rootCode);
                for (GcBaseData iBaseData : baseDatas) {
                    allChildCodes.add(iBaseData.getCode());
                }
                allChildCodes.add(rootCode);
                filterCondition.put("gcbusinesstypecode", allChildCodes);
            }
        }
    }

    public void initOffSetItemMap(Map<String, Object> itemMap) {
        if (itemMap == null) {
            return;
        }
        if (OrientEnum.D.getValue() == (Integer)itemMap.get("ORIENT")) {
            itemMap.put("OFFSETCREDIT", "");
            itemMap.put("OFFSETDEBIT", df.format(ConverterUtils.getAsDouble((Object)itemMap.get("OFFSETDEBIT"))));
        } else {
            itemMap.put("OFFSETDEBIT", "");
            itemMap.put("OFFSETCREDIT", df.format(ConverterUtils.getAsDouble((Object)itemMap.get("OFFSETCREDIT"))));
        }
    }

    @Override
    public Pagination<Map<String, Object>> getOffsetEntry(OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO) {
        Pagination<Map<String, Object>> page = this.offSetVchrItemInitDao.queryInitOffsetEntry(offsetItemInitQueryParamsVO);
        QueryParamsVO queryParamsVO = new QueryParamsVO();
        BeanUtils.copyProperties(offsetItemInitQueryParamsVO, queryParamsVO);
        return this.offSetItemAdjustService.assembleOffsetEntry(page, queryParamsVO, "GC_OFFSETVCHRITEM_INIT");
    }

    @Override
    public List<Map<String, Object>> getPartFieldOffsetEntry(OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO) {
        List<Map<String, Object>> partFieldDatas = this.offSetVchrItemInitDao.queryInitOffsetPartFieldEntry(offsetItemInitQueryParamsVO);
        return partFieldDatas;
    }

    @Override
    public List<GcBusinessTypeCountVO> rootBusinessTypes(OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO) {
        ArrayList<GcBusinessTypeCountVO> gcBusinessTypeCountVOs = new ArrayList<GcBusinessTypeCountVO>();
        int totalCount = 0;
        try {
            GcBaseDataCenterTool tool = GcBaseDataCenterTool.getInstance();
            List rootBasedatas = tool.queryRootBasedataItems("MD_GCBUSINESSTYPE");
            Map<String, Integer> businessTypeCode2Count = this.countEveryBusinessType(offsetItemInitQueryParamsVO);
            for (GcBaseData rootBasedata : rootBasedatas) {
                List baseDatas = tool.queryAllBasedataItemsByParentid("MD_GCBUSINESSTYPE", rootBasedata.getCode());
                int currRootCount = 0;
                Integer count = businessTypeCode2Count.remove(rootBasedata.getCode());
                if (null != count) {
                    currRootCount += count.intValue();
                }
                for (GcBaseData iBaseData2 : baseDatas) {
                    String code = iBaseData2.getCode();
                    count = businessTypeCode2Count.remove(code);
                    if (null == count) continue;
                    currRootCount += count.intValue();
                }
                if (currRootCount == 0) continue;
                totalCount += currRootCount;
                gcBusinessTypeCountVOs.add(new GcBusinessTypeCountVO(rootBasedata.getCode(), rootBasedata.getTitle(), currRootCount));
            }
            for (String code : businessTypeCode2Count.keySet()) {
                Integer count = businessTypeCode2Count.get(code);
                if (count == null || count == 0) continue;
                totalCount += count.intValue();
                String title = GcI18nUtil.getMessage((String)"gc.offset.init.import.fail");
                if (StringUtils.isEmpty(code)) {
                    code = "OnlyQueryEmpty";
                    title = GcI18nUtil.getMessage((String)"gc.offset.init.no.rule");
                }
                gcBusinessTypeCountVOs.add(new GcBusinessTypeCountVO(code, title, count.intValue()));
            }
        }
        catch (Exception e) {
            LOGGER.error("\u83b7\u53d6\u5408\u5e76\u4e1a\u52a1\u7c7b\u578b\u5931\u8d25\uff1a" + e.getMessage(), e);
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.offset.init.get.gcBusinessType.fail") + "\uff1a" + e.getMessage(), (Throwable)e);
        }
        gcBusinessTypeCountVOs.add(0, new GcBusinessTypeCountVO("", GcI18nUtil.getMessage((String)"gc.calculate.offsetitem.all.sort"), totalCount));
        return gcBusinessTypeCountVOs;
    }

    @Override
    public void deleteAllInitOffsetEntrys(OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO) {
        Assert.isNotNull((Object)offsetItemInitQueryParamsVO.getOrgId(), (String)GcI18nUtil.getMessage((String)"gc.offset.init.mergeUnit.not.null"), (Object[])new Object[0]);
        Assert.isNotNull((Object)offsetItemInitQueryParamsVO.getSystemId(), (String)GcI18nUtil.getMessage((String)"gc.offset.init.system.not.null"), (Object[])new Object[0]);
        Assert.isNotNull((Object)offsetItemInitQueryParamsVO.getTaskId(), (String)GcI18nUtil.getMessage((String)"gc.offset.init.task.not.null"), (Object[])new Object[0]);
        Assert.isNotNull((Object)offsetItemInitQueryParamsVO.getAcctYear(), (String)GcI18nUtil.getMessage((String)"gc.offset.init.year.not.null"), (Object[])new Object[0]);
        HashSet<String> mrecids = new HashSet<String>();
        this.offSetVchrItemInitDao.queryMrecids(offsetItemInitQueryParamsVO, mrecids);
        if (CollectionUtils.isEmpty(mrecids)) {
            throw new BusinessRuntimeException("\u5df2\u65e0\u53ef\u5220\u9664\u6570\u636e\uff0c\u8bf7\u786e\u8ba4");
        }
        this.deleteInitAdjustSameSrcId(mrecids, offsetItemInitQueryParamsVO.getTaskId());
        String systemName = ((ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)offsetItemInitQueryParamsVO.getSystemId()))).getSystemName();
        LogHelper.info((String)"\u5408\u5e76-\u5206\u5f55\u521d\u59cb\u5316", (String)("\u5168\u5220-\u4f53\u7cfb" + systemName + "-\u65f6\u671f" + offsetItemInitQueryParamsVO.getAcctYear()), (String)"");
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteInitAdjustSameSrcId(Collection<String> mrecids, String taskId) {
        Assert.isNotEmpty(mrecids, (String)GcI18nUtil.getMessage((String)"gc.offset.init.deleteFail"), (Object[])new Object[0]);
        try {
            Collection<String> newMrecids = this.offSetVchrItemInitDao.getMrecidsBySameSrcId(mrecids);
            newMrecids.addAll(mrecids);
            Collection<String> srcOffsetGroupIds = this.offSetVchrItemInitDao.getSrcOffsetGroupIdsByMrecid(newMrecids, null, null, null, null, null);
            this.cancelInputOffsetByOffsetGroupId(srcOffsetGroupIds, taskId);
            mrecids.remove(null);
            if (!CollectionUtils.isEmpty(mrecids)) {
                this.offSetVchrItemInitDao.deleteByMrecid(mrecids, null, null);
            }
        }
        catch (Exception e) {
            LOGGER.error("\u5220\u9664\u64cd\u4f5c\u5931\u8d25\uff1a" + e.getMessage(), e);
            String message = String.format("\u5220\u9664\u64cd\u4f5c\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f:%s", e.getMessage());
            throw new BusinessRuntimeException(message);
        }
    }

    @Override
    public void batchSave(List<GcOffSetVchrDTO> offSetItemDTOs) {
        if (CollectionUtils.isEmpty(offSetItemDTOs)) {
            return;
        }
        for (GcOffSetVchrDTO dto : offSetItemDTOs) {
            GcOffSetVchrItemDTO itemDTO = (GcOffSetVchrItemDTO)dto.getItems().get(0);
            String srcOffsetGroupId = itemDTO.getSrcOffsetGroupId();
            ArrayList<String> srcOffsetGroupIds = new ArrayList<String>();
            srcOffsetGroupIds.add(srcOffsetGroupId);
        }
        offSetItemDTOs.forEach(offSetItemDTO -> {
            this.offSetVchrItemInitDao.deleteByMrecid(Arrays.asList(offSetItemDTO.getMrecid()), ((GcOffSetVchrItemDTO)offSetItemDTO.getItems().get(0)).getAcctYear(), ((GcOffSetVchrItemDTO)offSetItemDTO.getItems().get(0)).getOffSetCurr());
            this.saveSingleGroup((GcOffSetVchrDTO)offSetItemDTO);
        });
        this.doSaveLog(offSetItemDTOs);
    }

    private void doSaveLog(List<GcOffSetVchrDTO> offSetItemDTOs) {
        try {
            GcOffSetVchrItemDTO gcOffSetVchrItemDTO = (GcOffSetVchrItemDTO)offSetItemDTOs.get(0).getItems().get(0);
            Integer acctYear = gcOffSetVchrItemDTO.getAcctYear();
            StringBuffer message = new StringBuffer("\u8be6\u7ec6\u4fe1\u606f\uff1a");
            HashSet unitOppunitRuleSet = new HashSet();
            offSetItemDTOs.forEach(dto -> dto.getItems().forEach(item -> {
                String unitOppUnitRuleStr = item.getUnitId() + "|" + item.getOppUnitId() + "|" + item.getRuleId();
                if (unitOppunitRuleSet.contains(unitOppUnitRuleStr)) {
                    return;
                }
                unitOppunitRuleSet.add(unitOppUnitRuleStr);
                message.append("\u672c\u65b9\u5355\u4f4d:").append(item.getUnitId()).append("\u5bf9\u65b9\u5355\u4f4d").append(item.getOppUnitId()).append("\u89c4\u5219").append(item.getRuleId()).append("\n");
            }));
            String systemName = ((ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)gcOffSetVchrItemDTO.getSystemId()))).getSystemName();
            LogHelper.info((String)"\u5408\u5e76-\u5206\u5f55\u521d\u59cb\u5316", (String)("\u65b0\u589e/\u4fee\u6539-\u4f53\u7cfb" + systemName + "-\u65f6\u671f" + acctYear), (String)message.toString());
        }
        catch (Exception e) {
            LOGGER.error("\u8bb0\u5f55\u4fdd\u5b58\u65e5\u5fd7\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }

    private GcOffSetVchrDTO saveSingleGroup(GcOffSetVchrDTO offSetItemDTO) {
        if (!StringUtils.hasLength(offSetItemDTO.getVchrCode())) {
            OffsetVchrCodeDTO vchrCodeDTO = new OffsetVchrCodeDTO();
            vchrCodeDTO.setPeriodType(0);
            vchrCodeDTO.setAcctYear(((GcOffSetVchrItemDTO)offSetItemDTO.getItems().get(0)).getAcctYear().intValue());
            offSetItemDTO.setVchrCode(OffsetVchrCodeUtil.createVchrCode((OffsetVchrCodeDTO)vchrCodeDTO));
        }
        offSetItemDTO.setConsFormulaCalcType("inputFlag");
        this.inputAdjustService.exeConsFormulaCalcOneGroup(offSetItemDTO);
        OffsetItemCheckResult offsetItemCheckResult = this.offSetCoreGroupChecker.saveCheck(offSetItemDTO);
        if (offsetItemCheckResult != null && !offsetItemCheckResult.isSuccess()) {
            throw new BusinessRuntimeException("\u62b5\u9500\u5206\u5f55\u521d\u59cb\u4fdd\u5b58\u6821\u9a8c\u5931\u8d25\uff1a" + offsetItemCheckResult.getMessage());
        }
        List itemDTOs = offSetItemDTO.getItems();
        if (itemDTOs != null && itemDTOs.size() > 0) {
            Date date = new Date();
            List offSetVchrItemInitEOS = itemDTOs.stream().map(itemDTO -> {
                itemDTO.setCreateTime(date);
                GcOffSetVchrItemInitEO itemEO = this.convertDTO2EO((GcOffSetVchrItemDTO)itemDTO);
                if (StringUtils.isEmpty(itemEO.getId())) {
                    String id = UUIDOrderSnowUtils.newUUIDStr();
                    itemEO.setId(id);
                    itemDTO.setId(id);
                }
                itemEO.setmRecid(offSetItemDTO.getMrecid());
                itemEO.setVchrCode(offSetItemDTO.getVchrCode());
                itemDTO.setmRecid(itemEO.getmRecid());
                return itemEO;
            }).collect(Collectors.toList());
            this.monitorExecute(monitor -> monitor.beforeSave(offSetVchrItemInitEOS));
            this.offSetVchrItemInitDao.saveAll(offSetVchrItemInitEOS);
            this.monitorExecute(monitor -> monitor.afterSave(offSetVchrItemInitEOS));
        }
        return offSetItemDTO;
    }

    void monitorExecute(Consumer<IOffsetInitMonitor> function) {
        if (CollectionUtils.isEmpty(this.offsetInitMonitors)) {
            return;
        }
        for (IOffsetInitMonitor offsetInitMonitor : this.offsetInitMonitors) {
            try {
                function.accept(offsetInitMonitor);
            }
            catch (Exception e) {
                LOGGER.error("\u5206\u5f55\u521d\u59cb\u5316\u76d1\u542c\u5668\u3010" + offsetInitMonitor.monitorName() + "\u3011\u6267\u884c\u5f02\u5e38:", e);
            }
        }
    }

    public GcOffSetVchrItemInitEO convertDTO2EO(GcOffSetVchrItemDTO itemDTO) {
        if (itemDTO == null) {
            return null;
        }
        GcOffSetVchrItemInitEO itemEo = new GcOffSetVchrItemInitEO();
        BeanUtils.copyProperties(itemDTO, (Object)itemEo);
        itemEo.setDisableFlag(Objects.equals(Boolean.TRUE, itemDTO.getDisableFlag()) ? 1 : 0);
        if (null == itemEo.getSrcOffsetGroupId()) {
            itemEo.setSrcOffsetGroupId(itemEo.getmRecid());
        }
        Map unSysFields = itemDTO.getUnSysFields();
        for (Map.Entry unSysField : unSysFields.entrySet()) {
            if (unSysField.getValue() instanceof Map) {
                itemEo.addFieldValue((String)unSysField.getKey(), ((Map)unSysField.getValue()).get("code"));
            } else if (unSysField.getValue() instanceof GcBaseDataDO) {
                itemEo.addFieldValue((String)unSysField.getKey(), ((GcBaseDataDO)unSysField.getValue()).getCode());
            } else {
                itemEo.addFieldValue((String)unSysField.getKey(), unSysField.getValue());
            }
            if (!StringUtils.isEmpty(ConverterUtils.getAsString((Object)itemDTO.getFieldValue((String)unSysField.getKey())))) continue;
            itemDTO.addFieldValue((String)unSysField.getKey(), null);
        }
        itemEo.setOffSetSrcType(OffSetSrcTypeEnum.getEnumValue((OffSetSrcTypeEnum)itemDTO.getOffSetSrcType()));
        itemEo.setSubjectOrient(itemDTO.getSubjectOrient().getValue());
        itemEo.setOrient(itemDTO.getOrient().getValue());
        itemEo.setVchrCode(itemDTO.getVchrCode());
        return itemEo;
    }

    @Override
    public GcOffSetVchrDTO getOne(String mrecid) {
        GcOffSetVchrDTO dto;
        if (mrecid == null) {
            throw new BusinessRuntimeException("\u62b5\u9500\u5206\u5f55\u67e5\u8be2\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f[\u62b5\u9500\u5206\u5f55\u5206\u7ec4ID\u53c2\u6570\u4e0d\u5141\u8bb8\u4e3a\u7a7a] ");
        }
        try {
            List<GcOffSetVchrItemInitEO> itemEOs = this.offSetVchrItemInitDao.findByMrecidOrderBySortOrder(mrecid);
            dto = this.convertEO2DTO(mrecid, itemEOs);
        }
        catch (Exception e) {
            String message = String.format("\u62b5\u9500\u5206\u5f55\u67e5\u8be2\u5931\u8d25\uff0c\u8be6\u7ec6\u4fe1\u606f[%s]\u3002", e.getMessage());
            throw new BusinessRuntimeException(message, (Throwable)e);
        }
        return dto;
    }

    private GcOffSetVchrDTO convertEO2DTO(String mrecid, List<GcOffSetVchrItemInitEO> itemEOs) {
        List itemDTOs = null;
        if (itemEOs != null && itemEOs.size() > 0) {
            itemDTOs = itemEOs.stream().map(itemEO -> this.convertEO2DTO((GcOffSetVchrItemInitEO)((Object)itemEO), null)).collect(Collectors.toList());
        }
        GcOffSetVchrDTO dto = new GcOffSetVchrDTO();
        dto.setMrecid(mrecid);
        dto.setItems(itemDTOs);
        return dto;
    }

    private GcOffSetVchrItemDTO convertEO2DTO(GcOffSetVchrItemInitEO itemEO, OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO) {
        if (itemEO == null) {
            return null;
        }
        GcOffSetVchrItemDTO itemDTO = new GcOffSetVchrItemDTO();
        BeanUtils.copyProperties((Object)itemEO, itemDTO);
        itemDTO.setDisableFlag(Boolean.valueOf(Objects.equals(1, itemEO.getDisableFlag())));
        itemDTO.resetFields(new HashMap());
        itemDTO.getFields().putAll(itemEO.getFields());
        itemDTO.setOffSetSrcType(OffSetSrcTypeEnum.getEnumByValue((int)itemEO.getOffSetSrcType()));
        itemDTO.setOffSetCredit(itemEO.getOffSetCredit());
        itemDTO.setOffSetDebit(itemEO.getOffSetDebit());
        itemDTO.setDiffc(itemEO.getDiffc());
        itemDTO.setDiffd(itemEO.getDiffd());
        if (OffSetSrcTypeEnum.OFFSET_ITEM_INIT.getSrcTypeValue() == itemEO.getOffSetSrcType().intValue()) {
            if (OrientEnum.D.getValue() == itemEO.getOrient()) {
                itemDTO.setOrient(OrientEnum.D);
            } else if (OrientEnum.C.getValue() == itemEO.getOrient()) {
                itemDTO.setOrient(OrientEnum.C);
            }
        }
        itemDTO.setSelectAdjustCode("0");
        if (offsetItemInitQueryParamsVO != null) {
            itemDTO.setSelectAdjustCode(offsetItemInitQueryParamsVO.getSelectAdjustCode());
        }
        return itemDTO;
    }

    @Override
    public List<GcOffSetVchrItemVO> getInvestmentOffsetItemByMrecids(List<String> mrecids) {
        List<GcOffSetVchrItemInitEO> eos = this.offSetVchrItemInitDao.getInvestmentOffsetItemByMrecids(mrecids);
        List itemVOs = null;
        if (eos != null && eos.size() > 0) {
            itemVOs = eos.stream().map(itemEO -> {
                GcOffSetVchrItemVO vo = new GcOffSetVchrItemVO();
                BeanUtils.copyProperties(itemEO, vo);
                vo.setDisableFlag(Boolean.valueOf(Objects.equals(1, itemEO.getDisableFlag())));
                return vo;
            }).collect(Collectors.toList());
        }
        return itemVOs;
    }

    @Override
    public GcOffSetVchrDTO save(GcOffSetVchrDTO dto) {
        return this.saveSingleGroup(dto);
    }

    @Override
    public List<GcOffSetVchrItemDTO> findByInvestment(GcOffsetItemQueryCondi condi) {
        if (StringUtils.isEmpty(condi.unitCode) || StringUtils.isEmpty(condi.oppUnitCode)) {
            return null;
        }
        List dimensionVOs = this.optionService.getDimensionsByTableName("GC_OFFSETVCHRITEM_INIT", condi.systemId);
        List<GcOffSetVchrItemInitEO> itemEOs = this.offSetVchrItemInitDao.findByInvestment(condi, dimensionVOs);
        List itemDTOs = null;
        if (!org.springframework.util.CollectionUtils.isEmpty(itemEOs)) {
            itemDTOs = itemEOs.stream().map(itemEO -> this.convertEO2DTO((GcOffSetVchrItemInitEO)((Object)itemEO), null)).collect(Collectors.toList());
        }
        return itemDTOs;
    }

    @Override
    public List<GcOffSetVchrDTO> queryOffSetGroupDTOs(OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO) {
        Pagination<GcOffSetVchrItemInitEO> page = this.offSetVchrItemInitDao.queryOffsetingEntryEO(offsetItemInitQueryParamsVO);
        List itemEOs = page.getContent();
        List itemDTOs = itemEOs.stream().map(itemPO -> this.convertEO2DTO((GcOffSetVchrItemInitEO)((Object)itemPO), offsetItemInitQueryParamsVO)).collect(Collectors.toList());
        ArrayList<GcOffSetVchrDTO> groups = new ArrayList<GcOffSetVchrDTO>(128);
        ArrayList<GcOffSetVchrItemDTO> oneGroupItems = new ArrayList<GcOffSetVchrItemDTO>(8);
        String currMrecid = UUIDUtils.emptyUUIDStr();
        String currVchrCode = "";
        for (GcOffSetVchrItemDTO itemDTO : itemDTOs) {
            if (currMrecid.equals(itemDTO.getmRecid())) {
                oneGroupItems.add(itemDTO);
                continue;
            }
            if (!oneGroupItems.isEmpty()) {
                GcOffSetVchrDTO groupDTO = new GcOffSetVchrDTO();
                groupDTO.setItems(oneGroupItems);
                groupDTO.setMrecid(currMrecid);
                groupDTO.setVchrCode(currVchrCode);
                groups.add(groupDTO);
            }
            currMrecid = itemDTO.getmRecid();
            currVchrCode = itemDTO.getVchrCode();
            oneGroupItems = new ArrayList(8);
            oneGroupItems.add(itemDTO);
        }
        if (!oneGroupItems.isEmpty()) {
            GcOffSetVchrDTO groupDTO = new GcOffSetVchrDTO();
            groupDTO.setItems(oneGroupItems);
            groupDTO.setMrecid(currMrecid);
            groupDTO.setVchrCode(currVchrCode);
            groups.add(groupDTO);
        }
        return groups;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void deleteOffsetEntrysBySrcOffsetGroupId(Collection<String> srcOffsetGroupIds, String taskId, Integer acctYear, Integer acctPeriod, String orgTypeId, String currencyCode) {
        if (CollectionUtils.isEmpty(srcOffsetGroupIds)) {
            return;
        }
        this.cancelInputOffsetByOffsetGroupId(srcOffsetGroupIds, taskId);
        Collection<String> mrecids = this.offSetVchrItemInitDao.getMrecidsBySrcOffsetGroupId(srcOffsetGroupIds, taskId, acctYear, acctPeriod, orgTypeId, null, currencyCode);
        if (!CollectionUtils.isEmpty(mrecids)) {
            this.offSetVchrItemInitDao.deleteByMrecid(mrecids, null, null);
            this.doLogByMrecIdList(mrecids, "\u5220\u9664");
        }
    }

    private void doLogByMrecIdList(Collection<String> mrecids, String title) {
        try {
            List<GcOffSetVchrItemInitEO> gcOffSetVchrItemInitEOS = this.offSetVchrItemInitDao.queryOffsetingEntryEO(mrecids);
            if (CollectionUtils.isEmpty(gcOffSetVchrItemInitEOS)) {
                return;
            }
            Integer acctYear = gcOffSetVchrItemInitEOS.get(0).getAcctYear();
            String systemId = gcOffSetVchrItemInitEOS.get(0).getSystemId();
            StringBuffer message = new StringBuffer("\u8be6\u7ec6\u4fe1\u606f\uff1a");
            HashSet unitOppunitRuleSet = new HashSet();
            gcOffSetVchrItemInitEOS.forEach(item -> {
                String unitOppUnitRuleStr = item.getUnitId() + "|" + item.getOppUnitId() + "|" + item.getRuleId();
                if (unitOppunitRuleSet.contains(unitOppUnitRuleStr)) {
                    return;
                }
                unitOppunitRuleSet.add(unitOppUnitRuleStr);
                message.append("\u672c\u65b9\u5355\u4f4d:").append(item.getUnitId()).append("\u5bf9\u65b9\u5355\u4f4d").append(item.getOppUnitId()).append("\u89c4\u5219").append(item.getRuleId()).append("\n");
            });
            String systemName = ((ConsolidatedSystemEO)this.consolidatedSystemDao.get((Serializable)((Object)systemId))).getSystemName();
            LogHelper.info((String)"\u5408\u5e76-\u5206\u5f55\u521d\u59cb\u5316", (String)(title + "-\u4f53\u7cfb" + systemName + "\u65f6\u671f-" + acctYear), (String)message.toString());
        }
        catch (Exception e) {
            LOGGER.error("\u8bb0\u5f55\u5220\u9664\u65e5\u5fd7\u62a5\u9519\uff1a" + e.getMessage(), e);
        }
    }

    @Override
    public boolean hasOffsetRecordByUnitAndRuleType(String srcOffsetGroupId, Integer acctYear, boolean isTz) {
        return this.offSetVchrItemInitDao.hasOffsetRecordByUnitAndRuleType(srcOffsetGroupId, acctYear, isTz);
    }

    @Override
    @Transactional
    public void changeAssetTitle(String unitId, String oppUnitId, Integer acctYear, String oldAssetTitle, String newAssetTitle) {
        this.offSetVchrItemInitDao.changeAssetTitle(unitId, oppUnitId, acctYear, oldAssetTitle, newAssetTitle);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void downloadErrorExcel(String sn, HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            String fileName = ErrorFileName + sn + ".xls";
            File file = this.downloadFromMongoDb(fileName);
            if (file.length() == 0L) {
                file.delete();
                throw new BusinessRuntimeException("\u6587\u4ef6\u540d\u4e3a\u7a7a\u4e0b\u8f7d\u5931\u8d25\uff01");
            }
            fileName = fileName.replaceAll("\\_[a-z0-9-]+", "");
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            byte[] buffer = new byte[1024];
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            ServletOutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (true) {
                if (i == -1) {
                    os.close();
                    this.close(bis);
                    this.close(fis);
                    return;
                }
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        }
        catch (BusinessRuntimeException e) {
            try {
                throw new BusinessRuntimeException((Throwable)e);
                catch (Exception e2) {
                    throw new BusinessRuntimeException("\u4e0b\u8f7dExcel\u6587\u4ef6\u51fa\u9519\uff01", (Throwable)e2);
                }
            }
            catch (Throwable throwable) {
                this.close(bis);
                this.close(fis);
                throw throwable;
            }
        }
    }

    private void cancelInputOffsetByOffsetGroupId(Collection<String> srcOffsetGroupIds, String taskId) {
        if (Objects.nonNull(this.inputDataOffsetCoreService)) {
            this.inputDataOffsetCoreService.cancelInputOffsetByOffsetGroupId(srcOffsetGroupIds, taskId);
        }
    }

    private void close(InputStream bis) {
        try {
            if (bis != null) {
                bis.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File downloadFromMongoDb(String fileName) throws IOException {
        return null;
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void updateOffsetInitDisabledFlag(List<String> mrecids, boolean isDisabled) {
        this.offSetVchrItemInitDao.updateOffsetInitDisabledFlag(mrecids, isDisabled);
        this.doLogByMrecIdList(mrecids, isDisabled ? "\u7981\u7528" : "\u542f\u7528");
    }

    @Override
    public List<GcOffSetVchrItemDTO> queryOffsetingEntryDTOSort(OffsetItemInitQueryParamsVO offsetItemInitQueryParamsVO) {
        QueryParamsDTO queryParamsDTO = new QueryParamsDTO();
        BeanUtils.copyProperties(offsetItemInitQueryParamsVO, queryParamsDTO);
        List adjustEOList = this.offsetCoreService.listEOWithFullGroup(queryParamsDTO).getContent();
        List<GcOffSetVchrItemAdjustEO> itemEOs = this.sortEO(adjustEOList);
        List<GcOffSetVchrItemDTO> itemDTOs = itemEOs.stream().map(itemPO -> OffsetCoreConvertUtil.convertEO2DTO((GcOffSetVchrItemAdjustEO)itemPO)).collect(Collectors.toList());
        return itemDTOs;
    }

    private List<GcOffSetVchrItemAdjustEO> sortEO(List<GcOffSetVchrItemAdjustEO> unSortedRecords) {
        if (org.springframework.util.CollectionUtils.isEmpty(unSortedRecords)) {
            return unSortedRecords;
        }
        ArrayList<GcOffSetVchrItemAdjustEO> sortedRecords = new ArrayList<GcOffSetVchrItemAdjustEO>();
        ArrayList<GcOffSetVchrItemAdjustEO> oneEntryRecords = new ArrayList<GcOffSetVchrItemAdjustEO>();
        String mrecid = null;
        for (GcOffSetVchrItemAdjustEO record : unSortedRecords) {
            String tempMrecid = (String)record.getFields().get("MRECID");
            if (null == mrecid || !mrecid.equals(tempMrecid)) {
                int size = oneEntryRecords.size();
                if (size > 0) {
                    oneEntryRecords.sort(OffsetItemComparatorUtil.eoUniversalComparator());
                    sortedRecords.addAll(oneEntryRecords);
                    oneEntryRecords.clear();
                }
                mrecid = tempMrecid;
            }
            oneEntryRecords.add(record);
        }
        int size = oneEntryRecords.size();
        if (size > 0) {
            oneEntryRecords.sort(OffsetItemComparatorUtil.eoUniversalComparator());
            sortedRecords.addAll(oneEntryRecords);
            oneEntryRecords.clear();
        }
        unSortedRecords.clear();
        return sortedRecords;
    }
}

