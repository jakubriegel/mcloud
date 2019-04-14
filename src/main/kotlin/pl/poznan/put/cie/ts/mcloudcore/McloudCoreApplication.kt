package pl.poznan.put.cie.ts.mcloudcore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RequestMapping

@SpringBootApplication
class McloudCoreApplication

fun main(args: Array<String>) {
    runApplication<McloudCoreApplication>(*args)
}