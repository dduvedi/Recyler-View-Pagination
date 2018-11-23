package Utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Util {
    public static ProgressDialog progressDialog;

    public static void getErrorMessage(String message, int code, Context context) throws JSONException {
        if (code == 500) {
            Util.showToast(context, "Try again...");
        } else {
            JSONObject jsonObject = new JSONObject(message);
            Util.showToast(context, jsonObject.getString("message"));
        }
    }

    public static void showToast(Context context, String showText) {
        Toast.makeText(context, showText, Toast.LENGTH_LONG).show();
    }

    public static void showProgressDialog(Context context, String msg) {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog = null;
        }
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context) {
                @Override
                public void onBackPressed() {

                }
            };
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }


    public static void removeProgressDialog() {
        try {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
