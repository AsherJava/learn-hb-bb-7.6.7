/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.log.LogHelper
 *  nr.single.client.bean.UploadJioNoticeParam
 *  nr.single.client.bean.UploadJioNoticeResult
 *  nr.single.client.service.upload.extend.IUploadJioNoticeService
 */
package com.jiuqi.gcreport.org.impl.callback;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.org.impl.base.InspectOrgUtils;
import com.jiuqi.gcreport.org.impl.inspect.OrgGcParentsInspect;
import com.jiuqi.np.log.LogHelper;
import java.util.HashMap;
import nr.single.client.bean.UploadJioNoticeParam;
import nr.single.client.bean.UploadJioNoticeResult;
import nr.single.client.service.upload.extend.IUploadJioNoticeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class GcJIOEventNotice
implements IUploadJioNoticeService {
    private static final Logger logger = LoggerFactory.getLogger(GcJIOEventNotice.class);

    public int getNotitykind() {
        return 2;
    }

    public String getNoticeName() {
        return "\u5408\u5e76\u62a5\u8868jio\u5bfc\u5165\u4e8b\u4ef6\u76d1\u542c";
    }

    public UploadJioNoticeResult beforeImportNotice(UploadJioNoticeParam param) {
        return null;
    }

    public UploadJioNoticeResult importNotice(UploadJioNoticeParam param) {
        return null;
    }

    public UploadJioNoticeResult aftterImportNotice(UploadJioNoticeParam param) {
        if (!InspectOrgUtils.enableMergeUnit()) {
            return new UploadJioNoticeResult();
        }
        String orgDefineCode = param.getOrgDefineCode();
        if (!StringUtils.hasText(orgDefineCode)) {
            return new UploadJioNoticeResult();
        }
        try {
            OrgGcParentsInspect parentsInspect = (OrgGcParentsInspect)SpringContextUtils.getBean(OrgGcParentsInspect.class);
            HashMap<String, Object> fixParam = new HashMap<String, Object>();
            fixParam.put("orgType", orgDefineCode);
            parentsInspect.executeFix(fixParam);
            LogHelper.info((String)"\u5408\u5e76-JIO\u5bfc\u5165\u540e\u76d1\u542c", (String)("\u4fee\u590d-GCPARENTS\u6570\u636e-\u673a\u6784\u7c7b\u578b" + orgDefineCode), (String)"");
        }
        catch (Exception e) {
            LogHelper.error((String)"\u5408\u5e76-JIO\u5bfc\u5165\u540e\u76d1\u542c", (String)("\u4fee\u590d-GCPARENTS\u6570\u636e-\u673a\u6784\u7c7b\u578b" + orgDefineCode), (String)"");
        }
        return new UploadJioNoticeResult();
    }
}

