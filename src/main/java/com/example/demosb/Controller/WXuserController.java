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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
@Api(value = "微信用户管理",description = "做各种操作 ")
@RequestMapping("/wechat/wxuser")
public class WXuserController {

    @Autowired
    private AchievementService achievementService;
    @Resource
    private WXuserService wxuserService;
   // private AchievementService achievementService;
    /**
     *成绩查询接口  getcode
     * url:  https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe68e474aba8ac509&redirect_uri=http%3a%2f%2flandbigdata.swjtu.edu.cn%2fgetcode&response_type=code&scope=snsapi_userinfo&state=123&connect_redirect=1#wechat_redirect
     * 下一步签到接口
     */
    @RequestMapping("/getcode")
    public  String  getcode(HttpServletRequest request,HttpServletResponse response, Map<String,Object> model) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
           String code =request.getParameter("code");
           System.out.println("code:"+code);
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

    //微信报到

    /**url:https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe68e474aba8ac509&redirect_uri=http%3a%2f%2flandbigdata.swjtu.edu.cn%2freport&response_type=code&scope=snsapi_userinfo&state=123&connect_redirect=1#wechat_redirect
     *
     */
    @RequestMapping("/report")
        @ApiOperation(value = "微信报到",httpMethod = "GET")
        public Result report(HttpServletRequest request,HttpServletResponse response) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        String code =request.getParameter("code");
        WeixinOauth2Token token=CommonUtil.getOauth2AccessToken(code);
        System.out.println("token:"+token.getAccessToken());
        SNSUserInfo userinfo=CommonUtil.getSNSUserInfo(token.getAccessToken(),token.getOpenId());
        if(userinfo!=null&&userinfo.getOpenId()!=null) {
            String openid = userinfo.getOpenId();
            System.out.println("openid:"+openid);
            //  String openid="o4zf8wFIOoCHyDPpOXlaqCpdxcvs";
            request.getSession().setAttribute("openid", openid);
            SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format0.format(new Date());
            String type = achievementService.querytype(time).getType();
            System.out.println("report:type"+type);
            String logic = "1";
            if (type == null) {
                return ResultUtils.error(209, "未到报道时间！");
            }
            if (achievementService.querybyopenid(openid) != null) {
//                if(achievementService.querybylogic(openid,type)!=null){
//                        //报到成功，请勿重新报到
//                        response.sendRedirect("https://landbigdata.swjtu.edu.cn/wechat/ui/#/report");
//                        return ResultUtils.success1(achievementService.queryreport(openid,type));
//                    }
//                    else {
//                        achievementService.insertreport(openid,logic,time,type,company);
//                        response.sendRedirect("https://landbigdata.swjtu.edu.cn/wechat/ui/#/report");
//                        return ResultUtils.error(200,"报到成功");
//                    }
                System.out.println("report:重定向");
                response.sendRedirect("https://landbigdata.swjtu.edu.cn/wechat/ui/#/report");
            } else {
                System.out.println("返回到绑定微信界面");
                //重定向跳转到查询页面
                response.sendRedirect("https://landbigdata.swjtu.edu.cn/wechat/ui/#/report");
                return ResultUtils.error(208, "返回到绑定微信页面");
            }
         /*   }
            else{
                return ResultUtils.error(207,"openid无效");
            }*/
        }
        return ResultUtils.error(208,"返回到绑定微信页面");
    }

    //微信签到
    @RequestMapping("/register")
    @ApiOperation(value = "微信签到",httpMethod = "GET")
    public Result register(String company,HttpServletRequest request,HttpServletResponse response) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        String code = request.getParameter("code");
        WeixinOauth2Token token = CommonUtil.getOauth2AccessToken(code);
        SNSUserInfo userinfo = CommonUtil.getSNSUserInfo(token.getAccessToken(), token.getOpenId());
        if (userinfo != null && userinfo.getOpenId() != null) {
            String openid = userinfo.getOpenId();
            request.getSession().setAttribute("openid", openid);
            //  String openid="o4zf8wFIOoCHyDPpOXlaqCpdxcvs";

            SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format0.format(new Date());
            String type = achievementService.querytype(time).getType();
            String logic = "1";
            if (type == null) {
                return ResultUtils.error(209, "签到时间未到！");
            }
            if (openid != null && openid.length() > 4) {
                if (achievementService.querybyopenid(openid) != null) {

                    //   String sr = achievementService.querybylogic(openid, type);
                    //    System.out.println(sr);
                    if (achievementService.querybylogic(openid, type) == null) {
                        System.out.println("请先报到");
                        response.sendRedirect("https://landbigdata.swjtu.edu.cn/wechat/ui/#/report");
                        return ResultUtils.error(211, "请先报到！");
                    }
                    response.sendRedirect("https://landbigdata.swjtu.edu.cn/wechat/ui/#/sign");

                   /* String starttime = (achievementService.querystarttime(type).toString()).substring(0, 19);
                    String endtime = (achievementService.queryendtime(type).toString()).substring(0, 19);
                    System.out.println(starttime + "       " + endtime);
                    if ((starttime.compareToIgnoreCase(time) <= 0) && (endtime.compareToIgnoreCase(time) >= 0)) {
                        if (achievementService.querylogic(openid, type) == null) {
                            System.out.println("签到成功---" + "现在时间：" + time + "    " + "开始时间：" + starttime + "     " + "结束时间：" + endtime);
                            achievementService.insertlogic(logic, openid, time, type, company);
                            //签到成功
                            response.sendRedirect("https://landbigdata.swjtu.edu.cn/wechat/ui/#/sign");
                            return ResultUtils.success2();
                        } else {
                            response.sendRedirect("https://landbigdata.swjtu.edu.cn/wechat/ui/#/sign");
                            return ResultUtils.error(210, "已经签到，请勿重复签到");
                        }
                    } else if (starttime.compareToIgnoreCase(time) > 0) {
                        response.sendRedirect("https://landbigdata.swjtu.edu.cn/wechat/ui/#/sign");
                        return ResultUtils.error(209, "签到失败，未到签到时间");
                    } else {
                        response.sendRedirect("https://landbigdata.swjtu.edu.cn/wechat/ui/#/sign");
                        return ResultUtils.error(209, "签到失败，签到时间已过");
                    }*/
                }
              /*  else {
                    response.sendRedirect("https://landbigdata.swjtu.edu.cn/wechat/ui/#/sign");
                    return ResultUtils.error(208, "返回到绑定微信页面");
                }*/
            }
        }
            return ResultUtils.error(208, "返回到绑定微信页面");

   /*     }
        return ResultUtils.error(207, "openid无效");*/
    }



    @RequestMapping("/wxuseradd")
    public String wxuseradd(HttpServletRequest request, Map<String,Object> model) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {


        return "";

    }
}
