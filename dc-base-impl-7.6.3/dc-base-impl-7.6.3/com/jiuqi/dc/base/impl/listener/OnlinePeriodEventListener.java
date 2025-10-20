/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.event.SyncCacheEvent
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.client.BaseDataClient
 */
package com.jiuqi.dc.base.impl.listener;

import com.jiuqi.dc.base.common.event.SyncCacheEvent;
import com.jiuqi.dc.base.impl.acctperiod.service.AcctPeriodService;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.client.BaseDataClient;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OnlinePeriodEventListener
implements ApplicationListener<SyncCacheEvent> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AcctPeriodService acctPeriodService;
    @Autowired
    private BaseDataClient baseDataClient;

    @Override
    public void onApplicationEvent(SyncCacheEvent event) {
        try {
            List<Integer> yearList = this.acctPeriodService.listYear();
            for (Integer year : yearList) {
                BaseDataDTO baseDataDTO = new BaseDataDTO();
                baseDataDTO.setTableName("MD_YEAR");
                baseDataDTO.put("code", (Object)year.toString());
                baseDataDTO.put("ordinal", (Object)year.toString());
                R r = this.baseDataClient.exist(baseDataDTO);
                if (((Boolean)r.get((Object)"exist")).booleanValue()) continue;
                baseDataDTO.put("name", (Object)(year.toString() + '\u5e74'));
                R addR = this.baseDataClient.add(baseDataDTO);
                if (addR.getCode() == 0) {
                    this.logger.info("\u57fa\u7840\u6570\u636e\u3010{}\u3011\u521d\u59cb\u5316\u6210\u529f", (Object)"MD_YEAR");
                    continue;
                }
                this.logger.error("\u57fa\u7840\u6570\u636e\u3010{}\u3011\u521d\u59cb\u5316\u5931\u8d25\uff0c\u539f\u56e0\uff1a{}", (Object)"MD_YEAR", (Object)r.getMsg());
            }
        }
        catch (Exception e) {
            this.logger.error("\u57fa\u7840\u6570\u636e\u3010{}\u3011\u521d\u59cb\u5316\u5f02\u5e38\uff1a{}", (Object)"MD_YEAR", (Object)e);
        }
    }
}

