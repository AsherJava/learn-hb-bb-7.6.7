/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.consolidatedsystem.common.TreeNode
 *  com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.FunctionEditorVO
 *  com.jiuqi.np.common.exception.JQException
 *  io.swagger.annotations.ApiParam
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.calculate.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.consolidatedsystem.common.TreeNode;
import com.jiuqi.gcreport.consolidatedsystem.vo.functionEditorVO.FunctionEditorVO;
import com.jiuqi.np.common.exception.JQException;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.consolidatedsystem.api.functionEditor.FunctionEditorClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface FunctionEditorClient {
    public static final String FUNCTIONEDIT_API_BASE_PATH = "/api/gcreport/v1/functionEditor";

    @GetMapping(value={"/api/gcreport/v1/functionEditor/FunctionTree/entity"})
    public BusinessResponseEntity<List<TreeNode>> initFunctionEntityTree();

    @GetMapping(value={"/api/gcreport/v1/functionEditor/FunctionTree/fields/{tableKey}"})
    public BusinessResponseEntity<List<TreeNode>> initFunctionFormTree(@PathVariable(value="tableKey") String var1);

    @PostMapping(value={"/api/gcreport/v1/functionEditor/Functions"})
    public BusinessResponseEntity<Object> addFunction(@RequestBody FunctionEditorVO var1);

    @GetMapping(value={"/api/gcreport/v1/functionEditor/Functions/{pageSize}/{pageNum}"})
    public BusinessResponseEntity<Object> getFunctions(@PathVariable(value="pageSize") Integer var1, @PathVariable(value="pageNum") Integer var2);

    @PostMapping(value={"/api/gcreport/v1/functionEditor/Functions/delete/{functionId}"})
    public BusinessResponseEntity<Object> deleteFunction(@PathVariable(value="functionId") String var1);

    @GetMapping(value={"/api/gcreport/v1/functionEditor/Functions/inherits/{notIncludeGc}", "/api/gcreport/v1/functionEditor/Functions/inherits"})
    public BusinessResponseEntity<Object> getInheritFunctionsTree(@PathVariable(value="notIncludeGc", required=false) Boolean var1);

    @GetMapping(value={"/api/gcreport/v1/functionEditor/Functions/formDefine"})
    public BusinessResponseEntity<Object> getBindTask();

    @PostMapping(value={"/api/gcreport/v1/functionEditor/Functions/formDefine/byTaskKeyList"})
    public BusinessResponseEntity<Object> getBindTaskByTaskKeyList(@RequestBody String[] var1);

    @GetMapping(value={"/api/gcreport/v1/functionEditor/Functions/formDefine/{schemeId}"})
    public BusinessResponseEntity<Object> getTaskSchemeByScheme(@PathVariable(value="schemeId") String var1);

    @PostMapping(value={"/api/gcreport/v1/functionEditor/Functions/check"})
    public BusinessResponseEntity<Object> checkFormula(@RequestBody String var1);

    @GetMapping(value={"/api/gcreport/v1/functionEditor/design_griddata/{formKey}"})
    public BusinessResponseEntity<Object> getDesignFormData(@ApiParam(value="\u62a5\u8868key", required=true) @PathVariable String var1) throws JQException;

    @GetMapping(value={"/api/gcreport/v1/functionEditor/Functions/formDefine/dataScheme/{dataSchemeId}"})
    public BusinessResponseEntity<Object> getBindTaskByDataScheme(@PathVariable(value="dataSchemeId") String var1);
}

