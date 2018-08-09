/*
 * Copyright (C) 2016 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arielos.platform.internal;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;

import com.android.server.SystemService;

import org.lineageos.platform.internal.LineageSystemService;
import org.lineageos.platform.internal.NativeHelper;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;

import lineageos.app.LineageContextConstants;

import lineageos.arielos.ad.IArielAdblockService;
import lineageos.platform.Manifest;

public class ArielAdblockService extends LineageSystemService {

    private static final String TAG = "ArielAdblockService";
    private static final boolean DEBUG = Log.isLoggable(TAG, Log.DEBUG);

    private final Context mContext;

    public ArielAdblockService(Context context) {
        super(context);

        mContext = context;
    }

    @Override
    public String getFeatureDeclaration() {
        return LineageContextConstants.Features.ADBLOCK;
    }

    @Override
    public void onStart() {
        publishBinderService(LineageContextConstants.ARIEL_ADBLOCK_SERVICE, mBinder);
        blockAds();
    }

    private void blockAds(){
        try{
            if (DEBUG) Log.d(TAG, "Entering blockAds()");
            File hosts_file = new File("/etc/hosts.my");
            if(!hosts_file.exists()){
                hosts_file.createNewFile();
            }
            if (DEBUG) Log.d(TAG, "Created hosts.my file");
            FileOutputStream stream = new FileOutputStream(hosts_file);
            try {
                if (DEBUG) Log.d(TAG, "Writing bytes");
                stream.write("This is ArielOS!".getBytes());
            } finally {
                stream.close();
            }
            if (DEBUG) Log.d(TAG, "Exiting blockAds()");
        } 
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBootPhase(int phase) {

    }

    private final IBinder mBinder = new IArielAdblockService.Stub() {

        @Override
        public void blockAds(boolean block) {

        }

    };

//    private void broadcastSessionChanged(boolean added, AudioSessionInfo sessionInfo) {
//        Intent i = new Intent(LineageAudioManager.ACTION_AUDIO_SESSIONS_CHANGED);
//        i.putExtra(LineageAudioManager.EXTRA_SESSION_INFO, sessionInfo);
//        i.putExtra(LineageAudioManager.EXTRA_SESSION_ADDED, added);
//
//        sendBroadcastToAll(i, Manifest.permission.OBSERVE_AUDIO_SESSIONS);
//    }
//
//    private void sendBroadcastToAll(Intent intent, String receiverPermission) {
//        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
//
//        final long ident = Binder.clearCallingIdentity();
//        try {
//            if (DEBUG) Log.d(TAG, "Sending broadcast: " + intent.toString());
//
//            mContext.sendBroadcastAsUser(intent, UserHandle.ALL, receiverPermission);
//        } finally {
//            Binder.restoreCallingIdentity(ident);
//        }
//    }

}
