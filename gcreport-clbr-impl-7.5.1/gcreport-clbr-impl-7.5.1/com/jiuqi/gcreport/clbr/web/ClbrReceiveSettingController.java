/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.clbr.api.ClbrReceiveSettingClient
 *  com.jiuqi.gcreport.clbr.vo.ClbrReceiveSettingCondition
 *  com.jiuqi.gcreport.clbr.vo.ClbrReceiveSettingVO
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.clbr.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.clbr.api.ClbrReceiveSettingClient;
import com.jiuqi.gcreport.clbr.service.ClbrReceiveSettingService;
import com.jiuqi.gcreport.clbr.vo.ClbrReceiveSettingCondition;
import com.jiuqi.gcreport.clbr.vo.ClbrReceiveSettingVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClbrReceiveSettingController
implements ClbrReceiveSettingClient {
    @Autowired
    private ClbrReceiveSettingService clbrReceiveSettingService;

    public BusinessResponseEntity<Boolean> save(@RequestBody ClbrReceiveSettingVO clbrReceiveSettingVO) {
        return BusinessResponseEntity.ok((Object)this.clbrReceiveSettingService.save(clbrReceiveSettingVO));
    }

    public BusinessResponseEntity<PageInfo<ClbrReceiveSettingVO>> query(@RequestBody ClbrReceiveSettingCondition clbrReceiveSettingCondition) {
        return BusinessResponseEntity.ok(this.clbrReceiveSettingService.query(clbrReceiveSettingCondition));
    }

    public BusinessResponseEntity<Boolean> delete(@RequestBody List<String> settingIds) {
        this.clbrReceiveSettingService.delete(settingIds);
        return BusinessResponseEntity.ok((Object)true);
    }

    public BusinessResponseEntity<Boolean> edit(@RequestBody ClbrReceiveSettingVO clbrReceiveSettingVO) {
        return BusinessResponseEntity.ok((Object)this.clbrReceiveSettingService.edit(clbrReceiveSettingVO));
    }

    public BusinessResponseEntity<List<ClbrReceiveSettingVO>> queryForUser(ClbrReceiveSettingCondition clbrReceiveSettingCondition) {
        return BusinessResponseEntity.ok(this.clbrReceiveSettingService.queryForUser(clbrReceiveSettingCondition));
    }
}

