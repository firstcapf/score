package com.example.demosb.tools;

import com.alibaba.fastjson.JSONObject;
import com.example.demosb.Entity.SNSUserInfo;
import com.example.demosb.Entity.WeixinOauth2Token;


import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class CommonUtil {


    static String access_token=null;
    static String appId="wxe68e474aba8ac509";
    static  String appSecret="8697ea4ad4c606da4da954d73e0e9b26";


    static long long_last_time=0;

    /**
     * 获取网页授权凭证
     * @param code
     * @return
     */
    public static WeixinOauth2Token getOauth2AccessToken(String code) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        WeixinOauth2Token wat = null;
        // 拼接请求地址
     //   https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxe68e474aba8ac509&secret=8697ea4ad4c606da4da954d73e0e9b26&code=021KG4k90hEelx1hRdj904jjk90KG4kV&grant_type=authorization_code
        String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("CODE", code);
       // System.out.println(requestUrl);
        // 获取网页授权凭证
        JSONObject jsonObject =JSONObject.parseObject( CommonUtil.httpsRequest(requestUrl, "GET", null).toString());
        if (null != jsonObject) {
            try {
                wat = new WeixinOauth2Token();
          //      System.out.println("access_token:"+jsonObject.getString("access_token"));
           //     System.out.println("openid:"+jsonObject.getString("openid"));
                wat.setAccessToken(jsonObject.getString("access_token"));
               // System.out.println("access_token:"+wat.getAccessToken());
                wat.setExpiresIn(jsonObject.getInteger("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
               int errorCode = jsonObject.getInteger("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                //log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode, errorMsg);
                System.out.println("获取网页授权凭证失败 errcode:"+errorCode+" errmsg:"+errorMsg );
            }
        }
        return wat;
    }



    public static String WeixinAccess_Token() throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {

        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ appId + "&secret=" + appSecret;


        //得到当前时间
        long current_time = System.currentTimeMillis();

        // 每次调用，如果token时间超时，重新获取，expires_in有效期为7200秒
    //    if ((current_time - long_last_time) / 1000 >= 200)
        //if ((current_time - long_last_time) / 1000 >= 7200)
        {
            JSONObject jsonObject=JSONObject.parseObject(CommonUtil.httpsRequest(url, "GET", null).toString());
            if (null != jsonObject) {
                String j_access_token = (String) jsonObject.get("access_token");
                //保存access_token
                if (j_access_token != null) {
                    access_token = j_access_token;
                    long_last_time = System.currentTimeMillis();
                }

            }
        }
        return access_token;

    }



    public static boolean sendCustomMessage(String openId,String content) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {


        String token=WeixinAccess_Token();
        content.replace("\"", "\\\"");
        String  jsonMsg="{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";
        jsonMsg=String.format(jsonMsg, openId,content);
        boolean flag=false;
        String requestUrl="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
        requestUrl=requestUrl.replace("ACCESS_TOKEN", token);
        JSONObject jsonResult =JSONObject.parseObject(CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg).toString());
        if(jsonResult!=null){
            int errorCode=jsonResult.getInteger("errcode");
            String errorMessage=jsonResult.getString("errmsg");
            if(errorCode==0){
                flag=true;
            }else{
                System.out.println("客服消息发送失败:"+errorCode+","+errorMessage);
                flag=false;
            }
        }
        return flag;
    }

    /**
     * 获取用户信息code
     */
    @SuppressWarnings( { "deprecation", "unchecked" })
    public static  String getcode() throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {

        // 拼接请求地址
        String requestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri=http://landbigdata.swjtu.edu.cn/getcode&response_type=code&scope=snsapi_base&state=123&connect_redirect=1#wechat_redirect";
        // 通过网页授权获取用户信息
        String  code = CommonUtil.httpsRequest(requestUrl, "GET", null).toString();


        return  code;
    }



    /**
     * 通过网页授权获取用户信息
     *
     * @param accessToken 网页授权接口调用凭证
     * @param openId 用户标识
     * @return SNSUserInfo
     */
    @SuppressWarnings( { "deprecation", "unchecked" })
    public static SNSUserInfo getSNSUserInfo(String accessToken, String openId) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        SNSUserInfo snsUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 通过网页授权获取用户信息
        JSONObject jsonObject =JSONObject.parseObject( CommonUtil.httpsRequest(requestUrl, "GET", null).toString());

        if (null != jsonObject) {
            try {
                snsUserInfo = new SNSUserInfo();
                // 用户的标识
                snsUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(jsonObject.getInteger("sex"));
                // 用户所在国家
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                // 用户特权信息
            //    snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = jsonObject.getInteger("errcode");
                String errorMsg = jsonObject.getString("errmsg");
            //    log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
            }
        }
        return snsUserInfo;
    }




    private static StringBuffer httpsRequest(String requestUrl, String requestMethod, String output)
            throws NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException, MalformedURLException,
            IOException, ProtocolException, UnsupportedEncodingException {

        URL url = new URL(requestUrl);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod(requestMethod);
        if (null != output) {
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(output.getBytes("UTF-8"));
            outputStream.close();
        }

        // 从输入流读取返回内容
        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        StringBuffer buffer = new StringBuffer();
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }

        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        inputStream = null;
        connection.disconnect();
        return buffer;
    }
}
