/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.exception.NotSupportedException
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.nr.designer.web.rest;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.exception.NotSupportedException;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.designer.common.NrDesingerErrorEnum;
import com.jiuqi.nr.designer.service.IBussinessModelService;
import com.jiuqi.nr.designer.util.InitParamObjPropertyUtil;
import com.jiuqi.nr.designer.web.rest.resultBean.FieldMapping;
import com.jiuqi.nr.designer.web.rest.vo.LightFieldVo;
import com.jiuqi.nr.designer.web.treebean.FieldObject;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@JQRestController
@RequestMapping(value={"api/v1/designer/"})
@Api(tags={"\u5efa\u6a21\u8bbe\u8ba1"})
public class TableAndFieldController {
    private static final Logger log = LoggerFactory.getLogger(TableAndFieldController.class);
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private IBussinessModelService bussinessModelService;
    @Autowired
    private InitParamObjPropertyUtil initParamObjPropertyUtil;
    @Autowired
    private IEntityMetaService entityMetaService;

    @ApiOperation(value="\u5b58\u50a8\u8868\u662f\u5426\u662f\u6d6e\u52a8\u8868")
    @GetMapping(value={"/tabledefineisfloat/{tableKey}"})
    public boolean checkTableIsFloat(@PathVariable(value="tableKey") String tableKey) throws JQException {
        try {
            List designFieldDefines = this.nrDesignTimeController.getAllFieldsInTable(tableKey);
            Optional<DesignFieldDefine> optional = designFieldDefines.stream().filter(field -> field.getValueType() == FieldValueType.FIELD_VALUE_BIZKEY_ORDER).findFirst();
            return optional.isPresent();
        }
        catch (JQException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_136, (Throwable)e);
        }
    }

    @ApiOperation(value="\u83b7\u53d6\u5b58\u50a8\u8868\u5185\u975e\u4e1a\u52a1\u4e3b\u952e\u6307\u6807")
    @PostMapping(value={"/notbiz-fields-table/{tableid}"})
    public List<LightFieldVo> getNoBizFields(@PathVariable(value="tableid") String tableid) throws JQException {
        try {
            return this.bussinessModelService.getNoBizFields(tableid);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_001, (Throwable)e);
        }
    }

    @ApiOperation(value="\u6839\u636e\u5b58\u50a8\u8868Key\u67e5\u51fa\u94fe\u63a5\u9700\u8981\u7684\u76f8\u5173\u4fe1\u606f")
    @GetMapping(value={"/getLinkMessage_byTableKey/{tableKey}"})
    public Map<String, Object> getLinkMessageByTableKey(@PathVariable(value="tableKey") String tableKey) throws JQException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        IEntityModel entityModel = this.entityMetaService.getEntityModel(tableKey);
        IEntityAttribute nameField = entityModel.getNameField();
        ArrayList<String> cellList = new ArrayList<String>();
        cellList.add(nameField.getCode());
        if (cellList.size() != 0) {
            map.put("cellDictionaryTitleId", cellList.stream().collect(Collectors.joining(";")));
        } else {
            map.put("cellDictionaryTitleId", "");
        }
        IEntityAttribute codeField = entityModel.getCodeField();
        ArrayList<String> dropList = new ArrayList<String>();
        dropList.add(codeField.getCode());
        dropList.add(nameField.getCode());
        if (dropList.size() != 0) {
            map.put("dropDictionaryTitleId", dropList.stream().collect(Collectors.joining(";")));
        } else {
            map.put("dropDictionaryTitleId", "");
        }
        return map;
    }

    @ApiOperation(value="\u6839\u636e\u6307\u6807\u6620\u5c04\u683c\u5f0f\u67e5\u627e\u6307\u6807")
    @PostMapping(value={"fieldMap/queryfields"})
    public List<FieldMapping> queryfields(@RequestBody ArrayList<String> fieldCodes) throws JQException {
        ArrayList<FieldMapping> fieldMappings = new ArrayList<FieldMapping>();
        try {
            if (fieldCodes != null) {
                for (String fieldText : fieldCodes) {
                    DesignFieldDefine designFieldDefine;
                    ArrayList<FieldObject> fieldObjectList = new ArrayList<FieldObject>();
                    ArrayList<DesignFieldDefine> designFieldDefineList = new ArrayList<DesignFieldDefine>();
                    int startIdx = fieldText.indexOf("[");
                    int endIdx = fieldText.indexOf("]");
                    String fieldCode = fieldText.substring(startIdx + 1, endIdx);
                    if (startIdx == 0) {
                        throw new NotSupportedException("\u6839\u636e\u6307\u6807\u4ee3\u7801\u6620\u5c04\u67e5\u627e\u6307\u6807\u5c1a\u672a\u652f\u6301");
                    }
                    String tableCode = fieldText.substring(0, startIdx);
                    DesignTableDefine designTableDefine = this.nrDesignTimeController.queryTableDefinesByCode(tableCode);
                    if (designTableDefine != null && (designFieldDefine = this.nrDesignTimeController.queryFieldDefineByCodeInTable(fieldCode, designTableDefine.getKey())) != null) {
                        designFieldDefineList.add(designFieldDefine);
                    }
                    for (DesignFieldDefine designFieldDefine2 : designFieldDefineList) {
                        fieldObjectList.add(this.initParamObjPropertyUtil.setFieldObjectProperty((FieldDefine)designFieldDefine2));
                    }
                    FieldMapping fieldMapping = new FieldMapping(fieldText, fieldObjectList);
                    fieldMappings.add(fieldMapping);
                }
            }
            return fieldMappings;
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDesingerErrorEnum.NRDESINGER_EXCEPTION_150, (Throwable)e);
        }
    }
}

