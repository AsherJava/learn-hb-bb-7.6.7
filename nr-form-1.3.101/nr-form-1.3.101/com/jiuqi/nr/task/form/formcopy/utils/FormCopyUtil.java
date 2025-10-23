/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 */
package com.jiuqi.nr.task.form.formcopy.utils;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.task.form.dto.FormCopySchemeVO;
import com.jiuqi.nr.task.form.formcopy.bean.IFormCopyAttSchemeInfo;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormCopyUtil {
    public static final String FORMULA = "FORMULA";
    public static final String PRINT = "PRINT";
    public static final String ROOT_LOCATION = System.getProperty("java.io.tmpdir");
    public static final String EXPORTDIR = ROOT_LOCATION + File.separator + ".nr" + File.separator + "AppData" + File.separator + "export";
    private static IDesignTimePrintController designTimePrintController = FormCopyUtil.getIDesignTimePrintController();
    private static IDesignTimeFormulaController designTimeFormulaController = FormCopyUtil.getIDesignTimeFormulaController();

    private static IDesignTimePrintController getIDesignTimePrintController() {
        return (IDesignTimePrintController)SpringBeanUtils.getBean(IDesignTimePrintController.class);
    }

    private static IDesignTimeFormulaController getIDesignTimeFormulaController() {
        return (IDesignTimeFormulaController)SpringBeanUtils.getBean(IDesignTimeFormulaController.class);
    }

    public static Map<String, Map<String, String>> decomposeScheme(List<IFormCopyAttSchemeInfo> formCopySchemeInfo) {
        HashMap<String, Map<String, String>> attSchemeMap = new HashMap<String, Map<String, String>>();
        HashMap<String, String> formulaMap = new HashMap<String, String>();
        HashMap<String, String> printMap = new HashMap<String, String>();
        if (formCopySchemeInfo != null && !formCopySchemeInfo.isEmpty()) {
            for (IFormCopyAttSchemeInfo linkInfo : formCopySchemeInfo) {
                switch (linkInfo.getSchemeType()) {
                    case FORMULA_SCHEME: {
                        formulaMap.put(linkInfo.getSrcSchemeKey(), linkInfo.getSchemeKey());
                        break;
                    }
                    case PRINT_SCHEME: {
                        printMap.put(linkInfo.getSrcSchemeKey(), linkInfo.getSchemeKey());
                        break;
                    }
                }
            }
        }
        attSchemeMap.put(FORMULA, formulaMap);
        attSchemeMap.put(PRINT, printMap);
        return attSchemeMap;
    }

    public static Map<String, List<FormCopySchemeVO>> querySchemesInFormScheme(String formSchemeKey) {
        HashMap<String, List<FormCopySchemeVO>> schemesInFormSchemeMap = new HashMap<String, List<FormCopySchemeVO>>();
        List<FormCopySchemeVO> formulaSchemes = FormCopyUtil.getFormulaSchemes(formSchemeKey, designTimeFormulaController);
        schemesInFormSchemeMap.put(FORMULA, formulaSchemes);
        List allPrintSchemeByFormScheme = designTimePrintController.listPrintTemplateSchemeByFormScheme(formSchemeKey);
        ArrayList<FormCopySchemeVO> printSchemes = new ArrayList<FormCopySchemeVO>();
        allPrintSchemeByFormScheme.sort((o1, o2) -> o1.getOrder().compareTo(o2.getOrder()));
        for (DesignPrintTemplateSchemeDefine printScheme : allPrintSchemeByFormScheme) {
            printSchemes.add(new FormCopySchemeVO(printScheme.getKey(), printScheme.getTitle()));
        }
        schemesInFormSchemeMap.put(PRINT, printSchemes);
        return schemesInFormSchemeMap;
    }

    public static List<FormCopySchemeVO> getFormulaSchemes(String formSchemeKey, IDesignTimeFormulaController designTimeFormulaController) {
        List allFormulaSchemeDefinesByFormScheme = designTimeFormulaController.listFormulaSchemeByFormScheme(formSchemeKey);
        allFormulaSchemeDefinesByFormScheme.sort((o1, o2) -> o1.getOrder().compareTo(o2.getOrder()));
        ArrayList<FormCopySchemeVO> formulaSchemes = new ArrayList<FormCopySchemeVO>();
        for (DesignFormulaSchemeDefine designFormulaSchemeDefine : allFormulaSchemeDefinesByFormScheme) {
            FormulaSchemeType formulaSchemeType = designFormulaSchemeDefine.getFormulaSchemeType();
            if (formulaSchemeType != FormulaSchemeType.FORMULA_SCHEME_TYPE_REPORT && formulaSchemeType != FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL) continue;
            formulaSchemes.add(new FormCopySchemeVO(designFormulaSchemeDefine.getKey(), designFormulaSchemeDefine.getTitle()));
        }
        return formulaSchemes;
    }

    public static void setProgressAndMessage(AsyncTaskMonitor monitor, double progress, String message) {
        if (monitor != null) {
            monitor.progressAndMessage(progress, message);
        }
    }

    public static void setFinish(AsyncTaskMonitor monitor, String result, Object detail) {
        if (monitor != null) {
            monitor.finish(result, detail);
        }
    }

    public static void setError(AsyncTaskMonitor monitor, String result, Throwable t) {
        if (monitor != null) {
            monitor.error(result, t);
        }
    }

    public static String getTempLocation() {
        StringBuffer filePath = new StringBuffer();
        NpContext context = NpContextHolder.getContext();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatDate = dateFormat.format(date);
        String dirUU = UUIDUtils.getKey();
        filePath.append(EXPORTDIR).append(File.separator).append(context.getUser().getName()).append(File.separator).append(formatDate).append(File.separator).append(dirUU);
        return filePath.toString();
    }
}

