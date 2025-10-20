/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 */
package com.jiuqi.nr.common.paramcheck.internal.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.common.paramcheck.bean.CheckResult;
import com.jiuqi.nr.common.paramcheck.bean.ErrorParam;
import com.jiuqi.nr.common.paramcheck.bean.FixParam;
import com.jiuqi.nr.common.paramcheck.bean.ParamCheckVO;
import com.jiuqi.nr.common.paramcheck.common.CheckErrorEnum;
import com.jiuqi.nr.common.paramcheck.common.NrContextUtil;
import com.jiuqi.nr.common.paramcheck.internal.CheckManagerService;
import com.jiuqi.nr.common.paramcheck.service.ACheckService;
import com.jiuqi.nr.common.paramcheck.service.CheckParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class CheckManagerServiceImpl
implements CheckManagerService,
ApplicationListener<ContextRefreshedEvent> {
    private static final Map<String, CheckParam> checkAnnotations = new ConcurrentHashMap<String, CheckParam>();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(CheckParam.class);
            for (Object bean : beans.values()) {
                Class<?> clazz = bean.getClass();
                String superName = clazz.getSuperclass().getName();
                CheckParam annotation = clazz.getAnnotation(CheckParam.class);
                if (!"com.jiuqi.nr.common.paramcheck.service.ACheckService".equals(superName) || annotation == null) continue;
                checkAnnotations.put(annotation.name(), annotation);
            }
        }
    }

    @Override
    public ACheckService getParamCheckBeanByBeanName(String name) {
        CheckParam checkParam = checkAnnotations.get(name);
        if (checkParam != null) {
            return null;
        }
        return (ACheckService)NrContextUtil.getBean(checkParam.name());
    }

    @Override
    public List<ParamCheckVO> getAllParamCheckService() {
        ArrayList<ParamCheckVO> result = new ArrayList<ParamCheckVO>();
        checkAnnotations.forEach((bean, item) -> result.add(ParamCheckVO.buildItem(item)));
        return result;
    }

    @Override
    public CheckResult executeParamCheck(String bean) throws JQException {
        ACheckService acheckService = this.getClazz(bean);
        boolean execute = false;
        try {
            execute = acheckService.execute();
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)CheckErrorEnum.CHECK_EXECUTE_101, e.getMessage());
        }
        List<ErrorParam> errorData = acheckService.getErrorData();
        return new CheckResult(bean, execute, errorData);
    }

    @Override
    public CheckResult executeParamFix(FixParam fixParam) throws JQException {
        ACheckService acheckService = this.getClazz(fixParam.getBean());
        boolean execute = false;
        try {
            execute = acheckService.fix(fixParam.getObj());
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)CheckErrorEnum.FIX_CHECK_201, e.getMessage());
        }
        List<ErrorParam> errorData = acheckService.getErrorData();
        return new CheckResult(fixParam.getBean(), execute, errorData);
    }

    private ACheckService getClazz(String bean) throws JQException {
        CheckParam checkParam = checkAnnotations.get(bean);
        if (checkParam == null) {
            throw new JQException((ErrorEnum)CheckErrorEnum.BEAN_CHECK_201);
        }
        return (ACheckService)NrContextUtil.getBean(bean);
    }
}

