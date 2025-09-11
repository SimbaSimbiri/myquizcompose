package com.simbiri.myquiz.data.remote

import com.simbiri.myquiz.domain.util.DataError
import com.simbiri.myquiz.domain.util.ResultType
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.JsonConvertException
import io.ktor.util.network.UnresolvedAddressException
import java.net.UnknownHostException

// we inline it so the data type we expect from the server is known at compile time
// this will also ensure the right @Serializable class is used for json conversion
// without this, the generic data type would be erased at compile time
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): ResultType<T, DataError>
{
    // the execute function is the lambda that sends a get request to the server
    // httpClient.get("$BASE_URL/...)
    val response = try {
        execute()
    } catch (e: UnknownHostException){
        return ResultType.Failure(DataError.NoInternetConnection)
    } catch (e: UnresolvedAddressException){
        return ResultType.Failure(DataError.NoInternetConnection)
    } catch (e: SocketTimeoutException){
        return ResultType.Failure(DataError.RequestTimeout)
    } catch (e: Exception){
        e.printStackTrace()
        return ResultType.Failure(DataError.Unknown(e.message))
    }

    return when (response.status.value){
        in 200..299 -> {
            try {
                // here we get the body of the json object with List<QuizTopicDto/QuizQuestionDto>
                // which were our serialized classes that our json will automatically be mapped to
                val data = response.body<T>()
                ResultType.Success(data)
            } catch (e: JsonConvertException){
                ResultType.Failure(DataError.SerializationError)
            } catch (e: NoTransformationFoundException){
                ResultType.Failure(DataError.SerializationError)
            } catch (e: Exception){
                e.printStackTrace()
                ResultType.Failure(DataError.Unknown(e.message))
            }
        }
        408 -> ResultType.Failure(DataError.RequestTimeout)
        429 -> ResultType.Failure(DataError.TooManyRequests)
        in 500..599 -> ResultType.Failure(DataError.ServerError)
        else -> ResultType.Failure(DataError.Unknown())
    }

}