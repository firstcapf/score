package com.example.demosb.Controller;

import com.example.demosb.Entity.*;
import com.example.demosb.Service.AchievementService;
import com.example.demosb.Service.WXuserService;
import com.example.demosb.Util.ResultUtils;
import com.example.demosb.tools.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;

@Controller
@Api(value = "微信用户管理",description = "做各种操作 ")
@RequestMapping("/text/wxuser")
public class WXuserController {
    @Resource
    private WXuserService wxuserService;


    /**
     *成绩查询接口  getcode
     * url:  https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe68e474aba8ac509&redirect_uri=http%3a%2f%2flandbigdata.swjtu.edu.cn%2fgetcode&response_type=code&scope=snsapi_userinfo&state=123&connect_redirect=1#wechat_redirect
     * 下一步签到接口
     */
    @RequestMapping("/getcode")
    public  String  getcode(HttpServletRequest request,HttpServletResponse response, Map<String,Object> model) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {

           String code =request.getParameter("code");
           WeixinOauth2Token token=CommonUtil.getOauth2AccessToken(code);
           SNSUserInfo userinfo=CommonUtil.getSNSUserInfo(token.getAccessToken(),token.getOpenId());
           if(userinfo!=null&&userinfo.getOpenId()!=null)
          {
              WXuser wxuser=new WXuser();
              wxuser.setOpenId(userinfo.getOpenId());
              wxuser.setNickname(userinfo.getNickname());
              wxuser.setProvince(userinfo.getProvince());
              wxuser.setCountry(userinfo.getCountry());
              wxuser.setSex(userinfo.getSex());
              wxuser.setHeadimgurl(userinfo.getHeadImgUrl());
              wxuserService.insertWXuser(wxuser);
              request.getSession().setAttribute("openid", token.getOpenId());
          }
          //重定向跳转到查询页面
          response.sendRedirect("http://landbigdata.swjtu.edu.cn/score/#/");
          return token.getOpenId();
    }


    /**
     * 下一步完成签到接口
     * @param request
     * @param response
     * @param model
     * @return
     * @throws KeyManagementException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws IOException
     */
    @RequestMapping("/signin")
    public  String  signin(HttpServletRequest request,HttpServletResponse response, Map<String,Object> model) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {

        String code =request.getParameter("code");
        WeixinOauth2Token token=CommonUtil.getOauth2AccessToken(code);
        SNSUserInfo userinfo=CommonUtil.getSNSUserInfo(token.getAccessToken(),token.getOpenId());
        if(userinfo!=null&&userinfo.getOpenId()!=null)
        {
            WXuser wxuser=new WXuser();
            wxuser.setOpenId(userinfo.getOpenId());
            wxuser.setNickname(userinfo.getNickname());
            wxuser.setProvince(userinfo.getProvince());
            wxuser.setCountry(userinfo.getCountry());
            wxuser.setSex(userinfo.getSex());
            wxuser.setHeadimgurl(userinfo.getHeadImgUrl());
            wxuserService.insertWXuser(wxuser);
            request.getSession().setAttribute("openid", token.getOpenId());
        }
        //重定向跳转到查询页面
        response.sendRedirect("http://landbigdata.swjtu.edu.cn/score/#/");
        return token.getOpenId();
    }


    @RequestMapping("/wxuseradd")
    public String wxuseradd(HttpServletRequest request, Map<String,Object> model) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {


        return "";

    }
}
