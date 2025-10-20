/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgCategoryDO
 *  com.jiuqi.va.feign.client.BaseDataClient
 *  org.aspectj.lang.annotation.AfterReturning
 *  org.aspectj.lang.annotation.Aspect
 *  org.aspectj.lang.annotation.Pointcut
 */
package com.jiuqi.gcreport.org.impl.callback;

import com.jiuqi.np.log.LogHelper;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgCategoryDO;
import com.jiuqi.va.feign.client.BaseDataClient;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class OrgCategoryServicePointCut {
    @Autowired
    private BaseDataClient baseDataClient;

    @Pointcut(value="execution(* com.jiuqi.va.organization.service.OrgCategoryService.add(..)) && args(orgCategory)")
    public void addMethodExecution(OrgCategoryDO orgCategory) {
    }

    @AfterReturning(pointcut="addMethodExecution(orgCategory)", returning="result", argNames="orgCategory,result")
    public void logAddOperationResult(OrgCategoryDO orgCategory, R result) {
        if (result != null && result.getCode() == R.ok().getCode()) {
            try {
                BaseDataDTO baseDataDTO = new BaseDataDTO();
                baseDataDTO.setCode(orgCategory.getName());
                baseDataDTO.setName(orgCategory.getTitle());
                baseDataDTO.setParentcode("-");
                baseDataDTO.setShortname(orgCategory.getTitle());
                baseDataDTO.setStopflag(null);
                baseDataDTO.setTableName("MD_GCORGTYPE");
                this.baseDataClient.add(baseDataDTO);
            }
            catch (Exception e) {
                LogHelper.error((String)"\u5408\u5e76-\u5408\u5e76\u5355\u4f4d\u7ba1\u7406", (String)("\u751f\u6210\u673a\u6784\u7c7b\u578b\u57fa\u7840\u6570\u636e" + orgCategory.getName() + "-\u76ee\u6807"), (String)e.getMessage());
            }
        }
    }
}

