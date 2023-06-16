package com.example.demo11;

import java.io.Serializable;

public class QueryResolutionForm  implements Serializable {


    private String queryData;

    QueryResolutionForm(String queryData) {
        this.queryData = queryData;
    }
  public String getQueryData(){
        return queryData;
  }
}

