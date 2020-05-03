package com.mao.logs.common;

/**
 * @author bigdope
 * @create 2019-06-21
 **/
public class RestResponse {

    private int resultCode;
    private String resultMsg;
    private Object data;
    public static final RestResponse SUCCESS = new RestResponse();
    public static final RestResponse FAIL = new RestResponse(-1, "fail");

    public RestResponse() {
        this(null);
    }

    public RestResponse(Object data) {
        this.resultCode = 0;
        this.resultMsg = "success";
        this.data = data;
    }

    public RestResponse(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public RestResponse(int resultCode, String resultMsg, Object data) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.data = data;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getResultCode() {
        return this.resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return this.resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String toString() {
        return "RestResponse [resultCode=" + this.resultCode + ", resultMsg=" + this.resultMsg + ", data=" + this.data + "]";
    }

}
