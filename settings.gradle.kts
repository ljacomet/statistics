rootProject.name = "statistics"

buildCache {
    remote(HttpBuildCache::class.java) {
        setUrl("http://192.168.0.29:8080/cache/")
    }
}
