package com.meridiane.customviewclock.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.meridiane.customviewclock.databinding.AnalogClockBinding
import kotlinx.coroutines.*
import java.util.*

class AnalogClock(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private var binding: AnalogClockBinding =
        AnalogClockBinding.inflate(LayoutInflater.from(context))

    init {
        addView(binding.root)
    }

    private val calendar = Calendar.getInstance()

    private var hour = ( (calendar.get(Calendar.HOUR_OF_DAY)) % 12 ) * 30 // изначальный градус поворота для часа
    private var minute = ( calendar.get(Calendar.MINUTE)  ) * 6 // изначальный градус поворота для минуты
    private var second = ( calendar.get(Calendar.SECOND) ) * 6 // изначальный градус поворота для секунды

    fun startAnimate() {

        binding.imageViewSec.animate().rotationBy( second.toFloat() )

        binding.imageViewMin.animate().rotationBy( minute.toFloat() )

        binding.imageViewHour.animate().rotationBy( hour.toFloat() )

        CoroutineScope( Dispatchers.Main).launch {
            while (true) {
                delay(999L)
                repeatFun()
            }
        }

        // можно через thread ( но зачем, если есть корутины ) и избегаем утечки
        // val thread = Thread {
        //
        //      while (true) {
        //             Thread.sleep(990)
        //             repeatFun()
        //       }
        //  }
        //  thread.start()

    }

    private fun repeatFun(){

        binding.imageViewSec.animate().rotationBy(6f)

        second+= 6

        if (second == 360){

            binding.imageViewMin.animate().rotationBy(6f)
            minute += 6
            second = 0

        }

        if (minute == 360){

            binding.imageViewMin.animate().rotationBy(30f)
            minute = 0

        }

    }

}