package com.example.demosb.Service.Impl;


import com.example.demosb.Entity.Achievement;
import com.example.demosb.Entity.Result;
import com.example.demosb.Entity.WXuser;
import com.example.demosb.Exception.ErrorEnum;
import com.example.demosb.Exception.MyException;
import com.example.demosb.Mapper.AchievementMapper;
import com.example.demosb.Mapper.WXuserMapper;
import com.example.demosb.Service.AchievementService;
import com.example.demosb.Service.WXuserService;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WxuserServiceImpl implements WXuserService {

    @Autowired
    private WXuserMapper wxuserMapper;

    @Override
    public void insertWXuser(WXuser wxuser)
    {
        wxuserMapper.insertWXuser(wxuser);

    }

    public WXuser querybyopenid(WXuser wxuser)

    {
        return wxuserMapper.querybyopenid(wxuser);
    }
    public void updateWXuser(WXuser wxuser){
        wxuserMapper.updateWXuser(wxuser);
    }


}
