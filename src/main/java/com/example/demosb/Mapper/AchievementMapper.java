package com.example.demosb.Mapper;

import com.example.demosb.Entity.Achievement;
import org.apache.ibatis.annotations.Param;

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
}
