package ru.cocovella.screencaptureapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.IBinder
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.util.SparseIntArray
import android.view.Surface
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class MediaProjectionService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

//
//    private var screenDensity: Int = 0
//    private var projectManager: MediaProjectionManager? = null
//    private var mediaProjection: MediaProjection? = null
//    private var virtualDisplay: VirtualDisplay? = null
//    private var mediaProjectionCallback: MediaProjectionCallback? = null
//    private var mediaRecorder: MediaRecorder? = null
//
//    private var videoUri: String = ""
//
//    companion object {
//        private val LOG_TAG = "SCREEN_VIDEO_LOG"
//        private val REQUEST_CODE = 1000
//        private val REQUEST_PERMISSION = 1001
//        private var DISPLAY_WIDTH = 700
//        private var DISPLAY_HEIGHT = 1280
//        private val ORIENTATIONS = SparseIntArray()
//
//        init {
//            ORIENTATIONS.append(Surface.ROTATION_0, 90)
//            ORIENTATIONS.append(Surface.ROTATION_90, 0)
//            ORIENTATIONS.append(Surface.ROTATION_180, 270)
//            ORIENTATIONS.append(Surface.ROTATION_270, 180)
//        }
//    }
//
//    inner class MediaProjectionCallback : MediaProjection.Callback() {
//
//        override fun onStop() {
//            if(toggle_btn.isChecked) {
//                toggle_btn.isChecked = false
//                mediaRecorder?.stop()
//                mediaRecorder?.reset()
//            }
//            mediaProjection = null
//            stopScreenRecord()
//        }
//    }
//
//
//    override fun onCreate() {
//        super.onCreate()
//
//        val metrics = DisplayMetrics()
//        windowManager.defaultDisplay.getMetrics(metrics)
//        screenDensity = metrics.densityDpi
//        mediaRecorder = MediaRecorder()
//        projectManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
//
//        DISPLAY_HEIGHT = metrics.heightPixels
//        DISPLAY_WIDTH = metrics.widthPixels
//
//        initToggleButton()
//    }
//
//    private fun initToggleButton() {
//        toggle_btn.setOnClickListener { button ->
//            if (permissionsRequired()) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(
//                        this,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    )
//                    || ActivityCompat.shouldShowRequestPermissionRationale(
//                        this,
//                        Manifest.permission.RECORD_AUDIO
//                    )
//                    || ActivityCompat.shouldShowRequestPermissionRationale(
//                        this,
//                        Manifest.permission.FOREGROUND_SERVICE
//                    )
//                )
//                {
//                    toggle_btn.isChecked = false
//                    Snackbar.make(root_layout, "Recording permissions are required", Snackbar.LENGTH_INDEFINITE)
//                        .setAction("ENABLE") {
//                            requestPermissions()
//                        }.show()
//                } else
//
//                {
//                    requestPermissions()
//                }
//
//            } else {
//                startRecording(button)
//            }
//        }
//    }
//
//    private fun requestPermissions() {
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(
//                Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                Manifest.permission.RECORD_AUDIO,
//                Manifest.permission.FOREGROUND_SERVICE
//            ),
//            REQUEST_PERMISSION
//        )
//    }
//
//    private fun permissionsRequired() =
//        (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) +
//                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) +
//                ContextCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE)
//                != PackageManager.PERMISSION_GRANTED)
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        when (requestCode) {
//            REQUEST_PERMISSION -> {
//                if (grantResults.isNotEmpty() && grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//                    startRecording(toggle_btn)
//                } else {
//                    toggle_btn.isChecked = false
//                    Snackbar.make(root_layout, "Permissions", Snackbar.LENGTH_INDEFINITE)
//                        .setAction("ENABLE") {
//                            startActivity(
//                                Intent().apply {
//                                    action = Settings.ACTION_APPLICATION_SETTINGS
//                                    addCategory(Intent.CATEGORY_DEFAULT)
//                                    data = Uri.parse("package: $packageName")
//                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                                    addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
//                                    addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
//                                }
//                            )
//                        }.show()
//                }
//                return
//            }
//        }
//    }
//
//    private fun startRecording(view: View) {
//        if ((view as ToggleButton).isChecked) {
//            initRecorder()
//            shareScreen()
//        } else {
//            mediaRecorder!!.stop()
//            mediaRecorder!!.reset()
//            stopScreenRecord()
//            playVideoInVideoView()
//        }
//    }
//
//    private fun shareScreen() {
//        if (mediaProjection == null) {
//            startActivityForResult(projectManager!!.createScreenCaptureIntent(), REQUEST_CODE)
//            return
//        }
//        virtualDisplay = createVirtualDisplay()
//        mediaRecorder!!.start()
//
//    }
//
//    private fun createVirtualDisplay(): VirtualDisplay? {
//        return mediaProjection!!. createVirtualDisplay("MainActivity", DISPLAY_WIDTH, DISPLAY_HEIGHT, screenDensity, DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
//            mediaRecorder!!.surface, null, null)
//    }
//
//    @SuppressLint("MissingSuperCall")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode != REQUEST_CODE) {
//            return
//        }
//
//        if (resultCode != Activity.RESULT_OK) {
//            Toast.makeText(this, "Screen cast permission denied", Toast.LENGTH_LONG).show()
//            return
//        }
//
//        mediaProjectionCallback = MediaProjectionCallback()
//        mediaProjection = projectManager!!.getMediaProjection(resultCode, data!!)
//        mediaProjection!!.registerCallback(mediaProjectionCallback, null)
//        virtualDisplay = createVirtualDisplay()
//        mediaRecorder!!.start()
//
//
//    }
//
//    private fun initRecorder() {
//        try {
//            mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
//            mediaRecorder!!.setVideoSource(MediaRecorder.VideoSource.SURFACE)
//            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
//
//            videoUri = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//                .toString() + StringBuilder("/")
//                .append("Key_Record_")
//                .append(SimpleDateFormat("dd-MM-yyyy-hh_mm_ss").format(Date()))
//                .append(".mp4")
//                .toString()
//            Log.e(LOG_TAG, "initRecorder(): videoUri = $videoUri")
//
//            mediaRecorder!!.setOutputFile(videoUri)
//            mediaRecorder!!.setVideoSize(DISPLAY_WIDTH, DISPLAY_HEIGHT)
//            mediaRecorder!!.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
//            mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
//            mediaRecorder!!.setVideoEncodingBitRate(512*1000)
//            mediaRecorder!!.setVideoFrameRate(30)
//
//            val rotation = windowManager.defaultDisplay.rotation
//            val orientation = ORIENTATIONS.get(rotation + 90)
//            mediaRecorder!!.setOrientationHint(orientation)
//            mediaRecorder!!.prepare()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun stopScreenRecord() {
//        if (virtualDisplay == null) return
//        virtualDisplay!!.release()
//        destroyMediaProjection()
//    }
//
//    private fun destroyMediaProjection() {
//        if (mediaProjection != null) {
//            mediaProjection!!.unregisterCallback(mediaProjectionCallback)
//            mediaProjection!!.stop()
//            mediaProjection = null
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        destroyMediaProjection()
//    }
}