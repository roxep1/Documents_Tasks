package com.bashkir.documentstasks.data.test

import android.os.Build
import androidx.annotation.RequiresApi
import com.bashkir.documentstasks.data.models.Status
import com.bashkir.documentstasks.data.models.Task
import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.data.models.Username
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalDateTime.*

@RequiresApi(Build.VERSION_CODES.O)
val testTask1 = Task(
    0,
    "Убрать снег",
    "Нужно убрать снег скопившийся во дворе школы.",
    now(),
    now().plusMonths(3),
    testUser1,
    mapOf(testUser2 to Status.Waiting, testUser3 to Status.InProgress, testUser4 to Status.Completed)
)

@RequiresApi(Build.VERSION_CODES.O)
val testTask2 = Task(
    1,
    "Перенести ноутбуки",
    "Необходимо перенести ноутбуки из аудитории 325 в аудиторию 230 во втором здании школы.",
    now().minusDays(5),
    now().plusDays(1),
    testUser2,
    mapOf(testUser4 to Status.Completed)
)

val testTasksList1 = listOf(testTask1, testTask2)
