package ru.galt.app.domain.base

import io.reactivex.Observable

abstract class ApiCommand<RESULT> : ApiCommandWithMapping<RESULT, RESULT>() {

    override fun afterResponse(response: Observable<RESULT>): Observable<RESULT> = response
}