/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.dafafill.service.impl;

import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.dafafill.model.DataFillContext;
import com.jiuqi.nr.dafafill.model.DataFillModel;
import com.jiuqi.nr.dafafill.service.IDataFillDataService;
import com.jiuqi.nr.dafafill.service.IDataFillModelHelp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DataFillModelHelpImpl
implements IDataFillModelHelp {
    private static Logger logger = LoggerFactory.getLogger(DataFillModelHelpImpl.class);
    @Autowired
    private IDataFillDataService dataFillDataService;

    @Override
    public DataFillModel completion(DataFillContext context) {
        if (null == context.model) {
            DataFillModel model = null;
            if (StringUtils.hasLength(context.getDefinitionId())) {
                try {
                    model = this.dataFillDataService.getModelByDefinition(context.getDefinitionId(), "ZH_CN");
                }
                catch (JQException e) {
                    logger.error("\u6839\u636e\u6a21\u578b\u5b9a\u4e49id:" + context.getDefinitionId() + ";zh;\u83b7\u53d6\u6a21\u578b\u5931\u8d25\uff01", e);
                }
            }
            context.model = model;
        }
        return context.model;
    }
}

