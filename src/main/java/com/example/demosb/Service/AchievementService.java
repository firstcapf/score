package com.example.demosb.Service;

import com.example.demosb.Entity.Achievement;
import com.example.demosb.Entity.Result;
import com.example.demosb.Exception.MyException;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface AchievementService {

    Result<Achievement> query(int pageNum);
    Result create(Achievement achievement) throws MyException;
    Result deletebyid(int id) throws MyException;
    Result update(Achievement achievement, @Param("id") Integer id) throws MyException;
    Result<Achievement> querybyid(String name, String idcard) throws MyException;
    void queryexcel(HttpServletResponse response) throws Exception;
    boolean batchImport(Achievement achievement, String fileName, MultipartFile file) throws Exception;
    Result<Achievement> queryfuzzy(int pageNum, String idcard);


}
