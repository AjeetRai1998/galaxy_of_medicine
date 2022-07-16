package com.emergence.study_app.newTech.retrofit.model.responseQuestion;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseQuestion{

	@SerializedName("msg")
	private String msg;

	@SerializedName("res")
	private String res;

	@SerializedName("data")
	private List<DataItem> data;

	public String getMsg(){
		return msg;
	}

	public String getRes(){
		return res;
	}

	public List<DataItem> getData(){
		return data;
	}
}