package ru.galt.app.domain.base

import io.reactivex.functions.Function
import kotlin.reflect.KClass

class BaseMapper<IN, OUT>(outClazz: KClass<Any>) : Function<IN, OUT> {

    var mapper: () -> OUT = { defaultValue }
    var defaultValue: OUT = outClazz.objectInstance as OUT

    override fun apply(t: IN): OUT {
        return mapper.invoke()
    }
}