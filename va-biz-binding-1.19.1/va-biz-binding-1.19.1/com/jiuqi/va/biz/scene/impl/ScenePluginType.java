/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.ParseException
 */
package com.jiuqi.va.biz.scene.impl;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.va.biz.impl.model.ModelDataContext;
import com.jiuqi.va.biz.impl.model.PluginDefineImpl;
import com.jiuqi.va.biz.impl.model.PluginTypeBase;
import com.jiuqi.va.biz.intf.model.Model;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.ModelException;
import com.jiuqi.va.biz.intf.model.Plugin;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import com.jiuqi.va.biz.ruler.ModelFormulaHandle;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import com.jiuqi.va.biz.scene.impl.SceneDefineImpl;
import com.jiuqi.va.biz.scene.impl.SceneEditPropImpl;
import com.jiuqi.va.biz.scene.impl.SceneImpl;
import com.jiuqi.va.biz.scene.intf.SceneDesign;
import com.jiuqi.va.biz.scene.intf.SceneEditProp;
import com.jiuqi.va.biz.scene.intf.SceneItem;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public abstract class ScenePluginType
extends PluginTypeBase {
    private static final Logger log = LoggerFactory.getLogger(ScenePluginType.class);
    public static final String NAME = "scene";
    public static final String TITLE = "\u89c4\u5219\u573a\u666f";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public Class<? extends PluginDefineImpl> getPluginDefineClass() {
        return SceneDefineImpl.class;
    }

    public Class<? extends SceneImpl> getPluginClass() {
        return SceneImpl.class;
    }

    @Override
    public void initPlugin(Plugin plugin, PluginDefine pluginDefine, Model model) {
        super.initPlugin(plugin, pluginDefine, model);
    }

    @Override
    public String[] getDependPlugins() {
        return new String[]{"data"};
    }

    @Override
    public void initPluginDefine(PluginDefine pluginDefine, ModelDefine modelDefine) {
        super.initPluginDefine(pluginDefine, modelDefine);
        SceneDefineImpl sceneDefine = (SceneDefineImpl)pluginDefine;
        List<? extends SceneItem> scenes = sceneDefine.getScenes();
        if (CollectionUtils.isEmpty(scenes)) {
            return;
        }
        for (SceneItem sceneItem : scenes) {
            List<? extends SceneEditProp> editProps;
            SceneDesign design = sceneItem.getDesign();
            if (design == null || CollectionUtils.isEmpty(editProps = design.getEditProps())) continue;
            ModelDataContext context = new ModelDataContext(modelDefine);
            ModelFormulaHandle handle = ModelFormulaHandle.getInstance();
            for (SceneEditProp sceneEditProp : editProps) {
                IExpression expression;
                if (!sceneEditProp.isEnable()) continue;
                try {
                    expression = handle.parse(context, sceneEditProp.getExpression(), FormulaType.EVALUATE);
                }
                catch (ParseException e) {
                    log.error(e.getMessage(), e);
                    throw new ModelException("\u89c4\u5219\u573a\u666f\u516c\u5f0f\u7f16\u8bd1\u5f02\u5e38" + sceneEditProp.getExpression(), e);
                }
                catch (Exception e) {
                    log.error(e.getMessage(), e);
                    throw new ModelException("\u89c4\u5219\u573a\u666f\u516c\u5f0f\u7f16\u8bd1\u5f02\u5e38" + sceneEditProp.getExpression(), e);
                }
                SceneEditPropImpl editPropImpl = (SceneEditPropImpl)sceneEditProp;
                editPropImpl.setCompileExpression(expression);
                String propTable = editPropImpl.computePropTable(modelDefine);
                editPropImpl.setPropTable(propTable);
            }
        }
    }
}

