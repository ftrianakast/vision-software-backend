package co.com.vision.prueba.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Felipe Triana <ftrianakast@gmail.com>
 * @version 1.0
 */
public class ListTools {

	/**
	 * 
	 * @param A
	 * @param B
	 * @return
	 */
	public static <T> List<T> joinLists(List<T> A, List<T> B) {
		List<T> joinList = new ArrayList<>();
		joinList.addAll(A);
		joinList.addAll(B);
		return joinList;
	}
}
