package com.projetogrupo8.taskmanager.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "tasks_table")
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tvTaskTitle: String,
    val tvTaskDescription: String,
    val date: String,
    val time: String
) : Parcelable
