package com.example.test1

import android.content.Context
import android.widget.Toast
import com.iflytek.cloud.RecognizerResult
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechError
import com.iflytek.cloud.SpeechRecognizer
import com.iflytek.cloud.SpeechUtility
import com.iflytek.cloud.ui.RecognizerDialog
import com.iflytek.cloud.ui.RecognizerDialogListener
import com.iflytek.speech.SpeechRecognizerAidl
import org.json.JSONObject
import org.json.JSONTokener


class XunfeiUtil {

    fun initXunfei(context:Context){
        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=6faa0d00")
    }

    fun startVoice(context: Context,callbackListener: XunFeiCallbackListener){
        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=6faa0d00")
        SpeechRecognizer.createRecognizer(context, null)
        val dialog = RecognizerDialog(context, null)
        dialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn")
        dialog.setParameter(SpeechConstant.ACCENT, "mandarin")
        dialog.setParameter(SpeechConstant.ASR_PTT, "0")
        dialog.setListener(object : RecognizerDialogListener {
            override fun onResult(recognizerResult: RecognizerResult?, b: Boolean) {
                callbackListener.onFinish(recognizerResult)
            }

            override fun onError(speechError: SpeechError) {}
        })
        dialog.show()
        Toast.makeText(context, "请开始说话", Toast.LENGTH_SHORT).show()

    }

    fun parseIatResult(json: String?): String? {
        val ret = StringBuffer()
        try {
            val tokener = JSONTokener(json)
            val joResult = JSONObject(tokener)
            val words = joResult.getJSONArray("ws")
            for (i in 0 until words.length()) {
                // 转写结果词，默认使用第一个结果
                val items = words.getJSONObject(i).getJSONArray("cw")
                val obj = items.getJSONObject(0)
                ret.append(obj.getString("w"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ret.toString()
    }

}