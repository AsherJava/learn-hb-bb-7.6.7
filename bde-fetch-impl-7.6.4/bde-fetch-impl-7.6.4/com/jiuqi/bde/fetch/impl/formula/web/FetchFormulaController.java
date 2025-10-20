/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.base.formula.FetchFormulaContext
 *  com.jiuqi.bde.base.formula.FetchFormulaPaser
 *  com.jiuqi.bde.base.formula.FetchFormulaUtil
 *  com.jiuqi.bde.fetch.client.FetchFormulaClient
 *  com.jiuqi.bde.fetch.client.dto.FetchFormulaDTO
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.StringUtils
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.fetch.impl.formula.web;

import com.jiuqi.bde.base.formula.FetchFormulaContext;
import com.jiuqi.bde.base.formula.FetchFormulaPaser;
import com.jiuqi.bde.base.formula.FetchFormulaUtil;
import com.jiuqi.bde.fetch.client.FetchFormulaClient;
import com.jiuqi.bde.fetch.client.dto.FetchFormulaDTO;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FetchFormulaController
implements FetchFormulaClient {
    public BusinessResponseEntity<Boolean> check(FetchFormulaDTO fetchFormula) {
        if (StringUtils.isEmpty((String)fetchFormula.getFormula())) {
            throw new BusinessRuntimeException("\u516c\u5f0f\u4e0d\u80fd\u4e3a\u7a7a");
        }
        FetchFormulaContext context = new FetchFormulaContext(FetchFormulaUtil.initEnv());
        context.setFloatRowMap(fetchFormula.getFloatRowMap());
        context.setFetchResultMap(fetchFormula.getFetchResultMap());
        new FetchFormulaPaser().parseEval(fetchFormula.getFormula(), context);
        return BusinessResponseEntity.ok();
    }
}

