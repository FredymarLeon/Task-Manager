package com.projetogrupo8.taskmanager.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tasks")
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val titleTask: String,
    val descriptionTask: String
): Parcelable
