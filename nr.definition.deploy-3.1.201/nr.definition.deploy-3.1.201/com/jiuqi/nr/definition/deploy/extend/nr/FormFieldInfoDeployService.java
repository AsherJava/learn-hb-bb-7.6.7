/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.ParamResourceType
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.definition.deploy.extend.nr;

import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.deploy.extend.IParamDeployExtendService;
import com.jiuqi.nr.definition.deploy.extend.IPartialDeployExtendService;
import com.jiuqi.nr.definition.deploy.extend.nr.FormulaVariableDeployService;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Order(value=10000)
public class FormFieldInfoDeployService
implements IParamDeployExtendService,
IPartialDeployExtendService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormulaVariableDeployService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String DELETE_BY_FORM = "DELETE FROM NR_PARAM_FORM_FIELD_INFO WHERE FORM_KEY = ?";
    private static final String DELETE_BY_FORMSCHEME = "DELETE FROM NR_PARAM_FORM_FIELD_INFO WHERE FORM_KEY IN ( SELECT FM_KEY FROM NR_PARAM_FORM WHERE FM_FORMSCHEME = ?)";
    private static final String INSERT_BY_FORM = "INSERT INTO NR_PARAM_FORM_FIELD_INFO (TASK_KEY, FORM_KEY, FIELD_KEY) SELECT S.FC_TASK_KEY TASK_KEY, F.FM_KEY FORM_KEY, L.DL_FIELD_KEY FIELD_KEY FROM NR_PARAM_DATALINK_DES L LEFT JOIN NR_PARAM_DATAREGION_DES R ON L.DL_REGION_KEY = R.DR_KEY LEFT JOIN NR_PARAM_FORM_DES F ON F.FM_KEY = R.DR_FORM_KEY LEFT JOIN NR_PARAM_FORMSCHEME_DES S ON S.FC_KEY = F.FM_FORMSCHEME WHERE L.DL_TYPE IN (1, 4) AND F.FM_KEY = ?";
    private static final String INSERT_BY_FORMSCHEME = "INSERT INTO NR_PARAM_FORM_FIELD_INFO (TASK_KEY, FORM_KEY, FIELD_KEY) SELECT S.FC_TASK_KEY TASK_KEY, F.FM_KEY FORM_KEY, L.DL_FIELD_KEY FIELD_KEY FROM NR_PARAM_DATALINK_DES L LEFT JOIN NR_PARAM_DATAREGION_DES R ON L.DL_REGION_KEY = R.DR_KEY LEFT JOIN NR_PARAM_FORM_DES F ON F.FM_KEY = R.DR_FORM_KEY LEFT JOIN NR_PARAM_FORMSCHEME_DES S ON S.FC_KEY = F.FM_FORMSCHEME WHERE L.DL_TYPE IN (1, 4) AND S.FC_KEY = ?";

    @Override
    public void beforeDeploy(String formSchemeKey, Consumer<String> progress) {
        progress.accept("\u53d1\u5e03\u62a5\u8868\u4e0e\u6307\u6807\u7684\u94fe\u63a5");
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u53d1\u5e03\u62a5\u8868\u4e0e\u6307\u6807\u7684\u94fe\u63a5");
        this.beforeDeploy(formSchemeKey, Collections.emptyList());
    }

    @Override
    public ParamResourceType getType() {
        return ParamResourceType.FORM;
    }

    @Override
    public void beforeDeploy(String schemeKey, List<String> sourceKeys) {
        if (CollectionUtils.isEmpty(sourceKeys)) {
            this.deploy(DELETE_BY_FORMSCHEME, INSERT_BY_FORMSCHEME, schemeKey);
        } else {
            for (String sourceKey : sourceKeys) {
                this.deploy(DELETE_BY_FORM, INSERT_BY_FORM, sourceKey);
            }
        }
    }

    private void deploy(String deleteSql, String insertSql, String schemeKey) {
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u5220\u9664\u62a5\u8868\u4e0e\u6307\u6807\u7684\u94fe\u63a5\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}", (Object)deleteSql, (Object)schemeKey);
        this.jdbcTemplate.update(deleteSql, new Object[]{schemeKey});
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u65b0\u589e\u62a5\u8868\u4e0e\u6307\u6807\u7684\u94fe\u63a5\uff0cSQL\uff1a{}\uff0c\u53c2\u6570\uff1a{}", (Object)insertSql, (Object)schemeKey);
        this.jdbcTemplate.update(insertSql, new Object[]{schemeKey});
    }
}

