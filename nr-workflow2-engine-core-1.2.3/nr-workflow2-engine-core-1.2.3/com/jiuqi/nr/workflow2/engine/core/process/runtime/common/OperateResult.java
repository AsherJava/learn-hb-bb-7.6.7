/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime.common;

import com.jiuqi.nr.workflow2.engine.core.exception.Error;
import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;

public class OperateResult {
    public static IOperateResult<Void> newSuccessResult() {
        return SuccessWithoutData.INSTANCE;
    }

    public static <T> IOperateResult<T> newSuccessResult(T resultData) {
        return new Success(resultData);
    }

    public static <T> IOperateResult<T> newFailResult(ErrorCode errorCode) {
        return new Fail(errorCode);
    }

    public static <T> IOperateResult<T> newFailResult(Error error) {
        return new Fail(error);
    }

    private static class Fail<T>
    implements IOperateResult<T> {
        private final Error error;

        private Fail(ErrorCode errorCode) {
            this.error = Error.fromErrorCode(errorCode);
        }

        private Fail(Error error) {
            this.error = error;
        }

        @Override
        public boolean isSuccessful() {
            return false;
        }

        @Override
        public T getResult() {
            return null;
        }

        @Override
        public Error getError() {
            return this.error;
        }
    }

    private static class Success<T>
    implements IOperateResult<T> {
        private final T resultData;

        private Success(T resultData) {
            this.resultData = resultData;
        }

        @Override
        public boolean isSuccessful() {
            return true;
        }

        @Override
        public T getResult() {
            return this.resultData;
        }

        @Override
        public Error getError() {
            return Error.NOERROR;
        }
    }

    private static class SuccessWithoutData
    implements IOperateResult<Void> {
        static final SuccessWithoutData INSTANCE = new SuccessWithoutData();

        private SuccessWithoutData() {
        }

        @Override
        public boolean isSuccessful() {
            return true;
        }

        @Override
        public Void getResult() {
            return null;
        }

        @Override
        public Error getError() {
            return Error.NOERROR;
        }
    }
}

