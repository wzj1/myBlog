package com.wzj.blog.myblog.config

/**
 * 常量池
 */
object Constant {
    //session 用户ID
    const val USER_ID="userId"

    const val SESSION_ID="sessions"

    //登录失效状态码
    const val ERROR_CLEAR = "301"


    //是否生产环境  更改存储文件路径

    const val isStorageFilePath:Boolean = true

    //生产默认文件地址
//    const val imagePath:String = "/var/image/upload"

    //测试默认文件地址
    const val imagePath:String = "/Volumes/AS/uploadImagePath/"


    /**
     * 图片显示地址
     *     private var image_https = "https://www.wzjlb.com.cn"
    private var image_prot = "8443"
     */

//    生产
//    const val image_IP:String = "https://www.wzjlb.com.cn"
//    const val image_prot:String = "8443"


    //测试
    const val image_IP:String = "127.0.0.1"
    const val image_prot:String = "8080"


    /**
     * 客户端私钥解密
     */
   const val privateKey:String = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC6Nqj3Aqd3XM5d\n" +
            "xezzJYugSo3GWt2wxzRG9FKrToP3IX1GbPweHtaw8bX9Qgm6RlH1YHHsJaDTFWkd\n" +
            "dCKbH1DthSaYMLjvm5hKgy8E3OmvdoYFPOIGlGdc6R+I35eBAdXLPf2VZa6d1XVc\n" +
            "43/oxERV7ue2I8u0TmroDGXC+ThQ9pRp2SHvcyYUbkd0qPXpfpfhvWly0xS/Uv+M\n" +
            "AXllqatcnKGTmg41pkA0d3hzlYDIQmfdJWxtNWJJDZY2VlzZSGETsOMW8CfOECKd\n" +
            "HkSeaT8p1npmuwuSslSyJ5uDWqqZZVTx9qSvnEFLDHhf4XhmppO+FeX0NW6sZ+O1\n" +
            "j0iMS6lrAgMBAAECggEAUjhaSJ6SZQDEWeFPq1hzmzXv/ZoXcruyWHX+nocPe6n8\n" +
            "z4DFnWMOdphzO7zR4L6153tLMfK3XRyCCjBCK24bZJS7Is24iouQJa5gr2v/fqr9\n" +
            "qAhm4HLcZMjGVMAs/BhcVt4rPrDj9erPZFFLI/KtHahcBS8u/ZD2YTlAIWO7Ovuz\n" +
            "NR1gl9mB4czoOkICJaM7f9IV55rHQSnTV3V8Gx1D+lGJZuBsKXXQKy9lFfFdg49Y\n" +
            "DW6FyqlWNxfmO0RIBVZ7HJ3kPWEAx5DDAUe04KPUVwnTXRqcrzt5zIOB7KV2L/rZ\n" +
            "2cegjLBkI9tYW/mru7J4Tu6aiKinoTnN6v0FQzuhuQKBgQDz2lHw/XTsD6NGDpFS\n" +
            "f/Pc7T4XbhlZztGsb1juOQwRbYK9630RyMwYEfTlpLth45nyz+kc4TNvTqEEans8\n" +
            "lqWcfACGCNnhC1GtbkA//1EVaRaR/pmR9jVZmHqrcbAUJikoUzlYC4Y11Msvm7T1\n" +
            "jlXrUQX9d2u1r7PH/yHE5VTzpQKBgQDDfU6uLbwSlfZK6g7b1NvxWEHxlQmS6sNM\n" +
            "tpOdr3KfafF9B7Rawn1N0nxXybnKWE4CzBUnHYlS9BIyT6+/Yd6TxCKfCnv1zrM3\n" +
            "niddkrGaLeZqF7swrfoXlAhx+6Srr/oU0QwIdLG9v9joYKWk5uSPCn3nu6/lbz1C\n" +
            "TeB1ZdNbzwKBgQCNlO3MkSxovu+ahmzDzLynQiux6N/ZL+a3SnME5aRnHgEyCd81\n" +
            "CzPMqehZyRpuzHoe6646cVquvqVobfadiA4h3DFloQ/3xN2oZ4HwZmoKl1hebcd2\n" +
            "YtA48/oNPtXv7PQqkJ+TyiUjbiiGvM4FYJ3sIb/rmYG1HI2yiiRuMITxbQKBgQCy\n" +
            "rAXMpppdtlh8Uf6wp5U6ubrTza8Ja66Cn+C9X4z47MC90NvjlB0okrf5GLPJn/n1\n" +
            "DohKzvEjM1aqSzDLT+05yysFh3SSzo/4xoEeQiQ3Ubplvzjkni6VfERXwXLZRKbi\n" +
            "fcpstBN75SugAC2FUU8W+bTnpyB2J7yqrDj5s86A+wKBgGp1V0v/REjcDEiF1zPv\n" +
            "4FgQla/sVrLtWeaPNCF5QldVE57aBiC7z9+c0A9GpjqfIFdEI1HWXCz4EDgi/cwX\n" +
            "ffQFZwAvUkgUnqqQOhUvUndO8fBpxxkUXpJsdK49DzXE0FNLecSJ/ORXy0pHEVDQ\n" +
            "w56ZgB9M9B9qxIL3sF2C8FMw"


    /**
     * 服务端 公钥加密
     */
    const val publicKey:String = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4w5zcHz8wGwQoGxsi5xK\n" +
            "x+NqVwh7isr/d3NeeyCv1CWvA9Rh91nj04q7DpAKMA+2HUG1NbzEjTzdKuRp8IYx\n" +
            "g/4cilJmgbtS53B3fCbfAOGaoaC8IMgL3y0Wvn8ClpCfnSv2ywpPQJlvHblatpa9\n" +
            "7XRrC16vDyCCYu7mqIglSymIdws5j5lIn7dOBs31DVoiW2++UToHV1a65zvXRzCg\n" +
            "gwqzGpSR+9D3I1CXPsv0uQCPkQUuB/o7xfQZC3vjY23ZHSiOtiE7r0O6QOyK7E9F\n" +
            "9w8FUGfVN1daTXxu9E77jSn2s7Cju9lKp8mcC7lccF1Y8dz3mAzpjLBcQYDKtXFM\n" +
            "5QIDAQAB"


    var version:Int = 0
}