/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 */
package com.jiuqi.nr.datacrud.impl.format.strategy;

import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.impl.MetaData;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.RegionRelationFactory;
import com.jiuqi.nr.datacrud.impl.param.TaskDefineProxy;
import com.jiuqi.nr.datacrud.spi.IEntityTableFactory;
import com.jiuqi.nr.datacrud.spi.TypeFormatStrategy;
import com.jiuqi.nr.datacrud.spi.entity.EntityQueryMode;
import com.jiuqi.nr.datacrud.spi.entity.IEntityTableWrapper;
import com.jiuqi.nr.datacrud.spi.entity.QueryModeImpl;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

public class EnumTypeStrategy
implements TypeFormatStrategy {
    protected final IEntityMetaService entityMetaService;
    protected final IEntityTableFactory entityTableFactory;
    protected final RegionRelationFactory regionRelationFactory;
    protected Map<String, RegionRelation> relationMap = new HashMap<String, RegionRelation>();
    protected Map<String, List<String>> captionCodesMap = new HashMap<String, List<String>>();
    protected Map<String, ReportFmlExecEnvironment> envMap = new HashMap<String, ReportFmlExecEnvironment>();
    protected IEntityTableWrapper entityTableWrapper;
    protected DimensionCombination currMasterKey;

    public EnumTypeStrategy(IEntityMetaService entityMetaService, IEntityTableFactory entityTableFactory, RegionRelationFactory regionRelationFactory) {
        this.entityMetaService = entityMetaService;
        this.entityTableFactory = entityTableFactory;
        this.regionRelationFactory = regionRelationFactory;
    }

    @Override
    public void setRowKey(DimensionCombination masterKey) {
        this.currMasterKey = masterKey;
    }

    @Override
    public String format(IMetaData metaData, com.jiuqi.np.dataengine.data.AbstractData abstractData) {
        IFMDMAttribute fmAttribute = metaData.getFmAttribute();
        if (fmAttribute != null) {
            return this.format(metaData.getDataLinkDefine(), fmAttribute, abstractData);
        }
        return this.format(metaData.getDataLinkDefine(), metaData.getDataField(), abstractData);
    }

    @Override
    public String format(DataLinkDefine link, DataField field, com.jiuqi.np.dataengine.data.AbstractData abstractData) {
        if (abstractData == null || abstractData.isNull) {
            return "";
        }
        String storeValue = abstractData.getAsString();
        if (!StringUtils.hasLength(storeValue)) {
            return "";
        }
        if (link != null && field != null) {
            String refDataEntityKey = field.getRefDataEntityKey();
            boolean allowMultipleSelect = field.isAllowMultipleSelect();
            if (StringUtils.hasLength(refDataEntityKey)) {
                MetaData metaData = new MetaData(field, link);
                RegionRelation regionRelation = this.relationMap.get(link.getRegionKey());
                if (regionRelation == null) {
                    regionRelation = this.regionRelationFactory.getRegionRelation(link.getRegionKey());
                    this.relationMap.put(link.getRegionKey(), regionRelation);
                }
                this.reSetEnv(regionRelation);
                if (this.entityTableWrapper == null) {
                    this.entityTableWrapper = this.entityTableFactory.getEntityTable((ParamRelation)regionRelation, this.currMasterKey, (IMetaData)metaData, QueryModeImpl.create(EntityQueryMode.IGNORE_ISOLATE_CONDITION.getValue(), true));
                } else {
                    this.entityTableFactory.reBuildEntityTable((ParamRelation)regionRelation, this.currMasterKey, (IMetaData)metaData, this.entityTableWrapper, QueryModeImpl.create(EntityQueryMode.IGNORE_ISOLATE_CONDITION.getValue(), true));
                }
                String captionFieldsString = link.getCaptionFieldsString();
                List captionCodes = this.captionCodesMap.computeIfAbsent(link.getKey(), k -> {
                    ArrayList<String> list = new ArrayList<String>();
                    if (!StringUtils.hasLength(captionFieldsString)) {
                        IEntityModel entityModel = this.entityMetaService.getEntityModel(refDataEntityKey);
                        IEntityAttribute nameField = entityModel.getNameField();
                        list.add(nameField.getCode());
                    } else {
                        list.addAll(Arrays.asList(captionFieldsString.split(";")));
                    }
                    return list;
                });
                String fullPathCode = link.getEnumShowFullPath();
                ArrayList<String> showValues = new ArrayList<String>();
                ArrayList<String> keys = new ArrayList<String>();
                if (allowMultipleSelect) {
                    String[] values = storeValue.split(";");
                    keys.addAll(Arrays.asList(values));
                } else {
                    keys.add(storeValue);
                }
                for (String key : keys) {
                    IEntityRow entityRow = this.entityTableWrapper.findByEntityKey(key);
                    if (entityRow == null) {
                        showValues.add(key);
                        continue;
                    }
                    ArrayList<String> showCodeValues = new ArrayList<String>();
                    for (String code : captionCodes) {
                        if (code.equals(fullPathCode)) {
                            String[] paths = entityRow.getParentsEntityKeyDataPath();
                            ArrayList<String> pathValues = new ArrayList<String>();
                            for (String pathValue : paths) {
                                AbstractData rowValue;
                                IEntityRow pathRow = this.entityTableWrapper.findByEntityKey(pathValue);
                                if (pathRow == null || (rowValue = pathRow.getValue(fullPathCode)) == null) continue;
                                pathValues.add(rowValue.getAsString());
                            }
                            AbstractData rowValue = entityRow.getValue(code);
                            if (rowValue == null || rowValue.isNull) {
                                pathValues.add("");
                            } else {
                                pathValues.add(rowValue.getAsString());
                            }
                            showCodeValues.add(String.join((CharSequence)"/", pathValues));
                            continue;
                        }
                        AbstractData rowValue = entityRow.getValue(code);
                        if (rowValue == null || rowValue.isNull) {
                            showCodeValues.add("");
                            continue;
                        }
                        showCodeValues.add(rowValue.getAsString());
                    }
                    showValues.add(String.join((CharSequence)"|", showCodeValues));
                }
                return String.join((CharSequence)";", showValues);
            }
        }
        return storeValue;
    }

    private void reSetEnv(RegionRelation regionRelation) {
        FormSchemeDefine formSchemeDefine = regionRelation.getFormSchemeDefine();
        ReportFmlExecEnvironment environment = this.envMap.get(formSchemeDefine.getKey());
        if (environment == null) {
            environment = regionRelation.getReportFmlExecEnvironment();
            this.envMap.put(formSchemeDefine.getKey(), environment);
        } else {
            regionRelation.setReportFmlExecEnvironment(environment);
        }
    }

    @Override
    public String format(DataLinkDefine link, IFMDMAttribute fmAttribute, com.jiuqi.np.dataengine.data.AbstractData abstractData) {
        DataField fmDataField = null;
        if (fmAttribute != null) {
            fmDataField = TaskDefineProxy.createDataFieldProxy(fmAttribute);
        }
        return this.format(link, fmDataField, abstractData);
    }
}

