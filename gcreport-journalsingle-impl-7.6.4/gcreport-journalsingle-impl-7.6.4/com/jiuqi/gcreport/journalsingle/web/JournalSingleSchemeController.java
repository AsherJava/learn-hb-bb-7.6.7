/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.journalsingle.api.JournalSingleSchemeClient
 *  com.jiuqi.gcreport.journalsingle.vo.JournalRelateSchemeVO
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.journalsingle.web;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.journalsingle.api.JournalSingleSchemeClient;
import com.jiuqi.gcreport.journalsingle.service.IJournalSingleSchemeService;
import com.jiuqi.gcreport.journalsingle.vo.JournalRelateSchemeVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Primary
public class JournalSingleSchemeController
implements JournalSingleSchemeClient {
    @Autowired
    private IJournalSingleSchemeService journalSingleSchemeService;

    public BusinessResponseEntity<String> insertRelateScheme(JournalRelateSchemeVO formMappingVo) {
        String msg = this.journalSingleSchemeService.insertRelateScheme(formMappingVo);
        if (StringUtils.isEmpty(msg)) {
            return BusinessResponseEntity.ok((Object)"\u65b0\u589e\u6210\u529f");
        }
        return BusinessResponseEntity.error((String)msg);
    }

    public BusinessResponseEntity<String> deleteRelateScheme(JournalRelateSchemeVO formMappingVo) {
        this.journalSingleSchemeService.deleteRelateScheme(formMappingVo);
        return BusinessResponseEntity.ok((Object)"\u5220\u9664\u6210\u529f");
    }

    public BusinessResponseEntity<List<JournalRelateSchemeVO>> listRelateSchemes() {
        return BusinessResponseEntity.ok(this.journalSingleSchemeService.listRelateSchemes());
    }
}

