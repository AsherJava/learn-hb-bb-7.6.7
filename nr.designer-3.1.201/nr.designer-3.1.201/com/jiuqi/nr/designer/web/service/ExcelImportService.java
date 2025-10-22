/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 */
package com.jiuqi.nr.designer.web.service;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.common.NrDesignLogHelper;
import com.jiuqi.nr.designer.excel.importexcel.FormObjImportHelper;
import com.jiuqi.nr.designer.excel.importexcel.common.ExcelType;
import com.jiuqi.nr.designer.util.InitParamObjPropertyUtil;
import com.jiuqi.nr.designer.web.facade.FormObj;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExcelImportService {
    private static final Logger log = LoggerFactory.getLogger(ExcelImportService.class);
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private FormObjImportHelper formObjImportHelper;
    @Autowired
    private InitParamObjPropertyUtil initParamObjPropertyUtil;
    private static final String ENUM_NAME = "\u679a\u4e3e\u5b57\u5178";

    public Map<String, Object> excelPrePrecessOnlyStyle(Workbook workbook, ExcelType excelType, String groupKey, String schemeKey, String formulaSchemeKey, String taskKey) {
        HashMap<String, Object> reMess = new HashMap<String, Object>();
        HashMap<String, Exception> errorMess = new HashMap<String, Exception>();
        ArrayList<FormObj> formObjList = new ArrayList<FormObj>();
        int sheetCount = workbook.getNumberOfSheets();
        for (int i = 0; i < sheetCount; ++i) {
            Sheet sheet = workbook.getSheetAt(i);
            String sheetName = sheet.getSheetName();
            if (sheetName.startsWith(ENUM_NAME)) continue;
            try {
                String formKey = this.formObjImportHelper.createFormDefineOnlyStyle(workbook, excelType, sheet, groupKey, schemeKey, formulaSchemeKey, taskKey);
                if (!StringUtils.isNotEmpty((String)formKey) || null == this.nrDesignTimeController.queryFormById(formKey)) continue;
                FormObj formObj = this.initParamObjPropertyUtil.setFormObjProperty(this.nrDesignTimeController.queryFormById(formKey), taskKey, groupKey, false, true);
                formObjList.add(formObj);
                continue;
            }
            catch (Exception e) {
                NrDesignLogHelper.log(sheetName + "\u5bfc\u5165\u5931\u8d25", e.getMessage(), NrDesignLogHelper.LOGLEVEL_ERROR);
                errorMess.put(sheetName, e);
            }
        }
        reMess.put("successFormData", formObjList);
        reMess.put("reErrorMess", errorMess);
        return reMess;
    }

    public Map<String, Object> excelPrePrecess(Workbook workbook, ExcelType excelType, String groupKey, String schemeKey, String formulaSchemeKey, String taskKey) {
        log.error("\u5bfc\u5165\u6307\u6807\u5c1a\u672a\u652f\u6301");
        return Collections.emptyMap();
    }
}

