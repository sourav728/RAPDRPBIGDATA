package com.tvd.r_apdrpbigdata.invoke;

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
import com.tvd.r_apdrpbigdata.values.FunctionCall;
import com.tvd.r_apdrpbigdata.values.GetSetValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tvd.r_apdrpbigdata.values.Constant.ABNORMAL_SUMMARIZATION_GRAPH_FAILURE;
import static com.tvd.r_apdrpbigdata.values.Constant.ABNORMAL_SUMMARIZATION_GRAPH_SUCCESS;
import static com.tvd.r_apdrpbigdata.values.Constant.BILLING_SUMMARIZATION_TARIFF_WISE_FAILURE;
import static com.tvd.r_apdrpbigdata.values.Constant.BILLING_SUMMARIZATION_TARIFF_WISE_SUCCESS;
import static com.tvd.r_apdrpbigdata.values.Constant.BILLING_SUMMARIZATION_YEAR_WISE_FAILURE;
import static com.tvd.r_apdrpbigdata.values.Constant.BILLING_SUMMARIZATION_YEAR_WISE_SUCCESS;
import static com.tvd.r_apdrpbigdata.values.Constant.CUSTOMER_SUMMARIZATION_TARIFF_WISE_FAILURE;
import static com.tvd.r_apdrpbigdata.values.Constant.CUSTOMER_SUMMARIZATION_TARIFF_WISE_SUCCESS;
import static com.tvd.r_apdrpbigdata.values.Constant.CUSTOMER_SUMMARIZATION_YEAR_WISE_FAILURE;
import static com.tvd.r_apdrpbigdata.values.Constant.CUSTOMER_SUMMARIZATION_YEAR_WISE_SUCCESS;
import static com.tvd.r_apdrpbigdata.values.Constant.DEPOSIT_SUMMARIZATION_FAILURE;
import static com.tvd.r_apdrpbigdata.values.Constant.DEPOSIT_SUMMARIZATION_SUCCESS;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_FAILURE;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_FAILURE1;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_FAILURE2;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_FAILURE3;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_FAILURE4;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_SUCCESS;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_SUCCESS1;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_SUCCESS2;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_SUCCESS3;
import static com.tvd.r_apdrpbigdata.values.Constant.DIALOG_SUCCESS4;
import static com.tvd.r_apdrpbigdata.values.Constant.DISCONNECTION_SUMMARIZATION_FAILURE;
import static com.tvd.r_apdrpbigdata.values.Constant.DISCONNECTION_SUMMARIZATION_SUCCESS;
import static com.tvd.r_apdrpbigdata.values.Constant.PAYMENT_SUMMARIZATION_TARIFF_WISE_FAILURE;
import static com.tvd.r_apdrpbigdata.values.Constant.PAYMENT_SUMMARIZATION_TARIFF_WISE_SUCCESS;
import static com.tvd.r_apdrpbigdata.values.Constant.PAYMENT_SUMMARIZATION_YEAR_WISE_FAILURE;
import static com.tvd.r_apdrpbigdata.values.Constant.PAYMENT_SUMMARIZATION_YEAR_WISE_SUCCESS;
import static com.tvd.r_apdrpbigdata.values.Constant.SUBNORMAL_SUMMARIZATION_GRAPH_FAILURE;
import static com.tvd.r_apdrpbigdata.values.Constant.SUBNORMAL_SUMMARIZATION_GRAPH_SUCCESS;
import static com.tvd.r_apdrpbigdata.values.Constant.UNBILLED_SUMMARIZATION_FAILURE;
import static com.tvd.r_apdrpbigdata.values.Constant.UNBILLED_SUMMARIZATION_SUCCESS;

public class ReceivingData {
    private FunctionCall functionCall = new FunctionCall();

    //For Billing_Summarization Report
    void get_Billing_Summarization_Tariffwise(String result, Handler handler, ArrayList<Billing_Summarization_model> arrayList, GetSetValues getSetValues) {
        JSONArray array, array1;
        arrayList.clear();
        try {
            array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                ArrayList<Billing_Summarization_model> arrayList1 = new ArrayList<>();
                Billing_Summarization_model billing_summarization_model = new Billing_Summarization_model();
                array1 = new JSONArray(array.getString(i));
                for (int j = 0; j < array1.length(); j++) {
                    Billing_Summarization_model billing_summarization_model1 = new Billing_Summarization_model();
                    billing_summarization_model1.setValue(array1.getString(j));
                    arrayList1.add(billing_summarization_model1);
                }
                if (getSetValues.getDates_equal().equals("N")){
                    billing_summarization_model.setYear(arrayList1.get(0).getValue());
                    Log.d("debug", "Year " + arrayList1.get(0).getValue());
                    billing_summarization_model.setInstallations(arrayList1.get(1).getValue());
                    Log.d("debug", "Install " + arrayList1.get(1).getValue());
                    billing_summarization_model.setConsumed_unit(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(2).getValue())));
                    Log.d("debug", "ConsumedUnit " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(2).getValue())));
                    billing_summarization_model.setCurrent_bill_amt(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                    Log.d("debug", "CurrentAmt" + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                }else {
                    billing_summarization_model.setYear(arrayList1.get(0).getValue());
                    Log.d("debug", "Year " + arrayList1.get(0).getValue());
                    billing_summarization_model.setMonth(arrayList1.get(1).getValue());
                    Log.d("debug", "Month " + arrayList1.get(1).getValue());
                    billing_summarization_model.setInstallations(arrayList1.get(2).getValue());
                    Log.d("debug", "Install " + arrayList1.get(2).getValue());
                    billing_summarization_model.setConsumed_unit(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                    Log.d("debug", "ConsumedUnit " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                    billing_summarization_model.setCurrent_bill_amt(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(4).getValue())));
                    Log.d("debug", "CurrentAmt" + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(4).getValue())));
                }

                arrayList.add(billing_summarization_model);

                if (arrayList.size() > 0)
                    handler.sendEmptyMessage(BILLING_SUMMARIZATION_TARIFF_WISE_SUCCESS);
                else handler.sendEmptyMessage(BILLING_SUMMARIZATION_TARIFF_WISE_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //For Billing Summarization Yearwise Report
    void get_Billing_Summarization_Yearwise(String result, Handler handler, ArrayList<Billing_Summarization_model> arrayList, GetSetValues getSetValues) {
        JSONArray array, array1;
        arrayList.clear();
        try {
            array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                ArrayList<Billing_Summarization_model> arrayList1 = new ArrayList<>();
                Billing_Summarization_model billing_summarization_model = new Billing_Summarization_model();
                array1 = new JSONArray(array.getString(i));
                for (int j = 0; j < array1.length(); j++) {
                    Billing_Summarization_model billing_summarization_model1 = new Billing_Summarization_model();
                    billing_summarization_model1.setValue(array1.getString(j));
                    arrayList1.add(billing_summarization_model1);
                }
                if (getSetValues.getDates_equal().equals("N")){
                    billing_summarization_model.setYear(arrayList1.get(0).getValue());
                    Log.d("debug", "Year " + arrayList1.get(0).getValue());
                    billing_summarization_model.setInstallations(arrayList1.get(1).getValue());
                    Log.d("debug", "Install " + arrayList1.get(1).getValue());
                    billing_summarization_model.setConsumed_unit(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(2).getValue())));
                    Log.d("debug", "ConsumedUnit " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(2).getValue())));
                    billing_summarization_model.setCurrent_bill_amt(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                    Log.d("debug", "CurrentAmt" + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                }else {
                    billing_summarization_model.setYear(arrayList1.get(0).getValue());
                    Log.d("debug", "Year " + arrayList1.get(0).getValue());
                    billing_summarization_model.setMonth(arrayList1.get(1).getValue());
                    Log.d("debug", "Month " + arrayList1.get(1).getValue());
                    billing_summarization_model.setInstallations(arrayList1.get(2).getValue());
                    Log.d("debug", "Install " + arrayList1.get(2).getValue());
                    billing_summarization_model.setConsumed_unit(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                    Log.d("debug", "ConsumedUnit " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                    billing_summarization_model.setCurrent_bill_amt(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(4).getValue())));
                    Log.d("debug", "CurrentAmt" + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(4).getValue())));
                }

                arrayList.add(billing_summarization_model);

                if (arrayList.size() > 0)
                    handler.sendEmptyMessage(BILLING_SUMMARIZATION_YEAR_WISE_SUCCESS);
                else handler.sendEmptyMessage(BILLING_SUMMARIZATION_YEAR_WISE_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //For Payment Summarization Yearwise Report
    void get_Payment_Summarization_Yearwise(String result, Handler handler, ArrayList<Payment_Summarization_model> arrayList, GetSetValues getSetValues) {
        JSONArray array, array1;
        arrayList.clear();
        try {
            array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                ArrayList<Payment_Summarization_model> arrayList1 = new ArrayList<>();
                Payment_Summarization_model payment_summarization_model = new Payment_Summarization_model();
                array1 = new JSONArray(array.getString(i));
                for (int j = 0; j < array1.length(); j++) {
                    Payment_Summarization_model payment_summarization_model1 = new Payment_Summarization_model();
                    payment_summarization_model1.setValue(array1.getString(j));
                    arrayList1.add(payment_summarization_model1);
                }
                if (getSetValues.getDates_equal().equals("N")){
                    payment_summarization_model.setYear(arrayList1.get(0).getValue());
                    Log.d("debug", "Year " + arrayList1.get(0).getValue());
                    payment_summarization_model.setInstallations(arrayList1.get(1).getValue());
                    Log.d("debug", "Instalations " + arrayList1.get(1).getValue());
                    payment_summarization_model.setOpening_balance(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(2).getValue())));
                    Log.d("debug", "Opening Balance " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(2).getValue())));
                    payment_summarization_model.setConsumed_unit(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                    Log.d("debug", "Consumed unit " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                    payment_summarization_model.setDemand(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(4).getValue())));
                    Log.d("debug", "Demand " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(4).getValue())));
                    payment_summarization_model.setNet_pay_amount(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(5).getValue())));
                    Log.d("debug", "Net Pay Amount " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(5).getValue())));
                    payment_summarization_model.setCollection_amt(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(6).getValue())));
                    Log.d("debug", "Collection Amount " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(6).getValue())));
                    payment_summarization_model.setClosing_balance_amt(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(7).getValue())));
                    Log.d("debug", "Closing Balance Amount " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(7).getValue())));
                }else {
                    payment_summarization_model.setYear(arrayList1.get(0).getValue());
                    Log.d("debug", "Year " + arrayList1.get(0).getValue());
                    payment_summarization_model.setMonth(arrayList1.get(1).getValue());
                    Log.d("debug", "Month " + arrayList1.get(1).getValue());
                    payment_summarization_model.setInstallations(arrayList1.get(2).getValue());
                    Log.d("debug", "Instalations " + arrayList1.get(2).getValue());
                    payment_summarization_model.setOpening_balance(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                    Log.d("debug", "Opening Balance " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                    payment_summarization_model.setConsumed_unit(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(4).getValue())));
                    Log.d("debug", "Consumed unit " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(4).getValue())));
                    payment_summarization_model.setDemand(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(5).getValue())));
                    Log.d("debug", "Demand " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(5).getValue())));
                    payment_summarization_model.setNet_pay_amount(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(6).getValue())));
                    Log.d("debug", "Net Pay Amount " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(6).getValue())));
                    payment_summarization_model.setCollection_amt(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(7).getValue())));
                    Log.d("debug", "Collection Amount " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(7).getValue())));
                    payment_summarization_model.setClosing_balance_amt(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(8).getValue())));
                    Log.d("debug", "Closing Balance Amount " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(8).getValue())));
                }

                arrayList.add(payment_summarization_model);

                if (arrayList.size() > 0)
                    handler.sendEmptyMessage(PAYMENT_SUMMARIZATION_YEAR_WISE_SUCCESS);
                else handler.sendEmptyMessage(PAYMENT_SUMMARIZATION_YEAR_WISE_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //For Payment Summarization TariffWise Report
    void get_Payment_Summarization_Tariffwise(String result, Handler handler, ArrayList<Payment_Summarization_model> arrayList, GetSetValues getSetValues) {
        JSONArray array, array1;
        arrayList.clear();
        try {
            array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                ArrayList<Payment_Summarization_model> arrayList1 = new ArrayList<>();
                Payment_Summarization_model payment_summarization_model = new Payment_Summarization_model();
                array1 = new JSONArray(array.getString(i));
                for (int j = 0; j < array1.length(); j++) {
                    Payment_Summarization_model payment_summarization_model1 = new Payment_Summarization_model();
                    payment_summarization_model1.setValue(array1.getString(j));
                    arrayList1.add(payment_summarization_model1);
                }
                if (getSetValues.getDates_equal().equals("N")){
                    payment_summarization_model.setYear(arrayList1.get(0).getValue());
                    Log.d("debug", "Year " + arrayList1.get(0).getValue());
                    payment_summarization_model.setInstallations(arrayList1.get(1).getValue());
                    Log.d("debug", "Instalations " + arrayList1.get(1).getValue());
                    payment_summarization_model.setOpening_balance(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(2).getValue())));
                    Log.d("debug", "Opening Balance " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(2).getValue())));
                    payment_summarization_model.setConsumed_unit(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                    Log.d("debug", "Consumed unit " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                    payment_summarization_model.setDemand(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(4).getValue())));
                    Log.d("debug", "Demand " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(4).getValue())));
                    payment_summarization_model.setNet_pay_amount(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(5).getValue())));
                    Log.d("debug", "Net Pay Amount " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(5).getValue())));
                    payment_summarization_model.setCollection_amt(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(6).getValue())));
                    Log.d("debug", "Collection Amount " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(6).getValue())));
                    payment_summarization_model.setClosing_balance_amt(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(7).getValue())));
                    Log.d("debug", "Closing Balance Amount " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(7).getValue())));
                }else {
                    payment_summarization_model.setYear(arrayList1.get(0).getValue());
                    Log.d("debug", "Year " + arrayList1.get(0).getValue());
                    payment_summarization_model.setMonth(arrayList1.get(1).getValue());
                    Log.d("debug", "Month " + arrayList1.get(1).getValue());

                    payment_summarization_model.setInstallations(arrayList1.get(2).getValue());
                    Log.d("debug", "Instalations " + arrayList1.get(2).getValue());
                    payment_summarization_model.setOpening_balance(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                    Log.d("debug", "Opening Balance " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                    payment_summarization_model.setConsumed_unit(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(4).getValue())));
                    Log.d("debug", "Consumed unit " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(4).getValue())));
                    payment_summarization_model.setDemand(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(5).getValue())));
                    Log.d("debug", "Demand " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(5).getValue())));
                    payment_summarization_model.setNet_pay_amount(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(6).getValue())));
                    Log.d("debug", "Net Pay Amount " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(6).getValue())));
                    payment_summarization_model.setCollection_amt(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(7).getValue())));
                    Log.d("debug", "Collection Amount " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(7).getValue())));
                    payment_summarization_model.setClosing_balance_amt(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(8).getValue())));
                    Log.d("debug", "Closing Balance Amount " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(8).getValue())));
                }

                arrayList.add(payment_summarization_model);

                if (arrayList.size() > 0)
                    handler.sendEmptyMessage(PAYMENT_SUMMARIZATION_TARIFF_WISE_SUCCESS);
                else handler.sendEmptyMessage(PAYMENT_SUMMARIZATION_TARIFF_WISE_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //For Customer_Summarization_Tariffwise Report
    void get_Customer_Summarization_Tariffwise(String result, Handler handler, ArrayList<Customer_Summarization_model> arrayList, GetSetValues getSetValues) {
        JSONArray array, array1;
        arrayList.clear();
        try {
            array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                ArrayList<Customer_Summarization_model> arrayList1 = new ArrayList<>();
                Customer_Summarization_model customer_summarization_model = new Customer_Summarization_model();
                array1 = new JSONArray(array.getString(i));
                for (int j = 0; j < array1.length(); j++) {
                    Customer_Summarization_model customer_summarization_model1 = new Customer_Summarization_model();
                    customer_summarization_model1.setValue(array1.getString(j));
                    arrayList1.add(customer_summarization_model1);
                }
                if (getSetValues.getDates_equal().equals("N")){
                    customer_summarization_model.setYear(arrayList1.get(0).getValue());
                    Log.d("debug", "Year " + arrayList1.get(0).getValue());
                    customer_summarization_model.setInstallation(arrayList1.get(1).getValue());
                    Log.d("debug", "Install " + arrayList1.get(1).getValue());
                }else {
                    customer_summarization_model.setYear(arrayList1.get(0).getValue());
                    Log.d("debug", "Year " + arrayList1.get(0).getValue());
                    customer_summarization_model.setMonth(arrayList1.get(1).getValue());
                    Log.d("debug","Month " + arrayList1.get(1).getValue());
                    customer_summarization_model.setInstallation(arrayList1.get(2).getValue());
                    Log.d("debug", "Install " + arrayList1.get(2).getValue());
                }

                arrayList.add(customer_summarization_model);
                if (arrayList.size() > 0)
                    handler.sendEmptyMessage(CUSTOMER_SUMMARIZATION_TARIFF_WISE_SUCCESS);
                else handler.sendEmptyMessage(CUSTOMER_SUMMARIZATION_TARIFF_WISE_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //For Customer Summarization Yearwise Report
    void get_Customer_Summarization_Yearwise(String result, Handler handler, ArrayList<Customer_Summarization_model> arrayList, GetSetValues getSetValues) {
        JSONArray array, array1;
        arrayList.clear();
        try {
            array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                ArrayList<Customer_Summarization_model> arrayList1 = new ArrayList<>();
                Customer_Summarization_model customer_summarization_model = new Customer_Summarization_model();
                array1 = new JSONArray(array.getString(i));
                for (int j = 0; j < array1.length(); j++) {
                    Customer_Summarization_model customer_summarization_model1 = new Customer_Summarization_model();
                    customer_summarization_model1.setValue(array1.getString(j));
                    arrayList1.add(customer_summarization_model1);
                }
                if (getSetValues.getDates_equal().equals("N")){
                    customer_summarization_model.setYear(arrayList1.get(0).getValue());
                    Log.d("debug", "Year " + arrayList1.get(0).getValue());
                    customer_summarization_model.setInstallation(arrayList1.get(1).getValue());
                    Log.d("debug", "Install " + arrayList1.get(1).getValue());
                }else {
                    customer_summarization_model.setYear(arrayList1.get(0).getValue());
                    Log.d("debug", "Year " + arrayList1.get(0).getValue());
                    customer_summarization_model.setMonth(arrayList1.get(1).getValue());
                    Log.d("debug","Month " + arrayList1.get(1).getValue());
                    customer_summarization_model.setInstallation(arrayList1.get(2).getValue());
                    Log.d("debug", "Install " + arrayList1.get(2).getValue());
                }

                arrayList.add(customer_summarization_model);

                if (arrayList.size() > 0)
                    handler.sendEmptyMessage(CUSTOMER_SUMMARIZATION_YEAR_WISE_SUCCESS);
                else handler.sendEmptyMessage(CUSTOMER_SUMMARIZATION_YEAR_WISE_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //For Abnormal_Subnormal Summarization  Report
    void get_Abnormal_Summarization(String result, Handler handler, ArrayList<Abnormal_Subnormal> arrayList, GetSetValues getSetValues) {
        JSONArray array, array1;
        arrayList.clear();
        try {
            array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                ArrayList<Abnormal_Subnormal> arrayList1 = new ArrayList<>();
                Abnormal_Subnormal abnormal_subnormal = new Abnormal_Subnormal();
                array1 = new JSONArray(array.getString(i));
                for (int j = 0; j < array1.length(); j++) {
                    Abnormal_Subnormal abnormal_subnormal1 = new Abnormal_Subnormal();
                    abnormal_subnormal1.setValue(array1.getString(j));
                    arrayList1.add(abnormal_subnormal1);
                }
                if (getSetValues.getDates_equal().equals("N")){
                    abnormal_subnormal.setYear(arrayList1.get(0).getValue());
                    Log.d("debug", "Year " + arrayList1.get(0).getValue());
                    abnormal_subnormal.setBilled_installation(arrayList1.get(1).getValue());
                    Log.d("debug", "Billed Install " + arrayList1.get(1).getValue());
                    abnormal_subnormal.setAbnormal_installation(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(2).getValue())));
                    Log.d("debug", "Abnormal Install " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(2).getValue())));
                }else {
                    abnormal_subnormal.setYear(arrayList1.get(0).getValue());
                    Log.d("debug", "Year " + arrayList1.get(0).getValue());
                    abnormal_subnormal.setMonth(arrayList1.get(1).getValue());
                    Log.d("debug", "Month " + arrayList1.get(1).getValue());
                    abnormal_subnormal.setBilled_installation(arrayList1.get(2).getValue());
                    Log.d("debug", "Billed Install " + arrayList1.get(2).getValue());
                    abnormal_subnormal.setAbnormal_installation(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                    Log.d("debug", "Abnormal Install " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                }

                arrayList.add(abnormal_subnormal);

                if (arrayList.size() > 0)
                    handler.sendEmptyMessage(ABNORMAL_SUMMARIZATION_GRAPH_SUCCESS);
                else handler.sendEmptyMessage(ABNORMAL_SUMMARIZATION_GRAPH_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //For Subnormal Summarization  Report
    void get_Subnormal_Summarization(String result, Handler handler, ArrayList<Abnormal_Subnormal> arrayList, GetSetValues getSetValues) {
        JSONArray array, array1;
        arrayList.clear();
        try {
            array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                ArrayList<Abnormal_Subnormal> arrayList1 = new ArrayList<>();
                Abnormal_Subnormal abnormal_subnormal = new Abnormal_Subnormal();
                array1 = new JSONArray(array.getString(i));
                for (int j = 0; j < array1.length(); j++) {
                    Abnormal_Subnormal abnormal_subnormal1 = new Abnormal_Subnormal();
                    abnormal_subnormal1.setValue(array1.getString(j));
                    arrayList1.add(abnormal_subnormal1);
                }
                if (getSetValues.getDates_equal().equals("N")){
                    abnormal_subnormal.setYear(arrayList1.get(0).getValue());
                    Log.d("debug", "Year " + arrayList1.get(0).getValue());
                    abnormal_subnormal.setBilled_installation(arrayList1.get(1).getValue());
                    Log.d("debug", "Billed Install " + arrayList1.get(1).getValue());
                    abnormal_subnormal.setSubnormal_installation(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(2).getValue())));
                    Log.d("debug", "Subnormal Install " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(2).getValue())));
                }else {
                    abnormal_subnormal.setYear(arrayList1.get(0).getValue());
                    Log.d("debug", "Year " + arrayList1.get(0).getValue());
                    abnormal_subnormal.setMonth(arrayList1.get(1).getValue());
                    Log.d("debug","Month " + arrayList1.get(1).getValue());
                    abnormal_subnormal.setBilled_installation(arrayList1.get(2).getValue());
                    Log.d("debug", "Billed Install " + arrayList1.get(2).getValue());
                    abnormal_subnormal.setSubnormal_installation(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                    Log.d("debug", "Subnormal Install " + functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                }

                arrayList.add(abnormal_subnormal);

                if (arrayList.size() > 0)
                    handler.sendEmptyMessage(SUBNORMAL_SUMMARIZATION_GRAPH_SUCCESS);
                else handler.sendEmptyMessage(SUBNORMAL_SUMMARIZATION_GRAPH_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void get_deposit_summarizaton_value(String result, Handler handler, ArrayList<Billing_Summarization_model> arrayList,GetSetValues getSetValues) {
        arrayList.clear();
        JSONArray jsonArray1, jsonArray2;

        try {
            jsonArray1 = new JSONArray(result);
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Billing_Summarization_model> arrayList1 = new ArrayList<>();
                Billing_Summarization_model billing_summarization_model = new Billing_Summarization_model();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Billing_Summarization_model billing_summarization_model1 = new Billing_Summarization_model();
                    if (!jsonArray2.getString(j).isEmpty())
                        billing_summarization_model1.setValue(jsonArray2.getString(j));
                    else billing_summarization_model1.setValue("0.0");
                    arrayList1.add(billing_summarization_model1);
                }


                if(getSetValues.getDates_equal().equals("N")){
                    billing_summarization_model.setYear(arrayList1.get(0).getValue());
                    billing_summarization_model.setInstallations(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(1).getValue())));
                    billing_summarization_model.setDeposit_amt(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(2).getValue())));


                }else {
                    billing_summarization_model.setYear(arrayList1.get(0).getValue());
                    billing_summarization_model.setMonth(arrayList1.get(1).getValue());
                    billing_summarization_model.setInstallations(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(2).getValue())));
                    billing_summarization_model.setDeposit_amt(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                }
                arrayList.add(billing_summarization_model);
            }

            if (arrayList.size() > 0)
                handler.sendEmptyMessage(DEPOSIT_SUMMARIZATION_SUCCESS);
            else handler.sendEmptyMessage(DEPOSIT_SUMMARIZATION_FAILURE);
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DEPOSIT_SUMMARIZATION_FAILURE);
        }
    }

    void get_disconnection_summerization(String result, Handler handler, ArrayList<Billing_Summarization_model> arrayList, GetSetValues getSetValues){
        arrayList.clear();
        JSONArray jsonArray1, jsonArray2;

        try {
            jsonArray1 = new JSONArray(result);
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Billing_Summarization_model> arrayList1 = new ArrayList<>();
                Billing_Summarization_model billing_summarization_model = new Billing_Summarization_model();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Billing_Summarization_model billing_summarization_model1 = new Billing_Summarization_model();
                    if (!jsonArray2.getString(j).isEmpty())
                        billing_summarization_model1.setValue(jsonArray2.getString(j));
                    else billing_summarization_model1.setValue("0.0");
                    arrayList1.add(billing_summarization_model1);
                }
                if(getSetValues.getDates_equal().equals("N")){
                    billing_summarization_model.setYear(arrayList1.get(0).getValue());
                    billing_summarization_model.setInstallations(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(1).getValue())));
                    billing_summarization_model.setConnecton_type((arrayList1.get(2).getValue()));

                }else {
                    billing_summarization_model.setYear(arrayList1.get(0).getValue());
                    billing_summarization_model.setMonth(arrayList1.get(1).getValue());
                    billing_summarization_model.setInstallations(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(2).getValue())));
                    billing_summarization_model.setConnecton_type((arrayList1.get(3).getValue()));

                }
                arrayList.add(billing_summarization_model);

            }

            if (arrayList.size() > 0)
                handler.sendEmptyMessage(DISCONNECTION_SUMMARIZATION_SUCCESS);
            else handler.sendEmptyMessage(DISCONNECTION_SUMMARIZATION_FAILURE);
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DISCONNECTION_SUMMARIZATION_FAILURE);
        }
    }

    //For Unbilled Summarization
    void get_unbilled_summarization(String result,Handler handler,ArrayList<Billing_Summarization_model> arrayList,GetSetValues getSetValues){
        arrayList.clear();
        JSONArray jsonArray1, jsonArray2;

        try {
            jsonArray1 = new JSONArray(result);
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Billing_Summarization_model> arrayList1 = new ArrayList<>();
                Billing_Summarization_model billing_summarization_model = new Billing_Summarization_model();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Billing_Summarization_model billing_summarization_model1 = new Billing_Summarization_model();
                    if (!jsonArray2.getString(j).isEmpty())
                        billing_summarization_model1.setValue(jsonArray2.getString(j));
                    else billing_summarization_model1.setValue("0.0");
                    arrayList1.add(billing_summarization_model1);
                }
                if(getSetValues.getDates_equal().equals("N")){
                    billing_summarization_model.setYear(arrayList1.get(0).getValue());
                    billing_summarization_model.setInstallations(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(1).getValue())));

                }else {
                    billing_summarization_model.setYear(arrayList1.get(0).getValue());
                    billing_summarization_model.setMonth(arrayList1.get(1).getValue());
                    if(arrayList1.size()>3)
                        billing_summarization_model.setInstallations(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(3).getValue())));
                    else billing_summarization_model.setInstallations(functionCall.decimalroundoff(Double.parseDouble(arrayList1.get(2).getValue())));
                }
                arrayList.add(billing_summarization_model);

            }

            if (arrayList.size() > 0)
                handler.sendEmptyMessage(UNBILLED_SUMMARIZATION_SUCCESS);
            else handler.sendEmptyMessage(UNBILLED_SUMMARIZATION_FAILURE);
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(UNBILLED_SUMMARIZATION_FAILURE);
        }
    }

    void get_BDA_Tariffwise_value(String result, Handler handler, ArrayList<Response> arrayList, GetSetValues getsetValues) {
        arrayList.clear();
        JSONArray jsonArray1, jsonArray2;
        try {
            jsonArray1 = new JSONArray(result);
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Response> arrayList1 = new ArrayList<>();
                Response response = new Response();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Response response1 = new Response();
                    if (jsonArray2.getString(j).equals("null")) {
                        response1.setValue("0");
                    } else response1.setValue(jsonArray2.getString(j));
                    arrayList1.add(response1);
                }
                if (getsetValues.getMonth_flag().equals("N")) {
                    response.setA1(arrayList1.get(0).getValue());
                    response.setA2(functionCall.roundoff1(arrayList1.get(1).getValue()));
                    response.setA3(functionCall.roundoff1(arrayList1.get(2).getValue()));
                    response.setA4(functionCall.roundoff1(arrayList1.get(3).getValue()));
                    response.setA5(functionCall.roundoff1(arrayList1.get(4).getValue()));
                    response.setA6(functionCall.roundoff1(arrayList1.get(5).getValue()));
                    response.setA7(functionCall.roundoff1(arrayList1.get(6).getValue()));
                    response.setA8(functionCall.roundoff1(arrayList1.get(7).getValue()));
                } else {
                    response.setA1(arrayList1.get(0).getValue());
                    response.setMonth(arrayList1.get(1).getValue());
                    response.setA2(functionCall.roundoff1(arrayList1.get(2).getValue()));
                    response.setA3(functionCall.roundoff1(arrayList1.get(3).getValue()));
                    response.setA4(functionCall.roundoff1(arrayList1.get(4).getValue()));
                    response.setA5(functionCall.roundoff1(arrayList1.get(5).getValue()));
                    response.setA6(functionCall.roundoff1(arrayList1.get(6).getValue()));
                    response.setA7(functionCall.roundoff1(arrayList1.get(7).getValue()));
                    response.setA8(functionCall.roundoff1(arrayList1.get(8).getValue()));
                }
                arrayList.add(response);
            }

            if (arrayList.size() > 0)
                handler.sendEmptyMessage(DIALOG_SUCCESS);
            else handler.sendEmptyMessage(DIALOG_FAILURE);
        } catch (JSONException e) {
            handler.sendEmptyMessage(DIALOG_FAILURE);
            e.printStackTrace();
        }
    }

    void getDashboardDetails1(String result, Handler handler, ArrayList<Response> responseArrayList, DashboardAdapter dashboardAdapter) {
        responseArrayList.clear();
        JSONArray jsonArray1, jsonArray2;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
            jsonArray1 = new JSONArray(jsonObject.getString("object1"));
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Response> arrayList1 = new ArrayList<>();
                Response response = new Response();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Response response1 = new Response();
                    if (jsonArray2.getString(j).equals("null")) {
                        response1.setValue("0");
                    } else response1.setValue(jsonArray2.getString(j));
                    arrayList1.add(response1);
                }
                response.setA1(arrayList1.get(0).getValue());
                response.setA2(arrayList1.get(1).getValue());
                response.setA3(arrayList1.get(2).getValue());
                response.setA4(arrayList1.get(3).getValue());
                response.setA5(arrayList1.get(4).getValue());
                response.setA6(arrayList1.get(5).getValue());
                response.setA7(arrayList1.get(6).getValue());
                response.setA8(arrayList1.get(7).getValue());
//                response.setA9(arrayList1.get(8).getValue());
                responseArrayList.add(response);
                dashboardAdapter.notifyDataSetChanged();
                if (responseArrayList.size() > 0)
                    handler.sendEmptyMessage(DIALOG_SUCCESS);
                else handler.sendEmptyMessage(DIALOG_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DIALOG_FAILURE);
        }
    }



    //---------------------------------------------------------------------------------------------------------------------------
    void getDashboardDetails2(String result, Handler handler, ArrayList<Response> responseArrayList) {
        responseArrayList.clear();
        JSONArray jsonArray1, jsonArray2;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
            jsonArray1 = new JSONArray(jsonObject.getString("object2"));
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Response> arrayList1 = new ArrayList<>();
                Response response = new Response();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Response response1 = new Response();
                    if (jsonArray2.getString(j).equals("null")) {
                        response1.setValue("0");
                    } else response1.setValue(jsonArray2.getString(j));
                    arrayList1.add(response1);
                }
                response.setA1(arrayList1.get(0).getValue());
                response.setA2(arrayList1.get(1).getValue());
                response.setA3(arrayList1.get(2).getValue());
                response.setA4(arrayList1.get(3).getValue());
                response.setA5(arrayList1.get(4).getValue());
                response.setA6(arrayList1.get(5).getValue());
                response.setA7(arrayList1.get(6).getValue());
                response.setA8(arrayList1.get(7).getValue());
//                response.setA9(arrayList1.get(8).getValue());
                responseArrayList.add(response);
                if (responseArrayList.size() > 0)
                    handler.sendEmptyMessage(DIALOG_SUCCESS1);
                else handler.sendEmptyMessage(DIALOG_FAILURE1);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DIALOG_FAILURE1);
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------
    void getDashboardDetails3(String result, Handler handler, ArrayList<Response> responseArrayList) {
        responseArrayList.clear();
        JSONArray jsonArray1, jsonArray2;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
            jsonArray1 = new JSONArray(jsonObject.getString("object4"));
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Response> arrayList1 = new ArrayList<>();
                Response response = new Response();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Response response1 = new Response();
                    if (jsonArray2.getString(j).equals("null")) {
                        response1.setValue("0");
                    } else response1.setValue(jsonArray2.getString(j));
                    arrayList1.add(response1);
                }
                response.setA1(arrayList1.get(0).getValue());
                response.setA2(arrayList1.get(1).getValue());
                response.setA3(arrayList1.get(2).getValue());
                response.setA4(arrayList1.get(3).getValue());
                response.setA5(arrayList1.get(4).getValue());
                response.setA6(arrayList1.get(5).getValue());
                response.setA7(arrayList1.get(6).getValue());
                response.setA8(arrayList1.get(7).getValue());
//                response.setA9(arrayList1.get(8).getValue());
                responseArrayList.add(response);
                if (responseArrayList.size() > 0)
                    handler.sendEmptyMessage(DIALOG_SUCCESS2);
                else handler.sendEmptyMessage(DIALOG_FAILURE2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DIALOG_FAILURE2);
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------
    void getDashboardDetails4(String result, Handler handler, ArrayList<Response> responseArrayList) {
        responseArrayList.clear();
        JSONArray jsonArray1, jsonArray2;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
            jsonArray1 = new JSONArray(jsonObject.getString("object3"));
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Response> arrayList1 = new ArrayList<>();
                Response response = new Response();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Response response1 = new Response();
                    if (jsonArray2.getString(j).equals("null")) {
                        response1.setValue("0");
                    } else response1.setValue(jsonArray2.getString(j));
                    arrayList1.add(response1);
                }
                response.setA1(arrayList1.get(0).getValue());
                response.setA2(arrayList1.get(1).getValue());
                response.setA3(arrayList1.get(2).getValue());
                response.setA4(arrayList1.get(3).getValue());
                response.setA5(arrayList1.get(4).getValue());
                response.setA6(arrayList1.get(5).getValue());
                response.setA7(arrayList1.get(6).getValue());
                response.setA8(arrayList1.get(7).getValue());
//                response.setA9(arrayList1.get(8).getValue());

                responseArrayList.add(response);
                if (responseArrayList.size() > 0)
                    handler.sendEmptyMessage(DIALOG_SUCCESS3);
                else handler.sendEmptyMessage(DIALOG_FAILURE3);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DIALOG_FAILURE3);
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------
    void getDashboardDetails5(String result, Handler handler, ArrayList<Response> responseArrayList) {
        responseArrayList.clear();
        JSONArray jsonArray1, jsonArray2;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
            jsonArray1 = new JSONArray(jsonObject.getString("object5"));
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Response> arrayList1 = new ArrayList<>();
                Response response = new Response();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Response response1 = new Response();
                    if (jsonArray2.getString(j).equals("null")) {
                        response1.setValue("0");
                    } else response1.setValue(jsonArray2.getString(j));
                    arrayList1.add(response1);
                }
                response.setA1(arrayList1.get(0).getValue());
                response.setA2(arrayList1.get(1).getValue());
                response.setA3(arrayList1.get(2).getValue());
                response.setA4(arrayList1.get(3).getValue());
                response.setA5(arrayList1.get(4).getValue());
                response.setA6(arrayList1.get(5).getValue());
                response.setA7(arrayList1.get(6).getValue());
                response.setA8(arrayList1.get(7).getValue());
//                response.setA9(arrayList1.get(8).getValue());
                responseArrayList.add(response);
                if (responseArrayList.size() > 0)
                    handler.sendEmptyMessage(DIALOG_SUCCESS4);
                else handler.sendEmptyMessage(DIALOG_FAILURE4);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DIALOG_FAILURE4);
        }
    }

    void getObCbDetails1(String result, Handler handler, ArrayList<Response> responseArrayList, OBCBAdapter1 obcbAdapter1) {
        responseArrayList.clear();
        JSONArray jsonArray1, jsonArray2;
        try {
            jsonArray1 = new JSONArray(result);
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Response> arrayList1 = new ArrayList<>();
                Response response = new Response();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Response response1 = new Response();
                    if (jsonArray2.getString(j).equals("null")) {
                        response1.setValue("0");
                    } else response1.setValue(jsonArray2.getString(j));
                    arrayList1.add(response1);
                }
                response.setA1(arrayList1.get(0).getValue());
                response.setA2(arrayList1.get(1).getValue());
                response.setA3(arrayList1.get(2).getValue());

                responseArrayList.add(response);
                obcbAdapter1.notifyDataSetChanged();
                if (responseArrayList.size() > 0)
                    handler.sendEmptyMessage(DIALOG_SUCCESS);
                else handler.sendEmptyMessage(DIALOG_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DIALOG_FAILURE);
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------
    void getObCbDetails2(String result, Handler handler, ArrayList<Response> responseArrayList, OBCBAdapter2 obcbAdapter2, GetSetValues getsetValues) {
        responseArrayList.clear();
        JSONArray jsonArray1, jsonArray2;
        try {
            jsonArray1 = new JSONArray(result);
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Response> arrayList1 = new ArrayList<>();
                Response response = new Response();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Response response1 = new Response();
                    if (jsonArray2.getString(j).equals("null")) {
                        response1.setValue("0");
                    } else response1.setValue(jsonArray2.getString(j));
                    arrayList1.add(response1);
                }
                if (getsetValues.getMonth_flag().equals("N")) {
                    response.setA1(arrayList1.get(0).getValue());
                    response.setA2(arrayList1.get(1).getValue());
                    response.setA3(arrayList1.get(2).getValue());
                    response.setA4(arrayList1.get(3).getValue());
                } else {
                    response.setA1(arrayList1.get(0).getValue());
                    response.setMonth(arrayList1.get(1).getValue());
                    response.setA2(arrayList1.get(2).getValue());
                    response.setA3(arrayList1.get(3).getValue());
                    response.setA4(arrayList1.get(4).getValue());
                }

                responseArrayList.add(response);
                obcbAdapter2.notifyDataSetChanged();
                if (responseArrayList.size() > 0)
                    handler.sendEmptyMessage(DIALOG_SUCCESS);
                else handler.sendEmptyMessage(DIALOG_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DIALOG_FAILURE);
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------
    void getObCbDetails3(String result, Handler handler, ArrayList<Response> responseArrayList, OBCBAdapter3 obcbAdapter3, GetSetValues getsetValues) {
        responseArrayList.clear();
        JSONArray jsonArray1, jsonArray2;
        try {
            jsonArray1 = new JSONArray(result);
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Response> arrayList1 = new ArrayList<>();
                Response response = new Response();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Response response1 = new Response();
                    if (jsonArray2.getString(j).equals("null")) {
                        response1.setValue("0");
                    } else response1.setValue(jsonArray2.getString(j));
                    arrayList1.add(response1);
                }
                if (getsetValues.getMonth_flag().equals("N")) {
                    response.setA1(arrayList1.get(0).getValue());
                    response.setA2(arrayList1.get(1).getValue());
                    response.setA3(arrayList1.get(2).getValue());
                } else {
                    response.setA1(arrayList1.get(0).getValue());
                    response.setMonth(arrayList1.get(1).getValue());
                    response.setA2(arrayList1.get(2).getValue());
                    response.setA3(arrayList1.get(3).getValue());
                }
                responseArrayList.add(response);
                obcbAdapter3.notifyDataSetChanged();
                if (responseArrayList.size() > 0)
                    handler.sendEmptyMessage(DIALOG_SUCCESS);
                else handler.sendEmptyMessage(DIALOG_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DIALOG_FAILURE);
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------
    void getObCbDetails4(String result, Handler handler, ArrayList<Response> responseArrayList, OBCBAdapter4 obcbAdapter4, GetSetValues getsetValues) {
        responseArrayList.clear();
        JSONArray jsonArray1, jsonArray2;
        try {
            jsonArray1 = new JSONArray(result);
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Response> arrayList1 = new ArrayList<>();
                Response response = new Response();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Response response1 = new Response();
                    if (jsonArray2.getString(j).equals("null")) {
                        response1.setValue("0");
                    } else response1.setValue(jsonArray2.getString(j));
                    arrayList1.add(response1);
                }
                if (getsetValues.getMonth_flag().equals("N")) {
                    response.setA1(arrayList1.get(0).getValue());
                    response.setA2(arrayList1.get(1).getValue());
                } else {
                    response.setMonth(arrayList1.get(0).getValue());
                    response.setA2(arrayList1.get(1).getValue());
                }
                responseArrayList.add(response);
                obcbAdapter4.notifyDataSetChanged();
                if (responseArrayList.size() > 0)
                    handler.sendEmptyMessage(DIALOG_SUCCESS);
                else handler.sendEmptyMessage(DIALOG_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DIALOG_FAILURE);
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------
    void getObCbDetails5(String result, Handler handler, ArrayList<Response> responseArrayList, OBCBBilledAdapter1 obcbBilledAdapter1) {
        responseArrayList.clear();
        JSONArray jsonArray1, jsonArray2;
        try {
            jsonArray1 = new JSONArray(result);
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Response> arrayList1 = new ArrayList<>();
                Response response = new Response();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Response response1 = new Response();
                    if (jsonArray2.getString(j).equals("null")) {
                        response1.setValue("0");
                    } else response1.setValue(jsonArray2.getString(j));
                    arrayList1.add(response1);
                }
                response.setA1(arrayList1.get(0).getValue());
                response.setA2(arrayList1.get(1).getValue());
                response.setA3(arrayList1.get(2).getValue());
                response.setA4(arrayList1.get(3).getValue());
                response.setA5(arrayList1.get(4).getValue());
                response.setA6(arrayList1.get(5).getValue());
                response.setA7(arrayList1.get(6).getValue());
                response.setA8(arrayList1.get(7).getValue());
                response.setA9(arrayList1.get(8).getValue());
                response.setA10(arrayList1.get(9).getValue());
                response.setA11(arrayList1.get(10).getValue());
                response.setA12(arrayList1.get(11).getValue());
                response.setA13(arrayList1.get(12).getValue());

                responseArrayList.add(response);
                obcbBilledAdapter1.notifyDataSetChanged();
                if (responseArrayList.size() > 0)
                    handler.sendEmptyMessage(DIALOG_SUCCESS);
                else handler.sendEmptyMessage(DIALOG_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DIALOG_FAILURE);
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------
    void getObCbDetails6(String result, Handler handler, ArrayList<Response> responseArrayList, OBCBBilledAdapter2 obcbBilledAdapter2,
                         GetSetValues getsetValues) {
        responseArrayList.clear();
        JSONArray jsonArray1, jsonArray2;
        try {
            jsonArray1 = new JSONArray(result);
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Response> arrayList1 = new ArrayList<>();
                Response response = new Response();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Response response1 = new Response();
                    if (jsonArray2.getString(j).equals("null")) {
                        response1.setValue("0");
                    } else response1.setValue(jsonArray2.getString(j));
                    arrayList1.add(response1);
                }
                if (getsetValues.getMonth_flag().equals("N")){
                    response.setA1(arrayList1.get(0).getValue());
                    response.setA2(arrayList1.get(1).getValue());
                    response.setA3(arrayList1.get(2).getValue());
                    response.setA4(arrayList1.get(3).getValue());
                    response.setA5(arrayList1.get(4).getValue());
                    response.setA6(arrayList1.get(5).getValue());
                    response.setA7(arrayList1.get(6).getValue());
                    response.setA8(arrayList1.get(7).getValue());
                    response.setA9(arrayList1.get(8).getValue());
                    response.setA10(arrayList1.get(9).getValue());
                    response.setA11(arrayList1.get(10).getValue());
                    response.setA12(arrayList1.get(11).getValue());
                    response.setA13(arrayList1.get(12).getValue());
                }else {
                    response.setA1(arrayList1.get(0).getValue());
                    response.setA2(arrayList1.get(1).getValue());
                    response.setA3(arrayList1.get(2).getValue());
                    response.setA4(arrayList1.get(3).getValue());
                    response.setA5(arrayList1.get(4).getValue());
                    response.setA6(arrayList1.get(5).getValue());
                    response.setA7(arrayList1.get(6).getValue());
                    response.setA8(arrayList1.get(7).getValue());
                    response.setA9(arrayList1.get(8).getValue());
                    response.setA10(arrayList1.get(9).getValue());
                    response.setA11(arrayList1.get(10).getValue());
                    response.setA12(arrayList1.get(11).getValue());
                    response.setA13(arrayList1.get(12).getValue());
                    response.setMonth(arrayList1.get(13).getValue());
                }
                responseArrayList.add(response);
                obcbBilledAdapter2.notifyDataSetChanged();
                if (responseArrayList.size() > 0)
                    handler.sendEmptyMessage(DIALOG_SUCCESS);
                else handler.sendEmptyMessage(DIALOG_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DIALOG_FAILURE);
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------
    void getObCbDetails7(String result, Handler handler, ArrayList<Response> responseArrayList, OBCBBilledAdapter3 obcbBilledAdapter3,
                         GetSetValues getsetValues) {
        responseArrayList.clear();
        JSONArray jsonArray1, jsonArray2;
        try {
            jsonArray1 = new JSONArray(result);
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Response> arrayList1 = new ArrayList<>();
                Response response = new Response();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Response response1 = new Response();
                    if (jsonArray2.getString(j).equals("null")) {
                        response1.setValue("0");
                    } else response1.setValue(jsonArray2.getString(j));
                    arrayList1.add(response1);
                }
                if (getsetValues.getMonth_flag().equals("N")){
                    response.setA1(arrayList1.get(0).getValue());
                    response.setA2(arrayList1.get(1).getValue());
                    response.setA3(arrayList1.get(2).getValue());
                    response.setA4(arrayList1.get(3).getValue());
                    response.setA5(arrayList1.get(4).getValue());
                    response.setA6(arrayList1.get(5).getValue());
                    response.setA7(arrayList1.get(6).getValue());
                    response.setA8(arrayList1.get(7).getValue());
                    response.setA9(arrayList1.get(8).getValue());
                    response.setA10(arrayList1.get(9).getValue());
                    response.setA11(arrayList1.get(10).getValue());
                }else {
                    response.setA1(arrayList1.get(0).getValue());
                    response.setMonth(arrayList1.get(1).getValue());
                    response.setA2(arrayList1.get(2).getValue());
                    response.setA3(arrayList1.get(3).getValue());
                    response.setA4(arrayList1.get(4).getValue());
                    response.setA5(arrayList1.get(5).getValue());
                    response.setA6(arrayList1.get(6).getValue());
                    response.setA7(arrayList1.get(7).getValue());
                    response.setA8(arrayList1.get(8).getValue());
                    response.setA9(arrayList1.get(9).getValue());
                    response.setA10(arrayList1.get(10).getValue());
                    response.setA11(arrayList1.get(11).getValue());
                }
                responseArrayList.add(response);
                obcbBilledAdapter3.notifyDataSetChanged();
                if (responseArrayList.size() > 0)
                    handler.sendEmptyMessage(DIALOG_SUCCESS);
                else handler.sendEmptyMessage(DIALOG_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DIALOG_FAILURE);
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------
    void getObCbDetails8(String result, Handler handler, ArrayList<Response> responseArrayList, OBCBBilledAdapter4 obcbBilledAdapter4) {
        responseArrayList.clear();
        JSONArray jsonArray1, jsonArray2;
        try {
            jsonArray1 = new JSONArray(result);
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Response> arrayList1 = new ArrayList<>();
                Response response = new Response();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Response response1 = new Response();
                    if (jsonArray2.getString(j).equals("null")) {
                        response1.setValue("0");
                    } else response1.setValue(jsonArray2.getString(j));
                    arrayList1.add(response1);
                }
                response.setA1(arrayList1.get(0).getValue());
                response.setA2(arrayList1.get(1).getValue());
                response.setA3(arrayList1.get(2).getValue());
                response.setA4(arrayList1.get(3).getValue());
                response.setA5(arrayList1.get(4).getValue());
                response.setA6(arrayList1.get(5).getValue());
                response.setA7(arrayList1.get(6).getValue());
                response.setA8(arrayList1.get(7).getValue());
                response.setA9(arrayList1.get(8).getValue());
                response.setA10(arrayList1.get(9).getValue());
                response.setA11(arrayList1.get(10).getValue());

                responseArrayList.add(response);
                obcbBilledAdapter4.notifyDataSetChanged();
                if (responseArrayList.size() > 0)
                    handler.sendEmptyMessage(DIALOG_SUCCESS);
                else handler.sendEmptyMessage(DIALOG_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DIALOG_FAILURE);
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------
    void getObCbDetails9(String result, Handler handler, ArrayList<Response> responseArrayList, OBCBUnBilledAdapter1 obcbUnBilledAdapter1) {
        responseArrayList.clear();
        JSONArray jsonArray1, jsonArray2;
        try {
            jsonArray1 = new JSONArray(result);
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Response> arrayList1 = new ArrayList<>();
                Response response = new Response();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Response response1 = new Response();
                    if (jsonArray2.getString(j).equals("null")) {
                        response1.setValue("0");
                    } else response1.setValue(jsonArray2.getString(j));
                    arrayList1.add(response1);
                }
                response.setA1(arrayList1.get(0).getValue());
                response.setA2(arrayList1.get(1).getValue());
                response.setA3(arrayList1.get(2).getValue());
                response.setA4(arrayList1.get(3).getValue());
                response.setA5(arrayList1.get(4).getValue());
                response.setA6(arrayList1.get(5).getValue());
                response.setA7(arrayList1.get(6).getValue());
                response.setA8(arrayList1.get(7).getValue());
                response.setA9(arrayList1.get(8).getValue());
                response.setA10(arrayList1.get(9).getValue());

                responseArrayList.add(response);
                obcbUnBilledAdapter1.notifyDataSetChanged();
                if (responseArrayList.size() > 0)
                    handler.sendEmptyMessage(DIALOG_SUCCESS);
                else handler.sendEmptyMessage(DIALOG_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DIALOG_FAILURE);
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------
    void getObCbDetails10(String result, Handler handler, ArrayList<Response> responseArrayList, OBCBUnBilledAdapter2 obcbUnBilledAdapter2,
                          GetSetValues getsetValues) {
        responseArrayList.clear();
        JSONArray jsonArray1, jsonArray2;
        try {
            jsonArray1 = new JSONArray(result);
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Response> arrayList1 = new ArrayList<>();
                Response response = new Response();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Response response1 = new Response();
                    if (jsonArray2.getString(j).equals("null")) {
                        response1.setValue("0");
                    } else response1.setValue(jsonArray2.getString(j));
                    arrayList1.add(response1);
                }
                if (getsetValues.getMonth_flag().equals("N")) {
                    response.setA1(arrayList1.get(0).getValue());
                    response.setA2(arrayList1.get(1).getValue());
                    response.setA3(arrayList1.get(2).getValue());
                    response.setA4(arrayList1.get(3).getValue());
                    response.setA5(arrayList1.get(4).getValue());
                    response.setA6(arrayList1.get(5).getValue());
                    response.setA7(arrayList1.get(6).getValue());
                    response.setA8(arrayList1.get(7).getValue());
                    response.setA9(arrayList1.get(8).getValue());
                    response.setA10(arrayList1.get(9).getValue());
                } else {
                    response.setA1(arrayList1.get(0).getValue());
                    response.setA2(arrayList1.get(1).getValue());
                    response.setA3(arrayList1.get(2).getValue());
                    response.setA4(arrayList1.get(3).getValue());
                    response.setA5(arrayList1.get(4).getValue());
                    response.setA6(arrayList1.get(5).getValue());
                    response.setA7(arrayList1.get(6).getValue());
                    response.setA8(arrayList1.get(7).getValue());
                    response.setA9(arrayList1.get(8).getValue());
                    response.setA10(arrayList1.get(9).getValue());
                    response.setMonth(arrayList1.get(10).getValue());
                }
                responseArrayList.add(response);
                obcbUnBilledAdapter2.notifyDataSetChanged();
                if (responseArrayList.size() > 0)
                    handler.sendEmptyMessage(DIALOG_SUCCESS);
                else handler.sendEmptyMessage(DIALOG_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DIALOG_FAILURE);
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------
    void getObCbDetails11(String result, Handler handler, ArrayList<Response> responseArrayList, OBCBUnBilledAdapter3 obcbUnBilledAdapter3,
                          GetSetValues getsetValues) {
        responseArrayList.clear();
        JSONArray jsonArray1, jsonArray2;
        try {
            jsonArray1 = new JSONArray(result);
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Response> arrayList1 = new ArrayList<>();
                Response response = new Response();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Response response1 = new Response();
                    if (jsonArray2.getString(j).equals("null")) {
                        response1.setValue("0");
                    } else response1.setValue(jsonArray2.getString(j));
                    arrayList1.add(response1);
                }
                if (getsetValues.getMonth_flag().equals("N")) {
                    response.setA1(arrayList1.get(0).getValue());
                    response.setA2(arrayList1.get(1).getValue());
                    response.setA3(arrayList1.get(2).getValue());
                    response.setA4(arrayList1.get(3).getValue());
                    response.setA5(arrayList1.get(4).getValue());
                    response.setA6(arrayList1.get(5).getValue());
                    response.setA7(arrayList1.get(6).getValue());
                    response.setA8(arrayList1.get(7).getValue());
                } else {
                    response.setA1(arrayList1.get(0).getValue());
                    response.setMonth(arrayList1.get(1).getValue());
                    response.setA2(arrayList1.get(2).getValue());
                    response.setA3(arrayList1.get(3).getValue());
                    response.setA4(arrayList1.get(4).getValue());
                    response.setA5(arrayList1.get(5).getValue());
                    response.setA6(arrayList1.get(6).getValue());
                    response.setA7(arrayList1.get(7).getValue());
                    response.setA8(arrayList1.get(8).getValue());
                }
                responseArrayList.add(response);
                obcbUnBilledAdapter3.notifyDataSetChanged();
                if (responseArrayList.size() > 0)
                    handler.sendEmptyMessage(DIALOG_SUCCESS);
                else handler.sendEmptyMessage(DIALOG_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DIALOG_FAILURE);
        }
    }

    //---------------------------------------------------------------------------------------------------------------------------
    void getObCbDetails12(String result, Handler handler, ArrayList<Response> responseArrayList, OBCBUnBilledAdapter4 obcbUnBilledAdapter4) {
        responseArrayList.clear();
        JSONArray jsonArray1, jsonArray2;
        try {
            jsonArray1 = new JSONArray(result);
            for (int i = 0; i < jsonArray1.length(); i++) {
                ArrayList<Response> arrayList1 = new ArrayList<>();
                Response response = new Response();
                jsonArray2 = new JSONArray(jsonArray1.getString(i));
                for (int j = 0; j < jsonArray2.length(); j++) {
                    Response response1 = new Response();
                    if (jsonArray2.getString(j).equals("null")) {
                        response1.setValue("0");
                    } else response1.setValue(jsonArray2.getString(j));
                    arrayList1.add(response1);
                }
                response.setA1(arrayList1.get(0).getValue());
                response.setA2(arrayList1.get(1).getValue());
                response.setA3(arrayList1.get(2).getValue());
                response.setA4(arrayList1.get(3).getValue());
                response.setA5(arrayList1.get(4).getValue());
                response.setA6(arrayList1.get(5).getValue());
                response.setA7(arrayList1.get(6).getValue());
                response.setA8(arrayList1.get(7).getValue());

                responseArrayList.add(response);
                obcbUnBilledAdapter4.notifyDataSetChanged();
                if (responseArrayList.size() > 0)
                    handler.sendEmptyMessage(DIALOG_SUCCESS);
                else handler.sendEmptyMessage(DIALOG_FAILURE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(DIALOG_FAILURE);
        }
    }

}
