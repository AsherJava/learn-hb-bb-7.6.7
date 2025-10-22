/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.designer.service;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.definitions.Formula;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IFormFormulaService {
    public void paraExcelFormulas(MultipartFile var1, String[] var2, Map<String, List> var3, String var4, boolean var5) throws JQException;

    public void exportRepeatCodeExcel(Map<String, List> var1, HttpServletResponse var2, boolean var3) throws IOException;

    public void exportAllFormulas(String var1, ArrayList<String> var2, HttpServletResponse var3, boolean var4) throws JQException;

    public void addImportFormula(String[] var1, String var2, Map<String, List> var3) throws ParseException, JQException;

    public void exportEfdcWithStyle(List<String> var1, String var2, HttpServletResponse var3) throws JQException, IOException;

    public void allImportFormula(String[] var1, String var2, Map<String, List> var3) throws JQException, ParseException;

    public void exportCycleFormulaExcel(Map<String, List<Formula>> var1, Map<String, String> var2, HttpServletResponse var3) throws JQException;
}

