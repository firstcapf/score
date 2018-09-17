package com.example.demosb.Mapper;


import com.example.demosb.Entity.WXuser;


public interface WXuserMapper {
    void insertWXuser(WXuser wxuser);
    WXuser querybyopenid(WXuser wxuser);
    void updateWXuser(WXuser wxuser);
}
