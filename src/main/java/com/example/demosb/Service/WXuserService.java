package com.example.demosb.Service;


import com.example.demosb.Entity.WXuser;


public interface WXuserService {

    void insertWXuser(WXuser wxuser);
    WXuser querybyopenid(WXuser wxuser);
    void updateWXuser(WXuser wxuser);


}
