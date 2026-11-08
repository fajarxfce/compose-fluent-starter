# Published session identity

The network inspector observes the identity already published in memory. Starting
diagnostics must not restore credentials, activate an account database, or acquire a
business session. Session restoration keeps its existing application owner.

`SessionIdentityRepository` exposes that passive observation. Its data implementation
reads `SessionDataSource.record`, maps session records to identity through
`safeStorageFlow`, and preserves mapping failures. `ObservePublishedSessionIdentity`
deduplicates the identity: token rotation alone does not reset the capture. A newly
published login or logout still clears old diagnostic metadata.

The original `ObserveSessionIdentity` remains available for flows that own session
acquisition. The new contract does not silently change those flows or initiate a
second restoration path. Cancelling an observer releases its memory subscription.

Regression coverage verifies zero credential reads/database activations on observer
creation, publication after an explicitly owned restoration completes, token rotation,
login/logout, late events after disposal, and recovery after a malformed publication.
The network inspector's existing container-lifetime tests exercise the consumer.

This port follows the accepted Mobile consumer in build 60. Android notification
shortcut construction waits for Activity resume; background identity restoration owns
its original lifetime. The current Starter gate records its independent tests.
