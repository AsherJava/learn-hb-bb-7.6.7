/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.access.param.IAccessResult
 *  com.jiuqi.nr.data.access.service.IDataAccessService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.io.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.access.param.IAccessResult;
import com.jiuqi.nr.data.access.service.IDataAccessService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.io.service.IoQualifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IoQualifierImpl
implements IoQualifier {
    private static final Logger log = LoggerFactory.getLogger(IoQualifierImpl.class);
    @Autowired
    private IDataAccessServiceProvider iDataAccessServiceProvider;

    @Override
    @Deprecated
    public List<String> getFormKeys() {
        return null;
    }

    @Override
    @Deprecated
    public List<String> getNoAccessFormKeys() {
        return null;
    }

    @Override
    public Map<String, List<String>> initQualifier(TableContext tableContext, DimensionValueSet dimensions) {
        ArrayList<String> formKeys = new ArrayList<String>();
        ArrayList<String> noAccessnFormKeys = new ArrayList<String>();
        IDataAccessService dataAccessService = this.iDataAccessServiceProvider.getDataAccessService(tableContext.getTaskKey(), tableContext.getFormSchemeKey());
        String formSchemeKey = tableContext.getFormSchemeKey();
        DimensionCombination collection = DimensionValueSetUtil.buildDimensionCombination((DimensionValueSet)dimensions, (String)formSchemeKey);
        IAccessResult access = dataAccessService.writeable(collection, tableContext.getFormKey());
        try {
            if (access.haveAccess()) {
                formKeys.add(tableContext.getFormKey());
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("\u7ef4\u5ea6\u3010{}\u3011\u6743\u9650\u6821\u9a8c\u4e0d\u901a\u8fc7\uff0c\u539f\u56e0\u4e3a\uff1a{}", (Object)collection.toDimensionValueSet(), (Object)access.getMessage());
                }
                noAccessnFormKeys.add(tableContext.getFormKey());
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
        HashMap<String, List<String>> res = new HashMap<String, List<String>>();
        res.put("formKeys", formKeys);
        res.put("noAccessnFormKeys", noAccessnFormKeys);
        return res;
    }
}

