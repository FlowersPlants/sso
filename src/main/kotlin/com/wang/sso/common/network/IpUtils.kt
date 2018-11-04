package com.wang.sso.common.network

import javax.servlet.http.HttpServletRequest

/**
 * ip地址工具类，待测试
 */
object IpUtils {

    /**
     * 获取客户端IP地址
     */
    fun getRemoteAddr(request: HttpServletRequest?): String {
        if (request == null) {
            return "unknown"
        }
        var ip: String? = request.getHeader("X-Forwarded-For")
        if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("Proxy-Client-IP")
        }
        if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("WL-Proxy-Client-IP")
        }
        if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.getHeader("X-Real-IP")
        }
        if (ip == null || ip.isEmpty() || "unknown".equals(ip, ignoreCase = true)) {
            ip = request.remoteAddr
        }
        return ip!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
    }

    /**
     * 是否是本地地址
     *
     * @param ip
     * @return
     */
    fun isLocalAddr(ip: String): Boolean {
        return "127.0.0.1" == ip || "0:0:0:0:0:0:0:1" == ip
    }

    /**
     * 判断IP地址为内网IP还是公网IP
     *
     * tcp/ip协议中，专门保留了三个IP地址区域作为私有地址，其地址范围如下：
     * 10.0.0.0/8：10.0.0.0～10.255.255.255
     * 172.16.0.0/12：172.16.0.0～172.31.255.255
     * 192.168.0.0/16：192.168.0.0～192.168.255.255
     *
     * @param ip
     * @return
     */
    fun isInternalAddr(ip: String): Boolean {
        if (isLocalAddr(ip)) {
            return true
        }

        val addr = textToNumericFormatV4(ip)

        val b0 = addr!![0]
        val b1 = addr[1]
        //10.x.x.x/8
        val section1: Byte = 0x0A
        //172.16.x.x/12
        val section2 = 0xAC.toByte()
        val section3 = 0x10.toByte()
        val section4 = 0x1F.toByte()
        //192.168.x.x/16
        val section5 = 0xC0.toByte()
        val section6 = 0xA8.toByte()
        when (b0) {
            section1 -> return true
            section2 -> {
                if (b1 in section3..section4) {
                    return true
                }
                when (b1) {
                    section6 -> return true
                }
                return false
            }
            section5 -> {
                when (b1) {
                    section6 -> return true
                }
                return false
            }
            else -> return false
        }
    }

    fun textToNumericFormatV4(paramString: String): ByteArray? {
        if (paramString.isEmpty()) {
            return null
        }
        val arrayOfByte = ByteArray(4)
        val arrayOfString = paramString.split("\\.".toRegex()).toTypedArray()
        try {
            var l: Long
            var i: Int
            when (arrayOfString.size) {
                1 -> {
                    l = java.lang.Long.parseLong(arrayOfString[0])
                    if (l < 0L || l > 4294967295L) {
                        return null
                    }
                    arrayOfByte[0] = (l shr 24 and 0xFF).toInt().toByte()
                    arrayOfByte[1] = (l and 0xFFFFFF shr 16 and 0xFF).toInt().toByte()
                    arrayOfByte[2] = (l and 0xFFFF shr 8 and 0xFF).toInt().toByte()
                    arrayOfByte[3] = (l and 0xFF).toInt().toByte()
                }
                2 -> {
                    l = Integer.parseInt(arrayOfString[0]).toLong()
                    if (l < 0L || l > 255L) {
                        return null
                    }
                    arrayOfByte[0] = (l and 0xFF).toInt().toByte()
                    l = Integer.parseInt(arrayOfString[1]).toLong()
                    if (l < 0L || l > 16777215L) {
                        return null
                    }
                    arrayOfByte[1] = (l shr 16 and 0xFF).toInt().toByte()
                    arrayOfByte[2] = (l and 0xFFFF shr 8 and 0xFF).toInt().toByte()
                    arrayOfByte[3] = (l and 0xFF).toInt().toByte()
                }
                3 -> {
                    i = 0
                    while (i < 2) {
                        l = Integer.parseInt(arrayOfString[i]).toLong()
                        if (l < 0L || l > 255L) {
                            return null
                        }
                        arrayOfByte[i] = (l and 0xFF).toInt().toByte()
                        i++
                    }
                    l = Integer.parseInt(arrayOfString[2]).toLong()
                    if (l < 0L || l > 65535L) {
                        return null
                    }
                    arrayOfByte[2] = (l shr 8 and 0xFF).toInt().toByte()
                    arrayOfByte[3] = (l and 0xFF).toInt().toByte()
                }
                4 -> {
                    i = 0
                    while (i < 4) {
                        l = Integer.parseInt(arrayOfString[i]).toLong()
                        if (l < 0L || l > 255L) {
                            return null
                        }
                        arrayOfByte[i] = (l and 0xFF).toInt().toByte()
                        i++
                    }
                }
                else -> return null
            }
        } catch (localNumberFormatException: NumberFormatException) {
            return null
        }

        return arrayOfByte
    }

    fun textToNumericFormatV6(paramString: String): ByteArray? {
        if (paramString.length < 2) {
            return null
        }
        val arrayOfChar = paramString.toCharArray()
        val arrayOfByte1 = ByteArray(16)

        var m = arrayOfChar.size
        val n = paramString.indexOf("%")
        if (n == m - 1) {
            return null
        }
        if (n != -1) {
            m = n
        }
        var i = -1
        var i1 = 0
        var i2 = 0
        if (arrayOfChar[i1] == ':' && arrayOfChar[++i1] != ':') {
            return null
        }
        var i3 = i1
        var j = 0
        var k = 0
        var i4: Int
        while (i1 < m) {
            val c = arrayOfChar[i1++]
            i4 = Character.digit(c, 16)
            if (i4 != -1) {
                k = k shl 4
                k = k or i4
                if (k > 65535) {
                    return null
                }
                j = 1
            } else if (c == ':') {
                i3 = i1
                if (j == 0) {
                    if (i != -1) {
                        return null
                    }
                    i = i2
                } else {
                    if (i1 == m) {
                        return null
                    }
                    if (i2 + 2 > 16) {
                        return null
                    }
                    arrayOfByte1[i2++] = (k shr 8 and 0xFF).toByte()
                    arrayOfByte1[i2++] = (k and 0xFF).toByte()
                    j = 0
                    k = 0
                }
            } else if (c == '.' && i2 + 4 <= 16) {
                val str = paramString.substring(i3, m)

                var i5 = 0
                var i6 = 0
                while ((i6) != -1) {
                    i6 = str.indexOf('.', i6)
                    i5++
                    i6++
                }
                if (i5 != 3) {
                    return null
                }
                val arrayOfByte3 = textToNumericFormatV4(str) ?: return null
                for (i7 in 0..3) {
                    arrayOfByte1[i2++] = arrayOfByte3[i7]
                }
                j = 0
            } else {
                return null
            }
        }
        if (j != 0) {
            if (i2 + 2 > 16) {
                return null
            }
            arrayOfByte1[i2++] = (k shr 8 and 0xFF).toByte()
            arrayOfByte1[i2++] = (k and 0xFF).toByte()
        }
        if (i != -1) {
            i4 = i2 - i
            if (i2 == 16) {
                return null
            }
            i1 = 1
            while (i1 <= i4) {
                arrayOfByte1[16 - i1] = arrayOfByte1[i + i4 - i1]
                arrayOfByte1[i + i4 - i1] = 0
                i1++
            }
            i2 = 16
        }
        if (i2 != 16) {
            return null
        }
        val arrayOfByte2 = convertFromIPv4MappedAddress(arrayOfByte1)
        return arrayOfByte2 ?: arrayOfByte1
    }

    fun isIPv4LiteralAddress(paramString: String): Boolean {
        return textToNumericFormatV4(paramString) != null
    }

    fun isIPv6LiteralAddress(paramString: String): Boolean {
        return textToNumericFormatV6(paramString) != null
    }

    fun convertFromIPv4MappedAddress(paramArrayOfByte: ByteArray): ByteArray? {
        if (isIPv4MappedAddress(paramArrayOfByte)) {
            val arrayOfByte = ByteArray(4)
            System.arraycopy(paramArrayOfByte, 12, arrayOfByte, 0, 4)
            return arrayOfByte
        }
        return null
    }

    private fun isIPv4MappedAddress(paramArrayOfByte: ByteArray): Boolean {
        if (paramArrayOfByte.size < 16) {
            return false
        }
        return paramArrayOfByte[0].toInt() == 0
                && paramArrayOfByte[1].toInt() == 0
                && paramArrayOfByte[2].toInt() == 0
                && paramArrayOfByte[3].toInt() == 0
                && paramArrayOfByte[4].toInt() == 0
                && paramArrayOfByte[5].toInt() == 0
                && paramArrayOfByte[6].toInt() == 0
                && paramArrayOfByte[7].toInt() == 0
                && paramArrayOfByte[8].toInt() == 0
                && paramArrayOfByte[9].toInt() == 0
                && paramArrayOfByte[10].toInt() == -1
                && paramArrayOfByte[11].toInt() == -1
    }
}