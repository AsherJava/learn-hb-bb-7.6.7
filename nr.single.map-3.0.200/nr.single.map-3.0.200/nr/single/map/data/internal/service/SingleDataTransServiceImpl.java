/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package nr.single.map.data.internal.service;

import nr.single.map.data.TaskDataContext;
import nr.single.map.data.service.SingleDataTransService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class SingleDataTransServiceImpl
implements SingleDataTransService {
    @Override
    public String toUpper(TaskDataContext context, String code) {
        if (context != null && context.isNeedChangeUpper() && StringUtils.isNotEmpty((CharSequence)code)) {
            return code.toUpperCase();
        }
        return code;
    }

    @Override
    public String toUpper(String code) {
        if (StringUtils.isNotEmpty((CharSequence)code)) {
            return code.toUpperCase();
        }
        return code;
    }
}

