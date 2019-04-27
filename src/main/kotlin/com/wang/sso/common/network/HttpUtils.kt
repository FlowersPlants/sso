package com.wang.sso.common.network

import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpDelete
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpPut
import org.apache.http.config.RegistryBuilder
import org.apache.http.conn.socket.ConnectionSocketFactory
import org.apache.http.conn.socket.PlainConnectionSocketFactory
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.conn.ssl.TrustSelfSignedStrategy
import org.apache.http.entity.ByteArrayEntity
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.apache.http.message.BasicNameValuePair
import org.apache.http.ssl.SSLContextBuilder
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * http 工具类
 * @author FlowersPlants
 * @since v1
 */
object HttpUtils {

    fun getRequest(): HttpServletRequest {
        return (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
    }

    fun getResponse(): HttpServletResponse? {
        return (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).response
    }

    /**
     * get 请求
     *
     * @param host 第三方主机地址
     * @param path 路由
     * @param headers 请求头
     * @param queries 参数
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun doGet(
        host: String, path: String,
        headers: Map<String, String>,
        queries: Map<String, String>
    ): HttpResponse {
        val httpClient = sslClient()

        val request = HttpGet(buildUrl(host, path, queries))
        for (e in headers.entries) {
            request.addHeader(e.key, e.value)
        }

        return httpClient.execute(request)
    }

    /**
     * post form 表单请求
     *
     * @param host 第三方主机地址
     * @param path 路由
     * @param headers 请求头
     * @param queries 参数
     * @param bodies form表单数据
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun doPost(
        host: String, path: String,
        headers: Map<String, String>,
        queries: Map<String, String>,
        bodies: Map<String, String>?
    ): HttpResponse {
        val httpClient = sslClient()

        val request = HttpPost(buildUrl(host, path, queries))
        for (e in headers.entries) {
            request.addHeader(e.key, e.value)
        }

        if (bodies != null) {
            val nameValuePairList = ArrayList<NameValuePair>()

            for (key in bodies.keys) {
                nameValuePairList.add(BasicNameValuePair(key, bodies[key]))
            }
            val formEntity = UrlEncodedFormEntity(nameValuePairList, "utf-8")
            formEntity.setContentType("application/x-www-form-urlencoded charset=UTF-8")
            request.entity = formEntity
        }

        return httpClient.execute(request)
    }

    /**
     * Post String
     *
     * @param host 第三方主机地址
     * @param path 路由
     * @param headers 请求头
     * @param queries 参数
     * @param body 请求数据
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun doPost(
        host: String, path: String,
        headers: Map<String, String>,
        queries: Map<String, String>,
        body: String
    ): HttpResponse {
        val httpClient = sslClient()

        val request = HttpPost(buildUrl(host, path, queries))
        for (e in headers.entries) {
            request.addHeader(e.key, e.value)
        }

        if (body.isNotBlank()) {
            request.entity = StringEntity(body, "utf-8")
        }

        return httpClient.execute(request)
    }

    /**
     * Post stream 流数据post请求
     *
     * @param host 第三方主机地址
     * @param path 路由
     * @param headers 请求头
     * @param queries 参数
     * @param body 流数据（上传文件）
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun doPost(
        host: String, path: String,
        headers: Map<String, String>,
        queries: Map<String, String>,
        body: ByteArray?
    ): HttpResponse {
        val httpClient = sslClient()

        val request = HttpPost(buildUrl(host, path, queries))
        for (e in headers.entries) {
            request.addHeader(e.key, e.value)
        }

        if (body != null) {
            request.entity = ByteArrayEntity(body)
        }

        return httpClient.execute(request)
    }

    /**
     * Put String put请求
     * @param host 第三方主机地址
     * @param path 路由
     * @param headers 请求头
     * @param queries 参数
     * @param body 字符类型的数据
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun doPut(
        host: String, path: String,
        headers: Map<String, String>,
        queries: Map<String, String>,
        body: String
    ): HttpResponse {
        val httpClient = sslClient()

        val request = HttpPut(buildUrl(host, path, queries))
        for (e in headers.entries) {
            request.addHeader(e.key, e.value)
        }

        if (body.isNotBlank()) {
            request.entity = StringEntity(body, "utf-8")
        }

        return httpClient.execute(request)
    }

    /**
     * Put stream
     * @param host 第三方主机地址
     * @param path 路由
     * @param headers 请求头
     * @param queries 参数
     * @param body 流数据
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun doPut(
        host: String, path: String,
        headers: Map<String, String>,
        queries: Map<String, String>,
        body: ByteArray?
    ): HttpResponse {
        val httpClient = sslClient()

        val request = HttpPut(buildUrl(host, path, queries))
        for (e in headers.entries) {
            request.addHeader(e.key, e.value)
        }

        if (body != null) {
            request.entity = ByteArrayEntity(body)
        }

        return httpClient.execute(request)
    }

    /**
     * Delete 删除操作请求
     *
     * @param host 第三方主机地址
     * @param path 路由
     * @param headers 请求头
     * @param queries 参数
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun doDelete(
        host: String, path: String,
        headers: Map<String, String>,
        queries: Map<String, String>
    ): HttpResponse {
        val httpClient = sslClient()

        val request = HttpDelete(buildUrl(host, path, queries))
        for (e in headers.entries) {
            request.addHeader(e.key, e.value)
        }

        return httpClient.execute(request)
    }

    /**
     * 构建URL
     */
    @Throws(UnsupportedEncodingException::class)
    private fun buildUrl(host: String, path: String, queries: Map<String, String>?): String {
        val sbUrl = StringBuilder()
        sbUrl.append(host)
        if (path.isNotBlank()) {
            sbUrl.append(path)
        }
        if (null != queries) {
            val sbQuery = StringBuilder()
            for (query in queries.entries) {
                if (sbQuery.isNotEmpty()) {
                    sbQuery.append("&")
                }
                if (query.key.isBlank() && query.value.isNotBlank()) {
                    sbQuery.append(query.value)
                }
                if (query.key.isNotBlank()) {
                    sbQuery.append(query.key)
                    if (query.value.isNotBlank()) {
                        sbQuery.append("=")
                        sbQuery.append(URLEncoder.encode(query.value, "utf-8"))
                    }
                }
            }
            if (sbQuery.isNotEmpty()) {
                sbUrl.append("?").append(sbQuery)
            }
        }

        return sbUrl.toString()
    }

    /**
     * 获取http和https请求连接
     */
    private fun sslClient(): HttpClient {
        try {
            val builder = SSLContextBuilder()
            builder.loadTrustMaterial(null, TrustSelfSignedStrategy())

            val ssf = SSLConnectionSocketFactory(builder.build())
            val registry = RegistryBuilder.create<ConnectionSocketFactory>()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", ssf)
                .build()

            val poolConnectionManager = PoolingHttpClientConnectionManager(registry)
            poolConnectionManager.apply {
                this.maxTotal = 200
                this.defaultMaxPerRoute = 2
            }

            val requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(10000)
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .build()

            return HttpClients.custom()
                .setConnectionManager(poolConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(DefaultHttpRequestRetryHandler(0, false))
                .build()
        } catch (ex: KeyManagementException) {
            throw RuntimeException(ex)
        } catch (ex: NoSuchAlgorithmException) {
            throw RuntimeException(ex)
        }
    }
}
