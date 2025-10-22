/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl.out;

public class CrudException
extends RuntimeException {
    private final int code;
    public static final int CRUD_4000 = 4000;
    public static final int CRUD_4001 = 4001;
    public static final int CRUD_4002 = 4002;
    public static final int CRUD_4101 = 4101;
    public static final int CRUD_4102 = 4102;
    public static final int CRUD_4111 = 4111;
    public static final int CRUD_4121 = 4121;
    public static final int CRUD_4122 = 4122;
    public static final int CRUD_4123 = 4123;
    public static final int CRUD_4124 = 4124;
    public static final int CRUD_4201 = 4201;
    public static final int CRUD_4301 = 4301;
    public static final int CRUD_4302 = 4302;
    public static final int CRUD_4501 = 4501;
    public static final int CRUD_4502 = 4502;
    public static final int CRUD_4503 = 4503;
    public static final int CRUD_4504 = 4504;
    public static final int CRUD_4505 = 4505;
    public static final int CRUD_4506 = 4506;
    public static final int CRUD_4507 = 4507;
    public static final int CRUD_4601 = 4601;

    public int getCode() {
        return this.code;
    }

    public CrudException(int code) {
        this.code = code;
    }

    public CrudException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CrudException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        if (message == null) {
            message = this.code + "";
        }
        return message;
    }
}

