package com.dev.vishaltraining.mytodo.interaces

interface ActionListener {
    fun onPosClick(position: Int, extraData: Any = "") {}
    fun onDeleted(position: Int,exrtaData:Any = "") {}
    fun onTaskComplete(position: Int,exrtaData:Any = ""){}
}