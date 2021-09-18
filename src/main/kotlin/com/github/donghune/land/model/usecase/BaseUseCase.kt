package com.github.donghune.land.model.usecase

abstract class BaseUseCase<Param, T> {
    abstract fun validation(param: Param): Boolean

    abstract fun execute(param: Param): T

    operator fun invoke(param: Param) {
        if (validation(param)) {
            execute(param)
        }
    }
}

abstract class UseCaseParam
