package com.wzj.blog.myblog.entity

/**
 * 客户端与服务端报文加减密
 */
open class RSAKeyEntity {

    var clientPublicKey:String = ""
    var clientPrivateKey:String = ""
    var servicePublicKey:String = ""
    var servicePrivateKey:String = ""

}