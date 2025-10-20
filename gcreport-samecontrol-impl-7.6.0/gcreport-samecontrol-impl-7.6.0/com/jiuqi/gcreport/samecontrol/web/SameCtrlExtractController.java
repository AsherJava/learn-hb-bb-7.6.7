/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.samecontrol.api.SameCtrlExtractClient
 *  com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlEnvContextResultVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO
 *  com.jiuqi.gcreport.samecontrol.vo.samectrloffset.SameCtrlOffSetItemVO
 *  com.jiuqi.nvwa.sf.anno.Licence
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.samecontrol.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.samecontrol.api.SameCtrlExtractClient;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlSrcTypeEnum;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlChgEnvContextImpl;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractDataService;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlExtractService;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlExtractBdeReport;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlExtractManageServiceImpl;
import com.jiuqi.gcreport.samecontrol.util.SameCtrlManageUtil;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlEnvContextResultVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtractDataVO;
import com.jiuqi.gcreport.samecontrol.vo.samectrloffset.SameCtrlOffSetItemVO;
import com.jiuqi.nvwa.sf.anno.Licence;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
@Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.samecontrol")
public class SameCtrlExtractController
implements SameCtrlExtractClient {
    @Autowired
    private SameCtrlExtractService sameCtrlExtractService;
    @Autowired
    private SameCtrlExtractBdeReport sameCtrlExtractBdeReport;
    @Autowired
    private SameCtrlExtractDataService sameCtrlExtractDataService;
    @Autowired
    private SameCtrlExtractManageServiceImpl sameCtrlExtractManageService;

    public BusinessResponseEntity<Pagination<SameCtrlOffSetItemVO>> querySameCtrlExtractDatas(SameCtrlOffsetCond sameCtrlOffsetCond) {
        return BusinessResponseEntity.ok(this.sameCtrlExtractService.listOffsetSameCtrlManage(sameCtrlOffsetCond));
    }

    public BusinessResponseEntity<SameCtrlEnvContextResultVO> extractSameCtrlData(SameCtrlExtractDataVO sameCtrlExtractDataVO) {
        this.sameCtrlExtractService.extractSameCtrlData(sameCtrlExtractDataVO);
        SameCtrlEnvContextResultVO sameCtrlEnvContextResult = new SameCtrlEnvContextResultVO();
        return BusinessResponseEntity.ok((Object)sameCtrlEnvContextResult);
    }

    public BusinessResponseEntity<String> disEnable(List<String> mRecids) {
        this.sameCtrlExtractService.disEnable(mRecids);
        return BusinessResponseEntity.ok((Object)"\u7981\u7528\u6210\u529f");
    }

    public BusinessResponseEntity<String> enable(List<String> mRecids) {
        this.sameCtrlExtractService.enable(mRecids);
        return BusinessResponseEntity.ok((Object)"\u542f\u7528\u6210\u529f");
    }

    public BusinessResponseEntity<String> writeBack(SameCtrlExtractDataVO sameCtrlExtractDataVO) {
        GcOrgTypeUtils.setContextEntityId((String)sameCtrlExtractDataVO.getOrgType());
        SameCtrlChgEnvContextImpl sameCtrlChgEnvContext = new SameCtrlChgEnvContextImpl(sameCtrlExtractDataVO.getSn());
        sameCtrlChgEnvContext.setSuccessFlag(true);
        this.sameCtrlExtractDataService.initOffsetSameCtrlChgEnvContextImpl(sameCtrlExtractDataVO, sameCtrlChgEnvContext);
        sameCtrlChgEnvContext.getSameCtrlOffsetCond().setSelectAdjustCode(sameCtrlExtractDataVO.getSelectAdjustCode());
        this.sameCtrlExtractManageService.initSameCtrlExtractManageCond(sameCtrlExtractDataVO, sameCtrlChgEnvContext, SameCtrlSrcTypeEnum.ACQUIRER_BEGIN_EXTRACT);
        String virtualCode = sameCtrlChgEnvContext.getSameCtrlExtractManageCond().getSameCtrlChgOrgEO().getVirtualCode();
        SameCtrlManageUtil.checkSameCtrlChgOrgAuthority(sameCtrlExtractDataVO.getSchemeId(), sameCtrlExtractDataVO.getPeriodStr(), sameCtrlExtractDataVO.getOrgType(), virtualCode);
        this.sameCtrlExtractBdeReport.executeBdeExtract(sameCtrlChgEnvContext);
        return BusinessResponseEntity.ok((Object)"\u56de\u5199\u6210\u529f");
    }

    public BusinessResponseEntity<String> delOffset(List<String> mRecids) {
        this.sameCtrlExtractService.delOffset(mRecids);
        return BusinessResponseEntity.ok((Object)"\u53d6\u6d88\u62b5\u9500\u6210\u529f");
    }
}

