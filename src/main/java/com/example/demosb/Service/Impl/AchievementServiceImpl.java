package com.example.demosb.Service.Impl;


import com.example.demosb.Entity.*;
import com.example.demosb.Exception.ErrorEnum;
import com.example.demosb.Exception.MyException;
import com.example.demosb.Mapper.AchievementMapper;
import com.example.demosb.Service.AchievementService;
import com.example.demosb.Util.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AchievementServiceImpl implements AchievementService {
    @Autowired
    private AchievementMapper achievementMapper;

    /**
     *
     * 查询所有信息
     * @param pageNum
     * @return
     *
     */
    @Override
    public Result<Achievement> query(int pageNum){
        if (achievementMapper.query().isEmpty()){
            throw new MyException(ErrorEnum.ERROR_204);
        }
        else{
            PageHelper.startPage(pageNum,10);
            List<Achievement> list=achievementMapper.query();
            PageInfo<Achievement> pageInfo=new PageInfo<Achievement>(list);
            return ResultUtils.success(pageInfo);
        }
    }

    /**
     *
     * 添加信息
     * @param achievement
     * @return
     *
     */
    @Override
    public Result create(Achievement achievement) throws MyException{
        if (achievementMapper.countidcard(achievement)==1){
            throw new MyException(ErrorEnum.ERROR_403);
        }
        else {
            //在数据库中生成修改时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String s = df.format(new Date());
            achievement.setCreatetime(s);
            achievementMapper.create(achievement);
            return ResultUtils.success();
        }
    }

    /**
     *
     * 删除信息
     * @param id
     * @return
     *
     */
    @Override
    public Result deletebyid(int id) throws MyException{
        achievementMapper.deletebyid(id);
        return ResultUtils.success();
    }

    /**
     *
     * 修改信息
     * @param achievement
     * @return
     *
     */
    @Override
    public Result update(Achievement achievement,@Param("id") Integer id) throws MyException{
        if (id==null||achievementMapper.queryid(id)==0){
            throw new MyException(ErrorEnum.ERROR_500);
        }
        else {
            //在数据库中生成修改时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String s = df.format(new Date());
            achievement.setUpdatetime(s);
            achievementMapper.update(achievement);
            return ResultUtils.success();
        }
    }

    /**
     *
     * 根据部分信息查询数据
     * @param
     * @param name
     * @param idcard
     * @return
     *
     */
    @Override
    public Result<Achievement> querybyid(String name,String idcard) throws MyException{
        if (achievementMapper.querybyid(name, idcard).isEmpty()){
            throw new MyException(ErrorEnum.ERROR_204);
        }
        else {
            return ResultUtils.success(achievementMapper.querybyid(name, idcard));
        }
    }

//    @Override
//    public String querylogic(String openid){
//            return achievementMapper.querylogic(openid);
//    }
    @Override
    public String querytime(String openid){
        return achievementMapper.querytime(openid);
    }
    @Override
    public Result<WXuser> updatelogic(String logic,String openid,String time2,String time1){
        achievementMapper.updatelogic(logic, openid,time2,time1);
        return ResultUtils.success();

    }
//    @Override
//    public Result<WXuser> insertlogic(String logic,String openid,String time2,String time1){
//        achievementMapper.insertlogic(logic, openid,time2,time1);
//        return ResultUtils.success();
//
//    }
    //查询签到记录
    @Override
    public Result queryregister(String openid,String type){
        return ResultUtils.success(achievementMapper.queryregister(openid,type));
    }
    @Override
    public Integer querycount(String openid,String time2){
        return achievementMapper.querycount(openid,time2);
    }
    @Override
    public Integer querycount2(String openid,String time2){
        return achievementMapper.querycount2(openid,time2);
    }
    //查询是否报到
    @Override
    public String querybylogic(String openid,String type){
        return achievementMapper.querybylogic(openid, type);
    }
    //添加报到信息
    public Result insertreport(String openid,String type,String logic,String time,String company){
        achievementMapper.insertreport(openid,type,logic,time,company);
        return ResultUtils.success();
    }
    //查询报到信息
    public Result queryreport(String openid,String type){
        return ResultUtils.success(achievementMapper.queryreport(openid, type));
    }
    //查询开始时间
    public Timestamp querystarttime(String type){
        return achievementMapper.querystarttime(type);
    }
    //查询结束时间
    public Timestamp queryendtime(String type){
        return achievementMapper.queryendtime(type);
    }
    //签到
    @Override
    public Result<WXuser> insertlogic(String logic,String openid,String time,String type,String company){
        achievementMapper.insertlogic(logic, openid,time,type,company);
        return ResultUtils.success();

    }


    @Override
    public Report queryreportby(String openid,String type)
    {
        return achievementMapper.queryreportby(openid, type);
    }
    //添加报到信息

    //查询是否签到
    @Override
    public Register queryregisterby(String openid,String type){
        return achievementMapper.queryregisterby(openid,type);
    }
    //查询全部单位
    @Override
    public Result querycompany(String openid){
        return ResultUtils.success(achievementMapper.querycompany(openid));
    }
    //查询会议名称
    @Override
    public Timetable querytype(String time){
        return achievementMapper.querytype(time);
    }






    @Override
    public Result<Achievement> queryfuzzy(int pageNum,String idcard){
        PageHelper.startPage(pageNum,10);
        List<Achievement> list=achievementMapper.queryfuzzy(idcard);
        PageInfo<Achievement> pageInfo=new PageInfo<Achievement>(list);
        return ResultUtils.success(pageInfo);
    }

    @Override
    public String querybyopenid(String openid){
        return achievementMapper.querybyopenid(openid);
    }

    /**
     *
     *导出数据库中achievement表中的数据
     * @param response
     * @throws Exception
     *
     */
    public void queryexcel (HttpServletResponse response) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("信息表");

        HSSFWorkbook demoWorkBook = new HSSFWorkbook();
        HSSFSheet demoSheet = demoWorkBook.createSheet("The World's 500 Enterprises");

        HSSFCell cell1 = demoSheet.createRow(0).createCell(0);
        List<Achievement> excleList = achievementMapper.queryexcel();

        String fileName = "achievement"  + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = { "考试科目", "姓名","身份证号","成绩","单位","地点","联系方式","性别","座位号","状态"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        //在表中存放查询到的数据放入对应 的列
        for (Achievement achievement : excleList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(achievement.getSubject());
            row1.createCell(1).setCellValue(achievement.getName());
            row1.createCell(2).setCellValue(achievement.getIdcard());
            row1.createCell(3).setCellValue(achievement.getGrade());
            row1.createCell(4).setCellValue(achievement.getCompany());
            row1.createCell(5).setCellValue(achievement.getPlace());
            row1.createCell(6).setCellValue(achievement.getPhone());
            row1.createCell(7).setCellValue(achievement.getSex());
            row1.createCell(8).setCellValue(achievement.getSeatnumber());
            row1.createCell(9).setCellValue(achievement.getRemarks());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        response.flushBuffer();
        workbook.write(response.getOutputStream());

    }

    /**
     *
     * 将Excel数据导入数据库
     * @param achievement
     * @param fileName
     * @param file
     * @return
     * @throws Exception
     *
     */
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public boolean batchImport(Achievement achievement, String fileName, MultipartFile file) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String s=df.format(new Date());
        int i=0;
        int j=0;
        boolean notNull = false;
        List<Achievement> userList = new ArrayList<Achievement>();

        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
//            throw new MyException("上传文件格式不正确");
        }
        boolean isExcel2003 = true;
        if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
            isExcel2003 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2003) {
            wb = new HSSFWorkbook(is);
        } else {
            wb = new XSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if (sheet != null) {
            notNull = true;
        }
        Achievement Achievement;
        for (int r = 1; r <= sheet.getLastRowNum(); r++) {//r = 2 表示从第三行开始循环 如果你的第三行开始是数据
            Row row = sheet.getRow(r);//通过sheet表单对象得到 行对象
            if (row == null) {
                continue;
            }
            //sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException
            Achievement = new Achievement();
            if (row.getCell(0).getCellType() != 1) {//循环时，得到每一行的单元格进行判断
//                throw new MyException("导入失败(第"+(r+1)+"行,用户名请设为文本格式)");
            }
            row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第二个单元格的值
            String subject = row.getCell(0).getStringCellValue();//得到每一行第一个单元格的值
            if (subject == null || subject.isEmpty()) {//判断是否为空
                System.out.println("导入失败(第" + (r + 1) + "行,考试科目未填写)");
            }
            row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第二个单元格的值
            String name = row.getCell(1).getStringCellValue();
            if (name == null || name.isEmpty()) {
                System.out.println("导入失败(第" + (r + 1) + "姓名未填写,)");
            }
            row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第三个单元格的值
            String idcard = row.getCell(2).getStringCellValue();
            if (idcard == null || idcard.isEmpty()) {
                System.out.println("导入失败(第" + (r + 1) + "行,身份证号码未填写)");
            }
            row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第三个单元格的值
            String grade = row.getCell(3).getStringCellValue();
            if (grade == null|| grade.isEmpty() ) {
                System.out.println("导入失败(第" + (r + 1) + "行,分数未填写)");
            }
            row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第三个单元格的值
            String company = row.getCell(4).getStringCellValue();
            if (company == null || company.isEmpty()) {
                System.out.println("导入失败(第" + (r + 1) + "行,所在单位未填写)");
            }
            row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第三个单元格的值
            String place = row.getCell(5).getStringCellValue();
            if (place == null || place.isEmpty()) {
                System.out.println("导入失败(第" + (r + 1) + "行,地点未填写)");
            }
            row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第三个单元格的值
            String phone = row.getCell(6).getStringCellValue();
            if (phone == null || phone.isEmpty()) {
                System.out.println("导入失败(第" + (r + 1) + "行,联系方式未填写)");
            }
            row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第三个单元格的值
            String sex = row.getCell(7).getStringCellValue();
            if (sex == null || sex.isEmpty()) {
                System.out.println("导入失败(第" + (r + 1) + "行,性别未填写)");
            }
            row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第三个单元格的值
            String seatnumber = row.getCell(8).getStringCellValue();
            if (seatnumber == null || seatnumber.isEmpty()) {
                System.out.println("导入失败(第" + (r + 1) + "行,座位号未填写)");
            }
            row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);//得到每一行的 第三个单元格的值
            String remarks = row.getCell(9).getStringCellValue();
            if (remarks == null || remarks.isEmpty()) {
                System.out.println("导入失败(第" + (r + 1) + "行,状态未填写)");
            }
            //完整的循环一次 就组成了一个对象
            Achievement.setSubject(subject);//考试科目
            Achievement.setName(name);//姓名
            Achievement.setIdcard(idcard);//身份证号
            Achievement.setGrade(grade);//成绩
            Achievement.setCompany(company);//单位
            Achievement.setPlace(place);//地点
            Achievement.setPhone(phone);//联系方式
            Achievement.setSex(sex);//性别
            Achievement.setSeatnumber(seatnumber);//座位号
            Achievement.setRemarks(remarks);//状态
            Achievement.setCreatetime(s);//创建时间
            Achievement.setUpdatetime(s);//修改时间
//            Achievement.setCreater(creater);//创建人

            userList.add(Achievement);

        }
        for (Achievement userResord : userList) {
            int cnt = achievementMapper.queryidcard(userResord); /*获取当前字段在数据库中出现的次数*/
            if (cnt == 0) {
                achievementMapper.create(userResord);
                i++;
            } else {
                achievementMapper.updateexcel(userResord);
                j++;
            }
        }
        System.out.println("共添加了："+i+"条语句");
        System.out.println("共更新了："+j+"条语句");
        return notNull;
    }

}
