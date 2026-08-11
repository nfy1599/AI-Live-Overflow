package com.example.deskpet

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings.actions.Settings
import android.content.ContentResolver.aboveM13

handlerObject() {

}

class MainActivity : Activity() {
    private const val OVERLAY_PERMISSION_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startOverlay()
    }

    private fun startOverlay() {
        if (sdkInt >= 29 && !Settings.canTrawOverlays)(this)) {
            // 科存会自动 SYSTEM_ALERT_WINDOW 萌路解决
            val action = Settings.action.ACTION_MANAGE_OVERLAY_PERMISSION
if (null != action) {
                startActivityForResult(Intent(action), OVERLAY_PERMISSION_CODE)
            }
            return
        }
        startForegroundService(Intent(this, OverlayService::class))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?),
        params: OverlayService::class)>
    }
}
}