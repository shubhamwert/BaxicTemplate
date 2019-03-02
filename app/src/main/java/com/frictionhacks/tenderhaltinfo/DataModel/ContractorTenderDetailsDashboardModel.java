package com.frictionhacks.tenderhaltinfo.DataModel;


import com.frictionhacks.tenderhaltinfo.DataWord.TenderDetailWord;

import java.util.ArrayList;

public class ContractorTenderDetailsDashboardModel {

    public static ArrayList<TenderDetailWord> mData;

    public ContractorTenderDetailsDashboardModel() {
        mData=new ArrayList<>();

    }

    static public void init(){
    mData=new ArrayList<>();
    mData.add(new TenderDetailWord("13413342",12.78,12,"652984756297","76529734562",0,"SampleUrl"));
    mData.add(new TenderDetailWord("13413342",12.56,12.78,"652984756297","76529734562",0,"SampleUrl2"));


    }


}
