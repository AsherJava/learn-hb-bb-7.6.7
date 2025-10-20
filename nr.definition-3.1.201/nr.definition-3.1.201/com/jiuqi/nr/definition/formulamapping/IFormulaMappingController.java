/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.definition.formulamapping;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingDefine;
import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingSchemeDefine;
import com.jiuqi.nr.definition.formulamapping.facade.FormSchemeAndTaskKeysObj;
import com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingObj;
import com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingSchemeObj;
import com.jiuqi.nr.definition.formulamapping.facade.QueryFormulaObj;
import java.util.List;

public interface IFormulaMappingController {
    public List<QueryFormulaObj> queryFormulas(String var1, String var2, String var3) throws JQException;

    public String querySourceFormulaSchemeKey(String var1);

    public String querySourceFormulaSchemeKey(String var1, String var2);

    public List<String> querySourceFormulaSchemes(String var1);

    public List<FormulaMappingDefine> queryFormulaMappings(String var1);

    public List<FormulaMappingDefine> queryValidFormulaMappings(String var1);

    public List<FormulaMappingDefine> queryValidFormulaMappings(String var1, String var2);

    public List<FormulaMappingDefine> queryFormulaMappings(String var1, String var2);

    public List<FormulaMappingDefine> queryFormulaMappings(String var1, String var2, String var3);

    public Long queryFormulaMappingsCount(String var1, String var2, String var3, String var4);

    public List<FormulaMappingObj> queryFormulaMappings(String var1, String var2, String var3, String var4, int var5, int var6) throws JQException;

    public List<FormulaMappingObj> queryFormulaMappingsByGroup(String var1, String var2, String var3) throws JQException;

    public double queryMaxOrder(String var1, String var2) throws JQException;

    public FormSchemeAndTaskKeysObj getTaskAndFormSchemeObj(String var1);

    public List<FormulaMappingSchemeObj> queryFormulaMappingSchemeObjs();

    public FormulaMappingSchemeDefine queryFormulaMappingSchemeObjsByKey(String var1);
}

