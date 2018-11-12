rootProject.name = "statistics"

buildCache {
    remote(HttpBuildCache::class.java) {
        setUrl("http://10.37.49.9:8080/cache/")
//        setPush(true)
    }
}
