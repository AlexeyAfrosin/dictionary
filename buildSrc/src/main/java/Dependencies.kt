import org.gradle.api.JavaVersion

object Config {
    const val application_id = "com.afrosin.dictionary"
    const val compile_sdk = 30
    const val build_tool_version = "30.0.3"
    const val min_sdk = 28
    const val target_sdk = 30


    val java_version = JavaVersion.VERSION_1_8
}

object Releases {
    const val version_code = 1
    const val version_name = "1.0"
}

object Modules {
    const val app = ":app"
    const val core = ":core"
    const val model = ":model"
    const val repository = ":repository"
    const val utils = ":utils"

    //features
    const val historyScreen = ":historyScreen"
}

object Versions {

    //Kotlin
    const val core = "1.6.0"
    const val stdlib = "1.5.21"

    //Design
    const val appcompat = "1.3.1"
    const val material = "1.4.0"
    const val constraintLayout = "2.0.4"
    const val swipeRefreshLayout = "1.1.0"

    //Test
    const val jUnit = "4.13.2"
    const val extJunit = "1.1.3"
    const val espressoCore = "3.4.0"

    //Retrofit
    const val retrofit = "2.9.0"
    const val converterGson = "2.9.0"
    const val coroutinesAdapter = "0.9.2"

    //okhttp3
    const val loggingInterceptor = "4.9.1"

    //ViewBindingDelegate
    const val viewBindingPropertyDelegate = "1.4.6"

    //koin
    const val koinAndroid = "2.0.1"
    const val koinAndroidViewModel = "2.0.1"

    //coroutines
    const val coroutinesCore = "1.4.1"
    const val coroutinesAndroid = "1.4.1"

    // Picasso
    const val picasso = "2.5.2"

    // Room
    const val roomRuntime = "2.4.0-alpha04"
    const val roomCompiler = "2.4.0-alpha04"
    const val roomKtx = "2.4.0-alpha04"

}

object Kotlin {
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.stdlib}"
    const val core = "androidx.core:core-ktx:${Versions.core}"
}

object Design {

    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
}

object TestImp {
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val extJunit = "androidx.test.ext:junit:${Versions.extJunit}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val coroutinesAdapter =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.coroutinesAdapter}"
}

object Okhttp3 {
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
}


object ViewBindingDelegate {
    const val viewBindingPropertyDelegate =
        "com.github.kirich1409:viewbindingpropertydelegate:${Versions.viewBindingPropertyDelegate}"
}

object Koin {
    const val koinAndroid = "org.koin:koin-android:${Versions.koinAndroid}"
    const val koinAndroidViewModel =
        "org.koin:koin-android-viewmodel:${Versions.koinAndroidViewModel}"
}

object Coroutines {
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
}

object Picasso {
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
}

object Room {
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomRuntime}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomKtx}"
}