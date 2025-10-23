/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 */
package com.jiuqi.nr.multcheck2.dynamic.impl;

import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.multcheck2.dynamic.IMCExecuteForUploadService;
import com.jiuqi.nr.multcheck2.dynamic.dto.ExecuteForUploadParamDTO;
import com.jiuqi.nr.multcheck2.service.IMCExecuteUploadMultiService;
import com.jiuqi.nr.multcheck2.web.result.MCUploadResult;
import com.jiuqi.nr.multcheck2.web.vo.MCRunVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MCExecuteForUploadServiceImpl
implements IMCExecuteForUploadService {
    private static final Logger logger = LoggerFactory.getLogger(MCExecuteForUploadServiceImpl.class);
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    IMCExecuteUploadMultiService multiUpload;

    @Override
    public MCUploadResult uploadMultiExecute(ExecuteForUploadParamDTO param) {
        MCUploadResult result = new MCUploadResult();
        MCRunVO vo = new MCRunVO();
        try {
            SchemePeriodLinkDefine scheme = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(param.getPeriod(), param.getTask());
            vo.setFormScheme(scheme.getSchemeKey());
        }
        catch (Exception e) {
            logger.error("\u6267\u884c\u7efc\u5408\u5ba1\u6838\u65f6\u83b7\u53d6\u62a5\u8868\u65b9\u6848\u5f02\u5e38\uff1a\u4efb\u52a1=" + vo.getTask() + ",\u65f6\u671f=" + vo.getPeriod() + ",\u5f02\u5e38:" + e.getMessage(), e);
            result.setErrorMsg(e.getMessage());
            return result;
        }
        vo.setTask(param.getTask());
        vo.setPeriod(param.getPeriod());
        vo.setOrg(param.getOrg());
        vo.setDimSetMap(param.getDimSetMap());
        vo.setOrgCodes(param.getOrgCodes());
        vo.setSource(param.getSource());
        return this.multiUpload.uploadMultiExecute(null, vo);
    }
}

