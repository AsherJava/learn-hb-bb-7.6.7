/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.internal.util.SpringUtil
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.ITree
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.FieldValidator
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.common.DataSchemeEnum
 *  com.jiuqi.nr.datascheme.web.facade.BaseDataSchemeVO
 *  com.jiuqi.nr.datascheme.web.facade.DataFieldPageVO
 *  com.jiuqi.nr.datascheme.web.param.DataSchemeQueryPM
 *  com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery
 *  com.jiuqi.nr.datascheme.web.rest.DataSchemeRestController
 *  com.jiuqi.nr.datascheme.web.rest.DataSchemeTreeRestController
 *  com.jiuqi.nr.query.datascheme.web.param.QueryDataFieldVO
 *  com.jiuqi.nr.query.datascheme.web.rest.QueryDataFieldRestController
 */
package com.jiuqi.gc.financialcubes.query.init;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.internal.util.SpringUtil;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.FieldValidator;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.web.facade.BaseDataSchemeVO;
import com.jiuqi.nr.datascheme.web.facade.DataFieldPageVO;
import com.jiuqi.nr.datascheme.web.param.DataSchemeQueryPM;
import com.jiuqi.nr.datascheme.web.param.DataSchemeTreeQuery;
import com.jiuqi.nr.datascheme.web.rest.DataSchemeRestController;
import com.jiuqi.nr.datascheme.web.rest.DataSchemeTreeRestController;
import com.jiuqi.nr.query.datascheme.web.param.QueryDataFieldVO;
import com.jiuqi.nr.query.datascheme.web.rest.QueryDataFieldRestController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.sql.DataSource;

public class SubjectDimInitUpdate
implements CustomClassExecutor {
    private static final String GROUP_KEY = "00000000-0000-0000-0000-111111111111";
    private static final String SUBJECTCODE = "SUBJECTCODE";
    private static final String CFITEMCODE = "CFITEMCODE";
    private static final int PAGE_COUNT = 30;
    private static final int PAGE_NUM = 1;

    public void execute(DataSource dataSource) throws Exception {
        DataSchemeRestController dataSchemeRestController = (DataSchemeRestController)SpringUtil.getBean(DataSchemeRestController.class);
        DataSchemeTreeRestController dataSchemeTreeRestController = (DataSchemeTreeRestController)SpringUtil.getBean(DataSchemeTreeRestController.class);
        QueryDataFieldRestController queryDataFieldRestController = (QueryDataFieldRestController)SpringUtil.getBean(QueryDataFieldRestController.class);
        IDesignDataSchemeService designDataSchemeService = (IDesignDataSchemeService)SpringUtil.getBean(IDesignDataSchemeService.class);
        FieldValidator fieldValidator = (FieldValidator)SpringUtil.getBean(FieldValidator.class);
        List baseDataSchemeVOS = dataSchemeRestController.queryBaseDataSchemes();
        baseDataSchemeVOS = baseDataSchemeVOS.stream().filter(dataScheme -> GROUP_KEY.equals(dataScheme.getDataGroupKey())).collect(Collectors.toList());
        for (BaseDataSchemeVO baseDataSchemeVO : baseDataSchemeVOS) {
            String key = baseDataSchemeVO.getKey();
            DataSchemeTreeQuery dataSchemeQuery = new DataSchemeTreeQuery();
            dataSchemeQuery.setDataSchemeKey(key);
            List iTrees = dataSchemeTreeRestController.querySchemeTreePath(dataSchemeQuery);
            List children = ((ITree)iTrees.get(0)).getChildren();
            for (ITree node : children) {
                if (node.getChildren() != null) continue;
                String tableKey = node.getKey();
                DataSchemeQueryPM dataSchemeQueryPM = new DataSchemeQueryPM();
                dataSchemeQueryPM.setPageCount(Integer.valueOf(30));
                dataSchemeQueryPM.setPageNum(Integer.valueOf(1));
                dataSchemeQueryPM.setTable(tableKey);
                DataFieldPageVO listDataFieldPageVO = queryDataFieldRestController.queryDataField(dataSchemeQueryPM);
                List value = (List)listDataFieldPageVO.getValue();
                ArrayList<String> keys = new ArrayList<String>();
                boolean hasCFITEMCODE = false;
                boolean hasSUBJECTCODE = false;
                String dataTableKey = null;
                for (QueryDataFieldVO dataFieldVO : value) {
                    if (dataTableKey == null) {
                        dataTableKey = dataFieldVO.getDataTableKey();
                    }
                    if (DataFieldKind.PUBLIC_FIELD_DIM.equals((Object)dataFieldVO.getDataFieldKind()) || DataFieldKind.TABLE_FIELD_DIM.equals((Object)dataFieldVO.getDataFieldKind())) {
                        keys.add(dataFieldVO.getKey());
                    }
                    if (SUBJECTCODE.equals(dataFieldVO.getCode())) {
                        hasSUBJECTCODE = true;
                        keys.add(dataFieldVO.getKey());
                        continue;
                    }
                    if (!CFITEMCODE.equals(dataFieldVO.getCode())) continue;
                    hasCFITEMCODE = true;
                    keys.add(dataFieldVO.getKey());
                }
                if (hasCFITEMCODE && hasSUBJECTCODE) {
                    keys.removeIf(singleKey -> value.stream().anyMatch(dataFieldVO -> SUBJECTCODE.equals(dataFieldVO.getCode()) && singleKey.equals(dataFieldVO.getKey())));
                }
                if (keys.isEmpty() || dataTableKey == null) continue;
                this.updateFieldWithKeys(keys, dataTableKey, designDataSchemeService, fieldValidator);
            }
        }
    }

    public void updateFieldWithKeys(List<String> keys, String tableKey, IDesignDataSchemeService designDataSchemeService, FieldValidator fieldValidator) throws JQException {
        List dataFields = keys.isEmpty() ? new ArrayList() : designDataSchemeService.getDataFields(keys);
        List bizFields = designDataSchemeService.getDataFieldByTableKeyAndKind(tableKey, new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM});
        Iterator iterator = dataFields.iterator();
        Map<String, DesignDataField> old = bizFields.stream().collect(Collectors.toMap(Basic::getKey, r -> r, (r1, r2) -> r1));
        while (iterator.hasNext()) {
            DesignDataField next = (DesignDataField)iterator.next();
            next.setUpdateTime(null);
            old.remove(next.getKey());
            if (next.getDataFieldKind().equals((Object)DataFieldKind.FIELD) || !DataFieldKind.TABLE_FIELD_DIM.equals((Object)next.getDataFieldKind())) {
                next.setDataFieldKind(DataFieldKind.TABLE_FIELD_DIM);
                continue;
            }
            iterator.remove();
        }
        try {
            if (!old.isEmpty()) {
                Collection<DesignDataField> oldValues = old.values();
                for (DesignDataField oldValue : oldValues) {
                    oldValue.setDataFieldKind(DataFieldKind.FIELD);
                    oldValue.setUpdateTime(null);
                    dataFields.add(oldValue);
                    fieldValidator.levelCheckField(oldValue);
                }
            }
            designDataSchemeService.updateDataFields(dataFields);
        }
        catch (SchemeDataException e) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }
}

