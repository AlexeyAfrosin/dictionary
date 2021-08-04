package com.afrosin.dictionary.scheduler

import io.reactivex.rxjava3.core.Scheduler

interface ISchedulerProvider {
    fun main(): Scheduler
    fun io(): Scheduler
}
