/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.exception.DBParaException
 *  com.jiuqi.np.definition.internal.db.BaseDao
 */
package com.jiuqi.nr.definition.internal.dao.formula;

import com.jiuqi.np.definition.exception.DBParaException;
import com.jiuqi.np.definition.internal.db.BaseDao;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.internal.impl.formula.DesignFormulaConditionLinkImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class DesignFormulaConditionLinkDao
extends BaseDao {
    private static final String S_KEY = "CL_FORMULA_SCHEME";
    private static final String F_KEY = "CL_FM_KEY";
    private static final String KEY = "CL_KEY";

    public Class<DesignFormulaConditionLinkImpl> getClz() {
        return DesignFormulaConditionLinkImpl.class;
    }

    public List<DesignFormulaConditionLink> listConditionLinkByFormulaScheme(String formulaScheme) {
        return this.list(new String[]{"formulaSchemeKey"}, new Object[]{formulaScheme}, this.getClz());
    }

    public void delete(List<DesignFormulaConditionLink> links) {
        if (!CollectionUtils.isEmpty(links)) {
            ArrayList<Object[]> args = new ArrayList<Object[]>(links.size());
            for (DesignFormulaConditionLink link : links) {
                args.add(new Object[]{link.getFormulaSchemeKey(), link.getFormulaKey(), link.getConditionKey()});
            }
            this.jdbcTemplate.batchUpdate("DELETE FROM NR_PARAM_CONDITION_LINK_DES WHERE CL_FORMULA_SCHEME=? AND CL_FM_KEY=? AND CL_KEY=?", args);
        }
    }

    public List<DesignFormulaConditionLink> listConditionLinksByCondition(List<String> conditionKeys) {
        String sbr = "CL_KEY IN (" + conditionKeys.stream().map(s -> "?").collect(Collectors.joining(",")) + ")";
        return this.list(sbr, conditionKeys.toArray(), this.getClz());
    }

    public void deleteByConditionKey(String ... conditionKey) {
        this.jdbcTemplate.batchUpdate("DELETE FROM NR_PARAM_CONDITION_LINK_DES WHERE CL_KEY =?", Arrays.asList(conditionKey), conditionKey.length, (pss, v) -> pss.setString(1, (String)v));
    }

    public void deleteByScheme(String formulaScheme) throws DBParaException {
        this.deleteBy(new String[]{"formulaSchemeKey"}, new Object[]{formulaScheme});
    }

    public void deleteByFormula(String ... formulaKey) {
        this.jdbcTemplate.batchUpdate("DELETE FROM NR_PARAM_CONDITION_LINK_DES WHERE CL_FM_KEY =?", Arrays.asList(formulaKey), formulaKey.length, (pss, v) -> pss.setString(1, (String)v));
    }
}

