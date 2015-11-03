package com.example.textmate;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Marcus on 10/29/2015.
 */
public class getSMSData2 extends MainActivity {

    public getSMSData2() {}

    public String getSMSData(ContentResolver CR) {
        //Note, date coming from http://developer.android.com/reference/java/text/SimpleDateFormat.html
        ArrayList<String[]> SMSData = new ArrayList<String[]> ();
        String [] smsMessage = {};
        String [] emptyVar = {};
        ContentResolver contentResolver = CR;
        //ContentResolver contentResolver;
        //contentResolver = getContentResolver();
        //Inbox = "content://sms/inbox"
        //Sent = "content://sms/sent"

        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null); //Point to inbox
        int indexBody = smsInboxCursor.getColumnIndex("body"); //Point to body column
        int indexAddress = smsInboxCursor.getColumnIndex("address"); //Point to sender's # column
        int indexDate = smsInboxCursor.getColumnIndex("date"); //point to date column

        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return ""; //no messages
        //arrayAdapter.clear();
        do {
            smsMessage = emptyVar;
            String addressSender = smsInboxCursor.getString(indexAddress); //sender's #
            String body = smsInboxCursor.getString(indexBody); //body of text
            Long dateMs = Long.parseLong(smsInboxCursor.getString(indexDate), 10);
            Date dateFromSms = new Date(dateMs);
            SimpleDateFormat simpleDate =  new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSSZ");
            String strDt = simpleDate.format(dateFromSms); //date

            //Pushing strings into local array
            smsMessage = push(smsMessage, addressSender);
            smsMessage = push(smsMessage, body);
            smsMessage = push(smsMessage, strDt);
            SMSData.add(smsMessage); //pushes local array into SMS list
        } while (smsInboxCursor.moveToNext());

        //Printing to make sure it works
        //TextView tv1 = (TextView)findViewById(R.id.textView2);
        String str2 = " ";
        for(int i=0; i < SMSData.size(); i++){
            str2 += "Address = " + SMSData.get(i)[0] + "\n" +
                    "Body = " + SMSData.get(i)[1] + "\n" +
                    "Date = " + SMSData.get(i)[2] + "\n";
        }
        //tv1.setText(str2);
        return str2;

    }

    private static String[] push(String[] array, String push) { //push function
        String[] longer = new String[array.length + 1];
        System.arraycopy(array, 0, longer, 0, array.length);
        longer[array.length] = push;
        return longer;
    }

}
