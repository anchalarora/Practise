package com.assignment.coroutinemitch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {

    private val RESULT1 = "#RESULT1"
    private val RESULT2 = "#RESULT2"
    private val JOB_TIMEOUT = 1900L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {

            CoroutineScope(IO).launch {
                fakeAPIResquest()
            }

        }
    }

    private fun setNewText(input : String)
    {
        val newText = textView.text.toString() +"\n$input"
        textView.text = newText
    }

    private suspend fun setTextOnMainThread(input : String){
        withContext(Main){
            setNewText(input)
        }

    }

    private suspend fun fakeAPIResquest(){
        withContext(IO){
            val job = withTimeoutOrNull(JOB_TIMEOUT){
                val result1 = getResult1FromAPI()
                println("debug: $result1")
                setTextOnMainThread(result1)

                val result2 = getResult2FromAPI()
                println("debug: $result2")
                setTextOnMainThread(result2)
            }

            if(job == null)
            {
                val cancelMsg = "Cancelling job...Taking more than $JOB_TIMEOUT ms"
                println(cancelMsg)
                setTextOnMainThread(cancelMsg)
            }

        }

    }

    private suspend fun getResult1FromAPI() : String{
        logThread("getResult1FromAPI")
        delay(1000)
        return RESULT1

    }

    private fun logThread(methodName : String)
    {
        println("debug: ${methodName} : ${Thread.currentThread().name}")
    }

    private suspend fun getResult2FromAPI(): String{

        logThread("getResult2FromAPI")
        delay(1000)
        return RESULT2
    }
}
