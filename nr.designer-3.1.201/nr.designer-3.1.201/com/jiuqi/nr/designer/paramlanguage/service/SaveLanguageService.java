/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.exception.DBParaException
 */
package com.jiuqi.nr.designer.paramlanguage.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.nr.designer.paramlanguage.vo.BigDataSaveObject;
import com.jiuqi.nr.designer.paramlanguage.vo.ParamLanguageObject;

public interface SaveLanguageService {
    public void batchSaveParamLanguage(ParamLanguageObject[] var1) throws DBParaException;

    public void saveBigDataParamLanguage(BigDataSaveObject var1) throws JQException;
}

