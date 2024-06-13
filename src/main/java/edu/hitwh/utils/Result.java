package edu.hitwh.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result {
    private int code;
    private String msg;
    private Object data;
    public static final int OK = 0;
    public static final int FAIL = 1;
    public static final int UNLOGIN = 2;
    public Result(int code, Object data, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    static public Result ok(){
        return new Result(OK,null,"success");
    }
    static public Result ok(Object data){
        return new Result(OK,data,"success");
    }
    static public Result okWithMessage(String message) {
        return new Result(OK, null, message);
    }
    static public Result fail(String msg){
        return new Result(FAIL,null,msg);
    }
    static public Result unLogin(String msg){
        return new Result(UNLOGIN,null,msg);
    }
}
