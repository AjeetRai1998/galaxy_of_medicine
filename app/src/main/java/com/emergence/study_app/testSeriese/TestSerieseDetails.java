package com.emergence.study_app.testSeriese;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Activity.Buy_Activity;
import com.emergence.study_app.Activity.Checksum;
import com.emergence.study_app.Adapter.TestSeriesDetailAdapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseActivity;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.Get_User.ResponseGetUser;
import com.emergence.study_app.newTech.retrofit.model.Order.ResponseOrder;
import com.emergence.study_app.newTech.retrofit.model.responseTestSeriesDetails.ResponseTestSeriesDetails;
import com.example.study_app.R;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentResultListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestSerieseDetails extends BaseActivity implements PaymentResultListener {
    ImageView iv_back;
    RecyclerView recycler_paid_series;
    String test_Id="";
    String price="";
    TextView tv_title,tv_payable_amount,tv_pay_now;
    PreferencesManager preferencesManager;
    RelativeLayout relative_bottom;
    String mob_no="";
    String u_email="";
    Order order;
    String id="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_seriese_details);
        preferencesManager=new PreferencesManager(TestSerieseDetails.this);
        Checkout.preload(getApplicationContext());
        iv_back=findViewById(R.id.iv_back);
        relative_bottom=findViewById(R.id.relative_bottom);
        recycler_paid_series=findViewById(R.id.recycler_paid_series);
        test_Id=getIntent().getStringExtra("id");
        price=getIntent().getStringExtra("price");
        if (price.equalsIgnoreCase("combo")){
            relative_bottom.setVisibility(View.GONE);
        }else {
            relative_bottom.setVisibility(View.VISIBLE);
        }
        tv_title=findViewById(R.id.tv_title);
        tv_payable_amount=findViewById(R.id.tv_payable_amount);
        tv_payable_amount.setText("\u20B9 "+price);
        tv_title.setText(getIntent().getStringExtra("name"));
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_pay_now=findViewById(R.id.tv_pay_now);
        tv_pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
                dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

//                Intent intent=new Intent(TestSerieseDetails.this, Checksum.class);
//                intent.putExtra("orderid",System.currentTimeMillis()+"");
//                intent.putExtra("amount",price);
//                intent.putExtra("str_pkgid",test_Id);
//                intent.putExtra("type","TestSeries");
//                intent.putExtra("custid",preferencesManager.getUserId());
//                startActivity(intent);
            }
        });
        freecoursesRecycler();
    }
    private void startPayment() {
        Checkout checkout = new Checkout();

        //amount

        float wholeamt = Float.parseFloat(price);
        wholeamt = wholeamt * 100;
        String pay= String.valueOf(Math.round(wholeamt));
        checkout.setKeyID("rzp_live_8UezMJTG5gZqGU");

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.galaxy_logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Galaxy of Unani Medicine");
            options.put("description", R.string.app_name);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", id);//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", pay);//pass amount in currency subunits
            options.put("prefill.email", preferencesManager.getEmailId());
            options.put("prefill.contact",preferencesManager.getMobileNo());
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);
            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }


    }
    private void getUser() {
        ApiServices user_api= API_Config.getApiClient_ByPost();
        Call<ResponseGetUser> userCall=user_api.getGetUser(preferencesManager.getUserId(),"GetUser");
        userCall.enqueue(new Callback<ResponseGetUser>() {
            @Override
            public void onResponse(Call<ResponseGetUser> call, Response<ResponseGetUser> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    preferencesManager.setMobileNo(response.body().getData().getMobile());
                    preferencesManager.setEmailId(response.body().getData().getEmail());
                }else {
                }
            }
            @Override
            public void onFailure(Call<ResponseGetUser> call, Throwable t) {
                hideLoading();
            }
        });
    }


    private void getOrder(String trans_id) {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseOrder> orderCall=apiServices.getOrder("Order",preferencesManager.getUserId(),test_Id,"upi","TestSeries");
        orderCall.enqueue(new Callback<ResponseOrder>() {
            @Override
            public void onResponse(Call<ResponseOrder> call, Response<ResponseOrder> response) {
                hideLoading();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    showMessage(response.body().getMsg());
                    finish();
                }else {
                    showMessage(response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<ResponseOrder> call, Throwable t) {
                hideLoading();
            }
        });
    }
    private void freecoursesRecycler() {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseTestSeriesDetails> couresesCall=apiServices.getTestSeriesDetails("Test",test_Id,preferencesManager.getUserId());
        couresesCall.enqueue(new Callback<ResponseTestSeriesDetails>() {
            @Override
            public void onResponse(Call<ResponseTestSeriesDetails> call, Response<ResponseTestSeriesDetails> response) {
                getUser();
                if (response.body().getRes().equalsIgnoreCase("success")){
                    recycler_paid_series.setHasFixedSize(true);
                    recycler_paid_series.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    TestSeriesDetailAdapter adapter=new TestSeriesDetailAdapter(getApplicationContext(),response.body().getData());
                    recycler_paid_series.setAdapter(adapter);
                }else {
                }
            }
            @Override
            public void onFailure(Call<ResponseTestSeriesDetails> call, Throwable t) {

                hideLoading();
            }
        });
    }
    @Override
    public void onPaymentSuccess(String s) {
        getOrder(s);

    }

    @Override
    public void onPaymentError(int i, String s) {
        showMessage(s);
        finish();

    }

    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String> {

        private ProgressDialog dialog = new ProgressDialog(TestSerieseDetails.this);



        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        protected String doInBackground(ArrayList<String>... alldata) {
            float wholeamt = Float.parseFloat(String.valueOf(price));
            wholeamt = wholeamt * 100;
            String pay= String.valueOf(Math.round(wholeamt));
            RazorpayClient razorpay = null;
            try {
                razorpay = new RazorpayClient("rzp_live_8UezMJTG5gZqGU", "wg43aOFqct5WdaL0CSxWnoPo");
                JSONObject orderRequest = new JSONObject();
                orderRequest.put("amount",pay);
                orderRequest.put("currency","INR");
                orderRequest.put("receipt", "receipt#1"+System.currentTimeMillis());
                JSONObject notes = new JSONObject();
                notes.put("notes_key_1","Tea, Earl Grey, Hot");
                notes.put("notes_key_1","Tea, Earl Grey, Hot");
                orderRequest.put("notes",notes);


                order = razorpay.orders.create(orderRequest);
                JSONObject jsonObject=new JSONObject(String.valueOf(order));
                id=jsonObject.getString("id");
            } catch (RazorpayException | JSONException e) {
                e.printStackTrace();
            }

            return  id;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.e(" setup acc ", "  signup result  " + result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            startPayment();

//
        }

    }

}