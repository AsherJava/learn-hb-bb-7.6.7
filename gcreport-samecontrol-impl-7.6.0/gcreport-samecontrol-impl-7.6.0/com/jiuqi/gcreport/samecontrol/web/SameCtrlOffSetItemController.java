/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.samecontrol.api.SameCtrlOffSetItemClient
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 *  com.jiuqi.gcreport.samecontrol.vo.samectrloffset.SameCtrlOffSetItemVO
 *  com.jiuqi.nvwa.sf.anno.Licence
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.samecontrol.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.samecontrol.api.SameCtrlOffSetItemClient;
import com.jiuqi.gcreport.samecontrol.env.impl.SameCtrlChgEnvContextImpl;
import com.jiuqi.gcreport.samecontrol.service.SameCtrlOffSetItemService;
import com.jiuqi.gcreport.samecontrol.service.impl.SameCtrlOffsetFactory;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import com.jiuqi.gcreport.samecontrol.vo.samectrloffset.SameCtrlOffSetItemVO;
import com.jiuqi.nvwa.sf.anno.Licence;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
@Licence(module="com.jiuqi.gcreport", point="com.jiuqi.gcreport.samecontrol")
public class SameCtrlOffSetItemController
implements SameCtrlOffSetItemClient {
    @Autowired
    @Qualifier(value="sameCtrlOffSetItemServiceImpl")
    private SameCtrlOffSetItemService offSetItemService;

    public BusinessResponseEntity<List<GcOrgCacheVO>> listUnitPaths(SameCtrlOffsetCond cond) {
        return BusinessResponseEntity.ok(this.offSetItemService.listUnitPaths(cond));
    }

    public BusinessResponseEntity<Pagination<SameCtrlOffSetItemVO>> listOffsets(SameCtrlOffsetCond cond) {
        Pagination<SameCtrlOffSetItemVO> pagination = this.offSetItemService.listOffsets(cond);
        return BusinessResponseEntity.ok(pagination);
    }

    public BusinessResponseEntity<String> deleteOffsetEntrysByMrecid(SameCtrlOffsetCond condition) {
        this.offSetItemService.deleteOffsetEntrysByMrecid(condition);
        return BusinessResponseEntity.ok((Object)"\u53d6\u6d88\u6210\u529f");
    }

    public void extractData(SameCtrlOffsetCond cond) {
        SameCtrlOffSetItemService offSetItemService = SameCtrlOffsetFactory.getSameCtrlOffsetItemService(cond.getShowTabType());
        SameCtrlChgEnvContextImpl sameCtrlChgEnvContext = new SameCtrlChgEnvContextImpl();
        sameCtrlChgEnvContext.setSameCtrlOffsetCond(cond);
        sameCtrlChgEnvContext.setSuccessFlag(true);
        offSetItemService.extractData(sameCtrlChgEnvContext);
    }

    public BusinessResponseEntity<List<SameCtrlOffSetItemVO>> queryInputAdjustment(String mrecid) {
        return BusinessResponseEntity.ok(this.offSetItemService.queryInputAdjustment(mrecid));
    }

    public BusinessResponseEntity<String> saveInputAdjustment(List<List<SameCtrlOffSetItemVO>> batchlist) {
        this.offSetItemService.saveInputAdjustment(batchlist);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f");
    }

    public BusinessResponseEntity<String> deleteInputAdjustment(SameCtrlOffsetCond condition) {
        this.offSetItemService.deleteInputAdjustment(condition);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
    }
}

