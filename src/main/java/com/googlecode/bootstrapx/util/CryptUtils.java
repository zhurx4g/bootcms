package com.googlecode.bootstrapx.util;


public class CryptUtils {

	public static void int2Bytes(int n) {
		byte[] buf = new byte[4];
		buf[0] = (byte) (n >> 24);
		buf[1] = (byte) (n >> 16);
		buf[2] = (byte) (n >> 8);
		buf[3] = (byte) n;
	}

	public static long byte2Int(byte[] buf, int index) {
		int firstByte = 0;
		int secondByte = 0;
		int thirdByte = 0;
		int fourthByte = 0;
		firstByte = (0x000000FF & ((int) buf[index]));
		secondByte = (0x000000FF & ((int) buf[index + 1]));
		thirdByte = (0x000000FF & ((int) buf[index + 2]));
		fourthByte = (0x000000FF & ((int) buf[index + 3]));
		return ((long) (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFFFFFFL;
	}

	public static long byte2Long(byte[] bb, int index) {
		return ((((long) bb[index + 0] & 0xff) << 56) | (((long) bb[index + 1] & 0xff) << 48) | (((long) bb[index + 2] & 0xff) << 40)
				| (((long) bb[index + 3] & 0xff) << 32) | (((long) bb[index + 4] & 0xff) << 24) | (((long) bb[index + 5] & 0xff) << 16)
				| (((long) bb[index + 6] & 0xff) << 8) | (((long) bb[index + 7] & 0xff) << 0));
	}

	public static byte[] long2Bytes(long num) {
		byte[] b = new byte[8];
		for (int i = 0; i < 8; i++) {
			b[i] = (byte) (num >>> (56 - i * 8));
		}
		return b;
	}

	public static long sign2Long(String src) {
		if (null == src || src.length() == 0) {
			return 0;
		}
		byte[] buf = org.apache.commons.codec.digest.DigestUtils.md5(src);
	    long high = byte2Long(buf, 0);
		long low = byte2Long(buf, 8);
		long sign = high ^ low;
		byte[] bufLong = long2Bytes(sign);
		high = byte2Int(bufLong, 0);
		low = byte2Int(bufLong, 4);
		return (high << 32) | low;
	}

}
