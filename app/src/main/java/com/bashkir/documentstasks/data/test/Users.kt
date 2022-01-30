package com.bashkir.documentstasks.data.test

import com.bashkir.documentstasks.data.models.User
import com.bashkir.documentstasks.data.models.Username

val testUser1 = User("122442143", Username("Филипп", "Башкир"), "test@email.com")

val testUser2 = User("24143312313", Username("Иван", "Андропов"), "andropov@gmail.com")

val testUser3 =
    User("432476543532", Username("Андрей", "Василец", "Ивпатович"), "vasilec@gmail.com")

val testUser4 =
    User("54354324442", Username("Кирилл", "Задугов", "Иванович"), "zadugov.k@gmail.com")

val testUser5 = User("12321233123231", Username("Артем", "Эрдоган"), "artemik@gmail.com")

val testUser6 = User("1233123231", Username("Евгений", "Смит"), "jek@gmail.com")

val testUser7 = User("123231", Username("Патрик", "Вольф"), "patrikk@gmail.com")

val testUser8 = User("1232321221321", Username("Питер", "Крузо"), "peter.k@gmail.com")

val testUser9 = User("232321221321", Username("Эндрю", "Макензи"), "mak.an@gmail.com")

val testUser10 = User("52321221321", Username("Йохан", "Больво"), "yoh@gmail.com")

val testUserList1 = listOf(
    testUser1,
    testUser2,
    testUser3,
    testUser4,
    testUser5,
    testUser6,
    testUser7,
    testUser8,
    testUser9,
    testUser10
)