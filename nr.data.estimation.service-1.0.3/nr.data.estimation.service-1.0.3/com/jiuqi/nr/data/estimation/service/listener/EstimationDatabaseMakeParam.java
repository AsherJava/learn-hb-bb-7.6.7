/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.estimation.sub.database.intf.ICopiedTableGenerator
 *  com.jiuqi.nr.data.estimation.sub.database.intf.IMakeSubDatabaseParam
 *  com.jiuqi.nr.data.estimation.sub.database.intf.impl.CopiedTableGenerator
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.data.estimation.service.listener;

import com.jiuqi.nr.data.estimation.sub.database.intf.ICopiedTableGenerator;
import com.jiuqi.nr.data.estimation.sub.database.intf.IMakeSubDatabaseParam;
import com.jiuqi.nr.data.estimation.sub.database.intf.impl.CopiedTableGenerator;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class EstimationDatabaseMakeParam
implements IMakeSubDatabaseParam {
    @Resource
    private DesignDataModelService designDataModelService;

    public String getSubDatabaseCode() {
        return "_DE_";
    }

    public String getSubDatabaseTitle(String dataSchemeId) {
        return "\u6d4b\u7b97\u5206\u5e93";
    }

    public ICopiedTableGenerator getCopiedTableGenerator(String dataSchemeId) {
        return new CopiedTableGenerator(this.getSubDatabaseCode());
    }

    public List<DesignColumnModelDefine> getOtherPrimaryColumns(String dataSchemeId) {
        ArrayList<DesignColumnModelDefine> columnModelDefines = new ArrayList<DesignColumnModelDefine>();
        DesignColumnModelDefine estimationSchemeCol = this.designDataModelService.createColumnModelDefine();
        estimationSchemeCol.setCode("ESTIMATION_SCHEME");
        estimationSchemeCol.setName("ESTIMATION_SCHEME");
        estimationSchemeCol.setTitle("\u6d4b\u7b97\u65b9\u6848\u6807\u8bc6");
        estimationSchemeCol.setColumnType(ColumnModelType.STRING);
        estimationSchemeCol.setPrecision(50);
        columnModelDefines.add(estimationSchemeCol);
        return columnModelDefines;
    }
}

