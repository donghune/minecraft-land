package com.github.donghune.land.extension

import java.util.*

fun String.toUUID(): UUID = UUID.fromString(this)
