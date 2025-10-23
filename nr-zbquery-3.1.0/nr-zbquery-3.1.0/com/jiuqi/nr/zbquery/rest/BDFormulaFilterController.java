/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.bql.interpret.BiAdaptFormulaParser
 *  com.jiuqi.nr.bql.interpret.BiAdaptParam
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nvwa.definition.common.ColumnModelKind
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.zbquery.rest;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bql.interpret.BiAdaptFormulaParser;
import com.jiuqi.nr.bql.interpret.BiAdaptParam;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.zbquery.rest.vo.AdaptFormulaValidVo;
import com.jiuqi.nvwa.definition.common.ColumnModelKind;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"/api/zbquery/design/fx"})
public class BDFormulaFilterController {
    private final Logger logger = LoggerFactory.getLogger(BDFormulaFilterController.class);
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private BiAdaptFormulaParser biAdaptFormulaParser;

    @GetMapping(value={"/bdFields/{bdEntityCode}"})
    public List<BaseDataFields> getBDFields(@PathVariable String bdEntityCode) throws Exception {
        IEntityTable entityTable = this.getEntityTable(bdEntityCode);
        if (entityTable == null) {
            return null;
        }
        return this.getBaseDataFields(entityTable);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @PostMapping(value={"/check"})
    public AdaptFormulaValidVo checkFormula(@RequestBody String formula) {
        AdaptFormulaValidVo result = null;
        BiAdaptParam param = new BiAdaptParam();
        try {
            formula = formula.replaceAll("^\"|\"$", "");
            boolean valid = this.biAdaptFormulaParser.checkFormula(param, formula);
            int resultType = this.biAdaptFormulaParser.getDataType(param, formula);
            if (valid) {
                result = AdaptFormulaValidVo.success(resultType);
            }
            result = AdaptFormulaValidVo.error("\u516c\u5f0f\u6821\u9a8c\u5931\u8d25\uff01");
            return result;
        }
        catch (ParseException e) {
            this.logger.error(e.getMessage(), e);
            result = AdaptFormulaValidVo.error(e.getMessage());
            return result;
        }
        finally {
            return result;
        }
    }

    private IEntityTable getEntityTable(String entityId) throws Exception {
        EntityViewDefine entityViewDefine = this.iEntityViewRunTimeController.buildEntityView(entityId);
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setAuthorityOperations(AuthorityType.Read);
        iEntityQuery.setMasterKeys(DimensionValueSet.EMPTY);
        iEntityQuery.setEntityView(entityViewDefine);
        iEntityQuery.sorted(true);
        return iEntityQuery.executeFullBuild(null);
    }

    private List<BaseDataFields> getBaseDataFields(IEntityTable entityTable) {
        ArrayList<BaseDataFields> result = new ArrayList<BaseDataFields>();
        Iterator attributes = entityTable.getEntityModel().getAttributes();
        while (attributes.hasNext()) {
            IEntityAttribute entityAttribute = (IEntityAttribute)attributes.next();
            String fieldName = entityAttribute.getCode();
            boolean isReturn = entityAttribute.getOrder() > 16.0 || entityAttribute.getKind() == ColumnModelKind.SYSTEM && ("CODE".equals(fieldName) || "NAME".equals(fieldName) || "ORDER".equals(fieldName) || "PARENTCODE".equals(fieldName));
            if (!isReturn) continue;
            result.add(new BaseDataFields(entityAttribute.getCode(), entityAttribute.getTitle(), entityTable.getEntityModel().getEntityId()));
        }
        return result;
    }

    class BaseDataFields {
        String code;
        String title;
        String tableCode;

        BaseDataFields() {
        }

        BaseDataFields(String code, String title, String tableCode) {
            this.code = code;
            this.title = title;
            this.tableCode = tableCode;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTableCode() {
            return this.tableCode;
        }

        public void setTableCode(String tableCode) {
            this.tableCode = tableCode;
        }
    }
}

