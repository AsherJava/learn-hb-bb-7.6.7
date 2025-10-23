/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.common.spring.web.rest.JQRestController
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestMethod
 */
package com.jiuqi.nr.formulaeditor.web;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.common.spring.web.rest.JQRestController;
import com.jiuqi.nr.formulaeditor.common.FormulaEditorException;
import com.jiuqi.nr.formulaeditor.dto.EditObject;
import com.jiuqi.nr.formulaeditor.dto.EditorFormula;
import com.jiuqi.nr.formulaeditor.dto.EditorSelect;
import com.jiuqi.nr.formulaeditor.dto.EditorTree;
import com.jiuqi.nr.formulaeditor.service.EnumQueryService;
import com.jiuqi.nr.formulaeditor.service.FormulaEditorService;
import com.jiuqi.nr.formulaeditor.vo.EditorNodeData;
import com.jiuqi.nr.formulaeditor.vo.EntityData;
import com.jiuqi.nr.formulaeditor.vo.EntityValueData;
import com.jiuqi.nr.formulaeditor.vo.EnumQueryParam;
import com.jiuqi.nr.formulaeditor.vo.FieldData;
import com.jiuqi.nr.formulaeditor.vo.FormData;
import com.jiuqi.nr.formulaeditor.vo.FormulaCheckData;
import com.jiuqi.nr.formulaeditor.vo.FormulaVariableData;
import com.jiuqi.nr.formulaeditor.vo.FunctionList;
import com.jiuqi.nr.formulaeditor.vo.ITree;
import com.jiuqi.nr.formulaeditor.vo.SchemeData;
import com.jiuqi.nr.formulaeditor.vo.TaskParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@JQRestController
@RequestMapping(value={"api/v1/definition/formulaeditor"})
@Api(tags={"\u516c\u5f0f\u7f16\u8f91\u5668\u63a5\u53e3"})
public class FormulaEditorController {
    @Autowired
    FormulaEditorService formulaEditorService;
    @Autowired
    private EnumQueryService entityQueryService;

    @ApiOperation(value="\u67e5\u8be2\u4efb\u52a1\u6570\u636e")
    @RequestMapping(value={"queryTaskData"}, method={RequestMethod.POST})
    public TaskParam queryTaskData(@RequestBody EditObject editObject) throws JQException {
        if (null == editObject) {
            throw new JQException((ErrorEnum)FormulaEditorException.EDITOR_EXCEPTION_001);
        }
        return this.formulaEditorService.queryTaskData(editObject);
    }

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u65b9\u6848\u6570\u636e")
    @RequestMapping(value={"querySchemeData"}, method={RequestMethod.POST})
    public List<SchemeData> querySchemeData(@RequestBody EditorSelect editorSelect) throws JQException {
        if (null == editorSelect || null == editorSelect.getEditObject() || null == editorSelect.getParamObj()) {
            throw new JQException((ErrorEnum)FormulaEditorException.EDITOR_EXCEPTION_001);
        }
        return this.formulaEditorService.querySchemeData(editorSelect.getEditObject(), editorSelect.getParamObj());
    }

    @ApiOperation(value="\u67e5\u8be2\u62a5\u8868\u6811\u5f62\u6570\u636e")
    @RequestMapping(value={"queryFormData"}, method={RequestMethod.POST})
    public List<ITree<EditorNodeData>> queryFormData(@RequestBody EditorSelect editorSelect) throws JQException {
        if (null == editorSelect || null == editorSelect.getEditObject() || null == editorSelect.getParamObj()) {
            throw new JQException((ErrorEnum)FormulaEditorException.EDITOR_EXCEPTION_001);
        }
        return this.formulaEditorService.queryFormData(editorSelect.getEditObject(), editorSelect.getParamObj());
    }

    @ApiOperation(value="\u641c\u7d22\u62a5\u8868\u6570\u636e")
    @RequestMapping(value={"searchForm"}, method={RequestMethod.POST})
    public List<ITree<EditorNodeData>> searchForm(@RequestBody EditorSelect editorSelect) throws JQException {
        if (null == editorSelect || null == editorSelect.getEditObject() || null == editorSelect.getParamObj()) {
            throw new JQException((ErrorEnum)FormulaEditorException.EDITOR_EXCEPTION_001);
        }
        return this.formulaEditorService.searchFormData(editorSelect.getEditObject(), editorSelect.getParamObj());
    }

    @ApiOperation(value="\u67e5\u8be2\u8868\u6837\u5c55\u793a\u6570\u636e")
    @RequestMapping(value={"queryStyleData"}, method={RequestMethod.POST})
    public FormData queryStyleData(@RequestBody EditorSelect editorSelect) throws JQException {
        if (null == editorSelect || null == editorSelect.getEditObject() || null == editorSelect.getParamObj()) {
            throw new JQException((ErrorEnum)FormulaEditorException.EDITOR_EXCEPTION_001);
        }
        return this.formulaEditorService.queryStyleData(editorSelect.getEditObject(), editorSelect.getParamObj());
    }

    @ApiOperation(value="\u6307\u6807\u6811\u5f62\u521d\u59cb\u5316\u6570\u636e")
    @RequestMapping(value={"initTreeData"}, method={RequestMethod.POST})
    public List<ITree<EditorNodeData>> initTreeData(@RequestBody EditorSelect editorSelect) throws JQException {
        if (null == editorSelect || null == editorSelect.getEditObject() || null == editorSelect.getParamObj()) {
            throw new JQException((ErrorEnum)FormulaEditorException.EDITOR_EXCEPTION_001);
        }
        return this.formulaEditorService.initTreeData(editorSelect.getEditObject(), editorSelect.getParamObj());
    }

    @ApiOperation(value="\u6307\u6807\u6811\u5f62\u5206\u7ec4\u8282\u70b9\u6570\u636e")
    @RequestMapping(value={"queryTreeGroupData"}, method={RequestMethod.POST})
    public List<ITree<EditorNodeData>> queryTreeGroupData(@RequestBody EditorTree editorTree) throws JQException {
        if (null == editorTree || null == editorTree.getEditObject() || null == editorTree.getTreeObj()) {
            throw new JQException((ErrorEnum)FormulaEditorException.EDITOR_EXCEPTION_001);
        }
        return this.formulaEditorService.queryTreeGroupData(editorTree.getEditObject(), editorTree.getTreeObj());
    }

    @ApiOperation(value="\u6307\u6807\u5217\u8868\u6570\u636e")
    @RequestMapping(value={"queryFieldData"}, method={RequestMethod.POST})
    public List<FieldData> queryFieldData(@RequestBody EditorTree editorTree) throws JQException {
        if (null == editorTree || null == editorTree.getEditObject()) {
            throw new JQException((ErrorEnum)FormulaEditorException.EDITOR_EXCEPTION_001);
        }
        if (null == editorTree.getTreeObj()) {
            return Collections.emptyList();
        }
        return this.formulaEditorService.queryFieldData(editorTree.getEditObject(), editorTree.getTreeObj());
    }

    @ApiOperation(value="\u516c\u5f0f\u6821\u9a8c")
    @RequestMapping(value={"formulaCheck"}, method={RequestMethod.POST})
    public List<FormulaCheckData> formulaCheck(@RequestBody EditorFormula editorFormula) throws JQException {
        if (null == editorFormula || null == editorFormula.getEditObject() || null == editorFormula.getFormulaObj()) {
            throw new JQException((ErrorEnum)FormulaEditorException.EDITOR_EXCEPTION_001);
        }
        return this.formulaEditorService.formulaCheck(editorFormula.getEditObject(), editorFormula.getFormulaObj());
    }

    @ApiOperation(value="\u51fd\u6570\u5217\u8868\u6570\u636e")
    @RequestMapping(value={"queryFunctionData"}, method={RequestMethod.POST})
    public List<FunctionList> queryFunctionData(@RequestBody EditObject editObject) throws JQException {
        if (null == editObject) {
            throw new JQException((ErrorEnum)FormulaEditorException.EDITOR_EXCEPTION_001);
        }
        return this.formulaEditorService.queryFunctionData(editObject);
    }

    @ApiOperation(value="\u679a\u4e3e\u5217\u8868\u6570\u636e")
    @PostMapping(value={"queryEnumData"})
    public List<EntityValueData> queryEnumData(@RequestBody EnumQueryParam queryParam) {
        return this.entityQueryService.queryEnumData(queryParam);
    }

    @ApiOperation(value="\u679a\u4e3e\u5217\u8868\u5b9a\u4e49")
    @RequestMapping(value={"queryEnumDefine"}, method={RequestMethod.POST})
    public List<EntityData> queryEnumDefine(@RequestBody EditorSelect editorSelect) throws JQException {
        if (null == editorSelect || null == editorSelect.getEditObject() || null == editorSelect.getParamObj()) {
            throw new JQException((ErrorEnum)FormulaEditorException.EDITOR_EXCEPTION_001);
        }
        return this.formulaEditorService.queryEnumDefine(editorSelect.getEditObject(), editorSelect.getParamObj());
    }

    @ApiOperation(value="\u53d8\u91cf\u6570\u636e")
    @RequestMapping(value={"queryFormulaValiData"}, method={RequestMethod.POST})
    public List<FormulaVariableData> queryFormulaValiData(@RequestBody EditorSelect editorSelect) throws JQException {
        if (null == editorSelect || null == editorSelect.getEditObject() || null == editorSelect.getParamObj()) {
            throw new JQException((ErrorEnum)FormulaEditorException.EDITOR_EXCEPTION_001);
        }
        return this.formulaEditorService.queryFormulaValiData(editorSelect.getEditObject(), editorSelect.getParamObj());
    }
}

