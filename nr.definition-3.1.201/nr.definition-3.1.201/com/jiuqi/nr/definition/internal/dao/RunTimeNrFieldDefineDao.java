/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.xlib.utils.StringUtils
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.RuntimeDefinitionTransfer;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.internal.dao.RunTimeDataLinkDefineDao;
import com.jiuqi.xlib.utils.StringUtils;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RunTimeNrFieldDefineDao {
    @Autowired
    private RunTimeDataLinkDefineDao runTimeDataLinkDefineDao;
    @Autowired
    private IRuntimeDataSchemeService iRuntimeDataSchemeService;

    public List<FieldDefine> getAllFieldsInRegion(String regionKey) {
        List<DataLinkDefine> allLinksInRegion = this.runTimeDataLinkDefineDao.getAllLinksInRegion(regionKey);
        if (null == allLinksInRegion || allLinksInRegion.isEmpty()) {
            return Collections.emptyList();
        }
        List collect = allLinksInRegion.stream().map(DataLinkDefine::getLinkExpression).filter(StringUtils::hasText).collect(Collectors.toList());
        return this.iRuntimeDataSchemeService.getDataFields(collect).stream().map(RuntimeDefinitionTransfer::toFieldDefine).collect(Collectors.toList());
    }
}

