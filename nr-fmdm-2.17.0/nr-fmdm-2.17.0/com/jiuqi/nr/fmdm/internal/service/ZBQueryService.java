/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataLinkType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.fmdm.internal.service;

import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.internal.dto.QueryParamDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZBQueryService {
    private static final Logger log = LoggerFactory.getLogger(ZBQueryService.class);
    @Autowired
    private IRunTimeViewController runTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    public List<FieldDefine> getAllFieldDefineInForm(String formKey) {
        return this.getFieldDefines(formKey, DataLinkType.DATA_LINK_TYPE_FIELD, false, null);
    }

    public List<FieldDefine> getAllFieldDefineInForm(QueryParamDTO queryParamDTO) {
        return this.getFieldDefines(queryParamDTO.getFormKey(), DataLinkType.DATA_LINK_TYPE_FIELD, queryParamDTO.isQueryPartZb(), queryParamDTO.getFmdmAttributeList());
    }

    public List<FieldDefine> getAllUnitInfoFieldInForm(String formKey) {
        return this.getFieldDefines(formKey, DataLinkType.DATA_LINK_TYPE_INFO, false, null, true);
    }

    public List<FieldDefine> getAllUnitInfoFieldInForm(QueryParamDTO queryParamDTO) {
        return this.getFieldDefines(queryParamDTO.getFormKey(), DataLinkType.DATA_LINK_TYPE_INFO, queryParamDTO.isQueryPartZb(), queryParamDTO.getFmdmAttributeList());
    }

    private List<FieldDefine> getFieldDefines(String formKey, DataLinkType linkType, boolean isQueryPartField, List<IFMDMAttribute> fmdmAttributeList) {
        return this.getFieldDefines(formKey, linkType, isQueryPartField, fmdmAttributeList, false);
    }

    private List<FieldDefine> getFieldDefines(String formKey, DataLinkType linkType, boolean isQueryPartField, List<IFMDMAttribute> fmdmAttributeList, boolean isQueryAllZb) {
        List<Object> fieldKeys = new ArrayList();
        if (isQueryAllZb) {
            FormDefine formDefine = this.runTimeController.queryFormById(formKey);
            if (formDefine == null) {
                return new ArrayList<FieldDefine>();
            }
            FormSchemeDefine formSchemeDefine = this.runTimeController.getFormScheme(formDefine.getFormScheme());
            TaskDefine taskDefine = this.runTimeController.queryTaskDefine(formSchemeDefine.getTaskKey());
            DataTable unitInfoTable = this.runtimeDataSchemeService.getDataTableForMdInfo(taskDefine.getDataScheme());
            String unitInfoTableKey = unitInfoTable == null ? "" : unitInfoTable.getKey();
            try {
                List unitInfoFields = this.dataDefinitionRuntimeController.getAllFieldsInTable(unitInfoTableKey);
                Set excludedCodes = this.runTimeController.getAllLinksInForm(formKey).stream().filter(e -> e.getType().equals((Object)DataLinkType.DATA_LINK_TYPE_FMDM)).map(DataLinkDefine::getLinkExpression).collect(Collectors.toCollection(HashSet::new));
                Collections.addAll(excludedCodes, "MDCODE", "DATATIME");
                fieldKeys = unitInfoFields.stream().filter(field -> !excludedCodes.contains(field.getCode())).map(IBaseMetaItem::getKey).collect(Collectors.toList());
            }
            catch (Exception e2) {
                log.error("\u67e5\u8be2\u5355\u4f4d\u4fe1\u606f\u8868\u6307\u6807\u65f6\u51fa\u9519\uff1a{}", (Object)e2.getMessage());
            }
        } else {
            List allLinksInForm = this.runTimeController.getAllLinksInForm(formKey);
            fieldKeys = allLinksInForm.stream().filter(e -> e.getType().equals((Object)linkType)).map(DataLinkDefine::getLinkExpression).collect(Collectors.toList());
        }
        if (isQueryPartField) {
            HashSet<String> intersection = new HashSet<String>();
            ArrayList<IFMDMAttribute> list = new ArrayList<IFMDMAttribute>(fmdmAttributeList);
            block4: for (String fieldKey : fieldKeys) {
                for (IFMDMAttribute ifmdmAttribute : list) {
                    if (!Objects.nonNull(ifmdmAttribute.getZBKey()) || !fieldKey.equals(ifmdmAttribute.getZBKey())) continue;
                    intersection.add(fieldKey);
                    list.remove(ifmdmAttribute);
                    continue block4;
                }
            }
            fieldKeys = new ArrayList(intersection);
        }
        List fieldDefines = null;
        try {
            fieldDefines = this.dataDefinitionRuntimeController.queryFieldDefines(fieldKeys);
        }
        catch (Exception e3) {
            log.error(e3.getMessage(), e3);
        }
        return fieldDefines;
    }
}

