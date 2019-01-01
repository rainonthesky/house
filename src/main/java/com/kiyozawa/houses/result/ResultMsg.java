package com.kiyozawa.houses.result;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ResultMsg {
    private static final String errorMsgKey="errorMsg";
    private static final String sucessMsgKey="sucessMsg";
    private String errorMsg;
    private String sucessMsg;
    public boolean isSucess(){
        return errorMsg==null;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSucessMsg() {
        return sucessMsg;
    }

    public void setSucessMsg(String sucessMsg) {
        this.sucessMsg = sucessMsg;
    }

    public static  ResultMsg errorMsg(String msg){
        ResultMsg resultMsg=new ResultMsg();
        resultMsg.setErrorMsg(msg);
        return resultMsg;

    }

    public static  ResultMsg sucessMsg(String msg){
        ResultMsg resultMsg=new ResultMsg();
        resultMsg.setSucessMsg(msg);
        return resultMsg;
    }

    public Map<String,String>asMap(){
        Map<String,String> map= Maps.newHashMap();
        map.put(sucessMsgKey,sucessMsg);
        map.put(errorMsgKey,errorMsg);
        return  map;
    }

    public String asUrlParams(){
        Map<String,String>map=asMap();
        Map<String,String>newMap=Maps.newHashMap();
        map.forEach((k,v)->{
            if(v!=null)try{
                newMap.put(k, URLEncoder.encode(v,"utf-8"));
            }catch (UnsupportedEncodingException e){
            }
        });
        return Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(newMap);
    }
}
