package com.cherry.doc

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.cherry.doc.util.BasicSet
import com.cherry.doc.util.WordUtils
import com.cherry.lib.doc.DocViewerActivity
import com.cherry.lib.doc.bean.FileType
import com.cherry.lib.doc.util.FileUtils
import com.cherry.lib.doc.widget.PoiViewer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    companion object {
        const val REQUEST_CODE_STORAGE_PERMISSION = 124
        const val REQUEST_CODE_STORAGE_PERMISSION11 = 125
        const val REQUEST_CODE_SELECT_DOCUMENT = 0x100
        const val TAG = "MainActivity"
    }

    var url =
        "http://cdn07.foxitsoftware.cn/pub/foxit/manual/phantom/en_us/API%20Reference%20for%20Application%20Communication.pdf"
//    var url = "https://xdts.xdocin.com/demo/resume3.docx"
//    var url = "http://172.16.28.95:8080/data/test2.ppt"
//    var url = "http://172.16.28.95:8080/data/testdocx.ll"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        setContent {
            ComposeApp()
        }
    }

    fun checkSupport(path: String): Boolean {
        var fileType = FileUtils.getFileTypeForUrl(path)
        Log.e(javaClass.simpleName, "fileType = $fileType")
        if (fileType == FileType.NOT_SUPPORT) {
            return false
        }
        return true
    }

    fun openDoc(path: String, docSourceType: Int, type: Int? = null) {
        DocViewerActivity.launchDocViewer(this, docSourceType, path, type)
    }

    fun word2Html(sourceFilePath: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val htmlFilePath = cacheDir.absolutePath + "/html"
            val htmlFileName = "word_pdf"

            var bs = BasicSet(this@MainActivity, sourceFilePath, htmlFilePath, htmlFileName)
            bs.picturePath = htmlFilePath

            WordUtils.getInstance(bs).word2html()

            CoroutineScope(Dispatchers.Main).launch {

            }
        }
    }
//    fun word2Html(sourceFilePath: String) {
//        var mPoiViewer = PoiViewer(this)
//        try {
//            mPoiViewer?.loadFile(mFlDocContainer, sourceFilePath)
//        } catch (e: java.lang.Exception) {
//            Toast.makeText(this, "打开失败", Toast.LENGTH_SHORT).show()
//        }
//    }

    /**
     * val documentUri = data?.data
     *             Log.d(TAG, "documentUri = $documentUri")
     *             documentUri?.let {
     *                 openDoc(it.toString(), DocSourceType.URI, null)
     *             }
     */


}