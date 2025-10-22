/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.consolidatedsystem.api.primaryworkpaper.PrimaryWorkpaperClient
 *  com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperSettingVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperTypeVO
 *  com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.consolidatedsystem.web.primaryworkpaper;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.consolidatedsystem.api.primaryworkpaper.PrimaryWorkpaperClient;
import com.jiuqi.gcreport.consolidatedsystem.service.primaryworkpaper.PrimaryWorkpaperService;
import com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperSettingVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.primaryworkpaper.PrimaryWorkpaperTypeVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

@Primary
@RestController
public class PrimaryWorkpaperController
implements PrimaryWorkpaperClient {
    @Autowired
    private PrimaryWorkpaperService primaryWorkpaperService;

    public BusinessResponseEntity<PrimaryWorkpaperTypeVO> addPrimaryWorkpaperType(PrimaryWorkpaperTypeVO primaryWorkpaperTypeVO) {
        return BusinessResponseEntity.ok((Object)this.primaryWorkpaperService.addPrimaryWorkpaperType(primaryWorkpaperTypeVO));
    }

    public BusinessResponseEntity<List<PrimaryWorkpaperTypeVO>> listPrimaryWorkpaperTypeTree(String reportSystemId) {
        return BusinessResponseEntity.ok(this.primaryWorkpaperService.listPrimaryWorkpaperTypeTree(reportSystemId));
    }

    public BusinessResponseEntity<String> deletePrimaryWorkpaperTypeById(String id) {
        this.primaryWorkpaperService.deletePrimaryWorkpaperTypeById(id);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f\uff01");
    }

    public BusinessResponseEntity<PrimaryWorkpaperTypeVO> updatePrimaryWorkpaperType(PrimaryWorkpaperTypeVO primaryWorkpaperTypeVO) {
        return BusinessResponseEntity.ok((Object)this.primaryWorkpaperService.updatePrimaryWorkpaperType(primaryWorkpaperTypeVO));
    }

    public BusinessResponseEntity<String> moveTypeTreeNode(String id, Integer step) {
        return BusinessResponseEntity.ok((Object)this.primaryWorkpaperService.moveTypeTreeNode(id, step));
    }

    public BusinessResponseEntity<List<PrimaryWorkpaperSettingVO>> listPrimarySettingDatas(String typeId) {
        return BusinessResponseEntity.ok(this.primaryWorkpaperService.listPrimarySettingDatas(typeId));
    }

    public BusinessResponseEntity<Map<String, List<ConsolidatedSubjectVO>>> getZbCodeToSubjectsMap(String systemId) {
        return BusinessResponseEntity.ok(this.primaryWorkpaperService.getZbCodeToSubjectsMap(systemId));
    }

    public BusinessResponseEntity<String> savePrimaryWorkpaperSets(List<String> deleteIds, List<PrimaryWorkpaperSettingVO> primaryWorkpaperSettingVOS) {
        if (!CollectionUtils.isEmpty(deleteIds)) {
            this.primaryWorkpaperService.deletePrimaryWorkpaperSetsByIds(deleteIds);
        }
        this.primaryWorkpaperService.savePrimaryWorkpaperSets(primaryWorkpaperSettingVOS);
        return BusinessResponseEntity.ok((Object)"\u4fdd\u5b58\u6210\u529f");
    }

    public BusinessResponseEntity<String> deletePrimaryWorkpaperSetsByIds(List<String> setIds) {
        this.primaryWorkpaperService.deletePrimaryWorkpaperSetsByIds(setIds);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
    }
}

