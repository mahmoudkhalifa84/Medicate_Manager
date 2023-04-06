package com.medicate.medicatemanager;

public class ChatItems {
    private String msg  , msg_date , send_by  , comp_name;
    public String getMsg() {
        return msg;
    }



    public String getMsg_date() {
        return msg_date;
    }

    public String getSend_by() {
        return send_by;
    }




    public ChatItems(String msg,  String msg_date, String send_by) {
        this.msg = msg;
        this.msg_date = msg_date;
        this.send_by = send_by;
    }

    public String getComp_name() {
        return comp_name;
    }
}
