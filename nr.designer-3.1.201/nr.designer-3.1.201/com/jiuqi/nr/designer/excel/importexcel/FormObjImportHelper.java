/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.common.FormViewType
 *  com.jiuqi.nr.definition.facade.DesignDataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.impl.FormExtendPropsDefaultValue
 *  com.jiuqi.nvwa.grid2.Grid2Data
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.designer.excel.importexcel;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.common.FormViewType;
import com.jiuqi.nr.definition.facade.DesignDataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.impl.FormExtendPropsDefaultValue;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.designer.excel.importexcel.FormObjStyleImportHelper;
import com.jiuqi.nr.designer.excel.importexcel.RegionObjImportHelper;
import com.jiuqi.nr.designer.excel.importexcel.cache.ExcelImportContext;
import com.jiuqi.nr.designer.excel.importexcel.common.ExcelType;
import com.jiuqi.nr.designer.excel.importexcel.common.FormulaImportContext;
import com.jiuqi.nr.designer.excel.importexcel.common.NrDesingerExcelErrorEnum;
import com.jiuqi.nr.designer.excel.importexcel.util.FormHelper;
import com.jiuqi.nr.designer.util.JQExceptionHelper;
import com.jiuqi.nvwa.grid2.Grid2Data;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FormObjImportHelper {
    private static final Logger log = LoggerFactory.getLogger(FormObjImportHelper.class);
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private RegionObjImportHelper regionObjImportHelper;
    @Autowired
    private FormHelper formHelper;
    @Autowired
    private FormObjStyleImportHelper formObjStyleImportHelper;
    private static final String CHECK_CHAR = "^[A-Z][0-9a-zA-Z_]*$";

    @Transactional(rollbackFor={Exception.class})
    public String createFormDefineOnlyStyle(Workbook workbook, ExcelType excelType, Sheet sheet, String groupKey, String schemeKey, String formulaSchemeKey, String taskKey) throws Exception {
        String oldTitle;
        if ((sheet.getLastRowNum() == 0 || sheet.getLastRowNum() == -1) && sheet.getRow(0) == null) {
            return "";
        }
        ExcelImportContext importContext = new ExcelImportContext();
        FormulaImportContext formulaImportContext = new FormulaImportContext();
        importContext.setFormulaImportContext(formulaImportContext);
        String formTilte = oldTitle = sheet.getSheetName();
        String fomeCode = "";
        if (oldTitle.contains("|")) {
            int index = oldTitle.indexOf("|");
            formTilte = oldTitle.substring(index + 1).trim();
            fomeCode = oldTitle.substring(0, index).trim().toUpperCase();
        }
        formTilte = this.getFormTitle(formTilte, schemeKey);
        if (StringUtils.isNotEmpty((String)(fomeCode = this.getFormCode(fomeCode, schemeKey))) && !Pattern.compile(CHECK_CHAR).matcher(fomeCode).matches()) {
            JQException e = new JQException((ErrorEnum)NrDesingerExcelErrorEnum.NRDESINGER_EXCEPTION_020);
            NrDesignLogHelper.log(oldTitle + " \u8868 \u7684 code \u4e0d\u7b26\u5408\u89c4\u8303", JQExceptionHelper.printStackTraceToString(NrDesingerExcelErrorEnum.NRDESINGER_EXCEPTION_020, e), NrDesignLogHelper.LOGLEVEL_ERROR);
            throw e;
        }
        DesignFormDefine formDefine = this.nrDesignTimeController.createFormDefine();
        importContext.setFormKey(formDefine.getKey());
        importContext.setSchemeKey(schemeKey);
        this.initFormDefinePropertyNotInclude(formDefine, formTilte, fomeCode, schemeKey, importContext);
        Grid2Data currGrid2Data = null;
        importContext.setDesignFormDefine(formDefine);
        try {
            currGrid2Data = this.formObjStyleImportHelper.excelPrecessFormStyle(workbook, excelType, sheet, importContext);
        }
        catch (Exception e) {
            log.error("excel\u5bfc\u5165\u8868\u6837\u5931\u8d25", e);
            NrDesignLogHelper.log("Excel\u53c2\u6570\u5bfc\u5165\u6784\u9020\u8868\u6837", JQExceptionHelper.printStackTraceToString(NrDesingerExcelErrorEnum.NRDESINGER_EXCEPTION_005, e), NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerExcelErrorEnum.NRDESINGER_EXCEPTION_100, (Throwable)e);
        }
        this.formObjStyleImportHelper.createFloatRegionByOnlyOneRow(currGrid2Data, importContext);
        try {
            this.formHelper.initMergeCell(currGrid2Data);
        }
        catch (Exception e) {
            NrDesignLogHelper.log("Excel\u53c2\u6570\u5bfc\u5165\u6784\u9020\u8868\u6837\u5408\u5e76\u5355\u5143\u683c", JQExceptionHelper.printStackTraceToString(NrDesingerExcelErrorEnum.NRDESINGER_EXCEPTION_006, e), NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerExcelErrorEnum.NRDESINGER_EXCEPTION_100);
        }
        formDefine.setBinaryData(Grid2Data.gridToBytes((Grid2Data)currGrid2Data));
        DesignDataRegionDefine defaultRegion = this.regionObjImportHelper.createDefaultRegion(1, 1, currGrid2Data.getColumnCount() - 1, currGrid2Data.getRowCount() - 1, importContext);
        try {
            this.nrDesignTimeController.insertFormDefine(formDefine);
            this.nrDesignTimeController.addFormToGroup(formDefine.getKey(), groupKey);
            this.nrDesignTimeController.insertDataRegionDefine(defaultRegion);
        }
        catch (JQException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            NrDesignLogHelper.log("Excel\u53c2\u6570\u5bfc\u5165\u62a5\u8868\u53c2\u6570\u5165\u5e93", JQExceptionHelper.printStackTraceToString(NrDesingerExcelErrorEnum.NRDESINGER_EXCEPTION_004, e), NrDesignLogHelper.LOGLEVEL_ERROR);
            throw new JQException((ErrorEnum)NrDesingerExcelErrorEnum.NRDESINGER_EXCEPTION_100);
        }
        return formDefine.getKey();
    }

    private DesignFormDefine initFormDefinePropertyNotInclude(DesignFormDefine designFormDefine, String formName, String fomeCode, String schemeKey, ExcelImportContext importContext) {
        designFormDefine.setTitle(formName);
        designFormDefine.setOrder(OrderGenerator.newOrder());
        if (StringUtils.isNotEmpty((String)fomeCode)) {
            designFormDefine.setFormCode(fomeCode);
        } else {
            designFormDefine.setFormCode(OrderGenerator.newOrder());
        }
        designFormDefine.setIsGather(true);
        designFormDefine.setFormViewType(FormViewType.FORM_VIEW_GRID);
        designFormDefine.setMasterEntitiesKey(FormExtendPropsDefaultValue.MASTER_ENTITIES_KEY_EXTEND_VALUE);
        designFormDefine.setMeasureUnit(FormExtendPropsDefaultValue.MEASURE_UNIT_EXTEND_VALUE);
        designFormDefine.setFormType(FormType.FORM_TYPE_FIX);
        DesignFormSchemeDefine designFormSchemeDefine = this.nrDesignTimeController.queryFormSchemeDefine(schemeKey);
        String schemeMasterKey = designFormSchemeDefine.getMasterEntitiesKey();
        if (StringUtils.isEmpty((String)schemeMasterKey)) {
            importContext.setFormMasterEntitiesKey(this.nrDesignTimeController.queryTaskDefine(designFormSchemeDefine.getTaskKey()).getMasterEntitiesKey());
        } else {
            importContext.setFormMasterEntitiesKey(designFormSchemeDefine.getMasterEntitiesKey());
        }
        designFormDefine.setFormScheme(schemeKey);
        return designFormDefine;
    }

    private String getFormTitle(String sheetName, String formSchemeKey) {
        int index = 1;
        String newFormTitle = sheetName;
        List designFormDefineList = this.nrDesignTimeController.queryAllFormDefinesByFormScheme(formSchemeKey);
        if (!this.isExistFormByTitle(sheetName, designFormDefineList)) {
            return newFormTitle;
        }
        StringBuffer newTitle = new StringBuffer();
        newFormTitle = newTitle.append(sheetName).append("_").append(index).toString();
        while (this.isExistFormByTitle(newFormTitle, designFormDefineList)) {
            newTitle.delete(0, newTitle.length());
            newFormTitle = newTitle.append(sheetName).append("_").append(++index).toString();
        }
        return newFormTitle;
    }

    private boolean isExistFormByTitle(String currFormTitle, List<DesignFormDefine> designFormDefineList) {
        if (designFormDefineList != null) {
            for (DesignFormDefine designFormDefine : designFormDefineList) {
                if (!designFormDefine.getTitle().equals(currFormTitle)) continue;
                return true;
            }
        }
        return false;
    }

    private String getFormCode(String sheetCode, String formSchemeKey) {
        int index = 1;
        String newFormCode = sheetCode;
        List designFormDefineList = this.nrDesignTimeController.queryAllFormDefinesByFormScheme(formSchemeKey);
        if (!this.isExistFormByCode(sheetCode, designFormDefineList)) {
            return newFormCode;
        }
        StringBuffer newCode = new StringBuffer();
        newFormCode = newCode.append(sheetCode).append("_").append(index).toString();
        while (this.isExistFormByCode(newFormCode, designFormDefineList)) {
            newCode.delete(0, newCode.length());
            newFormCode = newCode.append(sheetCode).append("_").append(++index).toString();
        }
        return newFormCode.length() <= 20 ? newFormCode : newFormCode.substring(0, 20);
    }

    private boolean isExistFormByCode(String currFormCode, List<DesignFormDefine> designFormDefineList) {
        if (designFormDefineList != null) {
            for (DesignFormDefine designFormDefine : designFormDefineList) {
                if (!designFormDefine.getFormCode().equals(currFormCode)) continue;
                return true;
            }
        }
        return false;
    }
}

