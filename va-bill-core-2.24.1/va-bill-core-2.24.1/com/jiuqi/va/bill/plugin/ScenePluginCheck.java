/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.va.biz.domain.PluginCheckResultDTO
 *  com.jiuqi.va.biz.domain.PluginCheckResultVO
 *  com.jiuqi.va.biz.domain.PluginCheckType
 *  com.jiuqi.va.biz.impl.model.ModelDataContext
 *  com.jiuqi.va.biz.intf.data.DataDefine
 *  com.jiuqi.va.biz.intf.data.DataTableDefine
 *  com.jiuqi.va.biz.intf.model.ModelDefine
 *  com.jiuqi.va.biz.intf.model.PluginCheck
 *  com.jiuqi.va.biz.intf.model.PluginDefine
 *  com.jiuqi.va.biz.ruler.ModelFormulaHandle
 *  com.jiuqi.va.biz.ruler.common.consts.FormulaType
 *  com.jiuqi.va.biz.scene.impl.SceneDefineImpl
 *  com.jiuqi.va.biz.scene.impl.SceneEditField
 *  com.jiuqi.va.biz.scene.impl.SceneEditPropImpl
 *  com.jiuqi.va.biz.scene.impl.SceneEditTable
 *  com.jiuqi.va.biz.scene.intf.SceneDesign
 *  com.jiuqi.va.biz.scene.intf.SceneEditProp
 *  com.jiuqi.va.biz.scene.intf.SceneItem
 */
package com.jiuqi.va.bill.plugin;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.va.bill.utils.SuperEditCondition;
import com.jiuqi.va.biz.domain.PluginCheckResultDTO;
import com.jiuqi.va.biz.domain.PluginCheckResultVO;
import com.jiuqi.va.biz.domain.PluginCheckType;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.intf.data.DataDefine;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginCheck;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.ruler.ModelFormulaHandle;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.scene.impl.SceneDefineImpl;
import com.jiuqi.va.biz.scene.impl.SceneEditField;
import com.jiuqi.va.biz.scene.impl.SceneEditPropImpl;
import com.jiuqi.va.biz.scene.impl.SceneEditTable;
import com.jiuqi.va.biz.scene.intf.SceneDesign;
import com.jiuqi.va.biz.scene.intf.SceneEditProp;
import com.jiuqi.va.biz.scene.intf.SceneItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@Conditional(value={SuperEditCondition.class})
public class ScenePluginCheck
implements PluginCheck {
    public String getName() {
        return "scene";
    }

    public Class<? extends PluginDefine> getPluginDefine() {
        return SceneDefineImpl.class;
    }

    public PluginCheckResultVO checkPlugin(PluginDefine pluginDefine, ModelDefine modelDefine) {
        PluginCheckResultVO pluginCheckResultVO = new PluginCheckResultVO();
        pluginCheckResultVO.setPluginName(this.getName());
        ArrayList<PluginCheckResultDTO> checkResults = new ArrayList<PluginCheckResultDTO>();
        pluginCheckResultVO.setCheckResults(checkResults);
        SceneDefineImpl sceneDefine = (SceneDefineImpl)pluginDefine;
        List scenes = sceneDefine.getScenes();
        if (CollectionUtils.isEmpty(scenes)) {
            return pluginCheckResultVO;
        }
        DataDefine data = (DataDefine)modelDefine.getPlugins().get("data");
        String masterTableName = ((DataTableDefine)data.getTables().getMasterTable()).getName();
        for (SceneItem scene : scenes) {
            List editProps;
            SceneDesign design = scene.getDesign();
            if (design == null || CollectionUtils.isEmpty(editProps = design.getEditProps())) continue;
            ModelDataContext context = new ModelDataContext(modelDefine);
            ModelFormulaHandle handle = ModelFormulaHandle.getInstance();
            int edFieldIndex = 0;
            int editTableIndex = 0;
            block3: for (SceneEditProp editProp : editProps) {
                IExpression expression;
                if (!editProp.isEnable()) continue;
                String type = editProp.getType();
                try {
                    expression = handle.parse(context, editProp.getExpression(), FormulaType.EVALUATE);
                }
                catch (Exception e) {
                    PluginCheckResultDTO checkResultDTO = new PluginCheckResultDTO();
                    checkResultDTO.setType(PluginCheckType.ERROR);
                    if (type.equals("field")) {
                        ++edFieldIndex;
                        checkResultDTO.setMessage(String.format("\u89c4\u5219\u573a\u666f\u3010%s\u3011_\u53ef\u7f16\u8f91\u5b57\u6bb5\u7b2c%s\u884c\uff1a\u516c\u5f0f\u3010%s\u3011\u7f16\u8bd1\u5f02\u5e38", scene.getName(), editTableIndex, editProp.getExpression()));
                    } else if (type.equals("table")) {
                        checkResultDTO.setMessage(String.format("\u89c4\u5219\u573a\u666f\u3010%s\u3011_\u53ef\u7f16\u8f91\u5b50\u8868\u7b2c%s\u884c\uff1a\u516c\u5f0f\u3010%s\u3011\u7f16\u8bd1\u5f02\u5e38", scene.getName(), ++editTableIndex, editProp.getExpression()));
                    } else {
                        checkResultDTO.setMessage(String.format("\u89c4\u5219\u573a\u666f\u3010%s\u3011\uff1a\u516c\u5f0f\u3010%s\u3011\u7f16\u8bd1\u5f02\u5e38", scene.getName(), editProp.getExpression()));
                    }
                    checkResults.add(checkResultDTO);
                    continue;
                }
                SceneEditPropImpl editPropImpl = (SceneEditPropImpl)editProp;
                editPropImpl.setCompileExpression(expression);
                String propTable = editPropImpl.computePropTable(modelDefine);
                editPropImpl.setCompileExpression(null);
                if (type.equals("field")) {
                    ++edFieldIndex;
                    if (masterTableName.equals(propTable)) continue;
                    SceneEditField sceneEditField = (SceneEditField)editProp;
                    List fields = sceneEditField.getFields();
                    for (Map tableField : fields) {
                        String tableName = String.valueOf(tableField.get("tableName"));
                        if (propTable.equals(tableName)) continue;
                        PluginCheckResultDTO checkResultDTO = new PluginCheckResultDTO();
                        checkResultDTO.setType(PluginCheckType.ERROR);
                        checkResultDTO.setMessage(String.format("\u89c4\u5219\u573a\u666f\u3010%s\u3011_\u53ef\u7f16\u8f91\u5b57\u6bb5\u7b2c%s\u884c\uff1a\u5b57\u6bb5\u9009\u62e9\u9519\u8bef\uff0c\u53ea\u80fd\u9009\u62e9%s\u8868\u4e2d\u7684\u5b57\u6bb5", scene.getName(), edFieldIndex, propTable));
                        checkResults.add(checkResultDTO);
                        continue block3;
                    }
                    continue;
                }
                if (!type.equals("table")) continue;
                ++editTableIndex;
                SceneEditTable sceneEditTable = (SceneEditTable)editProp;
                List editTables = sceneEditTable.getTables();
                if (masterTableName.equals(propTable)) continue;
                PluginCheckResultDTO checkResultDTO = new PluginCheckResultDTO();
                checkResultDTO.setType(PluginCheckType.ERROR);
                checkResultDTO.setMessage(String.format("\u89c4\u5219\u573a\u666f\u3010%s\u3011_\u53ef\u7f16\u8f91\u5b50\u8868\u7b2c%s\u884c\uff1a\u516c\u5f0f\u914d\u7f6e\u9519\u8bef\uff0c\u65e0\u6cd5\u786e\u5b9a\u5f53\u524d\u884c\uff08\u516c\u5f0f\u9a71\u52a8\u8868\u4e3a\u5b50\u8868\uff09", scene.getName(), editTableIndex));
                checkResults.add(checkResultDTO);
            }
        }
        return pluginCheckResultVO;
    }
}

