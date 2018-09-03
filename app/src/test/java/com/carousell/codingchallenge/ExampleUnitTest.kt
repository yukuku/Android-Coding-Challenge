package com.carousell.codingchallenge

import com.carousell.codingchallenge.util.Formatter
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class FormatterTest {
    @Test
    fun addition_isCorrect() {
        Assert.assertEquals("24 days", Formatter.formatTime(100000000))
    }
}
