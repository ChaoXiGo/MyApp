package com.example.photo

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter


class MainActivity : AppCompatActivity() {
    private val TAG = "ChaoXi"

    lateinit var editText:EditText

    val targetFileName = "info.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPerms()
        editText = findViewById<EditText>(R.id.et1)
        val btn = findViewById<Button>(R.id.btn)
        val btn1 = findViewById<Button>(R.id.btn1)
        btn.setOnClickListener{
            writToFile()
        }
        btn1.setOnClickListener{
            readFile()
        }
    }

    private fun readFile() {
        try {
            // 相册路径
            val photoPath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    .absolutePath;
            // 文件路径
            val file = File(photoPath, targetFileName)
            if (file.exists() && file.isFile){
                val inputStream = FileInputStream(file)
                val reader = InputStreamReader(inputStream)
                val bufferedReader = BufferedReader(reader)
                val sb = StringBuffer()
                var line: String?
                while (bufferedReader.readLine().also {
                        line = it } != null) {
                    sb.append(line)
                }
                editText.setText(sb)
                reader.close()
                bufferedReader.close()
            }else{
                Log.d(TAG, "文件夹不存在")
            }
        }catch (e:IOException){
            throw IOException(e)
        }
    }

    private fun writToFile(){
        try {
            // 相册路径
            val photoPath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    .absolutePath;
            // 文件路径
            val file = File(photoPath, targetFileName)
            // 写入前重置文件
            if (file.exists() && file.isFile) {
                // Log.d(TAG, "writToFile: 文件已存在")
                if (file.delete()){
                    // Log.d(TAG, "writToFile: 文件已删除")
                }
            }

            val outputStream = FileOutputStream(file)
            val writer = OutputStreamWriter(outputStream)
            writer.write(editText.text.toString())
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
            editText.setText("")
            writer.close()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }


    private fun checkPerms() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            // android 10 及以下
            if (ActivityCompat.checkSelfPermission(this, Environment.getExternalStorageState()) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Environment.getExternalStorageState()) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "checkPerms: ~~~~~~~~")
            } else {
                ActivityCompat.requestPermissions(this, arrayOf<String>(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE), 0)
            }
            // android 11及以上
        }else{
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.data = Uri.parse("package:$packageName");
            startActivityForResult(intent, 0);
        }
    }

    // android 10权限回调
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0){
            if (ActivityCompat.checkSelfPermission(this,READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult~~")
            }else{
                Log.d(TAG, "权限拒绝了")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0){
            Log.d(TAG, "onActivityResult: android11权限获取成功")
        }
    }
}