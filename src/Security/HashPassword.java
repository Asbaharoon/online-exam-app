package Security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class HashPassword {
	public static String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest md=MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] b=md.digest();
		StringBuffer sb=new StringBuffer();
		for(byte bl : b) {
			sb.append(Integer.toHexString(bl & 0xff).toString());
		}
		return sb.toString();
	}
	public static void main(String[] args) {
		ArrayList<Integer> list=new ArrayList<Integer>();
		Integer i1=new Integer(1);
		Integer i2=new Integer(2);
		Integer i3=new Integer(3);
		list.add(i1);
		list.add(i2);
		System.out.println(list);
		list.remove(i1);
		list.add(i3);
		System.out.println(list);
	}
}
