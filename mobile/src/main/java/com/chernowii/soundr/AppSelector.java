package com.chernowii.soundr;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Konrad Iturbe on 03/06/16.
 */
public class AppSelector extends ListActivity {
    AppAdapter adapter=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.chooser_layout);
        LayoutInflater factory = LayoutInflater.from(this);
        final View DialogView = factory.inflate(
                R.layout.chooser_layout, null);
        final AlertDialog Dialog = new AlertDialog.Builder(this).create();
        Dialog.setView(DialogView);
        Dialog.show();
        PackageManager pm=getPackageManager();
        Intent main=new Intent(Intent.ACTION_MAIN, null);

        main.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> launchables=pm.queryIntentActivities(main, 0);
        Collections.sort(launchables,
                new ResolveInfo.DisplayNameComparator(pm));

        adapter=new AppAdapter(pm, launchables);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v,
                                   int position, long id) {

        ResolveInfo launchable=adapter.getItem(position);
        ActivityInfo activity=launchable.activityInfo;
        ComponentName name=new ComponentName(activity.applicationInfo.packageName,
                activity.name);



        String pack_name = name.getPackageName();

        Intent intentMessage=new Intent();
        intentMessage.putExtra("MESSAGE_package_name", pack_name);
        setResult(1,intentMessage);
        finish();

    }

    class AppAdapter extends ArrayAdapter<ResolveInfo> {
        private PackageManager pm=null;

        AppAdapter(PackageManager pm, List<ResolveInfo> apps) {
            super(AppSelector.this, R.layout.row, apps);
            this.pm=pm;
        }

        @Override
        public View getView(int position, View convertView,
                            ViewGroup parent) {
            if (convertView==null) {
                convertView=newView(parent);
            }

            bindView(position, convertView);

            return(convertView);
        }

        private View newView(ViewGroup parent) {
            return(getLayoutInflater().inflate(R.layout.row, parent, false));
        }

        private void bindView(int position, View row) {
            TextView label=(TextView)row.findViewById(R.id.label);

            label.setText(getItem(position).loadLabel(pm));

            ImageView icon=(ImageView)row.findViewById(R.id.icon);

            icon.setImageDrawable(getItem(position).loadIcon(pm));
        }
    }


}