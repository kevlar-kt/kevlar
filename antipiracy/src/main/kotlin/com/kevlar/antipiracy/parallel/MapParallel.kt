package com.kevlar.antipiracy.parallel

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

public suspend fun <T, R> Iterable<T>.mapParallel(transform: suspend (T) -> R): List<R> = coroutineScope {
    map { async { transform(it) } }.map { it.await() }
}