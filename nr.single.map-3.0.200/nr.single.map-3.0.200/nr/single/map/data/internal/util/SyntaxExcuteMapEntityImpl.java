/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nr.fmdm.IFMDMData
 *  com.jiuqi.nr.single.core.syntax.SyntaxProvider
 *  com.jiuqi.nr.single.core.syntax.TSyntax
 *  com.jiuqi.nr.single.core.syntax.bean.CommonDataType
 *  com.jiuqi.nr.single.core.syntax.common.CommonDataTypeType
 *  com.jiuqi.nr.single.core.syntax.common.SyntaxCode
 *  com.jiuqi.nr.single.core.syntax.common.TCheckType
 *  com.jiuqi.nr.single.core.util.datatable.DataColumn
 *  com.jiuqi.nr.single.core.util.datatable.DataColumnCollection
 *  com.jiuqi.nr.single.core.util.datatable.DataRow
 *  org.apache.commons.lang3.StringUtils
 */
package nr.single.map.data.internal.util;

import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.single.core.syntax.SyntaxProvider;
import com.jiuqi.nr.single.core.syntax.TSyntax;
import com.jiuqi.nr.single.core.syntax.bean.CommonDataType;
import com.jiuqi.nr.single.core.syntax.common.CommonDataTypeType;
import com.jiuqi.nr.single.core.syntax.common.SyntaxCode;
import com.jiuqi.nr.single.core.syntax.common.TCheckType;
import com.jiuqi.nr.single.core.util.datatable.DataColumn;
import com.jiuqi.nr.single.core.util.datatable.DataColumnCollection;
import com.jiuqi.nr.single.core.util.datatable.DataRow;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nr.single.map.data.internal.util.SyntaxProviderMapEntityImpl;
import nr.single.map.data.util.SyntaxExcuteMapEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyntaxExcuteMapEntityImpl
implements SyntaxExcuteMapEntity {
    private static final Logger logger = LoggerFactory.getLogger(SyntaxExcuteMapEntityImpl.class);
    private TSyntax checkSyntax = new TSyntax();
    private SyntaxProvider provider = new SyntaxProviderMapEntityImpl();
    private Map<String, Object> configMap;

    public SyntaxExcuteMapEntityImpl() {
        this.checkSyntax.setProvider(this.provider);
        this.checkSyntax.getExpressions().clear();
        this.configMap = new HashMap<String, Object>();
        this.provider.setVariableMap(this.configMap);
    }

    @Override
    public boolean buildExpression(String expression, List<IFMDMAttribute> fieldList) {
        boolean result = false;
        this.configMap.clear();
        for (IFMDMAttribute filed : fieldList) {
            this.configMap.put(filed.getCode(), null);
        }
        this.provider.setVariableMap(this.configMap);
        this.checkSyntax.getExpressions().clear();
        this.checkSyntax.getExpressions().add(expression);
        SyntaxCode bb = this.checkSyntax.buildNiporlan(TCheckType.CHECK_ATTRACT);
        if (bb == SyntaxCode.SY_OK) {
            result = true;
        } else {
            logger.info("\u8bed\u6cd5\u9519\u8bef\uff1a" + expression);
        }
        return result;
    }

    @Override
    public String getExpValue(IFMDMData entityRow) {
        for (String code : this.configMap.keySet()) {
            this.configMap.put(code, entityRow.getValue(code).getAsString());
        }
        this.provider.setVariableMap(this.configMap);
        String result = "";
        if (this.checkSyntax.getExpressions().size() > 0 && this.checkSyntax.getNiporlans().size() > 0) {
            CommonDataType retData = new CommonDataType();
            this.checkSyntax.attract((String)this.checkSyntax.getNiporlans().get(0), retData);
            if (retData.getCdType() == CommonDataTypeType.CD_STRING_TYPE) {
                if (StringUtils.isNotEmpty((CharSequence)retData.getCdString())) {
                    result = retData.getCdString();
                }
            } else if (retData.getCdType() == CommonDataTypeType.CD_INT_TYPE) {
                result = String.valueOf(retData.getCdInt());
            } else if (retData.getCdType() == CommonDataTypeType.CD_REAL_TYPE) {
                result = String.valueOf(retData.getCdReal());
            } else {
                logger.info("\u53d6\u503c\u5931\u8d25");
            }
        }
        return result;
    }

    @Override
    public boolean buildExpression(String expression, DataColumnCollection dbfColumns) {
        boolean result = false;
        this.configMap.clear();
        for (DataColumn filed : dbfColumns) {
            this.configMap.put(filed.getColumnName(), null);
        }
        this.provider.setVariableMap(this.configMap);
        this.checkSyntax.getExpressions().clear();
        this.checkSyntax.getExpressions().add(expression);
        SyntaxCode bb = this.checkSyntax.buildNiporlan(TCheckType.CHECK_ATTRACT);
        if (bb == SyntaxCode.SY_OK) {
            result = true;
        } else {
            logger.info("\u8bed\u6cd5\u9519\u8bef\uff1a" + expression + ",\u9519\u8bef\u7801\uff1a" + bb);
        }
        return result;
    }

    @Override
    public String getExpValue(DataRow dbfRow) {
        for (String code : this.configMap.keySet()) {
            this.configMap.put(code, dbfRow.getValue(code));
        }
        this.provider.setVariableMap(this.configMap);
        String result = "";
        if (this.checkSyntax.getExpressions().size() > 0 && this.checkSyntax.getNiporlans().size() > 0) {
            CommonDataType retData = new CommonDataType();
            this.checkSyntax.attract((String)this.checkSyntax.getNiporlans().get(0), retData);
            if (retData.getCdType() == CommonDataTypeType.CD_STRING_TYPE) {
                if (StringUtils.isNotEmpty((CharSequence)retData.getCdString())) {
                    result = retData.getCdString();
                }
            } else if (retData.getCdType() == CommonDataTypeType.CD_INT_TYPE) {
                result = String.valueOf(retData.getCdInt());
            } else if (retData.getCdType() == CommonDataTypeType.CD_REAL_TYPE) {
                result = String.valueOf(retData.getCdReal());
            } else {
                logger.info("\u53d6\u503c\u5931\u8d25");
            }
        }
        return result;
    }

    public TSyntax getCheckSyntax() {
        return this.checkSyntax;
    }

    public void setCheckSyntax(TSyntax checkSyntax) {
        this.checkSyntax = checkSyntax;
    }
}

