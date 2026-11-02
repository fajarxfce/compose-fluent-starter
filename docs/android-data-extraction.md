# Android installation-owned storage

Android 12+ explicitly excludes all app file, database, preference, root and external
storage domains from cloud backup and device transfer. Existing allowBackup=false
remains; fullBackupContent=false also states the older Android policy explicitly.
This prevents automatic copying of local stores independently of the installation
keys that protect them. A user-managed encrypted export requires its own recovery
contract and explicit choice; these rules do not implement that feature.

The XML is identical to [Mobile c6347c75](https://github.com/fajarxfce/hris-mobile/commit/c6347c75278408256f775e65eb30d7ecae9b605e).
Starter independently passes root checks, release resource compilation and lint.
Actual linked XML and merged application attributes were inspected. The prior
DataExtractionRules warning is gone; 44 unrelated dependency/version warnings remain.
[Evidence](measurements/android-data-extraction-2026-11-02.json) records source and
report hashes. No physical backup/device transfer was exercised; no storage content,
Kotlin/Apple implementation, application policy or HRIS configuration was changed.
