/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.datascheme.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeAuthService;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.internal.convert.Convert;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.datascheme.web.facade.BaseDataVO;
import com.jiuqi.nr.datascheme.web.facade.DataDimPeriodVO;
import com.jiuqi.nr.datascheme.web.facade.DimDataVO;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/datascheme/"})
@Api(tags={"\u6570\u636e\u65b9\u6848\uff1a\u7ef4\u5ea6\u5217\u8868\u670d\u52a1"})
public class DataDimRestController {
    private final Logger logger = LoggerFactory.getLogger(DataDimRestController.class);
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDesignDataSchemeService iDesignDataSchemeService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IDataSchemeAuthService dataSchemeAuthService;

    @ApiOperation(value="\u67e5\u8be2\u7ec4\u7ec7\u673a\u6784\u548c\u7ef4\u5ea6\u5217\u8868")
    @GetMapping(value={"dim-list/org/{unit}"})
    public List<BaseDataVO> queryOrgDimList(@PathVariable(required=false) int unit) {
        List dims;
        this.logger.debug("\u83b7\u53d6\u7ec4\u7ec7\u673a\u6784\\\u7ef4\u5ea6\u5217\u8868 " + unit + " type");
        ArrayList entities = new ArrayList();
        List units = this.entityMetaService.getEntities(1);
        if (units != null) {
            entities.addAll(units);
        }
        if (0 == unit && (dims = this.entityMetaService.getEntities(0)) != null) {
            entities.addAll(dims);
        }
        if (entities.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<BaseDataVO> list = new ArrayList<BaseDataVO>();
        for (IEntityDefine e : entities) {
            list.add(new BaseDataVO(e.getId(), e.getCode(), e.getTitle(), e.getDesc()));
        }
        return list;
    }

    @ApiOperation(value="\u67e5\u8be2\u65f6\u671f\u4e3b\u4f53\u5217\u8868")
    @GetMapping(value={"dim-list/period"})
    public List<DataDimPeriodVO> queryPeriodDimList() {
        this.logger.debug("\u83b7\u53d6\u65f6\u671f\u7ef4\u5ea6\u5217\u8868 ");
        List periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity();
        ArrayList<DataDimPeriodVO> list = new ArrayList<DataDimPeriodVO>();
        for (IPeriodEntity p : periodEntity) {
            list.add(new DataDimPeriodVO(p.getKey(), p.getCode(), p.getTitle(), null, p.getPeriodType().type()));
        }
        return list;
    }

    public void addDataField(@RequestBody DimDataVO baseDataVO) throws JQException {
        DesignDataFieldDO dimField;
        String dimKey = baseDataVO.getKey();
        Integer precision = baseDataVO.getPrecision();
        String tableKey = baseDataVO.getTableKey();
        this.logger.debug("\u65b0\u589e\u7ef4\u5ea6\u6570\u636e\u6307\u6807\uff1a{}[{}]{}\u3002", dimKey, baseDataVO.getCode(), baseDataVO.getTitle());
        DesignDataTable dataTable = this.iDesignDataSchemeService.getDataTable(tableKey);
        Assert.notNull((Object)dataTable, "dataTable must not be null.");
        if (dataTable.getDataTableType() != DataTableType.DETAIL || dataTable.getDataTableType() != DataTableType.ACCOUNT) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_DF_1, "\u660e\u7ec6\u8868\u624d\u53ef\u4ee5\u6dfb\u52a0\u8868\u5185\u7ef4\u5ea6\u5b57\u6bb5");
        }
        if (this.dataSchemeAuthService.canWriteScheme(dataTable.getDataSchemeKey())) {
            throw new JQException((ErrorEnum)DataSchemeEnum.UNAUTHORIZED);
        }
        if (precision != null) {
            Assert.notNull((Object)baseDataVO.getCode(), "code must not be null.");
            Assert.notNull((Object)baseDataVO.getTitle(), "title must not be null.");
            dimField = Convert.dimFieldBuild(dataTable, baseDataVO.getCode(), baseDataVO.getTitle(), null, null, DataFieldKind.TABLE_FIELD_DIM, precision);
        } else {
            try {
                IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(dimKey);
                TableModelDefine tableModel = this.entityMetaService.getTableModel(iEntityDefine.getId());
                IEntityModel entityModel = this.entityMetaService.getEntityModel(iEntityDefine.getId());
                IEntityAttribute bizKeyField = entityModel.getBizKeyField();
                if (bizKeyField != null) {
                    precision = bizKeyField.getPrecision();
                }
                String code = iEntityDefine.getCode();
                DesignDataField field = this.iDesignDataSchemeService.getDataFieldByTableKeyAndCode(dataTable.getKey(), code);
                int sub = 0;
                String oldCode = code;
                while (field != null) {
                    code = oldCode + "_" + sub;
                    field = this.iDesignDataSchemeService.getDataFieldByTableKeyAndCode(dataTable.getKey(), code);
                    ++sub;
                }
                dimField = Convert.dimFieldBuild(dataTable, code, iEntityDefine.getTitle(), tableModel.getBizKeys(), dimKey, DataFieldKind.TABLE_FIELD_DIM, precision);
            }
            catch (Exception e) {
                this.logger.error(e.getMessage(), e);
                throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_DF_1, (Throwable)e);
            }
        }
        try {
            this.iDesignDataSchemeService.insertDataField((DesignDataField)dimField);
        }
        catch (SchemeDataException e) {
            this.logger.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME, e.getMessage());
        }
    }
}

