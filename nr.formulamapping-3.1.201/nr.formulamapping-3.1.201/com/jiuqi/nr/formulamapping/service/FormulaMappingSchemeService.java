/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingSchemeDefine
 *  com.jiuqi.nr.definition.formulamapping.facade.FormSchemeAndTaskKeysObj
 *  com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingSchemeObj
 */
package com.jiuqi.nr.formulamapping.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingSchemeDefine;
import com.jiuqi.nr.definition.formulamapping.facade.FormSchemeAndTaskKeysObj;
import com.jiuqi.nr.definition.formulamapping.facade.FormulaMappingSchemeObj;
import java.util.List;

public interface FormulaMappingSchemeService {
    public FormSchemeAndTaskKeysObj getTaskAndFormSchemeObj(String var1);

    public List<FormulaMappingSchemeObj> queryFormulaMappingSchemeObjs();

    public FormulaMappingSchemeDefine queryFormulaMappingSchemeObjsByKey(String var1);

    public void insertFormulaMappingSchemeDefine(FormulaMappingSchemeDefine var1) throws JQException;

    public void updateFormulaMappingSchemeDefine(FormulaMappingSchemeDefine var1) throws JQException;

    public void deletsFormulaMappingSchemeDefine(String[] var1) throws JQException;
}

