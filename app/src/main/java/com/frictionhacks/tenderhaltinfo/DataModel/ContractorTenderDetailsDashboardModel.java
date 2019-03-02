package com.frictionhacks.tenderhaltinfo.DataModel;

import android.widget.ArrayAdapter;

import com.frictionhacks.tenderhaltinfo.DataWord.TenderDetailWord;
import com.google.type.LatLng;

import java.util.ArrayList;

public class ContractorTenderDetailsDashboardModel {

    public static ArrayList<TenderDetailWord> mData;

    public ContractorTenderDetailsDashboardModel() {
        mData=new ArrayList<>();

    }

    static public void init(){
    mData=new ArrayList<>();
    mData.add(new TenderDetailWord("13413342",12d,12d,"652984756297","76529734562",0,"SampleUrl"));
    mData.add(new TenderDetailWord("13413342",12d,12d,"652984756297","76529734562",0,"SampleUrl2"));


    }


}
