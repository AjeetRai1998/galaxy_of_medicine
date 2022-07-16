package com.emergence.study_app.newTech.retrofit.model.Subject;

import com.google.gson.annotations.SerializedName;

public class PackagesItem{

	@SerializedName("date")
	private String date;

	@SerializedName("ord_id")
	private String ord_id;

	@SerializedName("combo")
	private String combo;

	@SerializedName("order_date")
	private String order_date;
	@SerializedName("order_time")
	private String order_time;
	@SerializedName("amount_order")
	private String amount_order;

	@SerializedName("order_id")
	private String order_id;

	@SerializedName("method")
	private String method;

	@SerializedName("isPurchased")
	private String isPurchased;


	@SerializedName("exp_date")
	private String exp_date;

	@SerializedName("subject_id")
	private Object subjectId;

	@SerializedName("include")
	private String include;

	@SerializedName("image")
	private String image;

	@SerializedName("total_lectures")
	private int totalLectures;

	@SerializedName("discount")
	private String discount;

	@SerializedName("description")
	private String description;

	@SerializedName("mrp")
	private String mrp;

	@SerializedName("requirement")
	private String requirement;

	@SerializedName("total_notes")
	private int totalNotes;

	@SerializedName("coaching_id")
	private String coachingId;

	@SerializedName("total_videos")
	private int totalVideos;

	@SerializedName("image2")
	private String image2;

	@SerializedName("video_url")
	private String videoUrl;

	@SerializedName("wwlearn")
	private String wwlearn;

	@SerializedName("active_status")
	private String activeStatus;

	@SerializedName("price")
	private String price;

	@SerializedName("delete_status")
	private String deleteStatus;

	@SerializedName("name")
	private String name;

	@SerializedName("total_chapters")
	private int totalChapters;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	@SerializedName("category")
	private Object category;

	@SerializedName("class")
	private String jsonMemberClass;

	public String getDate(){
		return order_id;
	}
	public String getCombo(){
		return combo;
	}
	public String getAmount_order(){
		return amount_order;
	}
	public String getOrder_time(){
		return order_time;
	}
	public String getOrder_date(){
		return order_date;
	}
	public String getOrd_id(){
		return ord_id;
	}
	public String getOrder_id(){
		return order_id;
	}
	public String getMethod(){
		return method;
	}
	public String getExp_date(){
		return exp_date;
	}

	public Object getSubjectId(){
		return subjectId;
	}

	public String getInclude(){
		return include;
	}

	public String getImage(){
		return image;
	}

	public int getTotalLectures(){
		return totalLectures;
	}

	public String getDiscount(){
		return discount;
	}

	public String getDescription(){
		return description;
	}

	public String getMrp(){
		return mrp;
	}

	public String getRequirement(){
		return requirement;
	}

	public int getTotalNotes(){
		return totalNotes;
	}

	public String getCoachingId(){
		return coachingId;
	}

	public int getTotalVideos(){
		return totalVideos;
	}

	public String getImage2(){
		return image2;
	}

	public String getVideoUrl(){
		return videoUrl;
	}

	public String getWwlearn(){
		return wwlearn;
	}

	public String getActiveStatus(){
		return activeStatus;
	}

	public String getPrice(){
		return price;
	}

	public String getDeleteStatus(){
		return deleteStatus;
	}

	public String getName(){
		return name;
	}

	public int getTotalChapters(){
		return totalChapters;
	}

	public String getId(){
		return id;
	}

	public String getTime(){
		return time;
	}
	public String getIsPurchased(){
		return isPurchased;
	}

	public Object getCategory(){
		return category;
	}

	public String getJsonMemberClass(){
		return jsonMemberClass;
	}
}