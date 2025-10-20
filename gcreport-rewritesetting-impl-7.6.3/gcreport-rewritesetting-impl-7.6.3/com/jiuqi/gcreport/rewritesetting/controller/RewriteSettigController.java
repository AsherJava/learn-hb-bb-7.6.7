/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.rewritesetting.RewriteSettingClient
 *  com.jiuqi.gcreport.rewritesetting.vo.FloatRegionTreeVO
 *  com.jiuqi.gcreport.rewritesetting.vo.RewriteFieldInfoVO
 *  com.jiuqi.gcreport.rewritesetting.vo.RewriteFieldMappingVO
 *  com.jiuqi.gcreport.rewritesetting.vo.RewriteSettingVO
 *  com.jiuqi.gcreport.rewritesetting.vo.RewriteSubjectSettingVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.rewritesetting.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.rewritesetting.RewriteSettingClient;
import com.jiuqi.gcreport.rewritesetting.service.RewriteSettingService;
import com.jiuqi.gcreport.rewritesetting.vo.FloatRegionTreeVO;
import com.jiuqi.gcreport.rewritesetting.vo.RewriteFieldInfoVO;
import com.jiuqi.gcreport.rewritesetting.vo.RewriteFieldMappingVO;
import com.jiuqi.gcreport.rewritesetting.vo.RewriteSettingVO;
import com.jiuqi.gcreport.rewritesetting.vo.RewriteSubjectSettingVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@Primary
@RestController
public class RewriteSettigController
implements RewriteSettingClient {
    @Autowired
    private RewriteSettingService rewriteSettingService;

    public BusinessResponseEntity<String> deleteRewriteSettingByIds(List<String> deleteIds) {
        this.rewriteSettingService.deleteRewriteSetting(deleteIds);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f\uff01");
    }

    public BusinessResponseEntity<List<RewriteSettingVO>> queryRewriteSettings(String schemeId) {
        return BusinessResponseEntity.ok(this.rewriteSettingService.queryRewriteSettings(schemeId));
    }

    public BusinessResponseEntity<String> saveRewriteSetting(RewriteSettingVO rewriteSettingVO) {
        this.rewriteSettingService.saveRewriteSetting(rewriteSettingVO);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f");
    }

    public BusinessResponseEntity<List<FloatRegionTreeVO>> getFloatRegionTree(String schemeId) {
        return BusinessResponseEntity.ok(this.rewriteSettingService.getFloatRegionTree(schemeId));
    }

    public BusinessResponseEntity<String> saveRewriteSubjectSettings(String schemeId, List<RewriteSubjectSettingVO> rewriteSubjectSettings) {
        this.rewriteSettingService.saveRewriteSubjectSettings(schemeId, rewriteSubjectSettings);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f");
    }

    public BusinessResponseEntity<List<RewriteSubjectSettingVO>> queryRewriteSubjectSettings(String schemeId) {
        return BusinessResponseEntity.ok(this.rewriteSettingService.queryRewriteSubjectSettings(schemeId));
    }

    public BusinessResponseEntity<List<RewriteFieldInfoVO>> queryOffsetDataModel(String id) {
        return BusinessResponseEntity.ok(this.rewriteSettingService.queryOffsetDataModel(id));
    }

    public BusinessResponseEntity<List<RewriteFieldMappingVO>> queryFieldMappingSettings(String id) {
        return BusinessResponseEntity.ok(this.rewriteSettingService.queryFieldMappingSettings(id));
    }
}

