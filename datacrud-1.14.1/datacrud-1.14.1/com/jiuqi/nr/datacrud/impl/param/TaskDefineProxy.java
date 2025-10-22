/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.fmdm.IFMDMAttribute
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.nr.datacrud.impl.param;

import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.springframework.util.StringUtils;

public class TaskDefineProxy {
    public static TaskDefine createTaskDefineProxy(String contextEntityId, TaskDefine taskDefine) {
        InvocationHandler handler = (proxy, method, args) -> {
            String methodName = method.getName();
            if ("getDw".equals(methodName)) {
                return contextEntityId;
            }
            return method.invoke(taskDefine, args);
        };
        return (TaskDefine)Proxy.newProxyInstance(TaskDefine.class.getClassLoader(), new Class[]{TaskDefine.class}, handler);
    }

    public static DataField createDataFieldProxy(IFMDMAttribute attribute) {
        return (DataField)Proxy.newProxyInstance(DataField.class.getClassLoader(), new Class[]{DataField.class}, (InvocationHandler)new IFMDMAttributeInvocationHandler(attribute));
    }

    private static class IFMDMAttributeInvocationHandler
    implements InvocationHandler {
        private final IFMDMAttribute attribute;

        public IFMDMAttributeInvocationHandler(IFMDMAttribute attribute) {
            this.attribute = attribute;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName;
            switch (methodName = method.getName()) {
                case "getDataTableKey": {
                    return this.attribute.getTableID();
                }
                case "getNullable": 
                case "isNullable": {
                    return this.attribute.isNullAble();
                }
                case "getRefDataEntityKey": {
                    return this.attribute.getReferEntityId();
                }
                case "getAllowMultipleSelect": 
                case "isAllowMultipleSelect": {
                    return this.attribute.isMultival();
                }
                case "getKey": {
                    return this.attribute.getID();
                }
                case "getDataFieldType": {
                    ColumnModelType columnType = this.attribute.getColumnType();
                    return DataFieldType.valueOf((int)columnType.getValue());
                }
                case "getFormatProperties": {
                    String showFormat = this.attribute.getShowFormat();
                    if (StringUtils.hasLength(showFormat)) {
                        FormatProperties formatProperties = new FormatProperties();
                        formatProperties.setPattern(showFormat);
                        return formatProperties;
                    }
                    return null;
                }
            }
            try {
                Method targetMethod = this.attribute.getClass().getMethod(methodName, method.getParameterTypes());
                if (this.isCompatibleReturnType(method.getReturnType(), targetMethod.getReturnType())) {
                    return targetMethod.invoke(this.attribute, args);
                }
                return this.getDefaultReturnValue(method.getReturnType());
            }
            catch (NoSuchMethodException e) {
                return this.getDefaultReturnValue(method.getReturnType());
            }
        }

        private boolean isCompatibleReturnType(Class<?> methodReturnType, Class<?> targetReturnType) {
            if (targetReturnType.isPrimitive()) {
                return targetReturnType == Integer.TYPE && methodReturnType == Integer.class || targetReturnType == Long.TYPE && methodReturnType == Long.class || targetReturnType == Boolean.TYPE && methodReturnType == Boolean.class || targetReturnType == Double.TYPE && methodReturnType == Double.class || targetReturnType == Float.TYPE && methodReturnType == Float.class || targetReturnType == Character.TYPE && methodReturnType == Character.class || targetReturnType == Byte.TYPE && methodReturnType == Byte.class || targetReturnType == Short.TYPE && methodReturnType == Short.class;
            }
            return methodReturnType.isAssignableFrom(targetReturnType);
        }

        private Object getDefaultReturnValue(Class<?> returnType) {
            if (returnType.isPrimitive()) {
                if (returnType == Boolean.TYPE) {
                    return false;
                }
                if (returnType == Integer.TYPE) {
                    return 0;
                }
                if (returnType == Long.TYPE) {
                    return 0L;
                }
                if (returnType == Float.TYPE) {
                    return Float.valueOf(0.0f);
                }
                if (returnType == Double.TYPE) {
                    return 0.0;
                }
                if (returnType == Character.TYPE) {
                    return Character.valueOf('\u0000');
                }
                if (returnType == Byte.TYPE) {
                    return (byte)0;
                }
                if (returnType == Short.TYPE) {
                    return (short)0;
                }
            }
            return null;
        }
    }
}

