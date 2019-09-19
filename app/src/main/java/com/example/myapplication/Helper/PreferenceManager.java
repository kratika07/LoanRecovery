package com.example.myapplication.Helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin1 on 23/5/18.
 */

public class PreferenceManager {

    /*shareprefence for storing data*/



    private static String LOAN_PREF = "com.loan.telecaller";
    private static String LOAN_ISLOGIN = "com.loan.telecaller.is.login";
    private static String LOAN_TOKEN = "com.loan.telecaller.is.tokenType";
    private static String LOAN_USERIMAGE = "com.loan.telecaller.is.userimage";
    private static String LOAN_USERNAME = "com.loan.telecaller.is.username";
    private static String LOAN_USERFIRSTNAME = "com.loan.telecaller.is.usersirstname";
    private static String LOAN_USERLASTNAME = "com.loan.telecaller.is.userlastname";
    private static String LOAN_USERID = "com.loan.telecaller.is.user_id";
    private static String LOAN_USERROLE = "com.loan.telecaller.is.user_role";
    private static String LOAN_USERROLE_ID = "com.loan.telecaller.is.user_role_id";

    private static String LOAN_MOBILE = "com.loan.agent.is.user_mobile";



    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(LOAN_PREF, Context.MODE_PRIVATE);
    }

    public static String getCUSTOMERNUMBER(Context context) {
        return getPrefs(context).getString(LOAN_MOBILE, "0");
    }

    public static void setCUSTOMERNUMBER(Context context, String value) {
        getPrefs(context).edit().putString(LOAN_MOBILE, value).commit();
    }


    public static String getLoanUserroleId(Context context) {
        return getPrefs(context).getString(LOAN_USERROLE_ID, "0");
    }

    public static void setLoanUserroleId(Context context,String loanUserroleTelecaller) {
        getPrefs(context).edit().putString(LOAN_USERROLE_ID, loanUserroleTelecaller).commit();

    }

    public static String getLOANISLOGIN(Context context) {
        return getPrefs(context).getString(LOAN_ISLOGIN, "0");
    }

    public static void setLOANISLOGIN(Context context, String value) {
        getPrefs(context).edit().putString(LOAN_ISLOGIN, value).commit();
    }


    public static String getLOANTOKEN(Context context) {
        return getPrefs(context).getString(LOAN_TOKEN, "0");
    }

    public static void setLOANTOKEN(Context context, String value) {
        getPrefs(context).edit().putString(LOAN_TOKEN, value).commit();
    }


    public static String getLOANUSERIMAGE(Context context) {
        return getPrefs(context).getString(LOAN_USERIMAGE, "0");
    }

    public static void setLOANUSERIMAGE(Context context, String value) {
        getPrefs(context).edit().putString(LOAN_USERIMAGE, value).commit();
    }


    public static String getLOANUSERNAME(Context context) {
        return getPrefs(context).getString(LOAN_USERNAME, "0");
    }

    public static void setLOANUSERNAME(Context context, String value) {
        getPrefs(context).edit().putString(LOAN_USERNAME, value).commit();
    }


    public static String getLOANUSERFIRSTNAME(Context context) {
        return getPrefs(context).getString(LOAN_USERFIRSTNAME, "0");
    }

    public static void setLOANUSERFIRSTNAME(Context context, String value) {
        getPrefs(context).edit().putString(LOAN_USERFIRSTNAME, value).commit();
    }


    public static String getLOANUSERLASTNAME(Context context) {
        return getPrefs(context).getString(LOAN_USERLASTNAME, "0");
    }

    public static void setLOANUSERLASTNAME(Context context, String value) {
        getPrefs(context).edit().putString(LOAN_USERLASTNAME, value).commit();
    }


    public static String getLOANUSERID(Context context) {
        return getPrefs(context).getString(LOAN_USERID, "0");
    }

    public static void setLOANUSERID(Context context, String value) {
        getPrefs(context).edit().putString(LOAN_USERID, value).commit();
    }


    public static String getLOANUSERROLE(Context context) {
        return getPrefs(context).getString(LOAN_USERROLE, "0");
    }

    public static void setLOANUSERROLE(Context context, String value) {
        getPrefs(context).edit().putString(LOAN_USERROLE, value).commit();
    }


}