package com.jiyun.frame;


//域名 在测试或正式上线后的修改类
public class ServerAddressConfig {
    //1表示内测  2表示外测  3表示外正
    public static final int API_TYPE=3;

    public static String BASE_URL="";

    static {
        if (API_TYPE==1){
            BASE_URL="";
        }
        if (API_TYPE==2){
            BASE_URL="";
        }
        if (API_TYPE==3){
            BASE_URL="http://static.owspace.com/";
        }

    }
}
