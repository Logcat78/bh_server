package org.example.project.actions

import org.example.project.models.GestureParams

class Gesture {

    fun generateGestureParams(): GestureParams {
        val randomParam = 5..1000
        return GestureParams(
            moveToX = randomParam.random(),
            moveToY = randomParam.random(),
            lineToX = randomParam.random(),
            lineToY = randomParam.random(),
            duration = randomParam.random()
        )
    }
}