package com.example.demosb.Controller;

import com.example.demosb.Entity.*;
import com.example.demosb.Exception.ErrorEnum;
import com.example.demosb.Service.AchievementService;
import com.example.demosb.Service.WXuserService;
import com.example.demosb.Util.ResultUtils;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@RestController
@Api(value = "成绩管理",description = "对成绩做各种操作 ")
@RequestMapping("/text/achievement")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;


    @Resource
    private WXuserService wxuserService;

    /**
     *
     * 查询所有信息
     * @param pageNum
     * @return
     *
     */
    @ApiOperation(value = "查询数据",notes = "分页查询全部数据",httpMethod = "GET")
    @GetMapping(value = "/query")
    public Result<Achievement> query(int pageNum){
        return achievementService.query(pageNum);
    }
    /**
     *
     * 添加信息
     * @param achievement
     * @return
     *
     */
    @ApiOperation(value = "添加数据",notes ="添加考生成绩信息",httpMethod = "POST")
    @PostMapping(value = "/create")
    public Result create(Achievement achievement){
        achievementService.create(achievement);
        return ResultUtils.success();
    }
    /**
     *
     * 删除信息
     * @param id
     * @return
     *
     */
    @ApiOperation(value = "删除信息",notes = "根据考生编号，删除信息",httpMethod = "DELETE")
    @DeleteMapping(value = "/deletebyid")
    public Result deletebyid(int id){
        achievementService.deletebyid(id);
        return ResultUtils.success();
    }
    /**
     *
     * 修改信息
     * @param achievement
     * @return
     *
     */
    @ApiOperation(value = "修改信息",notes = "修改考生信息",httpMethod = "PUT")
    @PutMapping(value = "/update")
    public Result update(Achievement achievement,Integer id){
        achievementService.update(achievement,id);
        return ResultUtils.success();
    }
    /**
     *
     * 根据部分信息查询数据
     * @param
     * @param name
     * @param idcard
     * @return
     *http://127.0.0.1:8080//text/achievement/querybyid?idcard=513427199209213417&name=%E9%A5%B6%E9%81%93%E6%96%8C
     */

    private String idcard="513124197602061179";
    private String name="冯德华";
    private String logic="1";
    private String openid="o4zf8wFKnnohmAcGVYB_rLRR0xuI";
    @ApiOperation(value = "按条件查询",notes = "按身份证号，姓名，科目查询考生成绩信息",httpMethod = "GET")
    @GetMapping(value = "/querybyid")
    public Result<Achievement> querybyid(HttpServletRequest request) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException, ParseException {

//        String openid=(String)request.getSession().getAttribute("openid");
        Result re=null;
        if(idcard==null||idcard.length()<10)
        {
            if(openid!=null&&openid.length()>4)
            {
                WXuser wxuser=new WXuser();
                wxuser.setOpenId(openid);
                wxuser= wxuserService.querybyopenid(wxuser);
                idcard=wxuser.getCardid();
                name=wxuser.getTruename();
                System.out.println();
            }
        }

        re=ResultUtils.success(achievementService.querybyid(name, idcard));

        if(re.getCode()==200&&openid!=null) {
            WXuser wxuser=new WXuser();
            wxuser.setTruename(name);
            wxuser.setOpenId(openid);
            wxuser.setCardid(idcard);
            wxuserService.updateWXuser(wxuser);
        }
        return re;
//
//        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");//创建日期转换对象
//        String dt1 ="08:00:00";
//        String dt2 ="09:00:00";
//        String dt3 ="17:00:00";
//        String dt4 ="23:30:00";
//        String time1=time.format(new Date());
//        //报到
//        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd");
//        String time2=format0.format(new Date());
////        System.out.println(achievementService.querycount(openid,time2));
////        String time3=achievementService.querytime(openid);
//        if (openid!=null&&openid.length()>4)
//        {
//            if (achievementService.querybyopenid(openid)!=null)
//            {
//                if (((dt1.compareToIgnoreCase(time1)<=0)&&(dt2.compareToIgnoreCase(time1)>=0))||
//                        ((dt3.compareToIgnoreCase(time1)<=0)&&(dt4.compareToIgnoreCase(time1)>=0))){
//
//                    if ((achievementService.querylogic(openid)!=null)&&(achievementService.querytime(openid).equals(time2))
//                            &&((achievementService.querycount(openid,time2))==1||(achievementService.querycount2(openid,time2))==1))
//                    {
//                        return ResultUtils.error(201,"已经签到，请勿重新签到");
//                    }
//                    else{
//                        if(((dt1.compareToIgnoreCase(time1)<=0)&&(dt2.compareToIgnoreCase(time1)>=0))){
//                            //time1比dt1时间早或者time1比dt2晚
//                            System.out.println("签到成功---"+"现在时间："+time1+"    "+"开始时间："+dt1+"     "+"结束时间："+dt2);
////                        achievementService.updatelogic(logic,openid,time2,time1);
////                            achievementService.insertlogic(logic,openid,time2,time1);
//                            return ResultUtils.error(200, "签到成功");
//                        }
//                        else if (((dt3.compareToIgnoreCase(time1)<=0)&&(dt4.compareToIgnoreCase(time1)>=0))) {
////                            achievementService.insertlogic(logic,openid,time2,time1);
//                            return ResultUtils.error(200, "签到成功");
//                        }
//                        else {
//                            //签到
//                            System.out.println("签到失败---" + "现在时间：" + time1 + "    " + "开始时间：" + dt1 + "    " + "结束时间：" + dt2);
//                            return ResultUtils.error(205, "未在签到时间内");
//                        }
//                    }
//                }
//                else{
//                    return ResultUtils.error(205, "未在签到时间内");
//                }
//
//            }
//            else{
//                return ResultUtils.error(208,"返回到绑定微信页面");
//            }
//        }
//        else{
//            return ResultUtils.error(207,"openid无效");
//        }
    }


    //考勤记录查询
    @GetMapping("/queryregister1")
    @ApiOperation(value = "查询考勤记录",httpMethod = "GET")
    public Result queryregister1(String openid,String type){
       return ResultUtils.success(achievementService.queryregister(openid,type));
    }
    @GetMapping("/querycompany")
    @ApiOperation(value = "查询单位",httpMethod = "GET")
    public Result querycompany(String openid){
        return ResultUtils.success(achievementService.querycompany(openid));
    }



    @GetMapping("/queryfuzzy")
    @ApiOperation(value = "模糊查询",httpMethod = "GET")
    public Result<Achievement> queryfuzzy(int pageNum,String idcard){
        return ResultUtils.success(achievementService.queryfuzzy(pageNum, idcard));
    }
    /**
     *
     *导出数据库中achievement表中的数据
     * @param response
     * @throws Exception
     *
     */
    @GetMapping(value = "/getexcel")
    @ApiOperation(value = "导出数据",notes = "查询出某传感器数据，导出其数据，方便查看",httpMethod = "GET")
    public void getexcel(HttpServletResponse response) throws Exception{
        achievementService.queryexcel(response);
    }
    /**
     *
     * 将Excel数据导入数据库
     * @param achievement
     * @param * fileName
     * @param file
     * @return
     * @throws Exception
     *
     */
    @PostMapping(value = "/import")
    @ApiOperation(value = "导入数据",notes = "批量",httpMethod = "POST")
    public Result exImport(Achievement achievement,@RequestParam(value = "filename")MultipartFile file, HttpSession session){
        boolean a = false;
        String fileName = file.getOriginalFilename();
        try {
            a = achievementService.batchImport(achievement,fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtils.success();
    }
}
