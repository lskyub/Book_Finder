package com.bookfinder.network.error

import io.reactivex.Observable
import io.reactivex.functions.Function
import retrofit2.*
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type

class Rx2ErrorHandlingCallAdapterFactory : CallAdapter.Factory() {
    private val original: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Suppress("UNCHECKED_CAST")
    override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<Observable<Any>, Any>? {
        return RxCallAdapterWrapper(retrofit, original.get(returnType, annotations, retrofit) as CallAdapter<Observable<Any>, Any>)
    }

    companion object {
        fun create(): CallAdapter.Factory {
            return Rx2ErrorHandlingCallAdapterFactory()
        }

        class RxCallAdapterWrapper(val retrofit: Retrofit?, private val wrapped: CallAdapter<Observable<Any>, Any>?) : CallAdapter<Observable<Any>, Any> {
            override fun adapt(call: Call<Observable<Any>>): Any {
                return (wrapped?.adapt(call) as Observable<*>).onErrorResumeNext(Function {
                    throwable -> Observable.error(asRetrofitException(throwable))
                })
            }

            override fun responseType(): Type? {
                return wrapped?.responseType()
            }

            private fun asRetrofitException(throwable: Throwable): RetrofitException {
                return when (throwable) {
                    is HttpException -> {
                        val response: Response<*> = throwable.response()!!
                        RetrofitException.httpError(response, retrofit)
                    }

                    is IOException -> RetrofitException.networkError(throwable)
                    else -> RetrofitException.unexpectedError(throwable)
                }
            }
        }
    }
}