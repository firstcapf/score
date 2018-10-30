package com.example.demosb.Service;

import com.example.demosb.Entity.*;
import com.example.demosb.Exception.MyException;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;

public interface AchievementService {

    Result<Achievement> query(int pageNum);
    Result create(Achievement achievement) throws MyException;
    Result deletebyid(int id) throws MyException;
    Result update(Achievement achievement, @Param("id") Integer id) throws MyException;
    Result<Achievement> querybyid(String name, String idcard) throws MyException;
    void queryexcel(HttpServletResponse response) throws Exception;
    boolean batchImport(Achievement achievement, String fileName, MultipartFile file) throws Exception;
    Result<Achievement> queryfuzzy(int pageNum, String idcard);

//    String querylogic(String openid);
    String querytime(String openid);
    String querybyopenid(@Param("openid") String openid);
    Result updatelogic(@Param("logic") String logic,@Param("openid") String openid,@Param("time2") String time2,@Param("time1") String time1);
//    Result insertlogic(@Param("logic") String logic,@Param("openid") String openid,@Param("time2") String time2,@Param("time1") String time1);
    Result queryregister(@Param("openid") String openid,@Param("type") String type);
    Integer querycount(@Param("openid") String openid,@Param("time2") String time2);
    Integer querycount2(@Param("openid") String openid,@Param("time2") String time2);
    Report queryreportby(@Param("openid") String openid,@Param("type") String type);

    String querybylogic(@Param("openid") String openid,@Param("type") String type);
    Result insertreport(@Param("openid") String openid,@Param("logic") String logic,@Param("time") String time,@Param("type") String type,@Param("company") String company);
    Result<Report> queryreport(@Param("openid") String openid,@Param("type") String type);
    Timestamp querystarttime(@Param("type") String type);
    Timestamp queryendtime(@Param("type") String type);
    Result insertlogic(@Param("logic") String logic,@Param("openid") String openid,@Param("time") String time,@Param("type") String type,@Param("company") String company);
    Register queryregisterby(@Param("openid") String openid,@Param("type") String type);

    Result querycompany(@Param("openid") String openid);
    Timetable querytype(@Param("time") String time);

}
