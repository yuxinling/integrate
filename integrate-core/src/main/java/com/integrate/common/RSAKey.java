package com.integrate.common;

public interface RSAKey {

	/* 注册用的公钥,给客户端的 */
	public static final String REGISTER_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCzp0UETiPt4DxZUUJA2WzaB98v"
			+ "aoqUSIDqNqMPDpIlHI8xkNXBC8fRQPXN/GE4lQI9iYdAZ8F+Q3roGfTdu6VBZIDC"
			+ "25fHo39NQy+3kaamSBQLwsDX73kglEA9jLmHFFfFJBkgUCo3pXP98Hknt5gTm5yN" + "C5IUj96JpSkR92cxwwIDAQAB";
	/* 注册用的私钥,解密端的 */
	public static final String REGISTER_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALOnRQROI+3gPFlR"
			+ "QkDZbNoH3y9qipRIgOo2ow8OkiUcjzGQ1cELx9FA9c38YTiVAj2Jh0BnwX5DeugZ"
			+ "9N27pUFkgMLbl8ejf01DL7eRpqZIFAvCwNfveSCUQD2MuYcUV8UkGSBQKjelc/3w"
			+ "eSe3mBObnI0LkhSP3omlKRH3ZzHDAgMBAAECgYA8Q4ADUbL7uJyF2Zw/5K9+8+UK"
			+ "ItMZqriT2IAaLOkZSds+5TbO6wS8TO4niuGR+Wom+ltEX4mosTopYYuiVa+5D+5X"
			+ "xZLJI0kRoC5kH2x+IcVprhC9AT+lnMUWOY9odVzCYZAeg3qiX6Tvm1sO5ApFkIWA"
			+ "VwKu+TOFAao5w/rsAQJBANyPmh0yBC9o6bweKqzE3PnGiCkU+tRZIs5ZbMKcYHTF"
			+ "cl5J47h4arKb4RtO87/h+4oinv67JNK7N1xwF5jafFMCQQDQhQFim0Bm1U6NZzTK"
			+ "mqQ/upALBGv8+wVEeQow5TtvcOHiSXXABNR3yiXFwzyMeN6+P/mkd3jqkgPIu0Rb"
			+ "s0bRAkEAmOAMESpmzjmosJQ0PhYhmBrsq5T4GpFB18cR5H4hOS7WC9apOWZ1hiEX"
			+ "USdTOe+3utIuZB9w8YOEZtTm9jKSMwJAWRWp3NXVMtnIwJKqNDDTIUL0TTCrbh5U"
			+ "Rseree5FcTv51TJrMwkdOLxNXOsnUCBHkWJFpmVAbH7LTRPshXzXMQJAWUggEkhF"
			+ "5uZwJcXc3hPfWN2W+6K7Rpr4c64Gv+bKv7ejDU40L2k1FsAvNBZxKDesY8OQpAHW" + "5jhv/UeCenOjjw==";

	public static final String RECHARGE_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCpNhnKfE3PViqvqJWn54jsDqQWe0t+Pafzt8bzKBjJsTVe766EyscDLe9rlDkeDLOiwbZEe+xDwg/Jh5SWmZVm6s2NB4Rq1zw2eskDsvhCxDOn/2EINFUpJCP16wGvlbGNIDXs60QXgTDUjswvqsHst8iLJtjvz0QE7QaO04/TPQIDAQAB";

	public static final String RECHARGE_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKk2Gcp8Tc9WKq+o"
			+ "lafniOwOpBZ7S349p/O3xvMoGMmxNV7vroTKxwMt72uUOR4Ms6LBtkR77EPCD8mH"
			+ "lJaZlWbqzY0HhGrXPDZ6yQOy+ELEM6f/YQg0VSkkI/XrAa+VsY0gNezrRBeBMNSO"
			+ "zC+qwey3yIsm2O/PRATtBo7Tj9M9AgMBAAECgYBABh1aFRBn2+WI01PU1PFkx+Fk"
			+ "NECLQo7y011ukYRXhDp6clvpGMSWOmI1pT67VPj/IyZw4SazOJE6opchKHlzesOJ"
			+ "xARGiU8IMLH5hr7vr9WFvAaQpaLZFrS3dmEUxWYIPafseMSueQGLVw+pVG5qyyVD"
			+ "ikCVKRC2bDkirgLEbQJBANpdCKktrqEyPYmFcWNkM3kf/EnGcGR/jyvro+7rgtFh"
			+ "SeNTuy090eSu9Tmgjk4ZSHF8vttzaburjMh/b3qsB0sCQQDGYE2dkmQSDfZtHgLa"
			+ "ktjuK6hOpCgAsSP5DERItFg+8ScmYDyTPbAQR0/jHNz7KcJvXRqgXETyd7zb16Dw"
			+ "1NKXAkEAyTvre11qDYurQk0DzcDN3tYnPiXt9e/IFYW3MbtAd4zc9u53FJ5gDAtg"
			+ "WpcoDbknFc8MZFjcG+NgfnVSWw6mOQJAIamb4h5CTvIBnBiYoWCD4T6VS+r9QsjM"
			+ "cumK+M9pnhyr6Q4xmUbBhrGjM8XlFdXLQbyJ1SZzKLX63U9k5xkmNwJBALajdfmI"
			+ "x8hrGEslJCSn1X/fk19dHWRifmG/NM1Lp+239S9zU00PR/xeX10z0m7ZYZmw1i1i"
			+ "qkXkWb+SQusE6Sw="
			;

	public static final String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";




	/*
	 * //应用公钥 public static final String RECHARGE_PUBLIC_KEY =
	 * "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+WyDIvRu7yIhUDor8VM+76Sus XESRXGT3wFP6M5vbRvxDVU/l0HV1uBgGRcrKBYF0xPw8U2vFLH8Q5wzkBILmorxB mUz7N2+Oo4KNfO4RBuhQugbHRi5nPUcLq0lhDFz42J9NUa+/tCQShAV0Jdk/Mwhc 31XU3b7OrqBqCXEDYQIDAQAB"
	 * ; //应用私钥 public static final String RECHARGE_PRIVATE_KEY =
	 * "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAL5bIMi9G7vIiFQO" +
	 * "ivxUz7vpK6xcRJFcZPfAU/ozm9tG/ENVT+XQdXW4GAZFysoFgXTE/DxTa8UsfxDn" +
	 * "DOQEguaivEGZTPs3b46jgo187hEG6FC6BsdGLmc9RwurSWEMXPjYn01Rr7+0JBKE" +
	 * "BXQl2T8zCFzfVdTdvs6uoGoJcQNhAgMBAAECgYAsCHh09wnLTn/6scKEzDmhhjaM" +
	 * "EGvsFCtnBB5o6jdLaghAqyNoTehd6s/j45EdWliv4kFW0xVC7yAVkNzygfilay4T" +
	 * "DriQCiLhagIVyUlmoAlsLm3as2dvX+mawkO0Sb8r3rueSJ4edpGRHRD89OAt3Y1y" +
	 * "zuwId1q/2m7ZVT9GgQJBAN2+LK7YCBn5UzZL0EjhTv620seksd9+b1GQ1SHJ0prw" +
	 * "A0PyYrX9jDs2VBMMBKCr+nqlPlMWVnppbqm0w0OmrS0CQQDbw53RiejwBYFSaA0e" +
	 * "/TlLVVr13HVLogHLI1KSK42s8WPZ/IlFKaNigLrRH+cc7xRlP9qoXeZaF8lNwdgl" +
	 * "sheFAkA4hV4cXyKJhFzqjR6VRSD+mhlWHCvevPqg/trk1u3g4mbirejtXKie+zUo" +
	 * "+bpAPjYFhxNA2IPIhhFYHqRbHSQtAkAxAGQvNbX31vJoAoVydHoS/xxCF/8bdys5" +
	 * "NU4TA50ag9SE5ZdiEY+5xgRh3uA0hmag0OUbh5x2WDltmhxtA1HVAkButievIdKo" +
	 * "eF0fD790rdnRm5VJ91BLbTFx+g8CRC+OSGQYY2D9X851/GH3qUewvBlHnqA9e3At" +
	 * "xxLYpHWZhI2L"; //支付公钥 public static final String ALIPAY_PUBLIC_KEY =
	 * "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	 */

}
