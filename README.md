# Test Application

With this app you can search GH repositories and see their owners common information

## Installation

After gradle ends building project you can set your github access token (it is unnecessary).
(In Project view) find in file data/src/main/java/ru/plovotok/data/network/NetworkConstants
and paste your github token

```kotlin
const val ACCESS_TOKEN = "" // <--- paste your token here
```