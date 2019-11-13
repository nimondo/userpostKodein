package com.example.mvvmkodein.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User(
    @field:PrimaryKey
    val id: Int,
    val  name: String,
    val username : String,
    val email : String,
    @Embedded
    val address : Adresse,
    val phone : String,
    val website : String,
    @Embedded
    val company : Company
)

data class Adresse(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    @Embedded
    val geo : Geo
)
data class Geo(val lat: Double, val lng: Double)
data class Company(
    @ColumnInfo(name = "company_name")
    val name: String,
    val catchPhrase: String,
    val bs: String
)
