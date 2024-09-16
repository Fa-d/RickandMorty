package com.experiment.rickandmorty.nav

import kotlin.reflect.full.declaredFunctions

fun locationScreenExists(): Boolean {
    try {
        Class.forName("com.experiment.rickmorty.location.LocationScreen")
        return true
    } catch (_: Exception) {
        return false
    }
}


fun functionExistsGlobally() {
    val className = "LocationScreen.kt"

    try {
        // Get the KClass of the generated class for MyFile.kt
        val kClass = Class.forName(className).kotlin

        val functionName = "LocationListScreen" // Replace with the actual function name
        val functionExists = kClass.declaredFunctions.any { it.name == functionName }
        if (functionExists) {
            println("Function '$functionName' exists.")
        } else {
            println("Function '$functionName' does not exist.")
        }
    } catch (e: ClassNotFoundException) {
        println("Class '$className' not found.")
    }
}