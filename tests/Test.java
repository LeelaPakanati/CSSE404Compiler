/* This is a test. */
class Test {
	public static void main (String[] args) {
		int len = 10;
		int[] a = new int[len];
		int b = 2;
		a[0] = 0;
		System.out.println(a[0]);
		a[1] = 1;
		System.out.println(a[1]);
		a[2] = 2;
		System.out.println(a[2]);
		b = a[1] + 1 * 2 + a[0];
		a[3] = b;
		System.out.println(a[3]);
		a[4] = 4;
		System.out.println(a[4]);
		a[5] = a[4] + 1;
		System.out.println(a[5]);
		a[6] = a[4] + 2;
		System.out.println(a[6]);
		a[7] = 2 + 5;
		System.out.println(a[7]);
		a[8] = a[2] * 4;
		System.out.println(a[8]);
		a[9] = 9;
		System.out.println(a[9]);
		System.out.println(99999);

		int i = 0;
		//while(i < a.length){
		//	a[i] = i;
		//	System.out.println(a[i]);
		//	i = i + 1;
		//}

		System.out.println(a.length);
	}
}
