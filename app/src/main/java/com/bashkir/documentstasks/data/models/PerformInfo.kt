package com.bashkir.documentstasks.data.models

data class PerformInfo(val isAuthor: Boolean, val status: PerformStatus){
    constructor(isAuthor: Boolean, perform: Perform) : this(isAuthor, perform.status)
}
