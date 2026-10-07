package dev.fajar.starter.auth.presentation.login

import dev.fajar.starter.common.result.Failure
import dev.fajar.starter.common.result.ValidationIssue

data class LoginState(
    val email: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val submitting: Boolean = false,
    val failure: Failure? = null,
    val fieldErrors: Map<String, ValidationIssue> = emptyMap(),
    val validated: Boolean = false,
) {
    override fun toString(): String = "LoginState(submitting=$submitting, credentials=[redacted])"
}
