/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.designer.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.designer.web.facade.FormulaDataVO;
import com.jiuqi.nr.designer.web.facade.FormulaObj;
import com.jiuqi.nr.designer.web.rest.param.FormulaSearchPM;
import com.jiuqi.nr.designer.web.rest.vo.LightFieldVo;
import java.util.List;

public interface IBussinessModelService {
    public String getFieldByTableID(String var1);

    public List<FormulaObj> getFormulaData(String var1, String var2) throws JQException;

    public List<LightFieldVo> getNoBizFields(String var1) throws JQException;

    public List<FormulaObj> getFormulaDataNew(String var1, String var2) throws JQException;

    public FormulaDataVO getFormulaData(FormulaSearchPM var1) throws JQException;
}

