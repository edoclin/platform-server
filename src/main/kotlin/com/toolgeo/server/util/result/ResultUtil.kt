package com.toolgeo.server.util.result

object ResultUtil {
    fun build(code: Int, message: String, data: Any?): Result {
        return Result(code, message, data)
    }

    fun ok(): Result {
        return build(Code.SUCCESS.code, Code.SUCCESS.message, null)
    }


    fun ok(data: Any): Result {
        return build(Code.SUCCESS.code, Code.SUCCESS.message, data)
    }

    fun ok(message: String, data: Any): Result {
        return build(Code.SUCCESS.code, message, data)
    }

    fun error(message: String): Result {
        return build(Code.ERROR.code, message, null)
    }

    fun error(): Result {
        return build(Code.ERROR.code, Code.ERROR.message, null)
    }
}