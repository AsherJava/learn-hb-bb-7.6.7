/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.input.GroupType
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.FormulaUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.GroupType;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataentry.bean.DesCheckResult;
import com.jiuqi.nr.dataentry.bean.DesCheckReturnInfo;
import com.jiuqi.nr.dataentry.bean.ExportData;
import com.jiuqi.nr.dataentry.internal.service.util.CheckDesTransformUtil;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;
import com.jiuqi.nr.dataentry.service.IBatchCheckDesService;
import com.jiuqi.nr.dataentry.service.IBatchCheckResultService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.FormulaUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchCheckDesServiceImpl
implements IBatchCheckDesService {
    private static final Logger logger = LoggerFactory.getLogger(BatchCheckDesServiceImpl.class);
    private static final int CHECK_CHAR_NUM = 10;
    private static final int CHECK_MAX_NUM = 172;
    @Autowired
    private IBatchCheckResultService batchCheckResultService;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    @Autowired
    private ICheckErrorDescriptionService checkErrorDescriptionService;
    @Autowired
    private CheckDesTransformUtil checkDesTransformUtil;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;

    @Override
    public DesCheckResult desCheckResult(BatchCheckInfo batchCheckInfo) {
        CheckResultQueryParam checkResultQueryParam = new CheckResultQueryParam();
        checkResultQueryParam.setBatchId(batchCheckInfo.getAsyncTaskKey());
        JtableContext jtableContext = batchCheckInfo.getContext();
        DimensionValueSetUtil.fillDw((JtableContext)jtableContext, (String)batchCheckInfo.getDwScope());
        DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(jtableContext.getDimensionSet(), jtableContext.getFormSchemeKey());
        checkResultQueryParam.setDimensionCollection(dimensionCollection);
        GroupType groupType = GroupType.unit;
        if (StringUtils.isNotEmpty((String)batchCheckInfo.getOrderField())) {
            groupType = GroupType.getByKey((String)batchCheckInfo.getOrderField().toLowerCase());
        }
        checkResultQueryParam.setGroupType(groupType);
        Map<String, List<String>> formulas = batchCheckInfo.getFormulas();
        if (formulas.isEmpty()) {
            checkResultQueryParam.setMode(Mode.FORM);
            checkResultQueryParam.setRangeKeys(new ArrayList());
        } else {
            Iterator<Map.Entry<String, List<String>>> iterator = formulas.entrySet().iterator();
            if (iterator.hasNext()) {
                Map.Entry<String, List<String>> entry = iterator.next();
                if (entry.getValue().isEmpty()) {
                    checkResultQueryParam.setMode(Mode.FORM);
                    checkResultQueryParam.setRangeKeys(new ArrayList<String>(formulas.keySet()));
                } else if ("null".equals(entry.getKey()) && (groupType == GroupType.form_formula || groupType == GroupType.checktype_form)) {
                    checkResultQueryParam.setMode(Mode.FORM);
                    checkResultQueryParam.setRangeKeys(new ArrayList(entry.getValue()));
                } else {
                    checkResultQueryParam.setMode(Mode.FORMULA);
                    ArrayList<String> formulaKeys = new ArrayList<String>();
                    for (List<String> list : formulas.values()) {
                        formulaKeys.addAll(list);
                    }
                    checkResultQueryParam.setRangeKeys(formulaKeys);
                }
            }
        }
        checkResultQueryParam.setVariableMap(batchCheckInfo.getContext().getVariableMap());
        checkResultQueryParam.setPagerInfo(batchCheckInfo.getPagerInfo());
        HashMap<Integer, Boolean> checkTypes = new HashMap<Integer, Boolean>();
        boolean checkDesNull = batchCheckInfo.isCheckDesNull();
        List<Integer> uploadCheckTypes = batchCheckInfo.getUploadCheckTypes();
        List<Integer> checkTypes1 = batchCheckInfo.getCheckTypes();
        if (uploadCheckTypes.isEmpty()) {
            for (Integer integer : checkTypes1) {
                checkTypes.put(integer, null);
            }
        } else if (checkDesNull) {
            for (Integer integer : checkTypes1) {
                checkTypes.put(integer, false);
            }
        } else {
            for (Integer integer : checkTypes1) {
                checkTypes.put(integer, true);
            }
        }
        checkResultQueryParam.setCheckTypes(checkTypes);
        List list = FormulaUtil.getFormulaSchemeList((String)batchCheckInfo.getContext().getFormSchemeKey(), (String)batchCheckInfo.getFormulaSchemeKeys()).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        checkResultQueryParam.setFormulaSchemeKeys(list);
        return this.checkDesTransformUtil.getDesCheckResult(this.checkErrorDescriptionService.desCheckResult(checkResultQueryParam));
    }

    @Override
    public ExportData desCheckResultExport(BatchCheckInfo batchCheckInfo) {
        List<DesCheckReturnInfo> desCheckReturnInfos;
        DesCheckResult desCheckResult = this.desCheckResult(batchCheckInfo);
        List<DesCheckReturnInfo> list = desCheckReturnInfos = desCheckResult == null ? null : desCheckResult.getCheckReturnInfos();
        if (null != desCheckReturnInfos && desCheckReturnInfos.size() > 0) {
            ArrayList list2 = new ArrayList();
            XSSFWorkbook wb = new XSSFWorkbook();
            for (DesCheckReturnInfo desCheckReturnInfo : desCheckReturnInfos) {
                ArrayList<String> oneList = new ArrayList<String>();
                oneList.add(desCheckReturnInfo.getUnitCode());
                oneList.add(desCheckReturnInfo.getUnitTitle());
                list2.add(oneList);
            }
            String[] titles = new String[]{"\u5355\u4f4d\u4ee3\u7801", "\u5355\u4f4d\u540d\u79f0"};
            XSSFSheet sheet = wb.createSheet("\u51fa\u9519\u8bf4\u660e\u68c0\u67e5");
            sheet.setColumnWidth(0, 6400);
            sheet.setColumnWidth(1, 6400);
            XSSFRow row = sheet.createRow(0);
            XSSFCellStyle style = wb.createCellStyle();
            style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_80_PERCENT.getIndex());
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
            style.setAlignment(HorizontalAlignment.CENTER);
            XSSFFont font = wb.createFont();
            font.setFontHeightInPoints((short)11);
            font.setFontName("\u5b8b\u4f53");
            font.setBold(true);
            style.setFont(font);
            XSSFCell cell = null;
            for (int i = 0; i < titles.length; ++i) {
                cell = row.createCell(i);
                cell.setCellValue(titles[i]);
                cell.setCellStyle(style);
            }
            style = wb.createCellStyle();
            style.setAlignment(HorizontalAlignment.LEFT);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
            XSSFCellStyle style2 = wb.createCellStyle();
            style2.setAlignment(HorizontalAlignment.CENTER);
            style2.setBorderBottom(BorderStyle.THIN);
            style2.setBorderLeft(BorderStyle.THIN);
            style2.setBorderTop(BorderStyle.THIN);
            style2.setBorderRight(BorderStyle.THIN);
            if (null != list2 && list2.size() > 0) {
                for (int i = 0; i < list2.size(); ++i) {
                    row = sheet.createRow(i + 1);
                    List clist = (List)list2.get(i);
                    for (int n = 0; n < clist.size(); ++n) {
                        String value = (String)clist.get(n);
                        XSSFCell cellData = row.createCell(n);
                        cellData.setCellValue(value);
                        if (n == 0) {
                            cellData.setCellStyle(style2);
                            continue;
                        }
                        cellData.setCellStyle(style);
                    }
                }
            }
            if (null != wb) {
                ByteArrayOutputStream os = new ByteArrayOutputStream(0xA00000);
                try {
                    wb.write(os);
                }
                catch (IOException e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                byte[] byteArray = os.toByteArray();
                ExportData formulaExportData = new ExportData("\u51fa\u9519\u8bf4\u660e\u68c0\u67e5.xlsx", byteArray);
                return formulaExportData;
            }
            return null;
        }
        return null;
    }

    private int getCheckCharNum() {
        try {
            String charNumOfErrorMsg = this.iNvwaSystemOptionService.get("nr-audit-group", "CHAR_NUMBER_OF_ERROR_MSG");
            if (!StringUtils.isEmpty((String)charNumOfErrorMsg)) {
                int charNum = Integer.parseInt(charNumOfErrorMsg);
                return charNum;
            }
            return 10;
        }
        catch (Exception e) {
            return 10;
        }
    }

    private int getCheckCharMaxNum() {
        try {
            String charMAXNumOfErrorMsg = this.iNvwaSystemOptionService.get("nr-audit-group", "MAX_NUMBER_OF_ERROR_MSG");
            if (!StringUtils.isEmpty((String)charMAXNumOfErrorMsg)) {
                int charNum = Integer.parseInt(charMAXNumOfErrorMsg);
                return charNum;
            }
            return 172;
        }
        catch (Exception e) {
            return 172;
        }
    }
}

