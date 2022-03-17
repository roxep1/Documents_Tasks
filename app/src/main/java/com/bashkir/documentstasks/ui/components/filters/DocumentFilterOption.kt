package com.bashkir.documentstasks.ui.components.filters

enum class DocumentFilterOption(private val option: String): FilterOption {
    ALL("Все"),
    AGREEMENT("На согласование"),
    FAMILIARIZE("На ознакомление"),
    ISSUED("Выданные");

    override fun getOption(): String = option
}