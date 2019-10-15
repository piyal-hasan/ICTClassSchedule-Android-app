package com.example.piyal.classrutine.batabase.model;

/**
 * Created by piyal on 1/24/2019.
 */
public class Db_Url {
    //http://ictroutine.mbstu.ac.bd/getAlldata.php
//    private String BASE_URL="http://afzalconstructionltd.com/ICT/Ictrutine/";
    //http://rutine.jbratrc.com/Ictrutine/getAlldata.php
    private String BASE_URL="http://rutine.jbratrc.com/Ictrutine/";
    private String GET_ALLDATA_URL=BASE_URL+"getAlldata.php";
    private String Status_Url=BASE_URL+"getStatus.php";

    public Db_Url() {
    }

    public String getGET_ALLDATA_URL() {
        return GET_ALLDATA_URL;
    }

    public void setGET_ALLDATA_URL(String GET_ALLDATA_URL) {
        this.GET_ALLDATA_URL = GET_ALLDATA_URL;
    }

    public String getBASE_URL() {
        return BASE_URL;
    }

    public void setBASE_URL(String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }

    public String getStatus_Url() {
        return Status_Url;
    }

    public void setStatus_Url(String status_Url) {
        Status_Url = status_Url;
    }

}
