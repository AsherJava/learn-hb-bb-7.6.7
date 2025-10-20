/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 *  com.jiuqi.va.formula.domain.FormulasVO
 */
package com.jiuqi.va.biz.service;

import com.jiuqi.va.biz.domain.BillAppParamDTO;
import com.jiuqi.va.biz.domain.SecureDataDTO;
import com.jiuqi.va.biz.domain.TableAndFieldSearchVO;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import com.jiuqi.va.formula.domain.FormulasVO;
import java.util.List;
import java.util.Map;

public interface IBizService {
    public List<TableAndFieldSearchVO> handleTableAndFieldSearh(ModelDefine var1, String var2);

    public DataModelDO getRefTable(String var1);

    public List<Map<String, Object>> getPlugins(String var1, String var2) throws InstantiationException, IllegalAccessException;

    public Map<String, Object> getBillAppParam(BillAppParamDTO var1);

    public List<TableAndFieldSearchVO> handleTableAndFieldSearh(List<Map<String, Object>> var1, String var2);

    public List<FormulasVO> handleFormulasRetrieval(String var1, List<String> var2);

    public String getSensitiveData(SecureDataDTO var1);
}

