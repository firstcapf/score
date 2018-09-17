package com.example.demosb.Controller;

import com.example.demosb.Entity.*;
import com.example.demosb.Mapper.AchievementMapper;
import com.example.demosb.Service.AchievementService;
import com.example.demosb.Service.WXuserService;
import com.example.demosb.Util.ResultUtils;
import com.example.demosb.tools.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
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

import static com.example.demosb.tools.CommonUtil.*;

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
    @ApiOperation(value = "按条件查询",notes = "按身份证号，姓名，科目查询考生成绩信息",httpMethod = "GET")
    @GetMapping(value = "/querybyid")
    public Result<Achievement> querybyid(HttpServletRequest request,String name, String idcard) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        Result re=ResultUtils.success(achievementService.querybyid(name, idcard));

        String openid=(String)request.getSession().getAttribute("openid");

System.out.println("openid:"+openid);

      //  String str=CommonUtil.getcode();
//
   //     request.getSession().setAttribute("openid", token.getOpenId());
      //  request.getSession().setAttribute(Config.USER_SESSION_KEY, loginInfo);


     //   System.out.println("str:"+str);

        if(re.getCode()==200&&openid!=null) {
            WXuser wxuser=new WXuser();
            wxuser.setTruename(name);
            wxuser.setOpenId(openid);
            wxuser.setCardid(idcard);

            wxuserService.updateWXuser(wxuser);
        }

        return re;
    }




    @GetMapping("/queryfuzzy")
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
    public Result exImport(Achievement achievement,@RequestParam(value = "filename")MultipartFile file, HttpSession session) {
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
