package edu.hitwh.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result {
    private int code;
    private String msg;
    private Object data;

    public Result(int code, Object data, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    static public Result ok(){
        return new Result(1,null,"");
    }
    static public Result ok(Object data){
        return new Result(1,data,"");
    }
    static public Result fail(String msg){
        return new Result(0,null,msg);
    }
}
