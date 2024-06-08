package org.example.project.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import org.example.project.models.GestureParams
import java.sql.Connection
import java.sql.Statement

@Serializable

class GestureService(private val connection: Connection) {
    companion object {
        private const val CREATE_TABLE_CITIES =
            "CREATE TABLE GESTURE_PARAMS (id SERIAL PRIMARY KEY, MOVETOX FLOAT, MOVETOY INT, LINETOX FLOAT, LINETOY FLOAT, DURATION INT);"
        private const val SELECT_GESTURE_BY_ID = "SELECT MOVETOX, MOVETOY, LINETOX, LINETOY, DURATION FROM GESTURE_PARAMS WHERE id = ?"
        private const val INSERT_GESTURE_PARAMS = "\n" +
                "INSERT INTO GESTURE_PARAMS (MOVETOX, MOVETOY, LINETOX, LINETOY, DURATION) VALUES (?, ?, ?, ?, ?);"

    }

    init {
        val statement = connection.createStatement()
        statement.executeUpdate(CREATE_TABLE_CITIES)
    }


    suspend fun create(gestureParams: GestureParams): Int = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(INSERT_GESTURE_PARAMS, Statement.RETURN_GENERATED_KEYS)
        statement.setInt(1, gestureParams.moveToX)
        statement.setInt(2, gestureParams.moveToY)
        statement.setInt(3, gestureParams.lineToX)
        statement.setInt(4, gestureParams.lineToY)
        statement.setInt(5, gestureParams.duration)
        statement.executeUpdate()

        val generatedKeys = statement.generatedKeys
        if (generatedKeys.next()) {
            return@withContext generatedKeys.getInt(1)
        } else {
            throw Exception("Unable to retrieve the id of the newly inserted gesture params")
        }
    }


    suspend fun read(id: Int): GestureParams = withContext(Dispatchers.IO) {
        val statement = connection.prepareStatement(SELECT_GESTURE_BY_ID)
        statement.setInt(1, id)
        val resultSet = statement.executeQuery()

        if (resultSet.next()) {
            val moveToX = resultSet.getInt("MOVETOX")
            val moveToY = resultSet.getInt("MOVETOY")
            val lineToX = resultSet.getInt("LINETOX")
            val lineToY = resultSet.getInt("LINETOY")
            val duration = resultSet.getInt("DURATION")
            return@withContext GestureParams(
                moveToX = moveToX,
                moveToY = moveToY,
                lineToX = lineToX,
                lineToY = lineToY,
                duration = duration
            )
        } else {
            throw Exception("Record not found")
        }
    }

}