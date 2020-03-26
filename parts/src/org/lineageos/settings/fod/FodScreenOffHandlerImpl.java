/*
 * Copyright (C) 2020 The PixelExperience Project
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

package org.lineageos.settings.fod;

import android.content.Context;
import android.os.SystemProperties;

import com.android.internal.util.custom.fod.FodScreenOffHandler;

public class FodScreenOffHandlerImpl implements FodScreenOffHandler {

    private static final String TAG = "ScreenOffFodHandler";

    private static final String FOD_SCRNOFFD_PROP = "persist.sys.gfscreenoffd.run";
    private static final String FOD_SET_STATUS_PROP = "persist.sys.gfscreenoffd.set_status";

    private Context mContext;
    private boolean mInteractive = true;
    private boolean mRunning = false;
    private boolean mIsDreaming = false;

    public FodScreenOffHandlerImpl(Context context) {
        mContext = context;
    }

    @Override
    public void onFingerprintRunningStateChanged(boolean running) {
        mRunning = running;
        updateState();
    }

    @Override
    public void onDreamingStateChanged(boolean dreaming) {
        mIsDreaming = dreaming;
        updateState();
    }

    @Override
    public void onScreenStateChanged(boolean interactive) {
        mInteractive = interactive;
        updateState();
    }

    private void updateState(){
        boolean daemonRunning = SystemProperties.getInt(FOD_SCRNOFFD_PROP, 0) != 0;
        if (mIsDreaming && mInteractive){
            if (daemonRunning){
                stopDaemon();
            }
        }else if (mInteractive && mRunning){
            mayStartDaemon();
        }else if (mInteractive){
            stopDaemon();
        }else if (mRunning){
            setFodStatus();
            mayStartDaemon();
        }
    }

    private void mayStartDaemon(){
        boolean daemonRunning = SystemProperties.getInt(FOD_SCRNOFFD_PROP, 0) != 0;
        if (!daemonRunning){
            SystemProperties.set(FOD_SCRNOFFD_PROP, "1");
        }
    }

    private void stopDaemon(){
        SystemProperties.set(FOD_SCRNOFFD_PROP, "0");
    }

    private void setFodStatus(){
        SystemProperties.set(FOD_SET_STATUS_PROP, "1");
    }

}