
package com.wizarpos.androidmvcmodel;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.wizarpos.androidmvcmodel.entity.MainItem;
import com.wizarpos.androidmvcmodel.helper.LanguageHelper;
import com.wizarpos.androidmvcmodel.helper.TerminalHelper;
import com.wizarpos.androidmvcmodel.helper.XmlPullParserHelper;
import com.wizarpos.mvc.base.ActionManager;

public class MainApplication extends Application {

    private Context context;
    public static List<MainItem> testItems = new ArrayList<MainItem>();

    @Override
    public void onCreate() {
        super.onCreate();
        initParameter();
        ActionManager.initActionContainer(new com.wizarpos.mvc.impl.ActionContainerImpl(context));
    }

    private void initParameter() {
        context = this;
        testItems = XmlPullParserHelper.getTestItems(context, TerminalHelper.getTerminalType());
        for (MainItem mainItem : testItems) {
            Log.e("DEBUG", "" + mainItem.getDisplayName(LanguageHelper.getLanguageType(context)));
        }
    }
}
