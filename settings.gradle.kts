rootProject.name = "statistics"

buildCache {
    remote(HttpBuildCache::class.java) {
        setUrl("http://172.20.253.36:8080/cache/")
    }
}
