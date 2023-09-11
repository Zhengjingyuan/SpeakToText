package com.example.test1

import com.iflytek.cloud.RecognizerResult

interface XunFeiCallbackListener {
    fun onFinish(results: RecognizerResult?)
}