package com.martdev.android.expensetrackr.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors(val diskIO: Executor = Executors.newSingleThreadExecutor(),
                   val mainThread: Executor = MainThreadExecutor()) {

    private class MainThreadExecutor : Executor {
        private val mainThread = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable?) {
            mainThread.post(command)
        }
    }
}