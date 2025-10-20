/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider
 */
package com.jiuqi.bde.plugin.nbrj_n9.util;

import com.jiuqi.bde.bizmodel.execute.assist.IAssistProvider;
import com.jiuqi.bde.plugin.nbrj_n9.BdeNbrjN9PluginType;
import com.jiuqi.bde.plugin.nbrj_n9.util.AssistPojo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NbrjN9AssistProvider
implements IAssistProvider<AssistPojo> {
    @Autowired
    private BdeNbrjN9PluginType pluginType;

    public String getPluginType() {
        return this.pluginType.getSymbol();
    }

    public List<AssistPojo> listAssist(String dataSourceCode) {
        ArrayList<AssistPojo> assistPojoList = new ArrayList<AssistPojo>();
        AssistPojo customer = new AssistPojo();
        customer.setAssistField("CODE06");
        customer.setCode("MD_CUSTOMER");
        customer.setName("\u5ba2\u6237");
        customer.setTablePk("GRPCCODE");
        customer.setTableName("GRPCCODE");
        customer.setAdvancedSql("SELECT GRP.GRPCCODE AS ID, GRP.GRPCOCODE AS CODE,GRP.LANGNAME AS NAME FROM GRPCCODE GRP");
        assistPojoList.add(customer);
        AssistPojo brand = new AssistPojo();
        brand.setAssistField("CODE09");
        brand.setCode("MD_SUPPLIER");
        brand.setName("\u4f9b\u5e94\u5546");
        brand.setTablePk("BRANDEFICODE");
        brand.setTableName("BRANDEF");
        brand.setAdvancedSql("SELECT BRANDEFICODE AS ID,BRANDEFCODE AS CODE,LANGNAME1 AS NAME FROM BRANDEF BRAND");
        assistPojoList.add(brand);
        return assistPojoList;
    }
}

