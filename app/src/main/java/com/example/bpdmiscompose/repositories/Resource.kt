package com.example.bpdmiscompose.repositories

sealed class Resource<out R> {
    data class Success<out R>(val result: R): Resource<R>()
    data class Failure(val exception: Exception): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}


sealed class Resources<T>(
    val data : T? = null,
    val throwable : Throwable? = null,
){
    class Loading<T> : Resources<T>()
    class Success<T>(data:T?): Resources<T>(data = data)
    class Error<T>(throwable: Throwable?):Resources<T>(throwable = throwable)
}