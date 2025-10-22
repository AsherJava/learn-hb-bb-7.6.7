/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.nr.dataentry.bean.RefreshStatusParam;
import com.jiuqi.nr.dataentry.service.IRefreshStatus;
import com.jiuqi.nr.dataentry.service.IRefreshStatusService;
import com.jiuqi.nr.dataentry.util.Consts;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RefreshStatusServiceImpl
implements IRefreshStatusService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final List<IRefreshStatus> refreshStatuses;

    public RefreshStatusServiceImpl(List<IRefreshStatus> refreshStatuses) {
        this.refreshStatuses = refreshStatuses;
    }

    @Override
    public Map<String, Object> getStatus(RefreshStatusParam param) {
        HashMap<String, Object> status = new HashMap<String, Object>();
        try {
            for (IRefreshStatus refreshStatus : this.refreshStatuses) {
                if (!refreshStatus.getEnable(param.getContext().getTaskKey(), param.getContext().getFormSchemeKey()) || param.isForm() && refreshStatus.getType() == Consts.RefreshStatusType.UNIT) continue;
                status.put(refreshStatus.getName(), refreshStatus.getStatus(param.getContext()));
            }
        }
        catch (Exception e) {
            this.logger.error("\u5355\u4f4d\u8868\u5355\u5237\u65b0\u72b6\u6001\u6269\u5c55\u4fe1\u606f\u67e5\u8be2\u5931\u8d25", e);
        }
        return status;
    }
}

