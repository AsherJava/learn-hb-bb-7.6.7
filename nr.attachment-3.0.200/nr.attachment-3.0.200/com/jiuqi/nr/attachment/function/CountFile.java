/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.IFunction
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.attachment.function;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.IFunction;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFunctionProvider;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import com.jiuqi.nr.attachment.message.FileInfo;
import com.jiuqi.nr.attachment.service.FilePoolService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@Lazy(value=false)
public class CountFile
extends Function
implements InitializingBean {
    private static final long serialVersionUID = -1895838744985695844L;
    private static final CountFile function = new CountFile();

    public CountFile() {
        this.parameters().add(new Parameter("fileGroup", 0, "\u9644\u4ef6\u5b58\u50a8\u5206\u7ec4"));
    }

    public static CountFile getInstance() {
        return function;
    }

    public String name() {
        return "CountFile";
    }

    public String title() {
        return "\u9644\u4ef6\u6570\u91cf\u7edf\u8ba1";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        int fileCount = 0;
        Object obj = parameters.get(0).evaluate(context);
        if (null != obj) {
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
            IFmlExecEnvironment fmlExecEnvironment = ((QueryContext)context).getExeContext().getEnv();
            ReportFmlExecEnvironment reportFmlExecEnvironment = (ReportFmlExecEnvironment)fmlExecEnvironment;
            String dataSchemeKey = reportFmlExecEnvironment.getDataScehmeKey();
            if (StringUtils.isEmpty((String)dataSchemeKey)) {
                FormSchemeDefine formScheme = runTimeViewController.getFormScheme(reportFmlExecEnvironment.getFormSchemeKey());
                TaskDefine taskDefine = runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
                dataSchemeKey = taskDefine.getDataScheme();
            }
            FilePoolService filePoolService = (FilePoolService)BeanUtil.getBean(FilePoolService.class);
            CommonParamsDTO params = new CommonParamsDTO();
            params.setDataSchemeKey(dataSchemeKey);
            params.setTaskKey(reportFmlExecEnvironment.getTaskDefine().getKey());
            List<FileInfo> fileInfoByGroup = filePoolService.getFileInfoByGroup(obj.toString(), params);
            fileCount = null == fileInfoByGroup || CollectionUtils.isEmpty(fileInfoByGroup) ? 0 : fileInfoByGroup.size();
        }
        return fileCount;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ReportFunctionProvider.GLOBAL_PROVIDER.add((IFunction)function);
    }
}

