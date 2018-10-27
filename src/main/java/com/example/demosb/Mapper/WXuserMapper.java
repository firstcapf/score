package com.example.demosb.Mapper;


import com.example.demosb.Entity.Information;
import com.example.demosb.Entity.WXuser;
import org.apache.ibatis.annotations.Param;


public interface WXuserMapper {
    void insertWXuser(WXuser wxuser);
    WXuser querybyopenid(WXuser wxuser);
    void updateWXuser(WXuser wxuser);

    Information queryidcard(@Param("idcard") String idcard);
    String queryname(@Param("idcard") String idcard);
}
