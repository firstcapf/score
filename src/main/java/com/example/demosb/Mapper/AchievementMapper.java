package com.example.demosb.Mapper;

import com.example.demosb.Entity.Achievement;
import com.example.demosb.Entity.Register;
import com.example.demosb.Entity.Report;
import com.example.demosb.Entity.WXuser;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface AchievementMapper {
    List<Achievement> query();
    List<Achievement> queryexcel();
    List<Achievement> querybyid(@Param("name") String name, @Param("idcard") String idcard);
    void create(Achievement achievement);
    void deletebyid(int id);
    void update(Achievement achievement);
    Integer queryidcard(Achievement achievement);
    Integer queryid(@Param("id") Integer id);
    Integer countidcard(Achievement achievement);
    void updateexcel(Achievement achievement);
    List<Achievement> queryfuzzy(String idcard);

//    String querylogic(@Param("openid") String openid);
    String querytime(@Param("openid") String openid);
    String querybyopenid(@Param("openid") String openid);
    void updatelogic(@Param("logic") String logic,@Param("openid") String openid,@Param("time2") String time2,@Param("time1") String time1);
//    void insertlogic(@Param("logic") String logic,@Param("openid") String openid,@Param("time2") String time2,@Param("time1") String time1);
    List queryregister(@Param("openid") String openid,@Param("type") String type);
    Integer querycount(@Param("openid") String openid,@Param("time2") String time2);
    Integer querycount2(@Param("openid") String openid,@Param("time2") String time2);

    String querybylogic(@Param("openid") String openid,@Param("type") String type);
    void insertreport(@Param("openid") String openid,@Param("logic") String logic,@Param("time") String time,@Param("type") String type,@Param("company") String company);
    List<Report> queryreport(@Param("openid") String openid,@Param("type") String type);
    Timestamp querystarttime(@Param("type") String type);
    Timestamp queryendtime(@Param("type") String type);
    void insertlogic(@Param("logic") String logic,@Param("openid") String openid,@Param("time") String time,@Param("type") String type,@Param("company") String company);
    String querylogic(@Param("openid") String openid,@Param("type") String type);

    List querycompany(@Param("openid") String openid);
    String querytype(@Param("time") String time);


}
