/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.enums.SourceDataTypeEnum
 */
package com.jiuqi.dc.datamapping.impl.gather.impl;

import com.jiuqi.dc.base.common.enums.SourceDataTypeEnum;
import com.jiuqi.dc.datamapping.impl.gather.IDataRefConfigureServiceGather;
import com.jiuqi.dc.datamapping.impl.service.DataRefListConfigureService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DataRefConfigureServiceGather
implements InitializingBean,
IDataRefConfigureServiceGather {
    @Autowired
    @Qualifier(value="BDEDataRefConfigureService")
    private DataRefListConfigureService bdeDataRefConfigureService;
    @Autowired
    @Qualifier(value="DataRefConfigureService")
    private DataRefListConfigureService dataRefConfigureService;
    @Autowired
    @Qualifier(value="HasRefDataRefConfigureService")
    private DataRefListConfigureService hasRefDataRefConfigureService;

    private void init() {
    }

    @Override
    public DataRefListConfigureService getDataRefConfigureServiceBySourceDataType(String sourceDataType) {
        if (sourceDataType == null) {
            return this.hasRefDataRefConfigureService;
        }
        if (SourceDataTypeEnum.DIRECT_TYPE.getCode().equals(sourceDataType)) {
            return this.bdeDataRefConfigureService;
        }
        return this.dataRefConfigureService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}

