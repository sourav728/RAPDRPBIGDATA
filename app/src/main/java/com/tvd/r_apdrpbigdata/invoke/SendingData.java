package com.tvd.r_apdrpbigdata.invoke;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.tvd.r_apdrpbigdata.adapters.OBCBBilledAdapter1;
import com.tvd.r_apdrpbigdata.adapters.OBCBBilledAdapter2;
import com.tvd.r_apdrpbigdata.adapters.OBCBBilledAdapter3;
import com.tvd.r_apdrpbigdata.adapters.OBCBBilledAdapter4;
import com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.Customer_Billed_Summerization.OBCBAdapter1;
import com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.Customer_Billed_Summerization.OBCBAdapter2;
import com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.Customer_Billed_Summerization.OBCBAdapter3;
import com.tvd.r_apdrpbigdata.OBCB_Summerization.OBCB_Summerization.Customer_Billed_Summerization.OBCBAdapter4;
import com.tvd.r_apdrpbigdata.adapters.OBCBUnBilledAdapter1;
import com.tvd.r_apdrpbigdata.adapters.OBCBUnBilledAdapter2;
import com.tvd.r_apdrpbigdata.adapters.OBCBUnBilledAdapter3;
import com.tvd.r_apdrpbigdata.adapters.OBCBUnBilledAdapter4;
import com.tvd.r_apdrpbigdata.adapters.DashboardAdapter;
import com.tvd.r_apdrpbigdata.models.Abnormal_Subnormal;
import com.tvd.r_apdrpbigdata.models.Billing_Summarization_model;
import com.tvd.r_apdrpbigdata.models.Customer_Summarization_model;
import com.tvd.r_apdrpbigdata.models.Payment_Summarization_model;
import com.tvd.r_apdrpbigdata.models.Response;
import com.tvd.r_apdrpbigdata.values.GetSetValues;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import static com.tvd.r_apdrpbigdata.values.Constant.PUBLIC_API;

public class SendingData {
    private ReceivingData receivingData = new ReceivingData();

    private String UrlPostConnection2(String Post_Url, HashMap<String, String> datamap) throws IOException {
        String response;
        URL url = new URL(Post_Url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(4 * 60 * 1000);
        conn.setConnectTimeout(4 * 60 * 1000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoInput(true);
        conn.setDoOutput(true);

        OutputStream os = conn.getOutputStream();
        @SuppressLint({"NewApi", "LocalSuppress"}) BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
        writer.write(getPostDataString(datamap));
        writer.flush();
        writer.close();
        os.close();
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseBuilder.append(line).append("\n");
            }
            response = responseBuilder.toString();
        } else response = "";
        return response;
    }

    private String UrlGetConnection1(String getUrl) throws IOException {
        String response;
        URL url = new URL(getUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(4 * 60 * 1000);
        conn.setConnectTimeout(4 * 60 * 1000);
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseBuilder.append(line);
            }
            response = responseBuilder.toString();
        } else response = "";
        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    private String UrlGetConnection(String getUrl) throws IOException {
        String response;
        URL url = new URL(getUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(4 * 60 * 1000);
        conn.setConnectTimeout(4 * 60 * 1000);
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                responseBuilder.append(line);
            }
            response = responseBuilder.toString();
        } else response = "";
        return response;
    }

    //For Billing Summarization tariffwise report
    @SuppressLint("StaticFieldLeak")
    public class Get_billing_summarization_tariffwise_value extends AsyncTask<String, String, String> {
        Handler handler;
        String response = "";
        ArrayList<Billing_Summarization_model> arrayList;
        GetSetValues getSetValues;
        public Get_billing_summarization_tariffwise_value(Handler handler, ArrayList<Billing_Summarization_model> arrayList, GetSetValues getSetValues) {
            this.handler = handler;
            this.arrayList = arrayList;
            this.getSetValues = getSetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("from", params[0]);
            datamap.put("to", params[1]);
            datamap.put("tariff", params[2]);
            datamap.put("subDivision", params[3]);
            datamap.put("company", params[4]);
            datamap.put("zone", params[5]);
            datamap.put("circle", params[6]);
            datamap.put("division", params[7]);
            try {
                response = UrlPostConnection2(PUBLIC_API + "GraphBill_Summ", datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            receivingData.get_Billing_Summarization_Tariffwise(response, handler, arrayList, getSetValues);
        }
    }

    //For Billing Summarization yearwise report
    @SuppressLint("StaticFieldLeak")
    public class Get_billing_summarization_yearwise_value extends AsyncTask<String, String, String> {
        Handler handler;
        String response = "";
        ArrayList<Billing_Summarization_model> arrayList;
        GetSetValues getSetValues;
        public Get_billing_summarization_yearwise_value(Handler handler, ArrayList<Billing_Summarization_model> arrayList, GetSetValues getSetValues) {
            this.handler = handler;
            this.arrayList = arrayList;
            this.getSetValues = getSetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("from", params[0]);
            datamap.put("to", params[1]);
            datamap.put("subDivision", params[2]);
            datamap.put("company", params[3]);
            datamap.put("zone", params[4]);
            datamap.put("circle", params[5]);
            datamap.put("division", params[6]);
            try {
                response = UrlPostConnection2(PUBLIC_API + "GraphBill_SummOverAll", datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            receivingData.get_Billing_Summarization_Yearwise(response, handler, arrayList, getSetValues);
        }
    }


    //For Payment Summarization yearwise report
    @SuppressLint("StaticFieldLeak")
    public class Get_payment_summarization_yearwise_value extends AsyncTask<String, String, String> {
        Handler handler;
        String response = "";
        ArrayList<Payment_Summarization_model> arrayList;
        GetSetValues getSetValues;
        public Get_payment_summarization_yearwise_value(Handler handler, ArrayList<Payment_Summarization_model> arrayList, GetSetValues getSetValues) {
            this.handler = handler;
            this.arrayList = arrayList;
            this.getSetValues = getSetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("from", params[0]);
            datamap.put("to", params[1]);
            datamap.put("subDivision", params[2]);
            datamap.put("company", params[3]);
            datamap.put("zone", params[4]);
            datamap.put("circle", params[5]);
            datamap.put("division", params[6]);
            datamap.put("acc_status", params[7]);
            datamap.put("billed_status", params[8]);
            try {
                response = UrlPostConnection2(PUBLIC_API + "GraphPay_SummOverAll", datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            receivingData.get_Payment_Summarization_Yearwise(response, handler, arrayList, getSetValues);
        }
    }

    //For Payment Summarization TariffWise report
    @SuppressLint("StaticFieldLeak")
    public class Get_payment_summarization_tariffwise_value extends AsyncTask<String, String, String> {
        Handler handler;
        String response = "";
        ArrayList<Payment_Summarization_model> arrayList;
        GetSetValues getSetValues;
        public Get_payment_summarization_tariffwise_value(Handler handler, ArrayList<Payment_Summarization_model> arrayList,GetSetValues getSetValues) {
            this.handler = handler;
            this.arrayList = arrayList;
            this.getSetValues = getSetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("from", params[0]);
            Log.d("debug", "from " + params[0]);
            datamap.put("to", params[1]);
            Log.d("debug", "to " + params[1]);
            datamap.put("subDivision", params[2]);
            Log.d("debug", "subDivision " + params[2]);
            datamap.put("tariff", params[3]);
            Log.d("debug", "tariff " + params[3]);
            datamap.put("company", params[4]);
            Log.d("debug", "company " + params[4]);
            datamap.put("zone", params[5]);
            Log.d("debug", "zone " + params[5]);
            datamap.put("circle", params[6]);
            Log.d("debug", "circle " + params[6]);
            datamap.put("division", params[7]);
            Log.d("debug", "division " + params[7]);
            datamap.put("acc_status", params[8]);
            Log.d("debug", "acc_status " + params[8]);
            datamap.put("billed_status", params[9]);
            Log.d("debug", "billed_status " + params[9]);
            try {
               // response = UrlPostConnection2(PUBLIC_API + "GraphPay_Summ", datamap);
                response = UrlGetConnection1(PUBLIC_API + "GraphPay_Summ.html?from="+params[0]+"&to="+params[1]+"&subDivision="+params[2]+"&tariff="+params[3]+"&company=500001%20-%20Hubli%20Electricity%20Supply%20Company%20Limited&zone="+params[5]+"&circle="+params[6]+"&division="+params[7]+"&acc_status="+params[8]+"&billed_status="+params[9]+"");
                Log.d("debug", "Response " + response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            receivingData.get_Payment_Summarization_Tariffwise(response, handler, arrayList, getSetValues);
        }
    }

    //For Customer Summarization TariffWise
    @SuppressLint("StaticFieldLeak")
    public class Get_customer_summarization_tariffwise_value extends AsyncTask<String, String, String> {
        Handler handler;
        String response = "";
        ArrayList<Customer_Summarization_model> arrayList;
        GetSetValues getSetValues;
        public Get_customer_summarization_tariffwise_value(Handler handler, ArrayList<Customer_Summarization_model> arrayList,GetSetValues getSetValues) {
            this.handler = handler;
            this.arrayList = arrayList;
            this.getSetValues = getSetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("from", params[0]);
            datamap.put("to", params[1]);
            datamap.put("tariff", params[2]);
            datamap.put("subDivision", params[3]);
            datamap.put("company", params[4]);
            datamap.put("zone", params[5]);
            datamap.put("circle", params[6]);
            datamap.put("division", params[7]);
            try {
                response = UrlPostConnection2(PUBLIC_API + "GraphCust_Summ", datamap);
                Log.d("debug","GraphCust_Summ " + response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            receivingData.get_Customer_Summarization_Tariffwise(response, handler, arrayList,getSetValues);
        }
    }

    //For Customer Summarization overall report
    @SuppressLint("StaticFieldLeak")
    public class Get_customer_summarization_yearwise_value extends AsyncTask<String, String, String> {
        Handler handler;
        String response = "";
        ArrayList<Customer_Summarization_model> arrayList;
        GetSetValues getSetValues;
        public Get_customer_summarization_yearwise_value(Handler handler, ArrayList<Customer_Summarization_model> arrayList,GetSetValues getSetValues) {
            this.handler = handler;
            this.arrayList = arrayList;
            this.getSetValues = getSetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("from", params[0]);
            datamap.put("to", params[1]);
            datamap.put("subDivision", params[2]);
            datamap.put("company", params[3]);
            datamap.put("zone", params[4]);
            datamap.put("circle", params[5]);
            datamap.put("division", params[6]);
            try {
                response = UrlPostConnection2(PUBLIC_API + "GraphCust_SummOverAll", datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            receivingData.get_Customer_Summarization_Yearwise(response, handler, arrayList, getSetValues);
        }
    }

    //For Abnormal Summarization Graph report
    @SuppressLint("StaticFieldLeak")
    public class Get_abnormal_summarization_graph_value extends AsyncTask<String, String, String> {
        Handler handler;
        String response = "";
        ArrayList<Abnormal_Subnormal> arrayList;
        GetSetValues getSetValues;
        public Get_abnormal_summarization_graph_value(Handler handler, ArrayList<Abnormal_Subnormal> arrayList, GetSetValues getSetValues) {
            this.handler = handler;
            this.arrayList = arrayList;
            this.getSetValues = getSetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("from", params[0]);
            datamap.put("to", params[1]);
            datamap.put("subDivision", params[2]);
            datamap.put("company", params[3]);
            datamap.put("zone", params[4]);
            datamap.put("circle", params[5]);
            datamap.put("division", params[6]);
            try {
                response = UrlPostConnection2(PUBLIC_API + "ab_graph", datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            receivingData.get_Abnormal_Summarization(response, handler, arrayList, getSetValues);
        }
    }

    //For Subnormal Summarization Graph report
    @SuppressLint("StaticFieldLeak")
    public class Get_subnormal_summarization_graph_value extends AsyncTask<String, String, String> {
        Handler handler;
        String response = "";
        ArrayList<Abnormal_Subnormal> arrayList;
        GetSetValues getSetValues;
        public Get_subnormal_summarization_graph_value(Handler handler, ArrayList<Abnormal_Subnormal> arrayList, GetSetValues getSetValues) {
            this.handler = handler;
            this.arrayList = arrayList;
            this.getSetValues = getSetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            HashMap<String, String> datamap = new HashMap<>();
            datamap.put("from", params[0]);
            datamap.put("to", params[1]);
            datamap.put("subDivision", params[2]);
            datamap.put("company", params[3]);
            datamap.put("zone", params[4]);
            datamap.put("circle", params[5]);
            datamap.put("division", params[6]);
            try {
                response = UrlPostConnection2(PUBLIC_API + "sub_graph", datamap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            receivingData.get_Subnormal_Summarization(response, handler, arrayList, getSetValues);
        }
    }

    //For Deposit Summarization Report
    @SuppressLint("StaticFieldLeak")
    public class Deposit_Summarization_value extends AsyncTask<String, String, String>{
        Handler handler;
        ArrayList<Billing_Summarization_model> arrayList;
        String response="";
        GetSetValues getSetValues;
        String value;

        public Deposit_Summarization_value(Handler handler, ArrayList<Billing_Summarization_model> arrayList,String value,GetSetValues getSetValues) {
            this.handler = handler;
            this.arrayList = arrayList;
            this.value = value;
            this.getSetValues = getSetValues;
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                response = UrlGetConnection1(PUBLIC_API+"depositSumm_g1_data.html?"+value);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            receivingData.get_deposit_summarizaton_value(s,handler,arrayList,getSetValues);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class DisConnection_Summerization extends AsyncTask<String, String, String>{
        Handler handler;
        ArrayList<Billing_Summarization_model> arrayList;
        String response="";
        String value;
        GetSetValues getSetValues;

        public DisConnection_Summerization(Handler handler, ArrayList<Billing_Summarization_model> arrayList, String value,GetSetValues getSetValues) {
            this.handler = handler;
            this.arrayList = arrayList;
            this.value = value;
            this.getSetValues = getSetValues;
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                response = UrlGetConnection1(PUBLIC_API+"disconnectionSummarization_g1_data?"+value);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            receivingData.get_disconnection_summerization(s,handler,arrayList,getSetValues);
        }
    }

    //For UnBilled_Summerization_Tariff
    @SuppressLint("StaticFieldLeak")
    public class UnBilled_Summerization_Tariff extends AsyncTask<String,String,String>{
        Handler handler;
        ArrayList<Billing_Summarization_model> arrayList;
        String value;
        GetSetValues getSetValues;
        String response="";

        public UnBilled_Summerization_Tariff(Handler handler, ArrayList<Billing_Summarization_model> arrayList, String value,GetSetValues getSetValues) {
            this.handler = handler;
            this.arrayList = arrayList;
            this.value = value;
            this.getSetValues = getSetValues;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                response = UrlGetConnection1(PUBLIC_API+"UnbilledWithTariff?"+value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            receivingData.get_unbilled_summarization(s,handler,arrayList,getSetValues);
        }
    }
    //For UnBilled_Summerization_without_Tariff
    @SuppressLint("StaticFieldLeak")
    public class UnBilled_Summerization_without_Tariff extends AsyncTask<String,String,String>{

        Handler handler;
        ArrayList<Billing_Summarization_model> arrayList;
        String value;
        GetSetValues getSetValues;
        String response="";

        public UnBilled_Summerization_without_Tariff(Handler handler, ArrayList<Billing_Summarization_model> arrayList, String value,GetSetValues getSetValues) {
            this.handler = handler;
            this.arrayList = arrayList;
            this.value = value;
            this.getSetValues = getSetValues;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                response = UrlGetConnection1(PUBLIC_API+"UnbilledWithoutTar.html?"+value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            receivingData.get_unbilled_summarization(s,handler,arrayList,getSetValues);
        }
    }

    public class BDA_Tariffwise_value extends AsyncTask<String, String, String> {
        Handler handler;
        String response = "";
        ArrayList<Response> arrayList;
        String value;
        GetSetValues getsetValues;

        public BDA_Tariffwise_value(Handler handler, ArrayList<Response> arrayList, String value, GetSetValues getsetValues) {
            this.handler = handler;
            this.arrayList = arrayList;
            this.value = value;
            this.getsetValues=getsetValues;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                response = UrlGetConnection(PUBLIC_API + "dashboard_g1_data?" + value);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            receivingData.get_BDA_Tariffwise_value(s, handler, arrayList, getsetValues);
        }
    }

    public class Dashborad_details1 extends AsyncTask<String, String, String> {
        String response = "";
        ArrayList<Response> arrayList;
        DashboardAdapter dashboardAdapter;
        Handler handler;
        String value = "";

        public Dashborad_details1(Handler handler, ArrayList<Response> arrayList, DashboardAdapter dashboardAdapter, String value) {
            this.handler = handler;
            this.arrayList = arrayList;
            this.dashboardAdapter = dashboardAdapter;
            this.value = value;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = UrlGetConnection(PUBLIC_API + "homeDashBoardsIndividual?val=" + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getDashboardDetails1(result, handler, arrayList, dashboardAdapter);
        }
    }

    //-----------------------------------------------Fetch------------------------------------------------------------------------------
    @SuppressLint("StaticFieldLeak")
    public class Dashborad_details2 extends AsyncTask<String, String, String> {
        String response = "";
        ArrayList<Response> arrayList;
        Handler handler;
        String value = "";

        public Dashborad_details2(Handler handler, ArrayList<Response> arrayList, String value) {
            this.handler = handler;
            this.arrayList = arrayList;
            this.value = value;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = UrlGetConnection(PUBLIC_API + "homeDashBoardsIndividual?val=" + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getDashboardDetails2(result, handler, arrayList);
        }
    }

    //-----------------------------------------------Fetch------------------------------------------------------------------------------
    @SuppressLint("StaticFieldLeak")
    public class Dashborad_details3 extends AsyncTask<String, String, String> {
        String response = "";
        ArrayList<Response> arrayList;
        Handler handler;
        String value = "";

        public Dashborad_details3(Handler handler, ArrayList<Response> arrayList, String value) {
            this.handler = handler;
            this.arrayList = arrayList;
            this.value = value;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = UrlGetConnection(PUBLIC_API + "homeDashBoardsIndividual?val=" + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getDashboardDetails3(result, handler, arrayList);
        }
    }

    //-----------------------------------------------Fetch------------------------------------------------------------------------------
    @SuppressLint("StaticFieldLeak")
    public class Dashborad_details4 extends AsyncTask<String, String, String> {
        String response = "";
        ArrayList<Response> arrayList;
        Handler handler;
        String value = "";

        public Dashborad_details4(Handler handler, ArrayList<Response> arrayList, String value) {
            this.handler = handler;
            this.arrayList = arrayList;
            this.value = value;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = UrlGetConnection(PUBLIC_API + "homeDashBoardsIndividual?val=" + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getDashboardDetails4(result, handler, arrayList);
        }
    }

    //-----------------------------------------------Fetch------------------------------------------------------------------------------
    @SuppressLint("StaticFieldLeak")
    public class Dashborad_details5 extends AsyncTask<String, String, String> {
        String response = "";
        ArrayList<Response> arrayList;
        Handler handler;
        String value = "";

        public Dashborad_details5(Handler handler, ArrayList<Response> arrayList, String value) {
            this.handler = handler;
            this.arrayList = arrayList;
            this.value = value;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = UrlGetConnection(PUBLIC_API + "homeDashBoardsIndividual?val=" + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getDashboardDetails5(result, handler, arrayList);
        }
    }

    public class OB_CB_Details1 extends AsyncTask<String, String, String> {
        String response = "";
        ArrayList<Response> responseArrayList;
        Handler handler;
        String value = "";
        OBCBAdapter1 obcbAdapter1;

        public OB_CB_Details1(Handler handler, ArrayList<Response> responseArrayList, String value, OBCBAdapter1 obcbAdapter1) {
            this.handler = handler;
            this.responseArrayList = responseArrayList;
            this.value = value;
            this.obcbAdapter1 = obcbAdapter1;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = UrlGetConnection(PUBLIC_API + "ob_cb_monthlyTariffWise_G1_data?" + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getObCbDetails1(result, handler, responseArrayList, obcbAdapter1);
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    @SuppressLint("StaticFieldLeak")
    public class OB_CB_Details2 extends AsyncTask<String, String, String> {
        String response = "";
        ArrayList<Response> responseArrayList;
        Handler handler;
        String value = "";
        OBCBAdapter2 obcbAdapter2;
        GetSetValues getsetValues;

        public OB_CB_Details2(Handler handler, ArrayList<Response> responseArrayList, String value, OBCBAdapter2 obcbAdapter2, GetSetValues getsetValues) {
            this.handler = handler;
            this.responseArrayList = responseArrayList;
            this.value = value;
            this.obcbAdapter2 = obcbAdapter2;
            this.getsetValues=getsetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = UrlGetConnection(PUBLIC_API + "ob_cb_TariffWise_G2_data?" + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getObCbDetails2(result, handler, responseArrayList, obcbAdapter2, getsetValues);
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    @SuppressLint("StaticFieldLeak")
    public class OB_CB_Details3 extends AsyncTask<String, String, String> {
        String response = "";
        ArrayList<Response> responseArrayList;
        Handler handler;
        String value = "";
        OBCBAdapter3 obcbAdapter3;
        GetSetValues getsetValues;

        public OB_CB_Details3(Handler handler, ArrayList<Response> responseArrayList, String value, OBCBAdapter3 obcbAdapter3, GetSetValues getsetValues) {
            this.handler = handler;
            this.responseArrayList = responseArrayList;
            this.value = value;
            this.obcbAdapter3 = obcbAdapter3;
            this.getsetValues=getsetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = UrlGetConnection(PUBLIC_API + "ob_cb_statuswise_G4_data?" + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getObCbDetails3(result, handler, responseArrayList, obcbAdapter3, getsetValues);
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    @SuppressLint("StaticFieldLeak")
    public class OB_CB_Details4 extends AsyncTask<String, String, String> {
        String response = "";
        ArrayList<Response> responseArrayList;
        Handler handler;
        String value = "";
        OBCBAdapter4 obcbAdapter4;
        GetSetValues getsetValues;

        public OB_CB_Details4(Handler handler, ArrayList<Response> responseArrayList, String value, OBCBAdapter4 obcbAdapter4, GetSetValues getsetValues) {
            this.handler = handler;
            this.responseArrayList = responseArrayList;
            this.value = value;
            this.obcbAdapter4 = obcbAdapter4;
            this.getsetValues=getsetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = UrlGetConnection(PUBLIC_API + "ob_cb_summary_G3_data?" + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getObCbDetails4(result, handler, responseArrayList, obcbAdapter4, getsetValues);
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    @SuppressLint("StaticFieldLeak")
    public class OB_CB_Details5 extends AsyncTask<String, String, String> {
        String response = "";
        ArrayList<Response> responseArrayList;
        Handler handler;
        String value = "";
        OBCBBilledAdapter1 obcbBilledAdapter1;

        public OB_CB_Details5(Handler handler, ArrayList<Response> responseArrayList, String value, OBCBBilledAdapter1 obcbBilledAdapter1) {
            this.handler = handler;
            this.responseArrayList = responseArrayList;
            this.value = value;
            this.obcbBilledAdapter1 = obcbBilledAdapter1;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = UrlGetConnection(PUBLIC_API + "ob_cb_Billed_monthlyTariffWise_G1_data?" + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getObCbDetails5(result, handler, responseArrayList, obcbBilledAdapter1);
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    @SuppressLint("StaticFieldLeak")
    public class OB_CB_Details6 extends AsyncTask<String, String, String> {
        String response = "";
        ArrayList<Response> responseArrayList;
        Handler handler;
        String value = "";
        OBCBBilledAdapter2 obcbBilledAdapter2;
        GetSetValues getsetValues;

        public OB_CB_Details6(Handler handler, ArrayList<Response> responseArrayList, String value, OBCBBilledAdapter2 obcbBilledAdapter2, GetSetValues getsetValues) {
            this.handler = handler;
            this.responseArrayList = responseArrayList;
            this.value = value;
            this.obcbBilledAdapter2 = obcbBilledAdapter2;
            this.getsetValues=getsetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = UrlGetConnection(PUBLIC_API + "ob_cb_billed_TariffWise_G2_data?" + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getObCbDetails6(result, handler, responseArrayList, obcbBilledAdapter2, getsetValues);
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    @SuppressLint("StaticFieldLeak")
    public class OB_CB_Details7 extends AsyncTask<String, String, String> {
        String response = "";
        ArrayList<Response> responseArrayList;
        Handler handler;
        String value = "";
        OBCBBilledAdapter3 obcbBilledAdapter3;
        GetSetValues getsetValues;

        public OB_CB_Details7(Handler handler, ArrayList<Response> responseArrayList, String value, OBCBBilledAdapter3 obcbBilledAdapter3, GetSetValues getsetValues) {
            this.handler = handler;
            this.responseArrayList = responseArrayList;
            this.value = value;
            this.obcbBilledAdapter3 = obcbBilledAdapter3;
            this.getsetValues=getsetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = UrlGetConnection(PUBLIC_API + "ob_cb_billed_summary_G3_data?" + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getObCbDetails7(result, handler, responseArrayList, obcbBilledAdapter3, getsetValues);
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    @SuppressLint("StaticFieldLeak")
    public class OB_CB_Details8 extends AsyncTask<String, String, String> {
        String response = "";
        ArrayList<Response> responseArrayList;
        Handler handler;
        String value = "";
        OBCBBilledAdapter4 obcbBilledAdapter4;
        GetSetValues getsetValues;

        public OB_CB_Details8(Handler handler, ArrayList<Response> responseArrayList, String value, OBCBBilledAdapter4 obcbBilledAdapter4) {
            this.handler = handler;
            this.responseArrayList = responseArrayList;
            this.value = value;
            this.obcbBilledAdapter4 = obcbBilledAdapter4;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = UrlGetConnection(PUBLIC_API + "ob_cb_billed_statusWise_summary_G4_data?" + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getObCbDetails8(result, handler, responseArrayList, obcbBilledAdapter4);
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    @SuppressLint("StaticFieldLeak")
    public class OB_CB_Details9 extends AsyncTask<String, String, String> {
        String response = "";
        ArrayList<Response> responseArrayList;
        Handler handler;
        String value = "";
        OBCBUnBilledAdapter1 obcbUnBilledAdapter1;

        public OB_CB_Details9(Handler handler, ArrayList<Response> responseArrayList, String value, OBCBUnBilledAdapter1 obcbUnBilledAdapter1) {
            this.handler = handler;
            this.responseArrayList = responseArrayList;
            this.value = value;
            this.obcbUnBilledAdapter1 = obcbUnBilledAdapter1;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = UrlGetConnection(PUBLIC_API + "ob_cb_unBilled_monthlyTariffWise_G1_data?" + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getObCbDetails9(result, handler, responseArrayList, obcbUnBilledAdapter1);
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    @SuppressLint("StaticFieldLeak")
    public class OB_CB_Details10 extends AsyncTask<String, String, String> {
        String response = "";
        ArrayList<Response> responseArrayList;
        Handler handler;
        String value = "";
        OBCBUnBilledAdapter2 obcbUnBilledAdapter2;
        GetSetValues getsetValues;

        public OB_CB_Details10(Handler handler, ArrayList<Response> responseArrayList, String value, OBCBUnBilledAdapter2 obcbUnBilledAdapter2,
                               GetSetValues getsetValues) {
            this.handler = handler;
            this.responseArrayList = responseArrayList;
            this.value = value;
            this.obcbUnBilledAdapter2 = obcbUnBilledAdapter2;
            this.getsetValues=getsetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = UrlGetConnection(PUBLIC_API + "ob_cb_unBilled_TariffWise_G2_data?" + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getObCbDetails10(result, handler, responseArrayList, obcbUnBilledAdapter2, getsetValues);
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    @SuppressLint("StaticFieldLeak")
    public class OB_CB_Details11 extends AsyncTask<String, String, String> {
        String response = "";
        ArrayList<Response> responseArrayList;
        Handler handler;
        String value = "";
        OBCBUnBilledAdapter3 obcbUnBilledAdapter3;
        GetSetValues getsetValues;

        public OB_CB_Details11(Handler handler, ArrayList<Response> responseArrayList, String value, OBCBUnBilledAdapter3 obcbUnBilledAdapter3,GetSetValues getsetValues) {
            this.handler = handler;
            this.responseArrayList = responseArrayList;
            this.value = value;
            this.obcbUnBilledAdapter3 = obcbUnBilledAdapter3;
            this.getsetValues=getsetValues;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = UrlGetConnection(PUBLIC_API + "ob_cb_Billed_summary_G3_data?" + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getObCbDetails11(result, handler, responseArrayList, obcbUnBilledAdapter3,getsetValues);
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------------
    @SuppressLint("StaticFieldLeak")
    public class OB_CB_Details12 extends AsyncTask<String, String, String> {
        String response = "";
        ArrayList<Response> responseArrayList;
        Handler handler;
        String value = "";
        OBCBUnBilledAdapter4 obcbUnBilledAdapter4;
        GetSetValues getsetValues;

        public OB_CB_Details12(Handler handler, ArrayList<Response> responseArrayList, String value, OBCBUnBilledAdapter4 obcbUnBilledAdapter4) {
            this.handler = handler;
            this.responseArrayList = responseArrayList;
            this.value = value;
            this.obcbUnBilledAdapter4 = obcbUnBilledAdapter4;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = UrlGetConnection(PUBLIC_API + "ob_cb_unBilled_statuswise_G4_data?" + value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            receivingData.getObCbDetails12(result, handler, responseArrayList, obcbUnBilledAdapter4);
        }
    }


}
