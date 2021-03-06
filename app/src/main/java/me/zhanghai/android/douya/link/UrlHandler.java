/*
 * Copyright (c) 2016 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.douya.link;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import org.chromium.customtabsclient.CustomTabsActivityHelper;

import me.zhanghai.android.douya.settings.info.Settings;
import me.zhanghai.android.douya.util.UrlUtils;

public class UrlHandler {

    private static final CustomTabsActivityHelper.CustomTabsFallback sFallback =
            new CustomTabsActivityHelper.CustomTabsFallback() {
                @Override
                public void openUri(Activity activity, Uri uri) {
                    open(uri, activity, false);
                }
            };

    private UrlHandler() {}

    private static void open(Uri uri, Context context, boolean enableCustomTabs) {

        switch (Settings.OPEN_URL_WITH_METHOD.getEnumValue(context)) {
            case CUSTOM_TABS:
                if (enableCustomTabs && context instanceof Activity) {
                    UrlUtils.openWithCustomTabs(uri, sFallback, (Activity) context);
                    break;
                }
                // Fall through!
            case WEBVIEW:
                UrlUtils.openWithWebViewActivity(uri, context);
                break;
            case INTENT:
                UrlUtils.openWithIntent(uri, context);
                break;
        }
    }

    public static void open(Uri uri, Context context) {
        open(uri, context, true);
    }

    public static void open(String url, Context context) {
        open(Uri.parse(url), context);
    }
}
