package com.vodafone.esim;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.telephony.euicc.DownloadableSubscription;
import android.telephony.euicc.EuiccInfo;
import android.telephony.euicc.EuiccManager;

import androidx.annotation.RequiresApi;

import org.json.JSONException;


@CapacitorPlugin(name = "Esim")
public class EsimPlugin extends Plugin {
  private EuiccManager mgr;
  Integer resultCode;
  Integer detailedCode;
  Intent resultIntent;

  @PluginMethod
  public void isEnabled(final PluginCall call) throws JSONException {
    JSObject ret = new JSObject();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      mgr = (EuiccManager) getContext().getSystemService(Context.EUICC_SERVICE);
      boolean isEnabled = mgr.isEnabled();
      if (!isEnabled) {
        ret.put("eSim", false);
        ret.put("error", "{errorCode:1, errorMessage:\"Device is not eSIM compatible\"}");
        call.resolve(ret);
        return;
      }
      ret.put("eSim", true);
    } else {
      ret.put("error", "{errorCode:2, errorMessage:\"Android version should be greater or equal to 9\"}");
    }
    call.resolve(ret);
  }

  @PluginMethod
  @RequiresApi(api = Build.VERSION_CODES.P)
  public void eSimAdd(PluginCall call) {
    String activationCode = call.getString("activationCode");
    JSObject ret = new JSObject();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      mgr = (EuiccManager) getContext().getSystemService(Context.EUICC_SERVICE);
      boolean isEnabled = mgr.isEnabled();
      if (!isEnabled) {
        ret.put("eSim", false);
        ret.put("error", "{errorCode:1, errorMessage:\"Device is not eSIM compatible\"}");
        call.resolve(ret);
      } else {
        // Register receiver.
        final String ACTION_DOWNLOAD_SUBSCRIPTION = "download_subscription";
        final String LPA_DECLARED_PERMISSION = getContext().getPackageName() + ".lpa.permission.BROADCAST";
        BroadcastReceiver receiver =
          new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
              if (!ACTION_DOWNLOAD_SUBSCRIPTION.equals(intent.getAction())) {
                return;
              }
              resultCode = getResultCode();
              detailedCode = intent.getIntExtra(
                EuiccManager.EXTRA_EMBEDDED_SUBSCRIPTION_DETAILED_CODE,
                0 /* defaultValue*/);
              resultIntent = intent;
              //Missing check the resultCode!
              ret.put("success", "OK, eSIM profile received successfully!");
              call.resolve(ret);
            }
          };

        getContext().registerReceiver(receiver,
          new IntentFilter(ACTION_DOWNLOAD_SUBSCRIPTION),
          LPA_DECLARED_PERMISSION /* broadcastPermission*/,
          null /* handler */);

        // Download subscription asynchronously.
        DownloadableSubscription sub = DownloadableSubscription
          .forActivationCode(activationCode /* encodedActivationCode*/);
        Intent intent = new Intent(ACTION_DOWNLOAD_SUBSCRIPTION);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent callbackIntent = PendingIntent.getBroadcast(
          getContext(), 0 /* requestCode */, intent,
          PendingIntent.FLAG_UPDATE_CURRENT);
        mgr.downloadSubscription(sub, true /* switchAfterDownload */,
          callbackIntent);
      }
    } else {
      ret.put("error", "{errorCode:2, errorMessage:\"Android version should be greater or equal to 9\"}");
      call.resolve(ret);
    }
  }
}
