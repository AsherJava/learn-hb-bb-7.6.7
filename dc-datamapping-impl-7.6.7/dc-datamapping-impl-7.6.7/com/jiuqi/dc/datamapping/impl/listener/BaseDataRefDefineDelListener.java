/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO
 *  com.jiuqi.dc.mappingscheme.impl.event.BaseDataRefDefineDelEvent
 */
package com.jiuqi.dc.datamapping.impl.listener;

import com.jiuqi.dc.datamapping.impl.service.DataRefConfigureService;
import com.jiuqi.dc.mappingscheme.client.dto.DataMappingDefineDTO;
import com.jiuqi.dc.mappingscheme.impl.event.BaseDataRefDefineDelEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BaseDataRefDefineDelListener
implements ApplicationListener<BaseDataRefDefineDelEvent> {
    @Autowired
    private DataRefConfigureService dataRefServcie;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(BaseDataRefDefineDelEvent event) {
        DataMappingDefineDTO define = event.getDefine();
        try {
            this.dataRefServcie.deleteByBaseDataRefDefine(define);
        }
        catch (Exception e) {
            this.logger.error(String.format("\u5220\u9664\u6570\u636e\u6620\u5c04\u65b9\u6848\u3010%1$s\u3011\u4e0b\u3010%2$s\u3011\u57fa\u7840\u6570\u636e\u6620\u5c04\u5b9a\u4e49\u5173\u8054\u7684\u6620\u5c04\u6570\u636e\u5f02\u5e38", define.getDataSchemeCode(), define.getCode()), e);
        }
    }
}

