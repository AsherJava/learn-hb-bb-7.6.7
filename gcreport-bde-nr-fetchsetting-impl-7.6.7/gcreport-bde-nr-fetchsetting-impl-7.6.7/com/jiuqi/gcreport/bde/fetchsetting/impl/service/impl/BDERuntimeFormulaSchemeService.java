/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.definition.common.FormulaSchemeType
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.RunTimeFormulaSchemeDefineImpl
 *  com.jiuqi.nr.definition.internal.runtime.service.NrFormulaCacheService
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.fetchsetting.client.vo.FetchSchemeVO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.service.FetchSchemeNrService;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.definition.common.FormulaSchemeType;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.RunTimeFormulaSchemeDefineImpl;
import com.jiuqi.nr.definition.internal.runtime.service.NrFormulaCacheService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class BDERuntimeFormulaSchemeService
extends NrFormulaCacheService {
    private final Logger logger = LoggerFactory.getLogger(((Object)((Object)this)).getClass());
    @Lazy
    @Autowired
    private FetchSchemeNrService fetchSchemeService;

    public FormulaSchemeDefine queryFormulaScheme(String formulaSchemeKey) {
        FetchSchemeVO fetchScheme;
        if (StringUtils.isEmpty((String)formulaSchemeKey)) {
            return super.queryFormulaScheme(formulaSchemeKey);
        }
        if (formulaSchemeKey.length() == 16 && (fetchScheme = this.fetchSchemeService.getFetchScheme(formulaSchemeKey)) != null) {
            return this.convertFetchSchemeVOToFormulaSchemeDefine(fetchScheme);
        }
        return super.queryFormulaScheme(formulaSchemeKey);
    }

    public List<FormulaSchemeDefine> queryFormulaSchemes(List<String> formulaSchemeKeys) {
        return null;
    }

    public FormulaSchemeDefine getDefaultFormulaSchemeInFormScheme(String formSchemeKey) {
        return super.getDefaultFormulaSchemeInFormScheme(formSchemeKey);
    }

    public List<FormulaSchemeDefine> getFormulaSchemesByFormScheme(String formSchemeKey) {
        ArrayList<FormulaSchemeDefine> formulaSchemeList = super.getFormulaSchemesByFormScheme(formSchemeKey);
        if (CollectionUtils.isEmpty((Collection)formulaSchemeList)) {
            formulaSchemeList = new ArrayList<FormulaSchemeDefine>();
        }
        return this.addBDEFormulaSchemeDefinesByFormSchemeKey(formSchemeKey, formulaSchemeList);
    }

    public List<FormulaSchemeDefine> getFormulaSchemesByFormScheme(String formSchemeKey, FormulaSchemeType formulaSchemeType) {
        List formulaSchemeList = super.getFormulaSchemesByFormScheme(formSchemeKey, formulaSchemeType);
        if (!FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL.equals((Object)formulaSchemeType)) {
            return formulaSchemeList;
        }
        return this.addBDEFormulaSchemeDefinesByFormSchemeKey(formSchemeKey, formulaSchemeList);
    }

    private List<FormulaSchemeDefine> addBDEFormulaSchemeDefinesByFormSchemeKey(String formSchemeKey, List<FormulaSchemeDefine> formulaSchemeList) {
        try {
            Set formulaSchemeKeySet = formulaSchemeList.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
            List<FetchSchemeVO> fetchSchemeVOList = this.fetchSchemeService.listFetchScheme(formSchemeKey);
            if (CollectionUtils.isEmpty(fetchSchemeVOList)) {
                return formulaSchemeList;
            }
            for (FetchSchemeVO fetchSchemeVO : fetchSchemeVOList) {
                if (formulaSchemeKeySet.contains(fetchSchemeVO.getId())) continue;
                formulaSchemeList.add(this.convertFetchSchemeVOToFormulaSchemeDefine(fetchSchemeVO));
            }
        }
        catch (Exception e) {
            this.logger.error("BDE\u53d6\u6570\u65b9\u6848\u83b7\u53d6\u5f02\u5e38\uff1a\u6839\u636e\u62a5\u8868\u65b9\u6848\u83b7\u53d6\u53d6\u6570\u65b9\u6848\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
        return formulaSchemeList;
    }

    private FormulaSchemeDefine convertFetchSchemeVOToFormulaSchemeDefine(FetchSchemeVO fetchScheme) {
        if (fetchScheme == null) {
            return null;
        }
        RunTimeFormulaSchemeDefineImpl runTimeFormulaSchemeDefine = new RunTimeFormulaSchemeDefineImpl();
        runTimeFormulaSchemeDefine.setKey(fetchScheme.getId());
        runTimeFormulaSchemeDefine.setFormSchemeKey(fetchScheme.getFormSchemeId());
        runTimeFormulaSchemeDefine.setTitle("BDE-" + fetchScheme.getName());
        runTimeFormulaSchemeDefine.setFormulaSchemeType(FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL);
        runTimeFormulaSchemeDefine.setFormulaSchemeTypeDB(Integer.valueOf(FormulaSchemeType.FORMULA_SCHEME_TYPE_FINANCIAL.getValue()));
        return runTimeFormulaSchemeDefine;
    }
}

