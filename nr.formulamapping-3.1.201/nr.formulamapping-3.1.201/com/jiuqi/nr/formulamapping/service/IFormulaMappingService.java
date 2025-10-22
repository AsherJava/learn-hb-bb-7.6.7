/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingDefine
 *  com.jiuqi.nr.definition.formulamapping.facade.ExportParamsObj
 *  com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingObj
 *  com.jiuqi.nr.definition.formulamapping.facade.MappingParamsObj
 *  com.jiuqi.nr.definition.formulamapping.facade.QueryFormulaObj
 */
package com.jiuqi.nr.formulamapping.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingDefine;
import com.jiuqi.nr.definition.formulamapping.facade.ExportParamsObj;
import com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingObj;
import com.jiuqi.nr.definition.formulamapping.facade.MappingParamsObj;
import com.jiuqi.nr.definition.formulamapping.facade.QueryFormulaObj;
import java.io.InputStream;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;

public interface IFormulaMappingService {
    public List<QueryFormulaObj> queryFormulas(String var1, String var2, String var3) throws JQException;

    public String querySourceFormulaSchemeKey(String var1);

    public List<FormulaMappingDefine> queryFormulaMappings(String var1);

    public List<FormulaMappingDefine> queryValidFormulaMappings(String var1);

    public String querySourceFormulaSchemeKey(String var1, String var2);

    public List<FormulaMappingDefine> queryValidFormulaMappings(String var1, String var2);

    public List<String> querySourceFormulaSchemes(String var1);

    public List<FormulaMappingDefine> queryFormulaMappings(String var1, String var2);

    public List<FormulaMappingDefine> queryFormulaMappings(String var1, String var2, String var3);

    public Long queryFormulaMappingsCount(String var1, String var2, String var3, String var4);

    public List<FormulaMappingObj> queryByCondition(String var1, String var2, String var3, String var4, int var5) throws Exception;

    public List<FormulaMappingObj> queryFormulaMappings(String var1, String var2, String var3, String var4, int var5, int var6) throws JQException;

    public List<FormulaMappingObj> queryFormulaMappingsByGroup(String var1, String var2, String var3) throws JQException;

    public double queryMaxOrder(String var1, String var2) throws JQException;

    public void addFormulaMappings(String var1, FormulaMappingObj[] var2) throws JQException;

    public void deleteFormulaMappings(String[] var1) throws JQException;

    public void deleteFormulaMappings(String var1) throws JQException;

    public void deleteSourceMapping(String var1, FormulaMappingObj[] var2) throws JQException;

    public void updateFormulaMapping(String var1, FormulaMappingObj var2) throws JQException;

    public void updateFormulaMappings(String var1, FormulaMappingObj[] var2) throws JQException;

    public void doMapping(MappingParamsObj var1) throws JQException;

    public void doImport(String var1, String var2, String var3, InputStream var4) throws JQException;

    public Workbook doExport(ExportParamsObj var1) throws JQException;

    public void deleteMappings(String var1, String var2, String var3, FormulaMappingObj[] var4) throws JQException;

    public void deleteSourceMappings(String var1, String var2, String var3, FormulaMappingObj[] var4) throws JQException;
}

