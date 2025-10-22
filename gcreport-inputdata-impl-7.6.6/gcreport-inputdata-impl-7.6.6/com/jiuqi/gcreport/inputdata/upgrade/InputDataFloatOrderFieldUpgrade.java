/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.consolidatedsystem.entity.InputDataSchemeEO
 *  com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 */
package com.jiuqi.gcreport.inputdata.upgrade;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.consolidatedsystem.entity.InputDataSchemeEO;
import com.jiuqi.gcreport.definition.impl.basic.dao.EntNativeSqlDefaultDao;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class InputDataFloatOrderFieldUpgrade
implements CustomClassExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputDataFloatOrderFieldUpgrade.class);
    private IDesignDataSchemeService iDesignDataSchemeService = (IDesignDataSchemeService)SpringContextUtils.getBean(IDesignDataSchemeService.class);
    private IDataSchemeDeployService iDataSchemeDeployService = (IDataSchemeDeployService)SpringContextUtils.getBean(IDataSchemeDeployService.class);

    public void execute(DataSource dataSource) throws Exception {
        List<DesignDataField> updateDataFields = this.getUpdateDataFields();
        this.updateFloatOrderFieldDecimal(updateDataFields);
    }

    private List<InputDataSchemeEO> listInputDataScheme() {
        EntNativeSqlDefaultDao inputDataSchemeDao = EntNativeSqlDefaultDao.newInstance((String)"GC_INPUTDATASCHEME", InputDataSchemeEO.class);
        List inputDataSchemes = inputDataSchemeDao.selectEntity("select * from GC_INPUTDATASCHEME", new Object[0]);
        if (CollectionUtils.isEmpty((Collection)inputDataSchemes)) {
            return CollectionUtils.newArrayList();
        }
        return inputDataSchemes;
    }

    private List<DesignDataField> getUpdateDataFields() {
        List<InputDataSchemeEO> inputDataSchemes = this.listInputDataScheme();
        ArrayList<DesignDataField> updateDataFields = new ArrayList<DesignDataField>();
        for (InputDataSchemeEO inputDataScheme : inputDataSchemes) {
            Integer decimal;
            DesignDataField designDataField = this.iDesignDataSchemeService.getDataFieldByTableKeyAndCode(inputDataScheme.getTableKey(), "FLOATORDER");
            if (ObjectUtils.isEmpty(designDataField) || !ObjectUtils.isEmpty(decimal = designDataField.getDecimal()) && decimal >= 5) continue;
            designDataField.setDecimal(Integer.valueOf(5));
            designDataField.setPrecision(Integer.valueOf(designDataField.getPrecision() + 1));
            designDataField.setUpdateTime(Instant.now());
            updateDataFields.add(designDataField);
        }
        return updateDataFields;
    }

    private void updateFloatOrderFieldDecimal(List<DesignDataField> updateDataFields) {
        for (DesignDataField designDataField : updateDataFields) {
            try {
                this.iDesignDataSchemeService.updateDataField(designDataField);
                this.iDataSchemeDeployService.deployDataTable(designDataField.getDataTableKey(), true);
            }
            catch (Exception e) {
                LOGGER.error("\u4fee\u6539\u5185\u90e8\u8868FLOATORDER\u5b57\u6bb5\u5c0f\u6570\u4f4d\u4e3a5\u5f02\u5e38", e);
            }
        }
    }
}

