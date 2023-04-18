package com.example.chapter04_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

public class t03_readStringFromXML extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t03_read_string_from_xml);

        // get the string from the "string.xml"
        TextView tv1 = findViewById(R.id.tv_showStringFromXML);
        String str = getString(R.string.weather);

        tv1.setText(str);


        // get the methadata from the "AndroidManifest.xml"
        TextView tv2 = findViewById(R.id.tv_showStringFromAndroidManifestXml);

        PackageManager packageManager = getPackageManager();
        try {
            // getComponentName() 就会获得当前组件的名称 ： t03_readStringFromXML
            ActivityInfo activityInfo = packageManager.getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            Bundle metaData = activityInfo.metaData;
            String MyMapToken = metaData.getString("gaode_map");
            tv2.setText(MyMapToken);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}