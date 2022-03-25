package com.bashkir.documentstasks.ui.components.filters

enum class TaskFilterOption(private val option: String): FilterOption {
    ALL("Все"),
    MY("Полученные"),
    ISSUED("Выданные");

    override fun getOption(): String = option
}