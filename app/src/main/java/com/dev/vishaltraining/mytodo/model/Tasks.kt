package com.dev.vishaltraining.mytodo.model

import android.os.Parcel
import android.os.Parcelable

class  Tasks() : Parcelable {
    var id : Int = 0
    var name : String? = ""
    var desc : String? = ""
    var completed : String? = "N"

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        name = parcel.readString()
        desc = parcel.readString()
        completed = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(desc)
        parcel.writeString(completed)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Tasks> {
        override fun createFromParcel(parcel: Parcel): Tasks {
            return Tasks(parcel)
        }

        override fun newArray(size: Int): Array<Tasks?> {
            return arrayOfNulls(size)
        }
    }
}