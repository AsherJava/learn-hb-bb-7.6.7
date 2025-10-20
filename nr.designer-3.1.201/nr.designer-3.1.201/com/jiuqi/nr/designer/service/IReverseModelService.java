/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 */
package com.jiuqi.nr.designer.service;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.designer.web.rest.param.ReverseBatchCheckPM;
import com.jiuqi.nr.designer.web.rest.param.ReverseCreateFieldPM;
import com.jiuqi.nr.designer.web.rest.param.ReverseCreateTablePM;
import com.jiuqi.nr.designer.web.rest.vo.ReverseDataFieldVO;
import com.jiuqi.nr.designer.web.rest.vo.ReverseDataTableVO;
import com.jiuqi.nr.designer.web.rest.vo.ReverseFormVO;
import java.util.List;
import java.util.Map;

public interface IReverseModelService {
    public ReverseDataTableVO createZBTables(ReverseCreateTablePM var1, ReverseCreateTablePM.Region var2, List<DesignDataTable> var3);

    public ReverseDataTableVO createMXTables(ReverseCreateTablePM var1, ReverseCreateTablePM.Region var2, List<DesignDataTable> var3);

    public List<ReverseDataFieldVO> createField(ReverseCreateFieldPM var1, ReverseCreateFieldPM.Region var2, List<DesignDataField> var3);

    public List<ReverseDataFieldVO> createZDField(ReverseCreateFieldPM var1, ReverseCreateFieldPM.Region var2);

    public String queryTableGroup(ReverseFormVO var1);

    public String queryTableGroup(String var1, String var2, String var3, String var4);

    public Map<String, List<ReverseDataFieldVO>> createFieldsAndCodes(ReverseCreateFieldPM var1);

    public Map<String, Boolean> reverseFieldChecks(ReverseBatchCheckPM var1);

    public void reverseFieldsSave(ReverseFormVO var1) throws JQException;
}

