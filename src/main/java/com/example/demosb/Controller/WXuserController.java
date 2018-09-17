package com.example.demosb.Controller;

import com.example.demosb.Entity.*;
import com.example.demosb.Service.AchievementService;
import com.example.demosb.Service.WXuserService;
import com.example.demosb.Util.ResultUtils;
import com.example.demosb.tools.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@Api(value = "微信用户管理",description = "做各种操作 ")
@RequestMapping("/text/wxuser")
public class WXuserController {
    @Resource
    private WXuserService wxuserService;

    @RequestMapping("/getcode")
    public  String  getcode(HttpServletRequest request,HttpServletResponse response, Map<String,Object> model) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {

           String code =request.getParameter("code");

        System.out.println("code:"+code);

           WeixinOauth2Token token=CommonUtil.getOauth2AccessToken(code);

           System.out.println("getOpenId:"+token.getOpenId());
           System.out.println("getAccessToken:"+token.getAccessToken());
           SNSUserInfo userinfo=CommonUtil.getSNSUserInfo(token.getAccessToken(),token.getOpenId());
        System.out.println("22");
          if(userinfo!=null)
          {
              System.out.println("333");
              WXuser wxuser=new WXuser();
              wxuser.setOpenId(userinfo.getOpenId());
              wxuser.setNickname(userinfo.getNickname());
              wxuser.setHeadimgurl(userinfo.getHeadImgUrl());
              //跳转到结果页面
              wxuserService.insertWXuser(wxuser);
              wxuser= wxuserService.querybyopenid(wxuser);
             if(wxuser.getCardid()!=null&&wxuser.getCardid().length()>10)
             {
                 System.out.println("直接跳转"+wxuser.getCardid()+wxuser.getTruename());
                 response.sendRedirect("http://landbigdata.swjtu.edu.cn/score/#/");
             }
             else
             {
                 System.out.println("填写查询条件"+wxuser.getCardid()+wxuser.getTruename());
                 request.getSession().setAttribute("openid", token.getOpenId());
                 System.out.println("set"+ request.getSession().getAttribute("openid"));
                 response.sendRedirect("http://landbigdata.swjtu.edu.cn/score/#/");
             }


             // response.sendRedirect("http://www.baidu.com");
          }
          else
          {
              System.out.println("444");
              //跳转到查询页面
          }



           return token.getOpenId();

        //    System.out.println("code:"+code);

        //   WeixinOauth2Token token=CommonUtil.getOauth2AccessToken(code);
        //     System.out.println("getOpenId:"+token.getOpenId());
        //     System.out.println("getAccessToken:"+token.getAccessToken());
        //    SNSUserInfo userinfo=CommonUtil.getSNSUserInfo(token.getAccessToken(),token.getOpenId());


       // Result re=new Result();

        //  Result re=ResultUtils.success(achievementService.querybyid("", ""));
        //    re.setData(userinfo);
      //  return re;
//
    }

    @RequestMapping("/wxuseradd")
    public String wxuseradd(HttpServletRequest request, Map<String,Object> model) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {


        return "";

    }
}
