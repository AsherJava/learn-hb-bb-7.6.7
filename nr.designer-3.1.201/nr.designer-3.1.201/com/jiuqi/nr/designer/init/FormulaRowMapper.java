/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.designer.init;

import com.jiuqi.nr.designer.init.FormulaUpgradeDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

class FormulaRowMapper
implements RowMapper<FormulaUpgradeDO> {
    FormulaRowMapper() {
    }

    public FormulaUpgradeDO mapRow(ResultSet rs, int rowNum) throws SQLException {
        String formulaKey = rs.getString("fl_key");
        String formKey = rs.getString("fl_form_key");
        String formulaSchemeKey = rs.getString("fl_scheme_key");
        String dLExpression = rs.getString("fl_datalink_expression");
        FormulaUpgradeDO formulaUpgradeDO = new FormulaUpgradeDO();
        formulaUpgradeDO.setFormulaKey(formulaKey);
        formulaUpgradeDO.setFormKey(formKey);
        formulaUpgradeDO.setFormulaSchemeKey(formulaSchemeKey);
        formulaUpgradeDO.setDLExpression(dLExpression);
        return formulaUpgradeDO;
    }
}

