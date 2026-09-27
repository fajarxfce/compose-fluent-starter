package dev.fajar.starter.storage

import dev.fajar.starter.common.result.AppResult
import dev.fajar.starter.common.result.Failure
import dev.fajar.starter.common.result.FailureKind
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/** Wrap acquisition and DTO mapping upstream; downstream collector failures are not caught. */
fun <T> safeStorageFlow(
    source: Flow<T>,
    onException: (Exception) -> Unit = ::reportStorageException,
): Flow<AppResult<T>> =
    source
        .map<T, AppResult<T>> { AppResult.Success(it) }
        .catch { error ->
            if (error is CancellationException || error !is Exception) throw error
            currentCoroutineContext().ensureActive()
            onException(error)
            emit(
                AppResult.Failed(
                    Failure(FailureKind.Storage, "Local data could not be read or saved.")
                )
            )
        }
